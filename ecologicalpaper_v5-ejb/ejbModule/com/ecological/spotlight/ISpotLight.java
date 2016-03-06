package com.ecological.spotlight;

import java.io.Serializable;
import java.util.List;

 

public interface ISpotLight extends Serializable{
	/**
	 * Metodo que modifica un spotLight
	 * 
	 * @param obj
	 *            El spotLightobjeto con todas las configuraciones.
	 * @param usuario
	 *            Usuario que realiza la operacion.
	 * @param Ip
	 *            Ip del usuario que realiza la modificaci�n para registrar en
	 *            bit�cora
	 * @return Un objeto tipo <code>SpotLightBean</code> que contiene la el
	 *         spotLight configurado.
	 * @throws RuntimeException
	 */
	public SpotLightOpcion modificarSpotLight(SpotLightOpcion obj, String usuario,
			String Ip) throws Exception;

	/**
	 * Metodo que obtiene una lista de SpotLightBean configuradas en el sistema.
	 * 
	 * @param clave
	 *            filtro por clave de spotLight.
	 * 
	 * @return Un objeto tipo <code>List<SpotLightBean> </code> que contiene la
	 *         lista de los SpotLightBean configurados.
	 * @throws Exception
	 */
	public List<SpotLightOpcion> obtenerSpoltLight(String clave) throws Exception;

	/**
	 * Metodo que configura una nueva categoria SVA.
	 * 
	 * @param usuario
	 *            Usuario que realiza la operacion.
	 * @param Ip
	 *            Ip del usuario que realiza la modificaci�n para registrar en
	 *            bit�cora
	 * @return Un objeto tipo <code>SpotLightBean </code> que contiene el obj
	 *         configurado.
	 * @throws Exception
	 */
	public SpotLightOpcion ingresarSpotLightBean(String usuario, String Ip,
			SpotLightOpcion obj) throws Exception;

	/**
	 * Metodo que borra una SVA de la categoria.
	 * 
	 * @param obj
	 *            Es el objeto spotlight que se borrara de la aplicacion
	 * @param usuario
	 *            Usuario que realiza la operacion.
	 * @param Ip
	 *            Ip del usuario que realiza la modificaci�n para registrar en
	 *            bit�cora
	 * @throws Exception
	 */
	public void borrarSpotLightBean(SpotLightOpcion obj, String usuario, String Ip)
			throws Exception;

	/**
	 * Metodo que busca un spotlight a traves de un id
	 * 
	 * @param idSpotLight
	 *            Id del spotLight a buscar
	 * @return Un <code>List<SpotLightBean></code>
	 * @throws Exception
	 */
	public ImagenSpotLight obtenerSpotLight(long idSpotLight) throws Exception;

	/**
	 * Metodo que actualiza un SpotLightBean obj.
	 * 
	 * @param obj
	 *            Es el objeto spotlight que se va a actualizar
	 * @param Usuario
	 *            Usuario que realiza la operaci�n
	 * @param Ip
	 *            Ip del usuario que realiza la modificaci�n para registrar en
	 *            bit�cora
	 * @throws Exception
	 */
	public void updateSpotLightBean(SpotLightOpcion obj, String Usuario, String Ip)
			throws Exception;
	
	public List<?> dataSpotLight(String ID_APLICACION, long ID_MMOCANALATENCION);	
	public List<SpotLightOpcion> dataSpotLight();

}
