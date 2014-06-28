/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import servicelayer.server.dal.pojos.DailyWinner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Kareem Moustafa
 */
public class WinnerDAO {

    private final Session session;

    public WinnerDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public void insert(DailyWinner w) {
        try {
            session.beginTransaction();
            session.persist(w);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public List<DailyWinner> getDailyWinner() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = f.getCalendar();
        try {

            System.out.println(c.getTime());
            Query q = session.createQuery("FROM DailyWinner Where date = ? "
                    + "order by noOfSnapshots desc ").setDate(0, new java.sql.Date(date.getTime())).setMaxResults(5);
            List<DailyWinner> dailyWinners = q.list();
            if (dailyWinners != null && !dailyWinners.isEmpty()) {
                for (DailyWinner dailyWinner : dailyWinners) {
                    session.refresh(dailyWinner);
                }
            }
            return dailyWinners;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DailyWinner getMonthlyWinner() {

        Calendar c1 = Calendar.getInstance();
        int month = c1.get(Calendar.MONTH) + 1;
        int year = c1.get(Calendar.YEAR);
        try {

            session.getTransaction().begin();
            Query q = session.createQuery("FROM DailyWinner Where Month(date) = ? And  Year(date) = ? "
                    + "order by noOfSnapshots desc ").setInteger(0, month).setInteger(1, year).setMaxResults(5);

            DailyWinner dailyWinner = (DailyWinner) q.list().get(0);
            session.refresh(dailyWinner);
            return dailyWinner;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

}
