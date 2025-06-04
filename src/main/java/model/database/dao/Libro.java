package model.database.dao;

import javax.persistence.*;

@Entity
public class Libro {
    @Id
    @GeneratedValue
    private Integer id;

    private String nombre;

    private String autor;

    private String editorial;

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

    public Libro() {
    }

    public Libro(String nombre, String autor, String editorial, Categoria categoria) {
        this.nombre = nombre;
        this.autor = autor;
        this.editorial = editorial;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", categoria=" + categoria +
                '}';
    }
}