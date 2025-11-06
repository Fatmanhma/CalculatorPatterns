/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import core.CalculatorEngine;
import ui.CalculatorUI;

public class CalculatorApp {
    private static CalculatorApp instance;
    private CalculatorEngine engine;
    private CalculatorUI ui;

    private CalculatorApp() {
        this.engine = new CalculatorEngine();
        this.ui = new CalculatorUI(engine);
    }

    public static CalculatorApp getInstance() {
        if (instance == null) {
            instance = new CalculatorApp();
        }
        return instance;
    }

    public CalculatorEngine getEngine() {
        return engine;
    }
    
    public CalculatorUI getUI() {
    return ui;
}

}