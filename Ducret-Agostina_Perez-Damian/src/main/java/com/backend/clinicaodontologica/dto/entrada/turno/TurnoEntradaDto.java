package com.backend.clinicaodontologica.dto.entrada.turno;
import com.backend.clinicaodontologica.dto.modificacion.odontologo.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.paciente.PacienteModificacionEntradaDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class TurnoEntradaDto {
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha del turno")
    //@JsonProperty("fechaYHora") CONSULTAR
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaYHora;


    @NotNull(message = "El campo odontologo no puede ser nulo")
    private OdontologoModificacionEntradaDto odontologoModificacionEntradaDto;
    @NotNull(message = "El campo paciente no puede ser nulo")
    private PacienteModificacionEntradaDto pacienteModificacionEntradaDto;

    public TurnoEntradaDto() {
    }

    public TurnoEntradaDto(LocalDateTime fechaYHora, OdontologoModificacionEntradaDto odontologoModificacionEntradaDto, PacienteModificacionEntradaDto pacienteModificacionEntradaDto) {
        this.fechaYHora = fechaYHora;
        this.odontologoModificacionEntradaDto = odontologoModificacionEntradaDto;
        this.pacienteModificacionEntradaDto = pacienteModificacionEntradaDto;
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
