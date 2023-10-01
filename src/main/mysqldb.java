
package main;
//package mysqlcon;
import java.sql.*;

public class mysqldb {

    private Connection conexion;
    
    public Connection getConexion() {
	return conexion;
    }    
    
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    } 

    public mysqldb conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url       = "jdbc:mysql://localhost:3306/bussoft";
            String user      = "root";
            String password  = "123456789";

            setConexion(DriverManager.getConnection(url, user, password));
            if(getConexion() != null){
                System.out.println("Conexion Exitosa!");
            }else{
                System.out.println("Conexion Fallida!");                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    
    public boolean ejecutar(String sql) {
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(sql);
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }        return true;
    }
    
    public ResultSet consultar(String sql) {
        ResultSet resultado;
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        
        return resultado;
    }
}
