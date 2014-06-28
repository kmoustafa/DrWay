/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.servlets;

import com.google.gson.JsonObject;
import servicelayer.server.dal.daos.UserDAO;
import servicelayer.server.dal.pojos.CarListLookUp;
import servicelayer.server.dal.pojos.FuelLookUp;
import servicelayer.server.dal.pojos.ModeLookUp;
import servicelayer.server.dal.pojos.User;
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
 * @author Kareem Moustafa
 */
public class UpdateUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs Parameters as JSON :
     * {"carId":"0","deviceName":"C6603","osVer":"18","appVer":"1.0","email":"re","name":"rr",
     * "osID":"41c24779d0a3b050",
     * "costlistVer":"-1","deviceId":"357945054443747","costId":"1","carlistVer":"-1",
     * "mode":"taxi","mobile":"44"}
     */
    String jsonFile = "";
    String errorCode = "";
    String appVer, osID, deviceId, deviceName, osVer, userId, name, email, mode, carlistVer, carId, fuelListVer, fuelId;
    byte[] photo;
    User user;
    UserDAO ud = HibernateUtil.getUSER_DAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            errorCode = "";
            System.out.println("\nBegin UpdateUser servlet:");
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
                System.out.println("USER ID : " + userId);
                name = jsonObject.get("name").getAsString();
                carlistVer = jsonObject.get("carlistVer").getAsString();
                carId = jsonObject.get("carId").getAsString();
                fuelListVer = jsonObject.get("fuelListVer").getAsString();
                fuelId = jsonObject.get("fuelId").getAsString();
                email = jsonObject.get("email").getAsString();
                mode = jsonObject.get("mode").getAsString();
                photo = jsonObject.get("photo").getAsString().getBytes();
                //
                if (appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty() || osVer.isEmpty() || userId.isEmpty()
                        || name.isEmpty() || email.isEmpty() || mode.isEmpty() || carlistVer.isEmpty()
                        || carId.isEmpty() || fuelListVer.isEmpty() || fuelId.isEmpty() || photo == null) {
                    errorCode = String.valueOf(ErrorCodesConstants.INVALID_PARAMETER);
                }

                //Starting your business
                //checking if the user is in system
                if (errorCode.isEmpty()) {
                    userId = userId.substring(3, userId.length() - 4);
                    int id = Integer.parseInt(userId);
                    user = ud.getUserById(id);
                    if (user == null) {
                        errorCode = String.valueOf(ErrorCodesConstants.USER_DOES_NOT_EXIST);
                    } else if (user.getId() == -1) {
                        errorCode = String.valueOf(ErrorCodesConstants.DATABASE_PROBLEM);
                    } else {
                        //User Found!!
                        if (!name.isEmpty()) {
                            user.setName(name);
                        }

                        if (!fuelId.isEmpty()) {
                            FuelLookUp ftlu = new FuelLookUp();
                            ftlu.setId(Integer.parseInt(fuelId));
                            user.setFuelLookUp(ftlu);
                        }
                        if (!mode.isEmpty()) {
                            ModeLookUp lookUp = new ModeLookUp();
                            lookUp.setModeId(Integer.parseInt(mode));
                            user.setModeLookUp(lookUp);
                        }
                        if (!carId.isEmpty()) {
                            CarListLookUp carListLookUp = new CarListLookUp();
                            carListLookUp.setCarId(Integer.parseInt(carId));
                            user.setCarListLookUp(carListLookUp);
                        }
                        user.setPhoto(photo);

                        UserDAO udao = HibernateUtil.getUSER_DAO();
//                        User user1 = new User();
//                        user1.setName(user.getName());
//                        user1.setCarListLookUp(user.getCarListLookUp());
//                        user1.setEmail(user.getEmail());
//                        user1.setFuelLookUp(user.getFuelLookUp());
//                        user1.setId(user.getId());
//                        user1.setModeLookUp(user.getModeLookUp());
//                        user1.setPhoto(photo);

//                        int status = udao.updateUser(user1);
                        int status = udao.updateUser(user);
                        if (status == -1) {
                            // User sent = null
                        } else if (status == 1) {
                            errorCode = String.valueOf(ErrorCodesConstants.DATABASE_PROBLEM);
                        } else if (status == 0) {
                            errorCode = String.valueOf(ErrorCodesConstants.SUCCESS);

                        }
                    }

                }
                // Updating User Info

                JsonObject nullError = new JsonObject();
                nullError.addProperty("message", errorCode);
                jsonFile = nullError.toString();
                System.out.println("output-success: " + jsonFile);
                String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                out.println(ba);
            }
            System.out.println("End UpdateUser\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException (missing parameter most likely) in UpdateUser");
            ex.printStackTrace();
            errorCode = String.valueOf(ErrorCodesConstants.MISSING_PARAMETER);
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", errorCode);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Exception (NumberFormat most likely) in UpdateUser");
            ex.printStackTrace();
            errorCode = String.valueOf(ErrorCodesConstants.SERVER_PROBLEM);
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", errorCode);
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
