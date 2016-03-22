/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private String ngayDi;
    private String ngayVe;

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

    public String getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(String ngayDi) {
        this.ngayDi = ngayDi;
    }

    public String getNgayVe() {
        return ngayVe;
    }

    public void setNgayVe(String ngayVe) {
        this.ngayVe = ngayVe;
    }
    
    
    
    /**
     * Creates a new instance of TimTauBean
     */
    public TimTauBean() {
        
    }
    
    public String timTau() {
        Map<String,String> params = 
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	String loaiVe = params.get("khu-hoi");
        String sGioDi = params.get("gioDi");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date dateNgayDi = new Date();
        try {
            dateNgayDi = sdf.parse(ngayDi);
        } catch (ParseException ex) {
            Logger.getLogger(TimTauBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataProcess dp = new DataProcess();
        ArrayList listKQTK = dp.timTau(gaDi, gaDen, dateNgayDi);
        if (!listKQTK.isEmpty()) {
            
            
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            
            session.setAttribute("listKQTK", listKQTK);
            return "train-search";
        }
        return "failed";
    }
}
