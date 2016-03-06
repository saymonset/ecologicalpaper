var $estatus_aplicacion = {
		
		/*
		 *  Base de la URL para las imágenes de 
		 *  las línea de negocio
		 *  
		 *  NOTA: En los objetos de tarjetas prepago
		 *  la url de las imágnes viene COMPLETA,
		 *  por lo que no es necesario concatenar
		 *  esta ruta base
		 * */
		url_base_imagenes: 'images/recarga/',	
		
		
		/*
		 *  íconos de los botones de línea de negocio
		 * 
		 *  out: ícono normal
		 *  over: ícono cuando el mouse está encima
		 *  click: ícono de "botón hundido"
		 *  disable: ícono cuando el botón está deshabilitado
		 * */
		iconos_negocio: {
		
			movil: {
				out: 'movil_out.jpg',
				over: 'movil_over.jpg',
				click: 'movil_click.jpg',
				disable: 'movil_disable.jpg'
			},
			
			fijo: {
				out: 'fijo_out.jpg',
				over: 'fijo_over.jpg',
				click: 'fijo_click.jpg',
				disable: 'fijo_disable.jpg'
			},
			
			tv: {
				out: 'tv_out.jpg',
				over: 'tv_over.jpg',
				click: 'tv_click.jpg',
				disable: 'tv_disable.jpg'
			},
			
			im: {
				out: 'im_out.jpg',
				over: 'im_over.jpg',
				click: 'im_click.jpg',
				disable: 'im_disable.jpg'
			}
		},
		
		/*
		 *  string para indicar el negocio seleccionado
		 *  se genera dinámicamente en la vista
		 *  
		 *  Posibles valores
		 *  - fijo
		 *  - movil
		 *  - tv
		 *  - im
		 *  
		 *  Este valor sirve para indexar el objeto tarjetas, valiéndose
		 *  de la equivalencia entre arrays y objetos de javascript
		 * */
		negocio_seleccionado: '',
		
		/*
		 *  objeto para mantener el estatus de los botones
		 *  de cada linea de negocio
		 *  
		 *  0 no pulsado
		 *  1 pulsado
		 * 
		 *    EJEMPLO DE LA ESTRUCTURA DEL OBJETO
		 *    
		 *    estatus_botones_negocio: {
		 *		movil: 	0,
		 *		fijo: 	0,
		 *		tv: 	0,
		 *		im: 	0
		 *    }  
		 * 
		 * */

		estatus_botones_negocio: {
			 		movil: 	0,
			 		fijo: 	0,
			 		tv: 	0,
			 		im: 	0
			     }   ,
			     
	 	/*
		 *  Tipo de pago "Prepago" o "Pospago"
		 * */
		tipo_pago: ''
		
};


/*
 *  mensajes de error para la validacion del numero en el formulario de ingreso de numero a recargar
 * */
var $mensaje_error_codigo_numero = 'Debes seleccionar el código de área e ingresar un número de 7 dígitos.';
var $mensaje_error_codigo = 'Debes seleccionar el código de área.';
var $mensaje_error_numero = 'Debes ingresar un número de 7 dígitos.';
var $mensaje_error_tv_digital = 'Debes ingresar una cuenta válida de TV digital';


/*
 *  Array con los índices de las líneas de negocios
 * */
var $lineas_negocios = ['movil' , 'fijo', 'im', 'tv'];



/*
* funcion que valida que el campo numero de telefono no acepte 
* carateres especiales ni letras
*/
function valNumero(val, e) {
 	var filter;
 	var keynum;
 	

 	filter = /^[0-9]*$/;
 	
 	keynum = (document.all) ? e.keyCode : e.which; 

 	if (keynum == 0 || keynum==7 || keynum == 8)
 		return true;
 	
 	keychar = String.fromCharCode(keynum);

 	return filter.test(val+keychar);
 }

/*
* funcion que valida que el campo numero de telefono no acepte 
* carateres especiales ni letras
*/
function valNumeroTv(val, e) {
 	var filter;
 	var keynum;
 	

 	filter = /^[0-9]*$/;
 	
 	keynum = (document.all) ? e.keyCode : e.which; 

 	if (keynum == 0 || keynum==8)
 		return true;
 	
 	keychar = String.fromCharCode(keynum);

 	return filter.test(val+keychar);
 }


/*
* funcion que valida que el campo numero de telefono no acepte 
* carateres especiales ni letras
*/
function valMonto(val, e) {
 	var filter;
 	var keynum;
 	

 	filter = /^[0-9]*$/;
 	
 	keynum = (document.all) ? e.keyCode : e.which; 

 	if (keynum == 0 || keynum==7 || keynum == 8)
 		return true;
 	
 	keychar = String.fromCharCode(keynum);

 	return filter.test(val+keychar);
 }



/*
 *  obtiene un objeto con elementos jQuery de los elementos
 *  de un formulario de una línea de negocio
 *  estructura
 *  {
 *     formulario: e, //elemento formulario
 *     input_codigo: e, //entrada del codigo
 *     input_numero: e, //entrada del numero
 *     carrusel: e, //elemento carrusel
 *     elem_input: e, //elementos imput hijos del formulario
 *     elem_select: e //elementos select hijos del formulario
 *     div_error: e //placeholder de error en el formulario
 *  }
 *  
 *  Parametro
 *  
 *  - negocio: linea de negocio: movil, im, tv, fijo
 *  
 * */
