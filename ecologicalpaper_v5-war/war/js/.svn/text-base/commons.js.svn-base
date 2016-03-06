function cut_stri(str, posicion) {
	debugger;
	var salida;
	if (str.length > posicion) {
		salida = str.substring(0,posicion) + "...";
	} else {
		salida = str;
	}
	return salida;
}


function ajustarPosicionCorteNivelDos(entrada, posicion) {
	var cadenaA = entrada.substring(0, posicion);
	var cadenaB = entrada.substring(posicion, entrada.length);	
	return cadenaA + "\n" + cadenaB;
}


function ajustarAncho(obj, tam) {
	debugger;
	if(tam > 0){
		document.getElementById(obj).style.width = "100px";
	}
}

var submenu = {
	load:function(my_class){
	jQuery(my_class+" li.submenu_01").each(function(){
			var imagen = jQuery("#"+this.id + " .submenu img");
			var srcimagen = imagen.attr("src");
			//var tmp = imagen.attr("src").replace("_sel","");
			var srcimagenover = srcimagen.replace(".png","") + "_over.png";
			jQuery(this).bind("touchend mouseenter",function(){
				lmenu();				
				submenu.mostrar(this.id, imagen, srcimagenover);				
			}).bind("mouseleave",function(){
				submenu.ocultar(this.id, imagen, srcimagen);
			});
		});
	},
	ocultar:function(my_id, imagen, srcimagen){
		jQuery("#"+my_id).removeClass("overmenu").css("position","static");
//		jQuery("#"+"imgprueba").fadeIn(500);
		imagen.attr("src", srcimagen);
		//if(swIE60==1) jQuery(".toinvisible").css("visibility","visible");
	},
	mostrar:function(my_id, imagen, srcimagen){
		jQuery("#"+my_id).addClass("overmenu").css("position","relative").css("z-index","999");
	//	jQuery("#"+"imgprueba").fadeOut(500);
		//othersLanguages.oculta();
		imagen.attr("src", srcimagen);
		//if(swIE60==1) jQuery(".toinvisible").css("visibility","hidden");
	}
	
};

/* Fin LATAM */
var init = {
	funciones:function(){			
			if(typeof loadThreeDots != "undefined") {
				loadThreeDots();
			}
			if(jQuery(".topmenu").size()!=0) submenu.load(".topmenu");		
		}
};

jQuery(document).ready(init.funciones);

//Funcion realizada especificamente para limpiar menu principal en tablets
function lmenu(){
	if(jQuery(".topmenu li.submenu_01").hasClass('overmenu')){
		var imagen = jQuery(".topmenu li.submenu_01.overmenu .submenu img");
		var srcimagen = imagen.attr("src").replace("_over.png","") + ".png";
		imagen.attr("src", srcimagen);
		jQuery(".topmenu li.submenu_01").removeClass("overmenu").css("position","static");
	}
}


//
//Manejador del timeout
//
var mostrarModal = false;

function to($,idPushComp,idTO,idModal,idStatusCarga,idStatusMenuPrincipal, idStatusCargaSolicitudes, idStatusCargaFacturas) {

	var timeout = typeof $("[id='"+idTO+"']").val() != 'undefined' ? $("[id='"+idTO+"']").val() : 60000;	
	

	if( idModal != '' ) {
		setTimeout(function(){

			if(!mostrarModal) {
				var errorEnCargaCliente = $("[id='"+idStatusCarga+"']").val();
				var datosMinimosCargados = $("[id='"+idStatusMenuPrincipal+"']").val();

				if( typeof errorEnCargaCliente != 'undefined' && errorEnCargaCliente != "" ) {
					if( errorEnCargaCliente == 'true' ) {
						mostrarModal = true;

					}
				}
				if( typeof datosMinimosCargados == 'undefined' || datosMinimosCargados != "true" ) {
					mostrarModal = true;
						
				} 

				
				if( mostrarModal ) {					
					Richfaces.showModalPanel(idModal);
					A4J.AJAX.StopPush(idPushComp);
				}else{					
					//Verificar si no llego informacion en mis facturas/solicitudes
					apagarMisSolicitudesMisFacturas($, timeout, idStatusCargaSolicitudes, idStatusCargaFacturas, idPushComp);
					
				}
			}
			
			
			
		},timeout);
	}

}

function apagarMisSolicitudesMisFacturas($,timeout2, idStatusCargaSolicitudes, idStatusCargaFacturas, idPushComp){
	var timeout = (timeout2/2);
	setTimeout(function(){
		A4J.AJAX.StopPush(idPushComp);
		var enableEnCargaMisSolicitudes = $("[id='"+idStatusCargaSolicitudes+"']").val();
		var enableErrorEnCargaMisFacturas = $("[id='"+idStatusCargaFacturas+"']").val();		
		if( typeof enableEnCargaMisSolicitudes != 'undefined' && enableEnCargaMisSolicitudes != "" ) {
			if( enableEnCargaMisSolicitudes == 'false' ) {								
				$("#misSolicitudesPreloader").html($('#misSolicitudesNoHayInformacionComplementario').html());			
			}
		}
		if( typeof enableErrorEnCargaMisFacturas != 'undefined' && enableErrorEnCargaMisFacturas != "" ) {
			if( enableErrorEnCargaMisFacturas == 'false' ) {
				$("#misFacturasPreloader").html($('#misFacturasNoHayInformacionComplementario').html());
			}
		}		
		
	},timeout);
 	
}

