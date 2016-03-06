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
		url_base_imagenes: 'images/pagoenlinea/'		
}	;


/*
 *  mensajes de error para la validacion del numero en el formulario de ingreso de numero a recargar
 * */
var $mensaje_error_codigo_numero = 'Debes seleccionar el código de área e ingresar un número de 7 dígitos.';
var $mensaje_error_codigo = 'Debes seleccionar el código de área.';
var $mensaje_error_numero = 'Debes ingresar un número de 7 dígitos.';

function enfocar_otraCuenta(){
	if(document.getElementById('paso1:radio1:0')!=null){ 
		document.getElementById('paso1:radio1:0').checked=false; 
	}
	if(document.getElementById('paso1:radio2:0')!=null){
		if(document.getElementById('paso1:radio2:0').checked==false){
			document.getElementById('paso1:radio2:0').checked=true;
		}
	}
}


$jQuery14('document').ready(
function() {
	if(document.getElementById('paso1:radio1:0') != null
			&& document.getElementById('paso1:radio2:0') != null){
		if(document.getElementById('tpCta').value=='miCuenta'){
			document.getElementById('paso1:radio1:0').checked=true;
			document.getElementById('paso1:radio2:0').checked=false;
		}else{
			if(document.getElementById('paso1:radio1:0') != null){
				document.getElementById('paso1:radio1:0').checked=false;
				document.getElementById('paso1:radio2:0').checked=true;
			}
		}
	}
});

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

