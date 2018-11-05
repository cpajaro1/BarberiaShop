/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rcarrillo
 */
@Entity
@Table(name = "Barberia")
@NamedQueries({
    @NamedQuery(name = "Barberia.findAll", query = "SELECT b FROM Barberia b")})
public class Barberia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "correo")
    private String correo;
    @JoinTable(name = "JornadaTrabajo", joinColumns = {
        @JoinColumn(name = "id_barberia", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_jornada", referencedColumnName = "id")})
    @ManyToMany
    private List<Jornada> jornadaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "barberia")
    private List<ServicioOfrecidos> servicioOfrecidosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "barberia")
    private List<Vincunlacion> vincunlacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "barberia")
    private List<ColaAtencion> colaAtencionList;

    public Barberia() {
    }

    public Barberia(Integer id) {
        this.id = id;
    }

    public Barberia(Integer id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Jornada> getJornadaList() {
        return jornadaList;
    }

    public void setJornadaList(List<Jornada> jornadaList) {
        this.jornadaList = jornadaList;
    }

    public List<ServicioOfrecidos> getServicioOfrecidosList() {
        return servicioOfrecidosList;
    }

    public void setServicioOfrecidosList(List<ServicioOfrecidos> servicioOfrecidosList) {
        this.servicioOfrecidosList = servicioOfrecidosList;
    }

    public List<Vincunlacion> getVincunlacionList() {
        return vincunlacionList;
    }

    public void setVincunlacionList(List<Vincunlacion> vincunlacionList) {
        this.vincunlacionList = vincunlacionList;
    }

    public List<ColaAtencion> getColaAtencionList() {
        return colaAtencionList;
    }

    public void setColaAtencionList(List<ColaAtencion> colaAtencionList) {
        this.colaAtencionList = colaAtencionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Barberia)) {
            return false;
        }
        Barberia other = (Barberia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Barberia[ id=" + id + " ]";
    }
    
}
