
var winWidth  = 600;
var winHeight = 800;

function setDimensionScreen() {
    if (screen.availWidth) {
        winWidth = screen.availWidth;
    }
    if (screen.availHeight){
        winHeight = screen.availHeight - 100;
    }
}
function showReporteListaMaestra(sql_query) {
        window.open("reportelistamaestra.jsp?sql_query=" + sql_query, "WebDocuments", "resizable=no,scrollbars=yes,statusbar=yes,width=800,height=600,left=115,top=50");
    }

function MsgInEditor(text,editor) {
    editor.docHtml = text.innerText;
}

function showDocRelations(pages,title,idDoc,value,width,height) {
    abrirVentana(pages+"?read=true&value="+value+"&idDoc="+idDoc+"&title="+title,width,height);
}

function getPosition(totalValue,value){
    var calculo = (totalValue - value) / 2;
    if (calculo < 0){
        return 0;
    }
    return calculo;
}

 function abrirVentanaAccionSacop(pagina,width,height) {
    var hWnd = null;
     var left = 200;
    var top = 200;
    hWnd = window.open(pagina, "WebDocuments", "resizable=no,scrollbars=yes,statusbar=yes,width="+width+",height="+height+",left="+left+",top="+top);
}

function abrirVentana(pagina,width,height) {
    var hWnd = null;
    var left = getPosition(winWidth,width);
    var top = getPosition(winHeight,height);
    hWnd = window.open(pagina, "WebDocuments", "resizable=yes,scrollbars=yes,statusbar=yes,width="+width+",height="+height+",left="+left+",top="+top);
}

function abrirVentana(pagina,width,height,nameWin) {
    var hWnd = null;
    var left = getPosition(winWidth,width);
    var top = getPosition(winHeight,height);
    hWnd = window.open(pagina, nameWin, "resizable=yes,scrollbars=yes,statusbar=yes,width="+width+",height="+height+",left="+left+",top="+top);
}

function abrirVentananorms(pagina,width,height) {
    var hWnd = null;
    var left = getPosition(winWidth,width);
    var top = getPosition(winHeight,height);
    hWnd = window.open(pagina, "WebDocuments", "resizable=yes,scrollbars=yes,statusbar=yes,width="+width+",height="+height+",left="+left+",top="+top);
}
function showDocument(idDoc,idVersion,nameFile) {
    var hWnd = null;
    var nameFile = escape(nameFile);
    abrirVentana("viewDocument.jsp?nameFile=" + nameFile + "&idDocument="+idDoc+"&idVersion="+idVersion,800,600);
}

function showDocumentimprimir(idDoc,idVersion,nameFile,imprimir) {
    var hWnd = null;
    var nameFile = escape(nameFile);
    var toPrint = escape(nameFile);
    if (imprimir == '0') {
        toPrint = "protegido.doc";
    }
    abrirVentana("viewDocument.jsp?nameFile=" + nameFile + "&idDocument="+idDoc+"&idVersion="+idVersion+"&imprimir="+imprimir+"&nameFileToPrint="+toPrint,800,600);
}

function showDocumentimprimir(idDoc,idVersion,nameFile,imprimir,nameWin) {
    var hWnd = null;
    var nameFile = escape(nameFile);
    var toPrint = escape(nameFile);
    if (imprimir == '0') {
        toPrint = "protegido.doc";
    }
    abrirVentana("viewDocument.jsp?sendURL=true&nameFile=" + nameFile + "&idDocument="+idDoc+"&idVersion="+idVersion+"&imprimir="+imprimir+"&nameFileToPrint="+toPrint,800,600,nameWin);
}

function showDocumentPublishImp(idDoc,idVersion,nameFile,imprimir) {
    var hWnd = null;
    var nameFile = escape(nameFile);
    var toPrint = escape(nameFile);
    if (imprimir == '0') {
        toPrint = "protegido.doc";
    }
    abrirVentana("loadDataDoc.do?nameFile=" + nameFile + "&idDocument=" + idDoc + "&idVersion=" + idVersion + "&imprimir=" + imprimir + "&nameFileToPrint="+toPrint,800,600);
}
function showDocumentPublish(idDoc,idVersion,nameFile) {
    var hWnd = null;
    var nameFile = escape(nameFile);
    abrirVentana("loadDataDoc.do?nameFile=" + nameFile + "&idDocument="+idDoc+"&idVersion="+idVersion,800,600);
}
//20 de JULIO 2005 INICIO
 function showFirmantes(pagina,width,height) {
        return window.open(pagina, "WebDocuments", "resizable=no,scrollbars=yes,statusbar=yes,width="+width+",height="+height+",left=115,top=50");
    }
 function showImpresion(pagina,width,height) {
        //window.open(pagina, "WebDocuments", "resizable=no,menubar=yes,scrollbars=yes,statusbar=yes,width="+width+",height="+height+",left=115,top=50");
          window.open(pagina, "WebDocuments", "resizable=no,scrollbars=yes,statusbar=yes,width="+width+",height="+height+",left=165,top=200");
    }
function showDocumentFirmantes(idDoc,idVersion,namePropietario) {
    var hWnd = null;
    var nameFile = escape(nameFile);
    showFirmantes("loadDataFirmantes.do?namePropietario=" + namePropietario + "&idDocument="+idDoc+"&idVersion="+idVersion,800,600);
}
function showSolicitudImpresion(idDoc,idVersion,namePropietario) {
    var hWnd = null;
    var nameFile = escape(nameFile);
    showImpresion("loadsolicitudImpresion.do?namePropietario=" + namePropietario + "&idDocument="+idDoc+"&idVersion="+idVersion,600,400);
}
//20 DE JULIO 2005 FIN

function searchItem(forma,action,value) {
    if (forma.toSearch) {
        forma.toSearch.value = value;
        forma.action = action;
        forma.submit();
    }
}

function showInfo(mensaje) {
    alert(mensaje);
}

function execApp(prog) {
    var theShell = new ActiveXObject("WScript.Shell");
    theShell.run(prog, 1, true);
}

function showDocumentInFrame(idDoc,idVersion,nameFile) {
    var hWnd = null;
    var nameFile = escape(nameFile);
    var link = "viewDocument.jsp?nameFile=" + nameFile + "&idDocument="+idDoc+"&idVersion="+idVersion;
    location.href = link;
}

function showWindow(pages,input,nameForm,value,title,width,height,read){
    var hWnd = null;
    hWnd = window.open(pages+"?input="+input+"&nameForma="+nameForm+"&value="+value+"&title="+title+"&read="+read,"","width="+width+",height="+height+",resizable=no,scrollbars=yes,statusbar=yes,left=100,top=150");
    if ((document.window != null) && (!hWnd.opener)){
        hWnd.opener = document.window;
    }
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
if(v_length < 1){
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
var w_comillita3 = "´";

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
  function loadNoconformidadesRef(number,input,nameForm,value,type) {
        abrirVentana("loadNoconformidadesRef.do?number="+number+"&input="+input+"&nameForma="+nameForm+"&value="+value+"&type="+type+"&userAssociate="+document.sacoplantilla.noconformidadesref.value,800,600)
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
