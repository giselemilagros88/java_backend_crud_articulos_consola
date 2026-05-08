package com.techlab.articulo;

// Seguimos trabajando con ArrayList para guardar datos en memoria.
import java.util.ArrayList;

// Importamos Scanner para leer datos por teclado.
import java.util.Scanner;

// Importamos los modelos del sistema.
import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.Categoria;

/*
 * CLASE 3 - CATEGORÍA COMO OBJETO DENTRO DE ARTÍCULO
 * --------------------------------------------------
 * OBJETIVO DIDÁCTICO:
 * Hasta la clase anterior, el artículo tenía:
 * - código
 * - nombre
 * - precio
 *
 * Ahora damos un paso más en POO:
 * un objeto puede tener como atributo a OTRO objeto.
 *
 * En vez de guardar la categoría como texto, vamos a guardar un objeto Categoria.
 *
 * Esto es importante porque:
 * 1) modela mejor la realidad
 * 2) evita repetir datos sueltos
 * 3) prepara el camino para relaciones entre clases
 * 4) luego conecta muy bien con Spring Boot y bases de datos
 *
 * En esta etapa todavía NO hacemos CRUD de categorías.
 * Para no complejizar demasiado, trabajaremos con categorías PRECARGADAS.
 */
public class App {

    public static void main(String[] args) {

        // Scanner para leer desde consola.
        Scanner scanner = new Scanner(System.in);

        // Lista donde guardaremos los artículos.
        ArrayList<Articulo> articulos = new ArrayList<>();

        // Lista donde guardaremos categorías precargadas.
        ArrayList<Categoria> categorias = new ArrayList<>();

        // Cargamos algunas categorías fijas para poder usarlas al crear artículos.
        precargarCategorias(categorias);

        int opcion;

        do {
            System.out.println("\n==================================================");
            System.out.println(" SISTEMA DE ARTÍCULOS - CLASE 3 (CATEGORÍA OBJETO)");
            System.out.println("==================================================");
            System.out.println("1 - Ingresar artículo");
            System.out.println("2 - Listar artículos");
            System.out.println("3 - Consultar un artículo");
            System.out.println("4 - Modificar un artículo");
            System.out.println("5 - Eliminar un artículo");
            System.out.println("6 - Listar categorías precargadas");
            System.out.println("0 - Salir");
            System.out.println("==================================================");

            opcion = leerEntero(scanner, "Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    ingresarArticulo(scanner, articulos, categorias);
                    break;
                case 2:
                    listarArticulos(articulos);
                    break;
                case 3:
                    consultarArticulo(scanner, articulos);
                    break;
                case 4:
                    modificarArticulo(scanner, articulos, categorias);
                    break;
                case 5:
                    eliminarArticulo(scanner, articulos);
                    break;
                case 6:
                    listarCategorias(categorias);
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
     * MÉTODO: precargarCategorias
     * --------------------------------------------------
     * Este método crea algunas categorías de ejemplo y las agrega a la lista.
     *
     * ¿Por qué lo hacemos así en esta etapa?
     * Porque todavía no queremos agregar el CRUD de categorías.
     * Solo queremos que el alumno entienda que:
     * - existe una clase Categoria
     * - un Articulo tiene una Categoria
     * - el usuario elige una categoría existente
     */
    public static void precargarCategorias(ArrayList<Categoria> categorias) {
        categorias.add(new Categoria(1, "Electrónica", "Productos tecnológicos y electrónicos"));
        categorias.add(new Categoria(2, "Periféricos", "Accesorios para computadora"));
        categorias.add(new Categoria(3, "Alimentos", "Productos alimenticios"));
        categorias.add(new Categoria(4, "Limpieza", "Artículos de limpieza del hogar"));
    }

    /*
     * MÉTODO: ingresarArticulo
     * --------------------------------------------------
     * Ahora, además de pedir código, nombre y precio,
     * también debemos pedir una categoría.
     *
     * Pero ya no será un String libre.
     * El usuario deberá elegir una categoría existente por código.
     */
    public static void ingresarArticulo(
            Scanner scanner,
            ArrayList<Articulo> articulos,
            ArrayList<Categoria> categorias
    ) {
        System.out.println("\n--- INGRESAR ARTÍCULO ---");

        int codigo = leerEntero(scanner, "Ingrese el código del artículo: ");

        if (buscarArticuloPorCodigo(articulos, codigo) != null) {
            System.out.println("Error: ya existe un artículo con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre del artículo: ");
        double precio = leerDoubleNoNegativo(scanner, "Ingrese el precio del artículo: ");

        // Mostramos las categorías disponibles para que el usuario elija una.
        listarCategorias(categorias);

        Categoria categoriaElegida = pedirCategoriaExistente(scanner, categorias);

        // Creamos el objeto Articulo con una categoría de tipo Categoria.
        Articulo articulo = new Articulo(codigo, nombre, precio, categoriaElegida);

        articulos.add(articulo);

        System.out.println("Artículo ingresado correctamente.");
    }

    /*
     * MÉTODO: listarArticulos
     * --------------------------------------------------
     * Muestra todos los artículos guardados en memoria.
     */
    public static void listarArticulos(ArrayList<Articulo> articulos) {
        System.out.println("\n--- LISTADO DE ARTÍCULOS ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        for (Articulo articulo : articulos) {
            System.out.println(articulo);
        }
    }

    /*
     * MÉTODO: consultarArticulo
     * --------------------------------------------------
     * Busca un artículo por código y lo muestra.
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
     * --------------------------------------------------
     * Permite modificar:
     * - nombre
     * - precio
     * - categoría
     *
     * Esto es importante porque ahora el alumno ve que también
     * se puede cambiar un atributo que es un objeto.
     */
    public static void modificarArticulo(
            Scanner scanner,
            ArrayList<Articulo> articulos,
            ArrayList<Categoria> categorias
    ) {
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

        listarCategorias(categorias);
        Categoria nuevaCategoria = pedirCategoriaExistente(scanner, categorias);

        articulo.setNombre(nuevoNombre);
        articulo.setPrecio(nuevoPrecio);
        articulo.setCategoria(nuevaCategoria);

        System.out.println("Artículo modificado correctamente.");
    }

    /*
     * MÉTODO: eliminarArticulo
     * --------------------------------------------------
     * Elimina un artículo por código.
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
     * MÉTODO: listarCategorias
     * --------------------------------------------------
     * Muestra las categorías precargadas disponibles.
     *
     * Esto ayuda a que el usuario sepa qué opciones puede elegir
     * al momento de ingresar o modificar un artículo.
     */
    public static void listarCategorias(ArrayList<Categoria> categorias) {
        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    /*
     * MÉTODO: pedirCategoriaExistente
     * --------------------------------------------------
     * Este método obliga al usuario a elegir una categoría válida.
     *
     * ¿Qué hace?
     * 1) pide el código de la categoría
     * 2) busca si existe
     * 3) si no existe, vuelve a pedirlo
     */
    public static Categoria pedirCategoriaExistente(Scanner scanner, ArrayList<Categoria> categorias) {

        while (true) {
            int codigoCategoria = leerEntero(scanner, "Ingrese el código de la categoría: ");

            Categoria categoria = buscarCategoriaPorCodigo(categorias, codigoCategoria);

            if (categoria != null) {
                return categoria;
            }

            System.out.println("Error: la categoría no existe.");
        }
    }

    /*
     * MÉTODO: buscarArticuloPorCodigo
     * --------------------------------------------------
     * Recorre la lista de artículos y devuelve el que coincida con el código.
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
     * MÉTODO: buscarCategoriaPorCodigo
     * --------------------------------------------------
     * Recorre la lista de categorías y devuelve la que coincida con el código.
     */
    public static Categoria buscarCategoriaPorCodigo(ArrayList<Categoria> categorias, int codigo) {

        for (Categoria categoria : categorias) {
            if (categoria.getCodigo() == codigo) {
                return categoria;
            }
        }

        return null;
    }

    /*
     * MÉTODO: leerEntero
     * --------------------------------------------------
     * Lee enteros de forma segura.
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
     * --------------------------------------------------
     * Lee un número decimal y valida que no sea negativo.
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
     * --------------------------------------------------
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
