/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import servicelayer.server.dal.daos.UpdatingUserDAO;
import servicelayer.server.util.ErrorCodesConstants;
import servicelayer.server.util.HibernateUtil;
import drway.server.util.JsonCompressEncode;
import drway.server.util.JsonDecodeDecompress;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicelayer.server.dal.daos.UserDAO;

/**
 *
 * @author Kareem Moustafa
 */
public class UpdateRoadStatus extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String jsonFile = "";
    String message = "";
    String appVer, osID, deviceId, deviceName, osVer;
    String authenticationKey, timeStamp, subRoadId, currentSpeed, userId, result = "";
    JsonObject snapShotJsonObject = new JsonObject();
    JsonArray snapShotsJsonArray = new JsonArray();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            message = "";
            System.out.println("\nBegin UpdateRoadStatus servlet:");
            if (request.getParameter("jsonFile") != null) {
                jsonFile = (String) request.getParameter("jsonFile");
                JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);
                //Starting recieve the JSON parameters
                //NullPointerException will be catched if there null value
                appVer = jsonObject.get("appVer").getAsString();
                osID = jsonObject.get("osID").getAsString();
                osVer = jsonObject.get("osVer").getAsString();
                deviceId = jsonObject.get("deviceId").getAsString();
                deviceName = jsonObject.get("deviceName").getAsString();
                userId = jsonObject.get("userId").getAsString();
                snapShotsJsonArray = jsonObject.get("snapShotsJsonArray").getAsJsonArray();
                //
                if (appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty() || osVer.isEmpty() || userId.isEmpty() || !snapShotsJsonArray.isJsonArray()) {
                    message = ErrorCodesConstants.INVALID_PARAMETER + "";
                }

                //Starting your business
                //checking if the user is in system
                if (message.isEmpty()) {
                    userId = userId.substring(3, userId.length() - 4);
                    int id = Integer.parseInt(userId);
                    UserDAO ud = HibernateUtil.getUSER_DAO();
                    int status = ud.isUserExist(id);
                    if (status == -1) {
                        message = ErrorCodesConstants.BAD_USER + "";
                    } else if (status == 1) {
                        message = ErrorCodesConstants.DATABASE_PROBLEM + "";
                    }
                    //User Found!!

                    authenticationKey = "Sending.....";
                    // serviceLayer URL
                    URL url = new URL(ErrorCodesConstants.serviceLayerURLOnITIServer + "/UpdateRoadStatus");
                    URLConnection connection = url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
//                    connection.setRequestProperty("Accept-Charset", charset);
                    connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
//                    connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + charset);

                    snapShotJsonObject.addProperty("key", authenticationKey);
                    snapShotJsonObject.addProperty("userId", userId);
                    snapShotJsonObject.add("snapShotsJsonArray", snapShotsJsonArray);

                    jsonFile = snapShotJsonObject.toString();
                    String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                    String content = "message=" + URLEncoder.encode(ba);
                    DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                    dos.writeBytes(content);
                    dos.flush();
                    dos.close();
                    System.out.println("Sent....");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    System.out.println("Recieved .....");
                    result = reader.readLine();
                    reader.close();
                }

                if (!result.isEmpty()) {
                    System.out.println("output-success: " + (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(result));
                    out.println(result);
                } else {
                    JsonObject nullError = new JsonObject();
                    nullError.addProperty("message", message);
                    jsonFile = nullError.toString();
                    System.out.println("output-fail:");
                    String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                    out.println(jsonString);
                }
            }
            System.out.println("End UpdateRoadStatus\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException (missing parameter most likely) in UpdateRoadStatus");
            ex.printStackTrace();
            message = String.valueOf(ErrorCodesConstants.MISSING_PARAMETER);
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Exception (IO/NumberFormat most likely) in UpdateRoadStatus");
            ex.printStackTrace();
            message = String.valueOf(ErrorCodesConstants.SERVER_PROBLEM);
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
