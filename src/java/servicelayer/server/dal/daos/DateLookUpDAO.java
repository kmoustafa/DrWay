/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.DateLookUp;

/**
 *
 * @author Kareem Moustafa
 */
public class DateLookUpDAO {

    private final Session session;

    public DateLookUpDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    //Getting the last three years ago for the specific date
    public List getLastThreeYearsDateG(int day, int month, int year) {
        Set<Integer> years = new LinkedHashSet<Integer>();
        years.add(year);
        years.add(year - 1);
        years.add(year - 2);
        try {
            //session.getTransaction().begin();
            Criteria criteria = session.createCriteria(DateLookUp.class)
                    .add(Restrictions.eq("mday", day)).add(Restrictions.eq("mmonth", month)).add(Restrictions.in("myear", years));
            return criteria.list();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
        return null;
    }

    //Getting the last three years ago for the specific date
    public DateLookUp getDateLookUpById(int id) {

        try {
            //session.getTransaction().begin();
            DateLookUp dateLookUp = (DateLookUp) session.get(DateLookUp.class, id);
            if(dateLookUp!=null){
                session.refresh(dateLookUp);
            }
            return dateLookUp;
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
        return null;
    }

    public DateLookUp getDateLookUp(Integer mDay, Integer mMonth, Integer mYear) {
        System.out.println(mDay + " " + mMonth + " " + mYear);
        DateLookUp dateLookUp = (DateLookUp) session.createCriteria(DateLookUp.class)
                .add(Restrictions.eq("mday", mDay))
                .add(Restrictions.eq("mmonth", mMonth))
                .add(Restrictions.eq("myear", mYear)).uniqueResult();
        if(dateLookUp!=null){
            session.refresh(dateLookUp);
        }
        return dateLookUp;
    }

    //Getting the last three years ago for the specific date
    public List getLastThreeYearsDateHijri(int day, int month, int year) {
        Set<Integer> years = new LinkedHashSet<Integer>();
        years.add(year);
        years.add(year - 1);
        years.add(year - 2);
        System.out.println("DAO :: " + day + month + year);
        try {
            //session.getTransaction().begin();
            Criteria criteria = session.createCriteria(DateLookUp.class)
                    .add(Restrictions.eq("hday", day)).add(Restrictions.eq("hmonth", month)).add(Restrictions.in("hyear", years));
            return criteria.list();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
        return null;
    }

    //Getting all days of lastWeek
    public List getLastWeekDates(int day, int month, int year, int lastday, int oldMonth, int oldYear, int lastDayInMonth) {
        boolean flag = false;

        try {
            //session.getTransaction().begin();
            Criteria criteria2 = session.createCriteria(DateLookUp.class);
            Criteria criteria22 = session.createCriteria(DateLookUp.class);

            if (oldYear == year && oldMonth == month) {
                criteria2.add(Restrictions.between("mday", lastday + 1, day)).add(Restrictions.eq("mmonth", oldMonth)).add(Restrictions.eq("myear", oldYear));
                System.out.println("List Size :: " + criteria2.list().size());
            } else if (oldYear != year) {
                System.out.println("First IF");
                flag = true;
                criteria2.add(Restrictions.between("mday", lastday + 1, lastDayInMonth)).add(Restrictions.eq("mmonth", month)).add(Restrictions.eq("myear", year));
                criteria22.add(Restrictions.between("mday", 1, day)).add(Restrictions.eq("mmonth", oldMonth)).add(Restrictions.eq("myear", oldYear));

            } else if (month != oldMonth) {
                flag = true;
                criteria2.add(Restrictions.between("mday", lastday + 1, lastDayInMonth)).add(Restrictions.eq("mmonth", month)).add(Restrictions.eq("myear", year));
                criteria22.add(Restrictions.between("mday", 1, day)).add(Restrictions.eq("mmonth", oldMonth)).add(Restrictions.eq("myear", year));
            }
            List<DateLookUp> weekDaysDateLookUps = new LinkedList<DateLookUp>();
            if (!flag) {
                weekDaysDateLookUps = (List<DateLookUp>) criteria2.list();
                return weekDaysDateLookUps;
            } else if (flag) {
                List<DateLookUp> weekDaysDateLookUps1 = (List<DateLookUp>) criteria2.list();
                List<DateLookUp> weekDaysDateLookUps2 = (List<DateLookUp>) criteria22.list();
                weekDaysDateLookUps.addAll(weekDaysDateLookUps1);
                weekDaysDateLookUps.addAll(weekDaysDateLookUps2);
                return weekDaysDateLookUps;
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
        return null;
    }

    //Getting the last three same day ago for the specific date
    public List getLastThreeSameDay(int fwDay, int fwMonth, int fwYear, int swDay, int swMonth, int swYear, int twDay, int twMonth, int twYear) {

        try {
            //session.getTransaction().begin();
            String q = "from DateLookUp d where d.mday = ? and d.mmonth = ? and d.myear = ?"
                    + "OR (d.mday = ? and d.mmonth = ? and d.myear = ?)" + "OR (d.mday = ? and d.mmonth = ? and d.myear = ?)";
            Query query = session.createQuery(q).setInteger(0, fwDay).setInteger(1, fwMonth + 1).setInteger(2, fwYear)
                    .setInteger(3, swDay).setInteger(4, swMonth + 1).setInteger(5, swYear)
                    .setInteger(6, twDay).setInteger(7, twMonth + 1).setInteger(8, twYear);
            return query.list();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
        return null;
    }

    public List<DateLookUp> getAllDates() {
        try{
            List<DateLookUp> dateLookUps = session.createCriteria(DateLookUp.class).list();
            if(dateLookUps!=null && !dateLookUps.isEmpty()){
                for(DateLookUp dateLookUp : dateLookUps){
                    session.refresh(dateLookUp);
                }
            }
            return dateLookUps;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
    }

    public DateLookUp getDateById(int id) {
        try{
        DateLookUp dateLookUp = (DateLookUp) session.get(DateLookUp.class, id);
        if(dateLookUp!=null)
            session.refresh(dateLookUp);
        return dateLookUp;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(DateLookUp dateLookUp) {
        if (dateLookUp == null) {
            System.err.println("dateLookUp is null !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(dateLookUp);
                session.getTransaction().commit();
                session.evict(dateLookUp);
                System.out.println("dateLookUp is updated");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("DataBase Exception");
                return false;
            }
        }
    }

    public boolean delete(DateLookUp dateLookUp) {
        if (dateLookUp == null) {
            System.err.println("dateLookUp is null !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.delete(dateLookUp);
                session.getTransaction().commit();
                System.out.println("dateLookUp is deleted");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("DataBase Exception");
                return false;
            }
        }
    }

    public boolean insert(DateLookUp dateLookUp) {
        if (dateLookUp == null) {
            System.err.println("dateLookUp is null !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.persist(dateLookUp);
                session.getTransaction().commit();
                session.evict(dateLookUp);
                System.out.println("dateLookUp is deleted");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("DataBase Exception");
                return false;
            }
        }
    }
}
