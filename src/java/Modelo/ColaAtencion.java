/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
@Table(name = "ColaAtencion")
@NamedQueries({
    @NamedQuery(name = "ColaAtencion.findAll", query = "SELECT c FROM ColaAtencion c")})
public class ColaAtencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ColaAtencionPK colaAtencionPK;
    @JoinColumn(name = "Barberia_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Barberia barberia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "colaAtencion")
    private List<Calificacion> calificacionList;

    public ColaAtencion() {
    }

    public ColaAtencion(ColaAtencionPK colaAtencionPK) {
        this.colaAtencionPK = colaAtencionPK;
    }

    public ColaAtencion(int id, Date fecha, int barberiaid) {
        this.colaAtencionPK = new ColaAtencionPK(id, fecha, barberiaid);
    }

    public ColaAtencionPK getColaAtencionPK() {
        return colaAtencionPK;
    }

    public void setColaAtencionPK(ColaAtencionPK colaAtencionPK) {
        this.colaAtencionPK = colaAtencionPK;
    }

    public Barberia getBarberia() {
        return barberia;
    }

    public void setBarberia(Barberia barberia) {
        this.barberia = barberia;
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
        hash += (colaAtencionPK != null ? colaAtencionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColaAtencion)) {
            return false;
        }
        ColaAtencion other = (ColaAtencion) object;
        if ((this.colaAtencionPK == null && other.colaAtencionPK != null) || (this.colaAtencionPK != null && !this.colaAtencionPK.equals(other.colaAtencionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.ColaAtencion[ colaAtencionPK=" + colaAtencionPK + " ]";
    }
    
}
