import java.util.List;

/**
 * Calculadora principal de notas finales.
 * Responsabilidad: Coordinar el cálculo de nota final aplicando todas las políticas (RF01-RF05).
 * Diseño stateless para soportar concurrencia (RNF02).
 */
public class GradeCalculator {
    private final AttendancePolicy attendancePolicy;
    private final ExtraPointsPolicy extraPointsPolicy;
    
    /**
     * Crea una calculadora de notas con las políticas especificadas.
     * @param attendancePolicy Política de asistencia
     * @param extraPointsPolicy Política de puntos extra
     */
    public GradeCalculator(
            AttendancePolicy attendancePolicy,
            ExtraPointsPolicy extraPointsPolicy) {
        
        this.attendancePolicy = attendancePolicy;
        this.extraPointsPolicy = extraPointsPolicy;
    }
    
    /**
     * Valida que la solicitud cumpla todos los requisitos.
     * @param request Solicitud a validar
     * @throws IllegalArgumentException si la validación falla
     */
    public void validateRequest(GradeCalculationRequest request) {
        List<Evaluation> evaluations = request.getEvaluations();
        
        // RNF01: Validar cantidad de evaluaciones (1-10)
        if (evaluations.size() < Constants.MIN_EVALUATIONS) {
            throw new IllegalArgumentException(
                "Debe haber al menos " + Constants.MIN_EVALUATIONS + " evaluación");
        }
        if (evaluations.size() > Constants.MAX_EVALUATIONS) {
            throw new IllegalArgumentException(
                "No puede haber más de " + Constants.MAX_EVALUATIONS + " evaluaciones (RNF01)");
        }
        
        // Validar cada evaluación
        double weightSum = 0.0;
        for (Evaluation eval : evaluations) {
            // Validar rango de notas
            if (eval.getScore() < Constants.MIN_GRADE) {
                throw new IllegalArgumentException(
                    String.format("La nota de '%s' no puede ser menor a %.1f",
                        eval.getName(), Constants.MIN_GRADE));
            }
            if (eval.getScore() > Constants.MAX_GRADE) {
                throw new IllegalArgumentException(
                    String.format("La nota de '%s' no puede ser mayor a %.1f",
                        eval.getName(), Constants.MAX_GRADE));
            }
            
            // Validar peso
            if (eval.getWeightPercentage() < 0) {
                throw new IllegalArgumentException(
                    String.format("El peso de '%s' no puede ser negativo",
                        eval.getName()));
            }
            
            weightSum += eval.getWeightPercentage();
        }
        
        // RNF03: Validar suma de pesos (determinista)
        double difference = Math.abs(weightSum - Constants.EXPECTED_WEIGHT_SUM);
        if (difference > Constants.TOLERANCE_WEIGHT_SUM) {
            throw new IllegalArgumentException(
                String.format("La suma de pesos debe ser %.1f%% (actual: %.2f%%)",
                    Constants.EXPECTED_WEIGHT_SUM, weightSum));
        }
    }
    
    /**
     * Calcula el promedio ponderado de las evaluaciones.
     * @param evaluations Lista de evaluaciones
     * @return Promedio ponderado
     */
    public double computeWeightedAverage(List<Evaluation> evaluations) {
        double weightedSum = 0.0;
        
        for (Evaluation eval : evaluations) {
            weightedSum += eval.getScore() * eval.getWeightPercentage();
        }
        
        return weightedSum / Constants.EXPECTED_WEIGHT_SUM;
    }
    
    /**
     * Calcula la nota final del estudiante aplicando todas las políticas.
     * Método stateless - no modifica estado interno (RNF02).
     * Cálculo determinista - mismo input produce mismo output (RNF03).
     * Tiempo < 300ms - sin I/O bloqueante (RNF04).
     * 
     * @param request Solicitud con todos los datos del estudiante
     * @return Resultado del cálculo con detalles completos
     * @throws IllegalArgumentException si la solicitud es inválida
     */
    public GradeCalculationResult calculateFinalGrade(GradeCalculationRequest request) {
        // Validar solicitud
        validateRequest(request);
        
        // Calcular promedio ponderado
        double weightedAverage = computeWeightedAverage(request.getEvaluations());
        
        // Aplicar política de asistencia (RF02)
        double gradeAfterAttendance = attendancePolicy.applyPenalty(
            weightedAverage,
            request.hasReachedMinimumClasses()
        );
        double attendancePenalty = attendancePolicy.getPenaltyAmount(
            weightedAverage,
            request.hasReachedMinimumClasses()
        );
        
        // Aplicar política de puntos extra (RF03)
        double finalGrade = extraPointsPolicy.applyExtraPoints(
            gradeAfterAttendance,
            request.getAcademicYear(),
            request.studentMeetsExtraCriteria(),
            Constants.MAX_GRADE
        );
        double extraPointsApplied = extraPointsPolicy.getExtraPointsAmount(
            gradeAfterAttendance,
            finalGrade
        );
        
        // Construir texto detallado (RF05)
        StringBuilder detailText = new StringBuilder();
        detailText.append(String.format("Promedio ponderado: %.2f", weightedAverage));
        
        if (attendancePenalty > 0) {
            detailText.append(String.format(" | Penalización por inasistencia: -%.2f", attendancePenalty));
        } else {
            detailText.append(" | Asistencia: cumplida");
        }
        
        if (extraPointsApplied > 0) {
            detailText.append(String.format(" | Puntos extra: +%.2f", extraPointsApplied));
        } else {
            detailText.append(" | Sin puntos extra");
        }
        
        detailText.append(String.format(" | Nota final: %.2f", finalGrade));
        
        return new GradeCalculationResult(
            finalGrade,
            weightedAverage,
            attendancePenalty,
            extraPointsApplied,
            detailText.toString()
        );
    }
}
