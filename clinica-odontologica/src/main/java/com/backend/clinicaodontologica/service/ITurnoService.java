package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.turno.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import java.util.List;

public interface ITurnoService {
    TurnoSalidaDto guardarTurno(TurnoEntradaDto turno) throws BadRequestException;
    TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);
    TurnoSalidaDto buscarTurnoPorId(Long id);
    List<TurnoSalidaDto> listarTurnos();
    void eliminarTurno(Long id) throws ResourceNotFoundException;
}
