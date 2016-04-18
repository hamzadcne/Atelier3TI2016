package Models;


import com.orm.SugarRecord;

/**
 * Created by LVSC on 01-03-20  16.
 */
public class Pharmacie extends SugarRecord {

    private String Nom;
    private String Ville;
    private Double Latitude;
    private Double Longitude;

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String ville) {
        Ville = ville;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }
    public Pharmacie(){

    }
    public Pharmacie(String nom, String ville, Double latitude, Double longitude) {
        Nom = nom;
        Ville = ville;
        Latitude = latitude;
        Longitude = longitude;
    }
}
