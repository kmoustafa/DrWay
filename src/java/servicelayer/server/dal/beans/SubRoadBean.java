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
import servicelayer.server.dal.daos.RoadDAO;
import servicelayer.server.dal.daos.SubRoadDAO;
import servicelayer.server.dal.pojos.Road;
import servicelayer.server.dal.pojos.SubRoad;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "subroads" ,eager = true)
@SessionScoped
public class SubRoadBean {

    private List<SubRoad> subroadList;
    private DataModel<SubRoad> subroadModel;
    private SubRoadDAO subroadDAO;
    private RoadDAO roadDAO;
    private int currentRoadId ; 
    private String parentRoadName;
    
    @ManagedProperty(value = "#{subroad}")
    private SubRoad currentSubRoad;
    
    @ManagedProperty(value="#{road}")
    private Road currentRoad;
    
    public SubRoadBean() {
        roadDAO = HibernateUtil.getROAD_DAO();
        subroadDAO = HibernateUtil.getSUB_ROAD_DAO();
        currentRoad = roadDAO.getRoadById(currentRoadId);
        subroadList = subroadDAO.getSubRoadsOfRoad(currentRoad);
        subroadModel = new ListDataModel<SubRoad>(subroadList);
        
    }

    public List<SubRoad> getSubroadList() {
        return subroadList;
    }

    public void setSubroadList(List<SubRoad> subroadList) {
        this.subroadList = subroadList;
    }

    public DataModel<SubRoad> getSubroadModel() {
        return subroadModel;
    }

    public void setSubroadModel(DataModel<SubRoad> subroadModel) {
        this.subroadModel = subroadModel;
    }

    public SubRoadDAO getSubroadDAO() {
        return subroadDAO;
    }

    public void setSubroadDAO(SubRoadDAO subroadDAO) {
        this.subroadDAO = subroadDAO;
    }

    public RoadDAO getRoadDAO() {
        return roadDAO;
    }

    public void setRoadDAO(RoadDAO roadDAO) {
        this.roadDAO = roadDAO;
    }

    public void setCurrentRoadId(int currentRoadId) {
        this.currentRoadId = currentRoadId;
        subroadList = subroadDAO.getSubRoadsOfRoad(currentRoadId);
        subroadModel = new ListDataModel<SubRoad>(subroadList);
    }
    

    public int getCurrentRoadId() {
        return currentRoadId;
    }
    
    public Road getCurrentRoad() {
        return roadDAO.getRoadById(currentRoadId);
    }

    public void setCurrentRoad(Road currentRoad) {
        this.currentRoad = roadDAO.getRoadById(currentRoadId);
    }

    public SubRoad getCurrentSubRoad() {
        return currentSubRoad;
    }

    public void setCurrentSubRoad(SubRoad currentSubRoad) {
        this.currentSubRoad = currentSubRoad;
    }

    public String getParentRoadName() {
        return parentRoadName;
    }

    public void setParentRoadName(String parentRoadName) {
        this.parentRoadName = parentRoadName;
    }
    
    
    public String goToAdd(){
        currentSubRoad = new SubRoad();
        return "add_subroad";
    }
    public String goToUpdate(int currentSubRoadId){
        currentSubRoad = subroadDAO.getSubRoadById(currentSubRoadId);
        return "edit_subroad";
    }

    public String addSubRoad() {
        if(currentSubRoad!=null){
            currentSubRoad.setRoad(roadDAO.getRoadByName(parentRoadName));
            if(subroadDAO.insert(currentSubRoad)){
                System.out.println("subroad should be inserted ");
                subroadList = subroadDAO.getSubRoadsOfRoad(currentRoad);
                subroadModel = new ListDataModel<SubRoad>(subroadList);
                return "subroads";
            }
            else{
                System.err.println("subroad didn't inserted !");
                return "error_page";
            }
        }
        else{
                System.err.println("subroad is null !");
                return "error_page";
            }
    }

    
    public String updateSubRoad() {
        if(currentSubRoad!=null){
            currentSubRoad.setRoad(roadDAO.getRoadByName(parentRoadName));
            if(subroadDAO.update(currentSubRoad)){
                System.out.println("subroad should be inserted ");
                subroadList = subroadDAO.getSubRoadsOfRoad(currentRoad);
                subroadModel = new ListDataModel<SubRoad>(subroadList);
                return "subroads";
            }
            else{
                System.err.println("subroad didn't updated !");
                return "error_page";
            }
        }
        else{
                System.err.println("subroad is null !");
                return "error_page";
            }
    }

    public String deleteSubRoad() {
        if(subroadModel.getRowData()!=null){
            if(subroadDAO.delete(subroadModel.getRowData())){
                System.out.println("subroad should be inserted ");
                subroadList = subroadDAO.getSubRoadsOfRoad(currentRoad);
                subroadModel = new ListDataModel<SubRoad>(subroadList);
                return "subroads";
            }
            else{
                System.err.println("subroad didn't deleted !");
                return "error_page";
            }
        }
        else{
                System.err.println("subroad is null !");
                return "error_page";
            }
    }

    
}