function getElementosFormularioNegocio(negocio) {
	
	var elementos = {};
	
	if (negocio == 'movil') {
		elementos = { 
			formulario: $jQuery14("[id=formSeleccionarMovil]"), 	
			input_codigo: $jQuery14("[id=formSeleccionarMovil:codigo_area_movil]"),
			input_numero: $jQuery14("[id=formSeleccionarMovil:numero_movil]"),
			carrusel: $jQuery14("#carruselMovil"),
			elem_input: $jQuery14("[id=formSeleccionarMovil] input"),
			elem_select: $jQuery14("[id=formSeleccionarMovil] select"),
			container_carrusel: $jQuery14("#wrapper_movil"),
			div_error: $jQuery14("[id=formSeleccionarMovil] div.errorClass"),
			div_container_form: $jQuery14("#containerFormMovil"),
			div_container_titulo_form: $jQuery14("#containerTituloFormMovil")
		};
	
	} else if (negocio  == 'fijo') {
		elementos = {
			formulario: $jQuery14("[id=formSeleccionarFijo]"),	
			input_codigo: $jQuery14("[id=formSeleccionarFijo:codigo_area_fijo]"),
			input_numero: $jQuery14("[id=formSeleccionarFijo:numero_fijo]"),
			carrusel: $jQuery14("#carruselFijo"),
			container_carrusel: $jQuery14("#wrapper_fijo"),
			elem_input: $jQuery14("[id=formSeleccionarFijo] input"),
			elem_select: $jQuery14("[id=formSeleccionarFijo] select"),
			div_error: $jQuery14("[id=formSeleccionarFijo] div.errorClass"),
			div_container_form: $jQuery14("#containerFormFijo"),
			div_container_titulo_form: $jQuery14("#containerTituloFormFijo")
		};
	
	} else if (negocio  == 'im') {
		elementos = {
			formulario: $jQuery14("[id=formSeleccionarCuentaIM]"),
			input_codigo: $jQuery14("[id=formSeleccionarCuentaIM:codigo_area_im]"),
			input_numero: $jQuery14("[id=formSeleccionarCuentaIM:numero_im]"),
			carrusel: $jQuery14("#carruselInternetMovil"),
			container_carrusel: $jQuery14("#wrapper_im"),
			elem_input: $jQuery14("[id=formSeleccionarCuentaIM] input"),
			elem_select: $jQuery14("[id=formSeleccionarCuentaIM] select"),
			div_error: $jQuery14("[id=formSeleccionarCuentaIM] div.errorClass"),
			div_container_form: $jQuery14("#containerFormIM"),
			div_container_titulo_form: $jQuery14("#containerTituloFormIM")
		};
	
	} else if (negocio  == 'tv') {
		elementos = {
			formulario: $jQuery14("[id=formSeleccionarCuentaTV]"),
			input_codigo: '',
			input_numero: $jQuery14("[id=formSeleccionarCuentaTV:numero_tv]"),
			carrusel: $jQuery14("#carruselTVDigital"),
			container_carrusel: $jQuery14("#wrapper_tv"),
			elem_input: $jQuery14("[id=formSeleccionarCuentaTV] input"),
			elem_select: $jQuery14("[id=formSeleccionarCuentaTV] select"),
			div_error: $jQuery14("[id=formSeleccionarCuentaTV] div.errorClass"),
			div_container_form: $jQuery14("#containerFormTV"),
			div_container_titulo_form: $jQuery14("#containerTituloFormTV")
		};
	
	}
	
	return elementos;
}

/*
 *  Aparece el texto debajo del detalle compra
 * */
function mostrarDivTextoDetalleCompra(tipo_pago) {
	
	if (tipo_pago.toLowerCase() == 'pospago') {
		$jQuery14("#textoDetalleTransferencia").show();
	} else {
		$jQuery14("#textoDetalleTransferencia").hide();
	}	
}

/*
 *  Oculta los elementos de introducción de código de área y número de teléfrono
 *  en el formulario tipo nube para cada área de negocio
 * */
function ocultarCamposFormularioIntroducirNumero(negocio, codigo_area, numero) {
	
	var elementos_formulario = getElementosFormularioNegocio(negocio);
	
	/*
	 *  Ocultar los campos del formulariio
	 * */
	elementos_formulario.elem_input.hide();
	if(negocio != 'tv')elementos_formulario.elem_select.hide();
	
	/*
	 * guardar el valor en los campos ocultos
	 * */
	if(negocio != 'tv')elementos_formulario.input_codigo.siblings('span.valor').text(codigo_area).hide();
	elementos_formulario.input_numero.siblings('span.valor').text(numero).hide();
	
}


/*
 *  Muestra el número y de código de área y número de teléfrono
 *  ingresados por el usuario, en el espacio del formulario tipo nube para cada área de negocio
 * */
function mostrarNumeroTransaccionFormulario(negocio, codigo_area, numero) {

	var numeroCuenta;
	var elementos_formulario = getElementosFormularioNegocio(negocio);

	
	/*
	 *  Colocar en el espacio del formulario el número a recargar
	 * */
	if (codigo_area == '') {
		//solo se tiene número de cuenta (caso tv digital)
		numeroCuenta = numero;
	} else {
		//se tiene numero y codigo
		numeroCuenta = codigo_area + '-' + numero;
	}
	
	var codigoHtmlValor = ""; 
	codigoHtmlValor =  codigoHtmlValor + "<span class=\"numero-a-recargar\" style=\"font-size: 12pt; color: #ffffff; font-weight: bold\">";
	codigoHtmlValor =  codigoHtmlValor + numeroCuenta;
	codigoHtmlValor =  codigoHtmlValor + "</span>";
	
	elementos_formulario.div_container_form.append(codigoHtmlValor);
	
	//cambiar el titulo
	if (negocio == 'tv') {
		elementos_formulario.div_container_titulo_form.text("La cuenta a transferir es");
	} else {
		elementos_formulario.div_container_titulo_form.text("El número a transferir es");
	}
}



