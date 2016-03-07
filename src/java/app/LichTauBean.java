/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.DataProcess;

/**
 *
 * @author tannm_a05357
 */
@ManagedBean
@SessionScoped
public class LichTauBean {

    private String train;

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }
    /**
     * Creates a new instance of TrainTimeBean
     */
    
    
    public LichTauBean() {
    }
    
    public String getLichTau() {
        DataProcess dp = new DataProcess();
        if (true) {
//            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//                    .getExternalContext().getSession(false);
//            session.setAttribute("traintime", this);
            return "traintime-detail";
        }
        return "traintime";
    } 
}
