/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;


import java.util.Hashtable;
import java.util.LinkedHashSet;
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
        terminals = new LinkedHashSet<String>();
        non_terminals = new LinkedHashSet<String>();
        createnodes();
        makeMTable();
    }
    
    
    public Set<String> getTerminals() {
        return terminals;
    }

    public Set<String> getNon_terminals() {
        return non_terminals;
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
    }

    private void makeMTable() {
        Set<String> prokeys = gram.getProductions().keySet();
        for (String prokey : prokeys) {
            Set<String> productions = gram.getProductions().get(prokey);
            for (String production : productions) {
                Character charact = production.charAt(0);
                if (isMayus(charact)) {/*Non-Terminal*/
                    for (String first : gram.getFisrts().get(charact.toString())) {
                        if (first.equals("&")) {
                            for (String follow : gram.getFollows().get(charact.toString())) {
                                this.add(prokey, follow, production);
                            }
                        } else {
                            System.out.println(prokey+" "+first+" "+ production);
                            this.add(prokey,first, production);
                        }
                    }
                } else {/*Terminal*/
                    if (charact == '&') {
                        for (String follow : gram.getFollows().get(prokey)) {
                            this.add(prokey, follow, production);
                        }
                    } else {
                        System.out.println(prokey +" "+  charact.toString()+" "+ production);
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

    public String getOut(String stop, String intop) {
        return ht.get(stop + intop);
    }

    public void showt() {
        Set<String> keys = ht.keySet();
        for (String key : keys) {
            System.out.println(key + " " + ht.get(key));
        }
    }
}
