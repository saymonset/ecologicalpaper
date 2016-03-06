package com.ecological.paper.documentos;

import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class AreaDocumentos
 *
 * @author simon 25 05 2011
 */
@Entity
public class AreaDocumentos implements Serializable {
    
    @TableGenerator(
    name="areaDocumentos_Gen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="areaDocumentos_Gen_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="areaDocumentos_Gen")
    private Long codigo;
    
    private boolean status;
    
    @Column(name="NOMBRE")
    private  String nombre;

    @Column(name="DESCRIPCION", length=4000)
    private String Descripcion;
    
    @Transient 
    private boolean  delete;
    
     

    @ManyToOne
    @JoinColumn(name="empresa")
    private Tree empresa;
    
    @OneToMany(mappedBy = "areaDocumentos")
	private Collection<Doc_detalle> areaDocumentosLstsoloentity = new ArrayList<Doc_detalle>();
	
	
	
	  @OneToMany(mappedBy = "areaDocumentos")
		private Collection<FlowParalelo> flowParaleloLst = new ArrayList<FlowParalelo>();

    
  
    /** Creates a new instance of Profesion */
    public AreaDocumentos() {
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
    
    public String getDescripcion() {
        return Descripcion;
    }
    
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    
    
    
    
       /*public boolean equals(Object o) {
                Departement d = (Departement)o;
                return d.getCode().equals(getCode());
        }*/
    public boolean equals(Object objeto) {
        if  (objeto!=null && objeto instanceof AreaDocumentos)  {
        	AreaDocumentos p =(AreaDocumentos)objeto;
            long k1 =p.getCodigo();
            long k2=this.codigo;
               
            if ((k1-k2)==0){
                return true;
            }else
                return false;
            
        }else
            return false;
        
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    

    /**
     * @return the empresa
     */
    public Tree getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Tree empresa) {
        this.empresa = empresa;
    }

	public Collection<FlowParalelo> getFlowParaleloLst() {
		return flowParaleloLst;
	}

	public void setFlowParaleloLst(Collection<FlowParalelo> flowParaleloLst) {
		this.flowParaleloLst = flowParaleloLst;
	}

	 

	public Collection<Doc_detalle> getAreaDocumentosLstsoloentity() {
		return areaDocumentosLstsoloentity;
	}

	public void setAreaDocumentosLstsoloentity(
			Collection<Doc_detalle> areaDocumentosLstsoloentity) {
		this.areaDocumentosLstsoloentity = areaDocumentosLstsoloentity;
	}

	 
    
}
