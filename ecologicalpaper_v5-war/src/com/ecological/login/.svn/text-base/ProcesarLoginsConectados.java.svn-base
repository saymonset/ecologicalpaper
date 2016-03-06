/*
 * ProcesarLoginsConectados.java
 *
 * Created on July 11, 2007, 7:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.login;

import com.ecological.paper.usuario.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * 
 * @author simon
 */
public class ProcesarLoginsConectados {
	private Collection usuarios;
	// request,session,application
	private String ambitoDelAlcance;

	/** Creates a new instance of ProcesarLoginsConectados */
	public ProcesarLoginsConectados() {
	}

	private void init(Map hashmap) {
		usuarios = new ArrayList();
		hashmap.put(ambitoDelAlcance, usuarios);
	}

	/**
	 * load data from db;
	 * 
	 * @param hashmap
	 */
	private void loadData(Map hashmap) {
		// laliluna 04.10.2004 load usuarios from db
		try {
			if (hashmap.get(ambitoDelAlcance) == null) {
				init(hashmap);

			} else {
				usuarios = (Collection) hashmap.get(ambitoDelAlcance);
				if (usuarios == null)
					init(hashmap);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * save data to DB ;-)
	 * 
	 * @param hashmap
	 */
	private void saveData(Map hashmap) {
		hashmap.put(ambitoDelAlcance, usuarios);
	}

	public long saveToUsuario(Usuario usuario, Map hashmap, String nombCollVar) {
		// nombCollVar-> el alcance, y el tipo de colleccion a alamacenar en el
		// hashmap
		setAmbitoDelAlcance(nombCollVar);

		loadData(hashmap);

		// laliluna 04.10.2004 loop over collection and trying to find the
		// usuario
		boolean UsuarioExist = false;
		ArrayList usuariosNew = (ArrayList) usuarios;
		int index = 0;
		for (Iterator iter = usuarios.iterator(); iter.hasNext();) {
			Usuario element = (Usuario) iter.next();
			// laliluna 04.10.2004 if Usuario is found do an update
			if (element.getId() == usuario.getId()) {
				usuariosNew.set(index, usuario);
				UsuarioExist = true;
				break;
			}
			index++;
		}
		usuarios = usuariosNew;
		// laliluna 04.10.2004 if Usuario is not found, create a new Usuario
		if (UsuarioExist == false) {
			usuarios.add(usuario);
		}

		// laliluna 04.10.2004 save to DB ;-)
		saveData(hashmap);
		if (usuario == null) {
			return 0;
		} else if (usuario.getId() == null) {
			return 0;
		}
		return usuario.getId();
	}

	public Usuario loadUsuarioOfSessiond(Map hashmap, String nombCollVar) {
		try {
			// nombCollVar-> el alcance, y el tipo de colleccion a alamacenar en
			// el
			// hashmap
			setAmbitoDelAlcance(nombCollVar);
			// laliluna 04.10.2004 load usuarios from db
			loadData(hashmap);
			// laliluna 04.10.2004 loop over all usuarios and return Usuario if
			// found
			Iterator iter = usuarios.iterator();
			if (iter.hasNext()) {
				Usuario element = (Usuario) iter.next();
				return (Usuario) element;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	public Usuario loadUsuarioById(long id, Map hashmap, String nombCollVar) {
		// nombCollVar-> el alcance, y el tipo de colleccion a alamacenar en el
		// hashmap
		try {
			setAmbitoDelAlcance(nombCollVar);
			// laliluna 04.10.2004 load usuarios from db
			loadData(hashmap);
			// laliluna 04.10.2004 loop over all usuarios and return Usuario if
			// found
			for (Iterator iter = usuarios.iterator(); iter.hasNext();) {
				Usuario element = (Usuario) iter.next();
				if ((element.getId() - id) == 0)
					return (Usuario) element;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public void deleteUsuarioById(long id, Map hashmap, String nombCollVar) {
		// nombCollVar-> el alcance, y el tipo de colleccion a alamacenar en el
		// hashmap
		setAmbitoDelAlcance(nombCollVar);

		// laliluna 04.10.2004 load usuarios from db
		loadData(hashmap);
		Collection usuariosNew = new ArrayList();
		// laliluna 04.10.2004 loop over all usuarios and delete Usuario if
		// found
		for (Iterator iter = usuarios.iterator(); iter.hasNext();) {
			Usuario element = (Usuario) iter.next();
			if ((element.getId() - id) != 0) {
				usuariosNew.add(element);
			}
		}
		// laliluna 04.10.2004 set the new usuarios
		usuarios = usuariosNew;

		// laliluna 04.10.2004 save to DB ;-)
		saveData(hashmap);
	}

	public Collection getAllusuarios(Map hashmap) {
		// laliluna 04.10.2004 load usuarios from db
		loadData(hashmap);
		return usuarios;

	}

	public String getAmbitoDelAlcance() {
		return ambitoDelAlcance;
	}

	public void setAmbitoDelAlcance(String ambitoDelAlcance) {
		this.ambitoDelAlcance = ambitoDelAlcance;
	}
}