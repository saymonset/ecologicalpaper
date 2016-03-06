package demo.ldap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LDAPTest {
    static class LDAP {
        static String ATTRIBUTE_FOR_USER = "sAMAccountName";

        public Attributes authenticateUser(String username, String password,
                String _domain, String host, String dn) {

            String returnedAtts[] = { "sn", "givenName", "mail" };
            String searchFilter = "(&(objectClass=user)(" + ATTRIBUTE_FOR_USER
                    + "=" + username + "))";
            // Create the search controls
            
            
            

            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            // Specify the search scope

            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchBase = dn;
            Hashtable environment = new Hashtable();
            environment.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.ldap.LdapCtxFactory");
            // Using starndard Port, check your instalation

            environment.put(Context.PROVIDER_URL, "ldap://" + host + ":389");
            environment.put(Context.SECURITY_AUTHENTICATION, "simple");

            environment.put(Context.SECURITY_PRINCIPAL, username + "@"
                    + _domain);
            environment.put(Context.SECURITY_CREDENTIALS, password);
            LdapContext ctxGC = null;
            try {
                ctxGC = new InitialLdapContext(environment, null);
                // Search for objects in the GC using the filter

                NamingEnumeration answer = ctxGC.search(searchBase,
                        searchFilter, searchCtls);
             
                int i = 0;
                while (answer.hasMoreElements()) {
                    SearchResult sr = (SearchResult) answer.next();
                    Attributes attrs = sr.getAttributes();
                    if (attrs != null)

                        return attrs;
                    {

                    }
                 
                }

            } catch (NamingException e) {
                System.out.println("Just reporting error");
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);
        System.out.println("Please type username:");
        String username = "ssmith";//in.readLine();
        System.out.println("Please type password:");
        String password = "sSmitH";//in.readLine();
        LDAP ldap = new LDAP();

      
        // "mydomain.com", "myactivedirectoryhost.com", "DC=mydomain,DC=com");
        Attributes att = ldap.authenticateUser(username, password,
                "example.com", "saymon", "dc=example,dc=com");
        if (att == null) {
            System.out
                    .println("Sorry your use is invalid or password incorrect");
        } else {
            String s = att.get("givenName").toString() + " " +att.get("sn").toString()+ " " +att.get("mail").toString();
            System.out.println("GIVEN NAME=" + s);
        }
    }
 
}