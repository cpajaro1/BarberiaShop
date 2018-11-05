/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rcarrillo
 */
@Embeddable
public class ServicioOfrecidosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Barberia_id")
    private int barberiaid;
    @Basic(optional = false)
    @Column(name = "Servicio_id")
    private int servicioid;

    public ServicioOfrecidosPK() {
    }

    public ServicioOfrecidosPK(int barberiaid, int servicioid) {
        this.barberiaid = barberiaid;
        this.servicioid = servicioid;
    }

    public int getBarberiaid() {
        return barberiaid;
    }

    public void setBarberiaid(int barberiaid) {
        this.barberiaid = barberiaid;
    }

    public int getServicioid() {
        return servicioid;
    }

    public void setServicioid(int servicioid) {
        this.servicioid = servicioid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) barberiaid;
        hash += (int) servicioid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioOfrecidosPK)) {
            return false;
        }
        ServicioOfrecidosPK other = (ServicioOfrecidosPK) object;
        if (this.barberiaid != other.barberiaid) {
            return false;
        }
        if (this.servicioid != other.servicioid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.ServicioOfrecidosPK[ barberiaid=" + barberiaid + ", servicioid=" + servicioid + " ]";
    }
    
}
