package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import org.hibernate.service.spi.ServiceException;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final String htmlUri = "htmlToPdfTemplate/";

    public String generatePdf(Invoice invoice) {
        String fileOutput = String.format("invoices/invoice_%s_%s.pdf", invoice.getDate().format(dateFormatter), invoice.getId());
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(htmlUri);
        try {
            String html = new String(Files.readAllBytes(Paths.get(htmlUri + "invoiceTemplate_v1.html")));

            final Document document = Jsoup.parse(html);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.body().select(".invoice-date").html(invoice.getDate().format(dateFormatter));
            document.body().select(".invoice-number").html(invoice.getId() + "");
            Element tableArticle = document.body().select(".article").first();
            String tableAsString = "<tr ><th class=\"center product\"><span>Produkt</span></th><th class=\"center price\"><span>Preis</span></th><th class=\"center quantity\"><span>Anzahl</span></th><th class=\"center tax\"><span>Steuer</span></th><th class=\"center amount\"><span>Betrag</span></th></tr>";
            double total = 0;
            double subtotal = 0;
            double tax = 0;

            for (InvoiceItem i : invoice.getItems()) {
                Product p = i.getProduct();
                TaxRate t = p.getTaxRate();
                double subtotalPerProduct = p.getPrice() * i.getNumberOfItems();
                double taxPerProduct = p.getPrice() * i.getNumberOfItems() * ((t.getPercentage() / 100));
                double totalPerProduct = subtotalPerProduct + taxPerProduct;
                subtotal = subtotal + subtotalPerProduct;
                tax = tax + taxPerProduct;
                total = total + totalPerProduct;
                tableAsString = tableAsString + String.format("<tr ><td class=\"center product\"><span>%s</span></td><td class=\"center price\"><span>%s</span></td><td class=\"center quantity\"><span>%s</span></td><td class=\"center tax\"><span>%s</span></td><td class=\"center amount\"><span>%s</span></td></tr>",
                    p.getName(), p.getPrice() + " €", i.getNumberOfItems(), t.getPercentage() + "%", totalPerProduct + " €");
            }
            tableAsString = tableAsString + "</table>";
            tableArticle.html(tableAsString);
            tableAsString = "";

            final Element tableAmount = document.body().select(".total").first();
            tableAsString = tableAsString + String.format("<tr ><td class=\"right span\" colspan=\"3\"></td>\n<td class=\"right total-text none-border\"><span>Zwischensumme</span></td><td class=\"center none-border\"><span>%1.2f €</span></td></tr>", subtotal);
            tableAsString = tableAsString + String.format("<tr ><td class=\"right span\" colspan=\"3\"><td class=\"right total-text none-border\"><span>Steuer</span></td><td class=\"center none-border\"><span>%1.2f €</span></td></tr>", tax);
            tableAsString = tableAsString + String.format("<tr ><td class=\"right span\" colspan=\"3\"><td class=\"right total-text\"><span>Summe</span></td><td class=\"center\"><span>%1.2f €</span></td></tr>", total);
            tableAsString = tableAsString + "</table>";
            tableAmount.html(tableAsString);

            document.body().select(".name").html("ShopCorner");
            document.body().select(".address").html("Favoritenstraße 9/11, 1040 Wien");
            document.body().select(".phone").html("01 5880119501");
            document.body().select(".email").html("admin@shop-corner.at");

            HtmlConverter.convertToPdf(document.html(), new FileOutputStream(fileOutput), properties);

        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return fileOutput;
    }

    public byte[] generatePdfAsByteArray(Invoice invoice) throws IOException {
        String fileOutput = generatePdf(invoice);
        Path pdfFile = Paths.get(fileOutput);
        return Files.readAllBytes(pdfFile);
    }
}
