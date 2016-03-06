function valCedula(val, e) {
	var filter;
	var keynum;
	

	filter = /^[0-9]*$/;
	
	keynum = (document.all) ? e.keyCode : e.which; 

	if (keynum == 0 || keynum==7)
		return true;
	
	keychar = String.fromCharCode(keynum);

	return filter.test(val+keychar);
}


function cargarParametrosLlamadaAjaxLimitesTDC() {

	
	var numeroTDC;
	var esTDCDomiciliada;
	var selectorNumeroTDC = "[id=" + formTdcPadre+":numeroFormateadoEditable" + "]" ;
	var selectorCodigoSeguridadTDC = "[id=" + formTdcPadre+":codigoSeguridad" + "]" ;
	var selectorMesVencimientoTDC = "[id=" + formTdcPadre+":mes" + "]" ;
	var selectorAnoVencimientoTDC = "[id=" + formTdcPadre+":anio" + "]" ;
	var selectorIdFranquiciaTDC = "[id=" + formTdcPadre+":tipoTarjeta" + "]" ;
	var selectorIdBancoTDC = "[id=" + formTdcPadre+":bancos" + "]" ;
	var selectorNombreTarjetahabienteTDC = "[id=" + formTdcPadre+":nombretarjetahabiente" + "]" ;
	var selectorCedulaTarjetahabienteTDC = "[id=formFormaPago:cedulaEditable]" ;
	
	var selectorIdEstado = "[id=formFormaPago:estadoSuceso]" ;
	var selectorIdCiudad = "[id=formFormaPago:ciudadSuceso]" ;
	var selectorIdMunicipio = "[id=formFormaPago:municipioSuceso]" ;
	var selectorDireccionFiscal = "[id=formFormaPago:referenciaSuceso]" ;
	
	var codigoSeguridadTDC = '';
	var mesVencimientoTDC = '';
	var anoVencimientoTDC = '';
	var idFranquiciaTDC = '';
	var idBancoTDC = '';
	var nombreTarjetahabienteTDC = '';
	var cedulaTarjetahabienteTDC = '';
	
	var idEstado = '';
	var idCiudad = '';
	var idMunicipio = '';
	var direccionFiscal = '';
		
	//sacar numero TDC del formulario
	try {
		numeroTDC = $jQuery14.trim($jQuery14(selectorNumeroTDC).val());	
	} catch (e) {
		numeroTDC = '';
	}
	
	
	try {
		codigoSeguridadTDC = $jQuery14.trim($jQuery14(selectorCodigoSeguridadTDC).val());	
	} catch (e) {
		codigoSeguridadTDC = '';
	}
	
	try {
		mesVencimientoTDC = $jQuery14.trim($jQuery14(selectorMesVencimientoTDC).val());	
	} catch (e) {
		mesVencimientoTDC = '';
	}
	
	try {
		anoVencimientoTDC = $jQuery14.trim($jQuery14(selectorAnoVencimientoTDC).val());	
	} catch (e) {
		anoVencimientoTDC = '';
	}
	
	try {
		idFranquiciaTDC = $jQuery14.trim($jQuery14(selectorIdFranquiciaTDC).val());	
	} catch (e) {
		idFranquiciaTDC = '';
	}
	
	try {
		idBancoTDC = $jQuery14.trim($jQuery14(selectorIdBancoTDC).val());	
	} catch (e) {
		idBancoTDC = '';
	}
	
	try {
		nombreTarjetahabienteTDC = $jQuery14.trim($jQuery14(selectorNombreTarjetahabienteTDC).val());	
	} catch (e) {
		nombreTarjetahabienteTDC = '';
	}
	
	try {
		cedulaTarjetahabienteTDC = $jQuery14.trim($jQuery14(selectorCedulaTarjetahabienteTDC).val());	
	} catch (e) {
		cedulaTarjetahabienteTDC = '';
	}
	
	try {
		idEstado = $jQuery14.trim($jQuery14(selectorIdEstado).val());	
	} catch (e) {
		idEstado = '';
	}
	
	try {
		idCiudad = $jQuery14.trim($jQuery14(selectorIdCiudad).val());	
	} catch (e) {
		idCiudad = '';
	}
	
	try {
		idMunicipio = $jQuery14.trim($jQuery14(selectorIdMunicipio).val());	
	} catch (e) {
		idMunicipio = '';
	}
	
	try {
		direccionFiscal = $jQuery14.trim($jQuery14(selectorDireccionFiscal).val());	
	} catch (e) {
		direccionFiscal = '';
	}
	 
	//validar si es o no TDC Domiciliada según el valor obtenido antes
	if (numeroTDC == '') {
		esTDCDomiciliada = '1';
	} else {
		esTDCDomiciliada = '0';
	}
	
	
	var resultado = {
			esDomiciliada: esTDCDomiciliada,	
			numeroTDC : numeroTDC  , 
			codigoSeguridad: codigoSeguridadTDC,
			mesVencimiento: mesVencimientoTDC,
			anoVencimiento: anoVencimientoTDC,
			idFranquicia: idFranquiciaTDC,
			idBanco: idBancoTDC,
			nombreTarjetahabiente: nombreTarjetahabienteTDC,
			cedulaTarjetahabiente: cedulaTarjetahabienteTDC,
			idEstado: idEstado,
			idMunicipio: idMunicipio,
			idCiudad: idCiudad,
			direccionFiscal: direccionFiscal
			
		};
	
	return resultado;
}


