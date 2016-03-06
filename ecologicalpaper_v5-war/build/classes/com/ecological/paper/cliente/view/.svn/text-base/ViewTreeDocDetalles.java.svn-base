package com.ecological.paper.cliente.view;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;
import com.ecological.paper.cliente.documentos.ClienteDocumentoMaestro;
import com.ecological.paper.cliente.documentos.ClientePadreDocumento;
import com.ecological.paper.cliente.viewimpl.ViewImp;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.util.Utilidades;
import com.util.file.Archivo;

@ManagedBean(name = "viewTreeDocDetalles")
@SessionScoped
public class ViewTreeDocDetalles extends ClientePadreDocumento implements
		ViewImp, Serializable {
	private HttpSession session = super.getSession();
	private Seguridad seguridadMenu = new Seguridad();
	private Usuario user_logueado;
	private boolean swEditar;
	private ServicioDelegado delegado = new ServicioDelegado();
	private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
			.getInstance();

	// editar con richfaces
	private List<ClienteDocumentoMaestro> allObjectItems = null;
	private List<ClienteDocumentoMaestro> allObjectItemsAux = null;
	private ClienteDocumentoMaestro objeto = null;
	private ClienteDocumentoMaestro objectItem = null;

	private int currentCarIndex;
	private int page = 1;
	private String title;

	private String cualquierComentario;
	private String descripcionTree;
	private boolean swInfo;
	private Tree treeNodoActual;
	private Doc_maestro docMaestro;
	private Doc_detalle docDetalle;
	private Doc_detalle doc_detalle;
	private List<Doc_detalle> doc_detalles;
	private Role roleParaPermisos;
	private boolean swPermGrupo;
	private boolean swHeredarPermisos;
	private String extensiones;
	private ArrayList<UploadedImage> files = new ArrayList<UploadedImage>();
	private ArrayList<FileUploadEvent> filesPrimeFaces = new ArrayList<FileUploadEvent>();
	private List<Doc_detalle> doc_detallesAux;
	private Doc_detalle doc_detallePrincipal_2 = null;
	private Seguridad seguridadTree = new Seguridad();
	


	public void handleFileUpload(FileUploadEvent event) {

		filesPrimeFaces.add(event);
		 //con este metodo, corremos la lista de mayor  A menor para agarra siempre el ultimo achivo  que se escojio
		Collections.reverse(filesPrimeFaces);
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public ViewTreeDocDetalles() {
		//super();
		session = super.getSession();
		//inicializamos para que el checkbox seleccione un grupo de permiso para que lo herede el documentoa subir
		swPermGrupo=true;
		 
		// CONSEGUIMOS LA SEGURIDAD MENU
		// ESTA ES IRONICAMENTE PARA CANCELAR AUTORIZACION DE IMPRIMIR
		Tree treeMenu = new Tree();
		treeMenu.setNodo(Utilidades.getNodoRaiz());
		seguridadMenu = super.getSeguridadTree(treeMenu);

		user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
				.getAttribute("user_logueado") : null;

		objeto = new ClienteDocumentoMaestro();

		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;

		 

	}

	public List<?> getAllObjectItems() {

		return allObjectItems;
	}

	public String editardocumento() {
		super.setRoleParaPermisos(roleParaPermisos);
		super.setSwPermGrupo(swPermGrupo);
		docDetalle.setDoc_maestro(docMaestro);

		super.modificarDocumentoRichFaces(docDetalle);
		return "";
	}

	public String grabardocumento() {
		super.setRoleParaPermisos(roleParaPermisos);
		super.setSwPermGrupo(swPermGrupo);
		super.setSwHeredarPermisos(swHeredarPermisos);
		treeNodoActual = session.getAttribute("treeNodoActual") != null ? (Tree) session
				.getAttribute("treeNodoActual") : null;
		// docDetalle.setDoc_maestro(docMaestro);

		// super.upload(docDetalle);
		return "";
	}

	public String docPublicar() {
		// flows.jsp
		return "docPublicar";
	}

	public String editar() {
		String pagIr = "";

		if (filesPrimeFaces == null || filesPrimeFaces.size() == 0
				|| filesPrimeFaces.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(messages.getString("archivo_nulo")));
			return "";
		}

		for (FileUploadEvent f : filesPrimeFaces) {
			Archivo archivo = new Archivo();

			/*
			 * inputStream = null;// _upFile.getInputStream(); tamanio = 0;//
			 * _upFile.getSize(); bytes = null;// _upFile.getBytes();
			 * contentType = null;// _upFile.getContentType(); name = null;//
			 * _upFile.getName();
			 */

			File _upFileFile = null;
			String ext = f
					.getFile()
					.getFileName()
					.substring(f.getFile().getFileName().lastIndexOf(".") + 1,
							f.getFile().getFileName().length());
			String nom2 = f
					.getFile()
					.getFileName()
					.substring(0,
							f.getFile().getFileName().lastIndexOf(".") );
			try {
				_upFileFile = archivo.fileDesdeStream(f.getFile()
						.getInputstream(), nom2, ext);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			super.setTreeNodoActual(this.treeNodoActual);
			super.setUser_logueado(this.user_logueado);
			super.setDoc_detalle(this.doc_detalle);
			super.setDoc_maestro(this.doc_detalle.getDoc_maestro());
			super.setRoleParaPermisos(this.roleParaPermisos);
			super.setSwPermGrupo(this.swPermGrupo);
			super.setSwHeredarPermisos(this.swHeredarPermisos);
			try {
				
				pagIr = super.uploadRichFaces(_upFileFile, nom2, f.getFile()
						.getContentType());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EcologicaExcepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		}

		return pagIr;
	}

	public String aceptar() {
		// TODO Auto-generated method stub

		return "aplicacion";
	}

	public String editDocumento() {
		swInfo = true;
		return "aplicacion";
	}

	public String remove() {
		session.setAttribute("toDelTree", objectItem);
		super.setCualquierComentario(this.cualquierComentario);
		String pagIr = "";
		// String pagIr = super.deleteTree();
		return pagIr;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Seguridad getSeguridadMenu() {
		return seguridadMenu;
	}

	public void setSeguridadMenu(Seguridad seguridadMenu) {
		this.seguridadMenu = seguridadMenu;
	}

	public Usuario getUser_logueado() {
		return user_logueado;
	}

	public void setUser_logueado(Usuario user_logueado) {
		this.user_logueado = user_logueado;
	}

	public boolean isSwEditar() {
		return swEditar;
	}

	public void setSwEditar(boolean swEditar) {
		this.swEditar = swEditar;
	}

	public ClienteDocumentoMaestro getObjeto() {
		return objeto;
	}

	public void setObjeto(ClienteDocumentoMaestro objeto) {
		this.objeto = objeto;
	}

	public ClienteDocumentoMaestro getObjectItem() {
		return objectItem;
	}

	public void setObjectItem(ClienteDocumentoMaestro objectItem) {
		this.objectItem = objectItem;
	}

	public int getCurrentCarIndex() {
		return currentCarIndex;
	}

	public void setCurrentCarIndex(int currentCarIndex) {
		this.currentCarIndex = currentCarIndex;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAllObjectItems(List<ClienteDocumentoMaestro> allObjectItems) {
		this.allObjectItems = allObjectItems;
	}

	public String getCualquierComentario() {
		return cualquierComentario;
	}

	public void setCualquierComentario(String cualquierComentario) {
		this.cualquierComentario = cualquierComentario;
	}

	public String getDescripcionTree() {
		return descripcionTree;
	}

	public void setDescripcionTree(String descripcionTree) {
		this.descripcionTree = descripcionTree;
	}

	public boolean isSwInfo() {
		return swInfo;
	}

	public void setSwInfo(boolean swInfo) {
		this.swInfo = swInfo;
	}

	public List<ClienteDocumentoMaestro> getAllObjectItemsAux() {

		session.setAttribute("treeNodoActual", docMaestro.getTree());
		allObjectItemsAux = super.getClienteDocumentoMaestros();

		return allObjectItemsAux;
	}

	public void setAllObjectItemsAux(
			List<ClienteDocumentoMaestro> allObjectItemsAux) {
		this.allObjectItemsAux = allObjectItemsAux;
	}

	public Tree getTreeNodoActual() {
		return treeNodoActual;
	}

	public void setTreeNodoActual(Tree treeNodoActual) {
		this.treeNodoActual = treeNodoActual;
	}

	public List<Doc_detalle> getDoc_detalles() {
		List<Doc_detalle> detallesTotales = new ArrayList<Doc_detalle>();
		if (docMaestro != null) {
			doc_detalles = (List<Doc_detalle>) delegado
					.findAllDoc_Detalles(docMaestro);

			for (Doc_detalle d : doc_detalles) {
				String icono = super.obtenIconoDoc(d.getNameFile());
				if (isEmptyOrNull(icono)) {
					icono = confmessages.getString("img_default");
				}
				d.setIcono(icono);
				detallesTotales.add(d);
			}

		}
		return detallesTotales;
	}

	public void setDoc_detalles(List<Doc_detalle> doc_detalles) {
		this.doc_detalles = doc_detalles;
	}

	public Doc_maestro getDocMaestro() {
		if (docMaestro != null) {
			return docMaestro;
		} else {
			docMaestro = delegado.findMaestro(docMaestro);
		}
		return docMaestro;
	}

	public void setDocMaestro(Doc_maestro docMaestro) {
		this.docMaestro = docMaestro;
	}

	public Doc_detalle getDoc_detalle() {
		if (doc_detalle == null) {

			doc_detalle = new Doc_detalle();
			doc_detalle.setMayorVer("1");
			doc_detalle.setMinorVer("0");
			Doc_maestro m = new Doc_maestro();
			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			doc_detalle.setDuenio(user_logueado);
			doc_detalle.setDoc_maestro(m);

		}
		return doc_detalle;
	}

	public Doc_detalle getDocDetalle() {
		if (docDetalle != null) {
			if (docDetalle.getDuenio() == null) {
				user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;
				docDetalle.setDuenio(user_logueado);
			}
			return docDetalle;
		}
		if (docMaestro != null) {
			doc_detalles = (List<Doc_detalle>) delegado
					.findAllDoc_Detalles(docMaestro);

			for (Doc_detalle d : doc_detalles) {
				String icono = super.obtenIconoDoc(d.getNameFile());
				if (isEmptyOrNull(icono)) {
					icono = confmessages.getString("img_default");
				} else {
					File f = new File(icono);
					if (!f.exists()) {
						icono = confmessages.getString("img_default");
					}
				}
				d.setIcono(icono);
				docDetalle = d;
				user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
						.getAttribute("user_logueado") : null;

				docDetalle.setDuenio(user_logueado);
				// con el primero y me voy
				return docDetalle;

			}

		}

		return docDetalle;
	}

	public void setDocDetalle(Doc_detalle docDetalle) {
		this.docDetalle = docDetalle;
	}

	public Role getRoleParaPermisos() {
		return roleParaPermisos;
	}

	public void setRoleParaPermisos(Role roleParaPermisos) {
		this.roleParaPermisos = roleParaPermisos;
	}

	public boolean isSwPermGrupo() {
		return swPermGrupo;
	}

	public void setSwPermGrupo(boolean swPermGrupo) {
		this.swPermGrupo = swPermGrupo;
	}

	public void setDoc_detalle(Doc_detalle doc_detalle) {
		this.doc_detalle = doc_detalle;
	}

	public boolean isSwHeredarPermisos() {
		return swHeredarPermisos;
	}

	public void setSwHeredarPermisos(boolean swHeredarPermisos) {
		this.swHeredarPermisos = swHeredarPermisos;
	}

	public void paint(OutputStream stream, Object object) throws IOException {
		stream.write(getFiles().get((Integer) object).getData());
		stream.close();
	}

	// public void listener(FileUploadEvent event) throws Exception {
	// UploadedFile item = event.getUploadedFile();
	// System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
	// System.out.println("item.getContentType()=" + item.getContentType());
	// System.out.println("item.toString()=" + item.toString());
	// System.out.println("item.name()=" + item.getName());
	//
	// UploadedImage file = new UploadedImage();
	//
	// file.setLength(item.getData().length);
	//
	// file.setName(item.getName());
	// file.setData(item.getData());
	// System.out.println("OOOOOOOFINOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
	// files.add(file);
	// }

	public String clearUploadData() {
		files.clear();
		return null;
	}

	public int getSize() {
		if (getFiles().size() > 0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public ArrayList<UploadedImage> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<UploadedImage> files) {
		this.files = files;
	}

	public String getExtensiones() {

		extensiones = session.getAttribute("extensiones") != null ? (String) session
				.getAttribute("extensiones") : null;
		if (extensiones == null) {
			extensiones = delegado.findAllExtensionWithHijos();
			session.setAttribute("extensiones", extensiones);
		}
		
		

		return extensiones;
	}

	public void setExtensiones(String extensiones) {
		this.extensiones = extensiones;
	}

	public ArrayList<FileUploadEvent> getFilesPrimeFaces() {
		return filesPrimeFaces;
	}

	public void setFilesPrimeFaces(ArrayList<FileUploadEvent> filesPrimeFaces) {
		this.filesPrimeFaces = filesPrimeFaces;
	}

	public void setDoc_detallesAux(List<Doc_detalle> doc_detallesAux) {
		this.doc_detallesAux = doc_detallesAux;
	}

	public Doc_detalle getDoc_detallePrincipal_2() {
		return doc_detallePrincipal_2;
	}

	public void setDoc_detallePrincipal_2(Doc_detalle doc_detallePrincipal_2) {
		this.doc_detallePrincipal_2 = doc_detallePrincipal_2;
	}

	public Seguridad getSeguridadTree() {
		return seguridadTree;
	}

	public void setSeguridadTree(Seguridad seguridadTree) {
		this.seguridadTree = seguridadTree;
	}

	public String docVinculados() {
		// flows.jsp
		return "docVinculados";
	}

	public String generarRegistro() {
		try {

			Doc_detalle detalle = (Doc_detalle) session
					.getAttribute("doc_detalle");
			session.setAttribute("swPage", detalle);
			// ____________________________________
			// GUARDAMOS LO QUE HIZO Y QUIEN LO HIZO

			boolean genReg = true;

			user_logueado = session.getAttribute("user_logueado") != null ? (Usuario) session
					.getAttribute("user_logueado") : null;
			super.guardamosHistoricoActivoDelDocumento(user_logueado,
					detalle.getDoc_maestro(), false, false, false, false,
					false, false, false, genReg, "");
			// ____________________________________
		} catch (Exception e) {
			redirect();
		}
		return "generarRegistro";
	}

}
