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
public class TurnoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "Barbero_id")
    private int barberoid;
    @Basic(optional = false)
    @Column(name = "Cliente_id")
    private int clienteid;

    public TurnoPK() {
    }

    public TurnoPK(int id, int barberoid, int clienteid) {
        this.id = id;
        this.barberoid = barberoid;
        this.clienteid = clienteid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBarberoid() {
        return barberoid;
    }

    public void setBarberoid(int barberoid) {
        this.barberoid = barberoid;
    }

    public int getClienteid() {
        return clienteid;
    }

    public void setClienteid(int clienteid) {
        this.clienteid = clienteid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) barberoid;
        hash += (int) clienteid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TurnoPK)) {
            return false;
        }
        TurnoPK other = (TurnoPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.barberoid != other.barberoid) {
            return false;
        }
        if (this.clienteid != other.clienteid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.TurnoPK[ id=" + id + ", barberoid=" + barberoid + ", clienteid=" + clienteid + " ]";
    }
    
}
