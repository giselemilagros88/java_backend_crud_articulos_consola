package com.techlab.articulo.menu;

import java.util.Scanner;

import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.ArticuloAlimenticio;
import com.techlab.articulo.model.ArticuloElectronico;
import com.techlab.articulo.model.Categoria;
import com.techlab.articulo.repository.Repositorio;

/*
 * MENÚ DE ARTÍCULOS
 * --------------------------------------------------
 * Esta clase se encarga solo del CRUD de artículos.
 *
 * Ya no mezclamos su lógica con categorías ni con el main.
 *
 * Eso hace que el código quede:
 * - más ordenado
 * - más mantenible
 * - más parecido a un proyecto real
 */
public class MenuArticulos extends Menu {

    private Repositorio<Articulo> repositorioArticulos;
    private Repositorio<Categoria> repositorioCategorias;

    public MenuArticulos(
            Scanner scanner,
            Repositorio<Articulo> repositorioArticulos,
            Repositorio<Categoria> repositorioCategorias
    ) {
        super(scanner);
        this.repositorioArticulos = repositorioArticulos;
        this.repositorioCategorias = repositorioCategorias;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n---------------- MENÚ DE ARTÍCULOS ----------------");
        System.out.println("1 - Ingresar artículo");
        System.out.println("2 - Listar artículos");
        System.out.println("3 - Consultar un artículo");
        System.out.println("4 - Modificar un artículo");
        System.out.println("5 - Eliminar un artículo");
        System.out.println("0 - Volver");
        System.out.println("---------------------------------------------------");
    }

    @Override
    public void ejecutar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    ingresarArticulo();
                    break;
                case 2:
                    listarArticulos();
                    break;
                case 3:
                    consultarArticulo();
                    break;
                case 4:
                    modificarArticulo();
                    break;
                case 5:
                    eliminarArticulo();
                    break;
                case 0:
                    System.out.println("\nVolviendo al menú principal...");
                    break;
                default:
                    System.out.println("\nError: la opción ingresada no es válida.");
            }

        } while (opcion != 0);
    }

    private void ingresarArticulo() {
        System.out.println("\n--- INGRESAR ARTÍCULO ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No es posible crear artículos porque no hay categorías cargadas.");
            return;
        }

        System.out.println("1 - Artículo electrónico");
        System.out.println("2 - Artículo alimenticio");

        int tipo;
        do {
            tipo = leerEntero("Seleccione el tipo de artículo: ");
            if (tipo != 1 && tipo != 2) {
                System.out.println("Error: debe elegir 1 o 2.");
            }
        } while (tipo != 1 && tipo != 2);

        int codigo = leerEnteroNoNegativo("Ingrese el código del artículo: ");

        if (repositorioArticulos.buscarPorCodigo(codigo) != null) {
            System.out.println("Error: ya existe un artículo con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio("Ingrese el nombre del artículo: ");
        double precio = leerDoubleNoNegativo("Ingrese el precio del artículo: ");

        listarCategoriasInterno();
        Categoria categoria = pedirCategoriaExistente();

        Articulo articulo;

        if (tipo == 1) {
            int garantiaMeses = leerEnteroNoNegativo("Ingrese la garantía en meses: ");
            articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantiaMeses);
        } else {
            int diasParaVencimiento = leerEnteroNoNegativo("Ingrese los días para vencimiento: ");
            articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, diasParaVencimiento);
        }

        repositorioArticulos.agregar(articulo);

        System.out.println("Artículo ingresado correctamente.");
        System.out.println(articulo);
    }

    private void listarArticulos() {
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

    private void consultarArticulo() {
        System.out.println("\n--- CONSULTAR ARTÍCULO ---");

        if (repositorioArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEnteroNoNegativo("Ingrese el código del artículo a consultar: ");

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

    private void modificarArticulo() {
        System.out.println("\n--- MODIFICAR ARTÍCULO ---");

        if (repositorioArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo("Ingrese el código del artículo a modificar: ");

        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        String nuevoNombre = leerTextoNoVacio("Ingrese el nuevo nombre del artículo: ");
        double nuevoPrecio = leerDoubleNoNegativo("Ingrese el nuevo precio del artículo: ");

        listarCategoriasInterno();
        Categoria nuevaCategoria = pedirCategoriaExistente();

        articulo.setNombre(nuevoNombre);
        articulo.setPrecio(nuevoPrecio);
        articulo.setCategoria(nuevaCategoria);

        if (articulo instanceof ArticuloElectronico) {
            ArticuloElectronico electronico = (ArticuloElectronico) articulo;
            int nuevaGarantia = leerEnteroNoNegativo("Ingrese la nueva garantía en meses: ");
            electronico.setGarantiaMeses(nuevaGarantia);
        }

        if (articulo instanceof ArticuloAlimenticio) {
            ArticuloAlimenticio alimenticio = (ArticuloAlimenticio) articulo;
            int nuevosDias = leerEnteroNoNegativo("Ingrese los nuevos días para vencimiento: ");
            alimenticio.setDiasParaVencimiento(nuevosDias);
        }

        System.out.println("Artículo modificado correctamente.");
    }

    private void eliminarArticulo() {
        System.out.println("\n--- ELIMINAR ARTÍCULO ---");

        if (repositorioArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEnteroNoNegativo("Ingrese el código del artículo a eliminar: ");

        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        repositorioArticulos.eliminar(articulo);
        System.out.println("Artículo eliminado correctamente.");
    }

    private void listarCategoriasInterno() {
        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");

        for (Categoria categoria : repositorioCategorias.listar()) {
            System.out.println(categoria);
        }
    }

    private Categoria pedirCategoriaExistente() {
        while (true) {
            int codigoCategoria = leerEnteroNoNegativo("Ingrese el código de la categoría: ");
            Categoria categoria = repositorioCategorias.buscarPorCodigo(codigoCategoria);

            if (categoria != null) {
                return categoria;
            }

            System.out.println("Error: la categoría no existe.");
        }
    }
}
