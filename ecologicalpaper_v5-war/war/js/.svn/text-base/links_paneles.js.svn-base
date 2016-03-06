/**
 * Variables globales para esta funcion
 * 
 */
var idAgregarServicios = "";
var idActualizarEquipo = "";
var idCanjearPuntosClub = "";
var idInscribirPuntosClub = "";
var idPagarEnLinea = "";
var idRecargar = "";
//variable constante que me sirve como referencia si estoy realizando el link canjear
var canjear='Canjear';

function loadThreeDots(){
	
	var flagRefrescamiento = "";

	var linkAgregarServicios = "...";
	var linkActualizarEquipo = "...";
	var linkPuntosClub = "";
	var linkPagarEnLinea= "...";
	var linkRecargar= "...";

//	alert(  "idAgregarServicios: "+ idAgregarServicios + "\n" + 
//			"idActualizarEquipo: "+ idActualizarEquipo + "\n" + 
//			"idCanjearPuntosClub: "+ idCanjearPuntosClub + "\n" + 
//			"idInscribirPuntosClub: "+ idInscribirPuntosClub + "\n"
//		);
//Mi cuenta
	flagRefrescamiento = $jQuery17('#flagRefrescamientoPanelMiCuenta');	
	if(flagRefrescamiento.length > 0) {		
		if( ($jQuery17('#planActual').length>0 || $jQuery17('#cuenta').length > 0 || $jQuery17('#puntosClub').length > 0 
				|| $jQuery17('#titular').length > 0 || $jQuery17('#equipo').length > 0)  && flagRefrescamiento.val() == '1' ) {
			
			flagRefrescamiento.val("0");

			if(idAgregarServicios != "") {
				linkAgregarServicios = _generarLinkParaCuandrante($jQuery17, idAgregarServicios,'linkAgregarServicios','Servicios');
				_agregarLink($jQuery17,'#planActual',linkAgregarServicios,'+');
			}else{
				$jQuery17('#planActual').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class: 'texto_con_elipses',ellipsis_string:' ...'});
			}

			$jQuery17('#cuenta').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class: 'texto_con_elipses',ellipsis_string:' ...'});

			if( idCanjearPuntosClub != "" || idInscribirPuntosClub != "" ) {
				if( idCanjearPuntosClub == "" ) {
					linkPuntosClub = _generarLinkParaCuandrante($jQuery17, idInscribirPuntosClub,'linkInscribirPuntosClub','Inscribir');
				} else {
					linkPuntosClub = _generarLinkParaCuandrante($jQuery17, idCanjearPuntosClub,'linkCanjearPuntosClub',canjear);
				}
				_agregarLink($jQuery17,'#puntosClub',linkPuntosClub, '|');
			}

			$jQuery17('#titular').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class:'texto_con_elipses',ellipsis_string:' ...'});

			if(idActualizarEquipo != "") {
				linkActualizarEquipo = _generarLinkParaCuandrante($jQuery17, idActualizarEquipo,'linkActualizarEquipo','Actualizar');
				_agregarLink($jQuery17,'#equipo',linkActualizarEquipo, ' |');
			}else{
				$jQuery17('#equipo').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class:'texto_con_elipses',ellipsis_string:' ...'});
			}

		}

	}
	
	//Mis facturas
	
	flagRefrescamiento = $jQuery17('#flagRefrescamientoPanelMisFacturas');
	if(flagRefrescamiento.length > 0) {		
			if( ($jQuery17('#saldo').length>0 ||  $jQuery17('#deuda').length>0 || $jQuery17('#controlGastos').length>0) && flagRefrescamiento.val() == '1' ) {
				flagRefrescamiento.val("0");
				
				if ($jQuery17('#saldo').length>0 && idRecargar != "") {	
					linkRecargar = _generarLinkParaCuandrante($jQuery17, idRecargar, 'linkRecargar', 'Recargar');				 
					_agregarLink($jQuery17, '#saldo', linkRecargar, '|');
				}else{
					$jQuery17('#saldo').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class:'texto_con_elipses',ellipsis_string:' ...'});
				}
				if ($jQuery17('#deuda').length>0 && idPagarEnLinea != "") {	
					linkPagarEnLinea = _generarLinkParaCuandrante($jQuery17, idPagarEnLinea, 'linkPagarEnLinea', 'Pagar');				 
					_agregarLink($jQuery17, '#deuda', linkPagarEnLinea, '|');
				}else{
					$jQuery17('#deuda').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class:'texto_con_elipses',ellipsis_string:' ...'});
				}
				if ($jQuery17('#controlGastos').length>0){
					$jQuery17('#controlGastos').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class: 'texto_con_elipses',ellipsis_string:' ...'});	
				}
			}
	}
	
