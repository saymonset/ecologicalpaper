var idt = "login:clave";
function mostrarTeclado() {
	idt = "login:clave";
	return null;

}
function mostrarTecladoLoginTV() {
	idt = "loginTV:claveTV";
	return null;

}

function mostrarTeclado1() {
	idt = "contPos:primerIngresoPos:claveTemporal";
	return null;
}

function mostrarTeclado2() {
	idt = "contPos:primerIngresoPos:claveNueva";
	return null;
}

function mostrarTeclado3() {
	idt = "contPos:primerIngresoPos:confClaveNueva";
	return null;
}

function mostrarTeclado4() {
	idt = "contPre:primerIngresoPre:claveTemporal";
	return null;
}

function mostrarTeclado5() {
	idt = "contPre:primerIngresoPre:claveNueva";
	return null;
}

function mostrarTeclado6() {
	idt = "contPre:primerIngresoPre:confClaveNueva";
	return null;
}

function mostrarTeclado7() {
	idt = "contCambioClave:reestablecerClave:claveActual";
	return null;
}

function mostrarTeclado8() {
	idt = "contCambioClave:reestablecerClave:claveNueva";
	return null;
}

function mostrarTeclado9() {
	idt = "contCambioClave:reestablecerClave:confirmarClave";
	return null;
}

function mostrarTeclado10() {
	idt = "registro:registroMiMovistar:clave";
	return null;
}
function mostrarTeclado11() {
	idt = "cambioClaveAcceso:cambioClave:claveTemporal";
	return null;
}

function mostrarTeclado12() {
	idt = "cambioClaveAcceso:cambioClave:nuevaClaveTemporal";
	return null;
}

function mostrarTeclado13() {
	idt = "cambioClaveAcceso:cambioClave:confirmarNuevaClaveTemporal";
	return null;
}

function mostrarTeclado14() {
	idt = "registroDialUp:registroMiMovistarDialUp:clave";
	return null;
}

function mostrarTeclado15() {
	idt = "olvidasteClave:recuperarClave:claveNueva";
	return null;
}

function mostrarTeclado16() {
	idt = "olvidasteClave:recuperarClave:confClaveNueva";
	return null;
}

function agregarCaracter(caracter) {
	txtbox = document.getElementById(idt);

	if (txtbox.value.length < 14) {
		txtbox.value = txtbox.value + caracter;
		
		if (idt == 'contPre:primerIngresoPre:claveNueva' 
				|| idt == 'contPos:primerIngresoPos:claveNueva' 
					|| idt == 'cambioClaveAcceso:cambioClave:nuevaClaveTemporal'
						|| idt == 'olvidasteClave:recuperarClave:claveNueva'
							|| idt == 'contCambioClave:reestablecerClave:claveNueva') {
			
			if (validarClave()){
				document.getElementById('confirmacion_clave_activo').style.display="";
				document.getElementById('confirmacion_clave_inactivo').style.display="none";
			}
		}
		
		if (idt == 'contPos:primerIngresoPos:confClaveNueva' 
				|| idt == 'contPre:primerIngresoPre:confClaveNueva'
					|| idt == 'cambioClaveAcceso:cambioClave:confirmarNuevaClaveTemporal'
						|| idt == 'olvidasteClave:recuperarClave:confClaveNueva'
							|| idt == 'contCambioClave:reestablecerClave:confirmarClave'){
			
			switch (idt) {
			case 'contPre:primerIngresoPre:confClaveNueva':
				txtClaveNueva = document.getElementById('contPre:primerIngresoPre:claveNueva');
				break;
			case 'contPos:primerIngresoPos:confClaveNueva':
				txtClaveNueva = document.getElementById('contPos:primerIngresoPos:claveNueva');
				break;
			case 'cambioClaveAcceso:cambioClave:confirmarNuevaClaveTemporal':
				txtClaveNueva = document.getElementById('cambioClaveAcceso:cambioClave:nuevaClaveTemporal');
				break;
			case 'olvidasteClave:recuperarClave:confClaveNueva':
				txtClaveNueva = document.getElementById('olvidasteClave:recuperarClave:claveNueva');
				break;
			case 'contCambioClave:reestablecerClave:confirmarClave':
				txtClaveNueva = document.getElementById('contCambioClave:reestablecerClave:claveNueva');
				break;
			default:
				break;
			}			
			
			document.getElementById('mensaje_error_claves').style.display="none";
			
			if (!compararClavesIguales(txtClaveNueva.value,txtbox.value)){
				document.getElementById('mensaje_error_claves').style.display='';
				document.getElementById('confirmacion_clave_inactivo').style.display='none';
				document.getElementById('confirmacion_clave_activo').style.display='';
				document.getElementById('boton_habilitado').style.display='none';
				document.getElementById('boton_inhabilitado').style.display='';
				activacionMensajes();
				txtbox.value = '';
				updateTieneHistorico(true);
			}
		}
		
		
	}else {
		jQuery('#keyboard').fadeOut(500);
		// SESS - 28.03.2010
		// En IE6 no funciona Richfaces.showModalPanel()
		// desde aca, no se porque pero si lo pones como
		// evento JS atado a UI (ej: onclick) si funciona.
		document.getElementById('alertaClave').component.show();
	}
	return null;
}

