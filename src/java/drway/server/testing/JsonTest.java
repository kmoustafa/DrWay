/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drway.server.testing;

import com.google.gson.Gson;
import drway.server.util.CompressionUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author KimOoO
 */
public class JsonTest {
    
    public static void main(String[] args) {
        try {
            String st = "{ \"firstName\":\"John\" , \"lastName\":\"Doe\" } { \"firstName\":\"John\" , \"lastName\":\"Doe\" } { \"firstName\":\"John\" , \"lastName\":\"Doe\" } { \"firstName\":\"John\" , \"lastName\":\"Doe\" }"
                    +"{ \"firstName\":\"John\" , \"lastName\":\"Doe\" } { \"firstName\":\"John\" , \"lastName\":\"Doe\" } { \"firstName\":\"John\" , \"lastName\":\"Doe\" } { \"firstName\":\"John\" , \"lastName\":\"Doe\" }";
            
//            Gson gson = new  Gson();
//            gson.toJson(st);
            
            byte[] s = st.getBytes();
            
            System.out.println("Before : " + s.length);
            System.out.println("Before : " + new String(s));
           
            byte[] s1 = CompressionUtils.compress(s);
            byte[] sEncoding = Base64.encodeBase64(s1);
            System.out.println("AfterC : " + s1.length);
            System.out.println("AfterC : " + new String(s1));
            System.out.println("AfterEN : " + sEncoding.length);
            System.out.println("AfterEN : " + new String(sEncoding));
            try {
                 byte[] sDecoding = Base64.decodeBase64(sEncoding);
                 System.out.println("AfterDe : " + sDecoding.length);
                 System.out.println("AfterDe : " + new String(sDecoding));
               
                 byte[] s2 = CompressionUtils.decompress(sDecoding);
                 System.out.println("AfterDC : " + s2.length);
                 System.out.println("AfterDC : " + new String(s2));
                 

                 
            } catch (DataFormatException ex) {
                Logger.getLogger(JsonTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           
        } catch (IOException ex) {
            Logger.getLogger(JsonTest.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
}
