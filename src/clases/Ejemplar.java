package src.clases;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "EJEMPLAR")
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Long libro;

    @OneToMany(mappedBy = "ejemplar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public long getLibro() { return libro; }
    public void setLibro(long libro) { this.libro = libro; }

    public List<Prestamo> getPrestamos() { return prestamos; }
    public void setPrestamos(List<Prestamo> prestamos) { this.prestamos = prestamos; }

    @Override
    public String toString() {
        return "Id: "+ this.getId()+", Codigo: "+ this.getCodigo()+ ", Disponible: " + this.getDisponible() + " \n";
    }
}

