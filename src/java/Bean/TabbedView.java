/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Modelo.Barberia;
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
public class TabbedView {

 private List<Barberia> barberias;
   
 
    @PostConstruct
    public void init() {
        barberias = new ArrayList<Barberia>();
        barberias.add(new Barberia(1,"Y25YIH5", "fgn"));
        barberias.add(new Barberia(1,"Y2grrg", "fgnn"));
        barberias.add(new Barberia(1,"Y2fdgh", "fnng"));
        barberias.add(new Barberia(1,"Y2dgn", "fgng"));
        barberias.add(new Barberia(1,"Y2gn5", "fgnn"));
        
    }
     
    public List<Barberia> getBarberias() {
        return barberias;
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

