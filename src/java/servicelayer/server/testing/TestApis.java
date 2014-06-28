/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.testing;

import com.google.gson.JsonObject;
import servicelayer.server.util.JsonCompressEncode;
import servicelayer.server.util.JsonDecodeDecompress;
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
                message = jsonObject.get("message").getAsString();

                System.out.println("Message : " + message);
                //out.println("Message : " + message);
                
            }else{
                JsonObject jsonObject = new JsonObject();
//                String[] names = {"11","12"};
//                String st1 = new Gson().toJson(names);
//                JsonArray arr = new JsonParser().parse(st1).getAsJsonArray();
          
                jsonObject.addProperty("appVer", "1");
                jsonObject.addProperty("osID", "2");
                jsonObject.addProperty("osVer", "3");
                jsonObject.addProperty("deviceId", "4");
                jsonObject.addProperty("deviceName", "5");
                jsonObject.addProperty("userId", "1");
                jsonObject.addProperty("x", "12");
                jsonObject.addProperty("y", "13");
                
                jsonObject.addProperty("name", "Rana");
                jsonObject.addProperty("mobile", "01000972429");
                jsonObject.addProperty("flag", 1);
                
                System.out.println("Client :: " + jsonObject.toString());
                //out.println("Client :: " + jsonObject.toString());
                String st = (String)new JsonCompressEncode().getCompressEncode(jsonObject.toString());
                
                request.setAttribute("jsonFile", st);
                //RequestDispatcher dispatcher = request.getRequestDispatcher("GetUserPosition");
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
