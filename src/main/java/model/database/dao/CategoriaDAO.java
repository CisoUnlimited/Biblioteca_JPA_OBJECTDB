package model.database.dao;

import model.dto.Categoria;

import java.util.List;

/**
 * Interfaz DAO para gestionar categorias en la base de datos.
 * @author Ciso
 */
public interface CategoriaDAO {

    boolean create(Categoria categoria);

    List<Categoria> read();

    boolean update(int id, Categoria categoria);

    boolean delete(int id);

    boolean exists(int id);

    Categoria readOne(int id);

}
