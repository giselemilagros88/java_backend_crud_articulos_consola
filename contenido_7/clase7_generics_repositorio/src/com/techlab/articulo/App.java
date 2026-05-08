package com.techlab.articulo;

import java.util.Scanner;

import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.ArticuloAlimenticio;
import com.techlab.articulo.model.ArticuloElectronico;
import com.techlab.articulo.model.Categoria;
import com.techlab.articulo.repository.Repositorio;

/*
 * CLASE 7 - GENERICS Y REPOSITORIO<T>
 * --------------------------------------------------
 * OBJETIVO DIDÁCTICO:
 * Hasta la clase anterior trabajábamos directamente con:
 * - ArrayList<Articulo>
 * - ArrayList<Categoria>
 *
 * Eso estaba bien para aprender lo básico, pero tenía una desventaja:
 * la lógica de agregar, buscar, eliminar y listar estaba muy acoplada
 * a la clase App.
 *
 * Ahora vamos a dar un paso muy importante:
 * vamos a crear una clase genérica llamada Repositorio<T>.
 *
 * ¿Qué queremos enseñar?
 * 1) Qué es un generic
 * 2) Qué significa la T en una clase genérica
 * 3) Cómo reutilizar una misma lógica para distintos tipos
 * 4) Cómo desacoplar la gestión de datos de la lógica principal
 *
 * RELACIÓN CON SPRING BOOT
 * --------------------------------------------------
 * Esta idea se parece mucho a estructuras como:
 * JpaRepository<T, ID>
 *
 * La diferencia es que acá trabajamos en memoria.
 */
