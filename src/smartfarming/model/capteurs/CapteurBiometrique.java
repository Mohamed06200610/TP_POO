package TP_POO.src.smartfarming.model.capteurs;

import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.manager.AlerteManager;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;

/**
 * Classe CapteurBiometrique — Capteur mesurant les données biométriques d'un
 * animal.
 *
 * Rôle : Mesure la température corporelle et le niveau d'activité (pas/minute)
 * d'un animal dans une zone d'élevage. Envoie des ReleveNumerique.
 *
 * Concept POO :
 * HÉRITAGE — étend Capteur avec une logique de mesure biométrique.
 * POLYMORPHISME — envoyerReleve() spécifique aux données animales.
 */
public class CapteurBiometrique extends Capteur {

    private String grandeurMesuree; // "temperature", "activite"

    /**
     * Constructeur.
     *
     * @param codeZone      code de la zone associée
     * @param seuilMin      seuil minimum
     * @param seuilMax      seuil maximum
     * @param alerteManager gestionnaire d'alertes
     */
    public CapteurBiometrique(String codeZone, double seuilMin, double seuilMax,
            AlerteManager alerteManager) {
        super(TypeCapteur.BIOMETRIQUE, codeZone, seuilMin, seuilMax, alerteManager);
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
     * Envoie un relevé biométrique (température corporelle, activité).
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
            case "activite":
                valeur = 20.0 + Math.random() * 80.0; // 20-100 pas/min
                unite = "pas/min";
                break;
            case "temperature":
            default:
                valeur = 37.0 + Math.random() * 4.0; // 37-41°C
                unite = "°C";
                break;
        }

        ReleveNumerique releve = new ReleveNumerique(getCode(), valeur, unite);
        getHistorique().add(releve);

        verifierSeuils(releve, valeur);

        return releve;
    }
}
