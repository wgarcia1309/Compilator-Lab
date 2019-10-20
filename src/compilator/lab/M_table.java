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
public class M_table {

    Hashtable<String, String> ht;
    ArrayList terminals, non_terminals;
    Gramatic gram;

    public M_table(Gramatic gram) {
        ht = new Hashtable<String, String>();
        this.gram=gram ;
        terminals = new ArrayList();
        non_terminals = new ArrayList();
        
    }
/*
    private void creation() {
        for (Production pro : gram.getPro()) {
            char charact=pro.destiny.charAt(0);
            if ( isMayus(charact)) {
                
                for (Character first : gram.getFisrt(pro.destiny.charAt(0))) {
                    if (first == '&') {
                        for (Character follow : gram.getFollow(pro.source.charAt(0))) {
                            this.add(pro.source, follow.toString(), "");
                        }
                    } else {
                        this.add(pro.source, first.toString(), pro.destiny);
                    }
                }
                
            } else {
                if (charact == '&') {
                    for (Character follow : gram.getFollow(pro.source.charAt(0))) {
                            this.add(pro.source, follow.toString(), "");
                        }
                }else{
                    this.add(pro.source, pro.destiny.charAt(0) + "", pro.destiny);
                }
                
            }
        }
    }
  */  
    private boolean isMayus(char c){
        return ((int) c>=65 && (int) c<=90);
    }
    private void add(String non_terminal, String terminal, String out) {
        ht.put(non_terminal + terminal, out);
    }

    String getOut(String stop, String intop) {
        return ht.get(stop + intop);
    }
    public void showt(){
      Set<String> keys = ht.keySet();
      for (String key:keys) {
            System.out.println(key+" "+ht.get(key));
        }
    }
}
