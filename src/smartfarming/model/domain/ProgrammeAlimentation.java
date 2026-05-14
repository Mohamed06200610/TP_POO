package TP_POO.src.smartfarming.model.domain;

/**
 * Classe ProgrammeAlimentation — Programme d'alimentation pour une zone.
 *
 * Rôle : Définit le type d'aliment, la quantité par repas et le nombre
 * de repas quotidiens pour les animaux d'une zone d'élevage
 * ou les espèces d'une zone aquacole.
 *
 * Concept POO : ENCAPSULATION — attributs privés, accès contrôlé.
 */
public class ProgrammeAlimentation {

    private String typeAliment;
    private double quantiteParRepas;
    private int nombreRepas;

    /**
     * Constructeur.
     *
     * @param typeAliment      type d'aliment (ex: "Foin", "Granulés")
     * @param quantiteParRepas quantité par repas en kg
     * @param nombreRepas      nombre de repas par jour
     */
    public ProgrammeAlimentation(String typeAliment, double quantiteParRepas, int nombreRepas) {
        this.typeAliment = typeAliment;
        this.quantiteParRepas = quantiteParRepas;
        this.nombreRepas = nombreRepas;
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getTypeAliment() {
        return typeAliment;
    }

    public void setTypeAliment(String typeAliment) {
        this.typeAliment = typeAliment;
    }

    public double getQuantiteParRepas() {
        return quantiteParRepas;
    }

    public void setQuantiteParRepas(double quantiteParRepas) {
        this.quantiteParRepas = quantiteParRepas;
    }

    public int getNombreRepas() {
        return nombreRepas;
    }

    public void setNombreRepas(int nombreRepas) {
        this.nombreRepas = nombreRepas;
    }

    /**
     * Calcule la quantité totale quotidienne.
     *
     * @return quantité totale = quantiteParRepas × nombreRepas
     */
    public double getQuantiteTotaleQuotidienne() {
        return quantiteParRepas * nombreRepas;
    }

    @Override
    public String toString() {
        return "Programme [" + typeAliment + "] " + quantiteParRepas + " kg × "
                + nombreRepas + " repas/jour";
    }
}
