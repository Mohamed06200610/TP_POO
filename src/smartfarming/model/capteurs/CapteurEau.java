package TP_POO.src.smartfarming.model.capteurs;

import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.manager.AlerteManager;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;

public class CapteurEau extends Capteur {

    private String grandeurMesuree; // "temperature", "O2", "pH"

    public CapteurEau(String codeZone, double seuilMin, double seuilMax,
            AlerteManager alerteManager) {
        this(codeZone, seuilMin, seuilMax, alerteManager, "temperature");
    }

    public CapteurEau(String codeZone, double seuilMin, double seuilMax,
            AlerteManager alerteManager, String grandeur) {
        super(TypeCapteur.EAU, codeZone, seuilMin, seuilMax, alerteManager);
        this.grandeurMesuree = grandeur;
    }

    public String getGrandeurMesuree() {
        return grandeurMesuree;
    }

    public void setGrandeurMesuree(String grandeurMesuree) {
        this.grandeurMesuree = grandeurMesuree;
    }

    @Override
    public Releve envoyerReleve() {
        if (getStatutCapteur() != StatutCapteur.ACTIF) {
            return null;
        }

        double valeur;
        String unite;

        switch (grandeurMesuree) {
            case "O2":
                valeur = 5.0 + Math.random() * 10.0;
                unite = "mg/L";
                break;
            case "pH":
                valeur = 6.0 + Math.random() * 3.0;
                unite = "pH";
                break;
            case "temperature":
            default:
                valeur = 18.0 + Math.random() * 14.0;
                unite = "°C";
                break;
        }

        ReleveNumerique releve = new ReleveNumerique(getCode(), valeur, unite);
        getHistorique().add(releve);
        verifierSeuils(releve, valeur);

        return releve;
    }
}
