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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rcarrillo
 */
@Entity
@Table(name = "Calificacion")
@NamedQueries({
    @NamedQuery(name = "Calificacion.findAll", query = "SELECT c FROM Calificacion c")})
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CalificacionPK calificacionPK;
    @Basic(optional = false)
    @Column(name = "valor")
    private int valor;
    @JoinColumns({
        @JoinColumn(name = "ColaAtencion_id", referencedColumnName = "id", insertable = false, updatable = false)
        , @JoinColumn(name = "ColaAtencion_Barberia_id", referencedColumnName = "Barberia_id", insertable = false, updatable = false)
        , @JoinColumn(name = "ColaAtencion_fecha", referencedColumnName = "fecha", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ColaAtencion colaAtencion;
    @JoinColumns({
        @JoinColumn(name = "Turno_id", referencedColumnName = "id", insertable = false, updatable = false)
        , @JoinColumn(name = "Turno_Barbero_id", referencedColumnName = "Barbero_id", insertable = false, updatable = false)
        , @JoinColumn(name = "Turno_Cliente_id", referencedColumnName = "Cliente_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Turno turno;

    public Calificacion() {
    }

    public Calificacion(CalificacionPK calificacionPK) {
        this.calificacionPK = calificacionPK;
    }

    public Calificacion(CalificacionPK calificacionPK, int valor) {
        this.calificacionPK = calificacionPK;
        this.valor = valor;
    }

    public Calificacion(int turnoid, int turnoBarberoid, int turnoClienteid, int colaAtencionid, int colaAtencionBarberiaid, Date colaAtencionfecha) {
        this.calificacionPK = new CalificacionPK(turnoid, turnoBarberoid, turnoClienteid, colaAtencionid, colaAtencionBarberiaid, colaAtencionfecha);
    }

    public CalificacionPK getCalificacionPK() {
        return calificacionPK;
    }

    public void setCalificacionPK(CalificacionPK calificacionPK) {
        this.calificacionPK = calificacionPK;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public ColaAtencion getColaAtencion() {
        return colaAtencion;
    }

    public void setColaAtencion(ColaAtencion colaAtencion) {
        this.colaAtencion = colaAtencion;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calificacionPK != null ? calificacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calificacion)) {
            return false;
        }
        Calificacion other = (Calificacion) object;
        if ((this.calificacionPK == null && other.calificacionPK != null) || (this.calificacionPK != null && !this.calificacionPK.equals(other.calificacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Calificacion[ calificacionPK=" + calificacionPK + " ]";
    }
    
}
