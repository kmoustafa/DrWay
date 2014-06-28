/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import servicelayer.server.dal.pojos.CurrentVersion;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author Kareem Moustafa
 */
public class CurrentVersionDAO {

    private final Session session;

    public CurrentVersionDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    // return 0 if new Veriosn found
    // return -1 if no new Veriosns found
    // return 1 if Database error
    public int getMainPointsVersion() {
        try {
            Criteria cr = this.session.createCriteria(CurrentVersion.class).setProjection(Projections.property("mainPointListVersion"));
            return (Integer) cr.uniqueResult();
        } catch (Exception e) {
            System.err.print("Error in Updating User!!");
            e.printStackTrace();
            return 1;
        }
        //return -1;
    }

    public CurrentVersion getCurrentVersion(int id) {
        try {
            return (CurrentVersion) session.get(CurrentVersion.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // return 1 if Database error
    public int getCarListVersion() {
        try {
            int version = (Integer) session.createCriteria(CurrentVersion.class).setProjection(Projections.property("carListVersion")).list().get(0);
            return version;

        } catch (HibernateException e) {
            System.err.print("Error in Database!!");
            e.printStackTrace();
            return 1;
        }
        //return -1;
    }

    // return 1 if Database error
    public int getFuelCostListVersion() {
        try {
            int version = (Integer) session.createCriteria(CurrentVersion.class).setProjection(Projections.property("fuelTypeListVersion")).list().get(0);
            return version;

        } catch (HibernateException e) {
            System.err.print("Error in Database!!");
            e.printStackTrace();
            return 1;
        }
        //return -1;
    }

    // return 1 if Database error
    public int getReportTypeVersion() {
        try {
            int version = (Integer) session.createCriteria(CurrentVersion.class).setProjection(Projections.property("reportTypeVersion")).list().get(0);
            return version;

        } catch (HibernateException e) {
            System.err.print("Error in Database!!");
            e.printStackTrace();
            return 1;
        }
        //return -1;
    }

    public Boolean updateCarListVersion() {
        CurrentVersion currentVersion = new CurrentVersion();
        try {
            int currentCarListVersion = HibernateUtil.getCURRENT_VERSION_DAO().getCarListVersion();
            if (currentCarListVersion != 0) {
                currentCarListVersion++;
                currentVersion.setCarListVersion(currentCarListVersion);

                session.getTransaction().begin();
                session.saveOrUpdate(currentVersion);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Boolean updateFuelTypeListVersion() {
        CurrentVersion currentVersion = new CurrentVersion();
        try {
            int currentFuelTypeVersion = HibernateUtil.getCURRENT_VERSION_DAO().getFuelCostListVersion();
            if (currentFuelTypeVersion != 0) {
                currentFuelTypeVersion++;
                currentVersion.setFuelTypeListVersion(currentFuelTypeVersion);

                session.getTransaction().begin();
                session.saveOrUpdate(currentVersion);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Boolean updateMainPointListVersion() {
        try {
            CurrentVersion currentVersion = HibernateUtil.getCURRENT_VERSION_DAO().getCurrentVersion(1);
            currentVersion.setMainPointListVersion(currentVersion.getMainPointListVersion() + 1);
            session.getTransaction().begin();
            session.saveOrUpdate(currentVersion);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean updateReportTypeVersion() {
        CurrentVersion currentVersion = new CurrentVersion();
        try {
            int currentReportTypeVersion = HibernateUtil.getCURRENT_VERSION_DAO().getReportTypeVersion();
            if (currentReportTypeVersion != 0) {
                currentReportTypeVersion++;
                currentVersion.setReportTypeVersion(currentReportTypeVersion);
                session.getTransaction().begin();
                session.saveOrUpdate(currentVersion);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public PrivateKey getRSAPrivateKey() {
        PrivateKey privateKey = null;
        try {
            byte[] encodedKey = {48, -126, 2, 119, 2, 1, 0, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 4, -126, 2, 97, 48, -126, 2, 93, 2, 1, 0, 2, -127, -127, 0, -114, -64, -57, -84, -117, 47, -42, -49, 56, 34, 127, 72, 8, 1, -118, -9, 94, 26, -80, 103, 7, 95, 16, 10, 119, -21, -20, 79, -98, -99, 30, 121, 92, 50, -30, -8, 91, 35, -65, 28, -88, 12, 70, -11, -101, 105, -120, -128, 111, -11, -2, -119, 89, -62, 38, -107, 101, -30, 59, -67, 37, 8, 107, -101, 32, -123, -93, -84, -106, 56, -24, -69, -36, -101, 46, -38, -1, 9, 102, -20, -128, 59, -54, -104, -72, 12, 42, 10, -81, -96, -35, 61, 106, 90, 111, 124, -80, -4, -73, -10, -68, 20, 12, 74, -79, 92, 52, 67, -26, 43, -82, 11, 112, 27, 93, -65, -101, 36, -5, 5, -56, 98, 118, -56, -87, 66, 72, -113, 2, 3, 1, 0, 1, 2, -127, -128, 102, -93, -93, -83, -94, -47, -3, -64, 54, 91, 79, 106, -22, 30, -71, -35, 76, 51, 34, 92, -95, -57, 47, 16, 103, -45, 32, -80, -54, -103, -85, -68, -108, -105, 29, -20, 118, 19, 72, -87, 1, 6, -6, -54, 67, 56, -24, -27, 120, 26, -58, 45, -39, 116, -88, 43, 72, -5, 21, -6, -44, 50, -82, -78, -31, -75, -76, -89, 30, -113, -34, 33, -21, -42, -73, -93, 36, 93, -73, 94, -52, 82, 116, -23, -38, -118, 56, 105, 69, -58, 92, -71, 81, -17, -19, 48, 14, -85, 33, 65, -8, -44, -109, -66, 24, -44, 4, 71, 125, -83, -122, -55, 120, 82, -115, 6, -2, -27, -21, -20, -25, -122, -44, -120, 59, -92, 81, 1, 2, 65, 0, -45, 51, -97, 29, -8, -44, 17, 25, -114, 21, 66, -71, -50, 89, 11, 121, -24, 46, -101, 13, 24, -125, -115, 114, 52, 85, -78, 94, 64, -122, -82, -112, -51, 35, 106, -115, -67, 68, -60, 49, -11, -55, -69, 88, 122, -38, -117, 34, 60, -6, 101, 98, 81, -33, -62, -113, -87, -85, 74, -20, -114, 14, -52, 15, 2, 65, 0, -83, 8, 91, -115, -58, -23, 95, 74, -17, -83, -53, -11, 13, -50, 54, 70, 2, 41, -5, -36, 124, -1, 45, -119, 3, 107, -98, -44, -28, 74, -121, -36, 80, -98, 76, 2, 6, -49, 43, 123, -44, 103, -84, 7, 97, -42, 41, 115, -69, 12, 9, 29, -62, -72, -71, 42, 120, -115, -39, 85, -70, -38, 59, -127, 2, 64, 106, -116, -59, 126, -110, 117, 50, 119, 73, 6, 18, -42, -72, 114, -107, -26, -105, -85, 67, -113, -12, -55, 17, -120, 7, 61, 32, -78, -118, 45, 40, 78, 103, 31, -79, -10, -10, 0, -122, 86, -118, 32, -113, 2, -111, 96, 70, 7, 44, -22, 74, 81, -78, -22, -121, 54, -127, 43, -88, -83, -12, -15, 36, 35, 2, 65, 0, -108, -123, 80, -8, -113, -36, -18, -13, -122, 105, 26, 97, 28, 89, 125, 82, 120, 55, 11, 61, -123, 121, 14, -33, -59, 52, -46, 31, -112, 88, -68, -25, 9, -73, -26, 43, -13, 38, 77, -1, -69, 32, -59, -109, -116, -111, -28, 0, -73, 115, -84, 59, -124, 81, 68, 118, -59, 5, -108, -96, 54, -125, 109, -127, 2, 65, 0, -68, -33, 105, -8, 55, 66, 86, -27, -102, 37, 109, 62, 82, -36, -57, -105, -104, 103, 121, 54, -71, 71, 113, -63, 72, -75, -21, -63, 122, 74, 112, -76, 120, 92, 91, 2, 112, -83, -44, -5, -78, -37, 117, 111, -76, -59, -84, 79, -113, 58, 37, 78, 17, 11, -84, -119, -86, 103, -126, -58, -57, -126, -15, 78};
            KeyFactory rsaKeyFac = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(encodedKey);
            privateKey = (PrivateKey) rsaKeyFac.generatePrivate(encodedKeySpec);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CurrentVersionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(CurrentVersionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return privateKey;
    }
}
