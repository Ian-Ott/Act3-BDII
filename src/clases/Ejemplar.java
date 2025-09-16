package src.clases;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "EJEMPLAR")
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    private Boolean disponible;

    @OneToMany(mappedBy = "ejemplar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos;

    // Getters y Setters


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }


    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Libro getLibro() {
        return libro;
    }

    public List<Prestamo> getPrestamos() { return prestamos; }
    public void setPrestamos(List<Prestamo> prestamos) { this.prestamos = prestamos; }

    @Override
    public String toString() {
        String Ejemplar = "Id Ejemplar: "+ this.id+", Libro: "+ this.getLibro()+ ", Disponible: " + this.getDisponible()  + " \n";
        Ejemplar = Ejemplar + "ID Prestamos:\n";
        for (int i = 0; i < prestamos.size(); i++) {
            Ejemplar = Ejemplar + this.getPrestamos().get(i) +"\n";
        }
        return Ejemplar;
    }
}

