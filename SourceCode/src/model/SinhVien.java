/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author ndnha
 */
public class SinhVien {
    private String MaSinhVien, HoTen, Email, SoDT, DiaChi;
    private Date dob;//dd/mm/yyyy
    private int GioiTinh;

    public SinhVien() {
    }

    public SinhVien(String MaSinhVien, String HoTen, String Email, String SoDT, String DiaChi, Date dob, int GioiTinh) {
        this.MaSinhVien = MaSinhVien;
        this.HoTen = HoTen;
        this.Email = Email;
        this.SoDT = SoDT;
        this.DiaChi = DiaChi;
        this.dob = dob;
        this.GioiTinh = GioiTinh;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getMaSinhVien() {
        return MaSinhVien;
    }

    public void setMaSinhVien(String MaSinhVien) {
        this.MaSinhVien = MaSinhVien;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String SoDT) {
        this.SoDT = SoDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public int getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(int GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

}
