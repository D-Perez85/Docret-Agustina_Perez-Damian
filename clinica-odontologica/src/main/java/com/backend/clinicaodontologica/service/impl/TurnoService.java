package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.turno.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final OdontologoService odontologoService;
    private final PacienteService pacienteService;
    private ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, OdontologoService odontologoService, PacienteService pacienteService, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto guardarTurno(TurnoEntradaDto turno) throws BadRequestException {
        TurnoSalidaDto turnoSalidaDto = null;
        LOGGER.info("Buscando Odonto: ");
        OdontologoSalidaDto odontologoABuscar = odontologoService.buscarOdontologoPorId(turno.getOdontologo());
        LOGGER.info("Odontologo Encontrado: " + JsonPrinter.toString(odontologoABuscar));
        PacienteSalidaDto pacienteABuscar = pacienteService.buscarPacientePorId(turno.getPaciente());
        LOGGER.info("Paciente Encontrado: " + JsonPrinter.toString(pacienteABuscar));
        if(pacienteABuscar != null && odontologoABuscar != null) {
            OdontologoSalidaDto odontologoEntidad = modelMapper.map(odontologoABuscar, OdontologoSalidaDto.class);
            PacienteSalidaDto pacienteEntidad = modelMapper.map(pacienteABuscar, PacienteSalidaDto.class);
            Turno turnoEntidad = modelMapper.map(turno, Turno.class);
            Turno turnoAPersistir = turnoRepository.save(turnoEntidad);
            turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
            turnoSalidaDto.setPaciente(pacienteABuscar.getNombre());
            turnoSalidaDto.setOdontologo(odontologoABuscar.getNombre());
            LOGGER.info("Turno Salida Dto: " + JsonPrinter.toString(turnoSalidaDto));
        }else if(pacienteABuscar == null && odontologoABuscar != null){
            throw new BadRequestException("El paciente no existe en nuestra DB");
            }else if(odontologoABuscar == null && pacienteABuscar != null){
                throw new BadRequestException("El odontologo no existe en nuestra DB");
                }else{
                    throw new BadRequestException("No se han encontrado los ID de paciente y Odontologo");
            }
            return turnoSalidaDto;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) {
        OdontologoSalidaDto odontologoABuscar = odontologoService.buscarOdontologoPorId(turno.getOdontologo());
        PacienteSalidaDto pacienteABuscar = pacienteService.buscarPacientePorId(turno.getPaciente());
        Turno turnoAActualizar = modelMapper.map(turno, Turno.class);
        Turno turnoRecibido = turnoRepository.findById(turno.getId()).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;
        if (turnoRecibido != null) {
            turnoRecibido = turnoAActualizar;
            turnoRepository.save(turnoRecibido);
            turnoSalidaDto = modelMapper.map(turnoRecibido, TurnoSalidaDto.class);
            turnoSalidaDto.setPaciente(pacienteABuscar.getNombre());
            turnoSalidaDto.setOdontologo(odontologoABuscar.getNombre());
            LOGGER.warn("Turno actualizado : {}", JsonPrinter.toString(turnoSalidaDto));
        } else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
        }
        return turnoSalidaDto;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        OdontologoSalidaDto odontologoABuscar = odontologoService.buscarOdontologoPorId(id);
        PacienteSalidaDto pacienteABuscar = pacienteService.buscarPacientePorId(id);
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;
        if(turnoBuscado != null){
            turnoEncontrado =  modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            turnoEncontrado.setPaciente(pacienteABuscar.getNombre());
            turnoEncontrado.setOdontologo(odontologoABuscar.getNombre());
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");
        return turnoEncontrado;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnoSalidaDto = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        if (LOGGER.isInfoEnabled())
            LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnoSalidaDto));
        return turnoSalidaDto;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if (turnoRepository.findById(id).orElse(null) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el turno con ID " + id);
        }
    }


    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getOdontologo, Turno::setOndontologo))
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getPaciente, Turno::setPaciente));
        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(modelMapper -> modelMapper.map(TurnoModificacionEntradaDto::getOdontologo, Turno::setOndontologo))
                .addMappings(modelMapper -> modelMapper.map(TurnoModificacionEntradaDto::getPaciente, Turno::setPaciente));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Turno::getPaciente, TurnoSalidaDto::setPaciente))
                .addMappings(modelMapper -> modelMapper.map(Turno::getOndontologo, TurnoSalidaDto::setOdontologo));
    }
}
