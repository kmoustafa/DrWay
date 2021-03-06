package servicelayer.server.dal.pojos;
// Generated Jun 12, 2014 10:23:42 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * DateLookUp generated by hbm2java
 */
public class DateLookUp  implements java.io.Serializable {


     private Integer id;
     private String dayName;
     private int mday;
     private int mmonth;
     private int myear;
     private int hday;
     private int hmonth;
     private int hyear;
     private Set predictions = new HashSet(0);
     private Set histories = new HashSet(0);

    public DateLookUp() {
    }

	
    public DateLookUp(String dayName, int mday, int mmonth, int myear, int hday, int hmonth, int hyear) {
        this.dayName = dayName;
        this.mday = mday;
        this.mmonth = mmonth;
        this.myear = myear;
        this.hday = hday;
        this.hmonth = hmonth;
        this.hyear = hyear;
    }
    public DateLookUp(String dayName, int mday, int mmonth, int myear, int hday, int hmonth, int hyear, Set predictions, Set histories) {
       this.dayName = dayName;
       this.mday = mday;
       this.mmonth = mmonth;
       this.myear = myear;
       this.hday = hday;
       this.hmonth = hmonth;
       this.hyear = hyear;
       this.predictions = predictions;
       this.histories = histories;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDayName() {
        return this.dayName;
    }
    
    public void setDayName(String dayName) {
        this.dayName = dayName;
    }
    public int getMday() {
        return this.mday;
    }
    
    public void setMday(int mday) {
        this.mday = mday;
    }
    public int getMmonth() {
        return this.mmonth;
    }
    
    public void setMmonth(int mmonth) {
        this.mmonth = mmonth;
    }
    public int getMyear() {
        return this.myear;
    }
    
    public void setMyear(int myear) {
        this.myear = myear;
    }
    public int getHday() {
        return this.hday;
    }
    
    public void setHday(int hday) {
        this.hday = hday;
    }
    public int getHmonth() {
        return this.hmonth;
    }
    
    public void setHmonth(int hmonth) {
        this.hmonth = hmonth;
    }
    public int getHyear() {
        return this.hyear;
    }
    
    public void setHyear(int hyear) {
        this.hyear = hyear;
    }
    public Set getPredictions() {
        return this.predictions;
    }
    
    public void setPredictions(Set predictions) {
        this.predictions = predictions;
    }
    public Set getHistories() {
        return this.histories;
    }
    
    public void setHistories(Set histories) {
        this.histories = histories;
    }




}


