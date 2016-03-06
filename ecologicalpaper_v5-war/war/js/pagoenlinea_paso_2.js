$jQuery14('document').ready(
	function() {
			asignarEjemplosCampos();
			var componente = document.getElementById('componente').value;
			var ruta = "paso2:";
			var cambioOpc = false;
			if(document.getElementById('paso2:radioTarjeta2:0') != null){
				if(document.getElementById('paso2:radioTarjeta2:0').checked == true){
					cambioOpc = true;
				}
			}
			if(componente != ""){
				ruta = ruta.concat(componente);
				document.getElementById(ruta).focus();
				document.getElementById(ruta).select();
				vaciarFoco(ruta);
//				cambioOpc = true;
			}if(document.getElementById('tpCuenta').value=='miCuenta' && document.getElementById('postpagoTitular').value == 'true' ){			
				document.getElementById('paso2:radbTipoDeuda:0').disabled=false;
				document.getElementById('paso2:radbTipoDeuda:1').disabled=false;
				//document.getElementById('paso2:radbTipoDeuda:0').checked=true;
			}			
//			if((document.getElementById('tpTarjeta').value=='miTarjeta' || document.getElementById('tpTarjeta').value=='otraTarjeta')
//					&& document.getElementById('TDCvalida').value == 'true'){
			
			admin = false;
			if (document.getElementById('adminParticular') != null){
				admin = true;
			}			
			if ((document.getElementById('postpagoTitular').value == 'true' || admin) && document.getElementById('TDCvalida').value == 'true'){
				document.getElementById('paso2:radioTarjeta1:0').checked=true;
				document.getElementById('paso2:radioTarjeta1:0').disabled=false;
				document.getElementById('paso2:radioTarjeta2:0').checked=false;
			}else{				
				document.getElementById('paso2:radioTarjeta2:0').checked=true;
				if(document.getElementById('paso2:radioTarjeta1:0') != null){
					document.getElementById('paso2:radioTarjeta1:0').checked=false;
				}
			}
			
			if(cambioOpc == true){
				document.getElementById('paso2:radioTarjeta2:0').checked=true;
				if(document.getElementById('paso2:radioTarjeta1:0') != null){
					document.getElementById('paso2:radioTarjeta1:0').checked=false;
				}
			}
		//
	}
);

function asignarEjemplosCampos(){
	if (document.getElementById('tpCuenta').value=='miCuenta' && document.getElementById('postpagoTitular').value == 'true' ){
		if(document.getElementById('paso2:txtErrorDeuda').value != "")
			document.getElementById('paso2:txtErrorDeuda').value="Ej. 150";
	}else{
		if(document.getElementById('paso2:txtErrorDeuda1').value.length == 0 || 
			 isNaN(document.getElementById('paso2:txtErrorDeuda1').value)){
			document.getElementById('paso2:txtErrorDeuda1').value="Ej. 150";
		}
	}
	if(document.getElementById('paso2:txtErrorNombre').value.length == 0){
		document.getElementById('paso2:txtErrorNombre').value="Ej. Gabriela Marcano";
	}
	if(document.getElementById('paso2:txtErrorCedula').value.length == 0){
		document.getElementById('paso2:txtErrorCedula').value="Ej. 999999999";
	}
	if(document.getElementById('paso2:txtErrorNumeroTdc').value.length == 0)
		document.getElementById('paso2:txtErrorNumeroTdc').value="";	
	if(document.getElementById('paso2:txtErrorCodSeg').value.length == 0)
		document.getElementById('paso2:txtErrorCodSeg').value="";	
	document.getElementById('paso2:txtErrorAcuerdo').checked=false;	
}
function vaciarEjemplosCampos(event){
	document.getElementById(event.id).value="";
	if(event.id == 'paso2:txtErrorDeuda'){
		enfocar_otroMonto(event);
	}	
}

function vaciarFoco(ruta){
	document.getElementById(ruta).value="";
}

function vaciarCampos(){	
	if(document.getElementById('paso2:txtErrorNombre').value == 'Ej. Gabriela Marcano'){
		document.getElementById('paso2:txtErrorNombre').value="";
	}
	if (document.getElementById('paso2:txtErrorCedula').value == 'Ej. 999999999'){
		document.getElementById('paso2:txtErrorCedula').value="";
	}
	if (document.getElementById('tpCuenta').value=='miCuenta'){
		if (document.getElementById('paso2:txtErrorDeuda').value == 'Ej. 150'){
			document.getElementById('paso2:txtErrorDeuda').value="";
		}
	}else{
		if (document.getElementById('paso2:txtErrorDeuda1').value == 'Ej. 150'){
			document.getElementById('paso2:txtErrorDeuda1').value="";
		}
	}
}

function enfocar_otraTarjeta(event){	
	if(document.getElementById('paso2:radioTarjeta1:0')!=null){ 
		document.getElementById('paso2:radioTarjeta1:0').checked=false; 
	}
	if(document.getElementById('paso2:radioTarjeta2:0').checked==false){	
		document.getElementById('paso2:radioTarjeta2:0').checked=true;
	}
}

function enfocar_otroMonto(event){
	if(document.getElementById('paso2:radbTipoDeuda:2')!=null){ 
		document.getElementById('paso2:radbTipoDeuda:0').checked=false; 
		document.getElementById('paso2:radbTipoDeuda:1').checked=false;
		document.getElementById('paso2:radbTipoDeuda:2').checked=true;
	}
}

function llamar_funciones_otraTarjeta(event){
	enfocar_otraTarjeta(event);
}

function manejarRadio(event){
	document.getElementById('paso2:txtErrorDeuda').value="";
}
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