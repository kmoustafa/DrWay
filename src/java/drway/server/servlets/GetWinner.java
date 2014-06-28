/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import servicelayer.server.dal.daos.UpdatingUserDAO;
import servicelayer.server.dal.daos.WinnerDAO;
import servicelayer.server.dal.pojos.DailyWinner;
import servicelayer.server.dal.pojos.User;
import servicelayer.server.util.ErrorCodesConstants;
import servicelayer.server.util.HibernateUtil;
import drway.server.util.JsonCompressEncode;
import drway.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicelayer.server.dal.daos.UserDAO;

/**
 *
 * @author Kareem Moustafa
 */
public class GetWinner extends HttpServlet {

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
    String message, result;
    String appVer, osID, deviceId, deviceName, osVer, userId, x, y;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            message = "";
            result = "";
            System.out.println("\nBegin GetWinner servlet:");
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

                //
                if (appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty() || osVer.isEmpty() || userId.isEmpty()) {
                    message = String.valueOf(ErrorCodesConstants.INVALID_PARAMETER);
                }

                //Starting your business
                //checking if the user is in system
                //userId = jsonObject.get("userId").getAsString();
                if (message.isEmpty()) {
                    UserDAO ud = HibernateUtil.getUSER_DAO();
                    userId = userId.substring(3, userId.length() - 4);
                    int status = ud.isUserExist(Integer.parseInt(userId));
                    if (status == -1) {
                        //UserDAO udao = new UserDAO();
                        // int status1 = udao.blockUser(Integer.parseInt(userId));
                        //System.out.println("User blocked ? : " + status1);
                        message = String.valueOf(ErrorCodesConstants.USER_DOES_NOT_EXIST);
                    } else if (status == 1) {
                        message = String.valueOf(ErrorCodesConstants.DATABASE_PROBLEM);
                    }
                    //User Found!!
                }

                if (message.isEmpty()) {

                    WinnerDAO winnerDAO = HibernateUtil.getWINNERDAO();
                    User user = new User();

                    DailyWinner monthlyWinner = winnerDAO.getMonthlyWinner();
                    JsonObject mWinner = new JsonObject();
                    user = monthlyWinner.getUser();
                    if (user.getId() == Integer.parseInt(userId)) {
                        System.out.println("I'm winner");
                        mWinner.addProperty("flag", "1");
                    } else {
                        System.out.println("Not Winner");
                        mWinner.addProperty("flag", "0");
                    }
                    mWinner.addProperty("name", user.getName());
                    mWinner.addProperty("pic", new String(user.getPhoto(), "UTF-8"));

                    List<DailyWinner> dailyWinner = winnerDAO.getDailyWinner();
                    JsonArray jsonArray = new JsonArray();
                    JsonObject object = new JsonObject();
                    DailyWinner dailyWinner1 = new DailyWinner();
                    for (int i = 0; i < 5 && i < dailyWinner.size(); i++) {
                        dailyWinner1 = dailyWinner.get(i);
                        user = dailyWinner1.getUser();
                        if (user.getId() == Integer.parseInt(userId)) {
                            System.out.println("I'm daily winner!!");
                            object.addProperty("flag", "1");
                        } else {
                            object.addProperty("flag", "0");
                            System.out.println("normal daily winner");
                        }
                        object.addProperty("name", user.getName());
                        object.addProperty("pic", new String(user.getPhoto(), "UTF-8"));
                        System.out.println("Photo : " + user.getId() + " Length " + user.getPhoto().length);
                        jsonArray.add(object);
                        object = new JsonObject();
                    }

                    JsonObject both = new JsonObject();
                    JsonObject jsonFile = new JsonObject();
                    both.add("day_winners", jsonArray);
                    both.add("month_winner", mWinner);
                    jsonFile.add("message", both);
                    System.out.println("Before Sending ... " + jsonFile.toString());
                    String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile.toString());
                    out.println(ba.toString());
//                request.setAttribute("jsonFile", ba);
//                    RequestDispatcher dispatcher = request.getRequestDispatcher("TestApis");
//                dispatcher.forward(request, response);
                }
            }
            System.out.println("End GetUserPosition\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException (missing parameter most likely) in GetUserPosition");
            ex.printStackTrace();
            message = String.valueOf(ErrorCodesConstants.MISSING_PARAMETER);
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
//                            request.setAttribute("jsonFile", ba);
//
//                                RequestDispatcher dispatcher = request.getRequestDispatcher("TestApis");
//                dispatcher.forward(request, response);
        } catch (Exception ex) {
            System.out.println("Exception (IO/NumberFormat most likely) in GetUserPosition");
            ex.printStackTrace();
            message = String.valueOf(ErrorCodesConstants.SERVER_PROBLEM);
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
//
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
