package demo.ldap;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

class Simple {
    public static void main(String[] args) {
    	Hashtable authEnv = new Hashtable(11);
    	String userName = "cn=John Smith,ou=people,dc=example,dc=com";
    	String passWord = "jSmitH";
    	String base = "";//"ou=people,dc=example,dc=com";
    	String dn =  userName ;
    	String ldapURL = "ldap://localhost:389";
 
    	authEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
   		authEnv.put(Context.PROVIDER_URL, ldapURL);
   		authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
   		authEnv.put(Context.SECURITY_PRINCIPAL, dn);
   		authEnv.put(Context.SECURITY_CREDENTIALS, passWord);
 
    	try {
    		DirContext authContext = new InitialDirContext(authEnv);
    		System.out.println("Authentication Success!");
    	} catch (AuthenticationException authEx) {
    		System.out.println("Authentication failed!");
    		authEx.printStackTrace();
 
    	} catch (NamingException namEx) {
    		System.out.println("Something went wrong!");
    		namEx.printStackTrace();
    	}
    }
}