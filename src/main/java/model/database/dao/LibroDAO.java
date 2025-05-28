package model.database.dao;

import model.dto.Categoria;
import model.dto.Libro;

import java.util.List;

/**
 * Interfaz DAO para gestionar libros en la base de datos.
 * @author Ciso
 */
public interface LibroDAO {

    boolean create(Libro libro);

    List<Libro> read();

    List<Libro> readByCategory(Categoria categoria);

    boolean update(int id, Libro libro);

    boolean delete(int id);

    boolean exists(int id);

    Libro readOne(int id);

}
