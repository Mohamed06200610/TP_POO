package TP_POO.src.smartfarming.enums;

/**
 * Énumération NiveauGravite — Niveau de gravité d'un relevé ou d'une alerte.
 *
 * Valeurs : NORMAL (dans les seuils), AVERTISSEMENT (déviation ≤ 10%),
 * CRITIQUE (déviation > 10% ou sortie de zone GPS).
 *
 * Concept POO : ENUMERATION — indicateurs colorés pour le tableau de bord.
 */
public enum NiveauGravite {
    NORMAL,
    AVERTISSEMENT,
    CRITIQUE
}