function borrarCaracter() {
	txtbox = document.getElementById(idt);
	cadena = txtbox.value;
	var ncad = "";
	if (cadena.length > 0) {
		txtbox.value = cadena.substring(0, (cadena.length - 1));
		
		if (idt == 'contPre:primerIngresoPre:claveNueva' 
			|| idt == 'contPos:primerIngresoPos:claveNueva' 
				|| idt == 'cambioClaveAcceso:cambioClave:nuevaClaveTemporal'
					|| idt == 'olvidasteClave:recuperarClave:claveNueva'
						|| idt == 'contCambioClave:reestablecerClave:claveNueva') {
			
			switch (idt) {
			case 'contPre:primerIngresoPre:claveNueva':
				confirmacion = document.getElementById('contPre:primerIngresoPre:confClaveNueva');
				break;
			case 'contPos:primerIngresoPos:claveNueva':
				confirmacion = document.getElementById('contPos:primerIngresoPos:confClaveNueva');
				break;
			case 'cambioClaveAcceso:cambioClave:nuevaClaveTemporal':
				confirmacion = document.getElementById('cambioClaveAcceso:cambioClave:confirmarNuevaClaveTemporal');
				break;
			case 'olvidasteClave:recuperarClave:claveNueva':
				confirmacion = document.getElementById('olvidasteClave:recuperarClave:confClaveNueva');
				break;
			case 'contCambioClave:reestablecerClave:claveNueva':
				confirmacion = document.getElementById('contCambioClave:reestablecerClave:confirmarClave');
				break;
			default:
				break;
			}		
			confirmacion.value = '';
			
			if (!validarClave()){
				document.getElementById('confirmacion_clave_inactivo').style.display='';
				document.getElementById('confirmacion_clave_activo').style.display='none';
				document.getElementById('mensaje_error_claves').style.display="none";
			}
			
			if (cadena.length==0 || cadena == null || cadena == ''){
				activacionMensajes2();
			}
			
			document.getElementById('boton_habilitado').style.display='none';
			document.getElementById('boton_inhabilitado').style.display='';
		}
		
		if (idt == 'contPos:primerIngresoPos:confClaveNueva' 
			|| idt == 'contPre:primerIngresoPre:confClaveNueva'
				|| idt == 'cambioClaveAcceso:cambioClave:confirmarNuevaClaveTemporal'
					|| idt == 'olvidasteClave:recuperarClave:confClaveNueva'
						|| idt == 'contCambioClave:reestablecerClave:confirmarClave'){
			document.getElementById('boton_habilitado').style.display='none';
			document.getElementById('boton_inhabilitado').style.display='';
		}
	}
}

