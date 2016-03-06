/*
 * ClienteDocumentoDetalle.java
 *
 * Created on July 25, 2007, 11:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.cliente.documentos;

 
import com.ecological.paper.documentos.AreaDocumentos;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_estado;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
 

/**
 *
 * @author simon
 */
public class ClienteDocumentoDetalle implements Serializable {
    
    private Long codigo;
    private Doc_maestro doc_maestro;
    private Doc_estado doc_estado;
   
    
    private String mayorVer;
    
    private String minorVer;
    
    private Date datecambio;
    private String datecambioStr;
    
    private String nameFile;
    
    private String descripcion;
    
    private Blob data;
    
    private double size_doc;
    private boolean doc_checkout;
    
    private Usuario duenio;
    private Usuario modificadoPor;
    private Collection<Flow> flujos = new ArrayList<Flow>();
    
    private String contextType;
    private boolean swPuedeRealizarFlujo;
    private boolean swIsObsoleto;
    private boolean swActualizar;
    private boolean swCheckOut;
    private boolean swCopy;
    private boolean swPage;
    private boolean swCute;
    private boolean swUnlocked;
    private boolean swLocked;
    private boolean swLockedwithkey;
    private boolean swFlujoActivo;
    private boolean swVincularDocumento;
    private boolean swPublico;
    private boolean swMover;
    private boolean swRegistro;
    private String icono;
    private String usuariosInFlowStr;
    public boolean swHistDoc; 
    public  boolean swHistFlow;
    private boolean swDiferenciaEntreVersiones;
    private boolean swImprimir;
    private boolean swHabilitadoImprimir;
    private boolean swEstaInFlowToImprimir;
    private boolean swFechaPublicoExpiro;
    private boolean swFirstBorrador;
    private AreaDocumentos areaDocumentos;
    private boolean swCanDoPublicoIsVigente;
    private boolean swVigente;
    
	private boolean swAttachment;
	private Doc_detalle doc_detalle;
    
    /** Creates a new instance of ClienteDocumentoDetalle */
    public ClienteDocumentoDetalle() {
    }
    
    public ClienteDocumentoDetalle(Doc_detalle doc_d) {
    	this.doc_detalle=doc_d;
        this.codigo = doc_d.getCodigo();
        this.doc_maestro = doc_d.getDoc_maestro();
        this.doc_estado = doc_d.getDoc_estado();
      this.areaDocumentos=doc_d.getAreaDocumentos();
        this.mayorVer = doc_d.getMayorVer();
        this.minorVer = doc_d.getMinorVer();
        this.datecambio = doc_d.getDatecambio();
        this.datecambioStr=Utilidades.sdfShow.format(datecambio);
        this.nameFile = doc_d.getNameFile();
        this.descripcion = doc_d.getDescripcion();
        this.data = doc_d.getData();
        this.size_doc = doc_d.getSize_doc();
        this.doc_checkout = doc_d.isDoc_checkout();
        this.duenio = doc_d.getDuenio();
        this.flujos = doc_d.getFlujos();
        this.contextType = doc_d.getContextType();
        this.modificadoPor = doc_d.getModificadoPor();
    }
    
    
    public ClienteDocumentoDetalle(Doc_detalle doc_d, boolean swPuedeRealizarFlujo,
            boolean swIsObsoleto, boolean swActualizar, boolean swCheckOut, boolean copy,
            boolean page, boolean cute,boolean swLocked,boolean swLockedwithkey,boolean swUnlocked,boolean swFlujoActivo,
            boolean swVincularDocumento, boolean swPublico,
            boolean swMover,
            boolean swRegistro,String icono,String usuariosInFlowStr,boolean swHistDoc, boolean swHistFlow) {
    	this.doc_detalle=doc_d;
   	  this.areaDocumentos=doc_d.getAreaDocumentos();
        this.swHistDoc=swHistDoc;
    	this.swHistFlow=swHistFlow;
        this.codigo = doc_d.getCodigo();
        this.doc_maestro = doc_d.getDoc_maestro();
        this.doc_estado = doc_d.getDoc_estado();
      
        this.mayorVer = doc_d.getMayorVer();
        this.minorVer = doc_d.getMinorVer();
        
        this.datecambio = doc_d.getDatecambio();
        this.datecambioStr=Utilidades.sdfShow.format(datecambio);
        
        this.nameFile = doc_d.getNameFile();
        this.descripcion = doc_d.getDescripcion();
        this.data = doc_d.getData();
        this.size_doc = doc_d.getSize_doc();
        this.doc_checkout = doc_d.isDoc_checkout();
        this.duenio = doc_d.getDuenio();
        this.flujos = doc_d.getFlujos();
        this.contextType = doc_d.getContextType();
        this.modificadoPor = doc_d.getModificadoPor();
        
        this.swCopy = copy;
        this.swPage = page;
        this.swCute = cute;
        this.swPuedeRealizarFlujo = swPuedeRealizarFlujo;
        this.swIsObsoleto = swIsObsoleto;
        this.swActualizar = swActualizar;
        this.swCheckOut = swCheckOut;
        this.swLocked=swLocked;
        this.swLockedwithkey=swLockedwithkey;
        this.swUnlocked=swUnlocked;
        this.setSwFlujoActivo(swFlujoActivo);
        this.setSwVincularDocumento(swVincularDocumento);
        this.swPublico=swPublico;
        this.swMover=swMover;
        this.swRegistro=swRegistro;
        this.icono=icono;
        this.usuariosInFlowStr=usuariosInFlowStr;
    }
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public Doc_maestro getDoc_maestro() {
        return doc_maestro;
    }
    
