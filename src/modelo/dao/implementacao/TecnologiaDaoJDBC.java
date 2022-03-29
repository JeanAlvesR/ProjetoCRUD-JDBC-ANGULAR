package modelo.dao.implementacao;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import modelo.dao.TecnologiaDao;
import modelo.entidades.Mercado;
import modelo.entidades.Tecnologia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TecnologiaDaoJDBC implements TecnologiaDao {

    private Connection conn;

    public TecnologiaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Tecnologia obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO tecnologia " +
                            "(tec_nome, prod_id) " +
                            "VALUES " +
                            "(?, ?)");

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
    public void atualizar(Tecnologia obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE tecnologia " +
                            "SET tec_nome = ?, " +
                            "prod_id = ?" +
                            "WHERE merc_id = ?");

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
            st = conn.prepareStatement("DELETE FROM tecnologia WHERE tec_id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Tecnologia buscarPorId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM tecnologia WHERE tec_id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Tecnologia obj = new Tecnologia();
                obj.setId(rs.getInt("tec_id"));
                obj.setNome(rs.getString("tec_nome"));
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
    public List<Tecnologia> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM tecnologia ORDER BY tec_nome");
            rs = st.executeQuery();

            List<Tecnologia> list = new ArrayList<>();

            while (rs.next()) {
                Tecnologia obj = new Tecnologia();
                obj.setId(rs.getInt("tec_id"));
                obj.setNome(rs.getString("tec_nome"));
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