//Mis solicitudes


	flagRefrescamiento = $jQuery17('#flagRefrescamientoPanelMisSolicitudes');
	if(flagRefrescamiento.length > 0) {
		var nn  = $jQuery17('div.solicitud').size();
		if (nn > 0 && flagRefrescamiento.val() == 1 ){

			flagRefrescamiento.val("0");

			$jQuery17('div.solicitud').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class: 'texto_con_elipses',ellipsis_string:'...'});

		}
	}
	
	
//	Mi perfil
	flagRefrescamiento = $jQuery17('#flagRefrescamientoPanelMiPerfil');
	if(flagRefrescamiento.length > 0) {

		if(($jQuery17('#nombre').length>0          ||
				$jQuery17('#nombre').length > 0    ||
				$jQuery17('#ci').length>0         ||
				$jQuery17('#correo').length>0     ||
				$jQuery17('#telefono').length>0   ||
				$jQuery17('#ultimoIngreso').length>0) && flagRefrescamiento.val() == '1' ){

			flagRefrescamiento.val("0");

			$jQuery17('#nombre').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class: 'texto_con_elipses',ellipsis_string:' ...'});
			$jQuery17('#ci').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class: 'texto_con_elipses',ellipsis_string:' ...'});
			$jQuery17('#correo').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class: 'texto_con_elipses',ellipsis_string:' ...'});
			$jQuery17('#telefono').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class: 'texto_con_elipses',ellipsis_string:' ...'});
			$jQuery17('#ultimoIngreso').ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class: 'texto_con_elipses',ellipsis_string:' ...'});

		}
	}
}

function _generarLinkParaCuandrante($,_idSrc,_idDest,_title) {
	var linkId = '[id="'+_idSrc+'"]';
	var onClickHandler='';
	if( typeof $(linkId) != 'undefined' ) {
		if( typeof $(linkId).outerHTML != 'undefined' ) {
			var html = $(linkId).outerHTML();
			var onClickRegex = /<.+?onclick=\"(.*?)\".+?>/ig;
			var match = onClickRegex.exec(html);
			onClickHandler = match.length > 0 ? match[1] : '';
		}
	}
	var linkDestino = null;
	if (_title==canjear){
	    if ($('#valorPuntosClub').val()==0){
	    	onClickHandler = 'false';
	    	//linkDestino ='<spam  '+  _title ; </
	    	
	    	linkDestino ='<span class="texto_con_elipses desactivado">'+  _title +'</span>';
	    }else{
	    	linkDestino = '<a id="' + _idDest + '" onclick="' + onClickHandler + '">' + _title + '</a>';
	    }	
	}else{
		linkDestino = '<a id="' + _idDest + '" onclick="' + onClickHandler + '">' + _title + '</a>';	
	}
	
	return linkDestino;
	
}


function _agregarLink($,idContenedor,link,separador) {
	
	// Primero aplica
	if( typeof $(idContenedor).attr("threedots") != "undefined"  ) {
//		alert("0.$(idContenedor).attr('threedots'):" + $(idContenedor).attr("threedots") +">> Salir");
		$(idContenedor).removeAttr('threedots');
		return;
	}
	var espacio = "&#160;";
	var textoOriginal = typeof $(idContenedor).attr("threedots") != "undefined" ? $(idContenedor).attr("threedots") : "";
	var contenedorTexto = '<span class="texto_con_elipses">' + textoOriginal + '</span>';
	var linkCompleto = separador + link;
	var contenedorObj = $(idContenedor).ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class:'texto_con_elipses',ellipsis_string:"..."+linkCompleto});
