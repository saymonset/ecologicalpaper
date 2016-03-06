package com.ecological.paper.documentos;

import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.flows.FlowsHistorico;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entity class SolicitudImpPart
 *
 * @author simon 2011 03 27
 */
@Entity
@Table(name = "SolicitudImpPart")
public class SolicitudImpPart  implements Serializable {

    @TableGenerator(name = "SolicitudImpPart_Gen", table = "SEQUENCE_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "SolicitudImpPart_ID", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SolicitudImpPart_Gen")
    private Long codigo;
    @ManyToOne
    @JoinColumn(name = "solicitudimpresion")
    private Solicitudimpresion solicitudimpresion;
    
    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;
    
    @ManyToOne
	@JoinColumn(name = "estado")
	private Doc_estado estado;
    private boolean status;
    
	@Transient
	private boolean cancelar;
    
	public Solicitudimpresion getSolicitudimpresion() {
		return solicitudimpresion;
	}
	public void setSolicitudimpresion(Solicitudimpresion solicitudimpresion) {
		this.solicitudimpresion = solicitudimpresion;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Doc_estado getEstado() {
		return estado;
	}
	public void setEstado(Doc_estado estado) {
		this.estado = estado;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public boolean isCancelar() {
		return cancelar;
	}
	public void setCancelar(boolean cancelar) {
		this.cancelar = cancelar;
	}
    
    /*
     * tabla solicitudImpParts
           
      status
     * */
}
