package TP_POO.src.smartfarming.model.capteurs;

import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.manager.AlerteManager;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;

/**
 * Classe CapteurSol — Capteur mesurant les propriétés du sol.
 *
 * Rôle : Mesure le pH, l'humidité du sol et la teneur en azote dans les zones
 * de culture. Envoie des ReleveNumerique.
 *
 * Concept POO :
 * HÉRITAGE — étend Capteur avec une logique de mesure pédologique.
 * POLYMORPHISME — envoyerReleve() retourne un ReleveNumerique simulé.
 */
public class CapteurSol extends Capteur {

    private String grandeurMesuree; // "pH", "humidite", "azote"

    /**
     * Constructeur.
     *
     * @param codeZone      code de la zone associée
     * @param seuilMin      seuil minimum
     * @param seuilMax      seuil maximum
     * @param alerteManager gestionnaire d'alertes
     */
    public CapteurSol(String codeZone, double seuilMin, double seuilMax,
            AlerteManager alerteManager) {
        super(TypeCapteur.SOL, codeZone, seuilMin, seuilMax, alerteManager);
        this.grandeurMesuree = "pH";
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
     * Envoie un relevé de sol (pH, humidité, azote).
     * Garde : bloqué si le capteur n'est pas ACTIF.
     *
     * @return le relevé numérique, ou null si capteur inactif
     */
    @Override
    public Releve envoyerReleve() {
        if (getStatutCapteur() != StatutCapteur.ACTIF) {
            return null;
        }

        double valeur;
        String unite;

        switch (grandeurMesuree) {
            case "humidite":
                valeur = 20.0 + Math.random() * 60.0; // 20-80%
                unite = "%";
                break;
            case "azote":
                valeur = 10.0 + Math.random() * 90.0; // 10-100 mg/kg
                unite = "mg/kg";
                break;
            case "pH":
            default:
                valeur = 4.0 + Math.random() * 5.0; // 4.0-9.0
                unite = "pH";
                break;
        }

        ReleveNumerique releve = new ReleveNumerique(getCode(), valeur, unite);
        getHistorique().add(releve);

        verifierSeuils(releve, valeur);

        return releve;
    }
}
