package ultramirinc.champs_mood.models;

import android.support.annotation.NonNull;

/**
 * Created by Étienne Bérubé on 2017-04-04.
 * This class helps to do calculations with the breaks
 */

public class Time implements Comparable<Time>{
    /**The minutes of the time*/
    private int minute;
    /**The hour of the time*/
    private int hour;

    public Time(){
        this.minute = 0;
        this.hour = 0;
    }

    public Time(int hour, int minute){
        this.minute = minute;
        this.hour = hour;
    }
    /**Getter for the hour.*/
    public int getHour() {
        return hour;
    }
    /**Setter for the hour.*/
    public void setHour(int hour) {
        this.hour = hour;
    }
    /**Getter for the minutes.*/
    public int getMinute() {
        return minute;
    }
    /**Setter for the minutes.*/
    public void setMinute(int minute) {
        this.minute = minute;
    }
    /**Returns if a Time is before or after another time.*/
    @Override
    public int compareTo(@NonNull Time o) {
        if(this.hour > o.getHour())
            return 1;
        else if(this.hour < o.getHour())
            return -1;
        else
            return 0;
    }
    /**Returns a String representation of a time.*/
    @Override
    public String toString(){
        return hour + ":" + ((minute < 10) ? " " : "")+ minute;
    }
}
