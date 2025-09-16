package src.clases;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;


@Embeddable
public class PrestamoID implements Serializable {
    private LocalDate fechaPrestamo;
    @Column(name = "id_usuario", nullable = false)
    private Long idSocio;

    @Column(name = "id_ejemplar", nullable = false)
    private Long idEjemplar;


    public PrestamoID(){

    }
    PrestamoID(LocalDate id, Long socio, Long ejemplar){
        this.fechaPrestamo = id;
        this.idSocio = socio;
        this.idEjemplar = ejemplar;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setSocio(Long socio) {
        this.idSocio = socio;
    }

    public Long getSocio() {
        return idSocio;
    }

    public void setEjemplar(Long ejemplar) {
        this.idEjemplar = ejemplar;
    }

    public Long getEjemplar() {
        return idEjemplar;
    }
}
