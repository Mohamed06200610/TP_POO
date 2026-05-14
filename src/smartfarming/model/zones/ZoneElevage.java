package TP_POO.src.smartfarming.model.zones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import TP_POO.src.smartfarming.enums.EtatSante;
import TP_POO.src.smartfarming.enums.TypeAnimal;
import TP_POO.src.smartfarming.enums.TypeZone;
import TP_POO.src.smartfarming.model.domain.Animal;
import TP_POO.src.smartfarming.model.domain.ProgrammeAlimentation;
import TP_POO.src.smartfarming.model.historique.HistoriqueProductionElevage;

/**
 * Classe ZoneElevage — Zone dédiée à l'élevage d'animaux.
 *
 * Rôle : Gère les animaux (ruminants et volailles), les événements sanitaires,
 * le programme d'alimentation et l'enregistrement de production.
 * Implémente les méthodes de la Fonction 3 (Gérer les animaux).
 *
 * Concept POO :
 * HÉRITAGE — étend Zone avec des attributs spécifiques à l'élevage.
 * POLYMORPHISME — enregistrerProduction() crée un HistoriqueProductionElevage.
 * La production dépend du type d'animaux (laitier vs œufs).
 */
public class ZoneElevage extends Zone {

    private List<Animal> animaux;
    private ProgrammeAlimentation programmeAlimentation;

    /**
     * Constructeur.
     *
     * @param code code unique de la zone
     * @param nom  nom de la zone
     */
    public ZoneElevage(String code, String nom) {
        super(code, nom, TypeZone.ELEVAGE);
        this.animaux = new ArrayList<>();
        this.programmeAlimentation = null;
    }

    // ── Getters / Setters ──────────────────────────────────

    public List<Animal> getAnimaux() {
        return animaux;
    }

    public void setAnimaux(List<Animal> animaux) {
        this.animaux = animaux;
    }

    public ProgrammeAlimentation getProgrammeAlimentation() {
        return programmeAlimentation;
    }

    public void setProgrammeAlimentation(ProgrammeAlimentation programmeAlimentation) {
        this.programmeAlimentation = programmeAlimentation;
    }

    // ── FONCTION 3 — Gérer les animaux ─────────────────────

    /**
     * Ajoute un animal existant à la zone.
     *
     * @param animal l'animal à ajouter
     */
    public void ajouterAnimal(Animal animal) {
        this.animaux.add(animal);
    }

    /**
     * Enregistre un nouvel animal dans la zone.
     * Génère un ID unique, crée l'objet Animal et l'ajoute à la liste.
     *
     * @param espece    espèce de l'animal
     * @param age       âge en mois
     * @param poids     poids en kg
     * @param etatSante état de santé initial
     * @return l'animal créé
     */
    public Animal enregistrerAnimal(TypeAnimal espece, int age, double poids,
            EtatSante etatSante) {
        Animal animal = new Animal(espece, age, poids, etatSante);
        this.animaux.add(animal);
        return animal;
    }

    /**
     * Consigne un événement sanitaire pour un animal.
     * Met à jour le poids et l'état de santé, et enregistre l'événement
     * dans l'historique de l'animal.
     *
     * @param idAnimal     identifiant de l'animal
     * @param evenement    description de l'événement sanitaire
     * @param nouveauPoids nouveau poids de l'animal
     */
    public void consignerEvenementSanitaire(String idAnimal, String evenement,
            double nouveauPoids) {
        for (Animal a : animaux) {
            if (a.getId().equals(idAnimal)) {
                double ancienPoids = a.getPoids();
                a.setPoids(nouveauPoids);

                // Mise à jour de l'état de santé selon la variation de poids
                double variation = Math.abs(nouveauPoids - ancienPoids) / ancienPoids;
                if (variation > 0.20) {
                    a.setEtatSante(EtatSante.EN_QUARANTAINE);
                } else if (variation > 0.10) {
                    a.setEtatSante(EtatSante.MALADE);
                } else {
                    a.setEtatSante(EtatSante.SAIN);
                }

                // Enregistrement dans l'historique
                String entry = new Date() + " — " + evenement
                        + " | Poids: " + ancienPoids + " → " + nouveauPoids + " kg"
                        + " | État: " + a.getEtatSante();
                a.ajouterEvenement(entry);
                return;
            }
        }
        System.out.println("Animal non trouvé : " + idAnimal);
    }

