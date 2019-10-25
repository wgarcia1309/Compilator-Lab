/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author PC
 */
public class Gramatic {

    private LinkedHashMap<String, Set<String>> productions, first, follow;
    private String initial;

    public Gramatic() {
        productions = new LinkedHashMap<String, Set<String>>();
        first = new LinkedHashMap<String, Set<String>>();
        follow = new LinkedHashMap<String, Set<String>>();
    }

    public String getInitial() {
        return initial;
    }

    public Gramatic(String initial) {
        this.initial = initial;
        productions = new LinkedHashMap<String, Set<String>>();
        first = new LinkedHashMap<String, Set<String>>();
        follow = new LinkedHashMap<String, Set<String>>();
    }

    private void createNodes() {
        Set<String> prokeys = productions.keySet();
        for (String prokey : prokeys) {
            first.put(prokey, new LinkedHashSet<String>());
            follow.put(prokey, new LinkedHashSet<String>());
        }
    }

    public void compute() {
        createNodes();
        ComputeFirst();
        computeFollow();
    }

    private void ComputeFirst() {
        Set<String> keys = productions.keySet();
        for (String key : keys) {
            for (String produc : productions.get(key)) {
                if (!isNonTerminal(produc)) {
                    first.get(key).add(produc.charAt(0) + "");
                }
            }
        }
         LinkedHashMap<String, Set<String>> newfirst= new LinkedHashMap<String, Set<String>>();
         int invariable=0;
        do {
            for (String key : keys) {
                newfirst= (LinkedHashMap<String, Set<String>>) first.clone();
                for (String produc : productions.get(key)) {
                    Set<String> temporalSet = new LinkedHashSet<String>();
                    if (isNonTerminal(produc)) {
                        int i = 0;
                        while (i < produc.length() && isNonTerminal(produc.charAt(i))) {
                            Set<String> primeroPro = newfirst.get(produc.charAt(i) + "");
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
                    combine(newfirst.get(key), temporalSet);
                }
            }
            if(!areEquals(first,newfirst))invariable=0;
            else invariable++;
        } while ( invariable<3 );
        first=newfirst;
    }

    private boolean isNonTerminal(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private boolean isNonTerminal(String s) {
        return s.charAt(0) >= 'A' && s.charAt(0) <= 'Z';
    }

    public LinkedHashMap<String, Set<String>> getProductions() {
        return productions;
    }

    Set<String> getFisrt(char key) {
        return first.get(key);
    }

    public LinkedHashMap<String, Set<String>> getFisrts() {
        return first;
    }

    public LinkedHashMap<String, Set<String>> getFollows() {
        return follow;
    }

    Set<String> getFollow(char key) {
        return follow.get(key);
    }

    public void addPro(String source, String destiny) {
        if (productions.get(source) == null) {
            Set<String> temp = new LinkedHashSet<String>();
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

    private void computeFollow() {
        // Add $ to FOLLOW(S), where S is the start symbol
        follow.get(initial).add("$");
        regla2();
        regla3();
    }

    /**
     * If B → αAω is a production, set FOLLOW(A) = FOLLOW(A) ∪ FIRST*(ω) - { ε
     * }.
     */
    public void regla2() {
        Set<String> prokeys = productions.keySet();
        for (String prokey : prokeys) {
            for (String production : productions.get(prokey)) {

                int i = 0;
                while (i < production.length() - 1) {
                    if (isNonTerminal(production.charAt(i))) {
                        int j = i + 1;
                        while (j < production.length()) {
                            Set<String> temp = new LinkedHashSet<String>();
                            if (isNonTerminal(production.charAt(j))) {
                                temp.addAll(first.get(production.charAt(j) + ""));
                                if (temp.contains("&")) {
                                    temp.remove("&");
                                    combine(follow.get(production.charAt(i) + ""), temp);
                                    j++;
                                } else {
                                    combine(follow.get(production.charAt(i) + ""), temp);
                                    break;
                                }
                            } else {
                                temp.add(production.charAt(j) + "");
                                combine(follow.get(production.charAt(i) + ""), temp);
                                break;
                            }
                        }
                    }
                    i++;
                }
            }
        }
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    /**
     * If B → αAω is a production and ε ∈ FIRST*(ω), set FOLLOW(A) = FOLLOW(A) ∪
     * FOLLOW(B).
     */
    private void regla3() {
        Set<String> prokeys = productions.keySet();
        for (String prokey : prokeys) {
            for (String production : productions.get(prokey)) {
                int i = production.length() - 1;
                while (i >= 0) {
                    if (isNonTerminal(production.charAt(i))) {
                        combine(follow.get(production.charAt(i) + ""), follow.get(prokey));
                        if (hasEpsilon(first.get((production.charAt(i) + "")))) {
                            i--;
                        } else {
                            break;
                        }

                    } else {
                        break;
                    }
                }
            }
        }
    }

    

    private boolean areEquals(LinkedHashMap<String, Set<String>> first, LinkedHashMap<String, Set<String>> newfirst) {
        Set<String> keys= this.first.keySet();
         for (String key : keys) {
             System.out.println( this.first.get(key) +" "+newfirst.get(key));
             if(   !this.first.get(key).equals(newfirst.get(key))){
                return false;
             }
        }
        return true;
    }

}
