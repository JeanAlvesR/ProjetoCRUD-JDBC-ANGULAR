package modelo.dao.implementacao;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import modelo.dao.MercadoDao;
import modelo.entidades.Mercado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MercadoDaoJDBC implements MercadoDao {

    private Connection conn;

    public MercadoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Mercado obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO mercado_alvo " + "(merc_nome, prod_id) " + "VALUES " + "(?, ?)");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getIdProd());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Mercado obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE mercado_alvo " + "SET merc_nome = ?, " + "prod_id = ?" + "WHERE merc_id = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getIdProd());
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
            st = conn.prepareStatement("DELETE FROM mercado_alvo WHERE merc_id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Mercado buscarPorId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM mercado_alvo WHERE merc_id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Mercado obj = new Mercado();
                obj.setId(rs.getInt("merc_id"));
                obj.setNome(rs.getString("merc_nome"));
                obj.setIdProd(rs.getInt("prod_id"));

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
    public List<Mercado> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM mercado_alvo ORDER BY merc_nome");
            rs = st.executeQuery();

            List<Mercado> list = new ArrayList<>();

            while (rs.next()) {
                Mercado obj = new Mercado();
                obj.setId(rs.getInt("merc_id"));
                obj.setNome(rs.getString("merc_nome"));
                obj.setIdProd(rs.getInt("prod_id"));
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
