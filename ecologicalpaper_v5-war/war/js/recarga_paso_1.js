/*
 *  Objeto principal que mantiene 
 *  el estatus de la aplicación
 *  del lado del cliente
 *  
 *  Parte de los valores de este objeto
 *  se arman dinámicamente desde la vista generada
 * */
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

		estatus_botones_negocio: $estatusBotonesNegocio, 
		
		
		
		/*
		 *  Objeto donde se va a cargar la lista de tarjetas
		 *  Este objeto es generado dinámicamente desde la vista
		 *  
		 *  EJEMPLO DE LA ESTRUCTURA DEL OBJETO
		 *  
		 *  
		 *  {
		 *	movil: 	[
		 *	       	  {monto: 15, descripcion:"Tarjeta Telpago Plus Bs. 15,00", img_mini: "images/recarga/mini_tarjeta_15.jpg"},
		 *	       	  {monto: 20, descripcion:"Tarjeta Telpago Plus Bs. 20,00", img_mini: "images/recarga/mini_tarjeta_20.jpg"},
		 *	       	  {monto: 30, descripcion:"Tarjeta Telpago Plus Bs. 30,00", img_mini: "images/recarga/mini_tarjeta_30.jpg"},
		 *	       	  {monto: 40, descripcion:"Tarjeta Telpago Plus Bs. 40,00", img_mini: "images/recarga/mini_tarjeta_40.jpg"},
		 *	       	  {monto: 50, descripcion:"Tarjeta Telpago Plus Bs. 50,00", img_mini: "images/recarga/mini_tarjeta_50.jpg"},
		 *	       	  {monto: 60, descripcion:"Tarjeta Telpago Plus Bs. 60,00", img_mini: "images/recarga/mini_tarjeta_60.jpg"},
		 *	       	  {monto: 100, descripcion:"Tarjeta Telpago Plus Bs. 100,00", img_mini: "images/recarga/mini_tarjeta_100.jpg"}
		 *	       	 ],
		 *	fijo: 	[ ...  ARRAY DE TARJETAS NEGOCIO FIJO ... ],
		 *	tv: 	[ ...  ARRAY DE TARJETAS NEGOCIO TV ... ],
		 *	im: 	[ ...  ARRAY DE TARJETAS NEGOCIO INTERNET MOVIL ...  ]
		 *	
		 *	}
		 *
		 *   EJEMPLO DE LA ESTRUCTURA DEL OBJETO TARJETA PREPAGO
		 *  
		 * */
		tarjetas: $arrayTarjetasPrepago,
		
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
		negocio_seleccionado: $negocioSeleccionado,
		
		/*
		 *  flag para indicar que se inició el proceso de selección de tarjetas
		 *  se genera dinámicamente en la vista
		 * */
		sel_tarjetas_iniciado: $selTarjetasIniciado, 
		
		/*
		 *  Objeto con la información de la compra
		 *  se genera dinámicamente en la vista
		 *  
		 *  info_compra: {
		 *	  tarjetas_seleccionadas: [], //arreglo de índices de tarjetas seleccionadas
		 *								  //los índices se muecen sobre la tarjeta del negocio respectivo
		 *								  //dado por el atributo negocio_seleccionado
		 *	  total_compra: 0			  //monto total de la compra en Bs.
		 *	}
		 * */
		info_compra: $infoCompra, 
		
		/*
		 *  tipo de recarga. Posibles valores:
		 *  - "texto" 
		 *	- "saldo"
		 *
		 * se genera dinámicamente en la vista
		 * */
		tipo_recarga: $tipoRecarga,
		
		/*
		 *  Monto máximo recarga
		 *  se genera dinámicamente en la vista
		 * */
		monto_maximo_recarga: $montoMaximoRecarga,
		
		/*
		 *  Monto máximo mensual recarga
		 *  se genera dinámicamente en la vista
		 * */
		monto_maximo_mes_recarga: $montoMaximoMesRecarga,
				
		/*
		 *  Monto recargado diario
		 *  se genera dinámicamente en la vista
		 * */
		monto_recarga_dia: $montoRecargaDia,
		
		/*
		 *  Monto recargado mensual
		 *  se genera dinámicamente en la vista
		 * */
		monto_recarga_mes: $montoRecargaMes,
		
		/*
		 *  Tipo de pago "Prepago" o "Pospago"
		 * */
		tipo_pago: $tipoPago
}	;


/*
 *  mensajes de error para la validacion del numero en el formulario de ingreso de numero a recargar
 * */
var $mensaje_error_codigo_numero = 'Debes seleccionar el código de área e ingresar un número de 7 dígitos.';
var $mensaje_error_codigo = 'Debes seleccionar el código de área.';
var $mensaje_error_numero = 'Debes ingresar un número de 7 dígitos.';
var $mensaje_error_tv_digital  = 'Debes ingresar una cuenta de TV Digital válida.';

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

 	if (keynum == 0 || keynum==8 || keynum == 7)
 		return true;
 	
 	keychar = String.fromCharCode(keynum);

 	return filter.test(val+keychar);
 }

