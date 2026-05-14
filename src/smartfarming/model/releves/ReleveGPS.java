package TP_POO.src.smartfarming.model.releves;

import TP_POO.src.smartfarming.helper.Coordonnees;

/**
 * Classe ReleveGPS — Relevé de position géographique (latitude / longitude).
 *
 * Rôle : Utilisé exclusivement par le CapteurGPS pour envoyer la position
 * d'un animal équipé d'un collier GPS.
 *
 * Concept POO : HÉRITAGE — spécialise Releve avec des coordonnées GPS.
 * POLYMORPHISME — implémente getDescription() avec lat/lng.
 */
public class ReleveGPS extends Releve {

    private double latitude;
    private double longitude;

    /**
     * Constructeur.
     *
     * @param codeCapteur code du capteur GPS source
     * @param latitude    latitude GPS
     * @param longitude   longitude GPS
     */
    public ReleveGPS(String codeCapteur, double latitude, double longitude) {
        super(codeCapteur);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // ── Getters / Setters ──────────────────────────────────

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // ── Méthodes métier ────────────────────────────────────

    /**
     * Retourne la position sous forme d'objet Coordonnees.
     *
     * @return les coordonnées GPS du relevé
     */
    public Coordonnees getPosition() {
        return new Coordonnees(latitude, longitude);
    }

    /**
     * Retourne la description sous la forme "lat=x, lng=y".
     *
     * @return description GPS formatée
     */
    @Override
    public String getDescription() {
        return "lat=" + latitude + ", lng=" + longitude;
    }
}
