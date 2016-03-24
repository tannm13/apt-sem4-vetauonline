/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author TAN
 */
public class VeTau {
    private String idGhe;
    private String tenKh;
    private String cmnd;
    private int idDonhang;

    public int getIdDonhang() {
        return idDonhang;
    }

    public void setIdDonhang(int idDonhang) {
        this.idDonhang = idDonhang;
    }
    
    

    public String getIdGhe() {
        return idGhe;
    }

    public void setIdGhe(String idGhe) {
        this.idGhe = idGhe;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }
    
    
}
