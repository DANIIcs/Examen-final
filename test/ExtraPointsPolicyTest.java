import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para ExtraPointsPolicy.
 * Verifica el cumplimiento de RF03 (puntos extra por año académico).
 */
class ExtraPointsPolicyTest {
    
    private ExtraPointsPolicy policy;
    private Map<Integer, Boolean> allYearsTeachers;
    
    @BeforeEach
    void setUp() {
        allYearsTeachers = new HashMap<>();
        allYearsTeachers.put(2023, true);
        allYearsTeachers.put(2024, false);
        allYearsTeachers.put(2025, true);
        
        policy = new ExtraPointsPolicy(allYearsTeachers);
    }
    
    @Test
    @DisplayName("shouldReturnTrueWhenYearHasExtraPointsEnabled")
    void shouldReturnTrueWhenYearHasExtraPointsEnabled() {
        assertTrue(policy.isExtraPointsEnabledForYear(2023));
        assertTrue(policy.isExtraPointsEnabledForYear(2025));
    }
    
    @Test
    @DisplayName("shouldReturnFalseWhenYearHasExtraPointsDisabled")
    void shouldReturnFalseWhenYearHasExtraPointsDisabled() {
        assertFalse(policy.isExtraPointsEnabledForYear(2024));
    }
    
    @Test
    @DisplayName("shouldReturnFalseWhenYearNotInMap")
    void shouldReturnFalseWhenYearNotInMap() {
        assertFalse(policy.isExtraPointsEnabledForYear(2022));
    }
    
    @Test
    @DisplayName("shouldApplyExtraPointsWhenYearEnabledAndStudentMeetsCriteria")
    void shouldApplyExtraPointsWhenYearEnabledAndStudentMeetsCriteria() {
        double currentGrade = 15.0;
        double result = policy.applyExtraPoints(currentGrade, 2023, true, 20.0);
        
        assertEquals(16.0, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldNotApplyExtraPointsWhenYearDisabled")
    void shouldNotApplyExtraPointsWhenYearDisabled() {
        double currentGrade = 15.0;
        double result = policy.applyExtraPoints(currentGrade, 2024, true, 20.0);
        
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldNotApplyExtraPointsWhenStudentDoesNotMeetCriteria")
    void shouldNotApplyExtraPointsWhenStudentDoesNotMeetCriteria() {
        double currentGrade = 15.0;
        double result = policy.applyExtraPoints(currentGrade, 2023, false, 20.0);
        
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldNotExceedMaxGradeWhenApplyingExtraPoints")
    void shouldNotExceedMaxGradeWhenApplyingExtraPoints() {
        double currentGrade = 19.8;
        double result = policy.applyExtraPoints(currentGrade, 2023, true, 20.0);
        
        assertEquals(20.0, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldCalculateExtraPointsAmount")
    void shouldCalculateExtraPointsAmount() {
        double amount = policy.getExtraPointsAmount(15.0, 16.0);
        assertEquals(1.0, amount, 0.001);
    }
    
    @Test
    @DisplayName("shouldHandleLowGradesWithExtraPoints")
    void shouldHandleLowGradesWithExtraPoints() {
        double result = policy.applyExtraPoints(5.0, 2023, true, 20.0);
        assertEquals(6.0, result, 0.001);
        
        double extraPoints = policy.getExtraPointsAmount(5.0, result);
        assertEquals(1.0, extraPoints, 0.001);
    }
    
    @Test
    @DisplayName("shouldHandleZeroGradeWithExtraPoints")
    void shouldHandleZeroGradeWithExtraPoints() {
        double result = policy.applyExtraPoints(0.0, 2023, true, 20.0);
        assertEquals(1.0, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldNotApplyExtraPointsToZeroWhenCriteriaNotMet")
    void shouldNotApplyExtraPointsToZeroWhenCriteriaNotMet() {
        double result = policy.applyExtraPoints(0.0, 2023, false, 20.0);
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldHandleUnknownYear")
    void shouldHandleUnknownYear() {
        double result = policy.applyExtraPoints(15.0, 2030, true, 20.0);
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldReturnZeroExtraPointsWhenNoChange")
    void shouldReturnZeroExtraPointsWhenNoChange() {
        double extraPoints = policy.getExtraPointsAmount(15.0, 15.0);
        assertEquals(0.0, extraPoints, 0.001);
    }
    
    @Test
    @DisplayName("shouldRespectMaxGradeBoundary")
    void shouldRespectMaxGradeBoundary() {
        double result = policy.applyExtraPoints(19.5, 2023, true, 20.0);
        assertEquals(20.0, result, 0.001);
        
        double extraPoints = policy.getExtraPointsAmount(19.5, result);
        assertEquals(0.5, extraPoints, 0.001);
    }
}
