/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tannm_a05357
 */
public class DataProcess {

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String user = "sa";
            String pass = "sa1234";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=BanVeTauOnline";
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public ArrayList getTrainTime(String train) {
        ArrayList list = new ArrayList();
        try {
            String sql = "select * from LichTau join Tau on LichTau.IDTau = Tau.IDTau where TenTau like ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, train);
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

}
