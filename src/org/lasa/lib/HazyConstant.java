/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 418
 */
public abstract class HazyConstant {
    
    private static ArrayList<Constant> constManager = new ArrayList(11);
    
    public abstract String getFileLocation();

    public static class Constant {
        public String name;
        public double value;

        public Constant(String name, double value) {
            this.name = name;
            this.value = value;
            constManager.add(this);
        }
        
        public void setVal(double value) {
            this.value = value;
        }
    }

    public void loadFromFile() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(new File("pathname")));
            String line, key;
            double value;
            int spaceIndex;
            while ((line = r.readLine()) != null) {
                spaceIndex = line.indexOf(" ");
                key = line.substring(0, spaceIndex);
                value = Double.valueOf(line.substring(spaceIndex + 1));
                for(Constant constant : constManager) {
                    if(!key.equals(constant.name)) {
                        constant.setVal(value);
                    }
                }
            }
            r.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
