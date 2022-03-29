package modelo.dao;

import modelo.entidades.Produto;

import java.util.List;

public interface ProdutoDao {

    Integer inserir(Produto obj);
    void atualizar(Produto obj);
    void deletar(Integer id);
    Produto buscarPorId(Integer id);
    List<Produto> buscarTodos();

}
