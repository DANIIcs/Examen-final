import java.util.HashMap;
import java.util.Map;

/**
 * Implementa la política de puntos extra por año académico.
 * Responsabilidad: Aplicar bonificación según criterios definidos (RF03).
 */
public class ExtraPointsPolicy {
    private final Map<Integer, Boolean> allYearsTeachers;
    
    /**
     * Crea una política de puntos extra.
     * @param allYearsTeachers Mapa de año académico a si hay política de puntos extra activa
     */
    public ExtraPointsPolicy(Map<Integer, Boolean> allYearsTeachers) {
        this.allYearsTeachers = new HashMap<>(allYearsTeachers);
    }
    
    /**
     * Verifica si los puntos extra están habilitados para un año específico.
     * @param year Año académico
     * @return true si hay política de puntos extra activa para ese año
     */
    public boolean isExtraPointsEnabledForYear(int year) {
        return allYearsTeachers.getOrDefault(year, false);
    }
    
    /**
     * Aplica puntos extra si corresponde.
     * @param currentGrade Nota actual antes de aplicar puntos extra
     * @param year Año académico
     * @param studentMeetsCriteria Si el estudiante cumple los criterios
     * @param maxGrade Nota máxima permitida
     * @return Nota después de aplicar puntos extra (sin exceder máximo)
     */
    public double applyExtraPoints(
            double currentGrade,
            int year,
            boolean studentMeetsCriteria,
            double maxGrade) {
        
        if (!isExtraPointsEnabledForYear(year) || !studentMeetsCriteria) {
            return currentGrade;
        }
        
        double newGrade = currentGrade + Constants.EXTRA_POINTS_BONUS;
        return Math.min(newGrade, maxGrade);
    }
    
    /**
     * Calcula el monto de puntos extra aplicados.
     * @param originalGrade Nota antes de puntos extra
     * @param finalGrade Nota después de puntos extra
     * @return Monto de puntos extra aplicados
     */
    public double getExtraPointsAmount(double originalGrade, double finalGrade) {
        return finalGrade - originalGrade;
    }
}