function validarNumeroMovilAfterValidate (data) {
	
	var div_error = jQuery("#form_selec_movil div.errorClass"); 
	var resultado  = eval("(" + data + ")");
	
	
	if (resultado.valido == 'true') {
		var tipo_pago = resultado.tipo_pago;
		iniciarEstadoDespuesValidarNumero('movil',tipo_pago);

		div_error.hide();
	} else {
		if(resultado.error != null && resultado.error != '') div_error.text(resultado.error).hide().fadeIn('slow');	
		else div_error.text("El número ingresado no es un móvil válido").hide().fadeIn('slow');
		
		$jQuery14('[id=formSeleccionarMovil:codigo_area_movil]').attr('disabled',false);
		$jQuery14('[id=formSeleccionarMovil:numero_movil]').attr('disabled',false);
		$jQuery14('[id=formSeleccionarMovil:boton_validar_movil]').attr('disabled',false);
		
		iniciarEventosBotonesLineaNegocio();
	}
	
	
	
	return false;
}


function validarNumeroFijoAfterValidate (data) {
	
	var div_error = jQuery("#form_selec_fijo div.errorClass"); 
	var resultado  = eval("(" + data + ")");
	
	if (resultado.valido == 'true') {
		var tipo_pago = resultado.tipo_pago;
		iniciarEstadoDespuesValidarNumero('fijo',tipo_pago);

		div_error.hide();				
	} else {
		if(resultado.error != null && resultado.error != '') div_error.text(resultado.error).hide().fadeIn('slow');	
		else div_error.text("El número ingresado no es un fijo válido").hide().fadeIn('slow');
		
		$jQuery14('[id=formSeleccionarFijo:codigo_area_fijo]').attr('disabled',false);
		$jQuery14('[id=formSeleccionarFijo:numero_fijo]').attr('disabled',false);
		$jQuery14('[id=formSeleccionarFijo:boton_validar_fijo]').attr('disabled',false);
		
		iniciarEventosBotonesLineaNegocio();
	}
	
	return false;
}


function validarTVDigitalAfterValidate (data) {
	
	var div_error = jQuery("#form_selec_tv div.errorClass");
	var resultado  = eval("(" + data + ")");
	
	if (resultado.valido == 'true') {
		var tipo_pago = resultado.tipo_pago;
		iniciarEstadoDespuesValidarNumero('tv',tipo_pago);

		div_error.hide();				
	} else {
		if(resultado.error != null && resultado.error != '') div_error.text(resultado.error).hide().fadeIn('slow');	
		else div_error.text("El número ingresado no es un tv válido").hide().fadeIn('slow');

		$jQuery14('[id=formSeleccionarCuentaTV:numero_tv]').attr('disabled',false);
		$jQuery14('[id=formSeleccionarCuentaTV:boton_validar_tv_digital]').attr('disabled',false);
		
		iniciarEventosBotonesLineaNegocio();
	}
	
	return false;
}


function validarInternetMovilAfterValidate (data) {

	var div_error = jQuery("#form_selec_im div.errorClass");
	var resultado  = eval("(" + data + ")");
	
	if (resultado.valido == 'true') {
		var tipo_pago = resultado.tipo_pago;
		iniciarEstadoDespuesValidarNumero('im',tipo_pago);
		
		div_error.hide();
	} else {
		if(resultado.error != null && resultado.error != '') div_error.text(resultado.error).hide().fadeIn('slow');	
		else div_error.text("El número ingresado no es una cuenta internet válida").hide().fadeIn('slow');
		
		$jQuery14('[id=formSeleccionarCuentaIM:codigo_area_im]').attr('disabled',false);
		$jQuery14('[id=formSeleccionarCuentaIM:numero_im]').attr('disabled',false);
		$jQuery14('[id=formSeleccionarCuentaIM:boton_validar_internet_movil]').attr('disabled',false);
		
		iniciarEventosBotonesLineaNegocio();
	}
	
	return false;
}




function mostrarModalTransferencia(){
	
	//llenar los valores del modarl
	var negocio = "";
	var monto_recarga = $jQuery14("[id=formSiguiente:montoTransferencia]").val();
	var codigo_area =  $jQuery14("[id=formSiguiente:codigoArea]").val();
	codigo_area = "(" + codigo_area + ")";
	var numero =  $jQuery14("[id=formSiguiente:numero]").val();
	
	if ($estatus_aplicacion.negocio_seleccionado == 'movil') {
		negocio = "al Móvil";
	} else if ($estatus_aplicacion.negocio_seleccionado == 'fijo') {
		negocio = "al Fijo";
	} else if ($estatus_aplicacion.negocio_seleccionado == 'im') {
		negocio = "al Internet Móvil";
	} else if ($estatus_aplicacion.negocio_seleccionado == 'tv') {
		negocio = "a la cuenta TV";
		codigo_area = "";
	}else if ($estatus_aplicacion.negocio_seleccionado == 'tarif') {
		negocio = "al Tarificador";
	}
	
	$jQuery14("#modalMontoRecarga").text(monto_recarga);
	$jQuery14("#modalNegocio").text(negocio);
	$jQuery14("#modalCodigoArea").text(codigo_area);
	$jQuery14("#modalNumero").text(numero);
	Richfaces.showModalPanel('modalTransferencia');
}

function ocultarModalTransferencia(){
	Richfaces.hideModalPanel('modalTransferencia');
}

function mostrarModalEspera(){
	Richfaces.showModalPanel('modalEspera');	
}

function bloquearBotonesModal(){
	var sel_modal_boton_cancelar = '[id=formSiguiente:botonCancelarModal]';
	var sel_modal_boton_siguiente = '[id=formSiguiente:botonAceptarModal]';
	$jQuery14(sel_modal_boton_cancelar).attr('disabled',true);
	$jQuery14(sel_modal_boton_siguiente).attr('disabled',true);
}

