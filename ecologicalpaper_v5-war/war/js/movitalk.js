function getDocHeight() {
    var D = document;
    return Math.max(
        D.body.scrollHeight, D.documentElement.scrollHeight,
        D.body.offsetHeight, D.documentElement.offsetHeight,
        D.body.clientHeight, D.documentElement.clientHeight
    );
}

function getDocWidth() {
    var D = document;
    return Math.max(
        D.body.scrollWidth, D.documentElement.scrollWidth,
        D.body.offsetWidth, D.documentElement.offsetWidth,
        D.body.clientWidth, D.documentElement.clientWidth
    );
}

function abrirCerrarMovitalk(grupo, esGrupo){
	ajustarTamano();
	el = document.getElementById("layerDeshabilitar");
	el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
	el = document.getElementById("layerWGP");
	el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
	if (el.style.visibility == "visible")
	{
		document.getElementById("ifrWGP").contentWindow.location="IniciarMovitalk?esgrupo=" + esGrupo + "&grupo=" + grupo;
	}
}

function ajustarTamano(){
	el = document.getElementById("layerDeshabilitar");
	if (el!=null){
		el.style.height = getDocHeight() + "px";
		el.style.width = getDocWidth() + "px";
		el = document.getElementById("layerWGP");
		el.style.left = ((getDocWidth() - 410) / 2) + "px";
	}
}

function buscarSalir(){
	var anchors = document.getElementById("ifrWGP").contentWindow.document.getElementsByTagName("a");
	for (i=0; i<anchors.length; i++){
		if (anchors[i].href.indexOf("knCloseWindow")>-1) {
			anchors[i].onclick = abrirCerrarMovitalk;
			anchors[i].href = "";
		}
	}
}

window.onresize = ajustarTamano;
