package servicelayer.server.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import servicelayer.server.dal.daos.AdminDAO;
import servicelayer.server.dal.daos.AdminRoadReportDAO;
import servicelayer.server.dal.daos.BlockedUserDAO;
import servicelayer.server.dal.daos.CarListLookUpDAO;
import servicelayer.server.dal.daos.CurrentVersionDAO;
import servicelayer.server.dal.daos.DailyWinnersDAO;
import servicelayer.server.dal.daos.DateLookUpDAO;
import servicelayer.server.dal.daos.FavouriteRoadDAO;
import servicelayer.server.dal.daos.FuelLookUpDAO;
import servicelayer.server.dal.daos.HistoryDAO;
import servicelayer.server.dal.daos.MainPointDAO;
import servicelayer.server.dal.daos.ModeLookUpDAO;
import servicelayer.server.dal.daos.PendingUserDAO;
import servicelayer.server.dal.daos.ReportTypeLookupDAO;
import servicelayer.server.dal.daos.RoadDAO;
import servicelayer.server.dal.daos.RouteDAO;
import servicelayer.server.dal.daos.SnapShotsDAO;
import servicelayer.server.dal.daos.StatusLookUpDAO;
import servicelayer.server.dal.daos.SubRoadDAO;
import servicelayer.server.dal.daos.SubRouteDAO;
import servicelayer.server.dal.daos.UserDAO;
import servicelayer.server.dal.daos.UserRoadReportDAO;
import servicelayer.server.dal.daos.BlockedUserDAO;
import servicelayer.server.dal.daos.WinnerDAO;


