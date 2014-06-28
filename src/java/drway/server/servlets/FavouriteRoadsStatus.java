/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import servicelayer.server.dal.daos.FavouriteRoadDAO;
import servicelayer.server.dal.daos.UserDAO;
import servicelayer.server.dal.pojos.Road;
import servicelayer.server.dal.pojos.SubRoad;
import servicelayer.server.util.ErrorCodesConstants;
import servicelayer.server.util.HibernateUtil;
import drway.server.util.JsonCompressEncode;
import drway.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rana Ashraf
 */
public class FavouriteRoadsStatus extends HttpServlet {

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
    int message;
    String appVer, osID, deviceId, deviceName, osVer, userId;
    JsonArray messages;
    ArrayList<Integer> favoriteRoadsId;
    FavouriteRoadDAO favouriteRoadDAO;
    ArrayList<Road> roadsList;
    JsonArray result;
    JsonArray favArray;
    double sum;
    double avg;
    Date date ;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            System.out.println("\nBegin FavouriteRoadsStatus servlet:");
            if (request.getParameter("jsonFile") != null) {
                messages = new JsonArray();
                favoriteRoadsId = new ArrayList<Integer>();
                favouriteRoadDAO = HibernateUtil.getFAVOURITE_ROAD_DAO();
                roadsList = new ArrayList<Road>();
                result = new JsonArray();
                message = 0;
                sum = 0;
                avg = 0;

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
                favArray = jsonObject.get("favListIds").getAsJsonArray();
                date =  new Date(jsonObject.get("date").getAsLong());
                
                if (appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty() || osVer.isEmpty() || userId.isEmpty() || favArray == null) {
                    message = ErrorCodesConstants.INVALID_PARAMETER;
                }

                if (message == 0) {
                    UserDAO userDao = HibernateUtil.getUSER_DAO();
                    userId = userId.substring(3, userId.length() - 4);
                    int status = userDao.isUserExist(Integer.parseInt(userId));
                    if (status == 1) {
                        message = ErrorCodesConstants.DATABASE_PROBLEM;
                    } else if (status == -1) {
                        message = ErrorCodesConstants.USER_DOES_NOT_EXIST;
//                        userDao.blockUser(Integer.parseInt(userId));
                    }
                }

                //Starting your business
                //checking if the user is in system
                // Updating User Info
                if (message == 0) {

                    // if the array is empty then populate it with all
                    if (favArray.size() == 0) {
                        List<Integer> l = favouriteRoadDAO.getALl();
                        for (Integer l1 : l) {
                            favArray.add(new JsonParser().parse(Integer.toString(l1)));
                        }
                    }

                    for (int i = 0; i < favArray.size() && message == 0; i++) {
                        Road road = favouriteRoadDAO.getRoad(favArray.get(i).getAsInt());
                        if (road != null && road.getId() != -202) { // WTF ??????????
//                            System.out.println("Road Name : " + road.getName());
                            roadsList.add(road);
                        } else if (road == null) {
                            UserDAO udao = HibernateUtil.getUSER_DAO();
                            int status = udao.blockUser(Integer.parseInt(userId));
//                                System.out.println("User bloked ? : " + status);
                            message = ErrorCodesConstants.NOT_SUPPORTED_AREA;
                        } else if (road.getId() == -202) {
                            message = ErrorCodesConstants.DATABASE_PROBLEM;
                        }
                    }
                    if (roadsList.isEmpty() && message == 0) {
                        message = ErrorCodesConstants.NOT_SUPPORTED_AREA;
                    }

                    for (Road road : roadsList) {
                        sum = 0;
                        Set subRoads = road.getSubRoads();
                        for (Object object : subRoads) {
                            SubRoad subRoad = (SubRoad) object;
                            sum += subRoad.getCurrentSpeed();
                        }
                        int sz = subRoads.isEmpty() ? 1 : subRoads.size();
                        avg = sum / sz;

                        JsonObject obj = new JsonObject();
                        obj.addProperty("id", road.getId());
                        obj.addProperty("name", road.getName());

                        Double blockingRatio = HibernateUtil.getADMIN_ROAD_REPORT_DAO().getReportBlockingRatio(road, date);
                        if (blockingRatio == null) {
                            obj.addProperty("speed", avg);
                        } else {
                            double speed = avg * ((100.0 - blockingRatio) / 100.0);
                            obj.addProperty("speed", speed);
                        }
//                        obj.addProperty("speed", avg);
                        
             
                        result.add(obj);
                    }
                }

                if (message != 0) {
                    JsonObject nullError = new JsonObject();
                    nullError.addProperty("message", message);
                    jsonFile = nullError.toString();
                    System.out.println("output-fail:");
                    String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                    out.println(jsonString);
                    //Back to Client with success report
                } else {
                    messages = result;
                    JsonObject success = new JsonObject();
                    success.add("message", messages);
                    jsonFile = success.toString();
                    System.out.println("output-success:");
                    String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                    out.println(jsonString);
                }
            }
            System.out.println("End FavouriteRoadsStatus\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException (missing parameter most likely) in FavouriteRoadsStatus");
            ex.printStackTrace();
            message = ErrorCodesConstants.MISSING_PARAMETER;
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.print(ba);
        } catch (Exception ex) {
            System.out.println("Exception (Json/NumberFomat most likely) in FavouriteRoadsStatus");
            ex.printStackTrace();
            message = ErrorCodesConstants.SERVER_PROBLEM;
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            //Back to Client with Server error report
            out.print(ba);
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
