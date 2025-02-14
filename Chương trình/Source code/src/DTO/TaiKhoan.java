package DTO;

public class TaiKhoan {
    private String IDTaiKhoan;
    private String TenTaiKhoan;
    private String MatKhau;
    private int SoDu;
    private String IDNhomQuyen;
    private String TenNhomQuyen;
    public TaiKhoan(String IDTaiKhoan, String TenTaiKhoan, String MatKhau, int soDu, String IDNhomQuyen, String TenNhomQuyen) {
        setIDNhomQuyen(IDNhomQuyen.trim());
        setIDTaiKhoan(IDTaiKhoan.trim());
        setMatKhau(MatKhau.trim());
        setSoDu(soDu);
        setTenTaiKhoan(TenTaiKhoan.trim());
        setTenNhomQuyen(TenNhomQuyen.trim());
    }
    public String getTenNhomQuyen() {
        return TenNhomQuyen;
    }
    public void setTenNhomQuyen(String tenNhomQuyen) {
        TenNhomQuyen = tenNhomQuyen;
    }
    public void setIDNhomQuyen(String iDNhomQuyen) {
        IDNhomQuyen = iDNhomQuyen;
    }
    public String getIDNhomQuyen() {
        return IDNhomQuyen;
    }
    public void setIDTaiKhoan(String iDTaiKhoan) {
        IDTaiKhoan = iDTaiKhoan;
    }
    public String getIDTaiKhoan() {
        return IDTaiKhoan;
    }
    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
    public String getMatKhau() {
        return MatKhau;
    }
    public void setSoDu(int soDu) {
        SoDu = soDu;
    }
    public int getSoDu() {
        return SoDu;
    }
    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }
    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }
}
