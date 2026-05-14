package TP_POO.src.smartfarming.helper;

import TP_POO.src.smartfarming.model.zones.Zone;

/**
 * Classe Coordonnees — Objet-valeur représentant une position GPS (latitude,
 * longitude).
 *
 * Rôle : Encapsule les coordonnées géographiques et fournit la vérification
 * d'appartenance d'un point à une zone.
 *
 * Concept POO : ENCAPSULATION — attributs privés, accès via getters.
 * VALUE OBJECT — objet immuable représentant une position.
 */
public class Coordonnees {

    private double latitude;
    private double longitude;

    /**
     * Constructeur.
     *
     * @param latitude  latitude GPS
     * @param longitude longitude GPS
     */
    public Coordonnees(double latitude, double longitude) {
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
     * Vérifie si cette coordonnée se situe dans les limites de la zone donnée.
     * Utilise la vérification de limites définie sur la zone.
     *
     * @param zone la zone à vérifier
     * @return true si le point est dans les limites de la zone
     */
    public boolean estDansZone(Zone zone) {
        return zone.estDansLimites(this);
    }

    @Override
    public String toString() {
        return "lat=" + latitude + ", lng=" + longitude;
    }
}
