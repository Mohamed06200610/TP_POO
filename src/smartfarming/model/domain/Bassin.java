package TP_POO.src.smartfarming.model.domain;

import java.util.ArrayList;
import java.util.List;
import TP_POO.src.smartfarming.model.capteurs.CapteurEau;

/**
 * Classe Bassin — Représente un bassin aquacole contenant des espèces et des
 * capteurs eau.
 *
 * Rôle : Modélise un bassin physique avec son volume, ses capteurs d'eau
 * et les espèces aquatiques qu'il héberge.
 *
 * Concept POO : ENCAPSULATION — attributs privés.
 * COMPOSITION — contient des CapteurEau et des EspeceAquacole.
 */
public class Bassin {

    private String id;
    private double volume; // en m³
    private List<CapteurEau> capteursEau;
    private List<EspeceAquacole> especesAquacoles;

    private static int compteur = 0;

    /**
     * Constructeur.
     *
     * @param volume volume du bassin en m³
     */
    public Bassin(double volume) {
        this.id = "BAS-" + (++compteur);
        this.volume = volume;
        this.capteursEau = new ArrayList<>();
        this.especesAquacoles = new ArrayList<>();
    }

    // ── Getters / Setters ──────────────────────────────────

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

    // ── Méthodes métier ────────────────────────────────────

    /**
     * Ajoute un capteur d'eau au bassin.
     *
     * @param capteur le capteur d'eau à ajouter
     */
    public void ajouterCapteurEau(CapteurEau capteur) {
        this.capteursEau.add(capteur);
    }

    /**
     * Ajoute une espèce aquacole au bassin.
     *
     * @param espece l'espèce à ajouter
     */
    public void ajouterEspece(EspeceAquacole espece) {
        this.especesAquacoles.add(espece);
    }

    /**
     * Retourne le nombre total d'espèces dans le bassin.
     *
     * @return nombre d'espèces
     */
    public int getNombreEspeces() {
        return especesAquacoles.size();
    }

    @Override
    public String toString() {
        return "Bassin [" + id + "] Volume: " + volume + " m³ — Espèces: "
                + especesAquacoles.size();
    }
}
