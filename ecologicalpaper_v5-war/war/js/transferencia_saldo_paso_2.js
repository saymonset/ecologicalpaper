/*
 *  Evento Ajax que indica
 *  que el proceso de transferencia fue finalizado
 * */
function transferenciaOnComplete(data) {
	
	var resultado  = eval("(" + data + ")");
	
	$jQuery14("[id=formSiguiente:botonSiguiente]").click();
}

/*
 * 
 *  Evento de carga de la página
 * */
$jQuery14('document').ready(
	function() {
		jsLanzarTransferencia();
	}
);