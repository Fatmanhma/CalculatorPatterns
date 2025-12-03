/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculator.commands;

import calculatorr.CalculatorLogic;
import operations.Operation; //

/**
 * هذا أمر مخصص (Concrete Command).
 * يغلف "عملية" (مثل + أو ×).
 */
public class OperationCommand implements Command {
    
    private CalculatorLogic receiver; // المستقبِل (الدماغ)
    private Operation operation;      // العملية (+, -, ×...)
    
    // متغيرات لحفظ الحالة قبل التنفيذ (للتراجع)
    private float oldCurrent;
    private float oldPrevious;
    private Operation oldPendingOp;
    private boolean oldNewNum;

    public OperationCommand(CalculatorLogic receiver, Operation operation) {
        this.receiver = receiver;
        this.operation = operation;
    }

    @Override
    public void execute() {
        // 1. حفظ الحالة الحالية قبل التنفيذ
        oldCurrent = receiver.getCurrentDisplay();
        oldPrevious = receiver.getPreviousInput();
        oldPendingOp = receiver.getPendingOperation();
        
        
        // 2. التنفيذ: اطلب من "الدماغ" تنفيذ العملية
        receiver.setOperation(operation);
    }

    @Override
    public void undo() {
        receiver.restoreState(oldCurrent, oldPrevious, oldPendingOp);
        System.out.println("UNDO: تم التراجع عن العملية.");
    }
}
