package com.techlab.articulo;

import java.util.Scanner;

import com.techlab.articulo.menu.MenuArticulos;
import com.techlab.articulo.menu.MenuCategorias;
import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.Categoria;
import com.techlab.articulo.repository.Repositorio;

/*
 * CLASE 8 - MENÚS SEPARADOS EN CLASES
 * --------------------------------------------------
 * OBJETIVO DIDÁCTICO:
 * Hasta la clase anterior, toda la lógica seguía concentrada en App.
 *
 * Eso servía para aprender, pero ya empezó a quedar demasiado cargada.
 *
 * Ahora damos un paso de organización muy importante:
 * vamos a separar responsabilidades en clases distintas.
 *
 * ¿Qué enseñamos en esta etapa?
 * 1) Que App debe ser liviana
 * 2) Que una clase puede encargarse solo del menú
 * 3) Que podemos reutilizar lógica común con una clase base Menu
 * 4) Que podemos tener:
 *    - MenuArticulos
 *    - MenuCategorias
 *
 * RELACIÓN CON PROYECTOS REALES
 * --------------------------------------------------
 * Esta forma de organizar el código se parece más a cómo se estructura
 * una aplicación real:
 * - clases separadas
 * - responsabilidades más claras
 * - menos código mezclado en una sola clase
 */
public class App {

    public static void main(String[] args) {

        // Creamos un único Scanner compartido para toda la aplicación.
        Scanner scanner = new Scanner(System.in);

        // Creamos los repositorios genéricos.
        Repositorio<Articulo> repositorioArticulos = new Repositorio<>();
        Repositorio<Categoria> repositorioCategorias = new Repositorio<>();

        // Creamos los menús y les pasamos por constructor lo que necesitan.
        MenuArticulos menuArticulos = new MenuArticulos(scanner, repositorioArticulos, repositorioCategorias);
        MenuCategorias menuCategorias = new MenuCategorias(scanner, repositorioCategorias, repositorioArticulos);

        int opcion;

        do {
            System.out.println("\n======================================================");
            System.out.println(" SISTEMA DE ARTÍCULOS - CLASE 8 (MENÚS SEPARADOS)");
            System.out.println("======================================================");
            System.out.println("1 - Menú de artículos");
            System.out.println("2 - Menú de categorías");
            System.out.println("0 - Salir");
            System.out.println("======================================================");

            opcion = leerEntero(scanner, "Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    menuArticulos.ejecutar();
                    break;
                case 2:
                    menuCategorias.ejecutar();
                    break;
                case 0:
                    System.out.println("\nSaliendo del sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("\nError: la opción ingresada no es válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    /*
     * Método auxiliar estático para leer enteros en el menú principal.
     *
     * Acá lo dejamos simple porque App solo necesita coordinar.
     * Toda la lógica más rica de lectura se trabaja dentro de la clase base Menu.
     */
    public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero válido.");
            }
        }
    }
}
