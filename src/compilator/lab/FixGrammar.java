/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilator.lab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author PC
 */
public class FixGrammar {

    String fileLocation;
    Gramatic originalG, minG;
    boolean[] alphabet = new boolean[26];

    public FixGrammar(String fileLocation) {
        this.fileLocation = fileLocation;
        originalG = new Gramatic();
        minG = new Gramatic();
        process();
    }

    public Gramatic getOriginalG() {
        return originalG;
    }

    public Gramatic getminG() {
        return minG;
    }

    private void process() {
        createOriginal();
        createMin();
    }

    private void createOriginal() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileLocation)));
            String line = br.readLine();
            boolean initial = true;
            while (line != null) {
                String sd[] = line.split("->");
                if (initial) {
                    initial = false;
                    originalG.setInitial(sd[0]);
                    minG.setInitial(sd[0]);
                }
                sd[1]=sd[1].replace("&", "");
                if(sd[1].length()==0){
                    minG.addPro(sd[0], "&");
                    originalG.addPro(sd[0], "&" );
                }else{
                    minG.addPro(sd[0], sd[1]);
                    originalG.addPro(sd[0], sd[1]);
                }
                alphabet[sd[0].charAt(0) - 'A'] = true;
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createMin() {
        while (hasRecursivity()) {
            removeRecursivity();
        }
        Set<String[]> s = hasFactorization();
        while (s != null) {
            removeFactorization(s);
            s = hasFactorization();
        }
    }

    private boolean hasRecursivity() {
        Set<String> keys = minG.getProductions().keySet();
        for (String key : keys) {
            for (String result : minG.getProductions().get(key)) {
                if (result.charAt(0) == key.charAt(0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeRecursivity() {
        Set<String> keys = minG.getProductions().keySet();
        LinkedHashMap<String, Set<String>> newProductions = (LinkedHashMap<String, Set<String>>) minG.getProductions().clone();
        for (String key : keys) {
            ArrayList<String> alpha = new ArrayList<>(), beta = new ArrayList<>();
            newProductions.put(key, new LinkedHashSet<>());
            boolean hasRecursi = false;
            for (String result : minG.getProductions().get(key)) {
                if (key.charAt(0) == result.charAt(0)) {
                    hasRecursi = true;
                    if (result.length() >= 2) {
                        alpha.add(result.substring(1, result.length()));
                    }
                } else {
                    beta.add(result);
                }
            }
            if (hasRecursi) {
                String nuevaletra = getLetter();
                Set<String> set = new LinkedHashSet<>();
                for (String B : beta) {
                    set.add(B + nuevaletra);
                }
                newProductions.get(key).addAll(set);
                set = new LinkedHashSet<>();
                for (String A : alpha) {
                    set.add(A + nuevaletra);
                }
                set.add("&");
                newProductions.put(nuevaletra, set);
            } else {
                newProductions.put(key, minG.getProductions().get(key));
            }
        }
        minG.setProductions(newProductions);
    }

    

    public String longestCommonPrefix(String[] strs, char active[]) {
        if (strs.length == 0) {
            return "";
        }
        String prefix = null;
        int start = 0;
        for (char c : active) {
            if (c == '1') {
                prefix = strs[start];
            }
            start++;
        }

        for (int i = start; i < strs.length; i++) {
            if (active[i] == '0') {
                while (strs[i].indexOf(prefix) != 0) {
                    prefix = prefix.substring(0, prefix.length() - 1);
                    if (prefix.isEmpty()) {
                        return "";
                    }
                }
            }
        }
        return prefix;
    }

    private Set<String[]> hasFactorization() {
        Set<String[]> answer = new LinkedHashSet<>();
        Set<String> keys = minG.getProductions().keySet();
        for (String key : keys) {
            ArrayList<FactorValue> seen = new ArrayList<>();
            for (String produccion : minG.getProductions().get(key)) {
                for (int i = 1; i <= produccion.length(); i++) {
                    FactorValue w = new FactorValue(produccion.substring(0, i));
                    if (!seen.contains(w)) {
                        seen.add(w);
                    } else {
                        seen.get(seen.indexOf(w)).increase();
                    }
                }
            }
            FactorValue max=null;
                for (FactorValue fv : seen) {
                    if(max==null){
                        max=fv;
                    }else{
                        if(max.getOcurrences()<fv.getOcurrences())max=fv;
                    }
                }
                
                if(max.getOcurrences()>1){
                    String s[]= new String[2];
                    s[0]=key;
                    s[1]=max.getWord();
                    answer.add(s);
                }
        }
        return answer.isEmpty() ? null : answer;
    }

    /*
    
    B->prefix w1|prefix w2|prefix Aw3| BETA1 | BETA2| BETA3
    
    ********************************************************
    
    B->prefix BETA
    C-> w1|w2|w3
     */
    private void removeFactorization(Set<String[]> set) {
        
        for (String[] keyPrefix : set) {
            Set<String> BETA = new HashSet<>();
            Set<String> words = new HashSet<>();
            String letter = getLetter();
            String prefix= keyPrefix[1];
            System.out.println(keyPrefix[0]+" "+prefix);
            for (String wordString : minG.getProductions().get(keyPrefix[0])) {
                System.out.println(wordString);
                int i = 0;
                boolean hasPrefix = true;
                if (prefix.length() >= wordString.length() && i < prefix.length()) {
                    if (prefix.charAt(i) != wordString.charAt(i)) {
                        hasPrefix = false;
                    }
                    i++;
                }
                if (hasPrefix) {
                    if (wordString.length() == prefix.length()) {
                        if (!wordString.equals(prefix)) {
                            BETA.add("&");
                        } else {
                            words.add("&");
                        }
                    } else {
                        words.add(wordString.substring(prefix.length()));
                    }
                    BETA.add(prefix + letter);
                } else {
                    BETA.add(wordString);
                }
            }
            System.out.println(keyPrefix[0]+" " +BETA.toString());
            minG.getProductions().put(keyPrefix[0], BETA);
            System.out.println(letter+" "+words.toString());
            minG.getProductions().put(letter, words);
        }
    }

    private String getLetter() {
        int i = 0;
        for (boolean b : alphabet) {
            if (!b) {
                break;
            }
            i++;
        }
        alphabet[i] = true;
        return ((char) ((int) ('A') + i) + "");
    }
}
