/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author PC
 */
public class M_table {
    Hashtable<String,String> ht;
    ArrayList terminals,non_terminals;

    public M_table() {
        ht= new Hashtable<String,String>();
        terminals= new ArrayList();
        non_terminals= new ArrayList();
    }
    
    public void add(String non_terminal,String terminal,String out){
        non_terminals.add(non_terminal);
        terminals.add(terminal);
        ht.put(non_terminal+terminal, out);
    }

    String getOut(String stop, String intop) {
        return ht.get(stop+intop);
    }
}
