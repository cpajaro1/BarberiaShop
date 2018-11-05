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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rcarrillo
 */
@Entity
@Table(name = "Vincunlacion")
@NamedQueries({
    @NamedQuery(name = "Vincunlacion.findAll", query = "SELECT v FROM Vincunlacion v")})
public class Vincunlacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VincunlacionPK vincunlacionPK;
    @Basic(optional = false)
    @Column(name = "tipo_contrato")
    private String tipoContrato;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @JoinColumn(name = "Barberia_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Barberia barberia;
    @JoinColumn(name = "Barbero_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Barbero barbero;

    public Vincunlacion() {
    }

    public Vincunlacion(VincunlacionPK vincunlacionPK) {
        this.vincunlacionPK = vincunlacionPK;
    }

    public Vincunlacion(VincunlacionPK vincunlacionPK, String tipoContrato, Date fechaInicio, Date fechaFinal) {
        this.vincunlacionPK = vincunlacionPK;
        this.tipoContrato = tipoContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public Vincunlacion(int barberiaid, int barberoid) {
        this.vincunlacionPK = new VincunlacionPK(barberiaid, barberoid);
    }

    public VincunlacionPK getVincunlacionPK() {
        return vincunlacionPK;
    }

    public void setVincunlacionPK(VincunlacionPK vincunlacionPK) {
        this.vincunlacionPK = vincunlacionPK;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Barberia getBarberia() {
        return barberia;
    }

    public void setBarberia(Barberia barberia) {
        this.barberia = barberia;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public void setBarbero(Barbero barbero) {
        this.barbero = barbero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vincunlacionPK != null ? vincunlacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vincunlacion)) {
            return false;
        }
        Vincunlacion other = (Vincunlacion) object;
        if ((this.vincunlacionPK == null && other.vincunlacionPK != null) || (this.vincunlacionPK != null && !this.vincunlacionPK.equals(other.vincunlacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Vincunlacion[ vincunlacionPK=" + vincunlacionPK + " ]";
    }
    
}
