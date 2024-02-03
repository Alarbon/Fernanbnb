package dataClass;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import componentes.Utils;
import persistencia.Persistencia;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ResumenReservaPDF {
    private int idReserva;
    private String nombreUser;
    private String localidad;
    private String provincia;
    private double precioNoche;
    private String correoUser;
    private LocalDate fechaInicial;
    private int numNoches;
    private int numHuesped;
    private String nombreVivienda;
    private LocalDateTime reservaCreada;
    private String context = null;

    public ResumenReservaPDF(int idReserva, String nombreUser, String localidad, String provincia, double precioNoche,
                             String correoUser, LocalDate fechaInicial, int numNoches, int numHuesped, String nombreVivienda) {
        this.idReserva = idReserva;
        this.nombreUser = nombreUser;
        this.localidad = localidad;
        this.provincia = provincia;
        this.precioNoche = precioNoche;
        this.correoUser = correoUser;
        this.fechaInicial = fechaInicial;
        this.numNoches = numNoches;
        this.numHuesped = numHuesped;
        this.nombreVivienda = nombreVivienda;
        this.reservaCreada = LocalDateTime.now();

    }


    public void generarResumenReserva() {
        //Creamos el ducumento con formato A4
        Document document = new Document(PageSize.A4, 45, 45, 40, 0);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(Persistencia.resumenReserva()));
            document.open();

            URL url = new URL("https://cdn.discordapp.com/attachments/1147607064525414410/1152281089243611327/pdffernanbnb.png");
            Image imagen = Image.getInstance(url);
            imagen.scalePercent(40f);
            imagen.setAlignment(Element.ALIGN_RIGHT);
            document.add(imagen);

            Paragraph texto = devuelveNegrita("\nHola " + nombreUser);
            texto.getFont().setSize(20);
            texto.setAlignment(Element.ALIGN_CENTER);
            document.add(texto);
            Paragraph texto2 = new Paragraph(("""
                    Gracias por realizar una reserva. Aquí le mandamos los datos pertinentes: 
                                        
                    """));
            texto2.setAlignment(Element.ALIGN_CENTER);
            document.add(texto2);

            texto.clear();
            String idReservaResumen = "ID de la reserva: \n" + idReserva + "\n\n";
            /*texto = devuelveNegrita("""
                    ID de la reserva:
                    123456789

                    """);*/
            texto = devuelveNegrita(idReservaResumen);
            texto.getFont().setSize(20);
            texto.setAlignment(Element.ALIGN_CENTER);
            document.add(texto);


            // Este codigo genera una tabla de 3 columnas
            PdfPTable tabla = new PdfPTable(2);

            // addCell() agrega una celda a la tabla, el cambio de fila
            // ocurre automaticamente al llenar la fila
            //Font font = new Font(Font.SERIF, Font.BOLD,10);

            tabla.setWidthPercentage(100);

            Paragraph parrafoTabla1 = new Paragraph("INFORMACIÓN GENERAL:");
            parrafoTabla1.getFont().setColor(BaseColor.RED);
            PdfPCell celdaIncialTabla1 = new PdfPCell(parrafoTabla1);
            celdaIncialTabla1.setColspan(2);
            //celdaIncialTabla1.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIncialTabla1.setBorder(Rectangle.BOTTOM);
            tabla.addCell(celdaIncialTabla1);

            //Comprobar si las celdas son iguales al metodo crearContenido.EN caso afirmativo se deja como en la tabla 2

            //Creamos el contenido de las 4 celdas

            PdfPTable tablaCorreo = new PdfPTable(1);
            //En cada celda que se cree se debe quitar el borde
            PdfPCell celda1Correo = devuelveCabeceraNegrita("Correo electrónico de contacto: ");
            celda1Correo.setPaddingTop(10);
            Paragraph correoElectronico = new Paragraph(correoUser);
            correoElectronico.getFont().setColor(BaseColor.BLUE);
            PdfPCell celda2Correo = new PdfPCell(correoElectronico);
            celda2Correo.setBorder(Rectangle.NO_BORDER);
            celda2Correo.setPaddingBottom(10);
            tablaCorreo.addCell(celda1Correo);
            tablaCorreo.addCell(celda2Correo);
            PdfPCell celdaCorreo = new PdfPCell(tablaCorreo);
            celdaCorreo.setBorder(Rectangle.NO_BORDER);

            tabla.addCell(crearContenidoTabla("Nombre de la vivienda: ", nombreVivienda));
            tabla.addCell(celdaCorreo);
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            tabla.addCell(crearContenidoTabla("Fecha de la reserva", reservaCreada.format(formatter).replace("-", "/")));
            tabla.addCell(crearContenidoTabla("", ""));

            // Si desea crear una celda de mas de una columna
            // Cree un objecto Cell y cambie su propiedad span

            // Agregamos la tabla al documento
            document.add(tabla);

            //Creamos la tabla de los datos del precio
            PdfPTable tabla2 = new PdfPTable(2);
            tabla2.setWidthPercentage(100);

            Paragraph parrafoTabla2 = new Paragraph("DATOS DE LA RESERVA:");
            parrafoTabla2.getFont().setColor(BaseColor.RED);
            PdfPCell celdaIncialTabla2 = new PdfPCell(parrafoTabla2);
            celdaIncialTabla2.setColspan(2);
            //celdaIncialTabla1.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIncialTabla2.setBorder(Rectangle.BOTTOM);
            celdaIncialTabla2.setPaddingTop(10);
            tabla2.addCell(celdaIncialTabla2);

            //Describimos los encabezados
            Paragraph parrafoDescripcion = new Paragraph("Descripción");
            parrafoDescripcion.getFont().setColor(BaseColor.WHITE);
            parrafoDescripcion.getFont().setStyle(java.awt.Font.BOLD);
            PdfPCell celdaDescripcion = new PdfPCell(parrafoDescripcion);
            celdaDescripcion.setBackgroundColor(BaseColor.RED);
            celdaDescripcion.setBorder(Rectangle.NO_BORDER);
            celdaDescripcion.setPaddingLeft(10);
            tabla2.addCell(celdaDescripcion);

            Paragraph parrafoPrecio = new Paragraph("Precio");
            parrafoPrecio.getFont().setColor(BaseColor.WHITE);
            parrafoPrecio.getFont().setStyle(java.awt.Font.BOLD);
            PdfPCell celdaPrecio = new PdfPCell(parrafoPrecio);
            celdaPrecio.setBackgroundColor(BaseColor.RED);
            celdaPrecio.setBorder(Rectangle.NO_BORDER);
            celdaPrecio.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celdaPrecio.setPaddingRight(10);
            tabla2.addCell(celdaPrecio);


            String fechaInicio = Utils.formateaFecha(fechaInicial).replace("-", "/");
            String fechaSalida = Utils.formateaFecha(fechaInicial.plusDays(numNoches)).replace("-", "/");
            String totNoches = "x " + numNoches;

            tabla2.addCell(crearContenidoTabla("Provincia", provincia));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Localidad", localidad));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Número de huéspedes", String.valueOf(numHuesped)));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Fecha Inicio", fechaInicio));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Fecha Fin", fechaSalida));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Precio por noche", String.format("%.2f", precioNoche) + " €"));
            tabla2.addCell(crearContenidoTablaDer(String.format("%.2f", precioNoche) + " €"));
            //Tenemos que sacar el total de noches
            tabla2.addCell(crearContenidoTabla("Total de noches", String.valueOf(numNoches)));
            PdfPCell cantidadNoches = crearContenidoTablaDer(totNoches);

            tabla2.addCell(cantidadNoches);

            String precioTotal = "Precio total: " + String.format("%.2f", precioNoche * numNoches) + " €";
            PdfPCell celdaFinal = new PdfPCell(new Paragraph(precioTotal));

            // Indicamos cuantas columnas ocupa la celda
            celdaFinal.setColspan(3);
            celdaFinal.setBorder(Rectangle.TOP);
            celdaFinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabla2.addCell(celdaFinal);

            document.add(tabla2);

            document.close();

        } catch (Exception e) {
            //System.err.println("Ocurrio un error al crear el archivo");
            //System.exit(-1);

        }

    }

    public void generarResumenReservaWeb(String context) {
        //Creamos el ducumento con formato A4
        Document document = new Document(PageSize.A4, 45, 45, 40, 0);

        try {
            String rutaPDF = context.replace("\\target\\fernanbnb-1.0-SNAPSHOT\\", "") + File.separator +Persistencia.resumenReservaWeb(context).replace("/","\\");
            PdfWriter.getInstance(document, new FileOutputStream(rutaPDF));
            document.open();

            URL url = new URL("https://cdn.discordapp.com/attachments/1147607064525414410/1152281089243611327/pdffernanbnb.png");
            Image imagen = Image.getInstance(url);
            imagen.scalePercent(40f);
            imagen.setAlignment(Element.ALIGN_RIGHT);
            document.add(imagen);

            Paragraph texto = devuelveNegrita("\nHola " + nombreUser);
            texto.getFont().setSize(20);
            texto.setAlignment(Element.ALIGN_CENTER);
            document.add(texto);
            Paragraph texto2 = new Paragraph(("""
                    Gracias por realizar una reserva. Aquí le mandamos los datos pertinentes: 
                                        
                    """));
            texto2.setAlignment(Element.ALIGN_CENTER);
            document.add(texto2);

            texto.clear();
            String idReservaResumen = "ID de la reserva: \n" + idReserva + "\n\n";
            /*texto = devuelveNegrita("""
                    ID de la reserva:
                    123456789

                    """);*/
            texto = devuelveNegrita(idReservaResumen);
            texto.getFont().setSize(20);
            texto.setAlignment(Element.ALIGN_CENTER);
            document.add(texto);


            // Este codigo genera una tabla de 3 columnas
            PdfPTable tabla = new PdfPTable(2);

            // addCell() agrega una celda a la tabla, el cambio de fila
            // ocurre automaticamente al llenar la fila
            //Font font = new Font(Font.SERIF, Font.BOLD,10);

            tabla.setWidthPercentage(100);

            Paragraph parrafoTabla1 = new Paragraph("INFORMACIÓN GENERAL:");
            parrafoTabla1.getFont().setColor(BaseColor.RED);
            PdfPCell celdaIncialTabla1 = new PdfPCell(parrafoTabla1);
            celdaIncialTabla1.setColspan(2);
            //celdaIncialTabla1.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIncialTabla1.setBorder(Rectangle.BOTTOM);
            tabla.addCell(celdaIncialTabla1);

            //Comprobar si las celdas son iguales al metodo crearContenido.EN caso afirmativo se deja como en la tabla 2

            //Creamos el contenido de las 4 celdas

            PdfPTable tablaCorreo = new PdfPTable(1);
            //En cada celda que se cree se debe quitar el borde
            PdfPCell celda1Correo = devuelveCabeceraNegrita("Correo electrónico de contacto: ");
            celda1Correo.setPaddingTop(10);
            Paragraph correoElectronico = new Paragraph(correoUser);
            correoElectronico.getFont().setColor(BaseColor.BLUE);
            PdfPCell celda2Correo = new PdfPCell(correoElectronico);
            celda2Correo.setBorder(Rectangle.NO_BORDER);
            celda2Correo.setPaddingBottom(10);
            tablaCorreo.addCell(celda1Correo);
            tablaCorreo.addCell(celda2Correo);
            PdfPCell celdaCorreo = new PdfPCell(tablaCorreo);
            celdaCorreo.setBorder(Rectangle.NO_BORDER);

            tabla.addCell(crearContenidoTabla("Nombre de la vivienda: ", nombreVivienda));
            tabla.addCell(celdaCorreo);
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            tabla.addCell(crearContenidoTabla("Fecha de la reserva", reservaCreada.format(formatter).replace("-", "/")));
            tabla.addCell(crearContenidoTabla("", ""));

            // Si desea crear una celda de mas de una columna
            // Cree un objecto Cell y cambie su propiedad span

            // Agregamos la tabla al documento
            document.add(tabla);

            //Creamos la tabla de los datos del precio
            PdfPTable tabla2 = new PdfPTable(2);
            tabla2.setWidthPercentage(100);

            Paragraph parrafoTabla2 = new Paragraph("DATOS DE LA RESERVA:");
            parrafoTabla2.getFont().setColor(BaseColor.RED);
            PdfPCell celdaIncialTabla2 = new PdfPCell(parrafoTabla2);
            celdaIncialTabla2.setColspan(2);
            //celdaIncialTabla1.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIncialTabla2.setBorder(Rectangle.BOTTOM);
            celdaIncialTabla2.setPaddingTop(10);
            tabla2.addCell(celdaIncialTabla2);

            //Describimos los encabezados
            Paragraph parrafoDescripcion = new Paragraph("Descripción");
            parrafoDescripcion.getFont().setColor(BaseColor.WHITE);
            parrafoDescripcion.getFont().setStyle(java.awt.Font.BOLD);
            PdfPCell celdaDescripcion = new PdfPCell(parrafoDescripcion);
            celdaDescripcion.setBackgroundColor(BaseColor.RED);
            celdaDescripcion.setBorder(Rectangle.NO_BORDER);
            celdaDescripcion.setPaddingLeft(10);
            tabla2.addCell(celdaDescripcion);

            Paragraph parrafoPrecio = new Paragraph("Precio");
            parrafoPrecio.getFont().setColor(BaseColor.WHITE);
            parrafoPrecio.getFont().setStyle(java.awt.Font.BOLD);
            PdfPCell celdaPrecio = new PdfPCell(parrafoPrecio);
            celdaPrecio.setBackgroundColor(BaseColor.RED);
            celdaPrecio.setBorder(Rectangle.NO_BORDER);
            celdaPrecio.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celdaPrecio.setPaddingRight(10);
            tabla2.addCell(celdaPrecio);


            String fechaInicio = Utils.formateaFecha(fechaInicial).replace("-", "/");
            String fechaSalida = Utils.formateaFecha(fechaInicial.plusDays(numNoches)).replace("-", "/");
            String totNoches = "x " + numNoches;

            tabla2.addCell(crearContenidoTabla("Provincia", provincia));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Localidad", localidad));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Número de huéspedes", String.valueOf(numHuesped)));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Fecha Inicio", fechaInicio));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Fecha Fin", fechaSalida));
            tabla2.addCell(crearContenidoTabla("", ""));
            tabla2.addCell(crearContenidoTabla("Precio por noche", String.format("%.2f", precioNoche) + " €"));
            tabla2.addCell(crearContenidoTablaDer(String.format("%.2f", precioNoche) + " €"));
            //Tenemos que sacar el total de noches
            tabla2.addCell(crearContenidoTabla("Total de noches", String.valueOf(numNoches)));
            PdfPCell cantidadNoches = crearContenidoTablaDer(totNoches);

            tabla2.addCell(cantidadNoches);

            String precioTotal = "Precio total: " + String.format("%.2f", precioNoche * numNoches) + " €";
            PdfPCell celdaFinal = new PdfPCell(new Paragraph(precioTotal));

            // Indicamos cuantas columnas ocupa la celda
            celdaFinal.setColspan(3);
            celdaFinal.setBorder(Rectangle.TOP);
            celdaFinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabla2.addCell(celdaFinal);

            document.add(tabla2);

            document.close();

        } catch (Exception e) {
            //System.err.println("Ocurrio un error al crear el archivo");
            //System.exit(-1);

        }

    }


    private Paragraph devuelveNegrita(String texto) {
        Paragraph contenido = new Paragraph(texto);
        contenido.getFont().setStyle(java.awt.Font.BOLD);
        return contenido;
    }

    private PdfPCell devuelveCabeceraNegrita(String encabezado) {
        PdfPCell encabezadoSinBorde = new PdfPCell(devuelveNegrita(encabezado));
        encabezadoSinBorde.setBorder(Rectangle.NO_BORDER);
        return encabezadoSinBorde;
    }

    private PdfPCell crearContenidoTabla(String encabezado, String info) {
        PdfPTable tabla = new PdfPTable(1);
        //En cada celda que se cree se debe quitar el borde
        PdfPCell celda1 = devuelveCabeceraNegrita(encabezado);
        celda1.setPaddingTop(10);
        PdfPCell celda2 = new PdfPCell(new Phrase(info));
        celda2.setBorder(Rectangle.NO_BORDER);
        celda2.setPaddingBottom(10);
        tabla.addCell(celda1);
        tabla.addCell(celda2);
        PdfPCell celda = new PdfPCell(tabla);
        celda.setBorder(Rectangle.NO_BORDER);
        return celda;
    }

    private PdfPCell crearContenidoTablaDer(String info) {
        PdfPTable tabla = new PdfPTable(1);
        //En cada celda que se cree se debe quitar el borde
        PdfPCell celda1 = devuelveCabeceraNegrita("\n");
        celda1.setPaddingTop(10);
        PdfPCell celda2 = new PdfPCell(new Phrase(info));
        celda2.setBorder(Rectangle.NO_BORDER);
        celda2.setPaddingBottom(10);
        celda2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(celda1);
        tabla.addCell(celda2);
        PdfPCell celda = new PdfPCell(tabla);
        celda.setBorder(Rectangle.NO_BORDER);
        return celda;
    }


}


