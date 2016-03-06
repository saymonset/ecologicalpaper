/*
 * ServicioClientesBean.java
 *
 * Created on June 22, 2007, 9:54 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.servicio;

import com.entity.Cliente;
import java.sql.DriverManager;
import java.util.ArrayList;
import javax.ejb.Stateless;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.Session;

import com.util.HibernateUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author simon
 */
@Stateless
public class ServicioClientesBean implements com.servicio.ServicioClientesLocal {
//  @PersistenceContext(name="ecologicalpaper_PU")
    @PersistenceContext
    EntityManager em;
    
    public void crearCliente(Cliente cliente) {
        em.persist(cliente);
    }
    
    
    public List<Cliente> listarClientes() {
        List list=null;
        try {
           Query query = em.createQuery("SELECT c FROM com.entity.Cliente AS c");
            list=query.getResultList();
         /*   System.out.print("...........JDBC ......................................")   ;
            Connection con;
              Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
              con = DriverManager.getConnection("jdbc:oracle:thin:@saymon:1521:ORCL","ecological","ecological"); 
              
              PreparedStatement ps;
              ResultSet rs;
            
            String sql ="select * from cliente ";
            ps =con.prepareStatement(sql.toString());
            rs=ps.executeQuery();
            list = new ArrayList();
            if (rs!=null){
                while(rs.next()){
                    System.out.print("...........1 ......................................")   ;
                    
                    String id =rs.getString("id");
                    String nombre =rs.getString("nombre");
                    String direccion =rs.getString("direccion");
                    Long idl = 0l;
                    if (id!=null){
                        System.out.print("...........4 ......................................")   ;
                        idl = Long.valueOf(id);
                    }
                    System.out.print("...........5 ......................................")   ;
                    Cliente cliente = new Cliente(idl,nombre,direccion);
                    System.out.print("...........6 ......................................")   ;
                    list.add(cliente);
                    System.out.print("...........7 ......................................")   ;
                }
            }con.close(); */
//              System.out.print("...........HIBERNATE SAY,MON SET......................................")   ;
            /* Session session = HibernateUtil.getSessionFactory().getCurrentSession();
             session.beginTransaction();
            list = session.createQuery("from com.entity.Cliente").list();
           session.getTransaction().commit();
            HibernateUtil.getSessionFactory().close();*/
            
            
        } catch (Exception e) {
            System.out.print("...........servicio da;ado......................................")   ;
            e.printStackTrace();
            System.out.print("...........fin servicio da;ado......................................")   ;
        }
        return list;
    }
    
    
    
    /** Creates a new instance of ServicioClientesBean */
    public ServicioClientesBean() {
    }
    
    
}
