package TP_POO.src.smartfarming.model.historique;

import TP_POO.src.smartfarming.enums.TypeZone;

/**
 * Classe HistoriqueProductionAquacole — Historique de production pour les zones
 * aquacoles.
 *
 * Rôle : Enregistre le poids de récolte (kg) et l'espèce récoltée
 * dans un bassin aquacole.
 *
 * Concept POO : HÉRITAGE — spécialise HistoriqueProduction avec des métriques
 * aquacoles (poidsRecolte, especeRecoltee).
 * POLYMORPHISME — enregistrer() et getResume() adaptés à l'aquaculture.
 */
public class HistoriqueProductionAquacole extends HistoriqueProduction {

    private double poidsRecolte; // en kg
    private String especeRecoltee;

    /**
     * Constructeur.
     *
     * @param codeZone       code de la zone aquacole
     * @param poidsRecolte   poids récolté en kg
     * @param especeRecoltee nom de l'espèce récoltée
     */
    public HistoriqueProductionAquacole(String codeZone, double poidsRecolte,
            String especeRecoltee) {
        super(codeZone, TypeZone.AQUACOLE);
        this.poidsRecolte = poidsRecolte;
        this.especeRecoltee = especeRecoltee;
    }

    // ── Getters / Setters ──────────────────────────────────

    public double getPoidsRecolte() {
        return poidsRecolte;
    }

    public void setPoidsRecolte(double poidsRecolte) {
        this.poidsRecolte = poidsRecolte;
    }

    public String getEspeceRecoltee() {
        return especeRecoltee;
    }

    public void setEspeceRecoltee(String especeRecoltee) {
        this.especeRecoltee = especeRecoltee;
    }

    // ── Méthodes polymorphes ───────────────────────────────

    /**
     * Enregistre la production aquacole.
     */
    @Override
    public void enregistrer() {
        System.out.println("Production aquacole enregistrée : " + especeRecoltee
                + " — " + poidsRecolte + " kg le " + getDate());
    }

    /**
     * Résumé formaté de la production aquacole.
     *
     * @return ex: "Aquacole: Tilapia | Récolte: 250.0 kg"
     */
    @Override
    public String getResume() {
        return "Aquacole: " + especeRecoltee + " | Récolte: " + poidsRecolte + " kg";
    }

    // ── Méthode spécifique ─────────────────────────────────

    /**
     * Retourne le rendement du bassin en kg.
     *
     * @return poids de la récolte
     */
    public double getRendementBassin() {
        return poidsRecolte;
    }
}
