package com.techlab.articulo;

import java.util.ArrayList;
import java.util.Scanner;

import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.ArticuloAlimenticio;
import com.techlab.articulo.model.ArticuloElectronico;
import com.techlab.articulo.model.Categoria;

/*
 * CLASE 4 - HERENCIA, POLIMORFISMO Y TOSTRING
 * --------------------------------------------------
 * OBJETIVO DIDÁCTICO:
 * Hasta la clase anterior, existía una sola clase Articulo.
 *
 * Ahora vamos a modelar una situación más real:
 * no todos los artículos son iguales.
 *
 * Por eso vamos a trabajar con:
 * - una clase abstracta Articulo
 * - una clase ArticuloElectronico
 * - una clase ArticuloAlimenticio
 *
 * ¿Qué queremos enseñar en esta etapa?
 * 1) Qué es una clase abstracta
 * 2) Qué significa heredar
 * 3) Qué es el polimorfismo
 * 4) Cómo una misma lista puede guardar distintos subtipos
 * 5) Qué papel cumple toString()
 * 6) Cómo complementar toString() con métodos específicos
 *
 * IMPORTANTE:
 * En esta etapa todavía NO usamos interfaz ni generics.
 * Primero queremos fijar muy bien herencia y polimorfismo.
 */
public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // La lista está declarada como ArrayList<Articulo>.
        // Esto es muy importante.
        // Significa que la lista puede guardar cualquier objeto cuyo tipo sea Articulo
        // o cualquier clase hija de Articulo.
        ArrayList<Articulo> articulos = new ArrayList<>();

        ArrayList<Categoria> categorias = new ArrayList<>();
        precargarCategorias(categorias);

        int opcion;

        do {
            System.out.println("\n======================================================");
            System.out.println(" SISTEMA DE ARTÍCULOS - CLASE 4 (HERENCIA Y TO_STRING)");
            System.out.println("======================================================");
            System.out.println("1 - Ingresar artículo");
            System.out.println("2 - Listar artículos");
            System.out.println("3 - Consultar un artículo");
            System.out.println("4 - Modificar un artículo");
            System.out.println("5 - Eliminar un artículo");
            System.out.println("6 - Listar categorías");
            System.out.println("0 - Salir");
            System.out.println("======================================================");

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
     * Seguimos usando categorías precargadas para no sumar todavía
     * el CRUD de categorías.
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
     * Ahora el usuario debe elegir qué tipo de artículo quiere crear.
     *
     * Según la elección:
     * - si es electrónico -> creamos ArticuloElectronico
     * - si es alimenticio -> creamos ArticuloAlimenticio
     *
     * Ambos objetos se guardan en la misma lista ArrayList<Articulo>.
     * Eso es polimorfismo.
     */
    public static void ingresarArticulo(
            Scanner scanner,
            ArrayList<Articulo> articulos,
            ArrayList<Categoria> categorias
    ) {
        System.out.println("\n--- INGRESAR ARTÍCULO ---");
        System.out.println("1 - Artículo electrónico");
        System.out.println("2 - Artículo alimenticio");

        int tipo;
        do {
            tipo = leerEntero(scanner, "Seleccione el tipo de artículo: ");

            if (tipo != 1 && tipo != 2) {
                System.out.println("Error: debe elegir 1 o 2.");
            }

        } while (tipo != 1 && tipo != 2);

        int codigo = leerEntero(scanner, "Ingrese el código del artículo: ");

        if (buscarArticuloPorCodigo(articulos, codigo) != null) {
            System.out.println("Error: ya existe un artículo con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre del artículo: ");
        double precio = leerDoubleNoNegativo(scanner, "Ingrese el precio del artículo: ");

        listarCategorias(categorias);
        Categoria categoria = pedirCategoriaExistente(scanner, categorias);

        // Declaramos una variable de tipo Articulo.
        // Después le asignaremos una instancia de la clase hija correspondiente.
        Articulo articulo;

        if (tipo == 1) {
            int garantiaMeses = leerEnteroNoNegativo(scanner, "Ingrese la garantía en meses: ");

            // Creamos un objeto de la clase hija ArticuloElectronico.
            articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantiaMeses);
        } else {
            int diasParaVencimiento = leerEnteroNoNegativo(scanner, "Ingrese los días para vencimiento: ");

            // Creamos un objeto de la clase hija ArticuloAlimenticio.
            articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, diasParaVencimiento);
        }

        articulos.add(articulo);

        System.out.println("Artículo ingresado correctamente.");
        System.out.println("Resumen del objeto creado:");
        System.out.println(articulo);
    }

    /*
     * MÉTODO: listarArticulos
     * --------------------------------------------------
     * Acá aparece una de las grandes ventajas de toString().
     *
     * Aunque la lista guarda distintos subtipos,
     * podemos imprimir cada objeto directamente.
     *
     * Java llamará al toString() del objeto real almacenado.
     */
    public static void listarArticulos(ArrayList<Articulo> articulos) {
        System.out.println("\n--- LISTADO DE ARTÍCULOS ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        for (Articulo articulo : articulos) {
            // Gracias a toString(), mostrar el objeto es muy simple.
            System.out.println(articulo);
            if (articulo instanceof ArticuloElectronico) {
                // tengo que transformar articulo en ArticuloElectronico para acceder a su método específico
               // articulo.nroTelMesaDeAyudaParaReclamos();
            }
           

            // Además, mostramos un detalle específico usando un método concreto.
            // Esto permite explicar que toString() no reemplaza todo:
            // muchas veces también tendremos métodos específicos del dominio.
           /* System.out.println("Detalle específico: " + articulo.getDetalleEspecifico());
            System.out.println("--------------------------------------------");*/ 
        }
    }

    /*
     * MÉTODO: consultarArticulo
     * --------------------------------------------------
     * Busca un artículo por código y luego lo muestra.
     *
     * También se aprovecha para explicar:
     * - el uso de toString()
     * - la posibilidad de consultar métodos específicos
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
            return;
        }

        System.out.println("Artículo encontrado:");
        System.out.println(articulo);
        System.out.println("Detalle específico: " + articulo.getDetalleEspecifico());
    }

    /*
     * MÉTODO: modificarArticulo
     * --------------------------------------------------
     * Permite modificar los datos comunes a todos los artículos:
     * - nombre
     * - precio
     * - categoría
     *
     * Y además modifica los datos específicos según el subtipo.
     *
     * Para eso usamos instanceof.
     *
     * instanceof permite preguntar:
     * "¿Este objeto es de este tipo concreto?"
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

        // Si el artículo real es electrónico, permitimos modificar la garantía.
        if (articulo instanceof ArticuloElectronico) {
            ArticuloElectronico electronico = (ArticuloElectronico) articulo;

            int nuevaGarantia = leerEnteroNoNegativo(scanner, "Ingrese la nueva garantía en meses: ");
            electronico.setGarantiaMeses(nuevaGarantia);
        }

        // Si el artículo real es alimenticio, permitimos modificar los días para vencimiento.
        if (articulo instanceof ArticuloAlimenticio) {
            ArticuloAlimenticio alimenticio = (ArticuloAlimenticio) articulo;

            int nuevosDias = leerEnteroNoNegativo(scanner, "Ingrese los nuevos días para vencimiento: ");
            alimenticio.setDiasParaVencimiento(nuevosDias);
        }

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
     * Muestra las categorías disponibles.
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
     * Obliga al usuario a elegir una categoría válida.
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
     * Recorre la lista y devuelve el objeto cuyo código coincida.
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
     * Recorre la lista de categorías y devuelve la coincidente.
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
     * MÉTODO: leerEnteroNoNegativo
     * --------------------------------------------------
     * Lee enteros y además valida que no sean negativos.
     */
    public static int leerEnteroNoNegativo(Scanner scanner, String mensaje) {
        while (true) {
            int valor = leerEntero(scanner, mensaje);

            if (valor < 0) {
                System.out.println("Error: el valor no puede ser negativo.");
                continue;
            }

            return valor;
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
