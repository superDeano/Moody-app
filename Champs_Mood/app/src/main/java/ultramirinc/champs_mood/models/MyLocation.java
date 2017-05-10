package ultramirinc.champs_mood.models;



/**
 * Created by Étienne Bérubé on 2017-05-10.
 */

public class MyLocation{
    private double lat;
    private double lng;


    public MyLocation(){
    }

    public MyLocation(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
