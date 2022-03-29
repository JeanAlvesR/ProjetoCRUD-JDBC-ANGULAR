package Teste;

import modelo.servico.ProdutoServico;
import modelo.entidades.Mercado;
import modelo.entidades.Produto;
import modelo.entidades.Tecnologia;
import org.junit.Test;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProdutoTeste{

    private ProdutoServico produtoServico = new ProdutoServico();

    @Test
    public void testeBuscarTodos() {
        assertTrue(Objects.nonNull(produtoServico.executarBuscarTodos()));
    }

    @Test
    public void testeDeletar() {
        Integer id = 11;
        produtoServico.executarDeletar(id);
       assertTrue(Objects.isNull(produtoServico.exercutarBuscarPorId(id)));
    }

    @Test
    public void testeInserir() {
        Produto prod = new Produto();
        prod.setNome("TesteProduto");
        prod.setDescricao("DescricaoTeste");
        prod.addMerc(new Mercado(null, "mercTeste", null));
        prod.addTec(new Tecnologia(null, "tecTeste", null));
        Integer id = produtoServico.exercutarInserir(prod);
        System.out.println(id);
        assertTrue(Objects.nonNull(id));

    }

    @Test
    public void testeBuscarPorId() {
        Produto prod = produtoServico.exercutarBuscarPorId(9);
        System.out.println(prod);
        assertTrue(Objects.nonNull(prod));
    }
}
