/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.servlets;

import com.google.gson.JsonObject;
import servicelayer.server.dal.daos.PendingUserDAO;
import servicelayer.server.dal.pojos.PendingUser;
import drway.server.handlers.RegistrationHandler;
import servicelayer.server.util.ErrorCodesConstants;
import servicelayer.server.util.HibernateUtil;
import drway.server.util.JsonCompressEncode;
import drway.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kareem Moustafa
 */
public class ResendVerificationCode extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * {"osID":"41c24779d0a3b050","deviceName":"C6603","osVer":"18","appVer":"1.0","deviceId":"357945054443747","userId":"-1"}
     */
    String jsonFile = "";
    int message = 0;
    String appVer = "";
    String osID = "";
    String deviceId = "";
    String deviceName = "";
    String osVer = "";
    String pendingUserId = "";
    PendingUserDAO pendingUserDAO = HibernateUtil.getPENDING_USER_DAO();
    PendingUser pendingUser;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            message = 0;
            System.out.println("\nBegin ResendVerificationCode:");
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
                pendingUserId = jsonObject.get("userId").getAsString();
                //
                if (appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty() || osVer.isEmpty() || pendingUserId.isEmpty()) {
                    message = ErrorCodesConstants.INVALID_PARAMETER;
                }

                //Starting your business
                //checking if the Pendinguser is in system
                if (message == ErrorCodesConstants.SUCCESS) {

                    System.out.println("Checking of PendingUser....");
                    pendingUserId = pendingUserId.substring(3, pendingUserId.length() - 4);
                    int id = Integer.parseInt(pendingUserId);
                    System.out.println("userId after: " + id);
                    int status = pendingUserDAO.isPendingUserExist(id);
                    if (status == ErrorCodesConstants.INVALID_PARAMETER) {
                        System.out.println("Not found");
                        message = ErrorCodesConstants.USER_DOES_NOT_EXIST;

                    } else if (status == ErrorCodesConstants.DATABASE_PROBLEM) {
                        System.out.println("DB Error");
                        message = ErrorCodesConstants.DATABASE_PROBLEM;
                    }
                    //Starting Sending an Email to client
                    try {
                        if (message == ErrorCodesConstants.SUCCESS) {
                            pendingUser = pendingUserDAO.getPendingUser(Integer.parseInt(pendingUserId));
                            pendingUser.setVerificationCode(new RegistrationHandler().sendCodeToEmail(pendingUser.getEmail(), ""));
                            pendingUserDAO.updatePendingUser(pendingUser);
                            System.out.println("Email Should be sent by the new verification code");
                        }
                    } catch (MessagingException e) {
                        //Sending Email Failed
                        message = ErrorCodesConstants.SENDING_VARIFICATION_CODE_FAILED;

                    }

                }

            }
            // Compress JSON FILE
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            System.out.println("output-success: " + jsonFile);
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
            System.out.println("End ResendVerificationCode\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException (missing parameter most likely) in ResendVerificationCode");
            ex.printStackTrace();
            message = ErrorCodesConstants.MISSING_PARAMETER;
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Exception (NumberFormat most likely) in ResendVerificationCode");
            ex.printStackTrace();
            message = ErrorCodesConstants.SERVER_PROBLEM;
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
