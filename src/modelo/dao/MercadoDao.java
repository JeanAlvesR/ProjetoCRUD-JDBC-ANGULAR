package modelo.dao;

import modelo.entidades.Mercado;

import java.util.List;

public interface MercadoDao {

    void inserir(Mercado obj);
    void atualizar(Mercado obj);
    void deletar(Integer id);
    Mercado buscarPorId(Integer id);
    List<Mercado> buscarTodos();
}
