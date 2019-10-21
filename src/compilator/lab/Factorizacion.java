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

/**
 *
 * @author PC
 */
public class Factorizacion {
    String fileLocation;
    Gramatic originalG,minG;
    public Factorizacion(String fileLocation) {
        this.fileLocation = fileLocation;
        originalG= new Gramatic();
        minG= new Gramatic();
        process();
    }

    public Gramatic getminG() {
        return originalG;
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
                }
                originalG.addPro(sd[0], sd[1]);
                line=br.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            
        
    }

    private void createMin() {
        
    }
    
}