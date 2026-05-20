package TP_POO.src.smartfarming.model.capteurs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import TP_POO.src.smartfarming.enums.NiveauGravite;
import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.helper.GraphData;
import TP_POO.src.smartfarming.interfaces.ISuspendable;
import TP_POO.src.smartfarming.interfaces.IVisualisable;
import TP_POO.src.smartfarming.manager.AlerteManager;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;

/**
 * Classe abstraite Capteur — Base de tous les capteurs du système.
 *
 * Rôle : Classe de base pour les 5 types de capteurs. Gère le code unique,
 * le statut, les seuils, l'historique des relevés et la référence
 * à la zone. Implémente ISuspendable et IVisualisable.
 *
 * Concept POO :
 * ABSTRACTION — déclare envoyerReleve() abstrait.
 * INTERFACE — implémente ISuspendable et IVisualisable.
 * ENCAPSULATION — attributs privés, garde métier dans envoyerReleve().
 *
 * Règle métier clé :
 * envoyerReleve() BLOQUÉ si statut != ACTIF (retourne null).
 * Après chaque relevé, vérification automatique des seuils → alerte.
 */
public abstract class Capteur implements ISuspendable, IVisualisable {

    private String code;
    private TypeCapteur typeCapteur;
    private StatutCapteur statut;
    private String codeZone;
    private double seuilMin;
    private double seuilMax;
    private List<Releve> historique;
    private AlerteManager alerteManager;

    private static int compteur = 0;

    /**
     * Constructeur.
     *
     * @param typeCapteur   type de capteur
     * @param codeZone      code de la zone associée
     * @param seuilMin      seuil minimum acceptable
     * @param seuilMax      seuil maximum acceptable
     * @param alerteManager gestionnaire d'alertes pour les dépassements
     */
    public Capteur(TypeCapteur typeCapteur, String codeZone,
            double seuilMin, double seuilMax, AlerteManager alerteManager) {
        this.code = "CAP-" + (++compteur);
        this.typeCapteur = typeCapteur;
        this.statut = StatutCapteur.ACTIF;
        this.codeZone = codeZone;
        this.seuilMin = seuilMin;
        this.seuilMax = seuilMax;
        this.historique = new ArrayList<>();
        this.alerteManager = alerteManager;
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TypeCapteur getTypeCapteur() {
        return typeCapteur;
    }

    public void setTypeCapteur(TypeCapteur typeCapteur) {
        this.typeCapteur = typeCapteur;
    }

    public StatutCapteur getStatutCapteur() {
        return statut;
    }

    public void setStatutCapteur(StatutCapteur statut) {
        this.statut = statut;
    }

    public String getCodeZone() {
        return codeZone;
    }

    public void setCodeZone(String codeZone) {
        this.codeZone = codeZone;
    }

    public double getSeuilMin() {
        return seuilMin;
    }

    public void setSeuilMin(double seuilMin) {
        this.seuilMin = seuilMin;
    }

    public double getSeuilMax() {
        return seuilMax;
    }

    public void setSeuilMax(double seuilMax) {
        this.seuilMax = seuilMax;
    }

    public List<Releve> getHistorique() {
        return historique;
    }

    public void setHistorique(List<Releve> historique) {
        this.historique = historique;
    }

    public AlerteManager getAlerteManager() {
        return alerteManager;
    }

    public void setAlerteManager(AlerteManager alerteManager) {
        this.alerteManager = alerteManager;
    }

    // ── ISuspendable ───────────────────────────────────────

    /**
     * Active le capteur → les relevés peuvent être envoyés.
     */
    @Override
    public void activer() {
        this.statut = StatutCapteur.ACTIF;
    }

    /**
     * Suspend le capteur → les relevés sont bloqués.
     */
    @Override
    public void suspendre() {
        this.statut = StatutCapteur.SUSPENDU;
    }

    /**
     * Retourne le statut sous forme de chaîne.
     *
     * @return "ACTIF", "DEFAILLANT" ou "SUSPENDU"
     */
    @Override
    public String getStatut() {
        return statut.name();
    }

    // ── IVisualisable ──────────────────────────────────────

    /**
     * Génère les données de graphique d'évolution pour ce capteur.
     *
     * @param codeCapteur code du capteur
     * @param dateDebut   date de début
     * @param dateFin     date de fin
     * @return données du graphique
     */
    @Override
    public GraphData afficherGraphiquesEvolution(String codeCapteur,
            Date dateDebut, Date dateFin) {
        GraphData graphData = new GraphData(this.code);
        for (Releve r : historique) {
            if (!r.getHorodatage().before(dateDebut) && !r.getHorodatage().after(dateFin)) {
                if (r instanceof ReleveNumerique) {
                    ReleveNumerique rn = (ReleveNumerique) r;
                    NiveauGravite niveau = determinerNiveau(rn.getValeur());
                    graphData.ajouterPoint(r.getHorodatage(), rn.getValeur(), niveau);
                }
            }
        }
        return graphData;
    }

    /**
     * Affiche le tableau de bord synthétique du capteur.
     */
    @Override
    public void afficherTableauBord() {
        System.out.println("══ Capteur " + code + " (" + typeCapteur + ") ══");
        System.out.println("  Statut   : " + statut);
        System.out.println("  Zone     : " + codeZone);
        System.out.println("  Seuils   : [" + seuilMin + " — " + seuilMax + "]");
        System.out.println("  Relevés  : " + historique.size());
        if (!historique.isEmpty()) {
            Releve dernier = historique.get(historique.size() - 1);
            System.out.println("  Dernier  : " + dernier.getDescription());
        }
    }

    public void changerStatut(StatutCapteur statut) {
        this.statut = statut;
    }

    public abstract Releve envoyerReleve();

    protected void verifierSeuils(Releve releve, double valeur) {
        if (valeur < seuilMin || valeur > seuilMax) {
            if (alerteManager != null) {
                alerteManager.declencherAlerte(releve, this);
            }
        }
    }

    public NiveauGravite determinerNiveau(double valeur) {
        if (valeur >= seuilMin && valeur <= seuilMax) {
            return NiveauGravite.NORMAL;
        }
        double range = seuilMax - seuilMin;
        double deviation;
        if (valeur < seuilMin) {
            deviation = Math.abs(valeur - seuilMin);
        } else {
            deviation = Math.abs(valeur - seuilMax);
        }
        if (deviation <= 0.10 * range) {
            return NiveauGravite.AVERTISSEMENT;
        }
        return NiveauGravite.CRITIQUE;
    }

    public Releve getDernierReleve() {
        if (historique.isEmpty()) {
            return null;
        }
        return historique.get(historique.size() - 1);
    }

    @Override
    public String toString() {
        return "Capteur [" + code + "] " + typeCapteur + " — " + statut
                + " — Zone: " + codeZone;
    }
}
