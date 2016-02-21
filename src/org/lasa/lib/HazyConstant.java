package org.lasa.lib;

import edu.wpi.first.wpilibj.DriverStation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class HazyConstant {

    private static final ArrayList<Constant> constants = new ArrayList(0);
    BufferedReader r;

    public abstract String getFileLocation();

    public static class Constant {

        public String name;
        public double value;

        public Constant(String name, double value) {
            this.name = name;
            this.value = value;
            constants.add(this);
        }

        public void setVal(double value) {
            this.value = value;
        }

        public double getDouble() {
            return this.value;
        }

        public int getInt() {
            return (int) this.value;
        }
    }

    public void loadFromFile() {
        try {
            r = new BufferedReader(new FileReader(new File(getFileLocation())));
            String line, key;
            double value;
            int spaceIndex;
            while ((line = r.readLine()) != null) {
                spaceIndex = line.indexOf(" ");
                key = line.substring(0, spaceIndex);
                value = Double.valueOf(line.substring(spaceIndex + 1));
                for (Constant constant : constants) {
                    if (key.equals(constant.name)) {
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
