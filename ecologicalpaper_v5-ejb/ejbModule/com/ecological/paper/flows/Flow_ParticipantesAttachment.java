/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecological.paper.flows;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import com.ecological.paper.subversion.SvnRutasRelativasFiles;

/**
 * 
 * @author simon
 */
@Entity
public class Flow_ParticipantesAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    @TableGenerator(name = "Flow_PartAttachment_Gen",
    table = "SEQUENCE_ID_GEN",
    pkColumnName = "GEN_KEY",
    valueColumnName = "GEN_VALUE",
    pkColumnValue = "Flow_PartAttachment_ID",
    allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Flow_PartAttachment_Gen")
    private Long codigo;
    private String nameFile;
    private String contentType;
    //si es oiracle o sql server    
    private Blob attachmentDoc;
      private byte[] attachmentDocPostgres;
    private double size_doc;
    private boolean swSVN;
    private boolean swSVNUpload;
    private String urlsubversionUpload;
    private boolean status;
    // si es postgresl
  
    @ManyToOne
    @JoinColumn(name = "flowParticipantes")
    private Flow_Participantes flowParticipantes;
    
    @OneToMany(mappedBy = "flow_ParticipantesAttachment")
    private Collection<SvnRutasRelativasFiles> svnRutasRelativasFiles = new ArrayList<SvnRutasRelativasFiles>();
  

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public Blob getAttachmentDoc() {
        return attachmentDoc;
    }

    public void setAttachmentDoc(Blob attachmentDoc) {
        this.attachmentDoc = attachmentDoc;
    }

    public byte[] getAttachmentDocPostgres() {
        return attachmentDocPostgres;
    }

    public void setAttachmentDocPostgres(byte[] attachmentDocPostgres) {
        this.attachmentDocPostgres = attachmentDocPostgres;
    }

    public double getSize_doc() {
        return size_doc;
    }

    public void setSize_doc(double size_doc) {
        this.size_doc = size_doc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Flow_Participantes getFlowParticipantes() {
        return flowParticipantes;
    }

    public void setFlowParticipantes(Flow_Participantes flowParticipantes) {
        this.flowParticipantes = flowParticipantes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

	public boolean isSwSVN() {
		return swSVN;
	}

	public void setSwSVN(boolean swSVN) {
		this.swSVN = swSVN;
	}

	public boolean isSwSVNUpload() {
		return swSVNUpload;
	}

	public void setSwSVNUpload(boolean swSVNUpload) {
		this.swSVNUpload = swSVNUpload;
	}

	public String getUrlsubversionUpload() {
		return urlsubversionUpload;
	}

	public void setUrlsubversionUpload(String urlsubversionUpload) {
		this.urlsubversionUpload = urlsubversionUpload;
	}

	public Collection<SvnRutasRelativasFiles> getSvnRutasRelativasFiles() {
		return svnRutasRelativasFiles;
	}

	public void setSvnRutasRelativasFiles(
			Collection<SvnRutasRelativasFiles> svnRutasRelativasFiles) {
		this.svnRutasRelativasFiles = svnRutasRelativasFiles;
	}
}
