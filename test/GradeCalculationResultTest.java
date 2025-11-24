import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para GradeCalculationResult.
 * Verifica el DTO de salida y su método toString.
 */
class GradeCalculationResultTest {
    
    @Test
    @DisplayName("shouldCreateResultWithAllFields")
    void shouldCreateResultWithAllFields() {
        GradeCalculationResult result = new GradeCalculationResult(
            16.5,
            15.5,
            0.0,
            1.0,
            "Promedio: 15.5 | Extra: +1.0 | Final: 16.5"
        );
        
        assertEquals(16.5, result.getFinalGrade(), 0.001);
        assertEquals(15.5, result.getWeightedAverage(), 0.001);
        assertEquals(0.0, result.getAttendancePenalty(), 0.001);
        assertEquals(1.0, result.getExtraPointsApplied(), 0.001);
        assertTrue(result.getDetailText().contains("15.5"));
    }
    
    @Test
    @DisplayName("shouldFormatToStringCorrectly")
    void shouldFormatToStringCorrectly() {
        GradeCalculationResult result = new GradeCalculationResult(
            14.9,
            14.9,
            0.0,
            0.0,
            "Detalle completo"
        );
        
        String output = result.toString();
        
        // Verificar que toString retorna algo
        assertNotNull(output);
        assertFalse(output.isEmpty());
    }
    
    @Test
    @DisplayName("shouldHandleZeroGrade")
    void shouldHandleZeroGrade() {
        GradeCalculationResult result = new GradeCalculationResult(
            0.0,
            14.5,
            14.5,
            0.0,
            "Penalización por inasistencia"
        );
        
        assertEquals(0.0, result.getFinalGrade(), 0.001);
        assertEquals(14.5, result.getAttendancePenalty(), 0.001);
    }
    
    @Test
    @DisplayName("shouldHandleMaximumGrade")
    void shouldHandleMaximumGrade() {
        GradeCalculationResult result = new GradeCalculationResult(
            20.0,
            19.5,
            0.0,
            0.5,
            "Nota máxima alcanzada"
        );
        
        assertEquals(20.0, result.getFinalGrade(), 0.001);
        assertEquals(0.5, result.getExtraPointsApplied(), 0.001);
    }
    
    @Test
    @DisplayName("shouldContainAllRelevantInfoInDetailText")
    void shouldContainAllRelevantInfoInDetailText() {
        String detailText = "Promedio ponderado: 15.50 | Asistencia: cumplida | Puntos extra: +1.00 | Nota final: 16.50";
        
        GradeCalculationResult result = new GradeCalculationResult(
            16.5, 15.5, 0.0, 1.0, detailText
        );
        
        String detail = result.getDetailText();
        assertTrue(detail.contains("15.50"));
        assertTrue(detail.contains("cumplida"));
        assertTrue(detail.contains("1.00"));
        assertTrue(detail.contains("16.50"));
    }
    
    @Test
    @DisplayName("shouldHandlePenaltyDetailText")
    void shouldHandlePenaltyDetailText() {
        String detailText = "Promedio ponderado: 14.00 | Penalización por inasistencia: -14.00 | Sin puntos extra | Nota final: 0.00";
        
        GradeCalculationResult result = new GradeCalculationResult(
            0.0, 14.0, 14.0, 0.0, detailText
        );
        
        String detail = result.getDetailText();
        assertTrue(detail.contains("Penalización") || detail.contains("penalización"));
        assertTrue(detail.contains("0.00"));
    }
}
