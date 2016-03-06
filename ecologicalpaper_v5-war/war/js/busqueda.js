
function spotlight(){
	window.setTimeout(function(){
		var value = $jQuery17("#busqueda_avanzada").val();
						//quitamos los espacion en blanco de los extremos izquierdo y derecho
						value= allTrim(value);
					 
alert('Hola mundo');
		var pattern = /^([a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\s]+)(,[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\s]+)*$/;
		if(pattern.test(value)) {
			value = $jQuery17.trim(value.toLowerCase());
			value = quitarAcentos(value);
			$jQuery17("#sl_searchResult").remove();
			var divResultToW = $jQuery17(document.createElement('div')).attr('id', 'sl_searchResult');
			divResultToW.appendTo("#sl_searchResultWrapper");
			var showItem = "";
			var count=0;
			for(var i=0;i<$arrayKeys.length; i++){
				if($arrayKeys[i].key==value || $arrayKeys[i].key.indexOf(value)!=-1 ){
					var index = $arrayKeys[i].index;
					var indexMenu = index.split("-");
					for(var j=0;j<indexMenu.length;j++){
						var posicion = indexMenu[j];
						var items = showItem.split('-');
						var flagAdd = true;
						// valida si el items del menu ya esta agregado al div
						for(var x=0;x<items.length;x++){
							if(items[x]==posicion){
								flagAdd = false;
								break;
							}
						}
						if(flagAdd){
							showItem = showItem + posicion + '-';
						}
					}
				}
			}
			var table = '<div  class="contenedorSpotlight bRightSide"><table class="spotlightTabla" border="0">'+
			'<tbody>';
			var opciones = showItem.split('-');
			for(var i=0;i<opciones.length-1;i++){
				var posicion = opciones[i];
				if(posicion.replace(/^\s*|\s*$/g,"")!='' && count<5){
					var idMmoOpcion = $arrayMenu[posicion].idMmoOpcion;
					var titulo = $arrayMenu[posicion].titulo;
					var descripcion = $arrayMenu[posicion].descripcion;
					var divResultBox = $jQuery17('#sl_searchResult');
					var divOption = $jQuery17(document.createElement('div'));
					var url = $arrayMenu[posicion].url;
					var table= table+
					'<tr>'+
					'<td class="Imagen" >'+
					'<img src="Imagen.jsf?IdMmoOpcion='+idMmoOpcion+'" alt=""/>'+
					'</td>'+
					'<td class="descripcionTabla">'+
					'<p><a href="#" onclick=jsNavegar("'+idMmoOpcion+'");>'+titulo+'</a></p>'+
					'<span>'+descripcion+'</span>'+
					'</td>'+
					'</tr>';
					if(i==opciones.length-2 || count==4  ){
						table = table +'</tbody>'+ '</table></div><div class="bBottom"  />';	
					}else{
						table = table + '<tr>'+
						'<td colspan="4" class="separadorTabla" ></td>'+
						'</tr>';
					}
					count = count +1;
				}

			}
			if(count==0){
				var imagen = $jQuery17('#img_spotlight');
				imagen.attr('src','images/cerrar_spotlight_Circulo.png');
				imagen.attr('onclick','cerrar();');
				$jQuery17('#sl_searchResult').remove();
				var divResultToW = $jQuery17(document.createElement('div')).attr('id', 'sl_searchResult');
				var result = '<div  class="contenedorSpotlight bRightSide"><table class="spotlightTabla" border="0">'+
								'<tbody>'+
									'<tr>'+
										'<td class="sinResultado_spotlight">'+
											'La búsqueda no arroja resultados.'+
										'<td>'+
									'</tr>'+
								'</tbody>'+
								'</table></div><div class="bBottom"  />';
				$jQuery17(result).appendTo(divResultToW);
				divResultToW.appendTo("#sl_searchResultWrapper");
			}else{
				var imagen = jQuery('#img_spotlight');
				imagen.attr('src','images/cerrar_spotlight_Circulo.png');
				imagen.attr('onclick','cerrar();');

				$jQuery17(table).appendTo(divOption);
				divOption.appendTo(divResultBox);
			}
		}else{
//			$jQuery17("#sl_searchResult").remove();
			cerrar();
		} 
	},1000);
}
function cerrar(){
	var imagen = $jQuery17('#img_spotlight');
	imagen.attr('src','images/lupa_Spotlight.jpg');
	imagen.attr('onclick','spotlight();');
	$jQuery17("#busqueda_avanzada").val('');
	$jQuery17("#sl_searchResult").remove();
}

function quitarAcentos(str){
	var from = "ÃÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàáäâèéëêìíïîòóöôùúüûÑñÇç",
	to   = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc",
	mapping = {};

	for(var i = 0, j = from.length; i < j; i++ )
		mapping[ from.charAt( i ) ] = to.charAt( i );
	var ret = [];

	for( var i = 0, j = str.length; i < j; i++ ) {
		var c = str.charAt( i );
		if( mapping.hasOwnProperty( str.charAt( i ) ) )
			ret.push( mapping[ c ] );
		else
			ret.push( c );
	}
	return ret.join( '' );
}


//metodos de jquery para validar el placeholder si lo soporta o no el navegador, si no lo soporta se crea con jquery
(function($){
	  $.fn.placeholder = function(){
	    // Ingnoramos si el navegador soporta nativamente esta funcionalidad
	    if ($.fn.placeholder.supported()){
	    	 
	      return $(this);
	    }else{

	      // En el evento submit del formulario reseteamos los values de los inputs
	      // cuyo value es igual al placeholder
	      $(this).parent('form').submit(function(e){
	        $('input[placeholder].placeholder', this).val('');
	      });

	      // activamos el placeholder
	      $(this).each(function(){
	        $.fn.placeholder.on(this);
	      });

	      return $(this)
	        // Evento on focus
	        .focus(function(){
	          // Desactivamos el placeholder si vamos a introducir nuevo texto
	          if ($(this).hasClass('placeholder')){
	            $.fn.placeholder.off(this);
	          }
	        })
	        // Evento on blur
	        .blur(function(){
	          // Activamos el placeholder si no tiene contenido
	          if ($(this).val() == ''){
	            $.fn.placeholder.on(this);
	          }
	        });
	    }
	  };

	  // Función que detecta si el navegdor soporta el placeholder nativamente
	  // Extraida de: http://diveintohtml5.org/detect.html#input-placeholder
	  $.fn.placeholder.supported = function(){
	    var input = document.createElement('input');
	    return !!('placeholder' in input);
	  };

	  // Añade el contenido del placeholder en el value del input
	  $.fn.placeholder.on = function(el){
	    var $el = $(el);
	    $el.val($el.attr('placeholder')).addClass('placeholder');
	  };
	  // Borra el contenido del value
	  $.fn.placeholder.off = function(el){
	    $(el).val('').removeClass('placeholder');
	  };
	})(jQuery);

jQuery(document).ready(function(){
	  jQuery('input[placeholder]').placeholder();
	});
//fin metodos de jquery para validar el placeholder si lo soporta o no el navegador, si no lo soporta se crea con jquery
// quitar espacios en blanco en los extremos de inicio y final de la cadena
function lTrim(sStr) {
	while (sStr.charAt(0) == " ")
		sStr = sStr.substr(1, sStr.length - 1);
	return sStr;
}

function rTrim(sStr) {
	while (sStr.charAt(sStr.length - 1) == " ")
		sStr = sStr.substr(0, sStr.length - 1);
	return sStr;
}

function allTrim(sStr) {
	return rTrim(lTrim(sStr));
}
// quitar espacios en blanco en los extremos de inicio y final de la cadena
