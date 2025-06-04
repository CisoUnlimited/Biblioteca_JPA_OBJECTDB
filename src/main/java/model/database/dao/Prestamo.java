package model.database.dao;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Libro idLibro;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario idUsuario;

    private Timestamp fechaPrestamo;

    public Prestamo() {
    }

    public Prestamo(Libro idLibro, Usuario idUsuario, Timestamp fechaPrestamo) {
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Libro getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Libro idLibro) {
        this.idLibro = idLibro;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Timestamp getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Timestamp fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", idLibro=" + idLibro +
                ", idUsuario=" + idUsuario +
                ", fechaPrestamo=" + fechaPrestamo +
                '}';
    }
}