/*
 *  Inicializa el formulario para una línea de negocio determinada
 * 
 *   negocio: El tipo de negocio (movil, fijo, im, tv)
 *   ocultar: Flag que indica si se debe ocultar el formulario
 * */
function iniciarFormularioNegocio(negocio, ocultar) {
	
	var selector_formulario = '#form_selec_' + negocio;
	var selector_form  = selector_formulario + " form";
	
	/*
	 *  Ver si se debe ocultar el formulario
	 * */
	if (ocultar) {
		$estatus_aplicacion.estatus_botones_negocio[negocio] = 0;
		var selector_imagen = '#img_selec_' +  negocio + ' img';
		var src_imagen = $estatus_aplicacion.url_base_imagenes + $estatus_aplicacion.iconos_negocio[negocio].out;
		$jQuery14(selector_formulario).hide();
		$jQuery14(selector_imagen).attr('src', src_imagen);
	} 
	
	/*
	 *  Resetear el formulario
	 * */
	$jQuery14(selector_form).get(0).reset();
	
	var div_error = jQuery("#form_selec_"+negocio+" div.errorClass"); 
	div_error.hide();
}



/*
 *  Alterna el formulario de una línea de negocio
 *  según sea seleccionada
 *  
 *  si mostrar == true entonces se muestra
 *  si mostrar == false entonces se oculta
 * 
 * */
function alternarFormularioNegocio(negocio, mostrar) {
	
	var estado;
	var negocio_seleccionado;
	var src_imagen;
	var selector_formulario = '#form_selec_' + negocio;
	var selector_imagen = '#img_selec_' +  negocio + ' img';
	
	if (mostrar) {
		estado = 1;
		negocio_seleccionado = negocio;
		src_imagen = $estatus_aplicacion.url_base_imagenes  + $estatus_aplicacion.iconos_negocio[negocio].click;
	} else {
		estado = 0;
		negocio_seleccionado = '';
		src_imagen = $estatus_aplicacion.url_base_imagenes  + $estatus_aplicacion.iconos_negocio[negocio].out;
	}
	
	//cambiar imagen
	$jQuery14(selector_imagen).attr('src', src_imagen);
	//actualizar el estatus de los botones
	$estatus_aplicacion.estatus_botones_negocio[negocio] = estado;
	//actualizar el negocio seleccionado
	$estatus_aplicacion.negocio_seleccionado = negocio_seleccionado;
	
	/*
	 *  mostrar u ocultar el formulario
	 * */
	if (mostrar) {
		$jQuery14(selector_formulario).fadeIn('slow');
	} else {
		$jQuery14(selector_formulario).fadeOut('slow');
	}
	
	
}




/*
 *  Actualiza las imágenes y callbacks de eventos de los íconos
 *  de selección de línea de negocios, luego de hacer click
 *  
 *  - negocio_seleccionado Linea de negocio a la cual se le hizo click
 *  - negocio_actual Alguna de las lineas de negocio
 *  
 *  si negocio_seleccionado != negocio_actual, se desactivan los eventos de click para los iconos
 * 
 * */
function florecitaMagica(negocio_seleccionado, negocio_actual) {
	
	var selector_icono_negocio_seleccionado = '#img_selec_' +  negocio_seleccionado + ' img';;
	var selector_icono_negocio_actual = '#img_selec_' +  negocio_actual + ' img';
	var e = $jQuery14(selector_icono_negocio_actual); 
	
	/*
	 *  Si el negocio seleccionado es distinto al actual
	 *  se desactivan los eventos de ratón para el ícono de negocio
	 * */
	var desactivar_eventos = (negocio_actual != negocio_seleccionado);
	
	if (desactivar_eventos) {
		var img_disable =  $estatus_aplicacion.url_base_imagenes + $estatus_aplicacion.iconos_negocio[negocio_actual].disable;
		//desactivar los eventos de raton 
		e.attr('src', img_disable);
		e.unbind('mouseover');
		e.unbind('mouseout');
	} 
	
	/*
	 * desactivar el evento de click para todos los botones
	 */
	e.unbind('click');	
}

/*
 *  Actualiza las imágenes y callbacks de eventos de los íconos
 *  de selección de línea de negocios, luego de hacer click
 *  
 *  - negocio_seleccionado Linea de negocio a la cual se le hizo click
 *  - negocio_actual Alguna de las lineas de negocio
 *  
 *  si negocio_seleccionado != negocio_actual, se desactivan los eventos de click para los iconos
 * 
 * */
function florecitaMagica2(negocio_seleccionado, negocio_actual) {
	
	var selector_icono_negocio_seleccionado = '#img_selec_' +  negocio_seleccionado + ' img';;
	var selector_icono_negocio_actual = '#img_selec_' +  negocio_actual + ' img';
	var e = $jQuery14(selector_icono_negocio_actual); 
	
	/*
	 *  Si el negocio seleccionado es distinto al actual
	 *  se desactivan los eventos de ratón para el ícono de negocio
	 * */
	var desactivar_eventos = (negocio_actual != negocio_seleccionado);
	
	if (desactivar_eventos) {
//		var img_disable =  $estatus_aplicacion.url_base_imagenes + $estatus_aplicacion.iconos_negocio[negocio_actual].disable;
//		//desactivar los eventos de raton 
//		e.attr('src', img_disable);
		e.unbind('mouseover');
		e.unbind('mouseout');
	} 
	
	/*
	 * desactivar el evento de click para todos los botones
	 */
	e.unbind('click');	
}



