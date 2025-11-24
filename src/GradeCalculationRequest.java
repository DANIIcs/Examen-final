import java.util.ArrayList;
import java.util.List;

/**
 * Encapsula todos los datos de entrada necesarios para calcular la nota final.
 * Responsabilidad: Agrupar toda la información requerida para el cálculo (RF01-RF03).
 */
public class GradeCalculationRequest {
    private final String studentId;
    private final List<Evaluation> evaluations;
    private final boolean hasReachedMinimumClasses;
    private final int academicYear;
    private final boolean studentMeetsExtraCriteria;
    
    /**
     * Crea una solicitud de cálculo de nota final.
     * @param studentId Identificador del estudiante
     * @param evaluations Lista de evaluaciones del estudiante
     * @param hasReachedMinimumClasses Si cumple con la asistencia mínima requerida
     * @param academicYear Año académico
     * @param studentMeetsExtraCriteria Si cumple criterios para puntos extra
     */
    public GradeCalculationRequest(
            String studentId,
            List<Evaluation> evaluations,
            boolean hasReachedMinimumClasses,
            int academicYear,
            boolean studentMeetsExtraCriteria) {
        
        this.studentId = studentId;
        this.evaluations = new ArrayList<>(evaluations); // Copia defensiva
        this.hasReachedMinimumClasses = hasReachedMinimumClasses;
        this.academicYear = academicYear;
        this.studentMeetsExtraCriteria = studentMeetsExtraCriteria;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public List<Evaluation> getEvaluations() {
        return new ArrayList<>(evaluations); // Copia defensiva
    }
    
    public boolean hasReachedMinimumClasses() {
        return hasReachedMinimumClasses;
    }
    
    public int getAcademicYear() {
        return academicYear;
    }
    
    public boolean studentMeetsExtraCriteria() {
        return studentMeetsExtraCriteria;
    }
}
