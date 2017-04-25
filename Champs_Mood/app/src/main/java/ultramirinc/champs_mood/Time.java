package ultramirinc.champs_mood;

import android.support.annotation.NonNull;

/**
 * Created by Étienne Bérubé on 2017-04-04.
 */

public class Time implements Comparable<Time>{

    private int minute;
    private int hour;

    public Time(){
        this.minute = 0;
        this.hour = 0;
    }

    public Time(int hour, int minute){
        this.minute = minute;
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    @Override
    public int compareTo(@NonNull Time o) {
        if(this.hour > o.getHour())
            return 1;
        else if(this.hour < o.getHour())
            return -1;
        else
            return 0;
    }

    @Override
    public String toString(){
        return hour + ":" + ((minute < 10) ? " " : "")+ minute;
    }
}
