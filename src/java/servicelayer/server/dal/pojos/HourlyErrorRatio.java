package servicelayer.server.dal.pojos;
// Generated Jun 12, 2014 10:23:42 PM by Hibernate Tools 3.6.0



/**
 * HourlyErrorRatio generated by hbm2java
 */
public class HourlyErrorRatio  implements java.io.Serializable {


     private Integer id;
     private SubRoad subRoad;
     private int hour;
     private double realSpeed;
     private double predictedSpeed;

    public HourlyErrorRatio() {
    }

    public HourlyErrorRatio(SubRoad subRoad, int hour, double realSpeed, double predictedSpeed) {
       this.subRoad = subRoad;
       this.hour = hour;
       this.realSpeed = realSpeed;
       this.predictedSpeed = predictedSpeed;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public SubRoad getSubRoad() {
        return this.subRoad;
    }
    
    public void setSubRoad(SubRoad subRoad) {
        this.subRoad = subRoad;
    }
    public int getHour() {
        return this.hour;
    }
    
    public void setHour(int hour) {
        this.hour = hour;
    }
    public double getRealSpeed() {
        return this.realSpeed;
    }
    
    public void setRealSpeed(double realSpeed) {
        this.realSpeed = realSpeed;
    }
    public double getPredictedSpeed() {
        return this.predictedSpeed;
    }
    
    public void setPredictedSpeed(double predictedSpeed) {
        this.predictedSpeed = predictedSpeed;
    }




}


