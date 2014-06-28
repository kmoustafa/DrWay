/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.handlers;

import servicelayer.server.dal.daos.RoadDAO;
import servicelayer.server.dal.daos.SubRoadDAO;
import servicelayer.server.dal.pojos.Road;
import servicelayer.server.dal.pojos.SubRoad;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
public class PossitionHandler {

    SubRoad subRoad = new SubRoad();
    Road road = new Road();
    SubRoadDAO subRoadDAO = HibernateUtil.getSUB_ROAD_DAO();
    RoadDAO roadDAO = HibernateUtil.getROAD_DAO();

    public PossitionHandler() {

    }

    public SubRoad getSubRoadOfCertainPosissition(double x, double y) {
        if (x != 0 && y != 0) {
            subRoad = subRoadDAO.getSubRoadByPosition(x, y);
            return subRoad;
        } else {
            return null;
        }
    }

//    public Road getRoadofCertainSubRoad (SubRoad subRoad){
//        if(subRoad != null){
//            roadDAO.getRoadBySubRoad(subRoad);
//            return road;
//        }
//        else
//            return null;
//    }
}
