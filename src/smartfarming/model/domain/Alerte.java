package TP_POO.src.smartfarming.model.domain;

import java.util.Date;
import TP_POO.src.smartfarming.enums.NiveauGravite;
import TP_POO.src.smartfarming.enums.StatutAlerte;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.model.capteurs.Capteur;
import TP_POO.src.smartfarming.model.releves.Releve;

/**
 * Classe Alerte — Représente une alerte déclenchée par un dépassement de seuil.
 *
 * Rôle : Modélise une alerte avec son relevé source, le capteur concerné,
 * le niveau de gravité et son statut (active, acquittée, supprimée).
 *
 * Concept POO : ENCAPSULATION — attributs privés, cycle de vie géré
 * en interne (ACTIVE → ACQUITTEE / SUPPRIMEE).
 */
public class Alerte {

    private String id;
    private Releve releve;
    private Capteur capteur;
    private NiveauGravite niveauGravite;
    private StatutAlerte statut;
    private Date horodatage;

    private static int compteur = 0;

    /**
     * Constructeur.
     *
     * @param releve        le relevé ayant déclenché l'alerte
     * @param capteur       le capteur source
     * @param niveauGravite niveau de gravité (AVERTISSEMENT ou CRITIQUE)
     */
    public Alerte(Releve releve, Capteur capteur, NiveauGravite niveauGravite) {
        this.id = "ALR-" + (++compteur);
        this.releve = releve;
        this.capteur = capteur;
        this.niveauGravite = niveauGravite;
        this.statut = StatutAlerte.ACTIVE;
        this.horodatage = new Date();
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Releve getReleve() {
        return releve;
    }

    public void setReleve(Releve releve) {
        this.releve = releve;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }

    public NiveauGravite getNiveauGravite() {
        return niveauGravite;
    }

    public void setNiveauGravite(NiveauGravite niveauGravite) {
        this.niveauGravite = niveauGravite;
    }

    public StatutAlerte getStatut() {
        return statut;
    }

    public void setStatut(StatutAlerte statut) {
        this.statut = statut;
    }

    public Date getHorodatage() {
        return horodatage;
    }

    public void setHorodatage(Date horodatage) {
        this.horodatage = horodatage;
    }

    // ── Méthodes utilitaires ───────────────────────────────

    /**
     * Retourne le type du capteur ayant déclenché l'alerte.
     *
     * @return le type de capteur
     */
    public TypeCapteur getTypeCapteur() {
        return capteur.getTypeCapteur();
    }

    /**
     * Retourne le code de zone du capteur.
     *
     * @return le code de zone
     */
    public String getCodeZone() {
        return capteur.getCodeZone();
    }

    @Override
    public String toString() {
        return "Alerte [" + id + "] " + niveauGravite + " — Capteur: "
                + capteur.getCode() + " — " + statut;
    }
}