/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Genuine
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final AdminDAO ADMIN_DAO;
    private static final AdminRoadReportDAO ADMIN_ROAD_REPORT_DAO;
    private static final CarListLookUpDAO CAR_LIST_LOOK_UP_DAO;
    private static final CurrentVersionDAO CURRENT_VERSION_DAO;
    private static final DateLookUpDAO DATE_LOOK_UP_DAO;
    private static final FavouriteRoadDAO FAVOURITE_ROAD_DAO;
    private static final FuelLookUpDAO FUEL_COST_LOOK_UP_DAO;
    private static final HistoryDAO HISTORY_DAO;
    private static final MainPointDAO MAIN_POINT_DAO;
    private static final RoadDAO ROAD_DAO;
    private static final RouteDAO ROUTE_DAO;
    private static final SnapShotsDAO SNAP_SHOTS_DAO;
    private static final SubRoadDAO SUB_ROAD_DAO;
    private static final SubRouteDAO SUB_ROUTE_DAO;
    private static final UserDAO USER_DAO;
    private static final UserRoadReportDAO USER_ROAD_REPORT_DAO;
    private static final ReportTypeLookupDAO REPORT_TYPE_LOOK_UP_DAO;
    private static final ModeLookUpDAO MODE_LOOK_UP_DAO;
    private static final StatusLookUpDAO STATUS_LOOK_UP_DAO;
    private static final PendingUserDAO PENDING_USER_DAO;
    private static final BlockedUserDAO BLOCKED_USER_DAO;
    private static final DailyWinnersDAO DAILY_WINNER_DAO;
    private static final WinnerDAO WINNER_DAO;
    
    

    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        ADMIN_DAO = new AdminDAO(sessionFactory);
        ADMIN_ROAD_REPORT_DAO = new AdminRoadReportDAO(sessionFactory);
        CAR_LIST_LOOK_UP_DAO = new CarListLookUpDAO(sessionFactory);
        CURRENT_VERSION_DAO = new CurrentVersionDAO(sessionFactory);
        DATE_LOOK_UP_DAO = new DateLookUpDAO(sessionFactory);
        FAVOURITE_ROAD_DAO = new FavouriteRoadDAO(sessionFactory);
        FUEL_COST_LOOK_UP_DAO = new FuelLookUpDAO(sessionFactory);
        HISTORY_DAO = new HistoryDAO(sessionFactory);
        MAIN_POINT_DAO = new MainPointDAO(sessionFactory);
        ROAD_DAO = new RoadDAO(sessionFactory);
        ROUTE_DAO = new RouteDAO(sessionFactory);
        SNAP_SHOTS_DAO = new SnapShotsDAO(sessionFactory);
        SUB_ROAD_DAO = new SubRoadDAO(sessionFactory);
        SUB_ROUTE_DAO = new SubRouteDAO(sessionFactory);
        USER_DAO = new UserDAO(sessionFactory);
        USER_ROAD_REPORT_DAO = new UserRoadReportDAO(sessionFactory);
        REPORT_TYPE_LOOK_UP_DAO = new ReportTypeLookupDAO(sessionFactory);
        MODE_LOOK_UP_DAO = new ModeLookUpDAO(sessionFactory);
        STATUS_LOOK_UP_DAO = new StatusLookUpDAO(sessionFactory);
        PENDING_USER_DAO = new PendingUserDAO(sessionFactory);
        BLOCKED_USER_DAO = new BlockedUserDAO(sessionFactory);
        DAILY_WINNER_DAO = new DailyWinnersDAO(sessionFactory);
        WINNER_DAO = new WinnerDAO(sessionFactory);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static AdminDAO getADMIN_DAO() {
        return ADMIN_DAO;
    }

    public static CarListLookUpDAO getCAR_LIST_LOOK_UP_DAO() {
        return CAR_LIST_LOOK_UP_DAO;
    }

    public static CurrentVersionDAO getCURRENT_VERSION_DAO() {
        return CURRENT_VERSION_DAO;
    }

    public static FavouriteRoadDAO getFAVOURITE_ROAD_DAO() {
        return FAVOURITE_ROAD_DAO;
    }

    public static FuelLookUpDAO getFUEL_COST_LOOK_UP_DAO() {
        return FUEL_COST_LOOK_UP_DAO;
    }

    public static MainPointDAO getMAIN_POINT_DAO() {
        return MAIN_POINT_DAO;
    }

    public static RoadDAO getROAD_DAO() {
        return ROAD_DAO;
    }

    public static RouteDAO getROUTE_DAO() {
        return ROUTE_DAO;
    }

    public static SnapShotsDAO getSNAP_SHOTS_DAO() {
        return SNAP_SHOTS_DAO;
    }

    public static SubRoadDAO getSUB_ROAD_DAO() {
        return SUB_ROAD_DAO;
    }

    public static SubRouteDAO getSub_Route_DAO() {
        return SUB_ROUTE_DAO;
    }

    public static UserDAO getUSER_DAO() {
        return USER_DAO;
    }

    public static DateLookUpDAO getDATE_LOOK_UP_DAO() {
        return DATE_LOOK_UP_DAO;
    }

    public static HistoryDAO getHISTORY_DAO() {
        return HISTORY_DAO;
    }

    public static SubRouteDAO getSUB_ROUTE_DAO() {
        return SUB_ROUTE_DAO;
    }

    public static AdminRoadReportDAO getADMIN_ROAD_REPORT_DAO() {
        return ADMIN_ROAD_REPORT_DAO;
    }

    public static ReportTypeLookupDAO getREPORT_TYPE_LOOK_UP_DAO() {
        return REPORT_TYPE_LOOK_UP_DAO;
    }
    public static UserRoadReportDAO getUSER_ROAD_REPORT_DAO(){
        return USER_ROAD_REPORT_DAO;
    }
    
    public static ModeLookUpDAO getMODE_LOOK_UP_DAO(){
        return MODE_LOOK_UP_DAO;
    }
    
    public static StatusLookUpDAO getSTATUS_LOOK_UP_DAO(){
        return STATUS_LOOK_UP_DAO;
    }
    
    public static PendingUserDAO getPENDING_USER_DAO(){
        return PENDING_USER_DAO;
    }
    
    public static BlockedUserDAO getBlockedUserDAO(){
        return BLOCKED_USER_DAO;
    }
    
    public static DailyWinnersDAO getDAILY_WINNERS_DAO(){
        return DAILY_WINNER_DAO;
    }
    public static WinnerDAO getWINNERDAO(){
        return WINNER_DAO;
    }
}
