/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rcarrillo
 */
@Embeddable
public class ColaAtencionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "Barberia_id")
    private int barberiaid;

    public ColaAtencionPK() {
    }

    public ColaAtencionPK(int id, Date fecha, int barberiaid) {
        this.id = id;
        this.fecha = fecha;
        this.barberiaid = barberiaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getBarberiaid() {
        return barberiaid;
    }

    public void setBarberiaid(int barberiaid) {
        this.barberiaid = barberiaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (int) barberiaid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColaAtencionPK)) {
            return false;
        }
        ColaAtencionPK other = (ColaAtencionPK) object;
        if (this.id != other.id) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if (this.barberiaid != other.barberiaid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.ColaAtencionPK[ id=" + id + ", fecha=" + fecha + ", barberiaid=" + barberiaid + " ]";
    }
    
}
