/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.Route;
import servicelayer.server.dal.pojos.SubRoute;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
public class SubRouteDAO {

    private final Session session;

    public SubRouteDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public List<SubRoute> getSubRoutesOfRoute(Route route) {
        try{
            Criteria criteria = this.session.createCriteria(SubRoute.class);
            criteria.add(Restrictions.eq("route", route));
            List<SubRoute> subRoutes = criteria.list();
            if(subRoutes!=null && !subRoutes.isEmpty()){
                for(SubRoute subRoute : subRoutes){
                    session.refresh(subRoute);
                }
            }
            return subRoutes;
        }catch(Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    public List<SubRoute> getSubRoutesOfRoute(int routeId) {
        try{
            Criteria criteria = this.session.createCriteria(SubRoute.class);
            RouteDAO routeDAO = HibernateUtil.getROUTE_DAO();
            Route route = routeDAO.getRouteById(routeId);
            criteria.add(Restrictions.eq("route", route));
            List<SubRoute> subRoutes = criteria.list();
            if(subRoutes!=null && !subRoutes.isEmpty()){
                for(SubRoute subRoute : subRoutes){
                    session.refresh(subRoute);
                }
            }
            return subRoutes;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public SubRoute getSubRouteById(int subRouteId) {

        if (subRouteId == 0) {
            return null;
        } else {
            try {
                SubRoute subRoute = (SubRoute) session.get(SubRoute.class, subRouteId);
                if(subRoute!=null){
                    session.refresh(subRoute);
                }
                return subRoute;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean update(SubRoute subRoute) {
        if (subRoute == null) {
            System.err.println("subRoute is null !");
            return false;
        } else if (subRoute.getRoute() == null) {
            System.err.println("subRoute.route is null !");
            return false;
        } else if (subRoute.getRoad() == null) {
            System.err.println("subRoute.road is null !");
            return false;
        } else if (subRoute.getOrderr() == 0) {
            System.err.println("subRoute.order is zero !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(subRoute);
                session.getTransaction().commit();
                session.evict(subRoute);
                System.out.println("Object is updated");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean delete(SubRoute subRoute) {
        if (subRoute == null) {
            System.err.println("subRoute is null !");
            return false;
        } else if (subRoute.getRoute() == null) {
            System.err.println("subRoute.route is null !");
            return false;
        } else if (subRoute.getRoad() == null) {
            System.err.println("subRoute.road is null !");
            return false;
        } else if (subRoute.getOrderr() == 0) {
            System.err.println("subRoute.order is zero !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.delete(subRoute);
                session.getTransaction().commit();
                System.out.println("Object is deleted");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean insert(SubRoute subRoute) {
        if (subRoute == null) {
            System.err.println("subRoute is null !");
            return false;
        } else if (subRoute.getRoute() == null) {
            System.err.println("subRoute.route is null !");
            return false;
        } else if (subRoute.getRoad() == null) {
            System.err.println("subRoute.road is null !");
            return false;
        } else if (subRoute.getOrderr() == 0) {
            System.err.println("subRoute.order is zero !");
            return false;
        } else {
            try {
                System.out.println("subroute object is not null !");
                session.beginTransaction();
                session.persist(subRoute);
                session.getTransaction().commit();
                session.evict(subRoute);
                System.out.println("subroute is inserted");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error in DB insertion ");
                return false;
            }
        }
    }
    
//    public static void main (String[] args){
//        Route route = HibernateUtil.getROUTE_DAO().getRouteById(9);
//        System.out.println("route name : "+route.getName());
//        Road road = HibernateUtil.getROAD_DAO().getRoadById(25);
//        System.out.println("road name : "+road.getName());
//        SubRoute subRoute = new SubRoute();
//        //subRoute.setId(10);
//        subRoute.setRoute(route);
//        subRoute.setRoad(road);
//        subRoute.setOrder(1);
//        System.out.println("Sub route ID  : "+subRoute.getId());
//        System.out.println("route Id : "+subRoute.getRoute().getId());
//        System.out.println("road Id : "+subRoute.getRoad().getId());
//        boolean res = HibernateUtil.getSUB_ROUTE_DAO().insert(subRoute);
//        System.out.println(res);
//    }
}
