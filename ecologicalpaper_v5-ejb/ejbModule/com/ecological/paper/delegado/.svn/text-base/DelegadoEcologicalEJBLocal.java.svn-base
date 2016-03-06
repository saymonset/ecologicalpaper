package com.ecological.paper.delegado;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;



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

@Local
public interface DelegadoEcologicalEJBLocal {
	public Tree crearNuevoTree(Tree tree,Usuario user_logueado,boolean swHeredarPermisos) throws ExceptionTree;
	public void saveUsuarioOperacionesFlows (Tree tree,Usuario user_logueado,Doc_detalle doc_detalle
			, List<Usuario> usuarioSeleccionados) throws EcologicaExcepciones;
	public void saveRoleOperacionesFlows (Tree tree,Usuario user_logueado,Doc_detalle doc_detalle
			, List<Role> roleSeleccionados) throws EcologicaExcepciones;
	
	public void firmarFlows(Flow_Participantes flow_Participante, 
			Flow_ParticipantesAttachment flow_ParticipantesAttachment) throws EcologicaExcepciones;
	
	public void upload(Tree tree, 
			Doc_maestro doc_maestro,Doc_detalle doc_detalle,
			File _upFile,Usuario user_logueado,String contextType,Map llenarSessiones) 
	throws EcologicaExcepciones,EcologicaExcepcionesContextType;
	public void devolucionFirmaFlowExecute(Flow_Participantes flow_Participante);
	
	public void MyHiloEnvioMails(Flow flow);
	public void MyHiloEnvioMails(Flow flow,Flow_Participantes flujo_participantes );
	public void devolucionStartFlow(Flow_Participantes flow_Participante);
	public void crearSolicitudImpresion(Solicitudimpresion solicitudimpresion) throws ExceptionTree;	
	public void saveRoleOperacionesFlowsNotificacion(Tree t, Usuario user_logueado,
			Doc_detalle doc_detalle, List<Role> roleSeleccionados)
			throws EcologicaExcepciones;
		
}
