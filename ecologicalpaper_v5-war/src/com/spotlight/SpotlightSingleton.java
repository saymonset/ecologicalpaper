package com.spotlight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpotlightSingleton {
//Logger log = Logger.getLogger(SpotlightSingleton.class);
private static SpotlightSingleton instance = null;
private List<SpotLightOpcionDTO> transacciones;
private Date ultimaVisita;
	
private SpotlightSingleton() {
	super();
}

public static SpotlightSingleton getInstance(){
	if(instance == null)
		instance = new SpotlightSingleton();
	
	return instance;
}

public List<SpotLightOpcionDTO> getTransacciones() {
	//if(ultimaVisita==null || UtilFechas.DiferenciaFechas(new Date(), ultimaVisita)>5){
	if(ultimaVisita==null ){
		this.ultimaVisita = new Date();
		transacciones = cargarOpcionesSpotlight();
	}
	return transacciones;
}


public Date getUltimaVisita() {
	return ultimaVisita;
}

public void setUltimaVisita(Date ultimaVisita) {
	this.ultimaVisita = ultimaVisita;
}

/*
 * 
 * Carga todas las opciones segun el id de aplicacion, 
 * que se van utilizar para la busqueda en el spotlight.
 * 
 * Sabelia Ruiz
 * 
 * */

private List<SpotLightOpcionDTO> cargarOpcionesSpotlight(){
	List<SpotLightOpcionDTO> dataSpotLight = new ArrayList<SpotLightOpcionDTO>();;
	SpotLightOpcionDTO spotLightOpcionDTO = new SpotLightOpcionDTO();
	spotLightOpcionDTO.setPALABRAS_CLAVE("Simon");
	spotLightOpcionDTO.setId_mmoOpcion(5);
	spotLightOpcionDTO.setEtiqueta("Saymon5");
	spotLightOpcionDTO.setDescripcionSpotLight("Hola Mundo5");
	dataSpotLight.add(spotLightOpcionDTO);
	spotLightOpcionDTO = new SpotLightOpcionDTO();
	spotLightOpcionDTO.setPALABRAS_CLAVE("Simon");
	spotLightOpcionDTO.setId_mmoOpcion(4);
	spotLightOpcionDTO.setEtiqueta("Saymon4");
	spotLightOpcionDTO.setDescripcionSpotLight("Hola Mundo4");
	dataSpotLight.add(spotLightOpcionDTO);
	spotLightOpcionDTO = new SpotLightOpcionDTO();
	spotLightOpcionDTO.setPALABRAS_CLAVE("Simon");
	spotLightOpcionDTO.setId_mmoOpcion(3);
	spotLightOpcionDTO.setEtiqueta("Saymon3");
	spotLightOpcionDTO.setDescripcionSpotLight("Hola Mundo3");
	dataSpotLight.add(spotLightOpcionDTO);
	spotLightOpcionDTO = new SpotLightOpcionDTO();
	spotLightOpcionDTO.setPALABRAS_CLAVE("Simon");
	spotLightOpcionDTO.setId_mmoOpcion(2);
	spotLightOpcionDTO.setEtiqueta("Saymon2");
	spotLightOpcionDTO.setDescripcionSpotLight("Hola Mundo2");
	dataSpotLight.add(spotLightOpcionDTO);
	spotLightOpcionDTO = new SpotLightOpcionDTO();
	spotLightOpcionDTO.setPALABRAS_CLAVE("Simon");
	spotLightOpcionDTO.setId_mmoOpcion(1);
	spotLightOpcionDTO.setEtiqueta("Saymon1");
	spotLightOpcionDTO.setDescripcionSpotLight("Hola Mundo1");
	dataSpotLight.add(spotLightOpcionDTO);
	spotLightOpcionDTO = new SpotLightOpcionDTO();
	spotLightOpcionDTO.setPALABRAS_CLAVE("carlos");
	spotLightOpcionDTO.setId_mmoOpcion(6);
	spotLightOpcionDTO.setEtiqueta("carlos");
	spotLightOpcionDTO.setDescripcionSpotLight("Hola Marianel");
	dataSpotLight.add(spotLightOpcionDTO);
	
	
//	try{
//		ConfiguracionProviderDelegate configuracionProviderDelegate = new ConfiguracionProviderDelegate();
//		Login login = (Login) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login");
//		
//		if(login!=null && login.getIusuario()!=null && login.getIusuario().getPerfil()!=null && login.getIusuario().getPerfil().getTipo()!=null){
//			String tipoCliente = login.getIusuario().getPerfil().getTipo();
//			if(tipoCliente.equals("Pospago")){
//				dataSpotLight = configuracionProviderDelegate.dataSpotLight("0022", Constantes.ID_PUNTO_ACCESO);
//			}else if(tipoCliente.equals("Prepago")){
//				dataSpotLight = configuracionProviderDelegate.dataSpotLight("0001", Constantes.ID_PUNTO_ACCESO);
//			}
//		}
//		
//	}catch (Exception e) {
//		log.error(e);
//	}
	return dataSpotLight;
}

}
