# Requerimientos del Sistema CS-GradeCalculator

## Software Requerido

### Para Ejecutar la Aplicación
- **Java Development Kit (JDK)** 8 o superior
  - Descargar: https://www.oracle.com/java/technologies/downloads/
  - Verificar instalación: `java -version`

### Para Ejecutar Tests (Opcional)
- **JUnit 5 Platform Console Standalone**
  - Descargar JAR: https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.1/junit-platform-console-standalone-1.10.1.jar
  - Colocar en la raíz del proyecto

### Para Análisis de Código (Opcional)
- **SonarQube** o **SonarLint**
  - SonarQube Server: https://www.sonarqube.org/downloads/
  - SonarLint Plugin para VS Code

## Dependencias Java

El proyecto NO requiere dependencias externas para la aplicación principal.

### Para Tests:
```xml
<!-- Si usas Maven -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.1</version>
    <scope>test</scope>
</dependency>
```

## Estructura de Archivos

```
exaemn final/
├── src/                          # Código fuente
│   ├── Constants.java
│   ├── Evaluation.java
│   ├── GradeCalculationRequest.java
│   ├── GradeCalculationResult.java
│   ├── AttendancePolicy.java
│   ├── ExtraPointsPolicy.java
│   ├── GradeCalculator.java
│   └── GradeCalculatorApp.java
│
├── test/                         # Tests unitarios
│   ├── EvaluationTest.java
│   ├── AttendancePolicyTest.java
│   ├── ExtraPointsPolicyTest.java
│   └── GradeCalculatorTest.java
│
├── bin/                          # Compilados (generado)
├── README.md                     # Documentación principal
├── REQUIREMENTS.md               # Este archivo
├── run.ps1                       # Script para ejecutar
└── build.ps1                     # Script para compilar
```

## Comandos Rápidos

### Windows PowerShell

```powershell
# Compilar y ejecutar
.\run.ps1

# Solo compilar
.\build.ps1

# Compilar manualmente
javac -encoding UTF-8 -d bin src/*.java

# Ejecutar manualmente
java -cp bin GradeCalculatorApp
```

### Linux/Mac

```bash
# Compilar
javac -encoding UTF-8 -d bin src/*.java

# Ejecutar
java -cp bin GradeCalculatorApp
```

## Configuración del Sistema

### Constantes Configurables (src/Constants.java)

| Constante | Valor Actual | Descripción |
|-----------|--------------|-------------|
| `MAX_EVALUATIONS` | 10 | Máximo de evaluaciones |
| `MIN_EVALUATIONS` | 1 | Mínimo de evaluaciones |
| `MIN_GRADE` | 0.0 | Nota mínima |
| `MAX_GRADE` | 20.0 | Nota máxima |
| `EXTRA_POINTS_BONUS` | 1.0 | Puntos extra a otorgar |
| `TOLERANCE_WEIGHT_SUM` | 0.01 | Tolerancia en suma de pesos |

### Política de Años (GradeCalculatorApp.java, línea ~140)

```java
Map<Integer, Boolean> allYearsTeachers = new HashMap<>();
allYearsTeachers.put(2023, true);   // Modificar según necesidad
allYearsTeachers.put(2024, false);
allYearsTeachers.put(2025, true);
```

## Recursos Adicionales

- **Documentación Java**: https://docs.oracle.com/en/java/
- **JUnit 5 User Guide**: https://junit.org/junit5/docs/current/user-guide/
- **SonarQube Documentation**: https://docs.sonarqube.org/
