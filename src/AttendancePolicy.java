/**
 * Implementa la política de penalización por asistencia.
 * Responsabilidad: Aplicar reglas de asistencia mínima (RF02).
 */
public class AttendancePolicy {
    
    /**
     * Aplica penalización si el estudiante no cumplió con la asistencia mínima.
     * @param currentGrade Nota actual antes de aplicar penalización
     * @param hasReachedMinimum Si alcanzó la asistencia mínima requerida
     * @return Nota después de aplicar penalización
     */
    public double applyPenalty(double currentGrade, boolean hasReachedMinimum) {
        if (hasReachedMinimum) {
            return currentGrade;
        }
        return Constants.ATTENDANCE_PENALTY_GRADE;
    }
    
    /**
     * Calcula el monto de penalización aplicada.
     * @param currentGrade Nota antes de penalización
     * @param hasReachedMinimum Si alcanzó la asistencia mínima
     * @return Monto de penalización (positivo indica reducción)
     */
    public double getPenaltyAmount(double currentGrade, boolean hasReachedMinimum) {
        if (hasReachedMinimum) {
            return 0.0;
        }
        return currentGrade - Constants.ATTENDANCE_PENALTY_GRADE;
    }
}
