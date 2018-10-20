package br.ufpa.labes.eas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DemandaCurso.
 */
@Entity
@Table(name = "demanda_curso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DemandaCurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "resolucao")
    private String resolucao;

    @OneToMany(mappedBy = "demandaCurso")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PeriodoOferta> periodoOfertas = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("demandaCursos")
    private Unidade unidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public DemandaCurso data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getResolucao() {
        return resolucao;
    }

    public DemandaCurso resolucao(String resolucao) {
        this.resolucao = resolucao;
        return this;
    }

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }

    public Set<PeriodoOferta> getPeriodoOfertas() {
        return periodoOfertas;
    }

    public DemandaCurso periodoOfertas(Set<PeriodoOferta> periodoOfertas) {
        this.periodoOfertas = periodoOfertas;
        return this;
    }

    public DemandaCurso addPeriodoOferta(PeriodoOferta periodoOferta) {
        this.periodoOfertas.add(periodoOferta);
        periodoOferta.setDemandaCurso(this);
        return this;
    }

    public DemandaCurso removePeriodoOferta(PeriodoOferta periodoOferta) {
        this.periodoOfertas.remove(periodoOferta);
        periodoOferta.setDemandaCurso(null);
        return this;
    }

    public void setPeriodoOfertas(Set<PeriodoOferta> periodoOfertas) {
        this.periodoOfertas = periodoOfertas;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public DemandaCurso unidade(Unidade unidade) {
        this.unidade = unidade;
        return this;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DemandaCurso demandaCurso = (DemandaCurso) o;
        if (demandaCurso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), demandaCurso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DemandaCurso{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", resolucao='" + getResolucao() + "'" +
            "}";
    }
}
