package TP_POO.src.smartfarming.interfaces;

import java.util.Date;
import TP_POO.src.smartfarming.helper.GraphData;

/**
 * Interface IVisualisable — Contrat pour les éléments capables de produire
 * des visualisations graphiques et des tableaux de bord.
 *
 * Rôle : Permet aux capteurs de fournir des données de visualisation
 * (graphiques d'évolution, tableau de bord des relevés).
 *
 * Concept POO : INTERFACE — découple la logique de visualisation de
 * l'implémentation concrète des capteurs.
 */
public interface IVisualisable {

    /**
     * Génère les données pour un graphique d'évolution des relevés.
     *
     * @param codeCapteur le code du capteur
     * @param dateDebut   date de début de la période
     * @param dateFin     date de fin de la période
     * @return les données du graphique (GraphData)
     */
    GraphData afficherGraphiquesEvolution(String codeCapteur, Date dateDebut, Date dateFin);

    /**
     * Affiche le tableau de bord synthétique du capteur.
     */
    void afficherTableauBord();
}
