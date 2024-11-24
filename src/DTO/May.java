package DTO;

public class May {
    private String IDMay;
    private String loaiMay;
    private int giaChoi;
    private String IDNguoiSuDung;
    private String CPU;
    private String ram;
    private String GPU;
    private String tinhTrang;
    private String trangThai;
    private String moTa;

    public May(String IDMay, String loaiMay, int giaChoi, String IDNguoiSuDung, 
               String CPU, String ram, String GPU, String tinhTrang, String trangThai, String moTa) {
        this.IDMay = (IDMay != null) ? IDMay.trim() : "";
        this.loaiMay = (loaiMay != null) ? loaiMay.trim() : "";
        this.giaChoi = giaChoi;
        this.IDNguoiSuDung = (IDNguoiSuDung != null) ? IDNguoiSuDung.trim() : "";
        this.CPU = (CPU != null) ? CPU.trim() : "";
        this.ram = (ram != null) ? ram.trim() : "";
        this.GPU = (GPU != null) ? GPU.trim() : "";
        this.tinhTrang = (tinhTrang != null) ? tinhTrang.trim() : "";
        this.trangThai = (trangThai != null) ? trangThai.trim() : "";
        this.moTa = (moTa != null) ? moTa.trim() : "";
    }

    public void setMoTa(String moTa) {
        this.moTa = (moTa != null) ? moTa.trim() : "";
    }

    public String getMoTa() {
        return moTa;
    }

    public String getCPU() {
        return CPU;
    }

    public String getGPU() {
        return GPU;
    }

    public int getGiaChoi() {
        return giaChoi;
    }

    public String getIDMay() {
        return IDMay;
    }

    public String getIDNguoiSuDung() {
        return IDNguoiSuDung;
    }

    public String getLoaiMay() {
        return loaiMay;
    }

    public String getRam() {
        return ram;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setCPU(String CPU) {
        this.CPU = (CPU != null) ? CPU.trim() : "";
    }

    public void setGPU(String GPU) {
        this.GPU = (GPU != null) ? GPU.trim() : "";
    }

    public void setGiaChoi(int giaChoi) {
        this.giaChoi = giaChoi;
    }

    public void setIDMay(String IDMay) {
        this.IDMay = (IDMay != null) ? IDMay.trim() : "";
    }

    public void setIDNguoiSuDung(String IDNguoiSuDung) {
        this.IDNguoiSuDung = (IDNguoiSuDung != null) ? IDNguoiSuDung.trim() : "";
    }

    public void setLoaiMay(String loaiMay) {
        this.loaiMay = (loaiMay != null) ? loaiMay.trim() : "";
    }

    public void setRam(String ram) {
        this.ram = (ram != null) ? ram.trim() : "";
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = (tinhTrang != null) ? tinhTrang.trim() : "";
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = (trangThai != null) ? trangThai.trim() : "";
    }
}
