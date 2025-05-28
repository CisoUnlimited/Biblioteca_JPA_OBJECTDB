package model.database.daoimpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.database.dao.CategoriaDAO;
import model.dto.Categoria;
import utils.JPAUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para realizar las acciones relacionadas con la tabla categoria.
 *
 * @author Ciso
 */
public class CategoriaDAOImpl implements CategoriaDAO {

    EntityManager em;

    public CategoriaDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean create(Categoria categoria) {
        try {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                categoria.setCategoria(categoria.getCategoria());
                em.persist(categoria);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Categoria> read() {
        try {
            Query q = em.createQuery("SELECT c FROM Categoria c");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean update(int id, Categoria categoriaModificada) {
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Categoria categoria = em.find(Categoria.class, id);
            categoria.setCategoria(categoriaModificada.getCategoria());
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Categoria categoria = em.find(Categoria.class, id);
            if (categoria != null) {
                System.out.println(categoria);
                em.remove(categoria);
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean exists(int id) {
        return em.find(Categoria.class, id) != null;
    }


    @Override
    public Categoria readOne(int id) {
        try {
            Categoria categoria = em.find(Categoria.class, id);
            return categoria;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
