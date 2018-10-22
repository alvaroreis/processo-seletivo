package br.ufpa.labes.eas.repository;

import br.ufpa.labes.eas.domain.Modalidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Modalidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {

}
