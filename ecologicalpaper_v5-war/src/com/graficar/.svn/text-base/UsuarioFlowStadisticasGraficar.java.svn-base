/*
 * UsuarioFlowStadisticasGraficar.java
 *
 * Created on September 8, 2008, 5:59 PM
 */

package com.graficar;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.dias.feriados.DiasFeriadosBean;
import com.ecological.paper.flows.Flow;
import com.ecological.paper.flows.Flow_Participantes;
import com.ecological.paper.usuario.Usuario;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;
import com.ecological.datoscombo.Converdocumento;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_historicoActivoDetalle;
import com.ecological.paper.documentos.Doc_historicoActivoMaestro;
import com.ecological.paper.documentos.Doc_maestro;
import com.util.Utilidades;
import org.jfree.chart.*;

import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Color;
import com.ecological.configuracion.Configuracion;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.documentos.ClienteDocumentoMaestro;
import com.ecological.util.ContextSessionRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 
 * @author simon
 * @version
 */
public class UsuarioFlowStadisticasGraficar extends HttpServlet {
	private ServicioDelegado delegado = new ServicioDelegado();

	private byte[] dataPostgres;
	ContextSessionRequest contextSessionRequest = new ContextSessionRequest();
	ResourceBundle confmessages = ContextSessionRequest
			.getResourceBundleStatic("com.util.resource.ecological_conf");
	ResourceBundle messages = null;// ContextSessionRequest.getResourceBundleStatic("com.ecological.resource.ecologicalpaper");
	private String carpeta_compartida;
	private Configuracion conf = new Configuracion();
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private Doc_historicoActivoMaestro doc_historicoActivoMaestro = new Doc_historicoActivoMaestro();
	private Doc_maestro doc_maestro;
	// historicos

	// private int flujosParaAprobar=0;
	// private int flujosParaRevisar=0;
	// private int firmaRevisado=0;
	// private int firmaAprobado=0;
	// private int firmaRechazado=0;
	private int sinFirma = 0;
	// private int canceladosPorDuenoOrcanceladosAutomaticos=0;
	private int atrazados = 0;
	private int satisfactorios = 0;
	private int sinControlTiempo = 0;
	// private int flujosEnvioParaAprobar=0;
	// private int flujosEnvioParaRevisar=0;
	private Usuario usuario;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String codigo = request.getParameter("codigo") != null ? request
				.getParameter("codigo") : null;

