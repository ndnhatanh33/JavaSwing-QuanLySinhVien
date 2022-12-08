/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.BangDiem;

/**
 *
 * @author ndnha
 */
public class BangDiemDao {

    public boolean insert(BangDiem bd) {
        try {
            String sql = "INSERT INTO [dbo].[BangDiem] ([MaSinhVien],[MSHP],[NamHoc],[HocKy],[Diem])"
                    + " values(?,?,?,?,?)";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bd.getMaSinhVien());
            ps.setString(2, bd.getMSHP());
            ps.setString(3, bd.getNamHoc());
            ps.setString(4, bd.getHocKy());
            ps.setDouble(5, bd.getDiem());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(BangDiem bd) {
        try {
            String sql = "UPDATE dbo.BangDiem SET Diem =?"
                    + " WHERE MaSinhVien = ? AND MSHP = ? AND NamHoc = ? AND HocKy = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(2, bd.getMaSinhVien());
            ps.setString(3, bd.getMSHP());
            ps.setString(4, bd.getNamHoc());
            ps.setString(5, bd.getHocKy());
            ps.setDouble(1, bd.getDiem());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maSinhVien, String namHoc, String hocKy, String MSHP) {
        try {
            String sql = "delete from bangdiem"
                    + " WHERE [MaSinhVien] = ? AND [NamHoc] = ? AND [HocKy] = ? AND [MSHP] = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSinhVien);
            ps.setString(2, namHoc);
            ps.setString(3, hocKy);
            ps.setString(4, MSHP);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<BangDiem> findByID(String maSinhVien) {
        try {
            String sql = "select * from bangdiem"
                    + " WHERE [MaSinhVien] = ? ORDER BY NamHoc, HocKy";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSinhVien);

            ResultSet rs = ps.executeQuery();
            ArrayList<BangDiem> list = new ArrayList<>();
            while (rs.next()) {
                BangDiem bd = creatBangDiem(rs);
                list.add(bd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BangDiem> findByID_NamHoc(String maSinhVien, String namHoc) {
        try {
            String sql = "select * from bangdiem"
                    + " WHERE [MaSinhVien] = ? AND [NamHoc] = ? ORDER BY HocKy";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSinhVien);
            ps.setString(2, namHoc);

            ResultSet rs = ps.executeQuery();
            ArrayList<BangDiem> list = new ArrayList<>();
            while (rs.next()) {
                BangDiem bd = creatBangDiem(rs);
                list.add(bd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BangDiem> findByID_HocKy(String maSinhVien, String hocKy) {
        try {
            String sql = "select * from bangdiem"
                    + " WHERE [MaSinhVien] = ? AND [HocKy] = ? ORDER BY NamHoc";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSinhVien);
            ps.setString(2, hocKy);

            ResultSet rs = ps.executeQuery();
            ArrayList<BangDiem> list = new ArrayList<>();
            while (rs.next()) {
                BangDiem bd = creatBangDiem(rs);
                list.add(bd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BangDiem> findByID_NamHoc_HocKy(String maSinhVien, String namHoc, String hocKy) {
        try {
            String sql = "select * from bangdiem"
                    + " WHERE [MaSinhVien] = ? AND [NamHoc] = ? AND [HocKy] = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSinhVien);
            ps.setString(2, namHoc);
            ps.setString(3, hocKy);

            ResultSet rs = ps.executeQuery();
            ArrayList<BangDiem> list = new ArrayList<>();
            while (rs.next()) {
                BangDiem bd = creatBangDiem(rs);
                list.add(bd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BangDiem findByID_MSHP_NamHoc_HocKy(String maSinhVien, String MSHP, String namHoc, String hocKy) {
        try {
            String sql = "select * from bangdiem"
                    + " WHERE [MaSinhVien] = ? AND [MSHP] = ? AND [NamHoc] = ? AND [HocKy] = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSinhVien);
            ps.setString(2, MSHP);
            ps.setString(3, namHoc);
            ps.setString(4, hocKy);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BangDiem bd = creatBangDiem(rs);
                return bd;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private BangDiem creatBangDiem(ResultSet rs) throws SQLException {
        BangDiem bd = new BangDiem();
        bd.setMaSinhVien(rs.getString("maSinhVien"));
        bd.setMSHP(rs.getString("MSHP"));
        bd.setNamHoc(rs.getString("NamHoc"));
        bd.setHocKy(rs.getString("HocKy"));
        bd.setDiem(rs.getDouble("Diem"));
        return bd;
    }
}
