package modelo.dao.implementacao;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import modelo.dao.DaoFactory;
import modelo.dao.MercadoDao;
import modelo.dao.ProdutoDao;
import modelo.dao.TecnologiaDao;
import modelo.entidades.Mercado;
import modelo.entidades.Produto;
import modelo.entidades.Tecnologia;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProdutoDaoJDBC implements ProdutoDao {

    private Connection conn;
    private MercadoDao mercadoDao = DaoFactory.criaMercadoDao();
    private TecnologiaDao tecnologiaDao = DaoFactory.criaTecnologiaDao();

    public ProdutoDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void inserir(Produto obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO produto " + "(prod_nome, prod_descricao) " + "VALUES " + "(?, ?)");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getDescricao());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
        if (obj.getListMerc() != null) {
            for (int i = 0; i > obj.getListMerc().size(); i++) {
                mercadoDao.inserir(obj.getListMerc().get(i));
            }
        }
        if (obj.getListTec() != null) {
            for (int i = 0; i > obj.getListTec().size(); i++) {
                tecnologiaDao.inserir(obj.getListTec().get(i));
            }
        }
    }

    @Override
    public void atualizar(Produto obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE produto " + "SET prod_nome = ?, prod_descricao = ?" + "WHERE prod_id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getDescricao());
            st.setInt(3, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }


    }

    @Override
    public void deletar(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM produto WHERE prod_id = ? on delete cascade");//O on delete cascade é para deletar todas as tecnologias e mercados que estão referenciando o produto que será deletado.

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Produto buscarPorId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM (produto inner join mercado_alvo on produto.prod_id = mercado_alvo.prod_id and produto.prod_id = ?) inner join tecnologia on tecnologia.prod_id = produto.prod_id");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Produto obj = new Produto();
                //para os valores não se repetirem, isto é, não ter objetos iguais com base no id, o qual foi parametro do hashcode implementado, utilizei o set.
                Set<Mercado> mercadoSet = new HashSet<>();
                Set<Tecnologia> tecnologiaSet = new HashSet<>();

                obj.setId(rs.getInt("produto.prod_id"));
                obj.setNome(rs.getString("prod_nome"));
                obj.setDescricao(rs.getString("prod_descricao"));
                Mercado mercado = new Mercado();
                Tecnologia tecnologia = new Tecnologia();
                //pegar todos os mercados associados com este produto
                do {
                    if (rs.getInt("mercado_alvo.prod_id") == obj.getId()) {
                        mercado.setId(rs.getInt(rs.getInt("mercado_alvo.merc_id")));
                        mercado.setNome(rs.getString("mercado_alvo.merc_nome"));
                        mercado.setIdProd(rs.getInt("mercado_alvo.prod_id"));
                        mercadoSet.add(mercado);
                    }

                    if (rs.getInt("tecnologia.prod_id") == obj.getId()) {
                        tecnologia.setId(rs.getInt("tecnologia.tec_id"));
                        tecnologia.setNome(rs.getString("tecnologia.tec_nome"));
                        tecnologia.setIdProd(rs.getInt("tecnologia.prod_id"));
                        tecnologiaSet.add(tecnologia);
                    }

                } while ((rs.next()));
                obj.setListMerc(new ArrayList<>(mercadoSet));
                obj.setListTec(new ArrayList<>(tecnologiaSet));
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Produto> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM (produto left join mercado_alvo on produto.prod_id = mercado_alvo.prod_id) left join tecnologia on tecnologia.prod_id = produto.prod_id");
            rs = st.executeQuery();

            Set<Produto> setProd = new HashSet<>();
            Set<Mercado> mercSet = new HashSet<>();
            Set<Tecnologia> tecSet = new HashSet<>();

            while (rs.next()) {
                Produto obj = new Produto();
                Mercado merc = new Mercado();
                Tecnologia tec = new Tecnologia();

                obj.setId(rs.getInt("produto.prod_id"));
                obj.setNome(rs.getString("prod_nome"));
                obj.setDescricao(rs.getString("prod_descricao"));

                merc.setId(rs.getInt("mercado_alvo.merc_id"));
                merc.setNome(rs.getString("mercado_alvo.merc_nome"));
                merc.setIdProd(rs.getInt("mercado_alvo.prod_id"));

                tec.setId(rs.getInt("tecnologia.tec_id"));
                tec.setNome(rs.getString("tecnologia.tec_nome"));
                tec.setIdProd(rs.getInt("tecnologia.prod_id"));

                mercSet.add(merc);
                tecSet.add(tec);
                setProd.add(obj);
            }

            organizaProduto(setProd, mercSet, tecSet);
            return new ArrayList<>(setProd);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);

        }
    }

    public void organizaProduto(Set<Produto> setProd, Set<Mercado> mercSet, Set<Tecnologia> tecSet) {

        for (Produto prod : setProd) {
            Set<Mercado> setMercTemp = mercSet.stream().filter(x -> Objects.equals(x.getIdProd(), prod.getId())).collect(Collectors.toSet());
            prod.setListMerc(new ArrayList<>(setMercTemp));

            Set<Tecnologia> setTecTemp = tecSet.stream().filter(x -> x.getIdProd() == prod.getId()).collect(Collectors.toSet());
            prod.setListTec(new ArrayList<>(setTecTemp));
        }
    }
}
