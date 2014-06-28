/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drway.server.testing;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



/**
 *
 * @author Rana Ashraf
 */
public class Test {
    
    public static void main(String[] args) {
        
//        FavouriteRoadDAO roadDAO = new FavouriteRoadDAO();
//        Road road = roadDAO.getRoad(11);
//        System.out.println(road);

      String jsonText = " {\"message\":[{\"name\":\"aa\", \"info\":\"aa\", \"status\":\"aa \"}]}";

      
  JsonParser parser = new JsonParser();
               
  try{
    JsonElement res = parser.parse(jsonText);
    JsonArray list = (JsonArray)res.getAsJsonArray();
      System.out.println(list.toString());
  }
  catch(Exception pe){
    pe.printStackTrace();
  }
    }
}
