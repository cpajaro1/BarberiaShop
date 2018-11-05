/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Modelo.Barberia;
import Modelo.Servicio;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author FAMILIAR
 */
@Named(value = "tabbedView")
@SessionScoped
public class TabbedView   implements Serializable {

 private List<Servicio> servicios;
   
 
    @PostConstruct
    public void init() {
        servicios =  new ArrayList<Servicio>();
        servicios.add(new Servicio(1,"cortes para jovenes","Corte especificos para jovenes"));    
        servicios.add(new Servicio(3,"cortes para niños","Corte especificos para niños")); 
        servicios.add(new Servicio(2,"cortes para ancianos","Corte especificos para ancianos"));  
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
     
    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
         
    public void onTabClose(TabCloseEvent event) {
        FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}