/*
 * 
 *  Crea el codigo html para una fila de la lista de la tabla
 *  del detalle de compra
 *  
 *  Parámetros
 *  
 *  tarjeta: Objeto tarjeta con la información de la tarjeta para generar la fila
 *  indice: entero mayor o igual a 0 para indexar la fila
 *  id_icono_restar: valor del id para el icono de restar items
 * 
 * */
function crearHtmlFilaTablaListaTarjeta(tarjeta, indice, id_icono_restar) {
	var codigo_fila;
	
	//apertura fila
	codigo_fila = "<tr class=\"fila_tarjeta item_tarjeta" + indice + "\"  style=\"display: none;\">";
	
	//columna miniatura
	codigo_fila = codigo_fila + "<td>";
	codigo_fila = codigo_fila + "<img src=\"" + tarjeta.img_mini + "\"/>" ;
	codigo_fila = codigo_fila + "</td>";
	//columna descripción
	codigo_fila = codigo_fila + "<td>";
	codigo_fila = codigo_fila +  tarjeta.descripcion;
	codigo_fila = codigo_fila + "</td>";
	//columna cantidad
	codigo_fila = codigo_fila + "<td class=\"columna_cantidad\">";
	codigo_fila = codigo_fila + "0";
	codigo_fila = codigo_fila + "</td>";
	//columna monto
	codigo_fila = codigo_fila + "<td class=\"columna_total\">";
	codigo_fila = codigo_fila + "0";
	codigo_fila = codigo_fila + "</td>";
	//columna boton quitar
	codigo_fila = codigo_fila + "<td>";
	codigo_fila = codigo_fila + "<a href=\"#\" class=\"boton_restar\" id=\"borrar" +  id_icono_restar + "\"><img style=\"border: none;\" src=\"images/delete.gif\" /></a> <span class=\"indice_tarjeta\" style=\"display: none;\">" + indice + "</span>";
	codigo_fila = codigo_fila + "</td>";
	
	//cierre fila
	codigo_fila = codigo_fila + "</tr>";
	
	return codigo_fila;
}


/*
 *  Crea el código HTML e inicia el tooltip 
 *  para el elemento tooltip del botón restar
 *  para una fila de la tabla
 * */
function iniciarTooltipBotonRestarTarjeta(id_icono_restar) {
	var span_tooltip = "<span class=\"rich-tool-tip tooltip\" id=\"tooltip" + id_icono_restar + "\" style=\"z-index: 999; width: 100px;\" >Eliminar 1 unidad</span>";
	//agregar el elemento al final del body
	$jQuery14('body').append(span_tooltip);
	//invocar el tooltip de RichFaces
	new ToolTip('tooltip' + id_icono_restar, 'borrar' + id_icono_restar, {showEvent: 'mouseover', delay: 100, followMouse: true, width: 25});
	
}



/*
 *  Inicializa el estatus de la aplicación
 * */
function inicializarEstatusAplicacion() {
	
	$estatus_aplicacion.info_compra =  {
		tarjetas_seleccionadas: [], 
		total_compra: 0
	};
	
	$estatus_aplicacion.sel_tarjetas_iniciado = false;
	$estatus_aplicacion.negocio_seleccionado = '';
	
}


/*
 *  Inicia la tabla de items, eliminando
 *  todas las filas
 *  y dejando el total en 0
 * */

function iniciarTablaItemsSeleccionados() {
	
	var selector_fila_tarjeta = "tr.item_tarjeta";
	var selector_cell_total = "#cellTotal";
	var selector_container_tabla_tarjeta = "#containerListaTarjeta";
	
	/*
	 *  Remover todos los items de tarjetas seleccionadas
	 * */
	$jQuery14(selector_fila_tarjeta).remove();
	
	/*
	 *  Fijar el monto total en 0
	 * */
	$jQuery14(selector_cell_total).text("0");
	
	
	/*
	 *  Ocultar la tabla de items
	 * */
	$jQuery14(selector_container_tabla_tarjeta).hide();
	
}

/*
 *  Aparece el texto de bonos
 * */
function mostrarDivTextoBonosrecarga() {
	/*
	 *  Si el negocio seleccionado es fijo o móvil se muestra el mensaje informativo
	 *  */
	var negocioSeleccionado = $estatus_aplicacion.negocio_seleccionado;
	
	if (negocioSeleccionado == 'movil') {
		$jQuery14("#textoBonosRecargaMovil").show();
	} else {
		$jQuery14("#textoBonosRecargaMovil").hide();
	}
	
	if (negocioSeleccionado == 'fijo') {
		$jQuery14("#textoBonosRecargaFijo").show();
	} else {
		$jQuery14("#textoBonosRecargaFijo").hide();
	}
	
	if (negocioSeleccionado == 'im') {
		$jQuery14("#textoBonosRecargaIM").show();
	} else {
		$jQuery14("#textoBonosRecargaIM").hide();
	}
	
	if (negocioSeleccionado == 'tv') {
		$jQuery14("#textoBonosRecargaTV").show();
	} else {
		$jQuery14("#textoBonosRecargaTV").hide();
	}	
}


/*
 *  Aparece el texto debajo del detalle compra
 * */
function mostrarDivTextoDetalleCompra(tipo_pago) {
	
	if (tipo_pago.toLowerCase() == 'pospago') {
		$jQuery14("#textoDetalleRecarga").show();
	} else {
		$jQuery14("#textoDetalleRecarga").hide();
	}	
}