    /**
     * Retourne le programme d'alimentation de la zone.
     *
     * @return le programme d'alimentation, ou null si non défini
     */
    public ProgrammeAlimentation afficherProgrammeAlimentation() {
        return this.programmeAlimentation;
    }

    /**
     * Définit ou met à jour le programme d'alimentation de la zone.
     *
     * @param typeAliment      type d'aliment
     * @param quantiteParRepas quantité par repas en kg
     * @param nombreRepas      nombre de repas par jour
     */
    public void definirProgrammeAlimentation(String typeAliment, double quantiteParRepas,
            int nombreRepas) {
        this.programmeAlimentation = new ProgrammeAlimentation(
                typeAliment, quantiteParRepas, nombreRepas);
    }

    // ── Zone abstraite ─────────────────────────────────────

    /**
     * Retourne le nombre d'animaux dans la zone.
     *
     * @return nombre d'animaux
     */
    @Override
    public int getNombreEntites() {
        return animaux.size();
    }

    /**
     * Enregistre la production de la zone d'élevage.
     * Crée un HistoriqueProductionElevage.
     * Si la zone contient des ruminants → production laitière.
     * Si la zone contient des volailles → production d'œufs.
     */
    @Override
    public void enregistrerProduction() {
        boolean hasRuminants = false;
        boolean hasVolaille = false;

        for (Animal a : animaux) {
            if (a.getEspece().isRuminant()) {
                hasRuminants = true;
            }
            if (a.getEspece().isVolaille()) {
                hasVolaille = true;
            }
        }

        if (hasRuminants) {
            HistoriqueProductionElevage h = new HistoriqueProductionElevage(
                    getCode(), "laitier");
            h.setRendementLaitier(calculerLait());
            h.enregistrer();
            getHistoriques().add(h);
        }

        if (hasVolaille) {
            HistoriqueProductionElevage h = new HistoriqueProductionElevage(
                    getCode(), "oeufs");
            h.setProductionOeufs(calculerOeufs());
            h.enregistrer();
            getHistoriques().add(h);
        }

        // Si la zone est vide, on enregistre quand même un historique vide
        if (!hasRuminants && !hasVolaille) {
            HistoriqueProductionElevage h = new HistoriqueProductionElevage(
                    getCode(), "laitier");
            h.setRendementLaitier(0.0);
            h.enregistrer();
            getHistoriques().add(h);
        }
    }

    // ── Méthodes utilitaires privées ───────────────────────

    /**
     * Calcule la production laitière (simulation).
     * Basée sur le nombre de ruminants sains.
     *
     * @return litres de lait produits
     */
    private double calculerLait() {
        double total = 0.0;
        for (Animal a : animaux) {
            if (a.getEspece().isRuminant() && a.getEtatSante() == EtatSante.SAIN) {
                // Simulation : une vache produit ~25L, mouton ~2L, chèvre ~5L
                switch (a.getEspece()) {
                    case VACHE:
                        total += 25.0;
                        break;
                    case MOUTON:
                        total += 2.0;
                        break;
                    case CHEVRE:
                        total += 5.0;
                        break;
                    default:
                        break;
                }
            }
        }
        return total;
    }

    /**
     * Calcule la production d'œufs (simulation).
     * Basée sur le nombre de volailles saines.
     *
     * @return nombre d'œufs
     */
    private int calculerOeufs() {
        int total = 0;
        for (Animal a : animaux) {
            if (a.getEspece().isVolaille() && a.getEtatSante() == EtatSante.SAIN) {
                // Simulation : un poulet ~1 œuf/jour, une dinde ~0.5
                switch (a.getEspece()) {
                    case POULET:
                        total += 1;
                        break;
                    case DINDE:
                        total += 1;
                        break;
                    default:
                        break;
                }
            }
        }
        return total;
    }
}
