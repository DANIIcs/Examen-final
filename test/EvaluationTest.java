import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Evaluation.
 */
class EvaluationTest {
    
    @Test
    @DisplayName("shouldCreateValidEvaluationWhenAllParametersAreCorrect")
    void shouldCreateValidEvaluationWhenAllParametersAreCorrect() {
        Evaluation eval = new Evaluation("PC1", 15.5, 30.0);
        
        assertEquals("PC1", eval.getName());
        assertEquals(15.5, eval.getScore(), 0.001);
        assertEquals(30.0, eval.getWeightPercentage(), 0.001);
    }
    
    @Test
    @DisplayName("shouldThrowExceptionWhenNameIsEmpty")
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Evaluation("", 15.0, 30.0);
        });
    }
    
    @Test
    @DisplayName("shouldThrowExceptionWhenScoreIsNegative")
    void shouldThrowExceptionWhenScoreIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Evaluation("PC1", -5.0, 30.0);
        });
    }
    
    @Test
    @DisplayName("shouldThrowExceptionWhenWeightIsNegative")
    void shouldThrowExceptionWhenWeightIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Evaluation("PC1", 15.0, -10.0);
        });
    }
    
    @Test
    @DisplayName("shouldAcceptZeroScoreAsValid")
    void shouldAcceptZeroScoreAsValid() {
        Evaluation eval = new Evaluation("PC1", 0.0, 30.0);
        assertEquals(0.0, eval.getScore(), 0.001);
    }
}
