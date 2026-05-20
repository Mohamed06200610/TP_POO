package TP_POO.src.smartfarming.model.historique;

import TP_POO.src.smartfarming.enums.TypeZone;

public class HistoriqueProductionAquacole extends HistoriqueProduction {

    private double poidsRecolte; // en kg
    private String especeRecoltee;

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

    @Override
    public void enregistrer() {
        System.out.println("Production aquacole enregistrée : " + especeRecoltee
                + " — " + poidsRecolte + " kg le " + getDate());
    }

    @Override
    public String getResume() {
        return "Aquacole: " + especeRecoltee + " | Récolte: " + poidsRecolte + " kg";
    }

    // ── Méthode spécifique ─────────────────────────────────

    public double getRendementBassin() {
        return poidsRecolte;
    }
}
