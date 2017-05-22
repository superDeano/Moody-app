package ultramirinc.champs_mood.models;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Etienne Berube on 2017-03-28.
 * This is the object that contains all the information and methods for a Break
 */

public class Break implements Comparable<Break>{
    /**Contains the time at which the break starts*/
    private Time start;
    /**Contains the at which the break ends*/
    private Time end;
    /**The day of the break*/
    private String day;
    /**The user's ID in the database*/
    private String userId;
    /**The integer representation of the day*/
    private int intDay;
    /**The Id of the Break*/
    private String Id;

    public Break() {}

    public Break(Time start, Time end, String day, String relatedUserId){
        this.start = start;
        this.end = end;
        this.day = day;
        this.userId = relatedUserId;
        switch (day){
            case ("Monday"): intDay = 1; break;
            case ("Tuesday"): intDay = 2; break;
            case ("Wednesday"): intDay = 3; break;
            case ("Thursday"): intDay = 4; break;
            case ("Friday"): intDay = 5; break;
        }
    }
    /**Setter for ID.*/
    public void setId(String id ) {
        this.Id = id;
    }
    /**Getter for ID.*/
    public String getId() {
        return this.Id;
    }
    /**Getter for the starting time.*/
    public Time getStart() {
        return start;
    }
    /**Setter for the starting time.*/
    public void setStart(Time start) {
        this.start = start;
    }
    /**Returns a string interpretation of the starting time.*/
    public String getFromTime() {
        if(start.getMinute() >= 10)
            return start.getHour() + ":" + start.getMinute();
        else
            return start.getHour() + ":0" + start.getMinute();
    }
    /**Getter for the end time.*/
    public Time getEnd() {
        return end;
    }
    /**Setter for the end time.*/
    public void setEnd(Time end) {
        this.end = end;
    }
    /**Returns a string interpretation of the ending time.*/
    public String getToTime(){
        if(end.getMinute() >= 10)
            return end.getHour() + ":" + end.getMinute();
        else
            return end.getHour() + ":0" + end.getMinute();
    }
    /**Getter for the day.*/
    public String getDay() {
        return day;
    }
    /**Setter for  the day.*/
    public void setDay(String day) {
        this.day = day;
    }
    /**Getter for the integer representation of the day.*/
    public int getIntDay() {
        return intDay;
    }
    /**Setter for the integer representation of the day.*/
    public void setIntDay(int intDay) {
        this.intDay = intDay;
    }
    /**Getter for the UserID*/
    public String getUserId() {
        return this.userId;
    }
    /**Setter for the the UserID*/
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**Returns whether or not two breaks are the same.*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Break aBreak = (Break) o;

        if (getIntDay() != aBreak.getIntDay()) return false;
        if (getStart() != null ? !getStart().equals(aBreak.getStart()) : aBreak.getStart() != null)
            return false;
        if (getEnd() != null ? !getEnd().equals(aBreak.getEnd()) : aBreak.getEnd() != null)
            return false;
        return getDay() != null ? getDay().equals(aBreak.getDay()) : aBreak.getDay() == null;

    }
    /**Determines is a break is before or after another break.*/
    @Override//Might change symbole;
    public int compareTo(@NonNull Break o) {
       if (this.intDay > o.getIntDay())
           return 1;
       else if(this.intDay < o.getIntDay())
           return -1;
       else {
           return this.getStart().compareTo(o.getStart());
       }
    }
    /**Returns the time difference between two breaks.*/
    public Time getTimeDifference(){

        GregorianCalendar current = new GregorianCalendar();

        int currentHour = current.get(Calendar.HOUR_OF_DAY);
        int currentMinute = current.get(Calendar.MINUTE);

        int breakHour = start.getHour();
        int breakMinute = start.getMinute();


        int hourDif = (breakHour - currentHour)%24;
        int minuteDif =(Math.abs(breakMinute - currentMinute))%60;

        if((breakMinute - currentMinute) < 0){
            hourDif--;
        }

        return new Time(hourDif, minuteDif);
    }

}
