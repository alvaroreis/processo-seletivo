package br.ufpa.labes.eas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Unidade.
 */
@Entity
@Table(name = "unidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Unidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "abreviacao")
    private String abreviacao;

    @OneToOne    @JoinColumn(unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "unidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Curso> cursos = new HashSet<>();
    @OneToMany(mappedBy = "unidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DemandaCurso> demandaCursos = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("unidades")
    private Cidade cidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Unidade nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Unidade telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public Unidade abreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
        return this;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Unidade usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public Unidade cursos(Set<Curso> cursos) {
        this.cursos = cursos;
        return this;
    }

    public Unidade addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.setUnidade(this);
        return this;
    }

    public Unidade removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.setUnidade(null);
        return this;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    public Set<DemandaCurso> getDemandaCursos() {
        return demandaCursos;
    }

    public Unidade demandaCursos(Set<DemandaCurso> demandaCursos) {
        this.demandaCursos = demandaCursos;
        return this;
    }

    public Unidade addDemandaCurso(DemandaCurso demandaCurso) {
        this.demandaCursos.add(demandaCurso);
        demandaCurso.setUnidade(this);
        return this;
    }

    public Unidade removeDemandaCurso(DemandaCurso demandaCurso) {
        this.demandaCursos.remove(demandaCurso);
        demandaCurso.setUnidade(null);
        return this;
    }

    public void setDemandaCursos(Set<DemandaCurso> demandaCursos) {
        this.demandaCursos = demandaCursos;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Unidade cidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
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
        Unidade unidade = (Unidade) o;
        if (unidade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Unidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", abreviacao='" + getAbreviacao() + "'" +
            "}";
    }
}
