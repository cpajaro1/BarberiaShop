/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Control.UsuarioJpaController;
import Modelo.Usuario;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class AdminUsuario extends UsuarioJpaController {
    
    
        
        


    public Usuario inicioSesion (String usuario,String password){
        
        
      for (Usuario x:findUsuarioEntities()) {
          System.out.println(x);
          if (x.getUsuarioPK().getUsuario().equals(usuario)&& x.getPassword().equals(password)){
              return x;
          }
      } 
      return null;
        
    
        
        
        
        
    }
    
}
