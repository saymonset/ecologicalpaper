package com.util.file;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.oro.io.AwkFilenameFilter;
public class Archivo {
	  /**
     * TamaÃ±o del Buffer por defecto.
     */
    private static final int NUM = 512;
    /**
     * MÃ©todo que convierte un archivo en su representaciÃ³n en un arreglo de
     * byte.
     * @param archivo El archivo a ser convertido.
     * @return Retornaun arrglo de byte que representa al archivo.
     * @deprecated
     */
    public static byte[] convertirFileADataBinaria(final File archivo) {
        byte[] dataBinaria = null;
        FileInputStream streamArchivo = null;
        try {
            streamArchivo = new FileInputStream(archivo);
            dataBinaria = new byte[(int) archivo.length()];
            streamArchivo.read(dataBinaria);
            streamArchivo.close();
        } catch (IOException ex) {
            ex.printStackTrace();
           
        } finally {
            if (streamArchivo != null) {
                try {
                    streamArchivo.close();
                } catch (IOException ex) {
                
                }
            }
        }
        return dataBinaria;
    }
    
    public static  byte[] convertirFileADataBinariaDinamico(final File archivo) {
        byte[] dataBinaria = null;
        FileInputStream streamArchivo = null;
        try {
            streamArchivo = new FileInputStream(archivo);
            dataBinaria = new byte[(int) archivo.length()];
            streamArchivo.read(dataBinaria);
            streamArchivo.close();
        } catch (IOException ex) {
            ex.printStackTrace();
           
        } finally {
            if (streamArchivo != null) {
                try {
                    streamArchivo.close();
                } catch (IOException ex) {
                
                }
            }
        }
        return dataBinaria;
    }
     

