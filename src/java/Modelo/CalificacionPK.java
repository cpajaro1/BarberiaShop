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
public class CalificacionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Turno_id")
    private int turnoid;
    @Basic(optional = false)
    @Column(name = "Turno_Barbero_id")
    private int turnoBarberoid;
    @Basic(optional = false)
    @Column(name = "Turno_Cliente_id")
    private int turnoClienteid;
    @Basic(optional = false)
    @Column(name = "ColaAtencion_id")
    private int colaAtencionid;
    @Basic(optional = false)
    @Column(name = "ColaAtencion_Barberia_id")
    private int colaAtencionBarberiaid;
    @Basic(optional = false)
    @Column(name = "ColaAtencion_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date colaAtencionfecha;

    public CalificacionPK() {
    }

    public CalificacionPK(int turnoid, int turnoBarberoid, int turnoClienteid, int colaAtencionid, int colaAtencionBarberiaid, Date colaAtencionfecha) {
        this.turnoid = turnoid;
        this.turnoBarberoid = turnoBarberoid;
        this.turnoClienteid = turnoClienteid;
        this.colaAtencionid = colaAtencionid;
        this.colaAtencionBarberiaid = colaAtencionBarberiaid;
        this.colaAtencionfecha = colaAtencionfecha;
    }

    public int getTurnoid() {
        return turnoid;
    }

    public void setTurnoid(int turnoid) {
        this.turnoid = turnoid;
    }

    public int getTurnoBarberoid() {
        return turnoBarberoid;
    }

    public void setTurnoBarberoid(int turnoBarberoid) {
        this.turnoBarberoid = turnoBarberoid;
    }

    public int getTurnoClienteid() {
        return turnoClienteid;
    }

    public void setTurnoClienteid(int turnoClienteid) {
        this.turnoClienteid = turnoClienteid;
    }

    public int getColaAtencionid() {
        return colaAtencionid;
    }

    public void setColaAtencionid(int colaAtencionid) {
        this.colaAtencionid = colaAtencionid;
    }

    public int getColaAtencionBarberiaid() {
        return colaAtencionBarberiaid;
    }

    public void setColaAtencionBarberiaid(int colaAtencionBarberiaid) {
        this.colaAtencionBarberiaid = colaAtencionBarberiaid;
    }

    public Date getColaAtencionfecha() {
        return colaAtencionfecha;
    }

    public void setColaAtencionfecha(Date colaAtencionfecha) {
        this.colaAtencionfecha = colaAtencionfecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) turnoid;
        hash += (int) turnoBarberoid;
        hash += (int) turnoClienteid;
        hash += (int) colaAtencionid;
        hash += (int) colaAtencionBarberiaid;
        hash += (colaAtencionfecha != null ? colaAtencionfecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalificacionPK)) {
            return false;
        }
        CalificacionPK other = (CalificacionPK) object;
        if (this.turnoid != other.turnoid) {
            return false;
        }
        if (this.turnoBarberoid != other.turnoBarberoid) {
            return false;
        }
        if (this.turnoClienteid != other.turnoClienteid) {
            return false;
        }
        if (this.colaAtencionid != other.colaAtencionid) {
            return false;
        }
        if (this.colaAtencionBarberiaid != other.colaAtencionBarberiaid) {
            return false;
        }
        if ((this.colaAtencionfecha == null && other.colaAtencionfecha != null) || (this.colaAtencionfecha != null && !this.colaAtencionfecha.equals(other.colaAtencionfecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.CalificacionPK[ turnoid=" + turnoid + ", turnoBarberoid=" + turnoBarberoid + ", turnoClienteid=" + turnoClienteid + ", colaAtencionid=" + colaAtencionid + ", colaAtencionBarberiaid=" + colaAtencionBarberiaid + ", colaAtencionfecha=" + colaAtencionfecha + " ]";
    }
    
}
