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
public class VincunlacionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Barberia_id")
    private int barberiaid;
    @Basic(optional = false)
    @Column(name = "Barbero_id")
    private int barberoid;

    public VincunlacionPK() {
    }

    public VincunlacionPK(int barberiaid, int barberoid) {
        this.barberiaid = barberiaid;
        this.barberoid = barberoid;
    }

    public int getBarberiaid() {
        return barberiaid;
    }

    public void setBarberiaid(int barberiaid) {
        this.barberiaid = barberiaid;
    }

    public int getBarberoid() {
        return barberoid;
    }

    public void setBarberoid(int barberoid) {
        this.barberoid = barberoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) barberiaid;
        hash += (int) barberoid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VincunlacionPK)) {
            return false;
        }
        VincunlacionPK other = (VincunlacionPK) object;
        if (this.barberiaid != other.barberiaid) {
            return false;
        }
        if (this.barberoid != other.barberoid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.VincunlacionPK[ barberiaid=" + barberiaid + ", barberoid=" + barberoid + " ]";
    }
    
}
