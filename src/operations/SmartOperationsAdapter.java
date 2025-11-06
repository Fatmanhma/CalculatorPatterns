package operations;


import operations.adaptees.AdvancedOperations; 


public class SmartOperationsAdapter implements Operation { 
    private String operationType;
    
     private AdvancedOperations adaptee; 

    public SmartOperationsAdapter(String operationType) { 
        this.operationType = operationType; //
       
        this.adaptee = new AdvancedOperations(); 
    }
    
    @Override
    public float calculate(float a, float b) {
       
        switch (operationType) { //
            case "square": return adaptee.square(a); //
            case "cube": return adaptee.cube(a); //
            case "half": return adaptee.half(a); //
            case "double": return adaptee.multiplyByTwo(a); //
            default: return a; //
        }
    }
    
    @Override
    public String getSymbol() {
        
        switch (operationType) { //
            case "square": return "²"; //
            case "cube": return "³"; //
            case "half": return "½"; //
            case "double": return "2×"; //
            default: return "?"; //
        }
    }
}