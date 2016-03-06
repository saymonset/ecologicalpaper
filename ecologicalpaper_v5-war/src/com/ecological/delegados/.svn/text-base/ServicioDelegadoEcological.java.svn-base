package com.ecological.delegados;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ecological.paper.delegado.DelegadoEJBLocal;
import com.ecological.paper.delegado.DelegadoEcologicalEJBLocal;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepcionesContextType;
import com.ecological.paper.ecologicaexcepciones.ExceptionTree;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.flows.Flow_ParticipantesAttachment;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;

public class ServicioDelegadoEcological implements Serializable {
	private DelegadoEcologicalEJBLocal delegado;
	private static DelegadoEcologicalEJBLocal instance = null;
	private static ServicioDelegadoEcological instance2 = null;
	
	
	
	private DelegadoEcologicalEJBLocal devolverInstancia(){
		if (instance==null){
			Context ctx;
			try {
				ctx = new InitialContext();

				try {
					ctx = new InitialContext();
					System.out
					.println("CREA UNA NUEVA CONEXION A BASE DE DATOS ECOLOGICAL");
		 
					instance = (DelegadoEcologicalEJBLocal) ctx
							.lookup("ecologicalpaper_v5/DelegadoEcologicalEJBBean/local");

				} catch (NamingException e) {
					e.printStackTrace();
				}

			} catch (NamingException ex) {
				ex.printStackTrace();
			}
	
		}
		
		return instance;
	}


	/** Creates a new instance of ServicioDocumentos */
	private ServicioDelegadoEcological() {
		delegado=devolverInstancia();
	}

	public static synchronized ServicioDelegadoEcological getInstance() {
		if (instance2 == null) {
			
			System.out
					.println("CREA UNA 2DA NUEVA CONEXION A BASE DE DATOS ECOLOGICAL");
			instance2 = new ServicioDelegadoEcological();
			return instance2;
		} else {
			// System.out.println("CONTINUA INSTANCIA DE BASE DE DATOS");
			return instance2;

		}
	}

	public Tree crearNuevoTree(Tree tree, Usuario user_logueado,
			boolean swHeredarPermisos) throws ExceptionTree {
		try {
			tree = delegado.crearNuevoTree(tree, user_logueado,
					swHeredarPermisos);
			return tree;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionTree("No se pudo crear el tree");
		}
	}

	public void saveUsuarioOperacionesFlows(Tree tree, Usuario user_logueado,
			Doc_detalle doc_detalle, List<Usuario> usuarioSeleccionados)
			throws EcologicaExcepciones {
		delegado.saveUsuarioOperacionesFlows(tree, user_logueado, doc_detalle,
				usuarioSeleccionados);

	}

	public void saveRoleOperacionesFlows(Tree tree, Usuario user_logueado,
			Doc_detalle doc_detalle, List<Role> roleSeleccionados)
			throws EcologicaExcepciones {

		delegado.saveRoleOperacionesFlows(tree, user_logueado, doc_detalle,
				roleSeleccionados);
	}
	
	public void saveRoleOperacionesFlowsNotificacion(Tree tree, Usuario user_logueado,
			Doc_detalle doc_detalle, List<Role> roleSeleccionados)
			throws EcologicaExcepciones{
		delegado.saveRoleOperacionesFlowsNotificacion(tree, user_logueado, doc_detalle,
				roleSeleccionados);
	}

	public void firmarFlows(Flow_Participantes flow_Participante,
			Flow_ParticipantesAttachment flow_ParticipantesAttachment)
			throws EcologicaExcepciones {
		delegado.firmarFlows(flow_Participante, flow_ParticipantesAttachment);
	}
	
	public void upload(Tree tree, 
			Doc_maestro doc_maestro,Doc_detalle doc_detalle,
			File _upFile,Usuario user_logueado,String contextType,
			Map llenarSessiones ) throws EcologicaExcepciones,
			EcologicaExcepcionesContextType{
		delegado.upload(tree, 
				doc_maestro,doc_detalle,
				_upFile,user_logueado,contextType,llenarSessiones );
	}
	
	public void devolucionFirmaFlowExecute(Flow_Participantes flow_Participante){
		delegado.devolucionFirmaFlowExecute(flow_Participante);
	}
	public void MyHiloEnvioMails(Flow flow){
		delegado.MyHiloEnvioMails(flow);
	}
	public void MyHiloEnvioMails(Flow flow,Flow_Participantes flujo_participantes ){
		delegado.MyHiloEnvioMails(flow, flujo_participantes );
	}
	public void devolucionStartFlow(Flow_Participantes flow_Participante){
		delegado.devolucionStartFlow(flow_Participante);
	}
	public void crearSolicitudImpresion(Solicitudimpresion solicitudimpresion) throws ExceptionTree{
		delegado.crearSolicitudImpresion(solicitudimpresion);
	}
}
