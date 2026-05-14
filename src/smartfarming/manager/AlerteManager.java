package TP_POO.src.smartfarming.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import TP_POO.src.smartfarming.enums.NiveauGravite;
import TP_POO.src.smartfarming.enums.StatutAlerte;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.model.capteurs.Capteur;
import TP_POO.src.smartfarming.model.domain.Alerte;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveGPS;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;

/**
 * Classe AlerteManager — Gestionnaire du système d'alertes.
 *
 * Rôle : Gère le cycle de vie complet des alertes : déclenchement automatique
 *        lors de dépassement de seuils, affichage du panneau trié par gravité,
 *        acquittement, suppression et consultation de l'historique filtré.
 *        Implémente les méthodes de la Fonction 5 (Gérer le système d'alertes).
 *
 * Concept POO : ENCAPSULATION — centralise toute la logique d'alerte.
 *               Sépare alertes actives et historique complet.
 */
public class AlerteManager {

    private List<Alerte> alertesActives;
    private List<Alerte> historiqueAlertes;

    /**
     * Constructeur.
     */
    public AlerteManager() {
        this.alertesActives = new ArrayList<>();
        this.historiqueAlertes = new ArrayList<>();
    }

    // ── Getters / Setters ──────────────────────────────────

    public List<Alerte> getAlertesActives() {
        return alertesActives;
    }

    public void setAlertesActives(List<Alerte> alertesActives) {
        this.alertesActives = alertesActives;
    }

    public List<Alerte> getHistoriqueAlertes() {
        return historiqueAlertes;
    }

    public void setHistoriqueAlertes(List<Alerte> historiqueAlertes) {
        this.historiqueAlertes = historiqueAlertes;
    }

    // ── FONCTION 5 — Gérer le système d'alertes ────────────

    /**
     * Déclenche une alerte automatiquement lorsqu'un relevé dépasse les seuils.
     *
     * @param releve  le relevé ayant déclenché l'alerte
     * @param capteur le capteur source
     * @return l'alerte créée
     */
    public Alerte declencherAlerte(Releve releve, Capteur capteur) {
        NiveauGravite niveau;

        if (releve instanceof ReleveGPS) {
            niveau = NiveauGravite.CRITIQUE;
        } else if (releve instanceof ReleveNumerique) {
            ReleveNumerique rn = (ReleveNumerique) releve;
            double valeur = rn.getValeur();
            double seuilMin = capteur.getSeuilMin();
            double seuilMax = capteur.getSeuilMax();
            double range = seuilMax - seuilMin;

            double deviation;
            if (valeur < seuilMin) {
                deviation = Math.abs(valeur - seuilMin);
            } else {
                deviation = Math.abs(valeur - seuilMax);
            }

            if (deviation <= 0.10 * range) {
                niveau = NiveauGravite.AVERTISSEMENT;
            } else {
                niveau = NiveauGravite.CRITIQUE;
            }
        } else {
            niveau = NiveauGravite.AVERTISSEMENT;
        }

        Alerte alerte = new Alerte(releve, capteur, niveau);
        alertesActives.add(alerte);
        historiqueAlertes.add(alerte);

        System.out.println("⚠ ALERTE " + niveau + " — Capteur: " + capteur.getCode()
                + " — " + releve.getDescription());

        return alerte;
    }

    /**
     * Affiche le panneau des alertes actives, triées par gravité.
     *
     * @return liste des alertes actives triées
     */
    public List<Alerte> afficherPanneauAlertes() {
        List<Alerte> panel = new ArrayList<>();
        for (Alerte a : alertesActives) {
            if (a.getStatut() == StatutAlerte.ACTIVE) {
                panel.add(a);
            }
        }

        panel.sort(new Comparator<Alerte>() {
            @Override
            public int compare(Alerte a1, Alerte a2) {
                return getPriorite(a1.getNiveauGravite()) - getPriorite(a2.getNiveauGravite());
            }

            private int getPriorite(NiveauGravite niveau) {
                switch (niveau) {
                    case CRITIQUE: return 0;
                    case AVERTISSEMENT: return 1;
                    default: return 2;
                }
            }
        });

        return panel;
    }

    /**
     * Acquitte une alerte.
     *
     * @param idAlerte identifiant de l'alerte
     */
    public void acquitterAlerte(String idAlerte) {
        for (Alerte a : alertesActives) {
            if (a.getId().equals(idAlerte)) {
                a.setStatut(StatutAlerte.ACQUITTEE);
                alertesActives.remove(a);
                return;
            }
        }
    }

    /**
     * Supprime une alerte.
     *
     * @param idAlerte identifiant de l'alerte
     */
    public void supprimerAlerte(String idAlerte) {
        for (Alerte a : alertesActives) {
            if (a.getId().equals(idAlerte)) {
                a.setStatut(StatutAlerte.SUPPRIMEE);
                alertesActives.remove(a);
                return;
            }
        }
    }

    /**
     * Consulte l'historique des alertes avec filtres.
     */
    public List<Alerte> consulterHistoriqueAlertes(String codeZone, TypeCapteur typeCapteur,
                                                    NiveauGravite niveau, Date dateDebut,
                                                    Date dateFin) {
        List<Alerte> resultats = new ArrayList<>();
        for (Alerte a : historiqueAlertes) {
            if (codeZone != null && !a.getCodeZone().equals(codeZone)) continue;
            if (typeCapteur != null && a.getTypeCapteur() != typeCapteur) continue;
            if (niveau != null && a.getNiveauGravite() != niveau) continue;
            if (dateDebut != null && a.getHorodatage().before(dateDebut)) continue;
            if (dateFin != null && a.getHorodatage().after(dateFin)) continue;
            resultats.add(a);
        }
        return resultats;
    }
}
