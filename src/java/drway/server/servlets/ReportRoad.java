/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drway.server.servlets;

import com.google.gson.JsonObject;
import servicelayer.server.dal.daos.ReportTypeLookupDAO;
import servicelayer.server.dal.daos.RoadDAO;
import servicelayer.server.dal.daos.UpdatingUserDAO;
import servicelayer.server.dal.daos.UserDAO;
import servicelayer.server.dal.daos.UserRoadReportDAO;
import servicelayer.server.dal.pojos.ReportTypeLookUp;
import servicelayer.server.dal.pojos.Road;
import servicelayer.server.dal.pojos.User;
import servicelayer.server.dal.pojos.UserRoadReport;
import drway.server.handlers.VerificationHandler;
import servicelayer.server.util.ErrorCodesConstants;
import servicelayer.server.util.HibernateUtil;
import drway.server.util.JsonCompressEncode;
import drway.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;

/**
 *
 * @author Kareem Moustafa
 */
public class ReportRoad extends HttpServlet {

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
    String appVer, osID, deviceId, deviceName, osVer, userId;
    int roadId = 0;
    int reportId = 0;
    long date;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
                try {
            message = "";
            date = 0;
            System.out.println("\nBegin ReportRoad servlet:");
            if (request.getParameter("jsonFile") != null) {
                jsonFile = (String) request.getParameter("jsonFile");
                JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);
                //Starting recieve the JSON parameters
                //NullPointerException will be catched if there null value
                appVer = jsonObject.get("appVer").getAsString();
                osVer = jsonObject.get("osVer").getAsString();
                osID = jsonObject.get("osID").getAsString();
                deviceId = jsonObject.get("deviceId").getAsString();
                deviceName = jsonObject.get("deviceName").getAsString();
                userId = jsonObject.get("userId").getAsString();
                roadId = jsonObject.get("roadId").getAsInt();
                reportId = jsonObject.get("reportId").getAsInt();
                date = jsonObject.get("time").getAsLong();
                //
                if (appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty() || osVer.isEmpty() || userId.isEmpty()
                        || roadId <= 0 || reportId <= 0 || date <= 0) {
                    message = ErrorCodesConstants.INVALID_PARAMETER + "";
                }

                
                //Starting your business
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
                    UserRoadReport report = new UserRoadReport();
                    UserDAO userDAO = HibernateUtil.getUSER_DAO();
                    User user = userDAO.getUserById(Integer.parseInt(userId));
                    ReportTypeLookupDAO reportTypeLookUpDAO = HibernateUtil.getREPORT_TYPE_LOOK_UP_DAO();
                    ReportTypeLookUp reportTypeLookUp = reportTypeLookUpDAO.getReportTypeLookUpById(reportId);
                    RoadDAO roadDAO = HibernateUtil.getROAD_DAO();
                    Road road = roadDAO.getRoadById(roadId);
                    report.setReportTypeLookUp(reportTypeLookUp);
                    report.setRoad(road);
                    Date d = new Date(date);
                    report.setTimeStamp(d);
                    report.setUser(user);
                    UserRoadReportDAO roadReportDAO = HibernateUtil.getUSER_ROAD_REPORT_DAO();
                    int status = roadReportDAO.insertReport(report);
                    if(status == -1){
                        message = String.valueOf(ErrorCodesConstants.DATABASE_PROBLEM);
                    }else if (status == 0){
                        message = String.valueOf(ErrorCodesConstants.SUCCESS);
                    }
//                    JsonObject jsonObject1 = new JsonObject();
//                     jsonObject1.addProperty("message", message);
//                    System.out.println("Before Sending ... " + jsonFile.toString());
//                    String ba = (String) new JsonCompressEncode().getCompressEncode(jsonObject1.toString());
//                    System.out.println("ba :: " + ba);
//                    out.println(ba);
                }
            }
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            System.out.println("ouput-success: " + jsonFile);
            String jsonSting = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(jsonSting);
            System.out.println("End End ReportRoad\n");
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

}
