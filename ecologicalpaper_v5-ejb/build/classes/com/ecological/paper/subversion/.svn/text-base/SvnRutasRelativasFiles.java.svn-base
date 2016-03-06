package com.ecological.paper.subversion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.flows.Flow_ParticipantesAttachment;
import com.ecological.paper.tree.Tree;

@Entity
@Table(name="svnrutasrelativasfiles")
public class SvnRutasRelativasFiles implements Serializable {
    @TableGenerator(
    	    name="svnrutasrelativasfiles_id_gen",
    	            table="SEQUENCE_ID_GEN",
    	            pkColumnName="GEN_KEY",
    	            valueColumnName="GEN_VALUE",
    	            pkColumnValue="svnrutasrelativasfiles_id",
    	            allocationSize=1)
    	            
    	            @Id
    	    @GeneratedValue(strategy=GenerationType.TABLE, generator="svnrutasrelativasfiles_id_gen")
    	    private Long codigo;
            private boolean status;
         
            private String urlRepositorio;
            private String urlArchivoRelativo;
            private String archivo;
            @ManyToOne
            @JoinColumn(name="doc_maestro")
            private Doc_maestro doc_maestro;
            
            @ManyToOne
            @JoinColumn(name="flow_ParticipantesAttachment")
            private Flow_ParticipantesAttachment flow_ParticipantesAttachment;
            
            private String urlsubversionUpload;
            private Long revision;
            
            
            
            
			public Long getCodigo() {
				return codigo;
			}
			public void setCodigo(Long codigo) {
				this.codigo = codigo;
			}
			public boolean isStatus() {
				return status;
			}
			public void setStatus(boolean status) {
				this.status = status;
			}
			public String getUrlRepositorio() {
				return urlRepositorio;
			}
			public void setUrlRepositorio(String urlRepositorio) {
				this.urlRepositorio = urlRepositorio;
			}
			public String getUrlArchivoRelativo() {
				return urlArchivoRelativo;
			}
			public void setUrlArchivoRelativo(String urlArchivoRelativo) {
				this.urlArchivoRelativo = urlArchivoRelativo;
			}
			public String getArchivo() {
				return archivo;
			}
			public void setArchivo(String archivo) {
				this.archivo = archivo;
			}
			public Doc_maestro getDoc_maestro() {
				return doc_maestro;
			}
			public void setDoc_maestro(Doc_maestro doc_maestro) {
				this.doc_maestro = doc_maestro;
			}
			public Flow_ParticipantesAttachment getFlow_ParticipantesAttachment() {
				return flow_ParticipantesAttachment;
			}
			public void setFlow_ParticipantesAttachment(
					Flow_ParticipantesAttachment flow_ParticipantesAttachment) {
				this.flow_ParticipantesAttachment = flow_ParticipantesAttachment;
			}
			public String getUrlsubversionUpload() {
				return urlsubversionUpload;
			}
			public void setUrlsubversionUpload(String urlsubversionUpload) {
				this.urlsubversionUpload = urlsubversionUpload;
			}
			public Long getRevision() {
				return revision;
			}
			public void setRevision(Long revision) {
				this.revision = revision;
			}
		
		 
}
