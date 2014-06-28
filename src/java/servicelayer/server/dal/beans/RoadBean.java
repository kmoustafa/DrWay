/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import servicelayer.server.dal.daos.RoadDAO;
import servicelayer.server.dal.pojos.Road;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean (name="roads" , eager = true)
@SessionScoped
public class RoadBean {
    private List<Road> roadList; 
    private List<Integer> hours;
    private List<String> roadNamesList;
    private DataModel<Road> roadModel ;
    private RoadDAO roadDAO;
    
    @ManagedProperty(value="#{road}")
    private Road currentRoad;
    
    @ManagedProperty(value = "#{subroads}")
    private SubRoadBean currentSubRoad;
    
    public RoadBean(){
        int[] intArray = new int[] { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 };
        hours = new ArrayList<Integer>();
        currentRoad = new Road();
        roadDAO = HibernateUtil.getROAD_DAO();
        roadList = roadDAO.getAllRoades();
        roadNamesList = roadDAO.getAllRoadesNames();
        roadModel = new ListDataModel<Road>(roadList);
    }

    public List<Road> getRoadList() {
        return roadList;
    }

    public void setRoadList(List<Road> roadList) {
        this.roadList = roadList;
    }

    public List<String> getRoadNamesList() {
        return roadNamesList;
    }

    public void setRoadNamesList(List<String> roadNamesList) {
        this.roadNamesList = roadNamesList;
    }

    
    public DataModel<Road> getRoadModel() {
        return roadModel;
    }

    public void setRoadModel(DataModel<Road> roadModel) {
        this.roadModel = roadModel;
    }

    public RoadDAO getRoadDAO() {
        return roadDAO;
    }

    public void setRoadDAO(RoadDAO roadDAO) {
        this.roadDAO = roadDAO;
    }

    public Road getCurrentRoad() {
        return currentRoad;
    }

    public void setCurrentRoad(Road currentRoad) {
        this.currentRoad = currentRoad;
    }

    public SubRoadBean getCurrentSubRoad() {
        return currentSubRoad;
    }

    public void setCurrentSubRoad (SubRoadBean currentSubRoads) {
        this.currentSubRoad = currentSubRoads;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours = hours;
    }

    
    
    public String goToDetails(int currentRoadId){
        currentRoad = roadDAO.getRoadById(currentRoadId);
        currentSubRoad.setCurrentRoadId(currentRoadId);
        return "road_details";
    }
    
    public String goToSubRoads (int currentRoadId){
        currentRoad = roadDAO.getRoadById(currentRoadId);
        currentSubRoad.setCurrentRoadId(currentRoadId);
        return "subroads";
    }
    
    public String goToUpdate(int currentRoadId){
        currentRoad = roadDAO.getRoadById(currentRoadId);
        return "edit_road";
    }
    public String goToAdd (){
        currentRoad = new Road();
        return "add_road";
    }
    
    public String getRoadStatus(int roadID){
        Road road = roadDAO.getRoadById(roadID);
        double res = roadDAO.getSubRoadsSpead(road);
        return Math.round( res * 100.0 ) / 100.0 + " Km/hour";
    }
       
    public String addRoad(){
        if(currentRoad!=null){
            if(roadDAO.insert(currentRoad)){
                System.out.println("Road Should Be inserted");
                roadList = roadDAO.getAllRoades();
                roadModel = new ListDataModel<Road>(roadList);
                return "roads";
            }
            else{
                System.err.println("Road didn't inserted");
                return "error_page";
            }
        }
        else{
            System.err.println("Road is null !");
            return "error_page";
        }
    }
    
    public String updateRoad(){
        if(currentRoad!=null){
            if(roadDAO.update(currentRoad)){
                System.out.println("Road Should Be inserted");
                roadList = roadDAO.getAllRoades();
                roadModel = new ListDataModel<Road>(roadList);
                return "roads";
            }
            else{
                System.err.println("Road didn't updated");
                return "error_page";
            }
        }
        else{
            System.err.println("Road is null !");
            return "error_page";
        }
    }
    
    public String deleteRoad(){
        if(roadModel.getRowData()!=null){
            if(roadDAO.delete(roadModel.getRowData())){
                System.out.println("Road Should Be deleted");
                roadList = roadDAO.getAllRoades();
                roadModel = new ListDataModel<Road>(roadList);
                return "roads";
            }
            else{
                System.err.println("Road didn't inserted");
                return "error_page";
            }
        }
        else{
            System.err.println("Road is null !");
            return "error_page";
        }
    }
 
    
}
