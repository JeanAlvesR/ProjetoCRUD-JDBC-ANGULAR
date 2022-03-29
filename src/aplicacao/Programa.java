package aplicacao;

import Teste.ProdutoTeste;
import modelo.controladores.ProdutoControler;
import modelo.dao.DaoFactory;
import modelo.dao.ProdutoDao;
import modelo.dao.TecnologiaDao;
import modelo.entidades.Produto;
import modelo.entidades.Tecnologia;

import java.io.IOException;
import java.util.List;

public class Programa {
    public static void main(String[] args) throws IOException {
        ProdutoControler.rest();
    }
}
