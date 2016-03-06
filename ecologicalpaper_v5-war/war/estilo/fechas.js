var a, mes, dia, anyo, febrero;
    
    /**
    * funcion para comprobar si una año es bisiesto
    * argumento anyo > año extraido de la fecha introducida por el usuario
    */
    function anyoBisiesto(anyo)
    {
        /**
        * si el año introducido es de dos cifras lo pasamos al periodo de 1900. Ejemplo: 25 > 1925
        */
        if (anyo < 100)
            var fin = anyo + 1900;
        else
            var fin = anyo ;

        /*
        * primera condicion: si el resto de dividir el año entre 4 no es cero > el año no es bisiesto
        */

        if (fin % 4 != 0)
            return false;
        else
        {
            if (fin % 100 == 0)
            {
                /**
                * si el año es divisible por 4 y por 100 y divisible por 400 > es bisiesto
                */
                if (fin % 400 == 0)
                {
                    return true;
                }
                /**
                * si es divisible por 4 y por 100 pero no lo es por 400 > no es bisiesto
                */
                else
                {
                    return false;
                }
            }
            /**
            * si es divisible por 4 y no es divisible por 100 > el año es bisiesto
            */
            else
            {
                return true;
            }
        }
    }
    
    /**
    * funcion principal de validacion de la fecha
    * argumento fecha > cadena de texto de la fecha introducida por el usuario
    */
    function validarFecha(fecha)
    {
       if (fecha==null||"null"==fecha) {
          return false;
       }
       /**
       * obtenemos la fecha introducida y la separamos en dia, mes y año
       */
       a=fecha;
       anyo=a.split("-")[0];
       mes=a.split("-")[1];
       dia=a.split("-")[2];
      
       if(anyoBisiesto(anyo))
           febrero=29;
       else
           febrero=28;
       /**
       * si el mes introducido es negativo, 0 o mayor que 12 > alertamos y detenemos ejecucion
       */
       if ((mes<1) || (mes>12))
           return false;
       /**
       * si el mes introducido es febrero y el dia es mayor que el correspondiente 
       * al año introducido > alertamos y detenemos ejecucion
       */
       if ((mes==2) && ((dia<1) || (dia>febrero)))
           return false;
       /**
       * si el mes introducido es de 31 dias y el dia introducido es mayor de 31 > alertamos y detenemos ejecucion
       */
       if (((mes==1) || (mes==3) || (mes==5) || (mes==7) || (mes==8) || (mes==10) || (mes==12)) && ((dia<1) || (dia>31)))
           return false;
       /**
       * si el mes introducido es de 30 dias y el dia introducido es mayor de 30 > alertamos y detenemos ejecucion
       */
       if (((mes==4) || (mes==6) || (mes==9) || (mes==11)) && ((dia<1) || (dia>30)))
           return false;
       /**
       * si el mes año introducido es menor que 1900 o mayor que 2010 > alertamos y detenemos ejecucion
       * NOTA: estos valores son a eleccion vuestra, y no constituyen por si solos fecha erronea
       */
       if ((anyo<1900) || (anyo>9999))
           return false;		
       /**
       * en caso de que todo sea correcto > enviamos los datos del formulario
       * para ello debeis descomentar la ultima sentencia
       */
       else
	 return true;
    }    


    function getDate(strDate){

        var day =   strDate.substr(8,2);
        var month =  strDate.substr(4,2);
        var year   = strDate.substr(0,4);

        var date = new Date();
        date.setFullYear(year,month-1,day);

        return date;
    }