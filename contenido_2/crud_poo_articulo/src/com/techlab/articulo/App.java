package com.techlab.articulo;

// Importamos ArrayList porque seguiremos trabajando con una lista en memoria.
import java.util.ArrayList;

// Importamos Scanner para leer datos por teclado.
import java.util.Scanner;

// Importamos la clase Articulo, que ahora será nuestro modelo.
import com.techlab.articulo.model.Articulo;

/*
 * CLASE 2 - PASO DE ARRAYLIST<STRING> A ARRAYLIST<ARTICULO>
 * ---------------------------------------------------------
 * OBJETIVO DIDÁCTICO:
 * En la clase anterior, cada artículo era solamente un String.
 * Eso servía para entender el CRUD básico, pero tenía muchas limitaciones.
 *
 * Ahora damos el paso a POO.
 *
 * ¿Qué cambia?
 * 1) Ya no representamos un artículo con un texto suelto.
 * 2) Ahora cada artículo es un OBJETO.
 * 3) Ese objeto tendrá atributos propios.
 * 4) La lista ya no será ArrayList<String>, sino ArrayList<Articulo>.
 *
 * En esta etapa todavía NO trabajamos con:
 * - categoría como objeto
 * - herencia
 * - interfaces
 * - generics
 *
 * Primero queremos que el alumno entienda:
 * - qué es una clase
 * - qué es un objeto
 * - cómo crear objetos con constructor
 * - cómo acceder a sus datos mediante getters y setters
 * - cómo mostrar un objeto usando toString()
 */
public class App {

    public static void main(String[] args) {
        
       
        // Creamos un Scanner para leer los datos desde la consola.
        Scanner scanner = new Scanner(System.in);

        // Ahora la lista ya no guarda String.
        // Guarda OBJETOS de tipo Articulo.
        ArrayList<Articulo> articulos = new ArrayList<>();

        // Variable para almacenar la opción elegida en el menú.
        int opcion;

        // Repetimos el menú hasta que el usuario elija salir.
        do {
            System.out.println("\n==========================================");
            System.out.println("   SISTEMA DE ARTÍCULOS - CLASE 2 (POO)");
            System.out.println("==========================================");
            System.out.println("1 - Ingresar artículo");
            System.out.println("2 - Listar artículos");
            System.out.println("3 - Consultar un artículo");
            System.out.println("4 - Modificar un artículo");
            System.out.println("5 - Eliminar un artículo");
            System.out.println("0 - Salir");
            System.out.println("==========================================");

            opcion = leerEntero(scanner, "Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    ingresarArticulo(scanner, articulos);
                    break;
                case 2:
                    listarArticulos(articulos);
                    break;
                case 3:
                    consultarArticulo(scanner, articulos);
                    break;
                case 4:
                    modificarArticulo(scanner, articulos);
                    break;
                case 5:
                    eliminarArticulo(scanner, articulos);
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
     * MÉTODO: ingresarArticulo
     * ---------------------------------------------------------
     * En esta versión, cuando damos de alta un artículo:
     * - pedimos código
     * - pedimos nombre
     * - pedimos precio
     *
     * Luego construimos un objeto Articulo y lo guardamos en la lista.
     */
    public static void ingresarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {

        System.out.println("\n--- INGRESAR ARTÍCULO ---");

        int codigo = leerEntero(scanner, "Ingrese el código del artículo: ");

        // Validamos que no exista otro artículo con el mismo código.
        if (buscarArticuloPorCodigo(articulos, codigo) != null) {
            System.out.println("Error: ya existe un artículo con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre del artículo: ");
        double precio = leerDoubleNoNegativo(scanner, "Ingrese el precio del artículo: ");

        // Creamos un objeto Articulo usando el constructor.
        Articulo articulo = new Articulo(codigo, nombre, precio);
        

        // Guardamos el objeto en la lista.
        articulos.add(articulo);

        System.out.println("Artículo ingresado correctamente.");
    }

    /*
     * MÉTODO: listarArticulos
     * ---------------------------------------------------------
     * Recorre la lista de objetos Articulo y muestra cada uno.
     *
     * Gracias a toString(), no necesitamos mostrar cada atributo por separado.
     */
    public static void listarArticulos(ArrayList<Articulo> articulos) {

        System.out.println("\n--- LISTADO DE ARTÍCULOS ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        for (Articulo articulo : articulos) {
            System.out.println(articulo.getCodigo() + " - " + articulo.getNombre() + " - $" + articulo.getPrecio());
        }
    }

    /*
     * MÉTODO: consultarArticulo
     * ---------------------------------------------------------
     * Busca un artículo por código.
     *
     * Ahora ya no buscamos por descripción como antes.
     * Buscamos por un atributo del objeto.
     */
    public static void consultarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {

        System.out.println("\n--- CONSULTAR ARTÍCULO ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese el código del artículo a consultar: ");

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
        } else {
            System.out.println("Artículo encontrado:");
            System.out.println(articulo);
        }
    }

    /*
     * MÉTODO: modificarArticulo
     * ---------------------------------------------------------
     * Permite cambiar los datos de un artículo existente.
     *
     * ¿Qué cambia respecto de la clase anterior?
     * Que ahora modificamos los atributos del objeto usando setters.
     */
    public static void modificarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {

        System.out.println("\n--- MODIFICAR ARTÍCULO ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese el código del artículo a modificar: ");

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        String nuevoNombre = leerTextoNoVacio(scanner, "Ingrese el nuevo nombre del artículo: ");
        double nuevoPrecio = leerDoubleNoNegativo(scanner, "Ingrese el nuevo precio del artículo: ");

        // Usamos setters para modificar el estado del objeto.
        articulo.setNombre(nuevoNombre);
        articulo.setPrecio(nuevoPrecio);

        System.out.println("Artículo modificado correctamente.");
    }

    /*
     * MÉTODO: eliminarArticulo
     * ---------------------------------------------------------
     * Elimina un artículo de la lista buscando primero por código.
     */
    public static void eliminarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {

        System.out.println("\n--- ELIMINAR ARTÍCULO ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese el código del artículo a eliminar: ");

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        articulos.remove(articulo);

        System.out.println("Artículo eliminado correctamente.");
    }

    /*
     * MÉTODO: buscarArticuloPorCodigo
     * ---------------------------------------------------------
     * Recorre la lista y devuelve el objeto Articulo que tenga el código buscado.
     *
     * Si no lo encuentra, devuelve null.
     */
    public static Articulo buscarArticuloPorCodigo(ArrayList<Articulo> articulos, int codigo) {

        for (Articulo articulo : articulos) {
            if (articulo.getCodigo() == codigo) {
                return articulo;
            }
        }

        return null;
    }

    /*
     * MÉTODO: leerEntero
     * ---------------------------------------------------------
     * Lee enteros de manera segura.
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

    /*
     * MÉTODO: leerDoubleNoNegativo
     * ---------------------------------------------------------
     * Lee un número decimal y además valida que no sea negativo.
     */
    public static double leerDoubleNoNegativo(Scanner scanner, String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine());

                if (valor < 0) {
                    System.out.println("Error: el precio no puede ser negativo.");
                    continue;
                }

                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número decimal válido.");
            }
        }
    }

    /*
     * MÉTODO: leerTextoNoVacio
     * ---------------------------------------------------------
     * Obliga a ingresar un texto no vacío.
     */
    public static String leerTextoNoVacio(Scanner scanner, String mensaje) {

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
