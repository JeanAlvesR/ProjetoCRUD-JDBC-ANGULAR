/*package model.resources;

import model.entities.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value="/produtos")
public class ProdutoControler {

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Produto>> BuscarTodos() {
        Produto geladeira = new Produto(null, "Geladeira" );
        Produto microondas = new Produto(null, "microondas" );
        List<Produto> list = new ArrayList<>(Arrays.asList(geladeira, microondas));
        return ResponseEntity.ok().body(list);
    }
}
*/