    /**
     * Convierte un InputStream en un archivo con la extesiÃ³n pasada por
     * parÃ¡metros. Se crearÃ¡ un archivo temporal.
     * @param fuente InputStream fuente para la generaciÃ³n del archivo.
     * @param extension la extessiÃ³n con la cual se generarÃ¡ el archivo.
     * @param identificador Esta cadena se incluirÃ¡ en el nombre del archivo
     * para identificar el mismo en caso de ser necesario.
     * @return Retona un objeto File contentivo con el archivo
     * @deprecated 
     */
    public static final File archivoDesdeStream(final InputStream fuente,
            final String extension, final String identificador)
            throws Exception {
        byte[] buf = new byte[NUM];
        int len;
        File archivo = null;
        if (fuente == null) {
            throw new Exception("error.sis.fuente_invalida");
        }
        try {
            archivo = new File(identificador+"."+extension); 
            	//File.createTempFile(
                  //  "archivo_" + identificador + "_", "." + extension);
            archivo.deleteOnExit();
            OutputStream out = new FileOutputStream(archivo);
            while ((len = fuente.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
        } catch (IOException ex) {
          
        }
        return archivo;
    }
    public  final File fileDesdeStream(final InputStream fuente, 
    		 String nombre,
            final String extension)
            throws Exception {
        byte[] buf = new byte[NUM];
        int len;
        File archivo = null;
        if (fuente == null) {
            throw new Exception("error.sis.fuente_invalida, fuente nula");
        }
        try {
        	if (nombre.indexOf(".")!=-1){
        		nombre=nombre.substring(0,nombre.indexOf("."));
        	}
            archivo = new File(nombre+"."+extension); 
            	//File.createTempFile(
                  //  "archivo_" + identificador + "_", "." + extension);
            archivo.deleteOnExit();
            OutputStream out = new FileOutputStream(archivo);
            while ((len = fuente.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
        } catch (IOException ex) {
          
        }
        return archivo;
    }
public  InputStream fileToinputStream(File file){
	InputStream inputStream=null;
	try {
		inputStream= new FileInputStream
		(file);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return inputStream;
	
} 
     /**
     * Convierte un InputStream en un archivo con la extesiÃ³n pasada por
     * parÃ¡metros. Se crearÃ¡ un archivo temporal.
     * @param fuente InputStream fuente para la generaciÃ³n del archivo.
     * @param extension la extessiÃ³n con la cual se generarÃ¡ el archivo.
     * @param identificador Esta cadena se incluirÃ¡ en el nombre del archivo
     * para identificar el mismo en caso de ser necesario.
     * @return Retona un objeto File contentivo con el archivo
     */
    public  final File archivoDesdeStreamDinamico(final InputStream fuente,
            final String extension, final String identificador)
            throws Exception {
        byte[] buf = new byte[NUM];
        int len;
        File archivo = null;
        if (fuente == null) {
            throw new Exception("error.sis.fuente_invalida");
        }
        try {
            archivo = File.createTempFile(
                    "archivo_" + identificador + "_", "." + extension);
            archivo.deleteOnExit();
            OutputStream out = new FileOutputStream(archivo);
            while ((len = fuente.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
        } catch (IOException ex) {
            
        }
        return archivo;
    }



    public static final byte[] convertirStreamADataBinaria (
            InputStream fuente) throws Exception {
        return convertirFileADataBinaria(archivoDesdeStream(fuente, "tmp", ""));
    }

    public static final File convertirbyteAFile (byte[] fuente,
            String extension) {
        File archivo = null;
        try {
            archivo = File.createTempFile("archivo_", "." + extension);
            archivo.deleteOnExit();
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            archivoSalida.write(fuente);
            archivoSalida.close();



        } catch (IOException ex) {
      
        }
        return archivo;
    }

    public static final File convertirbyteAFile (final byte[] fuente,
            final String prefix, final String extension) {
        File archivo = null;
        try {
            archivo = File.createTempFile(prefix, "." + extension);
            //archivo.deleteOnExit();
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            archivoSalida.write(fuente);
            archivoSalida.close();

        } catch (IOException ex) {
            
        }
        return archivo;
    }


    public  final File convertirbyteAFileDinamico (final byte[] fuente,
            final String prefix, final String extension) {
        File archivo = null;
        try {
            archivo = File.createTempFile(prefix, "." + extension);
            archivo.deleteOnExit();
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            archivoSalida.write(fuente);
            archivoSalida.close();

        } catch (IOException ex) {
            
        }
        return archivo;
    }

      public static final File convertirbyteAFileNombre (byte[] fuente,
            String nombre) {
        File archivo = null;
        try {
            String extensionFile = nombre.substring(nombre.lastIndexOf(".") + 1,
                    nombre.length());
            String nombreFile = nombre.substring(0,nombre.lastIndexOf(".") );
            archivo = File.createTempFile(nombreFile, "." + extensionFile);
            archivo.deleteOnExit();
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            archivoSalida.write(fuente);
            archivoSalida.close();

        } catch (IOException ex) {
           
        }
        return archivo;
    }

    /**
     * Busca un archivo
     * @param archivoABuscarStr
     * @param buscarEnStr
     * @return
     */
    public static File buscaArchivo(final String archivoABuscarStr,
            final String buscarEnStr){
        File archivoEncontrado = null;

        File directoriaBuscarEn = new File(buscarEnStr);
        FilenameFilter filtro = new AwkFilenameFilter(".xml");
        archivoEncontrado = directoriaBuscarEn.listFiles(filtro)[0];

        return archivoEncontrado;
    }

    /**
     * Dado los datos de conexiÃ³n descarga el archivo especificado a travÃ©s de
     * protocolo ftp.
     * @param host Nombre del Host FTP al cual se conectarÃ¡.
     * @param port Puerto de conecciÃ³n.
     * @param user Usuario.
     * @param pass ContraseÃ±a.
     * @param workingDir Directorio de ftp donde se ubica el archivo a descagar.
     * @param archivoStr Nombre del archivo a descargar.
     * @return Retorna ela rchivo descargado.
     * @throws GeneralException Si ocurre algÃºn error esta excepciÃ³n es
     * arrojada.
     */
    public static final File descargaFTP(final String host, final String port,
            final String user, final String pass, final String workingDir,
            final String archivoStr)
            throws Exception {
        File archivoDescargado = null;
        try {
            FTPClient fTPClient = new FTPClient();
            fTPClient.connect(host, Integer.parseInt(port));
            fTPClient.login(user, pass);
            fTPClient.changeWorkingDirectory(workingDir);
            fTPClient.setFileType(FTP.BINARY_FILE_TYPE);

            archivoDescargado = Archivo.archivoDesdeStream(fTPClient
                    .retrieveFileStream(archivoStr), Archivo
                    .extencionArchivo(archivoStr), archivoStr);
            fTPClient.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.ftp");
        }
        return archivoDescargado;
    }

        /**
     * Dado los datos de conexiÃ³n lista los archivos a travÃ©s de
     * protocolo ftp.
     * @param host Nombre del Host FTP al cual se conectarÃ¡.
     * @param port Puerto de conecciÃ³n.
     * @param user Usuario.
     * @param pass ContraseÃ±a.
     * @param workingDir Directorio de ftp donde se ubica el archivo a descagar.
     * @return Retorna los nombres de los archivos.
     * @throws GeneralException Si ocurre algÃºn error esta excepciÃ³n es
     * arrojada.
     */
    public static final List listarNombresArchivosFTP(final String host, final String port,
            final String user, final String pass, final String workingDir)
            throws Exception {

        List nombresArchivos = new ArrayList();
        try {
            FTPClient fTPClient = new FTPClient();
            fTPClient.connect(host, Integer.parseInt(port));
            fTPClient.login(user, pass);
            fTPClient.changeWorkingDirectory(workingDir);

            FTPFile[] files = fTPClient.listFiles(); // Obtiene los archivos del servidor y los mostramos

            for (FTPFile arch : files) {
		StringTokenizer t = new StringTokenizer(arch.toString());
		String ultimo="";
                //recorro e string completo hasta obtene el nombre
		while(t.hasMoreTokens()){
			String i = t.nextToken();
			ultimo=i;
		}
                //solo los comprimidos con zip solo agarramos... 
                if ("zip".equalsIgnoreCase(extencionArchivo(ultimo))){
                    nombresArchivos.add(ultimo);
                }
                
            }
            fTPClient.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.ftp");
        }
        return nombresArchivos;
    }


    /**
     * Dado los datos de conexiÃ³n sube el archivo especificado a travÃ©s de
     * protocolo ftp. Si el directorio de trabajo pasado por parametros no
     * existe lo crea.
     * @param host Nombre del Host FTP al cual se conectarÃ¡.
     * @param port Puerto de conecciÃ³n.
     * @param user Usuario.
     * @param pass ContraseÃ±a.
     * @param workingDir Directorio de ftp donde se ubica el archivo a descagar.
     * @param archivoStr Archivo que se intentarÃ  subir.
     * @return Retorna verdadero (true) si la opercion culmina con exito.
     * @throws GeneralException Si ocurre algÃºn error esta excepciÃ³n es
     * arrojada.
     */
    public static final Boolean cargaFTP(final String host,
            final String port, final String user, final String pass,
            final String workingDir, final File archivoASubir)
            throws Exception {
        Boolean exito = null;
        try {
            FTPClient fTPClient = new FTPClient();
            fTPClient.connect(host, Integer.parseInt(port));
            fTPClient.login(user, pass);
            if (!fTPClient.changeWorkingDirectory(workingDir)) {
                fTPClient.makeDirectory(workingDir);
                fTPClient.changeWorkingDirectory(workingDir);
            }
            FileInputStream archivoStream = new FileInputStream(archivoASubir);
            fTPClient.setFileType(FTP.BINARY_FILE_TYPE);
            exito = fTPClient.storeFile(archivoASubir.getName(),
                    archivoStream);
            fTPClient.disconnect();
            archivoStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.ftp");
        }
        return exito;
    }


     /**
     * Dado los datos de conexiÃ³n sube el archivo especificado a travÃ©s de
     * protocolo ftp. Si el directorio de trabajo pasado por parametros no
     * existe lo crea.
     * @param host Nombre del Host FTP al cual se conectarÃ¡.
     * @param port Puerto de conecciÃ³n.
     * @param user Usuario.
     * @param pass ContraseÃ±a.
     * @param workingDir Directorio de ftp donde se ubica el archivo a descagar.
     * @param archivoStr Archivo que se intentarÃ  subir.
     * @return Retorna verdadero (true) si la opercion culmina con exito.
     * @throws GeneralException Si ocurre algÃºn error esta excepciÃ³n es
     * arrojada.
     */
    public  final Boolean cargaFTPDinamico(final String host,
            final String port, final String user, final String pass,
            final String workingDir, final File archivoASubir)
            throws Exception {
        Boolean exito = null;
        try {
            FTPClient fTPClient = new FTPClient();
            fTPClient.connect(host, Integer.parseInt(port));
            fTPClient.login(user, pass);
            if (!fTPClient.changeWorkingDirectory(workingDir)) {
                fTPClient.makeDirectory(workingDir);
                fTPClient.changeWorkingDirectory(workingDir);
            }else{
                System.out.println("nopasaaa en ruta");
            }
            fTPClient.setFileType(FTP.BINARY_FILE_TYPE);
            exito = fTPClient.appendFile(archivoASubir.getName(),
                    new FileInputStream(archivoASubir));
            fTPClient.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.ftp");
        }
        return exito;
    }

    public static final String extencionArchivo(File archivo) {
        String extencionArchivo = null;

        String nombreArchivo = archivo.getName();
        extencionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(
                ".") + 1);

        return extencionArchivo;
    }
    
     public static final String extencionArchivo(String nombreArchivo) {
        String extencionArchivo = null;
        extencionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(
                ".") + 1);
        return extencionArchivo;
    }



  /*   public File   crearReporte(File archivoPlantilla,
            Object fnteImpresiOrConnec, Map<String, String> parametrosReporte,
            String extencionArchivo) {

         File archivoSalida=null;

        try {
            File fuenteImpresion = null;
            Connection conn = null;
            JasperPrint print = null;

            if (fnteImpresiOrConnec instanceof File) {

                fuenteImpresion = (File) fnteImpresiOrConnec;

                JRXmlDataSource fuenteDatos = new JRXmlDataSource(
                        fuenteImpresion, "/Cuentas/Documento");

                print = JasperFillManager.fillReport(
                        new FileInputStream(archivoPlantilla), parametrosReporte,
                        fuenteDatos);

            } else if (fnteImpresiOrConnec instanceof Connection) {

                conn = (Connection) fnteImpresiOrConnec;

                print = JasperFillManager.fillReport(
                        new FileInputStream(archivoPlantilla), parametrosReporte,
                        conn);

            }

            JRExporter exporter = null;
            String archivoNombre = "archivo_salida";


           // extencionArchivo = "pdf";

            exporter = new JRPdfExporter();

            archivoSalida = File.createTempFile(archivoNombre,
                    "." + extencionArchivo);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,
                    archivoSalida);
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }



        return archivoSalida;
    }*/


     public static final String nombreArchivo (String archivoCompleto) {
         String nombreArchivo = null;
         int ultimo = archivoCompleto.lastIndexOf("/") + 1;
         nombreArchivo = archivoCompleto.substring(ultimo);
         return nombreArchivo;
     }

     /**
      * Realzia la traduccion de los caracateres de un archivo de texto, segun
      * la cadena de caracteres originales y la cadena de caracteres de
      * traduccion.
      * @param archivoTraducir archivo al cual se le realizara la taduccion.
      * @param cadenaOriginales Cadena de caracteres a traducir.
      * @param cadenaTraduccion Cadena de caracteres de traduccion.
      * @return Retorna un archivo en la mismna ubicacion y con el mismo nombre
      * que el archivo original pero con la tradccion realizada.
      * @throws GeneralException Si ocurre algun error esta excepcion es
      * arrojada.
      */
     public static File traducirCodificacionArchivo (final File archivoTraducir,
             final String cadenaOriginales, final String cadenaTraduccion)
             throws Exception {

         OutputStream out = null;
         InputStream inputStream = null;
         InputStreamReader streamReader = null;
         BufferedReader br = null;
         try {
             String archivoStr = "";

             inputStream = new FileInputStream(archivoTraducir);
             streamReader = new InputStreamReader(inputStream,
                "UTF-8");
             br = new BufferedReader(streamReader);
 
             String linea = null;
             while ((linea = br.readLine()) != null) {
                 archivoStr += linea + "\n";
             }

             archivoStr = translate(archivoStr, "Ã Ã©ÂµÂ¥Â�Ã–", "Ã“ÃšÃ�Ã‘Ã‰Ã�");

             out = new FileOutputStream(archivoTraducir.getAbsolutePath());

             out.write(archivoStr.getBytes());
         } catch (FileNotFoundException ex) {
             throw new Exception("Error al abrir archivo a traducir");
         } catch (IOException ex) {
             throw new Exception("Error al escribir archivo a traducir");
         } finally {
             try {
                 if (out != null) {
                     out.close();
                 }
             } catch (IOException ex) {
                 throw new Exception("Error con archivo a traducir");
             }
         }
         return archivoTraducir;
    }
     
     public static String translate(final String texto, final String original,
             final String traduccion) throws Exception {
         String textoTraducido = texto;

         if (original.length() != traduccion.length()) {
             throw new Exception("La cantidad de caracteres originales y "
                     + "de traduccion deben ser iguales");
         }
         for(int i = 0; i < original.length(); i++) {
             textoTraducido = textoTraducido.replace(original.charAt(i),
                     traduccion.charAt(i));
         }

         return textoTraducido;
     }
     
     public String nombrExtensionFile(File item, boolean swExtension){
     	 String ext = item.getName().substring(item.getName().lastIndexOf(".") + 1, item.getName()
					.length());
         String name=item.getName().substring(0,item.getName().lastIndexOf(".") );
 
    	 if(swExtension){
            return ext; 		 
    	 }else{
    		 return name;	 
    	 }
         
     
    	 
     }
}
