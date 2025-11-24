/**
 * Encapsula el resultado del cálculo de nota final con todos los detalles.
 * Responsabilidad: Proporcionar información completa del cálculo realizado (RF05).
 */
public class GradeCalculationResult {
    private final double finalGrade;
    private final double weightedAverage;
    private final double attendancePenalty;
    private final double extraPointsApplied;
    private final String detailText;
    
    /**
     * Crea un resultado de cálculo de nota final.
     * @param finalGrade Nota final calculada
     * @param weightedAverage Promedio ponderado de evaluaciones
     * @param attendancePenalty Penalización aplicada por inasistencia
     * @param extraPointsApplied Puntos extra aplicados
     * @param detailText Descripción textual del cálculo
     */
    public GradeCalculationResult(
            double finalGrade,
            double weightedAverage,
            double attendancePenalty,
            double extraPointsApplied,
            String detailText) {
        
        this.finalGrade = finalGrade;
        this.weightedAverage = weightedAverage;
        this.attendancePenalty = attendancePenalty;
        this.extraPointsApplied = extraPointsApplied;
        this.detailText = detailText;
    }
    
    public double getFinalGrade() {
        return finalGrade;
    }
    
    public double getWeightedAverage() {
        return weightedAverage;
    }
    
    public double getAttendancePenalty() {
        return attendancePenalty;
    }
    
    public double getExtraPointsApplied() {
        return extraPointsApplied;
    }
    
    public String getDetailText() {
        return detailText;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Nota Final: %.2f\n" +
            "Promedio Ponderado: %.2f\n" +
            "Penalización por Asistencia: %.2f\n" +
            "Puntos Extra: %.2f\n" +
            "Detalle: %s",
            finalGrade, weightedAverage, attendancePenalty, extraPointsApplied, detailText
        );
    }
}
