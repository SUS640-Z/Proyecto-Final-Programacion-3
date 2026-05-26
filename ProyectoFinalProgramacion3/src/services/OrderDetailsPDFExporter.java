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

import models.OrderDetails;

public class OrderDetailsPDFExporter {
	public void exportOrderDetails(List<OrderDetails> ordersDetails, File file) throws IOException {

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

			doc.add(new Paragraph("Reporte de detalles de ordenes").setBold().setFontSize(12)
					.setTextAlignment(TextAlignment.CENTER));

			doc.add(new Paragraph("").setMarginTop(30));

			float[] columnsWidth = {1, 2f, 4f, 4f, 2f };

			Table table = new Table(UnitValue.createPercentArray(columnsWidth)).useAllAvailableWidth();

			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

			Cell cell = new Cell(1, 5).add(new Paragraph("Detalles de Ordenes del sistema")).setFont(font).setFontSize(14)
					.setFontColor(DeviceGray.WHITE).setBackgroundColor(new DeviceRgb(114, 155, 121))
					.setTextAlignment(TextAlignment.CENTER);

			table.addHeaderCell(cell);

			for (int i = 0; i < 2; i++) {

				Cell[] headerFooter = new Cell[] {
						new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f))
								.setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("#")),

						new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f))
								.setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("N. Orden")),

						new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f))
								.setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Cliente")),

						new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f))
								.setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Producto")),
								
						new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f))
								.setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Cantidad")),
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

			for (OrderDetails o : ordersDetails) {
			    table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
			            .add(new Paragraph(String.valueOf(o.getId()))));

			    
			    table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
			            .add(new Paragraph(String.valueOf(o.getOrder_id()))));

			    
			    table.addCell(new Cell().setTextAlignment(TextAlignment.LEFT)
			            .add(new Paragraph(o.getClient_name() != null ? o.getClient_name() : "")));

			  
			    table.addCell(new Cell().setTextAlignment(TextAlignment.LEFT)
			            .add(new Paragraph(o.getProduct_name() != null ? o.getProduct_name() : "")));

			    
			    table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER)
			            .add(new Paragraph(String.valueOf(o.getQuantity()))));

			    indice++;
			}

			doc.add(table);
		}

	}


}
