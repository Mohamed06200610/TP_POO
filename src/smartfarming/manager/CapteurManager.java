package TP_POO.src.smartfarming.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import TP_POO.src.smartfarming.enums.NiveauGravite;
import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.helper.GraphData;
import TP_POO.src.smartfarming.helper.ReleveInfo;
import TP_POO.src.smartfarming.model.capteurs.Capteur;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;
import TP_POO.src.smartfarming.model.zones.Zone;

/**
 * Classe CapteurManager — Gestionnaire centralisé des capteurs.
 *
 * Rôle : Gère l'ajout, la configuration, le changement de statut des capteurs,
 *        ainsi que l'affichage du tableau de bord des relevés et des graphiques
 *        d'évolution. Implémente les méthodes de la Fonction 4 (Gérer les capteurs).
 *
 * Concept POO : ENCAPSULATION — centralise toute la logique de gestion
 *               des capteurs. Délègue la création à CapteurFactory.
 */
public class CapteurManager {

    private List<Capteur> capteurs;
    private AlerteManager alerteManager;

    /**
     * Constructeur.
     *
     * @param alerteManager gestionnaire d'alertes pour le déclenchement automatique
     */
    public CapteurManager(AlerteManager alerteManager) {
        this.capteurs = new ArrayList<>();
        this.alerteManager = alerteManager;
    }

    // ── Getters / Setters ──────────────────────────────────

    public List<Capteur> getCapteurs() {
        return capteurs;
    }

    public void setCapteurs(List<Capteur> capteurs) {
        this.capteurs = capteurs;
    }

    public AlerteManager getAlerteManager() {
        return alerteManager;
    }

    public void setAlerteManager(AlerteManager alerteManager) {
        this.alerteManager = alerteManager;
    }

    // ── FONCTION 4 — Gérer les capteurs ────────────────────

    /**
     * Ajoute un capteur à une zone via CapteurFactory.
     * Le capteur est créé par la fabrique, ajouté à la zone et au registre global.
     *
     * @param type     type de capteur à créer
     * @param codeZone code de la zone cible
     * @param seuilMin seuil minimum
     * @param seuilMax seuil maximum
     * @param zone     la zone à laquelle rattacher le capteur
     * @return le capteur créé
     */
    public Capteur ajouterCapteur(TypeCapteur type, String codeZone,
                                   double seuilMin, double seuilMax, Zone zone) {
        Capteur capteur = CapteurFactory.creerCapteur(type, zone, seuilMin, seuilMax,
                alerteManager);
        zone.ajouterCapteur(capteur);
        capteurs.add(capteur);
        return capteur;
    }

    /**
     * Configure les seuils d'un capteur existant.
     *
     * @param codeCapteur code du capteur
     * @param seuilMin    nouveau seuil minimum
     * @param seuilMax    nouveau seuil maximum
     */
    public void configurerCapteur(String codeCapteur, double seuilMin, double seuilMax) {
        Capteur capteur = trouverCapteur(codeCapteur);
        if (capteur != null) {
            capteur.setSeuilMin(seuilMin);
            capteur.setSeuilMax(seuilMax);
        } else {
            System.out.println("Capteur non trouvé : " + codeCapteur);
        }
    }

    /**
     * Affiche le tableau de bord des relevés d'une zone.
     * Pour chaque capteur de la zone, récupère le dernier relevé et
     * détermine le niveau de gravité (indicateur coloré).
     *
     * @param codeZone code de la zone
     * @param zone     la zone cible
     * @return liste de ReleveInfo avec indicateurs colorés
     */
    public List<ReleveInfo> afficherTableauBordReleves(String codeZone, Zone zone) {
        List<ReleveInfo> infos = new ArrayList<>();

        for (Capteur capteur : zone.getCapteurs()) {
            Releve dernier = capteur.getDernierReleve();
            if (dernier != null && dernier instanceof ReleveNumerique) {
                ReleveNumerique rn = (ReleveNumerique) dernier;
                NiveauGravite niveau = capteur.determinerNiveau(rn.getValeur());
                ReleveInfo info = new ReleveInfo(
                        capteur.getCode(), rn.getValeur(), rn.getUnite(), niveau);
                infos.add(info);
            } else if (dernier != null) {
                // Pour les relevés GPS, on met NORMAL par défaut (la vérification
                // de limites se fait dans CapteurGPS.envoyerReleve)
                ReleveInfo info = new ReleveInfo(
                        capteur.getCode(), 0.0, "GPS", NiveauGravite.NORMAL);
                infos.add(info);
            }
        }

        return infos;
    }

    /**
     * Consulte l'historique des relevés d'un capteur, filtré par plage de dates.
     *
     * @param codeCapteur code du capteur
     * @param dateDebut   date de début
     * @param dateFin     date de fin
     * @return liste des relevés dans la plage de dates
     */
    public List<Releve> consulterHistoriqueReleves(String codeCapteur,
                                                    Date dateDebut, Date dateFin) {
        Capteur capteur = trouverCapteur(codeCapteur);
        if (capteur == null) {
            System.out.println("Capteur non trouvé : " + codeCapteur);
            return new ArrayList<>();
        }

        List<Releve> resultats = new ArrayList<>();
        for (Releve r : capteur.getHistorique()) {
            if (!r.getHorodatage().before(dateDebut) && !r.getHorodatage().after(dateFin)) {
                resultats.add(r);
            }
        }
        return resultats;
    }

    /**
     * Change le statut d'un capteur.
     * Si SUSPENDU → envoyerReleve() sera bloqué.
     * Si ACTIF → envoyerReleve() reprend.
     *
     * @param codeCapteur code du capteur
     * @param statut      nouveau statut
     */
    public void changerStatutCapteur(String codeCapteur, StatutCapteur statut) {
        Capteur capteur = trouverCapteur(codeCapteur);
        if (capteur != null) {
            capteur.changerStatut(statut);
        } else {
            System.out.println("Capteur non trouvé : " + codeCapteur);
        }
    }

    /**
     * Génère les données de graphique d'évolution d'un capteur sur une période.
     * Chaque point inclut la valeur et le niveau de gravité coloré
     * (NORMAL / AVERTISSEMENT / CRITIQUE).
     *
     * @param codeCapteur code du capteur
     * @param dateDebut   date de début
     * @param dateFin     date de fin
     * @return données du graphique pour le rendu UI
     */
    public GraphData afficherGraphiquesEvolution(String codeCapteur,
                                                  Date dateDebut, Date dateFin) {
        Capteur capteur = trouverCapteur(codeCapteur);
        if (capteur == null) {
            System.out.println("Capteur non trouvé : " + codeCapteur);
            return new GraphData(codeCapteur);
        }

        return capteur.afficherGraphiquesEvolution(codeCapteur, dateDebut, dateFin);
    }

    // ── Méthode utilitaire ─────────────────────────────────

    /**
     * Recherche un capteur par son code dans le registre global.
     *
     * @param codeCapteur code du capteur
     * @return le capteur trouvé, ou null
     */
    private Capteur trouverCapteur(String codeCapteur) {
        for (Capteur c : capteurs) {
            if (c.getCode().equals(codeCapteur)) {
                return c;
            }
        }
        return null;
    }
}
