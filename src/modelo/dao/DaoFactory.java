package modelo.dao;

import db.DB;
import modelo.dao.implementacao.MercadoDaoJDBC;
import modelo.dao.implementacao.ProdutoDaoJDBC;
import modelo.dao.implementacao.TecnologiaDaoJDBC;

public class DaoFactory {
    public static ProdutoDaoJDBC criaProdutoDao(){
        return new ProdutoDaoJDBC(DB.getConnection());
    }
    public static MercadoDaoJDBC criaMercadoDao(){return new MercadoDaoJDBC(DB.getConnection());}
    public static TecnologiaDaoJDBC criaTecnologiaDao(){return new TecnologiaDaoJDBC(DB.getConnection());}
}
