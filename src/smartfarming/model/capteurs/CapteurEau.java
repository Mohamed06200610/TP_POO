package TP_POO.src.smartfarming.model.capteurs;

import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.manager.AlerteManager;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;

/**
 * Classe CapteurEau — Capteur mesurant les paramètres de l'eau dans un bassin.
 *
 * Rôle : Mesure la température de l'eau, l'oxygène dissous (O2) et le pH
 * dans les zones aquacoles. Envoie des ReleveNumerique.
 *
 * Concept POO :
 * HÉRITAGE — étend Capteur avec une logique de mesure aquatique.
 * POLYMORPHISME — envoyerReleve() spécifique aux paramètres de l'eau.
 */
public class CapteurEau extends Capteur {

    private String grandeurMesuree; // "temperature", "O2", "pH"

    /**
     * Constructeur.
     *
     * @param codeZone      code de la zone associée
     * @param seuilMin      seuil minimum
     * @param seuilMax      seuil maximum
     * @param alerteManager gestionnaire d'alertes
     */
    public CapteurEau(String codeZone, double seuilMin, double seuilMax,
            AlerteManager alerteManager) {
        super(TypeCapteur.EAU, codeZone, seuilMin, seuilMax, alerteManager);
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
     * Envoie un relevé de paramètres d'eau (température, O2, pH).
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
            case "O2":
                valeur = 5.0 + Math.random() * 10.0; // 5-15 mg/L
                unite = "mg/L";
                break;
            case "pH":
                valeur = 6.0 + Math.random() * 3.0; // 6.0-9.0
                unite = "pH";
                break;
            case "temperature":
            default:
                valeur = 18.0 + Math.random() * 14.0; // 18-32°C
                unite = "°C";
                break;
        }

        ReleveNumerique releve = new ReleveNumerique(getCode(), valeur, unite);
        getHistorique().add(releve);

        verifierSeuils(releve, valeur);

        return releve;
    }
}
