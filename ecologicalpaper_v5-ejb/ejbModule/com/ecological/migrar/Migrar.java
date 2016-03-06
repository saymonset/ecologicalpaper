/*
 * Migrar.java
 *
 * Created on 1 de octubre de 2007, 07:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.migrar;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;
import org.hibernate.Hibernate;

public class Migrar {

    public static void main(String args[]) {
        // System.out.println("empezando ...");

        try {
            Connection con = null;
            Connection conoracle2 = null;
            Statement sta = null;
            ResultSet res = null;
            // System.out.println("aja.....");
            // Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Class.forName("org.postgresql.Driver").newInstance();
            //  System.out.println("1");
          /*  conoracle2 = DriverManager.getConnection(
            "jdbc:jtds:sqlserver://localhost:1433/ecolofama", "sa",
            "sa");*/
            conoracle2 = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/migrar", "ecological",
                    "ecological");

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://localhost:1433/ecological", "sa",
                    "12760187");
            //System.out.println("2");
            DatabaseMetaData db = con.getMetaData();
            //System.out.println("3");
            //System.out.print("Producto nombre:" + db.getDatabaseProductName()
            //+ "!" + db.getDatabaseProductVersion());
            Statement stmt = con.createStatement();
            // /

            String nombreTablas = "%"; // Listamos todas las tablas
            String tipos[] = new String[1];
            tipos[0] = "TABLE";
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet tablas = dbmd.getTables(null, null, nombreTablas, tipos);
            boolean seguir = tablas.next();
            //  System.out.println("WEmpieza");
            Calendar fecha = Calendar.getInstance();

            while (seguir) {
                //  System.out.println(tablas.getString(tablas    .findColumn("TABLE_NAME")));

                if ("Struct".equalsIgnoreCase(tablas.getString(tablas.findColumn("TABLE_NAME")))) {
                    //crearStruct(tablas, stmt, conoracle2, con); //tercerta corrida
                } else if ("Person".equalsIgnoreCase(tablas.getString(tablas.findColumn("TABLE_NAME")))) {
                //  crearPerson(tablas, stmt,conoracle2,con); //cuarta corrida

                } else if ("TypeDocuments".equalsIgnoreCase(tablas.getString(tablas.findColumn("TABLE_NAME")))) {

               //  crearTiposDodumentos(tablas, stmt,conoracle2,con); //1era corrida

                } else if ("GroupUsers".equalsIgnoreCase(tablas.getString(tablas.findColumn("TABLE_NAME")))) {
                  crearRole(tablas, stmt,conoracle2,con);//2da corrida

                }
                //

                seguir = tablas.next();

            }
        } catch (Exception e) {
            System.out.println("e.toString()=" + e.toString());
        }
    }

    public static void crearRole(ResultSet tablas, Statement stmt, Connection conoracle2, Connection con) {
        String query;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            StringBuffer sql2 = new StringBuffer("");

            query = "select * from " + tablas.getString(tablas.findColumn("TABLE_NAME"));
            query += " where accountActive=1";
            rs = stmt.executeQuery(query);
            ps = null;

            String codigoArea = "";



            while (rs.next()) {



                StringBuffer sql = new StringBuffer();
                sql.append("insert into Role ");
                sql.append("(codigo,NOMBRE,status,DESCRIPCION");
                sql.append("  ) ");
                sql.append(" values (?,?,?,?)");
                ps = conoracle2.prepareStatement(sql.toString());

                // secuenciador
                sql2 = new StringBuffer("");
                sql2.append("select GEN_KEY,GEN_VALUE from SEQUENCE_ID_GEN where GEN_KEY='Role_ID'");
           
                PreparedStatement ps2 = conoracle2.prepareStatement(sql2.toString());
                ResultSet rsSeq = ps2.executeQuery();
                rsSeq.next();
                int id = rsSeq.getInt("GEN_VALUE");


                sql2 = new StringBuffer("");
                sql2.append("update SEQUENCE_ID_GEN set GEN_VALUE=?");
                sql2.append(" where gen_key='Role_ID'");
                ps2 = conoracle2.prepareStatement(sql2.toString());
                int idbd = id + 1;
                ps2.setInt(1, new Integer(idbd));
                ps2.execute();

                ps.setInt(1, id);
                ps.setString(2, rs.getString("nombreGrupo"));
                ps.setBoolean(3, true);
                ps.setString(4, rs.getString("descripcionGrupo"));
                ps.execute();




            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.toString();
        }
    }

    public static void crearTiposDodumentos(ResultSet tablas, Statement stmt, Connection conoracle2, Connection con) {
        String query;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            StringBuffer sql2 = new StringBuffer("");

            query = "select * from " + tablas.getString(tablas.findColumn("TABLE_NAME"));
            //query +=" where accountActive=1";
            rs = stmt.executeQuery(query);
            ps = null;

            String codigoArea = "";



            while (rs.next()) {



                StringBuffer sql = new StringBuffer();
                sql.append("insert into DOC_TIPO ");
                sql.append("(codigo,NOMBRE,status,DESCRIPCION");
                sql.append("  ) ");
                sql.append(" values (?,?,?,?)");
                ps = conoracle2.prepareStatement(sql.toString());

                // secuenciador
                sql2 = new StringBuffer("");
                sql2.append("select GEN_KEY,GEN_VALUE from SEQUENCE_ID_GEN where GEN_KEY='DOC_TIPO_ID'");
            
                PreparedStatement ps2 = conoracle2.prepareStatement(sql2.toString());
                ResultSet rsSeq = ps2.executeQuery();
                rsSeq.next();
                int id = rsSeq.getInt("GEN_VALUE");


                sql2 = new StringBuffer("");
                sql2.append("update SEQUENCE_ID_GEN set GEN_VALUE=?");
                sql2.append(" where gen_key='DOC_TIPO_ID'");
                ps2 = conoracle2.prepareStatement(sql2.toString());
                int idbd = id + 1;
                ps2.setInt(1, new Integer(idbd));
                ps2.execute();

                ps.setInt(1, id);
                ps.setString(2, rs.getString("TypeDoc"));
                ps.setBoolean(3, true);
                ps.setString(4, rs.getString("TypeDoc"));
                ps.execute();




            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.toString();
        }
    }

    public static void crearPerson(ResultSet tablas, Statement stmt, Connection conoracle2, Connection con) {
        String query;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            StringBuffer sql2 = new StringBuffer("");

            query = "select * from " + tablas.getString(tablas.findColumn("TABLE_NAME"));
            query += " where accountActive=1";
            rs = stmt.executeQuery(query);
            ps = null;
            //ps= con.prepareStatement(query.toString());
            //rs=ps.executeQuery();
            String codigoArea = "";

            //_______________________________________________________
            //obtenemos la raiz
            sql2 = new StringBuffer("");
            sql2.append("select * from tree where tiponodo=-1");
            ps = conoracle2.prepareStatement(sql2.toString());
            ResultSet rsNomEcoloTree = ps.executeQuery();
            rsNomEcoloTree.next();
            int empresa = Integer.parseInt(rsNomEcoloTree.getString("nodo"));

            int area = 0;

            //_______________________________________________________


            while (rs.next()) {


                area = obtenerArea(tablas, stmt, conoracle2, con, rs);
                int cargo = obtenerCargo(tablas, stmt, conoracle2, con, rs, area);



                StringBuffer sql = new StringBuffer();
                sql.append("insert into usuario ");
                sql.append("(id,NOMBRE,status,APELLIDO,LOGIN,PASSWORD,MAIL_PRINCIPAL,ULTIMACONEXION,FECHA_CREADO,FECHA_CADUCA");
                sql.append(",DIRECCION,EMPRESA,AREA,CARGO ) ");
                sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps = conoracle2.prepareStatement(sql.toString());

                // secuenciador
                sql2 = new StringBuffer("");
                sql2.append("select GEN_KEY,GEN_VALUE from SEQUENCE_ID_GEN where GEN_KEY='USUARIO_ID'");
             
                PreparedStatement ps2 = conoracle2.prepareStatement(sql2.toString());
                ResultSet rsSeq = ps2.executeQuery();
                rsSeq.next();
                int id = rsSeq.getInt("GEN_VALUE");


                sql2 = new StringBuffer("");
                sql2.append("update SEQUENCE_ID_GEN set GEN_VALUE=?");
                sql2.append(" where gen_key='USUARIO_ID'");
                ps2 = conoracle2.prepareStatement(sql2.toString());
                int idbd = id + 1;
                ps2.setInt(1, new Integer(idbd));
                ps2.execute();

                ps.setInt(1, id);
                ps.setString(2, rs.getString("Nombres"));
                ps.setBoolean(3, true);
                ps.setString(4, rs.getString("Apellidos"));
                ps.setString(5, rs.getString("nameUser"));
                ps.setString(6, rs.getString("clave"));
                ps.setString(7, rs.getString("email"));
                ps.setString(8, null);
                ps.setDate(9, null);
                ps.setDate(10, null);
                ps.setString(11, rs.getString("Direccion"));
                ps.setInt(12, empresa);
               

                ps.setInt(13, area);
                ps.setInt(14, cargo);
                ps.execute();




            }
        } catch (Exception ex) {
           
            ex.toString();
        }
    }

    public static int obtenerCargo(ResultSet tablas, Statement stmt, Connection conoracle2, Connection con, ResultSet rs, int padreaArea) {
        int nodoCargo = 0;

        try {

            StringBuffer sql3 = new StringBuffer("");
            sql3.append("select cargo from tbl_cargo where idcargo=").append(rs.getString("cargo"));
            PreparedStatement psCargo2 = con.prepareStatement(sql3.toString());
            ResultSet rsNomEcoloTree1 = psCargo2.executeQuery();
            rsNomEcoloTree1.next();
            String cargo = rsNomEcoloTree1.getString("cargo");

            StringBuffer sql2 = new StringBuffer("");
            PreparedStatement psCargo = null;
            PreparedStatement ps = null;
            ResultSet rsNomGrupo = null;

            sql2 = new StringBuffer("");
            sql2.append("select * from tree where tiponodo=-1");
            psCargo = conoracle2.prepareStatement(sql2.toString());
            ResultSet rsNomEcoloTree = psCargo.executeQuery();
            rsNomEcoloTree.next();

            StringBuffer sql = new StringBuffer();
            sql.append("insert into tree ");
            sql.append("( nodo,nodopadre,nombre,descripcion,maquina,tiponodo,fecha_creado,prefix,deBaseToUsuario,status)");
            sql.append(" values (?, ?,?,?,?,?,?,?,?,?)");
            ps = conoracle2.prepareStatement(sql.toString());
            //actualizamos el nodo en tree
            sql2 = new StringBuffer("");
            // secuenciador
            sql2 = new StringBuffer("");
            sql2.append("select GEN_KEY,GEN_VALUE from SEQUENCE_ID_GEN where GEN_KEY='NODO_ID'");
            System.out.println(sql2.toString());
            PreparedStatement ps2 = conoracle2.prepareStatement(sql2.toString());
            ResultSet rsSeq = ps2.executeQuery();
            rsSeq.next();
            int nodo = rsSeq.getInt("GEN_VALUE");
            nodoCargo = nodo;

            sql2 = new StringBuffer("");
            sql2.append("update SEQUENCE_ID_GEN set GEN_VALUE=?");
            sql2.append(" where gen_key='NODO_ID'");
            System.out.println("sql2cargo=" + sql2.toString());
            ps2 = conoracle2.prepareStatement(sql2.toString());
            nodo = nodo + 1;
            ps2.setInt(1, new Integer(nodo));
            ps2.execute();
            System.out.println("saymon 11");
            System.out.println("nodoCargo=" + nodoCargo);
            System.out.println("saymon 22");

            ps.setInt(1, nodoCargo);
            ps.setInt(2, padreaArea);
            ps.setString(3, cargo);
            ps.setString(4, cargo);
            ps.setString(5, "");
            ps.setInt(6, 2);
            ps.setDate(7, null);
            ps.setString(8, cargo);
            ps.setInt(9, 1);
            ps.setBoolean(10, true);
            ps.execute();




        } catch (Exception e) {
            e.toString();
        }

        return nodoCargo;
    }

    public static int obtenerArea(ResultSet tablas, Statement stmt, Connection conoracle2, Connection con, ResultSet rs) {
        int nodoArea = 0;

        try {

            StringBuffer sql3 = new StringBuffer("");
            sql3.append("select idarea from tbl_cargo where idcargo=").append(rs.getString("cargo"));
            PreparedStatement psCargo = con.prepareStatement(sql3.toString());
            ResultSet rsNomEcoloTree1 = psCargo.executeQuery();
            rsNomEcoloTree1.next();
            String area = rsNomEcoloTree1.getString("idarea");

            StringBuffer sql2 = new StringBuffer("");
            sql2.append("select area from tbl_area where idarea=").append(area);
            sql2.append(" and activea=1");
            System.out.println("sql2=" + sql2.toString());
            PreparedStatement psgrupo = con.prepareStatement(sql2.toString());
            PreparedStatement ps = null;
            ResultSet rsNomGrupo = psgrupo.executeQuery();
            //SIEMPRE DEBE EXISTIR
            String nombreGrupo = "";

            if (rsNomGrupo.next()) {

                nombreGrupo = rsNomGrupo.getString("area");
            }


            //OBTENEMOS EL AREA
            sql2 = new StringBuffer("");
            sql2.append("select * from tree where tiponodo=1");
            sql2.append(" and tree.nombre='").append(nombreGrupo).append("'");
            psgrupo = conoracle2.prepareStatement(sql2.toString());
            ResultSet rsNomEcoloTree = psgrupo.executeQuery();
            if (rsNomEcoloTree.next()) {

                nodoArea = Integer.parseInt(rsNomEcoloTree.getString("nodo"));
                System.out.println("saymon q1");
                System.out.println("nodoAreaq=" + nodoArea);
                System.out.println("saymon q2");
            } else {

                System.out.println("----------pasa 3------------------");
                StringBuffer sql = new StringBuffer();
                sql.append("insert into tree ");
                sql.append("( nodo,nodopadre,nombre,descripcion,maquina,tiponodo,fecha_creado,prefix,deBaseToUsuario,status)");
                sql.append(" values (?, ?,?,?,?,?,?,?,?,?)");
                ps = conoracle2.prepareStatement(sql.toString());
                //actualizamos el nodo en tree
                sql2 = new StringBuffer("");
                // secuenciador
                sql2 = new StringBuffer("");
                sql2.append("select GEN_KEY,GEN_VALUE from SEQUENCE_ID_GEN where GEN_KEY='NODO_ID'");
                System.out.println(sql2.toString());
                PreparedStatement ps2 = conoracle2.prepareStatement(sql2.toString());
                ResultSet rsSeq = ps2.executeQuery();
                rsSeq.next();
                int nodo = rsSeq.getInt("GEN_VALUE");
                nodoArea = nodo;

                sql2 = new StringBuffer("");
                sql2.append("update SEQUENCE_ID_GEN set GEN_VALUE=?");
                sql2.append(" where gen_key='NODO_ID'");
                System.out.println("sql2Area=" + sql2.toString());
                ps2 = conoracle2.prepareStatement(sql2.toString());
                nodo = nodo + 1;
                ps2.setInt(1, new Integer(nodo));
                ps2.execute();


                System.out.println("saymon 1");
                System.out.println("nodoArea=" + nodoArea);
                System.out.println("saymon 2");
                ps.setInt(1, nodoArea);
                System.out.println("PAPPAPAPAPPAPPAPA");
                //OBTENEMOS LA RAIZ
                sql2 = new StringBuffer("");
                sql2.append("select * from tree where tiponodo=-1");
                psgrupo = conoracle2.prepareStatement(sql2.toString());
                ResultSet rsPadreaRaiz = psgrupo.executeQuery();
                rsPadreaRaiz.next();
                System.out.println("rsPadreaRaiz.getString(nodo)=" + rsPadreaRaiz.getString("nodo"));
                System.out.println("FIN PAPPAPAPAPPAPPAPA");
                ps.setInt(2, Integer.parseInt(rsPadreaRaiz.getString("nodo")));
                ps.setString(3, nombreGrupo);
                ps.setString(4, nombreGrupo);
                ps.setString(5, "");
                ps.setInt(6, 1);
                ps.setDate(7, null);
                ps.setString(8, nombreGrupo);
                ps.setInt(9, 1);
                ps.setBoolean(10, true);
                ps.execute();


            }

        } catch (Exception e) {
            System.out.println("e=" + e.toString());
            e.toString();
        }
        System.out.println("nodoArea=" + nodoArea);
        return nodoArea;
    }

    public static void crearStruct(ResultSet tablas, Statement stmt, Connection conoracle2, Connection con) {
        String query;
        ResultSet rsStruct = null;
        PreparedStatement ps = null;
        try {


            query = "select * from " + tablas.getString(tablas.findColumn("TABLE_NAME"));
            rsStruct = stmt.executeQuery(query);
            ps = null;
            while (rsStruct.next()) {
                StringBuffer sql = new StringBuffer();

                //actualizamos el nodo en tree
                StringBuffer sql2 = new StringBuffer("");



                sql2 = new StringBuffer("");
                long nodobuscar = rsStruct.getString("IdNode") != null ? Long.parseLong(rsStruct.getString("IdNode")) : 0;

                System.out.println("nodobuscar11=" + nodobuscar);

                sql2.append("select nodo from tree where nodo=?");
                System.out.println("sql2.toString()=" + sql2.toString());
                PreparedStatement ps4 = conoracle2.prepareStatement(sql2.toString());
                ps4.setLong(1, nodobuscar);
                //  PreparedStatement  ps2 =null;
                ResultSet rsSeq11 = null;
                rsSeq11 = ps4.executeQuery();

                while (rsSeq11.next()) {
                    System.out.println("-------------2----------------------");
                    sql2 = new StringBuffer("");
                    sql2.append("update SEQUENCE_ID_GEN set GEN_VALUE=?");
                    sql2.append(" where gen_key='NODO_ID'");
                    System.out.println("-------------3----------------------");
                    PreparedStatement ps5 = conoracle2.prepareStatement(sql2.toString());
                    System.out.println("-------------4----------------------");
                    nodobuscar += 1;

                    long nodo = nodobuscar + 1;
                    System.out.println("nodo=" + nodo);
                    ps5.setLong(1, new Long(nodo));
                    System.out.println("-------------5----------------------");
                    ps5.execute();
                    System.out.println("-------------6----------------------");
                    sql2 = new StringBuffer("");
                    sql2.append("select nodo from tree where nodo=").append(nodobuscar);
                    ps5 = conoracle2.prepareStatement(sql2.toString());
                    rsSeq11 = ps5.executeQuery();
                    System.out.println("nodobuscar22=" + nodobuscar);
                }



                sql = new StringBuffer();
                sql.append("insert into tree ");
                sql.append("( nodo,nodopadre,nombre,descripcion,maquina,tiponodo,fecha_creado,prefix,deBaseToUsuario,status)");
                sql.append(" values (?, ?,?,?,?,?,?,?,?,?)");
                ps = conoracle2.prepareStatement(sql.toString());

                ps.setLong(1, nodobuscar);
                ps.setLong(2, Long.parseLong(rsStruct.getString("IdNodeParent")));
                ps.setString(3, rsStruct.getString("Name"));
                ps.setString(4, rsStruct.getString("Description"));
                ps.setString(5, "");
                int idr = Integer.parseInt(rsStruct.getString("NodeType"));
                switch (idr) {
                    case 0:
                        ps.setInt(6, -1);
                        break;
                    case 1:
                        ps.setInt(6, 0);
                        break;
                    case 2:
                        ps.setInt(6, 3);
                        break;
                    case 3:
                        ps.setInt(6, 4);
                        //call
                        break;
                    default:
                        break;
                }
                ps.setDate(7, null);
                ps.setString(8, rsStruct.getString("Name"));
                ps.setBoolean(9, true);
                ps.setBoolean(10, true);
                ps.execute();



               // crearDoc(nodobuscar, con, conoracle2);




         

            }
        } catch (Exception e) {

            System.out.println("e333=" + e);
        }
    }

    //public static void crearDoc(int padre, Connection con, Connection  conoracle2) {
    public static void crearDoc(long padre, Connection con, Connection conoracle2) {
        try {
            StringBuffer sql2 = new StringBuffer("");
            PreparedStatement ps2 = null;
            ResultSet rsSeq = null;

            long nodobd = 0;



            sql2 = new StringBuffer("");
            StringBuffer sql = new StringBuffer("");
            sql.append("select * from Documents where idnode=").append(padre);
            PreparedStatement ps = con.prepareStatement(sql.toString());
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {



                sql2 = new StringBuffer("");
                sql2.append("select GEN_KEY,GEN_VALUE from SEQUENCE_ID_GEN where GEN_KEY='NODO_ID'");
                System.out.println(sql2.toString());
                ps2 = conoracle2.prepareStatement(sql2.toString());
                rsSeq = ps2.executeQuery();
                rsSeq.next();
                long nodo = rsSeq.getLong("GEN_VALUE");
                nodo += 1;
                nodobd = nodo + 1;


                sql2 = new StringBuffer("");
                sql2.append("update SEQUENCE_ID_GEN set GEN_VALUE=?");
                sql2.append(" where gen_key='NODO_ID'");
                ps2 = conoracle2.prepareStatement(sql2.toString());
                nodo = nodo + 1;
                ps2.setLong(1, new Long(nodo));
                ps2.execute();







                sql2 = new StringBuffer("");
                long nodobuscar = nodobd;
                sql2.append("select nodo from tree where nodo=").append(nodobuscar);
                ps2 = conoracle2.prepareStatement(sql2.toString());
                ResultSet rsSeq1 = ps2.executeQuery();

                // insertamos documento en tree

                sql = new StringBuffer();
                sql.append("insert into tree ");
                sql.append("( nodo,nodopadre,nombre,descripcion,maquina,tiponodo,fecha_creado,prefix,deBaseToUsuario,status)");
                sql.append(" values (?, ?,?,?,?,?,?,?,?,?)");
                ps = conoracle2.prepareStatement(sql.toString());
                System.out.println("----------------INSERTA----EN DOCUMENTOS-----------------------------");
                System.out.println("nodobd=" + nodobd);
                System.out.println("-------------------------------------------------");
                ps.setLong(1, nodobd);
                ps.setLong(2, padre);
                ps.setString(3, rs.getString("nameDocument"));
                ps.setString(4, rs.getString("comments"));
                ps.setString(5, "");
                int tipoDocumento = Integer.parseInt("5");
                ps.setInt(6, tipoDocumento);
                ps.setDate(7, null);
                ps.setString(8, rs.getString("prefix"));
                ps.setInt(9, 1);
                ps.setBoolean(10, true);
                ps.execute();





                // CREAMOS EL MAESTRO

                // secuenciador
                sql = new StringBuffer("");

                sql.append("select gen_key,GEN_VALUE  from SEQUENCE_ID_GEN where gen_key='NODO_ID'");
                ps = conoracle2.prepareStatement(sql.toString());
                rsSeq = ps.executeQuery();
                rsSeq.next();
                int idmaestro = Integer.parseInt(rsSeq.getString("GEN_VALUE"));
                int idmaestrbd = idmaestro;

                sql = new StringBuffer("");
                sql.append("update SEQUENCE_ID_GEN set GEN_VALUE =").append((++idmaestro));
                sql.append("  where gen_key='NODO_ID'");
                ps = conoracle2.prepareStatement(sql.toString());
                ps.execute();




                sql = new StringBuffer();
                sql.append("insert into DOC_MAESTRO ");
                sql.append("( codigo,nombre,consecutivo,busquedakeys,publico,USUARIO_CREADOR,TREE,DOC_TIPO,status,numRegistrosHechos ) ");
                sql.append(" values (?, ?,?,?,?,?,?,?,?,?)");
                ps = conoracle2.prepareStatement(sql.toString());

                ps.setInt(1, idmaestrbd);
                ps.setString(2, rs.getString("nameDocument"));
                ps.setString(3, rs.getString("number"));
                ps.setString(4, rs.getString("keys"));
                int l = rs.getString("docPublic") != null ? Integer.parseInt(rs.getString("docPublic")) : 0;
                ps.setInt(5, l);
                int root = 1;
                //PILAS CON EL SUUARIO, SIEMPRE DA ERROR
                ps.setInt(6, root);
                ps.setLong(7, nodobd);

                int registro = 2;
                StringBuffer sql3 = new StringBuffer("");
                sql3.append("select TypeDoc from TypeDocuments where idTypeDoc=").append(rs.getString("type"));
                PreparedStatement psCargo = con.prepareStatement(sql3.toString());
                ResultSet rsNomEcoloTree1 = psCargo.executeQuery();
                rsNomEcoloTree1.next();
                String TypeDoc = rsNomEcoloTree1.getString("TypeDoc");
                if (TypeDoc != null && TypeDoc != "") {

                    sql3 = new StringBuffer("");
                    sql3.append("select codigo from DOC_TIPO where nombre='").append(TypeDoc).append("'");
                    psCargo = conoracle2.prepareStatement(sql3.toString());
                    rsNomEcoloTree1 = psCargo.executeQuery();
                    rsNomEcoloTree1.next();
                    TypeDoc = rsNomEcoloTree1.getString("codigo");
                    ps.setInt(8, Integer.parseInt(TypeDoc));
                } else {

                    ps.setInt(8, registro);
                }




                ps.setInt(9, 1);
                ps.setInt(10, 0);

                ps.execute();


                // CREAMOS EL DETALLE
                sql = new StringBuffer("");
                sql.append("select * from VersionDoc where numDoc=").append(
                        rs.getString("numGen"));
                ps = con.prepareStatement(sql.toString());
                ResultSet rsDetalle = ps.executeQuery();
                while (rsDetalle.next()) {
                    // secuenciador
                    sql = new StringBuffer("");

                    sql.append("select gen_key,GEN_VALUE  from SEQUENCE_ID_GEN where gen_key='DOC_DETALLE_ID'");
                    ps = conoracle2.prepareStatement(sql.toString());
                    rsSeq = ps.executeQuery();
                    rsSeq.next();
                    int iddetalle = Integer.parseInt(rsSeq.getString("GEN_VALUE"));
                    int iddetallebd = iddetalle;


                    sql = new StringBuffer("");
                    sql.append("update SEQUENCE_ID_GEN set GEN_VALUE=").append((++iddetalle));
                    sql.append(" where gen_key='DOC_DETALLE_ID'");
                    ps = conoracle2.prepareStatement(sql.toString());
                    ps.execute();

                    sql = new StringBuffer();
                    sql.append("insert into DOC_DETALLE ");
                    sql.append("( codigo,mayorVer,minorVer,DATECAMBIO,nameFile,DESCRIPCION");
                    sql.append(",size_doc,doc_checkout,contextType,DOC_MAESTRO,MODIFICADOPOR,DUENIO,DOC_ESTADO,status,data_doc)");
                    sql.append(" values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps = conoracle2.prepareStatement(sql.toString());
                    ps.setInt(1, iddetallebd);
                    ps.setString(2, rsDetalle.getString("MayorVer"));
                    ps.setString(3, rsDetalle.getString("MinorVer"));
                    ps.setDate(4, null);
                    ps.setString(5, rs.getString("nameFile"));
                    ps.setString(6, rsDetalle.getString("comments"));


                    ps.setInt(7, 0);
                    ps.setInt(8, 0);
                    ps.setString(9, rs.getString("contextType"));
                    ps.setInt(10, idmaestrbd);
                    ps.setInt(11, root);
                    ps.setInt(12, root);
                    int borrador = 2;
                    ps.setInt(13, borrador);
                    ps.setBoolean(14, true);
                    Blob blob=null;
                    try {

                        blob = Hibernate.createBlob(rsDetalle.getBinaryStream("data"));
                       
                        ps.setBlob(15, blob);
                    } catch (Exception e) {
                        System.out.println("e1111=" + e);

                    }
                    ps.execute();
                    //INGRESAMOS LA DATA QUE VA EN POSTGRES
                    /*sql = new StringBuffer();
                    sql.append("insert into doc_datapostgres ");
                    sql.append("( codigo,data_doc_postgres,doc_detalle,status");
                    sql.append(" ) ");
                    sql.append(" values (?,?,?,?)");
                    ps = conoracle2.prepareStatement(sql.toString());
                        ps.setInt(1, iddetallebd);
                    //   ps.s*/
                //_____________________________________________________


                }

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("e2222=" + e);
        }

    }

    public void crearDetalle(int doc) {

    }
}
