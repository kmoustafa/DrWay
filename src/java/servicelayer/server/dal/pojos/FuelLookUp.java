package servicelayer.server.dal.pojos;
// Generated Jun 12, 2014 10:23:42 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * FuelLookUp generated by hbm2java
 */
public class FuelLookUp  implements java.io.Serializable {


     private int id;
     private String model;
     private String value;
     private Set users = new HashSet(0);
     private Set pendingUsers = new HashSet(0);

    public FuelLookUp() {
    }

	
    public FuelLookUp(int id, String model, String value) {
        this.id = id;
        this.model = model;
        this.value = value;
    }
    public FuelLookUp(int id, String model, String value, Set users, Set pendingUsers) {
       this.id = id;
       this.model = model;
       this.value = value;
       this.users = users;
       this.pendingUsers = pendingUsers;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getModel() {
        return this.model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    public Set getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }
    public Set getPendingUsers() {
        return this.pendingUsers;
    }
    
    public void setPendingUsers(Set pendingUsers) {
        this.pendingUsers = pendingUsers;
    }




}

