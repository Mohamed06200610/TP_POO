package TP_POO.src.smartfarming.model.releves;

/**
 * Classe ReleveNumerique — Relevé à valeur numérique unique avec unité de
 * mesure.
 *
 * Rôle : Représente les mesures des capteurs environnementaux, sol,
 * biométriques
 * et eau. Stocke une valeur double et son unité (°C, %, mg/L, etc.).
 *
 * Concept POO : HÉRITAGE — spécialise Releve avec une valeur numérique.
 * POLYMORPHISME — implémente getDescription() de manière spécifique.
 */
public class ReleveNumerique extends Releve {

    private double valeur;
    private String unite;

    /**
     * Constructeur.
     *
     * @param codeCapteur code du capteur source
     * @param valeur      valeur numérique mesurée
     * @param unite       unité de mesure (ex: "°C", "%", "mg/L")
     */
    public ReleveNumerique(String codeCapteur, double valeur, String unite) {
        super(codeCapteur);
        this.valeur = valeur;
        this.unite = unite;
    }

    // ── Getters / Setters ──────────────────────────────────

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    // ── Méthode polymorphe ─────────────────────────────────

    /**
     * Retourne la description sous la forme "valeur unité".
     *
     * @return ex: "25.3 °C"
     */
    @Override
    public String getDescription() {
        return valeur + " " + unite;
    }
}
