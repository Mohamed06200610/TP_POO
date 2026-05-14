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

/**
 * Classe abstraite Zone — Représente une zone géographique de la ferme.
 *
 * Rôle : Classe de base pour les trois types de zones (Culture, Élevage,
 * Aquacole).
 * Gère le code, le nom, le type, le statut, les capteurs et l'historique
 * de production. Implémente ISuspendable pour la gestion du cycle de vie.
 *
 * Concept POO :
 * ABSTRACTION — déclare enregistrerProduction() abstrait, forcant chaque
 * sous-classe à créer sa propre sous-classe d'HistoriqueProduction.
 * INTERFACE — implémente ISuspendable (activer/suspendre/getStatut).
 * ENCAPSULATION — tous les attributs sont privés.
 *
 * Règle métier clé :
 * suspendre() cascade automatiquement à TOUS les capteurs de la zone.
 * activer() restaure TOUS les capteurs.
 */
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

    /**
     * Constructeur.
     *
     * @param code     code unique de la zone
     * @param nom      nom descriptif de la zone
     * @param typeZone type de zone (CULTURE, ELEVAGE, AQUACOLE)
     */
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

    /**
     * Active la zone et restaure TOUS ses capteurs (cascade).
     */
    @Override
    public void activer() {
        this.statut = StatutZone.ACTIVE;
        for (Capteur c : capteurs) {
            c.activer();
        }
    }

    /**
     * Suspend la zone et cascade la suspension à TOUS ses capteurs.
     * Un capteur suspendu ne peut plus envoyer de relevés.
     */
    @Override
    public void suspendre() {
        this.statut = StatutZone.SUSPENDUE;
        for (Capteur c : capteurs) {
            c.suspendre();
        }
    }

    /**
     * Retourne le statut sous forme de chaîne.
     *
     * @return "ACTIVE" ou "SUSPENDUE"
     */
    @Override
    public String getStatut() {
        return statut.name();
    }

    // ── Méthodes métier ────────────────────────────────────

    /**
     * Ajoute un capteur à la zone.
     *
     * @param capteur le capteur à ajouter
     */
    public void ajouterCapteur(Capteur capteur) {
        this.capteurs.add(capteur);
    }

    /**
     * Vérifie si un point GPS est dans les limites géographiques de la zone.
     *
     * @param coordonnees les coordonnées à vérifier
     * @return true si le point est dans les limites
     */
    public boolean estDansLimites(Coordonnees coordonnees) {
        return coordonnees.getLatitude() >= latitudeMin
                && coordonnees.getLatitude() <= latitudeMax
                && coordonnees.getLongitude() >= longitudeMin
                && coordonnees.getLongitude() <= longitudeMax;
    }

    /**
     * Définit les limites géographiques de la zone.
     *
     * @param latMin latitude minimale
     * @param latMax latitude maximale
     * @param lngMin longitude minimale
     * @param lngMax longitude maximale
     */
    public void definirLimites(double latMin, double latMax, double lngMin, double lngMax) {
        this.latitudeMin = latMin;
        this.latitudeMax = latMax;
        this.longitudeMin = lngMin;
        this.longitudeMax = lngMax;
    }

    /**
     * Retourne le nombre d'entités hébergées dans la zone.
     * Chaque sous-classe implémente sa propre logique de comptage.
     *
     * @return nombre d'entités
     */
    public abstract int getNombreEntites();

    /**
     * Enregistre la production de la zone.
     * Chaque sous-classe crée sa propre sous-classe d'HistoriqueProduction.
     *
     * Polymorphisme : ZoneCulture → HistoriqueProductionCulture,
     * ZoneElevage → HistoriqueProductionElevage,
     * ZoneAquacole → HistoriqueProductionAquacole.
     */
    public abstract void enregistrerProduction();

    @Override
    public String toString() {
        return "Zone [" + code + "] " + nom + " — " + typeZone + " — " + statut;
    }
}
