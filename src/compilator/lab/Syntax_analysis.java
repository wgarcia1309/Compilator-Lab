/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;

import java.util.Hashtable;
import java.util.Stack;

/**
 *
 * @author PC
 */
public class Syntax_analysis {

    final String WORD;
    Stack<String> stack, input;
    String[][] output;
    M_table mtable;

    public Syntax_analysis(String WORD, M_table mtable, String source) {
        this.WORD = WORD;
        this.mtable = mtable;
        stack = new Stack();
        input = new Stack();
        process(source);
    }

    private void process(String source) {
        stack.push("$");
        stack.push(source);
        input.push("$");
        for (int i = 0; i < WORD.length(); i++) {
            input.push(WORD.charAt(WORD.length() - i - 1) + "");
        }
        String stop = stack.peek(), intop = input.peek();
        String out = null;
        while (!intop.equals("$") && !stop.equals("$")) {
            System.out.println(stop + " " + intop);
            System.out.println(stack.toString());
            System.out.println(input.toString());
            System.out.println("");
            if (intop.equals(stop)) {
                stack.pop();
                input.pop();
            } else {
                out = mtable.getOut(stop,intop);
                if (out == null) {
                    break;
                }
                stack.pop();
                for (int i = 0; i < out.length(); i++) {
                    stack.push(out.charAt(out.length() - i - 1) + "");
                }
            }
            stop = stack.peek();
            intop = input.peek();

        }
        if (out != null) {
            System.out.println("Acepted");
        } else {
            System.out.println("Invalid");
        }
    }

}
