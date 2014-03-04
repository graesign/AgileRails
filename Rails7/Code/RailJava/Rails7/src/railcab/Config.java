/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package railcab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author joramruitenschild
 */
public class Config {
    static Properties prop;
    
    public static void load() {
        Config.prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("../../../../config.properties");
            prop.load(input);
            input.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String get(String name) {
        return Config.prop.getProperty(name);
    }
    
}