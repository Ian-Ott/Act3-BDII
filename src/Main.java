package src;

import src.clases.Libro;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import src.clases.LibroManager;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        // Cargar la configuración de persistencia
        System.out.println("Provider: " + Persistence.class.getResource("Persistence.class"));
        System.out.println(Main.class.getClassLoader().getResource("resources/META-INF/persistence.xml"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Biblioteca");

        int op;
        boolean bcontinuar = true;
        Scanner entrada = new Scanner(System.in);
        // Crear un gestor de entidades
        EntityManager em = emf.createEntityManager();
        Manager manager = new Manager(emf,em);
        LibroManager LM = new LibroManager(emf,em);
        try {
            // Iniciar una transacción
            em.getTransaction().begin();
            do {
                menu();
                op = entrada.next().charAt(0);

                switch (op) {
                    case '1':
                        manager.altaSocio();
                        break;
                    case '2':
                        manager.buscarSocio();
                        break;
                    case '3':
                        manager.listarSocios();
                        break;
                    case '4':
                        LM.guardarLibro();
                        break;
                    case '5':
                        manager.listarSocios();
                        break;
                    case '0':
                        manager.salir();
                        bcontinuar = false;
                        break;
                    default:
                        System.out.println("Seleccione una opcion valida");
                        break;
                }

            } while (bcontinuar);
            System.exit(0);




            // Crear una nueva entidad
            Libro libro = new Libro();
            libro.setTitulo("El Señor de los Anillos");
            libro.setAutor("J.R.R. Tolkien");

            // Persistir la entidad en la base de datos
            em.persist(libro);

            // Confirmar la transacción
            em.getTransaction().commit();

            System.out.println("Libro guardado con éxito.");

            // Puedes realizar más operaciones aquí, como buscar, actualizar, etc.

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
    public static void menu() {
        System.out.println("BIENVENIDOS A LA ACTIVIDAD 3 - BENITEZ,LOPEZ,OTT\n");
        System.out.println("Ingrese una opcion:\n");
        System.out.println("1 - Dar de alta un Socio");
        System.out.println("2 - Buscar Socio");
        System.out.println("3 - Listar Socios");
        System.out.println("4 - Dar de alta Libro");
        System.out.println("5 - Borrar Libro");
        System.out.println("6 - Modificar Libros");
        System.out.println("7 - Buscar Libro");
        System.out.println("8 - Listar Libros");
        System.out.println("0 - Salir");
    }


}

