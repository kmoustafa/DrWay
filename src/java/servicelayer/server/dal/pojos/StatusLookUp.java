package servicelayer.server.dal.pojos;
// Generated Jun 12, 2014 10:23:42 PM by Hibernate Tools 3.6.0



/**
 * StatusLookUp generated by hbm2java
 */
public class StatusLookUp  implements java.io.Serializable {


     private int id;
     private String name;
     private double minSpeed;
     private double maxSpeed;

    public StatusLookUp() {
    }

    public StatusLookUp(int id, String name, double minSpeed, double maxSpeed) {
       this.id = id;
       this.name = name;
       this.minSpeed = minSpeed;
       this.maxSpeed = maxSpeed;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public double getMinSpeed() {
        return this.minSpeed;
    }
    
    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }
    public double getMaxSpeed() {
        return this.maxSpeed;
    }
    
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }




}


