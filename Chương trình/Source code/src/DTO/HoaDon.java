package DTO;

import java.sql.Date;

public class HoaDon {
    private String IDHoaDon;
    private String IDTaiKhoan;
    private Date thoiGian;
    private String trangThai;
    private String IDMay;
    private String tenTaiKhoan;
    private int donGia;
    public HoaDon(String IDHoaDon, String IDTaiKhoan, Date thoiGian, String trangThai, String IDMay, String tenTaiKhoan, int donGia) {
        setIDHoaDon(IDHoaDon.trim());
        setIDMay(IDMay.trim());
        setIDTaiKhoan(IDTaiKhoan.trim());
        setTenTaiKhoan(tenTaiKhoan.trim());
        setThoiGian(thoiGian);
        setTrangThai(trangThai.trim());
        setDonGia(donGia);
    }
    public void setDonGia(int giaTien) {
        this.donGia = giaTien;
    }
    public int getDonGia() {
        return donGia;
    }
    public String getIDHoaDon() {
        return IDHoaDon;
    }
    public String getIDMay() {
        return IDMay;
    }
    public String getIDTaiKhoan() {
        return IDTaiKhoan;
    }
    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }
    public Date getThoiGian() {
        return thoiGian;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setIDHoaDon(String iDHoaDon) {
        IDHoaDon = iDHoaDon;
    }
    public void setIDMay(String iDMay) {
        IDMay = iDMay;
    }
    public void setIDTaiKhoan(String iDTaiKhoan) {
        IDTaiKhoan = iDTaiKhoan;
    }
    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }
    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
