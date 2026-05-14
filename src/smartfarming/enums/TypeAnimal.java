package TP_POO.src.smartfarming.enums;

public enum TypeAnimal {
    VACHE,
    MOUTON,
    CHEVRE,
    POULET,
    DINDE;

    public boolean isRuminant() {
        return this == VACHE || this == MOUTON || this == CHEVRE;
    }

    public boolean isVolaille() {
        return this == POULET || this == DINDE;
    }
}
