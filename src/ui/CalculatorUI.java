
package ui;

import calculatorr.CalculatorLogic;
import calculatorr.CommandHistory;
import calculator.commands.*;
import operations.OperationFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorUI {

    private CalculatorLogic logic;
    private CommandHistory history; // هذا المتغير ضروري جداً
    
    private JFrame frame;
    private JTextField displayField;
    
    private String currentBuffer = "";      
    private boolean isNewNumber = true;     
    private boolean complexMode = false;    

    // الكونستركتور يستقبل الاثنين
    public CalculatorUI(CalculatorLogic logic, CommandHistory history) {
        this.logic = logic;
        this.history = history; // حفظ السجل
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Calculator Final");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 680); 
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        createDisplayPanel();
        createButtonsPanel();

        frame.setVisible(true);
    }

    private void createDisplayPanel() {
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setPreferredSize(new Dimension(420, 100));
        displayPanel.setBackground(new Color(30, 30, 30));
        
        displayField = new JTextField("0");
        displayField.setFont(new Font("Segoe UI", Font.BOLD, 36));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        displayField.setBackground(new Color(30, 30, 30));
        displayField.setForeground(Color.WHITE);
        displayField.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        displayPanel.add(displayField, BorderLayout.CENTER);
        frame.add(displayPanel, BorderLayout.NORTH);
    }

    private void createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(8, 4, 6, 6));
        buttonsPanel.setBackground(new Color(30, 30, 30));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
            "C", "Undo", "(", ")",
            "7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "-",
            "0", ".", "±", "+",
            "=", "²", "³", "½",
            "R→$", "$→R", "C→F", "F→C",
            "kg→lb", "cm→in", "", "" 
        };

        for (String text : buttons) {
            if (!text.isEmpty()) {
                JButton button = createButton(text);
                buttonsPanel.add(button);
            } else {
                JLabel spacer = new JLabel(""); 
                buttonsPanel.add(spacer);
            }
        }
        frame.add(buttonsPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        
        // --- التصحيح: إضافة علامة || ---
        if (text.matches("[0-9.]")) {
            button.setBackground(new Color(60, 60, 60));
            button.setForeground(Color.WHITE);
        } else if (text.equals("=")) {
            button.setBackground(new Color(0, 120, 215)); 
            button.setForeground(Color.WHITE);
        } else if (text.matches("[²³½]|.*→.*")) {
            button.setBackground(new Color(50, 50, 70));
            button.setForeground(new Color(255, 190, 0)); 
            button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        } else if (text.equals("C") || text.equals("Undo")) { // تمت إضافة ||
            button.setBackground(new Color(200, 60, 60));
            button.setForeground(Color.WHITE);
        } else if (text.equals("(") || text.equals(")")) { // تمت إضافة ||
             button.setBackground(new Color(80, 80, 80));
             button.setForeground(Color.CYAN);
        } else {
            button.setBackground(new Color(45, 45, 45));
            button.setForeground(new Color(0, 200, 150));
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().darker());
            }
        });

        button.addActionListener(createButtonListener(text));
        return button;
    }

    private ActionListener createButtonListener(String key) {
        return e -> {
            try {
                // --- التصحيح: إضافة علامة || ---
                if (key.equals("(") || key.equals(")")) { // تمت إضافة ||
                    handleParentheses(key);
                }
                else if (complexMode && !key.equals("=") && !key.equals("C") && !key.equals("Undo")) {
                    currentBuffer += key;
                }
                else if (key.matches("[0-9]") || key.equals(".")) { // تمت إضافة ||
                    handleNumberInput(key);
                } 
                else if (key.matches("[+\\-×÷]")) {
                    handleBinaryInput(key);
                } 
                else if (key.matches("[²³½]|.*→.*")) {
                    handleUnaryInput(key);
                }
                else if (key.equals("=")) {
                    if (complexMode) handleComplexCalculation();
                    else handleEquals();
                } 
                else if (key.equals("C")) {
                    handleClear();
                } else if (key.equals("Undo")) {
                    handleUndo();
                } else if (key.equals("±")) {
                     toggleSign();
                }

                updateDisplay();

            } catch (Exception ex) {
                displayField.setText("Error");
                ex.printStackTrace();
            }
        };
    }

    // --- Logic Helpers ---

    private void handleParentheses(String key) {
        if (!complexMode) {
            complexMode = true;
            if (isNewNumber) currentBuffer = ""; 
        }
        currentBuffer += key;
    }

    private void handleNumberInput(String key) {
        if (isNewNumber) {
            currentBuffer = "";
            isNewNumber = false;
        }
        if (key.equals(".") && currentBuffer.contains(".")) return;
        
        currentBuffer += key;
        
        // Command Pattern
        float val = Float.parseFloat(currentBuffer);
        Command cmd = new NumberCommand(logic, val);
        history.executeCommand(cmd);
    }

    private void handleBinaryInput(String symbol) {
        isNewNumber = true; 
        currentBuffer = ""; 
        
        String opCode = symbol;
        if(symbol.equals("×")) opCode = "*"; 
        if(symbol.equals("÷")) opCode = "/";
        
        try {
            // Command Pattern
            Command cmd = new OperationCommand(logic, OperationFactory.createOperation(opCode));
            history.executeCommand(cmd);
        } catch (IllegalArgumentException e) {
        }
    }

    private void handleUnaryInput(String symbol) {
        Command opCmd = new OperationCommand(logic, OperationFactory.createOperation(symbol));
        history.executeCommand(opCmd);
        
        Command dummyNum = new NumberCommand(logic, 0); 
        history.executeCommand(dummyNum);
        
        logic.performCalculation();
        
        isNewNumber = true;
        currentBuffer = String.valueOf(logic.getCurrentDisplay());
    }

    private void handleEquals() {
        logic.performCalculation();
        isNewNumber = true;
        currentBuffer = String.valueOf(logic.getCurrentDisplay());
    }

    private void handleComplexCalculation() {
        Command expCmd = new ExpressionCmd(logic, currentBuffer);
        history.executeCommand(expCmd);
        
        currentBuffer = String.valueOf(logic.getCurrentDisplay());
        if(currentBuffer.endsWith(".0")) currentBuffer = currentBuffer.replace(".0", "");
        
        isNewNumber = true;
        complexMode = false; 
    }

    // ==========================================
    // الحل لمشكلة logic.undo
    // ==========================================
    private void handleUndo() {
        // الخطأ كان هنا: logic.undo()
        // التصحيح:
        history.pressUndo(); 
        
        float val = logic.getCurrentDisplay();
        currentBuffer = String.valueOf(val);
        if(currentBuffer.endsWith(".0")) currentBuffer = currentBuffer.replace(".0", "");
        
        isNewNumber = (val == 0); 
        complexMode = false; 
    }

    private void handleClear() {
        logic.clear();
        currentBuffer = "0";
        isNewNumber = true;
        complexMode = false;
    }
    
    private void toggleSign() {
         if (!currentBuffer.isEmpty() && !currentBuffer.equals("0")) {
            if (currentBuffer.startsWith("-")) currentBuffer = currentBuffer.substring(1);
            else currentBuffer = "-" + currentBuffer;
            
            if (!complexMode) {
                 float val = Float.parseFloat(currentBuffer);
                 Command cmd = new NumberCommand(logic, val);
                 history.executeCommand(cmd);
            }
        }
    }

    private void updateDisplay() {
        if (complexMode) {
            displayField.setText(currentBuffer);
        } else {
            if (!isNewNumber) {
                 displayField.setText(currentBuffer);
            } else {
                 float val = logic.getCurrentDisplay();
                 String s = String.valueOf(val);
                 if(s.endsWith(".0")) s = s.replace(".0", "");
                 displayField.setText(s);
            }
        }
    }
    
    private class ExpressionCmd implements Command {
        private CalculatorLogic receiver;
        private String expression;
        private float oldVal; 

        public ExpressionCmd(CalculatorLogic receiver, String expression) {
            this.receiver = receiver;
            this.expression = expression;
        }

        @Override
        public void execute() {
            oldVal = receiver.getCurrentDisplay();
            String cleanExpr = expression.replace("×", "*").replace("÷", "/");
            receiver.calculateExpression(cleanExpr);
        }

        @Override
        public void undo() {
            receiver.setOperand(oldVal);
            System.out.println("UNDO: Reverted complex expression.");
        }
    }
}