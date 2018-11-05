/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Control.UsuarioJpaController;
import Modelo.Usuario;
import Modelo.UsuarioPK;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author rcarrillo
 */
public class Conexion {
    public  static void main (String[]args) throws Exception{
        
        //EntityManagerFactory smf= Persistence.createEntityManagerFactory("BarberschopCTAPU");
        
        
        
        UsuarioJpaController dao=new UsuarioJpaController();
        //dao.findUsuarioEntities();
//        Usuario u=new Usuario();
//        u.setEstado( (short)1);
//        u.setIdUsuario("1");
//        u.setPassword("123");
//        u.setTipo("A");
//        u.setUsuarioPK(new UsuarioPK(0, "rcarrillo"));
//        dao.create(u);
dao.findUsuarioEntities().forEach((x) -> {
    System.out.println(x);
        });
        
        
        
        
    }
}
