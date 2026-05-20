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

public class ZoneElevage extends Zone {

    private List<Animal> animaux;
    private ProgrammeAlimentation programmeAlimentation;

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

    public void ajouterAnimal(Animal animal) {
        this.animaux.add(animal);
    }

    public Animal enregistrerAnimal(TypeAnimal espece, int age, double poids,
            EtatSante etatSante) {
        Animal animal = new Animal(espece, age, poids, etatSante);
        this.animaux.add(animal);
        return animal;
    }

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

    public ProgrammeAlimentation afficherProgrammeAlimentation() {
        return this.programmeAlimentation;
    }

    public void definirProgrammeAlimentation(String typeAliment, double quantiteParRepas,
            int nombreRepas) {
        this.programmeAlimentation = new ProgrammeAlimentation(
                typeAliment, quantiteParRepas, nombreRepas);
    }

    @Override
    public int getNombreEntites() {
        return animaux.size();
    }

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

        if (!hasRuminants && !hasVolaille) {
            HistoriqueProductionElevage h = new HistoriqueProductionElevage(
                    getCode(), "laitier");
            h.setRendementLaitier(0.0);
            h.enregistrer();
            getHistoriques().add(h);
        }
    }

    // ── Méthodes utilitaires privées ───────────────────────

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
