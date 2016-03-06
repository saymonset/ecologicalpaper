/**
 * 
 *  Función para eliminar todos los cookies del dominio
 *  en donde se encuentra la aplicación
 *  
 *  Esta función debe invocarse justo antes de seguir con la acción
 *  de cierre de sesión 
 * 
 */
function getCookie(c_name)
{
var i,x,y,ARRcookies=document.cookie.split(";");
for (i=0;i<ARRcookies.length;i++)
  {
  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
  x=x.replace(/^\s+|\s+$/g,"");
  if (x==c_name)
    {
    return unescape(y);
    }
  }
}

function checkCookie()
{
	var nameCookie='primeraVezMM';
var username=getCookie(nameCookie);
 
if (username!=null && username!="")
  {
 
 return true;
  }
else 
  {
 return false;
  }
}
function borrarCookies() {
	var cookies = document.cookie.split(";");
	//cookie para instalar la aplicacion en un ipad o iphone, esta cookie no debe borrarse
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        if (!checkCookie()){
        	document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";	
        }
        
    }
    
    return true;
}

