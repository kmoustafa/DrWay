package servicelayer.server.dal.pojos;
// Generated Jun 12, 2014 10:23:42 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * OutsideSnapShot generated by hbm2java
 */
public class OutsideSnapShot  implements java.io.Serializable {


     private Integer id;
     private User user;
     private int x;
     private int y;
     private Double speed;
     private Date timeStamp;

    public OutsideSnapShot() {
    }

	
    public OutsideSnapShot(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public OutsideSnapShot(User user, int x, int y, Double speed, Date timeStamp) {
       this.user = user;
       this.x = x;
       this.y = y;
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
    public int getX() {
        return this.x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return this.y;
    }
    
    public void setY(int y) {
        this.y = y;
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


