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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.CarListLookUp;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author MIRO
 */
public class CarListLookUpDAO {

    private final Session session;

    public CarListLookUpDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }
    
    

    public List<CarListLookUp> getList() {
        try{
        List<CarListLookUp>  carListLookUps = session.createCriteria(CarListLookUp.class).list();
        if(carListLookUps!=null && !carListLookUps.isEmpty()){
            for(CarListLookUp carListLookUp : carListLookUps){
                session.refresh(carListLookUp);
            }
        }
        return carListLookUps;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    

    
    public List<CarListLookUp> getAllCarLists(){
        try{
            List<CarListLookUp> carListLookUps = session.createCriteria(CarListLookUp.class).list();
            if(carListLookUps!=null && !carListLookUps.isEmpty()){
                for(CarListLookUp carListLookUp : carListLookUps){
                    session.refresh(carListLookUp);
                }
            }
            return carListLookUps;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getAllCarListsValues(){
        return session.createCriteria(CarListLookUp.class).setProjection(Projections.property("carValue")).list();
    }
    public CarListLookUp getCarListById(int carId){
        try{
            CarListLookUp carListLookUp = (CarListLookUp)session.get(CarListLookUp.class, carId);
            if(carListLookUp!=null)
                session.refresh(carListLookUp);
            return carListLookUp;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
     public CarListLookUp getCarListByValue(String carValue) {

         try{
            Criteria criteria = this.session.createCriteria(CarListLookUp.class);
            criteria.add(Restrictions.eq("CarList_Value", carValue));
            CarListLookUp carListLookUp =  (CarListLookUp) criteria.uniqueResult();
            if(carListLookUp!=null){
                session.refresh(carListLookUp);
            }
            return carListLookUp;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
     
      public boolean update(CarListLookUp carLookUp) {
        if(carLookUp==null ){
            System.err.println("carLookUp is null !");
            return false;
        }
        else if(carLookUp.getCarValue()==null ){
            System.err.println("carLookUp.value is null !");
            return false;
        }
         
         else {
           try{
                session.getTransaction().begin();
                session.saveOrUpdate(carLookUp);  
                session.getTransaction().commit();
                session.evict(carLookUp);
                System.out.println("carLookUp is updated");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateCarListVersion())
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

    public boolean delete(CarListLookUp carLookUp) {
        if(carLookUp==null ){
            System.err.println("carLookUp is null !");
            return false;
        }
        else if(carLookUp.getCarValue()==null ){
            System.err.println("carLookUp.value is null !");
            return false;
        }
         else {
             try{
                session.getTransaction().begin();
                session.delete(carLookUp);  
                session.getTransaction().commit();;
                System.out.println("carLookUp is deleted");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateCarListVersion())
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

    public boolean insert(CarListLookUp carLookUp) {
        if(carLookUp==null ){
            System.err.println("carLookUp is null !");
            return false;
        }
        else if(carLookUp.getCarValue()==null ){
            System.err.println("carLookUp.value is null !");
            return false;
        }
         else {
            try{
                session.getTransaction().begin();
                session.persist(carLookUp);  
                session.getTransaction().commit();
                session.evict(carLookUp);
                System.out.println("carLookUp is deleted");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateCarListVersion())
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
