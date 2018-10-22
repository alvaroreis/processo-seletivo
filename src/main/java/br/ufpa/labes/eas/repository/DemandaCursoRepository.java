package br.ufpa.labes.eas.repository;

import br.ufpa.labes.eas.domain.DemandaCurso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DemandaCurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandaCursoRepository extends JpaRepository<DemandaCurso, Long> {

}
