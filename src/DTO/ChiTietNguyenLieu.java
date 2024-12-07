package DTO;

import java.sql.Date;
import java.util.Calendar;

public class ChiTietNguyenLieu {
    private String IDNguyenLieu;
    private Date ngayNhap;
    private int hanSuDung; // số ngày hạn sử dụng
    private int soLuongNhap;
    private int daSuDung;

    // Hàm khởi tạo đầy đủ tham số
    public ChiTietNguyenLieu(String IDNguyenLieu, Date ngayNhap, int hanSuDung, int soLuongNhap, int daSuDung) {
        this.IDNguyenLieu = IDNguyenLieu;
        this.ngayNhap = ngayNhap;
        this.hanSuDung = hanSuDung;
        this.soLuongNhap = soLuongNhap;
        this.daSuDung = daSuDung;
    }

    // Getter và Setter
    public String getIDNguyenLieu() {
        return IDNguyenLieu;
    }

    public void setIDNguyenLieu(String IDNguyenLieu) {
        this.IDNguyenLieu = IDNguyenLieu;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(int hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getDaSuDung() {
        return daSuDung;
    }

    public void setdaSuDung(int daSuDung) {
        this.daSuDung = daSuDung;
    }

    // Hàm tính ngày hết hạn
    public Date tinhNgayHetHan() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ngayNhap);
        calendar.add(Calendar.DAY_OF_YEAR, hanSuDung); // Thêm số ngày hạn sử dụng vào ngày nhập
        return new Date(calendar.getTimeInMillis()); // Trả về ngày hết hạn
    }
    public int tinhConLai() {
        return soLuongNhap - daSuDung;
    }
    public boolean kiemTraHetHan() {
        Date ngayHetHan = tinhNgayHetHan(); // Lấy ngày hết hạn
        Date today = new Date(System.currentTimeMillis()); // Lấy ngày hiện tại
        return today.after(ngayHetHan); // Trả về true nếu hôm nay sau ngày hết hạn
    }
    public String kiemTraHopLe() {
        // Kiểm tra số lượng nhập
        if (soLuongNhap <= 0) {
            return "Số lượng nhập không được rỗng và phải lớn hơn 0.";
        }
    
        // Kiểm tra hạn sử dụng
        if (hanSuDung <= 0) {
            return "Hạn sử dụng không được rỗng và phải lớn hơn 0.";
        }
    
        // Nếu tất cả đều hợp lệ
        return "Hợp lệ";
    }
}
