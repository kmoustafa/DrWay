/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import servicelayer.server.dal.pojos.DateLookUp;
import servicelayer.server.dal.pojos.History;
import servicelayer.server.dal.pojos.Prediction;
import servicelayer.server.dal.pojos.SubRoad;

/**
 *
 * @author Kareem Moustafa
 */
public class HistoryDAO {

    private final Session session;

    public HistoryDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public double getPredictionThreeYears(SubRoad road, List<DateLookUp> dlu) {

        try {

            String q1 = "from Prediction p where subRoad = ? And (dateLookUp = ? OR dateLookUp = ? OR dateLookUp = ?)";
            Query query2 = session.createQuery(q1).setEntity(0, road).setEntity(1, dlu.get(0)).setEntity(2, dlu.get(1)).setEntity(3, dlu.get(2));
            List<Prediction> predictionsYearsM = (List<Prediction>) query2.list();
            return (predictionsYearsM.get(0).getAvgSpeed14() + predictionsYearsM.get(1).getAvgSpeed14() + predictionsYearsM.get(2).getAvgSpeed14()) / 3;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public double getPredictionWeekDays(SubRoad road, List<DateLookUp> weekDaysDateLookUps) {
        System.out.println("SIZEEEEEEEe " + weekDaysDateLookUps.size());

        try {

            String lWeek = "from Prediction p where subRoad = ? And (dateLookUp = ? OR dateLookUp = ? OR dateLookUp = ? OR dateLookUp = ?"
                    + " OR dateLookUp = ? OR dateLookUp = ? OR dateLookUp = ?)";
            Query query4 = session.createQuery(lWeek).setEntity(0, road).setEntity(1, weekDaysDateLookUps.get(0))
                    .setEntity(2, weekDaysDateLookUps.get(1)).setEntity(3, weekDaysDateLookUps.get(2))
                    .setEntity(4, weekDaysDateLookUps.get(3)).setEntity(5, weekDaysDateLookUps.get(4)).setEntity(6, weekDaysDateLookUps.get(5))
                    .setEntity(7, weekDaysDateLookUps.get(6));
            List<Prediction> lastWeek = (List<Prediction>) query4.list();

            return (lastWeek.get(0).getAvgSpeed14() + lastWeek.get(1).getAvgSpeed14() + lastWeek.get(2).getAvgSpeed14() + lastWeek.get(3).getAvgSpeed14()
                    + lastWeek.get(4).getAvgSpeed14() + lastWeek.get(5).getAvgSpeed14() + lastWeek.get(6).getAvgSpeed14()) / 7;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public double getPredictionLastThreeDays(SubRoad road, List<DateLookUp> monthDaysDateLookUps) {
        System.out.println("SIZEE " + monthDaysDateLookUps.size());

        for (DateLookUp dateLookUp : monthDaysDateLookUps) {
            System.out.println("ID Fate" + dateLookUp.getId());
        }
        try {

            String lWeek = "from Prediction p where subRoad = ? And (dateLookUp = ? OR dateLookUp = ? OR dateLookUp = ? )";
            Query query4 = session.createQuery(lWeek).setEntity(0, road).setEntity(1, monthDaysDateLookUps.get(0))
                    .setEntity(2, monthDaysDateLookUps.get(1)).setEntity(3, monthDaysDateLookUps.get(2));

            List<Prediction> lastWeek = (List<Prediction>) query4.list();

            return (lastWeek.get(0).getAvgSpeed14() + lastWeek.get(1).getAvgSpeed14() + lastWeek.get(2).getAvgSpeed14()) / 3;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public double getCurrentSubRoadsStatus(SubRoad road, DateLookUp dateLookUp, int hourTime) {
        // System.out.println("SIZEE " + monthDaysDateLookUps.size());
//
//        for (DateLookUp dateLookUp : monthDaysDateLookUps) {
//            System.out.println("ID Fate" + dateLookUp.getId());
//        }
        try {

            String lWeek = "from History p where subRoad = ? And dateLookUp = ?";
            Query query4 = session.createQuery(lWeek).setEntity(0, road).setEntity(1, dateLookUp);
            //   .setEntity(2, monthDaysDateLookUps.get(1)).setEntity(3, monthDaysDateLookUps.get(2));

            List<History> lastWeek = (List<History>) query4.list();

            //return lastWeek.get(0).getAvgSpeed14();
            switch (hourTime) {
                case 0:
                    return lastWeek.get(0).getAvgSpeed00();
                case 1:
                    return lastWeek.get(0).getAvgSpeed01();
                case 2:
                    return lastWeek.get(0).getAvgSpeed02();
                case 3:
                    return lastWeek.get(0).getAvgSpeed03();
                case 4:
                    return lastWeek.get(0).getAvgSpeed04();
                case 5:
                    return lastWeek.get(0).getAvgSpeed05();
                case 6:
                    return lastWeek.get(0).getAvgSpeed06();
                case 7:
                    return lastWeek.get(0).getAvgSpeed07();
                case 8:
                    return lastWeek.get(0).getAvgSpeed08();
                case 9:
                    return lastWeek.get(0).getAvgSpeed09();
                case 10:
                    return lastWeek.get(0).getAvgSpeed10();
                case 11:
                    return lastWeek.get(0).getAvgSpeed11();
                case 12:
                    return lastWeek.get(0).getAvgSpeed12();
                case 13:
                    return lastWeek.get(0).getAvgSpeed13();
                case 14:
                    return lastWeek.get(0).getAvgSpeed14();
                case 15:
                    return lastWeek.get(0).getAvgSpeed15();
                case 16:
                    return lastWeek.get(0).getAvgSpeed16();
                case 17:
                    return lastWeek.get(0).getAvgSpeed17();
                case 18:
                    return lastWeek.get(0).getAvgSpeed18();
                case 19:
                    return lastWeek.get(0).getAvgSpeed19();
                case 20:
                    return lastWeek.get(0).getAvgSpeed20();
                case 21:
                    return lastWeek.get(0).getAvgSpeed21();
                case 22:
                    return lastWeek.get(0).getAvgSpeed22();
                case 23:
                    return lastWeek.get(0).getAvgSpeed23();

            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public double getFutureSubRoadsStatus(SubRoad road, DateLookUp dateLookUp) {
        // System.out.println("SIZEE " + monthDaysDateLookUps.size());
//
//        for (DateLookUp dateLookUp : monthDaysDateLookUps) {
//            System.out.println("ID Fate" + dateLookUp.getId());
//        }
        try {

            String lWeek = "from Prediction p where subRoad = ? And dateLookUp = ?";
            Query query4 = session.createQuery(lWeek).setEntity(0, road).setEntity(1, dateLookUp);
            //   .setEntity(2, monthDaysDateLookUps.get(1)).setEntity(3, monthDaysDateLookUps.get(2));

            List<Prediction> lastWeek = (List<Prediction>) query4.list();

            return lastWeek.get(0).getAvgSpeed14();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
