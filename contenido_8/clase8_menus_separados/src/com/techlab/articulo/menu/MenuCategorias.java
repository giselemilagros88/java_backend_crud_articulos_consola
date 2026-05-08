package com.techlab.articulo.menu;

import java.util.Scanner;

import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.Categoria;
import com.techlab.articulo.repository.Repositorio;

/*
 * MENÚ DE CATEGORÍAS
 * --------------------------------------------------
 * Esta clase se encarga solo del CRUD de categorías.
 *
 * También recibe el repositorio de artículos porque necesita validar
 * si una categoría está siendo utilizada antes de eliminarla.
 */
public class MenuCategorias extends Menu {

    private Repositorio<Categoria> repositorioCategorias;
    private Repositorio<Articulo> repositorioArticulos;

    public MenuCategorias(
            Scanner scanner,
            Repositorio<Categoria> repositorioCategorias,
            Repositorio<Articulo> repositorioArticulos
    ) {
        super(scanner);
        this.repositorioCategorias = repositorioCategorias;
        this.repositorioArticulos = repositorioArticulos;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n--------------- MENÚ DE CATEGORÍAS ---------------");
        System.out.println("1 - Ingresar categoría");
        System.out.println("2 - Listar categorías");
        System.out.println("3 - Consultar una categoría");
        System.out.println("4 - Modificar una categoría");
        System.out.println("5 - Eliminar una categoría");
        System.out.println("0 - Volver");
        System.out.println("--------------------------------------------------");
    }

    @Override
    public void ejecutar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    ingresarCategoria();
                    break;
                case 2:
                    listarCategorias();
                    break;
                case 3:
                    consultarCategoria();
                    break;
                case 4:
                    modificarCategoria();
                    break;
                case 5:
                    eliminarCategoria();
                    break;
                case 0:
                    System.out.println("\nVolviendo al menú principal...");
                    break;
                default:
                    System.out.println("\nError: la opción ingresada no es válida.");
            }

        } while (opcion != 0);
    }

    private void ingresarCategoria() {
        System.out.println("\n--- INGRESAR CATEGORÍA ---");

        int codigo = leerEnteroNoNegativo("Ingrese el código de la categoría: ");

        if (repositorioCategorias.buscarPorCodigo(codigo) != null) {
            System.out.println("Error: ya existe una categoría con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio("Ingrese el nombre de la categoría: ");

        if (existeCategoriaPorNombre(nombre)) {
            System.out.println("Error: ya existe una categoría con ese nombre.");
            return;
        }

        String descripcion = leerTextoNoVacio("Ingrese la descripción de la categoría: ");

        Categoria categoria = new Categoria(codigo, nombre, descripcion);
        repositorioCategorias.agregar(categoria);

        System.out.println("Categoría ingresada correctamente.");
    }

    private void listarCategorias() {
        System.out.println("\n--- LISTADO DE CATEGORÍAS ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        for (Categoria categoria : repositorioCategorias.listar()) {
            System.out.println(categoria);
        }
    }

    private void consultarCategoria() {
        System.out.println("\n--- CONSULTAR CATEGORÍA ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo("Ingrese el código de la categoría a consultar: ");

        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            return;
        }

        System.out.println("Categoría encontrada:");
        System.out.println(categoria);
    }

    private void modificarCategoria() {
        System.out.println("\n--- MODIFICAR CATEGORÍA ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo("Ingrese el código de la categoría a modificar: ");

        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            return;
        }

        String nuevoNombre = leerTextoNoVacio("Ingrese el nuevo nombre de la categoría: ");

        if (existeCategoriaPorNombre(nuevoNombre) &&
                !categoria.getNombre().equalsIgnoreCase(nuevoNombre)) {
            System.out.println("Error: ya existe otra categoría con ese nombre.");
            return;
        }

        String nuevaDescripcion = leerTextoNoVacio("Ingrese la nueva descripción de la categoría: ");

        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(nuevaDescripcion);

        System.out.println("Categoría modificada correctamente.");
    }

    private void eliminarCategoria() {
        System.out.println("\n--- ELIMINAR CATEGORÍA ---");

        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        int codigo = leerEnteroNoNegativo("Ingrese el código de la categoría a eliminar: ");

        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            return;
        }

        if (categoriaTieneArticulosAsociados(categoria)) {
            System.out.println("No se puede eliminar la categoría porque tiene artículos asociados.");
            return;
        }

        repositorioCategorias.eliminar(categoria);
        System.out.println("Categoría eliminada correctamente.");
    }

    private boolean existeCategoriaPorNombre(String nombre) {
        for (Categoria categoria : repositorioCategorias.listar()) {
            if (categoria.getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
        }
        return false;
    }

    private boolean categoriaTieneArticulosAsociados(Categoria categoria) {
        for (Articulo articulo : repositorioArticulos.listar()) {
            if (articulo.getCategoria().getCodigo() == categoria.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}
