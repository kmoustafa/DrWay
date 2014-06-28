/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import servicelayer.server.dal.daos.UserRoadReportDAO;
import servicelayer.server.dal.daos.ReportTypeLookupDAO;
import servicelayer.server.dal.daos.RoadDAO;
import servicelayer.server.dal.pojos.UserRoadReport;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "userRoadReports")
@ViewScoped
public class UserRoadReportBean {
    
    private List<UserRoadReport> reportList;
    private DataModel<UserRoadReport> reportModel ;
    private UserRoadReportDAO userRoadReportDAO;
    private RoadDAO roadDAO;
    //private UserDAO adminDAO;
    private ReportTypeLookupDAO reportTypeLookUpDAO;
    private int roadId ;
    private String roadName="",reportTypeName="",userName="";
    
    @ManagedProperty(value="#{userRoadReport}")
    private UserRoadReport currentUserRoadReport;
  
    public UserRoadReportBean(){
        currentUserRoadReport = new UserRoadReport();
        userRoadReportDAO = HibernateUtil.getUSER_ROAD_REPORT_DAO();
        reportTypeLookUpDAO = HibernateUtil.getREPORT_TYPE_LOOK_UP_DAO();
        roadDAO = HibernateUtil.getROAD_DAO();
        //userDAO = HibernateUtil.get
        reportList = userRoadReportDAO.getAllReports();
        reportModel = new ListDataModel<UserRoadReport>(reportList);
    }

    public List<UserRoadReport> getReportList() {
        return reportList;
    }

    public void setReportList(List<UserRoadReport> reportList) {
        this.reportList = reportList;
    }

    public DataModel<UserRoadReport> getReportModel() {
        return reportModel;
    }

    public void setReportModel(DataModel<UserRoadReport> reportModel) {
        this.reportModel = reportModel;
    }

    public UserRoadReportDAO getUserRoadReportDAO() {
        return userRoadReportDAO;
    }

    public void setUserRoadReportDAO(UserRoadReportDAO userRoadReportDAO) {
        this.userRoadReportDAO = userRoadReportDAO;
    }

    public RoadDAO getRoadDAO() {
        return roadDAO;
    }

    public void setRoadDAO(RoadDAO roadDAO) {
        this.roadDAO = roadDAO;
    }

    public ReportTypeLookupDAO getReportTypeLookUpDAO() {
        return reportTypeLookUpDAO;
    }

    public void setReportTypeLookUpDAO(ReportTypeLookupDAO reportTypeLookUpDAO) {
        this.reportTypeLookUpDAO = reportTypeLookUpDAO;
    }

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getReportTypeName() {
        return reportTypeName;
    }

    public void setReportTypeName(String reportTypeName) {
        this.reportTypeName = reportTypeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserRoadReport getCurrentUserRoadReport() {
        return currentUserRoadReport;
    }

    public void setCurrentUserRoadReport(UserRoadReport currentUserRoadReport) {
        this.currentUserRoadReport = currentUserRoadReport;
    }
    
    public String goToAdd (){
        return "add_userRoadReport";
    }
    public String goToUpdate(int userRoadReportId){
        currentUserRoadReport = userRoadReportDAO.getUserRoadReportById(userRoadReportId);
        return "edit_userRoadReport";
    }
    
     public void restore(){
        reportList = userRoadReportDAO.getAllReports();
        reportModel = new ListDataModel<UserRoadReport>(reportList);
         System.out.println("Calling restore function in userRoadReport Bean");
    }
    
    public String addAdminRoadReport(){
        if(currentUserRoadReport!=null && roadName!=null && userName!=null){
            if(roadDAO.getRoadByName(roadName)!=null){
                currentUserRoadReport.setRoad(roadDAO.getRoadByName(roadName));
            }
            else{
                System.err.println("road is null !");
                return "error_page";
            }
            if(reportTypeLookUpDAO.getReportLookupByName(reportTypeName)!= null){
                currentUserRoadReport.setReportTypeLookUp(reportTypeLookUpDAO.getReportLookupByName(reportTypeName));
            }
            else{
                 System.err.println("reportTypeLookup is null !");
                 return "error_page";
            }
//            if(adminDAO.getAdminByUserName(adminName)!=null){
//                currentAdminRoadReport.setAdmin(adminDAO.getAdminByUserName(adminName));
//            }
//            else{
//                System.err.println("admin is null !");
//                return "error_page";
//            }
            if(userRoadReportDAO.insert(currentUserRoadReport)){
                System.out.println("AdminRoadReport Should be inserted");
                reportList = userRoadReportDAO.getAllReports();
                reportModel = new ListDataModel<UserRoadReport>(reportList);
                return "admin_reports";
            }
            else{
                System.err.println("AdminRoadReport didn't inserted !");
                return "error_page";
            }
        }
        else{
            System.err.println("AdminRoadReport is null !");
            return "error_page";
        }
    }
    public String updateAdminRoadReport(){
        if(currentUserRoadReport!=null){
            if(currentUserRoadReport.getRoad().getName()!=null){
                currentUserRoadReport.setRoad(roadDAO.getRoadByName(currentUserRoadReport.getRoad().getName()));
            }
            else{
                System.err.println("road is null !");
                return "error_page";
            }
            if(currentUserRoadReport.getReportTypeLookUp().getReportTypeValue()!= null){
                currentUserRoadReport.setReportTypeLookUp(reportTypeLookUpDAO.getReportLookupByName(currentUserRoadReport.getReportTypeLookUp().getReportTypeValue()));
            }
            else{
                 System.err.println("reportTypeLookup is null !");
                 return "error_page";
            }
//            if(currentUserRoadReport.getAdmin().getName()!=null){
//                currentUserRoadReport.setAdmin(adminDAO.getAdminByUserName(currentAdminRoadReport.getAdmin().getUsername()));
//            }
//            else{
//                System.err.println("admin is null !");
//                return "error_page";
//            }
            if(userRoadReportDAO.update(currentUserRoadReport)){
                System.out.println("AdminRoadReport Should be inserted");
                reportList = userRoadReportDAO.getAllReports();
                reportModel = new ListDataModel<UserRoadReport>(reportList);
                return "admin_reports";
            }
            else{
                System.err.println("AdminRoadReport didn't inserted !");
                return "error_page";
            }
        }
        else{
            System.err.println("AdminRoadReport is null !");
            return "error_page";
        }
    }
     public String deleteAdminRoadReport(){
        if(reportModel.getRowData()!=null){
            if(userRoadReportDAO.delete(reportModel.getRowData())){
                System.out.println("AdminReport Should be deleted");
                 reportList = userRoadReportDAO.getAllReports();
                 reportModel = new ListDataModel<UserRoadReport>(reportList);
                return "admin_reports";
            }
            else{
                System.err.println("AdminReport didn't updated !");
                return "error_page";
            }
        }
        else{
                System.err.println("AdminReport is null !");
                return "error_page";
            }
        
    }
}