function mostrar(id){
    try {
		var NuevaCapa = document.getElementById(id);
		if (NuevaCapa.className == 'ocultar') {
			NuevaCapa.className = '';
		} else {
			NuevaCapa.className = 'ocultar';
		}
	} catch (e) {
		
	}
}

function botonCancelarModalOnClick() {
	try {
		Richfaces.hideModalPanel("pnlUrlBanner");
	} catch (e) {
	
	}
	
	return false;
}

function botonRecargarOnClick() {
	
	/*
	 *  Validar el correcto ingreso de una TDC
	 *  ya sea que se eligió TDC domiciliada o se eligió otra TDC
	 * */
	var estado = $jQuery14.trim($jQuery14("[id=formFormaPago:estadoSuceso]").val());
	var ciudad = $jQuery14.trim($jQuery14("[id=formFormaPago:ciudadSuceso]").val());
	var municipio = $jQuery14.trim($jQuery14("[id=formFormaPago:municipioSuceso]").val());
	var direccion = $jQuery14.trim($jQuery14("[id=formFormaPago:referenciaSuceso]").val());
	var acuerdo = $jQuery14("[id=formFormaPago:acuerdoCheck]").attr('checked');
	var cedulaTarjAdl = $jQuery14.trim($jQuery14("[id=formFormaPago:cedulaEditable]").val());
	
	/*
	 *  Validación de datos de facturación
	 * */
	var valido_estado = (estado != '') && (estado != '0'); 
	var valido_ciudad =  (ciudad != '') && (ciudad != '0');
	var valido_municipio = (municipio != '') && (municipio != '0'); 
	var valido_direccion = (direccion != '') && (direccion != '0'); 
	var valido_acuerdo = (acuerdo == true);
	
	var tipo = $jQuery14.trim($jQuery14("[name=formFormaPago:tarjetaEsc]:checked").val());
	
	var formaPagoSeleccionada = (tipo == 'tarjetaDom' || tipo == 'tarjetaOtr');
	var valido ;
	
	 if(tipo == 'tarjetaOtr'){
		 /*
		  *  Se eligió otra TDC
		  * */
		var valido_cedulaTarjAdl = (cedulaTarjAdl != '') && (cedulaTarjAdl != '0');
		var valido_nombretarjetahabiente = ($jQuery14.trim($jQuery14("[id=" + formTdcPadre+":nombretarjetahabiente" + "]").val().length > 0)) ;
		var valido_numero_tdc = ($jQuery14.trim($jQuery14("[id=" + formTdcPadre+":numeroFormateadoEditable" + "]").val()).length >= 15);
		var valido_nombre_banco = ($jQuery14.trim($jQuery14("[id=" + formTdcPadre+":bancos" + "]").val()).length>0);
		var valido_tipo_tarjeta = ($jQuery14.trim($jQuery14("[id=" + formTdcPadre+":tipoTarjeta" + "]").val()).length>0);
		var valido_mes_vencimiento = ($jQuery14.trim($jQuery14("[id=" + formTdcPadre+":mes" + "]").val()).length>0);
		var valido_ano_vencimiento = ($jQuery14.trim($jQuery14("[id=" + formTdcPadre+":anio" + "]").val()).length>0);
		var valido_codigo_seguridad = ($jQuery14.trim($jQuery14("[id=" + formTdcPadre+":codigoSeguridad" + "]").val()).length>=3);
		
		
		var valido_formTdcAdicional =  valido_nombretarjetahabiente && valido_numero_tdc && valido_nombre_banco && valido_tipo_tarjeta && valido_mes_vencimiento && valido_ano_vencimiento && valido_codigo_seguridad; 
		
		valido = valido_estado && valido_ciudad && valido_municipio && valido_direccion && valido_acuerdo && valido_cedulaTarjAdl && valido_formTdcAdicional && formaPagoSeleccionada;
		
     }else{
    	 /*
    	  * Se eligió TDC domicialiada
    	  * */
    	 valido = valido_estado && valido_ciudad && valido_municipio && valido_direccion && valido_acuerdo && formaPagoSeleccionada;
    	
     }
	
	/*
	 *  Si el formulario es válido se despliega el modal
	 *  
	 *  y se lanza el ajax para validar los límites de la TDC
	 * */
	 
	 
	if (valido) {
		var p = cargarParametrosLlamadaAjaxLimitesTDC();
		
		
		/* Llamada ajax 
		 *  - param 1: esDomiciliada
		 *  - param 2: numeroTDC
		 *  - param 3: codigoSeguridad
		 *  - param 4: mesVencimiento
		 *  - param 5: anoVencimiento
		 *  - param 6: idFranquicia
		 *  - param 7: idBanco
		 *  - param 8: nombreTitular
		 *  - param 9: cedulaTitular
		 *  - param 10: idEstado
		 *  - param 11: idCiudad
		 *  - param 12: idMunicipio
		 *  - param 13: direcionFiscal
		 * 
		 * */
		
		
		jsValidarLimitesTDC(p.esDomiciliada, p.numeroTDC, p.codigoSeguridad, p.mesVencimiento, p.anoVencimiento, p.idFranquicia, p.idBanco, p.nombreTarjetahabiente, p.cedulaTarjetahabiente, p.idEstado, p.idCiudad, p.idMunicipio, p.direccionFiscal);
		
		
		//se activa el modal de espera
		Richfaces.showModalPanel('wait');
	} 
	
	return false;
}




