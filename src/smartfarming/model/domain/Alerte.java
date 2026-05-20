package TP_POO.src.smartfarming.model.domain;

import java.util.Date;
import TP_POO.src.smartfarming.enums.NiveauGravite;
import TP_POO.src.smartfarming.enums.StatutAlerte;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.model.capteurs.Capteur;
import TP_POO.src.smartfarming.model.releves.Releve;

public class Alerte {

    private String id;
    private Releve releve;
    private Capteur capteur;
    private NiveauGravite niveauGravite;
    private StatutAlerte statut;
    private Date horodatage;

    private static int compteur = 0;

    public Alerte(Releve releve, Capteur capteur, NiveauGravite niveauGravite) {
        this.id = "ALR-" + (++compteur);
        this.releve = releve;
        this.capteur = capteur;
        this.niveauGravite = niveauGravite;
        this.statut = StatutAlerte.ACTIVE;
        this.horodatage = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Releve getReleve() {
        return releve;
    }

    public void setReleve(Releve releve) {
        this.releve = releve;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }

    public NiveauGravite getNiveauGravite() {
        return niveauGravite;
    }

    public void setNiveauGravite(NiveauGravite niveauGravite) {
        this.niveauGravite = niveauGravite;
    }

    public StatutAlerte getStatut() {
        return statut;
    }

    public void setStatut(StatutAlerte statut) {
        this.statut = statut;
    }

    public Date getHorodatage() {
        return horodatage;
    }

    public void setHorodatage(Date horodatage) {
        this.horodatage = horodatage;
    }

    public TypeCapteur getTypeCapteur() {
        return capteur.getTypeCapteur();
    }

    public String getCodeZone() {
        return capteur.getCodeZone();
    }

    @Override
    public String toString() {
        return "Alerte [" + id + "] " + niveauGravite + " — Capteur: "
                + capteur.getCode() + " — " + statut;
    }
}
