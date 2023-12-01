package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.paciente.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.turno.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Paciente;
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
        OdontologoSalidaDto odontologoABuscar = odontologoService.buscarOdontologoPorId(turno.getOdontologoModificacionEntradaDto().getId());
        LOGGER.info("Odontologo Encontrado: " + JsonPrinter.toString(odontologoABuscar));
        PacienteSalidaDto pacienteABuscar = pacienteService.buscarPacientePorId(turno.getPacienteModificacionEntradaDto().getId());
        if(pacienteABuscar != null && odontologoABuscar != null) {
            LOGGER.info("Paciente Encontrado: " + JsonPrinter.toString(pacienteABuscar));
            Turno turnoEntidad = modelMapper.map(turno, Turno.class);
            Turno turnoAPersistir = turnoRepository.save(turnoEntidad);
            turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
            LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
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
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) throws ResourceNotFoundException{

        LOGGER.warn("Turno recibido JSON: {}", JsonPrinter.toString(turno));
                Turno turnoAActualizar = modelMapper.map(turno, Turno.class);

        LOGGER.warn("Turno de JSON a BD: {}", JsonPrinter.toString(turnoAActualizar));


        Turno turnoRecibido = turnoRepository.findById(turno.getId()).orElse(null);
        LOGGER.warn("Turno BD: {}", JsonPrinter.toString(turnoRecibido));


        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoRecibido != null) {
            turnoRecibido = turnoAActualizar;

            turnoRepository.save(turnoRecibido);

            turnoSalidaDto = modelMapper.map(turnoRecibido, TurnoSalidaDto.class);

            LOGGER.warn("Turno actualizado : {}", JsonPrinter.toString(turnoSalidaDto));
        } else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
            throw new ResourceNotFoundException("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
        }
        return turnoSalidaDto;
    }


    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {

        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if(turnoBuscado != null){
            turnoEncontrado =  modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
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
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getOdontologoModificacionEntradaDto, Turno::setOndontologo))
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getPacienteModificacionEntradaDto, Turno::setPaciente));

        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(modelMapper -> modelMapper.map(TurnoModificacionEntradaDto::getOdontologoModificacionEntradaDto, Turno::setOndontologo))
                .addMappings(modelMapper -> modelMapper.map(TurnoModificacionEntradaDto::getPacienteModificacionEntradaDto, Turno::setPaciente));

        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto))
                .addMappings(modelMapper -> modelMapper.map(Turno::getOndontologo, TurnoSalidaDto::setOdontologoSalidaDto));


    }


    }







