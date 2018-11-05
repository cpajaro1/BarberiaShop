/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rcarrillo
 */
@Entity
@Table(name = "Turno")
@NamedQueries({
    @NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t")})
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TurnoPK turnoPK;
    @Column(name = "Numero_turno")
    private Integer numeroturno;
    @JoinColumn(name = "Barbero_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Barbero barbero;
    @JoinColumn(name = "Cliente_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turno")
    private List<Calificacion> calificacionList;

    public Turno() {
    }

    public Turno(TurnoPK turnoPK) {
        this.turnoPK = turnoPK;
    }

    public Turno(int id, int barberoid, int clienteid) {
        this.turnoPK = new TurnoPK(id, barberoid, clienteid);
    }

    public TurnoPK getTurnoPK() {
        return turnoPK;
    }

    public void setTurnoPK(TurnoPK turnoPK) {
        this.turnoPK = turnoPK;
    }

    public Integer getNumeroturno() {
        return numeroturno;
    }

    public void setNumeroturno(Integer numeroturno) {
        this.numeroturno = numeroturno;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public void setBarbero(Barbero barbero) {
        this.barbero = barbero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Calificacion> getCalificacionList() {
        return calificacionList;
    }

    public void setCalificacionList(List<Calificacion> calificacionList) {
        this.calificacionList = calificacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (turnoPK != null ? turnoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.turnoPK == null && other.turnoPK != null) || (this.turnoPK != null && !this.turnoPK.equals(other.turnoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Turno[ turnoPK=" + turnoPK + " ]";
    }
    
}