/*
 *  Aparece el contenedor de la lista de tarjetas
 * */
function mostrarContainerListaTarjetas() {
	$jQuery14("#containerListaTarjeta").fadeIn('slow');
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
	
	//colocar los numeros del formulario
	
	/*
	var elementos = getElementosFormularioNegocio(negocio_seleccionado);
	elementos.input_codigo.siblings('span.valor').text($codigoAreaPaso1).show();
	elementos.input_numero.siblings('span.valor').text($numeroPaso1).show();
	*/
	
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
 * 
 *  Actualiza las imágenes de los íconos del carrusel según 
 *  el tipo de recarga seleccionado
 *  
 *  - parámetro habilitar_todas booleano que indica si se deben habilitar todas
 *  o se deben deshabilitar las que no sean del negocio
 * */
function actualizarIconosTarjetasPorTipoRecarga(habilitar_todos) {
	
	$jQuery14('img.icono_tarjeta').each(
			function() {
				//ver el icono que no sea del tipo de recarga
				var tipo = $jQuery14.trim($jQuery14(this).siblings('span.tipo_recarga').text());
				var indice = parseInt(($jQuery14.trim($jQuery14(this).siblings('span.indice_tarjeta').text())));
				var negocio = $estatus_aplicacion.negocio_seleccionado;
				var tarjeta = $estatus_aplicacion.tarjetas[negocio][indice];
				
				if (habilitar_todos) {
					$jQuery14(this).attr('src',tarjeta.img_icono);
					$jQuery14('#mensajeErrorTipoRecarga').fadeOut('fast');
				} else {
					if (tipo != $estatus_aplicacion.tipo_recarga) {
						$jQuery14(this).attr('src',tarjeta.img_out);
					}
				}
			}
	);
	
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


function iniciarEstadoControlesLineaNegocio(elemento) {
	
	var i ;
	var n;
	var estatus_botones  = $estatus_aplicacion.estatus_botones_negocio;
	var elementos = $lineas_negocios;
	var selector  = '#img_selec_' +  elemento + ' img';
	
	/*
	 *  Se recorren todos los estatus de los botones
	 *  para ocultar el formulario en todas las líneas de negocios
	 *  distintas a la seleccionada
	 * */
	for ( i in  $estatus_aplicacion.estatus_botones_negocio) {
		var ocultar = (i != elemento);
		
		iniciarFormularioNegocio(i, ocultar);
	}
	
	iniciarTablaItemsSeleccionados();
	ocultarCarruseles();
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
	
	elementos.container_carrusel.fadeIn('slow',carruselAfterFadeIn);
	$estatus_aplicacion.sel_tarjetas_iniciado = true;
	iniciarTablaDetalleCompra(negocio, true);
	
	
	//mostra el campo número a recargar
	//mostrarDivNumeroARecargar(negocio, codigo, numero);
	
	mostrarDivTextoDetalleCompra(tipo_pago);
	
	actualizarBotonAnterior();
	
	
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
		elementos_formulario.div_container_titulo_form.text("La cuenta a recargar es");
	} else {
		elementos_formulario.div_container_titulo_form.text("El número a recargar es");
	}
}



/*
 *  Recarga los datos en el formulario de seleccionar
 *  teléfono o cuenta según el negocio seleccionado si
 *  $estatus_aplicacion.negocio_seleccionado tiene un valor no vacío
 * */

function recargarDatosFormularioTelefono() {
	
	var negocio_seleccionado = false;
	var n = $estatus_aplicacion.negocio_seleccionado;
	
	
	//flag que indica si se validó un negocio
	var negocio_seleccionado = (n == 'movil') || (n == 'fijo') || (n == 'im') || (n == 'tv');
	var elementos_formulario = getElementosFormularioNegocio(n);
	
	/*
	 *  Si hubo algún negocio seleccionado se actualizan los formularios
	 * */
	if (negocio_seleccionado) {
		/*
		 *  Cargar en el formulario los valores que vienen de sesión
		 * */
		if(n != 'tv') elementos_formulario.input_codigo.val($codigoAreaPaso1);
		elementos_formulario.input_numero.val($numeroPaso1);
		
		
		/*
		 * Mostrar el carrusel
		 * */
		elementos_formulario.container_carrusel.fadeIn('slow');
		
		ocultarCamposFormularioIntroducirNumero(n, $codigoAreaPaso1, $numeroPaso1);
		mostrarNumeroTransaccionFormulario(n, $codigoAreaPaso1, $numeroPaso1);
	}
}


function recargarTablaListaTarjetas(elemento) {
	
	
	var i;
	var tarjetas = $estatus_aplicacion.tarjetas[elemento];
	var n = tarjetas.length;
	
	/*
	 *  Iterar sobre todas las tarjetas 
	 *  y actualizar la cantidad y monto para cada item
	 * */
	for (i = 0; i < n; ++i) {
		var fila = $jQuery14('#tablaListaTarjeta tr.item_tarjeta' + i);
		var columna_cantidad = fila.children('td.columna_cantidad');
		var columna_total = fila.children('td.columna_total');
		
		//monto de la línea
		var monto_linea =  'Bs. '+ $estatus_aplicacion.info_compra.tarjetas_seleccionadas[i] * tarjetas[i].monto;
		var cantidad = $estatus_aplicacion.info_compra.tarjetas_seleccionadas[i];
		
		/*
		 *  Actualizar el texto de cada una de las columnas
		 * */
		columna_cantidad.text(cantidad);
		columna_total.text(monto_linea);
		
		/*
		 *  Si hay más de una tarjeta para la línea actual se muestra
		 * */
		if ($estatus_aplicacion.info_compra.tarjetas_seleccionadas[i] > 0) {
			fila.show();
		}
	}
	
	//actualizar el total de la compra
	$jQuery14("#cellTotal").text('Bs. '+ $estatus_aplicacion.info_compra.total_compra);
	
	//aparecer la lista de tarjetas
	carruselAfterFadeIn();
	//mostrarContainerListaTarjetas();
	
	mostrarDivTextoDetalleCompra($estatus_aplicacion.tipo_pago);
	
	
	
	
}


/*
 *  Actualizar el estado de los botones siguiente y cancelar
 * */
function actualizarBotonesSiguienteCancelar() {
	
	var sel_boton_cancelar = '[id=formSiguiente:botonCancelar]';
	var sel_boton_siguiente = '[id=formSiguiente:botonSiguiente]';
	var sel_boton_anterior='[id=formSiguiente:botonAnterior]';
	
	
	
	$jQuery14(sel_boton_cancelar).css('visibility','visible');
	$jQuery14(sel_boton_siguiente).click(botonSiguienteOnClick).css('visibility','visible').attr('disabled',false);
	$jQuery14(sel_boton_anterior).css('visibility','visible').attr('disabled',false);
	
}

/*
 *  Actualiza el estado del botón anterior
 * */
function actualizarBotonAnterior() {
	var sel_boton_anterior='[id=formSiguiente:botonAnterior]';
	$jQuery14(sel_boton_anterior).css('visibility','visible').attr('disabled',false);
	
}


/*
 *  Actualiza el estado de deshabilitado del botón siguiente
 * */
function actualizarDisabledBotonSiguiente() {
	//si el total de la compra es mayor a cero
	var sel_boton_siguiente = '[id=formSiguiente:botonSiguiente]';
	var deshabilitar= ($estatus_aplicacion.info_compra.total_compra <= 0);
	$jQuery14(sel_boton_siguiente).attr('disabled', deshabilitar);
	
}
 

/*
 *  Oculta el mensaje de error de tipo de recarga
 * */
function ocultarErrorTipoRecarga() {
	
	$jQuery14('#mensajeErrorTipoRecarga').fadeOut('fast');
}

/*
 *  Oculta el mensaje de error con el monto máximo
 * */
function ocultarErrorMontoMaximo() {
	$jQuery14('#mensajeErrorMontoMaximo').fadeOut('fast');
}

/*
 *  Oculta todos los carruseles
 * */
function ocultarCarruseles() {
	var sel_wrapper_carruseles = "#wrapper_movil, #wrapper_fijo, #wrapper_im, #wrapper_tv";
	var selector_carruseles = "#carruselMovil, #carruselFijo, #carruselTVDigital, #carruselInternetMovil"; 
	$jQuery14(sel_wrapper_carruseles).hide();
}

/*
 *  Muestra el mensaje de error del monto máximo
 * */
function mostrarErrorMontoMaximo(tarjeta) {

	/*
	$jQuery14("#modalMontoMaximo div.contenido").text("Has recargado el monto máximo permitidio de Bs. " + monto_maximo + " no puedes agregar otra tarjeta de Bs. " + tarjeta.monto)+ ". <br/>  Prueba con una tarjeta por un monto menor.";
	Richfaces.showModalPanel('modalMontoMaximo');
	*/
	
	//mostrar el mensaje de errror de monto máximo
	var error_monto_maximo = "El monto máximo de la recarga es de Bs. " + $estatus_aplicacion.monto_maximo_recarga + ". No puedes agregar otra tarjeta de Bs. " + tarjeta.monto + ",&#160; prueba con una tarjeta por un monto menor.";
	$jQuery14('#mensajeErrorMontoMaximo').html(error_monto_maximo).fadeOut('slow', function() { $jQuery14('#mensajeErrorMontoMaximo').html(error_monto_maximo).fadeIn('slow');});
	
}

/*
 *  Muestra el mensaje de error del monto diario máximo
 * */
function mostrarErrorMontoMaximoDiario(tarjeta) {
	
	//mostrar el mensaje de errror de monto diario máximo
	var error_monto_maximo = "El monto diario máximo de recarga es de Bs. " + $estatus_aplicacion.monto_maximo_recarga + ". No puedes agregar otra tarjeta de Bs. " + tarjeta.monto + ",&#160; prueba con una tarjeta por un monto menor.";
	$jQuery14('#mensajeErrorMontoMaximo').html(error_monto_maximo).fadeOut('slow', function() { $jQuery14('#mensajeErrorMontoMaximo').html(error_monto_maximo).fadeIn('slow');});
	
}


/*
 *  Muestra el mensaje de error del monto mensual máximo
 * */
function mostrarErrorMontoMaximoMensual(tarjeta) {
	
	//mostrar el mensaje de errror de monto mensual máximo
	var error_monto_maximo = "El monto mensual máximo de recarga es de Bs. " + $estatus_aplicacion.monto_maximo_mes_recarga + ". No puedes agregar otra tarjeta de Bs. " + tarjeta.monto + ",&#160; prueba con una tarjeta por un monto menor.";
	$jQuery14('#mensajeErrorMontoMaximo').html(error_monto_maximo).fadeOut('slow', function() { $jQuery14('#mensajeErrorMontoMaximo').html(error_monto_maximo).fadeIn('slow');});
	
}

/*
 *  Muestra el mensaje de werropr del tipo de recarga
 * */
function mostrarErrorTipoRecarga(icono) {
	
	/*
	 * sacar el tipo de recarga al que pertenece el icono
	 * */
	var tipo_recarga_icono = icono.siblings('span.tipo_recarga').text();
	
	var tipo_recarga = $estatus_aplicacion.tipo_recarga;
	var error_tipo_recarga = "Has seleccionado una tarjeta de recarga de " + tipo_recarga_icono + ", pero habías iniciado una recarga de " + $estatus_aplicacion.tipo_recarga + " continua con la "+ tipo_recarga_icono +" y luego realiza la de "+$estatus_aplicacion.tipo_recarga+"."; 
	$jQuery14('#mensajeErrorTipoRecarga').fadeOut('slow', function() { $jQuery14('#mensajeErrorTipoRecarga').html(error_tipo_recarga).fadeIn('slow');});
}

/*
 *  Muestra el div que tiene la información del número a recargar
 * */
function mostrarDivNumeroARecargar(negocio, codigo, numero) {
	
	var selector_formulario = '#form_selec_' + negocio;
	var selector_div_numero_a_recargar = "#infoNumeroARecargar";
	var selector_span = selector_div_numero_a_recargar + " span.numeroRecargar";
	
	if (codigo == '') {
		$jQuery14(selector_span).text(numero);
	}
	else {
		$jQuery14(selector_span).text(codigo +  " " + numero);
	}
	
	$jQuery14(selector_formulario).hide();
	$jQuery14(selector_div_numero_a_recargar).fadeIn('slow');
	
}

/*
 *  Callbak para aparecer la tabla de detalle compra después 
 *  de la aparición del carrusel
 * */
function carruselAfterFadeIn() {
	
	//aparecer la tabla con el detalle de compra
	mostrarContainerListaTarjetas();
	//aparecer el texto informativo con los bonos
	mostrarDivTextoBonosrecarga();
	
}

/*
 * Evento click del botón aceptar del modal 
 * */
function botonAceptarMinisitiosOnClick() {
	Richfaces.hideModalPanel('modalMiniSitios');
	return false;
}


/*
 *  Asocia a cada ícono de restar en la tabla de detalle de compra
 *  el evento on click para la acción
 * */
function asociarBotonesRestarEventoOnClick(negocio) {
	//asociar el evento a los botones de restar
	$jQuery14("#tablaListaTarjeta a.boton_restar").click(
			function() {
				var indice = parseInt($jQuery14.trim($jQuery14(this).siblings('span').text()));
				var tarjeta = $estatus_aplicacion.tarjetas[negocio][indice];
				var fila = $jQuery14('#tablaListaTarjeta tr.item_tarjeta' + indice);
				
				var columna_cantidad = fila.children("td.columna_cantidad");
				var columna_total = fila.children("td.columna_total");
				
				//restar en el objeto de compra
				$estatus_aplicacion.info_compra.tarjetas_seleccionadas[indice]--;
				$estatus_aplicacion.info_compra.total_compra -= tarjeta.monto;
				
				columna_cantidad.text($estatus_aplicacion.info_compra.tarjetas_seleccionadas[indice]);
				columna_total.text("Bs. " +  $estatus_aplicacion.info_compra.tarjetas_seleccionadas[indice] * tarjeta.monto);
				
				//actualizar el total de la compra
				$jQuery14("#cellTotal").text("Bs. " +  $estatus_aplicacion.info_compra.total_compra);
				
				//si la fila tiene 0 tarjetas se oculta
				
				if ($estatus_aplicacion.info_compra.tarjetas_seleccionadas[indice] == 0) {
					fila.hide();
				}
				
				actualizarDisabledBotonSiguiente();
				
				ocultarErrorMontoMaximo();
				
				if ($estatus_aplicacion.info_compra.total_compra <= 0) {
					actualizarIconosTarjetasPorTipoRecarga(true);
				}
				
				return false;
			}
		);
	
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
			
			/*
			 *  Verificar si es un caso de confirmación
			 * */
			/*
			var necesita_confirmacion = false;
			necesita_confirmacion = ($estatus_aplicacion.negocio_seleccionado != '') && ($estatus_aplicacion.sel_tarjetas_iniciado);
			
			if (necesita_confirmacion) {
				var c;
				c = confirm("¿Estás seguro de que deseas cancelar la compra actual?");
				if (!c) {
					return;
				}
			}
			*/
			
			inicializarEstatusAplicacion();
			iniciarEstadoControlesLineaNegocio(elemento);
			return false;
		};
	
	return florecita;
}





