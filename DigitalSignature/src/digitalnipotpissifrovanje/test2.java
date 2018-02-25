/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalnipotpissifrovanje;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Cmiloje
 */
public class test2 {
    
    public static void main (String args[]) throws IOException {
        File fajl = new File("C:\\Users\\Cmiloje\\Desktop\\test.mp4");
        String[] ext = fajl.getName().split("\\.");
        System.out.println("ext: "+ext[1]);
    }
    
}
