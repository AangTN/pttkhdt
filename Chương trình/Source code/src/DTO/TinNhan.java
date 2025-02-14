package DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TinNhan {
    private String id;
    private String idMay;
    private String noiDung;
    private LocalDateTime thoiGian;
    private String trangThai;
    private String idTaiKhoan;
    private String tenTaiKhoan;

    public TinNhan(String id, String idMay,String idTaiKhoan,String tenTaiKhoan, String noiDung, LocalDateTime thoiGian, String trangThai) {
        this.id = id.trim();
        this.idMay = idMay.trim();
        this.idTaiKhoan = idTaiKhoan.trim();
        this.tenTaiKhoan = tenTaiKhoan.trim();
        this.noiDung = noiDung.trim();
        this.thoiGian = thoiGian;
        this.trangThai = trangThai.trim();
    }
    public String getIdTaiKhoan() {
        return idTaiKhoan;
    }
    public void setIdTaiKhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }
    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }
    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMay() {
        return idMay;
    }

    public void setIdMay(String idMay) {
        this.idMay = idMay;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung.trim();
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "["+thoiGian.format(formatter)+"] "+tenTaiKhoan+": "+noiDung;
    }
}
