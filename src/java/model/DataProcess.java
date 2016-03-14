/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tannm_a05357
 */
public class DataProcess {

     public Connection getConnection() {
        Connection conn = DBConfig.getConnection();
        return conn;
    }

    public boolean checkLogin(String u, String p) {
        boolean f = false;
        try {
            String sql = "select * from TaiKhoan where TenDangNhap = ? and MatKhau = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, u);
            ps.setString(2, p);
            ResultSet rs = ps.executeQuery();
            f = rs.next();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

//    public boolean addAccount(String u, String p) {
//        int n = 0;
//        try {
//            String sql = "insert into tblAccount values (?,?)";
//            PreparedStatement ps = getConnection().prepareStatement(sql);
//            ps.setString(1, u);
//            ps.setString(2, p);
//            n = ps.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return n > 0;
//    }

    public ArrayList getLichTau(String tau) {
        ArrayList list = new ArrayList();
        try {
            String sql = "select * from LichTau join Tau on LichTau.IDTau = Tau.IDTau where TenTau like ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, tau);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList timTau(String gaDi, String gaDen, Date gioDi) {
        ArrayList listKQTK = new ArrayList();
        HashMap hmLichDi = new HashMap();
        HashMap hmLichDen = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cGioDi = Calendar.getInstance();
        cGioDi.setTime(gioDi);
        Calendar cGioDiDb = Calendar.getInstance();
        
        try {
            String sql1 = "select * from LichTau join Tau on LichTau.IDTau = Tau.IDTau join GaTau on LichTau.IDGaDung = GaTau.IDGaTau where TenGa like ?";
            PreparedStatement ps1 = getConnection().prepareStatement(sql1);
            ps1.setString(1, gaDi);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                LichTau lichDi = new LichTau();
                lichDi.setTau(rs1.getString("TenTau"));
                lichDi.setIdTau(rs1.getInt("IDTau"));
                lichDi.setGaDung(rs1.getString("TenGa"));
                lichDi.setSttGaDung(rs1.getInt("IDSttGa"));
                String g = rs1.getString("GioDi");
                try {
                    Date d = sdf.parse(g);               
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    cGioDiDb.setTime(d);
                    c.set(Calendar.DAY_OF_YEAR, cGioDi.get(Calendar.DAY_OF_YEAR));
                    lichDi.setGioDi(sdf.format(c.getTime()));
                } catch (ParseException ex) {
                    Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
                hmLichDi.put(lichDi.getIdTau(), lichDi);
            }
            rs1.close();
            ps1.close();
            
            PreparedStatement ps2 = getConnection().prepareStatement(sql1);
            ps2.setString(1, gaDen);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                LichTau lichDen = new LichTau();
                lichDen.setTau(rs2.getString("TenTau"));
                lichDen.setIdTau(rs2.getInt("IDTau"));
                lichDen.setGaDung(rs2.getString("TenGa"));
                lichDen.setSttGaDung(rs2.getInt("IDSttGa"));
                String g = rs2.getString("GioDen");
                try {
                    Date d = sdf.parse(g);               
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    int diff = c.get(Calendar.DAY_OF_YEAR) - cGioDiDb.get(Calendar.DAY_OF_YEAR);
                    c.set(Calendar.DAY_OF_YEAR, cGioDi.get(Calendar.DAY_OF_YEAR)+diff);
                    lichDen.setGioDen(sdf.format(c.getTime()));
                } catch (ParseException ex) {
                    Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
                hmLichDen.put(lichDen.getIdTau(), lichDen);
            }
            rs2.close();
            ps2.close();
            
            Iterator i = hmLichDi.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry e = (Map.Entry) i.next();
                int idTau = (int) e.getKey();
                LichTau lichDi = (LichTau) hmLichDi.get(idTau);
                LichTau lichDen = (LichTau) hmLichDen.get(idTau);
                if (lichDen!=null && lichDi.getSttGaDung()<lichDen.getSttGaDung()) {
                    KQTK kq = new KQTK();
                    kq.setLichDi(lichDi);
                    kq.setLichDen(lichDen);
                    listKQTK.add(kq);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listKQTK;
    }

}