    public void setDoc_maestro(Doc_maestro doc_maestro) {
        this.doc_maestro = doc_maestro;
    }
    
    public Doc_estado getDoc_estado() {
        return doc_estado;
    }
    
    public void setDoc_estado(Doc_estado doc_estado) {
        this.doc_estado = doc_estado;
    }
    
    public String getMayorVer() {
        return mayorVer;
    }
    
    public void setMayorVer(String mayorVer) {
        this.mayorVer = mayorVer;
    }
    
    public String getMinorVer() {
        return minorVer;
    }
    
    public void setMinorVer(String minorVer) {
        this.minorVer = minorVer;
    }
    
    public Date getDatecambio() {
        return datecambio;
    }
    
    public void setDatecambio(Date datecambio) {
        this.datecambio = datecambio;
    }
    
    public String getNameFile() {
        return nameFile;
    }
    
    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Blob getData() {
        return data;
    }
    
    public void setData(Blob data) {
        this.data = data;
    }
    
    public double getSize_doc() {
        return size_doc;
    }
    
    public void setSize_doc(double size_doc) {
        this.size_doc = size_doc;
    }
    
    public boolean isDoc_checkout() {
        return doc_checkout;
    }
    
    public void setDoc_checkout(boolean doc_checkout) {
        this.doc_checkout = doc_checkout;
    }
    
    public Usuario getDuenio() {
        return duenio;
    }
    
    public void setDuenio(Usuario duenio) {
        this.duenio = duenio;
    }
    
    public Collection<Flow> getFlujos() {
        return flujos;
    }
    
    public void setFlujos(Collection<Flow> flujos) {
        this.flujos = flujos;
    }
    
    public String getContextType() {
        return contextType;
    }
    
    public void setContextType(String contextType) {
        this.contextType = contextType;
    }
    
    public boolean isSwPuedeRealizarFlujo() {
        return swPuedeRealizarFlujo;
    }
    
    public void setSwPuedeRealizarFlujo(boolean swPuedeRealizarFlujo) {
        this.swPuedeRealizarFlujo = swPuedeRealizarFlujo;
    }
    
    public boolean isSwIsObsoleto() {
        return swIsObsoleto;
    }
    
    public void setSwIsObsoleto(boolean swIsObsoleto) {
        this.swIsObsoleto = swIsObsoleto;
    }
    
    public boolean isSwActualizar() {
        return swActualizar;
    }
    
    public void setSwActualizar(boolean swActualizar) {
        this.swActualizar = swActualizar;
    }
    
    public boolean isSwCheckOut() {
        return swCheckOut;
    }
    
    public void setSwCheckOut(boolean swCheckOut) {
        this.swCheckOut = swCheckOut;
    }
    
    public boolean isSwCopy() {
        return swCopy;
    }
    
    public void setSwCopy(boolean swCopy) {
        this.swCopy = swCopy;
    }
    
    public boolean isSwPage() {
        return swPage;
    }
    
    public void setSwPage(boolean swPage) {
        this.swPage = swPage;
    }
    
    public boolean isSwCute() {
        return swCute;
    }
    
    public void setSwCute(boolean swCute) {
        this.swCute = swCute;
    }
    
