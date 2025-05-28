package view;

/**
 * Clase que contiene el menú de consola de la aplicación
 * @author Ciso
 */
public class Menu {

    public void menuBiblioteca() {
        System.out.println("\n--- Men\u00fa Biblioteca ---");
        System.out.println("1. USUARIOS");
        System.out.println("2. LIBROS");
        System.out.println("3. PRESTAMOS");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opci\u00f3n: ");
    }

    public void submenuUsuarios() {
        System.out.println("\n--- USUARIOS ---");
        System.out.println("1. Consultar usuarios");
        System.out.println("2. Dar de alta usuario");
        System.out.println("3. Dar de baja usuario");
        System.out.println("4. Modificar usuario");
        System.out.println("0. Volver");
        System.out.print("Selecciona una opci\u00f3n: ");
    }

    public void submenuLibros() {
        System.out.println("\n--- LIBROS ---");
        System.out.println("1. Consultar libros");
        System.out.println("2. Consultar libros por categoria");
        System.out.println("3. Dar de alta libro");
        System.out.println("4. Dar de baja libro");
        System.out.println("5. Modificar libro");
        System.out.println("6. CATEGORIAS");
        System.out.println("0. Volver");
        System.out.print("Selecciona una opci\u00f3n: ");
    }

    public void submenuCategorias() {
        System.out.println("\n--- CATEGORIAS ---");
        System.out.println("1. Consultar categorias");
        System.out.println("2. Dar de alta categoria");
        System.out.println("3. Dar de baja categoria");
        System.out.println("4. Modificar categoria");
        System.out.println("0. Volver");
    }

    public void submenuPrestamos() {
        System.out.println("\n--- PRESTAMOS ---");
        System.out.println("1. Consultar prestamos");
        System.out.println("2. Dar de alta prestamo");
        System.out.println("3. Dar de baja prestamo");
        System.out.println("4. Modificar prestamo");
        System.out.println("0. Volver");
    }

}
