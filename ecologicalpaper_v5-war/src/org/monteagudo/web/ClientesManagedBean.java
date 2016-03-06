package org.monteagudo.web;

import com.entity.Cliente;
import com.servicio.ServicioClientesLocal;
import com.util.HibernateUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.List;
 
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;
import org.hibernate.Session;
/**
 *
 * @author jlm
 */
public class ClientesManagedBean {
    
    private Cliente cliente = new Cliente();
    private List<Cliente>  clientes;
    
    //@EJB
    ServicioClientesLocal servicioClientes;
    Context ctx =null;
    public ClientesManagedBean(){
        try {
            
            
            
            
            ctx=new InitialContext();
            Enumeration ce = ctx.list("");
              while(ce.hasMoreElements()){
                NameClassPair c = (NameClassPair)ce.nextElement();
                System.out.print("Class name="+c.getClassName());
                System.out.print("n.getName()="+c.getName());
            }
            
            servicioClientes=(ServicioClientesLocal) ctx.lookup("ecologicalpaper/ServicioClientesBean/local");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void setClientes(List<Cliente> c) {
        
        
        this.clientes=c;
    }
    public List<Cliente> getClientes() {
       
       // clientes=null;
        try {
         /*    System.out.print("...............JDBC...............");  
            Connection con;
              Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
              con = DriverManager.getConnection("jdbc:oracle:thin:@saymon:1521:ORCL","ecological","ecological"); 
              
              PreparedStatement ps;
              ResultSet rs;
            
            String sql ="select * from cliente ";
            ps =con.prepareStatement(sql.toString());
            rs=ps.executeQuery();
            if (rs!=null){
                while(rs.next()){
                    String id =rs.getString("id");
                    String nombre =rs.getString("nombre");
                    String direccion =rs.getString("direccion");
                    Long idl = Long.valueOf(id);
                    //Cliente cliente = new Cliente(idl,nombre,direccion);
                    Cliente cliente = new Cliente(1L,"CCC","DDDD");
                    clientes.add(cliente);
                }
            }con.close();
            System.out.print("...............FIN...............");  */
         /*   System.out.print("...............1...............");
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            clientes = session.createQuery("from com.entity.Cliente").list();
            session.getTransaction().commit();
            HibernateUtil.getSessionFactory().close();      
            System.out.print("..................fin haybernate............");*/
            if (servicioClientes==null){
                servicioClientes=(ServicioClientesLocal) ctx.lookup("ecologicalpaper/ServicioClientesBean/local");
               
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
      
            //return result;
         clientes=servicioClientes.listarClientes();
        return clientes;
    }
    
    public String crearCliente() {
        servicioClientes.crearCliente(cliente);
        return "success";
    }
    
    
    
}