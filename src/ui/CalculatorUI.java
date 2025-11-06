// CalculatorUI.java - Cleaned and neatly formatted
package ui;

import core.CalculatorEngine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorUI {
    private CalculatorEngine engine;
    private JFrame frame;
    private JTextField displayField;
    private JTextField operationField;
    private String expression = "";

    public CalculatorUI(CalculatorEngine engine) {
        this.engine = engine;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        createDisplayPanel();
        createButtonsPanel();

        frame.setVisible(true);
    }

    private void createDisplayPanel() {
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBackground(new Color(34, 34, 34));
        displayPanel.setPreferredSize(new Dimension(350, 120));

        operationField = createTextField(16, new Color(21, 20, 22), new Color(203, 198, 213));
        displayField = createTextField(24, new Color(41, 39, 44), Color.WHITE);
        displayField.setText("0");
        displayField.setFont(new Font("Arial", Font.BOLD, 24));

        displayPanel.add(operationField, BorderLayout.NORTH);
        displayPanel.add(displayField, BorderLayout.CENTER);

        frame.add(displayPanel, BorderLayout.NORTH);
    }

    private JTextField createTextField(int fontSize, Color bg, Color fg) {
        JTextField field = new JTextField();
        field.setEditable(false);
        field.setBackground(bg);
        field.setForeground(fg);
        field.setFont(new Font("Arial", Font.PLAIN, fontSize));
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setHorizontalAlignment(JTextField.RIGHT);
        return field;
    }

    private void createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(8, 4, 5, 5));
        buttonsPanel.setBackground(new Color(21, 20, 22));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
            "C", "DEL", "±", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=",
            "(", ")", "½", "²",
            "³", "R→$", "$→R", "C→F",
            "F→C", "kg→lb", "cm→in", ""
        };

        for (String text : buttons) {
            JButton button = createButton(text);
            buttonsPanel.add(button);
        }

        frame.add(buttonsPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);

        if (text.equals("0")) {
            button.setPreferredSize(new Dimension(160, 60));
        }

        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        styleButton(button, text);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                styleButton(button, text);
            }
        });

        button.addActionListener(createButtonListener(text));
        return button;
    }

    private void styleButton(JButton button, String text) {
        if (text.matches("[0-9.]")) {
            button.setBackground(new Color(21, 20, 22));
            button.setForeground(Color.WHITE);
        } else if (text.equals("=")) {
            button.setBackground(new Color(65, 105, 225));
            button.setForeground(Color.WHITE);
        } else if (text.matches("[²³½×]|R→\\$|C→F|F→C|kg→lb|cm→in")) {
            button.setBackground(new Color(70, 70, 90));
            button.setForeground(Color.YELLOW);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
        } else {
            button.setBackground(new Color(41, 39, 44));
            button.setForeground(Color.WHITE);
        }
    }

    private ActionListener createButtonListener(String buttonText) {
        return e -> {
            try {
                switch (buttonText) {
                    case "(": case ")": case "+": case "-": case "×": case "÷":
                    case "0": case "1": case "2": case "3": case "4":
                    case "5": case "6": case "7": case "8": case "9": case ".":
                        expression += buttonText;
                        operationField.setText(expression);
                        break;

                    case "²": case "³": case "½":
                    case "R→$": case "$→R":
                    case "C→F": case "F→C":
                    case "kg→lb": case "cm→in":
                        engine.appendNumber(expression);
                        engine.chooseOperation(buttonText);
                        expression = "";
                        break;

                    case "=":
                        String clean = expression.trim().replaceAll("\\s+", "");
                        float result = engine.evaluateExpression(clean);
                        displayField.setText((result == (int) result) ? String.valueOf((int) result) : String.format("%.2f", result));
                        engine.setCurrentOperand(String.valueOf(result));
                        expression = String.valueOf(result);
                        break;

                    case "C":
                        expression = "";
                        engine.clear();
                        break;

                    case "DEL":
                        if (!expression.isEmpty()) {
                            expression = expression.substring(0, expression.length() - 1);
                            engine.setCurrentOperand(expression);
                        } else {
                            engine.deleteLast();
                        }
                        break;

                    case "±":
                        engine.toggleSign();
                        break;
                }
                updateDisplay();
            } catch (Exception ex) {
                displayField.setText("Error");
            }
        };
    }

    private void updateDisplay() {
        if (!expression.isEmpty()) {
            operationField.setText(expression);
            displayField.setText(expression);
        } else {
            operationField.setText(engine.getFullOperation());
            displayField.setText(engine.getCurrentDisplay());
        }
    }
}