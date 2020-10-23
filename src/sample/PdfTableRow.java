package sample;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.text.DecimalFormat;

public class PdfTableRow {

    private String itemName;
    private int amount;
    private int price;
    private int number;

    public PdfTableRow(String itemName, String amount, String price, int number) {
        this.itemName = itemName;
        this.amount = Integer.parseInt(amount);
        this.price = Integer.parseInt(price);
        this.number = number;
    }

    public void addRow (PdfPTable table) throws IOException, DocumentException {
        createCellToTable(table, Integer.toString(number));
        createCellToTable(table, itemName);
        createCellToTable(table, Integer.toString(amount));
        createCellToTable(table, roundValues(amount*price / 1.23F) + " zł");
        createCellToTable(table, roundValues((amount*price) - (amount*price / 1.23F)) + " zł");
        createCellToTable(table, roundValues(price) +" zł");
        createCellToTable(table, roundValues(amount*price) + " zł");
    }

    private void createCellToTable(PdfPTable table, String name) throws IOException, DocumentException {
        Paragraph phr = new Paragraph(name,setFont(false));
        PdfPCell cell = new PdfPCell(phr);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(6);
        cell.setPaddingTop(3);
        table.addCell(cell);
    }

    private Font setFont(boolean isBold) throws IOException, DocumentException {
        int defaultSize = 10;
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        if(isBold)
            return new Font(helvetica,defaultSize,Font.BOLD);
        else
            return new Font(helvetica,defaultSize);
    }

    private String roundValues(float value){
        DecimalFormat myFormatter = new DecimalFormat("###.00");
        return myFormatter.format(value);
    }


    public double getNetto(){
        return amount*price / 1.23F;
    }

    public double getTax(){
        return (amount*price) - (amount*price / 1.23F);
    }

    public double getFinalPrice(){
        return amount*price;
    }

    public boolean isGoodItem (){
        if (price > 0 && amount > 0)
            return true;
        else
            return false;
    }
}
