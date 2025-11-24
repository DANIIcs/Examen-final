import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para AttendancePolicy.
 * Verifica el cumplimiento de RF02 (asistencia mínima).
 */
class AttendancePolicyTest {
    
    private final AttendancePolicy policy = new AttendancePolicy();
    
    @Test
    @DisplayName("shouldReturnSameGradeWhenAttendanceReached")
    void shouldReturnSameGradeWhenAttendanceReached() {
        double grade = 15.5;
        double result = policy.applyPenalty(grade, true);
        
        assertEquals(grade, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldReturnZeroWhenAttendanceNotReached")
    void shouldReturnZeroWhenAttendanceNotReached() {
        double grade = 15.5;
        double result = policy.applyPenalty(grade, false);
        
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    @DisplayName("shouldCalculateNoPenaltyWhenAttendanceReached")
    void shouldCalculateNoPenaltyWhenAttendanceReached() {
        double grade = 15.5;
        double penalty = policy.getPenaltyAmount(grade, true);
        
        assertEquals(0.0, penalty, 0.001);
    }
    
    @Test
    @DisplayName("shouldCalculateFullPenaltyWhenAttendanceNotReached")
    void shouldCalculateFullPenaltyWhenAttendanceNotReached() {
        double grade = 15.5;
        double penalty = policy.getPenaltyAmount(grade, false);
        
        assertEquals(15.5, penalty, 0.001);
    }
}
