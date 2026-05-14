package TP_POO.src.smartfarming.manager;

import java.util.ArrayList;
import java.util.List;
import TP_POO.src.smartfarming.enums.TypeZone;
import TP_POO.src.smartfarming.helper.ZoneInfo;
import TP_POO.src.smartfarming.model.domain.Animal;
import TP_POO.src.smartfarming.model.domain.Culture;
import TP_POO.src.smartfarming.model.zones.Zone;
import TP_POO.src.smartfarming.model.zones.ZoneAquacole;
import TP_POO.src.smartfarming.model.zones.ZoneCulture;
import TP_POO.src.smartfarming.model.zones.ZoneElevage;

/**
 * Classe Ferme — Classe principale de l'application Smart Farming.
 *
 * Rôle : Point d'entrée du système. Gère les zones de la ferme et délègue
 * aux managers spécialisés (AlerteManager, CapteurManager).
 * Implémente les méthodes de la Fonction 1 (Gérer les zones et entités).
 *
 * Concept POO : ENCAPSULATION — orchestre toutes les opérations via des
 * managers dédiés. Façade du système.
 * POLYMORPHISME — appelle zone.enregistrerProduction() qui
 * est résolu dynamiquement selon le type concret de zone.
 */
public class Ferme {

    private String nom;
    private List<Zone> zones;
    private AlerteManager alerteManager;
    private CapteurManager capteurManager;

    /**
     * Constructeur.
     *
     * @param nom nom de la ferme
     */
    public Ferme(String nom) {
        this.nom = nom;
        this.zones = new ArrayList<>();
        this.alerteManager = new AlerteManager();
        this.capteurManager = new CapteurManager(alerteManager);
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public AlerteManager getAlerteManager() {
        return alerteManager;
    }

    public void setAlerteManager(AlerteManager alerteManager) {
        this.alerteManager = alerteManager;
    }

    public CapteurManager getCapteurManager() {
        return capteurManager;
    }

    public void setCapteurManager(CapteurManager capteurManager) {
        this.capteurManager = capteurManager;
    }

    // ── FONCTION 1 — Gérer les zones et entités ────────────

    /**
     * Ajoute une zone à la ferme.
     *
     * @param zone la zone à ajouter
     */
    public void ajouterZone(Zone zone) {
        this.zones.add(zone);
    }

    /**
     * Modifie le nom et le type d'une zone existante.
     *
     * @param code        code de la zone à modifier
     * @param nouveauNom  nouveau nom
     * @param nouveauType nouveau type de zone
     */
    public void modifierZone(String code, String nouveauNom, TypeZone nouveauType) {
        Zone zone = trouverZone(code);
        if (zone != null) {
            zone.setNom(nouveauNom);
            zone.setTypeZone(nouveauType);
        } else {
            System.out.println("Zone non trouvée : " + code);
        }
    }

    /**
     * Désactive (suspend) une zone et cascade à tous ses capteurs.
     * Appelle zone.suspendre() qui suspend automatiquement tous les capteurs.
     *
     * @param code code de la zone à désactiver
     */
    public void desactiverZone(String code) {
        Zone zone = trouverZone(code);
        if (zone != null) {
            zone.suspendre(); // Cascade automatique aux capteurs (RÈGLE 1)
        } else {
            System.out.println("Zone non trouvée : " + code);
        }
    }

    /**
     * Réactive une zone et restaure tous ses capteurs.
     *
     * @param code code de la zone à réactiver
     */
    public void activerZone(String code) {
        Zone zone = trouverZone(code);
        if (zone != null) {
            zone.activer(); // Cascade automatique aux capteurs
        } else {
            System.out.println("Zone non trouvée : " + code);
        }
    }

    /**
     * Affecte une culture à une zone de culture.
     * Trouve la ZoneCulture par code et lui ajoute la culture.
     *
     * @param culture  la culture à affecter
     * @param codeZone code de la zone de culture
     */
    public void affecterCultureAZone(Culture culture, String codeZone) {
        Zone zone = trouverZone(codeZone);
        if (zone instanceof ZoneCulture) {
            ((ZoneCulture) zone).ajouterCulture(culture);
        } else {
            System.out.println("Zone de culture non trouvée : " + codeZone);
        }
    }

    /**
     * Affecte un animal à une zone d'élevage.
     * Trouve la ZoneElevage par code et lui ajoute l'animal.
     *
     * @param animal   l'animal à affecter
     * @param codeZone code de la zone d'élevage
     */
    public void affecterAnimalAZone(Animal animal, String codeZone) {
        Zone zone = trouverZone(codeZone);
        if (zone instanceof ZoneElevage) {
            ((ZoneElevage) zone).ajouterAnimal(animal);
        } else {
            System.out.println("Zone d'élevage non trouvée : " + codeZone);
        }
    }

    /**
     * Affiche la vue d'ensemble de toutes les zones de la ferme.
     * Retourne pour chaque zone : code, nom, type, statut et nombre d'entités.
     *
     * @return liste de ZoneInfo pour l'affichage
     */
    public List<ZoneInfo> afficherVueEnsembleZones() {
        List<ZoneInfo> vueEnsemble = new ArrayList<>();

        for (Zone zone : zones) {
            ZoneInfo info = new ZoneInfo(
                    zone.getCode(),
                    zone.getNom(),
                    zone.getTypeZone(),
                    zone.getStatutZone(),
                    zone.getNombreEntites());
            vueEnsemble.add(info);
        }

        return vueEnsemble;
    }

    /**
     * Enregistre la production d'une zone.
     * Appelle zone.enregistrerProduction() qui est résolu dynamiquement
     * selon le type concret (polymorphisme).
     *
     * Chaque sous-classe crée sa propre sous-classe d'HistoriqueProduction :
     * ZoneCulture → HistoriqueProductionCulture
     * ZoneElevage → HistoriqueProductionElevage
     * ZoneAquacole → HistoriqueProductionAquacole
     *
     * @param codeZone code de la zone
     */
    public void enregistrerProductionZone(String codeZone) {
        Zone zone = trouverZone(codeZone);
        if (zone != null) {
            zone.enregistrerProduction(); // Polymorphisme (RÈGLE 7)
        } else {
            System.out.println("Zone non trouvée : " + codeZone);
        }
    }

    // ── Méthode utilitaire ─────────────────────────────────

    /**
     * Recherche une zone par son code.
     *
     * @param code code de la zone
     * @return la zone trouvée, ou null
     */
    private Zone trouverZone(String code) {
        for (Zone z : zones) {
            if (z.getCode().equals(code)) {
                return z;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Ferme [" + nom + "] — Zones: " + zones.size();
    }
}