function iniciarEstadoControlesLineaNegocio(elemento) {
	
	var i ;
	var n;
	var estatus_botones  = $estatus_aplicacion.estatus_botones_negocio;
	var elementos = $lineas_negocios;
	var selector  = '#img_selec_' +  elemento + ' img';;
	
	/*
	 *  Se recorren todos los estatus de los botones
	 *  para ocultar el formulario en todas las líneas de negocios
	 *  distintas a la seleccionada
	 * */
	for ( i in  $estatus_aplicacion.estatus_botones_negocio) {
		var ocultar = (i != elemento);
		
		iniciarFormularioNegocio(i, ocultar);
	}
	
	estatus_botones[elemento] = 1;
	
	/*
	 *  Si el formulario no está seleccionado
	 *  es el formulario a seleccionar, por lo que se muestra
	 *  
	 *  en caso contrario se oculta
	 * */
	alternarFormularioNegocio(elemento, true);
	
}

/*
 *  Desactiva los botones de la línea de negocios 
 *  no seleccionada
 * */
function desactivarIconosLineasNegociosNoSeleccionadas() {
	var i;
	var elemento = $estatus_aplicacion.negocio_seleccionado;
	var elementos_negocios = $lineas_negocios;
	n = elementos_negocios.length;
	
	for (i = 0; i < n ; ++i) {
		
		florecitaMagica(elemento, elementos_negocios[i]);
	}
	
}

/*
 *  Desactiva los botones de la línea de negocios 
 *  no seleccionada
 * */
function desactivarIconosLineasNegociosNoSeleccionadasAlValidar() {
	var i;
	var elemento = $estatus_aplicacion.negocio_seleccionado;
	var elementos_negocios = $lineas_negocios;
	n = elementos_negocios.length;
	
	for (i = 0; i < n ; ++i) {
		
		florecitaMagica2(elemento, elementos_negocios[i]);
	}
	
}



/*
 *  Inicializa el formulario y los carruseles
 *  después del evento de validación remota
 *  según la línea de negocio indicada
 * */
function iniciarEstadoDespuesValidarNumero(negocio, tipo_pago) {
	
	/*
	 *  Desactivar todas las imágenes
	 *  y desactivar el evento click sobre ellas
	 * */ 
	desactivarIconosLineasNegociosNoSeleccionadas();
	
	
	
	//sacar los elementos del formulario para el negocio
	var elementos = getElementosFormularioNegocio(negocio);
	
	//setear el tipo de pago en el estado
	$estatus_aplicacion.tipo_pago = tipo_pago;
	
	/*
	 * ocultar los controles del formulario
	 * y dejar los valores ingresados como texto estátido
	 * mostrar carrusel de tarjetas
	 * iniciar la tabla con el detalle de compra
	 * ocultar mensaje de error si hay
	 */
	var codigo, numero;
	
	try {
		codigo =  $jQuery14.trim(elementos.input_codigo.val());
	}
	catch(e) {
		codigo = '';
	}
	
	try {
		numero =  $jQuery14.trim(elementos.input_numero.val());
	}
	catch(e) {
		numero = '';
	}
	
	ocultarCamposFormularioIntroducirNumero(negocio, codigo, numero);
	mostrarNumeroTransaccionFormulario(negocio, codigo, numero);
	
	/*
	mostrarDivTextoDetalleCompra(tipo_pago);
	
	actualizarBotonAnterior();
	*/
	mostrarFormularioMontoRecargar();
	actualizarBotonAnterior();
	
	mostrarDivTextoDetalleCompra(tipo_pago);
	
	jQuery("#texto_campo_obligatorio").show();
}



/*
 *  Genera dinamicamente un callback para el evento
 *  de mouseout para cada uno de los íconos de la línea de negocio
 *  de acuerdo a la línea de negocio seleccionada
 * */
function getEvtImgLineaNegocioMouseOut(elemento) {
	
	var florecita = 
		function() {
		
			/*
			 *  Ver el estatus del ícono para la línea de negocio
			 * */
			var estatus_boton = $estatus_aplicacion.estatus_botones_negocio[elemento];
			
			/*
			 *  Según el estatus del botón de línea de negocio 
			 *  se sustituye la imagen del ícono
			 * */
			var src_imagen = '';
			if (estatus_boton == 0) {
				//el botón no está seleccionado, la imagen normal
				src_imagen =  $estatus_aplicacion.url_base_imagenes + $estatus_aplicacion.iconos_negocio[elemento].out;
				
			} else {
				//el botón sí está seleccionado, la imagen pulsada
				src_imagen = $estatus_aplicacion.url_base_imagenes + $estatus_aplicacion.iconos_negocio[elemento].click;
			}
			$jQuery14(this).attr('src', src_imagen);
		};
	
	return florecita;
}





/*
 *  Genera dinamicamente un callback para el evento
 *  de mouseover para cada uno de los íconos de la línea de negocio
 *  de acuerdo a la línea de negocio seleccionada
 * */
function getEvtImgLineaNegocioMouseOver(elemento) {
	
	var florecita = 
		function() {
		
			var estatus_boton = $estatus_aplicacion.estatus_botones_negocio[elemento];
		
			/*
			 *  Según el estatus del botón de línea de negocio 
			 *  se sustituye la imagen del ícono
			 * */
			var src_imagen = '';
			if (estatus_boton == 0) {
				
				//el botón no está seleccionado, la imagen over
				src_imagen =  $estatus_aplicacion.url_base_imagenes + $estatus_aplicacion.iconos_negocio[elemento].over;
			} else {
				//el botón sí está seleccionado, la imagen pulsada
				src_imagen =  $estatus_aplicacion.url_base_imagenes + $estatus_aplicacion.iconos_negocio[elemento].click;
			}
			$jQuery14(this).attr('src', src_imagen);
		};
	
	return florecita;
}






