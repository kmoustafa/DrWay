/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.beans;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import servicelayer.server.dal.daos.MainPointDAO;
import servicelayer.server.dal.pojos.MainPoint;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean (name="mainPoints")
@SessionScoped
public class MainPointBean implements Serializable{
    private List<MainPoint> mainPointList; 
    private List<String> mainPointListNames; 
    private DataModel<MainPoint> mainPointModel ;
    private MainPointDAO mainPointDAO;
    
    @ManagedProperty(value="#{mainPoint}")
    private MainPoint currentMainPoint;
    
    
    public MainPointBean(){
        
        currentMainPoint = new MainPoint();
        mainPointDAO = HibernateUtil.getMAIN_POINT_DAO();
        mainPointList = mainPointDAO.getAllMainPoints();
        mainPointListNames = mainPointDAO.getAllMainPointsNames();
        mainPointModel = new ListDataModel<MainPoint>(mainPointList);
    }

    public List<String> getMainPointListNames() {
        return mainPointDAO.getAllMainPointsNames();
    }

    
    public void setMainPointListNames(List<String> mainPointListNames) {
        this.mainPointListNames = mainPointDAO.getAllMainPointsNames();
    }
    
    public List<MainPoint> getMainPointList() {
        return mainPointList;
    }

    public void setMainPointList(List<MainPoint> mainPointList) {
        this.mainPointList = mainPointList;
    }

    public DataModel<MainPoint> getMainPointModel() {
        return mainPointModel;
    }

    public void setMainPointModel(DataModel<MainPoint> mainPointModel) {
        this.mainPointModel = mainPointModel;
    }

    public MainPointDAO getMainPointDAO() {
        return mainPointDAO;
    }

    public void setMainPointDAO(MainPointDAO mainPointDAO) {
        this.mainPointDAO = mainPointDAO;
    }
   
    public String goToUpdate(int currentMainPointId){
        currentMainPoint = mainPointDAO.getMainPointByID(currentMainPointId);
        return "edit_mainpoint";
    }
    public String goToAdd (){
        currentMainPoint = new MainPoint();
        return "add_mainpoint";
    }

    public MainPoint getCurrentMainPoint() {
        return currentMainPoint;
    }

    public void setCurrentMainPoint(MainPoint currentMainPoint) {
        this.currentMainPoint = currentMainPoint;
    }
    
    public void restore(){
        mainPointListNames = mainPointDAO.getAllMainPointsNames();
        mainPointModel = new ListDataModel<MainPoint>(mainPointList);
    }
    
     public String addMainPoint(){
        if(currentMainPoint!=null){
            if(mainPointDAO.insert(currentMainPoint)){
            System.out.println("MainPoint Should be updated");
            mainPointList = mainPointDAO.getAllMainPoints();
            mainPointModel = new ListDataModel<MainPoint>(mainPointList);
            return "mainpoints";
            }
            else{
                System.err.println("Main Point didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("Main Point is null !");
            return "error_page";
        }
            
    }
    
    public String updateMainPoint(){
        if(currentMainPoint!=null){
            if(mainPointDAO.update(currentMainPoint)){
            System.out.println("MainPoint Should be updated");
            mainPointList = mainPointDAO.getAllMainPoints();
            mainPointModel = new ListDataModel<MainPoint>(mainPointList);
            return "mainpoints";
            }
            else{
                System.err.println("Main Point didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("Main Point is null !");
            return "error_page";
        }
            
        
    }
    
    public String deleteMainPoint(){
        if(mainPointModel.getRowData()!=null){
            if(mainPointDAO.delete(mainPointModel.getRowData())){
                System.out.println("MainPoint Should be deleted");
                mainPointList = mainPointDAO.getAllMainPoints();
                mainPointModel = new ListDataModel<MainPoint>(mainPointList);
                return "mainpoints";
            }
            else{
                System.err.println("Main Point didn't updated !");
                return "error_page";
            }
        }
        else{
                System.err.println("Main Point is null !");
                return "error_page";
            }
        
    }
   
}
