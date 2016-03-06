package com.ecological.paper.documentos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.usuario.Usuario;
 

@Entity
public class RolesOnlyViewDocPublicados implements Serializable {
    @TableGenerator(
    	    name="RolPublicado",
    	            table="SEQUENCE_ID_GEN",
    	            pkColumnName="GEN_KEY",
    	            valueColumnName="GEN_VALUE",
    	            pkColumnValue="RolPublicado_ID",
    	            allocationSize=1)
    	            @Id
    	    @GeneratedValue(strategy=GenerationType.TABLE, generator="RolPublicado")
    	    
    	    private Long codigo;
    	    
    	    @ManyToOne
    	    @JoinColumn(name="Role")
    	    private Role role;
    	    
    	    @ManyToOne
    	    @JoinColumn(name="doc_detalle")
    	    private Doc_detalle doc_detalle;
    	    @Transient
    	    private Usuario usuario;

    	    
    	   
    	    
    	    
    	    public Long getCodigo() {
    	        return codigo;
    	    }

    	    public void setCodigo(Long codigo) {
    	        this.codigo = codigo;
    	    }

    	    public Role getRole() {
    	        return role;
    	    }

    	    public void setRole(Role role) {
    	        this.role = role;
    	    }

			public Doc_detalle getDoc_detalle() {
				return doc_detalle;
			}

			public void setDoc_detalle(Doc_detalle doc_detalle) {
				this.doc_detalle = doc_detalle;
			}

			public Usuario getUsuario() {
				return usuario;
			}

			public void setUsuario(Usuario usuario) {
				this.usuario = usuario;
			}

			 
    	   
    	    
    	}