/*
 *  Genera dinamicamente un callback para el evento
 *  de click para cada uno de los íconos de la línea de negocio
 *  de acuerdo a la línea de negocio seleccionada
 * */
function getEvtImgLineaNegocioOnClick(elemento) {
	var florecita = 
		function() {
		
			
			iniciarEstadoControlesLineaNegocio(elemento);
			return false;
		};
	
	return florecita;
}




function validarNumeroMovilOnClick() {
	

	var elementos = getElementosFormularioNegocio('movil');
	
	//sacar datos del formulario
	var codigo =  $jQuery14.trim(elementos.input_codigo.val());
	var numero =  $jQuery14.trim(elementos.input_numero.val());
	
	var div_error = jQuery("#form_selec_movil div.errorClass"); 
	var mensaje_error = '';
	
	var valido_codigo = (codigo != '') && (codigo != '0');
	var valido_numero = (numero.length == 7) && (/^\d+$/.test(numero));	
	
	if (!valido_codigo && !valido_numero) {
		mensaje_error = $mensaje_error_codigo_numero;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}  else if (!valido_codigo) {
		mensaje_error = $mensaje_error_codigo;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}   else if (!valido_numero) {
		mensaje_error = $mensaje_error_numero;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}
	div_error.hide();	

	$jQuery14('[id=formSeleccionarMovil:codigo_area_movil]').attr('disabled',true);
	$jQuery14('[id=formSeleccionarMovil:numero_movil]').attr('disabled',true);
	$jQuery14('[id=formSeleccionarMovil:boton_validar_movil]').attr('disabled',true);	
	
	desactivarIconosLineasNegociosNoSeleccionadasAlValidar();
	
	//validación ajax contra CVSC
	jsValidarMovil(codigo,numero);
	return false;
}


function validarNumeroFijoOnClick() {
	
	var elementos = getElementosFormularioNegocio('fijo');
	
	//sacar datos del formulario
	var codigo =  $jQuery14.trim(elementos.input_codigo.val());
	var numero =  $jQuery14.trim(elementos.input_numero.val());
	
	var div_error = jQuery("#form_selec_fijo div.errorClass"); 
	var mensaje_error = '';
	
	var valido_codigo = (codigo != '')&& (codigo != '0');
	var valido_numero = (numero.length == 7) && (/^\d+$/.test(numero));
	
	if (!valido_codigo && !valido_numero) {
		mensaje_error = $mensaje_error_codigo_numero;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}  else if (!valido_codigo) {
		mensaje_error = $mensaje_error_codigo;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}   else if (!valido_numero) {
		mensaje_error = $mensaje_error_numero;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}
	div_error.hide();

	$jQuery14('[id=formSeleccionarFijo:codigo_area_fijo]').attr('disabled',true);
	$jQuery14('[id=formSeleccionarFijo:numero_fijo]').attr('disabled',true);
	$jQuery14('[id=formSeleccionarFijo:boton_validar_fijo]').attr('disabled',true);	
	
	desactivarIconosLineasNegociosNoSeleccionadasAlValidar();
	
	//validación ajax contra CVSC
	jsValidarFijo(codigo,numero);
	return false;
}


function validarTVDigitalOnClick() {
	
	var elementos = getElementosFormularioNegocio('tv');
	
	//sacar datos del formulario
	var numero =  $jQuery14.trim(elementos.input_numero.val());
	
	var div_error = jQuery("#form_selec_tv div.errorClass");
	var mensaje_error = '';
	
	var valido_numero = (numero.length == 8) && (/^\d+$/.test(numero));
	
	 if (!valido_numero) {
		mensaje_error = $mensaje_error_tv_digital;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}
	div_error.hide();

	$jQuery14('[id=formSeleccionarCuentaTV:numero_tv]').attr('disabled',true);
	$jQuery14('[id=formSeleccionarCuentaTV:boton_validar_tv_digital]').attr('disabled',true);

	desactivarIconosLineasNegociosNoSeleccionadasAlValidar();
	
	//validación ajax contra CVSC
	jsValidarTVDigital(numero);
	return false;
}


function validarInternetMovilOnClick() {

	var elementos = getElementosFormularioNegocio('im');
	
	//sacar datos del formulario
	var codigo =  $jQuery14.trim(elementos.input_codigo.val());
	var numero =  $jQuery14.trim(elementos.input_numero.val());
	
	var div_error = jQuery("#form_selec_im div.errorClass");
	var mensaje_error = '';
	
	var valido_codigo = (codigo != '') && (codigo != '0');
	var valido_numero = (numero.length == 7) && (/^\d+$/.test(numero));
	
	if (!valido_codigo && !valido_numero) {
		mensaje_error = $mensaje_error_codigo_numero;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}  else if (!valido_codigo) {
		mensaje_error = $mensaje_error_codigo;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}   else if (!valido_numero) {
		mensaje_error = $mensaje_error_numero;
		div_error.text(mensaje_error).hide().fadeIn('slow');
		return false;
	}
	div_error.hide();
	
	$jQuery14('[id=formSeleccionarCuentaIM:codigo_area_im]').attr('disabled',true);
	$jQuery14('[id=formSeleccionarCuentaIM:numero_im]').attr('disabled',true);
	$jQuery14('[id=formSeleccionarCuentaIM:boton_validar_internet_movil]').attr('disabled',true);
	
	desactivarIconosLineasNegociosNoSeleccionadasAlValidar();
	
	//validación ajax contra CVSC
	jsValidarIM(codigo,numero);
	return false;
}



/*
 *  Actualiza el estado del botón anterior
 * */
function actualizarBotonAnterior() {
	var sel_boton_anterior='[id=formSiguiente:botonAnterior]';
	$jQuery14(sel_boton_anterior).css('visibility','visible').attr('disabled',false);
	
}

