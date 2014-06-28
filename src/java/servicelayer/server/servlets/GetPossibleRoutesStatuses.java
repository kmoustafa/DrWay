/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.icu.util.ByteArrayWrapper;
import servicelayer.server.dal.daos.MainPointDAO;
import servicelayer.server.dal.daos.RouteDAO;
import servicelayer.server.dal.pojos.MainPoint;
import servicelayer.server.dal.pojos.Route;
import servicelayer.server.util.JsonCompressEncode;
import servicelayer.server.util.JsonDecodeDecompress;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicelayer.server.dal.daos.DateLookUpDAO;
import servicelayer.server.dal.daos.HistoryDAO;
import servicelayer.server.dal.pojos.DateLookUp;
import servicelayer.server.dal.pojos.Road;
import servicelayer.server.dal.pojos.SubRoad;
import servicelayer.server.dal.pojos.SubRoute;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author Rana Ashraf
 */
public class GetPossibleRoutesStatuses extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //String appVer, osID, osVer, deviceId, deviceName;
    MainPoint source, destination;
    String jsonFile;
    String message;
    String authenticationKey, date;
    int srcId, destId;
    String result;
    double roadStatus = 0, avgroadStatus = 0;
    Date d;
    boolean current = false;
    Road road = new Road();
    HashMap<Integer, Double> subroadsStatuses = new HashMap<Integer, Double>();
    HashMap<SubRoad, Road> roudsOfSubRoadnames = new HashMap<SubRoad, Road>();
    HashMap<Road, Double> roudsDistance = new HashMap<Road, Double>();
    HashMap<Road, Double> roudsStatus = new HashMap<Road, Double>();
    JsonArray jsonArrayOfRoutes;
    JsonArray jsonArrayOfRoads;
    JsonObject jsonObjectOfRoad;
    int houreTime;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            jsonFile = "";
            message = "";
