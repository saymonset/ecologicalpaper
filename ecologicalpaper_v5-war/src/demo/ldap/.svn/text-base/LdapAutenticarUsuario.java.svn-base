package demo.ldap;

 

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapAutenticarUsuario
{
        private final String INITIAL_CONTEXT= "com.sun.jndi.ldap.LdapCtxFactory";
        private final String DN_SUFFIX= ",ou=people,dc=example,dc=com";
        private final String DN_PREFIX= "uid=";

     
       
        public boolean authenticate(String host, int port, String user, String pass) {
               
                String HOST= "ldap://" + host + ":" + port;
                String dn = DN_PREFIX+user+DN_SUFFIX;
               
                Hashtable env = new Hashtable();
                if (pass.compareTo("") == 0 || user.compareTo("") == 0)
                        return false;
               
                env.put(Context.INITIAL_CONTEXT_FACTORY,INITIAL_CONTEXT);
                env.put(Context.PROVIDER_URL, HOST);
                env.put(Context.SECURITY_AUTHENTICATION, "simple");
                env.put(Context.SECURITY_PRINCIPAL, dn);
                env.put(Context.SECURITY_CREDENTIALS, pass);
                System.out.println("---------------");
               System.out.println("dn->"+dn);
               System.out.println("---------------");
                try {
                        DirContext ctx = new InitialDirContext(env);
                }
                catch (NamingException e) {
                        //e.printStackTrace();
                        return false;
                }

                return true;  
        }

        public static void main(String[] argv) {
        	LdapAutenticarUsuario aut = new LdapAutenticarUsuario();
                if (aut.authenticate("localhost", 389, "rjsmith","rJsmitH"))
                {
                        System.out.println("Autenticado");
                }
                else
                {
                        System.out.println("No Auntenticado");
                }

               
        }
}

