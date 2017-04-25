package ultramirinc.champs_mood;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Étienne Bérubé on 2017-03-28.
 * test
 */

class Break implements Comparable<Break>{
    private Time start;
    private Time end;
    private String day;
    private int intDay;

    public Break(Time start, Time end, String day){
        this.start = start;
        this.end = end;
        this.day = day;

        switch (day){
            case ("Monday"): intDay = 1; break;
            case ("Tuesday"): intDay = 2; break;
            case ("Wednesday"): intDay = 3; break;
            case ("Thursday"): intDay = 4; break;
            case ("Friday"): intDay = 5; break;
        }

    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public String getFromTime(){
        if(start.getMinute() >= 10)
            return start.getHour() + ":" + start.getMinute();
        else
            return start.getHour() + ":0" + start.getMinute();
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public String getToTime(){
        if(end.getMinute() >= 10)
            return end.getHour() + ":" + end.getMinute();
        else
            return end.getHour() + ":0" + end.getMinute();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getIntDay() {
        return intDay;
    }

    public void setIntDay(int intDay) {
        this.intDay = intDay;
    }

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
}
