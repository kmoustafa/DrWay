package servicelayer.server.dal.pojos;
// Generated Jun 12, 2014 10:23:42 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * SubRoad generated by hbm2java
 */
public class SubRoad  implements java.io.Serializable {


     private Integer id;
     private Road road;
     private double x1;
     private double y1;
     private double x2;
     private double y2;
     private Integer orderInRoad;
     private double distance;
     private double currentSpeed;
     private Set dailyErrorRatios = new HashSet(0);
     private Set hourlyErrorRatios = new HashSet(0);
     private Set histories = new HashSet(0);
     private Set snapshots = new HashSet(0);
     private Set predictions = new HashSet(0);

    public SubRoad() {
    }

	
    public SubRoad(Road road, double x1, double y1, double x2, double y2, double distance, double currentSpeed) {
        this.road = road;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.distance = distance;
        this.currentSpeed = currentSpeed;
    }
    public SubRoad(Road road, double x1, double y1, double x2, double y2, Integer orderInRoad, double distance, double currentSpeed, Set dailyErrorRatios, Set hourlyErrorRatios, Set histories, Set snapshots, Set predictions) {
       this.road = road;
       this.x1 = x1;
       this.y1 = y1;
       this.x2 = x2;
       this.y2 = y2;
       this.orderInRoad = orderInRoad;
       this.distance = distance;
       this.currentSpeed = currentSpeed;
       this.dailyErrorRatios = dailyErrorRatios;
       this.hourlyErrorRatios = hourlyErrorRatios;
       this.histories = histories;
       this.snapshots = snapshots;
       this.predictions = predictions;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Road getRoad() {
        return this.road;
    }
    
    public void setRoad(Road road) {
        this.road = road;
    }
    public double getX1() {
        return this.x1;
    }
    
    public void setX1(double x1) {
        this.x1 = x1;
    }
    public double getY1() {
        return this.y1;
    }
    
    public void setY1(double y1) {
        this.y1 = y1;
    }
    public double getX2() {
        return this.x2;
    }
    
    public void setX2(double x2) {
        this.x2 = x2;
    }
    public double getY2() {
        return this.y2;
    }
    
    public void setY2(double y2) {
        this.y2 = y2;
    }
    public Integer getOrderInRoad() {
        return this.orderInRoad;
    }
    
    public void setOrderInRoad(Integer orderInRoad) {
        this.orderInRoad = orderInRoad;
    }
    public double getDistance() {
        return this.distance;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public double getCurrentSpeed() {
        return this.currentSpeed;
    }
    
    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }
    public Set getDailyErrorRatios() {
        return this.dailyErrorRatios;
    }
    
    public void setDailyErrorRatios(Set dailyErrorRatios) {
        this.dailyErrorRatios = dailyErrorRatios;
    }
    public Set getHourlyErrorRatios() {
        return this.hourlyErrorRatios;
    }
    
    public void setHourlyErrorRatios(Set hourlyErrorRatios) {
        this.hourlyErrorRatios = hourlyErrorRatios;
    }
    public Set getHistories() {
        return this.histories;
    }
    
    public void setHistories(Set histories) {
        this.histories = histories;
    }
    public Set getSnapshots() {
        return this.snapshots;
    }
    
    public void setSnapshots(Set snapshots) {
        this.snapshots = snapshots;
    }
    public Set getPredictions() {
        return this.predictions;
    }
    
    public void setPredictions(Set predictions) {
        this.predictions = predictions;
    }




}


