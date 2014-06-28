/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 *
 * @author Rana Ashraf
 */
public class JsonDecodeDecompress {

    public JsonObject getDecodeDecompress(Object message) {
        try {
            System.out.println("\nBegin Decode Decompress:");
            String encodedArray = (String) message;
            System.out.println("Before decode: " + encodedArray);

            byte[] encodedByteArray = encodedArray.getBytes();
            // decode the array of bytes

            byte[] decodedByteArray = Base64.decode(encodedByteArray, Base64.DEFAULT);
            System.out.println("After decode: " + new String(decodedByteArray));

            // decompress the decoded array of bytes
            byte[] decompressedArray = CompressionUtils.decompress(decodedByteArray);
            System.out.println("After Decompress: " + new String(decompressedArray));
            String jsonString = new String(decompressedArray);
            System.out.println("After Decompress: " + jsonString);
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(jsonString);
            System.out.println("End Decode Decompress\n");
            return jsonObject;
        } catch (IOException ex) {
            System.out.println("IOExpection in Decode Decompress");
            ex.printStackTrace();
            return null;
        } catch (DataFormatException ex) {
            System.out.println("DataFormatException in Decode Decompress");
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            System.out.println("DataFormatException in Decode Decompress");
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress("eJyrVspNLS5OTE9VslLSNTYwVqoFADjYBWM=");
        System.out.println("Decompressed json:  " + jsonObject);
    }
}
