package com.techlab.articulo.menu;

// Importamos listas porque vamos a recorrer resultados del repositorio.
import java.util.List;
import java.util.Scanner;

// Importamos los modelos del dominio.
import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.ArticuloAlimenticio;
import com.techlab.articulo.model.ArticuloElectronico;
import com.techlab.articulo.model.Categoria;

// Importamos el repositorio genérico.
import com.techlab.articulo.repository.Repositorio;

// Importamos utilidades de validación y secuencias automáticas.
import com.techlab.articulo.utils.Secuencias;
import com.techlab.articulo.utils.Validaciones;

/**
 * Menú encargado del CRUD de artículos.
 *
 * Este menú tiene acceso a:
 * - el repositorio de artículos
 * - el repositorio de categorías
 *
 * ¿Por qué necesita el repositorio de categorías?
 * Porque todo artículo debe pertenecer a una categoría ya existente.
 */
public class MenuArticulos extends Menu {

    // Repositorio genérico donde se guardan los artículos.
    private final Repositorio<Articulo> repositorioArticulos;

    // Repositorio genérico donde se guardan las categorías.
    private final Repositorio<Categoria> repositorioCategorias;

    /**
     * Constructor del menú de artículos.
     *
     * @param scanner Scanner compartido.
     * @param repositorioArticulos repositorio de artículos.
     * @param repositorioCategorias repositorio de categorías.
     */
    public MenuArticulos(
            Scanner scanner,
            Repositorio<Articulo> repositorioArticulos,
            Repositorio<Categoria> repositorioCategorias
    ) {
        // Llamamos al constructor de la clase padre.
        super(scanner);

        // Guardamos las referencias recibidas.
        this.repositorioArticulos = repositorioArticulos;
        this.repositorioCategorias = repositorioCategorias;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n---------------- MENÚ DE ARTÍCULOS ----------------");
        System.out.println("1 - Ingresar artículo");
        System.out.println("2 - Consultar artículos");
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

    /**
     * Alta de un artículo.
     *
     * Este método:
     * 1. Verifica que existan categorías cargadas.
     * 2. Pide el tipo de artículo.
     * 3. Pide datos comunes y específicos.
     * 4. Valida toda la información.
     * 5. Crea el objeto correcto.
     * 6. Lo guarda en memoria.
     */
    private void ingresarArticulo() {
        // No podemos crear artículos si aún no existen categorías.
        if (repositorioCategorias.estaVacio()) {
            System.out.println("\nNo es posible crear artículos porque no hay categorías cargadas.");
            System.out.println("Primero debe ingresar al menos una categoría.");
            return;
        }

        System.out.println("\nAlta de artículo");
        System.out.println("1 - Artículo electrónico");
        System.out.println("2 - Artículo alimenticio");

        int tipo;
        do {
            tipo = leerEntero("Seleccione el tipo de artículo: ");
            if (tipo != 1 && tipo != 2) {
                System.out.println("Error: debe elegir 1 o 2.");
            }
        } while (tipo != 1 && tipo != 2);

        String nombre = pedirNombreArticulo();
        double precio = pedirPrecioArticulo();
        Categoria categoria = pedirCategoriaExistente();

        // Validamos duplicados lógicos.
        if (existeArticuloDuplicado(nombre, categoria, -1)) {
            System.out.println("\nError: ya existe un artículo con ese nombre dentro de esa categoría.");
            return;
        }

        // Generamos el código automático.
        int codigo = Secuencias.generarCodigoArticulo();

        Articulo nuevoArticulo;

        if (tipo == 1) {
            int garantiaMeses = pedirGarantia();

            // Creamos un artículo electrónico.
            nuevoArticulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantiaMeses);
        } else {
            int diasParaVencimiento = pedirDiasParaVencimiento();

            // Creamos un artículo alimenticio.
            nuevoArticulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, diasParaVencimiento);
        }

        // Guardamos el artículo en memoria.
        boolean agregado = repositorioArticulos.agregar(nuevoArticulo);

