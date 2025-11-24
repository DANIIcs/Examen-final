import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Aplicación de consola para el sistema de cálculo de notas (CS-GradeCalculator).
 * Implementa el Caso de Uso CU001: Calcular nota final del estudiante.
 * Responsabilidad: Interacción con el usuario - solo UI, sin lógica de negocio.
 */
public class GradeCalculatorApp {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("  CS-GradeCalculator");
            System.out.println("  Sistema de Cálculo de Nota Final");
            System.out.println("========================================");
            System.out.println();
            
            // CU001 - Paso 1: Solicitar identificador del estudiante
            System.out.print("Ingrese el código del estudiante: ");
            String studentId = scanner.nextLine().trim();
            
            if (studentId.isEmpty()) {
                System.out.println("Error: El código del estudiante es requerido.");
                return;
            }
            
            // CU001 - Paso 2: Solicitar cantidad de evaluaciones
            System.out.print("\n¿Cuántas evaluaciones desea registrar? (máximo " + 
                Constants.MAX_EVALUATIONS + "): ");
            int numEvaluations;
            try {
                numEvaluations = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                return;
            }
            
            if (numEvaluations < Constants.MIN_EVALUATIONS || 
                numEvaluations > Constants.MAX_EVALUATIONS) {
                System.out.println("Error: La cantidad debe estar entre " + 
                    Constants.MIN_EVALUATIONS + " y " + Constants.MAX_EVALUATIONS);
                return;
            }
            
            // CU001 - Paso 3: Registrar evaluaciones
            List<Evaluation> evaluations = new ArrayList<>();
            System.out.println("\n--- Registro de Evaluaciones ---");
            
            for (int i = 1; i <= numEvaluations; i++) {
                System.out.println("\nEvaluación #" + i + ":");
                
                System.out.print("  Nombre (ej: PC1, Examen Final): ");
                String name = scanner.nextLine().trim();
                
                System.out.print("  Nota obtenida (0-" + Constants.MAX_GRADE + "): ");
                double score;
                try {
                    score = Double.parseDouble(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debe ingresar un número válido.");
                    return;
                }
                
                System.out.print("  Peso en porcentaje (%): ");
                double weight;
                try {
                    weight = Double.parseDouble(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debe ingresar un número válido.");
                    return;
                }
                
                try {
                    evaluations.add(new Evaluation(name, score, weight));
                    System.out.println("  ✓ Evaluación registrada: " + name);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
            }
            
            // CU001 - Paso 4: Verificar asistencia mínima
            System.out.print("\n¿El estudiante cumplió con la asistencia mínima? (S/N): ");
            String attendanceResponse = scanner.nextLine().trim().toUpperCase();
            boolean hasReachedMinimumClasses = attendanceResponse.equals("S") || 
                                               attendanceResponse.equals("SI") ||
                                               attendanceResponse.equals("SÍ");
            
            // CU001 - Paso 5: Solicitar año académico
            System.out.print("\nIngrese el año académico (ej: 2024): ");
            int academicYear;
            try {
                academicYear = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un año válido.");
                return;
            }
            
            // CU001 - Paso 6: Verificar criterios para puntos extra
            System.out.print("\n¿El estudiante cumple los criterios para puntos extra? (S/N): ");
            String extraPointsResponse = scanner.nextLine().trim().toUpperCase();
            boolean studentMeetsExtraCriteria = extraPointsResponse.equals("S") || 
                                                extraPointsResponse.equals("SI") ||
                                                extraPointsResponse.equals("SÍ");
            
            // Configurar política de puntos extra por año (RF03)
            // En un sistema real, esto vendría de una base de datos o configuración
            Map<Integer, Boolean> allYearsTeachers = new HashMap<>();
            allYearsTeachers.put(2023, true);   // 2023: política activa
            allYearsTeachers.put(2024, false);  // 2024: política inactiva
            allYearsTeachers.put(2025, true);   // 2025: política activa
            
            // CU001 - Paso 7: Crear solicitud
            GradeCalculationRequest request = new GradeCalculationRequest(
                studentId,
                evaluations,
                hasReachedMinimumClasses,
                academicYear,
                studentMeetsExtraCriteria
            );
            
            // CU001 - Paso 8: Calcular nota final
            AttendancePolicy attendancePolicy = new AttendancePolicy();
            ExtraPointsPolicy extraPointsPolicy = new ExtraPointsPolicy(allYearsTeachers);
            GradeCalculator calculator = new GradeCalculator(attendancePolicy, extraPointsPolicy);
            
            GradeCalculationResult result = calculator.calculateFinalGrade(request);
            
            // CU001 - Paso 9: Mostrar resultado detallado (RF05)
            System.out.println("\n========================================");
            System.out.println("  RESULTADO DEL CÁLCULO");
            System.out.println("========================================");
            System.out.println("Estudiante: " + studentId);
            System.out.println("Año académico: " + academicYear);
            System.out.println();
            
            System.out.println("--- Evaluaciones Registradas ---");
            for (Evaluation eval : evaluations) {
                System.out.println("  " + eval);
            }
            System.out.println();
            
            System.out.println("--- Cálculo Detallado ---");
            System.out.printf("Promedio Ponderado: %.2f\n", result.getWeightedAverage());
            
            if (result.getAttendancePenalty() > 0) {
                System.out.printf("Penalización por Inasistencia: -%.2f\n", 
                    result.getAttendancePenalty());
                System.out.println("  (El estudiante NO cumplió con la asistencia mínima)");
            } else {
                System.out.println("Asistencia: ✓ Cumplida (sin penalización)");
            }
            
            if (result.getExtraPointsApplied() > 0) {
                System.out.printf("Puntos Extra Aplicados: +%.2f\n", 
                    result.getExtraPointsApplied());
                System.out.println("  (Política activa para año " + academicYear + 
                    " y estudiante cumple criterios)");
            } else {
                if (extraPointsPolicy.isExtraPointsEnabledForYear(academicYear)) {
                    if (!studentMeetsExtraCriteria) {
                        System.out.println("Puntos Extra: No aplicados (estudiante no cumple criterios)");
                    }
                } else {
                    System.out.println("Puntos Extra: No aplicados (política inactiva para año " + 
                        academicYear + ")");
                }
            }
            
            System.out.println();
            System.out.println("========================================");
            System.out.printf("  NOTA FINAL: %.2f / %.1f\n", 
                result.getFinalGrade(), Constants.MAX_GRADE);
            System.out.println("========================================");
            System.out.println();
            System.out.println("Detalle completo:");
            System.out.println(result.getDetailText());
            
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
