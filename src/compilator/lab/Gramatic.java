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
public class Gramatic {

    ArrayList<Production> productions;
    Hashtable<Character, ArrayList<Character>> first, follow;

    public Gramatic() {
        productions= new ArrayList<Production>();
        first=new Hashtable<Character, ArrayList<Character>>();
        follow=new Hashtable<Character, ArrayList<Character>>();
    }

    ArrayList<Production> getPro() {
        return productions;
    }

    ArrayList<Character> getFisrt(char key) {
        return first.get(key);
    }
    ArrayList<Character> getFollow(char key) {
        return follow.get(key);
    }

}
