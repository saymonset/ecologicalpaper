/*
 * ClienteDocumentoMaestro.java
 *
 * Created on July 25, 2007, 11:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.documentos;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_relacionados;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author simon
 */
public class ClienteDocumentoMaestro       implements Serializable {
    private Long codigo;
    private boolean publico;
    private String datecambio;
    private String publicoStr;
    private String nombre;
    private String prefijo;
    private Usuario usuario_creador;
    private String consecutivo;
    private String tipoDocumento;
    private String busquedakeys;
    private Date  fecha_creado;
    private String fecha_mostrar;
    private Doc_tipo doc_tipo;
    private ClienteDocumentoDetalle primerClienteDocumentoDetalle;
    private Collection<Doc_detalle> doc_detalles = new ArrayList<Doc_detalle>();
    private Collection<Doc_relacionados> doc_rel1 = new ArrayList<Doc_relacionados>();
    private Collection<Doc_relacionados> doc_rel2 = new ArrayList<Doc_relacionados>();
    
    private Tree tree;
    private Collection<ClienteDocumentoDetalle> doc_detallesCliente = new ArrayList<ClienteDocumentoDetalle>();
    private ServicioDelegado delegado = new ServicioDelegado();
    private boolean swLocked;
    
    /** Creates a new instance of ClienteDocumentoMaestro */
    public ClienteDocumentoMaestro() {
    }
    public ClienteDocumentoMaestro(Doc_maestro doc_m,ClienteDocumentoDetalle primerClienteDocumentoDetalle,String prefijo) {
        this.publico=doc_m.isPublico();
        this.codigo=doc_m.getCodigo();
        this.nombre=doc_m.getNombre();
        this.usuario_creador=doc_m.getUsuario_creador();
        this.consecutivo=doc_m.getConsecutivo();
        this.busquedakeys=doc_m.getBusquedakeys();
        this.fecha_creado=doc_m.getFecha_creado();
        if (this.fecha_creado!=null){
            this.fecha_mostrar=Utilidades.sdfShow.format(this.fecha_creado);
        }
        
        this.doc_tipo=doc_m.getDoc_tipo();
        this.doc_detalles=doc_m.getDoc_detalles();
        this.doc_rel1=doc_m.getDoc_rel1();
        this.doc_rel2=doc_m.getDoc_rel2();
        this.tree=doc_m.getTree();
        this.primerClienteDocumentoDetalle=primerClienteDocumentoDetalle;
        this.prefijo=prefijo;
        
    }
    public ClienteDocumentoMaestro(String publicoStr,Doc_maestro doc_m,ClienteDocumentoDetalle primerClienteDocumentoDetalle) {
        this.publico=doc_m.isPublico();
        this.publicoStr=publicoStr;
        this.codigo=doc_m.getCodigo();
        this.nombre=doc_m.getNombre();
        this.usuario_creador=doc_m.getUsuario_creador();
        this.consecutivo=doc_m.getConsecutivo();
        this.busquedakeys=doc_m.getBusquedakeys();
        this.fecha_creado=doc_m.getFecha_creado();
        if (this.fecha_creado!=null){
            this.fecha_mostrar=Utilidades.sdfShow.format(this.fecha_creado);
        }
        
        this.doc_tipo=doc_m.getDoc_tipo();
        this.doc_detalles=doc_m.getDoc_detalles();
        this.doc_rel1=doc_m.getDoc_rel1();
        this.doc_rel2=doc_m.getDoc_rel2();
        this.tree=doc_m.getTree();
        this.tree.setPrefix(doc_m.getTree().getPrefix());
        this.primerClienteDocumentoDetalle=primerClienteDocumentoDetalle;
        
    }
    
    public ClienteDocumentoMaestro(Doc_maestro doc_m) {
        this.publico=doc_m.isPublico();
        this.codigo=doc_m.getCodigo();
        this.nombre=doc_m.getNombre();
        this.usuario_creador=doc_m.getUsuario_creador();
        this.consecutivo=doc_m.getConsecutivo();
        this.busquedakeys=doc_m.getBusquedakeys();
        this.fecha_creado=doc_m.getFecha_creado();
          if (this.fecha_creado!=null){
            this.fecha_mostrar=Utilidades.sdfShow.format(this.fecha_creado);
        }
        
        this.doc_tipo=doc_m.getDoc_tipo();
        this.doc_detalles=doc_m.getDoc_detalles();
        this.doc_rel1=doc_m.getDoc_rel1();
        this.doc_rel2=doc_m.getDoc_rel2();
        this.tree=doc_m.getTree();
    }
    
