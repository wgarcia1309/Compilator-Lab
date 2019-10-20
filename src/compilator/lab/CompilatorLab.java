/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

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
       /*
        */
        test6(g);
       

        System.out.println("Productions");
        Set<String> keys=g.getProductions().keySet();
        for (String key : keys) {
            System.out.println(key+"->"+g.getProductions().get(key));
        }
        g.compute();
        System.out.println("First");
        keys=g.getFisrts().keySet();
        for (String key : keys) {
            System.out.println(key+":"+g.getFisrts().get(key));
        }
        
        System.out.println("Follow");
        keys=g.getFollows().keySet();
        for (String key : keys) {
            System.out.println(key+":"+g.getFollows().get(key));
        }        
        M_table mTable= new M_table(g);
        System.out.println("M table");
        mTable.showt();
        Syntax_analysis sa = new Syntax_analysis();
        System.out.println("\n\nANALISIS");
        sa.analisys("bcdefg", mTable);
        /*
        System.out.println("cb");
        
        /*
        System.out.println("caaaaaab");
        sa.analisys("caaaaaab", mTable,"S");
        System.out.println("aaaaaaaaaa");
        sa.analisys("aaaaaaaaaa", mTable,"S");
        */
    }

    
    private static void test1(Gramatic g){
        g.addPro("E","A");
        g.addPro("E","L");
        g.addPro("A","n");
        g.addPro("A","id");
        g.addPro("L","(S)");
        g.addPro("S","E,S");
        g.addPro("S","E");
    }
    private static void testM0(Gramatic g){
        g.addPro("E","TB");
        g.addPro("B","+TB");
        g.addPro("B","-TB");
        g.addPro("B","&");
        g.addPro("T","FA");
        g.addPro("A","*FA");
        g.addPro("A","/FA");
        g.addPro("A","&");
        g.addPro("F","(E)");
        g.addPro("F","i");
    }
    
    private static void testM1(Gramatic g){
        g.setInitial("A");
         g.addPro("A","BCDE");
         /*
         g.addPro("A","&");
         g.addPro("H","h");
         g.addPro("H","&");
         g.addPro("G","g");
         g.addPro("G","&");
         */
         g.addPro("B","b");
         g.addPro("B","&");
         g.addPro("C","c");
         g.addPro("C","&");
         g.addPro("D","d");
         g.addPro("D","&");
         g.addPro("E","e");
         g.addPro("E","&");
    }
    
    
    private static void testM2(Gramatic g){
        g.setInitial("A");
        g.addPro("A","BCBDeL");
        g.addPro("A","z");
        g.addPro("B","aBB");
        g.addPro("B","aBbB");
        g.addPro("B","&");
        g.addPro("C","DeF");
        g.addPro("C","&");
        g.addPro("D","abcd");
        g.addPro("F","f");
        g.addPro("L","l");
    }
    
    
    private static void test2(Gramatic g){
        g.addPro("S","Sa");
        g.addPro("S","aAc");
        g.addPro("S","c");
        g.addPro("A","Ab");
        g.addPro("A","ba");
    }
    
    private static void test3(Gramatic g){
        g.addPro("E","E+T");
        g.addPro("E","T");
        g.addPro("T","T*F");
        g.addPro("T","F");
        g.addPro("F","i");
        g.addPro("F","(E)"); 
    }
    
    private static void test4(Gramatic g){ //Gramatica Ambigua
        g.addPro("E","E+T");
        g.addPro("E","E-T");
        g.addPro("E","T");
        g.addPro("T","T*F");
        g.addPro("T","T/F");
        g.addPro("T","F");
        g.addPro("F","(E)");
        g.addPro("F","i");
        g.addPro("F","E");
    }
     private static void testW(Gramatic g){
         g.setInitial("S");
          g.addPro("S", "Ab");
        g.addPro("S", "B");
        g.addPro("A", "cC");
        g.addPro("A", "dC");
        g.addPro("C", "aC");
        g.addPro("C", "&");
        g.addPro("B", "aD");
        g.addPro("D", "B");
        g.addPro("D", "&");
     }
    private static void test5(Gramatic g){
        g.addPro("S","a");
        g.addPro("S","ab");
        g.addPro("S","abc");
        g.addPro("S","abcd");
        g.addPro("S","e");
        g.addPro("S","f");
    }
    
    private static void test6(Gramatic g){
        g.setInitial("A");
        g.addPro("A","BCDEfG");
        g.addPro("B","b");
        g.addPro("B","&");
        g.addPro("C","c");
        g.addPro("C","&");
        g.addPro("D","d");
        g.addPro("D","&");
        g.addPro("E","e");
        g.addPro("E","&");
        g.addPro("G","g");
        g.addPro("G","&");
    }
    
    private static void test7(Gramatic g){
        g.addPro("A","BCBDe");
        g.addPro("A","z");
        g.addPro("B","aBB");
        g.addPro("B","aBbB");
        g.addPro("B","&");
        g.addPro("C","DeF");
        g.addPro("C","&");
        g.addPro("D","abcd");
        g.addPro("F","f");
    }
}