/*
 *  Evento para actualizar la vaidacion de contrasena
 *  luego de que se agrega una caja de texto sin teclado virtual
 * */
function caracterAgregado() { 	 
	 document.getElementById('boton_habilitado').style.display='none';
	 document.getElementById('boton_inhabilitado').style.display='';
	var txtbox = document.getElementById(idt);
	txtbox.value = txtbox.value.toUpperCase();
	if (txtbox.value.length <=14) {
		
		if (idt == 'contPre:primerIngresoPre:claveNueva' || idt == 'contPos:primerIngresoPos:claveNueva'|| idt == 'cambioClaveAcceso:cambioClave:nuevaClaveTemporal'|| idt == 'olvidasteClave:recuperarClave:claveNueva'|| idt == 'contCambioClave:reestablecerClave:claveNueva') {
			
			//flag para deshabilitar la caja de confirmacion de password
			var dis = '';
			
			if (validarClave()){
				dis = '';
			}
			else {
				dis = 'disabled';
			}

			//habilitar la caja de texto de confirmacion de password
			//si la contrasena pasa todas las validaciones
			//De manera similar a los otros switch segun el valor de idt
			//se toma el nombre del campo de confirmacion de clave
			switch (idt) {
			case 'contCambioClave:reestablecerClave:claveNueva':
				document.getElementById('contCambioClave:reestablecerClave:confirmarClave').disabled = dis;
				break;
			case 'contPos:primerIngresoPos:claveNueva':
				document.getElementById('contPos:primerIngresoPos:confClaveNueva').disabled = dis;
				break;
			case 'contPre:primerIngresoPre:claveNueva':
				document.getElementById('contPre:primerIngresoPre:confClaveNueva').disabled = dis;
				break;
			case 'cambioClaveAcceso:cambioClave:nuevaClaveTemporal':
				document.getElementById('cambioClaveAcceso:cambioClave:confirmarNuevaClaveTemporal').disabled = dis;
				break;
			case 'olvidasteClave:recuperarClave:claveNueva':
				document.getElementById('olvidasteClave:recuperarClave:confClaveNueva').disabled = dis;
				break;
			default:
				break;
			}
		}
		
		if (idt == 'contPos:primerIngresoPos:confClaveNueva' || idt == 'contPre:primerIngresoPre:confClaveNueva'|| idt == 'cambioClaveAcceso:cambioClave:confirmarNuevaClaveTemporal'|| idt == 'olvidasteClave:recuperarClave:confClaveNueva'|| idt == 'contCambioClave:reestablecerClave:confirmarClave'){
			//flag para deshabilitar la caja de confirmacion de password
			var dis = '';
			if (validarClave()){
				dis = '';
			
			}
			else {
				dis = 'disabled';
			}
			
			switch (idt) {
			case 'contPre:primerIngresoPre:confClaveNueva':
				txtClaveNueva = document.getElementById('contPre:primerIngresoPre:claveNueva');
				break;
			case 'contPos:primerIngresoPos:confClaveNueva':
				txtClaveNueva = document.getElementById('contPos:primerIngresoPos:claveNueva');
				break;
			case 'cambioClaveAcceso:cambioClave:confirmarNuevaClaveTemporal':
				txtClaveNueva = document.getElementById('cambioClaveAcceso:cambioClave:nuevaClaveTemporal');
				break;
			case 'olvidasteClave:recuperarClave:confClaveNueva':
				txtClaveNueva = document.getElementById('olvidasteClave:recuperarClave:claveNueva');
				break;
			case 'contCambioClave:reestablecerClave:confirmarClave':
				txtClaveNueva = document.getElementById('contCambioClave:reestablecerClave:claveNueva');
				break;
			default:
				break;
			}			
			document.getElementById('mensaje_error_claves').style.display="none";
			
			if (!compararClavesIguales(txtClaveNueva.value,txtbox.value)){
				document.getElementById('mensaje_error_claves').style.display='';
				document.getElementById('boton_habilitado').style.display='none';
				document.getElementById('boton_inhabilitado').style.display='';
				activacionMensajes();
				txtbox.value = '';
				updateTieneHistorico(true);
			}
		}
	}else {
		/*
		 *  Longitud del password muy larga se muestra la notificacion
		 * */
		/* 
		document.getElementById('boton_habilitado').style.display='';
		document.getElementById('boton_inhabilitado').style.display='none';
		*/
		document.getElementById('boton_habilitado').style.display='none';
		document.getElementById('boton_inhabilitado').style.display='';
		//activacionMensajes();
		document.getElementById('error_longitud_no').style.display='';
		document.getElementById('error_longitud_si').style.display='none';
		document.getElementById('alertaClave').component.show();
	}
	return null;
}



