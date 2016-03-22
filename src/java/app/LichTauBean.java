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
import model.LichTau;

/**
 *
 * @author tannm_a05357
 */
public class LichTauBean {

    private ArrayList<LichTau> lstLichTau;
    private String train;
    private int IDTau;

    public void setIDTau(int IDTau) {
        this.IDTau = IDTau;
    }

    
    
    public int getIDTau() {
        return IDTau;
    }

    public ArrayList<LichTau> getLstLichTau() {
        return lstLichTau;
    }

    public void setLstLichTau(ArrayList<LichTau> lstLichTau) {
        this.lstLichTau = lstLichTau;
    }
    
    
    
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
        lstLichTau = new ArrayList<>();
        for(int i = 0 ; i<= 4; i++){
            LichTau lt = new LichTau();
            lstLichTau.add(lt);
        }
    }
    
    public String getLichTau() {
        DataProcess dp = new DataProcess();
        if (true) {
//            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//                    .getExternalContext().getSession(false);
//            session.setAttribute("traintime", this);
            return "traintime-detail";
        }
        return "failed";
    }
    
//    public String ThemLichTau(){
//        DataProcess dp = new DataProcess();
//        for(int i = 0 ; i< lstLichTau.size(); i++){
//            dp.themLichTau(IDTau, lstLichTau.get(i).getIdGaDung(), i+1, lstLichTau.get(i).getGioDi(), lstLichTau.get(i).getGioDen());
//        }
//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//                    .getExternalContext().getSession(false);
//            session.setAttribute("lstTrainTime", dp.getTrainTimeById(IDTau));
//            return "traintime-detail.xhtml";
//    }
}
