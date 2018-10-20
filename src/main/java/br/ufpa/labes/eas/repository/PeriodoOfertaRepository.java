package br.ufpa.labes.eas.repository;

import br.ufpa.labes.eas.domain.PeriodoOferta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PeriodoOferta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoOfertaRepository extends JpaRepository<PeriodoOferta, Long> {

}
