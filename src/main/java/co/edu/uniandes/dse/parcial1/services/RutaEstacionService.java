package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Slf4j
@Service
public class RutaEstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Transactional
    public void removeEstacionRuta(Long estacionId, Long rutaId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminar la estación con id {} de la ruta con id {}", estacionId, rutaId);

        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        if (estacionEntity.isEmpty())
            throw new EntityNotFoundException("La estación no existe");

        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty())
            throw new EntityNotFoundException("La ruta no existe");

        boolean tieneOtraRutaNocturna = estacionEntity.get().getRutas().stream()
                .anyMatch(r -> !r.getId().equals(rutaId) && "nocturna".equalsIgnoreCase(r.getTipo()));

        if (!tieneOtraRutaNocturna && "nocturna".equalsIgnoreCase(rutaEntity.get().getTipo())) {
            throw new IllegalStateException("No se puede eliminar la última ruta nocturna de una estación");
        }

        rutaEntity.get().getEstaciones().remove(estacionEntity.get());
        estacionEntity.get().getRutas().remove(rutaEntity.get());

        log.info("Finaliza proceso de eliminar la estación con id {} de la ruta con id {}", estacionId, rutaId);
    }

    @Transactional
    public void addEstacionRuta(Long estacionId, Long rutaId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregar la estación con id {} a la ruta con id {}", estacionId, rutaId);
        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        if (estacionEntity.isEmpty())
            throw new EntityNotFoundException("La estación no existe");

        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty())
            throw new EntityNotFoundException("La ruta no existe");

        long rutasCirculares = estacionEntity.get().getRutas().stream()
                .filter(r -> "circular".equalsIgnoreCase(r.getTipo()))
                .count();

        if (estacionEntity.get().getCapacidad() < 100 && rutasCirculares >= 2 && "circular".equalsIgnoreCase(rutaEntity.get().getTipo())) {
            throw new IllegalStateException("Una estacion con capacidad menor a 100 pasajeros no puede tener más de 2 rutas circulares");
        }

        rutaEntity.get().getEstaciones().add(estacionEntity.get());
        estacionEntity.get().getRutas().add(rutaEntity.get());

        log.info("Finaliza proceso de agregar la estación con id {} a la ruta con id {}", estacionId, rutaId);
    }



}

















