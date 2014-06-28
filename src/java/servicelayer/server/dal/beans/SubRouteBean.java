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
import servicelayer.server.dal.daos.RouteDAO;
import servicelayer.server.dal.daos.SubRouteDAO;
import servicelayer.server.dal.pojos.Route;
import servicelayer.server.dal.pojos.SubRoute;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "subroutes" ,eager = true)
@SessionScoped
public class SubRouteBean {
    private List<SubRoute> subrouteList;
    private DataModel<SubRoute> subrouteModel;
    private SubRouteDAO subrouteDAO;
    private RouteDAO routeDAO;
    private RoadDAO roadDAO;
    //private int currentSubRouteId;
    private int currentRouteId;
    private String parentRoadName,order;
    
    @ManagedProperty(value="#{subroute}")
    private SubRoute currentSubRoute;
    
    @ManagedProperty(value="#{route}")
    private Route currentRoute;
    
    
    public SubRouteBean(){
        //currentRoute = new Route();
        //currentSubRoute = new SubRoute();
        routeDAO = HibernateUtil.getROUTE_DAO();
        roadDAO = HibernateUtil.getROAD_DAO();
        subrouteDAO = HibernateUtil.getSub_Route_DAO();
        currentRoute = routeDAO.getRouteById(currentRouteId);
        subrouteList = subrouteDAO.getSubRoutesOfRoute(currentRoute);
        subrouteModel =  new ListDataModel<SubRoute>(subrouteList);
    }

    public List<SubRoute> getSubrouteList() {
        return subrouteList;
    }

    public void setSubrouteList(List<SubRoute> subrouteList) {
        this.subrouteList = subrouteList;
    }

    public DataModel<SubRoute> getSubrouteModel() {
        return subrouteModel;
    }

    public void setSubrouteModel(DataModel<SubRoute> subrouteModel) {
        this.subrouteModel = subrouteModel;
    }

    public SubRouteDAO getSubrouteDAO() {
        return subrouteDAO;
    }

    public void setSubrouteDAO(SubRouteDAO subrouteDAO) {
        this.subrouteDAO = subrouteDAO;
    }

    public RouteDAO getRouteDAO() {
        return routeDAO;
    }

    public void setRouteDAO(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    public RoadDAO getRoadDAO() {
        return roadDAO;
    }

    public void setRoadDAO(RoadDAO roadDAO) {
        this.roadDAO = roadDAO;
    }
    

    public void setCurrentRouteId(int currentRouteId) {
        this.currentRouteId = currentRouteId;
        subrouteList = subrouteDAO.getSubRoutesOfRoute(currentRouteId);
        subrouteModel = new ListDataModel<SubRoute>(subrouteList);
    }

    public int getCurrentRouteId() {
        return currentRouteId;
    }

    public String getParentRoadName() {
        return parentRoadName;
    }

    public void setParentRoadName(String parentRoadName) {
        this.parentRoadName = parentRoadName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    
    public Route getCurrentRoute() {
        return routeDAO.getRouteById(currentRouteId);
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = routeDAO.getRouteById(currentRouteId);
    }

    public SubRoute getCurrentSubRoute() {
        return currentSubRoute;
    }

    public void setCurrentSubRoute(SubRoute currentSubRoute) {
        this.currentSubRoute = currentSubRoute;
    }

//    public int getCurrentSubRouteId() {
//        return currentSubRouteId;
//    }
//
//    public void setCurrentSubRouteId(int currentSubRouteId) {
//        this.currentSubRouteId = currentSubRouteId;
//    }
    

    public String goToAdd(Route currentRoute){
        currentSubRoute = new SubRoute();
        this.currentRoute = currentRoute;
        return "add_subroute";
    }
    public String goToUpdate(int currentSubRouteId){
        currentSubRoute = subrouteDAO.getSubRouteById(currentSubRouteId);
        System.out.println("currentSubRoute Name : "+currentSubRoute.getId());
        return "edit_subroute";
    }
    
    public String addSubRoute() {
        if(currentSubRoute!=null && parentRoadName!=null  ){
            currentSubRoute.setRoute(currentRoute);
            System.out.println("Name of route of subroute : "+currentSubRoute.getRoute().getName());
            currentSubRoute.setRoad(roadDAO.getRoadByName(parentRoadName));
            System.out.println("Name of road of subroute : "+ currentSubRoute.getRoad().getName());
            //currentSubRoute.setOrder(Integer.parseInt(order));
            System.out.println("Order of subroute : "+currentSubRoute.getOrderr());
            if(subrouteDAO.insert(currentSubRoute)){
                System.out.println("subroute should be inserted ");
                subrouteList = subrouteDAO.getSubRoutesOfRoute(currentRoute);
                subrouteModel = new ListDataModel<SubRoute>(subrouteList);
                return "subroutes";
            }
            else{
                System.err.println("subroute didn't inserted !");
                return "error_page";
            }
        }
        else{
                System.err.println("subroute is null!");
                return "error_page";
            }
    }

    public String updateSubRoute(){
        
        if(currentSubRoute!=null){
            currentSubRoute.setRoute(routeDAO.getRouteById(currentRouteId));
            currentSubRoute.setRoad(roadDAO.getRoadByName(parentRoadName));
            if(subrouteDAO.update(currentSubRoute)){
                System.out.println("subroute should be updated ");
                currentRoute = routeDAO.getRouteById(currentRouteId);
                subrouteList = subrouteDAO.getSubRoutesOfRoute(currentRoute);
                subrouteModel = new ListDataModel<SubRoute>(subrouteList);
                return "subroutes";
            }
            else{
                System.err.println("subroute didn't inserted !");
                return "error_page";
            }
        }
        else{
                System.err.println("subroute is null!");
                return "error_page";
            }
    }
    
    public String deleteSubRoute(){
        if(subrouteModel.getRowData()!=null){
            if(subrouteDAO.delete(subrouteModel.getRowData())){
                System.out.println("subroute should be inserted ");
                currentRoute = routeDAO.getRouteById(currentRouteId);
                subrouteList = subrouteDAO.getSubRoutesOfRoute(currentRoute);
                subrouteModel = new ListDataModel<SubRoute>(subrouteList);
                return "subroutes";
            }
            else{
                System.err.println("subroute didn't inserted !");
                return "error_page";
            }
        }
        else{
                System.err.println("subroute is null!");
                return "error_page";
            }
    }
}
