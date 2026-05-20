package TP_POO.src.smartfarming.model.domain;

import java.util.ArrayList;
import java.util.List;
import TP_POO.src.smartfarming.model.capteurs.CapteurEau;

public class Bassin {

    private String id;
    private double volume; // en m³
    private List<CapteurEau> capteursEau;
    private List<EspeceAquacole> especesAquacoles;

    private static int compteur = 0;

    public Bassin(double volume) {
        this.id = "BAS-" + (++compteur);
        this.volume = volume;
        this.capteursEau = new ArrayList<>();
        this.especesAquacoles = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public List<CapteurEau> getCapteursEau() {
        return capteursEau;
    }

    public void setCapteursEau(List<CapteurEau> capteursEau) {
        this.capteursEau = capteursEau;
    }

    public List<EspeceAquacole> getEspecesAquacoles() {
        return especesAquacoles;
    }

    public void setEspecesAquacoles(List<EspeceAquacole> especesAquacoles) {
        this.especesAquacoles = especesAquacoles;
    }

    public void ajouterCapteurEau(CapteurEau capteur) {
        this.capteursEau.add(capteur);
    }

    public void ajouterEspece(EspeceAquacole espece) {
        this.especesAquacoles.add(espece);
    }

    public int getNombreEspeces() {
        return especesAquacoles.size();
    }

    @Override
    public String toString() {
        return "Bassin [" + id + "] Volume: " + volume + " m³ — Espèces: "
                + especesAquacoles.size();
    }
}
