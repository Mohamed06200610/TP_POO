package TP_POO.src.smartfarming.helper;

import TP_POO.src.smartfarming.enums.StatutZone;
import TP_POO.src.smartfarming.enums.TypeZone;

/**
 * Classe ZoneInfo — Objet de transfert pour la vue d'ensemble des zones.
 *
 * Rôle : Regroupe les informations synthétiques d'une zone pour l'affichage :
 * code, nom, type, statut et nombre d'entités hébergées.
 *
 * Concept POO : ENCAPSULATION — objet DTO (Data Transfer Object) avec
 * attributs privés et getters.
 */
public class ZoneInfo {

    private String code;
    private String nom;
    private TypeZone type;
    private StatutZone statut;
    private int nombreEntites;

    /**
     * Constructeur.
     *
     * @param code          code unique de la zone
     * @param nom           nom de la zone
     * @param type          type de zone
     * @param statut        statut actuel
     * @param nombreEntites nombre d'entités hébergées
     */
    public ZoneInfo(String code, String nom, TypeZone type, StatutZone statut, int nombreEntites) {
        this.code = code;
        this.nom = nom;
        this.type = type;
        this.statut = statut;
        this.nombreEntites = nombreEntites;
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

    public TypeZone getType() {
        return type;
    }

    public void setType(TypeZone type) {
        this.type = type;
    }

    public StatutZone getStatut() {
        return statut;
    }

    public void setStatut(StatutZone statut) {
        this.statut = statut;
    }

    public int getNombreEntites() {
        return nombreEntites;
    }

    public void setNombreEntites(int nombreEntites) {
        this.nombreEntites = nombreEntites;
    }

    @Override
    public String toString() {
        return "Zone [" + code + "] " + nom + " | Type: " + type
                + " | Statut: " + statut + " | Entités: " + nombreEntites;
    }
}
