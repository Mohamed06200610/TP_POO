package TP_POO.src.smartfarming.model.capteurs;

import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.manager.AlerteManager;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;

/**
 * Classe CapteurEnvironnemental — Capteur mesurant les conditions environnementales.
 *
 * Rôle : Mesure la température, l'humidité et la pluviométrie dans les zones
 *        de culture. Envoie des ReleveNumerique.
 *
 * Concept POO :
 *   HÉRITAGE — étend Capteur avec une logique de mesure environnementale.
 *   POLYMORPHISME — envoyerReleve() retourne un ReleveNumerique simulé.
 */
public class CapteurEnvironnemental extends Capteur {

    private String grandeurMesuree; // "temperature", "humidite", "pluviometrie"

    /**
     * Constructeur.
     *
     * @param codeZone      code de la zone associée
     * @param seuilMin      seuil minimum
     * @param seuilMax      seuil maximum
     * @param alerteManager gestionnaire d'alertes
     */
    public CapteurEnvironnemental(String codeZone, double seuilMin, double seuilMax,
                                   AlerteManager alerteManager) {
        super(TypeCapteur.ENVIRONNEMENTAL, codeZone, seuilMin, seuilMax, alerteManager);
        this.grandeurMesuree = "temperature";
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getGrandeurMesuree() {
        return grandeurMesuree;
    }

    public void setGrandeurMesuree(String grandeurMesuree) {
        this.grandeurMesuree = grandeurMesuree;
    }

    // ── Méthode polymorphe ─────────────────────────────────

    /**
     * Envoie un relevé environnemental (température, humidité, pluviométrie).
     * Garde : bloqué si le capteur n'est pas ACTIF.
     *
     * @return le relevé numérique, ou null si capteur inactif
     */
    @Override
    public Releve envoyerReleve() {
        if (getStatutCapteur() != StatutCapteur.ACTIF) {
            return null; // Bloqué — capteur suspendu ou défaillant
        }

        // Simulation de mesure environnementale
        double valeur;
        String unite;

        switch (grandeurMesuree) {
            case "humidite":
                valeur = 40.0 + Math.random() * 60.0; // 40-100%
                unite = "%";
                break;
            case "pluviometrie":
                valeur = Math.random() * 50.0; // 0-50mm
                unite = "mm";
                break;
            case "temperature":
            default:
                valeur = 15.0 + Math.random() * 25.0; // 15-40°C
                unite = "°C";
                break;
        }

        ReleveNumerique releve = new ReleveNumerique(getCode(), valeur, unite);
        getHistorique().add(releve);

        // Vérification des seuils → alerte automatique si dépassement
        verifierSeuils(releve, valeur);

        return releve;
    }
}
