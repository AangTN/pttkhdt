package DTO;

import java.sql.Date;

public class DTONapTien {
    private String ID;
    private String IDTaiKhoan;
    private int GiaTriNap;
    private Date ThoiGian;

    public DTONapTien(String ID, String IDTaiKhoan, int GiaTriNap, Date ThoiGian) {
        this.ID = ID;
        this.IDTaiKhoan = IDTaiKhoan;
        this.GiaTriNap = GiaTriNap;
        this.ThoiGian = ThoiGian;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDTaiKhoan() {
        return IDTaiKhoan;
    }

    public void setIDTaiKhoan(String IDTaiKhoan) {
        this.IDTaiKhoan = IDTaiKhoan;
    }

    public int getGiaTriNap() {
        return GiaTriNap;
    }

    public void setGiaTriNap(int GiaTriNap) {
        this.GiaTriNap = GiaTriNap;
    }

    public Date getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(Date ThoiGian) {
        this.ThoiGian = ThoiGian;
    }
}
