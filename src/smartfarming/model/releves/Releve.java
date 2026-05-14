package TP_POO.src.smartfarming.model.releves;

import java.util.Date;

/**
 * Classe abstraite Releve — Représente un relevé brut envoyé par un capteur.
 *
 * Rôle : Classe de base pour tous les types de relevés. Chaque sous-classe
 * concrète (ReleveNumerique, ReleveGPS) implémente getDescription()
 * pour fournir un résumé lisible du relevé.
 *
 * Concept POO : ABSTRACTION — impose la méthode getDescription() sans
 * imposer de format. HÉRITAGE — ReleveNumerique et ReleveGPS
 * spécialisent cette classe.
 */
public abstract class Releve {

    private String id;
    private Date horodatage;
    private String codeCapteur;

    private static int compteur = 0;

    /**
     * Constructeur.
     *
     * @param codeCapteur code du capteur ayant généré le relevé
     */
    public Releve(String codeCapteur) {
        this.id = "REL-" + (++compteur);
        this.horodatage = new Date();
        this.codeCapteur = codeCapteur;
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getHorodatage() {
        return horodatage;
    }

    public void setHorodatage(Date horodatage) {
        this.horodatage = horodatage;
    }

    public String getCodeCapteur() {
        return codeCapteur;
    }

    public void setCodeCapteur(String codeCapteur) {
        this.codeCapteur = codeCapteur;
    }

    // ── Méthode abstraite ──────────────────────────────────

    /**
     * Retourne une description lisible du relevé.
     * Implémentée différemment par ReleveNumerique et ReleveGPS.
     *
     * @return description formatée du relevé
     */
    public abstract String getDescription();

    @Override
    public String toString() {
        return "[" + id + "] " + horodatage + " — " + getDescription();
    }
}
