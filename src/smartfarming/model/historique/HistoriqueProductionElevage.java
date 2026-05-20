package TP_POO.src.smartfarming.model.historique;

import TP_POO.src.smartfarming.enums.TypeZone;

public class HistoriqueProductionElevage extends HistoriqueProduction {

    private double rendementLaitier; // en litres
    private int productionOeufs; // en unités
    private String typeProduction; // "laitier" ou "oeufs"

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

    @Override
    public String getResume() {
        if ("laitier".equals(typeProduction)) {
            return "Elevage: " + typeProduction + " | Total: " + rendementLaitier + " L ou unités";
        } else {
            return "Elevage: " + typeProduction + " | Total: " + productionOeufs + " L ou unités";
        }
    }

    public double getTotalProduction() {
        if ("laitier".equals(typeProduction)) {
            return rendementLaitier;
        } else {
            return (double) productionOeufs;
        }
    }
}
