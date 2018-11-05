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
@Table(name = "ServicioOfrecidos")
@NamedQueries({
    @NamedQuery(name = "ServicioOfrecidos.findAll", query = "SELECT s FROM ServicioOfrecidos s")})
public class ServicioOfrecidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ServicioOfrecidosPK servicioOfrecidosPK;
    @Basic(optional = false)
    @Column(name = "costo")
    private int costo;
    @Basic(optional = false)
    @Column(name = "estado")
    private short estado;
    @Basic(optional = false)
    @Column(name = "duracion_min")
    @Temporal(TemporalType.TIMESTAMP)
    private Date duracionMin;
    @JoinColumn(name = "Barberia_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Barberia barberia;
    @JoinColumn(name = "Servicio_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Servicio servicio;

    public ServicioOfrecidos() {
    }

    public ServicioOfrecidos(ServicioOfrecidosPK servicioOfrecidosPK) {
        this.servicioOfrecidosPK = servicioOfrecidosPK;
    }

    public ServicioOfrecidos(ServicioOfrecidosPK servicioOfrecidosPK, int costo, short estado, Date duracionMin) {
        this.servicioOfrecidosPK = servicioOfrecidosPK;
        this.costo = costo;
        this.estado = estado;
        this.duracionMin = duracionMin;
    }

    public ServicioOfrecidos(int barberiaid, int servicioid) {
        this.servicioOfrecidosPK = new ServicioOfrecidosPK(barberiaid, servicioid);
    }

    public ServicioOfrecidosPK getServicioOfrecidosPK() {
        return servicioOfrecidosPK;
    }

    public void setServicioOfrecidosPK(ServicioOfrecidosPK servicioOfrecidosPK) {
        this.servicioOfrecidosPK = servicioOfrecidosPK;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public Date getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(Date duracionMin) {
        this.duracionMin = duracionMin;
    }

    public Barberia getBarberia() {
        return barberia;
    }

    public void setBarberia(Barberia barberia) {
        this.barberia = barberia;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servicioOfrecidosPK != null ? servicioOfrecidosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioOfrecidos)) {
            return false;
        }
        ServicioOfrecidos other = (ServicioOfrecidos) object;
        if ((this.servicioOfrecidosPK == null && other.servicioOfrecidosPK != null) || (this.servicioOfrecidosPK != null && !this.servicioOfrecidosPK.equals(other.servicioOfrecidosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.ServicioOfrecidos[ servicioOfrecidosPK=" + servicioOfrecidosPK + " ]";
    }
    
}
