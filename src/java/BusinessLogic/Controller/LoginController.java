/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Controller;

import DataAccess.DAO.UserDAO;
import DataAccess.Entity.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Juan
 */
public class LoginController {
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage faceMessage;
    
    public LoginController(){
        faceContext = FacesContext.getCurrentInstance(); 
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }
    
    
    public String login(String username, String password){
        
        UserDAO userDAO = new UserDAO();
        Usuario user = userDAO.searchByUsername(username);
        
        if (user != null){
            if(user.getPassword().equals(password)){
               httpServletRequest.getSession().setAttribute("sessionUsuario", user);
               faceMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,"Acceso Correcto", null);
               faceContext.addMessage(null, faceMessage);
               return "home";
            }else{
                faceMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario o Contraseña incorrecto", null);
                faceContext.addMessage(null, faceMessage);
                return "index";
            }
        }else{
            faceMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario o Contraseña incorrecto", null);
            faceContext.addMessage(null, faceMessage);
            return "index";
        }
    }
    
    public String logout(){
        httpServletRequest.getSession().removeAttribute("sessionUsuario");
        faceMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,"Session cerrada correctmente", null);
        faceContext.addMessage(null, faceMessage);
        return "index";
    }
}
