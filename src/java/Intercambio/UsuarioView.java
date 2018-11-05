/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intercambio;

import Servicio.AdminUsuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;
import static org.primefaces.component.focus.Focus.PropertyKeys.context;

@ManagedBean
public class UsuarioView {

    private AdminUsuario operacion = new AdminUsuario();

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(ActionEvent event) {
        //  System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        FacesMessage message = null;
        boolean loggedIn = false;

        if (operacion.inicioSesion(username, password) != null) {
            System.out.println("iniciado");
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "BIENVENIDO", username);
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }
      
        FacesContext.getCurrentInstance()
                .addMessage(null, message);
        
    }
}
