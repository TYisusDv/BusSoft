package modelo;

import java.sql.*;

//import main.mysqldb;

public class modelo {
    private Connection conexion;
    String us_id = null;
    String us_usuario = null;
    String tu_tipo = null;
    String vt_id = null;
    String tu_id = null;
    
    public Connection getConexion() {
	return conexion;
    }    
    
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    } 

    public modelo conectar() {
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
    
    public boolean consultarUsuario(String usuario, String clave) throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT * FROM us_usuarios WHERE us_usuario = ? AND us_clave = ?");
        p.setString(1, usuario);
        p.setString(2, clave);
        
        ResultSet resultados = p.executeQuery();
        if(!resultados.next()){
            return false;
        }else{
            this.us_id = String.valueOf(resultados.getString("us_id"));
            return true;        
        }        
    }
    
    public boolean obtenerUsuario() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT us_usuarios.*, tu_tiposusuario.tu_tipo FROM us_usuarios "
                + "INNER JOIN tu_tiposusuario ON tu_tiposusuario.tu_id = us_usuarios.tu_id "
                + "WHERE us_usuarios.us_id = ?");
        p.setString(1, this.us_id);
            
        ResultSet resultados = p.executeQuery();
        if(!resultados.next()){
            return false;
        }else{
            this.us_usuario = String.valueOf(resultados.getString("us_usuario"));
            this.tu_tipo = String.valueOf(resultados.getString("tu_tipo"));
            this.tu_id = String.valueOf(resultados.getString("tu_id"));
            return true;        
        }        
    } 
    
    public String obtenerDatosUsuario(String opcion) throws SQLException {
        obtenerUsuario();
        return switch (opcion) {
            case "us_usuario" -> this.us_usuario;
            case "tu_tipo" -> this.tu_tipo;
            case "tu_id" -> this.tu_id;
            case "vt_id" -> this.vt_id;
            default -> "N/A";
        };
    }
    
    public ResultSet obtenerMarcas() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT * FROM ma_marcas");
            
        ResultSet resultados = p.executeQuery();
        return resultados;    
    } 
    
    public ResultSet obtenerProductos() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT pr_productos.*, ma_marcas.ma_nombre FROM pr_productos "
                + "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id");
            
        ResultSet resultados = p.executeQuery();
        return resultados;    
    } 
     
    public ResultSet obtenerProductosCarrito() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT pr_productos.*, ma_marcas.ma_nombre FROM pr_productos "
                + "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id "
            + " WHERE pr_productos.pr_visible = 1");
            
        ResultSet resultados = p.executeQuery();
        return resultados;    
    } 
    
    public ResultSet buscarProductos(String txt) throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT pr_productos.*, ma_marcas.ma_nombre FROM pr_productos "
                + "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id "
                + "WHERE pr_productos.pr_codigoBarras LIKE ? OR pr_productos.pr_id = ? OR pr_productos.pr_nombre LIKE ?");
        
        p.setString(1, "%" + txt + "%");
        p.setString(2, txt);
        p.setString(3, "%" + txt + "%");
        
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public ResultSet buscarProductosCarrito(String txt) throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT pr_productos.*, ma_marcas.ma_nombre FROM pr_productos "
                + "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id "
                + "WHERE pr_productos.pr_codigoBarras LIKE ? OR pr_productos.pr_id = ? OR pr_productos.pr_nombre LIKE ? AND pr_productos.pr_visible = 1");
        
        p.setString(1, "%" + txt + "%");
        p.setString(2, txt);
        p.setString(3, "%" + txt + "%");
        
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public ResultSet buscarMarca(String txt) throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT ma_marcas.* FROM ma_marcas "
                + "WHERE ma_marcas.ma_id = ? OR ma_marcas.ma_nombre LIKE ?");
        
        p.setString(1, txt);
        p.setString(2, "%" + txt + "%");
        
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public ResultSet obtenerProducto(String codigo) throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT pr_productos.*, ma_marcas.ma_nombre FROM pr_productos "
                + "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id "
                + "WHERE pr_productos.pr_codigoBarras = ?");
        
        p.setString(1, codigo);
        
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public ResultSet obtenerMarca(String codigo) throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT ma_marcas.* FROM ma_marcas "
                + "WHERE ma_marcas.ma_id = ?");
        
        p.setString(1, codigo);
        
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public boolean insertarProducto(String codigoProducto, String nombreProducto, String precioProducto, String costoProducto, int posMarca) throws SQLException{
        ResultSet marcas = obtenerMarcas();
        int i = 0;
        String ma_id = null;
        while (marcas.next()){    
            if(i == (posMarca - 1)){
                ma_id = String.valueOf(marcas.getString("ma_id"));
                break;
            }
            i++;
        }
        
        try{
            Float.parseFloat(precioProducto);
            Float.parseFloat(costoProducto);
        } catch (Exception e){
            return false;
        }
        
        PreparedStatement p = getConexion().prepareStatement("INSERT INTO pr_productos (pr_codigoBarras,pr_nombre,pr_precio,pr_costo,ma_id) VALUES (?,?,?,?,?)");
        p.setString(1, codigoProducto);
        p.setString(2, nombreProducto);
        p.setString(3, precioProducto);
        p.setString(4, costoProducto);
        p.setString(5, ma_id);
        p.executeUpdate();
        
        return true;
    } 
    
    public boolean insertarMarca(String nombreMarca) throws SQLException{       
        PreparedStatement p = getConexion().prepareStatement("INSERT INTO ma_marcas (ma_nombre) VALUES (?)");
        p.setString(1, nombreMarca);
        p.executeUpdate();
        
        return true;
    } 
    
     public boolean modificarMarca(String idMarca, String nombreMarca) throws SQLException{       
        PreparedStatement p = getConexion().prepareStatement("UPDATE ma_marcas SET ma_nombre = ? WHERE ma_id = ?");
        p.setString(1, nombreMarca);
        p.setString(2, idMarca);
        p.executeUpdate();
        
        return true;
    } 
    
    
    public boolean modificarProducto(String idProducto, String codigoProducto, String nombreProducto, String precioProducto, String costoProducto, String marca, boolean visibleProducto) throws SQLException{
        ResultSet marcas = obtenerMarcas();
        String ma_id = null;
        
        while (marcas.next()){    
            if(marca.equals(String.valueOf(marcas.getString("ma_id")) + ". " + String.valueOf(marcas.getString("ma_nombre")))){
                ma_id = String.valueOf(marcas.getString("ma_id"));
                break;
            }
        }
        
        try{
            Float.parseFloat(precioProducto);
            Float.parseFloat(costoProducto);
        } catch (Exception e){
            return false;
        }
        
        PreparedStatement p = getConexion().prepareStatement("UPDATE pr_productos SET pr_codigoBarras = ?, pr_nombre = ?, pr_precio = ?, pr_costo = ?, ma_id = ?, pr_visible = ? WHERE pr_id = ?");
        p.setString(1, codigoProducto);
        p.setString(2, nombreProducto);
        p.setString(3, precioProducto);
        p.setString(4, costoProducto);
        p.setString(5, ma_id);
        p.setBoolean(6, visibleProducto);
        p.setString(7, idProducto);
        
        p.executeUpdate();
        
        return true;
    } 
    
    public ResultSet obtenerTiposUsuario() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT tu_tiposusuario.* FROM tu_tiposusuario WHERE tu_tiposusuario.tu_id >= 1010");
                
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public boolean insertarUsuario(String nombrePersona, String appPersona, String apmPersona, String fechaNacPersona, String nombreUsuario, String emailUsuario, String claveUsuario, String tipoUsuario) throws SQLException{       
        String idTipoUsuario = null;
        String idPersona = null;
        
        ResultSet tiposUsaurio = obtenerTiposUsuario();
        while (tiposUsaurio.next()){    
            if(tipoUsuario.equals(String.valueOf(tiposUsaurio.getString("tu_id")) + ". " + String.valueOf(tiposUsaurio.getString("tu_tipo")))){
                idTipoUsuario = String.valueOf(tiposUsaurio.getString("tu_id"));
                break;
            }
        }
        
        PreparedStatement p = getConexion().prepareStatement("INSERT INTO dp_datospersona (dp_nombres,dp_apellidoPaterno,dp_apellidoMaterno,dp_fechaNacimiento) VALUES (?,?,?,?)");
        p.setString(1, nombrePersona);
        p.setString(2, appPersona);
        p.setString(3, apmPersona);
        p.setString(4, fechaNacPersona);
        
        p.executeUpdate();
        
        PreparedStatement per = getConexion().prepareStatement("SELECT MAX(dp_id) AS dp_id FROM dp_datospersona WHERE dp_nombres = ? AND dp_apellidoPaterno = ? AND dp_apellidoMaterno = ?");                
        per.setString(1, nombrePersona);
        per.setString(2, appPersona);
        per.setString(3, apmPersona);
        
        ResultSet personas = per.executeQuery();
        
        if(personas.next()){    
            idPersona = String.valueOf(personas.getString("dp_id"));
        }
        
        PreparedStatement u = getConexion().prepareStatement("INSERT INTO us_usuarios (us_usuario,us_email,us_clave,dp_id,tu_id) VALUES (?,?,?,?,?)");
        u.setString(1, nombreUsuario);
        u.setString(2, emailUsuario);
        u.setString(3, claveUsuario);
        u.setString(4, idPersona);
        u.setString(5, idTipoUsuario);
        
        u.executeUpdate();
        
        return true;
    } 
    
    
    public ResultSet obtenerUsuarios() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT us_usuarios.*, tu_tiposusuario.tu_tipo FROM us_usuarios " +
            "INNER JOIN tu_tiposusuario ON tu_tiposusuario.tu_id = us_usuarios.tu_id"
        );
                
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public ResultSet obtenerPersona(String id) throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT * FROM dp_datospersona WHERE dp_id = ?");
        p.setString(1, id);
            
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public ResultSet buscarUsuario(String txt) throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT us_usuarios.*, tu_tiposusuario.tu_tipo FROM us_usuarios " +
            "INNER JOIN tu_tiposusuario ON tu_tiposusuario.tu_id = us_usuarios.tu_id " +
            "WHERE us_usuarios.us_id = ? OR us_usuarios.us_usuario LIKE ? OR us_usuarios.us_email LIKE ?"
        );
        
        p.setString(1, txt);
        p.setString(2, "%" + txt + "%");
        p.setString(3, "%" + txt + "%");
        
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public boolean modificarUsuario(String nombrePersona, String appPersona, String apmPersona, String fechaNacPersona, String nombreUsuario, String emailUsuario, String claveUsuario, String tipoUsuario, String idUsuario, String idPersona) throws SQLException{       
        String idTipoUsuario = null;
        
        ResultSet tiposUsaurio = obtenerTiposUsuario();
        while (tiposUsaurio.next()){    
            if(tipoUsuario.equals(String.valueOf(tiposUsaurio.getString("tu_id")) + ". " + String.valueOf(tiposUsaurio.getString("tu_tipo")))){
                idTipoUsuario = String.valueOf(tiposUsaurio.getString("tu_id"));
                break;
            }
        }
        
        PreparedStatement p = getConexion().prepareStatement("UPDATE dp_datospersona SET dp_nombres = ?, dp_apellidoPaterno = ? ,dp_apellidoMaterno = ?, dp_fechaNacimiento = ? WHERE dp_id = ?");
        p.setString(1, nombrePersona);
        p.setString(2, appPersona);
        p.setString(3, apmPersona);
        p.setString(4, fechaNacPersona);
        p.setString(5, idPersona);
        
        p.executeUpdate();
        
        PreparedStatement u = getConexion().prepareStatement("UPDATE us_usuarios SET us_usuario = ?, us_email = ?, us_clave = ?, tu_id = ? WHERE us_id = ?");
        u.setString(1, nombreUsuario);
        u.setString(2, emailUsuario);
        u.setString(3, claveUsuario);
        u.setString(4, idTipoUsuario);
        u.setString(5, idUsuario);
        
        u.executeUpdate();
        
        return true;
    } 
    
    public boolean insertarVenta() throws SQLException{
        PreparedStatement c = getConexion().prepareStatement("SELECT * FROM vt_ventas WHERE us_id_caja = ? AND vt_completed = 0");
        c.setString(1, this.us_id);
        
        ResultSet resultados = c.executeQuery();   
        while(resultados.next()){
            this.vt_id = String.valueOf(resultados.getString("vt_id"));
            return false;  
        }
        
        PreparedStatement p = getConexion().prepareStatement("INSERT INTO vt_ventas (us_id_caja) VALUES (?)");
        p.setString(1, this.us_id);
        
        p.executeUpdate();
        insertarVenta();
        return true;    
    }
    
    public ResultSet obtenerCarrito() throws SQLException{
        insertarVenta();
        PreparedStatement p = getConexion().prepareStatement("SELECT cr_carrito.*, pr_productos.pr_codigoBarras, pr_productos.pr_nombre, pr_productos.pr_precio, pr_productos.pr_costo, ma_marcas.ma_nombre FROM cr_carrito " +
            "INNER JOIN pr_productos ON pr_productos.pr_id = cr_carrito.pr_id " + 
            "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id " + 
            "WHERE cr_carrito.vt_id = ?"
        );
        
        p.setString(1, this.vt_id);
        
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public ResultSet obtenerUnCarrito(String ventaID) throws SQLException{
        insertarVenta();
        PreparedStatement p = getConexion().prepareStatement("SELECT cr_carrito.*, pr_productos.pr_codigoBarras, pr_productos.pr_nombre, pr_productos.pr_precio, pr_productos.pr_costo, ma_marcas.ma_nombre FROM cr_carrito " +
            "INNER JOIN pr_productos ON pr_productos.pr_id = cr_carrito.pr_id " + 
            "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id " + 
            "WHERE cr_carrito.vt_id = ?"
        );
        
        p.setString(1, ventaID);
        
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public ResultSet obtenerProdCarrito(String carrID) throws SQLException{
        insertarVenta();
        PreparedStatement p = getConexion().prepareStatement("SELECT cr_carrito.*, pr_productos.pr_codigoBarras, pr_productos.pr_nombre, pr_productos.pr_precio, pr_productos.pr_costo, ma_marcas.ma_nombre FROM cr_carrito " +
            "INNER JOIN pr_productos ON pr_productos.pr_id = cr_carrito.pr_id " + 
            "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id " + 
            "WHERE cr_carrito.vt_id = ? AND cr_carrito.cr_id = ?"
        );
        
        p.setString(1, this.vt_id);
        p.setString(2, carrID);
        
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
    public boolean actualizarCantidad(String CarritoID, String Cantidad) throws SQLException{
        try{
            Float.parseFloat(Cantidad);
        } catch (Exception e){
            return false;
        }
        
        PreparedStatement p = getConexion().prepareStatement("UPDATE cr_carrito SET cr_cantidad = ? WHERE cr_id = ?");
        p.setString(1, Cantidad);
        p.setString(2, CarritoID);
        
        p.executeUpdate();
        return true;    
    }
    
    public boolean eliminarCarrito(String CarritoID) throws SQLException{      
        PreparedStatement p = getConexion().prepareStatement("DELETE FROM cr_carrito WHERE cr_id = ?");
        p.setString(1, CarritoID);
        
        p.executeUpdate();
        return true;    
    }
    
    public boolean insertarCarrito(String codigoProducto) throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT * FROM pr_productos "
                + "WHERE pr_codigoBarras = ?");        
        p.setString(1, codigoProducto);
        
        ResultSet resultados = p.executeQuery();        
        if(resultados.next()){
            PreparedStatement cs = getConexion().prepareStatement("SELECT * FROM cr_carrito "
            + "WHERE pr_id = ? AND vt_id = ?");

            cs.setString(1, String.valueOf(resultados.getString("pr_id")));
            cs.setString(2, this.vt_id);

            ResultSet res = cs.executeQuery();
            
            if(res.next()){
                PreparedStatement c = getConexion().prepareStatement("UPDATE cr_carrito SET cr_cantidad = cr_cantidad + 1 WHERE cr_id = ?");
                c.setString(1, String.valueOf(res.getString("cr_id")));
                c.executeUpdate();
            }else{
                PreparedStatement c = getConexion().prepareStatement("INSERT INTO cr_carrito(cr_cantidad,cr_precio,cr_costo,pr_id,vt_id) VALUES ('1',?,?,?,?)");
                c.setString(1, String.valueOf(resultados.getString("pr_precio")));
                c.setString(2, String.valueOf(resultados.getString("pr_costo")));
                c.setString(3, String.valueOf(resultados.getString("pr_id")));
                c.setString(4, this.vt_id);
                c.executeUpdate();
            }     
        }
        
        return true;    
    }
    
    public String obtenerCantidad() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT SUM(cr_cantidad) AS cantidad FROM cr_carrito " +
            "INNER JOIN pr_productos ON pr_productos.pr_id = cr_carrito.pr_id " + 
            "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id " + 
            "WHERE cr_carrito.vt_id = ?"
        );
        
        p.setString(1, this.vt_id);
        
        ResultSet resultados = p.executeQuery();
        if(resultados.next()){
            return resultados.getString("cantidad");   
        } else {
           return "0";
        }
    }
    
    public String obtenerTotal() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT SUM(cr_cantidad * cr_precio) AS precio FROM cr_carrito " +
            "INNER JOIN pr_productos ON pr_productos.pr_id = cr_carrito.pr_id " + 
            "INNER JOIN ma_marcas ON ma_marcas.ma_id = pr_productos.ma_id " + 
            "WHERE cr_carrito.vt_id = ?"
        );
        
        p.setString(1, this.vt_id);
        
        ResultSet resultados = p.executeQuery();
        if(resultados.next()){
            return resultados.getString("precio");   
        } else {
           return "0";
        }
    }
    
    public ResultSet obtenerMetodos() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT * FROM mp_metodos_pago");
            
        ResultSet resultados = p.executeQuery();
        return resultados;    
    }
    
     public boolean actualizarVenta(String total, String metodo) throws SQLException{  
        String id_metodo = null;
        
        PreparedStatement pa = getConexion().prepareStatement("SELECT * FROM mp_metodos_pago");        
        
        ResultSet resultados = pa.executeQuery();     
        while(resultados.next()){
            if(metodo.equals(String.valueOf(resultados.getString("mp_id")) + ". " + String.valueOf(resultados.getString("mp_name")))){
                id_metodo = String.valueOf(resultados.getString("mp_id"));
                break;
            }
        }
        
        if(id_metodo.isBlank() || id_metodo.isEmpty()){
            return false;
        }
        
        PreparedStatement p = getConexion().prepareStatement("UPDATE vt_ventas SET vt_completed = 1, vt_total = ?, mp_id = ? WHERE vt_id = ?");
        p.setString(1, total);
        p.setString(2, id_metodo);
        p.setString(3, this.vt_id);
        
        p.executeUpdate();
        return true;    
    }
     
    public String obtenerCantidadUsuarios() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT COUNT(*) AS cantidad FROM us_usuarios");
               
        ResultSet resultados = p.executeQuery();
        
        if(resultados.next()){
            return resultados.getString("cantidad");   
        } else {
           return "0";
        }
    }
    
    public String obtenerCantidadVentas() throws SQLException{
        PreparedStatement p = getConexion().prepareStatement("SELECT COUNT(*) AS cantidad FROM vt_ventas WHERE vt_completed = 1");
               
        ResultSet resultados = p.executeQuery();
        
        if(resultados.next()){
            return resultados.getString("cantidad");   
        } else {
           return "0";
        }
    }
    
    public float obtenerGanancias() throws SQLException{
        float total = 0;
        PreparedStatement p = getConexion().prepareStatement("SELECT * FROM vt_ventas WHERE vt_completed = 1");
               
        ResultSet resultados = p.executeQuery();
        
        while(resultados.next()){
            PreparedStatement pa = getConexion().prepareStatement("SELECT * FROM cr_carrito WHERE vt_id = ?"); 
            pa.setString(1, resultados.getString("vt_id"));
            ResultSet r = pa.executeQuery();

            while(r.next()){
                total = total + ((r.getFloat("cr_precio") * r.getFloat("cr_cantidad")) - (r.getFloat("cr_costo") * r.getFloat("cr_cantidad")));
            } 
        }
        
        return total;
    }
}
