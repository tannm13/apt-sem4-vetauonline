/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.DataProcess;
import model.Ghe;

/**
 *
 * @author TAN
 */
@ManagedBean(name = "gheBean")
@RequestScoped
public class GheBean {

    private ArrayList cart;
    /**
     * Creates a new instance of GheBean
     */
    public GheBean() {
        cart = new ArrayList();
    }

    public ArrayList getCart() {
        return cart;
    }

    public void setCart(ArrayList cart) {
        this.cart = cart;
    }
    
    public String timGhe() {
        Map<String,String> params = 
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	String idTau = params.get("id-tau");
        String gioDi = params.get("gio-di");
        String gioDen = params.get("gio-den");
        
        DataProcess dp = new DataProcess();
        HashMap hmGhe = dp.getGhe(idTau, gioDi, gioDen);
        if (!hmGhe.isEmpty()) {
            ArrayList listGhe = new ArrayList(hmGhe.values());
            
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            
            session.setAttribute("listGhe", listGhe);
            return "dat-ghe";
        }
        return "failed";
    }
    
    public String themVe(Ghe g) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
        ArrayList c = (ArrayList) session.getAttribute("vetauCart");
        if (null != c) {
            cart = c;
        }
        cart.add(g);
            
        session.setAttribute("vetauCart", cart);
        return "vetau-cart";
    }
}
