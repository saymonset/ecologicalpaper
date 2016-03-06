package com.ldap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.ecological.configuracion.Configuracion;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.cliente.seguridad.ClienteSeguridad;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.roles_usuarios.Roles_Usuarios;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.tree.Tree;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.EncryptorMD5;
import com.util.Utilidades;

public class ObtenerDataWithLDAP extends ContextSessionRequest {
	private String ldapactivedirectoryhost;
	private String ldapdominiodc;

	private String ldaporganizacion;
	private String ldapUsuarioAdmin;
	private String ldapPasswordAdmin;
	private String uid = "";

	private boolean swWindows;
	private boolean swLdapdominiodc;

	private String nameServer;
	private boolean swPostgresVieneDeConfiguracion;
	private boolean swConfiguracionHayData;
	private List<Configuracion> configuraciones = new ArrayList<Configuracion>();
	private Configuracion conf = new Configuracion();
	private ServicioDelegado delegado = new ServicioDelegado();
	private ResourceBundle messages = super
			.getResourceBundle("com.ecological.resource.ecologicalpaper");

	public ObtenerDataWithLDAP(Usuario usu) {
		traerDatabD();
	}

	public ObtenerDataWithLDAP() {
		traerDatabD();
	}

	public boolean existeEnLDAP(String usuario, String clave) {
		boolean existe = false;

		try {
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);

			DirContext context = conectarseLDAP();
			String dn = ldaporganizacion;
			if (context != null) {
				NamingEnumeration enumeration = null;

				if (swWindows) {

					enumeration = context.search(dn, "(sAMAccountName="
							+ usuario + ")", ctrl);

				} else {

					enumeration = context.search(dn, "(uid=" + usuario + ")",
							ctrl);
				}

				String cnEncontrado = null;

				boolean swEncontro = false;

				while (!swEncontro && enumeration.hasMoreElements()) {
					SearchResult match = (SearchResult) enumeration
							.nextElement();

					Attributes attrs = match.getAttributes();
					NamingEnumeration e = attrs.getAll();
					while (!swEncontro && e.hasMoreElements()) {

						Attribute attr = (Attribute) e.nextElement();
						for (int i = 0; (i < attr.size() && !swEncontro); i++) {
							if (swWindows) {
								if (Utilidades.getDistinguishednamewin()
										.equalsIgnoreCase(attr.getID())) {
									cnEncontrado = (String) attr.get(i);
									swEncontro = true;

								}
							} else {
								if (Utilidades.getCn().equalsIgnoreCase(
										attr.getID())) {
									cnEncontrado = (String) attr.get(i);
									swEncontro = true;

								}
							}

						}
					}
					// cn=John Smith,ou=people,dc=example,dc=com
					String usuarioBuscar = "";
					if (swWindows) {
						usuarioBuscar = cnEncontrado;
					} else {
						usuarioBuscar = "cn=" + cnEncontrado + ","
								+ dn.toString();
					}

					context = obtenerDirContext(usuarioBuscar, clave,
							ldapactivedirectoryhost);
					if (context != null) {
						existe = true;
					} else {
						existe = false;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return existe;
	}

	public void llenarBuscarBdWithLDAP(Usuario usuarioBuscar,
			boolean esRootEjecuta) {
		String dnDominio = "";

		if (esRootEjecuta) {
			dnDominio = ldapdominiodc;// dominio de admin, para leer todos los
			// recursos
		} else {
			dnDominio = ldaporganizacion;// solo para leer el usuario
		}

		try {
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			DirContext context = conectarseLDAP();
			if (context != null) {
				NamingEnumeration enumeration = null;
				if (esRootEjecuta) {
					enumeration = context.search(dnDominio, "(objectclass=*)",
							ctrl);
				} else {
					if (swWindows) {
						enumeration = context.search(dnDominio,
								"(sAMAccountName=" + usuarioBuscar.getLogin()
										+ ")", ctrl);

					} else {
						enumeration = context.search(dnDominio, "(uid="
								+ usuarioBuscar.getLogin() + ")", ctrl);
					}

				}
				Tree empresa = new Tree();

				// solo una sola data de cada campo se recoje
				Calendar cal = Calendar.getInstance();
				Tree tree = new Tree();
				empresa = usuarioBuscar.getEmpresa();

				while (enumeration.hasMoreElements()) {
					SearchResult match = (SearchResult) enumeration
							.nextElement();
					Attributes attrs = match.getAttributes();
					NamingEnumeration e = attrs.getAll();
					Usuario usuario = new Usuario();
					usuario.setStatus(true);

					Role role = new Role();
					role.setStatus(true);
					String grupo = "";
					String empresaNew = "";
					while (e.hasMoreElements()) {
						Attribute attr = (Attribute) e.nextElement();
						for (int i = 0; i < attr.size(); i++) {
							// if (i > 0)
							// System.out.println(", ");

							// DC
							if (Utilidades.getDc().equalsIgnoreCase(
									attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									empresaNew = (String) attr.get(i);
								}
							}

							if (Utilidades.getCompany().equalsIgnoreCase(
									attr.getID())) {
								if (isEmptyOrNull(empresaNew)) {
									empresaNew = (String) attr.get(i);
								}
							}
							// ou
							if (Utilidades.getOu().equalsIgnoreCase(
									attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									grupo = (String) attr.get(i);
								}
							}
							if (Utilidades.getDepartment().equalsIgnoreCase(
									attr.getID())) {
								if (isEmptyOrNull(grupo)) {
									grupo = (String) attr.get(i);
								}
							}

							// SN
							if (Utilidades.getSn().equalsIgnoreCase(
									attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									usuario.setApellido((String) attr.get(i));
								}

							}
							// UID

							if ("sAMAccountName".equalsIgnoreCase(attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									usuario.setLogin((String) attr.get(i));

								}
							}
							if (uid.equalsIgnoreCase(attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {

									usuario.setLogin((String) attr.get(i));
								}
							}

							// CN
							if (Utilidades.getCn().equalsIgnoreCase(
									attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									usuario.setNombre((String) attr.get(i));
								}
							}
							if (usuario.getNombre() == null) {
								usuario.setNombre("nombre?");
							}
							if (Utilidades.getMail().equalsIgnoreCase(
									attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									usuario.setMail_principal((String) attr
											.get(i));
								}

							}
							if (Utilidades.getTelephonenumber()
									.equalsIgnoreCase(attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									usuario.setTelefono_ofic((String) attr
											.get(i));
								}
							}

							if (Utilidades.getStreetAddress().equalsIgnoreCase(
									attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									usuario.setDireccion((String) attr.get(i));
								}

							}

							if (Utilidades.getHomePhone().equalsIgnoreCase(
									attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									usuario.setTelefono_casa((String) attr
											.get(i));
								}

							}
							if (Utilidades.getOtherTelephone()
									.equalsIgnoreCase(attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									if (!isEmptyOrNull(usuario
											.getTelefono_ofic())) {
										usuario.setTelefono_ofic(usuario
												.getTelefono_ofic()
												+ "," + (String) attr.get(i));
									} else {
										usuario.setTelefono_ofic((String) attr
												.get(i));
									}

								}

							}
							if (Utilidades.getMobile().equalsIgnoreCase(
									attr.getID())) {
								if (null != attr.get(i)
										&& !isEmptyOrNull((String) attr.get(i))) {
									usuario.setTelefono_cel((String) attr
											.get(i));
								}

							}

							break;
							// HCEMO PRIMER RECORRIDO INTERNO DE LOS VALORES
							// DE CADA
							// ATRIBUTO DE LAS PERSONA
						}
						// HACEMOS EL RECORRIDO POR LA PERSONA O OBJETO
					}
					// GRABAMOS EL OBJETO O LA PERSONA
					Tree principal = new Tree();
					Tree area = new Tree();
					Tree cargo = new Tree();

					tree = new Tree();
					tree.setTiponodo(Utilidades.getNodoPrincipal());
					tree.setNombre("SUCURSALLDAP");
					List<Tree> listaTree = delegado.finTreeByNameAndTipo(tree);
					if (listaTree == null || listaTree.isEmpty()) {
						tree.setTiponodo(Utilidades.getNodoPrincipal());
						tree.setNombre("SUCURSALLDAP");
						tree.setDeBaseToUsuario(true);
						tree.setFecha_creado(cal.getTime());
						tree.setNodopadre(empresa.getNodo());
						tree.setMaquina(null);
						tree.setStatus(true);
						delegado.crearNuevoTree(tree,usuarioBuscar);
						principal = delegado.obtenerNodo(tree.getNodo());
					} else {
						for (Tree t : listaTree) {
							principal = t;
							break;
						}
					}

					usuario.setPrincipal(principal);

					tree = new Tree();
					tree
							.setNombre(grupo != null && !isEmptyOrNull(grupo) ? grupo
									: "UNIDADESLDAP");
					tree.setTiponodo(Utilidades.getNodoArea());
					listaTree = delegado.finTreeByNameAndTipo(tree);
					if (listaTree == null || listaTree.isEmpty()) {
						tree.setTiponodo(Utilidades.getNodoArea());
						tree.setDeBaseToUsuario(true);
						tree.setFecha_creado(cal.getTime());
						tree.setMaquina(null);
						tree.setNodopadre(principal.getNodo());
						tree.setStatus(true);
						delegado.crearNuevoTree(tree,usuarioBuscar);
						area = delegado.obtenerNodo(tree.getNodo());

					} else {
						for (Tree t : listaTree) {
							area = t;

							break;
						}
					}

					usuario.setArea(area);
					if (usuario.getLogin() != null) {
						usuario.setEmpresa(empresa);

						Usuario usuarioExiste = delegado
								.findLoginOrMailExistaLdap(usuario);
						if (usuarioExiste != null) {
							if (!esRootEjecuta) {
								System.out.println("actualizando pasdsworsd:"
										+ usuarioBuscar.getPassword());
								// yA VIENBE ENCRIPTADO
								usuarioExiste.setPassword(usuarioBuscar
										.getPassword());
								delegado.edit(usuarioExiste);
							}

						} else {
							tree = new Tree();
							tree.setNombre("CARGOLDAP");
							tree.setTiponodo(Utilidades.getNodoCargo());

							tree.setDeBaseToUsuario(true);
							tree.setFecha_creado(cal.getTime());

							tree.setMaquina(null);
							tree.setNodopadre(area.getNodo());
							tree.setStatus(true);
							delegado.crearNuevoTree(tree,usuarioBuscar);

							cargo = delegado.obtenerNodo(tree.getNodo());
							usuario.setCargo(cargo);

							usuario.setFecha_creado(new Date());
							usuario.setStatus(true);
							// inicializamos el password con el usuario que se
							// valla a crear
							if (!esRootEjecuta) {
								// yA VIENBE ENCRIPTADO
								usuario
										.setPassword(usuarioBuscar
												.getPassword());
							} else {
								usuario.setPassword(EncryptorMD5
										.getMD5("123456"));
							}
							// System.out.println(usuario.getNombre());

							delegado.create(usuario);

							// damos la nueva seguridad a dichoo nodo del

							// seguridad al cargo nodo
							ClienteSeguridad clienteSeguridad = new ClienteSeguridad();

							Seguridad_User_Lineal seguridad_User_Lineal = new Seguridad_User_Lineal();
							seguridad_User_Lineal.setUsuario(usuario);
							seguridad_User_Lineal.setTree(usuario.getCargo());
							clienteSeguridad
									.darSeguridadNodo(seguridad_User_Lineal);
							// nuevo usuario
							// creamos una carpeta
							Tree newCarpeta = new Tree();
							newCarpeta.setDeBaseToUsuario(false);
							newCarpeta.setDescripcion(messages
									.getString("misdocumentos"));
							newCarpeta.setFecha_creado(new Date());
							newCarpeta.setMaquina(super.getMaquinaConectada());
							newCarpeta.setNodopadre(usuario.getCargo()
									.getNodo());
							newCarpeta.setNombre(messages
									.getString("misdocumentos"));
							newCarpeta.setPrefix(messages
									.getString("misdocumentos"));
							newCarpeta.setTiponodo(Utilidades.getNodoCarpeta());
							delegado.crearNuevoTree(newCarpeta,usuarioBuscar);

							seguridad_User_Lineal = new Seguridad_User_Lineal();
							seguridad_User_Lineal = new Seguridad_User_Lineal();
							seguridad_User_Lineal.setUsuario(usuario);
							seguridad_User_Lineal.setTree(newCarpeta);
							clienteSeguridad
									.darSeguridadNodo(seguridad_User_Lineal);
							// fin crear carpeta

							// creamos un rol del suuario o grçupo
							role.setNombre(grupo != null ? grupo
									: "UNIDADESLDAP");
							role.setStatus(true);
							role.setEmpresa(usuario.getEmpresa());
							role = delegado.findRole(role);
							if (role == null) {
								role = new Role();
								role.setNombre(grupo != null ? grupo
										: "UNIDADESLDAP");
								role.setStatus(true);
								role.setEmpresa(usuario.getEmpresa());
								delegado.create(role);

								Roles_Usuarios roles_Usuarios = new Roles_Usuarios();
								roles_Usuarios.setRole(role);
								roles_Usuarios.setUsuario(usuario);

								delegado.create(roles_Usuarios);
							}

						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean swConectaAdmin(boolean swAdmin, String login)
			throws NamingException {
		DirContext context = conectarseLDAP();

		if (context != null) {
			if (swAdmin) {
				return true;
			} else {
				SearchControls ctrl = new SearchControls();
				ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
				NamingEnumeration enumeration = null;
				String dnDominio = ldaporganizacion;// solo para leer el usuario
				if (swWindows) {
					enumeration = context.search(dnDominio, "(sAMAccountName="
							+ login + ")", ctrl);
					if (enumeration != null && enumeration.hasMoreElements()) {
						return true;
					}

				} else {

					enumeration = context.search(dnDominio, "(uid=" + login
							+ ")", ctrl);
					return true;
				}

			}

		}
		return false;
	}

	public DirContext conectarseLDAP() {

		// traerDatabD();
		String dnDominio = ldapdominiodc;
		String password = ldapPasswordAdmin;
		String userAdminldap = ldapUsuarioAdmin;

		if (null != dnDominio && !isEmptyOrNull(dnDominio)) {
			String userName = "cn=" + userAdminldap + ","
					+ dnDominio.toString();
			try {

				System.out.println("Conectado--");
				DirContext context = obtenerDirContext(userName, password,
						ldapactivedirectoryhost);
				return context;
			} catch (Exception e) {
				System.out.println(e.toString());

			}
		}
		return null;
	}

	private DirContext obtenerDirContext(String userName, String password,
			String ldapactivedirectoryhost) {
		DirContext context = null;
		try {
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://" + ldapactivedirectoryhost
					+ ":389");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, userName);
			env.put(Context.SECURITY_CREDENTIALS, password);

			context = new InitialDirContext(env);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return context;

	}

	public void traerDatabD() {
		delegado = new ServicioDelegado();
		// siempre va hacer para la primera...
		configuraciones = delegado.find_allConfiguracion();
		if (configuraciones != null && configuraciones.size() > 0) {

			conf = configuraciones.get(0);
			// OBTENEMOS EL DOMINIO
			ldapdominiodc = conf.getLdapdominiodc();

			// EL DIRECTORIO DEL HOST
			ldapactivedirectoryhost = conf.getLdapactivedirectoryhost();
			// para formar el CN
			ldaporganizacion = conf.getLdaporganizacion();
			// EL USUARIO ADMIN
			ldapUsuarioAdmin = conf.getLdapUsuarioAdmin();
			// PASSWORD ADMIN,ESTA ENCRIPTADA
			if ((conf.getLdapPasswordAdmin() != null && conf
					.getLdapPasswordAdmin().toString().length() > 15)) {
				ldapPasswordAdmin = EncryptorMD5.decrypt(EncryptorMD5.getKey(),
						conf.getLdapPasswordAdmin());
			}
			// verificamos si es windows
			swWindows = conf.isSwWindows();
			if (swWindows) {
				uid = "sAMAccountName";
			} else {
				uid = Utilidades.getUid();
			}

			swLdapdominiodc = ((null != ldapdominiodc && !isEmptyOrNull(ldapdominiodc)) && (null != ldapactivedirectoryhost && !isEmptyOrNull(ldapactivedirectoryhost)));

			swPostgresVieneDeConfiguracion = conf.isBdpostgres();
			swConfiguracionHayData = true;
		}

	}

}
