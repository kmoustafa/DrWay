/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.util;

import java.io.IOException;

/**
 *
 * @author Rana Ashraf
 */
public class JsonCompressEncode {

    public Object getCompressEncode(String jsonFile) {
        try {
            System.out.println("\nBegin Compress Encode:");
            System.out.println("Before compress: " + jsonFile);
            byte[] compressedJSON = CompressionUtils.compress(jsonFile.getBytes());
            System.out.println("After compress: " + new String(compressedJSON));

            // Encode JSON FILE
            byte[] encodingJSON = Base64.encode(compressedJSON, Base64.DEFAULT);
            System.out.println("After encode: " + new String(encodingJSON));
            System.out.println("End Compress Encode\n");
            return new String(encodingJSON);
        } catch (IOException ex) {
            System.out.println("IOException in Compress Encode");
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            System.out.println("Exception in Compress Encode");
            ex.printStackTrace();
            return null;
        }
    }

}
