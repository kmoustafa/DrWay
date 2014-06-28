/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import servicelayer.server.dal.pojos.MainPoint;
import servicelayer.server.dal.pojos.Route;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ghazala
 */
public class RouteDAO {

    private final Session session;

    public RouteDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }
    
    public List<Route> getAllRoutes() {
        try{
            List<Route> routes = session.createCriteria(Route.class).list();
            if(routes!=null && !routes.isEmpty()){
                for(Route route : routes){
                    session.refresh(route);
                }
            }
            return routes;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     public List<String> getAllRoutesNames(){
         return session.createCriteria(Route.class).setProjection(Projections.property("name")).list();
     }
     
     public int getTotalNumberOfRoutes() {
        return session.createCriteria(Route.class).list().size();
    }

    public List<Route> getRoutesFromPointToPoint(MainPoint src, MainPoint dest) {
        try{
        Criteria cr = session.createCriteria(Route.class);
        cr.add(Restrictions.eq("mainPointBySrcId", src));
        cr.add(Restrictions.eq("mainPointByDestId", dest));
        List<Route> routes = cr.list();
        if(routes!=null && !routes.isEmpty()){
            for(Route route : routes){
                session.refresh(route);
            }
        }
        return routes;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public Route getRouteById(int routeId) {
    
        try{
        if(routeId==0){
            return null;
        } else { 
            Route route = (Route)session.get(Route.class, routeId);
            if(route!=null){
                //session.evict(route);
                session.refresh(route);
            }
            return route ;
        }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
     }
    
     public Route getRouteByName(String routeName) {
    
         try{
            Criteria criteria = this.session.createCriteria(Route.class);
            criteria.add(Restrictions.eq("name",routeName));
            Route route =  (Route)criteria.uniqueResult() ;
            if(route!=null){
                session.refresh(route);
            }
            return route ;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
     }
     

    public boolean update(Route route){
        if(route==null ){
            System.err.println("route is null !");
            return false;
        }
        else if(route.getMainPointBySrcId()==null ){
            System.err.println("route.SrcId is null !");
            return false;
        }
        else if (route.getMainPointByDestId()==null){
            System.err.println("route.DestId is null !");
            return false;
        }
         else {
            try{
            session.getTransaction().begin();
            session.saveOrUpdate(route);  
            session.getTransaction().commit();
            session.evict(route);
            System.out.println("route is updated");
            return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error in DB insertion ");
                return false;

            }
        }
    }
    
    public boolean delete(Route route){
         if(route==null ){
            System.err.println("route is null !");
            return false;
        }
        else if(route.getMainPointBySrcId()==null ){
            System.err.println("route.SrcId is null !");
            return false;
        }
        else if (route.getMainPointByDestId()==null){
            System.err.println("route.DestId is null !");
            return false;
        }
         else {
            try{
            session.getTransaction().begin();
            session.delete(route);  
            session.getTransaction().commit();
            System.out.println("route is deleted");
            return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error in DB insertion ");
                return false;

            }
        }
    }
    
    public boolean insert(Route route){
        if(route==null ){
            System.err.println("route is null !");
            return false;
        }
        else if(route.getMainPointBySrcId()==null ){
            System.err.println("route.SrcId is null !");
            return false;
        }
        else if (route.getMainPointByDestId()==null){
            System.err.println("route.DestId is null !");
            return false;
        }
         else {
            try{
            session.getTransaction().begin();
            session.persist(route);  
            session.getTransaction().commit();
            session.evict(route);
            System.out.println("route is inserted");
            return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error in DB insertion ");
                return false;

            }
        }
    }
//    public static void main(String[] args){
//        Route route = new Route();
//        route.setMainPointBySrcId(HibernateUtil.getMAIN_POINT_DAO().getMainPointByID(1));
//        route.setMainPointByDestId(HibernateUtil.getMAIN_POINT_DAO().getMainPointByID(2));
//        boolean res = HibernateUtil.getROUTE_DAO().insert(route);
//        System.out.println(res);
//        
//    }
}
