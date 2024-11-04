package DAO;
import java.sql.*;
public class DAODangNhap {
    private Connection con;
    private String dbUrl ="jdbc:sqlserver://localhost:1433;databaseName=pttkhdt;encrypt=true;trustServerCertificate=true;";
    private String userName = "sa"; String password= "123456";
    public DAODangNhap(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }catch(Exception e){
            System.out.println(e);   
        }
    }
    public String kiemTraDangNhap(String username, String pass){
        String truyVan = "SELECT * FROM TaiKhoan WHERE TenTaiKhoan = ?";
        try {
            con = DriverManager.getConnection(dbUrl, userName, password);
            PreparedStatement pStatement = con.prepareStatement(truyVan);
            pStatement.setString(1, username);
            ResultSet rs = pStatement.executeQuery();
            if(rs.next()){
                if(rs.getString("MatKhau").trim().equals(pass)) return "Thành công:"+ rs.getString("IDTaiKhoan");
                else return "Sai mật khẩu";
            }
            else return "Tài khoản không tồn tại";
        } catch (SQLException e) {
            System.out.println(e);
            return "Loi data";
        }
    }
}
