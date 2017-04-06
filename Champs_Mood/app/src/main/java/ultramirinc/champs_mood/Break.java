package ultramirinc.champs_mood;

import java.util.Date;

/**
 * Created by Étienne Bérubé on 2017-03-28.
 */

class Break {
    private Time start;
    private Time end;
    private String day;

    public Break(Time start, Time end, String day){
        this.start = start;
        this.end = end;
        this.day = day;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Break aBreak = (Break) o;

        if (!getStart().equals(aBreak.getStart())) return false;
        if (!getEnd().equals(aBreak.getEnd())) return false;
        return getDay().equals(aBreak.getDay());
    }

}