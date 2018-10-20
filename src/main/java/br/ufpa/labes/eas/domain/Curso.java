package br.ufpa.labes.eas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Curso.
 */
@Entity
@Table(name = "curso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_sigaa")
    private Integer codSigaa;

    @Column(name = "nome")
    private String nome;

    @Column(name = "curso_novo")
    private Integer cursoNovo;

    @Column(name = "justificativa_novo")
    private String justificativaNovo;

    @Column(name = "vagas_autorizadas_mec")
    private Integer vagasAutorizadasMec;

    @Column(name = "resolucao")
    private String resolucao;

    @ManyToOne
    @JsonIgnoreProperties("cursos")
    private Modalidade modalidade;

    @ManyToOne
    @JsonIgnoreProperties("cursos")
    private Turno turno;

    @ManyToOne
    @JsonIgnoreProperties("cursos")
    private Unidade unidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodSigaa() {
        return codSigaa;
    }

    public Curso codSigaa(Integer codSigaa) {
        this.codSigaa = codSigaa;
        return this;
    }

    public void setCodSigaa(Integer codSigaa) {
        this.codSigaa = codSigaa;
    }

    public String getNome() {
        return nome;
    }

    public Curso nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCursoNovo() {
        return cursoNovo;
    }

    public Curso cursoNovo(Integer cursoNovo) {
        this.cursoNovo = cursoNovo;
        return this;
    }

    public void setCursoNovo(Integer cursoNovo) {
        this.cursoNovo = cursoNovo;
    }

    public String getJustificativaNovo() {
        return justificativaNovo;
    }

    public Curso justificativaNovo(String justificativaNovo) {
        this.justificativaNovo = justificativaNovo;
        return this;
    }

    public void setJustificativaNovo(String justificativaNovo) {
        this.justificativaNovo = justificativaNovo;
    }

    public Integer getVagasAutorizadasMec() {
        return vagasAutorizadasMec;
    }

    public Curso vagasAutorizadasMec(Integer vagasAutorizadasMec) {
        this.vagasAutorizadasMec = vagasAutorizadasMec;
        return this;
    }

    public void setVagasAutorizadasMec(Integer vagasAutorizadasMec) {
        this.vagasAutorizadasMec = vagasAutorizadasMec;
    }

    public String getResolucao() {
        return resolucao;
    }

    public Curso resolucao(String resolucao) {
        this.resolucao = resolucao;
        return this;
    }

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public Curso modalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
        return this;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Turno getTurno() {
        return turno;
    }

    public Curso turno(Turno turno) {
        this.turno = turno;
        return this;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public Curso unidade(Unidade unidade) {
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
        Curso curso = (Curso) o;
        if (curso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", codSigaa=" + getCodSigaa() +
            ", nome='" + getNome() + "'" +
            ", cursoNovo=" + getCursoNovo() +
            ", justificativaNovo='" + getJustificativaNovo() + "'" +
            ", vagasAutorizadasMec=" + getVagasAutorizadasMec() +
            ", resolucao='" + getResolucao() + "'" +
            "}";
    }
}
