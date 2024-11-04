package BLL;
import DAO.DAODangNhap;
import DAO.DAOQuanLy;
import DTO.DTOBanPhim;
import DTO.DTOChiTietNguyenLieu;
import DTO.DTOChuot;
import DTO.DTOMonAn;
import DTO.DTONapTien;
import DTO.DTONguoiDung;
import DTO.DTONguyenLieu;
import DTO.ThanhPhanMonAn;

import java.util.ArrayList;
import java.util.Vector;
public class BLLNet {
    DAODangNhap daoDangNhap;
    DAOQuanLy daoQuanLy;
    public BLLNet() {
        daoDangNhap = new DAODangNhap();
        daoQuanLy = new DAOQuanLy();
    }
    public String kiemTraDangNhap(String username, String pass) {
        return daoDangNhap.kiemTraDangNhap(username, pass);
    }
    public ArrayList<String> layDanhSachQuyen(String IDTaiKhoan) {
        return daoQuanLy.layDanhSachQuyen(IDTaiKhoan);
    }
    public String layTenNhanVien(String IDTaiKhoan) {
        return daoQuanLy.layTenNhanVien(IDTaiKhoan);
    }
    public ArrayList<DTONguoiDung> layDanhSachTaiKhoan() {
        return daoQuanLy.layDanhSachTaiKhoan();
    }
    public Vector<String> layDanhSachQuyen() {
        return daoQuanLy.layDanhSachQuyen();
    }
    public String taoTaiKhoanVaNguoiDung(DTONguoiDung thongtin) {
        String maTaiKhoan = daoQuanLy.layMaTaiKhoanChuaCo();
        String maNguoiDung = daoQuanLy.layMaNguoiDungChuaTonTai();
        String IDNhomQuyen = daoQuanLy.timIDNhomQuyen(thongtin.getTenNhomQuyen());
        if(daoQuanLy.kiemTraTonTaiTaiKhoan(thongtin.getTenTaiKhoan())) return "Tên tài khoản đã tồn tại";
        thongtin.setIDNhomQuyen(IDNhomQuyen);
        thongtin.setIDTaiKhoan(maTaiKhoan);
        thongtin.setIDNguoiDung(maNguoiDung);
        String s = daoQuanLy.ThemNguoiDung(thongtin);
        if(s.equals("Thành công")) {
            String s1 = daoQuanLy.ThemTaiKhoan(thongtin);
            if(s1.equals("Thành công"))
            return s1;
        }
        return s;
    }
    public String suaTaiKhoanVaNguoiDung(DTONguoiDung thongtin) {
        String IDNhomQuyen = daoQuanLy.timIDNhomQuyen(thongtin.getTenNhomQuyen());
        thongtin.setIDNhomQuyen(IDNhomQuyen);
        String s = daoQuanLy.suaTaiKhoan(thongtin);
        if(s.equals("Thành công")) {
            String s1 = daoQuanLy.suaThongTinNguoiDung(thongtin);
            if(s1.equals("Thành công"))
            return s1;
        }
        return s;
    }
    public ArrayList<DTONguoiDung> timKiemTaiKhoanVaNguoiDung(String noiDungTim, String truong) {
        if(truong.equals("Tên tài khoản")) return daoQuanLy.timKiemTaiKhoanTheoTenTaiKhoan(noiDungTim);
        if(truong.equals("Tên người dùng")) return daoQuanLy.timKiemTaiKhoanTheoHoTen(noiDungTim);
        if(truong.equals("Số điện thoại")) return daoQuanLy.timKiemTaiKhoanTheoSoDienThoai(noiDungTim);
        return daoQuanLy.layDanhSachTaiKhoan();
    }
    public String napTien(DTONapTien nap) {
        String maNap = daoQuanLy.layMaNapTienChuaTonTai();
        nap.setID(maNap);
        if(daoQuanLy.ghiThongTinNap(nap) == true) {
            if(daoQuanLy.themTienVaoTaiKhoan(nap.getIDTaiKhoan(), nap.getGiaTriNap()) == true) return "Thành công";
            return "Thêm tiền vào thất bại";
        }
        return "Ghi thông tin nạp thất bại";
    }
    public ArrayList<DTOChuot> layDanhSachChuot() {
        return daoQuanLy.layDanhSachChuot();
    }
    public ArrayList<DTOBanPhim> layDanhSachBanPhim() {
        return daoQuanLy.layDanhSachBanPhim();
    }
    public ArrayList<DTOChuot> timKiemChuot(String noiDungTim, String loaiTim) {
        if(noiDungTim.equals("")) return daoQuanLy.layDanhSachChuot();
        if(loaiTim.equals("ID chuột")) return daoQuanLy.timKiemChuotTheoIDChuot(noiDungTim);
        if(loaiTim.equals("Tên chuột")) return daoQuanLy.timKiemChuotTheoTen(noiDungTim);
        if(loaiTim.equals("ID máy")) return daoQuanLy.timKiemChuotTheoIDMay(noiDungTim);
        return daoQuanLy.layDanhSachChuot();
    }
    public ArrayList<DTOBanPhim> timKiemBanPhim(String noiDungTim, String loaiTim) {
        if(noiDungTim.equals("")) return daoQuanLy.layDanhSachBanPhim();
        if (loaiTim.equals("ID bàn phím")) return daoQuanLy.timKiemBanPhimTheoIDBanPhim(noiDungTim);
        if (loaiTim.equals("Tên bàn phím")) return daoQuanLy.timKiemBanPhimTheoTen(noiDungTim);
        if (loaiTim.equals("ID máy")) return daoQuanLy.timKiemBanPhimTheoIDMay(noiDungTim);
        return daoQuanLy.layDanhSachBanPhim();
    }
    public String xoaChuot(String IDChuot) {
        return daoQuanLy.xoaChuot(IDChuot);
    }
    public String xoaBanPhim(String IDBanPhim) {
        return daoQuanLy.xoaBanPhim(IDBanPhim);
    }
    public String suaChuot(DTOChuot chuot) {
        return daoQuanLy.suaChuot(chuot);
    }
    public String suaBanPhim(DTOBanPhim banPhim) {
        return daoQuanLy.suaBanPhim(banPhim);
    }
    public String themNChuot(DTOChuot chuot, int soLuong) {
        return daoQuanLy.themNChuot(chuot, soLuong);
    }
    public String themNBanPhim(DTOBanPhim banPhim, int soLuong) {
        return daoQuanLy.themNBanPhim(banPhim, soLuong);
    }
    public ArrayList<DTONguyenLieu> layDanhSachNguyenLieu() {
        return daoQuanLy.layDanhSachNguyenLieu();
    }
    public int laySoLuongNguyenLieuTrongKho(String idNguyenLieu) {
        return daoQuanLy.soLuongNguyenLieuTrongKho(idNguyenLieu);
    }
    public String themNguyenlieu(DTONguyenLieu nguyenLieu) {
        if(daoQuanLy.kiemTraTenNguyenLieuTonTai(nguyenLieu.getTenNguyenLieu()) == true) return "Tên nguyên liệu đã tồn tại";
        nguyenLieu.setIDNguyenLieu(daoQuanLy.layMaNguyenLieuChuaTonTai());
        return daoQuanLy.themNguyenLieu(nguyenLieu);
    }
    public ArrayList<DTONguyenLieu> timKiemNguyenLieu(String tenNguyenLieu) {
        if(tenNguyenLieu.equals("")) return daoQuanLy.layDanhSachNguyenLieu();
        return daoQuanLy.timKiemNguyenLieu(tenNguyenLieu);
    }
    public ArrayList<DTOChiTietNguyenLieu> layThongTinLoNguyenLieu(String IDNguyenLieu) {
        return daoQuanLy.layThongTinLoNguyenLieu(IDNguyenLieu);
    }
    public String NhapNguyenLieu(DTOChiTietNguyenLieu nl) {
        String id = daoQuanLy.timMaLoChuaTonTai();
        return daoQuanLy.NhapNguyenLieu(nl, id);
    }
    public ArrayList<DTOMonAn> layDanhSachMonAn() {
        return daoQuanLy.layDanhSachMonAn();
    }
    public ArrayList<DTOMonAn> timKiemMonAn(String tenMonAn) {
        if(tenMonAn.equals("")) return daoQuanLy.layDanhSachMonAn();
        return daoQuanLy.timKiemMonAn(tenMonAn);
    }
    public ArrayList<ThanhPhanMonAn> layThanhPhanMonAnDeThem() {
        return daoQuanLy.layThanhPhanMonAnDeThem();
    }
    public String themMonAn(DTOMonAn monAn, ArrayList<ThanhPhanMonAn> dsThanhPhan) {
        if(daoQuanLy.kiemTraTenMonAnDaTonTai(monAn.getTenMonAn()) == true) return "Tên món ăn đã tồn tại";
        monAn.setID(daoQuanLy.layMaMonAnChuaTonTai());
        String s = daoQuanLy.themMonAn(monAn);
        if(s.equals("Thành công")) {
            for(int i=0;i<dsThanhPhan.size();i++) {
                String s2 = daoQuanLy.themThanhPhanMonAn(dsThanhPhan.get(i).getIDNguyenLieu(), monAn.getID(), dsThanhPhan.get(i).getSoLuong());
                if(!s2.equals("Thành công")) return "Thêm thành phần thất bại";
            }
            return "Thành công";
        }
        return s;
    }
}
