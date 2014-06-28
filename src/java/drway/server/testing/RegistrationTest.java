///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package drway.server.testing;
//
//import com.google.gson.Gson;
//import com.ibm.icu.util.ByteArrayWrapper;
//import drway.server.dal.pojos.User;
//import drway.server.util.CompressionUtils;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.zip.DataFormatException;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.tomcat.util.codec.binary.Base64;
//
///**
// *
// * @author KimOoO
// */
//public class RegistrationTest extends HttpServlet {
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
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException, DataFormatException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            /* TODO output your page here. You may use following sample code. */
//            if(request.getAttribute("jsonFile") != null){
//                System.out.println("I'm in json regtest");
//                ByteArrayWrapper encodedArray = (ByteArrayWrapper)request.getAttribute("jsonFile");
//                
//                byte[] encodedByteArray = encodedArray.releaseBytes();
//                System.out.println("encodedByteArray Length : " + encodedByteArray.length);
//                // decode the array of bytes
//                byte[] decodedByteArray = Base64.decodeBase64(encodedByteArray);
//                // decompress the decoded array of bytes
//                byte[] decompressedArray = CompressionUtils.decompress(decodedByteArray);
//                System.out.println("decompressedArray : " + new String(decompressedArray));
//                String jsonString = new String (decompressedArray);
//                System.out.println("REceived Json : " + jsonString);
//                
//                
//            }else{
//                System.out.println("I'm in else test");
//            User user = new User();
//            user.setTokenType(1);
//            Gson jsonObj = new Gson();
//            String jsonFile = jsonObj.toJson(user);
//            
//                // Compress JSON FILE
//            System.out.println("Before compressed : "+ jsonFile.getBytes().length);
//            byte[] compressedJSON = CompressionUtils.compress(jsonFile.getBytes());
//            System.out.println("After compressed : "+ compressedJSON.length);
//            System.out.println("After compressed String : " + new String(compressedJSON));
//
//            // Encode JSON FILE
//            byte[] encodingJSON = Base64.encodeBase64(compressedJSON);
//            System.out.println("After encoding : "+ encodingJSON.length);
//            System.out.println("After encoding String : " + new String(encodingJSON));
//
//            ByteArrayWrapper ba = new ByteArrayWrapper();
//            ba.append(encodingJSON, 0, encodingJSON.length);
//            
//            request.setAttribute("jsonFile", ba);
//            RequestDispatcher rd = request.getRequestDispatcher("Registration");
//            rd.forward(request, response);
//            out.println("Done");
//            }
//        } finally {
//            out.close();
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
//        try {
//            processRequest(request, response);
//        } catch (DataFormatException ex) {
//            Logger.getLogger(RegistrationTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
//        try {
//            processRequest(request, response);
//        } catch (DataFormatException ex) {
//            Logger.getLogger(RegistrationTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
