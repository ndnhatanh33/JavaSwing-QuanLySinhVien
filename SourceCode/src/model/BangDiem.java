/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ndnha
 */
public class BangDiem {
    private String MaSinhVien, MSHP, NamHoc, HocKy; 
    private double Diem;

    public BangDiem() {
    }

    public BangDiem(String MaSinhVien, String MSHP, String NamHoc, String HocKy, double Diem) {
        this.MaSinhVien = MaSinhVien;
        this.MSHP = MSHP;
        this.NamHoc = NamHoc;
        this.HocKy = HocKy;
        this.Diem = Diem;
    }

    public String getMaSinhVien() {
        return MaSinhVien;
    }

    public void setMaSinhVien(String MaSinhVien) {
        this.MaSinhVien = MaSinhVien;
    }

    public String getMSHP() {
        return MSHP;
    }

    public void setMSHP(String MSHP) {
        this.MSHP = MSHP;
    }

    public String getNamHoc() {
        return NamHoc;
    }

    public void setNamHoc(String NamHoc) {
        this.NamHoc = NamHoc;
    }

    public String getHocKy() {
        return HocKy;
    }

    public void setHocKy(String HocKy) {
        this.HocKy = HocKy;
    }

    public double getDiem() {
        return Diem;
    }

    public void setDiem(double Diem) {
        this.Diem = Diem;
    }
    
    
}
