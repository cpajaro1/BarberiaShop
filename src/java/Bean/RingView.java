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

/**
 *
 * @author FAMILIA
 */
@Named(value = "ringView")
@SessionScoped
public class RingView implements Serializable {
    
    private List<Barberia> barberias;
    private  Barberia selectedBarberia;
    
    
    @PostConstruct
    public void init() {
        barberias = new ArrayList<Barberia>();
        //barberias =  se debe llamar un metodo que liste las barberias 
        barberias.add( new Barberia(1,"Barberia 2", "Calle 54"));         
        barberias.add( new Barberia(1,"Barberia 1", "Calle 53")); 
        barberias.add( new Barberia(1,"Barberia 3", "Calle 51")); 
    }

    public List<Barberia> getBarberias() {
        return barberias;
    }

    public void setBarberias(List<Barberia> barberias) {
        this.barberias = barberias;
    }

    public Barberia getSelectedBarberia() {
        return selectedBarberia;
    }

    public void setSelectedBarberia(Barberia selectedBarberia) {
        this.selectedBarberia = selectedBarberia;
    }
}