//	alert("1.Threedots aplicado ("+idContenedor+")...");
	
	var numContenedoresElipse = $(idContenedor).find(".threedots_ellipsis").length;

	if( numContenedoresElipse == 0 ) {
		// Si la fila no fue truncada, entonces agrega el link al final.
//		alert("2.numContenedoresElipse: "+numContenedoresElipse);
//		alert("3.idContenedor.threedots: "+$(idContenedor).attr('threedots'));
		$(idContenedor).removeAttr('threedots');
		
		var span = $(idContenedor).find(".texto_con_elipses");

		if(span.length > 0 ) {
			var anchoEspacioEnPixeles = _calcularAncho($,idContenedor,espacio);
			if(anchoEspacioEnPixeles==0) anchoEspacioEnPixeles = 1;
//			alert("anchoEspacioEnPixeles:"+anchoEspacioEnPixeles);
			var bordeDerechoContenedor = parseInt($(idContenedor).css("marginRight"),10)+parseInt($(idContenedor).css("paddingRight"),10)+parseInt($(idContenedor).css("borderRightWidth"),10);
//			alert("bordeDerechoContenedor:"+bordeDerechoContenedor);
			var numEspacios = Math.ceil(bordeDerechoContenedor/anchoEspacioEnPixeles)*4+1;
//			alert("numEspacios: " + numEspacios);
			var padding = Array(numEspacios+1).join(espacio);
//			alert("anchoEspacioEnPixeles: " + anchoEspacioEnPixeles + "\n" +
//				  "bordeDerechoContenedor: " + bordeDerechoContenedor + "\n" +
//				  "padding("+(numEspacios+1)+"):'"+padding+"'");
			
//			alert("4.span.html: "+span.html());

//			alert("5.idContenedor.threedots: "+$(idContenedor).attr('threedots'));
			//$(idContenedor).ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class:'texto_con_elipses',ellipsis_string:"..."+linkCompleto});
			var flag = true;
			
				
			if(idContenedor=='#puntosClub' ){
				
				var valorPuntosClub = $('#valorPuntosClub').val();
				var anchoPuntosClub = _calcularAncho($,idContenedor,valorPuntosClub);
				var anchoLinkCompleto = _calcularAncho($,idContenedor,linkCompleto);
				var anchoTresPuntos = _calcularAncho($,idContenedor,'...');
				var anchoTotal = anchoPuntosClub + anchoLinkCompleto;
				if(anchoTotal<93){
					$(span).append(linkCompleto);
				}else{
					var corte = valorPuntosClub.length - 7;
					var result = valorPuntosClub.substr(0,valorPuntosClub.length-corte-2);
					$(idContenedor).find('.texto_con_elipses').remove();
					var textoResultado = '<span class="texto_con_elipses">' + result + '...' + linkCompleto + '</span>';
					$(idContenedor).append(textoResultado);
				}	
				flag = false;
			}else if(idContenedor=='#equipo'){
				var valorEquipoActual = $('#valorEquipoActual').val();
				var anchoEquipoActual = _calcularAncho($,idContenedor,valorEquipoActual);
				var anchoLinkCompleto = _calcularAncho($,idContenedor,linkCompleto);
				var anchoTresPuntos = _calcularAncho($,idContenedor,'...');
				var anchoTotal = anchoEquipoActual + anchoLinkCompleto;
				if(anchoTotal<143){
					$(span).append(linkCompleto);
				}else{
					var corte = valorEquipoActual.length - 13;
					var result = valorEquipoActual.substr(0,valorEquipoActual.length-corte-2);
					$(idContenedor).find('.texto_con_elipses').remove();
					var textoResultado = '<span class="texto_con_elipses">' + result + '...' + linkCompleto + '</span>';
					$(idContenedor).append(textoResultado);
				}	
				flag = false;
			}else if(idContenedor=='#planActual'){
				var valorPlanActual = $('#valorPlanActual').val();
				var anchoPlanActual = _calcularAncho($,idContenedor,valorPlanActual);
				var anchoLinkCompleto = _calcularAncho($,idContenedor,linkCompleto);
				var anchoTresPuntos = _calcularAncho($,idContenedor,'...');
				var anchoTotal = anchoPlanActual + anchoLinkCompleto;
				if(anchoTotal<156){
					$(span).append(linkCompleto);
				}else{
					var corte = valorPlanActual.length - 14;
					var result = valorPlanActual.substr(0,valorPlanActual.length-corte-2);
					$(idContenedor).find('.texto_con_elipses').remove();
					var textoResultado = '<span class="texto_con_elipses">' + result + '...' + linkCompleto + '</span>';
					$(idContenedor).append(textoResultado);
				}	
				flag = false;
			}else if(idContenedor=='#saldo'){
				var valorSaldo = $('#valorSaldo').val();
				var valorFecha = $('#valorFecha').val();
				var valorTotal = valorSaldo + valorFecha;
				var anchoDeuda = _calcularAncho($,idContenedor,valorSaldo);
				var anchoFecha = _calcularAncho($,idContenedor,valorFecha);
				var anchoLinkCompleto = _calcularAncho($,idContenedor,linkCompleto);
				var anchoTotal = anchoDeuda + anchoFecha + anchoLinkCompleto;
				if(anchoTotal<216){
					$(span).append(linkCompleto);
				}else{
					var corte = valorTotal.length - 23;
					var result = valorTotal.substr(0,valorTotal.length-corte-3);
					$(idContenedor).find('.texto_con_elipses').remove();
					var textoResultado = '<span class="texto_con_elipses">' + result + '...' + linkCompleto + '</span>';
					$(idContenedor).append(textoResultado);
				}
				flag = false;
				//DEUDA DEL CUADRANTE MIS FACTURAS
			}else if(idContenedor=='#deuda'){
				var valorDeuda = $('#valorDeuda').val();
				var valorFecha = $('#valorFecha').val();
				var valorTotal = valorDeuda + valorFecha;
				var anchoDeuda = _calcularAncho($,idContenedor,valorDeuda);
				var anchoFecha = _calcularAncho($,idContenedor,valorFecha);
				var anchoLinkCompleto = _calcularAncho($,idContenedor,linkCompleto);
				var anchoTotal = anchoDeuda + anchoFecha + anchoLinkCompleto;
				if(anchoTotal<217){
				
					$(span).append(linkCompleto);
				}else{
					var corte = valorTotal.length - 29;
//					El result es el valor del lado izquierdo (facturado e) al link pagar, ej: facturado e...| pagar
					var result = valorTotal.substr(0,valorTotal.length-corte-4);
					
					$(idContenedor).find('.texto_con_elipses').remove();
					var textoResultado = '<span class="texto_con_elipses">' + result + '...' + linkCompleto + '</span>';
					$(idContenedor).append(textoResultado);
				}
				flag = false;
			}

			
			if(flag){
				$(span).append(linkCompleto+padding);
				$(contenedorObj).ThreeDots.update({max_rows:1,allow_dangle:false,whole_word:false,text_span_class:'texto_con_elipses',ellipsis_string:"..."+linkCompleto+padding});
//				alert("6.Threedots aplicado...");

				// Si el link recien agregado hace que la linea crezca, entonces reaplicar truncamiento...
				numContenedoresElipse = $(idContenedor).find(".threedots_ellipsis").length;

				if( numContenedoresElipse > 0 ) {
//					alert("7.numContenedoresElipse: "+numContenedoresElipse);
					$(idContenedor).find('.texto_con_elipses').remove();
//					alert("8.$(idContenedor).find('.texto_con_elipses').html: "+$(idContenedor).html());
					$(idContenedor).append(contenedorTexto);
					//$(idContenedor).ThreeDots({max_rows:1,allow_dangle:false,whole_word:false,text_span_class:'texto_con_elipses',ellipsis_string:"..."+linkCompleto});
					$(contenedorObj).ThreeDots.update({max_rows:1,allow_dangle:false,whole_word:false,text_span_class:'texto_con_elipses',ellipsis_string:"..."+linkCompleto});
//					alert("9.Threedots aplicado...");

				}
			}
		}
		
	}
}

/**********************************************************************************

METHOD
	_anchoEspacio {private}

DESCRIPTION
	returna el ancho de un contenedor con el texto provisto internamente en pixeles

**********************************************************************************/

function _calcularAncho($,contenedor,texto) {

	$(contenedor).append("<div id='div_calc_ancho_' style='position:absolute; visibility:hidden'>"+texto+"</div>");
	// medir
	var temp_ancho = $('#div_calc_ancho_').width();
	// remover
	$('#div_calc_ancho_').remove();

	return temp_ancho;
}

