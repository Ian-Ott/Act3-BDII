package src.clases;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PRESTAMO")
public class Prestamo {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PrestamoID Id;  // Usamos ID propio para simplificar


//    @Column(name = "FECHAPRESTAMO", nullable = false)
//    private LocalDate fechaPrestamo;

    @Column(name = "FECHADEVOLUCION")
    private LocalDate fechaDevolucion;

    @MapsId("idSocio")
    @ManyToOne
    @JoinColumn(name = "IDSOCIO", nullable = false)
    private Socio socio;
    @MapsId("idEjemplar")
    @ManyToOne
    @JoinColumn(name = "IDEJEMPLAR", nullable = false)
    private Ejemplar ejemplar;

    // Getters y Setters


    public void setId(PrestamoID id) {
        this.Id = id;
    }

    public PrestamoID getId() {
        return Id;
    }

//    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
//    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public Socio getSocio() { return socio; }
    public void setSocio(Socio socio) { this.socio = socio; }

    public Ejemplar getEjemplar() { return ejemplar; }
    public void setEjemplar(Ejemplar ejemplar) { this.ejemplar = ejemplar; }

    @Override
    public String toString() {
        return "Fecha del Prestamo "+ this.getId().getFechaPrestamo() + ", Fecha de devolucion: " + this.getFechaDevolucion()
                + ", Id Socio: " + this.getId().getSocio() + ", Id Ejemplar: " + this.getId().getEjemplar() +  " \n";
    }
}