    public String encontraPrefijo(Tree nodo,String prefijo){
        prefijo +="/"+nodo.getNombre();
        List nodosHijosLst =delegado.findAllTreeHijos(nodo.getNodo());
        if (nodosHijosLst!=null){
            Iterator nodosHijosIt = nodosHijosLst.iterator();
            if(nodosHijosIt.hasNext()){
                Tree treeHijo = (Tree) nodosHijosIt.next();
                prefijo=encontraPrefijo(treeHijo, prefijo);
            }
        }
        
        return prefijo;
    }
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Usuario getUsuario_creador() {
        return usuario_creador;
    }
    
    public void setUsuario_creador(Usuario usuario_creador) {
        this.usuario_creador = usuario_creador;
    }
    
    public String getConsecutivo() {
        return consecutivo;
    }
    
    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }
    
    public String getBusquedakeys() {
        return busquedakeys;
    }
    
    public void setBusquedakeys(String busquedakeys) {
        this.busquedakeys = busquedakeys;
    }
    
    public Date getFecha_creado() {
        return fecha_creado;
    }
    
    public void setFecha_creado(Date fecha_creado) {
        this.fecha_creado = fecha_creado;
    }
    
    public Doc_tipo getDoc_tipo() {
        return doc_tipo;
    }
    
    public void setDoc_tipo(Doc_tipo doc_tipo) {
        this.doc_tipo = doc_tipo;
    }
    
    public Collection<Doc_detalle> getDoc_detalles() {
        return doc_detalles;
    }
    
    public void setDoc_detalles(Collection<Doc_detalle> doc_detalles) {
        this.doc_detalles = doc_detalles;
    }
    
    public Collection<Doc_relacionados> getDoc_rel1() {
        return doc_rel1;
    }
    
    public void setDoc_rel1(Collection<Doc_relacionados> doc_rel1) {
        this.doc_rel1 = doc_rel1;
    }
    
    public Collection<Doc_relacionados> getDoc_rel2() {
        return doc_rel2;
    }
    
    public void setDoc_rel2(Collection<Doc_relacionados> doc_rel2) {
        this.doc_rel2 = doc_rel2;
    }
    
    public Tree getTree() {
        return tree;
    }
    
    public void setTree(Tree tree) {
        this.tree = tree;
    }
    
    public Collection<ClienteDocumentoDetalle> getDoc_detallesCliente() {
        return doc_detallesCliente;
    }
    
    public void setDoc_detallesCliente(Collection<ClienteDocumentoDetalle> doc_detallesCliente) {
        this.doc_detallesCliente = doc_detallesCliente;
    }
    
    public ClienteDocumentoDetalle getPrimerClienteDocumentoDetalle() {
        return primerClienteDocumentoDetalle;
    }
    
    public void setPrimerClienteDocumentoDetalle(ClienteDocumentoDetalle primerClienteDocumentoDetalle) {
        this.primerClienteDocumentoDetalle = primerClienteDocumentoDetalle;
    }
    
    public String getPrefijo() {
        return prefijo;
    }
    
    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }
    
    public boolean isPublico() {
        return publico;
    }
    
    public void setPublico(boolean publico) {
        this.publico = publico;
    }
    
    
   


    public String getPublicoStr() {
        return publicoStr;
    }

    public void setPublicoStr(String publicoStr) {
        this.publicoStr = publicoStr;
    }

    public String getFecha_mostrar() {
        return fecha_mostrar;
    }

    public void setFecha_mostrar(String fecha_mostrar) {
        this.fecha_mostrar = fecha_mostrar;
    }
	public String getDatecambio() {
		return datecambio;
	}
	public void setDatecambio(String datecambio) {
		this.datecambio = datecambio;
	}
	public boolean isSwLocked() {
		return swLocked;
	}
	public void setSwLocked(boolean swLocked) {
		this.swLocked = swLocked;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
    
}
