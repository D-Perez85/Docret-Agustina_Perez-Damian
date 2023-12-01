package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.domicilio.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.domicilio.DomicilioModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.odontologo.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.paciente.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @BeforeEach
    void doBefore(){
        pacienteService.guardarPaciente(new PacienteEntradaDto("Alicia", "Gomez", 1574649, LocalDate.of(2023, 12, 15),
                new DomicilioEntradaDto("calle", 1234, "Localidad", "Provincia")));
        odontologoService.guardarOdontologo(new OdontologoEntradaDto("35435","Natalia","Rios"));
    }

    @Test
    @Order(1)
    void deberiaRegistrarUnTurnoYRetornarElId() throws BadRequestException {
        TurnoEntradaDto turnoEntradaDto = new  TurnoEntradaDto(LocalDateTime.of(2023, 12, 24, 20, 12, 12),
                new OdontologoModificacionEntradaDto(1L, "35435","Natalia","Rios"),
                new PacienteModificacionEntradaDto(1L,"Alicia", "Gomez", 1574649, LocalDate.of(2023, 12, 15), new DomicilioModificacionEntradaDto(1L,"calle", 1234, "Localidad", "Provincia")));
        TurnoSalidaDto turnoSalidaDto = turnoService.guardarTurno(turnoEntradaDto);
        assertNotNull(turnoSalidaDto.getId());
    }

    @Test
    @Order(2)
    void alIntentarEliminarUnTurnoConId1YaEliminado_deberiaLanzarUnaResourceNotFoundException(){
        try{
            turnoService.eliminarTurno(1L);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        assertThrows(ResourceNotFoundException.class, () -> turnoService.eliminarTurno(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaVaciaDeTurnos(){
        List<TurnoSalidaDto> turnosDto = turnoService.listarTurnos();
        assertTrue(turnosDto.isEmpty());
    }
}