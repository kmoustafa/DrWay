/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import servicelayer.server.dal.daos.CurrentVersionDAO;
import servicelayer.server.util.JsonCompressEncode;
import servicelayer.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicelayer.server.dal.daos.MainPointDAO;
import servicelayer.server.dal.pojos.MainPoint;
import servicelayer.server.util.HibernateUtil;
import servicelayer.server.util.RSAUtil;

/**
 *
 * @author Kareem Moustafa
 */
public class UpdateMainPoints extends HttpServlet {

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
    String authenticationKey, listVersion;
    JsonArray result;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            message = "";
            result = new JsonArray();
            if (request.getParameterNames() != null) {
                System.out.println("\nBegin UpdateMainPoints in service Layer:");
                Enumeration enum1 = request.getParameterNames();
                while (enum1.hasMoreElements()) {
                    String param = (String) enum1.nextElement();
                    jsonFile = request.getParameter(param);
//                    System.out.println("param=" + param);
                }
                //jsonFile = (String) request.getParameter("message");
                JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);
                //Starting recieve the JSON parameters
                //NullPointerException will be catched if there null value
                authenticationKey = jsonObject.get("key").getAsString();

//                authenticationKey = Arrays.toString(RSAUtil.decrypt(authenticationKey.getBytes("UTF-8")));
                listVersion = jsonObject.get("mainPointsListVer").getAsString();

                if (authenticationKey.isEmpty() || listVersion.isEmpty()) {
                    message = "-303";
                }

                //Starting your business
                if (message.isEmpty()) {
                    CurrentVersionDAO currentVersionDAO = HibernateUtil.getCURRENT_VERSION_DAO();
                    if (currentVersionDAO.getMainPointsVersion() != Integer.parseInt(listVersion)) {
                        System.out.println("new version of main points");
                        JsonObject success = new JsonObject();
                        success.addProperty("mainPointsListVer", currentVersionDAO.getMainPointsVersion());
                        MainPointDAO mainPointDAO = HibernateUtil.getMAIN_POINT_DAO();
                        mainPointDAO.getMainPointByID(1);
                        List<MainPoint> m = mainPointDAO.getAllMainPoints();
                        for (MainPoint mainPoint : m) {
                            JsonObject obj = new JsonObject();
                            obj.addProperty("id", mainPoint.getId());
                            obj.addProperty("name", mainPoint.getName());
                            result.add(obj);
                        }
                        success.add("message", result);
                        jsonFile = success.toString();
                        String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                        System.out.println("output-success:");
                        ba = ba.replace("\n", "");
                        out.println(ba);
                    } else {
                        System.out.println("no change in main points");
                        JsonObject success = new JsonObject();
                        success.addProperty("message", "0");
                        jsonFile = success.toString();
                        System.out.println("output-success:");
                        String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                        out.println(ba);
                    }
                } else if (!message.isEmpty()) {
                    JsonObject nullError = new JsonObject();
                    nullError.addProperty("message", message);
                    jsonFile = nullError.toString();
                    System.out.println("output-fail:");
                    String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                    out.println(jsonString);
                }
            } else {
                System.out.println("no params");
            }
            System.out.println("End UpdateMainPoints\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException in UpdateMainPoints");
            message = "-302";
            ex.printStackTrace();
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Exception (Number fomat most likely) in UpdateMainPoints");
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
