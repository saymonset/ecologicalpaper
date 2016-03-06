package com.ecological.mailBackend;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ecological.configuracion.Configuracion;
import com.ecological.paper.flows.Flow;
import com.util.EncryptorMD5;

public class MandarMail {

	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private String serverIp;// encriptado
	private String smtpClave;// encriptado
	private String smtpUsuario;
	private String serverPuerto;
	private String puerto;
	private Flow flow;

	public MandarMail() {
	}
 

	public void send(String to, String content,
			List<Configuracion> configuraciones,Flow flow) throws AddressException,
			MessagingException {
		
		try {
			System.out.println("--------------en el backend-----------------");
			String from = "invitado@ecologicalpaper.com"; 

		    this.flow=flow;
			this.configuraciones = configuraciones; 
			Configuracion conf = null;
			if (configuraciones != null && configuraciones.size() > 0) {
			 
				conf = configuraciones.get(0);
				swPostgresVieneDeConfiguracion = conf.isBdpostgres();
				puerto=conf.getPuerto();
				swConfiguracionHayData = true;
				smtpUsuario=conf.getSmtpUsuario();
				serverIp = EncryptorMD5.decrypt(EncryptorMD5.getKey(),
						conf.getServerIp());
				 
				smtpClave=EncryptorMD5.decrypt(EncryptorMD5.getKey(),
						conf.getSmtpClave());
				
	

				serverPuerto = conf.getServerPuerto();

				String url = serverIp + ":" + serverPuerto;
				if (flow !=null){
					url +="/index.jsf?flow="+flow.getCodigo();
				}

				content += "  http://" + url;
			 

				from = conf.getMailCliente() != null ? conf.getMailCliente()
						: "admin@ecologicalpaper.com";
				if ("".equalsIgnoreCase(from)) {
					from = "admin@ecologicalpaper.com";
				}
			}
			ResourceBundle messages;
			ResourceBundle confmessages;
			Locale locale = Locale.getDefault();
			messages = ResourceBundle.getBundle(
					"com.ecological.resource.ecologicalpaper", locale);
			confmessages = ResourceBundle.getBundle(
					"com.util.resource.ecological_conf", locale);

			// Create a mail session

			System.out.println("from=" + from);
			java.util.Properties props = new java.util.Properties();
			 
			if (swConfiguracionHayData) {
				//ECOLOGICAL INICIO
//				props.put("mail.smtp.host", conf.getSmtpHost());
//				props.put("mail.smtp.port", "" + conf.getPuerto());
//				props.put("mail.smtp.user", smtpUsuario);
//				props.put("mail.smtp.auth", "true");
				//ECOLOGICAL FIN
				puerto=conf.getPuerto();
				System.out.println("por gmail...................................................");
				//GMAIL INICIO
				
				props.put("mail.smtp.host", conf.getSmtpHost());
				props.put("mail.smtp.port", conf.getPuerto());
//				props.put("mail.smtp.host", "smtp.gmail.com");
//				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				
				
				//GMAIL FIN

			} else {
				props.put("mail.smtp.host", confmessages.getString("smtpHost"));
				props.put(
						"mail.smtp.port",
						""
								+ Integer.parseInt(confmessages
										.getString("smtpPort")));
				System.out.println("smtpHost="
						+ confmessages.getString("smtpHost"));
				System.out.println("smtpPort="
						+ confmessages.getString("smtpPort"));
			}
			String subject = messages.getString("envio_mail2");
			if (flow!=null){
				try {
					StringBuilder str = new StringBuilder("");
					str.append(flow.getDuenio().getEmpresa().getNombre() +", " +flow.getDoc_detalle().getDoc_maestro().getNombre()+"->"+flow.getDoc_detalle().getDoc_maestro().getConsecutivo()+"");
					 subject = str.toString();
				} catch (Exception e) {
					 subject = messages.getString("envio_mail2");
				}
			}
		
			 
		 
			System.out.println("content =" + content);
			System.out.println("to=" + to);
			if (props.getProperty("smtpPort") == null) {
				props.put("mail.smtp.port", puerto);
			}
			 
			props.put("mail.transport.protocol", "smtp");
 
			
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(smtpUsuario, smtpClave);
						}
					});

			

			boolean swChismoso = true;
			StringTokenizer separarCorreos = new StringTokenizer(to, ",");
			String mail;
			String url="";
			//contentAux conservara siempre el valorn original de content que es la url a ecological
			String contentAux=content;
			while (separarCorreos.hasMoreElements()) {
				//contentAux conservara siempre el valorn original de content que es la url a ecological
				content=contentAux;
				mail = (String) separarCorreos.nextElement();
				System.out.println("mail=" + mail);
				Address[] dirTo = getEmails(to);
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
						mail));
				msg.setSubject(subject);
				if (flow !=null){
					String parametro ="&mail="+mail;
					content+=parametro;
					msg.setText(content);
				}else{
					msg.setText(content);
				}
				
				// Send the message
				try {
					// Lo enviamos.
					Transport t = session.getTransport("smtp");
					System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
					System.out.println("smtpUsuario="+smtpUsuario);
				//	System.out.println("smtpClave="+smtpClave);
					System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
					t.connect(smtpUsuario, smtpClave);
					t.sendMessage(msg,msg.getAllRecipients());

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("auxiliar ***mail---");
					auxiliar(conf.getSmtpHost(), to, from, subject, content,
							confmessages);
					System.out.println("se mando el mails-");

				}

				if (swChismoso) {// lo manda a ecologicalpaper a ver que esta
									// pasando ..
					swChismoso = false;
					mail = from;
					System.out.println("mail=" + mail);
					dirTo = getEmails(to);
					msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(from));
					msg.setRecipient(Message.RecipientType.TO,
							new InternetAddress(mail));
					msg.setSubject(subject);
					msg.setText(content);
					// Send the message
					try {
						// Lo enviamos.
						Transport t = session.getTransport("smtp");
						t.connect(smtpUsuario, smtpClave);
						t.sendMessage(msg,msg.getAllRecipients());
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private static synchronized Address[] getEmails(String values)
			throws AddressException {
		Address[] emails = null;
		int items = 0;
		if (values != null && values.length() > 0) {
			StringTokenizer st = new StringTokenizer(values, ",");
			items = st.countTokens();
			emails = new Address[items];
			int cont = 0;
			while (st.hasMoreTokens()) {
				String dir = st.nextToken();
				emails[cont] = new InternetAddress(dir);
				cont++;
			}
		}
		return emails;
	}

	public void auxiliar(String host1, String to1, String from1,
			String subject1, String messageText1, ResourceBundle confmessages) {

		try {
		 
			// Propiedades de la conexión
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "mail.ecologicalpaper.com");
			props.setProperty("mail.smtp.port", puerto);
			props.setProperty("mail.smtp.user", smtpUsuario);
			props.setProperty("mail.smtp.auth", "true");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props, null);

			// Construimos el mensaje
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("yo@yo.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"admin@ecologicalpaper.com"));
			message.setSubject("Hola");
			message.setText("Mensajito con Java Mail" + "de los buenos."
					+ "poque si");

			// Lo enviamos.
			Transport t = session.getTransport("smtp");
			t.connect(smtpUsuario, smtpClave);
			t.sendMessage(message, message.getAllRecipients());

			// Cierre.
			t.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * try{ send("mail.cantv.net", 25, "Saymon_set@hotmail.com",
		 * "oracle_fedora@yahoo.com", "prueba de mandar mail",
		 * "este es el cuerpo del mensaje?");
		 * System.out.println("Mensaj enviado...."); }catch(Exception e){
		 * e.printStackTrace(); System.out.print(e); }
		 */

	}

	public String getSmtpClave() {
		return smtpClave;
	}

	public void setSmtpClave(String smtpClave) {
		this.smtpClave = smtpClave;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

}
