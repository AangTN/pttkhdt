package DTO;
public class DTOChuot extends ThietBi{
    private int tocDoChuot;  // Tốc độ chuột
    public DTOChuot(String idChuot, String tenChuot, int tocDoChuot, String tinhTrang, String idMay, String hinhAnh, String moTa) {
        super(idChuot, tenChuot, tinhTrang, idMay, hinhAnh, moTa);
        setTocDoChuot(tocDoChuot);
    }
    public int getTocDoChuot() {
        return tocDoChuot;
    }
    public void setTocDoChuot(int tocDoChuot) {
        this.tocDoChuot = tocDoChuot;
    }
    public String kiemTraDuLieu() {
        // Kiểm tra IDChuot
        // Kiểm tra TenChuot
        if (ten == null || ten.trim().isEmpty()) {
            return "Tên chuột không được để trống.";
        }
        if (ten.length() > 30) {
            return "Tên chuột không được vượt quá 30 ký tự.";
        }
    
        // Kiểm tra TocDoChuot
        if (tocDoChuot <= 0) {
            return "Tốc độ chuột phải lớn hơn 0.";
        }
    
        // Kiểm tra TinhTrang
        if (tinhTrang == null || tinhTrang.trim().isEmpty()) {
            return "Tình trạng không được để trống.";
        }

        // Kiểm tra IDMay
        if (idMay == null || idMay.trim().isEmpty()) {
            idMay = "NULL";
        }
        // Kiểm tra HinhAnh
        if (hinhAnh == null || hinhAnh.trim().isEmpty()) {
            hinhAnh = "image/Chuot/ChuotMacDinh.jpg";
        }
        if (!hinhAnh.trim().endsWith(".png") && !hinhAnh.trim().endsWith(".jpg")) {
            return "Hình ảnh phải có định dạng .png hoặc .jpg.";
        }
    
        // Kiểm tra MoTa
        if (moTa == null || moTa.trim().isEmpty()) {
            moTa = "NULL";
        }
        if (moTa.length() > 100) {
            return "Mô tả phải < 100 ký tự";
        }
        // Nếu tất cả các kiểm tra đều hợp lệ
        return "Hợp lệ";
    }    
}
