/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.servlets;

import com.google.gson.JsonObject;
import servicelayer.server.dal.pojos.PendingUser;
import servicelayer.server.dal.pojos.User;
import drway.server.handlers.RegistrationHandler;
import servicelayer.server.util.ErrorCodesConstants;
import servicelayer.server.util.HibernateUtil;
import drway.server.util.JsonCompressEncode;
import drway.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rana Ashraf
 */
public class Registration extends HttpServlet {

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
    String appVer, osID, osVer, deviceId, deviceName;
    String name = "", email = "";
    RegistrationHandler registrationHandler;
    String emailVerificationCode, mobileVerificationCode;
    User user;
    PendingUser pendingUser;
    int userId = 0, pendingUesrID = 0;

    //appVer:1.0 osID:xxx osVer:xxx deviceId:xxx deviceName:xxx .. any other data}
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        System.out.println("\nBegin Registration servlet");
        try {
            message = "";
            registrationHandler = new RegistrationHandler();
            if (request.getParameter("jsonFile") == null) {
//                if (request.getAttribute("jsonFile") == null) {
                System.out.println("Json is null");
                message = ErrorCodesConstants.INVALID_PARAMETER + "";
            }
            if (request.getParameter("jsonFile") != null) {
//                if (request.getAttribute("jsonFile") != null) {
                jsonFile = request.getParameter("jsonFile").toString();
//                jsonFile = request.getAttribute("jsonFile").toString();
                JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);

                appVer = jsonObject.get("appVer").getAsString();
                osVer = jsonObject.get("osVer").getAsString();
                osID = jsonObject.get("osID").getAsString();
                deviceId = jsonObject.get("deviceId").getAsString();
                deviceName = jsonObject.get("deviceName").getAsString();

                // checking if any parameter is empty (Invalid Prameters Error)
                if (appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty() || osVer.isEmpty()) {
                    message = ErrorCodesConstants.INVALID_PARAMETER + "";
                }

                //Starting your business
                if (message.isEmpty()) {
                    // write your business here .....
                    int flag = jsonObject.get("flag").getAsInt();
                    if (flag == 1) {
                        // this indicates that user required to register through email
                        email = jsonObject.get("email").getAsString();
                        name = jsonObject.get("name").getAsString();
                        if (email != null && name != null) {
                            message = registrationHandler.EmailRegistration(email, name);
                            user = HibernateUtil.getUSER_DAO().getUserByEmail(email);
                            pendingUser = HibernateUtil.getPENDING_USER_DAO().getPendingUserByEmail(email);
                            if (user != null) {
                                userId = user.getId();
                            }
                            if (pendingUser != null) {
                                pendingUesrID = pendingUser.getId();
                            }
                        }

                    } else {
                        email = jsonObject.get("email").getAsString();
                        name = jsonObject.get("name").getAsString();
                        System.out.println(email);
                        System.out.println(name);
                        if (email != null && name != null) {
                            message = registrationHandler.socialRegistration(email, name);
                        }
                    }
                }
            }
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            if (userId != 0) {
                nullError.addProperty("id", RegistrationHandler.getRandomAlphaNumeric(3) + userId + RegistrationHandler.getRandomAlphaNumeric(4));
            }
            if (pendingUesrID != 0) {
                nullError.addProperty("id", RegistrationHandler.getRandomAlphaNumeric(3) + pendingUesrID + RegistrationHandler.getRandomAlphaNumeric(4));
            }
            jsonFile = nullError.toString();
            String jsonSting = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            System.out.println(jsonFile);
            out.println(jsonSting);
            System.out.println("End Resgistration\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException (missing parameter most likely) in Registration");
            ex.printStackTrace();
            message = String.valueOf(ErrorCodesConstants.MISSING_PARAMETER);
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Exception in Registration");
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
