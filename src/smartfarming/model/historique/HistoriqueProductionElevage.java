package TP_POO.src.smartfarming.model.historique;

import TP_POO.src.smartfarming.enums.TypeZone;

/**
 * Classe HistoriqueProductionElevage — Historique de production pour les zones
 * d'élevage.
 *
 * Rôle : Enregistre la production laitière (litres) pour les ruminants
 * ou la production d'œufs (unités) pour la volaille.
 *
 * Concept POO : HÉRITAGE — spécialise HistoriqueProduction avec des métriques
 * d'élevage (rendementLaitier, productionOeufs, typeProduction).
 * POLYMORPHISME — enregistrer() et getResume() adaptés à l'élevage.
 */
public class HistoriqueProductionElevage extends HistoriqueProduction {

    private double rendementLaitier; // en litres
    private int productionOeufs; // en unités
    private String typeProduction; // "laitier" ou "oeufs"

    /**
     * Constructeur.
     *
     * @param codeZone       code de la zone d'élevage
     * @param typeProduction type de production ("laitier" ou "oeufs")
     */
    public HistoriqueProductionElevage(String codeZone, String typeProduction) {
        super(codeZone, TypeZone.ELEVAGE);
        this.typeProduction = typeProduction;
        this.rendementLaitier = 0.0;
        this.productionOeufs = 0;
    }

    // ── Getters / Setters ──────────────────────────────────

    public double getRendementLaitier() {
        return rendementLaitier;
    }

    public void setRendementLaitier(double rendementLaitier) {
        this.rendementLaitier = rendementLaitier;
    }

    public int getProductionOeufs() {
        return productionOeufs;
    }

    public void setProductionOeufs(int productionOeufs) {
        this.productionOeufs = productionOeufs;
    }

    public String getTypeProduction() {
        return typeProduction;
    }

    public void setTypeProduction(String typeProduction) {
        this.typeProduction = typeProduction;
    }

    // ── Méthodes polymorphes ───────────────────────────────

    /**
     * Enregistre la production d'élevage.
     */
    @Override
    public void enregistrer() {
        if ("laitier".equals(typeProduction)) {
            System.out.println("Production laitière enregistrée : "
                    + rendementLaitier + " litres le " + getDate());
        } else {
            System.out.println("Production d'œufs enregistrée : "
                    + productionOeufs + " unités le " + getDate());
        }
    }

    /**
     * Résumé formaté de la production d'élevage.
     *
     * @return ex: "Elevage: laitier | Total: 150.0 L ou unités"
     */
    @Override
    public String getResume() {
        if ("laitier".equals(typeProduction)) {
            return "Elevage: " + typeProduction + " | Total: " + rendementLaitier + " L ou unités";
        } else {
            return "Elevage: " + typeProduction + " | Total: " + productionOeufs + " L ou unités";
        }
    }

    // ── Méthode spécifique ─────────────────────────────────

    /**
     * Retourne la production totale sous forme numérique.
     * Litres pour le laitier, unités pour les œufs.
     *
     * @return production totale
     */
    public double getTotalProduction() {
        if ("laitier".equals(typeProduction)) {
            return rendementLaitier;
        } else {
            return (double) productionOeufs;
        }
    }
}
