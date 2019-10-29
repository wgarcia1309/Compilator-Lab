/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;

import java.util.Hashtable;
import java.util.Stack;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class Syntax_analyzer {

    String WORD;
    Stack<String> stack, input;
    String[][] output;
    M_table mtable;
    final String[] COLUMNS={"Pila","Entrada","Salida"};
    DefaultTableModel tm;
    public Syntax_analyzer() {
        
    }

    public DefaultTableModel getTm() {
        return tm;
    }
    private String[] st_in(){
                String [] arr=new String [2];
                arr[0]="";
                arr[1]="";
                for (String string : stack) {
                    arr[0]=arr[0]+string;
                }
                for (String string : input) {
                    arr[1]=string+arr[1];
                }
                return arr;
    }
    public boolean analisys(String WORD, M_table mtable){
        this.WORD = WORD.replaceAll("&", "");
        this.mtable = mtable;
        stack = new Stack();
        input = new Stack();
        return process(mtable.gram.getInitial());
    }
    private boolean process(String source) {
        tm= new DefaultTableModel(COLUMNS, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        
        };
        
        stack.push("$");
        stack.push(source);
        
        input.push("$");
        for (int i = 0; i < WORD.length(); i++) {
            input.push(WORD.charAt(WORD.length() - i - 1) + "");
        }
        String stop = stack.peek(), intop = input.peek();
        String out = null;
        while (! (intop.equals("$") && stop.equals("$"))) {
            System.out.println(stop + " " + intop);
            System.out.println(stack.toString());
            System.out.println(input.toString());
            System.out.println("");
            
            if (intop.equals(stop)) {
                String[] stin=st_in();
                tm.addRow(new String[]{stin[0],stin[1],""});
                stack.pop();
                input.pop();
                
            } else {
                
                
                out = mtable.getOut(stop,intop);
                if (out == null) {
                    break;
                }
                String[] stin=st_in();
                tm.addRow(new String[]{stin[0],stin[1],stop+"->"+out});
                stack.pop();
                out=out.replaceAll("&", "");
                for (int i = 0; i < out.length(); i++) {
                    stack.push(out.charAt(out.length() - i - 1) + "");
                }
            }
            stop = stack.peek();
            intop = input.peek();
        }
            System.out.println(stop + " " + intop);
            System.out.println(stack.toString());
            System.out.println(input.toString());
            System.out.println("");
        if (out != null) {
            System.out.println("Acepted");
            String[] stin=st_in();
            tm.addRow(new String[]{stin[0],stin[1],"ACCEPT"});
            return true;
        } else {
            System.out.println("Invalid");
            String[] stin=st_in();
            tm.addRow(new String[]{stin[0],stin[1],"ERROR"});
            return false;
        }
        
    }

}