/*
 *  Manejador del evento del botón siguiente
 * */
function botonSiguienteOnClick () {
	
	
	
	var elem_error = $jQuery14("#_errorMonto");
	
	elem_error.hide();
	
	/*
	 *  validar que venga el monto y que sea numérico
	 * */
	var sel_monto = $jQuery14("[id=formSiguiente:montoTransferencia]");
	
	var monto = $jQuery14.trim(sel_monto.val()).replace(',','.');
	sel_monto.val(monto);

	var monto_maximo = parseFloat($montoMaximoTransferencia.replace(',','.'));
	
	
	var regex = /^(\d+)(\.\d*)?$/;
	var valido = regex.test(monto);
	var elem_error = $jQuery14("#_errorMonto");
	
	
	if (!valido) {
		//error: monto no numérico
		elem_error.text("Debes colocar el monto a transferir.");
		elem_error.fadeIn("slow");
		return false;
	} else {
		
	//	sel_monto.formatNumber({format:"#.00", locale:"us"});
		
		
		//error el monto no es mayor a 0
		var m1=monto.charAt(0);
		if(m1=='0'){
			elem_error.text("El monto mínimo a transferir es de Bs. 1,00.");
			elem_error.fadeIn("slow");
			return false;
		}
		var m = parseFloat(monto);
		if (!(m > 0)) {
			elem_error.text("El monto mínimo a transferir es de Bs. 1,00.");
			elem_error.fadeIn("slow");
			return false;
		}
		
		
		//validar que el monto no tenga céntimos
		var parteEntera = Math.floor(m);
		var parteDecimal = Math.abs(m - parteEntera);
		
		if (parteDecimal > 0) {
			elem_error.text("Debes ingresar un monto entero, con 0 (cero) céntimos.");
			elem_error.fadeIn("slow");
			return false;
		}
		
		
		//validar que no se pasa el monto máximo
		if (m > monto_maximo) {
			elem_error.text("El monto máximo a transferir es de Bs. " + $montoMaximoTransferencia+".");
			elem_error.fadeIn("slow");
			return false;
		} 
	}
		
	elem_error.hide();
	
	/*
	 *  pasar los valores al formulario oculto
	 * */
	//segun el negocio seleccionado se llenan el resto de campos
	//para el caso de movil, fijo e im se carga codigo de area y numero
	//en tv solo el numero
	
	elementos = getElementosFormularioNegocio($estatus_aplicacion.negocio_seleccionado);
	
	//para IE deben habilitarse temporalmente los campos del formulario para sacar la información
	if($estatus_aplicacion.negocio_seleccionado != 'tv') elementos.input_codigo.attr('disabled',false);
	elementos.input_numero.attr('disabled',false);
	
	try {
		if($estatus_aplicacion.negocio_seleccionado != 'tv') $jQuery14("[id=formSiguiente:codigoArea]").val(elementos.input_codigo.val());
		else $jQuery14("[id=formSiguiente:codigoArea]").val('');
	} catch (e) {
		$jQuery14("[id=formSiguiente:codigoArea]").val('');
	}
	
	
	try {
		$jQuery14("[id=formSiguiente:numero]").val(elementos.input_numero.val());
	} catch (e) {
		$jQuery14("[id=formSiguiente:numero]").val('');
	}
	
	if($estatus_aplicacion.negocio_seleccionado != 'tv') elementos.input_codigo.attr('disabled',true);
	elementos.input_numero.attr('disabled',true);
	
	$jQuery14("[id=formSiguiente:negocio]").val($estatus_aplicacion.negocio_seleccionado);
	$jQuery14("[id=formSiguiente:tipoPago]").val($estatus_aplicacion.tipo_pago);
	
	
	
	mostrarModalTransferencia();
	
	return false;
}
		

function botonCancelarOnClick() {
	/*
	 *  se le pone un valor al campo de monto a recargar para saltarse la validación
	 * */
	$jQuery14("[id=formSiguiente:montoTransferencia]").hide().val('1');
	
	return true;
}


function mostrarFormularioMontoRecargar() {
	var sel_boton_siguiente = '[id=formSiguiente:botonSiguiente]';
	var sel_fila_monto = "#filaMontoTransferir"; 
	$jQuery14(sel_boton_siguiente).attr('disabled',false);
	$jQuery14(sel_fila_monto).fadeIn('slow');
	
}

/*
 *  Manejador del botón aceptar del modal
 * */
function modalBotonAceptarOnClick () {

	bloquearBotonesModal();
//	ocultarModalTransferencia();
	return true;
}

/*
 *  Manejador del botón cancelar del modal
 * */
function modalBotonCancelarOnClick () {
	
	ocultarModalTransferencia();
	return false;
}


function iniciarEventosBotonesLineaNegocio() {
	
	var sel_icono_tel_movil = '#img_selec_movil img';
	var sel_icono_tel_fijo = '#img_selec_fijo img';
	var sel_icono_tv_digital = '#img_selec_tv img';
	var sel_icono_internet_movil = '#img_selec_im img';
	
	/*
	 *  Asignar eventos de mouseover para los iconos
	 *  Asignar eventos de click para los iconos
	 * */
	$jQuery14(sel_icono_tel_movil)
				.hover(getEvtImgLineaNegocioMouseOver('movil'), getEvtImgLineaNegocioMouseOut('movil'))
				.click(getEvtImgLineaNegocioOnClick('movil'));
	$jQuery14(sel_icono_tel_fijo)
				.hover(getEvtImgLineaNegocioMouseOver('fijo'), getEvtImgLineaNegocioMouseOut('fijo'))
				.click(getEvtImgLineaNegocioOnClick('fijo'));
	$jQuery14(sel_icono_tv_digital)
				.hover(getEvtImgLineaNegocioMouseOver('tv'), getEvtImgLineaNegocioMouseOut('tv'))
				.click(getEvtImgLineaNegocioOnClick('tv'));
	$jQuery14(sel_icono_internet_movil)
				.hover(getEvtImgLineaNegocioMouseOver('im'), getEvtImgLineaNegocioMouseOut('im'))
				.click(getEvtImgLineaNegocioOnClick('im'));	
}



