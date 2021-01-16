package utils;

import java.text.DecimalFormat;

public class PdfCreateUtils {
    public static String Value(float v) {
        return zeroBefore(v) +roundValues(v)+ " zł";
    }

    private static String roundValues(float value){
        DecimalFormat myFormatter = new DecimalFormat("###.00");
        return myFormatter.format(value);
    }

    private static String zeroBefore(float a){

        if (a<1)
            return "0";
        else
            return "";
    }
}
