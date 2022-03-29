package modelo.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Produto implements Serializable {

    private Integer id;
    private String nome;
    private String descricao;

    private List<Mercado> listMerc = new ArrayList<>();
    private List<Tecnologia> listTec = new ArrayList<>();

    public Produto() {}

    //Deixei os dois arrays no construtor, pois posso precisar futuramente.
    public Produto(Integer id, String nome, String descricao, List<Mercado> listMerc, List<Tecnologia> listTec) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.listMerc = listMerc;
        this.listTec = listTec;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setListMerc(List<Mercado> listMerc) {
        this.listMerc = listMerc;
    }

    public void setListTec(List<Tecnologia> listTec) {
        this.listTec = listTec;
    }

    public void addMerc(Mercado obj){
        listMerc.add(obj);
    }

    public List<Mercado> getListMerc(){
        return listMerc;
    }

    public void addTec(Tecnologia obj){
        listTec.add(obj);
    }

    public List<Tecnologia> getListTec() {
        return listTec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id.equals(produto.id) && nome.equals(produto.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", listMerc=" + listMerc +
                ", listTec=" + listTec +
                '}';
    }
}

