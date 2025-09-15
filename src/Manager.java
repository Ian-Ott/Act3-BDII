package src;

import jakarta.persistence.EntityManager;
import src.clases.Ejemplar;
import src.clases.Libro;
import src.clases.Socio;

import java.util.List;
import java.util.Scanner;

public class Manager {
    private  EntityManager em;
    Scanner entrada = new Scanner(System.in);

    public Manager( EntityManager em) {

        this.em = em;
    }

    private void insertSocio(long id, String nombre, String mail) {
        //EntityTransaction emt = em.getTransaction();

        Socio s = new Socio();
        s.setId(id);
        s.setNombre(nombre);
        s.setMail(mail);
        String smsg = "persist()";
        try {
            em.getTransaction().begin();
            em.persist(s);
            System.out.println("Main:em.persist(c) hecho");
            smsg = "commit()";
            em.getTransaction().commit();
            System.out.println("Main:emt.commit() hecho");
        } catch (IllegalArgumentException iae) {
            System.out.println("Main:Error en " + smsg + " persistiendo Socio, posiblemente sea null");
        }
//        } catch (EntityExistsException eee) {
//            System.out.println("Main:Error en " + smsg + " persistiendo Socio, esta entidad ya existe");
//        } catch (TransactionRequiredException tre) {
//            System.out.println("Main:Error en " + smsg + " persistiendo Socio, se requiere de una transaccion");
//        }
            catch (Exception e) {
            System.out.println("Main:Error en " + smsg + " persistiendo Socio, error: " + e.getMessage());
        }

    }
    void altaSocio(){
        int id;
        String nombre;
        String mail;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese Id del nuevo socio:");
        id = Integer.valueOf( entrada.nextLine() );

        System.out.println("Ingrese nombre del socio:");
        nombre = entrada.nextLine();

        System.out.println("Ingrese mail del socio:");
        mail = entrada.nextLine();
        insertSocio(id, nombre,mail);
        System.out.println("Socio cargado con exito!");
    }

    List<Socio> listarSocios(){
        List<Socio> socios = em.createQuery("SELECT s FROM SOCIO s", Socio.class).getResultList();
        em.close();
        return socios;
    }

    void buscarSocio(){
        int id;


        System.out.println("Ingrese id del socio a buscar:");
        id = Integer.valueOf( entrada.nextLine() );

        //System.out.println(manager.buscarSocio(id));
    }
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
        Ejemplar ejemplar = new Ejemplar();
        int cantidadE = 0;
        ejemplar.setLibro(libro.getId());
        System.out.println("Ingrese la cantidad de ejemplares disponibles:");
        cantidadE = Integer.parseInt( entrada.nextLine());
        ejemplar.setDisponible(true);
        for (int i = 0; i < cantidadE; i++) {
            try {
                em.persist(ejemplar);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error al guardar el ejemplar: " + e.getMessage());
            }
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

    public void altaEjemplar() {
        Ejemplar ejemplar = new Ejemplar();
        int cantidadE = 0;
        em.getTransaction().begin();
        System.out.println("Ingrese Id de Libro:");
        ejemplar.setLibro(Long.parseLong( entrada.nextLine()));
        System.out.println("Ingrese la cantidad de ejemplares disponibles:");
        cantidadE = Integer.parseInt( entrada.nextLine());
        ejemplar.setDisponible(true);
        for (int i = 0; i < cantidadE; i++) {
            try {
                em.persist(ejemplar);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error al guardar el ejemplar: " + e.getMessage());
            }
        }
        em.close();
    }


    /**
     * Busca y recupera un libro por su ID.
     *
     *
     * @return El objeto Libro si se encuentra, o null si no existe.
     */
    public Libro obtenerEjemplarPorId() {
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
    public List<Libro> obtenerTodosLosEjemplaresDeLibro() {

        List<Libro> libros = em.createQuery("SELECT l FROM LIBRO l", Libro.class).getResultList();
        em.close();
        return libros;
    }


    /**
     * Actualiza un libro existente en la base de datos.
     *
     *
     */
    public void actualizarEjemplar() {
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
    public void eliminarEjemplar() {
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

    private  void limpiar(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Presione una tecla para continuar...");
        entrada.nextLine();
    }

    public void salir() {
    }
}
