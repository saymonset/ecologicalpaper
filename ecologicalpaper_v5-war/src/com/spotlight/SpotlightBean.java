package com.spotlight;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SpotlightBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -450057734399858978L;

	/*
	 * Construye un array con notacion JSON con todas las opciones
	 * que se van utilizar en el spotlight.
	 * 
	 * Sabelia Ruiz 
	 * 
	 * */
	public String getOpciones(){
		SpotlightSingleton singleton = SpotlightSingleton.getInstance();
		List<SpotLightOpcionDTO> transacciones = singleton.getTransacciones();
		StringBuffer buf = new StringBuffer(1024);
		if(transacciones!=null){
			FiltroListarOpcionSpotligh filtro = new FiltroListarOpcionSpotligh();
			List<SpotLightOpcionDTO> listaFiltrada = filtro.filtrarLista(transacciones);
			int count = 0;
			buf.append("[");
			for(SpotLightOpcionDTO t :listaFiltrada){
				String descripcion ="";
				String titulo = "";
				if(t.getDescripcionSpotLight()!=null){
					descripcion = t.getDescripcionSpotLight().replace("\"", "\\\"");
				}
				if(t.getEtiqueta()!=null){
					titulo = t.getEtiqueta().replace("\"", "\\\"");
				}
				buf.append("{");
				buf.append("idMmoOpcion:\"").append(t.getId_mmoOpcion()).append("\"");
				buf.append(",titulo:\"").append(titulo).append("\"");
				buf.append(",descripcion:\"").append(descripcion).append("\"");
				buf.append(",url:\"").append(t.getUrl()).append("\"");
				buf.append("}");
				if(count!=transacciones.size()-1){
					buf.append(",");
				}
				count++;
			}
			buf.append("]");
		}
		return buf.toString();
		
	}
	
	/*
	 * Construye un array con notacion JSON con las palabras claves.
	 * 
	 * Sabelia Ruiz
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public String getListarPalabrasClaves(){
		HashMap<String, String> palabrasClaves = getPalabrasClaves();
		StringBuffer buf = new StringBuffer(1024);		
		Iterator it = palabrasClaves.entrySet().iterator();
		buf.append("[");
		while(it.hasNext()){
			Map.Entry e = (Map.Entry)it.next();
			buf.append("{");
			buf.append("key:\"").append(e.getKey()).append("\"");
			buf.append(",index:\"").append(e.getValue()).append("\"");
			buf.append("}");
			if(it.hasNext()){
				buf.append(",");
			}
		}
		buf.append("]");
		return buf.toString();
	}	
	
	private HashMap<String, String> getPalabrasClaves(){
		HashMap<String, String> claves = new HashMap<String, String>();
		SpotlightSingleton singleton = SpotlightSingleton.getInstance();
		List<SpotLightOpcionDTO> transacciones = singleton.getTransacciones();
		if(transacciones!=null){
			FiltroListarOpcionSpotligh filtro = new FiltroListarOpcionSpotligh();
			List<SpotLightOpcionDTO> listaFiltrada = filtro.filtrarLista(transacciones);
			int count = 0;
			for(SpotLightOpcionDTO t : listaFiltrada){
				if(t.getPALABRAS_CLAVE()!=null){
					String[] palabrasClaves = t.getPALABRAS_CLAVE().split(",");
					for(String key : palabrasClaves){
						String clave = key.trim();
						clave = clave.toLowerCase();
						if(claves.containsKey(clave)){
							String value = claves.get(clave);
							value += "-"+Integer.toString(count);
							claves.put(clave, value);
						}else{
							claves.put(clave, Integer.toString(count));
						}
					}
				}
				count++;	
			}
		}
		return claves;
	}

}
