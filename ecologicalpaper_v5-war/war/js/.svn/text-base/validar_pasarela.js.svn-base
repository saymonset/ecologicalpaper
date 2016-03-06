/*
  m * Scripts para los eventos que realizan el manejo de validación de pasarela
 * de pago
 */


function getIdOpcionMenuPospago() {
	var resultado = $jQuery17("[id=opMenuRecargaPospago]").text();
	return $jQuery17.trim(resultado);
}

function getIdOpcionMenuPrepago() {
	var resultado = $jQuery17("[id=opMenuRecargaPrepago]").text();
	return $jQuery17.trim(resultado);
}


function getIdBannerRecarga() {
	var resultado = $jQuery17("[id=idBannerRecarga]").text();
	return $jQuery17.trim(resultado);
}


function getIdsOpcionesRecarga() {
	var resultado = {
		idRecargaPrepago: 	getIdOpcionMenuPrepago(),
		idRecargaPospago: 	getIdOpcionMenuPospago(),
		idBanerRecarga:		getIdBannerRecarga()
	};
	
	return resultado;
}


/**
 * Objeto con los flags para indicar si ya se inició 
 * la operación ajax para la validación de pasarela
 * según el botón de menú
 * */
var $flagsAjax =
	{
		menuHead : false,   //menús en el encabezado
		menuFooter: false,  //menús en el footer
		banner: false	    //botón en el banner
	};

/**
 * Objeto que sirve como placeholder 
 * para las referencias a los elementos DOM
 * que representan los links de cada uno de los botones del men´ú
 * */
var $elementosLink = 
	{
		menuHead : null,   //botón menú en el encabezado
		menuFooter: null,  //botón menú en el footer
		banner: null	    //botón en el banner
	};



function initFlags() {
	$elementosLink = { menuHead : null, menuFooter: null, banner: null };
	$flagsAjax = { menuHead : false, menuFooter: false, banner: false };
}

function dumpFlags(){
	alert('menuHead-->'+$flagsAjax.menuHead+'  menuFooter-->'+$flagsAjax.menuFooter+' banner-->'+$flagsAjax.banner);
}


function pasarelaAfterValidate(data, linkBoton) {
	
	var result = eval ("(" + data + ")");
	var activa = (result.pasarelaActiva != '0');
		
	if (activa) {
		/*
		 *  Si la pasarela está activa, se vuelve
		 *  a invocar el evento click para continuar con la navegación
		 * */
		try {
			Richfaces.showModalPanel('wait');	
		} catch (e) {			
		}
		
		/*
		 *  Hack para IE y Chrome
		 *  se programa con un timeout de nuevo la carga del modal
		 * */
		setTimeout(
		 
				function () {
					try {
						Richfaces.showModalPanel('wait');	
					} catch (e) {			
					}
				},
				150
		);
		
		//alert(linkBoton);
		
		try{
			$jQuery17(linkBoton).click();
		}catch(e){
			//alert(e.name + "\n" + e.message);
		}
		
		
		
	} else {
		/*
		 *  Si la pasarela no está activa, se 
		 *  presenta el modal de error
		 * */
		try {
			Richfaces.hideModalPanel('wait');	
		} catch (e) {
			
		}
		Richfaces.showModalPanel('modalErrorComunicacionBanco');
			
	}
	initFlags();
}

/**
 *  Método callback del ajax luego de
 *  completarse el ajax de validación pasarela 
 *  para el evento click del menú del header
 * */
function validarPasarelaHeaderAfterValidate(data) {
	pasarelaAfterValidate(data, $elementosLink.menuHead);
}

/**
 *  Método callback del ajax luego de
 *  completarse el ajax de validación pasarela 
 *  para el evento click del menú del footer
 * */
function validarPasarelaFooterAfterValidate(data) {
	pasarelaAfterValidate(data, $elementosLink.menuFooter);
}

/**
 *  Método callback del ajax luego de
 *  completarse el ajax de validación pasarela 
 *  para el evento click del menú del banner
 * */
function validarPasarelaBannerAfterValidate(data) {
	pasarelaAfterValidate(data, $elementosLink.banner);
}


function menuItemOnClick (dataEvento, flag) {
	var resultado  = false;
	
	/*
	 *  Verificar el id de opción
	 *  es alguno de recarga
	 * */
	var idOpciones = getIdsOpcionesRecarga();
	var esOpcionRecarga; 
	
	if (!dataEvento.esBanner) {
		esOpcionRecarga = ((dataEvento.idOpcion == idOpciones.idRecargaPospago) || (dataEvento.idOpcion == idOpciones.idRecargaPrepago));
	} else {
		esOpcionRecarga = (dataEvento.idOpcion == idOpciones.idBanerRecarga);
	}
	
	
	if (esOpcionRecarga && !flag) {
		/*
		 *  Es una opción de recarga:
		 *  1) Activar modal espera
		 *  2) inicializar flags y elementos link de la opción aplicando el callback
		 *  3) invocar la función ajax para validar pasarela
		 * */
		//paso 1
		try {
			Richfaces.showModalPanel('wait');	
		} catch (e) {
			
		}
		//paso 2
		dataEvento.callbackInit();
		//paso 3
		dataEvento.callbackAjax();
		resultado  = false;
		
	} else {
		resultado  = true;
	}
	return resultado;
}

function menuFooterItemOnClick(link, idOpcion) {
	var callback = function() {
		$flagsAjax.menuFooter = true;
		$elementosLink.menuFooter = link;	
	};
	
	var data = {
		callbackInit: 	callback,
		callbackAjax:   jsValidarPasarelaFooter,
		idOpcion: idOpcion,
		flagAjax: $flagsAjax.menuFooter,
		esBanner: false
			
	};
	
	return menuItemOnClick(data, $flagsAjax.menuFooter);
	
} 

function menuHeaderItemOnClick(link, idOpcion) {

	var callback = function() {
		$flagsAjax.menuHead = true;
		$elementosLink.menuHead = link;	
	};
	

	var data = {
		callbackInit: 	callback,
		callbackAjax:   jsValidarPasarelaHeader,
		idOpcion: idOpcion,
		flagAjax: $flagsAjax.menuHead,
		esBanner: false
			
	};
	
	return menuItemOnClick(data, $flagsAjax.menuHead);
} 

function menuBannerItemOnClick(link, idOpcion) {
	
	var callback = function() {
		$flagsAjax.banner = true;
		$elementosLink.banner = link;	
	};
	
	var data = {
			callbackInit: 	callback,
			callbackAjax:   jsValidarPasarelaBanner,
			idOpcion: idOpcion,
			flagAjax: $flagsAjax.banner,
			esBanner: true
				
		};
	
	return menuItemOnClick(data, $flagsAjax.banner);
} 


