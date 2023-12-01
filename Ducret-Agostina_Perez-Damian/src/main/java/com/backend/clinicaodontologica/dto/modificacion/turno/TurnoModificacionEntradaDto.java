package com.backend.clinicaodontologica.dto.modificacion.turno;
import com.backend.clinicaodontologica.dto.modificacion.odontologo.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.paciente.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TurnoModificacionEntradaDto {

    @NotNull(message = "Debe especificarse el ID del turno que se desea modificar")
    private Long id;

    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha del turno")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaYHora;


    @NotNull(message = "El campo odontologo no puede ser nulo")
    private OdontologoModificacionEntradaDto odontologoModificacionEntradaDto;
    @NotNull(message = "El campo paciente no puede ser nulo")
    private PacienteModificacionEntradaDto pacienteModificacionEntradaDto;

    public TurnoModificacionEntradaDto() {
    }

    public TurnoModificacionEntradaDto(Long id, LocalDateTime fechaYHora, OdontologoModificacionEntradaDto odontologoModificacionEntradaDto, PacienteModificacionEntradaDto pacienteModificacionEntradaDto) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.odontologoModificacionEntradaDto = odontologoModificacionEntradaDto;
        this.pacienteModificacionEntradaDto = pacienteModificacionEntradaDto;
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

    public OdontologoModificacionEntradaDto getOdontologoModificacionEntradaDto() {
        return odontologoModificacionEntradaDto;
    }

    public void setOdontologoModificacionEntradaDto(OdontologoModificacionEntradaDto odontologoModificacionEntradaDto) {
        this.odontologoModificacionEntradaDto = odontologoModificacionEntradaDto;
    }

    public PacienteModificacionEntradaDto getPacienteModificacionEntradaDto() {
        return pacienteModificacionEntradaDto;
    }

    public void setPacienteModificacionEntradaDto(PacienteModificacionEntradaDto pacienteModificacionEntradaDto) {
        this.pacienteModificacionEntradaDto = pacienteModificacionEntradaDto;
    }
}