        if (agregado) {
            System.out.println("\nArtículo ingresado correctamente.");
            System.out.println(nuevoArticulo);
        } else {
            System.out.println("\nError: no se pudo guardar el artículo.");
        }
    }

    /**
     * Muestra todos los artículos almacenados.
     */
    private void listarArticulos() {
        List<Articulo> articulos = repositorioArticulos.listar();

        if (articulos.isEmpty()) {
            System.out.println("\nNo hay artículos cargados para mostrar.");
            return;
        }

        System.out.println("\nListado de artículos:");
        for (Articulo articulo : articulos) {
            System.out.println(articulo);
        }
    }

    /**
     * Consulta un artículo por código.
     */
    private void consultarArticulo() {
        if (repositorioArticulos.estaVacio()) {
            System.out.println("\nNo hay artículos cargados.");
            return;
        }

        listarArticulos();

        int codigo = leerEntero("\nIngrese el código del artículo a consultar: ");

        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("\nError: no existe un artículo con ese código.");
            return;
        }

        System.out.println("\nArtículo encontrado:");
        System.out.println(articulo);
    }

    /**
     * Modifica un artículo existente.
     *
     * Se permite modificar:
     * - nombre
     * - precio
     * - categoría
     * - dato específico según tipo
     */
    private void modificarArticulo() {
        if (repositorioArticulos.estaVacio()) {
            System.out.println("\nNo hay artículos cargados.");
            return;
        }

        listarArticulos();

        int codigo = leerEntero("\nIngrese el código del artículo a modificar: ");
        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("\nError: no existe un artículo con ese código.");
            return;
        }

        System.out.println("\nArtículo seleccionado:");
        System.out.println(articulo);

        // Variable temporal para validar duplicados antes de confirmar.
        String nuevoNombre = articulo.getNombre();
        Categoria nuevaCategoria = articulo.getCategoria();

        if (leerSiNo("\n¿Desea modificar el nombre? (S/N): ")) {
            nuevoNombre = pedirNombreArticulo();
        }

        if (leerSiNo("¿Desea modificar el precio? (S/N): ")) {
            articulo.setPrecio(pedirPrecioArticulo());
        }

        if (leerSiNo("¿Desea modificar la categoría? (S/N): ")) {
            nuevaCategoria = pedirCategoriaExistente();
        }

        // Validamos duplicado lógico, excluyendo el artículo actual.
        if (existeArticuloDuplicado(nuevoNombre, nuevaCategoria, articulo.getCodigo())) {
            System.out.println("\nError: ya existe otro artículo con ese nombre dentro de esa categoría.");
            return;
        }

        // Recién ahora aplicamos nombre y categoría, porque ya pasaron la validación.
        articulo.setNombre(nuevoNombre);
        articulo.setCategoria(nuevaCategoria);

        // Si el artículo es electrónico, permitimos modificar la garantía.
        if (articulo instanceof ArticuloElectronico electronico) {
            if (leerSiNo("¿Desea modificar la garantía? (S/N): ")) {
                electronico.setGarantiaMeses(pedirGarantia());
            }
        }

        // Si el artículo es alimenticio, permitimos modificar los días para vencimiento.
        if (articulo instanceof ArticuloAlimenticio alimenticio) {
            if (leerSiNo("¿Desea modificar los días para vencimiento? (S/N): ")) {
                alimenticio.setDiasParaVencimiento(pedirDiasParaVencimiento());
            }
        }

        System.out.println("\nArtículo modificado correctamente.");
        System.out.println(articulo);
    }

    /**
     * Elimina un artículo por código, previa confirmación.
     */
    private void eliminarArticulo() {
        if (repositorioArticulos.estaVacio()) {
            System.out.println("\nNo hay artículos cargados.");
            return;
        }

        listarArticulos();

        int codigo = leerEntero("\nIngrese el código del artículo a eliminar: ");
        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("\nError: no existe un artículo con ese código.");
            return;
        }

        System.out.println("\nArtículo seleccionado:");
        System.out.println(articulo);

        boolean confirmar = leerSiNo("¿Confirma la eliminación? (S/N): ");

        if (!confirmar) {
            System.out.println("\nOperación cancelada por el usuario.");
            return;
        }

        boolean eliminado = repositorioArticulos.eliminar(articulo);

        if (eliminado) {
            System.out.println("\nArtículo eliminado correctamente.");
        } else {
            System.out.println("\nError: no se pudo eliminar el artículo.");
        }
    }

    /**
     * Solicita y valida el nombre de un artículo.
     *
     * @return nombre válido.
     */
    private String pedirNombreArticulo() {
        while (true) {
            String nombre = leerTexto("Ingrese el nombre del artículo: ");

            if (!Validaciones.validarTextoNoVacio(nombre)) {
                System.out.println("Error: el nombre no puede estar vacío.");
                continue;
            }

            if (!Validaciones.validarLongitudMaxima(nombre, 50)) {
                System.out.println("Error: el nombre no puede superar los 50 caracteres.");
                continue;
            }

            return nombre.trim();
        }
    }

    /**
     * Solicita y valida el precio del artículo.
     *
     * @return precio válido.
     */
    private double pedirPrecioArticulo() {
        while (true) {
            double precio = leerDouble("Ingrese el precio del artículo: ");

            if (!Validaciones.validarNoNegativo(precio)) {
                System.out.println("Error: el precio no puede ser negativo.");
                continue;
            }

            return precio;
        }
    }

    /**
     * Solicita y valida la garantía del artículo electrónico.
     *
     * @return garantía válida en meses.
     */
    private int pedirGarantia() {
        while (true) {
            int garantia = leerEntero("Ingrese la garantía en meses: ");

            if (!Validaciones.validarNoNegativo(garantia)) {
                System.out.println("Error: la garantía no puede ser negativa.");
                continue;
            }

            return garantia;
        }
    }

    /**
     * Solicita y valida los días para vencimiento del artículo alimenticio.
     *
     * @return días válidos.
     */
    private int pedirDiasParaVencimiento() {
        while (true) {
            int dias = leerEntero("Ingrese los días para vencimiento: ");

            if (!Validaciones.validarNoNegativo(dias)) {
                System.out.println("Error: los días para vencimiento no pueden ser negativos.");
                continue;
            }

            return dias;
        }
    }

    /**
     * Permite elegir una categoría existente.
     *
     * Este método lista todas las categorías y solicita el código.
     *
     * @return categoría válida seleccionada.
     */
    private Categoria pedirCategoriaExistente() {
        while (true) {
            System.out.println("\nCategorías disponibles:");
            for (Categoria categoria : repositorioCategorias.listar()) {
                System.out.println(categoria);
            }

            int codigoCategoria = leerEntero("Ingrese el código de la categoría: ");
            Categoria categoria = repositorioCategorias.buscarPorCodigo(codigoCategoria);

            if (categoria == null) {
                System.out.println("Error: la categoría ingresada no existe.");
                continue;
            }

            return categoria;
        }
    }

    /**
     * Verifica si ya existe otro artículo con el mismo nombre dentro de la misma categoría.
     *
     * ¿Por qué hacemos esta validación?
     * Porque evita duplicados lógicos en el sistema.
     *
     * @param nombre nombre a validar.
     * @param categoria categoría a validar.
     * @param codigoExcluir código que no debe compararse (útil al modificar).
     * @return true si existe un duplicado, false si no existe.
     */
    private boolean existeArticuloDuplicado(String nombre, Categoria categoria, int codigoExcluir) {
        for (Articulo articulo : repositorioArticulos.listar()) {
            boolean mismoCodigo = articulo.getCodigo() == codigoExcluir;
            boolean mismoNombre = articulo.getNombre().equalsIgnoreCase(nombre.trim());
            boolean mismaCategoria = articulo.getCategoria().getCodigo() == categoria.getCodigo();

            if (!mismoCodigo && mismoNombre && mismaCategoria) {
                return true;
            }
        }
        return false;
    }
}
