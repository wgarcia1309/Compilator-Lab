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
                minG.addPro(sd[0], sd[1]);
                originalG.addPro(sd[0], sd[1]);
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
        Set<String> s = hasFactorization();
        while (s != null) {
            System.out.println(s.toString());
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

    public static String findstem(String arr[]) {
        int n = arr.length;

        // Take first word from array as reference 
        String s = arr[0];
        int len = s.length();

        String res = "";

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j <= len; j++) {

                // generating all possible substrings 
                // of our reference string arr[0] i.e s 
                String stem = s.substring(i, j);
                int k = 1;
                for (k = 1; k < n; k++) // Check if the generated stem is 
                // common to all words 
                {
                    if (!arr[k].contains(stem)) {
                        break;
                    }
                }

                // If current substring is present in 
                // all strings and its length is greater   
                // than current result 
                if (k == n && res.length() < stem.length()) {
                    res = stem;
                }
            }
        }

        return res;
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

    private Set<String> hasFactorization() {
        Set<FactorValue> answer = new LinkedHashSet<>();
        Set<String> keys = minG.getProductions().keySet();
        for (String key : keys) {
            ArrayList<String> seen = new ArrayList<>();
            for (String produccion : minG.getProductions().get(key)) {
                for (int i = 1; i < produccion.length(); i++) {
                    String w = produccion.substring(0, i);
                    if (seen.contains(w)) {
                        answer.add();
                    } else {
                        answer.add(new FactorValue(w));
                        seen.add(w);
                    }
                }
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
    private void removeFactorization(Set<String> set) {

        for (String key : set) {
            Set<String> BETA = new HashSet<>();
            Set<String> words = new HashSet<>();
            String letter = getLetter();

            int max = minG.getProductions().get(key).size();
            char activos[] = new char[max];
            for (int i = 0; i < max; i++) {
                activos[i] = '1';
            }
            int valor = Integer.parseInt(Arrays.toString(activos), 2);
            String prefix = "";
            while (valor > 1 && prefix.equals("")) {
                prefix = longestCommonPrefix(minG.getProductions().get(key).toArray(new String[0]), (valor + "").toCharArray());
                System.out.println(prefix);
                valor--;
            }

            for (String wordString : minG.getProductions().get(key)) {
                int i = 0;
                boolean hasPrefix = true;
                if (prefix.length() > wordString.length() && i < prefix.length()) {
                    if (prefix.charAt(i) != wordString.charAt(i)) {
                        hasPrefix = false;
                    }
                    i++;
                }
                if (hasPrefix) {
                    System.out.println(wordString);
                    if (wordString.length() == 1) {
                        if (!wordString.equals("&")) {
                            BETA.add("&");
                        } else {
                            words.add("&");
                        }
                    } else {
                        words.add(wordString.substring(1));
                    }

                    BETA.add(prefix + letter);
                } else {
                    BETA.add(wordString);
                }
            }
            minG.getProductions().put(key, BETA);
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
