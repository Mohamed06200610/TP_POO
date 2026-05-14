package TP_POO.src.smartfarming.helper;

import TP_POO.src.smartfarming.enums.NiveauGravite;

/**
 * Classe ReleveInfo — Objet de transfert pour le tableau de bord des relevés.
 *
 * Rôle : Regroupe les informations synthétiques d'un relevé capteur :
 * code du capteur, dernière valeur, unité et niveau de gravité
 * (indicateur coloré).
 *
 * Concept POO : ENCAPSULATION — DTO avec attributs privés et getters.
 */
public class ReleveInfo {

    private String codeCapteur;
    private double derniereValeur;
    private String unite;
    private NiveauGravite niveauGravite;

    /**
     * Constructeur.
     *
     * @param codeCapteur    code unique du capteur
     * @param derniereValeur dernière valeur relevée
     * @param unite          unité de mesure
     * @param niveauGravite  niveau de gravité (NORMAL, AVERTISSEMENT, CRITIQUE)
     */
    public ReleveInfo(String codeCapteur, double derniereValeur, String unite,
            NiveauGravite niveauGravite) {
        this.codeCapteur = codeCapteur;
        this.derniereValeur = derniereValeur;
        this.unite = unite;
        this.niveauGravite = niveauGravite;
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getCodeCapteur() {
        return codeCapteur;
    }

    public void setCodeCapteur(String codeCapteur) {
        this.codeCapteur = codeCapteur;
    }

    public double getDerniereValeur() {
        return derniereValeur;
    }

    public void setDerniereValeur(double derniereValeur) {
        this.derniereValeur = derniereValeur;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public NiveauGravite getNiveauGravite() {
        return niveauGravite;
    }

    public void setNiveauGravite(NiveauGravite niveauGravite) {
        this.niveauGravite = niveauGravite;
    }

    @Override
    public String toString() {
        return "Capteur [" + codeCapteur + "] = " + derniereValeur + " " + unite
                + " | Niveau: " + niveauGravite;
    }
}
