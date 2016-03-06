// JavaScript Document
var ramdomdisplay = 0;
var interval = 10000;
/*var imageDir = 'images/lightbox/';*/

function HideShow(div){
	//alert(div.id);
	if(div.id=='control3'){
		div.style.display = 'none';
		div.style.visibility = 'hidden';
		aux = document.getElementById('control2');
		aux.style.display = 'inline';
		aux.style.visibility = 'visible';
	}else{
		div.style.display = 'none';
		div.style.visibility = 'hidden';
		aux = document.getElementById('control3');
		aux.style.display = 'inline';
		aux.style.visibility = 'visible';
	}	
}

var imageNum = 0;  
/*
imageArray = new Array();
imageArray[imageNum++] = new imageItem(imageDir + "0.jpg",'');
imageArray[imageNum++] = new imageItem(imageDir + "1.jpg",'');
imageArray[imageNum++] = new imageItem(imageDir + "2.jpg",'');

var totalImages = imageArray.length; 
*/

function addShortCuts()
{
	contenedor = document.createElement('table');
	fila = document.createElement('tr');

	for(x=0;x<imageArray.length;x++)
	{
		columna = document.createElement('td');
		columna.id = 'columna_'+x;	

		cajaboton = document.createElement('div');
		cajaboton.id = 'botondiv';
		cajaboton.style.position = 'absolute';
	
		
		linkboton = document.createElement('a'); 
		linkboton.id = 'boton_'+x;
		linkboton.href = 'javascript:showImage('+x+')';
		//linkboton.onmouseover = 'javascript:updateImageBGOnMouseOver('+x+')';
		
		imagen = document.createElement('img');
		imagen.id = 'imagen_'+x;
		imagen.src = 'images/img/pestanaUp.png';
		imagen.style.border=3;

		imagen.onclick = 'showImage('+x+')';
		
		linkboton.appendChild(imagen);
		cajaboton.appendChild(linkboton);
		columna.appendChild(cajaboton);
		fila.appendChild(columna);
		
	}
	contenedor.appendChild(fila);
	controles = document.getElementById('shortcuts');
	controles.appendChild(contenedor);
}


function setonOver(id)
{
	imagen = document.getElementById(id);
	imagen.src = 'images/img/pestanaDown.png';
}
function setonOut(id)
{
	imagen = document.getElementById(id);
	imagen.src = 'images/img/pestanaUp.png';
}

function updateImageBG(num)
{
	for(x=0;x<imageArray.length;x++)
	{	
		if(x==num)
		{
			document.getElementById('imagen_'+num).src = 'images/img/pestanaSeleccionada.png';

		}else
		{
			document.getElementById('imagen_'+x).src = 'images/img/pestanaUp.png';

		}
	}
}
function updateImageBGOnMouseOver(num)
{
	alert('ok');
	for(x=0;x<imageArray.length;x++)
	{	
		if(x==num)
		{
			document.getElementById('imagen_'+num).src = 'images/img/pestanaSeleccionada.png';
		}else
		{
			document.getElementById('imagen_'+x).src = 'images/img/pestanaUp.png';
		}
	}
}

/*function imageItem(image_location,url) {  

       this.image_item = new Image();  
       this.image_item.src = image_location; 

       this.image_item.longDesc = url;
}*/ 

function get_ImageItemLocation(imageObj) {
       var linkdestacado = document.getElementById('linkdestacado');
       linkdestacado.href = imageObj.image_item.longDesc;
       return(imageObj.image_item.src)  

}

function randNum(x, y) {  

	var range = y - x + 1;  
	return Math.floor(Math.random() * range) + x;  

}

function getNextImage() {  

	if (ramdomdisplay) {  
		imageNum = randNum(0, totalImages-1);  
	}  
	 else {  
		imageNum = (imageNum+1) % totalImages;
		updateImageBG(imageNum);  
	}
	var new_image = get_ImageItemLocation(imageArray[imageNum]);  
	return(new_image);  
} 

function getPrevImage() {  

	imageNum = (imageNum-1) % totalImages;  
	updateImageBG(imageNum); 
	if(imageNum<0){imageNum=totalImages-1;}
	var new_image = get_ImageItemLocation(imageArray[imageNum]);  
	return(new_image);  

} 

