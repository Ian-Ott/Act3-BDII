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
        String input;
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID:");
                id = Long.valueOf(entrada.nextLine());
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID no valido!");
            }
        }while(error);

        socio = em.find(Socio.class,id);
        if (socio == null){
            System.out.println("El socio no existe!");
            em.getTransaction().rollback();
        }else {

            System.out.println("Ingrese Nombre: ('.' para no actualizar)");
            input = String.valueOf(entrada.nextLine());
            if (!input.contains(".")) {
                socio.setNombre(input);
            }
            System.out.println("Ingrese Mail: ('.' para no actualizar)");
            input = String.valueOf(entrada.nextLine());
            if (!input.contains(".")) {
                socio.setMail(input);
            }

            try {
                em.getTransaction().commit();
                System.out.println("\nSocio modificado!");
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error al actualizar el socio: " + e.getMessage());
            }

        }
        em.close();
    }


    /**
     * Elimina un libro de la base de datos por su ID.
     *
     *
     */
    public void eliminarSocio() {
        EntityManager em = emf.createEntityManager();
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID:");
                id = Long.valueOf(entrada.nextLine());
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID no valido!");
            }
        }while(error);
        em.getTransaction().begin();
        try {
            Socio socio = em.find(Socio.class, id);
            if (socio != null) {
                em.remove(socio);
                em.getTransaction().commit();
            }
            System.out.println("Socio eliminado con exito!");
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

        em.getTransaction().begin();
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID:");
                id = Long.valueOf(entrada.nextLine());
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID no valido!");
            }
        }while(error);
        Socio socio = em.find(Socio.class, id);
        System.out.println("Socio:\n");
        System.out.println(socio);
        em.close();
        //System.out.println(manager.buscarSocio(id));
    }
    public void guardarLibro() {
        EntityManager em = emf.createEntityManager();
        Libro libro = new Libro();
        em.getTransaction().begin();
        System.out.println("Ingrese Titulo:");
        libro.setTitulo(String.valueOf(entrada.nextLine()));
        System.out.println("Ingrese Autor:");
        libro.setAutor(String.valueOf(entrada.nextLine()));
        int anio = 0;
        boolean error;
        do {
            try {
                System.out.println("Ingrese Anio:");
                anio = Integer.valueOf(entrada.nextLine());
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID no valido!");
            }
        } while (error);
        libro.setAnio(anio);
        try {
            em.persist(libro);
            System.out.println("\nLibro guardado con exito!");
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("\nError al guardar el libro: " + e.getMessage());
        }

        /*int cantidadE = 0;

        System.out.println("Ingrese la cantidad de ejemplares disponibles:");
        cantidadE = Integer.parseInt( entrada.nextLine());

        try {
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
        boolean error = false;
        String input;
        Long id = 0L;
        do{
            try {
                System.out.println("Ingrese ID:");
                id = Long.valueOf(entrada.nextLine());
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID no valido!");
            }
        }while(error);
        libro = em.find(Libro.class,id);
        if (libro == null){
            em.getTransaction().rollback();
            System.out.println("\nEl libro no existe!");
        }else {
            System.out.println("Ingrese Titulo:( '.' para no actualizar)");
            input = String.valueOf(entrada.nextLine());
            if (!input.contains(".")) {
                libro.setTitulo(input);
            }
            System.out.println("Ingrese Autor: ( '.' para no actualizar)");
            input = String.valueOf(entrada.nextLine());
            if (!input.contains(".")) {
                libro.setAutor(input);
            }

            do{
                System.out.println("Ingrese Anio: ( '.' para no actualizar)");
                input = String.valueOf(entrada.nextLine());
                if (!input.contains(".")) {
                    try {
                        libro.setAnio(Integer.valueOf(entrada.nextLine()));
                        error = false;
                    } catch (NumberFormatException e) {
                        System.out.println("EL anio debe ser un entero!");
                        error = true;
                    }
                }
            }while(error);
            try {
                em.getTransaction().commit();
                System.out.println("Libro actualizado!!");
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error al actualizar el libro: " + e.getMessage());
            }
        }
            em.close();
    }


    /**
     * Elimina un libro de la base de datos por su ID.
     *
     *
     */
    public void eliminarLibro() {
        EntityManager em = emf.createEntityManager();
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID:");
                id = Long.valueOf(entrada.nextLine());
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID no valido!");
            }
        }while(error);
        em.getTransaction().begin();
        try {
            Libro libro = em.find(Libro.class, id);
            if (libro != null) {
                em.remove(libro);
                em.getTransaction().commit();
                System.out.println("\nLibro eliminado!");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("\nError al eliminar el libro: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void altaEjemplar() {
        EntityManager em = emf.createEntityManager();
        Libro libro = new Libro();
        em.getTransaction().begin();
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID de libro:");
                id = Long.valueOf(entrada.nextLine());
                libro.setId(id);
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de libro no valido!");
            }
        }while(error);
        int cantidadE = 0;
        do{
            try {
                System.out.println("Ingrese la cantidad de ejemplares disponibles:");
                cantidadE = Integer.parseInt( entrada.nextLine());
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("Ingreso no valido!");
            }
        }while(error);

        try {
            for (int i = 0; i < cantidadE; i++) {
                Ejemplar ejemplar = new Ejemplar();
                ejemplar.setLibro(libro);
                ejemplar.setDisponible(true);
                em.persist(ejemplar);
            }
            em.getTransaction().commit();
            System.out.println("Ejemplar/es cargados.");
        } catch (Exception e) {
            System.out.println(e);
            em.getTransaction().rollback();
            System.err.println("Error al guardar el ejemplar: " + e.getMessage());
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
        em.getTransaction().begin();
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID de ejemplar:");
                id = Long.valueOf(entrada.nextLine());
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de ejemplar no valido!");
            }
        }while(error);
        Ejemplar ejemplar = em.find(Ejemplar.class,id);
        System.out.println(ejemplar);
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
    /*public void actualizarEjemplar() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Ejemplar ejemplar= new Ejemplar();
        Long id;
        System.out.println("Ingrese ID de Ejemplar:");
        id = Long.parseLong(entrada.nextLine());
        ejemplar = em.find(Ejemplar.class, id);
        if (ejemplar == null){
            em.getTransaction().rollback();
            System.out.println("\nEl Ejemplar no existe!");
        }else {

            System.out.println("Ingrese Estado del ejemplar (true | false):");
            ejemplar.setDisponible(Boolean.valueOf(entrada.nextLine()));

            try {
                em.getTransaction().commit();
                System.out.println("\nEjemplar modificado!");
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error al actualizar el ejemplar: " + e.getMessage());
            }
        }
        em.close();
    }*/


    /**
     * Elimina un ejemplar de la base de datos por su ID.
     *
     *
     */
    public void eliminarEjemplar() {
        EntityManager em = emf.createEntityManager();
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID de ejemplar:");
                id = Long.valueOf(entrada.nextLine());
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de ejemplar no valido!");
            }
        }while(error);
        em.getTransaction().begin();
        try {
            Ejemplar ejemplar = em.find(Ejemplar.class, id);
            if (ejemplar != null) {
                em.remove(ejemplar);
                em.getTransaction().commit();
                System.out.println("\nEjemplar eliminado!!");
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
        Socio socio = new Socio();
        Ejemplar ejemplar = new Ejemplar();
        em.getTransaction().begin();
        ID.setFechaPrestamo(LocalDate.now());
        //prestamo.setFechaPrestamo(LocalDate.now());
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID de socio:");
                id = Long.valueOf(entrada.nextLine());
                socio = em.find(Socio.class,id);
                if (socio == null){
                    em.getTransaction().rollback();
                    System.out.println("El socio no existe!");
                    error = true;
                }else {
                    prestamo.setSocio(socio);
                    //ID.setSocio(id);
                    error = false;
                }
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de socio no valido!");
            }
        }while(error);
        id = 0L;
        do{
            try {
                System.out.println("Ingrese el ID de ejemplar:");
                id = Long.valueOf(entrada.nextLine());
                ejemplar = em.find(Ejemplar.class,id);
                if (ejemplar == null){
                    em.getTransaction().rollback();
                    System.out.println("NO se encontro el ejemplar!");
                    error = true;
                }else {
                    prestamo.setEjemplar(ejemplar);
                    //ID.setEjemplar(id);
                    error = false;
                }
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de ejemplar no valido!");
            }
        }while(error);
            prestamo.setId(ID);
            try {
                em.persist(prestamo);
                em.getTransaction().commit();
                System.out.println("\nPrestamo cargado!!");
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
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID de socio:");
                id = Long.valueOf(entrada.nextLine());
                ID.setSocio(id);
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de socio no valido!");
            }
        }while(error);
        id = 0L;
        do{
            try {
                System.out.println("Ingrese el ID de ejemplar:");
                id = Long.valueOf(entrada.nextLine());
                ID.setEjemplar(id);
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de ejemplar no valido!");
            }
        }while(error);
        LocalDate fecha;
        do{
            try {
                System.out.println("Ingrese Fecha de prestamo (AAAA-MM-DD): ");
                fecha = LocalDate.parse(entrada.nextLine());
                ID.setFechaPrestamo(fecha);
                error = false;
            } catch (Exception e) {
                error = true;
                System.out.println("Fecha no valida!");
            }
        }while(error);
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
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID de socio:");
                id = Long.valueOf(entrada.nextLine());
                ID.setSocio(id);
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de socio no valido!");
            }
        }while(error);
        id = 0L;
        do{
            try {
                System.out.println("Ingrese el ID de ejemplar:");
                id = Long.valueOf(entrada.nextLine());
                ID.setEjemplar(id);
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de ejemplar no valido!");
            }
        }while(error);
        LocalDate fecha;
        do{
            try {
                System.out.println("Ingrese Fecha de prestamo (AAAA-MM-DD): ");
                fecha = LocalDate.parse(entrada.nextLine());
                ID.setFechaPrestamo(fecha);
                error = false;
            } catch (Exception e) {
                error = true;
                System.out.println("Fecha no valida!");
            }
        }while(error);
        prestamo = em.find(Prestamo.class,ID);
        if(prestamo == null){
            em.getTransaction().rollback();
            System.out.println("\nEl prestamo no existe!");
        }else {
            do{
                try {
                    System.out.println("Ingrese Fecha de devolucion (AAAA-MM-DD): ");
                    fecha = LocalDate.parse(entrada.nextLine());
                    prestamo.setFechaDevolucion(fecha);
                    error = false;
                } catch (Exception e) {
                    error = true;
                    System.out.println("Fecha no valida!");
                }
            }while(error);

            prestamo.setId(ID);
            try {
                em.getTransaction().commit();
                System.out.println("\nPrestamo modificado!");
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("\nError al actualizar el prestamo: " + e.getMessage());
            }
        }
        em.close();
    }


    /**
     * Elimina un ejemplar de la base de datos por su ID.
     *
     *
     */
    public void eliminarPrestamo() {
        EntityManager em = emf.createEntityManager();
        PrestamoID ID = new PrestamoID();
        Long id = 0L;
        boolean error;
        do{
            try {
                System.out.println("Ingrese ID de socio:");
                id = Long.valueOf(entrada.nextLine());
                ID.setSocio(id);
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de socio no valido!");
            }
        }while(error);
        id = 0L;
        do{
            try {
                System.out.println("Ingrese el ID de ejemplar:");
                id = Long.valueOf(entrada.nextLine());
                ID.setEjemplar(id);
                error = false;
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("ID de ejemplar no valido!");
            }
        }while(error);
        LocalDate fecha;
        do{
            try {
                System.out.println("Ingrese Fecha de prestamo (AAAA-MM-DD): ");
                fecha = LocalDate.parse(entrada.nextLine());
                ID.setFechaPrestamo(fecha);
                error = false;
            } catch (Exception e) {
                error = true;
                System.out.println("Fecha no valida!");
            }
        }while(error);

        em.getTransaction().begin();
        try {
            Prestamo prestamo = em.find(Prestamo.class, ID);
            if (prestamo != null) {
                em.remove(prestamo);
                em.getTransaction().commit();
                System.out.println("\nPrestamo eliminado!");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar el prestamo: " + e.getMessage());
        } finally {
            em.close();
        }
    }



}
