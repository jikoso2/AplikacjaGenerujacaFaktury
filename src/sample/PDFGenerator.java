package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.valueOf;

public class PDFGenerator {
    private final int factureNumber;
    private final String buyer;
    private final String buyerAddress;
    private final String buyerCity;
    private final PaymentType paymentMethod;
    private final String NIP;
    private final PdfTableRow[] Rows;
    private final boolean isPersonal;

    public PDFGenerator(int factureNumber, String buyer, String buyerAddress, String buyerCity, PaymentType paymentMethod, String nip, PdfTableRow[] Rows, boolean isPersonal) {
        this.factureNumber = factureNumber;
        this.buyer = buyer;
        this.buyerAddress = buyerAddress;
        this.buyerCity = buyerCity;
        this.paymentMethod = paymentMethod;
        this.NIP = nip;
        this.Rows = Rows;
        this.isPersonal = isPersonal;
    }

    public void finalGenerator () throws IOException, DocumentException {
                var doc = new Document(PageSize.A4,25,25,25,25);
                PdfWriter.getInstance(doc, new FileOutputStream(fileName()));
                doc.open();
                addFactureTitle(doc,factureNumber);
                addContent(doc);
                doc.close();
            }

    private void addFactureTitle(Document document,int number) {
        document.addTitle("Faktura numer: " + number);
    }

    private void addContent(Document document) throws DocumentException, IOException {
        addLogo(document);
        addShopInformation(document);
        addTitle(document);
        addSeller(document);
        addBuyer(document);

        if (!isPersonal)
        addNIP(document);

        addPaymentMethod(document);
        addItemTable(document);
        addSummary(document);
        addSignature(document);
    }

    private void addSignature(Document document) throws IOException, DocumentException {
        addEmptyParagraph(document,3);

        Paragraph paragraph1 = new Paragraph("_ _ _ _ _ _ _ _ _ _ _ _ _ _");
        paragraph1.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph1);