/*
 * 
 *  retorna el callback que se debe invocar cuando se inicia
 *  el estado de aplicación desde valores existentes en sesión
 * */
function getIniciarEstadoAplicacion(elemento) {
	var florecita = 
		function() {
			
			//iniciar el estado de los controles de línea de negocio
			iniciarEstadoControlesLineaNegocio(elemento);
			//recargar el formulario con los datos que vienen de sesión
			recargarDatosFormularioTelefono();
			//actualizar los íconos de tarjetas por tipo recarga
			//los que no sean del tipo de recarga se deshabilitan
			actualizarIconosTarjetasPorTipoRecarga(false);
			//armar la lista de tarjetas
			iniciarTablaDetalleCompra(elemento, false);
			//actualizar los valores de la lista de tarjetas
			recargarTablaListaTarjetas(elemento);
			//actualizar el estao d elos botones siguiente y cancelar
			actualizarBotonesSiguienteCancelar();
			//desactivar iconos negocio no seleccionado
			desactivarIconosLineasNegociosNoSeleccionadas();
			return false;
		};
	
	
	return florecita;
}


/**
 * Callback de inicialización 
 * del carrusel de imágeness
 */
function getCallbackCarruselOnLoad(footer_carrusel) {

	var florecita = function(carousel) {
		
		
		jQuery('#' + footer_carrusel + ' .jcarousel-control a').bind('click', function() {
	        carousel.scroll(jQuery.jcarousel.intval(jQuery(this).text()));
	        return false;
	    });

	    jQuery('#' + footer_carrusel + ' .jcarousel-scroll select').bind('change', function() {
	        carousel.options.scroll = jQuery.jcarousel.intval(this.options[this.selectedIndex].value);
	        return false;
	    });
	    
		
	    jQuery('#' + footer_carrusel + ' img.mycarousel-next').bind('click', function() {
	        carousel.next();
	        return false;
	    });

	    jQuery('#' + footer_carrusel + ' img.mycarousel-prev').bind('click', function() {
	        carousel.prev();
	        return false;
	    });
	};
    
	
	return florecita;
};


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
 *  inicia las filas de la tabla de detalle de compra
 * */
