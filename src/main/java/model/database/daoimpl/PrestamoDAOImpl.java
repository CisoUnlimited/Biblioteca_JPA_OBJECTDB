package model.database.daoimpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import model.database.dao.PrestamoDAO;
import model.database.dao.Libro;
import model.database.dao.Prestamo;
import model.database.dao.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para realizar las acciones relacionadas con la tabla prestamos.
 *
 * @author Ciso
 */
public class PrestamoDAOImpl implements PrestamoDAO {

    EntityManager em;

    public PrestamoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean create(Prestamo prestamo) {
        try {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                prestamo.setIdLibro(prestamo.getIdLibro());
                prestamo.setIdUsuario(prestamo.getIdUsuario());
                prestamo.setFechaPrestamo(prestamo.getFechaPrestamo());
                em.persist(prestamo);
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
    public List<Prestamo> read() {
        try {
            // Esto falla
            Query q = em.createQuery("SELECT p FROM Prestamo p Order by p.idLibro.nombre");
            return q.getResultList();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Prestamo readOne(int id) {
        try {
            Prestamo prestamo = em.find(Prestamo.class, id);
            return prestamo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(int id, Prestamo prestamoModificado) {
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Prestamo prestamo = em.find(Prestamo.class, id);
            prestamo.setIdLibro(prestamoModificado.getIdLibro());
            prestamo.setIdUsuario(prestamoModificado.getIdUsuario());
            prestamo.setFechaPrestamo(prestamoModificado.getFechaPrestamo());
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int idPrestamo) {
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Prestamo prestamo = em.find(Prestamo.class, idPrestamo);
                System.out.println(prestamo);
                em.remove(prestamo);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isBookAvailable(Libro libro) {
        try {
            Query q = em.createQuery("SELECT p FROM Prestamo p where p.idLibro = :idLibro");
            q.setParameter("idLibro", libro);
            return q.getResultList().isEmpty();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isUserAvailable(Usuario usuario) {
        try {
            // Esto falla
            Query q = em.createQuery("SELECT p FROM Prestamo p where p.idUsuario = :idUsuario");
            q.setParameter("idUsuario", usuario);
            return q.getResultList().isEmpty();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getIdPrestamo(Libro libro) {
        try {
            Query q = em.createQuery("SELECT p.id FROM Prestamo p where p.idLibro = :idLibro");
            q.setParameter("idLibro", libro);
            List<Integer> list = q.getResultList();
            if (list.size() > 0) {
                return list.get(0);
            } else {
                System.out.println("No se encontro el libro");
                return 9999;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean exists(int id) {
        try {
            if (em.find(Prestamo.class, id) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
