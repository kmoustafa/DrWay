/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import servicelayer.server.dal.pojos.BlockedUser;

/**
 *
 * @author eng.banoota
 */
public class BlockedUserDAO {
    private final Session session;

    public BlockedUserDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }
    
    public List<BlockedUser> getAllPendingUsers (){
        try{
        List<BlockedUser> blockedUsers = session.createCriteria(BlockedUser.class).list();
        if(blockedUsers!=null && !blockedUsers.isEmpty()){
            for(BlockedUser blockedUser : blockedUsers){
                session.refresh(blockedUser);
            }
        }
        return blockedUsers;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<String> getAllBlockedUserNames(){
        return session.createCriteria(BlockedUser.class).setProjection(Projections.property("name")).list();
    }
}