function iniciarTablaDetalleCompra (negocio, iniciar) {
	/*iniciar la tabla de detalle de compra*/
	var codigo_filas_tabla = "";
	
	var lista_tarjetas = $estatus_aplicacion.tarjetas[negocio];
	
	var i;
	
	//iniciar el array de tarjetas seleccionadas
	if (iniciar) {
		$estatus_aplicacion.info_compra.tarjetas_seleccionadas = [];
	}
	
	for (i = 0 ; i < lista_tarjetas.length; i++) {
		
		var codigo_fila;
		var t = lista_tarjetas[i];
		
		//se le agrega un contador de tarjetas seleccionadas a la posición
		//para tener el número de tarjetas seleccionadas del tipo i
		if (iniciar) {
			$estatus_aplicacion.info_compra.tarjetas_seleccionadas.push(0);
		}
		
		//id random para los span de los tooltips de las filas
		var random = Math.floor(Math.random() * 100000000);
		
		//armar la fila de la tabla para la tarjeta actual
		codigo_fila = crearHtmlFilaTablaListaTarjeta(t, i, random);
		codigo_filas_tabla = codigo_filas_tabla + codigo_fila ;
		
		//iniciar el tooltip de eliminar para la fila de la tabla
		iniciarTooltipBotonRestarTarjeta(random);
	}
	
	//agregar las filas a la tabla
	$jQuery14("#headerTablaListaTarjeta").after(codigo_filas_tabla);
	
	//asociar el evento a los botones de restar
	asociarBotonesRestarEventoOnClick(negocio);
	
	$estatus_aplicacion.sel_tarjetas_iniciado = true;
	
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
		else div_error.text("El número ingresado no es una cuenta internet válido").hide().fadeIn('slow');
		
		$jQuery14('[id=formSeleccionarCuentaIM:codigo_area_im]').attr('disabled',false);
		$jQuery14('[id=formSeleccionarCuentaIM:numero_im]').attr('disabled',false);
		$jQuery14('[id=formSeleccionarCuentaIM:boton_validar_internet_movil]').attr('disabled',false);
				iniciarEventosBotonesLineaNegocio();
	}
	
	return false;
}


