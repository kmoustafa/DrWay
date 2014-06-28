/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import java.io.Serializable;
import servicelayer.server.dal.pojos.MainPoint;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author Ghazala
 */
public class MainPointDAO implements Serializable{

    private final Session session;

    public MainPointDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public MainPoint getMainPointByID(int id) {
        try{
        MainPoint mainPoint = (MainPoint) session.get(MainPoint.class, id);
        if(mainPoint!=null)
            session.refresh(mainPoint);
        return mainPoint;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public MainPoint getMainPointByName(String name) {
        try{
        Criteria cr = session.createCriteria(MainPoint.class).add(Restrictions.eq("name",name));
        MainPoint mainPoint = (MainPoint)cr.uniqueResult();
        if(mainPoint!= null)
            session.refresh(mainPoint);
        return mainPoint;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<MainPoint> getAllMainPoints() {
        try{
            List<MainPoint> mainPoints = session.createCriteria(MainPoint.class).list();
            if(mainPoints!=null && !mainPoints.isEmpty()){
                for(MainPoint mainPoint : mainPoints){
                    session.refresh(mainPoint);
                }
            }
            return mainPoints;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllMainPointsNames() {
        return session.createCriteria(MainPoint.class).setProjection(Projections.property("name")).list();
    }

    public boolean update(MainPoint mainPoint) {
        if(mainPoint==null ){
            System.err.println("mainPoint is null !");
            return false;
        }
        else if(mainPoint.getX()==0 ){
            System.err.println("mainPoint.X is null !");
            return false;
        }
         else if(mainPoint.getX()==0 ){
            System.err.println("mainPoint.Y is null !");
            return false;
        }
         else {
           try{
                session.getTransaction().begin();
                session.saveOrUpdate(mainPoint);  
                session.getTransaction().commit();
                session.evict(mainPoint);
                System.out.println("route is deleted");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateMainPointListVersion())
                    return true;
                else
                    return false;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }

    public boolean delete(MainPoint mainPoint) {
        if(mainPoint==null ){
            System.err.println("mainPoint is null !");
            return false;
        }
        else if(mainPoint.getX()==0 ){
            System.err.println("mainPoint.X is null !");
            return false;
        }
         else if(mainPoint.getX()==0 ){
            System.err.println("mainPoint.Y is null !");
            return false;
        }
         else {
             try{
                session.getTransaction().begin();
                session.delete(mainPoint);  
                session.getTransaction().commit();
                System.out.println("route is deleted");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateMainPointListVersion())
                    return true;
                else
                    return false;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }

    public boolean insert(MainPoint mainPoint) {
        if(mainPoint==null ){
            System.err.println("mainPoint is null !");
            return false;
        }
        else if(mainPoint.getX()==0 ){
            System.err.println("mainPoint.X is null !");
            return false;
        }
         else if(mainPoint.getX()==0 ){
            System.err.println("mainPoint.Y is null !");
            return false;
        }
         else {
            try{
                session.getTransaction().begin();
                session.persist(mainPoint);  
                session.getTransaction().commit();
                session.evict(mainPoint);
                System.out.println("route is deleted");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateMainPointListVersion())
                    return true;
                else
                    return false;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }

}
