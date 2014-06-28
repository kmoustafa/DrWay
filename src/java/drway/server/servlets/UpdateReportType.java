/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import servicelayer.server.dal.daos.CurrentVersionDAO;
import servicelayer.server.dal.daos.ReportTypeLookupDAO;
import servicelayer.server.dal.daos.UserDAO;
import servicelayer.server.dal.pojos.ReportTypeLookUp;
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
 * @author MIRO
 */
public class UpdateReportType extends HttpServlet {

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
    JsonArray result;
    String appVer, osID, deviceId, deviceName, osVer, reportTypeVersion, userId;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            message = 0;
            result = new JsonArray();
            System.out.println("\nBegin UpdateReportType:");
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
                reportTypeVersion = jsonObject.get("reportTypeVer").getAsString();
                userId = jsonObject.get("userId").getAsString();

                //
                if (appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty() || osVer.isEmpty() || userId.isEmpty() || reportTypeVersion.isEmpty()) {
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
                if (message == 0) {
                    CurrentVersionDAO currentVersionDAO = HibernateUtil.getCURRENT_VERSION_DAO();
                    if (currentVersionDAO.getReportTypeVersion() != Integer.parseInt(reportTypeVersion)) {
                        System.out.println("new version of ReportType: " + currentVersionDAO.getReportTypeVersion());
                        JsonObject success = new JsonObject();
                        ReportTypeLookupDAO reportTypeLookUpDAO = HibernateUtil.getREPORT_TYPE_LOOK_UP_DAO();
                        success.addProperty("reportTypeVer", currentVersionDAO.getReportTypeVersion());
                        System.out.println("Hereeeee");
                        for (ReportTypeLookUp reportTypeLookUp : reportTypeLookUpDAO.getList()) {
                            JsonObject obj = new JsonObject();
                            obj.addProperty("id", reportTypeLookUp.getReportTypeId());
                            obj.addProperty("value", reportTypeLookUp.getReportTypeValue());
                            result.add(obj);
                        }
                        success.add("message", result);
                        jsonFile = success.toString();
                        String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                        System.out.println("output-success: " + jsonFile);
                        out.println(ba);
                        // Back to Client with Server error report
                    } else {
                        System.out.println("no change in ReportType");
                        JsonObject success = new JsonObject();
                        success.addProperty("message", "0");
                        jsonFile = success.toString();
                        String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                        System.out.println("output-success: " + jsonFile);
                        out.println(ba);
                        // Back to Client with Server error report
                    }
                }
            } else if (message != 0) {
                JsonObject nullError = new JsonObject();
                nullError.addProperty("message", message);
                jsonFile = nullError.toString();
                String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                out.println(jsonString);
                System.out.println("output-fail: " + jsonFile);
            }
            System.out.println("End UpdateReportType\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException (missing parameter most likely) in UpdateReportType");
            ex.printStackTrace();
            message = ErrorCodesConstants.MISSING_PARAMETER;
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Exception (NumberFormat most likely) in UpdateReportType");
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
