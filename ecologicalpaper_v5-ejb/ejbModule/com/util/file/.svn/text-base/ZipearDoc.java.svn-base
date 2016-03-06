package com.util.file;

 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
public class ZipearDoc {

    /**
     * Constante entera que indica el tamaño en bytes de lectura para los
     * archivos.
     */
    private static final int NUM = 512;
    /**
     * Varitable entera que se utiliza en la escritura de archivos.
     */
    private static int len = 0;
    /**
     * Buffer compuesto de un arreglo de byte para la lectura y escritura de
     * archivos.
     */
    private static byte[] buffer = new byte[NUM];
    /**
     * Objecto utilizado para la escritura de archivos.
     */
    private static FileOutputStream out = null;
    static final int BUFFER = 2048;

    /**
     * Método que dado una lista (List) de archivos, los comprime en formato
     * ZIP y devuelve un Objeto File que representa el archivo comprimido.
     * @param arvhivosAComprimir Objecto List con los archivos que serán
     * comprimidos.
     * @return un Objecto File que representa el archivo comprimido.
     * @throws GeneralException Si no se encuentra alguno de los archivos de
     * la lista o no puede crear el temporal para el archivo ZIP esta Excepcion
     * es arrojada.
     */
    public   File comprimirArchivos(List<File> arvhivosAComprimir,String nombre)
            throws Exception {
        if (arvhivosAComprimir == null) {
            throw new Exception("error.desconocido");
        }
        File archivoZIP = null;
        FileInputStream in = null;
        try {
        	String tempdir = System.getProperty("java.io.tmpdir");

        	System.out.println("********************************");
        	System.out.println("tempdir="+tempdir);
        	System.out.println("********************************");
        	archivoZIP = new File(nombre+".zip");
        	System.out.println("archivoZIP.getName()="+archivoZIP.getName());
            //archivoZIP = File.createTempFile("ecological", ".zip");
           
            FileOutputStream outArchivoZIP = new FileOutputStream(archivoZIP);
            ZipOutputStream outZip = new ZipOutputStream(outArchivoZIP);
            HashMap unico = new HashMap<String,String>();
            for (File archivo : arvhivosAComprimir) {
            	if (!unico.containsValue(archivo.getPath().trim())){
                    in = new FileInputStream(archivo);
                    outZip.putNextEntry(new ZipEntry(archivo.getPath()));
                    while ((len = in.read(buffer)) > 0) {
                        outZip.write(buffer, 0, len);
                    }
                    outZip.closeEntry();
                    in.close();
            		unico.put(archivo.getPath().trim(), archivo.getPath().trim());
            		archivo.delete();
            	}
            }
            outZip.close();
            outArchivoZIP.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            archivoZIP = null;
            throw new Exception("error.core.compresion.desconocido");
        }finally{
        	 archivoZIP.deleteOnExit();
        }
       
        
        return archivoZIP;
    }
    
    /**
     * Método que dado una lista (List) de archivos, los comprime en formato
     * ZIP y devuelve un Objeto File que representa el archivo comprimido.
     * @param arvhivosAComprimir Objecto List con los archivos que serán
     * comprimidos.
     * @return un Objecto File que representa el archivo comprimido.
     * @throws GeneralException Si no se encuentra alguno de los archivos de
     * la lista o no puede crear el temporal para el archivo ZIP esta Excepcion
     * es arrojada.
     */
    public   File comprimirArchivos(List<File> arvhivosAComprimir)
            throws Exception {
        if (arvhivosAComprimir == null) {
            throw new Exception("error.desconocido");
        }
        File archivoZIP = null;
        FileInputStream in = null;
        try {
        	String tempdir = System.getProperty("java.io.tmpdir");

        	System.out.println("********************************");
        	System.out.println("tempdir="+tempdir);
        	System.out.println("********************************");
            archivoZIP = File.createTempFile("ecological", ".zip");
           
            FileOutputStream outArchivoZIP = new FileOutputStream(archivoZIP);
            ZipOutputStream outZip = new ZipOutputStream(outArchivoZIP);
            HashMap unico = new HashMap<String,String>();
            for (File archivo : arvhivosAComprimir) {
            	if (!unico.containsValue(archivo.getPath().trim())){
                    in = new FileInputStream(archivo);
                    outZip.putNextEntry(new ZipEntry(archivo.getPath()));
                    while ((len = in.read(buffer)) > 0) {
                        outZip.write(buffer, 0, len);
                    }
                    outZip.closeEntry();
                    in.close();
            		unico.put(archivo.getPath().trim(), archivo.getPath().trim());
            		archivo.delete();
            	}
            }
            outZip.close();
            outArchivoZIP.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            archivoZIP = null;
            throw new Exception("error.core.compresion.desconocido");
        }finally{
        	 archivoZIP.deleteOnExit();
        }
        
        return archivoZIP;
    }

    /**
     * Método que dado un archivo, lo comprime en formato
     * ZIP y devuelve un Objeto File que representa el archivo comprimido.
     * @param arvhivosAComprimir Objecto List con los archivos que serán
     * comprimidos.
     * @return un Objecto File que representa el archivo comprimido.
     * @throws Exception Si no se encuentra alguno de los archivos de
     * la lista o no puede crear el temporal para el archivo ZIP esta Excepcion
     * es arrojada.
     */
    public   File comprimirArchivos(File archivoAComprimir)
            throws Exception {
        if (archivoAComprimir == null) {
            throw new Exception("error.desconocido");
        }
        File archivoZIP = null;
        FileInputStream in = null;

        try {
            archivoZIP = File.createTempFile("binaria_uno_", ".zip");
            archivoZIP.deleteOnExit();
            FileOutputStream outArchivoZIP = new FileOutputStream(archivoZIP);
            ZipOutputStream outZip = new ZipOutputStream(outArchivoZIP);
            in = new FileInputStream(archivoAComprimir);
            outZip.putNextEntry(new ZipEntry(archivoAComprimir.getPath()));
            while ((len = in.read(buffer)) > 0) {
                outZip.write(buffer, 0, len);
            }
            in.close();
            outZip.closeEntry();
            outZip.close();
            outArchivoZIP.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            archivoZIP = null;
            throw new Exception("error.core.compresion.desconocido");
        }
        return archivoZIP;
    }

