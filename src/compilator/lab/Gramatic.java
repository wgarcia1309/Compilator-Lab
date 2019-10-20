/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author PC
 */
public class Gramatic {

    private Hashtable<String, Set<String>> productions, first, follow;
    String origin;

    public Gramatic() {
        productions = new Hashtable<String, Set<String>>();
        first = new Hashtable<String, Set<String>>();
        follow = new Hashtable<String, Set<String>>();
    }

    public Gramatic(String origin) {
        this.origin = origin;
        productions = new Hashtable<String, Set<String>>();
        first = new Hashtable<String, Set<String>>();
        follow = new Hashtable<String, Set<String>>();
    }

    public void compute() {
        ComputeFirst();
    }

    private void ComputeFirst() {
        Set<String> keys = productions.keySet();
        for (String key : keys) {
            for (String produc : productions.get(key)) {
                if (!isNonTerminal(produc)) {
                    if (first.get(key) == null) {
                        Set<String> temp = new HashSet<String>();
                        temp.add(produc.charAt(0) + "");
                        first.put(key, temp);
                    } else {
                        first.get(key).add(produc.charAt(0) + "");
                    }
                }
            }
        }
        for (String key : keys) {
            for (String produc : productions.get(key)) {
                Set<String> temporalSet = new HashSet<String>();
                if (isNonTerminal(produc)) {
                    int i = 0;
                    while (i < produc.length() && isNonTerminal(produc.charAt(i))) {
                        Set<String> primeroPro = first.get(produc.charAt(i) + "");
                        combine(temporalSet, primeroPro);
                        if (!primeroPro.contains("&")) {
                            break;
                        }
                        i++;
                    }
                    if (i < produc.length()) {
                        temporalSet.remove("&");
                        if (!isNonTerminal(produc.charAt(i))) {
                            temporalSet.add(produc.charAt(i) + "");
                        }
                    }
                }
                if (first.get(key) == null) {
                    first.put(key, temporalSet);
                } else {
                    combine(first.get(key), temporalSet);
                }
            }
        }
    }

    private boolean isNonTerminal(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private boolean isNonTerminal(String s) {
        return s.charAt(0) >= 'A' && s.charAt(0) <= 'Z';
    }

    public Hashtable<String, Set<String>> getProductions() {
        return productions;
    }

    Set<String> getFisrt(char key) {
        return first.get(key);
    }

    Hashtable<String, Set<String>> getFisrts() {
        return first;
    }

    Set<String> getFollow(char key) {
        return follow.get(key);
    }

    public void addPro(String source, String destiny) {
        if (productions.get(source) == null) {
            Set<String> temp = new HashSet<String>();
            temp.add(destiny);
            productions.put(source, temp);
        } else {
            productions.get(source).add(destiny);
        }

    }

    private boolean hasEpsilon(Set<String> primeroPro) {
        return primeroPro.contains("&");
    }

    private void combine(Set<String> temp, Set<String> primeroPro) {
        temp.addAll(primeroPro);
    }

}
