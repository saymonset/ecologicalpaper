<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Documento sin t&iacute;tulo</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="dtree.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(img/bg_left.jpg);
}
-->
</style>
<link href="img/dtree.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="111%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><p><a href="javascript: d.openAll1();" class="dtree">open all</a> | <a href="javascript: d.closeAll();" class="dtree">close all</a></p>
        <script type="text/javascript">
		<!--

		p = new dTree('p');

		p.add(0,-1,'y example tree');
                p.add(31,0,'Menu Principal','menuprincipal.html');
		p.add(19,31,'Bandeja de Trabajo','menuprincipal.html');
		p.add(20,31,'Correos','http://www.google.com');
		p.add(21,31,'Documentos Publicados','menuprincipal.html');
		p.add(22,31,'Seccion de Buscar','menuprincipal.html');
		p.add(23,31,'Configuracion','menuprincipal.html');
		p.add(24,31,'Usuario','menuprincipal.html');
		p.add(25,31,'Grupos','menuprincipal.html');
		p.add(26,31,'SACOP','menuprincipal.html');
		p.add(27,31,'AUDITORIAS','menuprincipal.html','Pictures I\'ve taken over the years','','','img/imgfolder.gif');
		p.add(28,31,'The trip to Iceland','menuprincipal.html','Pictures of Gullfoss and Geysir');
		p.add(29,31,'Mom\'s birthday','menuprincipal.html');
		p.add(30,31,'Recycle Bin','menuprincipal.html','','','img/trash.gif');

		document.write(p);

		//-->
	</script></td>
  </tr>
  <tr>
    <td valign="top">
      <div class="dtree">
        <p><a href="javascript: d.openAll1();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>
        <script type="text/javascript">
		<!--

		d = new dTree('d');

		d.add(0,-1,'Esta es la raiz');
		d.add(1,0,'Node 1','example01.html');
		d.add(2,0,'Node 2','example01.html');
		d.add(3,1,'Node 1.1','example01.html');
		d.add(4,0,'Node 3','example01.html');
		d.add(5,3,'Node 1.1.1','example01.html');
		d.add(6,5,'Node 1.1.1.1','example01.html');
		d.add(7,0,'Node 4','example01.html');
		d.add(8,1,'Node 1.2','example01.html');
		d.add(9,0,'My Pictures','example01.html','Pictures I\'ve taken over the years','','','img/imgfolder.gif');
		d.add(10,9,'The trip to Iceland','example01.html','Pictures of Gullfoss and Geysir');
		d.add(11,9,'Mom\'s birthday','example01.html');
		d.add(12,0,'Recycle Bin','example01.html','','','img/trash.gif');

		document.write(d);

                h = new dTree('h');
                h.add(0,-1,'Esta es segunda raiz');
                h.add(13,0,'Node 1','example01.html');
		h.add(14,13,'Node 2','example01.html');
		h.add(15,13,'Node 1.1','example01.html');
		h.add(16,15,'Node 3','example01.html');
		h.add(17,0,'Node 1.1.1','example01.html');
		h.add(18,17,'Node 1.1.1.1','example01.html');
               document.write(h);
		//-->
	</script>
    </div></td>
  </tr>
</table>
</body>
</html>