function getImage(num) {  

	var new_image = get_ImageItemLocation(imageArray[num]);  
	return(new_image);  

}

function showImage(num){
	updateImageBG(num); 
	var new_image = get_ImageItemLocation(imageArray[num]); 
	cambia(new_image);
}

function prevImage() {  
	prev_image = getPrevImage();
	cambia(prev_image);
} 

function nextImage() {
       next_image = getNextImage();
       cambia(next_image);
}

function startImage(){
       updateImageBG(0);
       var new_image = get_ImageItemLocation(imageArray[0]);
       cambia(new_image);
       var recur_call = "switchImage()";  
               timerID = setTimeout(recur_call, interval);
}

function switchImage() {  

	var src = document.getElementById('play').src;
	//alert(src.split('img/')[1]);
	//if(src.split('img/')[1]=='pause.png')
	//{
		//document.getElementById('play').src='img/play.png';
	//}
	
	//if(src.split('img/')[1]=='play.png'){
		var new_image = getNextImage(); 
		cambia(new_image);
		var recur_call = "switchImage()";  
		timerID = setTimeout(recur_call, interval);  
		//document.getElementById('play').src='img/pause.png';
	//}
} 
function cambia(image)
{
	//FadeOut('destacado');
	 
	//document.getElementById('destacado').style.backgroundImage = "url("+new_image+")"; 
	//FadeIn('destacado');
	
	FadeInImage('foregroundimg',image,'destacado');

	
}




//////////////////////////////////////////////////////////////////////////////////


// Opacity and Fade in script.
// Script copyright (C) 2008 http://www.cryer.co.uk/.
// Script is free to use provided this copyright header is included.
function SetOpacity(object,opacityPct)
{
  // IE.
  object.style.filter = 'alpha(opacity=' + opacityPct + ')';
  // Old mozilla and firefox
  object.style.MozOpacity = opacityPct/100;
  // Everything else.
  object.style.opacity = opacityPct/100;
}
function ChangeOpacity(id,msDuration,msStart,fromO,toO)
{
  var element=document.getElementById(id);
  var opacity = element.style.opacity * 100;
  var msNow = (new Date()).getTime();
  opacity = fromO + (toO - fromO) * (msNow - msStart) / msDuration;
  if (opacity<0) 
    SetOpacity(element,0)
  else if (opacity>100)
    SetOpacity(element,100)
  else
  {
    SetOpacity(element,opacity);
    element.timer = window.setTimeout("ChangeOpacity('" + id + "'," + msDuration + "," + msStart + "," + fromO + "," + toO + ")",1);
  }
}

function FadeIn(id)
{
  var element=document.getElementById(id);
  if (element.timer) window.clearTimeout(element.timer); 
  var startMS = (new Date()).getTime();
  element.timer = window.setTimeout("ChangeOpacity('" + id + "',1000," + startMS + ",0,100)",1);
}
function FadeOut(id)
{
  var element=document.getElementById(id);
  if (element.timer) window.clearTimeout(element.timer); 
  var startMS = (new Date()).getTime();
  element.timer = window.setTimeout("ChangeOpacity('" + id + "',1000," + startMS + ",100,0)",1);
}
function FadeInImageDiv(oldImage,newImage,backgroundID)
{
  var background=document.getElementById(backgroundID);
  SetOpacity(background,0);
  background.style.backgroundImage = 'url(' + newImage + ')';
  if (background.timer) window.clearTimeout(background.timer); 
  var startMS = (new Date()).getTime();
  background.timer = window.setTimeout("ChangeOpacity('" + oldImage + "',1000," + startMS + ",0,100)",10);
}
function FadeInImage(foregroundID,newImage,backgroundID)
{
  var foreground=document.getElementById(foregroundID);
  if (backgroundID)
  {
    var background=document.getElementById(backgroundID);
    if (background)
    {
      background.style.backgroundImage = 'url(' + foreground.src + ')';      
      background.style.backgroundRepeat = 'no-repeat';
    }
  }
  SetOpacity(foreground,0);
  foreground.src = newImage;
  if (foreground.timer) window.clearTimeout(foreground.timer); 
  var startMS = (new Date()).getTime();
  foreground.timer = window.setTimeout("ChangeOpacity('" + foregroundID + "',1000," + startMS + ",0,100)",10);
}









 
 


