/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.SinhVien;

/**
 *
 * @author ndnha
 */
public class SinhVienDao {
    public boolean insert (SinhVien sv) {
        try {
            String sql = "INSERT INTO [dbo].[SinhVien] ([MaSinhVien], [HoTen], [Email], [SoDT], [GioiTinh], [NgaySinh], [DiaChi])"
                    + " values(?,?,?,?,?,?,?)";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sv.getMaSinhVien());
            ps.setString(2, sv.getHoTen());
            ps.setString(3, sv.getEmail());
            ps.setString(4, sv.getSoDT());
            ps.setInt(5, sv.getGioiTinh());
            ps.setDate(6, (java.sql.Date) new Date(sv.getDob().getTime()));
            ps.setString(7, sv.getDiaChi());

            return ps.executeUpdate()>0;
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
        
    public boolean update (SinhVien sv) {
        try {
            String sql = "UPDATE dbo.SinhVien" +
                    " SET HoTen = ?, Email = ?, SoDT = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?" +
                    " where MaSinhVien = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(7, sv.getMaSinhVien());
            ps.setString(1, sv.getHoTen());
            ps.setString(2, sv.getEmail());
            ps.setString(3, sv.getSoDT());
            ps.setInt(4, sv.getGioiTinh());
            ps.setDate(5, (java.sql.Date) new Date(sv.getDob().getTime()));
            ps.setString(6, sv.getDiaChi());

            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete (String maSinhVien) {
        try {
            String sql = "delete from sinhvien where MaSinhVien=?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSinhVien);
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public SinhVien findById (String maSinhVien) {
        try {
            String sql = "select * from sinhvien where maSinhVien=?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSinhVien);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SinhVien sv = createSinhVien(rs);
                return sv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<SinhVien> findAll () {
        try {
            String sql = "select * from sinhvien";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<SinhVien> list = new ArrayList<>();
            while (rs.next()) {  
                SinhVien sv = createSinhVien(rs);
                list.add(sv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SinhVien createSinhVien(ResultSet rs) throws SQLException {
        SinhVien sv = new SinhVien();
        sv.setMaSinhVien(rs.getString("masinhvien"));
        sv.setHoTen(rs.getString("hoten"));
        sv.setEmail(rs.getString("email"));
        sv.setSoDT(rs.getString("soDT"));
        sv.setDiaChi(rs.getString("diachi"));
        sv.setGioiTinh(rs.getInt("gioitinh"));
        sv.setDob(rs.getDate("ngaysinh"));
        return sv;
    }
}