function desactivarEventoClickBotonesLineaNegocio () {
	
	var selector_botones = '#img_selec_movil img, #img_selec_fijo img, #img_selec_tv img, #img_selec_im img';
	$jQuery14(selector_botones).unbind('click');
}

function activarEventoClickBotonesLineaNegocio () {
	var sel_icono_tel_movil = '#img_selec_movil img';
	var sel_icono_tel_fijo = '#img_selec_fijo img';
	var sel_icono_tv_digital = '#img_selec_tv img';
	var sel_icono_internet_movil = '#img_selec_im img';
	
	$jQuery14(sel_icono_tel_movil).click(getEvtImgLineaNegocioOnClick('movil'));
	$jQuery14(sel_icono_tel_fijo).click(getEvtImgLineaNegocioOnClick('fijo'));
	$jQuery14(sel_icono_tv_digital).click(getEvtImgLineaNegocioOnClick('tv'));
	$jQuery14(sel_icono_internet_movil).click(getEvtImgLineaNegocioOnClick('im'));	
	
}


function iniciarEventosFormulariosTelefono() {
	/*
	 *  Asignar eventos a los botones de los formularios de validar
	 *  número para cada opción de negocio
	 * */
	var sel_boton_validar_movil = '[id=formSeleccionarMovil:boton_validar_movil]';
	var sel_boton_validar_fijo = '[id=formSeleccionarFijo:boton_validar_fijo]';
	var sel_boton_validar_tv_digital = '[id=formSeleccionarCuentaTV:boton_validar_tv_digital]';
	var sel_boton_validar_internet_movil = '[id=formSeleccionarCuentaIM:boton_validar_internet_movil]';
	
	$jQuery14(sel_boton_validar_movil).click(validarNumeroMovilOnClick);
	$jQuery14(sel_boton_validar_fijo).click(validarNumeroFijoOnClick);
	$jQuery14(sel_boton_validar_tv_digital).click(validarTVDigitalOnClick);
	$jQuery14(sel_boton_validar_internet_movil).click(validarInternetMovilOnClick);

}

function iniciarEventosBotonesAnteriorSiguiente() {
	var sel_boton_cancelar = '[id=formSiguiente:botonCancelar]';
	var sel_boton_siguiente = '[id=formSiguiente:botonSiguiente]';
	var sel_boton_anterior = '[id=formSiguiente:botonAnterior]';
	$jQuery14(sel_boton_cancelar).css('visibility','visible');
	$jQuery14(sel_boton_siguiente).click(botonSiguienteOnClick).css('visibility','visible').attr('disabled',true);
	$jQuery14(sel_boton_anterior).css('visibility','visible').attr('disabled',true);
	
}

function iniciarEventosBotonesModal() {
	var sel_modal_boton_cancelar = '[id=formSiguiente:botonCancelarModal]';
	var sel_modal_boton_siguiente = '[id=formSiguiente:botonAceptarModal]';
	$jQuery14(sel_modal_boton_cancelar).click(modalBotonCancelarOnClick);
	$jQuery14(sel_modal_boton_siguiente).click(modalBotonAceptarOnClick);
}

/*
 * Evento click del botón aceptar del modal 
 * */
function botonAceptarMinisitiosOnClick() {
	Richfaces.hideModalPanel('modalMiniSitios');
	return false;
}

function iniciarEventoLinkModalMinisitios() {
	$jQuery14("a.linkInformacionMinisitios").click(
			function() {
				Richfaces.showModalPanel("modalMiniSitios");
				return false;
			}
	);	
}

function iniciarEventosModalMinisitios() {
	var sel_botones_cerrar = "[id=formModalMiniSitios:botonAceptar], #modalMiniSitios .icono_cerrar";
	$jQuery14(sel_botones_cerrar).click(botonAceptarMinisitiosOnClick);
}

function iniciarEventosLinksModalInformacionPospago() {

	var sel_link_info_modal_1 = "#linkflotante1";
	var sel_link_info_modal_2 = "#linkflotante2";
	var sel_link_info_modal_3 = "#linkflotante3";
	
	$jQuery14(sel_link_info_modal_1).click(getLinkModalPagosOnClick(1));
	$jQuery14(sel_link_info_modal_2).click(getLinkModalPagosOnClick(2));
	$jQuery14(sel_link_info_modal_3).click(getLinkModalPagosOnClick(3));
	
}

function getLinkModalPagosOnClick(idFlotante) {
	
	var florecita = 
		function () {
			var flotante = $jQuery14("#flotante" + idFlotante);
			flotante.toggle('slow');
		};
	
	return florecita;
}


/*
 *  Inicialización de todos los eventos de la vista
 *  
 *  Este callback es invocado cuando se completa 
 *  todo el proceso de carga de la página
 * */
$jQuery14('document').ready(
	function() {
		iniciarEventosBotonesLineaNegocio();
		iniciarEventosFormulariosTelefono();
		iniciarEventosBotonesAnteriorSiguiente();
		iniciarEventoLinkModalMinisitios();
		iniciarEventosModalMinisitios();
		iniciarEventosBotonesModal();
		iniciarEventosLinksModalInformacionPospago();
		return true;
	}
);