public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Ahora ya no usamos ArrayList directamente desde App.
        // En su lugar, usamos un repositorio genérico.
        Repositorio<Articulo> repositorioArticulos = new Repositorio<>();
        Repositorio<Categoria> repositorioCategorias = new Repositorio<>();

        int opcion;

        do {
            System.out.println("\n======================================================");
            System.out.println(" SISTEMA DE ARTÍCULOS - CLASE 7 (GENERICS Y REPO<T>)");
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
                    ingresarArticulo(scanner, repositorioArticulos, repositorioCategorias);
                    break;
                case 2:
                    listarArticulos(repositorioArticulos);
                    break;
                case 3:
                    consultarArticulo(scanner, repositorioArticulos);
                    break;
                case 4:
                    modificarArticulo(scanner, repositorioArticulos, repositorioCategorias);
                    break;
                case 5:
                    eliminarArticulo(scanner, repositorioArticulos);
                    break;
                case 6:
                    ingresarCategoria(scanner, repositorioCategorias);
                    break;
                case 7:
                    listarCategorias(repositorioCategorias);
                    break;
                case 8:
                    consultarCategoria(scanner, repositorioCategorias);
                    break;
                case 9:
                    modificarCategoria(scanner, repositorioCategorias);
                    break;
                case 10:
                    eliminarCategoria(scanner, repositorioCategorias, repositorioArticulos);
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
            Repositorio<Articulo> repositorioArticulos,
            Repositorio<Categoria> repositorioCategorias
    ) {
        System.out.println("\n--- INGRESAR ARTÍCULO ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No es posible crear artículos porque no hay categorías cargadas.");
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

        if (repositorioArticulos.buscarPorCodigo(codigo) != null) {
            System.out.println("Error: ya existe un artículo con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre del artículo: ");
        double precio = leerDoubleNoNegativo(scanner, "Ingrese el precio del artículo: ");

        listarCategorias(repositorioCategorias);
        Categoria categoria = pedirCategoriaExistente(scanner, repositorioCategorias);

        Articulo articulo;

        if (tipo == 1) {
            int garantiaMeses = leerEnteroNoNegativo(scanner, "Ingrese la garantía en meses: ");
            articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantiaMeses);
        } else {
            int diasParaVencimiento = leerEnteroNoNegativo(scanner, "Ingrese los días para vencimiento: ");
            articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, diasParaVencimiento);
        }

        repositorioArticulos.agregar(articulo);

        System.out.println("Artículo ingresado correctamente.");
        System.out.println(articulo);
    }

    public static void listarArticulos(Repositorio<Articulo> repositorioArticulos) {
        System.out.println("\n--- LISTADO DE ARTÍCULOS ---");

        if (repositorioArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        for (Articulo articulo : repositorioArticulos.listar()) {
            System.out.println(articulo);
            System.out.println("Detalle específico: " + articulo.getDetalleEspecifico());
            System.out.println("Precio final calculado: " + articulo.calcularPrecioFinal());
            System.out.println("--------------------------------------------");
        }
    }

    public static void consultarArticulo(Scanner scanner, Repositorio<Articulo> repositorioArticulos) {
        System.out.println("\n--- CONSULTAR ARTÍCULO ---");

        if (repositorioArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código del artículo a consultar: ");

        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);

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
            Repositorio<Articulo> repositorioArticulos,
            Repositorio<Categoria> repositorioCategorias
    ) {
        System.out.println("\n--- MODIFICAR ARTÍCULO ---");

        if (repositorioArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código del artículo a modificar: ");

        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        String nuevoNombre = leerTextoNoVacio(scanner, "Ingrese el nuevo nombre del artículo: ");
        double nuevoPrecio = leerDoubleNoNegativo(scanner, "Ingrese el nuevo precio del artículo: ");

        listarCategorias(repositorioCategorias);
        Categoria nuevaCategoria = pedirCategoriaExistente(scanner, repositorioCategorias);

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

    public static void eliminarArticulo(Scanner scanner, Repositorio<Articulo> repositorioArticulos) {
        System.out.println("\n--- ELIMINAR ARTÍCULO ---");

        if (repositorioArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código del artículo a eliminar: ");

        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        repositorioArticulos.eliminar(articulo);
        System.out.println("Artículo eliminado correctamente.");
    }

    /*
     * ==================================================
     * CRUD DE CATEGORÍAS
     * ==================================================
     */

    public static void ingresarCategoria(Scanner scanner, Repositorio<Categoria> repositorioCategorias) {
        System.out.println("\n--- INGRESAR CATEGORÍA ---");

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría: ");

        if (repositorioCategorias.buscarPorCodigo(codigo) != null) {
            System.out.println("Error: ya existe una categoría con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre de la categoría: ");

        if (existeCategoriaPorNombre(repositorioCategorias, nombre)) {
            System.out.println("Error: ya existe una categoría con ese nombre.");
            return;
        }

        String descripcion = leerTextoNoVacio(scanner, "Ingrese la descripción de la categoría: ");

        Categoria categoria = new Categoria(codigo, nombre, descripcion);
        repositorioCategorias.agregar(categoria);

        System.out.println("Categoría ingresada correctamente.");
    }

    public static void listarCategorias(Repositorio<Categoria> repositorioCategorias) {
        System.out.println("\n--- LISTADO DE CATEGORÍAS ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        for (Categoria categoria : repositorioCategorias.listar()) {
            System.out.println(categoria);
        }
    }

    public static void consultarCategoria(Scanner scanner, Repositorio<Categoria> repositorioCategorias) {
        System.out.println("\n--- CONSULTAR CATEGORÍA ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría a consultar: ");

        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            return;
        }

        System.out.println("Categoría encontrada:");
        System.out.println(categoria);
    }

    public static void modificarCategoria(Scanner scanner, Repositorio<Categoria> repositorioCategorias) {
        System.out.println("\n--- MODIFICAR CATEGORÍA ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría a modificar: ");

        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            return;
        }

        String nuevoNombre = leerTextoNoVacio(scanner, "Ingrese el nuevo nombre de la categoría: ");

        if (existeCategoriaPorNombre(repositorioCategorias, nuevoNombre) &&
                !categoria.getNombre().equalsIgnoreCase(nuevoNombre)) {
            System.out.println("Error: ya existe otra categoría con ese nombre.");
            return;
        }

        String nuevaDescripcion = leerTextoNoVacio(scanner, "Ingrese la nueva descripción de la categoría: ");

        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(nuevaDescripcion);

        System.out.println("Categoría modificada correctamente.");
    }

    public static void eliminarCategoria(
            Scanner scanner,
            Repositorio<Categoria> repositorioCategorias,
            Repositorio<Articulo> repositorioArticulos
    ) {
        System.out.println("\n--- ELIMINAR CATEGORÍA ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría a eliminar: ");

        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            return;
        }

        if (categoriaTieneArticulosAsociados(categoria, repositorioArticulos)) {
            System.out.println("No se puede eliminar la categoría porque tiene artículos asociados.");
            return;
        }

        repositorioCategorias.eliminar(categoria);
        System.out.println("Categoría eliminada correctamente.");
    }

    /*
     * ==================================================
     * MÉTODOS AUXILIARES
     * ==================================================
     */

    public static boolean existeCategoriaPorNombre(Repositorio<Categoria> repositorioCategorias, String nombre) {
        for (Categoria categoria : repositorioCategorias.listar()) {
            if (categoria.getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean categoriaTieneArticulosAsociados(
            Categoria categoria,
            Repositorio<Articulo> repositorioArticulos
    ) {
        for (Articulo articulo : repositorioArticulos.listar()) {
            if (articulo.getCategoria().getCodigo() == categoria.getCodigo()) {
                return true;
            }
        }
        return false;
    }

    public static Categoria pedirCategoriaExistente(Scanner scanner, Repositorio<Categoria> repositorioCategorias) {
        while (true) {
            int codigoCategoria = leerEnteroNoNegativo(scanner, "Ingrese el código de la categoría: ");
            Categoria categoria = repositorioCategorias.buscarPorCodigo(codigoCategoria);

            if (categoria != null) {
                return categoria;
            }

            System.out.println("Error: la categoría no existe.");
        }
    }

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
