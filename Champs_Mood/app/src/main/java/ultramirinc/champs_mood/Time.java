package ultramirinc.champs_mood;

/**
 * Created by Étienne Bérubé on 2017-04-04.
 */

public class Time {

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
}
