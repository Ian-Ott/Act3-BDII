package src;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import src.clases.Socio;

import java.util.Scanner;

public class Manager {
    private  EntityManagerFactory emf = null;
    private  EntityManager em = null;

    public Manager(EntityManagerFactory emf, EntityManager em) {
        this.emf = emf;
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
    }

    void listarSocios(){

        //System.out.println(manager.listarSocio());
    }

    void buscarSocio(){
        int id;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese id del socio a buscar:");
        id = Integer.valueOf( entrada.nextLine() );

        //System.out.println(manager.buscarSocio(id));
    }
    private  void limpiar(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Presione una tecla para continuar...");
        entrada.nextLine();
    }

    public void salir() {
    }
}
