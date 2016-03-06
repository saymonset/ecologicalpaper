// This function calls the popup window.
		
function showPlaceList(action, form, onlyView) {
   features="height=300,width=250,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";			
   winId=window.open('ClienteDocumentoGenerar?onlyView='+onlyView,'list',features); // open an empty window

} 

		

function cerrarFramesViewPDF() 
{

parent.close(); 

} 

		

function refrescar() 
{ 

 top.frames['leftFrame'].location.href = './arbol.jsf'; 
 
 
} 


  
function isbadMail(value){
        if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value))) {
                return true;
        }
         return false;
}

 

function Trim(TRIM_VALUE){
if(TRIM_VALUE.length < 1){
return"";
}
TRIM_VALUE = RTrim(TRIM_VALUE);
TRIM_VALUE = LTrim(TRIM_VALUE);
if(TRIM_VALUE==""){
return "";
}
else{
return TRIM_VALUE;
}
} //End Function

function RTrim(VALUE){
var w_space = String.fromCharCode(32);
var v_length = VALUE.length;
var strTemp = "";
if(v_length < 0){
return"";
}
var iTemp = v_length -1;

while(iTemp > -1){
    if(VALUE.charAt(iTemp) == w_space){
    }
    else{
    strTemp = VALUE.substring(0,iTemp +1);
    break;
    }
    iTemp = iTemp-1;

} //End While
return strTemp;

} //End Function

function LTrim(VALUE){
var w_space = String.fromCharCode(32);
if(VALUE.length < 1){
return"";
}
var v_length = VALUE.length;
var strTemp = "";

var iTemp = 0;

while(iTemp < v_length){
if(VALUE.charAt(iTemp) == w_space){
}
else{
strTemp = VALUE.substring(iTemp,v_length);
break;
}
iTemp = iTemp + 1;
} //End While
return strTemp;
} //End Function


function stringVacio(VALUE){
var v_length = VALUE.length;
var w_space = String.fromCharCode(32);
var iTemp = v_length -1;
var sw=true;
    while(iTemp > -1){
         if(VALUE.charAt(iTemp) == w_space){
         }else{
          sw=false;
         }
    iTemp = iTemp-1;
    }
   return sw;
}

function hayComillas(VALUE){
var w_space = String.fromCharCode(32);
var w_comillita1 = "'";
var w_comillita2 = "`";
var w_comillita3 = "Ž";

var v_length = VALUE.length;
var strTemp = "";
if(v_length < 0){
return false;
}
var iTemp = v_length -1;

while(iTemp > -1){
   if(VALUE.charAt(iTemp) == w_comillita1){
      return true;
    }
    if(VALUE.charAt(iTemp) == w_comillita2){
      return true;
    }
    if(VALUE.charAt(iTemp) == w_comillita3){
      return true;
    }
    iTemp = iTemp-1;
} //End While
return false;

} //End Function



function anyoBisiesto(anyo)
    {
        /**
        * si el año introducido es de dos cifras lo pasamos al periodo de 1900. Ejemplo: 25 > 1925
        */
        if (anyo < 100)
            var fin = anyo + 1900;
        else
            var fin = anyo ;

        /*
        * primera condicion: si el resto de dividir el año entre 4 no es cero > el año no es bisiesto
        * es decir, obtenemos año modulo 4, teniendo que cumplirse anyo mod(4)=0 para bisiesto
        */
        if (fin % 4 != 0)
            return false;
        else
        {
            if (fin % 100 == 0)
            {
                /**
                * si el año es divisible por 4 y por 100 y divisible por 400 > es bisiesto
                */
                if (fin % 400 == 0)
                {
                    return true;
                }
                /**
                * si es divisible por 4 y por 100 pero no lo es por 400 > no es bisiesto
                */
                else
                {
                    return false;
                }
            }
            /**
            * si es divisible por 4 y no es divisible por 100 > el año es bisiesto
            */
            else
            {
                return true;
            }
        }
    }
 
  function fechavalbisiesto(dia,mes,anyo){
  var febrero;
       if(anyoBisiesto(anyo))
           febrero=29;
       else
           febrero=28;
         /**
       * si el mes introducido es febrero y el dia es mayor que el correspondiente
       * al año introducido > alertamos y detenemos ejecucion
       */
       if ((mes==2) && ((dia<1) || (dia>febrero)))
       {
           return '1';
       }
          /**
       * si el mes introducido es de 31 dias y el dia introducido es mayor de 31 > alertamos y detenemos ejecucion
       */
       if (((mes==1) || (mes==3) || (mes==5) || (mes==7) || (mes==8) || (mes==10) || (mes==12)) && ((dia<1) || (dia>31)))
       {
           return '2';
       }
          /**
       * si el mes introducido es de 30 dias y el dia introducido es mayor de 31 > alertamos y detenemos ejecucion
       */
       if (((mes==4) || (mes==6) || (mes==9) || (mes==11)) && ((dia<1) || (dia>30)))
       {
           return '3';
       }
       return '4';

}