package TP_POO.src.smartfarming.model.domain;

import java.util.ArrayList;
import java.util.List;
import TP_POO.src.smartfarming.enums.EtatSante;
import TP_POO.src.smartfarming.enums.TypeAnimal;
import TP_POO.src.smartfarming.model.capteurs.CapteurBiometrique;
import TP_POO.src.smartfarming.model.capteurs.CapteurGPS;

/**
 * Classe Animal — Représente un animal dans une zone d'élevage.
 *
 * Rôle : Modélise un animal avec son espèce, son âge, son poids, son état
 *        de santé, ses capteurs biométriques et son collier GPS optionnel.
 *
 * Concept POO : ENCAPSULATION — attributs privés.
 *               COMPOSITION — contient des capteurs biométriques et un collier GPS.
 */
public class Animal {

    private String id;
    private TypeAnimal espece;
    private int age;
    private double poids;
    private EtatSante etatSante;
    private List<CapteurBiometrique> capteursBiometriques;
    private CapteurGPS collierGPS;
    private List<String> historiqueEvenements;

    private static int compteur = 0;

    public Animal(TypeAnimal espece, int age, double poids, EtatSante etatSante) {
        this.id = "ANI-" + (++compteur);
        this.espece = espece;
        this.age = age;
        this.poids = poids;
        this.etatSante = etatSante;
        this.capteursBiometriques = new ArrayList<>();
        this.collierGPS = null;
        this.historiqueEvenements = new ArrayList<>();
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getId() { return id; }
    public TypeAnimal getEspece() { return espece; }
    public void setEspece(TypeAnimal espece) { this.espece = espece; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public double getPoids() { return poids; }
    public void setPoids(double poids) { this.poids = poids; }
    public EtatSante getEtatSante() { return etatSante; }
    public void setEtatSante(EtatSante etatSante) { this.etatSante = etatSante; }
    public List<CapteurBiometrique> getCapteursBiometriques() { return capteursBiometriques; }
    public CapteurGPS getCollierGPS() { return collierGPS; }
    public void setCollierGPS(CapteurGPS collierGPS) { this.collierGPS = collierGPS; }

    // ── Méthodes métier ────────────────────────────────────

    public void ajouterCapteurBiometrique(CapteurBiometrique capteur) {
        this.capteursBiometriques.add(capteur);
    }

    public void ajouterEvenement(String evenement) {
        this.historiqueEvenements.add(evenement);
    }

    @Override
    public String toString() {
        return "Animal [" + id + "] " + espece + " — " + poids + "kg — " + etatSante;
    }
}
