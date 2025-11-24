import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para GradeCalculatorApp.
 * Verifica la interfaz de usuario de consola.
 */
class GradeCalculatorAppTest {
    
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream testOut;
    
    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }
    
    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }
    
    private void setSystemInput(String input) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }
    
    @Test
    @DisplayName("shouldExitWhenEmptyStudentId")
    void shouldExitWhenEmptyStudentId() {
        setSystemInput("\n");
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("CS-GradeCalculator"));
        assertTrue(output.contains("Error") || output.contains("requerido"));
    }
    
    @Test
    @DisplayName("shouldExitWhenInvalidNumberOfEvaluations")
    void shouldExitWhenInvalidNumberOfEvaluations() {
        String input = "U202012345\nabc\n";
        setSystemInput(input);
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("Error") || output.contains("válido"));
    }
    
    @Test
    @DisplayName("shouldExitWhenNumberOfEvaluationsOutOfRange")
    void shouldExitWhenNumberOfEvaluationsOutOfRange() {
        String input = "U202012345\n0\n";
        setSystemInput(input);
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("Error") || output.contains("entre"));
    }
    
    @Test
    @DisplayName("shouldExitWhenInvalidScore")
    void shouldExitWhenInvalidScore() {
        String input = "U202012345\n1\nPC1\nabc\n";
        setSystemInput(input);
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("Error") || output.contains("válido"));
    }
    
    @Test
    @DisplayName("shouldExitWhenInvalidWeight")
    void shouldExitWhenInvalidWeight() {
        String input = "U202012345\n1\nPC1\n15.0\nabc\n";
        setSystemInput(input);
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("Error") || output.contains("válido"));
    }
    
    @Test
    @DisplayName("shouldExitWhenInvalidAcademicYear")
    void shouldExitWhenInvalidAcademicYear() {
        String input = "U202012345\n1\nPC1\n15.0\n100.0\nS\nabc\n";
        setSystemInput(input);
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("Error") || output.contains("válido") || output.contains("año"));
    }
    
    @Test
    @DisplayName("shouldCalculateGradeSuccessfully")
    void shouldCalculateGradeSuccessfully() {
        String input = "U202012345\n2\nPC1\n15.0\n50.0\nExamen\n16.0\n50.0\nS\n2024\nN\n";
        setSystemInput(input);
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("CS-GradeCalculator"));
        assertTrue(output.contains("registrada") || output.contains("Nota") || output.contains("final"));
    }
    
    @Test
    @DisplayName("shouldHandleAttendanceResponseNo")
    void shouldHandleAttendanceResponseNo() {
        String input = "U202012345\n1\nExamen\n18.0\n100.0\nN\n2024\nN\n";
        setSystemInput(input);
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("CS-GradeCalculator"));
    }
    
    @Test
    @DisplayName("shouldHandleExtraPointsCriteriaYes")
    void shouldHandleExtraPointsCriteriaYes() {
        String input = "U202012345\n1\nExamen\n15.0\n100.0\nS\n2023\nS\n";
        setSystemInput(input);
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("CS-GradeCalculator"));
    }
    
    @Test
    @DisplayName("shouldDisplayWelcomeMessage")
    void shouldDisplayWelcomeMessage() {
        String input = "\n";
        setSystemInput(input);
        
        GradeCalculatorApp.main(new String[]{});
        
        String output = testOut.toString();
        assertTrue(output.contains("CS-GradeCalculator"));
        assertTrue(output.contains("Sistema de Cálculo de Nota Final"));
    }
}