        Paragraph paragraph = new Paragraph("pieczątka i podpis         ",setFont(false));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph);

    }

    private void addSummary(Document document) throws IOException, DocumentException {
        addEmptyParagraph(document,1);
        addSummaryP1(document);
        addSummaryP2(document);
    }
    private void addSummaryP1(Document document) throws DocumentException, IOException {
        double[] sumPrices = sumPrices(Rows);
        double sumFinalPrice = sumPrices[2];

        Paragraph firstText = new Paragraph("Do zapłaty",setFont(true));
        Paragraph secondText = new Paragraph(valueOf(roundValues((float) (sumFinalPrice)))+"zł",setFont(false));
        document.add(alignmentText(firstText, secondText));
    }
    private void addSummaryP2(Document document) throws DocumentException, IOException {
        double[] sumPrices = sumPrices(Rows);
        double sumFinalPrice = sumPrices[2];

        Paragraph firstText = new Paragraph("Słownie złotych:",setFont(true));
        Paragraph secondText = new Paragraph(NumberTranslation.translacja((int) (sumFinalPrice))+"złotych",setFont(false));
        document.add(alignmentText(firstText, secondText));
    }

    private PdfPTable alignmentText(Paragraph firstText, Paragraph secondText) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        float[] columnWidths = new float[]{120f,600f};
        table.setWidthPercentage(columnWidths,PageSize.A4);

        patternCell(firstText,table);
        patternCell(secondText,table);
        return table;
    }
    private void patternCell(Paragraph text, PdfPTable table){
        PdfPCell patternCell = new PdfPCell(text);
        patternCell.setBorder(0);
        table.addCell(patternCell);
    }

    private void addItemTable(Document document) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(7);

        tableTemplate(table);
        setColumnWidths(table);

        table.setHeaderRows(1);


        for (PdfTableRow row : Rows) {
            if (row.isGoodItem())
                row.addRow(table);
        }


        addLastTableRow(table);

        document.add(table);
    }

    private String roundValues(float value){
        DecimalFormat myFormatter = new DecimalFormat("###.00");
        return myFormatter.format(value);
    }

    private double[] sumPrices(PdfTableRow[] Rows){
        double sumNetto = 0;
        for (PdfTableRow row : Rows) {
            sumNetto += row.getNetto();
        }
        double sumTax = 0;
        for (PdfTableRow row : Rows) {
            sumTax += row.getTax();
        }
        double sumFinalPrice = 0;
        for (PdfTableRow row : Rows) {
            sumFinalPrice += row.getFinalPrice();
        }
        return new double[] {sumNetto,sumTax,sumFinalPrice};
    }

    private void addLastTableRow(PdfPTable table) throws DocumentException, IOException {

        double[] sumPrices = sumPrices(Rows);
        double sumNetto = sumPrices[0];
        double sumTax = sumPrices[1];
        double sumFinalPrice = sumPrices[2];

        String netto = valueOf(roundValues((float) sumNetto));
        String tax = valueOf(roundValues((float) sumTax));
        String finalPrice = valueOf(roundValues((float) sumFinalPrice));

        createCellToLastRow(table, " ");
        createCellToLastRow(table, " ");
        createCellToLastRow(table, " ");
        createCellToLastRow(table, netto +" zł");
        createCellToLastRow(table, tax + " zł");
        createCellToLastRow(table, " ");
        createCellToLastRow(table, finalPrice + " zł");

    }

    private void setColumnWidths(PdfPTable table) throws DocumentException {
        float[] columnWidths = new float[]{40f,255f,40f,65f,65f,73f,65f};
        table.setWidthPercentage(columnWidths,PageSize.A4);
    }

    private void tableTemplate(PdfPTable table) throws IOException, DocumentException {
        createCellToTableTemplate(table,"L.P");
        createCellToTableTemplate(table,"Nazwa towaru");
        createCellToTableTemplate(table,"Ilość");
        createCellToTableTemplate(table,"Wartość netto");
        createCellToTableTemplate(table,"Podatek VAT (23%)");
        createCellToTableTemplate(table,"Wartość jednostkowa");
        createCellToTableTemplate(table,"Wartość brutto");
    }

    private void createCellToTableTemplate(PdfPTable table,String name) throws IOException, DocumentException {
        Font font =setFont(false);
        font.setSize(11);
        Paragraph phr = new Paragraph(name,font);
        PdfPCell cell = new PdfPCell(phr);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(7);
        cell.setPaddingTop(7);
        table.addCell(cell);
    }


    private void createCellToLastRow(PdfPTable table,String name) throws IOException, DocumentException {
        Paragraph phr = new Paragraph(name,setFont(10));
        PdfPCell cell = new PdfPCell(phr);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4);
        cell.setPaddingTop(2);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
    }


    private void addPaymentMethod(Document document) throws IOException, DocumentException {

        Paragraph firstText = new Paragraph("Forma płatności:",setFont(true));
        Paragraph secondText = new Paragraph(paymentMethod(),setFont(false));
        document.add(alignmentText(firstText, secondText));

        addEmptyParagraph(document,1);
    }

    private String paymentMethod() {
        if(paymentMethod == PaymentType.both)
            return "karta/gotówka";
        else if (paymentMethod == PaymentType.money)
            return "gotówka";
        else
            return "karta";
    }

    private void addNIP(Document document) throws DocumentException, IOException {
        Paragraph firstText = new Paragraph("NIP",setFont(true));
        Paragraph secondText = new Paragraph(NIP,setFont(false));
        document.add(alignmentText(firstText, secondText));

        addEmptyParagraph(document,1);
    }

    private void addBuyer(Document document) throws DocumentException, IOException {
        addBuyerP1(document);
        addBuyerP2(document);
        addBuyerP3(document);
        addEmptyParagraph(document,1);
    }

    private void addBuyerP1(Document document) throws IOException, DocumentException {
        Paragraph firstText = new Paragraph("Nabywca:",setFont(true));
        Paragraph secondText = new Paragraph(buyer,setFont(false));
        document.add(alignmentText(firstText, secondText));
    }

    private void addBuyerP2(Document document) throws IOException, DocumentException {
        Paragraph firstText = new Paragraph(" ",setFont(true));
        Paragraph secondText = new Paragraph(buyerAddress,setFont(false));
        document.add(alignmentText(firstText, secondText));
    }

    private void addBuyerP3(Document document) throws IOException, DocumentException {
        Paragraph firstText = new Paragraph("Adres",setFont(true));
        Paragraph secondText = new Paragraph(buyerCity,setFont(false));
        document.add(alignmentText(firstText, secondText));
    }

    private void addSeller(Document document) throws DocumentException, IOException {
        addSellerP1(document);
        addSellerP2(document);
        addSellerP3(document);
        addEmptyParagraph(document,1);
    }

    private void addSellerP1(Document document) throws IOException, DocumentException {
        Paragraph firstText = new Paragraph("Sprzedawca",setFont(true));
        Paragraph secondText = new Paragraph("AMER SPORTS POLAND SP. Z O.O.",setFont(false));
        document.add(alignmentText(firstText, secondText));
    }

    private void addSellerP2(Document document) throws IOException, DocumentException {
        Paragraph firstText = new Paragraph("Adres:",setFont(true));
        Paragraph secondText = new Paragraph("ul. Opolska 110, 31-323 Kraków",setFont(false));
        document.add(alignmentText(firstText, secondText));
    }

    private void addSellerP3(Document document) throws IOException, DocumentException {
        Paragraph firstText = new Paragraph("NIP:",setFont(true));
        Paragraph secondText = new Paragraph("526 285 53 43",setFont(false));
        document.add(alignmentText(firstText, secondText));
    }

    private void addTitle(Document document) throws IOException, DocumentException {
        Paragraph paragraph = new Paragraph("FAKTURA VAT " + factureNumber + "/" + dateNumber() + "/S",setFont(16));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        addEmptyParagraph(document,1);
    }

    private void addShopInformation(Document document) throws DocumentException, IOException {
        document.add(tableShopInformation());

        Paragraph firstText = new Paragraph("Data Wystawienia:",setFont(false));
        Paragraph secondText = new Paragraph(addDate(),setFont(false));
        document.add(alignmentText(firstText, secondText));
        addEmptyParagraph(document,1);
    }

    private PdfPTable tableShopInformation() throws DocumentException, IOException {
        Paragraph firstText = new Paragraph("Miejscowość:",setFont(false));
        Paragraph secondText = new Paragraph("Sosnowiec",setFont(false));
        Paragraph thirdText = new Paragraph("Oryginał/Kopia",setFont(false));

        PdfPTable table = new PdfPTable(3);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        float[] columnWidths = new float[]{120f,400f,200f};
        table.setWidthPercentage(columnWidths,PageSize.A4);

        patternCell(firstText,table);
        patternCell(secondText,table);
        patternCell(thirdText,table);

        return table;
    }

    private Font setFont(boolean isBold) throws IOException, DocumentException {
        int defaultSize = 10;
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        if(isBold)
            return new Font(helvetica,defaultSize,Font.BOLD);
        else
            return new Font(helvetica,defaultSize);
    }

    private Font setFont(int size) throws IOException, DocumentException {
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        return new Font(helvetica, size, Font.BOLD);
    }

    private void addLogo(Document document) throws IOException, DocumentException {
        Image logo;
        try{
            URL treeURL = getClass().getResource("Pictures/LogoAmerSports.jpg");
            logo = Image.getInstance(treeURL);
        }
        catch (FileNotFoundException e){
            logo = Image.getInstance("out/production/AplikacjaGenerujacaFaktury/sample/Pictures/LogoAmerSports.jpg");
        }

        logo.setAlignment(Image.MIDDLE);
        scaleImage(logo);
        document.add(logo);
        addEmptyParagraph(document,1);
    }

    private void scaleImage (Image image) {
        float imageSize = 80;
        float scale = image.getWidth() / image.getHeight();
        image.scaleAbsolute(imageSize*scale, imageSize);
    }

    private void addEmptyParagraph(Document document, int size) throws DocumentException {
        for (int i = 0; i < size; i++) {
            document.add (new Paragraph(" "));
        }
    }

    private String addDate() {
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd" );
        return sdf1.format(nowDate);
    }

    private String dateNumber() {
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yy" );
        return sdf1.format(nowDate);
    }

    private String fileName() {
        return "Faktura " + factureNumber +"-"+ dateNumber()+" "+ buyer +".pdf";
    }
}
