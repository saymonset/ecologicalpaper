<html>
<head>
<title></title>
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="cache-control" content="must-revalidate">
<meta http-equiv="cache-control" content="no-store">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<script language="JavaScript">
    document.onkeydown = function() {
        if(window.event && (window.event.keyCode == 122 ||
                            window.event.keyCode == 116 || window.event.ctrlKey)) {
            window.event.keyCode = 505;
        }
        if(window.event.keyCode == 505) {
            return false;
        }
    }
</script>

<script>
    function sendForm(){
        document.showDocument.submit();
    }
</script>
</head>
<body onLoad="javascript:sendForm();">
     <form name="showDocument" action="ClienteDocumentoGenerar?onlyView='onlyView'" method="Post" >    
     
    </form>
</body>
</html>