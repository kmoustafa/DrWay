/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import servicelayer.server.dal.pojos.MainPoint;
import servicelayer.server.dal.pojos.Route;
import servicelayer.server.util.JsonCompressEncode;
import servicelayer.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicelayer.server.dal.daos.RoadDAO;
import servicelayer.server.dal.pojos.DateLookUp;
import servicelayer.server.dal.pojos.Road;
import servicelayer.server.dal.pojos.SubRoute;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author MIRO
 */
public class GetPossibleRoutes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    MainPoint source, destination;
    String jsonFile;
    String message;
    String authenticationKey, srcId, destId;
    Date date;
    boolean predict;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            message = "";
            predict = false;
            if (request.getParameterNames() != null) {
                System.out.println("\nBegin GetPossibleRoutes in service Layer:");
                Enumeration enum1 = request.getParameterNames();
                while (enum1.hasMoreElements()) {
                    String param = (String) enum1.nextElement();
                    jsonFile = request.getParameter(param);
//                    System.out.println("param=" + param);
                }
                //  jsonFile = (String) request.getParameter("jsonFile");
                JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);
                //Starting recieve the JSON parameters
                //NullPointerException will be catched if there null value
                authenticationKey = jsonObject.get("key").getAsString();
                System.out.println(authenticationKey);
                srcId = jsonObject.get("sourceId").getAsString();
                System.out.println(srcId);
                destId = jsonObject.get("destId").getAsString();
                System.out.println(destId);
                date = new Date(jsonObject.get("date").getAsLong());
                System.out.println(date);

                Calendar before = Calendar.getInstance();
                before.add(Calendar.MINUTE, -90);
                Calendar after = Calendar.getInstance();
                after.add(Calendar.MINUTE, 90);


                if (date.after(after.getTime())) {
                    predict = true;
                }

                if (authenticationKey.isEmpty() || srcId.isEmpty() || destId.isEmpty() || date.before(before.getTime())) {
                    message = "-303";
                }

                //Starting your business
                if (message.isEmpty()) {
                    source = HibernateUtil.getMAIN_POINT_DAO().getMainPointByID(Integer.parseInt(srcId));
                    destination = HibernateUtil.getMAIN_POINT_DAO().getMainPointByID(Integer.parseInt(destId));

                    JsonArray routesJsonArray = new JsonArray();
                    List<Route> resultRoutes = HibernateUtil.getROUTE_DAO().getRoutesFromPointToPoint(source, destination);
                    for (Route route : resultRoutes) {
                        JsonObject routeJsonObject = new JsonObject();
                        routeJsonObject.addProperty("id", route.getId());
                        routeJsonObject.addProperty("name", route.getName());
                        routeJsonObject.addProperty("nameEn", route.getDisplayNameEn());
                        routeJsonObject.addProperty("nameAr", route.getDisplayNameAr());

                        JsonArray roadsJsonArray = new JsonArray();
                        Set<SubRoute> resultSubRoutes = route.getSubRoutes();
                        for (SubRoute subRoute : resultSubRoutes) {
                            Road road = subRoute.getRoad();
                            RoadDAO roadDAO = HibernateUtil.getROAD_DAO();

                            JsonObject roadJsonObject = new JsonObject();
                            roadJsonObject.addProperty("name", road.getName());
                            roadJsonObject.addProperty("displayNameAr", road.getDisplayNameAr());
                            roadJsonObject.addProperty("displayNameEn", road.getDisplayNameEn());
                               System.out.println("Prediction is "+predict);
                            if (predict == false) {
                                Object[] obj = (Object[]) roadDAO.getSubRoadsDistSpeed(road);
                                roadJsonObject.addProperty("dist", (Double) obj[0]);
                                Double blockingRatio = HibernateUtil.getADMIN_ROAD_REPORT_DAO().getReportBlockingRatio(road, date);
                                System.out.println("Blocking ratio is "+blockingRatio);
                                if (blockingRatio == null) {
                                    roadJsonObject.addProperty("speed", (Double) obj[1]);
                                } else {
                                    double speed = (Double) obj[1] * ((100.0 - blockingRatio) / 100.0);
                                    System.out.println("Speed is "+speed);
                                    roadJsonObject.addProperty("speed", speed);
                                }
                            } else {
                                Double dist = (Double) roadDAO.getSubRoadsDist(road);
                                roadJsonObject.addProperty("dist", dist);
                                DateLookUp dateLookUp = HibernateUtil.getDATE_LOOK_UP_DAO().getDateLookUp(date.getDate(), date.getMonth() + 1, date.getYear() + 1900);
                                Double speed = roadDAO.getSubRoadsPredictedSpeed(road, dateLookUp, date.getHours());
//                                roadJsonObject.addProperty("speed", speed);

                                Double blockingRatio = HibernateUtil.getADMIN_ROAD_REPORT_DAO().getReportBlockingRatio(road, date);
                                if (blockingRatio == null) {
                                    roadJsonObject.addProperty("speed", speed);
                                } else {
                                    speed = speed * ((100.0 - blockingRatio) / 100.0);
                                    roadJsonObject.addProperty("speed", speed);
                                }

                            }
                            roadsJsonArray.add(roadJsonObject);
                        }

                        routeJsonObject.add("subs", roadsJsonArray);
                        routesJsonArray.add(routeJsonObject);
                    }
                    JsonObject success = new JsonObject();
                    success.add("message", routesJsonArray);
                    jsonFile = success.toString();
                    System.out.println("output-success: " + jsonFile);
                    String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                    ba = ba.replace("\n", "");
                    out.println(ba);
                } else if (!message.isEmpty()) {
                    JsonObject nullError = new JsonObject();
                    nullError.addProperty("message", message);
                    jsonFile = nullError.toString();
                    System.out.println("output-fail:");
                    String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                    out.println(jsonString);
                }
            } else {
                System.out.println("no params");
            }
            System.out.println("End GetPossibleRoutes\n");
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException in GetPossibleRoutes");
            message = "-302";
            ex.printStackTrace();
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Exception (Number fomat most likely) in GetPossibleRoutes");
            message = "-301";
            ex.printStackTrace();
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