function botonModalRecargarOnclick() {
	
	//$jQuery14("[id=formFormaPago:botonModalRecargar]").attr('disabled',true);
	return true;
}


function mostrarVentanaNoDomiciliada(id){
   
	try {
		var Ventana = document.getElementById(id);
		var ll = document.getElementById("formFormaPago:tarjetaEsc:0").checked;
		var ll2 = document.getElementById("formFormaPago:tarjetaEsc:1").checked;
		if (ll2) {
			Ventana.className = '';
		} else {
			Ventana.className = 'ocultar';
		}
	} catch (e) {
		
	}

}
function mostrarMensajeError(id){
   try {
	   var NuevaCapa = document.getElementById(id);
	    if (NuevaCapa.style.display == 'none'){	
	        NuevaCapa.style.display = 'block';
	    }   
   }
   catch (e) {
	   
   }
	
	
}

function ocultarMensajeError(id){
    try {
    	var NuevaCapa = document.getElementById(id);
        if(NuevaCapa.style.display == 'block'){
            NuevaCapa.style.display = 'none';
        }	
    } 
    catch (e) {
    	
    }
}



function otrasSolicitudes(formOtraSol){
	document.getElementById(formOtraSol).onclick();
	return false;
}


/*
 *  Evento Ajax para validación de los límites de la tDC
 * */
