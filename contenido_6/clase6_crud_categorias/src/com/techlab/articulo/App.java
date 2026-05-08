package com.techlab.articulo;

import java.util.ArrayList;
import java.util.Scanner;

import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.ArticuloAlimenticio;
import com.techlab.articulo.model.ArticuloElectronico;
import com.techlab.articulo.model.Categoria;

/*
 * CLASE 6 - CRUD COMPLETO DE CATEGORÍAS
 * --------------------------------------------------
 * OBJETIVO DIDÁCTICO:
 * Hasta la clase anterior trabajábamos con categorías precargadas.
 *
 * Ahora damos un paso importante:
 * las categorías pasan a tener su propio CRUD.
 *
 * Esto permite enseñar:
 * 1) que una clase puede ser administrada por separado
 * 2) que dos entidades distintas pueden convivir en el mismo sistema
 * 3) que existen reglas de integridad entre objetos relacionados
 *
 * REGLA DE NEGOCIO CLAVE:
 * No se puede eliminar una categoría si existe al menos un artículo
 * que la esté utilizando.
 *
 * En esta clase todavía no usamos:
 * - generics
 * - repositorio genérico
 * - menús separados en clases
 *
 * Eso quedará para las siguientes etapas.
 */
public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Articulo> articulos = new ArrayList<>();
        ArrayList<Categoria> categorias = new ArrayList<>();

        int opcion;

        do {
            System.out.println("\n======================================================");
            System.out.println(" SISTEMA DE ARTÍCULOS - CLASE 6 (CRUD DE CATEGORÍAS)");
            System.out.println("======================================================");
            System.out.println("1 - Ingresar artículo");
            System.out.println("2 - Listar artículos");
            System.out.println("3 - Consultar un artículo");
            System.out.println("4 - Modificar un artículo");
            System.out.println("5 - Eliminar un artículo");
            System.out.println("6 - Ingresar categoría");
            System.out.println("7 - Listar categorías");
            System.out.println("8 - Consultar una categoría");
            System.out.println("9 - Modificar una categoría");
            System.out.println("10 - Eliminar una categoría");
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
                    ingresarCategoria(scanner, categorias);
                    break;
                case 7:
                    listarCategorias(categorias);
                    break;
                case 8:
                    consultarCategoria(scanner, categorias);
                    break;
                case 9:
                    modificarCategoria(scanner, categorias);
                    break;
                case 10:
                    eliminarCategoria(scanner, categorias, articulos);
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
     * ==================================================
     * CRUD DE ARTÍCULOS
     * ==================================================
     */

    public static void ingresarArticulo(
            Scanner scanner,
            ArrayList<Articulo> articulos,
            ArrayList<Categoria> categorias
    ) {
        System.out.println("\n--- INGRESAR ARTÍCULO ---");

        // Antes de crear un artículo, validamos que existan categorías.
        if (categorias.isEmpty()) {
            System.out.println("No es posible crear artículos porque no hay categorías cargadas.");
            System.out.println("Primero debe crear al menos una categoría.");
            return;
        }

        System.out.println("1 - Artículo electrónico");
        System.out.println("2 - Artículo alimenticio");

        int tipo;
        do {
            tipo = leerEntero(scanner, "Seleccione el tipo de artículo: ");
            if (tipo != 1 && tipo != 2) {
                System.out.println("Error: debe elegir 1 o 2.");
            }
        } while (tipo != 1 && tipo != 2);

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código del artículo: ");

        if (buscarArticuloPorCodigo(articulos, codigo) != null) {
            System.out.println("Error: ya existe un artículo con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre del artículo: ");
        double precio = leerDoubleNoNegativo(scanner, "Ingrese el precio del artículo: ");

        listarCategorias(categorias);
        Categoria categoria = pedirCategoriaExistente(scanner, categorias);

        Articulo articulo;

        if (tipo == 1) {
            int garantiaMeses = leerEnteroNoNegativo(scanner, "Ingrese la garantía en meses: ");
            articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantiaMeses);
        } else {
            int diasParaVencimiento = leerEnteroNoNegativo(scanner, "Ingrese los días para vencimiento: ");
            articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, diasParaVencimiento);
        }

        articulos.add(articulo);

        System.out.println("Artículo ingresado correctamente.");
        System.out.println(articulo);
    }

    public static void listarArticulos(ArrayList<Articulo> articulos) {
        System.out.println("\n--- LISTADO DE ARTÍCULOS ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        for (Articulo articulo : articulos) {
            System.out.println(articulo);
            System.out.println("Detalle específico: " + articulo.getDetalleEspecifico());
            System.out.println("Precio final calculado: " + articulo.calcularPrecioFinal());
            System.out.println("--------------------------------------------");
        }
    }

    public static void consultarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {
        System.out.println("\n--- CONSULTAR ARTÍCULO ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código del artículo a consultar: ");

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        System.out.println("Artículo encontrado:");
        System.out.println(articulo);
        System.out.println("Detalle específico: " + articulo.getDetalleEspecifico());
        System.out.println("Precio final calculado: " + articulo.calcularPrecioFinal());
    }

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

        if (categorias.isEmpty()) {
            System.out.println("No es posible modificar artículos porque no hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código del artículo a modificar: ");

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

        if (articulo instanceof ArticuloElectronico) {
            ArticuloElectronico electronico = (ArticuloElectronico) articulo;
            int nuevaGarantia = leerEnteroNoNegativo(scanner, "Ingrese la nueva garantía en meses: ");
            electronico.setGarantiaMeses(nuevaGarantia);
        }

        if (articulo instanceof ArticuloAlimenticio) {
            ArticuloAlimenticio alimenticio = (ArticuloAlimenticio) articulo;
            int nuevosDias = leerEnteroNoNegativo(scanner, "Ingrese los nuevos días para vencimiento: ");
            alimenticio.setDiasParaVencimiento(nuevosDias);
        }

        System.out.println("Artículo modificado correctamente.");
    }

    public static void eliminarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {
        System.out.println("\n--- ELIMINAR ARTÍCULO ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código del artículo a eliminar: ");

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        articulos.remove(articulo);
        System.out.println("Artículo eliminado correctamente.");
    }

    /*
     * ==================================================
     * CRUD DE CATEGORÍAS
     * ==================================================
     */

    /*
     * Alta de categoría.
     *
     * En esta etapa las categorías ya no están precargadas.
     * El usuario las gestiona desde el menú.
     */
    public static void ingresarCategoria(Scanner scanner, ArrayList<Categoria> categorias) {
        System.out.println("\n--- INGRESAR CATEGORÍA ---");

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría: ");

        if (buscarCategoriaPorCodigo(categorias, codigo) != null) {
            System.out.println("Error: ya existe una categoría con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre de la categoría: ");

        if (existeCategoriaPorNombre(categorias, nombre)) {
            System.out.println("Error: ya existe una categoría con ese nombre.");
            return;
        }

        String descripcion = leerTextoNoVacio(scanner, "Ingrese la descripción de la categoría: ");

        Categoria categoria = new Categoria(codigo, nombre, descripcion);
        categorias.add(categoria);

        System.out.println("Categoría ingresada correctamente.");
    }

    public static void listarCategorias(ArrayList<Categoria> categorias) {
        System.out.println("\n--- LISTADO DE CATEGORÍAS ---");

        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    public static void consultarCategoria(Scanner scanner, ArrayList<Categoria> categorias) {
        System.out.println("\n--- CONSULTAR CATEGORÍA ---");

        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría a consultar: ");

        Categoria categoria = buscarCategoriaPorCodigo(categorias, codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            return;
        }

        System.out.println("Categoría encontrada:");
        System.out.println(categoria);
    }

    public static void modificarCategoria(Scanner scanner, ArrayList<Categoria> categorias) {
        System.out.println("\n--- MODIFICAR CATEGORÍA ---");

        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría a modificar: ");

        Categoria categoria = buscarCategoriaPorCodigo(categorias, codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            return;
        }

        String nuevoNombre = leerTextoNoVacio(scanner, "Ingrese el nuevo nombre de la categoría: ");

        // Validamos nombre repetido, pero permitiendo que conserve su propio nombre actual.
        if (existeCategoriaPorNombre(categorias, nuevoNombre) &&
                !categoria.getNombre().equalsIgnoreCase(nuevoNombre)) {
            System.out.println("Error: ya existe otra categoría con ese nombre.");
            return;
        }

        String nuevaDescripcion = leerTextoNoVacio(scanner, "Ingrese la nueva descripción de la categoría: ");

        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(nuevaDescripcion);

        System.out.println("Categoría modificada correctamente.");
    }

    /*
     * Eliminación de categoría con validación de integridad.
     *
     * Esta es la regla más importante de esta clase:
     * no se puede eliminar una categoría que esté siendo usada por artículos.
     */
    public static void eliminarCategoria(
            Scanner scanner,
            ArrayList<Categoria> categorias,
            ArrayList<Articulo> articulos
    ) {
        System.out.println("\n--- ELIMINAR CATEGORÍA ---");

        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría a eliminar: ");

        Categoria categoria = buscarCategoriaPorCodigo(categorias, codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            return;
        }

        // Validamos si hay artículos que usan esta categoría.
        if (categoriaTieneArticulosAsociados(categoria, articulos)) {
            System.out.println("No se puede eliminar la categoría porque tiene artículos asociados.");
            return;
        }

        categorias.remove(categoria);
        System.out.println("Categoría eliminada correctamente.");
    }

    /*
     * ==================================================
     * MÉTODOS AUXILIARES DE BÚSQUEDA
     * ==================================================
     */

    public static Articulo buscarArticuloPorCodigo(ArrayList<Articulo> articulos, int codigo) {
        for (Articulo articulo : articulos) {
            if (articulo.getCodigo() == codigo) {
                return articulo;
            }
        }
        return null;
    }

    public static Categoria buscarCategoriaPorCodigo(ArrayList<Categoria> categorias, int codigo) {
        for (Categoria categoria : categorias) {
            if (categoria.getCodigo() == codigo) {
                return categoria;
            }
        }
        return null;
    }

    public static boolean existeCategoriaPorNombre(ArrayList<Categoria> categorias, String nombre) {
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
        }
        return false;
    }

    /*
     * Método que verifica si una categoría está siendo utilizada por al menos un artículo.
     */
    public static boolean categoriaTieneArticulosAsociados(Categoria categoria, ArrayList<Articulo> articulos) {
        for (Articulo articulo : articulos) {
            if (articulo.getCategoria().getCodigo() == categoria.getCodigo()) {
                return true;
            }
        }
        return false;
    }

    public static Categoria pedirCategoriaExistente(Scanner scanner, ArrayList<Categoria> categorias) {
        while (true) {
            int codigoCategoria = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría: ");
            Categoria categoria = buscarCategoriaPorCodigo(categorias, codigoCategoria);

            if (categoria != null) {
                return categoria;
            }

            System.out.println("Error: la categoría no existe.");
        }
    }

    /*
     * ==================================================
     * MÉTODOS AUXILIARES DE LECTURA Y VALIDACIÓN
     * ==================================================
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
