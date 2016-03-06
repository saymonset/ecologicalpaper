function AsignaValor(Nombre,Valor){
    var Campo = document.getElementById(Nombre);
	var maxLongitud = 3;
	var longitud = Campo.value.length;
    if(longitud <= maxLongitud)
	{
		if(Valor==""){
			Campo.value="";
		}else{
			if(Campo.value!=""){
				Campo.value = Campo.value + Valor;
			}else{
				Campo.value = Valor;
			}
		}
	}
}

function randOrd(){
    return (Math.round(Math.random())-0.5);
}

function marcador(Div,Nombre){
    var resultado = "";
	var num  = new Array('1','2','3','4','5','6','7','8','9','0');
    num.sort(randOrd);
    resultado = "<table cellpadding='1' cellspacing='1' width='100'>";
    var ini = 0;
    var fin = 10;
    resultado += "<tr bgcolor='#F0F7FD'>";
    for ( var n=0; n<10; ++n ){
        if(n%5 == 0)
		{
			resultado += "</tr>";
			resultado += "<tr bgcolor='#F0F7FD' valign='top'>";
		}
		resultado += "<td align='center'><input type='button' onclick=\"AsignaValor('"+Nombre+"','"+num[n]+"')\" value=" + num[n] + " class='button_key'></td>";
		
    }
    
	resultado += "</tr>";
    resultado += "</table><br>";
    document.getElementById(Div).innerHTML=resultado;    
}
function borrar(Nombre){
	document.getElementById(Nombre).value="";
	return false;
}

