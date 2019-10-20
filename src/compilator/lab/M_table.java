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
public class M_table {

    Hashtable<String, String> ht;
    Set<String> terminals, non_terminals;
    Gramatic gram;

    public M_table(Gramatic gram) {
        ht = new Hashtable<String, String>();
        this.gram = gram;
        terminals = new HashSet<String>();
        non_terminals = new HashSet<String>();
        createnodes();
        makeMTable();
    }
    
    private void createnodes() {
        Set<String> keys = gram.getProductions().keySet();
        for (String key : keys) {
            non_terminals.add(key);
            for (String production : gram.getProductions().get(key)) {
                for (Character c : production.toCharArray()) {
                    if (isMayus(c)) {
                        non_terminals.add(c.toString());
                    } else if (c != '&') {
                        terminals.add(c.toString());
                    }
                }
            }
        }
        terminals.add("$");
    }

    private void makeMTable() {
        
        
    
        Set<String> prokeys = gram.getProductions().keySet();
        for (String prokey : prokeys) {
            Set<String> productions = gram.getProductions().get(prokey);
            for (String production : productions) {
                System.out.println("Produc " + production);
                Character charact = production.charAt(0);
                if (isMayus(charact)) {/*Non-Terminal*/
                    System.out.println("charac"+charact);
                    for (String first : gram.getFisrts().get(charact.toString())) {
                        if (first.equals("&")) {
                            for (String follow : gram.getFollows().get(charact.toString())) {
                                this.add(prokey, follow, production);
                            }
                        } else {
                            this.add(prokey,first, production);
                        }
                    }
                } else {/*Terminal*/
                    if (charact == '&') {
                        for (String follow : gram.getFollows().get(prokey)) {
                            this.add(prokey, follow, production);
                        }
                    } else {
                        this.add(prokey, charact.toString(), production);
                    }

                }
            }

        }
    }

    private boolean isMayus(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private void add(String non_terminal, String terminal, String out) {
        ht.put(non_terminal + terminal, out);
    }

    String getOut(String stop, String intop) {
        return ht.get(stop + intop);
    }

    public void showt() {
        Set<String> keys = ht.keySet();
        for (String key : keys) {
            System.out.println(key + " " + ht.get(key));
        }
    }
}
