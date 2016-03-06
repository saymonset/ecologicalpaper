package demo.ldap;

import java.beans.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.novell.ldap.LDAPSearchResults;
import com.util.Utilidades;

import java.lang.*;
import java.util.*;

public class ObtenerDN {
	public static void main(String[] args) {

	 
		try {
			String dominio = "dc=estrellitados, dc=com";
			
			String userName = "CN=Administrador,CN=Users,DC=estrellitados,DC=com";
			String password = "aA12760187";
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://localhost:389");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, userName);
			env.put(Context.SECURITY_CREDENTIALS, password);
			DirContext context = new InitialDirContext(env);
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			System.out.println("Conectado--");

			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration enumeration = context.search("dc=estrellitados,dc=com",
					"(sAMAccountName=simon)", ctrl);
					 //windows->(sAMAccountName=simon)", ctrl);
//					"ldap-active directory->(uid=simon)", ctrl);
					//ldaplinux->"(uid=rjsmith)", ctrl);
			//
			// NamingEnumeration enumeration =
			// context.search("DC=example,DC=com",
			// "(objectclass=*)", ctrl);
			String cn = null;
			String ou = null;
			boolean swEncontro=false;

			while (!swEncontro && enumeration.hasMoreElements()) {
				System.out
						.println("--------------------------------nuevo----------------ini------");
				SearchResult match = (SearchResult) enumeration.nextElement();
				Attributes attrs = match.getAttributes();
				NamingEnumeration e = attrs.getAll();
				while (!swEncontro && e.hasMoreElements()) {
					Attribute attr = (Attribute) e.nextElement();
					for (int i = 0; (i < attr.size() && !swEncontro); i++) {
						System.out.println("------------lo qje venia------------------");
						if (Utilidades.getDc().equalsIgnoreCase(
								attr.getID())) {
							System.out.println("Empresa:"+(String) attr.get(i));
						}

						if (Utilidades.getCompany().equalsIgnoreCase(
								attr.getID())) {
							System.out
									.println("company:" + attr.get(i));
						}
						// ou
						if (Utilidades.getOu().equalsIgnoreCase(
								attr.getID())) {
							System.out.println("grupo:" + attr.get(i));
						}
						if (Utilidades.getDepartment()
								.equalsIgnoreCase(attr.getID())) {
							System.out.println("department:"
									+ attr.get(i));
						}

						// SN
						if (Utilidades.getSn().equalsIgnoreCase(
								attr.getID())) {
							System.out
									.println("Apellido" + attr.get(i));

						}
						// UID
						if (Utilidades.getUid().equalsIgnoreCase(
								attr.getID())) {
							System.out.println("Login:" + attr.get(i));

						}
						// CN
						if (Utilidades.getCn().equalsIgnoreCase(
								attr.getID())) {
							System.out.println("Nombre:" + attr.get(i));
						}
						if (Utilidades.getMail().equalsIgnoreCase(
								attr.getID())) {
							System.out.println("Mail:" + attr.get(i));

						}
						if (Utilidades.getTelephonenumber()
								.equalsIgnoreCase(attr.getID())) {
							System.out.println("telephonenumber:"
									+ attr.get(i));
						}

						if (Utilidades.getStreetAddress()
								.equalsIgnoreCase(attr.getID())) {
							System.out.println("streetAddress:"
									+ attr.get(i));

						}

						if (Utilidades.getHomePhone().equalsIgnoreCase(
								attr.getID())) {
							System.out.println("homePhone:"
									+ attr.get(i));

						}
						if (Utilidades.getOtherTelephone()
								.equalsIgnoreCase(attr.getID())) {
							System.out.println("otherTelephone:"
									+ attr.get(i));

						}
						if (Utilidades.getMobile().equalsIgnoreCase(
								attr.getID())) {
							System.out.println("mobile:" + attr.get(i));

						}

						
						
						System.out.println(attr.getID() + ":"+attr.get(i));
						System.out.println("---------fin---lo qje venia------------------");
						
						if (Utilidades.getCn().equalsIgnoreCase(attr.getID())) {
							cn = (String) attr.get(i);
							//swEncontro=true;
						}
					}
				}
				
				System.out
						.println("--------------------------------nuevo----------------FIN------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}


/*package demo.ldap;

import java.beans.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.novell.ldap.LDAPSearchResults;
import com.util.Utilidades;

import java.lang.*;
import java.util.*;

public class ObtenerDN {
	public static void main(String[] args) {

		// String userName = "cn=Robert Smith,ou=people,dc=example,dc=com";
		// String password = "rJsmitH";

		// System.out.println("Running query: " + query);
		try {
			String dominio = "dc=example, dc=com";
			
			//String userName = "cn=jimbob, dc=example, dc=com";
			String userName = "CN=Administrador,CN=Users,DC=estrellitados,DC=com";
			String password = "aA12760187";
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://localhost:389");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, userName);
			env.put(Context.SECURITY_CREDENTIALS, password);
			DirContext context = new InitialDirContext(env);
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			System.out.println("Conectado--");
			String ATTRIBUTE_FOR_USER = "sAMAccountName";
			String searchFilter = "(&(objectClass=user)(" + ATTRIBUTE_FOR_USER
			+ "=" + "simon" + "))";
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration enumeration = context.search("CN=Users,DC=estrellitados,DC=com",
					"sAMAccountName=simon", ctrl);
			 //windows->(sAMAccountName=simon)", ctrl);
//					"ldap->(uid=simon)", ctrl);
			System.out.println("hello................");
			//
			// NamingEnumeration enumeration =
			// context.search("DC=example,DC=com",
			// "(objectclass=*)", ctrl);
			String cn = null;
			String ou = null;
			boolean swEncontro=false;

			while (!swEncontro && enumeration.hasMoreElements()) {
				System.out
						.println("--------------------------------nuevo----------------ini------");
				SearchResult match = (SearchResult) enumeration.nextElement();
				Attributes attrs = match.getAttributes();
				NamingEnumeration e = attrs.getAll();
				while (!swEncontro && e.hasMoreElements()) {
					Attribute attr = (Attribute) e.nextElement();
					for (int i = 0; (i < attr.size() && !swEncontro); i++) {
						System.out.println(attr.getID()+":"+attr.get(i));
						if (Utilidades.getCn().equalsIgnoreCase(attr.getID())) {
							cn = (String) attr.get(i);
							///swEncontro=true;
						}
					}
				}
				//System.out.println(cn);
				System.out
						.println("--------------------------------nuevo----------------FIN------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}*/
