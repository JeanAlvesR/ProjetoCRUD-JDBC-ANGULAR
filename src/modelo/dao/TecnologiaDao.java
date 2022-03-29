package modelo.dao;

import modelo.entidades.Tecnologia;

import java.util.List;

public interface TecnologiaDao {

    void inserir(Tecnologia obj);
    void atualizar(Tecnologia obj);
    void deletar(Integer id);
    Tecnologia buscarPorId(Integer id);
    List<Tecnologia> buscarTodos();
}
