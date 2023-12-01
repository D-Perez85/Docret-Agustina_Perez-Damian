package com.backend.clinicaodontologica.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TURNOS")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaYHora;
    @OneToOne
    @JoinColumn(name = "odontologo_id")
    private Odontologo ondontologo;
    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    public Turno() {
    }

    public Turno(LocalDateTime fechaYHora, Odontologo ondontologo, Paciente paciente) {
        this.fechaYHora = fechaYHora;
        this.ondontologo = ondontologo;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Odontologo getOndontologo() {
        return ondontologo;
    }

    public void setOndontologo(Odontologo ondontologo) {
        this.ondontologo = ondontologo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
