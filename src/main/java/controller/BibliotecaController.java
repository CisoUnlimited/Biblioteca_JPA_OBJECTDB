package controller;

import javax.persistence.EntityManager;
import model.database.dao.CategoriaDAO;
import model.database.dao.LibroDAO;
import model.database.dao.PrestamoDAO;
import model.database.dao.UsuarioDAO;
import model.database.daoimpl.CategoriaDAOImpl;
import model.database.daoimpl.LibroDAOImpl;
import model.database.daoimpl.PrestamoDAOImpl;
import model.database.daoimpl.UsuarioDAOImpl;
import model.dto.Categoria;
import model.dto.Libro;
import model.dto.Prestamo;
import model.dto.Usuario;
import utils.JPAUtil;
import view.Formatters;
import view.Menu;

import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BibliotecaController {
    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;
    private final UsuarioDAO usuarioDAO;
    private final CategoriaDAO categoriaDAO;
    private final Menu menu;

    public BibliotecaController() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        this.libroDAO = new LibroDAOImpl(em);
        this.prestamoDAO = new PrestamoDAOImpl(em);
        this.usuarioDAO = new UsuarioDAOImpl(em);
        this.categoriaDAO = new CategoriaDAOImpl(em);
        this.menu = new Menu();
    }

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            BibliotecaController controller = new BibliotecaController();
            controller.logicaMenuBiblioteca(scanner);
            JPAUtil.close();
        }

    }

    public void logicaMenuBiblioteca(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            menu.menuBiblioteca();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (opcion) {
                    case 1 -> logicaSubmenuUsuarios(scanner);
                    case 2 -> logicaSubmenuLibros(scanner);
                    case 3 -> logicaSubmenuPrestamos(scanner);
                    case 0 -> salir = true;
                    default -> System.out.println("Opción no válida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error en el menú Biblioteca: Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private void logicaSubmenuUsuarios(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            menu.submenuUsuarios();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (opcion) {
                    case 1 -> consultarUsuarios();
                    case 2 -> altaUsuario(scanner);
                    case 3 -> bajaUsuario(scanner);
                    case 4 -> modificarUsuario(scanner);
                    case 0 -> volver = true;
                    default -> System.out.println("Opción no válida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error en el menú Usuarios: Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private void logicaSubmenuLibros(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            menu.submenuLibros();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (opcion) {
                    case 1 -> consultarLibros();
                    case 2 -> consultarLibrosPorCategoria(scanner);
                    case 3 -> altaLibro(scanner);
                    case 4 -> bajaLibro(scanner);
                    case 5 -> modificarLibro(scanner);
                    case 6 -> logicaSubmenuCategorias(scanner);
                    case 0 -> volver = true;
                    default -> System.out.println("Opción no válida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error en el menú Libros: Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private void logicaSubmenuCategorias(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            menu.submenuCategorias();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (opcion) {
                    case 1 -> consultarCategorias();
                    case 2 -> altaCategoria(scanner);
                    case 3 -> bajaCategoria(scanner);
                    case 4 -> modificarCategoria(scanner);
                    case 0 -> volver = true;
                    default -> System.out.println("Opción no válida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error en el menú Categorías: Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private void logicaSubmenuPrestamos(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            menu.submenuPrestamos();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (opcion) {
                    case 1 -> consultarPrestamos();
                    case 2 -> altaPrestamo(scanner);
                    case 3 -> bajaPrestamo(scanner);
                    case 4 -> modificarPrestamo(scanner);
                    case 0 -> volver = true;
                    default -> System.out.println("Opción no válida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error en el menú Préstamos: Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private boolean consultarUsuarios() {
        System.out.println("\n--- Listar todos los usuarios ---");
        List<Usuario> usuarios = usuarioDAO.read();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return false;
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
            return true;
        }
    }

    private void altaUsuario(Scanner scanner) {
        consultarUsuarios();
        System.out.println("\n--- Dar de alta nuevo usario ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();

        Usuario nuevoUsuario = new Usuario(nombre, apellidos);
        try {
            if (usuarioDAO.create(nuevoUsuario)) {
                System.out.println("Nuevo usuario agregado con éxito.");
            } else {
                System.out.println("Error al agregar el usuario.");
            }
        } catch (Exception e) {

        }
    }

    private void bajaUsuario(Scanner scanner) {
        if (consultarUsuarios()) {
            System.out.println("\n--- Eliminar usuario ---");
            System.out.print("ID del usuario a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (usuarioDAO.exists(id)) {
                if (prestamoDAO.isUserAvailable(usuarioDAO.readOne(id))) {
                    if (usuarioDAO.delete(id)) {
                        System.out.println("Usuario eliminado con éxito.");
                    } else {
                        System.out.println("Error al eliminar el usuario.");
                    }
                } else {
                    System.out.println("Error al eliminar el usuario. Compruebe préstamos pendientes.");
                }
            } else {
                System.out.println("El ID introducido no se encuentra en la base de datos.");
            }
        }
    }

    private void modificarUsuario(Scanner scanner) {
        if (consultarUsuarios()) {
            System.out.println("\n--- Modificar usuario ---");
            System.out.print("ID del usuario a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer

            if (usuarioDAO.exists(id)) {
                System.out.print("Nuevo nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Nuevos apellidos: ");
                String apellidos = scanner.nextLine();

                Usuario usuarioModificado = new Usuario(nombre, apellidos);
                if (usuarioDAO.update(id, usuarioModificado)) {
                    System.out.println("Usuario modificado con éxito.");
                } else {
                    System.out.println("Error al modificar el usuario.");
                }
            } else {
                System.out.println("El ID introducido no existe.");
            }
        }
    }

    private boolean consultarLibros() {
        System.out.println("\n--- Listar todos los libros ---");
        List<Libro> libros = libroDAO.read();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return false;
        } else {
            for (Libro libro : libros) {
                System.out.println(libro);
            }
            return true;
        }
    }

    private boolean consultarLibrosPorCategoria(Scanner scanner) {
        System.out.println("\n--- Listar todos los libros de una categoria ---");
        consultarCategorias();
        System.out.println("Seleccione una categoria: ");
        int cat = scanner.nextInt();
        scanner.nextLine();
        Categoria categoria = categoriaDAO.readOne(cat);
        List<Libro> libros = libroDAO.readByCategory(categoria);
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados con esa categoria.");
            return false;
        } else {
            for (Libro libro : libros) {
                System.out.println(libro);
            }
            return true;
        }
    }

    private void altaLibro(Scanner scanner) {
        consultarLibros();
        consultarCategorias();
        System.out.println("\n--- Dar de alta nuevo libro ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Editorial: ");
        String editorial = scanner.nextLine();
        consultarCategorias();
        System.out.print("Categoría (ID): ");
        int categoriaID = scanner.nextInt();
        while (!categoriaDAO.exists(categoriaID)) {
            consultarCategorias();
            System.out.println("El ID de categoría introducido no existe. Vuelva a intentarlo.");
            System.out.print("Categoría (ID): ");
            categoriaID = scanner.nextInt();
            scanner.nextLine();
        }
        Categoria categoria = categoriaDAO.readOne(categoriaID);
        Libro nuevoLibro = new Libro(nombre, autor, editorial, categoria);
        if (libroDAO.create(nuevoLibro)) {
            System.out.println("Libro agregado con éxito.");
        } else {
            System.out.println("Error al agregar el libro.");
        }

    }

    private void bajaLibro(Scanner scanner) {
        if (consultarLibros()) {
            System.out.println("\n--- Eliminar libro ---");
            System.out.print("ID del libro a borrar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (libroDAO.exists(id)) {
                Libro libro = libroDAO.readOne(id);
                if (prestamoDAO.isBookAvailable(libro)) {
                    if (libroDAO.delete(id)) {
                        System.out.println("Libro eliminado con éxito.");
                    } else {
                        System.out.println("Error al eliminar el libro.");
                    }
                } else {
                    System.out.println("Error al eliminar el libro. Compruebe préstamos pendientes.");
                }
            } else {
                System.out.println("El ID introducido no se encuentra en la base de datos.");
            }
        }
    }

    private void modificarLibro(Scanner scanner) {
        if (consultarLibros()) {
            System.out.println("\n--- Modificar libro ---");
            System.out.print("ID del libro a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer
            while (!libroDAO.exists(id)) {
                System.out.println("El ID introducido no se encuentra en la base de datos. Pruebe de nuevo");
                consultarLibros();
                System.out.print("ID del libro a modificar: ");
                id = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Nuevo autor: ");
            String autor = scanner.nextLine();
            System.out.print("Nueva editorial: ");
            String editorial = scanner.nextLine();
            consultarCategorias();
            System.out.print("Nueva categoría (ID): ");
            int categoriaID = scanner.nextInt();
            scanner.nextLine();
            while (!categoriaDAO.exists(categoriaID)) {
                System.out.println("El ID de categoría introducido no existe. Vuelva a intentarlo.");
                consultarCategorias();
                System.out.print("Nueva categoría (ID): ");
                categoriaID = scanner.nextInt();
                scanner.nextLine();
            }
            Categoria categoria = categoriaDAO.readOne(categoriaID);
            Libro libroModificado = new Libro(nombre, autor, editorial, categoria);
            if (libroDAO.update(id, libroModificado)) {
                System.out.println("Libro modificado con éxito.");
            } else {
                System.out.println("Al menos uno de los dos siguientes elementos no existe en la base de datos: 'ID del libro', 'ID de la categoría'.");
                System.out.println("Error al modificar el libro.");
            }
        }
    }

    private boolean consultarCategorias() {
        System.out.println("\n--- Listar todas las categorías ---");
        List<Categoria> categorias = categoriaDAO.read();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías registradas.");
            return false;
        } else {
            for (Categoria categoria : categorias) {
                System.out.println(categoria);
            }
            return true;
        }
    }

    private void altaCategoria(Scanner scanner) {
        consultarCategorias();
        System.out.println("\n--- Dar de alta nueva categoría ---");
        System.out.print("Nombre: ");
        String categoria = scanner.nextLine();
        Categoria nuevaCategoria = new Categoria(categoria);
        if (categoriaDAO.create(nuevaCategoria)) {
            System.out.println("Categoría agregada con éxito.");
        } else {
            System.out.println("Error al agregar la categoría.");
        }
    }

    private void bajaCategoria(Scanner scanner) {
        if (consultarCategorias()) {
            System.out.println("\n--- Eliminar categoría ---");
            System.out.print("ID de la categoría a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (categoriaDAO.exists(id)) {
                if (libroDAO.readByCategory(categoriaDAO.readOne(id)).isEmpty()) {
                    if (categoriaDAO.delete(id)) {
                        System.out.println("Categoría eliminada con éxito.");
                    } else {
                        System.out.println("Error al eliminar la categoría.");
                    }
                } else {
                    System.out.println("Error al eliminar la categoría. Compruebe que no pertenezca a ningún libro.");
                }
            } else {
                System.out.println("El ID introducido no se encuentra en la base de datos.");
            }
        }
    }

    private void modificarCategoria(Scanner scanner) {
        if (consultarCategorias()) {
            System.out.println("\n--- Modificar categoría ---");
            System.out.print("ID de la categoría a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer
            while (!categoriaDAO.exists(id)) {
                System.out.println("El ID de categoría introducido no existe. Vuelva a intentarlo.");
                consultarCategorias();
                System.out.print("ID de la categoría a modificar: ");
                id = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();

            Categoria categoriaModificada = new Categoria(nombre);
            if (categoriaDAO.update(id, categoriaModificada)) {
                System.out.println("Categoría modificada con éxito.");
            } else {
                System.out.println("Error al modificar la categoría.");
            }
        }
    }

    private boolean consultarPrestamos() {
        System.out.println("\n--- Listar todos los préstamos ---");
        List<Prestamo> prestamos = prestamoDAO.read();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos activos.");
            return false;
        } else {
            for (Prestamo prestamo : prestamos) {
                System.out.println(prestamo);
            }
            return true;
        }
    }

    private void altaPrestamo(Scanner scanner) {
        consultarPrestamos();
        System.out.println("\n--- Gestionar préstamo ---");

        consultarLibros();
        System.out.print("ID del libro: ");
        int idLibro = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        while (!libroDAO.exists(idLibro)) {
            System.out.println("El ID de libro introducido no existe. Vuelva a intentarlo.");
            consultarLibros();
            System.out.print("ID del libro: ");
            idLibro = scanner.nextInt();
            scanner.nextLine();
        }

        Libro libro = libroDAO.readOne(idLibro);

        // Aquí falla
        if (!prestamoDAO.isBookAvailable(libro)) {
            System.out.println("El libro ya está prestado.");
            return;
        }

        consultarUsuarios();
        System.out.print("ID del usuario: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        while (!usuarioDAO.exists(idUsuario)) {
            System.out.println("El ID de usuario introducido no existe. Vuelva a intentarlo.");
            consultarUsuarios();
            System.out.print("ID del usuario: ");
            idUsuario = scanner.nextInt();
            scanner.nextLine();
        }
        Usuario usuario = usuarioDAO.readOne(idUsuario);

        Timestamp fechaPrestamo = new Timestamp(System.currentTimeMillis());
        Prestamo prestamo = new Prestamo(libro, usuario, fechaPrestamo);

        if (prestamoDAO.create(prestamo)) {
            System.out.println("Préstamo registrado con éxito.");
        } else {
            System.out.println("Al menos uno de los dos siguientes elementos no existe en la base de datos: 'ID del libro', 'ID del usuario'.");
            System.out.println("Error al registrar el préstamo.");
        }
    }

    private void bajaPrestamo(Scanner scanner) {
        if (consultarPrestamos()) {
            System.out.println("\n--- Gestionar devolución ---");
            System.out.print("ID del libro: ");
            int idLibro = scanner.nextInt();
            scanner.nextLine();
            Libro libro = libroDAO.readOne(idLibro);
            // Verifica si el libro está prestado
            int idPrestamo = prestamoDAO.getIdPrestamo(libro);

            // Elimina el préstamo de la base de datos
            if (prestamoDAO.delete(idPrestamo)) {
                System.out.println("Devolución registrada con éxito.");
            } else {
                System.out.println("Error al registrar la devolución.");
            }
        }
    }

    private void modificarPrestamo(Scanner scanner) {
        if (consultarPrestamos()) {
            System.out.println("\n--- Modificar préstamo ---");
            System.out.print("ID del préstamo a modificar: ");
            int idPrestamo = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer
            while (!prestamoDAO.exists(idPrestamo)) {
                System.out.println("El ID introducido no existe. Vuelva a intentarlo.");
                consultarPrestamos();
                System.out.print("ID del préstamo a modificar: ");
                idPrestamo = scanner.nextInt();
                scanner.nextLine();
            }
            Prestamo prestamo = prestamoDAO.readOne(idPrestamo);

            consultarLibros();
            System.out.print("Nuevo idLibro: ");
            int idLibro = scanner.nextInt();
            scanner.nextLine();
            while (!libroDAO.exists(idLibro)) {
                System.out.println("El ID de libro introducido no existe. Vuelva a intentarlo.");
                consultarLibros();
                System.out.print("Nuevo idLibro: ");
                idLibro = scanner.nextInt();
                scanner.nextLine();
            }
            Libro nuevoLibro = libroDAO.readOne(idLibro);

            if (!prestamoDAO.isBookAvailable(nuevoLibro)) {
                System.out.println("El libro ya está prestado.");
                return;
            }

            consultarUsuarios();
            System.out.print("Nuevo idUsuario: ");
            int idUsuario = scanner.nextInt();
            scanner.nextLine();
            while (!usuarioDAO.exists(idUsuario)) {
                System.out.println("El ID de usuario introducido no existe. Vuelva a intentarlo.");
                consultarUsuarios();
                System.out.print("Nuevo idUsuario: ");
                idUsuario = scanner.nextInt();
                scanner.nextLine();
            }
            Usuario nuevoUsuario = usuarioDAO.readOne(idUsuario);

            System.out.print("Nueva fecha (Presione INTRO en caso de querer mantener la fecha anterior): ");
            String nuevaFechaPrestamo = scanner.nextLine();
            Timestamp fechaPrestamo = null;
            if (nuevaFechaPrestamo.isEmpty()) {
                fechaPrestamo = prestamo.getFechaPrestamo();
            } else {
                fechaPrestamo = Formatters.dateTimestampFormatter(nuevaFechaPrestamo);
            }
            Prestamo newPrestamo = new Prestamo(nuevoLibro, nuevoUsuario, fechaPrestamo);

            if (prestamoDAO.update(idPrestamo, newPrestamo)) {
                System.out.println("Préstamo modificado con éxito.");
            } else {
                System.out.println("Al menos uno de los dos siguientes elementos no existe en la base de datos: 'ID del libro', 'ID del usuario'.");
                System.out.println("Error al modificar el préstamo.");
            }
        }
    }

}
