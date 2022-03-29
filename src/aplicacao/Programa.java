package aplicacao;

import modelo.dao.DaoFactory;
import modelo.dao.ProdutoDao;
import modelo.dao.TecnologiaDao;
import modelo.entidades.Produto;
import modelo.entidades.Tecnologia;

import java.util.List;

public class Programa {
    public static void main(String[] args) {
        ProdutoDao produtoDao = DaoFactory.criaProdutoDao();

        //Produto p1 = produtoDao.buscarPorId(9);

      List<Produto> li1 = produtoDao.buscarTodos();

        for (Produto x:
             li1) {
            System.out.println(x);
        }

      /*  Produto prod = new Produto();
        prod.setId(9);
        prod.setNome("PlayStation");
        prod.setDescricao("40028922");*/

    //    produtoDao.atualizar(prod);


        /*produtoDao.atualizar(prod);
       /* produtoDao.inserir(prod);*/


        //System.out.println("Novo id do produto: "+ prod.getId());
    }
}
