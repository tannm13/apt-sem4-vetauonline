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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    //frontend
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
                int gDen = rs1.getInt("GioDen");
                int phutDen = rs1.getInt("PhutDen");
                int ngayThemDen = rs1.getInt("NgayThemDen");
                Calendar cGioDi = Calendar.getInstance();
                cGioDi.setTime(gioDi);
                cGioDi.add(Calendar.DATE, ngayThemDen);
                cGioDi.set(Calendar.HOUR_OF_DAY, gDen);
                cGioDi.set(Calendar.MINUTE, phutDen);
                lichDi.setGioDen(sdf.format(cGioDi.getTime()));
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
                int gDen = rs2.getInt("GioDen");
                int phutDen = rs2.getInt("PhutDen");
                int ngayThemDen = rs2.getInt("NgayThemDen");
                Calendar cGioDen = Calendar.getInstance();
                cGioDen.setTime(gioDi);
                cGioDen.add(Calendar.DATE, ngayThemDen);
                cGioDen.set(Calendar.HOUR_OF_DAY, gDen);
                cGioDen.set(Calendar.MINUTE, phutDen);
                lichDen.setGioDen(sdf.format(cGioDen.getTime()));
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
                if (lichDen != null && lichDi.getSttGaDung() < lichDen.getSttGaDung()) {
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

    public HashMap getGhe(String idTau, String gioDi, String gioDen) {
        HashMap list = new HashMap();
        try {
            String sql = "select * from Ghe left outer join VeTau on Ghe.IDGhe = VeTau.IDGhe \n" +
                         "and ((GioDi is null and GioDen is null)\n" +
                         "or (not (((GioDi<?) and (GioDen<?)) or ((GioDi>?) and (GioDen>?)))))\n" +
                         "where Ghe.IDTau like ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, gioDi);
            ps.setString(2, gioDi);
            ps.setString(3, gioDen);
            ps.setString(4, gioDen);
            ps.setString(5, idTau);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ghe g = new Ghe();
                g.setIdGhe(rs.getString(1));
                g.setIdTau(rs.getInt(2));
                g.setLoaiGhe(rs.getString("LoaiGhe"));
                String idve = rs.getString("IDVe");
                if (null == idve) {
                    g.setStatusGhe("COSAN");
                } else {
                    g.setStatusGhe("DABAN");
                }
                list.put(g.getIdGhe(), g);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean themDonhang(DonHang don) {
        int n = 0;
        try {
            String sql = "insert into DonHang(TenKH, CMND) values (?,?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, don.getHoTen());
            ps.setString(2, don.getCmtnd());
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n > 0;
    }
    
    public int getTopIdDonhang() {
        int id = 0;
        try {
            String sql = "select top 1 from DonHang order by IDDonHang desc";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("IDDonHang");
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public boolean themVetau(VeTau ve) {
        int n = 0;
        try {
            String sql = "insert into VeTau(IDGhe, TenKH, CMND, IDDonHang) values (?,?,?,?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, ve.getIdGhe());
            ps.setString(2, ve.getTenKh());
            ps.setString(3, ve.getCmnd());
            ps.setInt(4, ve.getIdDonhang());
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n > 0;
    }
    
    // backend
//    public boolean themLichTau(int idTai, int idGaDung, int sttGa, String gioDi, String gioDen) {
//        int n = 0;
//        try {
//            String sql = "insert into LichTau(IDTau,IDGaDung,IDSttGa,GioDi,GioDen) values (?,?,?,?,?)";
//            PreparedStatement ps = getConnection().prepareStatement(sql);
//            ps.setInt(1, idTai);
//            ps.setInt(2, idGaDung);
//            ps.setInt(3, sttGa);
//            ps.setString(4, gioDi);
//            ps.setString(5, gioDen);
////            ps.setInt(6, khoangCach);
////            ps.setInt(7, idTk);
//            n = ps.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return n > 0;
//    }
//
//    public ArrayList getTrainTimeById(int id) {
//        ArrayList<LichTau> list = new ArrayList();
//        try {
//            String sql = "select * from LichTau join Tau on LichTau.IDTau = Tau.IDTau join GaTau on LichTau.IDGaDung = GaTau.IDGaTau where LichTau.IDTau = ?";
//            PreparedStatement ps = getConnection().prepareStatement(sql);
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                String tau = rs.getString("TenTau");
//                String gaDung = rs.getString("TenGa");
//                int sttGaDung = rs.getInt("IDSttGa");
//                String gioDi = rs.getString("GioDi");
//                String gioDen = rs.getString("GioDen");
//                int khoangCach = rs.getInt("KhoangCach");
//                LichTau lt = new LichTau();
//                lt.setGaDung(gaDung);
//                lt.setGioDen(gioDen);
//                lt.setGioDi(gioDi);
//                lt.setKhoangCach(khoangCach);
//                lt.setSttGaDung(sttGaDung);
//                lt.setTau(tau);
//                list.add(lt);
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }
//
//    public ArrayList getTrainTime(String train) {
//        ArrayList<LichTau> list = new ArrayList();
//        try {
//            String sql = "select * from LichTau join Tau on LichTau.IDTau = Tau.IDTau join GaTau on LichTau.IDGaDung = GaTau.IDGaTau where TenTau like ?";
//            PreparedStatement ps = getConnection().prepareStatement(sql);
//            ps.setString(1, train);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                String tau = rs.getString("TenTau");
//                String gaDung = rs.getString("TenGa");
//                int sttGaDung = rs.getInt("IDSttGa");
//                String gioDi = rs.getString("GioDi");
//                String gioDen = rs.getString("GioDen");
//                int khoangCach = rs.getInt("KhoangCach");
//                LichTau lt = new LichTau();
//                lt.setGaDung(gaDung);
//                lt.setGioDen(gioDen);
//                lt.setGioDi(gioDi);
//                lt.setKhoangCach(khoangCach);
//                lt.setSttGaDung(sttGaDung);
//                lt.setTau(tau);
//                list.add(lt);
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }
//
//    public ArrayList<Tau> getTrain() {
//        ArrayList<Tau> list = new ArrayList();
//        try {
//            String sql = "select IDTau,TenTau from Tau";
//            PreparedStatement ps = getConnection().prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Tau tau = new Tau();
//                String name = rs.getString("TenTau");
//                int id = rs.getInt("IDTau");
//                tau.setIDTau(id);
//                tau.setTentau(name);
//                list.add(tau);
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }
}
