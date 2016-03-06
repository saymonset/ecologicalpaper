package demo.ldap;


import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;

class ldapminimo
{
 InitialDirContext ctx = null;

 public void depura(String cadena) // codigo para unificar salidas
 {
     System.out.println(cadena);
 }

 public static void main(String[] args) // punto de entrada a la aplicacion
 {
     ldapminimo instancia = new ldapminimo();
     instancia.ejecuta(); // evitamos instranciacion estatica de los metodos
 }


 public void ejecuta()
 {
     String target = "";

     Properties env = System.getProperties();

     env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
     env.put(Context.PROVIDER_URL, "ldap://localhost:389/ou=front,o=desarrollo,o=casa");
     env.put(Context.SECURITY_PRINCIPAL, "cn=root,o=casa");
     env.put(Context.SECURITY_CREDENTIALS, "roberto");

  try
  {
     ctx = new InitialDirContext(env);
     depura ("El DN es: " + ctx.getNameInNamespace());
     muestraLista(target, ctx.list(target));

     ctx.close();
  }
  catch (Exception e)
  {
     depura("Excepcion EN BUCLE PRINCIPAL");
     e.printStackTrace();
  }
 }

 void muestraLista(String msg, NamingEnumeration nl)
{
     System.out.println("Sacamos lista de elementos para: " + msg);

     if (nl == null)
     {
       System.out.println("No hay Elementos en la lista");
     }
     else
     {
       try
       {
              // recorrer la enumeracion
              while (nl.hasMore())
              {
              Object objeto = nl.next();
              NameClassPair parNombre = null;

              depura("Detalle del Objeto" + objeto.getClass().getName());

              // nos aseguramos que es objeto del tipo adecuado
              if (objeto instanceof javax.naming.NameClassPair)
              {
                     // depura ("Es un javax.naming.NameClassPair");
                     parNombre = (NameClassPair) objeto;
              }
              else
              {
                     depura("No es un nombre");
                     return;
              }

              // Cojer el nombre
              String nombre = parNombre.getName();
              depura("El nombre recogido es " + nombre);

               listaAtributos(ctx,nombre);
              }
       }
       catch (NamingException e)
       {
              e.printStackTrace();
       }
    }
}
 void listaAtributos (DirContext localContext, String cadena)
 {
      try
      {
           // se puede mejorar pasandole un array con el nombre de los atributos a recoger
           Attributes attr = localContext.getAttributes(cadena);

           // recuperamos una enumeracion con todos los atributos
           NamingEnumeration nl = attr.getAll();

           if (nl == null)
           {
                depura("lista de atributos nula");
                return;
           }

           while (nl.hasMore())
           {
                Object objeto = nl.next(); // recorremos todos los tributos

                if (objeto instanceof Attribute)
                {
                     // cojemos un atributo especifico
                     Attribute internalAttr = (Attribute)objeto;
                     depura("\tAtributo = " + objeto.toString());
                }
           }
      }
      catch (NamingException e)
      {
           e.printStackTrace();
      }
 }

}