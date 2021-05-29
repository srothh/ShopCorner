package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;

import org.hibernate.service.spi.ServiceException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final String htmlUri = "htmlToPdfTemplate/";

    public byte[] generatePdf(Invoice invoice) {
        byte[] pdfAsBytes;

        ConverterProperties properties = new ConverterProperties();

        try {
            String html = new String(Files.readAllBytes(Paths.get(htmlUri + "invoiceTemplate_v1.html")));

            final Document document = Jsoup.parse(html);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.body().select(".invoice-date").html(invoice.getDate().format(dateFormatter));
            document.body().select(".invoice-number").html(invoice.getId() + "");

            final Element tableArticle = document.body().select(".article").first();

            String tableAsString = "<tr >";
            tableAsString = tableAsString + "<th class=\"center product\"><span>Produkt</span></th>";
            tableAsString = tableAsString + "<th class=\"center price\"><span>Preis</span></th>";
            tableAsString = tableAsString + "<th class=\"center quantity\"><span>Anzahl</span></th>";
            tableAsString = tableAsString + "<th class=\"center tax\"><span>Steuer</span></th>";
            tableAsString = tableAsString + "<th class=\"center amount\"><span>Betrag</span></th>";
            tableAsString = tableAsString + "</tr>";
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
                // Tabel header
                tableAsString = tableAsString + "<tr>";
                tableAsString = tableAsString + String.format("<td class=\"center product\"><span>%s</span></td>", p.getName());
                tableAsString = tableAsString + String.format("<td class=\"center price\"><span>%s</span></td>", p.getPrice() + " €");
                tableAsString = tableAsString + String.format("<td class=\"center quantity\"><span>%s</span></td>", i.getNumberOfItems());
                tableAsString = tableAsString + String.format("<td class=\"center tax\"><span>%s</span></td>", t.getPercentage() + "%");
                tableAsString = tableAsString + String.format("<td class=\"center amount\"><span>%s</span></td>", totalPerProduct + " €");
                tableAsString = tableAsString + "</tr>";

            }
            tableAsString = tableAsString + "</table>";
            tableArticle.html(tableAsString);
            tableAsString = "";

            final Element tableAmount = document.body().select(".total").first();
            tableAsString = tableAsString + "<tr ><td class=\"right span\" colspan=\"3\"></td>";
            tableAsString = tableAsString + "<td class=\"right total-text none-border\"><span>Zwischensumme</span></td>";
            tableAsString = tableAsString + String.format("<td class=\"center none-border\"><span>%1.2f €</span></td></tr>", subtotal);

            tableAsString = tableAsString + "<tr ><td class=\"right span\" colspan=\"3\"></td>";
            tableAsString = tableAsString + "<td class=\"right total-text none-border\"><span>Steuer</span></td>";
            tableAsString = tableAsString + String.format("<td class=\"center none-border\"><span>%1.2f €</span></td></tr>", tax);

            tableAsString = tableAsString + "<tr ><td class=\"right span\" colspan=\"3\"></td>";
            tableAsString = tableAsString + "<td class=\"right total-text\"><span>Summe</span></td>";
            tableAsString = tableAsString + String.format("<td class=\"center\"><span>%1.2f €</span></td></tr>", total);
            tableAsString = tableAsString + "</table>";
            tableAmount.html(tableAsString);

            document.body().select(".name").html("ShopCorner");
            document.body().select(".address").html("Favoritenstraße 9/11, 1040 Wien");
            document.body().select(".phone").html("01 5880119501");
            document.body().select(".email").html("admin@shop-corner.at");

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(document.html(), buffer, properties);
            pdfAsBytes = buffer.toByteArray();


        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return pdfAsBytes;

    }

}
