package servicelayer.server.dal.pojos;
// Generated Jun 12, 2014 10:23:42 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Snapshot generated by hbm2java
 */
public class Snapshot  implements java.io.Serializable {


     private Integer id;
     private User user;
     private SubRoad subRoad;
     private Double speed;
     private Date timeStamp;

    public Snapshot() {
    }

	
    public Snapshot(SubRoad subRoad) {
        this.subRoad = subRoad;
    }
    public Snapshot(User user, SubRoad subRoad, Double speed, Date timeStamp) {
       this.user = user;
       this.subRoad = subRoad;
       this.speed = speed;
       this.timeStamp = timeStamp;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public SubRoad getSubRoad() {
        return this.subRoad;
    }
    
    public void setSubRoad(SubRoad subRoad) {
        this.subRoad = subRoad;
    }
    public Double getSpeed() {
        return this.speed;
    }
    
    public void setSpeed(Double speed) {
        this.speed = speed;
    }
    public Date getTimeStamp() {
        return this.timeStamp;
    }
    
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }




}


