//package drway.server.util;
//
//
//import java.util.HashMap;
//import org.hibernate.cfg.DefaultNamingStrategy;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
///**
// *
// * @author Rana Ashraf
// */
//public class NamingStrategyUtil extends DefaultNamingStrategy {
//
//    HashMap<String, String> speadHistory = new HashMap<String, String>();
//
//    public void setKey(String className, String tableName) {
//        speadHistory.put(className, tableName);
//    }
//
//    @Override
//    public String classToTableName(String className) {
//        String key = speadHistory.get(className);
//        if (key == null) {
//            key = className;
//        }
//        return key;
//    }
//}
