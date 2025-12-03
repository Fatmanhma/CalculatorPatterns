package app;

import calculatorr.CalculatorLogic;
import calculatorr.CommandHistory;
import ui.CalculatorUI;

public class CalculatorApp {
    private static CalculatorApp instance;
    
    // نستخدم Logic و History بدلاً من Engine
    private CalculatorLogic logic;
    private CommandHistory history;
    private CalculatorUI ui;

    private CalculatorApp() {
        // 1. إنشاء الدماغ (Logic)
        this.logic = new CalculatorLogic();
        
        // 2. إنشاء السجل (History)
        this.history = new CommandHistory();
        
        // 3. تمريرهما للواجهة
        this.ui = new CalculatorUI(logic, history);
    }

    public static CalculatorApp getInstance() {
        if (instance == null) {
            instance = new CalculatorApp();
        }
        return instance;
    }
}