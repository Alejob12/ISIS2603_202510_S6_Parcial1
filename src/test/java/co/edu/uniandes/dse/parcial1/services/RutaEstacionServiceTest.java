package co.edu.uniandes.dse.parcial1.services;


import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(RutaEstacionService.class)
public class RutaEstacionServiceTest {

    @Autowired
    private RutaEstacionService rutaEstacionService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private RutaEntity ruta;
    private EstacionEntity estacion;


    @BeforeEach
    void setUp() {
        estacion = factory.manufacturePojo(EstacionEntity.class);
        entityManager.persist(estacion);

        ruta = factory.manufacturePojo(RutaEntity.class);
        entityManager.persist(ruta);
    }

    @Test
    void testRemoveEstacionRuta() {
        estacion.getRutas().add(ruta);
        ruta.getEstaciones().add(estacion);
        entityManager.persist(estacion);
        entityManager.persist(ruta);

        assertDoesNotThrow(() -> rutaEstacionService.removeEstacionRuta(estacion.getId(), ruta.getId()));
        assertFalse(estacion.getRutas().contains(ruta));
        assertThrows(EntityNotFoundException.class, () -> rutaEstacionService.removeEstacionRuta(999L, ruta.getId()));
        assertThrows(EntityNotFoundException.class, () -> rutaEstacionService.removeEstacionRuta(estacion.getId(), 999L));
    }

    @Test
    void testAddEstacionRuta() {
        assertDoesNotThrow(() -> rutaEstacionService.addEstacionRuta(estacion.getId(), ruta.getId()));
        assertTrue(estacion.getRutas().contains(ruta));
        assertThrows(EntityNotFoundException.class, () -> rutaEstacionService.addEstacionRuta(999L, ruta.getId()));
        assertThrows(EntityNotFoundException.class, () -> rutaEstacionService.addEstacionRuta(estacion.getId(), 999L));
    }
}










