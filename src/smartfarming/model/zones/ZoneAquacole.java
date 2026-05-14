package TP_POO.src.smartfarming.model.zones;

import java.util.ArrayList;
import java.util.List;
import TP_POO.src.smartfarming.enums.TypeZone;
import TP_POO.src.smartfarming.model.domain.Bassin;
import TP_POO.src.smartfarming.model.domain.EspeceAquacole;
import TP_POO.src.smartfarming.model.domain.ProgrammeAlimentation;
import TP_POO.src.smartfarming.model.historique.HistoriqueProductionAquacole;

/**
 * Classe ZoneAquacole — Zone dédiée à l'aquaculture (poissons, crustacés…).
 *
 * Rôle : Gère un bassin aquacole contenant des espèces aquatiques,
 *        des capteurs d'eau et un programme d'alimentation.
 *        Enregistre la production en poids de récolte (kg).
 *
 * Concept POO :
 *   HÉRITAGE — étend Zone avec des attributs spécifiques à l'aquaculture.
 *   POLYMORPHISME — enregistrerProduction() crée un HistoriqueProductionAquacole.
 *   COMPOSITION — contient un Bassin et un ProgrammeAlimentation.
 */
public class ZoneAquacole extends Zone {

    private Bassin bassin;
    private List<EspeceAquacole> especesAquacoles;
    private ProgrammeAlimentation programmeAlimentation;

    /**
     * Constructeur.
     *
     * @param code code unique de la zone
     * @param nom  nom de la zone
     */
    public ZoneAquacole(String code, String nom) {
        super(code, nom, TypeZone.AQUACOLE);
        this.bassin = new Bassin(100.0); // bassin par défaut
        this.especesAquacoles = new ArrayList<>();
        this.programmeAlimentation = null;
    }

    // ── Getters / Setters ──────────────────────────────────

    public Bassin getBassin() {
        return bassin;
    }

    public void setBassin(Bassin bassin) {
        this.bassin = bassin;
    }

    public List<EspeceAquacole> getEspecesAquacoles() {
        return especesAquacoles;
    }

    public void setEspecesAquacoles(List<EspeceAquacole> especesAquacoles) {
        this.especesAquacoles = especesAquacoles;
    }

    public ProgrammeAlimentation getProgrammeAlimentation() {
        return programmeAlimentation;
    }

    public void setProgrammeAlimentation(ProgrammeAlimentation programmeAlimentation) {
        this.programmeAlimentation = programmeAlimentation;
    }

    // ── Méthodes métier ────────────────────────────────────

    /**
     * Ajoute une espèce aquacole à la zone et au bassin.
     *
     * @param espece l'espèce à ajouter
     */
    public void ajouterEspece(EspeceAquacole espece) {
        this.especesAquacoles.add(espece);
        this.bassin.ajouterEspece(espece);
    }

    /**
     * Définit le programme d'alimentation de la zone aquacole.
     *
     * @param typeAliment     type d'aliment
     * @param quantiteParRepas quantité par repas en kg
     * @param nombreRepas     nombre de repas par jour
     */
    public void definirProgrammeAlimentation(String typeAliment, double quantiteParRepas,
                                              int nombreRepas) {
        this.programmeAlimentation = new ProgrammeAlimentation(
                typeAliment, quantiteParRepas, nombreRepas);
    }

    // ── Zone abstraite ─────────────────────────────────────

    /**
     * Retourne le nombre d'espèces aquacoles dans la zone.
     *
     * @return nombre d'espèces
     */
    @Override
    public int getNombreEntites() {
        return especesAquacoles.size();
    }

    /**
     * Enregistre la production aquacole.
     * Crée un HistoriqueProductionAquacole avec le poids de récolte
     * et l'espèce principale.
     */
    @Override
    public void enregistrerProduction() {
        double poids = calculerPoidsRecolte();
        String espece = getEspecePrincipale();

        HistoriqueProductionAquacole h = new HistoriqueProductionAquacole(
                getCode(), poids, espece);
        h.enregistrer();
        getHistoriques().add(h);
    }

    // ── Méthodes utilitaires privées ───────────────────────

    /**
     * Calcule le poids total de récolte (simulation).
     * Basé sur la quantité d'individus par espèce.
     *
     * @return poids en kg
     */
    private double calculerPoidsRecolte() {
        double total = 0.0;
        for (EspeceAquacole e : especesAquacoles) {
            // Simulation : poids moyen par individu × quantité
            total += e.getQuantite() * 0.5; // 0.5 kg en moyenne par individu
        }
        return total;
    }

    /**
     * Retourne le nom de l'espèce principale (la plus nombreuse).
     *
     * @return nom de l'espèce principale
     */
    private String getEspecePrincipale() {
        if (especesAquacoles.isEmpty()) {
            return "Aucune";
        }
        EspeceAquacole principale = especesAquacoles.get(0);
        for (EspeceAquacole e : especesAquacoles) {
            if (e.getQuantite() > principale.getQuantite()) {
                principale = e;
            }
        }
        return principale.getNom();
    }
}
