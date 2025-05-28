package model.database.daoimpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.database.dao.UsuarioDAO;
import model.dto.Usuario;
import utils.JPAUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para realizar las acciones relacionadas con la tabla usuario.
 *
 * @author Ciso
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    EntityManager em;

    public UsuarioDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean create(Usuario usuario) {
        try {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                usuario.setNombre(usuario.getNombre());
                usuario.setApellidos(usuario.getApellidos());
                em.persist(usuario);
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
    public List<Usuario> read() {
        try {
            Query q = em.createQuery("SELECT u FROM Usuario u");
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean update(int id, Usuario usuarioModificado) {
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Usuario usuario = em.find(Usuario.class, id);
            usuario.setNombre(usuarioModificado.getNombre());
            usuario.setApellidos(usuarioModificado.getApellidos());
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
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                System.out.println(usuario);
                em.remove(usuario);
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
        try {
            if (em.find(Usuario.class, id) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario readOne(int id) {
        try {
            Usuario usuario = em.find(Usuario.class, id);
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
