package com.techlab.articulo.menu;

// Importamos Scanner porque todos los menús leerán datos por teclado.
import java.util.Scanner;

/**
 * Clase abstracta base para todos los menús del sistema.
 *
 * ¿Por qué hacemos una clase Menu?
 * Porque tanto el menú de artículos como el de categorías comparten una idea común:
 * mostrar opciones, leer una opción y ejecutar acciones.
 *
 * Esta clase concentra utilidades que ambos menús necesitan y obliga
 * a que cada menú hijo implemente su propia versión de:
 * - mostrarMenu()
 * - ejecutar()
 */
public abstract class Menu {

    // Scanner protegido para que las clases hijas puedan usarlo.
    protected Scanner scanner;

    /**
     * Constructor de la clase base Menu.
     *
     * @param scanner Scanner compartido de la aplicación.
     */
    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Cada menú concreto debe mostrar sus propias opciones.
     */
    public abstract void mostrarMenu();

    /**
     * Cada menú concreto debe definir cómo se ejecuta su loop principal.
     */
    public abstract void ejecutar();

    /**
     * Lee un entero de forma segura.
     *
     * Este método centraliza la lógica de lectura numérica para no repetirla
     * en cada menú.
     *
     * @param mensaje mensaje que se muestra al usuario.
     * @return entero válido.
     */
    protected int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero válido.");
            }
        }
    }

    /**
     * Lee un número decimal de forma segura.
     *
     * Se utiliza para precios u otros valores con decimales.
     *
     * @param mensaje mensaje que se muestra al usuario.
     * @return double válido.
     */
    protected double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número decimal válido.");
            }
        }
    }

    /**
     * Lee un texto cualquiera.
     *
     * @param mensaje mensaje que se muestra al usuario.
     * @return String ingresado por el usuario.
     */
    protected String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    /**
     * Lee una opción S/N de forma segura.
     *
     * Este método se usa especialmente en confirmaciones como:
     * - ¿Desea eliminar?
     * - ¿Desea modificar este campo?
     *
     * @param mensaje mensaje que se muestra al usuario.
     * @return true si eligió S, false si eligió N.
     */
    protected boolean leerSiNo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = scanner.nextLine().trim().toUpperCase();

            if (respuesta.equals("S")) {
                return true;
            }

            if (respuesta.equals("N")) {
                return false;
            }

            System.out.println("Error: debe ingresar S o N.");
        }
    }
}
