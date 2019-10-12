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
public class CompilatorLab {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Gramatic g= new Gramatic();
        g.productions.add(new Production("S", "Ab"));
        g.productions.add(new Production("S", "B"));
        g.productions.add(new Production("A", "cC"));
        g.productions.add(new Production("A", "dC"));
        g.productions.add(new Production("C", "aC"));
        g.productions.add(new Production("C", "&"));
        g.productions.add(new Production("B", "aD"));
        g.productions.add(new Production("D", "B"));
        g.productions.add(new Production("D", "&"));
        
        ArrayList<Character> temp = new ArrayList();
        temp.add('c');
        temp.add('d');
        temp.add('a');
        g.first.put('S', temp);
        
        temp = new ArrayList();
        temp.add('c');
        temp.add('d');
        g.first.put('A', temp);
        
        temp = new ArrayList();
        temp.add('a');
        temp.add('&');
        g.first.put('C', temp);
        g.first.put('D', temp);
        temp = new ArrayList();
        temp.add('a');
        g.first.put('B', temp);
        
        
        temp = new ArrayList();
        temp.add('$');
        g.follow.put('S', temp);
        g.follow.put('B', temp);
        g.follow.put('D', temp);
        
        
        temp = new ArrayList();
        temp.add('b');
        g.follow.put('A', temp);
        g.follow.put('C', temp);
        
        M_table mTable= new M_table(g);
        Syntax_analysis sa = new Syntax_analysis();
        
        System.out.println("cb");
        sa.analisys("cb", mTable,"S");
        /*
        System.out.println("caaaaaab");
        sa.analisys("caaaaaab", mTable,"S");
        System.out.println("aaaaaaaaaa");
        sa.analisys("aaaaaaaaaa", mTable,"S");*/
    }

}

