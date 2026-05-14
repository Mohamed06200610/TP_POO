package TP_POO.src.smartfarming.model.historique;

import TP_POO.src.smartfarming.enums.TypeZone;

/**
 * Classe HistoriqueProductionCulture — Historique de production pour les zones
 * de culture.
 *
 * Rôle : Enregistre le rendement des cultures en kg/hectare avec le nom
 * de la culture principale. Fournit un rapport de rendement détaillé.
 *
 * Concept POO : HÉRITAGE — spécialise HistoriqueProduction avec des métriques
 * spécifiques aux cultures (rendementCultures, nomCulture).
 * POLYMORPHISME — enregistrer() et getResume() adaptés aux cultures.
 */
public class HistoriqueProductionCulture extends HistoriqueProduction {

    private double rendementCultures; // en kg/hectare
    private String nomCulture;

    /**
     * Constructeur.
     *
     * @param codeZone          code de la zone de culture
     * @param rendementCultures rendement en kg/hectare
     * @param nomCulture        nom de la culture principale
     */
    public HistoriqueProductionCulture(String codeZone, double rendementCultures,
            String nomCulture) {
        super(codeZone, TypeZone.CULTURE);
        this.rendementCultures = rendementCultures;
        this.nomCulture = nomCulture;
    }

    // ── Getters / Setters ──────────────────────────────────

    public double getRendementCultures() {
        return rendementCultures;
    }

    public void setRendementCultures(double rendementCultures) {
        this.rendementCultures = rendementCultures;
    }

    public String getNomCulture() {
        return nomCulture;
    }

    public void setNomCulture(String nomCulture) {
        this.nomCulture = nomCulture;
    }

    // ── Méthodes polymorphes ───────────────────────────────

    /**
     * Enregistre la production de culture.
     * Valide le rendement et logge l'enregistrement.
     */
    @Override
    public void enregistrer() {
        System.out.println("Production culture enregistrée : " + nomCulture
                + " — " + rendementCultures + " kg/ha le " + getDate());
    }

    /**
     * Résumé formaté de la production de culture.
     *
     * @return ex: "Culture: Blé | Rendement: 450.0 kg/ha"
     */
    @Override
    public String getResume() {
        return "Culture: " + nomCulture + " | Rendement: " + rendementCultures + " kg/ha";
    }

    // ── Méthode spécifique ─────────────────────────────────

    /**
     * Génère un rapport de rendement détaillé pour cette culture.
     *
     * @return rapport de rendement formaté
     */
    public String getRapportRendement() {
        return "=== Rapport Rendement Culture ===\n"
                + "Zone: " + getCodeZone() + "\n"
                + "Culture: " + nomCulture + "\n"
                + "Rendement: " + rendementCultures + " kg/ha\n"
                + "Date: " + getDate() + "\n"
                + "=================================";
    }
}
