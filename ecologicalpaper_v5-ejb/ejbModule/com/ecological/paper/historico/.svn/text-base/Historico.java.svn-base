/*
 * Historico.java
 *
 * Created on September 25, 2007, 8:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.historico;

import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class Historico
 *
 * @author simon
 */
@Entity
@Table(name="HISTORICO")
public class Historico implements Serializable {
    
    @TableGenerator(
    name="HistoricoGen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="HISTORICO_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="HistoricoGen")
    private Long codigo;
    
    private boolean status;
    
    @Column(name="FECHA_ACCION")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fecha_accion;
    
    @Transient
    private String fechaAux;
    
    
    @Transient
    private List<Historico> detalles;
    
    @Column(name="COMENTARIOS", length=4000)
    private String comentarios;
    @Transient
    private boolean  comentariosh;
    
    @ManyToOne
    @JoinColumn(name="USUARIO_ACCION")
    private Usuario usuario_accion;
     @Transient
    private boolean usuario_accionh;
    
    @ManyToOne
    @JoinColumn(name="USUARIO_ANTERIOR")
    private Usuario usuario_anterior;
    @Transient
    private boolean usuario_anteriorh;

    
    @ManyToOne
    @JoinColumn(name="USUARIO_NEW")
    private Usuario usuario_new;
    @Transient
    private boolean  usuario_newh;
    
    private long tipo_accion;
    
    @ManyToOne
    @JoinColumn(name="TREE_ORIGEN")
    private Tree tree_origen;
    @Transient
    private boolean  tree_origenh;
    
    @Transient
    private boolean swNodoDocumento;
    @Transient
    private Doc_maestro doc_maestro;
    
    private String maquina;
      @Transient
    private boolean maquinah;
    
    @ManyToOne
    @JoinColumn(name="TREE_ANTERIOR")
    private Tree tree_anterior;
    
    @Transient
    private boolean  tree_anteriorh;;
    
    @ManyToOne
    @JoinColumn(name="TREE_NEW")
    private Tree tree_new;
    @Transient
    private boolean tree_newh;;
    
    
    
    @ManyToOne
    @JoinColumn(name="FLOW")
    private Flow flow;
    @Transient
    private boolean  flowh;;
    
    /** Creates a new instance of Historico */
    public Historico() {
    }
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof Historico)  {
            Historico p =(Historico)objeto;
            /*String k1 =p.getCodigo()+"";
            String k2=this.codigo+"";*/
            //if (k1.trim().equalsIgnoreCase(k2.trim())){
            if ( (p.getCodigo()-this.codigo)==0){
                return true;
            }else
                return false;
            
        }else
            return false;
        
    }
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public java.util.Date getFecha_accion() {
        return fecha_accion;
    }
    
    public void setFecha_accion(java.util.Date fecha_accion) {
        this.fecha_accion = fecha_accion;
    }
    
    public String getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public Usuario getUsuario_accion() {
        return usuario_accion;
    }
    
    public void setUsuario_accion(Usuario usuario_accion) {
        this.usuario_accion = usuario_accion;
    }
    
    public long getTipo_accion() {
        return tipo_accion;
    }
    
    public void setTipo_accion(long tipo_accion) {
        this.tipo_accion = tipo_accion;
    }
    
    public Tree getTree_origen() {
        return tree_origen;
    }
    
    public void setTree_origen(Tree tree_origen) {
        this.tree_origen = tree_origen;
    }
    
    public String getMaquina() {
        return maquina;
    }
    
    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }
    
    public Tree getTree_anterior() {
        return tree_anterior;
    }
    
    public void setTree_anterior(Tree tree_anterior) {
        this.tree_anterior = tree_anterior;
    }
    
    public Tree getTree_new() {
        return tree_new;
    }
    
    public void setTree_new(Tree tree_new) {
        this.tree_new = tree_new;
    }
    
    public Usuario getUsuario_anterior() {
        return usuario_anterior;
    }
    
    public void setUsuario_anterior(Usuario usuario_anterior) {
        this.usuario_anterior = usuario_anterior;
    }
    
    public Usuario getUsuario_new() {
        return usuario_new;
    }
    
    public void setUsuario_new(Usuario usuario_new) {
        this.usuario_new = usuario_new;
    }
    
    public Flow getFlow() {
        return flow;
    }
    
    public void setFlow(Flow flow) {
        this.flow = flow;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getFechaAux() {
        return fechaAux;
    }
    
    public void setFechaAux(String fechaAux) {
        this.fechaAux = fechaAux;
    }
    
    public List<Historico> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<Historico> detalles) {
        this.detalles = detalles;
    }

    public boolean isComentariosh() {
        return comentariosh;
    }

    public void setComentariosh(boolean comentariosh) {
        this.comentariosh = comentariosh;
    }

    public boolean isUsuario_anteriorh() {
        return usuario_anteriorh;
    }

    public void setUsuario_anteriorh(boolean usuario_anteriorh) {
        this.usuario_anteriorh = usuario_anteriorh;
    }

    public boolean isUsuario_newh() {
        return usuario_newh;
    }

    public void setUsuario_newh(boolean usuario_newh) {
        this.usuario_newh = usuario_newh;
    }

    public boolean isTree_origenh() {
        return tree_origenh;
    }

    public void setTree_origenh(boolean tree_origenh) {
        this.tree_origenh = tree_origenh;
    }

    public boolean isMaquinah() {
        return maquinah;
    }

    public void setMaquinah(boolean maquinah) {
        this.maquinah = maquinah;
    }

    public boolean isTree_anteriorh() {
        return tree_anteriorh;
    }

    public void setTree_anteriorh(boolean tree_anteriorh) {
        this.tree_anteriorh = tree_anteriorh;
    }

    public boolean isTree_newh() {
        return tree_newh;
    }

    public void setTree_newh(boolean tree_newh) {
        this.tree_newh = tree_newh;
    }

    public boolean isFlowh() {
        return flowh;
    }

    public void setFlowh(boolean flowh) {
        this.flowh = flowh;
    }

    public boolean isUsuario_accionh() {
        return usuario_accionh;
    }

    public void setUsuario_accionh(boolean usuario_accionh) {
        this.usuario_accionh = usuario_accionh;
    }
	public boolean isSwNodoDocumento() {
		return swNodoDocumento;
	}
	public void setSwNodoDocumento(boolean swNodoDocumento) {
		this.swNodoDocumento = swNodoDocumento;
	}
	public Doc_maestro getDoc_maestro() {
		return doc_maestro;
	}
	public void setDoc_maestro(Doc_maestro docMaestro) {
		doc_maestro = docMaestro;
	}

   
   
}
