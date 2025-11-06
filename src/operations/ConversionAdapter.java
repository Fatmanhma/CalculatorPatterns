package operations;


import operations.adaptees.ConversionOperations;


public class ConversionAdapter implements Operation { 
    private String conversionType;
    
    
    private ConversionOperations adaptee;

    public ConversionAdapter(String conversionType) { 
        this.conversionType = conversionType; 
        
        this.adaptee = new ConversionOperations(); 
    }
    
    @Override
    public float calculate(float a, float b) {
        
        switch (conversionType) { //
            case "SAR_TO_USD": return adaptee.sarToUsd(a); //
            case "USD_TO_SAR": return adaptee.usdToSar(a); //
            case "C_TO_F": return adaptee.celsiusToFahrenheit(a); //
            case "F_TO_C": return adaptee.fahrenheitToCelsius(a); //
            case "KG_TO_LB": return adaptee.kgToLb(a); //
            case "LB_TO_KG": return adaptee.lbToKg(a); //
            case "CM_TO_INCH": return adaptee.cmToInch(a); //
            case "INCH_TO_CM": return adaptee.inchToCm(a); //
            default: return a; //
        }
    }
    
    @Override
    public String getSymbol() {
        
        switch (conversionType) { //
            case "SAR_TO_USD": return "R→$"; //
            case "USD_TO_SAR": return "$→R"; //
            case "C_TO_F": return "C→F"; //
            case "F_TO_C": return "F→C"; //
            case "KG_TO_LB": return "kg→lb"; //
            case "LB_TO_KG": return "lb→kg"; //
            case "CM_TO_INCH": return "cm→in"; //
            case "INCH_TO_CM": return "in→cm"; //
            default: return "?"; //
        }
    }
}