package TP_POO.src.smartfarming.model.capteurs;

import TP_POO.src.smartfarming.enums.NiveauGravite;
import TP_POO.src.smartfarming.enums.StatutCapteur;
import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.helper.Coordonnees;
import TP_POO.src.smartfarming.manager.AlerteManager;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveGPS;
import TP_POO.src.smartfarming.model.zones.Zone;

/**
 * Classe CapteurGPS — Capteur GPS pour le suivi de position d'un animal.
 *
 * Rôle : Envoie la position géographique (latitude/longitude) d'un animal.
 *        Après chaque relevé, vérifie si l'animal est dans les limites de sa zone.
 *        Déclenche une alerte CRITIQUE si l'animal est hors zone.
 *
 * Concept POO :
 *   HÉRITAGE — étend Capteur avec une logique GPS spécifique.
 *   POLYMORPHISME — envoyerReleve() retourne un ReleveGPS (pas numérique).
 */
public class CapteurGPS extends Capteur {

    private Zone zoneReference; // zone pour la vérification des limites

    /**
     * Constructeur.
     *
     * @param codeZone      code de la zone associée
     * @param seuilMin      seuil minimum (non utilisé pour GPS, convention)
     * @param seuilMax      seuil maximum (non utilisé pour GPS, convention)
     * @param alerteManager gestionnaire d'alertes
     */
    public CapteurGPS(String codeZone, double seuilMin, double seuilMax,
                      AlerteManager alerteManager) {
        super(TypeCapteur.GPS, codeZone, seuilMin, seuilMax, alerteManager);
        this.zoneReference = null;
    }

    // ── Getters / Setters ──────────────────────────────────

    public Zone getZoneReference() {
        return zoneReference;
    }

    public void setZoneReference(Zone zoneReference) {
        this.zoneReference = zoneReference;
    }

    // ── Méthode polymorphe ─────────────────────────────────

    /**
     * Envoie un relevé GPS (latitude, longitude).
     * Garde : bloqué si le capteur n'est pas ACTIF.
     * Après le relevé : vérifie si la position est dans les limites de la zone.
     * Si hors limites → alerte CRITIQUE automatique.
     *
     * @return le relevé GPS, ou null si capteur inactif
     */
    @Override
    public Releve envoyerReleve() {
        if (getStatutCapteur() != StatutCapteur.ACTIF) {
            return null;
        }

        // Simulation de position GPS
        double latitude = 36.7 + Math.random() * 0.1;
        double longitude = 3.0 + Math.random() * 0.1;

        ReleveGPS releve = new ReleveGPS(getCode(), latitude, longitude);
        getHistorique().add(releve);

        // Vérification des limites de zone (RÈGLE 5)
        if (zoneReference != null) {
            Coordonnees position = releve.getPosition();
            if (!zoneReference.estDansLimites(position)) {
                // Hors zone → alerte CRITIQUE automatique
                if (getAlerteManager() != null) {
                    getAlerteManager().declencherAlerte(releve, this);
                }
            }
        }

        return releve;
    }
}
