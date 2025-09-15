package src.clases;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "SOCIO")
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String mail;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public List<Prestamo> getPrestamos() { return prestamos; }
    public void setPrestamos(List<Prestamo> prestamos) { this.prestamos = prestamos; }

    @Override
    public String toString() {
        return "Id: "+ this.getId()+", Nombre: "+ this.getNombre()+ ", Mail: " + this.getMail() + " \n";
    }
}

