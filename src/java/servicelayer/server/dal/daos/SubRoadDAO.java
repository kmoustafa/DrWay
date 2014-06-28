/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import java.util.List;
import servicelayer.server.dal.pojos.SubRoad;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.Road;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author Kareem Moustafa
 */
public class SubRoadDAO {

    private final Session session;

    public SubRoadDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public SubRoad getSubRoadById(int id) {

        SubRoad subRoad = new SubRoad();
        try {
            subRoad = (SubRoad) this.session.get(SubRoad.class, id);
            if(subRoad!=null){
                session.refresh(subRoad);
            }
        } catch (Exception e) {
            System.err.print("Error in Updating PendingUser!!1");
            this.session.getTransaction().rollback();
            e.printStackTrace();
            SubRoad road1 = new SubRoad();
            subRoad.setId(-1);
            return road1;
        }

        return subRoad;
    }

    public SubRoad getSubRoadByPosition(double x, double y) {

        SubRoad road = new SubRoad();
        try {
            List list = session.createQuery("FROM SubRoad where "
                    + "((x1 < :X and :X < x2) or (x1 > :X and :X > x2))"
                    + "and ((y1 <= :Y and :Y <= y2) or (y1 >= :Y and :Y >= y2))")
                    .setDouble("X", x)
                    .setDouble("Y", y)
                    .list();

            if (list.isEmpty()) {
                road = null;
            } else if (list.size() > 0) {
                System.out.println(list.size());
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(((SubRoad) list.get(i)).getId());
                }
                System.out.println("");
                road = (SubRoad) list.get(0);
                if(road!=null){
                    session.refresh(road);
                }
            }
        } catch (Exception e) {
            System.err.print("Error in Updating PendingUser!!1");
            this.session.getTransaction().rollback();
            e.printStackTrace();
            SubRoad road1 = new SubRoad();
            road1.setId(-1);
            return road1;
        }

        return road;
    }

    public List<SubRoad> getSubRoadsOfRoad(Road road) {
        try{
            Criteria criteria = this.session.createCriteria(SubRoad.class);
            criteria.add(Restrictions.eq("road", road));
            List<SubRoad> subRoads = criteria.list();
            if(subRoads!=null && !subRoads.isEmpty()){
                for(SubRoad subRoad : subRoads){
                    session.refresh(subRoad);
                }
            }
            return subRoads;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<SubRoad> getSubRoadsOfRoad(int roadId) {

        try{
            Road road = HibernateUtil.getROAD_DAO().getRoadById(roadId);
            Criteria criteria = this.session.createCriteria(SubRoad.class);
            criteria.add(Restrictions.eq("road", road));
            List<SubRoad> subRoads = criteria.list();
            if(subRoads!=null && !subRoads.isEmpty()){
                for(SubRoad subRoad : subRoads){
                    session.refresh(subRoad);
                }
            }
            return subRoads;
        }catch(Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    public boolean update(SubRoad subRoad) {
        if (subRoad == null) {
            System.err.println("subRoad is null !");
            return false;
        } else if (subRoad.getRoad() == null) {
            System.err.println("subRoad.road is null !");
            return false;
        } else if (subRoad.getX1() == 0) {
            System.err.println("subRoad.X1 is zero !");
            return false;
        } else if (subRoad.getX2() == 0) {
            System.err.println("subRoad.X2 is zero !");
            return false;
        } else if (subRoad.getY1() == 0) {
            System.err.println("subRoad.Y1 is zero !");
            return false;
        } else if (subRoad.getY2() == 0) {
            System.err.println("subRoad.Y2 is zero !");
            return false;
        } else if (subRoad.getDistance() == 0) {
            System.err.println("subRoad.Y2 is zero !");
            return false;
        } else if (subRoad.getCurrentSpeed() == 0) {
            System.err.println("subRoad.speed is zero !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(subRoad);
                session.getTransaction().commit();
                session.evict(subRoad);
                System.out.println("subRoad is updated");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean delete(SubRoad subRoad) {
        if (subRoad == null) {
            System.err.println("subRoad is null !");
            return false;
        } else if (subRoad.getRoad() == null) {
            System.err.println("subRoad.road is null !");
            return false;
        } else if (subRoad.getX1() == 0) {
            System.err.println("subRoad.X1 is zero !");
            return false;
        } else if (subRoad.getX2() == 0) {
            System.err.println("subRoad.X2 is zero !");
            return false;
        } else if (subRoad.getY1() == 0) {
            System.err.println("subRoad.Y1 is zero !");
            return false;
        } else if (subRoad.getY2() == 0) {
            System.err.println("subRoad.Y2 is zero !");
            return false;
        } else if (subRoad.getDistance() == 0) {
            System.err.println("subRoad.Y2 is zero !");
            return false;
        } else if (subRoad.getCurrentSpeed() == 0) {
            System.err.println("subRoad.speed is zero !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.delete(subRoad);
                session.getTransaction().commit();
                System.out.println("subRoad is updated");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean insert(SubRoad subRoad) {
        if (subRoad == null) {
            System.err.println("subRoad is null !");
            return false;
        } else if (subRoad.getRoad() == null) {
            System.err.println("subRoad.road is null !");
            return false;
        } else if (subRoad.getX1() == 0) {
            System.err.println("subRoad.X1 is zero !");
            return false;
        } else if (subRoad.getX2() == 0) {
            System.err.println("subRoad.X2 is zero !");
            return false;
        } else if (subRoad.getY1() == 0) {
            System.err.println("subRoad.Y1 is zero !");
            return false;
        } else if (subRoad.getY2() == 0) {
            System.err.println("subRoad.Y2 is zero !");
            return false;
        } else if (subRoad.getDistance() == 0) {
            System.err.println("subRoad.Y2 is zero !");
            return false;
        } else if (subRoad.getCurrentSpeed() == 0) {
            System.err.println("subRoad.speed is zero !");
            return false;
        } else {
            try {
                session.getTransaction().begin();
                session.persist(subRoad);
                session.getTransaction().commit();
                session.evict(subRoad);
                System.out.println("subRoad is updated");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

//    public static void main(String[] args) {
//        SubRoadDAO subRoadDAO = HibernateUtil.getSUB_ROAD_DAO();
//        SubRoad subRoad = subRoadDAO.getSubRoadByPosition(30.06248730878783, 31.249405281548364);
//        System.out.println(subRoad.getId());
//    }
}
