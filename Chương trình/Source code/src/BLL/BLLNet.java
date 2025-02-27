package BLL;
import DAO.DAODangNhap;
import DAO.DAOQuanLy;
import DTO.ChiTietHoaDon;
import DTO.BanPhim;
import DTO.ChiTietNguyenLieu;
import DTO.Chuot;
import DTO.MonAn;
import DTO.NapTien;
import DTO.NguoiDung;
import DTO.NguyenLieu;
import DTO.HoaDon;
import DTO.May;
import DTO.ThanhPhanMonAn;
import DTO.TinNhan;

import java.time.LocalDateTime;
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
    public ArrayList<NguoiDung> layDanhSachTaiKhoan() {
        return daoQuanLy.layDanhSachTaiKhoan();
    }
    public Vector<String> layDanhSachQuyen() {
        return daoQuanLy.layDanhSachQuyen();
    }
    public String taoTaiKhoanVaNguoiDung(NguoiDung thongtin) {
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
    public String suaTaiKhoanVaNguoiDung(NguoiDung thongtin) {
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
    public ArrayList<NguoiDung> timKiemTaiKhoanVaNguoiDung(String noiDungTim, String truong) {
        if(truong.equals("Tên tài khoản")) return daoQuanLy.timKiemTaiKhoanTheoTenTaiKhoan(noiDungTim);
        if(truong.equals("Tên người dùng")) return daoQuanLy.timKiemTaiKhoanTheoHoTen(noiDungTim);
        if(truong.equals("Số điện thoại")) return daoQuanLy.timKiemTaiKhoanTheoSoDienThoai(noiDungTim);
        return daoQuanLy.layDanhSachTaiKhoan();
    }
    public String napTien(NapTien nap) {
        String maNap = daoQuanLy.layMaNapTienChuaTonTai();
        nap.setID(maNap);
        if(daoQuanLy.ghiThongTinNap(nap) == true) {
            if(daoQuanLy.themTienVaoTaiKhoan(nap.getIDTaiKhoan(), nap.getGiaTriNap()) == true) return "Thành công";
            return "Thêm tiền vào thất bại";
        }
        return "Ghi thông tin nạp thất bại";
    }
    public ArrayList<Chuot> layDanhSachChuot() {
        return daoQuanLy.layDanhSachChuot();
    }
    public ArrayList<BanPhim> layDanhSachBanPhim() {
        return daoQuanLy.layDanhSachBanPhim();
    }
    public ArrayList<Chuot> timKiemChuot(String noiDungTim, String loaiTim) {
        if(noiDungTim.equals("")) return daoQuanLy.layDanhSachChuot();
        if(loaiTim.equals("ID chuột")) return daoQuanLy.timKiemChuotTheoIDChuot(noiDungTim);
        if(loaiTim.equals("Tên chuột")) return daoQuanLy.timKiemChuotTheoTen(noiDungTim);
        if(loaiTim.equals("ID máy")) return daoQuanLy.timKiemChuotTheoIDMay(noiDungTim);
        return daoQuanLy.layDanhSachChuot();
    }
    public ArrayList<BanPhim> timKiemBanPhim(String noiDungTim, String loaiTim) {
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
    public String suaChuot(Chuot chuot) {
        return daoQuanLy.suaChuot(chuot);
    }
    public String suaBanPhim(BanPhim banPhim) {
        return daoQuanLy.suaBanPhim(banPhim);
    }
    public String themNChuot(Chuot chuot, int soLuong) {
        return daoQuanLy.themNChuot(chuot, soLuong);
    }
    public String themNBanPhim(BanPhim banPhim, int soLuong) {
        return daoQuanLy.themNBanPhim(banPhim, soLuong);
    }
    public ArrayList<NguyenLieu> layDanhSachNguyenLieu() {
        return daoQuanLy.layDanhSachNguyenLieu();
    }
    public int laySoLuongNguyenLieuTrongKho(String idNguyenLieu) {
        return daoQuanLy.soLuongNguyenLieuTrongKho(idNguyenLieu);
    }
    public String themNguyenlieu(NguyenLieu nguyenLieu) {
        if(daoQuanLy.kiemTraTenNguyenLieuTonTai(nguyenLieu.getTenNguyenLieu()) == true) return "Tên nguyên liệu đã tồn tại";
        nguyenLieu.setIDNguyenLieu(daoQuanLy.layMaNguyenLieuChuaTonTai());
        return daoQuanLy.themNguyenLieu(nguyenLieu);
    }
    public ArrayList<NguyenLieu> timKiemNguyenLieu(String tenNguyenLieu) {
        if(tenNguyenLieu.equals("")) return daoQuanLy.layDanhSachNguyenLieu();
        return daoQuanLy.timKiemNguyenLieu(tenNguyenLieu);
    }
    public ArrayList<ChiTietNguyenLieu> layThongTinLoNguyenLieu(String IDNguyenLieu) {
        return daoQuanLy.layThongTinLoNguyenLieu(IDNguyenLieu);
    }
    public String NhapNguyenLieu(ChiTietNguyenLieu nl) {
        String id = daoQuanLy.timMaLoChuaTonTai();
        return daoQuanLy.NhapNguyenLieu(nl, id);
    }
    public ArrayList<MonAn> layDanhSachMonAn() {
        return daoQuanLy.layDanhSachMonAn();
    }
    public ArrayList<MonAn> timKiemMonAn(String tenMonAn) {
        if(tenMonAn.equals("")) return daoQuanLy.layDanhSachMonAn();
        return daoQuanLy.timKiemMonAn(tenMonAn);
    }
    public ArrayList<ThanhPhanMonAn> layThanhPhanMonAnDeThem() {
        return daoQuanLy.layThanhPhanMonAnDeThem();
    }
    public String themMonAn(MonAn monAn, ArrayList<ThanhPhanMonAn> dsThanhPhan) {
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
    public String suaThongTinMonAn(String idMon, String tenMonAn, int Gia, String trangThai, String tenCu, ArrayList<ThanhPhanMonAn> danhSachThanhPhan) {
        String s;
        if(!tenMonAn.equals(tenCu))
            if(daoQuanLy.kiemTraTenMonAnDaTonTai(tenMonAn) == true) return "Tên món ăn đã tồn tại";
        s = daoQuanLy.suaThongTinMonAn(idMon, tenMonAn, Gia, trangThai);
        if(s.equals("Thành công")) {
            daoQuanLy.xoaThanhPhanMonAn(idMon);
            for(int i=0;i<danhSachThanhPhan.size();i++)
            daoQuanLy.themThanhPhanMonAn(danhSachThanhPhan.get(i).getIDNguyenLieu(), idMon, danhSachThanhPhan.get(i).getSoLuong());
        }
        return s;
    }
    public ArrayList<ThanhPhanMonAn> layThanhPhanCuaMonAn(String idMon) {
        return daoQuanLy.layThanhPhanCuaMonAn(idMon);
    }
    public ArrayList<ThanhPhanMonAn> layThanhPhanKhongCoCuaMonAn(String idMon) {
        return daoQuanLy.layThanhPhanKhongCoCuaMonAn(idMon);
    }
    public ArrayList<HoaDon> layDanhSachHoaDonChuaDuyet() {
        return daoQuanLy.layDanhSachHoaDonChuaDuyet();
    }
    public ArrayList<HoaDon> layDanhSachHoaDonDaXuLy() {
        return daoQuanLy.layDanhSachHoaDonDaXuLy();
    }
    public ArrayList<HoaDon> timKiemHoaDonChuaDuyet(String noiDung, String truong) {
        String cauLenh = "";
        if(truong.equals("Tên tài khoản")) cauLenh = "TK.TenTaiKhoan = '"+noiDung+"'";
        else cauLenh = "HD.IDMay = '"+noiDung+"'";
        return daoQuanLy.timKiemHoaDonChuaDuyet(cauLenh);
    }
    public ArrayList<HoaDon> timKiemHoaDonDaXuLy(String noiDung, String truong) {
        String cauLenh = "";
        if(truong.equals("Tên tài khoản")) cauLenh = "TK.TenTaiKhoan = '"+noiDung+"'";
        else cauLenh = "HD.IDMay = '"+noiDung+"'";
        return daoQuanLy.timKiemHoaDonDaXuLy(cauLenh);
    }
    public ArrayList<ChiTietHoaDon> layChiTietHoaDonCua(String IDHoaDon) {
        return daoQuanLy.layChiTietHoaDonCua(IDHoaDon);
    }
    public ArrayList<ThanhPhanMonAn> layTongThanhPhanMonAnCuaHoaDon(String idHoaDon) {
        return daoQuanLy.layTongThanhPhanCuaHoaDon(idHoaDon);
    }
    public String huyDonHang(String idHoaDon) {
        return daoQuanLy.huyDonHang(idHoaDon);
    }
    public String duyetDon(String idDon, ArrayList<String> idNguyenLieu, ArrayList<Integer> soLuongSuDung) {
        for(int i=0;i<idNguyenLieu.size();i++) {
            String s = daoQuanLy.tangSoLuongSuDungNguyenLieu(idNguyenLieu.get(i), soLuongSuDung.get(i));
            if(!s.equals("Thành công")) return "Cập nhật chi tiết nguyên liệu thất bại";
            String s2 = daoQuanLy.capNhatTrangThaiHoaDon(idDon, "Đã duyệt");
            if(!s2.equals("Thành công")) return "Cập nhật trạng thái hóa đơn thất bại";
        }
        return "Thành công";
    }
    public ArrayList<May> layDanhSachMayThuong() {
        ArrayList<May> ds = new ArrayList<>();
        ds = daoQuanLy.layDanhSachMayThuong();
        // for(int i=0;i<ds.size();i++) {
        //     if(!ds.get(i).getTinhTrang().equals("Hư")) {
        //         if(daoQuanLy.kiemTraMayCoChuot(ds.get(i).getIDMay()).equals("Không") || daoQuanLy.kiemTraMayCoBanPhim(ds.get(i).getIDMay()).equals("Không")) {
        //             ds.get(i).setTinhTrang("Thiếu thiết bị");
        //         }
        //         else if(daoQuanLy.kiemTraMayXemChuotCoHu(ds.get(i).getIDMay()).equals("Hư") || daoQuanLy.kiemTraMayXemBanPhimCoHu(ds.get(i).getIDMay()).equals("Hư")) {
        //             ds.get(i).setTinhTrang("Thiết bị hư");
        //         } 
        //     }
        // }
        return ds;
    }
    public String mayCoThieuThietBi(String idMay) {
        if(daoQuanLy.kiemTraMayCoChuot(idMay).equals("Không") || daoQuanLy.kiemTraMayCoBanPhim(idMay).equals("Không")) {
            return "Thiếu thiết bị";
        }
        return "Không";
    }
    public String mayCoHuThietBi(String idMay) {
        if(daoQuanLy.kiemTraMayXemChuotCoHu(idMay).equals("Hư") || daoQuanLy.kiemTraMayXemBanPhimCoHu(idMay).equals("Hư")) {
            return "Thiết bị hư";
        }
        return "Không";
    }
    public boolean kiemTraMayCoTinNhanMoiKhong(String idMay) {
        return daoQuanLy.kiemTraMayCoTinNhanMoiKhong(idMay);
    }
    public ArrayList<May> layDanhSachMayVIP() {
        ArrayList<May> ds = new ArrayList<>();
        ds = daoQuanLy.layDanhSachMayVIP();
        for(int i=0;i<ds.size();i++) {
            if(!ds.get(i).getTinhTrang().equals("Hư")) {
                if(daoQuanLy.kiemTraMayCoChuot(ds.get(i).getIDMay()).equals("Không") || daoQuanLy.kiemTraMayCoBanPhim(ds.get(i).getIDMay()).equals("Không")) {
                    ds.get(i).setTinhTrang("Thiếu thiết bị");
                }
                else if(daoQuanLy.kiemTraMayXemChuotCoHu(ds.get(i).getIDMay()).equals("Hư") || daoQuanLy.kiemTraMayXemBanPhimCoHu(ds.get(i).getIDMay()).equals("Hư")) {
                    ds.get(i).setTinhTrang("Thiết bị hư");
                } 
            }
        }
        return ds;
    }
    public String timTenTaiKhoanCuaID(String id) {
        return daoQuanLy.timTenTaiKhoanCuaID(id);
    }
    public Chuot layChuotCuaMay(String idMay) {
        return daoQuanLy.layChuotCuaMay(idMay);
    }
    public BanPhim layBanPhimCuaMay(String idMay) {
        return daoQuanLy.layBanPhimCuaMay(idMay);
    }
    public String suaGiaGioChoi(String idMay, int giaChoi){
        return daoQuanLy.suaGiaGioChoi(idMay, giaChoi);
    }
    public String tatMay(String idMay) {
        daoQuanLy.xoaTinNhanTuMay(idMay);
        return daoQuanLy.tatMay(idMay);
    }
    public String moMay(String idMay) {
        return daoQuanLy.moMay(idMay);
    }
    public String thaoChuot(String idMay) {
        return daoQuanLy.thaoChuot(idMay);
    }
    public String thaoBanPhim(String idMay) {
        return daoQuanLy.thaoBanPhim(idMay);
    }
    public ArrayList<Chuot> layDanhSachChuotChuaLap() {
        return daoQuanLy.layDanhSachChuotChuaLap();
    }
    public String lapChuot(String idChuot, String idMay) {
        return daoQuanLy.lapChuot(idChuot, idMay);
    }
    public ArrayList<BanPhim> layDanhSachBanPhimChuaLap() {
        return daoQuanLy.layDanhSachBanPhimChuaLap();
    }
    public String lapBanPhim(String idBanPhim, String idMay) {
        return daoQuanLy.lapBanPhim(idBanPhim, idMay);
    }
    public ArrayList<TinNhan> layTinNhanTuMay(String idMay) {
        return daoQuanLy.layTinNhanTuMay(idMay);
    }
    public String guiTinNhan(String idMay, String idTaiKhoan, String noiDung) {
        String id = daoQuanLy.timIDTinNhanChuaCo();
        LocalDateTime currentTime = LocalDateTime.now();
        TinNhan tinNhan = new TinNhan(id, idMay, idTaiKhoan, "", noiDung, currentTime, "Đã xem");
        return daoQuanLy.guiTinNhan(tinNhan);
    }
    public void chinhTrangThaiDaXemTinNhanTuMay(String idMay) {
        daoQuanLy.chinhTrangThaiDaXemTinNhanTuMay(idMay);
    }
    public String capNhatTinhTrangMay(String tinhTrang, String moTa, String idMay) {
        if(moTa.length() > 100) return "Mô tả quả dài";
        return daoQuanLy.capNhatTinhTrangMay(tinhTrang, moTa, idMay);
    }
}
