package TP_POO.src.smartfarming.model.domain;

/**
 * Classe EspeceAquacole — Représente une espèce aquatique dans un bassin.
 *
 * Rôle : Modélise une espèce aquatique avec son nom, sa quantité
 * et son programme d'alimentation associé.
 *
 * Concept POO : ENCAPSULATION — attributs privés avec getters/setters.
 * COMPOSITION — contient un ProgrammeAlimentation.
 */
public class EspeceAquacole {

    private String nom;
    private int quantite;
    private ProgrammeAlimentation programmeAlimentation;

    /**
     * Constructeur.
     *
     * @param nom                   nom de l'espèce (ex: "Tilapia", "Crevettes")
     * @param quantite              nombre d'individus
     * @param programmeAlimentation programme d'alimentation de l'espèce
     */
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
