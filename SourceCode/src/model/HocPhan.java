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
public class HocPhan {
    private String MSHP, TenHocPhan;

    public HocPhan() {
    }

    public HocPhan(String MSHP, String TenHocPhan) {
        this.MSHP = MSHP;
        this.TenHocPhan = TenHocPhan;
    }

    public String getMSHP() {
        return MSHP;
    }

    public void setMSHP(String MSHP) {
        this.MSHP = MSHP;
    }

    public String getTenHocPhan() {
        return TenHocPhan;
    }

    public void setTenHocPhan(String TenHocPhan) {
        this.TenHocPhan = TenHocPhan;
    }
    
    
}
