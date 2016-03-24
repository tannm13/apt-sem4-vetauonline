/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author tannm_a05357
 */
@ManagedBean(name = "donHangBean")
@RequestScoped
public class DonHangBean {

    private String hoTen;
    private String cmtnd;
    /**
     * Creates a new instance of DonHangBean
     */
    public DonHangBean() {
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getCmtnd() {
        return cmtnd;
    }

    public void setCmtnd(String cmtnd) {
        this.cmtnd = cmtnd;
    }
    
    public String muaHang() {
        DataProcess dp = new DataProcess();
        DonHang d = new DonHang();
        d.setHoTen(hoTen);
        d.setCmtnd(cmtnd);
        if (dp.themDonhang(d)) {
            int idDonhang = dp.getTopIdDonhang();
            Map<String,String> params = 
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            int size = Integer.parseInt(params.get("tong-so"));
            for (int i = 0; i < size; i++) {
                VeTau v = new VeTau();
                v.setIdGhe(params.get("idghe-"+i));
                v.setTenKh(params.get("hoten-"+i));
                v.setCmnd(params.get("cmt-"+i));
                v.setIdDonhang(idDonhang);
                dp.themVetau(v);
            }
        }
        return "thongtin-vetau";
    }
}
