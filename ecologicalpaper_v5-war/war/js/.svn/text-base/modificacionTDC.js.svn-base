function mostrarVentana(){
	
	if(document.getElementById("modificacionTDCPadre:modificarTDC:cedulaEditable").value.length<6 ||
			document.getElementById(formTdcPadre+":nombretarjetahabiente").value.length==0 ||
			document.getElementById(formTdcPadre+":numeroFormateadoEditable").value.length<15 ||
			document.getElementById(formTdcPadre+":bancos").value.length==0 ||
			document.getElementById(formTdcPadre+":tipoTarjeta").value.length==0 ||			
			document.getElementById(formTdcPadre+":mes").value.length==0 ||
			document.getElementById(formTdcPadre+":anio").value.length==0 ||
			document.getElementById(formTdcPadre+":codigoSeguridad").value.length<3 )
		{
		return false;
		
	}
	
	
	Richfaces.showModalPanel('modificacionTDCPadre:modificarTDC:pnlUrlBanner');
	var test = document.getElementById(formTdcPadre+":nombretarjetahabiente").value;
	
	jQuery("[id=nombreTarjetaHab]").html(test);

	test = document.getElementById("modificacionTDCPadre:modificarTDC:cedulaEditable").value;
	jQuery("[id=cedulaTDC]").html(test);

	test = document.getElementById(formTdcPadre+":numeroFormateadoEditable").value;
	jQuery("[id=numTarjetaHab]").html(test);

	var numBanco = document.getElementById(formTdcPadre+":bancos");
	var indiceSeleccionado = numBanco.selectedIndex;
	var opcionSeleccionada = numBanco.options[indiceSeleccionado];
	test = opcionSeleccionada.text;
	
	//Este campo esta escondido en la vista para 
	//poder capturar el nombre del banco no esta dentro del formulario
	jQuery("[id='modificacionTDCPadre:modificarTDC:nombreBanco']").val(test);	
	jQuery("[id=nombBanco]").html(test);
	
	test = document.getElementById(formTdcPadre+":mes").value;	
	jQuery("[id=mesTDC]").html(test);

	test = document.getElementById(formTdcPadre+":anio").value;	
	jQuery("[id=anioTDC]").html(test);

	test = document.getElementById(formTdcPadre+":codigoSeguridad").value;	
	jQuery("[id=codSegTDC]").html(test);	
	
	
	var numTipoTarjeta = document.getElementById(formTdcPadre+":tipoTarjeta");
	var indiceSeleccionado = numTipoTarjeta.selectedIndex;
	var opcionSeleccionada = numTipoTarjeta.options[indiceSeleccionado];
	test = opcionSeleccionada.text;
	
	jQuery("[id=tipoTarjetaTDC]").html(test);	
	
	return true;
}


function comboBancosOnChange() {
	
	var combo = jQuery("[id="+formTdcPadre+":bancos]");
	
	var indiceSeleccionado = combo.get(0).selectedIndex;
	var opcionSeleccionada = combo.get(0).options[indiceSeleccionado];	
	
	jQuery("[id="+formTdcPadre+":nombreBanco]").val(opcionSeleccionada.text);
}

function botonModalAceptarOnClick() {
		 
	 jQuery("#modificarTDC").submit();
	 return true;
}


function valText(val, e) {
	var filter;
	var keynum;
	

	filter = /^[0-9]*$/;
	
	keynum = (document.all) ? e.keyCode : e.which; 

	if (keynum == 0 || keynum == 8)
		return true;
	
	keychar = String.fromCharCode(keynum);

	return filter.test(val+keychar);
}

function valCodSeg(val, e) {
	var filter;
	var keynum;
	

	filter = /^$/;
	
	keynum = (document.all) ? e.keyCode : e.which; 

	if (keynum == 0 || keynum == 8)
		return true;
	
	keychar = String.fromCharCode(keynum);

	return filter.test(val+keychar);
}


function valCedula(val, e) {
	var filter;
	var keynum;
	

	filter = /^[0-9]*$/;
	
	keynum = (document.all) ? e.keyCode : e.which; 
    
	if (keynum == 0 || keynum == 8)
		return true;
	
	keychar = String.fromCharCode(keynum);

	return filter.test(val+keychar);
}


function validar(val,e) { // 1
    tecla = (document.all) ? e.keyCode : e.which; // 2
    if (tecla==0 || tecla==8) return true; // 3
    patron =/^([A-Za-zñÑáéíóúäëïöüàèìòùÁÉÍÓÚÀÈÌÒÙÄËÏÖÜÂÊÎÔÛâêîôû]+[\s]*)*$/; // 4
    te = String.fromCharCode(tecla); // 5
    return patron.test(val+te); // 6
} 

function otrasSolicitudes(formOtraSol){
	document.getElementById(formOtraSol).onclick();
	return false;
}

function validarArchivo(nombreArchivo)
{
	if(nombreArchivo.length!=0)
	{
		cadena = "Se ha enviado el archivo exitosamente";
		jQuery("[id=msjFaltaArchivo]").remove();
		jQuery("[id=msjArchivoEnviado]").html(cadena);

		
		jQuery("[id=erroresM]").remove();
	}
	
}
