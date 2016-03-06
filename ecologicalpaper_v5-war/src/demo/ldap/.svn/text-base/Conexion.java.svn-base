package demo.ldap;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;

import demo.ldap.LDAPTest.LDAP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * @author christian
 */

public class Conexion {

	private int ldapPort;
	private int ldapVersion;
	private LDAPConnection lc;
	private String login;
	private String ldapHost = "localhost";
	private String ATTRIBUTE_FOR_USER = "sAMAccountName";
	private String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
	private String MY_HOST = "ldap://localhost:389";

	public Conexion() {
	}

	public static void main(String[] args) {
		Conexion c = new Conexion();
		// c. ConexionUser("", "");
		Conexion Aut = new Conexion();
		if (Aut.Authenticate("example.com",
				"cn=Robert Smith,ou=people,dc=example,dc=com", "rJsmitH") != null) {
			System.out.println("Autenticado");
		} else {
			System.out.println("No Auntenticado");
		}

	}

	public void CerrarConLDAP(LDAPConnection lc) {
		try {
			lc.disconnect();
			System.out.println("Conexion Cerrada Correctamente...");
		} catch (LDAPException ex) {
			ex.printStackTrace();
			/*Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null,
					ex);*/
		}

	}

	public String Authenticate(String domain, String user, String pass) {
		Hashtable env = new Hashtable();
		if (pass.compareTo("") == 0 || user.compareTo("") == 0)
			return null;
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
		env.put(Context.PROVIDER_URL, MY_HOST);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, new String(user));
		env.put(Context.SECURITY_CREDENTIALS, new String(pass));
		try {
			DirContext ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}

		return user;
	}

}