/*
 *  Actualiza la cantidad y monto de una fila de la lista del detalle de compra de
 *  tarjetas
 *  
 *  Parámetros:
 *  
 *  -tipo_recarga: String con el tipo de recarga actual
 *  -indice_tarjeta: índice en el array de tarjetas de la tarjeta seleccionada
 * */
function actualizarFilaListaTarjetas(tipo_recarga, indice_tarjeta) {
	
	var tarjeta = $estatus_aplicacion.tarjetas[$estatus_aplicacion.negocio_seleccionado][indice_tarjeta];
	
	//actualizar el tipo de recarga
	$estatus_aplicacion.tipo_recarga = tipo_recarga;
	
	//deshabilitar los iconos que no sean del tipo de recarga
	//sobre todos los carruseles
	actualizarIconosTarjetasPorTipoRecarga(false);
	
	//actualizar el total de tarjetas dentro de la info de compra
	$estatus_aplicacion.info_compra.tarjetas_seleccionadas[indice_tarjeta]++;
	$estatus_aplicacion.info_compra.total_compra += tarjeta.monto;
	
	//buscar la fila de la lista de tarjetas 
	var clase_fila = "item_tarjeta" + indice_tarjeta;
	var selector_fila = "#tablaListaTarjeta tr." + clase_fila;
	
	//mostrar la fila
	var fila = $jQuery14(selector_fila).show();
	
	//actualizar el numero total en la columna
	var columna_cantidad = fila.children("td.columna_cantidad");
	var columna_total = fila.children("td.columna_total");
	
	columna_cantidad.text($estatus_aplicacion.info_compra.tarjetas_seleccionadas[indice_tarjeta]);
	columna_total.text("Bs. " +  $estatus_aplicacion.info_compra.tarjetas_seleccionadas[indice_tarjeta] * tarjeta.monto);
	
	//actualizar el total de la compra
	$jQuery14("#cellTotal").text("Bs. " + $estatus_aplicacion.info_compra.total_compra);
	actualizarDisabledBotonSiguiente();
	
}




