package services;

import java.io.File;
import java.io.FileNotFoundException;
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

import models.User;

public class PDFExporter {

	public void exportUsers(List<User> users, File file) throws IOException {

		try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
				Document doc = new Document(pdfDoc, PageSize.LETTER.rotate());) {
			InputStream is = getClass().getResourceAsStream("/assets/img/SATURN_BUCKS_51.png");

			if (is != null) {
				ImageData data = ImageDataFactory.create(is.readAllBytes());
				Image img = new Image(data).scaleAbsolute(50, 50);

				float altoPagina = PageSize.LETTER.rotate().getHeight();
				float margen = 40;

				img.setFixedPosition(margen, altoPagina - margen - 50);

				doc.add(img);
			}

			doc.add(new Paragraph("Reporte de Usuarios").setBold().setFontSize(12)
					.setTextAlignment(TextAlignment.CENTER));

			doc.add(new Paragraph("").setMarginTop(30));

			float[] columnsWidth = { 1, 4, 4, 6};

			Table table = new Table(UnitValue.createPercentArray(columnsWidth)).useAllAvailableWidth();

			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

			Cell cell = new Cell(1, 5).add(new Paragraph("Usuarios del sistema")).setFont(font).setFontSize(14)
					.setFontColor(DeviceGray.WHITE).setBackgroundColor(new DeviceRgb(114, 155, 121))
					.setTextAlignment(TextAlignment.CENTER);

			table.addHeaderCell(cell);

			for (int i = 0; i < 2; i++) {

				Cell[] headerFooter = new Cell[] {
						new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f))
								.setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("#")),

						new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f))
								.setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Nombre")),

						new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f))
								.setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Apellido")),

						new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f))
								.setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Correo")),
				};

				for (Cell celda : headerFooter) {
					if (i == 0) {
						table.addHeaderCell(celda);
					} else {
						table.addFooterCell(celda);
					}
				}
			}
			
			int indice = 1;
			
			for(User u : users) {
				table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                        .add(new Paragraph(String.valueOf(indice))));

                table.addCell(new Cell().setTextAlignment(TextAlignment.LEFT)
                        .add(new Paragraph(u.getName())));

                table.addCell(new Cell().setTextAlignment(TextAlignment.LEFT)
                        .add(new Paragraph(u.getLastName())));

                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
                        .add(new Paragraph(u.getEmail())));

                indice++;
			}

			doc.add(table);
		}

	}

}