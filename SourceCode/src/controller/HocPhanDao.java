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
import model.HocPhan;

/**
 *
 * @author ndnha
 */
public class HocPhanDao {

    public String findTenHP(String maHocPhan) {
        try {
            String sql = "select * from hocphan"
                    + " WHERE [MSHP] = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maHocPhan);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HocPhan hp = creatHocPhan(rs);

                return hp.getTenHocPhan();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HocPhan> findAll() {
        try {
            String sql = "select * from hocphan";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            ArrayList<HocPhan> list = new ArrayList<>();
            while (rs.next()) {
                HocPhan hp = creatHocPhan(rs);

                list.add(hp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HocPhan creatHocPhan(ResultSet rs) throws SQLException {
        HocPhan hp = new HocPhan();
        hp.setMSHP(rs.getString("MSHP"));
        hp.setTenHocPhan(rs.getString("TenHocPhan"));
        return hp;
    }
}
