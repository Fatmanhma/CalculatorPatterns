/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculator.commands;

import calculatorr.CalculatorLogic;

/**
 * هذا أمر مخصص (Concrete Command) لإدخال رقم.
 */
public class NumberCommand implements Command {
    
    private CalculatorLogic receiver;
    private float number;
    
    // متغيرات لحفظ الحالة (للتراجع)
    private float oldCurrent;
    private boolean oldNewNum;

    public NumberCommand(CalculatorLogic receiver, float number) {
        this.receiver = receiver;
        this.number = number;
    }

    @Override
    public void execute() {
        // 1. حفظ الحالة قبل التنفيذ
        oldCurrent = receiver.getCurrentDisplay();
        // oldNewNum = receiver.isNewNumberStarted(); // (تحتاج getter)
        
        // 2. التنفيذ: اطلب من "الدماغ" إدخال الرقم
        receiver.setOperand(number);
    }

    @Override
    public void undo() {
        // 3. التراجع: استرجاع القيمة السابقة
        // (هذا تراجع بسيط، لا يسترجع الحالة كاملة مثل OperationCommand)
        receiver.setOperand(oldCurrent);
        System.out.println("  UNDO: تم التراجع عن الرقم.");
    }
}
