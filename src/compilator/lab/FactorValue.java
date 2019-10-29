/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;

/**
 *
 * @author LinkRs
 */
public class FactorValue {
    String word;
    private int ocurrences;

    public FactorValue(String word) {
        this.word = word;
        ocurrences=1;
    }
    public void increase(){
        this.ocurrences=this.ocurrences+1;
    }

    public String getWord() {
        return word;
    }

    public int getOcurrences() {
        return ocurrences;
    }
    @Override
    public boolean equals(Object o){
     if(!(o instanceof FactorValue))return false;
      return ((FactorValue)(o)).word.equals(this.word);
    }
}
