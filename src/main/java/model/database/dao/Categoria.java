package model.database.dao;

import javax.persistence.*;

@Entity
public class Categoria {
    @Id
    @GeneratedValue
    private Integer id;

    private String categoria;

    public Categoria() {
    }

    public Categoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}