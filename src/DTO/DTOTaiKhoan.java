package DTO;

public class DTOTaiKhoan {
    private String IDTaiKhoan;
    private String TenTaiKhoan;
    private String MatKhau;
    private int SoDu;
    private String IDNhomQuyen;
    public DTOTaiKhoan(String IDTaiKhoan, String TenTaiKhoan, String MatKhau, int soDu, String IDNhomQuyen) {
        setIDNhomQuyen(IDNhomQuyen);
        setIDTaiKhoan(IDTaiKhoan);
        setMatKhau(MatKhau);
        setSoDu(soDu);
        setTenTaiKhoan(TenTaiKhoan);
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