function ocultarVentana() {
	document.getElementById("waitDiv").style.display = "none";
	jQuery('#alertMessage').fadeOut(500);
}

function activacionMensajes(){
	/*document.getElementById('mensaje_info').style.display='';
	document.getElementById('mensaje_error').style.display='none';*/
	document.getElementById('error_numeros_no').style.display='none';
	document.getElementById('error_numeros_si').style.display='';
	document.getElementById('error_letras_no').style.display='none';
	document.getElementById('error_letras_si').style.display='';
	document.getElementById('error_longitud_no').style.display='none';
	document.getElementById('error_longitud_si').style.display='';
	
}

function activacionMensajes2(){
	/*document.getElementById('mensaje_info').style.display='none';
	document.getElementById('mensaje_error').style.display='';*/
	document.getElementById('error_numeros_no').style.display='';
	document.getElementById('error_numeros_si').style.display='none';
	document.getElementById('error_letras_no').style.display='';
	document.getElementById('error_letras_si').style.display='none';
	document.getElementById('error_longitud_no').style.display='';
	document.getElementById('error_longitud_si').style.display='none';
}

function cambioCheck(){		
	document.getElementById('error_comienzo_numero_si').style.display=''; 
	document.getElementById('error_comienzo_numero_no').style.display='none';
}

function validarClave(){
	txtbox = document.getElementById(idt);
	cadena = txtbox.value;
	claveCorrecta = false;
	validaNum = false;
	validaLetras = false;
	validaLong = false;

	
	/*document.getElementById('mensaje_info').style.display='none';
	document.getElementById('mensaje_error').style.display='';*/

	regExPatternNumeros = /\d+/; 
	regExPatternInicioLetra = /^[A-ZÑ]/;
	
	
	
	/*if (!regExPatternInicioLetra.test(cadena)){
		document.getElementById('error_comienzo_numero_no').style.display='';
		document.getElementById('error_comienzo_numero_si').style.display='none';
		validaInicio = false;
	}else{
		document.getElementById('error_comienzo_numero_no').style.display='none';
		document.getElementById('error_comienzo_numero_si').style.display='';
		validaInicio = true;
	}*/
	
	if (!regExPatternNumeros.test(cadena)){
		document.getElementById('error_numeros_no').style.display='';
		document.getElementById('error_numeros_si').style.display='none';
		validaNum = false;
	}else{
		document.getElementById('error_numeros_no').style.display='none';
		document.getElementById('error_numeros_si').style.display='';
		validaNum = true;
	}
	
	if (validarLetras(cadena) < 3){
		document.getElementById('error_letras_no').style.display='';
		document.getElementById('error_letras_si').style.display='none';
		validaLetras = false;
	}else{
		document.getElementById('error_letras_no').style.display='none';
		document.getElementById('error_letras_si').style.display='';
		validaLetras = true;
	}
	
	if (cadena.length > 7){
		document.getElementById('error_longitud_no').style.display='none';
		document.getElementById('error_longitud_si').style.display='';
		validaLong = true;
	}else{
		document.getElementById('error_longitud_si').style.display='none';
		document.getElementById('error_longitud_no').style.display='';		
		validaLong = false;
	}

	//alert("valNum: "+validaNum +" valLetra: "+ validaLetras +" valLong:"+ validaLong);
	if (validaNum && validaLetras && validaLong){
		claveCorrecta = true;
	}
	
	
	return claveCorrecta;
}