/*
 *  Manejador de evento del icono de tarjeta
 * */
function iconoTarjetaOnClick() {
	/*
	 *  sacar los valores 
	 * */
	
	//índice de la tarjeta dentro del array de tarjetas para el negocio seleccionado
	var indice_tarjeta = parseInt(($jQuery14.trim($jQuery14(this).siblings('span.indice_tarjeta').text())));
	//tipo de recarga de la tarjeta
	var tipo_recarga = $jQuery14.trim($jQuery14(this).siblings('span.tipo_recarga').text());
	
	
	//sacar el objeto tarjeta
	var tarjeta = $estatus_aplicacion.tarjetas[$estatus_aplicacion.negocio_seleccionado][indice_tarjeta];
	var imagen = $jQuery14(this).attr('src');
	
	
	
	/*
	 *  Si la imagen del carrusel no es deshabilidata
	 *  significa que es una tarjeta del tipo recarga correcto
	 *  en caso contrario se muestra el mensaje de error
	 * */
	if (imagen != tarjeta.img_out) {
		
		ocultarErrorTipoRecarga();
		
		//validar que no se sobrepase el monto máximo de la recarga
		var monto_maximo = $estatus_aplicacion.monto_maximo_recarga;
		var monto_maximo_mes = $estatus_aplicacion.monto_maximo_mes_recarga;
		var monto_nuevo = $estatus_aplicacion.info_compra.total_compra + tarjeta.monto;
		var monto_dia = parseInt($estatus_aplicacion.monto_recarga_dia);
		var monto_mes = parseInt($estatus_aplicacion.monto_recarga_mes);
		
		if (monto_nuevo > monto_maximo) {
			mostrarErrorMontoMaximo(tarjeta);
		}
		else if((monto_dia + monto_nuevo) > monto_maximo){
			mostrarErrorMontoMaximoDiario(tarjeta);
		}
		else if((monto_mes + monto_nuevo) > monto_maximo_mes){
			mostrarErrorMontoMaximoMensual(tarjeta);
		}
		else {
			ocultarErrorMontoMaximo();
			actualizarFilaListaTarjetas(tipo_recarga, indice_tarjeta);			
		}
	} else {
		/*
		 *  No es un tipo de recarga válido
		 *  se despliega el mensaje de error
		 * */
		mostrarErrorTipoRecarga($jQuery14(this));
	}
	return false;
}




/*
 *  Manejador del evento del botón siguiente
 * */
function botonSiguienteOnClick () {
	
	var iniciado = $estatus_aplicacion.sel_tarjetas_iniciado;
	var monto_total_compra = $estatus_aplicacion.info_compra.total_compra;
	var result = true;
	
	/*
	 *  Debe validarse que se haya iniciado la compra
	 *  y que tenga al menos una tarjeta
	 * */
	if (!iniciado) {
		alert("Debes iniciar una compra para poder continuar.");
		result = false;
	} else if (monto_total_compra == 0) {
		alert("Debes seleccionar al menos una tarjeta.");
		result = false;
	}
	
	/*
	 * 
	 *  Validadas las condiciones se procede a rellenar el formulario
	 *  con los datos necesarios para todo
	 * */
	
	//cargar lista de indices de tarjetas
	$jQuery14("[id=formSiguiente:indicesTarjetas]").val($estatus_aplicacion.info_compra.tarjetas_seleccionadas.join(','));
	//cargar negocio
	$jQuery14("[id=formSiguiente:negocio]").val($estatus_aplicacion.negocio_seleccionado);
	
	
	if (result) {
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
		
		$jQuery14("[id=formSiguiente:tipoRecarga]").val($estatus_aplicacion.tipo_recarga);
		$jQuery14("[id=formSiguiente:tipoPago]").val($estatus_aplicacion.tipo_pago);
	}
	
	return result;
}



