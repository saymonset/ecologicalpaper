package com.ecological.hilosBackend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ecological.configuracion.Configuracion;
import com.ecological.mailBackend.MandarMail;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.usuario.Usuario;

public class MyHiloEnvioMails extends Thread {
	// private DelegadoEJBLocal delegado = null;

	private List<Usuario> mandarMailUsuarios;
	private StringBuffer mails;
	private StringBuffer usuarios;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private Flow flow;

	public MyHiloEnvioMails(List<Usuario> mandarMailUsuarios,
			List<Configuracion> configuraciones,Flow flow) {
		this.mandarMailUsuarios = new ArrayList();
		//unicos , sin repetir SET
		Map unico = new HashMap();
	
		for (Usuario u:mandarMailUsuarios){
			if (!unico.containsKey(u.getId())){
				System.out.println(u.toString());
				this.mandarMailUsuarios.add(u);
				unico.put(u.getId(), u);
			}
		}
		this.configuraciones = configuraciones;
		this.flow=flow;
	}

	/** Creates a new instance of MyHiloEnvioMails */
	public MyHiloEnvioMails() {
	}

	public void run() {
		try {
			runTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized void runTask() {
		try {
			mails = new StringBuffer();
			usuarios = new StringBuffer();
			String mensajeDuenio = "";
			for (Usuario usu : mandarMailUsuarios) {
				// el primer comentario es el que se agarra al mandar maikls. es
				// la respuesta del mensaje del participante
				if (mensajeDuenio.equalsIgnoreCase("")) {
					mensajeDuenio = usu.getComentarioMailFlujo();
				}
				// fin el primer comentario es el que se agarra al mandar
				// maikls. es la respuesta del mensaje del participante
				//data  de los mails
				mails.append(usu.getMail_principal()).append(",");
				//data  de los usuarios
				usuarios.append(usu.getApellido()).append(" ")
						.append(usu.getNombre()).append(",");
			}
			String mails2 = mails.substring(0, mails.lastIndexOf(","));
			String usuarios2 = usuarios.substring(0, usuarios.lastIndexOf(","));
			usuarios.append(mensajeDuenio);

			StringBuffer contenido = new StringBuffer("");
			// contenido.append(usuarios2).append(" ").append(mensajeDuenio);
			contenido.append("").append(" ").append(mensajeDuenio);

			MandarMail mandarMail = new MandarMail();

			mandarMail.send(mails2.toString(), contenido.toString(),
					configuraciones,flow);

			// System.out.println("procesando hilo");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
	}

}
