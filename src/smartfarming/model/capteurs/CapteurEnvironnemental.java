package TP_POO.src.smartfarming.model.capteurs;

import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.manager.AlerteManager;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;

public class CapteurEnvironnemental extends Capteur {

    private String grandeurMesuree; // "temperature", "humidite", "pluviometrie"

    public CapteurEnvironnemental(String codeZone, double seuilMin, double seuilMax,
            AlerteManager alerteManager) {
        this(codeZone, seuilMin, seuilMax, alerteManager, "temperature");
    }

    public CapteurEnvironnemental(String codeZone, double seuilMin, double seuilMax,
            AlerteManager alerteManager, String grandeur) {
        super(TypeCapteur.ENVIRONNEMENTAL, codeZone, seuilMin, seuilMax, alerteManager);
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

        switch (grandeurMesuree.toLowerCase()) {
            case "humidite":
                valeur = 40.0 + Math.random() * 60.0;
                unite = "%";
                break;
            case "pluviometrie":
                valeur = Math.random() * 50.0;
                unite = "mm";
                break;
            case "temperature":
            default:
                valeur = 15.0 + Math.random() * 25.0;
                unite = "°C";
                break;
        }

        ReleveNumerique releve = new ReleveNumerique(getCode(), valeur, unite);
        getHistorique().add(releve);
        verifierSeuils(releve, valeur);

        return releve;
    }
}
