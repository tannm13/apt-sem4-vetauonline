/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.DataProcess;

/**
 *
 * @author tannm_a05357
 */
@ManagedBean
@RequestScoped
public class AccountBean {

    private String user;
    private String pass;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
    /**
     * Creates a new instance of AccountBean
     */
    public AccountBean() {
    }
    
    public String checkLogin() {
        DataProcess dp = new DataProcess();
        if (dp.checkLogin(user, pass)) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.setAttribute("user", this);
            return "success";
        }
        return "failed";
    }
}