function validarLimitesTDCAfterComplete (data) {
	
	/*
	 *  Resultado del procesamiento AJAX
	 * 
	 *  El objeto JSON esperado es de la forma
	 *  
	 *  {
	 *  	montoTransaccionFinancieraMaximo : x,
	 *  	montoTransaccionFinanciera : y,
	 *  	montoTransaccionFinancieraMiPunto: z,
	 *  	validoBin: u
	 *  }
	 *  
	 *  donde x, y, z son valores numéricos que se refieren a los montos
	 *  
	 *  x = máximo monto por tiempo (mes, semana, etc) a recargar por TDC
	 *  y = monto total hasta la fecha de transacciones hechas por mi MMO
	 *  z = monto total hasta la fecha de transacciones hechas por mi Mi Punto
	 *  
	 *  u = 1 o 0 según el resultado del servicio de validación de bines
	 * 
	 * */
	var resultado  = eval("(" + data + ")");
	

	var montoMaximo;
	var montoTransaccionMMO;
	var montoTransaccionMiPunto;
	var montoActualRecarga;
	var montoMaximoRecarga;
	var cedulasExcedida;
	var validoBin;
	
	  
	try {
		montoMaximo = parseFloat(resultado.montoTransaccionFinancieraMaximo);
	}
	catch(e) {
		montoMaximo = 0;
	}
	try {
		montoTransaccionMMO = parseFloat(resultado.montoTransaccionFinanciera);
		
	}
	catch(e) {
		montoTransaccionMMO = 0;
	}
	try {
		montoTransaccionMiPunto = parseFloat(resultado.montoTransaccionFinancieraMiPunto);
	}
	catch(e) {
		montoTransaccionMiPunto = 0;
	}
	try {
		montoActualRecarga = parseFloat($jQuery14.trim($jQuery14("[id=montoTotalCompra]").text()));
	} catch (ee) {
		montoActualRecarga = 0;
	}
	
	cedulasExcedida = ($jQuery14.trim(resultado.cedulasTransaccionFinancieraExcedida) != '0');
	validoBin = ($jQuery14.trim(resultado.validoBin) != '0');
		
	
	montoMaximoRecarga = montoMaximo - montoTransaccionMMO - montoTransaccionMiPunto;
	
	var excedeMonto = (montoActualRecarga > montoMaximoRecarga); 
	
	/*
	 *  Validar que el monto actual no sobrepase 
	 * */
	if (excedeMonto) {
		
		/*
		 *  El monto actual de recarga excede al máximo permitido
		 *  
		 *  1) Ocultar los botones normales y mostrar los de limite maximo TDC
		 *  2) Ocultar la pregunta de está seguro normal
		 *  3) Mostrar espacio para el mensaje de error monto maximo TDC
		 * */
		$jQuery14("[id=grupoBotonesModal1]").hide();
		$jQuery14("[id=grupoBotonesModal2]").show();
		$jQuery14("[id=estaSeguro1]").hide();
		$jQuery14("[id=mensajeErrorMontoMaximoTDC]").show();
		
	}else if(cedulasExcedida){
		$jQuery14("[id=grupoBotonesModal1]").hide();
		$jQuery14("[id=grupoBotonesModal2]").show();
		$jQuery14("[id=estaSeguro1]").hide();
		$jQuery14("[id=mensajeErrorCedulasExcedidasTDC]").show();
	} else {

		/*
		 *  El monto actual de recarga no excede al máximo permitido
		 *  
		 *  1) Mostrar los botones normales y ocultar los de limite maximo TDC
		 *  2) Mostrar la pregunta de está seguro normal
		 *  3) Ocultar espacio para el mensaje de error monto maximo TDC
		 * */
		$jQuery14("[id=grupoBotonesModal1]").show();
		$jQuery14("[id=grupoBotonesModal2]").hide();
		$jQuery14("[id=estaSeguro1]").show();
		$jQuery14("[id=mensajeErrorMontoMaximoTDC]").hide();
	} 
	
	/*
	 *  Según sea o no válido el BIN 
	 *  se muestra u oculta el mensaje de error
	 * */
	if (validoBin) {
		$jQuery14("[id=datosTdcInvalidos]").hide();
		$jQuery14("[id=formFormaPago:botonModalRecargar]").attr('disabled',false);
	} else {
		if (!excedeMonto) {
			$jQuery14("[id=datosTdcInvalidos]").show();
		}
		$jQuery14("[id=formFormaPago:botonModalRecargar]").attr('disabled',true);
	}
	
	/*
	 *  Ocultar el modal de espera
	 *  y mostrar el modal de confirmación de recarga
	 * */
	Richfaces.hideModalPanel('wait');
	Richfaces.showModalPanel('formFormaPago:pnlUrlBanner');
	
}

/*
 *  Inicialización de todos los eventos de la vista
 *  
 *  Este callback es invocado cuando se completa 
 *  todo el proceso de carga de la página
 * */
jQuery('document').ready(
	function() {
		var selector_boton_modal_cancelar = "[id=formFormaPago:botonModalCancelar]";
		var selector_boton_modal_cancelar2 = "[id=formFormaPago:botonModalCancelar2]";
		var selector_link_ocultar = "[id=formFormaPago:roaming:lnkOcultar12]";
		var selector_boton_recargar = "[id=formFormaPago:botonRecargar]";
		
		$jQuery14(selector_boton_modal_cancelar).click(botonCancelarModalOnClick);
		//$jQuery14(selector_boton_modal_cancelar2).click(botonCancelarModalOnClick);
		//$jQuery14(selector_link_ocultar).click(botonCancelarModalOnClick);
		$jQuery14(selector_boton_recargar).click(botonRecargarOnClick);
		$jQuery14("[id=formFormaPago:botonModalRecargar]").click(botonModalRecargarOnclick);
		$jQuery14("[id=grupoBotonesModal2]").hide();
		mostrarVentanaNoDomiciliada('datosTdc');
		
	}
);