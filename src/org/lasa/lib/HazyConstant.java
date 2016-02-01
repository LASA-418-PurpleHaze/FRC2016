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

/**
 *
 * @author 418
 */
public abstract class HazyConstant {

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
            }

            r.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
