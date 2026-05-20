package TP_POO.src.smartfarming.model.historique;

import TP_POO.src.smartfarming.enums.TypeZone;

public class HistoriqueProductionCulture extends HistoriqueProduction {

    private double rendementCultures; // en kg/hectare
    private String nomCulture;

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

    @Override
    public void enregistrer() {
        System.out.println("Production culture enregistrée : " + nomCulture
                + " — " + rendementCultures + " kg/ha le " + getDate());
    }

    @Override
    public String getResume() {
        return "Culture: " + nomCulture + " | Rendement: " + rendementCultures + " kg/ha";
    }

    public String getRapportRendement() {
        return "=== Rapport Rendement Culture ===\n"
                + "Zone: " + getCodeZone() + "\n"
                + "Culture: " + nomCulture + "\n"
                + "Rendement: " + rendementCultures + " kg/ha\n"
                + "Date: " + getDate() + "\n"
                + "=================================";
    }
}
