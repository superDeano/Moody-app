package ultramirinc.champs_mood.models;



/**
 * Created by Étienne Bérubé on 2017-05-10.
 * This class acts as an alternative to the LatLng object provided by Google. However, the LatLng object cannot be stored
 * in the database. Thus, we created this object
 */

public class MyLocation{
    /**A latitude*/
    private double lat;
    /**A longitude*/
    private double lng;


    public MyLocation(){
    }

    public MyLocation(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }
    /**Getter for the latitude.*/
    public double getLat() {
        return lat;
    }
    /**Setter for the latitude.*/
    public void setLat(double lat) {
        this.lat = lat;
    }
    /**Getter for the longitude.*/
    public double getLng() {
        return lng;
    }
    /**Setter for the longitude.*/
    public void setLng(double lng) {
        this.lng = lng;
    }
}
