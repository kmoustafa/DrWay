//package drway.server.testing;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//
//
//import drway.server.util.NamingStrategyUtil;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
///**
// *
// * @author Rana Ashraf
// */
//public class NamingStrategyTest {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//       
//        Configuration cfg = new Configuration();
//        NamingStrategyUtil ref = new NamingStrategyUtil();
//        cfg.setNamingStrategy(ref);
//        cfg.configure();
//        SessionFactory sf = cfg.buildSessionFactory();
//        Session session = sf.openSession();
//        session.getTransaction().begin();
//        
//        String query = "delimiter $$\n" +
//"\n" +
//"CREATE TABLE `speed_history_test` (\n" +
//"  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
//"  `Sub_Road_ID` int(11) NOT NULL,\n" +
//"  `Time_Stamp_ID` int(11) NOT NULL,\n" +
//"  `avg_speed_00` double DEFAULT NULL,\n" +
//"  `avg_speed_01` double DEFAULT NULL,\n" +
//"  `avg_speed_02` double DEFAULT NULL,\n" +
//"  `avg_speed_03` double DEFAULT NULL,\n" +
//"  `avg_speed_04` double DEFAULT NULL,\n" +
//"  `avg_speed_05` double DEFAULT NULL,\n" +
//"  `avg_speed_06` double DEFAULT NULL,\n" +
//"  `avg_speed_07` double DEFAULT NULL,\n" +
//"  `avg_speed_08` double DEFAULT NULL,\n" +
//"  `avg_speed_09` double DEFAULT NULL,\n" +
//"  `avg_speed_10` double DEFAULT NULL,\n" +
//"  `avg_speed_11` double DEFAULT NULL,\n" +
//"  `avg_speed_12` double DEFAULT NULL,\n" +
//"  `avg_speed_13` double DEFAULT NULL,\n" +
//"  `avg_speed_14` double DEFAULT NULL,\n" +
//"  `avg_speed_15` double DEFAULT NULL,\n" +
//"  `avg_speed_16` double DEFAULT NULL,\n" +
//"  `avg_speed_17` double DEFAULT NULL,\n" +
//"  `avg_speed_18` double DEFAULT NULL,\n" +
//"  `avg_speed_19` double DEFAULT NULL,\n" +
//"  `avg_speed_20` double DEFAULT NULL,\n" +
//"  `avg_speed_21` double DEFAULT NULL,\n" +
//"  `avg_speed_22` double DEFAULT NULL,\n" +
//"  `avg_speed_23` double DEFAULT NULL,\n" +
//"  PRIMARY KEY (`ID`),\n" +
//"  KEY `History_Sub_Road_idx` (`Sub_Road_ID`),\n" +
//"  KEY `History_Time_Stamp_idx` (`Time_Stamp_ID`),\n" +
//"  CONSTRAINT `History_Sub_Road` FOREIGN KEY (`Sub_Road_ID`) REFERENCES `sub_road` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
//"  CONSTRAINT `History_Time_Stamp` FOREIGN KEY (`Time_Stamp_ID`) REFERENCES `time_stamp_look_up` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION\n" +
//") ENGINE=InnoDB DEFAULT CHARSET=latin1$$";       
//        session.createSQLQuery(query);
//        ref.setKey("SpeedHistory", "speed_history_test");
//         
//        session.getTransaction().commit();
//        session.close();
//        System.out.println("Table Creation Done");
//    }
//    
//}
