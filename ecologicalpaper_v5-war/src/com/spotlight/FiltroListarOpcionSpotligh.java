package com.spotlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

 


public class FiltroListarOpcionSpotligh  implements FiltroOpcionSpotlight {

	
	public Map<String, Object> getSessionScope() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}
	
	/**
	 * Hacemos un recorrido por los menus e manera recursiva hasta encontrar la
	 * raiz, coincide con idopcion de la lista que traemos en memoria la
	 * añadimos , las demas que no cumplan con esta condicion , seran filtradas.
	 * 
	 */
	public List<SpotLightOpcionDTO> filtrarLista(List<SpotLightOpcionDTO> lista){
		//List<SpotLightOpcionDTO> listaFiltrada = new ArrayList<SpotLightOpcionDTO>();
		
		return lista;
	}
	
//	private List<SpotLightOpcionDTO> getOption(ItemMenu item, List<SpotLightOpcionDTO> lista){
//		List<SpotLightOpcionDTO> listaFiltrada = new ArrayList<SpotLightOpcionDTO>();
//		if(item.getSubMenu()!=null){
//			for(ItemMenu i : item.getSubMenu()){
//				listaFiltrada.addAll(getOption(i, lista));
//			}
//		}else{
//			SpotLightOpcionDTO opcion; 
//			opcion =  getSpotlightOpcion(item.getNumero(), lista);
//			if(opcion!=null){
//				listaFiltrada.add(opcion);
//			}
//		}
//		
//		return listaFiltrada;
//	}
	
	private SpotLightOpcionDTO getSpotlightOpcion(int id, List<SpotLightOpcionDTO> lista){
		for(SpotLightOpcionDTO s : lista){
			if(s.getId_mmoOpcion()==id){
				return s;
			}
		}
		return null;
	}
	
}
