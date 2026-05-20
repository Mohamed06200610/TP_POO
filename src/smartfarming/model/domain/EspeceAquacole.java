package TP_POO.src.smartfarming.model.domain;

public class EspeceAquacole {

    private String nom;
    private int quantite;
    private ProgrammeAlimentation programmeAlimentation;

    public EspeceAquacole(String nom, int quantite, ProgrammeAlimentation programmeAlimentation) {
        this.nom = nom;
        this.quantite = quantite;
        this.programmeAlimentation = programmeAlimentation;
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public ProgrammeAlimentation getProgrammeAlimentation() {
        return programmeAlimentation;
    }

    public void setProgrammeAlimentation(ProgrammeAlimentation programmeAlimentation) {
        this.programmeAlimentation = programmeAlimentation;
    }

    @Override
    public String toString() {
        return "Espèce [" + nom + "] — Quantité: " + quantite;
    }
}