//            result = new JsonArray();
            if (request.getParameterNames() != null) {
//                System.out.println("I'm in service Layer :) ");
                Enumeration enum1 = request.getParameterNames();
                System.out.println(enum1.hasMoreElements());
                while (enum1.hasMoreElements()) {
                    String param = (String) enum1.nextElement();
                    jsonFile = request.getParameter(param);
                    System.out.println("param=" + param);
                }
                //                jsonFile = jsonFile.trim();
                System.out.println("jsonReceived : " + jsonFile);
                //JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(request.getParameter("jsonFile"));
                JsonObject jsonObject = (JsonObject) new JsonDecodeDecompress().getDecodeDecompress(jsonFile);
                //Starting recieve the JSON parameters
                //NullPointerException will be catched if there null value
                authenticationKey = jsonObject.get("key").getAsString();
                System.out.println("Key: " + authenticationKey);
                srcId = jsonObject.get("srcID").getAsInt();
                System.out.println("srcID: " + srcId);
                destId = jsonObject.get("destID").getAsInt();
                System.out.println("destId: " + destId);
                date = jsonObject.get("date").getAsString();
                System.out.println("date: " + date);
                d = new Date(date);
                Date currentDate = new Date();
                current = d.before(currentDate);
                System.out.println("Beforeeeeeeeeee " + current);
//
                if (authenticationKey.isEmpty() || srcId == 0 || destId == 0 || date.isEmpty()) {
                    message = "-303";
                }
                if (d != null) {
                    houreTime = d.getHours();
                    System.out.println("Houre Time : " + houreTime);
                }

                //Starting your business
                if (message.isEmpty()) {
//                    DateLookUpDAO lookUpDAO = new DateLookUpDAO();
//                    DateLookUp lookUp = lookUpDAO.getDateLookUpById(1);
                    source = HibernateUtil.getMAIN_POINT_DAO().getMainPointByID(srcId);
                    System.out.println("Source: " + source.getId());
                    destination = HibernateUtil.getMAIN_POINT_DAO().getMainPointByID(destId);
                    System.out.println("Destination : " + destination.getId());
                    List<Route> resultRoutes = HibernateUtil.getROUTE_DAO().getRoutesFromPointToPoint(source, destination);
                    Set<SubRoute> subRoutesList = resultRoutes.get(0).getSubRoutes();
                    System.out.println("LISTTTTTTTTTTTTTT " + subRoutesList.size());
//                    HistoryDAO historyDAO = new HistoryDAO();

                    Iterator it = subRoutesList.iterator();
//                    while (it.hasNext()) {
//                        //System.out.println("kimoooo oooooomik");
//                        SubRoute route = (SubRoute) it.next();
//                        System.out.println("ROAD ID " + route.getSubRoad().getRoad().getId());
//                        if (current) {
//                            System.out.println("Current");
//                            subroadsStatuses.put(route.getSubRoad().getId(), historyDAO.getCurrentSubRoadsStatus(route.getSubRoad(), lookUp,houreTime));
//                            roudsOfSubRoadnames.put(route.getSubRoad(), route.getSubRoad().getRoad());
//
//                        } else {
//                            System.out.println("Mesh Current");
//                            //System.out.println(historyDAO.getCurrentSubRoadsStatus(route.getSubRoad(), lookUp));
//                            subroadsStatuses.put(route.getSubRoad().getId(), historyDAO.getFutureSubRoadsStatus(route.getSubRoad(), lookUp));
//                            roudsOfSubRoadnames.put(route.getSubRoad(), route.getSubRoad().getRoad());
//                        }
//                    }
                    // System.out.println("NAMES:: " + ro);
                    double roadAvg = 0;
                    double roadDistance = 0;
                    int count = 0;
                    boolean f = false;
                    for (int i = 0; i < roudsOfSubRoadnames.size(); i++) {
                        Set<SubRoad> setKeys = roudsOfSubRoadnames.keySet();
                        Iterator iterator = setKeys.iterator();

                        road.setId(-1);
                        //System.out.println("RoadIDDDD " + road.getName());
                        System.out.println("I'm at Begin " + roudsOfSubRoadnames.size());
                        while (iterator.hasNext()) {
                            SubRoad subRoadID = (SubRoad) iterator.next();
                            if (!f) {
                                road = roudsOfSubRoadnames.get(subRoadID);
                                f = true;
                            }
                            System.out.println("Kimooooo " + subRoadID + " ROADID " + road.getId());
                            if (roudsStatus.containsKey(road)) {
                                break;
                            }
                            if (subRoadID.getRoad().getId() == road.getId()) {
                                System.out.println("Get ID ::" + subRoadID);
                                roadAvg += subroadsStatuses.get(subRoadID.getId());
                                roadDistance += subRoadID.getDistance();
                                System.out.println("Avg Speed" + roadAvg + " " + subroadsStatuses.get(subRoadID.getId()));
                                count++;
                                roudsOfSubRoadnames.remove(subRoadID);
                                setKeys = roudsOfSubRoadnames.keySet();
                                iterator = setKeys.iterator();
                                System.out.println("After Removed : " + roudsOfSubRoadnames.size());
                                //setKeys = roudsOfSubRoadnames.keySet();
                            }
                        }
                        if (road.getId() != -1) {
                            System.out.println("I put new Road " + road.getId());
                            roudsStatus.put(road, roadAvg / count);
                            roudsDistance.put(road, roadDistance);
                            roadDistance = 0;
                            count = 0;
                            roadAvg = 0;
                            f = false;
                            i = 0;
                            System.out.println("I'm and End " + i + " " + roudsOfSubRoadnames.size());
                        }
                    }

                    System.out.println("Newwwwwwwwwwwwww " + roudsStatus.size());
                    System.out.println("Newwwwwwwwwwwwww " + roudsDistance.size());

//                    for (int i = 67; i < 100; i++) {
//                        System.out.println("Status ::" + subroadsStatuses.get(i));
//                        roadStatus+=subroadsStatuses.get(i);
//                    }
                    Set<Road> roads = roudsStatus.keySet();
                    Iterator it1 = roads.iterator();
                    jsonArrayOfRoutes = new JsonArray();
                    jsonArrayOfRoads = new JsonArray();
                    jsonObjectOfRoad = new JsonObject();
                    Road roadF = new Road();
                    while (it1.hasNext()) {
                        roadF = (Road) it1.next();
                        System.out.println("I'm Road" + roadF.getName());
                        jsonObjectOfRoad.addProperty("name", roadF.getName());
                        jsonObjectOfRoad.addProperty("status", roudsStatus.get(roadF));
                        jsonObjectOfRoad.addProperty("distance", roudsDistance.get(roadF));
                        jsonArrayOfRoads.add(jsonObjectOfRoad);
                        jsonObjectOfRoad = new JsonObject();
                        jsonArrayOfRoutes.addAll(jsonArrayOfRoads);
                        jsonArrayOfRoads = new JsonArray();
                    }

                    System.out.println(jsonArrayOfRoutes.toString());
//                    for (int i = 67; i < 100; i++) {
//                        System.out.println("Map ::" + roudsOfSubRoadnames.get(i).get);
//                        //roadStatus+=roudsOfSubRoadnames.get(i);
//                    }

//                    for(int i=0; i<roudsOfSubRoadnames.size(); i++){
//                    
//                        SubRoad srd = HibernateUtil.getSUB_ROAD_DAO().getSubRoadById(i)
//                        if(srd!=null){
//                            jsonObjectOfRoad = new JsonObject();
//                            jsonObjectOfRoad.addProperty("name", );
//                            jsonObjectOfRoad.addProperty("",);
//                        }
//                        
//                        
//                    }
                    avgroadStatus = roadStatus / subroadsStatuses.size();
                    System.out.println("Size of subroadsStatuses : " + subroadsStatuses.size());
                    System.out.println("Size of roudsOfSubRoadnames : " + roudsOfSubRoadnames.size());
                    System.out.println("Size :: " + subRoutesList.size());
                    JsonObject success = new JsonObject();
                    success.addProperty("message", avgroadStatus);
                    jsonFile = success.toString();
                    System.out.println("b4 compress: " + jsonFile);
                    String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                    ba = ba.replace("\n", "");
                    System.out.println("after compress: ");
                    System.out.println(new JsonDecodeDecompress().getDecodeDecompress(ba));
                    out.println(ba);
                }
                if (!message.isEmpty()) {
                    JsonObject nullError = new JsonObject();
                    nullError.addProperty("message", message);
                    jsonFile = nullError.toString();
                    String jsonString = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
                    out.println(jsonString);
                }

            } else {
                System.out.println("no params");
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println("Null Excpetion");
            message = "-302";
            nullPointerException.printStackTrace();
            JsonObject nullError = new JsonObject();
            nullError.addProperty("message", message);
            jsonFile = nullError.toString();
            String ba = (String) new JsonCompressEncode().getCompressEncode(jsonFile);
            out.println(ba);
        } catch (Exception ex) {
            System.out.println("Service Error");
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
