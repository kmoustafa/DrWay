/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import servicelayer.server.dal.pojos.DateLookUp;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.Road;
import servicelayer.server.dal.pojos.SubRoad;

/**
 *
 * @author eng.banoota
 */
public class RoadDAO {

    private final Session session;

    public RoadDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public List<Road> getAllRoades() {
        try{
        List<Road> roads =   session.createCriteria(Road.class).list();
        if(roads!=null && !roads.isEmpty()){
            for(Road road : roads){
                session.refresh(road);
            }
        }
        return roads;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllRoadesNames() {
        return session.createCriteria(Road.class).setProjection(Projections.property("name")).list();
    }

    public Road getRoadById(int roadId) {

        try{
        Road road = (Road) session.get(Road.class, roadId);
        if(road!=null){
            //session.evict(road);
            session.refresh(road);
        }
        
        return road;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Road getRoadByName(String roadName) {

        try{
        Criteria criteria = this.session.createCriteria(Road.class);
        criteria.add(Restrictions.eq("name", roadName));
        Road road = (Road) criteria.uniqueResult();
        if(road!=null)
            session.refresh(road);
        return road;
        }catch(Exception e){
            e.printStackTrace(); 
            return null;
        }
    }

    public boolean update(Road road) {
        if (road == null) {
            System.err.println("road is null !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(road);
                session.getTransaction().commit();
                session.evict(road);
                System.out.println("road is updated");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean delete(Road road) {
        if (road == null) {
            System.err.println("road is null !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.delete(road);
                session.getTransaction().commit();
                System.out.println("route is deleted");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean insert(Road road) {
        if (road == null) {
            System.err.println("road is null !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.persist(road);
                session.getTransaction().commit();
                session.evict(road);
                System.out.println("road is inserted");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public Object getSubRoadsDistSpeed(Road road) {
        return session.createCriteria(SubRoad.class).setProjection(Projections.projectionList()
                .add(Projections.sum("distance")).add(Projections.avg("currentSpeed")))
                .add(Restrictions.eq("road", road)).uniqueResult();
    }
    
    public double getSubRoadsSpead(Road road) {
        return (Double) session.createCriteria(SubRoad.class).setProjection(Projections.avg("currentSpeed"))
                .add(Restrictions.eq("road", road)).uniqueResult();
    }

    public Object getSubRoadsDist(Road road) {
        return session.createCriteria(SubRoad.class).setProjection(Projections.sum("distance"))
                .add(Restrictions.eq("road", road)).uniqueResult();
    }

    public Double getSubRoadsPredictedSpeed(Road road, DateLookUp dateLookUp, int hour) {
        String avgSpeed;
        if (hour < 10) {
            avgSpeed = "avgSpeed0" + hour;
        } else {
            avgSpeed = "avgSpeed" + hour;
        }

        List subroads = session.createCriteria(SubRoad.class).add(Restrictions.eq("road", road)).list();
        String queryString = "select avg(p." + avgSpeed + ") from Prediction p where dateLookUp = ? and subRoad in (:subroad)";
        if (dateLookUp == null) {
            System.out.println("dateLookup is null");
        }
        if (subroads == null) {
            System.out.println("null");
        }
        try {
            Query query = session.createQuery(queryString).setEntity(0, dateLookUp).setParameterList("subroad", subroads);
            return (Double) query.uniqueResult();
        } catch (Exception ex) {
            System.out.println("");
            System.out.println("exceptionnnnnnnn");
            System.out.println("");
            ex.printStackTrace();
            return 0.0;
        }
    }

}