    public Usuario getModificadoPor() {
        return modificadoPor;
    }
    
    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }
    public boolean isSwLocked() {
        return swLocked;
    }
    
    public void setSwLocked(boolean swLocked) {
        this.swLocked = swLocked;
    }
    
    public boolean isSwLockedwithkey() {
        return swLockedwithkey;
    }
    
    public void setSwLockedwithkey(boolean swLockedwithkey) {
        this.swLockedwithkey = swLockedwithkey;
    }
    
    public boolean isSwUnlocked() {
        return swUnlocked;
    }
    
    public void setSwUnlocked(boolean swUnlocked) {
        this.swUnlocked = swUnlocked;
    }
    
    public boolean isSwFlujoActivo() {
        return swFlujoActivo;
    }
    
    public void setSwFlujoActivo(boolean swFlujoActivo) {
        this.swFlujoActivo = swFlujoActivo;
    }
    
    public boolean isSwVincularDocumento() {
        return swVincularDocumento;
    }
    
    public void setSwVincularDocumento(boolean swVincularDocumento) {
        this.swVincularDocumento = swVincularDocumento;
    }
    
    public boolean isSwPublico() {
        return swPublico;
    }
    
    public void setSwPublico(boolean swPublico) {
        this.swPublico = swPublico;
    }
    
    public boolean isSwMover() {
        return swMover;
    }
    
    public void setSwMover(boolean swMover) {
        this.swMover = swMover;
    }
    
    public boolean isSwRegistro() {
        return swRegistro;
    }
    
    public void setSwRegistro(boolean swRegistro) {
        this.swRegistro = swRegistro;
    }
    
    public String getIcono() {
        return icono;
    }
    
    public void setIcono(String icono) {
        this.icono = icono;
    }
    
    public String getUsuariosInFlowStr() {
        return usuariosInFlowStr;
    }
    
    public void setUsuariosInFlowStr(String usuariosInFlowStr) {
        this.usuariosInFlowStr = usuariosInFlowStr;
    }

	public String getDatecambioStr() {
		return datecambioStr;
	}

	public void setDatecambioStr(String datecambioStr) {
		this.datecambioStr = datecambioStr;
	}

	public boolean isSwHistDoc() {
		return swHistDoc;
	}

	public void setSwHistDoc(boolean swHistDoc) {
		this.swHistDoc = swHistDoc;
	}

	public boolean isSwHistFlow() {
		return swHistFlow;
	}

	public void setSwHistFlow(boolean swHistFlow) {
		this.swHistFlow = swHistFlow;
	}

	public boolean isSwDiferenciaEntreVersiones() {
		return swDiferenciaEntreVersiones;
	}

	public void setSwDiferenciaEntreVersiones(boolean swDiferenciaEntreVersiones) {
		this.swDiferenciaEntreVersiones = swDiferenciaEntreVersiones;
	}

	public boolean isSwImprimir() {
		return swImprimir;
	}

	public void setSwImprimir(boolean swImprimir) {
		this.swImprimir = swImprimir;
	}

	 
	 
	public boolean isSwFechaPublicoExpiro() {
		return swFechaPublicoExpiro;
	}

	public void setSwFechaPublicoExpiro(boolean swFechaPublicoExpiro) {
		this.swFechaPublicoExpiro = swFechaPublicoExpiro;
	}

	 

	public boolean isSwFirstBorrador() {
		return swFirstBorrador;
	}

	public void setSwFirstBorrador(boolean swFirstBorrador) {
		this.swFirstBorrador = swFirstBorrador;
	}

	public AreaDocumentos getAreaDocumentos() {
		return areaDocumentos;
	}

	public void setAreaDocumentos(AreaDocumentos areaDocumentos) {
		this.areaDocumentos = areaDocumentos;
	}

	public boolean isSwHabilitadoImprimir() {
		return swHabilitadoImprimir;
	}

	public void setSwHabilitadoImprimir(boolean swHabilitadoImprimir) {
		this.swHabilitadoImprimir = swHabilitadoImprimir;
	}

	public boolean isSwEstaInFlowToImprimir() {
		return swEstaInFlowToImprimir;
	}

	public void setSwEstaInFlowToImprimir(boolean swEstaInFlowToImprimir) {
		this.swEstaInFlowToImprimir = swEstaInFlowToImprimir;
	}

	public boolean isSwCanDoPublicoIsVigente() {
		return swCanDoPublicoIsVigente;
	}

	public void setSwCanDoPublicoIsVigente(boolean swCanDoPublicoIsVigente) {
		this.swCanDoPublicoIsVigente = swCanDoPublicoIsVigente;
	}

	public boolean isSwVigente() {
		return swVigente;
	}

	public void setSwVigente(boolean swVigente) {
		this.swVigente = swVigente;
	}

	public boolean isSwAttachment() {
		return swAttachment;
	}

	public void setSwAttachment(boolean swAttachment) {
		this.swAttachment = swAttachment;
	}

	public Doc_detalle getDoc_detalle() {
		return doc_detalle;
	}

	public void setDoc_detalle(Doc_detalle doc_detalle) {
		this.doc_detalle = doc_detalle;
	}

    
   
}
