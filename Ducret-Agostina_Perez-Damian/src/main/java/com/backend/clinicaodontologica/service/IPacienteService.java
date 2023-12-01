package com.backend.clinicaodontologica.service;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.paciente.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IPacienteService {
    PacienteSalidaDto guardarPaciente(PacienteEntradaDto paciente);
    PacienteSalidaDto actualizarPaciente(PacienteModificacionEntradaDto paciente) throws ResourceNotFoundException;
    PacienteSalidaDto buscarPacientePorId(Long id);
    List<PacienteSalidaDto> listarPacientes();

    void eliminarPaciente(Long id) throws ResourceNotFoundException;


}
