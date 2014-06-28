/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import servicelayer.server.dal.pojos.PendingUser;
import servicelayer.server.dal.pojos.Road;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Kareem Moustafa
 */
public class FavouriteRoadDAO {

    private final Session session;

    public FavouriteRoadDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public Road getRoad(int id) {
        Road road;
        try {
            road = (Road) this.session.get(Road.class, id);
            if(road!=null){
                session.refresh(road);
            }
            
        } catch (Exception e) {
            road = new Road();
            road.setId(-202);
            return road;
        }
        return road;
    }

    public int updatePendingUser(PendingUser pendingUser) {

        if (pendingUser != null) {
            try {
                this.session.getTransaction().begin();
                //this.session.beginTransaction();
                this.session.saveOrUpdate(pendingUser);
                this.session.getTransaction().commit();
            } catch (Exception e) {
                System.err.print("Error in Updating PendingUser!!1");
                this.session.getTransaction().rollback();
                e.printStackTrace();
                return -1;
            }
        } else {
            return 0;
        }
        return 1;
    }

    //Retrun true if The code Exist 
    public boolean isCodeExist(String verificationCode) {
        if (verificationCode != null) {
            try {
                this.session.beginTransaction();
                Criteria cr = session.createCriteria(PendingUser.class);
                cr.add(Restrictions.eq("verificationCode", verificationCode));
                List pendUser = cr.list();
                if (pendUser != null) {
                    return true;
                }
            } catch (Exception e) {
                System.err.print("Error in Getting PendingUser verification codes!!");
                e.printStackTrace();
            }
        } else {
            System.err.print("verificationCode is null!!");
            return false;
        }
        return false;
    }

    // return 0 if Pending user found
    // return -1 if pending user not found
    // return 1 if Database error
    public int isPendingUserExist(int id) {
        try {
            this.session.getTransaction().begin();
            Object obj = this.session.get(PendingUser.class, id);
            if (obj != null) {
                return 0;
            }
        } catch (Exception e) {
            System.err.print("Error in Updating User!!");
            this.session.getTransaction().rollback();
            e.printStackTrace();
            return 1;
        }
        return -1;
    }
    
     public List<Integer> getALl() {
        try {
            return session.createCriteria(Road.class).setProjection(Projections.id()).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
