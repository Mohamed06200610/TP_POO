package TP_POO.src.smartfarming.model.zones;

import java.util.ArrayList;
import java.util.List;
import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.StatutZone;
import TP_POO.src.smartfarming.enums.TypeZone;
import TP_POO.src.smartfarming.helper.Coordonnees;
import TP_POO.src.smartfarming.interfaces.ISuspendable;
import TP_POO.src.smartfarming.model.capteurs.Capteur;
import TP_POO.src.smartfarming.model.historique.HistoriqueProduction;

public abstract class Zone implements ISuspendable {

    private String code;
    private String nom;
    private TypeZone typeZone;
    private StatutZone statut;
    private List<Capteur> capteurs;
    private List<HistoriqueProduction> historiques;

    // Limites géographiques de la zone (rectangle simplifié)
    private double latitudeMin;
    private double latitudeMax;
    private double longitudeMin;
    private double longitudeMax;

    public Zone(String code, String nom, TypeZone typeZone) {
        this.code = code;
        this.nom = nom;
        this.typeZone = typeZone;
        this.statut = StatutZone.ACTIVE;
        this.capteurs = new ArrayList<>();
        this.historiques = new ArrayList<>();
        // Limites par défaut (zone large)
        this.latitudeMin = -90.0;
        this.latitudeMax = 90.0;
        this.longitudeMin = -180.0;
        this.longitudeMax = 180.0;
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeZone getTypeZone() {
        return typeZone;
    }

    public void setTypeZone(TypeZone typeZone) {
        this.typeZone = typeZone;
    }

    public StatutZone getStatutZone() {
        return statut;
    }

    public void setStatutZone(StatutZone statut) {
        this.statut = statut;
    }

    public List<Capteur> getCapteurs() {
        return capteurs;
    }

    public void setCapteurs(List<Capteur> capteurs) {
        this.capteurs = capteurs;
    }

    public List<HistoriqueProduction> getHistoriques() {
        return historiques;
    }

    public void setHistoriques(List<HistoriqueProduction> historiques) {
        this.historiques = historiques;
    }

    public double getLatitudeMin() {
        return latitudeMin;
    }

    public void setLatitudeMin(double latitudeMin) {
        this.latitudeMin = latitudeMin;
    }

    public double getLatitudeMax() {
        return latitudeMax;
    }

    public void setLatitudeMax(double latitudeMax) {
        this.latitudeMax = latitudeMax;
    }

    public double getLongitudeMin() {
        return longitudeMin;
    }

    public void setLongitudeMin(double longitudeMin) {
        this.longitudeMin = longitudeMin;
    }

    public double getLongitudeMax() {
        return longitudeMax;
    }

    public void setLongitudeMax(double longitudeMax) {
        this.longitudeMax = longitudeMax;
    }

    // ── ISuspendable ───────────────────────────────────────

    @Override
    public void activer() {
        this.statut = StatutZone.ACTIVE;
        for (Capteur c : capteurs) {
            c.activer();
        }
    }

    @Override
    public void suspendre() {
        this.statut = StatutZone.SUSPENDUE;
        for (Capteur c : capteurs) {
            c.suspendre();
        }
    }

    @Override
    public String getStatut() {
        return statut.name();
    }

    // ── Méthodes métier ────────────────────────────────────

    public void ajouterCapteur(Capteur capteur) {
        this.capteurs.add(capteur);
    }

    public boolean estDansLimites(Coordonnees coordonnees) {
        return coordonnees.getLatitude() >= latitudeMin
                && coordonnees.getLatitude() <= latitudeMax
                && coordonnees.getLongitude() >= longitudeMin
                && coordonnees.getLongitude() <= longitudeMax;
    }

    public void definirLimites(double latMin, double latMax, double lngMin, double lngMax) {
        this.latitudeMin = latMin;
        this.latitudeMax = latMax;
        this.longitudeMin = lngMin;
        this.longitudeMax = lngMax;
    }

    public abstract int getNombreEntites();

    /**
     * Enregistre la production de la zone.
     */
    public abstract void enregistrerProduction();

    @Override
    public String toString() {
        return "Zone [" + code + "] " + nom + " — " + typeZone + " — " + statut;
    }
}
