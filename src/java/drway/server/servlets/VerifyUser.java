/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import servicelayer.server.dal.pojos.FuelLookUp;
import drway.server.handlers.VerificationHandler;
import drway.server.util.ErrorCodesConstants;
import drway.server.util.JsonCompressEncode;
import drway.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MIRO
 */
public class VerifyUser extends HttpServlet {

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
    String message;
    String appVer, osID, deviceId, deviceName, osVer, pendingUserId, verificationCode;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            message = "";
            System.out.println("\nBegin VerifyUser servlet:");
            if (request.getParameter("jsonFile") != null) {
//                if (request.getAttribute("jsonFile") != null) {
                jsonFile = (String) request.getParameter("jsonFile");
//                jsonFile = (String) request.getAttribute("jsonFile");
                JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);
                //Starting recieve the JSON parameters
                //NullPointerException will be catched if there null value
                appVer = jsonObject.get("appVer").getAsString();
                osVer = jsonObject.get("osVer").getAsString();
                osID = jsonObject.get("osID").getAsString();
                deviceId = jsonObject.get("deviceId").getAsString();
                deviceName = jsonObject.get("deviceName").getAsString();
                pendingUserId = jsonObject.get("userId").getAsString();
                verificationCode = jsonObject.get("code").getAsString();
                //
                if (appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty() || osVer.isEmpty() || pendingUserId.isEmpty() || verificationCode.isEmpty()) {
                    message = ErrorCodesConstants.INVALID_PARAMETER + "";
                }

                //Starting your business
                if (message.isEmpty()) {
                    pendingUserId = pendingUserId.substring(3, pendingUserId.length() - 4);
                    message = new VerificationHandler().VerifyUser(pendingUserId, verificationCode);
                }
            }
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            System.out.println("ouput-success: " + jsonFile);
            String jsonSting = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(jsonSting);
            System.out.println("End VerifyUser\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException (missing parameter most likely) in VerifyUser");
            ex.printStackTrace();
            message = String.valueOf(ErrorCodesConstants.MISSING_PARAMETER);
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Exception in VerifyUser");
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

    public JsonArray toJsonList(List<FuelLookUp> list) {
        JsonArray jsonArray = new JsonArray();

        for (FuelLookUp fuelCostLookUp : list) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fuelCostValue", fuelCostLookUp.getValue());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
