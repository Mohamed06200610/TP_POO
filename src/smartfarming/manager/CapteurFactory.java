package TP_POO.src.smartfarming.manager;

import TP_POO.src.smartfarming.enums.TypeCapteur;
import TP_POO.src.smartfarming.model.capteurs.*;
import TP_POO.src.smartfarming.model.zones.Zone;

/**
 * Classe CapteurFactory — Fabrique de capteurs (Factory Pattern).
 *
 * Rôle : Centralise la création des capteurs. Selon le TypeCapteur demandé,
 *        instancie le bon type concret (CapteurEnvironnemental, CapteurSol,
 *        CapteurBiometrique, CapteurGPS, CapteurEau).
 *
 * Concept POO : FACTORY PATTERN — découple la logique de création des capteurs
 *               de leur utilisation. Le code appelant ne connaît que le type
 *               demandé, pas la classe concrète instanciée.
 *
 * RÈGLE 6 : switch(type) → instanciation du bon type concret.
 */
public class CapteurFactory {

    /**
     * Crée un capteur du type spécifié.
     *
     * @param type          type de capteur à créer
     * @param zone          zone à laquelle rattacher le capteur
     * @param seuilMin      seuil minimum
     * @param seuilMax      seuil maximum
     * @param alerteManager gestionnaire d'alertes
     * @return le capteur créé (sous-type concret)
     */
    public static Capteur creerCapteur(TypeCapteur type, Zone zone,
                                        double seuilMin, double seuilMax,
                                        AlerteManager alerteManager) {
        Capteur capteur;

        switch (type) {
            case ENVIRONNEMENTAL:
                capteur = new CapteurEnvironnemental(zone.getCode(), seuilMin, seuilMax,
                        alerteManager);
                break;
            case SOL:
                capteur = new CapteurSol(zone.getCode(), seuilMin, seuilMax,
                        alerteManager);
                break;
            case BIOMETRIQUE:
                capteur = new CapteurBiometrique(zone.getCode(), seuilMin, seuilMax,
                        alerteManager);
                break;
            case GPS:
                CapteurGPS capteurGPS = new CapteurGPS(zone.getCode(), seuilMin, seuilMax,
                        alerteManager);
                capteurGPS.setZoneReference(zone); // pour la vérification des limites
                capteur = capteurGPS;
                break;
            case EAU:
                capteur = new CapteurEau(zone.getCode(), seuilMin, seuilMax,
                        alerteManager);
                break;
            default:
                throw new IllegalArgumentException("Type de capteur inconnu : " + type);
        }

        return capteur;
    }
}
