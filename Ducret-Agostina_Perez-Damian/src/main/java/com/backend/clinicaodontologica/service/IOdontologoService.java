package com.backend.clinicaodontologica.service;
import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.odontologo.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IOdontologoService {
    OdontologoSalidaDto guardarOdontologo(OdontologoEntradaDto odontologo);
    OdontologoSalidaDto actualizarOdontologo(OdontologoModificacionEntradaDto odontologo) throws ResourceNotFoundException;
    OdontologoSalidaDto buscarOdontologoPorId(Long id);

    List<OdontologoSalidaDto> listarOdontologos();

    void eliminarOdontologo(Long id) throws ResourceNotFoundException;

}
