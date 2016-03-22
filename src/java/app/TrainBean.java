/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.DataProcess;
import model.Tau;

/**
 *
 * @author Duc
 */
public class TrainBean {
    private String tenTau;
    
    private ArrayList<Tau> lstTrain;

    public ArrayList<Tau> getLstTrain() {
        return lstTrain;
    }

    public void setLstTrain(ArrayList<Tau> lstTrain) {
        this.lstTrain = lstTrain;
    }

    public String getLstTrainTemp() {
        return tenTau;
    }

    public void setLstTrainTemp(String tenTau) {
        this.tenTau = tenTau;
    }

    
    
    

    
    
    
    /**
     * Creates a new instance of TrainBean
     */
//    public TrainBean() {
//        DataProcess dt = new DataProcess();
//        lstTrain = dt.getTrain();
//    }
//    
//    public String tim(){
//       String strName = tenTau;
//       DataProcess dt = new DataProcess();
//       HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//                    .getExternalContext().getSession(false);
//            session.setAttribute("lstTrainTime", dt.getTrainTime(strName));
//        return "traintime-detail";
//    }
    
}
