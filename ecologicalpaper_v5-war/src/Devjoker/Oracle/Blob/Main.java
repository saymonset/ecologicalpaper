package Devjoker.Oracle.Blob;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Blob;

import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleDriver;

/**
 *
 * @author devjoker
 */

public class Main {       
    public static void main(String[] args) {                
        System.out.println("Inicializando programa ...");
        Connection conn = null;
        GestorDeConexiones gc = null;
        try{
            gc = new GestorDeConexiones("app1", "password");
            conn = gc.getConnection();            
            String path = "c:\\javaout";
            RecuperadorBLOB.RecuperarBLOB(conn,"000001",path);
            RecuperadorBLOB.RecuperarBLOB(conn,"000002",path);
            RecuperadorBLOB.RecuperarBLOB(conn,"000003",path);                                            
        }
        catch (SQLException sqle) {
            System.out.println
	    ("Error de acceso a BD:" + sqle.getMessage()); 
            sqle.printStackTrace();
        }
        catch (IOException ioe){
            System.out.println
	    ("Error de acceso a disco:" + ioe.getMessage());
            ioe.printStackTrace();
        }
        
        try{
            if (gc != null && conn != null)
            gc.closeConnection();
        }
        catch (SQLException sqle)
        {
            System.out.println
	    ("Error de acceso a BD:" + sqle.getMessage()); 
            sqle.printStackTrace();
            conn = null;
            gc = null;
        }
        System.out.println("Finalizando programa ...");
    }    
}

class RecuperadorBLOB
{
    public static void RecuperarBLOB
    (Connection cn,  String idBLOB, String path)
    throws SQLException, IOException
    {
        FileOutputStream fos = null;
        Statement st = null;
        ResultSet rs = null;
        String sql ="select CO_ARCHIVO, " +
                    "       NOMBRE_ARCHIVO, " + 
                    "       BIN, " + 
                    "       FX_ALTA " + 
                    "from archivos " + 
                    "WHERE CO_ARCHIVO = '" + idBLOB + "' ";
      
        try{
            st = cn.createStatement();        
            rs = st.executeQuery(sql);
            if (rs.next()) 
            {                
                String pathname= 
                path + "\\" + rs.getString("NOMBRE_ARCHIVO") ;
                File file = new File(pathname);
                fos = new FileOutputStream(file);                    
                Blob bin = rs.getBlob("BIN");
                InputStream inStream = bin.getBinaryStream();
                int size = (int)bin.length();
                byte[] buffer = new byte[size];
                int length = -1;
                while ((length = inStream.read(buffer)) != -1)
                {
                  fos.write(buffer, 0, length);                
                }                                        
            }
        }
        catch (IOException ioe)
        {
            throw new IOException(ioe.getMessage());
        }
        finally 
        {
            if (fos != null)
                fos.close();
            if (rs != null)
                rs.close();
            rs = null;
            st = null;        
        }        
    }
}


class GestorDeConexiones
{
    private String user;
    private String password;
    private Connection conn = null;
    private boolean conectado = false;
 
    public  GestorDeConexiones(String usr, String pwd){
        user = usr;
        password = pwd;
    }
 
    public void closeConnection() throws SQLException{
        if (conectado)
            conn.close();
    }
    
    private void conectar() throws SQLException   {
        
        String url;              
        DriverManager.registerDriver(new OracleDriver()); 
        

       // url = "jdbc:oracle:oci:@<TNS_NAME>";
       // url = "jdbc:oracle:thin:@<server>:<port=1521>:<SID>";
        url = "jdbc:oracle:oci:@ORACLEBD";
        conn = DriverManager.getConnection(url,user, password);
        System.out.println("Conexion correcta"); 
        conectado = true;
        
    }
    
    public Connection getConnection() throws SQLException
    {
        if (!conectado) 
            conectar();
        return conn;
    }        
}       