function validarLetras(cadena){
	regExPatternLetras =/[A-ZÑ]/; 
	contador = 0;
	
	for (i=0; i < cadena.length; i++){
		if (regExPatternLetras.test(cadena.substring(i,i+1))){
			contador++;
		}
		if (contador==3) break;
	}
	
	return contador;
}

function compararClavesIguales(cadena1,cadena2){
	validar = false;
	for (i=0; i < cadena2.length; i++){
		if (cadena1.substring(i,i+1) == cadena2.substring(i,i+1)){
			validar = true;
		}else{
			validar = false;
			break;
		}
	}
	if (cadena1 === cadena2){
		document.getElementById('boton_habilitado').style.display='';
		document.getElementById('boton_inhabilitado').style.display='none';
	}
	else{
		document.getElementById('boton_habilitado').style.display='none';
		document.getElementById('boton_inhabilitado').style.display='';
	}
	return validar;
}

function doNothing() {
	return null;
}

function activarDesactivarBoton() {
	if (document.getElementById('activarRoaming:activarRoaming:checkAcepto:0').checked == false)
		document.getElementById('activarRoaming:activarRoaming:botonCondiciones').disabled = 'disabled';
	else
		document.getElementById('activarRoaming:activarRoaming:botonCondiciones').disabled = '';
}

/*
 *  verificar que se ingreso algo de clave anterior
 * */
function validarClaveAnterior(elemClaveActual, elemClaveNueva) {
	var claveActual = document.getElementById(elemClaveActual);
	var claveNueva = document.getElementById(elemClaveNueva);
	if (claveActual.value == '') {
		//deshabilitar password nuevo
		claveNueva.value = '';
		claveNueva.disabled = 'disabled';
	} else {
		//habilitar password nuevo
		claveNueva.disabled = '';
	}
	return true;
}
 
function activarConfirmacion(idImg,confClave){
	confClave1=confClave;
	document.getElementById(confClave).value='';
	if(idImg != null)
		document.getElementById(confClave).disabled = 'disabled';
	else{
		document.getElementById(confClave).disabled = '';
	}
}

function confirmarClave(clave,claveNueva){
	if(claveNueva==null || claveNueva.value==""){
		return;
	}else{
		if(compararClavesIguales(clave.value.toUpperCase(),claveNueva.value.toUpperCase())){
			document.getElementById('mensaje_error_claves').style.display="none";
		}else{
			document.getElementById(confClave1).value='';
			document.getElementById('mensaje_error_claves').style.display='';
		}
	}
}

// INICIOo 
// Ultima modificación: [27/10/2011] - [Genaro Bermúdez]
// Descripción: funciones javascript que establecen el foco a un textbox y posiciona el curso al final del texto y aplica solo para IE
var textBoxGlobal;
function establecerFocoTextBox(textBox) {
	textBoxGlobal = textBox;
	var cadena = textBoxGlobal.value;
	var pos = cadena.length;
	if (typeof document.selection != 'undefined' && document.selection) {
		var tex = textBoxGlobal.value;
		textBoxGlobal.value = '';
		forzar_focus();
		var str = document.selection.createRange();
		textBoxGlobal.value = tex;
		str.move("character", pos);
		str.moveEnd("character", 0);
		str.select();
	}
}
function forzar_focus() {
	textBoxGlobal.onfocus=null;
	textBoxGlobal.focus();
    setTimeout("textBoxGlobal.onfocus=focus_handler",1);
}
function focus_handler() {
	establecerFocoTextBox(textBoxGlobal);
    return false;
}
//FINn

