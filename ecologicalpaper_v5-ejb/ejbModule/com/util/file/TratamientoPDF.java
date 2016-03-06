package com.util.file;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
public class TratamientoPDF {
    public static final int IZQUIERDO = 1;
    public static final int DERECHO = 2;
    public static final int SUPERIOR = 3;
    public static final int INFERIOR = 4;
    public static final int VERTICAL = 5;
    public static final int HORIZONTAL = 6;

    /**
     * Compagina un documento pdf en formato de libro para impresión duplex.
     * @param archivoPDF Archivo PDF original a ser compaginado.
     * @param paginaBlanca Indica si el documento lleva pagínas en blanco.
     * El método supone que el documento pdf siempre tiene solo una pagina en
     * blanco "cantPaginasFinales" antes del fin del doucmento.
     * @param cantPaginasIniciales Total de paginas iniciales fijas.
     * El método supondra que las "cantPaginasIniciales" de paginas iniciales
     * son fijas.
     * @param cantPaginasFinales Total de paginas finales fijas.
     * El método supondra que las "cantPaginasFinales" de paginas finales son
     * fijas.
     * @return Retona un arvhivo File que representa el archivo compaginado.
     * @throws GeneralException Si ocurre algún error se arroja esta
     * excepción.
     * @deprecated utilizar en su lugar compaginarPDFLibroVariosDocs1.
     */
    public static final File compaginarPDFLibro(final File archivoPDF,
            final boolean paginaBlanca, final int cantPaginasIniciales,
            final int cantPaginasFinales)
            throws Exception {

        File pdfCompaginado = null;
        File pdfCompleto = null;
        File pdfPaginado = null;

        PdfReader reader = null;
        Document document = null;
        PdfCopy copy = null;
        PdfImportedPage page = null;
        try {
            pdfCompleto = File.createTempFile("pdfcompleto", ".pdf");
            pdfPaginado = File.createTempFile("pdfpaginado", ".pdf");
            pdfCompaginado = File.createTempFile("pdfcompaginado", ".pdf");

            pdfCompleto.deleteOnExit();
            pdfPaginado.deleteOnExit();

            reader = new PdfReader(new FileInputStream(archivoPDF));
            document = new Document(reader.getPageSizeWithRotation(1));
            copy = new PdfCopy(document, new FileOutputStream(
                    pdfCompleto));
            document.open();
            int paginas = reader.getNumberOfPages();

            int ultimaPagDoc = paginas;
            int auxLast = ultimaPagDoc;
            int auxFirst = 1;

            int auxPaginaBlanca = 0;
            if(paginaBlanca) {
                auxPaginaBlanca = auxLast - cantPaginasFinales;
            }

            int cantDetalles = paginas - cantPaginasIniciales
                    - cantPaginasFinales - 1;
            int paginasImprimir = proxMultiplo(4, cantDetalles
                    + cantPaginasFinales + cantPaginasIniciales);
            int cantBanca = paginasImprimir - (cantDetalles
                    + cantPaginasFinales + cantPaginasIniciales);

            //AGREGANDO PRIMERA PAGINA
            page = copy.getImportedPage(reader, auxFirst);
            copy.addPage(page);
            //AGREGANDO DETALLE
            int paginaActual = 2;
            for(int i = 0; i < cantDetalles; i++) {
                page = copy.getImportedPage(reader, i + paginaActual);
                copy.addPage(page);
            }
            //AGREGANDO PAGINAS EN BLANCO
            for(int i = 0; i < cantBanca; i++) {
                page = copy.getImportedPage(reader, auxPaginaBlanca);
                copy.addPage(page);
            }
            //AGREGANDO ULTIMA PAGINA
            if (cantPaginasFinales > 0) {
                paginaActual = 1 + cantDetalles + cantBanca;
                page = copy.getImportedPage(reader, auxLast);
                copy.addPage(page);
            }

            document.close();

            //--------------------------------------------PAGINANDO EL DOCUMENTO
            reader = new PdfReader(new FileInputStream(pdfCompleto));
            document = new Document(reader.getPageSizeWithRotation(1));
            copy = new PdfCopy(document, new FileOutputStream(
                    pdfPaginado));
            document.open();

            paginas = reader.getNumberOfPages();

            ultimaPagDoc = paginas;
            auxLast = ultimaPagDoc;
            auxFirst = 1;

            paginaActual = 1;
            while (auxLast >= auxFirst) {
                if (auxLast - auxFirst == 1) {
                    page = copy.getImportedPage(reader, auxFirst);
                    copy.addPage(page);
                    page = copy.getImportedPage(reader, auxLast);
                    copy.addPage(page);
                } else {
                    if (paginaActual % 2 == 0) {
                        page = copy.getImportedPage(reader, auxFirst);
                        copy.addPage(page);
                        page = copy.getImportedPage(reader, auxLast);
                        copy.addPage(page);
                    } else {
                        page = copy.getImportedPage(reader, auxLast);
                        copy.addPage(page);
                        page = copy.getImportedPage(reader, auxFirst);
                        copy.addPage(page);
                    }
                }
                auxLast--;
                auxFirst++;
                paginaActual++;
            }
            document.close();

            //------------------------------COMPAGINANDO EL DOCUMENTO DEFINITIVO
            reader = new PdfReader(new FileInputStream(pdfPaginado));
            paginas = reader.getNumberOfPages();

            Rectangle psize = reader.getPageSize(1);
            float width = psize.getWidth() * 2;
            float height = psize.getHeight();

            document = new Document(new Rectangle(width, height));
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(pdfCompaginado));

            document.open();

            PdfContentByte cb = writer.getDirectContent();
            int i = 0;
            int p = 0;

            while (i < paginas) {
                document.newPage();
                p++;
                i++;
                PdfImportedPage page1 = writer.getImportedPage(reader, i);
                cb.addTemplate(page1, 1f, 0, 0, 1f, 0, 0);
                if (i < paginas) {
                    i++;
                    PdfImportedPage page2 = writer.getImportedPage(reader, i);
                    cb.addTemplate(page2, 1f, 0, 0, 1f, width / 2 + 0, 0);
                }
            }
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.compaginacion_pdf");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.compaginacion_pdf");
        }
        return pdfCompaginado;
    }

    /**
     * Compagina un documento pdf en formato de libro para impresión duplex.
     * Para realziar esta tarea el metodo requiere que los documentos posean
     * marcadores o bookmarks para identificar el inicio de cada pagina.
     * @param archivoPDF Archivo PDF original a ser compaginado.
     * @param paginaBlanca Indica si el documento lleva pagínas en blanco.
     * El método supone que el documento pdf siempre tiene solo una pagina en
     * blanco "cantPaginasFinales" antes del fin del doucmento.
     * @param cantPaginasIniciales Total de paginas iniciales fijas.
     * El método supondra que las "cantPaginasIniciales" de paginas iniciales
     * son fijas.
     * @param cantPaginasFinales Total de paginas finales fijas.
     * El método supondra que las "cantPaginasFinales" de paginas finales son
     * fijas.
     * @return Retona un arvhivo File que representa el archivo compaginado.
     * @throws Exception Si ocurre algún error se arroja esta
     * excepción.
     */
    public static final File compaginarPDFLibroVariosDocs1(
            final File archivoPDF, final boolean llevaPaginaBlanca,
            final int cantPaginasIniciales, final int cantPaginasFinales,
            final boolean imprimeNumPagina, final float tamanoLetraNumPagina,
            final boolean enPrimeraNumPagina, final boolean enUltimaNumPagina)
            throws Exception {

        File pdfCompaginado = null;
        File pdfTodo = null;

        PdfReader readerOriginal = null;
        Document document = null;
        PdfCopy copy = null;
        PdfImportedPage page = null;
        List<File> archivosPegar = new ArrayList();
        try {
            pdfTodo = File.createTempFile("compaginado_", ".pdf");

            readerOriginal = new PdfReader(new FileInputStream(archivoPDF));

            List bookmarksLst = SimpleBookmark.getBookmark(readerOriginal);
            Object[] bookmarksMap = bookmarksLst.toArray();
            for (int x = 0; x < bookmarksMap.length; x++) {
                if (x % 500 == 0) {
                    System.out.println("PASADA: " + x);
                }
                pdfCompaginado = File.createTempFile("pdfcompaginado_", ".pdf");

                Map mapa = (Map) bookmarksMap[x];
                StringTokenizer tokenizer = new StringTokenizer((String)
                        mapa.get("Page"), " ");
                int inicioDocumento = Integer.parseInt(tokenizer.nextToken());
                int finDocumento = -1;
                if ((x + 1) == bookmarksMap.length) {
                    finDocumento = readerOriginal.getNumberOfPages();
                } else {
                    Map mapaNxt = (Map) bookmarksMap[x + 1];
                    tokenizer = new StringTokenizer((String) mapaNxt.get(
                            "Page"), " ");
                    finDocumento = Integer.parseInt(tokenizer.nextToken()) - 1;
                }

                String paginaActualStr = null;
                StringTokenizer tokenizerPagActual = null;
                Integer yPagActual = null;
                Integer xPagActual = null;
                if (imprimeNumPagina) {
                    paginaActualStr = (String) ((Map) ((Map) ((ArrayList)
                            mapa.get("Kids")).get(0))).get("Page");
                    tokenizerPagActual = new StringTokenizer(
                            paginaActualStr, " ");

                    tokenizerPagActual.nextToken();
                    tokenizerPagActual.nextToken();
                    xPagActual = Integer.parseInt(tokenizerPagActual
                            .nextToken());
                    yPagActual = Integer.parseInt(tokenizerPagActual
                            .nextToken());

                }


                int paginas = finDocumento - inicioDocumento + 1;

                int ultimaPagDoc = finDocumento;
                int auxLast = finDocumento;
                int auxFirst = inicioDocumento;

                int paginaBlanca = 0;
                int auxPag = 0;
                if(llevaPaginaBlanca) {
                    paginaBlanca = auxLast - cantPaginasFinales;
                    auxPag = 1;
                }

                int cantDetalles = paginas - cantPaginasIniciales -
                        cantPaginasFinales - auxPag;
                int paginasImprimir = proxMultiplo(4, cantDetalles +
                        cantPaginasIniciales + cantPaginasFinales);
                int cantBanca = paginasImprimir - (cantDetalles +
                        cantPaginasIniciales + cantPaginasFinales);

                int[] arregloCompleto = new int[paginasImprimir];

                //AGREGANDO PRIMERA PAGINA
                for(int i = 0; i < cantPaginasIniciales; i++) {
                    arregloCompleto[i] = i + inicioDocumento;
                }
                //AGREGANDO DETALLE
                int paginaActual = auxFirst + cantPaginasIniciales;
                int posicion = cantPaginasIniciales + 1;
                for(int i = 0; i < cantDetalles; i++) {
                    arregloCompleto[i + posicion - 1] = paginaActual;
                    paginaActual++;
                }
                //AGREGANDO PAGINAS EN BLANCO
                posicion = cantPaginasIniciales + cantDetalles + 1;
                for(int i = 0; i < cantBanca; i++) {
                    arregloCompleto[i + posicion - 1] = paginaBlanca;
                    paginaActual++;
                }
                //AGREGANDO ULTIMA PAGINA
                posicion = cantPaginasIniciales + cantDetalles + cantBanca + 1;
                for(int i = 0; i < cantPaginasFinales; i++) {
                    arregloCompleto[i + posicion - 1] = paginaBlanca + i + 1;
                    paginaActual++;
                }

                //----------------------------------------PAGINANDO EL DOCUMENTO
                auxLast = paginasImprimir - 1;
                auxFirst = 0;
                int[] arregloPaginado = new int[paginasImprimir];
                Integer[] arregloPaginas = new Integer[paginasImprimir];
                paginaActual = 1;
                int paginasAux = 0;
                while (auxLast >= auxFirst) {
                    if (auxLast - auxFirst == 1) {
                        arregloPaginado[paginasAux] = arregloCompleto[auxFirst];
                        arregloPaginas[paginasAux] = auxFirst + 1;
                        paginasAux++;
                        arregloPaginado[paginasAux] = arregloCompleto[auxLast];
                        arregloPaginas[paginasAux] = auxLast + 1;
                        paginasAux++;
                    } else {
                        if (paginaActual % 2 == 0) {
                            arregloPaginado[paginasAux] = arregloCompleto[
                                    auxFirst];
                            arregloPaginas[paginasAux] = auxFirst + 1;
                            paginasAux++;
                            arregloPaginado[paginasAux] = arregloCompleto[
                                    auxLast];
                            arregloPaginas[paginasAux] = auxLast + 1;
                            paginasAux++;
                        } else {
                            arregloPaginado[paginasAux] = arregloCompleto[
                                    auxLast];
                            arregloPaginas[paginasAux] = auxLast + 1;
                            paginasAux++;
                            arregloPaginado[paginasAux] = arregloCompleto[
                                    auxFirst];
                            arregloPaginas[paginasAux] = auxFirst + 1;
                            paginasAux++;
                        }
                    }
                    auxLast--;
                    auxFirst++;
                    paginaActual++;
                }

                //--------------------------COMPAGINANDO EL DOCUMENTO DEFINITIVO
                Rectangle psize = readerOriginal.getPageSize(1);
                float width = psize.getWidth() * 2;
                float height = psize.getHeight();

                document = new Document(new Rectangle(width, height));
                PdfWriter writer = PdfWriter.getInstance(document,
                        new FileOutputStream(pdfCompaginado));

                document.open();

                PdfContentByte cb = writer.getDirectContent();
                for (int i = 0; i < arregloPaginado.length; i++) {
                    if ((i + 1) % 2 != 0) {
                        document.newPage();
                        page = writer.getImportedPage(
                                readerOriginal, arregloPaginado[i]);
                        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                        if (imprimeNumPagina && (enPrimeraNumPagina
                                || arregloPaginas[i] != 1)
                                && (enUltimaNumPagina || arregloPaginas[i]
                                != paginasImprimir)) {
                            cb.beginText();
                            cb.setFontAndSize(BaseFont.createFont(BaseFont
                                    .HELVETICA, BaseFont.WINANSI, BaseFont
                                    .NOT_EMBEDDED) , 6F);
                            cb.moveText(xPagActual, yPagActual);
                            cb.showText(Integer.toString(arregloPaginas[i])
                                    + " de " +  paginasImprimir);
                            cb.endText();
                        }
                    } else {
                        page = writer.getImportedPage(
                                readerOriginal, arregloPaginado[i]);
                        cb.addTemplate(page, 1f, 0, 0, 1f, width / 2 + 0, 0);
                        if (imprimeNumPagina && (enPrimeraNumPagina
                                || arregloPaginas[i] != 1)
                                && (enUltimaNumPagina || arregloPaginas[i]
                                != paginasImprimir)) {

                            cb.beginText();
                            cb.setFontAndSize(BaseFont.createFont(BaseFont
                                    .HELVETICA, BaseFont.WINANSI, BaseFont
                                    .NOT_EMBEDDED) , 6F);
                            cb.moveText(((width / 2) + xPagActual), yPagActual);
                            cb.showText(Integer.toString(arregloPaginas[i])
                                    + " de " +  paginasImprimir);
                            cb.endText();
                        }
                    }
                }
                document.close();

                //-----------------------------------PEGANDO EL ARCHIVO COMPLETO
                archivosPegar.add(pdfCompaginado);
            }
            pdfTodo = pegarArchivosPDF(archivosPegar, "pdfTodo");
            for (File file : archivosPegar) {
                file.delete();
            }
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.compaginacion_pdf");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.compaginacion_pdf");
        }
        return pdfTodo;
    }

    /**
     * Compagina un documento pdf en formato de libro para impresión duplex.
     * @param archivoPDF Archivo PDF original a ser compaginado.
     * @param paginaBlanca Indica si el documento lleva pagínas en blanco.
     * El método supone que el documento pdf siempre tiene solo una pagina en
     * blanco "cantPaginasFinales" antes del fin del doucmento.
     * @param cantPaginasIniciales Total de paginas iniciales fijas.
     * El método supondra que las "cantPaginasIniciales" de paginas iniciales
     * son fijas.
     * @param cantPaginasFinales Total de paginas finales fijas.
     * El método supondra que las "cantPaginasFinales" de paginas finales son
     * fijas.
     * @return Retona un arvhivo File que representa el archivo compaginado.
     * @throws Exception Si ocurre algún error se arroja esta
     * excepción.
     * @deprecated Usar en su lugar compaginarPDFLibroVariosDocs1.
     */
    public static final File compaginarPDFLibroVariosDocs(final File archivoPDF,
            final boolean paginaBlanca, final int cantPaginasIniciales,
            final int cantPaginasFinales)
            throws Exception {

        File pdfCompaginado = null;
        File pdfCompleto = null;
        File pdfPaginado = null;
        File pdfTodo = null;

        PdfReader reader = null;
        PdfReader readerOriginal = null;
        Document document = null;
        Document documentOriginal = null;
        PdfCopy copy = null;
        PdfImportedPage page = null;
        PdfCopy copyTodo = null;
        List<File> archivosPegar = new ArrayList();
        try {
            System.out.println("EMPEZANDO COMPAGINADO");
            pdfTodo = File.createTempFile("compaginado_", ".pdf");

            readerOriginal = new PdfReader(new FileInputStream(archivoPDF));

            List bookmarksLst = SimpleBookmark.getBookmark(readerOriginal);
            Object[] bookmarksMap = bookmarksLst.toArray();
            for (int x = 0; x < bookmarksMap.length; x++) {
                if (x % 500 == 0) {
                    System.out.println("PASADA: " + x);
                }
                pdfCompleto = File.createTempFile("pdfcompleto", ".pdf");
                pdfPaginado = File.createTempFile("pdfpaginado", ".pdf");
                pdfCompaginado = File.createTempFile("pdfcompaginado", ".pdf");

                documentOriginal = new Document(readerOriginal
                        .getPageSizeWithRotation(1));
                copy = new PdfCopy(documentOriginal,
                        new FileOutputStream(pdfCompleto));
                documentOriginal.open();

                Map mapa = (Map) bookmarksMap[x];
                StringTokenizer tokenizer = new StringTokenizer((String)
                        mapa.get("Page"), " ");
                int inicioDocumento = Integer.parseInt(tokenizer.nextToken());
                int finDocumento = -1;
                if ((x + 1) == bookmarksMap.length) {
                    finDocumento = readerOriginal.getNumberOfPages();
                } else {
                    Map mapaNxt = (Map) bookmarksMap[x + 1];
                    tokenizer = new StringTokenizer((String) mapaNxt.get(
                            "Page"), " ");
                    finDocumento = Integer.parseInt(tokenizer.nextToken()) - 1;
                }

                int paginas = finDocumento - inicioDocumento + 1;

                int ultimaPagDoc = finDocumento;
                int auxLast = finDocumento;
                int auxFirst = inicioDocumento;

                int auxPaginaBlanca = 0;
                int auxPag = 0;
                if(paginaBlanca) {
                    auxPaginaBlanca = auxLast - cantPaginasFinales;
                    auxPag = 1;
                }

                int cantDetalles = paginas - cantPaginasIniciales -
                        cantPaginasFinales - auxPag;
                int paginasImprimir = proxMultiplo(4, cantDetalles +
                        cantPaginasIniciales + cantPaginasFinales);
                int cantBanca = paginasImprimir - (cantDetalles +
                        cantPaginasIniciales + cantPaginasFinales);

                //AGREGANDO PRIMERA PAGINA
                page = copy.getImportedPage(readerOriginal, auxFirst);
                copy.addPage(page);
                //AGREGANDO DETALLE
                int paginaActual = auxFirst + cantPaginasIniciales;
                for(int i = 0; i < cantDetalles; i++) {
                    page = copy.getImportedPage(readerOriginal, i
                            + paginaActual);
                    copy.addPage(page);
                }
                //AGREGANDO PAGINAS EN BLANCO
                for(int i = 0; i < cantBanca; i++) {
                    page = copy.getImportedPage(readerOriginal,
                            auxPaginaBlanca);
                    copy.addPage(page);
                }
                //AGREGANDO ULTIMA PAGINA
                if (cantPaginasFinales > 0) {
                    paginaActual = 1 + cantDetalles + cantBanca;
                    page = copy.getImportedPage(readerOriginal, auxLast);
                    copy.addPage(page);
                }

                documentOriginal.close();
                //----------------------------------------PAGINANDO EL DOCUMENTO
                reader = new PdfReader(new FileInputStream(pdfCompleto));
                document = new Document(reader.getPageSizeWithRotation(1));
                copy = new PdfCopy(document, new FileOutputStream(
                        pdfPaginado));
                document.open();

                paginas = reader.getNumberOfPages();

                ultimaPagDoc = paginas;
                auxLast = ultimaPagDoc;
                auxFirst = 1;

                paginaActual = 1;
                while (auxLast >= auxFirst) {
                    if (auxLast - auxFirst == 1) {
                        page = copy.getImportedPage(reader, auxFirst);
                        copy.addPage(page);
                        page = copy.getImportedPage(reader, auxLast);
                        copy.addPage(page);
                    } else {
                        if (paginaActual % 2 == 0) {
                            page = copy.getImportedPage(reader, auxFirst);
                            copy.addPage(page);
                            page = copy.getImportedPage(reader, auxLast);
                            copy.addPage(page);
                        } else {
                            page = copy.getImportedPage(reader, auxLast);
                            copy.addPage(page);
                            page = copy.getImportedPage(reader, auxFirst);
                            copy.addPage(page);
                        }
                    }
                    auxLast--;
                    auxFirst++;
                    paginaActual++;
                }

                document.close();

                //--------------------------COMPAGINANDO EL DOCUMENTO DEFINITIVO
                reader = new PdfReader(new FileInputStream(pdfPaginado));
                paginas = reader.getNumberOfPages();

                Rectangle psize = reader.getPageSize(1);
                float width = psize.getWidth() * 2;
                float height = psize.getHeight();

                document = new Document(new Rectangle(width, height));
                PdfWriter writer = PdfWriter.getInstance(document,
                        new FileOutputStream(pdfCompaginado));

                document.open();

                PdfContentByte cb = writer.getDirectContent();
                int i = 0;
                int p = 0;

                while (i < paginas) {
                    document.newPage();
                    p++;
                    i++;
                    PdfImportedPage page1 = writer.getImportedPage(reader, i);
                    cb.addTemplate(page1, 1f, 0, 0, 1f, 0, 0);
                    if (i < paginas) {
                        i++;
                        PdfImportedPage page2 = writer.getImportedPage(reader,
                                i);
                        cb.addTemplate(page2, 1f, 0, 0, 1f, width / 2 + 0, 0);
                    }
                }

                document.close();

                //-----------------------------------PEGANDO EL ARCHIVO COMPLETO
                archivosPegar.add(pdfCompaginado);
            }
            pdfTodo = pegarArchivosPDF(archivosPegar, "pdfTodo");
            pdfCompaginado.delete();
            pdfCompleto.delete();
            pdfPaginado.delete();
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.compaginacion_pdf");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.compaginacion_pdf");
        }
        return pdfTodo;
    }



    /**
     * Compagina un documento pdf en formato de libro para impresión duplex.
     * @param archivoPDF Archivo PDF original a ser compaginado.
     * @param paginaBlanca Indica si el documento lleva pagínas en blanco.
     * El método supone que el documento pdf siempre tiene solo una pagina en
     * blanco "cantPaginasFinales" antes del fin del doucmento.
     * @param cantPaginasIniciales Total de paginas iniciales fijas.
     * El método supondra que las "cantPaginasIniciales" de paginas iniciales
     * son fijas.
     * @param cantPaginasFinales Total de paginas finales fijas.
     * El método supondra que las "cantPaginasFinales" de paginas finales son
     * fijas.
     * @return Retona un arvhivo File que representa el archivo compaginado.
     * @throws Exception Si ocurre algún error se arroja esta
     * excepción.
     */
    public  final File compaginarPDFLibroDinamico(final File archivoPDF,
            final boolean paginaBlanca, final int cantPaginasIniciales,
            final int cantPaginasFinales)
            throws Exception {

        File pdfCompaginado = null;
        File pdfCompleto = null;
        File pdfPaginado = null;

        PdfReader reader = null;
        Document document = null;
        PdfCopy copy = null;
        PdfImportedPage page = null;
        try {
            pdfCompleto = File.createTempFile("pdfcompleto", ".pdf");
            pdfPaginado = File.createTempFile("pdfpaginado", ".pdf");
            pdfCompaginado = File.createTempFile("pdfcompaginado", ".pdf");

            pdfCompleto.deleteOnExit();
            pdfPaginado.deleteOnExit();

            reader = new PdfReader(new FileInputStream(archivoPDF));
            document = new Document(reader.getPageSizeWithRotation(1));
            copy = new PdfCopy(document, new FileOutputStream(
                    pdfCompleto));
            document.open();
            int paginas = reader.getNumberOfPages();

            int ultimaPagDoc = paginas;
            int auxLast = ultimaPagDoc;
            int auxFirst = 1;

            int auxPaginaBlanca = 0;
            if(paginaBlanca) {
                auxPaginaBlanca = auxLast - cantPaginasFinales;
            }

            int cantDetalles = paginas - 2 - cantPaginasFinales;
            int paginasImprimir = proxMultiplo(4, cantDetalles + 2);
            int cantBanca = paginasImprimir - (cantDetalles + 2);

            //AGREGANDO PRIMERA PAGINA
            page = copy.getImportedPage(reader, auxFirst);
            copy.addPage(page);
            //AGREGANDO DETALLE
            int paginaActual = 2;
            for(int i = 0; i < cantDetalles; i++) {
                page = copy.getImportedPage(reader, i + paginaActual);
                copy.addPage(page);
            }
            //AGREGANDO PAGINAS EN BLANCO
            for(int i = 0; i < cantBanca; i++) {
                page = copy.getImportedPage(reader, auxPaginaBlanca);
                copy.addPage(page);
            }
            //AGREGANDO ULTIMA PAGINA
            paginaActual = 1 + cantDetalles + cantBanca;
            page = copy.getImportedPage(reader, auxLast);
            copy.addPage(page);

            document.close();

            //--------------------------------------------PAGINANDO EL DOCUMENTO
            reader = new PdfReader(new FileInputStream(pdfCompleto));
            document = new Document(reader.getPageSizeWithRotation(1));
            copy = new PdfCopy(document, new FileOutputStream(
                    pdfPaginado));
            document.open();

            paginas = reader.getNumberOfPages();

            ultimaPagDoc = paginas;
            auxLast = ultimaPagDoc;
            auxFirst = 1;

            paginaActual = 1;
            while (auxLast >= auxFirst) {
                if (auxLast - auxFirst == 1) {
                    page = copy.getImportedPage(reader, auxFirst);
                    copy.addPage(page);
                    page = copy.getImportedPage(reader, auxLast);
                    copy.addPage(page);
                } else {
                    if (paginaActual % 2 == 0) {
                        page = copy.getImportedPage(reader, auxFirst);
                        copy.addPage(page);
                        page = copy.getImportedPage(reader, auxLast);
                        copy.addPage(page);
                    } else {
                        page = copy.getImportedPage(reader, auxLast);
                        copy.addPage(page);
                        page = copy.getImportedPage(reader, auxFirst);
                        copy.addPage(page);
                    }
                }
                auxLast--;
                auxFirst++;
                paginaActual++;
            }
            document.close();

            //------------------------------COMPAGINANDO EL DOCUMENTO DEFINITIVO
            reader = new PdfReader(new FileInputStream(pdfPaginado));
            paginas = reader.getNumberOfPages();

            Rectangle psize = reader.getPageSize(1);
            float width = psize.getWidth() * 2;
            float height = psize.getHeight();

            document = new Document(new Rectangle(width, height));
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(pdfCompaginado));

            document.open();

            PdfContentByte cb = writer.getDirectContent();
            int i = 0;
            int p = 0;

            while (i < paginas) {
                document.newPage();
                p++;
                i++;
                PdfImportedPage page1 = writer.getImportedPage(reader, i);
                cb.addTemplate(page1, 1f, 0, 0, 1f, 0, 0);
                if (i < paginas) {
                    i++;
                    PdfImportedPage page2 = writer.getImportedPage(reader, i);
                    cb.addTemplate(page2, 1f, 0, 0, 1f, width / 2 + 0, 0);
                }
            }
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.compaginacion_pdf");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.compaginacion_pdf");
        }
        return pdfCompaginado;
    }

    private static int proxMultiplo (int multiplo, int numero) {
        int auxNum = numero;
        while (auxNum % multiplo != 0) {
            auxNum++;
        }
        return auxNum;
    }

    public static File pegarArchivosPDF (List<File> archivosPDF,
            final String prefijo)
            throws Exception {
        File archivoPDF = null;
        try {
            archivoPDF = File.createTempFile(prefijo + "_", ".pdf");

            PdfReader reader = new PdfReader(new FileInputStream((File)
                    archivosPDF.get(0)));
            Document document = new Document(reader.getPageSizeWithRotation(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(
                    archivoPDF));
            PdfImportedPage page = null;

            document.open();
            for (File file : archivosPDF) {
                reader = new PdfReader(new FileInputStream(file));
                int paginas = reader.getNumberOfPages();
                for(int i = 1; i <= paginas; i++) {
                    page = copy.getImportedPage(reader, i);
                    copy.addPage(page);
//                    if (i == 22) {
//                        System.out.println("terminando");
//                        break;
//                    }
                }
            }
//            for (int x = archivosPDF.size() - 1; x >= 0; x--) {
//                File file = archivosPDF.get(x);
//                reader = new PdfReader(new FileInputStream(file));
//                int paginas = reader.getNumberOfPages();
//                for(int i = 1; i <= paginas; i++) {
//                    page = copy.getImportedPage(reader, i);
//                    copy.addPage(page);
//                }
//            }
            document.close();
            reader.close();
            document = null;
            reader = null;
            return archivoPDF;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.generacion_pdf");
        } catch (DocumentException ex) {
            throw new Exception("error.sis.generacion_pdf");
        }
    }

    public static File pegarArchivosNCPDF (File archivoPDF,
            final String prefijo)
            throws Exception {
        File archivoPDF1 = null;
        try {

            List<File> archivos = new ArrayList();
            PdfReader reader = new PdfReader(new FileInputStream(archivoPDF));


            int paginas = reader.getNumberOfPages();
            for(int i = 1; i <= paginas; i++) {
                if (i % 100 == 0) {
                    System.out.println("VA POR " + i);
                }
                if ((i -1) % 4 == 0) {
                    continue;
                }
                archivoPDF1 = File.createTempFile(prefijo + "_", ".pdf");
                Rectangle psize = reader.getPageSize(1);
                float width = psize.getWidth();
                float height = psize.getHeight();
                Document document = new Document(new Rectangle(width, height));
                PdfWriter writer = PdfWriter.getInstance(document,
                            new FileOutputStream(archivoPDF1));

                PdfImportedPage page = null;

                document.open();
                PdfContentByte cb = writer.getDirectContent();

                document.newPage();
                page = writer.getImportedPage(reader, i);
                cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                document.close();
                archivos.add(archivoPDF1);
            }

            archivoPDF1 = pegarArchivosPDF(archivos,
                    "descomprimido");

            archivoPDF.delete();
            for (File file : archivos) {
                file.delete();
            }
        } catch (IOException ex) {
            throw new Exception("error.sis.generacion_pdf");
        } catch (DocumentException ex) {
            throw new Exception("error.sis.generacion_pdf");
        }
        return archivoPDF1;
    }

      public  File pegarArchivosPDFDinamico (List<File> archivosPDF,
            final String prefijo)
            throws Exception {
        File archivoPDF = null;
        try {
            archivoPDF = File.createTempFile(prefijo + "_", ".pdf");

            PdfReader reader = new PdfReader(new FileInputStream((File)
                    archivosPDF.get(0)));
            Document document = new Document(reader.getPageSizeWithRotation(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(
                    archivoPDF));
            PdfImportedPage page = null;

            document.open();
            for (File file : archivosPDF) {
                reader = new PdfReader(new FileInputStream(file));
                int paginas = reader.getNumberOfPages();

                for(int i = 1; i <= paginas; i++) {
                    page = copy.getImportedPage(reader, i);
                    copy.addPage(page);
                }
            }
            document.close();
            return archivoPDF;
        } catch (IOException ex) {
            throw new Exception("error.sis.generacion_pdf");
        } catch (DocumentException ex) {
            throw new Exception("error.sis.generacion_pdf");
        }
    }


    public static File agregaCodigoDeBarra(File archivoPDF,
            final String secuencia)
            throws Exception {
        File archivoPDFCodigo = null;
        PdfReader reader = null;
        Document document = null;
        PdfImportedPage page = null;
        try {
            archivoPDFCodigo = File.createTempFile("conCodigo", ".pdf");

            reader = new PdfReader(new FileInputStream(archivoPDF));
            document = new Document(reader.getPageSizeWithRotation(1));
            Rectangle psize = reader.getPageSize(1);
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(archivoPDFCodigo));
            document.open();

            PdfContentByte cb = writer.getDirectContent();

            Integer totalPaginas = reader.getNumberOfPages();
            String totalPaginasStr;
            if (totalPaginas < 10) {
                totalPaginasStr = "0" + totalPaginas.toString();
            } else {
                totalPaginasStr = totalPaginas.toString();
            }

            for (int i = 1; i <= totalPaginas; i++) {
                String numPaginaStr;
                if (i < 10) {
                    numPaginaStr = "0" + i;
                } else {
                    numPaginaStr = Integer.toString(i);
                }

                String codigoStr = totalPaginasStr + numPaginaStr + secuencia
                        + "0";

                Barcode39 barcode = new Barcode39();
                barcode.setSize(6);
                barcode.setBarHeight(29);
                barcode.setAltText(codigoStr);
                barcode.setGuardBars(true);
                barcode.setCode(codigoStr);
                barcode.setX(0.78f);
                barcode.setSize(7f);


                Image codigoBarra = barcode.createImageWithBarcode(
                        cb, null, null);
                codigoBarra.setRotation(91.108f);
                codigoBarra.setAbsolutePosition(38.9f, 30);

                page = writer.getImportedPage(reader, i);
                document.newPage();
                document.add(codigoBarra);
                cb.addTemplate(page, 0, 0);

            }
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.agregando_codigo_barras");
        } catch (IOException ex) {
            throw new Exception("error.sis.agregando_codigo_barras");
        }

        return archivoPDFCodigo;
    }

    public static File agregaCodigoDeBarraBM(File archivoPDF,
            Integer secuencia)
            throws Exception {
        File archivoPDFCodigo = null;
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            archivoPDFCodigo = File.createTempFile("archivoPDFCodigo", ".pdf");
            reader = new PdfReader(new FileInputStream(archivoPDF));
            stamper = new PdfStamper(reader, new FileOutputStream(
                    archivoPDFCodigo));

            List bookmarksLst = SimpleBookmark.getBookmark(reader);
            Object[] bookmarksMap = bookmarksLst.toArray();
            for (int x = 0; x < bookmarksMap.length; x++) {
                Map mapa = (Map) bookmarksMap[x];
                StringTokenizer tokenizer = new StringTokenizer((String)
                        mapa.get("Page"), " ");
                int inicioDocumento = Integer.parseInt(tokenizer.nextToken());
                tokenizer.nextToken();

                int finDocumento = -1;
                if ((x + 1) == bookmarksMap.length) {
                    finDocumento = reader.getNumberOfPages();
                } else {
                    Map mapaNxt = (Map) bookmarksMap[x + 1];
                    tokenizer = new StringTokenizer((String) mapaNxt.get(
                            "Page"), " ");
                    finDocumento = Integer.parseInt(tokenizer.nextToken()) - 1;
                }

                int paginas = finDocumento - inicioDocumento + 1;

                String totalPaginasStr;
                if (paginas / 2 < 10) {
                    totalPaginasStr = "0" + (paginas / 2);
                } else {
                    totalPaginasStr = "" + (paginas / 2);
                }

                List hijos = (ArrayList) mapa.get("Kids");

                int secuenciaAux = secuencia + paginas;

                int codNum = paginas / 2;
//                for (Object hijo : hijos) {
                //PARA ORDEN INVERTIDO
                for (int i = hijos.size() - 1; i >= 0; i--) {
                    Object hijo = hijos.get(i);
                    Map mapaHijo = (Map) hijo;
                    if ("CB".equals(mapaHijo.get("Title"))) {
                        StringTokenizer tokenizerHijo = new StringTokenizer(
                                (String) mapaHijo.get("Page"), " ");

                        int pagina = Integer.parseInt(tokenizerHijo
                                .nextToken());
                        tokenizerHijo.nextToken();
                        int xPos = Integer.parseInt(tokenizerHijo.nextToken());
                        int yPos = Integer.parseInt(tokenizerHijo.nextToken());

                        PdfContentByte cb = stamper.getOverContent(pagina);
                        int numPagina = pagina - inicioDocumento + 1;
                        String numPaginaStr;
                        if (codNum < 10) {
                            numPaginaStr = "0" + codNum;
                        } else {
                            numPaginaStr = Integer.toString(codNum);
                        }
                        codNum--;
                        String secuenciaStr = "00";
                        if (secuencia < 10) {
                            secuenciaStr = "0" + secuencia;
                        } else {
                            secuenciaStr = secuencia + "";
                        }

//                        if (secuenciaAux < 10) {
//                            secuenciaStr = "0" + secuenciaAux;
//                        } else {
//                            secuenciaStr = secuenciaAux + "";
//                        }

                        secuenciaAux--;
                        if (secuenciaAux < 0) {
                            secuenciaAux = 99;
                        }

                        String codigoStr = totalPaginasStr + numPaginaStr
                                + secuenciaStr + "0";

                        Barcode39 barcode = new Barcode39();
                        barcode.setSize(5f);
                        barcode.setBarHeight(30);
                        barcode.setInkSpreading(0.5f);
                        barcode.setAltText(codigoStr);
                        barcode.setGuardBars(true);
                        barcode.setCode(codigoStr);
                        barcode.setX(0.95f);

                        Image codigoBarra = barcode.createImageWithBarcode(
                                cb, null, null);
                        codigoBarra.setRotationDegrees(90);
                        codigoBarra.setAbsolutePosition(xPos - 13, yPos + 6
                                - codigoBarra.getPlainWidth());

                        cb.addImage(codigoBarra);
                    }
                    secuencia--;
                    if (secuencia < 0) {
                        secuencia = 99;
                    }
//                    secuencia++;
//                    if (secuencia == 100) {
//                        secuencia = 0;
//                    }
                }
            }
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.agregando_codigo_barras");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.agregando_codigo_barras");
        } finally {
            try {
                if (stamper != null) {
                    stamper.close();
                }
            } catch (DocumentException ex) {
                throw new Exception("error.sis.agregando_codigo_barras");

            } catch (IOException ex) {
                throw new Exception("error.sis.agregando_codigo_barras");
            }
        }
        archivoPDF.delete();
        return archivoPDFCodigo;
    }


    public static File agregaCodigoDeBarraBM(File archivoPDF,
            Integer secuencia, final boolean invertido)
            throws Exception {
        File archivoPDFCodigo = null;
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            archivoPDFCodigo = File.createTempFile("archivoPDFCodigo", ".pdf");
            reader = new PdfReader(new FileInputStream(archivoPDF));
            stamper = new PdfStamper(reader, new FileOutputStream(
                    archivoPDFCodigo));

            List bookmarksLst = SimpleBookmark.getBookmark(reader);
            Object[] bookmarksMap = bookmarksLst.toArray();
            for (int x = 0; x < bookmarksMap.length; x++) {
                Map mapa = (Map) bookmarksMap[x];
                StringTokenizer tokenizer = new StringTokenizer((String)
                        mapa.get("Page"), " ");
                int inicioDocumento = Integer.parseInt(tokenizer.nextToken());
                tokenizer.nextToken();

                int finDocumento = -1;
                if ((x + 1) == bookmarksMap.length) {
                    finDocumento = reader.getNumberOfPages();
                } else {
                    Map mapaNxt = (Map) bookmarksMap[x + 1];
                    tokenizer = new StringTokenizer((String) mapaNxt.get(
                            "Page"), " ");
                    finDocumento = Integer.parseInt(tokenizer.nextToken()) - 1;
                }

                int paginas = finDocumento - inicioDocumento + 1;

                String totalPaginasStr;
                if (paginas / 2 < 10) {
                    totalPaginasStr = "0" + (paginas / 2);
                } else {
                    totalPaginasStr = "" + (paginas / 2);
                }

                List hijos = (ArrayList) mapa.get("Kids");

                int secuenciaAux = secuencia + paginas;

                int codNum = paginas / 2;
                if (invertido) {
                    for (int i = hijos.size() - 1; i >= 0; i--) {
                        Object hijo = hijos.get(i);
                        Map mapaHijo = (Map) hijo;
                        if ("CB".equals(mapaHijo.get("Title"))) {
                            StringTokenizer tokenizerHijo = new StringTokenizer(
                                    (String) mapaHijo.get("Page"), " ");

                            int pagina = Integer.parseInt(tokenizerHijo
                                    .nextToken());
                            tokenizerHijo.nextToken();
                            int xPos = Integer.parseInt(tokenizerHijo
                                    .nextToken());
                            int yPos = Integer.parseInt(tokenizerHijo
                                    .nextToken());

                            PdfContentByte cb = stamper.getOverContent(pagina);
                            int numPagina = pagina - inicioDocumento + 1;
                            String numPaginaStr;
                            if (codNum < 10) {
                                numPaginaStr = "0" + codNum;
                            } else {
                                numPaginaStr = Integer.toString(codNum);
                            }
                            codNum--;
                            String secuenciaStr = "00";
                            if (secuencia < 10) {
                                secuenciaStr = "0" + secuencia;
                            } else {
                                secuenciaStr = secuencia + "";
                            }

    //                        if (secuenciaAux < 10) {
    //                            secuenciaStr = "0" + secuenciaAux;
    //                        } else {
    //                            secuenciaStr = secuenciaAux + "";
    //                        }

                            secuenciaAux--;
                            if (secuenciaAux < 0) {
                                secuenciaAux = 99;
                            }

                            String codigoStr = totalPaginasStr + numPaginaStr
                                    + secuenciaStr + "0";

                            Barcode39 barcode = new Barcode39();
                            barcode.setSize(5f);
                            barcode.setBarHeight(30);
                            barcode.setInkSpreading(0.5f);
                            barcode.setAltText(codigoStr);
                            barcode.setGuardBars(true);
                            barcode.setCode(codigoStr);
                            barcode.setX(0.95f);

                            Image codigoBarra = barcode.createImageWithBarcode(
                                    cb, null, null);
                            codigoBarra.setRotationDegrees(90);
                            codigoBarra.setAbsolutePosition(xPos - 13, yPos + 6
                                    - codigoBarra.getPlainWidth());

                            cb.addImage(codigoBarra);
                        }
                        secuencia--;
                        if (secuencia < 0) {
                            secuencia = 99;
                        }
                    }
                } else {
                    for (Object hijo : hijos) {
                        Map mapaHijo = (Map) hijo;
                        if ("CB".equals(mapaHijo.get("Title"))) {
                            StringTokenizer tokenizerHijo = new StringTokenizer(
                                    (String) mapaHijo.get("Page"), " ");

                            int pagina = Integer.parseInt(tokenizerHijo
                                    .nextToken());
                            tokenizerHijo.nextToken();
                            int xPos = Integer.parseInt(tokenizerHijo
                                    .nextToken());
                            int yPos = Integer.parseInt(tokenizerHijo
                                    .nextToken());

                            PdfContentByte cb = stamper.getOverContent(pagina);
                            int numPagina = pagina - inicioDocumento + 1;
                            String numPaginaStr;
                            if (codNum < 10) {
                                numPaginaStr = "0" + codNum;
                            } else {
                                numPaginaStr = Integer.toString(codNum);
                            }
                            codNum--;
                            String secuenciaStr = "00";
                            if (secuencia < 10) {
                                secuenciaStr = "0" + secuencia;
                            } else {
                                secuenciaStr = secuencia + "";
                            }


                            secuenciaAux--;
                            if (secuenciaAux < 0) {
                                secuenciaAux = 99;
                            }

                            String codigoStr = totalPaginasStr + numPaginaStr
                                    + secuenciaStr + "0";

                            Barcode39 barcode = new Barcode39();
                            barcode.setSize(5f);
                            barcode.setBarHeight(30);
                            barcode.setInkSpreading(0.5f);
                            barcode.setAltText(codigoStr);
                            barcode.setGuardBars(true);
                            barcode.setCode(codigoStr);
                            barcode.setX(0.95f);

                            Image codigoBarra = barcode.createImageWithBarcode(
                                    cb, null, null);
                            codigoBarra.setRotationDegrees(90);
                            codigoBarra.setAbsolutePosition(xPos - 13, yPos + 6
                                    - codigoBarra.getPlainWidth());

                            cb.addImage(codigoBarra);
                        }

                        secuencia++;
                        if (secuencia == 100) {
                            secuencia = 0;
                        }
                    }
                }
            }
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.agregando_codigo_barras");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.agregando_codigo_barras");
        } finally {
            try {
                if (stamper != null) {
                    stamper.close();
                }
            } catch (DocumentException ex) {
                throw new Exception("error.sis.agregando_codigo_barras");

            } catch (IOException ex) {
                throw new Exception("error.sis.agregando_codigo_barras");
            }
        }
        archivoPDF.delete();
        return archivoPDFCodigo;
    }


    /**
     * Se agrega un codigo OMR a la pagina indicada del archivo.
     * @param archivoPDF Archivo al cual se le agregara el codigo OMR.
     * @param codigo Codigo OMR en arreglo de byte (los valor se consideran
     * bits). Los valores son los siguientes todo valor menor o igual a cero
     * se consideran 0 y todos los valores mayores a cero se consideran 1.
     * @param Xpos Borde horizontal desde donde se empezara a pintar el Codigo
     * OMR.
     * Los posibles valores son: izquierda -> BinariaPDF.IZQUIERDO; derecha ->
     * BinariaPDF.DERECHO.
     * @param Ypos Borde vertical desde donde se empezara a pintar el condigo
     * OMR.
     * Los posibles valores son: superior -> BinariaPDF.SUPERIOR; inferior ->
     *  BinariaPDF.INFERIOR.
     * @param pagina Numero de pagina al cual se le agregara el codigo.
     * @return Retorna el mismo archivo pero con el codigo OMR agregado.
     * @throws Exception Si ocurre algun error esta excepcion es
     * arrojada.
     */
    public static File agregaCodigoOMR(final File archivoPDF, final int Xpos,
            final int Ypos, final int orientacion)
            throws Exception {
        float anchoLinea        = 2.14f;
        float separacionLinea   = 11.33f;
        float separacionBordeY  = 21.24f;
        float separacionBordeX  = 0;
        float altoLinea         = 21.24f;

        try {
            PdfReader reader = new PdfReader(new FileInputStream(archivoPDF));
            PdfStamper stamper = new PdfStamper(reader,
                    new FileOutputStream(archivoPDF));

            Rectangle rectangle = reader.getPageSize(1);

            int factorX = 0;
            int factorY = 0;
            float bordeXInicial = 0;
            float bordeYInicial = 0;
            if (orientacion == HORIZONTAL) {
                if (Xpos == IZQUIERDO) {
                    factorX = 1;
                    bordeXInicial = 0;
                } else if (Xpos == DERECHO) {
                    factorX = -1;
                    bordeXInicial = rectangle.getWidth();
                } else {
                    throw new Exception("error.sis.PDF"
                            + ".foctor_X_invalido");
                }
                if (Ypos == SUPERIOR) {
                    factorY = 1;
                    bordeYInicial = rectangle.getHeight();
                } else if (Ypos == INFERIOR) {
                    factorY = -1;
                    bordeYInicial = 0;
                } else {
                    throw new Exception("error.sis.PDF"
                            + ".foctor_Y_invalido");
                }
            } else if (orientacion == VERTICAL) {
                if (Xpos == IZQUIERDO) {
                    factorX = -1;
                    bordeXInicial = 0;
                } else if (Xpos == DERECHO) {
                    factorX = 1;
                    bordeXInicial = rectangle.getWidth();
                } else {
                    throw new Exception("error.sis.PDF"
                            + ".foctor_X_invalido");
                }
                if (Ypos == SUPERIOR) {
                    factorY = -1;
                    bordeYInicial = rectangle.getHeight();
                } else if (Ypos == INFERIOR) {
                    factorY = 1;
                    bordeYInicial = 0;
                } else {
                    throw new Exception("error.sis.PDF"
                            + ".foctor_Y_invalido");
                }
            }

            int secuencia = 0;
            List bookmarksLst = SimpleBookmark.getBookmark(reader);
            Object[] bookmarksMap = bookmarksLst.toArray();
            for (int x = 0; x < bookmarksMap.length; x++) {
                Map mapa = (Map) bookmarksMap[x];
                StringTokenizer tokenizer = new StringTokenizer((String)
                        mapa.get("Page"), " ");
                int inicioDocumento = Integer.parseInt(tokenizer.nextToken());
                tokenizer.nextToken();

                int finDocumento = -1;
                if ((x + 1) == bookmarksMap.length) {
                    finDocumento = reader.getNumberOfPages();
                } else {
                    Map mapaNxt = (Map) bookmarksMap[x + 1];
                    tokenizer = new StringTokenizer((String) mapaNxt.get(
                            "Page"), " ");
                    finDocumento = Integer.parseInt(tokenizer.nextToken()) - 1;
                }

                int paginas = finDocumento - inicioDocumento + 1;

                byte[] codigo = new byte[16];

                for (int pos = 0; pos < paginas; pos++) {
                    if (pos % 2 != 0) {
                        int pagina = pos + inicioDocumento;
                        codigo[0] = 1;
                        int cuantasLineas = 1;
                        if ((pos + inicioDocumento) == finDocumento) {
                            codigo[1] = 1;
                            cuantasLineas++;
                        } else {
                            codigo[1] = 0;
                        }

                        byte[] secuenciaArr = preparaSecuenciaOMR(secuencia++);
                        codigo[2] = secuenciaArr[0];
                        codigo[3] = secuenciaArr[1];
                        codigo[4] = secuenciaArr[2];
                        codigo[5] = secuenciaArr[3];

                        cuantasLineas += secuenciaArr[4];

                        if (cuantasLineas % 2 != 0) {
                            codigo[6] = 1;
                        } else {
                            codigo[6] = 0;
                        }

                        PdfContentByte cb = stamper.getOverContent(pagina);
                        cb.setLineWidth(anchoLinea);

                        for(int i = 0; i < codigo.length; i ++) {
                            float auxSeparacionLinea = 0;
                            if (i == 0) {
                                auxSeparacionLinea = 0;
                            } else {
                                auxSeparacionLinea = separacionLinea * i;
                            }
                            float auxAltoLinea = 0;
                            if (codigo[i] <= 0) {
                                auxAltoLinea = 0;
                            } else {
                                auxAltoLinea = altoLinea;
                            }

                            float x1 = 0;
                            float y1 = 0;
                            float x2 = 0;
                            float y2 = 0;

                            if (orientacion == HORIZONTAL) {
                                x1 = bordeXInicial + ((separacionBordeX + (
                                        anchoLinea * i) + (auxSeparacionLinea))
                                        * factorX);
                                y1 = bordeYInicial - (separacionBordeY
                                        * factorY);
                                x2 = bordeXInicial + ((separacionBordeX + (
                                        anchoLinea * i) + (auxSeparacionLinea))
                                        * factorX);
                                y2 = bordeYInicial - ((auxAltoLinea
                                        + separacionBordeY) * factorY);
                            } else if (orientacion == VERTICAL) {
                                x1 = bordeXInicial - (separacionBordeX
                                        * factorX);
                                y1 = bordeYInicial + ((separacionBordeY  + (
                                        anchoLinea * i) + auxSeparacionLinea)
                                        * factorY);
                                x2 = bordeXInicial - ((auxAltoLinea
                                        + separacionBordeX) * factorX);
                                y2 = bordeYInicial + (separacionBordeY + (
                                        anchoLinea * i) + auxSeparacionLinea)
                                        * factorY;
                            }
                            cb.moveTo(x1, y1);
                            cb.lineTo(x2, y2);
                        }

                        cb.stroke();
                    }
                }
            }
            stamper.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.pdf");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.pdf");
        }
        return archivoPDF;
    }


    private static byte[] preparaSecuenciaOMR (int secuencia) {
        byte[] secuenciaArr = new byte[5];

        if (secuencia == 0) {
            secuenciaArr[0] = 0;
            secuenciaArr[1] = 0;
            secuenciaArr[2] = 0;
            secuenciaArr[3] = 0;
            secuenciaArr[4] = 0;
        } else if (secuencia == 1) {
            secuenciaArr[0] = 1;
            secuenciaArr[1] = 0;
            secuenciaArr[2] = 0;
            secuenciaArr[3] = 0;
            secuenciaArr[4] = 1;
        } else if (secuencia == 2) {
            secuenciaArr[0] = 0;
            secuenciaArr[1] = 1;
            secuenciaArr[2] = 0;
            secuenciaArr[3] = 0;
            secuenciaArr[4] = 1;
        } else if (secuencia == 3) {
            secuenciaArr[0] = 1;
            secuenciaArr[1] = 1;
            secuenciaArr[2] = 0;
            secuenciaArr[3] = 0;
            secuenciaArr[4] = 2;
        } else if (secuencia == 4) {
            secuenciaArr[0] = 0;
            secuenciaArr[1] = 0;
            secuenciaArr[2] = 1;
            secuenciaArr[3] = 0;
            secuenciaArr[4] = 1;
        } else if (secuencia == 5) {
            secuenciaArr[0] = 1;
            secuenciaArr[1] = 0;
            secuenciaArr[2] = 1;
            secuenciaArr[3] = 0;
            secuenciaArr[4] = 2;
        } else if (secuencia == 6) {
            secuenciaArr[0] = 0;
            secuenciaArr[1] = 1;
            secuenciaArr[2] = 1;
            secuenciaArr[3] = 0;
            secuenciaArr[4] = 2;
        } else if (secuencia == 7) {
            secuenciaArr[0] = 1;
            secuenciaArr[1] = 1;
            secuenciaArr[2] = 1;
            secuenciaArr[3] = 0;
            secuenciaArr[4] = 3;
        } else if (secuencia == 8) {
            secuenciaArr[0] = 0;
            secuenciaArr[1] = 0;
            secuenciaArr[2] = 0;
            secuenciaArr[3] = 1;
            secuenciaArr[4] = 1;
        } else if (secuencia == 9) {
            secuenciaArr[0] = 1;
            secuenciaArr[1] = 0;
            secuenciaArr[2] = 0;
            secuenciaArr[3] = 1;
            secuenciaArr[4] = 2;
        } else if (secuencia == 10) {
            secuenciaArr[0] = 0;
            secuenciaArr[1] = 1;
            secuenciaArr[2] = 0;
            secuenciaArr[3] = 1;
            secuenciaArr[4] = 2;
        } else if (secuencia == 11) {
            secuenciaArr[0] = 1;
            secuenciaArr[1] = 1;
            secuenciaArr[2] = 0;
            secuenciaArr[3] = 1;
            secuenciaArr[4] = 3;
        } else if (secuencia == 12) {
            secuenciaArr[0] = 0;
            secuenciaArr[1] = 0;
            secuenciaArr[2] = 1;
            secuenciaArr[3] = 1;
            secuenciaArr[4] = 2;
        } else if (secuencia == 13) {
            secuenciaArr[0] = 1;
            secuenciaArr[1] = 0;
            secuenciaArr[2] = 1;
            secuenciaArr[3] = 1;
            secuenciaArr[4] = 3;
        } else if (secuencia == 14) {
            secuenciaArr[0] = 0;
            secuenciaArr[1] = 1;
            secuenciaArr[2] = 1;
            secuenciaArr[3] = 1;
            secuenciaArr[4] = 0;
        } else if (secuencia == 15) {
            secuenciaArr[0] = 1;
            secuenciaArr[1] = 1;
            secuenciaArr[2] = 1;
            secuenciaArr[3] = 1;
            secuenciaArr[4] = 4;
        }

        return secuenciaArr;
    }

    public static final File agregaNumPagina(final File archivoPDF,
            final float tamanoLetra, final boolean enPrimera,
            final boolean enUltima) throws Exception, Exception {
        PdfStamper stamper = null;
        try {
            PdfReader reader = new PdfReader(new FileInputStream(archivoPDF));
            stamper = new PdfStamper(reader, new FileOutputStream(
                    archivoPDF));

            List bookmarksLst = SimpleBookmark.getBookmark(reader);
            Object[] bookmarksMap = bookmarksLst.toArray();
            for (int x = 0; x < bookmarksMap.length; x++) {
                Map mapa = (Map) bookmarksMap[x];
                StringTokenizer tokenizer = new StringTokenizer((String)
                        mapa.get("Page"), " ");
                int inicioDocumento = Integer.parseInt(tokenizer.nextToken());
                tokenizer.nextToken();

                boolean muestraTotalPaginas = false;
                if (((ArrayList) mapa.get("Kids")).size() > 1) {
                    muestraTotalPaginas = true;
                }

                String paginaActualStr = (String) ((Map) ((Map) ((ArrayList)
                        mapa.get("Kids")).get(0))).get("Page");
                StringTokenizer tokenizerPagActual = new StringTokenizer(
                        paginaActualStr, " ");

                String paginasStr = (String) ((Map) ((Map) ((ArrayList)
                        mapa.get("Kids")).get(1))).get("Page");
                StringTokenizer tokenizerPaginas = new StringTokenizer(
                        paginasStr, " ");

                tokenizerPagActual.nextToken();
                tokenizerPagActual.nextToken();
                Integer xPagActual = Integer.parseInt(tokenizerPagActual
                        .nextToken());
                Integer yPagActual = Integer.parseInt(tokenizerPagActual
                        .nextToken());

                tokenizerPaginas.nextToken();
                tokenizerPaginas.nextToken();
                int xPaginas = Integer.parseInt(tokenizerPaginas.nextToken())
                        - xPagActual;
                int yPaginas = Integer.parseInt(tokenizerPaginas.nextToken())
                        - yPagActual;

                int finDocumento = -1;
                if ((x + 1) == bookmarksMap.length) {
                    finDocumento = reader.getNumberOfPages();
                } else {
                    Map mapaNxt = (Map) bookmarksMap[x + 1];
                    tokenizer = new StringTokenizer((String) mapaNxt.get(
                            "Page"), " ");
                    finDocumento = Integer.parseInt(tokenizer.nextToken()) - 1;
                }

                int paginas = finDocumento - inicioDocumento + 1;

                for (int pagActual = 1 ; pagActual <= paginas; pagActual++) {
                        if ((pagActual != 1 || enPrimera)
                                && (pagActual != paginas || enUltima)) {
                            PdfContentByte cb = stamper.getOverContent(pagActual
                                    + inicioDocumento - 1);
                            cb.beginText();
                            cb.setFontAndSize(BaseFont.createFont(BaseFont
                                    .HELVETICA, BaseFont.WINANSI, BaseFont
                                    .NOT_EMBEDDED) , tamanoLetra);
                            cb.moveText(xPagActual, yPagActual);
                            cb.showText(Integer.toString(pagActual));
                            if (muestraTotalPaginas) {
                                cb.moveText(xPaginas, yPaginas);
                                cb.showText(" de " + Integer.toString(paginas));
                            }
                            cb.endText();
                        }
                }
            }
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.pdf");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.pdf");
        } finally {
            try {
                stamper.close();
            } catch (DocumentException ex) {
                ex.printStackTrace();
                throw new Exception("error.sis.pdf");
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new Exception("error.sis.pdf");
            }
        }
        return archivoPDF;
    }

    public static final File agregaNumPaginaUnDoc(final File archivoPDF,
            final int Xpos, final int Ypos, float tamanoLetra,
            final boolean enPrimera, final boolean enUltima)
            throws Exception {
        PdfStamper stamper = null;
        try {
            PdfReader reader = new PdfReader(new FileInputStream(archivoPDF));
            stamper = new PdfStamper(reader, new FileOutputStream(
                    archivoPDF));

            Rectangle rectangle = reader.getPageSize(1);
            float separacionBordeY = 17;
            float separacionBordeX = rectangle.getWidth() / 2;

            int factorX = 1;
            float bordeXInicial = 0;
            if (Xpos == IZQUIERDO) {
                factorX = 1;
                bordeXInicial = 0;
            } else if (Xpos == DERECHO) {
                factorX = -1;
                bordeXInicial = rectangle.getWidth();
            } else {
                throw new Exception("error.sis.PDF.foctor_X_invalido");
            }
            int factorY = 1;
            float bordeYInicial = 0;
            if (Ypos == SUPERIOR) {
                factorY = +1;
                bordeYInicial = rectangle.getHeight();
            } else if (Ypos == INFERIOR) {
                factorY = 1;
                bordeYInicial = 0;
            } else {
                throw new Exception("error.sis.PDF.foctor_Y_invalido");
            }

            int inicioDocumento = 1;
            int finDocumento = reader.getNumberOfPages();
            int paginas = reader.getNumberOfPages();

            System.out.println("paginas = " + paginas);
            for (int pagActual = 1 ; pagActual <= paginas; pagActual++) {
                if ((enPrimera || pagActual != 1)
                        && (enUltima || pagActual != paginas)) {

                    PdfContentByte cb = stamper.getOverContent(pagActual
                            + inicioDocumento - 1);
                    cb.beginText();
                    cb.setFontAndSize(BaseFont.createFont(BaseFont
                            .HELVETICA, BaseFont.WINANSI, BaseFont
                            .NOT_EMBEDDED) , tamanoLetra);
                    cb.moveText(bordeXInicial
                            + (separacionBordeX * factorX), bordeYInicial
                            + (separacionBordeY * factorY));
                    cb.showText(pagActual + " de " + "4");
                    cb.endText();
                }
            }
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.pdf");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.pdf");
        } finally {
            try {
                stamper.close();
            } catch (DocumentException ex) {
                ex.printStackTrace();
                throw new Exception("error.sis.pdf");
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new Exception("error.sis.pdf");
            }
        }
        return archivoPDF;
    }

    public static File cambiaOrden(final File archivoPDF)
            throws Exception {
        File archivoPDFCambiado = new File ("c:/cambiado.pdf");
        PdfReader reader = null;
        Document document = null;
        try {
            archivoPDFCambiado.createNewFile();

             reader = new PdfReader(new FileInputStream(archivoPDF));
             document = new Document(reader.getPageSizeWithRotation(1));
             PdfCopy copy = new PdfCopy(document, new FileOutputStream(
                    archivoPDFCambiado));
             System.out.println("CARGO ELA ARCHIVO");
             document.open();

             for(int i = reader.getNumberOfPages(); i > 0; i--) {
                 if (i % 500 == 0) {
                     System.out.println("VA POR: " + i);
                 }
                 PdfImportedPage page1 = copy.getImportedPage(reader, i - 1);
                 PdfImportedPage page2 = copy.getImportedPage(reader, i);

                 copy.addPage(page1);
                 copy.addPage(page2);
                 i--;
             }

             document.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.pdf1");
        } catch (DocumentException ex) {
            throw new Exception("error.sis.pdf2");
        }
        return archivoPDFCambiado;
    }

    public static File cambiaOrden(final File archivoPDF, 
            final boolean formatoImpresion)
            throws Exception {
        File archivoPDFCambiado = null;
        PdfReader reader = null;
        Document document = null;
        try {
            archivoPDFCambiado = File.createTempFile("archivoCambiado"
                    , ".pdf");

             reader = new PdfReader(new FileInputStream(archivoPDF));
             document = new Document(reader.getPageSizeWithRotation(1));
             PdfCopy copy = new PdfCopy(document, new FileOutputStream(
                    archivoPDFCambiado));
             document.open();

            List bookmarksLst = SimpleBookmark.getBookmark(reader);
            Object[] bookmarksMap = bookmarksLst.toArray();
            for (int x = bookmarksMap.length - 1; x >= 0 ; x--) {
                Map mapa = (Map) bookmarksMap[x];
                StringTokenizer tokenizer = new StringTokenizer((String)
                        mapa.get("Page"), " ");
                int inicioDocumento = Integer.parseInt(tokenizer.nextToken());
                tokenizer.nextToken();

                int finDocumento = -1;
                if ((x + 1) == bookmarksMap.length) {
                    finDocumento = reader.getNumberOfPages();
                } else {
                    Map mapaNxt = (Map) bookmarksMap[x + 1];
                    tokenizer = new StringTokenizer((String) mapaNxt.get(
                            "Page"), " ");
                    finDocumento = Integer.parseInt(tokenizer.nextToken()) - 1;
                }

                int paginas = finDocumento - inicioDocumento + 1;

                for(int y = 0; y < paginas; y++) {
                    PdfImportedPage page = copy.getImportedPage(reader,
                            y + inicioDocumento);
                    copy.addPage(page);
                }
            }

             document.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("error.sis.pdf1");
        } catch (DocumentException ex) {
            throw new Exception("error.sis.pdf2");
        }
        return archivoPDFCambiado;
    }
}
