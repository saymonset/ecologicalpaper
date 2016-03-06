package com.ecological.paper.delegado;

import com.ecological.paper.documentos.Solicitudimpresion;
import com.ecological.paper.ecologicaexcepciones.ExceptionTree;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.Flow_Participantes;

public interface DelegadoEcologicalEJBRemote {

	public void devolucionFirmaFlowExecute(Flow_Participantes flow_Participante);
	public void MyHiloEnvioMails(Flow flow);
	public void MyHiloEnvioMails(Flow flow,Flow_Participantes flujo_participantes );
	public void devolucionStartFlow(Flow_Participantes flow_Participante);
	public void crearSolicitudImpresion(Solicitudimpresion solicitudimpresion) throws ExceptionTree;
}
