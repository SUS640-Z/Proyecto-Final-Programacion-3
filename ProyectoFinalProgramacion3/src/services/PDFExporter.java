package services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import models.Address;
import models.Rol;
import models.User;

public class PDFExporter {

    // Método privado para agregar el logo (para no repetir código)
    private void agregarLogo(Document doc) throws IOException {
        InputStream is = getClass().getResourceAsStream("/assets/img/SATURN_BUCKS_51.png");
        if (is != null) {
            ImageData data = ImageDataFactory.create(is.readAllBytes());
            Image img = new Image(data).scaleAbsolute(50, 50);
            float altoPagina = PageSize.LETTER.rotate().getHeight();
            img.setFixedPosition(40, altoPagina - 90);
            doc.add(img);
        }
    }

    // --- EXPORTAR USUARIOS ---
    public void exportUsers(List<User> users, File file) throws IOException {
        try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
             Document doc = new Document(pdfDoc, PageSize.LETTER.rotate())) {
            
            agregarLogo(doc);
            doc.add(new Paragraph("Reporte de Usuarios").setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("").setMarginTop(30));

            float[] widths = {1, 4, 4, 6};
            Table table = new Table(UnitValue.createPercentArray(widths)).useAllAvailableWidth();
            
            Cell header = new Cell(1, 4).add(new Paragraph("Usuarios del sistema")).setBackgroundColor(new DeviceRgb(114, 155, 121)).setFontColor(DeviceGray.WHITE).setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(header);
            table.addHeaderCell("ID"); table.addHeaderCell("Nombre"); table.addHeaderCell("Apellido"); table.addHeaderCell("Correo");

            for (User u : users) {
                table.addCell(String.valueOf(u.getId()));
                table.addCell(u.getName());
                table.addCell(u.getLastName());
                table.addCell(u.getEmail());
            }
            doc.add(table);
        }
    }

    // --- EXPORTAR ROLES ---
    public void exportRoles(List<Rol> roles, File file) throws IOException {
        try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
             Document doc = new Document(pdfDoc, PageSize.LETTER.rotate())) {
            
            agregarLogo(doc);
            doc.add(new Paragraph("Reporte de Roles").setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("").setMarginTop(30));

            float[] widths = {1, 4, 7};
            Table table = new Table(UnitValue.createPercentArray(widths)).useAllAvailableWidth();
            
            Cell header = new Cell(1, 3).add(new Paragraph("Roles del sistema")).setBackgroundColor(new DeviceRgb(114, 155, 121)).setFontColor(DeviceGray.WHITE).setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(header);
            table.addHeaderCell("ID"); table.addHeaderCell("Nombre"); table.addHeaderCell("Descripción");

            for (Rol r : roles) {
                table.addCell(String.valueOf(r.getId()));
                table.addCell(r.getName());
                table.addCell(r.getDescription());
            }
            doc.add(table);
        }
    }

    // --- EXPORTAR DIRECCIONES ---
    public void exportAddresses(List<Address> addresses, File file) throws IOException {
        try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
             Document doc = new Document(pdfDoc, PageSize.LETTER.rotate())) {
            
            agregarLogo(doc);
            doc.add(new Paragraph("Reporte de Direcciones").setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("").setMarginTop(30));

            float[] widths = {1, 3, 3, 3, 3};
            Table table = new Table(UnitValue.createPercentArray(widths)).useAllAvailableWidth();
            
            Cell header = new Cell(1, 5).add(new Paragraph("Direcciones registradas")).setBackgroundColor(new DeviceRgb(114, 155, 121)).setFontColor(DeviceGray.WHITE).setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(header);
            table.addHeaderCell("ID"); table.addHeaderCell("Usuario"); table.addHeaderCell("Colonia"); table.addHeaderCell("Calle"); table.addHeaderCell("Ref.");

            for (Address a : addresses) {
                table.addCell(String.valueOf(a.getId()));
                table.addCell(a.getUserName());
                table.addCell(a.getNeighborhood());
                table.addCell(a.getStreet());
                table.addCell(a.getReference());
            }
            doc.add(table);
        }
    }
}