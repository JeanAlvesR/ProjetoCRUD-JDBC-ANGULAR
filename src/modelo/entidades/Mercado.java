package modelo.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Mercado implements Serializable {

    private Integer id;
    private String nome;
    private Integer idProd;

    public Mercado() {
    }

    public Mercado(Integer id, String nome, Integer idProd) {
        this.id = id;
        this.nome = nome;
        this.idProd = idProd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mercado mercado = (Mercado) o;
        return id.equals(mercado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Mercado{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idProd=" + idProd +
                '}';
    }
}