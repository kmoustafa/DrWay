/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drway.server.testing;

import com.google.gson.JsonObject;
import drway.server.util.JsonCompressEncode;
import drway.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kareem Moustafa
 */
public class TestApis extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String jsonFile;
    String message ; 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
 try {
           if (request.getAttribute("jsonFile") != null) {
                jsonFile = (String) request.getAttribute("jsonFile");
                System.out.println("jsonReceived : " + jsonFile);
                JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);
                //Starting recieve the JSON parameters
                //NullPointerException will be catched if there null value
                //String jsonArray = jsonObject.get("message").getAsString();
                
                System.out.println("Message : " + jsonObject.toString());
                //out.println("Message : " + message);
                
            }else{
                JsonObject jsonObject = new JsonObject();
//                String[] names = {"1","2"};
//                String jsonString = "[{ \"roadId\": \"1\"},{ \"roadId\": \"2\"}]";
//                String st1 = new Gson().toJson(names);
//                JsonArray arr = new JsonParser().parse(st1).getAsJsonArray();
          
//                  String jsonText = " {\"message\":[{\"name\":\"aa\", \"info\":\"aa\", \"status\":\"aa \"}]}";
//                  JsonParser parser = new JsonParser();
//                   JsonElement res = null;
//                    try{
//                      res = parser.parse(jsonText);
//                      //jsonArray = (JsonArray)res.getAsJsonArray();
//                      //  System.out.println(res.getAsString());
//                    }
//                    catch(Exception pe){
//                      pe.printStackTrace();
//                    }
                
                
                jsonObject.addProperty("appVer", "1");
                jsonObject.addProperty("osID", "2");
                jsonObject.addProperty("osVer", "3");
                jsonObject.addProperty("deviceId", "4");
                jsonObject.addProperty("deviceName", "5");

                //jsonObject.addProperty("favRoadsID", jsonString);
                jsonObject.addProperty("userId", "abc1cvbe");
//                jsonObject.addProperty("sourceId", "1");
//                jsonObject.addProperty("destId", "2");

//                jsonObject.addProperty("favRoadsID", jsonString);
               jsonObject.addProperty("userId", "fsa10wdwr");
                jsonObject.addProperty("code", "uOPtL8");
//                jsonObject.addProperty("date", "05/29/2014 14:00");
//                jsonObject.addProperty("mobile", "999999");
                //jsonObject.addProperty("email", "eng.banoota@gmail.com");
//                jsonObject.addProperty("mode", "3");
//                jsonObject.addProperty("fuelType", "1");

                
                jsonObject.addProperty("name", "Rana");
                jsonObject.addProperty("email", "eng.banoota@gmail.com");
                jsonObject.addProperty("flag", 1);
                
                System.out.println("Client :: " + jsonObject.toString());
                //out.println("Client :: " + jsonObject.toString());
                String st = (String)new JsonCompressEncode().getCompressEncode(jsonObject.toString());
                
                request.setAttribute("jsonFile", st);


//                RequestDispatcher dispatcher = request.getRequestDispatcher("UpdateMainPoints");

                RequestDispatcher dispatcher = request.getRequestDispatcher("Registration");


                dispatcher.forward(request, response);
                
                
            }

        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            //Back to Client with Server error report
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
