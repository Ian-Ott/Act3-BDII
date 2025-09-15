package src.clases;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Scanner;


/**
 * Clase Manager para gestionar las operaciones CRUD de la entidad Libro
 * utilizando la especificación Jakarta Persistence API (JPA) con Hibernate.
 */
public class LibroManager {

    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;
    Scanner entrada = new Scanner(System.in);
    public  LibroManager (EntityManagerFactory emf, EntityManager em){
        this.emf = emf;
        this.em = em;
    }



    /**
     * Guarda un nuevo libro en la base de datos.
     *
     *
     */
    public void guardarLibro() {
        Libro libro = new Libro();
        em.getTransaction().begin();
        System.out.println("Ingrese Titulo:");
        libro.setTitulo(String.valueOf( entrada.nextLine()));
        System.out.println("Ingrese Autor:");
        libro.setAutor(String.valueOf( entrada.nextLine()));
        System.out.println("Ingrese Anio:");
        libro.setAnio(Integer.valueOf( entrada.nextLine()));
        try {
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al guardar el libro: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    /**
     * Busca y recupera un libro por su ID.
     *
     *
     * @return El objeto Libro si se encuentra, o null si no existe.
     */
    public Libro obtenerLibroPorId() {
        Long id;
        System.out.println("Ingrese ID:");
        id = Long.valueOf( entrada.nextLine());
        Libro libro = em.find(Libro.class, id);
        em.close();
        return libro;
    }


    /**
     * Recupera todos los libros de la base de datos.
     *
     * @return Una lista de todos los objetos Libro.
     */
    public List<Libro> obtenerTodosLosLibros() {

        List<Libro> libros = em.createQuery("SELECT l FROM LIBRO l", Libro.class).getResultList();
        em.close();
        return libros;
    }


    /**
     * Actualiza un libro existente en la base de datos.
     *
     *
     */
    public void actualizarLibro() {
        em.getTransaction().begin();
        Libro libro = new Libro();
        System.out.println("Ingrese ID:");
        libro.setId( Long.valueOf( entrada.nextLine()));
        System.out.println("Ingrese Titulo:");
        libro.setTitulo(String.valueOf( entrada.nextLine()));
        System.out.println("Ingrese Autor:");
        libro.setAutor(String.valueOf( entrada.nextLine()));
        System.out.println("Ingrese Anio:");
        libro.setAnio(Integer.valueOf( entrada.nextLine()));
        try {
            em.merge(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al actualizar el libro: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    /**
     * Elimina un libro de la base de datos por su ID.
     *
     *
     */
    public void eliminarLibro() {
        Long id;
        System.out.println("Ingrese ID:");
        id = Long.valueOf( entrada.nextLine());
        em.getTransaction().begin();
        try {
            Libro libro = em.find(Libro.class, id);
            if (libro != null) {
                em.remove(libro);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar el libro: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}


