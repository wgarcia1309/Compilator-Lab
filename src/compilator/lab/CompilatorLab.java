/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;

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
        M_table mTable= new M_table();
        mTable.add("S", "x","xyN");
        mTable.add("N","z","zA");
        mTable.add("A","x","S");
        mTable.add("A","$","");
        Syntax_analysis sa = new Syntax_analysis("xyzxy", mTable, "S");
    }

}

