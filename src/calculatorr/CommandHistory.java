/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculatorr;

import calculator.commands.Command;
import java.util.Stack;

public class CommandHistory {
    
    private Stack<Command> history = new Stack<>();
    public void executeCommand(Command command) {
        command.execute(); // نفذ الأمر
        history.push(command); // ضعه في السجل
    }
  
    public void pressUndo() {
        if (!history.isEmpty()) {
            Command lastCommand = history.pop(); // خذ آخر أمر من السجل
            lastCommand.undo(); // اطلب منه التراجع
        } else {
            System.out.println("لا يوجد شيء للتراجع عنه.");
        }
    }
}
