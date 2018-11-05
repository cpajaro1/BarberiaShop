/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author rcarrillo
 */
public class Parametro {
    public static EntityManagerFactory conf (){
        return Persistence.createEntityManagerFactory("BarberschopCTAPU");
    }
}
