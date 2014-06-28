///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package drway.server.util;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.ibm.icu.util.ByteArrayWrapper;
//import drway.server.dal.pojos.PendingUser;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.zip.DataFormatException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.NullPointerException;
//import org.apache.tomcat.util.codec.binary.Base64;
//
///**
// *
// * @author Rana Ashraf
// */
//public class TemplateUtil extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    String jsonFile="" ;
//    String errorCode="";
//    String appVer, osID, deviceId, deviceName;
//   
//    
//            //appVer:1.0 osID:xxx osVer:xxx deviceId:xxx deviceName:xxx name:xxx mobile:xxx email:xxx flag:1}
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        try {
//            if(request.getAttribute("jsonFile") != null){
//
//                JsonObject jsonObject = (JsonObject)new JsonDecodeDecompress().getDecodeDecompress(request.getAttribute("jsonFile"));
//                //Starting recieve the JSON parameters
//                //NullPointerException will be catched if there null value
//                appVer = jsonObject.get("appVer").getAsString(); 
//                osID = jsonObject.get("osID").getAsString(); 
//                //osId = "";
//                deviceId = jsonObject.get("devieId").getAsString();
//                deviceName = jsonObject.get("deviceName").getAsString();
//                
//                //
//                if(appVer.isEmpty() || osID.isEmpty() || deviceId.isEmpty() || deviceName.isEmpty())
//                    errorCode = "-303";
//                
//                //Starting your business
//                if(errorCode.isEmpty()){
//                    
//                }
//                //If Success => jsonFile has some values
//                //
//                // Compress JSON FILE
//                    
//            out.println("Done");
//        } 
//        
//    }   catch(NullPointerException nullPointerException){
//            errorCode = "-302";
//            JsonObject nullError =   new JsonObject();
//            nullError.addProperty("message", errorCode);
//            jsonFile = nullError.toString();
//            ByteArrayWrapper ba = (ByteArrayWrapper) new JsonCompressEncode().getCompressEncode(jsonFile);
//            request.setAttribute("jsonFile", ba);
//        }catch (Exception ex) {
//             errorCode = "-301";
//            JsonObject nullError =   new JsonObject();
//            nullError.addProperty("message", errorCode);
//            jsonFile = nullError.toString();
//            ByteArrayWrapper ba = (ByteArrayWrapper) new JsonCompressEncode().getCompressEncode(jsonFile);
//            request.setAttribute("jsonFile", ba);
//        }
//        finally {
//            JsonObject nullError =   new JsonObject();
//            nullError.addProperty("message", errorCode);
//            jsonFile = nullError.toString();
//            ByteArrayWrapper ba = (ByteArrayWrapper) new JsonCompressEncode().getCompressEncode(jsonFile);
//            request.setAttribute("jsonFile", ba);
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
