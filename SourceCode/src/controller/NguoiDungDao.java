/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.NguoiDung;

/**
 *
 * @author ndnha
 */
public class NguoiDungDao {

    public NguoiDung checkLogin(String tenDangNhap, String matKhau) {
        try {
            String sql = "select tenDangNhap, matKhau from nguoidung "
                    + " where tendangnhap=? and matKhau=?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setTenDangNhap(tenDangNhap);

                return nd;
            }
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
