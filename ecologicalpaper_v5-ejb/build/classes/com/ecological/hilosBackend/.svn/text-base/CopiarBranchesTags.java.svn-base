package com.ecological.hilosBackend;

import org.tmatesoft.svn.core.SVNException;

import com.ecological.mailBackend.MandarMail;
import com.ecological.paper.delegado.DelegadoEJBLocal;
import com.ecological.paper.subversion.RepositorioSVN;
import com.ecological.paper.subversion.SubVersionUsuario;
import com.ecological.paper.usuario.Usuario;

public class CopiarBranchesTags extends Thread {
	private DelegadoEJBLocal delegado = null;
	private SubVersionUsuario subVersionUsuario;
	// creamos el directorio en la nueva raiz url
	private RepositorioSVN tester = new RepositorioSVN();

	public CopiarBranchesTags(SubVersionUsuario subVersionUsuario) {
		 this.subVersionUsuario=subVersionUsuario;
	}

	public CopiarBranchesTags() {

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
	        	tester.createBranchesOrTagsRepositorio(subVersionUsuario);
	          // System.out.println("procesando hilo");
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } finally {
	            
	        }
	    }

}
