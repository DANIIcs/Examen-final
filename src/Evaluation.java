/**
 * Representa una evaluación individual con su nota y peso porcentual.
 * Responsabilidad: Encapsular los datos de una evaluación del estudiante.
 */
public class Evaluation {
    private final String name;
    private final double score;
    private final double weightPercentage;
    
    /**
     * Crea una nueva evaluación.
     * @param name Nombre de la evaluación (ej: "PC1", "Examen Final")
     * @param score Nota obtenida
     * @param weightPercentage Peso en porcentaje (0-100)
     * @throws IllegalArgumentException si los valores son inválidos
     */
    public Evaluation(String name, double score, double weightPercentage) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la evaluación no puede estar vacío");
        }
        if (score < 0) {
            throw new IllegalArgumentException("La nota no puede ser negativa");
        }
        if (weightPercentage < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        
        this.name = name.trim();
        this.score = score;
        this.weightPercentage = weightPercentage;
    }
    
    public String getName() {
        return name;
    }
    
    public double getScore() {
        return score;
    }
    
    public double getWeightPercentage() {
        return weightPercentage;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %.2f (peso: %.1f%%)", name, score, weightPercentage);
    }
}
