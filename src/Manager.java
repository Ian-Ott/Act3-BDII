package src;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import src.clases.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Manager {
    private  EntityManager em;
    Scanner entrada = new Scanner(System.in);
    EntityManagerFactory emf;

    public Manager( EntityManager em, EntityManagerFactory emf) {
        this.emf = emf;
        this.em = em;
    }

    private void insertSocio( String nombre, String mail) {
        //EntityTransaction emt = em.getTransaction();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Socio s = new Socio();

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
        em.close();
    }
    void altaSocio(){

        String nombre;
        String mail;
        Scanner entrada = new Scanner(System.in);


        System.out.println("Ingrese nombre del socio:");
        nombre = entrada.nextLine();

        System.out.println("Ingrese mail del socio:");
        mail = entrada.nextLine();
        insertSocio( nombre,mail);
        System.out.println("Socio cargado con exito!");
    }

    /**
     * Actualiza un libro existente en la base de datos.
     *
     *
     */
    public void actualizarSocio() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Socio socio = new Socio();
        System.out.println("Ingrese ID:");
        socio.setId( Long.valueOf( entrada.nextLine()));
        System.out.println("Ingrese Nombre:");
        socio.setNombre(String.valueOf( entrada.nextLine()));
        System.out.println("Ingrese Mail:");
        socio.setMail(String.valueOf( entrada.nextLine()));

        try {
            em.merge(socio);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al actualizar el socio: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    /**
     * Elimina un libro de la base de datos por su ID.
     *
     *
     */
    public void eliminarSocio() {
        EntityManager em = emf.createEntityManager();
        Long id;
        System.out.println("Ingrese ID:");
        id = Long.valueOf( entrada.nextLine());
        em.getTransaction().begin();
        try {
            Socio socio = em.find(Socio.class, id);
            if (socio != null) {
                em.remove(socio);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar el socio: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    List<Socio> listarSocios(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Socio> socios = em.createQuery("SELECT s FROM Socio s", Socio.class).getResultList();
        em.close();
        return socios;
    }

    void buscarSocio(){
        EntityManager em = emf.createEntityManager();
        int id;
        em.getTransaction().begin();

        System.out.println("Ingrese id del socio a buscar:");
        id = Integer.valueOf( entrada.nextLine() );
        Socio socio = em.find(Socio.class, id);
        em.close();
        //System.out.println(manager.buscarSocio(id));
    }
    public void guardarLibro() {
        EntityManager em = emf.createEntityManager();
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
            //em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al guardar el libro: " + e.getMessage());
        }

        int cantidadE = 0;

        System.out.println("Ingrese la cantidad de ejemplares disponibles:");
        cantidadE = Integer.parseInt( entrada.nextLine());

        /*try {
        for (int i = 0; i < cantidadE; i++) {
            Ejemplar ejemplar = new Ejemplar();
            ejemplar.setLibro(libro);
            ejemplar.setDisponible(true);
            em.persist(ejemplar);
        }
        em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            em.getTransaction().rollback();
            System.err.println("Error al guardar el ejemplar: " + e.getMessage());
        }*/

        em.close();
    }


    /**
     * Busca y recupera un libro por su ID.
     *
     *
     * @return El objeto Libro si se encuentra, o null si no existe.
     */
    public Libro obtenerLibroPorId() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
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
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
        em.close();
        return libros;
    }


    /**
     * Actualiza un libro existente en la base de datos.
     *
     *
     */
    public void actualizarLibro() {
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
        Ejemplar ejemplar = new Ejemplar();
        Libro libro = new Libro();
        int cantidadE = 0;
        em.getTransaction().begin();
        System.out.println("Ingrese Id de Libro:");
        libro.setId(Long.parseLong( entrada.nextLine()));
        ejemplar.setLibro(libro);
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
     * Busca y recupera los ejemplares de un libro por su ID.
     *
     *
     * @return El objeto Ejemplar si se encuentra, o null si no existe.
     */
    public Ejemplar obtenerEjemplarPorId() {
        EntityManager em = emf.createEntityManager();
        Long id;
        em.getTransaction().begin();
        System.out.println("Ingrese ID de Ejemplar:");
        id = Long.parseLong( entrada.nextLine());
        Ejemplar ejemplar = em.find(Ejemplar.class,id);
        em.close();
        return ejemplar;
    }


    /**
     * Recupera todos los ejemplares de la base de datos.
     *
     * @return Una lista de todos los objetos Ejemplar.
     */
    public List<Ejemplar> obtenerTodosLosEjemplaresLibro() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Ejemplar> ejemplares = em.createQuery("SELECT e FROM Ejemplar e", Ejemplar.class).getResultList();
        em.close();
        return ejemplares;
    }


    /**
     * Actualiza un ejemplar existente en la base de datos.
     *
     *
     */
    public void actualizarEjemplar() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Ejemplar ejemplar= new Ejemplar();
        System.out.println("Ingrese ID de Ejemplar:");
        ejemplar.setId(Long.parseLong(entrada.nextLine()));
        System.out.println("Ingrese Estado del ejemplar (true | false):");
        ejemplar.setDisponible(Boolean.valueOf(entrada.nextLine()));

        try {
            em.merge(ejemplar);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al actualizar el ejemplar: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    /**
     * Elimina un ejemplar de la base de datos por su ID.
     *
     *
     */
    public void eliminarEjemplar() {
        EntityManager em = emf.createEntityManager();
        Long id;
        System.out.println("Ingrese ID de Ejemplar:");
        id = Long.parseLong(entrada.nextLine());
        em.getTransaction().begin();
        try {
            Ejemplar ejemplar = em.find(Ejemplar.class, id);
            if (ejemplar != null) {
                em.remove(ejemplar);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar el ejemplar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void altaPrestamo() {
        EntityManager em = emf.createEntityManager();
        Prestamo prestamo = new Prestamo();
        PrestamoID ID = new PrestamoID();
        em.getTransaction().begin();
        ID.setFechaPrestamo(LocalDate.now());
        System.out.println("Ingrese Id de Socio:");
        ID.setSocio(Long.parseLong( entrada.nextLine()));
        System.out.println("Ingrese el ID de ejemplar:");
        ID.setEjemplar(Long.parseLong(entrada.nextLine()));
            try {
                em.persist(prestamo);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error al guardar el ejemplar: " + e.getMessage());
            }
        em.close();
    }


    /**
     * Busca y recupera los prestamos.
     *
     *
     * @return El objeto Prestamo si se encuentra, o null si no existe.
     */
    public Prestamo obtenerPrestamo() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        PrestamoID ID = new PrestamoID();
        System.out.println("Ingrese ID de socio");
        ID.setSocio(Long.parseLong( entrada.nextLine()));
        System.out.println("Ingrese ID de Ejemplar:");
        ID.setEjemplar(Long.parseLong(entrada.nextLine()));

        System.out.println("Ingrese Fecha de prestamo (AAAA-MM-DD): ");
        ID.setFechaPrestamo(LocalDate.parse(entrada.nextLine()));
        Prestamo prestamo = em.find(Prestamo.class, ID);
        em.close();
        return prestamo;
    }


    /**
     * Recupera todos los prestamos de la base de datos.
     *
     * @return Una lista de todos los objetos Prestamo.
     */
    public List<Prestamo> obtenerTodosLosPrestamos() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p", Prestamo.class).getResultList();
        em.close();
        return prestamos;
    }


    /**
     * Actualiza un prestamo existente en la base de datos.
     *
     *
     */
    public void actualizarPrestamo() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        PrestamoID ID = new PrestamoID();
        Prestamo prestamo = new Prestamo();
        System.out.println("Ingrese ID de socio");
        ID.setSocio(Long.parseLong( entrada.nextLine()));
        System.out.println("Ingrese ID de Ejemplar:");
        ID.setEjemplar(Long.parseLong(entrada.nextLine()));

        System.out.println("Ingrese Fecha de prestamo (AAAA-MM-DD): ");
        ID.setFechaPrestamo(LocalDate.parse(entrada.nextLine()));
        System.out.println("Ingrese Fecha de devolucion (AAAA-MM-DD): ");
        prestamo.setFechaDevolucion(LocalDate.parse(entrada.nextLine()));
        prestamo.setId(ID);
        try {
            em.merge(prestamo);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al actualizar el prestamo: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    /**
     * Elimina un ejemplar de la base de datos por su ID.
     *
     *
     */
    public void eliminarPrestamo() {
        EntityManager em = emf.createEntityManager();
        PrestamoID ID = new PrestamoID();
        System.out.println("Ingrese ID de socio");
        ID.setSocio(Long.parseLong( entrada.nextLine()));
        System.out.println("Ingrese ID de Ejemplar:");
        ID.setEjemplar(Long.parseLong(entrada.nextLine()));

        System.out.println("Ingrese Fecha de prestamo (AAAA-MM-DD): ");
        ID.setFechaPrestamo(LocalDate.parse(entrada.nextLine()));

        em.getTransaction().begin();
        try {
            Prestamo prestamo = em.find(Prestamo.class, ID);
            if (prestamo != null) {
                em.remove(prestamo);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar el prestamo: " + e.getMessage());
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
