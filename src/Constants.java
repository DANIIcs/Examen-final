/**
 * Constantes del sistema de cálculo de notas.
 * Define los límites y valores configurables para asegurar consistencia.
 */
public class Constants {
    // Límites de evaluaciones (RNF01)
    public static final int MAX_EVALUATIONS = 10;
    public static final int MIN_EVALUATIONS = 1;
    
    // Rango de calificaciones
    public static final double MIN_GRADE = 0.0;
    public static final double MAX_GRADE = 20.0;
    
    // Política de puntos extra (RF03)
    public static final double EXTRA_POINTS_BONUS = 1.0;
    
    // Política de asistencia (RF02)
    public static final double ATTENDANCE_PENALTY_GRADE = 0.0;
    
    // Tolerancia para validación de suma de pesos
    public static final double TOLERANCE_WEIGHT_SUM = 0.01;
    
    // Valor esperado para suma de pesos
    public static final double EXPECTED_WEIGHT_SUM = 100.0;
    
    private Constants() {
        // Prevenir instanciación
    }
}
