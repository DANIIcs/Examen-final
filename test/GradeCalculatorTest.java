import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para GradeCalculator.
 * Verifica el cumplimiento de RF01-RF05 y RNF01-RNF04.
 */
class GradeCalculatorTest {
    
    private GradeCalculator calculator;
    private AttendancePolicy attendancePolicy;
    private ExtraPointsPolicy extraPointsPolicy;
    private Map<Integer, Boolean> allYearsTeachers;
    
    @BeforeEach
    void setUp() {
        attendancePolicy = new AttendancePolicy();
        
        allYearsTeachers = new HashMap<>();
        allYearsTeachers.put(2023, true);
        allYearsTeachers.put(2024, false);
        allYearsTeachers.put(2025, true);
        
        extraPointsPolicy = new ExtraPointsPolicy(allYearsTeachers);
        calculator = new GradeCalculator(attendancePolicy, extraPointsPolicy);
    }
    
    // ===== CASOS BÁSICOS =====
    
    @Test
    @DisplayName("shouldCalculateNormalGradeWhenAllConditionsValid")
    void shouldCalculateNormalGradeWhenAllConditionsValid() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 15.0, 30.0));
        evaluations.add(new Evaluation("PC2", 16.0, 30.0));
        evaluations.add(new Evaluation("Examen Final", 14.0, 40.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2024, false
        );
        
        GradeCalculationResult result = calculator.calculateFinalGrade(request);
        
        // Promedio: (15*30 + 16*30 + 14*40) / 100 = 14.9
        assertEquals(14.9, result.getWeightedAverage(), 0.001);
        assertEquals(0.0, result.getAttendancePenalty(), 0.001);
        assertEquals(0.0, result.getExtraPointsApplied(), 0.001);
        assertEquals(14.9, result.getFinalGrade(), 0.001);
    }
    
    @Test
    @DisplayName("shouldReturnZeroWhenAttendanceNotReached")
    void shouldReturnZeroWhenAttendanceNotReached() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 15.0, 30.0));
        evaluations.add(new Evaluation("PC2", 16.0, 30.0));
        evaluations.add(new Evaluation("Examen Final", 14.0, 40.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, false, 2024, false
        );
        
        GradeCalculationResult result = calculator.calculateFinalGrade(request);
        
        assertEquals(14.9, result.getWeightedAverage(), 0.001);
        assertEquals(14.9, result.getAttendancePenalty(), 0.001);
        assertEquals(0.0, result.getExtraPointsApplied(), 0.001);
        assertEquals(0.0, result.getFinalGrade(), 0.001);
    }
    
    @Test
    @DisplayName("shouldApplyExtraPointsWhenYearEnabledAndStudentMeetsCriteria")
    void shouldApplyExtraPointsWhenYearEnabledAndStudentMeetsCriteria() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 15.0, 50.0));
        evaluations.add(new Evaluation("Examen Final", 16.0, 50.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2023, true
        );
        
        GradeCalculationResult result = calculator.calculateFinalGrade(request);
        
        // Promedio: (15*50 + 16*50) / 100 = 15.5
        assertEquals(15.5, result.getWeightedAverage(), 0.001);
        assertEquals(0.0, result.getAttendancePenalty(), 0.001);
        assertEquals(1.0, result.getExtraPointsApplied(), 0.001);
        assertEquals(16.5, result.getFinalGrade(), 0.001);
    }
    
    @Test
    @DisplayName("shouldNotApplyExtraPointsWhenYearDisabled")
    void shouldNotApplyExtraPointsWhenYearDisabled() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 15.0, 50.0));
        evaluations.add(new Evaluation("Examen Final", 16.0, 50.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2024, true
        );
        
        GradeCalculationResult result = calculator.calculateFinalGrade(request);
        
        assertEquals(15.5, result.getWeightedAverage(), 0.001);
        assertEquals(0.0, result.getExtraPointsApplied(), 0.001);
        assertEquals(15.5, result.getFinalGrade(), 0.001);
    }
    
    @Test
    @DisplayName("shouldNotApplyExtraPointsWhenStudentDoesNotMeetCriteria")
    void shouldNotApplyExtraPointsWhenStudentDoesNotMeetCriteria() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 15.0, 50.0));
        evaluations.add(new Evaluation("Examen Final", 16.0, 50.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2023, false
        );
        
        GradeCalculationResult result = calculator.calculateFinalGrade(request);
        
        assertEquals(15.5, result.getWeightedAverage(), 0.001);
        assertEquals(0.0, result.getExtraPointsApplied(), 0.001);
        assertEquals(15.5, result.getFinalGrade(), 0.001);
    }
    
    // ===== CASOS BORDE =====
    
    @Test
    @DisplayName("shouldThrowExceptionWhenZeroEvaluations")
    void shouldThrowExceptionWhenZeroEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2024, false
        );
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculateFinalGrade(request)
        );
        
        assertTrue(exception.getMessage().contains("al menos"));
    }
    
    @Test
    @DisplayName("shouldThrowExceptionWhenMoreThan10Evaluations")
    void shouldThrowExceptionWhenMoreThan10Evaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            evaluations.add(new Evaluation("Eval" + i, 15.0, 9.09));
        }
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2024, false
        );
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculateFinalGrade(request)
        );
        
        assertTrue(exception.getMessage().contains("10") || 
                   exception.getMessage().contains("RNF01"));
    }
    
    @Test
    @DisplayName("shouldThrowExceptionWhenWeightSumIsNot100")
    void shouldThrowExceptionWhenWeightSumIsNot100() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 15.0, 30.0));
        evaluations.add(new Evaluation("PC2", 16.0, 30.0));
        evaluations.add(new Evaluation("Examen Final", 14.0, 35.0)); // Suma = 95
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2024, false
        );
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculateFinalGrade(request)
        );
        
        assertTrue(exception.getMessage().contains("suma") || 
                   exception.getMessage().contains("peso"));
    }
    
    @Test
    @DisplayName("shouldThrowExceptionWhenScoreIsNegative")
    void shouldThrowExceptionWhenScoreIsNegative() {
        // La validación debe fallar al crear Evaluation con nota negativa
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Evaluation("PC1", -5.0, 50.0);
        });
        
        assertTrue(exception.getMessage().contains("negativ"));
    }
    
    @Test
    @DisplayName("shouldThrowExceptionWhenScoreExceedsMaximum")
    void shouldThrowExceptionWhenScoreExceedsMaximum() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 25.0, 50.0));
        evaluations.add(new Evaluation("Examen Final", 16.0, 50.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2024, false
        );
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculateFinalGrade(request)
        );
        
        assertTrue(exception.getMessage().contains("mayor") || 
                   exception.getMessage().contains("20"));
    }
    
    @Test
    @DisplayName("shouldAcceptWeightSumWithinTolerance")
    void shouldAcceptWeightSumWithinTolerance() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 15.0, 33.33));
        evaluations.add(new Evaluation("PC2", 16.0, 33.33));
        evaluations.add(new Evaluation("Examen Final", 14.0, 33.34)); // Suma = 100.00
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2024, false
        );
        
        // No debe lanzar excepción
        assertDoesNotThrow(() -> calculator.calculateFinalGrade(request));
    }
    
    // ===== DETERMINISMO (RNF03) =====
    
    @Test
    @DisplayName("shouldReturnSameResultWhenCalledMultipleTimes")
    void shouldReturnSameResultWhenCalledMultipleTimes() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 15.0, 30.0));
        evaluations.add(new Evaluation("PC2", 16.0, 30.0));
        evaluations.add(new Evaluation("Examen Final", 14.0, 40.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2023, true
        );
        
        // Ejecutar múltiples veces
        GradeCalculationResult result1 = calculator.calculateFinalGrade(request);
        GradeCalculationResult result2 = calculator.calculateFinalGrade(request);
        GradeCalculationResult result3 = calculator.calculateFinalGrade(request);
        
        // Verificar que todos los resultados son idénticos
        assertEquals(result1.getFinalGrade(), result2.getFinalGrade(), 0.0001);
        assertEquals(result2.getFinalGrade(), result3.getFinalGrade(), 0.0001);
        assertEquals(result1.getWeightedAverage(), result2.getWeightedAverage(), 0.0001);
        assertEquals(result1.getAttendancePenalty(), result2.getAttendancePenalty(), 0.0001);
        assertEquals(result1.getExtraPointsApplied(), result2.getExtraPointsApplied(), 0.0001);
    }
    
    // ===== CASOS COMBINADOS =====
    
    @Test
    @DisplayName("shouldHandleCombinedScenarioWithAttendancePenaltyAndNoExtraPoints")
    void shouldHandleCombinedScenarioWithAttendancePenaltyAndNoExtraPoints() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 18.0, 50.0));
        evaluations.add(new Evaluation("Examen Final", 17.0, 50.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, false, 2024, true
        );
        
        GradeCalculationResult result = calculator.calculateFinalGrade(request);
        
        // Promedio: 17.5, pero penalizado a 0 por inasistencia (2024 no tiene extra points)
        assertEquals(17.5, result.getWeightedAverage(), 0.001);
        assertEquals(17.5, result.getAttendancePenalty(), 0.001);
        assertEquals(0.0, result.getExtraPointsApplied(), 0.001);
        assertEquals(0.0, result.getFinalGrade(), 0.001);
    }
    
    @Test
    @DisplayName("shouldNotExceedMaxGradeWithExtraPoints")
    void shouldNotExceedMaxGradeWithExtraPoints() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 19.5, 50.0));
        evaluations.add(new Evaluation("Examen Final", 19.5, 50.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2023, true
        );
        
        GradeCalculationResult result = calculator.calculateFinalGrade(request);
        
        // Promedio: 19.5, con bonus sería 20.5, pero se limita a 20
        assertEquals(19.5, result.getWeightedAverage(), 0.001);
        assertEquals(0.5, result.getExtraPointsApplied(), 0.001);
        assertEquals(20.0, result.getFinalGrade(), 0.001);
    }
    
    @Test
    @DisplayName("shouldComputeWeightedAverageCorrectly")
    void shouldComputeWeightedAverageCorrectly() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("PC1", 10.0, 20.0));
        evaluations.add(new Evaluation("PC2", 12.0, 20.0));
        evaluations.add(new Evaluation("PC3", 14.0, 20.0));
        evaluations.add(new Evaluation("Examen Final", 16.0, 40.0));
        
        double result = calculator.computeWeightedAverage(evaluations);
        
        // (10*20 + 12*20 + 14*20 + 16*40) / 100 = 13.6
        assertEquals(13.6, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldAcceptMaximumAllowedEvaluations")
    void shouldAcceptMaximumAllowedEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            evaluations.add(new Evaluation("Eval" + i, 15.0, 10.0));
        }
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2024, false
        );
        
        // No debe lanzar excepción
        assertDoesNotThrow(() -> calculator.calculateFinalGrade(request));
    }
    
    @Test
    @DisplayName("shouldAcceptSingleEvaluation")
    void shouldAcceptSingleEvaluation() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("Examen Único", 18.0, 100.0));
        
        GradeCalculationRequest request = new GradeCalculationRequest(
            "U202012345", evaluations, true, 2024, false
        );
        
        GradeCalculationResult result = calculator.calculateFinalGrade(request);
        
        assertEquals(18.0, result.getWeightedAverage(), 0.001);
        assertEquals(18.0, result.getFinalGrade(), 0.001);
    }
}
