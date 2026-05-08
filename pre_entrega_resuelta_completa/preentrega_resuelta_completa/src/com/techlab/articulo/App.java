package com.techlab.articulo;

// Importamos Scanner para poder leer datos por teclado desde la consola.
import java.util.Scanner;

// Importamos los dos menús concretos que utilizará la aplicación.
import com.techlab.articulo.menu.MenuArticulos;
import com.techlab.articulo.menu.MenuCategorias;

// Importamos los modelos que administrará el sistema.
import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.Categoria;

// Importamos el repositorio genérico, que nos permitirá guardar objetos en memoria.
import com.techlab.articulo.repository.Repositorio;

/**
 * Clase principal de la aplicación.
 *
 * Esta clase contiene el método main, que es el punto de entrada del programa.
 * Desde aquí se inicializan:
 * - el Scanner compartido,
 * - los repositorios en memoria,
 * - los menús de artículos y categorías,
 * - y el menú principal que permite navegar entre ambos.
 */
public class App {

    public static void main(String[] args) {

        // Creamos UN SOLO Scanner para toda la aplicación.
        // Esto evita tener múltiples Scanners leyendo System.in, lo cual no es recomendable.
        Scanner scanner = new Scanner(System.in);

        // Creamos el repositorio genérico para guardar categorías en memoria.
        Repositorio<Categoria> repositorioCategorias = new Repositorio<>();

        // Creamos el repositorio genérico para guardar artículos en memoria.
        Repositorio<Articulo> repositorioArticulos = new Repositorio<>();

        // Creamos el menú de categorías y le pasamos:
        // - el Scanner compartido
        // - el repositorio donde se guardarán las categorías
        // - el repositorio de artículos, porque se necesita para validar
        //   si una categoría está siendo utilizada antes de eliminarla.
        MenuCategorias menuCategorias = new MenuCategorias(
                scanner,
                repositorioCategorias,
                repositorioArticulos
        );

        // Creamos el menú de artículos y le pasamos:
        // - el Scanner compartido
        // - el repositorio de artículos
        // - el repositorio de categorías, porque un artículo necesita
        //   asociarse a una categoría ya existente.
        MenuArticulos menuArticulos = new MenuArticulos(
                scanner,
                repositorioArticulos,
                repositorioCategorias
        );

        // Variable que almacenará la opción elegida en el menú principal.
        int opcion;

        // Utilizamos un ciclo do-while porque queremos mostrar el menú
        // al menos una vez y repetirlo hasta que el usuario elija salir.
        do {
            // Mostramos el encabezado del sistema.
            System.out.println("\n==================================================");
            System.out.println(" SISTEMA DE GESTIÓN - PREENTREGA JAVA CONSOLA");
            System.out.println("==================================================");
            System.out.println("1 - Menú de artículos");
            System.out.println("2 - Menú de categorías");
            System.out.println("0 - Salir");
            System.out.println("==================================================");

            // Leemos la opción del usuario utilizando un método auxiliar.
            opcion = leerEntero(scanner, "Ingrese una opción: ");

            // Evaluamos la opción elegida.
            switch (opcion) {
                case 1:
                    // Si elige 1, ejecutamos el submenú de artículos.
                    menuArticulos.ejecutar();
                    break;

                case 2:
                    // Si elige 2, ejecutamos el submenú de categorías.
                    menuCategorias.ejecutar();
                    break;

                case 0:
                    // Si elige 0, mostramos mensaje de salida.
                    System.out.println("\nGracias por utilizar el sistema. ¡Hasta luego!");
                    break;

                default:
                    // Si la opción no existe, informamos el error.
                    System.out.println("\nError: la opción ingresada no es válida.");
            }

        } while (opcion != 0);

        // Cerramos el Scanner al finalizar el programa.
        scanner.close();
    }

    /**
     * Método auxiliar para leer enteros de forma segura.
     *
     * ¿Por qué lo usamos?
     * Porque si el usuario escribe letras en lugar de números, el programa
     * no debe romperse. En ese caso, se captura el error y se vuelve a pedir el dato.
     *
     * @param scanner Scanner compartido de la aplicación.
     * @param mensaje Texto que se muestra al usuario.
     * @return entero válido ingresado por el usuario.
     */
    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                // Mostramos el mensaje de pedido.
                System.out.print(mensaje);

                // Leemos la línea completa y la convertimos a entero.
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Si falla la conversión, avisamos y repetimos.
                System.out.println("Error: debe ingresar un número entero válido.");
            }
        }
    }
}
