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
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class PdfGenerator {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final String html;

    public PdfGenerator() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("htmlToPdfTemplate/operatorInvoiceTemplate_v1.html"));
            html = in.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Generates a PDF from an HTML template, parses the template into a document, which can then be changed and edited.
     *
     * @param invoice Invoice Items
     * @return byte array with generated pdf
     */
    public byte[] generatePdfOperator(Invoice invoice) {

        final Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        document.body().select(".invoice-date").html(invoice.getDate().format(dateFormatter));
        document.body().select(".invoice-number").html(invoice.getInvoiceNumber());

        final Element tableArticle = document.body().select(".article").first();
        StringBuilder tableItemStringBuilder = new StringBuilder();
        tableItemStringBuilder.append("<tr >");
        tableItemStringBuilder.append("<th class=\"center product\"><span>Produkt</span></th>");
        tableItemStringBuilder.append("<th class=\"center price\"><span>Preis</span></th>");
        tableItemStringBuilder.append("<th class=\"center quantity\"><span>Anzahl</span></th>");
        tableItemStringBuilder.append("<th class=\"center tax\"><span>Steuer</span></th>");
        tableItemStringBuilder.append("<th class=\"center amount\"><span>Betrag</span></th>");
        tableItemStringBuilder.append("</tr>");
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
            tableItemStringBuilder.append("<tr>");
            tableItemStringBuilder.append("<td class=\"center product\"><span>").append(p.getName()).append("</span></td>");
            tableItemStringBuilder.append(String.format("<td class=\"center price\"><span>%s</span></td>", p.getPrice() + " €"));
            tableItemStringBuilder.append(String.format("<td class=\"center quantity\"><span>%s</span></td>", i.getNumberOfItems()));
            tableItemStringBuilder.append(String.format("<td class=\"center tax\"><span>%s</span></td>", String.format("%.2f ", t.getPercentage()) + "%"));
            tableItemStringBuilder.append(String.format("<td class=\"center amount\"><span>%s</span></td>", String.format("%.2f €", totalPerProduct)));
            tableItemStringBuilder.append("</tr>");

        }
        tableItemStringBuilder.append("</table>");
        tableArticle.html(tableItemStringBuilder.toString());
        StringBuilder tableTotalStringBuilder = new StringBuilder();

        final Element tableAmount = document.body().select(".total").first();
        tableTotalStringBuilder.append("<tr ><td class=\"right span\" colspan=\"3\"></td>");
        tableTotalStringBuilder.append("<td class=\"right total-text none-border\"><span>Zwischensumme</span></td>");
        tableTotalStringBuilder.append(String.format("<td class=\"center none-border\"><span>%1.2f €</span></td></tr>", subtotal));

        tableTotalStringBuilder.append("<tr ><td class=\"right span\" colspan=\"3\"></td>");
        tableTotalStringBuilder.append("<td class=\"right total-text none-border\"><span>Steuer</span></td>");
        tableTotalStringBuilder.append(String.format("<td class=\"center none-border\"><span>%1.2f €</span></td></tr>", tax));

        tableTotalStringBuilder.append("<tr ><td class=\"right span\" colspan=\"3\"></td>");
        tableTotalStringBuilder.append("<td class=\"right total-text\"><span>Summe</span></td>");
        tableTotalStringBuilder.append(String.format("<td class=\"center\"><span>%1.2f €</span></td></tr>", total));
        tableTotalStringBuilder.append("</table>");
        tableAmount.html(tableTotalStringBuilder.toString());

        document.body().select(".name").html("ShopCorner");
        document.body().select(".address").html("Favoritenstraße 9/11, 1040 Wien");
        document.body().select(".phone").html("01 5880119501");
        document.body().select(".email").html("admin@shop-corner.at");

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ConverterProperties properties = new ConverterProperties();
        HtmlConverter.convertToPdf(document.html(), buffer, properties);
        return buffer.toByteArray();


    }

}