		// if (session != null) {
		if (!"".equalsIgnoreCase(codigo) && codigo != null) {
			// usuario = (Usuario) session.getAttribute("usuario");

			usuario = delegado.find_Usuario(Long.parseLong(codigo));

			// colocamos la nueva seguridad en session
			// contextSessionRequest.clearSession(session,confmessages.getString("usuarioSession"));

			if (usuario != null) {
				// flujosEnvioParaAprobar=0;
				// flujosEnvioParaRevisar=0;
				// flujosParaAprobar=0;
				// flujosParaRevisar=0;
				// firmaRevisado=0;
				// firmaAprobado=0;
				// firmaRechazado=0;
				sinFirma = 0;
				// canceladosPorDuenoOrcanceladosAutomaticos=0;
				atrazados = 0;
				satisfactorios = 0;
				sinControlTiempo = 0;
				getInfDetlladaHist();
				response.setContentType("image/jpeg");
				OutputStream salida = response.getOutputStream();
				JFreeChart grafica = null;
				try {
					messages = contextSessionRequest
							.getResourceBundleGrafico("com.ecological.resource.ecologicalpaper");
					// grafica = gerarGraficoLinha3D("HABITAT", "XXXX", "YYYY");
					if (usuario != null) {
						String nombre = usuario.getNombre() != null ? usuario
								.getNombre() : "";
						String apellido = usuario.getApellido() != null ? usuario
								.getApellido() : "";
						String cargo = usuario.getCargo() != null ? usuario
								.getCargo().getNombre() : "";
						grafica = gerarGraficoBarraVertical3D(
								messages.getString("ultimos") + " "
										+ Utilidades.getMaxregistermostrarall()
										+ " "
										+ messages.getString("flows_trab_doc")
										+ " " + nombre + " " + apellido + "["
										+ cargo + "]",
								messages.getString("coordenadasX"),
								messages.getString("coordenadasY"));
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				int ancho = getParamEntero(request, "ancho", 400);
				int alto = getParamEntero(request, "alto", 300);

				ChartUtilities.writeChartAsJPEG(salida, grafica, ancho, alto);

				salida.close();

			}

		} else {
			System.out.println("SI ES NULA LA SESSION");

		}

	}

	public void getInfDetlladaHist() {
		// BUSCAMOS TODOS LOS FLUJOS QUE A ENVIADO PARA APROBAR O REVISAR EL
		// USUARIO
		Flow fEnviados = new Flow();
		fEnviados.setDuenio(usuario);
		/*
		 * List<Flow>flowsEnviados=delegado.findAllFlowEnviadosByUsuario(fEnviados
		 * ); for(Flow f:flowsEnviados){ if (f!=null && f.getTipoFlujo()!=null
		 * ){ if (
		 * (Long.parseLong(f.getTipoFlujo())==(Utilidades.getEnAprobacion()))||
		 * (Long.parseLong(f.getTipoFlujo())==(Utilidades.getAprobado()) ) ){
		 * 
		 * 
		 * flujosEnvioParaAprobar +=1; }
		 * 
		 * if (
		 * (Long.parseLong(f.getTipoFlujo())==(Utilidades.getEnRevision()))||
		 * (Long.parseLong(f.getTipoFlujo())==(Utilidades.getRevisado()) ) ){
		 * flujosEnvioParaRevisar+=1; } } }
		 */
		// ------------------------fin BUSCAMOS TODOS LOS FLUJOS QUE A ENVIADO
		// PARA APROBAR O REVISAR EL USUARIO
		List<Flow_Participantes> flows_part = delegado
				.findAllFlow_ParticipantesByUsuaurio(usuario);
		if (flows_part != null && !flows_part.isEmpty()) {

			for (Flow_Participantes f_p : flows_part) {

				// clasificamos que firma es

				/*
				 * if (f_p.getFirma().getCodigo() == Utilidades.getRechazado()){
				 * firmaRechazado +=1; }
				 */
				if (f_p.getFirma().getCodigo() == Utilidades.getSinFirmar()) {
					sinFirma += 1;
				}
				/*
				 * if (f_p.getFirma().getCodigo() == Utilidades.getRevisado()){
				 * firmaRevisado+=1; }
				 * 
				 * if (f_p.getFirma().getCodigo() == Utilidades.getAprobado()){
				 * firmaAprobado+=1; } if (f_p.getFirma().getCodigo() ==
				 * Utilidades.getCanceladoByDuenio()){
				 * canceladosPorDuenoOrcanceladosAutomaticos+=1; } if
				 * (f_p.getFirma().getCodigo() ==
				 * Utilidades.getRechazadoAutomatico()){
				 * canceladosPorDuenoOrcanceladosAutomaticos+=1; }
				 */

				// fin clasificar

				// TIPOS DE FLUJOS
				/*
				 * List<Flow> flows= delegado.findAllFlowOfParticipantes( f_p);
				 * if (flows!=null && !flows.isEmpty()){ //BUSCAMOS TODOS LOS
				 * FLUJOS QUE A RECIBIDO PARA APROBAR O REVISAR EL USUARIO
				 * for(Flow f:flows){ //esto se hace con el flow que esta en
				 * participantes, se obtiene //lo de participantes.. if (f!=null
				 * && f.getTipoFlujo()!=null ){ if (
				 * (Long.parseLong(f.getTipoFlujo
				 * ())==(Utilidades.getEnAprobacion()))||
				 * (Long.parseLong(f.getTipoFlujo())==(Utilidades.getAprobado())
				 * ) ){ flujosParaAprobar +=1; }
				 * 
				 * if (
				 * (Long.parseLong(f.getTipoFlujo())==(Utilidades.getEnRevision
				 * ()))||
				 * (Long.parseLong(f.getTipoFlujo())==(Utilidades.getRevisado())
				 * ) ){ flujosParaRevisar+=1; } } } }
				 */

				// ATRAZOS DE HORAS

				List<FlowControlByUsuarioBean> flowControlByUsuarioBeans = delegado
						.find_allControlTimeByFlowParticipAndFlow(f_p);

				if (flowControlByUsuarioBeans != null
						&& !flowControlByUsuarioBeans.isEmpty()) {
					for (FlowControlByUsuarioBean flowControlByUsuarioBean : flowControlByUsuarioBeans) {

						contextSessionRequest
								.calcularMinutosHorasAcumuladas(
										flowControlByUsuarioBean,
										f_p.getParticipante());

						int minutosAsignados = flowControlByUsuarioBean
								.getMinutosAsignados();
						int minutosAcumulados = flowControlByUsuarioBean
								.getMinutosAcumulados();
						int horasAsignada = flowControlByUsuarioBean
								.getHorasAsignadas();
						int horasAcumulada = flowControlByUsuarioBean
								.getHorasAcumuladas();// flowControlByUsuarioBean.getHorasAcumuladas();
						/*
						 * System.out.println(
						 * "----------------------------------------");
						 * System.out
						 * .println("minutosAsignados="+minutosAsignados);
						 * System.out.println("horasAsignada="+horasAsignada);
						 * System
						 * .out.println("minutosAcumulados="+minutosAcumulados);
						 * System.out.println("horasAcumulada="+horasAcumulada);
						 * System
						 * .out.println("----------------------------------------"
						 * );
						 */

						if ((horasAsignada != 0) || (minutosAsignados != 0)) {

							if (((minutosAsignados - minutosAcumulados) < 0)
									&& ((horasAsignada - horasAcumulada) <= 0)) {
								atrazados += 1;

							} else if (((minutosAsignados - minutosAcumulados) >= 0)
									&& ((horasAsignada - horasAcumulada) < 0)) {
								atrazados += 1;

							} else if (((minutosAsignados - minutosAcumulados) <= 0)
									&& ((horasAsignada - horasAcumulada) > 0)) {
								satisfactorios += 1;

							} else if (((minutosAsignados - minutosAcumulados) > 0)
									&& ((horasAsignada - horasAcumulada) >= 0)) {
								satisfactorios += 1;

							} else {
								//
							}

						} else {
							sinControlTiempo += 1;
							System.out.println("sin control de tiempo "
									+ sinControlTiempo);
						}
					}
				}

			}
		}
		// return treeData;
	}

	public void init(ServletConfig config) throws ServletException {

	}

	// public static BufferedImage gerarGraficoBarraVertical3D(String
	// tituloGrafico, String
	public JFreeChart gerarGraficoBarraVertical3D(String tituloGrafico, String

	tituloEixoX, String tituloEixoY) throws Exception {

		BufferedImage buf = null;
		JFreeChart chart = null;
		try {
			messages = contextSessionRequest
					.getResourceBundleGrafico("com.ecological.resource.ecologicalpaper");

			DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

			ArrayList arrayValores = new ArrayList();

			ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
			/*
			 * modeloGraficoItem.setMes(messages.getString(
			 * "flujosenviadosparaaprobar"));
			 * modeloGraficoItem.setProduto(messages
			 * .getString("flujosenviadosparaaprobar"));
			 * 
			 * modeloGraficoItem.setQuantidade((flujosEnvioParaAprobar));
			 * arrayValores.add(modeloGraficoItem);
			 */

			/*
			 * modeloGraficoItem = new ModeloGraficoItem();
			 * modeloGraficoItem.setMes
			 * (messages.getString("flujosenviadospararevisar"));
			 * modeloGraficoItem
			 * .setProduto(messages.getString("flujosenviadospararevisar"));
			 * 
			 * modeloGraficoItem.setQuantidade((flujosEnvioParaRevisar));
			 * arrayValores.add(modeloGraficoItem);
			 */

			/*
			 * modeloGraficoItem = new ModeloGraficoItem();
			 * modeloGraficoItem.setMes
			 * (messages.getString("flujosobtenidoparaaprobar"));
			 * modeloGraficoItem
			 * .setProduto(messages.getString("flujosobtenidoparaaprobar"));
			 * modeloGraficoItem.setQuantidade((flujosParaAprobar));
			 * arrayValores.add(modeloGraficoItem);
			 * 
			 * modeloGraficoItem = new ModeloGraficoItem();
			 * modeloGraficoItem.setMes
			 * (messages.getString("flujosobtenidopararevisar"));
			 * modeloGraficoItem
			 * .setProduto(messages.getString("flujosobtenidopararevisar"));
			 * modeloGraficoItem.setQuantidade((flujosParaRevisar));
			 * arrayValores.add(modeloGraficoItem);
			 */

			/*
			 * modeloGraficoItem = new ModeloGraficoItem();
			 * modeloGraficoItem.setMes(messages.getString("firmorevisado"));
			 * modeloGraficoItem
			 * .setProduto(messages.getString("firmorevisado"));
			 * modeloGraficoItem.setQuantidade((firmaRevisado));
			 * arrayValores.add(modeloGraficoItem);
			 * 
			 * 
			 * modeloGraficoItem = new ModeloGraficoItem();
			 * modeloGraficoItem.setMes(messages.getString("firmoaprobado"));
			 * modeloGraficoItem
			 * .setProduto(messages.getString("firmoaprobado"));
			 * modeloGraficoItem.setQuantidade((firmaAprobado));
			 * arrayValores.add(modeloGraficoItem);
			 * 
			 * modeloGraficoItem = new ModeloGraficoItem();
			 * modeloGraficoItem.setMes(messages.getString("firmorechazado"));
			 * modeloGraficoItem
			 * .setProduto(messages.getString("firmorechazado"));
			 * modeloGraficoItem.setQuantidade((firmaRechazado));
			 * arrayValores.add(modeloGraficoItem);
			 */

			modeloGraficoItem = new ModeloGraficoItem();
			modeloGraficoItem.setMes(messages.getString("flow_sinFirmar"));
			modeloGraficoItem.setProduto(messages.getString("flow_sinFirmar"));
			modeloGraficoItem.setQuantidade((sinFirma));
			arrayValores.add(modeloGraficoItem);

			/*
			 * modeloGraficoItem = new ModeloGraficoItem();
			 * modeloGraficoItem.setMes
			 * (messages.getString("canceladoByDuenio"));
			 * modeloGraficoItem.setProduto
			 * (messages.getString("canceladoByDuenio"));
			 * modeloGraficoItem.setQuantidade
			 * ((canceladosPorDuenoOrcanceladosAutomaticos));
			 * arrayValores.add(modeloGraficoItem);
			 */

			modeloGraficoItem = new ModeloGraficoItem();
			modeloGraficoItem.setMes(messages
					.getString("entiemposatisfactorio"));
			modeloGraficoItem.setProduto(messages
					.getString("entiemposatisfactorio"));
			modeloGraficoItem.setQuantidade((satisfactorios));
			arrayValores.add(modeloGraficoItem);

			modeloGraficoItem = new ModeloGraficoItem();
			modeloGraficoItem.setMes(messages
					.getString("entiemponosatisfactorio"));
			modeloGraficoItem.setProduto(messages
					.getString("entiemponosatisfactorio"));
			modeloGraficoItem.setQuantidade((atrazados));
			arrayValores.add(modeloGraficoItem);

			modeloGraficoItem = new ModeloGraficoItem();
			modeloGraficoItem.setMes(messages.getString("sincontroldetiempo"));
			modeloGraficoItem.setProduto(messages
					.getString("sincontroldetiempo"));
			modeloGraficoItem.setQuantidade((sinControlTiempo));
			arrayValores.add(modeloGraficoItem);

			Iterator iterator = arrayValores.iterator();

			while (iterator.hasNext()) {

				ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();

				defaultCategoryDataset.addValue(modelo.getQuantidade(),

				modelo.getProduto(), modelo.getMes().substring(0, 3));

			}

			chart = ChartFactory.createBarChart3D(tituloGrafico, tituloEixoX,

			tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,

			true, false, false);

			chart.setBorderVisible(true);

			chart.setBorderPaint(Color.black);

			buf = chart.createBufferedImage(400, 250);

		} catch (Exception e) {

			throw new Exception(e);

		}

		// return buf;
		return chart;

	}

	int getParamEntero(HttpServletRequest request, String pNombre, int pDefecto) {
		String param = request.getParameter(pNombre);

		if (param == null || param.compareTo("") == 0) {
			return pDefecto;
		}

		return Integer.parseInt(param);

	}

	/**
	 * Processes requests for both HTTP GET and POST methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */

	/**
	 * Handles the HTTP GET method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
     *
     *
     *
     */

	public static JFreeChart gerarGraficoBarraVertical(String tituloGrafico,
			String

			tituloEixoX, String tituloEixoY) throws Exception {

		BufferedImage buf = null;
		JFreeChart chart = null;
		try {
			ArrayList arrayValores = new ArrayList();
			for (int i = 0; i < 3; i++) {
				ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
				modeloGraficoItem.setMes("Mayo");
				modeloGraficoItem.setProduto("Ppsicola" + i);
				modeloGraficoItem.setQuantidade(15 + i * 7);
				arrayValores.add(modeloGraficoItem);
			}

			DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

			Iterator iterator = arrayValores.iterator();

			while (iterator.hasNext()) {

				ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();

				defaultCategoryDataset.addValue(modelo.getQuantidade(),

				modelo.getProduto(), modelo.getMes().substring(0, 3));

			}

			chart = ChartFactory.createBarChart(tituloGrafico, tituloEixoX,

			tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,
					true, false, false);

			chart.setBorderVisible(true);

			chart.setBorderPaint(Color.black);

			buf = chart.createBufferedImage(400, 250);

		} catch (Exception e) {

			throw new Exception(e);

		}

		return chart;

	}

	/**
	 * 
	 * Gera um Grafico de Linhas
	 * 
	 */

	public static JFreeChart gerarGraficoLinha(String tituloGrafico,
			String tituloEixoX,

			String tituloEixoY) throws Exception {
		JFreeChart chart = null;
		BufferedImage buf = null;

		try {
			ArrayList arrayValores = new ArrayList();
			for (int i = 0; i < 3; i++) {
				ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
				modeloGraficoItem.setMes("Mayo");
				modeloGraficoItem.setProduto("Ppsicola" + i);
				modeloGraficoItem.setQuantidade(15 + i * 7);
				arrayValores.add(modeloGraficoItem);
			}
			DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

			Iterator iterator = arrayValores.iterator();

			while (iterator.hasNext()) {

				ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();

				defaultCategoryDataset.addValue(modelo.getQuantidade(),

				modelo.getProduto(), modelo.getMes().substring(0, 3));

			}

			chart = ChartFactory.createLineChart(tituloGrafico, tituloEixoX,

			tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,

			true, false, false);

			chart.setBorderVisible(true);

			chart.setBorderPaint(Color.black);

			buf = chart.createBufferedImage(400, 250);

		} catch (Exception e) {

			throw new Exception(e);

		}

		return chart;

	}

	/**
	 * 
	 * Gera um grafico de linhas 3D
	 * 
	 */

	public static JFreeChart gerarGraficoLinha3D(String tituloGrafico, String

	tituloEixoX, String tituloEixoY) throws Exception {

		BufferedImage buf = null;
		JFreeChart chart = null;
		try {
			ArrayList arrayValores = new ArrayList();
			for (int i = 0; i < 3; i++) {
				ModeloGraficoItem modeloGraficoItem = new ModeloGraficoItem();
				modeloGraficoItem.setMes("Mayo");
				modeloGraficoItem.setProduto("Ppsicola" + i);
				modeloGraficoItem.setQuantidade(15 + i * 7);
				arrayValores.add(modeloGraficoItem);
			}
			DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

			Iterator iterator = arrayValores.iterator();

			while (iterator.hasNext()) {

				ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();

				defaultCategoryDataset.addValue(modelo.getQuantidade(),

				modelo.getProduto(), modelo.getMes().substring(0, 3));

			}

			chart = ChartFactory.createLineChart3D(tituloGrafico, tituloEixoX,

			tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,

			true, false, false);

			chart.setBorderVisible(true);

			chart.setBorderPaint(Color.black);

			buf = chart.createBufferedImage(400, 250);

		} catch (Exception e) {

			throw new Exception(e);

		}

		return chart;

	}

}