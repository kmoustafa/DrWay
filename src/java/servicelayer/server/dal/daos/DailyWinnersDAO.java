/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import servicelayer.server.dal.pojos.DailyWinner;

/**
 *
 * @author eng.banoota
 */
public class DailyWinnersDAO {
    
     private final Session session;

    public DailyWinnersDAO (SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }
    
    public List<DailyWinner> getAllWinners (){
        try{
            List<DailyWinner> dailyWinners = session.createCriteria(DailyWinner.class).list();
            if(dailyWinners!=null && !dailyWinners.isEmpty()){
                for(DailyWinner dailyWinner : dailyWinners){
                    session.refresh(dailyWinner);
                }
            }
            return dailyWinners;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean insert(DailyWinner winner) {
        if (winner == null) {
            System.err.println("winner is null !");
            return false;
        } else {
            try{
            session.getTransaction().begin();
            session.persist(winner);
            session.getTransaction().commit();
            session.evict(winner);
            System.out.println("winner is inserted");
            return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    
//    public static void main (String[] agrs){
//        User win1 = HibernateUtil.getUSER_DAO().getUserById(5);
//        User win2 = HibernateUtil.getUSER_DAO().getUserById(6);
//        User win3 = HibernateUtil.getUSER_DAO().getUserById(8);
//        
//        Calendar c = Calendar.getInstance();
//        
//        DailyWinner d1 = new DailyWinner(win1, 485, new Date());
//        System.out.println("Saving winner 1 : "+ HibernateUtil.getDAILY_WINNERS_DAO().insert(d1));
//        
//       
//        DailyWinner d2 = new DailyWinner(win2, 265, new Date());
//        System.out.println("Saving winner 2 :" +HibernateUtil.getDAILY_WINNERS_DAO().insert(d2));
//        
//        DailyWinner d3 = new DailyWinner(win3, 874, new Date());
//        System.out.println("Saving winner 3 :" +HibernateUtil.getDAILY_WINNERS_DAO().insert(d3));
//    }
    
}