function inicializarCarruseles() {
	
	var sel_wrapper_carruseles = "#wrapper_movil, #wrapper_fijo, #wrapper_im, #wrapper_tv"; 
	var sel_carruseles = " #carruselMovil , #carruselFijo , #carruselTVDigital , #carruselInternetMovil ";

	var errorCarruseles = false;
	
	/*
	 *  Iniciar los carruseles para todas las líneas de negocios
	 * */
	
	try {
		$jQuery14("#carruselFijo").jcarousel({
			scroll: 2,
			animation: "medium",
			wrap: "circular",
		    initCallback: getCallbackCarruselOnLoad('footer_carrusel_fijo'),
	        buttonNextHTML: null,
	        buttonPrevHTML: null,
	        itemFallbackDimension: 135
		});	
	} catch (e) {
		errorCarruseles = true;
	}
	
	
	try {
		$jQuery14("#carruselMovil").jcarousel({
			scroll: 2,
			animation: "medium",
			wrap: "circular",
		    initCallback: getCallbackCarruselOnLoad('footer_carrusel_movil'),
	        buttonNextHTML: null,
	        buttonPrevHTML: null,
	        itemFallbackDimension: 135
				
		});	
	} catch (e) {
		errorCarruseles = true;
	}
	
	try {
		$jQuery14("#carruselTVDigital").jcarousel({
			scroll: 2,
			animation: "medium",
			wrap: "circular",
		    initCallback: getCallbackCarruselOnLoad('footer_carrusel_tv'),
	        buttonNextHTML: null,
	        buttonPrevHTML: null,
	        itemFallbackDimension: 135
				
		});	
	} catch (e) {
		errorCarruseles = true;
	}
	
	try {
		$jQuery14("#carruselInternetMovil").jcarousel({
			scroll: 2,
			animation: "medium",
			wrap: "circular",
		    initCallback: getCallbackCarruselOnLoad('footer_carrusel_im'),
	        buttonNextHTML: null,
	        buttonPrevHTML: null,
	        itemFallbackDimension: 135
				
		});	
	} catch (e) {
		errorCarruseles = true;
	}
	
	
	if (errorCarruseles) {
		alert("Error cargando data de tarjetas prepago. Consulte con soporte técnico.");
	} else {
		/*
		 *  Ocultar los carruseles
		 * */
		$jQuery14(sel_wrapper_carruseles).css("visibility","visible").hide();
			
	}

	return errorCarruseles;
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

function iniciarEventosIconosTarjetas() {
	var sel_icono_tarjeta = "img.icono_tarjeta";
	$jQuery14(sel_icono_tarjeta).click(iconoTarjetaOnClick);
	$jQuery14().delegate(sel_icono_tarjeta,'click',iconoTarjetaOnClick);
}


function iniciarEventosBotonesAnteriorSiguiente() {
	var sel_boton_cancelar = '[id=formSiguiente:botonCancelar]';
	var sel_boton_siguiente = '[id=formSiguiente:botonSiguiente]';
	var sel_boton_anterior = '[id=formSiguiente:botonAnterior]';
	$jQuery14(sel_boton_cancelar).css('visibility','visible');
	$jQuery14(sel_boton_siguiente).click(botonSiguienteOnClick).css('visibility','visible').attr('disabled',true);
	$jQuery14(sel_boton_anterior).css('visibility','visible').attr('disabled',true);
	
}


function iniciarEventosLinksModalInformacionPospago() {

	var sel_link_info_modal_1 = "#linkflotante1";
	var sel_link_info_modal_2 = "#linkflotante2";
	var sel_link_info_modal_3 = "#linkflotante3";
	
	$jQuery14(sel_link_info_modal_1).click(getLinkModalPagosOnClick(1));
	$jQuery14(sel_link_info_modal_2).click(getLinkModalPagosOnClick(2));
	$jQuery14(sel_link_info_modal_3).click(getLinkModalPagosOnClick(3));
	
}


/*
 *  Valida si se está cargando la pantalla desde  el paso anterior
 *  y si viene del paso anterior, inicia el estado de la aplicación
 * 
 * */
function iniciarEstadoAplicacionDesdePasoAnterior() {
	/*
	 *  Validar si se viene del paso anterior
	 * */
	if ($estatus_aplicacion.sel_tarjetas_iniciado) {
		
		//desplegar el campo adecuado
		var negocio = $estatus_aplicacion.negocio_seleccionado;
		
		//iniciar toda la pantalla con los datos que vienen de sesión
		var florecita = getIniciarEstadoAplicacion(negocio);
		florecita();
	}
	
}


/*
 *  Flag para validar si se ejecuta el evento de carga la primera vez.
 *  
 *  Este flag se agrega debido a un comportamiento extraño en Firefox 11
 *  en el cual el evento ready se registra dos veces.
 *  
 * */
var flag_evento_inicio = false;

/*
 *  Inicialización de todos los eventos de la vista
 *  
 *  Este callback es invocado cuando se completa 
 *  todo el proceso de carga de la página
 * */

$jQuery14('document').ready(
	function() {
		
		
		if (!flag_evento_inicio) {
			flag_evento_inicio = true;
		

			var errorCarruseles;
			
			errorCarruseles = inicializarCarruseles();
			
			if (!errorCarruseles) {
				iniciarEventosBotonesLineaNegocio();
				iniciarEventosFormulariosTelefono();
				iniciarEventosIconosTarjetas();
				iniciarEventosBotonesAnteriorSiguiente();
				iniciarEventoLinkModalMinisitios();
				iniciarEventosModalMinisitios();
				iniciarEstadoAplicacionDesdePasoAnterior();	
				iniciarEventosLinksModalInformacionPospago();
				
			}

		
		}
				
	}
);

 
