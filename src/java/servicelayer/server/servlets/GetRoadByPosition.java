/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.servlets;

import com.google.gson.JsonObject;
import servicelayer.server.dal.daos.SubRoadDAO;
import servicelayer.server.dal.pojos.SubRoad;
import servicelayer.server.util.JsonCompressEncode;
import servicelayer.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author Kareem Moustafa
 */
public class GetRoadByPosition extends HttpServlet {

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
    String message;
    String authenticationKey, x, y;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        System.out.println("Here");
        try {
            message = "";
            if (request.getParameterNames() != null) {
                System.out.println("\nBegin GetRoadByPosition in service Layer:");
                Enumeration enum1 = request.getParameterNames();
                while (enum1.hasMoreElements()) {
                    String param = (String) enum1.nextElement();
                    jsonFile = request.getParameter(param);
//                    System.out.println("param=" + param);
                }
                //  jsonFile = (String) request.getParameter("jsonFile");
                JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);
                //Starting recieve the JSON parameters
                //NullPointerException will be catched if there null value

                authenticationKey = jsonObject.get("key").getAsString();
                x = jsonObject.get("x").getAsString();
                y = jsonObject.get("y").getAsString();
                //
                if (x.isEmpty() || y.isEmpty() || authenticationKey.isEmpty()) {
                    message = "-303";
                }

                //Starting your business
                SubRoadDAO subRoadDAO = HibernateUtil.getSUB_ROAD_DAO();
                if (message.isEmpty()) {
                    SubRoad road = subRoadDAO.getSubRoadByPosition(Double.parseDouble(x), Double.parseDouble(y));
                    if (road == null) {
                        System.out.println("subroad is null");
                        message = "-305";
                    } else if (road.getId() == -1) {
                        message = "-202";
                    } else {
                        message = String.valueOf(road.getId());
                    }
                }
                JsonObject obj = new JsonObject();
                obj.addProperty("message", message);
                jsonFile = obj.toString();
                System.out.println("output:");
                String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                ba = ba.replace("\n", "");
                out.println(ba);
            }
            System.out.println("End GetRoadByPosition\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException in GetPossibleRoutes");
            message = "-302";
            ex.printStackTrace();
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Exception (Number fomat most likely) in GetPossibleRoutes");
            message = "-301";
            ex.printStackTrace();
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
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
