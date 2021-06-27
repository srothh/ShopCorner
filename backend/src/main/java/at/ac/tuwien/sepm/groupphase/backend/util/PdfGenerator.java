package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
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
    private final String htmlOperator;
    private final String htmlCustomer;
    private final String htmlCanceledOperator;
    private final String htmlCanceledCustomer;

    public PdfGenerator() {
        String directory = "htmlToPdfTemplate";
        try {
            BufferedReader in = new BufferedReader(new FileReader(directory + "/operatorInvoiceTemplate_v1.html"));
            htmlOperator = in.lines().collect(Collectors.joining());
            in = new BufferedReader(new FileReader(directory + "/customerInvoiceTemplate_v1.html"));
            htmlCustomer = in.lines().collect(Collectors.joining());
            in = new BufferedReader(new FileReader(directory + "/canceledOperatorInvoiceTemplate_v1.html"));
            htmlCanceledOperator = in.lines().collect(Collectors.joining());
            in = new BufferedReader(new FileReader(directory + "/canceledCustomerInvoiceTemplate_v1.html"));
            htmlCanceledCustomer = in.lines().collect(Collectors.joining());
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
    public byte[] generatePdf(Invoice invoice, Order order) {
        Customer customer = null;
        if (order != null) {
            customer = order.getCustomer();
        }
        Document document = null;
        if (customer != null && invoice.getInvoiceType() == InvoiceType.customer) {
            document = Jsoup.parse(htmlCustomer);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            this.addInvoiceInformation(document, invoice);
            this.addOrderInformation(document, invoice);
            this.addCustomerInformation(document, customer);
            this.addProductTable(document, invoice, order.getPromotion() == null ? 0 : order.getPromotion().getDiscount(), 1);

        } else if (customer != null && invoice.getInvoiceType() == InvoiceType.canceled) {
            document = Jsoup.parse(htmlCanceledCustomer);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            this.addInvoiceInformation(document, invoice);
            this.addCustomerInformation(document, customer);
            this.addOrderInformation(document, invoice);
            this.addProductTable(document, invoice, order.getPromotion() == null ? 0 : order.getPromotion().getDiscount(), -1);

        } else if (invoice.getInvoiceType() == InvoiceType.operator) {
            document = Jsoup.parse(htmlOperator);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            this.addInvoiceInformation(document, invoice);
            this.addOrderInformation(document, invoice);
            this.addProductTable(document, invoice, 0, 1);

        } else if (invoice.getInvoiceType() == InvoiceType.canceled) {
            document = Jsoup.parse(htmlCanceledOperator);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            this.addInvoiceInformation(document, invoice);
            this.addProductTable(document, invoice, 0, -1);
        }

        this.addCompanyFooter(document);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ConverterProperties properties = new ConverterProperties();
        HtmlConverter.convertToPdf(document.html(), buffer, properties);
        return buffer.toByteArray();
    }


    private void addCustomerInformation(Document document, Customer customer) {
        String street = customer.getAddress().getStreet();
        String houseNum = customer.getAddress().getHouseNumber();
        String door = customer.getAddress().getDoorNumber();
        int stair = customer.getAddress().getStairNumber();
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        document.body().select(".customer-name").html(customer.getName());

        if (door != null && stair != 0) {
            document.body().select(".customer-street").html(street + " " + houseNum + "/" + door + "/" + stair);
        } else if (door != null) {
            document.body().select(".customer-street").html(street + " " + houseNum + "/" + door);
        } else {
            document.body().select(".customer-street").html(street + " " + houseNum);
        }

        int postalCode = customer.getAddress().getPostalCode();
        document.body().select(".customer-city").html(postalCode + " ");

    }

    private void addOrderInformation(Document document, Invoice invoice) {
        document.body().select(".order-number").html(invoice.getOrderNumber());
    }

    private void addInvoiceInformation(Document document, Invoice invoice) {
        document.body().select(".invoice-date").html(invoice.getDate().format(dateFormatter));
        document.body().select(".invoice-number").html(invoice.getInvoiceNumber());
    }


    private void addProductTable(Document document, Invoice invoice, double promotionDiscount, int calculationFactor) {
        final Element tableArticle = this.getElement(document, ".article");
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
            tableItemStringBuilder.append(String.format("<td class=\"center amount\"><span>%s</span></td>", String.format("%.2f €", totalPerProduct * calculationFactor)));
            tableItemStringBuilder.append("</tr>");

        }
        if (promotionDiscount > 0) {
            total -= promotionDiscount;
        }
        tableItemStringBuilder.append("</table>");
        tableArticle.html(tableItemStringBuilder.toString());
        addTotalTable(document, total, subtotal, tax, promotionDiscount, calculationFactor);
    }

    private void addTotalTable(Document document, double total, double subtotal, double tax, double promotionDiscount, int calculationFactor) {
        StringBuilder tableTotalStringBuilder = new StringBuilder();
        final Element tableAmount = getElement(document, ".total");
        tableTotalStringBuilder.append("<tr ><td class=\"right span\" colspan=\"3\"></td>");
        tableTotalStringBuilder.append("<td class=\"right total-text none-border\"><span>Zwischensumme</span></td>");
        tableTotalStringBuilder.append(String.format("<td class=\"center none-border\"><span>%1.2f €</span></td></tr>", subtotal * calculationFactor));

        tableTotalStringBuilder.append("<tr ><td class=\"right span\" colspan=\"3\"></td>");
        tableTotalStringBuilder.append("<td class=\"right total-text none-border\"><span>Steuer</span></td>");
        tableTotalStringBuilder.append(String.format("<td class=\"center none-border\"><span>%1.2f €</span></td></tr>", tax * calculationFactor));

        if (promotionDiscount > 0) {
            tableTotalStringBuilder.append("<tr ><td class=\"right span\" colspan=\"3\"></td>");
            tableTotalStringBuilder.append("<td class=\"right total-text none-border\"><span>Gutscheincode</span></td>");
            tableTotalStringBuilder.append(String.format("<td class=\"center none-border\"><span>%1.2f €</span></td></tr>", -promotionDiscount));
        }
        tableTotalStringBuilder.append("<tr ><td class=\"right span\" colspan=\"3\"></td>");
        tableTotalStringBuilder.append("<td class=\"right total-text\"><span>Summe</span></td>");
        tableTotalStringBuilder.append(String.format("<td class=\"center\"><span>%1.2f €</span></td></tr>", total * calculationFactor));
        tableTotalStringBuilder.append("</table>");
        tableAmount.html(tableTotalStringBuilder.toString());
    }


    private void addCompanyFooter(Document document) {
        document.body().select(".name").html("ShopCorner");
        document.body().select(".address").html("Favoritenstraße 9/11, 1040 Wien");
        document.body().select(".phone").html("01 5880119501");
        document.body().select(".email").html("admin@shop-corner.at");
    }

    private Element getElement(Document document, String cssClass) {
        return document.body().select(cssClass).first();
    }
}
