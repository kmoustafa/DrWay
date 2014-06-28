/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import servicelayer.server.dal.daos.SnapShotsDAO;
import servicelayer.server.dal.daos.SubRoadDAO;
import servicelayer.server.dal.pojos.Snapshot;
import servicelayer.server.dal.pojos.SubRoad;
import servicelayer.server.util.JsonCompressEncode;
import servicelayer.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicelayer.server.dal.daos.UserDAO;
import servicelayer.server.dal.pojos.User;
import servicelayer.server.util.HibernateUtil;

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
    String authenticationKey, timeStamp, subRoadId, currentSpeed, userId;
    JsonObject snapShotJsonObject;
    JsonArray snapShotsJsonArray;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        message = "";
        jsonFile = "";
        snapShotJsonObject = new JsonObject();
        snapShotsJsonArray = new JsonArray();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        System.out.println("I'm in service layer");
        try {
            Enumeration enum1 = request.getParameterNames();
            System.out.println(enum1.hasMoreElements());
            while (enum1.hasMoreElements()) {
                String param = (String) enum1.nextElement();
                jsonFile = request.getParameter(param);
                System.out.println("param=" + param);
            }
           // if (request.getParameter("jsonFile") != null) {
            //     jsonFile = (String) request.getParameter("jsonFile");
            System.out.println("jsonReceived : " + jsonFile);
            JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);
                //Starting recieve the JSON parameters
            //NullPointerException will be catched if there null value
            userId = jsonObject.get("userId").getAsString();
            authenticationKey = jsonObject.get("key").getAsString();
            snapShotsJsonArray = jsonObject.get("snapShotsJsonArray").getAsJsonArray();
            //
            if (authenticationKey.isEmpty() || userId.isEmpty()) {
                message = "-303";
                System.out.println("authentication Key is emty or user is empty  ");
            }
            if (!snapShotsJsonArray.isJsonArray()) {
                message = "-303";
                System.out.println("Array is not a JsonArray ");
            }

            //Starting your business
            if (message.isEmpty()) {
                for (JsonElement jsonElement : snapShotsJsonArray) {
                    SnapShotsDAO snapShotsDAO = HibernateUtil.getSNAP_SHOTS_DAO();
                    snapShotJsonObject = jsonElement.getAsJsonObject();
                    timeStamp = snapShotJsonObject.get("timeStamp").getAsString();
                    currentSpeed = snapShotJsonObject.get("roadSpeed").getAsString();
                    subRoadId = snapShotJsonObject.get("subRoadId").getAsString();
                    Snapshot snapshot = new Snapshot();
                    snapshot.setSpeed(Double.parseDouble(currentSpeed));
                    long milliSeconds = Long.parseLong(timeStamp);
                    Timestamp timestamp = new Timestamp(milliSeconds);
                    System.out.println("Time Stamp before setting in DB : " + timestamp);
                    snapshot.setTimeStamp(timestamp);
                    System.out.println("Time Stamp after setting in DB : " + snapshot.getTimeStamp());
                    SubRoadDAO roadDAO = HibernateUtil.getSUB_ROAD_DAO();
                    SubRoad road = roadDAO.getSubRoadById(Integer.parseInt(subRoadId));
                    snapshot.setSubRoad(road);
                    UserDAO userDAO = HibernateUtil.getUSER_DAO();
                    int userID = Integer.parseInt(userId);
                    User user = userDAO.getUserById(userID);
                    if (user != null) {
                        snapshot.setUser(user);
                    }
                    int status1 = snapShotsDAO.updateSnapShot(snapshot);
                    if (status1 == -1) {
                        message = "-202";
                    }
//                        else if( status1 == 1){
//                            //BlockUser
//                            message="-202";
//                        }
//                        else

                }
                message = "0";
                System.out.println("Service Layer Sending zero ..");
            }
            // Compress JSON FILE
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            System.out.println("jsonString sent is : " + jsonString);
            out.println(jsonString.toString());

        } catch (NullPointerException nullPointerException) {
            message = "-302";
            nullPointerException.printStackTrace();
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(jsonString);
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "-301";
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(jsonString);
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
