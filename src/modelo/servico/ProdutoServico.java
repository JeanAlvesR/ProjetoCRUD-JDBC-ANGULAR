package modelo.servico;

import modelo.dao.DaoFactory;
import modelo.dao.ProdutoDao;
import modelo.entidades.Produto;

import java.util.List;

public class ProdutoServico {

    private ProdutoDao produtoDao = DaoFactory.criaProdutoDao();

    public List<Produto> executarBuscarTodos() {
        return produtoDao.buscarTodos();
    }

    public Produto exercutarBuscarPorId(Integer id) {
        return produtoDao.buscarPorId(id);
    }

    public void executarDeletar (Integer id) {
        produtoDao.deletar(id);
    }

    public Integer exercutarInserir(Produto produto) {
        return produtoDao.inserir(produto);
    }

    public void exercutarAtualizar(Produto produto) {
        produtoDao.atualizar(produto);
    }
}
