/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.DataProcess;

/**
 *
 * @author TAN
 */
@ManagedBean
@RequestScoped
public class TimTauBean implements Serializable {

    private String gaDi;
    private String gaDen;

    public String getGaDi() {
        return gaDi;
    }

    public void setGaDi(String gaDi) {
        this.gaDi = gaDi;
    }

    public String getGaDen() {
        return gaDen;
    }

    public void setGaDen(String gaDen) {
        this.gaDen = gaDen;
    }
    
    
    
    /**
     * Creates a new instance of TimTauBean
     */
    public TimTauBean() {
    }
    
    public String timTau() {
        DataProcess dp = new DataProcess();
        ArrayList listKQTK = dp.timTau(gaDi, gaDen, "");
        if (!listKQTK.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.setAttribute("listKQTK", listKQTK);
            return "train-search";
        }
        return "failed";
    }
}
