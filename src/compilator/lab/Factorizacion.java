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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author PC
 */
public class Factorizacion {
    String fileLocation;
    Gramatic originalG,minG;
    boolean[] alphabet= new boolean [26];
    
    public Factorizacion(String fileLocation) {
        this.fileLocation = fileLocation;
        originalG= new Gramatic();
        minG= new Gramatic();
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
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(fileLocation)));
            String line=br.readLine();
            boolean initial=true;
            while(line!=null){
                String sd[] =line.split("->");
                if(initial){
                    initial=false;
                    originalG.setInitial(sd[0]);
                    minG.setInitial(sd[0]);
                }
                minG.addPro(sd[0], sd[1] );
                originalG.addPro(sd[0], sd[1]);
                alphabet[sd[0].charAt(0)-'A']=true;
                line=br.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            
        
    }

    private void createMin() {
        boolean r=hasRecursivity(),f=hasFactorization();
        while( r || f){
            if(r)removeRecursivity();
            if(f)removeFactorization();
            r=hasRecursivity();
            f=hasFactorization();
        }
        
    }
    private boolean hasRecursivity(){
        Set<String> keys = minG.getProductions().keySet();
        for (String key : keys) {
            for (String result : minG.getProductions().get(key)) {
                if(result.charAt(0)==key.charAt(0))return true;
            }
            
        }
        return false;
    }

    private void removeRecursivity() {
        Set<String> keys = minG.getProductions().keySet();
        LinkedHashMap<String, Set<String>> newProductions = (LinkedHashMap<String, Set<String>>) minG.getProductions().clone();
        for (String key : keys) {
            ArrayList<String> alpha= new ArrayList<>(), beta= new ArrayList<>();
            newProductions.put(key, new LinkedHashSet<>());
            boolean hasr=false;
            for (String result : minG.getProductions().get(key)) {
                if(key.charAt(0)==result.charAt(0)){
                    hasr=true;
                    alpha.add(result.substring(1, result.length()-1));
                }else{
                    beta.add(result);
                }
            }
            if(hasr){
                String nuevaletra=getLetter();
                Set<String> set=new LinkedHashSet<>();
                for (String string : beta) {
                    set.add(beta+nuevaletra);
                }
                newProductions.get(key).addAll(set);
                newProductions.get(key).addAll(set);
                set=new LinkedHashSet<>();
                for (String string : alpha) {
                    set.add(alpha+nuevaletra);
                }
                System.out.println(set.toString());
                newProductions.put(nuevaletra, set);
            }else{
                newProductions.put(key, minG.getProductions().get(key) );
            }
        }
        minG.setProductions(newProductions);
    }

    
    private boolean hasFactorization() {
        return false;
    }
    
    private void removeFactorization() {
        
    }

    private String getLetter() {
        int i=0;
        for (boolean b : alphabet) {
            if(!b)break;
            i++;
        }
        alphabet[i]=true;
        return ((char)((int)('A')+i)+"");
    }
}
