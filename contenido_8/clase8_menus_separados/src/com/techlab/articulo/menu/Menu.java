package com.techlab.articulo.menu;

import java.util.Scanner;

/*
 * CLASE BASE MENU
 * --------------------------------------------------
 * Esta clase concentra utilidades comunes a todos los menús.
 *
 * ¿Por qué conviene crearla?
 * Porque tanto MenuArticulos como MenuCategorias necesitan:
 * - leer enteros
 * - leer decimales
 * - leer textos
 * - validar entradas
 *
 * Si no tuviéramos esta clase, repetiríamos el mismo código muchas veces.
 *
 * Entonces:
 * - Menu es la superclase
 * - MenuArticulos y MenuCategorias heredan de Menu
 */
public abstract class Menu {

    // Scanner protegido para que las clases hijas puedan usarlo.
    protected Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    /*
     * Cada menú hijo deberá definir cómo mostrar su propio menú.
     */
    public abstract void mostrarMenu();

    /*
     * Cada menú hijo deberá definir su propio loop de ejecución.
     */
    public abstract void ejecutar();

    /*
     * Métodos protegidos reutilizables
     * --------------------------------------------------
     * Se declaran protected para que solo los usen la clase base
     * y las clases hijas.
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

    protected int leerEnteroNoNegativo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);

            if (valor < 0) {
                System.out.println("Error: el valor no puede ser negativo.");
                continue;
            }

            return valor;
        }
    }

    protected double leerDoubleNoNegativo(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine());

                if (valor < 0) {
                    System.out.println("Error: el valor no puede ser negativo.");
                    continue;
                }

                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número decimal válido.");
            }
        }
    }

    protected String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();

            if (!texto.trim().isEmpty()) {
                return texto.trim();
            }

            System.out.println("Error: el texto no puede estar vacío.");
        }
    }
}
