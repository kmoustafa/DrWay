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
import servicelayer.server.dal.daos.MainPointDAO;
import servicelayer.server.dal.daos.RouteDAO;
import servicelayer.server.dal.pojos.Route;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean (name="routes", eager = true)
@SessionScoped
public class RouteBean {
    private List<Route> routeList; 
    private List<String> routesNamesList;
    private DataModel<Route> routeModel ;
    private RouteDAO routeDAO;
    private String sourceName, destName;
    private MainPointDAO mainPointDAO;
    private int totalNumberOfRoutes;
    
    @ManagedProperty(value="#{route}")
    private Route currentRoute;
    
    @ManagedProperty (value="#{subroutes}")
    private SubRouteBean currentSubRoute;
    
    public RouteBean(){
        currentRoute = new Route();
        routeDAO = HibernateUtil.getROUTE_DAO();
        routeList = routeDAO.getAllRoutes();
        routesNamesList = routeDAO.getAllRoutesNames();
        routeModel = new ListDataModel<Route>(routeList);
        mainPointDAO = HibernateUtil.getMAIN_POINT_DAO();
        totalNumberOfRoutes = routeDAO.getTotalNumberOfRoutes();
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public List<String> getRoutesNamesList() {
        return routesNamesList;
    }

    public void setRoutesNamesList(List<String> routesNamesList) {
        this.routesNamesList = routesNamesList;
    }
    

    public DataModel<Route> getRouteModel() {
        return routeModel;
    }

    public void setRouteModel(DataModel<Route> routeModel) {
        this.routeModel = routeModel;
    }

    public RouteDAO getRouteDAO() {
        return routeDAO;
    }

    public void setRouteDAO(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }
    
    public SubRouteBean getCurrentSubRoute() {
        return currentSubRoute;
    }

    public void setCurrentSubRoute(SubRouteBean currentSubRoute) {
        this.currentSubRoute = currentSubRoute;
    }
    
     public void setMainPointDAO(MainPointDAO mainPointDAO) {
        this.mainPointDAO = mainPointDAO;
    }

    public MainPointDAO getMainPointDAO() {
        return mainPointDAO;
    }
    

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public int getTotalNumberOfRoutes() {
        return totalNumberOfRoutes;
    }

    public void setTotalNumberOfRoutes(int totalNumberOfRoutes) {
        this.totalNumberOfRoutes = totalNumberOfRoutes;
    }
    

    public String goToDetails(int currentRoadId){
        currentRoute = routeDAO.getRouteById(currentRoadId);
        currentSubRoute.setCurrentRouteId(currentRoadId);
        return "route_details";
    }
    
    public String goToAdd (){
        currentRoute = new Route();
        return "add_route";
    }
    
    public void restore(){
        mainPointDAO = HibernateUtil.getMAIN_POINT_DAO();
        totalNumberOfRoutes = routeDAO.getTotalNumberOfRoutes();
    }
    
    public String goToUpdate(int currentRouteId, String sourceName, String destName){
        currentRoute = routeDAO.getRouteById(currentRouteId);
        this.sourceName = sourceName;
        this.destName = destName;
        return "edit_route";
    }
    public String goToSubRoutes(int currentRouteId){
        currentRoute = routeDAO.getRouteById(currentRouteId);
        currentSubRoute.setCurrentRouteId(currentRouteId);
        return "subroutes";
    }
    
    public String  addRoute(){
        if(sourceName!=null && destName!=null && currentRoute!=null){
            currentRoute.setMainPointBySrcId(mainPointDAO.getMainPointByName(sourceName));
            currentRoute.setMainPointByDestId(mainPointDAO.getMainPointByName(destName));
            if(routeDAO.insert(currentRoute)){
            System.out.println("Route Should Be inserted");
            routeList = routeDAO.getAllRoutes();
            routeModel = new ListDataModel<Route>(routeList);
            return "routes";
            }
            else{
                System.err.println("Route didn't inserted");
                return "error_page";
            }
        }
        else{
            System.err.println("Route is null !");
            return "error_page";
        }
        
    }
    
    public String updateRoute(){
       if(currentRoute!=null){
            currentRoute.setMainPointBySrcId(mainPointDAO.getMainPointByName(sourceName));
            currentRoute.setMainPointByDestId(mainPointDAO.getMainPointByName(destName));
            if(routeDAO.update(currentRoute)){
            System.out.println("Route Should Be inserted");
            routeList = routeDAO.getAllRoutes();
            routeModel = new ListDataModel<Route>(routeList);
            return "routes";
            }
            else{
                System.err.println("Route didn't inserted");
                return "error_page";
            }
        }
        else{
            System.err.println("Route is null !");
            return "error_page";
        }
    }
    
    public String deleteRoute(){
        if(routeModel.getRowData()!=null){
            if(routeDAO.delete(routeModel.getRowData())){
                System.out.println("MainPoint Should be deleted");
                 routeList = routeDAO.getAllRoutes();
                 routeModel = new ListDataModel<Route>(routeList);
                return "routes";
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
