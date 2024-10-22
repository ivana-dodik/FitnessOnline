package org.unibl.etf.ip.fitnessonline.models.types;

public enum DifficultyLevel {
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED");

    private final String value;

    private DifficultyLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}