    /**
     * Descomprime un archivo comprimido en fortmato ZIP.
     * @param entrada objeto Inpustream que representa el archivo comprimido
     * en formato ZIP.
     * @return un objecto List con los archivos descomprimidos del ZIP.
     */
    public     List<File> descomprimirZip (File archivoEntrada)
            throws Exception {
        List<File> archivos = null;
        InputStream entrada = null;
        try {
            if (archivoEntrada == null) {
            	System.out.println("error.core.compresion.desconocido");
                throw new Exception("error.core.compresion.desconocido");
            }
             
            entrada = new FileInputStream(archivoEntrada);
            ZipInputStream inZip = new ZipInputStream(entrada);
            ZipEntry archivoComprimido = null;
            try {
                archivos = new ArrayList<File>();
                archivoComprimido = inZip.getNextEntry();
                if ((archivoComprimido = inZip.getNextEntry()) == null) {
                	System.out.println("error.core.compresion.archivo_"
                            + "incorrecto_o_vacio");
                    throw new Exception("error.core.compresion.archivo_"
                            + "incorrecto_o_vacio");
                }
                do {
                    if (!archivoComprimido.isDirectory()) {
                        File archivoDescomprimido = File.createTempFile(
                                Archivo.nombreArchivo(archivoComprimido
                                .getName().replace("\\", "/")), "."
                                + Archivo.extencionArchivo(new File(
                                archivoComprimido.getName())));
                        out = new FileOutputStream(archivoDescomprimido);
                        while ((len = inZip.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                        out.close();
                        archivos.add(archivoDescomprimido);
                    }
                } while ((archivoComprimido = inZip.getNextEntry()) != null);
                inZip.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                archivos = null;
            }
            entrada.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.core.compresion.problema"
                    + "_archivo");
        }
        return archivos;
    }

    
    public     List<File> unZip2 (File archivoEntrada)
    throws Exception {
    	 
        List<File> archivos = new ArrayList<File>();
        try {
            BufferedOutputStream dest = null;
            BufferedInputStream is = null;
            ZipEntry entry;
            ZipFile zipfile = new ZipFile(archivoEntrada);
            Enumeration e = zipfile.entries();
            while(e.hasMoreElements()) {
                entry = (ZipEntry) e.nextElement();
          
                File archivoDescomprimido = new File(entry.getName());
            	 /*File archivoDescomprimido = File.createTempFile(
                         Archivo.nombreArchivo(entry.getName()
                        		 .replace("\\", "/")), "."
                         + Archivo.extencionArchivo(new File(
                        		 entry.getName())));*/
            	

               System.out.println("Extracting: " +entry);
               is = new BufferedInputStream
                 (zipfile.getInputStream(entry));
               int count;
               byte data[] = new byte[BUFFER];
              
               FileOutputStream fos = new 
                 FileOutputStream(archivoDescomprimido);
               dest = new 
                 BufferedOutputStream(fos, BUFFER);
               while ((count = is.read(data, 0, BUFFER)) 
                 != -1) {
                  dest.write(data, 0, count);
               }
               dest.flush();
               dest.close();
               is.close();
               
               archivos.add(archivoDescomprimido);
              
            }
         } catch(Exception e) {
            e.printStackTrace();
         }
        
        return archivos;
    }


    /**
     * Descomprime un archivo comprimido en fortmato ZIP.
     * @param entrada objeto Inpustream que representa el archivo comprimido
     * en formato ZIP.
     * @return un objecto List con los archivos descomprimidos del ZIP.
     */
    public  List<File> descomprimirZipDinamico (File archivoEntrada)
            throws Exception {
        List<File> archivos = null;
        InputStream entrada = null;
        try {
            if (archivoEntrada == null) {
                throw new Exception("error.core.compresion.desconocido");
            }
            entrada = new FileInputStream(archivoEntrada);
            ZipInputStream inZip = new ZipInputStream(entrada);
            ZipEntry archivoComprimido = null;
            try {
                archivos = new ArrayList<File>();
                if ((archivoComprimido = inZip.getNextEntry()) == null) {
                    throw new Exception("error.core.compresion.archivo_"
                            + "incorrecto_o_vacio");
                }
                do {
                    if (!archivoComprimido.isDirectory()) {
                        File archivoDescomprimido =  File.createTempFile(
                                archivoComprimido.getName().substring(
                                archivoComprimido.getName().lastIndexOf(
                                File.separator) + 1), "." + Archivo
                                .extencionArchivo(new File(archivoComprimido
                                .getName())));
                        out = new FileOutputStream(archivoDescomprimido);
                        while ((len = inZip.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                        out.close();
                        archivos.add(archivoDescomprimido);
                    }
                } while ((archivoComprimido = inZip.getNextEntry()) != null);
                inZip.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                archivos = null;
            }
            entrada.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.core.compresion.problema"
                    + "_archivo");
        }
        return archivos;
    }
}