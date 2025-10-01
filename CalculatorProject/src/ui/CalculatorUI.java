/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public CalculatorUI(CalculatorEngine engine) {
        this.engine = engine;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 530);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        createDisplayPanel();
        createButtonsPanel();

        frame.setVisible(true);
    }

    private void createDisplayPanel() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBackground(new Color(34, 34, 34));
        displayPanel.setPreferredSize(new Dimension(320, 110));

        operationField = new JTextField();
        operationField.setEditable(false);
        operationField.setBackground(new Color(21, 20, 22));
        operationField.setForeground(new Color(203, 198, 213));
        operationField.setFont(new Font("Arial", Font.PLAIN, 16));
        operationField.setBorder(BorderFactory.createEmptyBorder());
        operationField.setHorizontalAlignment(JTextField.RIGHT);

        displayField = new JTextField("0");
        displayField.setEditable(false);
        displayField.setBackground(new Color(41, 39, 44));
        displayField.setForeground(Color.WHITE);
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        displayField.setBorder(BorderFactory.createEmptyBorder());
        displayField.setHorizontalAlignment(JTextField.RIGHT);

        displayPanel.add(operationField, BorderLayout.NORTH);
        displayPanel.add(displayField, BorderLayout.CENTER);

        frame.add(displayPanel, BorderLayout.NORTH);
    }

    private void createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonsPanel.setBackground(new Color(21, 20, 22));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
            "C", "DEL", "±", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "="
        };

        for (String text : buttons) {
            JButton button = createButton(text);
            buttonsPanel.add(button);
        }

        JButton emptyButton = new JButton();
        emptyButton.setEnabled(false);
        emptyButton.setBackground(new Color(21, 20, 22));
        buttonsPanel.add(emptyButton);

        frame.add(buttonsPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (text.matches("[0-9.]")) {
            button.setBackground(new Color(21, 20, 22));
            button.setForeground(Color.WHITE);
        } else if (text.equals("=")) {
            button.setBackground(new Color(65, 105, 225));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(41, 39, 44));
            button.setForeground(Color.WHITE);
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (text.matches("[0-9.]")) {
                    button.setBackground(new Color(21, 20, 22));
                } else if (text.equals("=")) {
                    button.setBackground(new Color(65, 105, 225));
                } else {
                    button.setBackground(new Color(41, 39, 44));
                }
            }
        });

        button.addActionListener(createButtonListener(text));
        return button;
    }

    private ActionListener createButtonListener(String buttonText) {
        return e -> {
            try {
                switch (buttonText) {
                    case "0": case "1": case "2": case "3": case "4":
                    case "5": case "6": case "7": case "8": case "9":
                    case ".":
                        engine.appendNumber(buttonText);
                        break;
                    case "+": case "-": case "×": case "÷":
                        engine.chooseOperation(buttonText);
                        break;
                    case "=":
                        engine.compute();
                        break;
                    case "C":
                        engine.clear();
                        break;
                    case "DEL":
                        engine.deleteLast();
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
        displayField.setText(engine.getCurrentDisplay());
        operationField.setText(engine.getFullOperation());
    }
}
