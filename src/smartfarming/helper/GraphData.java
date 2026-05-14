package TP_POO.src.smartfarming.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import TP_POO.src.smartfarming.enums.NiveauGravite;

/**
 * Classe GraphData — Objet de transfert pour les données de graphiques.
 *
 * Rôle : Contient les séries temporelles de relevés avec leur niveau de gravité
 * pour le rendu graphique dans l'interface utilisateur.
 *
 * Concept POO : ENCAPSULATION — regroupe toutes les données nécessaires
 * au rendu d'un graphique d'évolution.
 */
public class GraphData {

    private String codeCapteur;
    private List<Date> pointsTemps;
    private List<Double> valeurs;
    private List<NiveauGravite> niveaux;

    /**
     * Constructeur.
     *
     * @param codeCapteur code unique du capteur
     */
    public GraphData(String codeCapteur) {
        this.codeCapteur = codeCapteur;
        this.pointsTemps = new ArrayList<>();
        this.valeurs = new ArrayList<>();
        this.niveaux = new ArrayList<>();
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getCodeCapteur() {
        return codeCapteur;
    }

    public void setCodeCapteur(String codeCapteur) {
        this.codeCapteur = codeCapteur;
    }

    public List<Date> getPointsTemps() {
        return pointsTemps;
    }

    public void setPointsTemps(List<Date> pointsTemps) {
        this.pointsTemps = pointsTemps;
    }

    public List<Double> getValeurs() {
        return valeurs;
    }

    public void setValeurs(List<Double> valeurs) {
        this.valeurs = valeurs;
    }

    public List<NiveauGravite> getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(List<NiveauGravite> niveaux) {
        this.niveaux = niveaux;
    }

    // ── Méthodes utilitaires ───────────────────────────────

    /**
     * Ajoute un point de données au graphique.
     *
     * @param temps  horodatage du relevé
     * @param valeur valeur mesurée
     * @param niveau niveau de gravité associé
     */
    public void ajouterPoint(Date temps, double valeur, NiveauGravite niveau) {
        this.pointsTemps.add(temps);
        this.valeurs.add(valeur);
        this.niveaux.add(niveau);
    }

    @Override
    public String toString() {
        return "GraphData [capteur=" + codeCapteur + ", points=" + valeurs.size() + "]";
    }
}
