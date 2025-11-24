import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Constants.
 * Verifica que las constantes del sistema estén correctamente definidas.
 */
class ConstantsTest {
    
    @Test
    @DisplayName("shouldHaveCorrectMaxEvaluations")
    void shouldHaveCorrectMaxEvaluations() {
        assertEquals(10, Constants.MAX_EVALUATIONS);
    }
    
    @Test
    @DisplayName("shouldHaveCorrectMinEvaluations")
    void shouldHaveCorrectMinEvaluations() {
        assertEquals(1, Constants.MIN_EVALUATIONS);
    }
    
    @Test
    @DisplayName("shouldHaveCorrectGradeLimits")
    void shouldHaveCorrectGradeLimits() {
        assertEquals(0.0, Constants.MIN_GRADE, 0.001);
        assertEquals(20.0, Constants.MAX_GRADE, 0.001);
    }
    
    @Test
    @DisplayName("shouldHaveCorrectWeightSum")
    void shouldHaveCorrectWeightSum() {
        assertEquals(100.0, Constants.EXPECTED_WEIGHT_SUM, 0.001);
    }
    
    @Test
    @DisplayName("shouldHaveReasonableTolerance")
    void shouldHaveReasonableTolerance() {
        assertTrue(Constants.TOLERANCE_WEIGHT_SUM > 0);
        assertTrue(Constants.TOLERANCE_WEIGHT_SUM < 1.0);
    }
    
    @Test
    @DisplayName("shouldHaveCorrectExtraPoints")
    void shouldHaveCorrectExtraPoints() {
        assertEquals(1.0, Constants.EXTRA_POINTS_BONUS, 0.001);
    }
    
    @Test
    @DisplayName("shouldHaveCorrectAttendancePenalty")
    void shouldHaveCorrectAttendancePenalty() {
        assertEquals(0.0, Constants.ATTENDANCE_PENALTY_GRADE, 0.001);
    }
    
    @Test
    @DisplayName("shouldHaveLogicalConstraints")
    void shouldHaveLogicalConstraints() {
        // MIN_EVALUATIONS debe ser menor que MAX_EVALUATIONS
        assertTrue(Constants.MIN_EVALUATIONS < Constants.MAX_EVALUATIONS);
        
        // MIN_GRADE debe ser menor que MAX_GRADE
        assertTrue(Constants.MIN_GRADE < Constants.MAX_GRADE);
        
        // La tolerancia debe ser positiva y razonable
        assertTrue(Constants.TOLERANCE_WEIGHT_SUM > 0);
        assertTrue(Constants.TOLERANCE_WEIGHT_SUM <= 1.0);
    }
}
