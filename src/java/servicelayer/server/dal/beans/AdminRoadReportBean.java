/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import servicelayer.server.dal.daos.AdminDAO;
import servicelayer.server.dal.daos.AdminRoadReportDAO;
import servicelayer.server.dal.daos.ReportTypeLookupDAO;
import servicelayer.server.dal.daos.RoadDAO;
import servicelayer.server.dal.pojos.AdminRoadReport;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "adminRoadReports")
@SessionScoped
public class AdminRoadReportBean {
    
    private List<AdminRoadReport> reportList;
    private DataModel<AdminRoadReport> reportModel ;
    private AdminRoadReportDAO adminRoadREportDAO;
    private RoadDAO roadDAO;
    private AdminDAO adminDAO;
    private ReportTypeLookupDAO reportTypeLookUpDAO;
    private int roadId ;
    private String roadName="",reportTypeName="",adminName="";
    
    @ManagedProperty(value="#{adminRoadReport}")
    private AdminRoadReport currentAdminRoadReport;
  
    
    public AdminRoadReportBean(){
        currentAdminRoadReport = new AdminRoadReport();
        adminRoadREportDAO = HibernateUtil.getADMIN_ROAD_REPORT_DAO();
        reportTypeLookUpDAO = HibernateUtil.getREPORT_TYPE_LOOK_UP_DAO();
        roadDAO = HibernateUtil.getROAD_DAO();
        adminDAO = HibernateUtil.getADMIN_DAO();
        reportList = adminRoadREportDAO.getAllReports();
        reportModel = new ListDataModel<AdminRoadReport>(reportList);
    }

    public List<AdminRoadReport> getReportList() {
        return reportList;
    }

    public void setReportList(List<AdminRoadReport> reportList) {
        this.reportList = reportList;
    }

    public DataModel<AdminRoadReport> getReportModel() {
        return reportModel;
    }

    public void setReportModel(DataModel<AdminRoadReport> reportModel) {
        this.reportModel = reportModel;
    }

    public AdminRoadReportDAO getAdminRoadREportDAO() {
        return adminRoadREportDAO;
    }

    public void setAdminRoadREportDAO(AdminRoadReportDAO adminRoadREportDAO) {
        this.adminRoadREportDAO = adminRoadREportDAO;
    }

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public AdminRoadReport getCurrentAdminRoadReport() {
        return currentAdminRoadReport;
    }

    public void setCurrentAdminRoadReport(AdminRoadReport currentAdminRoadReport) {
        this.currentAdminRoadReport = currentAdminRoadReport;
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
    
       public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
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

    public AdminDAO getAdminDAO() {
        return adminDAO;
    }

    public void setAdminDAO(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }
    
    
    public String goToAdd (){
        currentAdminRoadReport = new AdminRoadReport();
        return "add_adminRoadReport";
    }
    public String goToUpdate(int adminRoadReportId){
        currentAdminRoadReport = adminRoadREportDAO.getAdminRoadReportById(adminRoadReportId);
        return "edit_adminRoadReport";
    }
    
    public void restore(){
        reportList = adminRoadREportDAO.getAllReports();
        reportModel = new ListDataModel<AdminRoadReport>(reportList);
    }
    
    public String addAdminRoadReport(){
        if(currentAdminRoadReport!=null && roadName!=null && adminName!=null){
            if(roadDAO.getRoadByName(roadName)!=null){
                currentAdminRoadReport.setRoad(roadDAO.getRoadByName(roadName));
            }
            else{
                System.err.println("road is null !");
                return "error_page";
            }
            if(reportTypeLookUpDAO.getReportLookupByName(reportTypeName)!= null){
                currentAdminRoadReport.setReportTypeLookUp(reportTypeLookUpDAO.getReportLookupByName(reportTypeName));
            }
            else{
                 System.err.println("reportTypeLookup is null !");
                 return "error_page";
            }
            if(adminDAO.getAdminByUserName(adminName)!=null){
                currentAdminRoadReport.setAdmin(adminDAO.getAdminByUserName(adminName));
            }
            else{
                System.err.println("admin is null !");
                return "error_page";
            }
            if(adminRoadREportDAO.insert(currentAdminRoadReport)){
                System.out.println("AdminRoadReport Should be inserted");
                reportList = adminRoadREportDAO.getAllReports();
                reportModel = new ListDataModel<AdminRoadReport>(reportList);
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
        if(currentAdminRoadReport!=null){
            if(currentAdminRoadReport.getRoad().getName()!=null){
                currentAdminRoadReport.setRoad(roadDAO.getRoadByName(currentAdminRoadReport.getRoad().getName()));
            }
            else{
                System.err.println("road is null !");
                return "error_page";
            }
            if(currentAdminRoadReport.getReportTypeLookUp().getReportTypeValue()!= null){
                currentAdminRoadReport.setReportTypeLookUp(reportTypeLookUpDAO.getReportLookupByName(reportTypeName));
            }
            else{
                 System.err.println("reportTypeLookup is null !");
                 return "error_page";
            }
            if(currentAdminRoadReport.getAdmin().getName()!=null){
                currentAdminRoadReport.setAdmin(adminDAO.getAdminByUserName(currentAdminRoadReport.getAdmin().getUsername()));
            }
            else{
                System.err.println("admin is null !");
                return "error_page";
            }
            if(adminRoadREportDAO.update(currentAdminRoadReport)){
                System.out.println("AdminRoadReport Should be inserted");
                reportList = adminRoadREportDAO.getAllReports();
                reportModel = new ListDataModel<AdminRoadReport>(reportList);
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
            if(adminRoadREportDAO.delete(reportModel.getRowData())){
                System.out.println("AdminReport Should be deleted");
                 reportList = adminRoadREportDAO.getAllReports();
                 reportModel = new ListDataModel<AdminRoadReport>(reportList);
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
