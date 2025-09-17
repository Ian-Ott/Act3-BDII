package src;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import src.clases.Ejemplar;
import src.clases.Libro;
import src.clases.Prestamo;
import src.clases.Socio;

import java.util.*;


public class Main {
    static int op;
    static Scanner entrada = new Scanner(System.in);
    static Manager manager;
    static EntityManager em;
    static EntityManagerFactory emf;

    public static void main(String[] args) {
        // Cargar la configuración de persistencia
        System.out.println("Provider: " + Persistence.class.getResource("Persistence.class"));
        System.out.println(Main.class.getClassLoader().getResource("resources/META-INF/persistence.xml"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Biblioteca");



        // Crear un gestor de entidades
        manager = new Manager(em,emf);

        menuPrincipal();

    }

    private static void menuPrincipal() {
        try {
            // Iniciar una transacción
            boolean bcontinuar = true;
            do {
                menu();
                op = entrada.next().charAt(0);

                switch (op) {
                    case '1':
                        opcionSocio();
                        break;
                    case '2':
                        opcionLibro();
                        break;
                    case '3':
                        opcionEjemplar();
                        break;
                    case '4':
                        opcionPrestamo();
                        break;
                    case '0':
                        bcontinuar = false;
                        break;
                    default:
                        System.out.println("Seleccione una opcion valida\n");
                        break;
                }

            } while (bcontinuar);
            System.exit(0);


        } finally {
            // Cerrar el gestor de entidades
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }

    public static void menuSocio(){
        System.out.println("Ingrese una opcion:\n");
        System.out.println("1 - Dar de alta un Socio");
        System.out.println("2 - Borrar Socio");
        System.out.println("3 - Modificar Socio");
        System.out.println("4 - Buscar Socio");
        System.out.println("5 - Listar Socios");
        System.out.println("0 - Salir");
    }
    public static void opcionSocio(){
        boolean bcontinuar = true;
        do{
        menuSocio();
        op = entrada.next().charAt(0);

        switch (op) {
            case '1':
                manager.altaSocio();
                break;
            case '2':
                manager.eliminarSocio();
                break;
            case '3':
                manager.actualizarSocio();
                break;
            case '4':
                manager.buscarSocio();
                break;
            case '5':
                List<Socio> lista = manager.listarSocios();
                System.out.println("Lista de Socios: \n");
                if (!lista.isEmpty()) {
                    for (Socio socio : lista) {
                        System.out.println(socio);
                    }
                }else{
                    System.out.println("No hay socios cargados.");
                }
                break;
            case '0':
                bcontinuar = false;
                break;
            default:
                System.out.println("Seleccione una opcion valida\n");
                break;
        }
        } while (bcontinuar);
        //menuPrincipal();
    }
    public static void opcionLibro(){
        boolean bcontinuar = true;
        do{
            menuLibro();
            op = entrada.next().charAt(0);

            switch (op) {
                case '1':
                    manager.guardarLibro();
                    break;
                case '2':
                    manager.eliminarLibro();
                    break;
                case '3':
                    manager.actualizarLibro();
                    break;
                case '4':
                    System.out.println(manager.obtenerLibroPorId());
                    break;
                case '5':
                    List<Libro> lista = manager.obtenerTodosLosLibros();
                    System.out.println("Lista de Libros: \n");
                    if (!lista.isEmpty()) {
                        for (Libro libro : lista) {
                            System.out.println(libro);
                        }
                    }else{
                        System.out.println("No hay libros cargados.");
                    }
                    break;
                case '0':
                    bcontinuar = false;
                    break;
                default:
                    System.out.println("Seleccione una opcion valida\n");
                    break;
            }
        } while (bcontinuar);
        //menuPrincipal();
    }

    public static void opcionPrestamo(){
        boolean bcontinuar = true;
        do{
            menuPrestamo();
            op = entrada.next().charAt(0);

            switch (op) {
                case '1':
                    manager.altaPrestamo();
                    break;
                case '2':
                    manager.eliminarPrestamo();
                    break;
                case '3':
                    manager.actualizarPrestamo();
                    break;
                case '4':
                    System.out.println(manager.obtenerPrestamo());
                    break;
                case '5':
                    List<Prestamo> lista = manager.obtenerTodosLosPrestamos();
                    System.out.println("Lista de Prestamos: \n");
                    if (!lista.isEmpty()) {
                        for (Prestamo prestamo : lista) {
                            System.out.println(prestamo);
                        }
                    }else{
                        System.out.println("No hay prestamos cargados.");
                    }
                    break;
                case '0':
                    bcontinuar = false;
                    break;
                default:
                    System.out.println("Seleccione una opcion valida\n");
                    break;
            }
        } while (bcontinuar);
        //menuPrincipal();
    }

    private static void menuPrestamo() {
        System.out.println("Ingrese una opcion:\n");
        System.out.println("1 - Dar de alta Prestamo");
        System.out.println("2 - Borrar Prestamo");
        System.out.println("3 - Modificar Prestamo");
        System.out.println("4 - Buscar Prestamo");
        System.out.println("5 - Listar Prestamo");
        System.out.println("0 - Salir");
    }

    public static void opcionEjemplar(){
        boolean bcontinuar = true;
        do{
            menuEjemplar();
            op = entrada.next().charAt(0);

            switch (op) {
                case '1':
                    manager.altaEjemplar();
                    break;
                case '2':
                    manager.eliminarEjemplar();
                    break;
                case '3':
                    manager.obtenerEjemplarPorId();
                    break;
                case '4':
                    List<Ejemplar> lista = manager.obtenerTodosLosEjemplaresLibro();
                    System.out.println("Lista de Ejemplares: \n");
                    if (!lista.isEmpty()) {
                        for (Ejemplar ejemplar : lista) {
                            System.out.println(ejemplar);
                        }
                    }else{
                        System.out.println("No hay ejemplares cargados.");
                    }
                    break;
                case '0':
                    bcontinuar = false;
                    break;
                default:
                    System.out.println("Seleccione una opcion valida\n");
                    break;
            }
        } while (bcontinuar);
        //menuPrincipal();
    }

    private static void menuEjemplar() {
        System.out.println("Ingrese una opcion:\n");
        System.out.println("1 - Dar de alta Ejemplar");
        System.out.println("2 - Borrar Ejemplar");
        System.out.println("3 - Buscar Ejemplar");
        System.out.println("4 - Listar Ejemplar");
        System.out.println("0 - Salir");
    }

    public static void menuLibro(){
        System.out.println("Ingrese una opcion:\n");
        System.out.println("1 - Dar de alta Libro");
        System.out.println("2 - Borrar Libro");
        System.out.println("3 - Modificar Libros");
        System.out.println("4 - Buscar Libro");
        System.out.println("5 - Listar Libros");
        System.out.println("0 - Salir");
    }
    public static void menu() {
        System.out.println("BIENVENIDOS A LA ACTIVIDAD 3 - BENITEZ,LOPEZ,OTT\n");
        System.out.println("Ingrese una opcion:\n");
        System.out.println("1 - Socio");
        System.out.println("2 - Libro");
        System.out.println("3 - Ejemplar");
        System.out.println("4 - Prestamo");
        System.out.println("0 - Salir");
    }


}

