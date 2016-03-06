package com.ecological.hilos;

import javax.servlet.http.HttpSession;

import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.delegado.DelegadoEJBLocal;
import com.ecological.paper.subversion.RepositorioSVN;
import com.ecological.paper.subversion.SubVersionUsuario;
import com.ecological.paper.usuario.Usuario;

public class HiloClienteSeguridad extends Thread {
	private DelegadoEJBLocal delegado = null;
	private Usuario usuario;
	private HttpSession session;

	public HiloClienteSeguridad(Usuario usuario,HttpSession session) {
		this.usuario = usuario;
		this.session=session;
	}
	
	public HiloClienteSeguridad(Usuario usuario) {
		this.usuario = usuario;
	  
	}

	public HiloClienteSeguridad() {

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
			ClienteSeguridad clienteSeguridad = new ClienteSeguridad("No se Usa");
			System.out.println("OBTENIENDO SEGURIDAD-----------");
			clienteSeguridad.ponerSeguridadInSession(usuario,session);
			// System.out.println("procesando hilo");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
	}

}