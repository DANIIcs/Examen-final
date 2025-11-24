# Sistema CS-GradeCalculator

Sistema de cálculo de nota final para estudiantes, implementando evaluaciones ponderadas, validación de asistencia mínima y políticas de puntos extra por año académico.

## 📋 Descripción

CS-GradeCalculator es una aplicación de consola orientada a objetos que permite a los docentes de UTEC calcular la nota final de un estudiante considerando:
- Evaluaciones con notas y pesos porcentuales
- Asistencia mínima requerida
- Políticas de puntos extra definidas por año académico

## 🎯 Requisitos Funcionales Implementados

### ✅ RF01: Registro de Evaluaciones
Como docente podré registrar las evaluaciones de un estudiante, indicando para cada una:
- Nota obtenida (0-20)
- Porcentaje de peso sobre la nota final
- Variable: `examsStudents` (implementada como `List<Evaluation>`)

### ✅ RF02: Validación de Asistencia Mínima
Como docente podré registrar si el estudiante cumplió la asistencia mínima requerida por el reglamento académico de UTEC.
- Variable: `hasReachedMinimumClasses` (boolean)
- Penalización: Nota final = 0 si no cumple

### ✅ RF03: Política de Puntos Extra
Como docente podré registrar, para cada año académico, si los docentes del curso están de acuerdo en otorgar puntos extra a los estudiantes que cumplan ciertos criterios.
- Variable: `allYearsTeachers` (Map<Integer, Boolean>)
- Bonificación: +1.0 punto (sin exceder 20.0)

### ✅ RF04: Cálculo de Nota Final
Como docente podré solicitar el cálculo de la nota final de un estudiante, considerando evaluaciones, asistencia mínima y políticas de puntos extra.

### ✅ RF05: Detalle del Cálculo
Como docente podré visualizar el detalle del cálculo: promedio ponderado, penalización por inasistencias y puntos extra aplicados.

## ⚙️ Requisitos No Funcionales Cumplidos

### ✅ RNF01: Límite de Evaluaciones
La cantidad máxima de evaluaciones por estudiante será de **10**.

### ✅ RNF02: Concurrencia (50 usuarios)
El sistema está diseñado con lógica **stateless** en `GradeCalculator`, permitiendo atender múltiples solicitudes simultáneas sin conflictos de estado.

### ✅ RNF03: Determinismo
El cálculo de la nota final es **determinista**: con los mismos datos de entrada siempre se genera la misma nota final. No se utilizan fuentes aleatorias ni timestamps.

### ✅ RNF04: Tiempo de Respuesta
El tiempo de cálculo es menor a **300 ms** por solicitud. La complejidad es O(n) con n ≤ 10, sin operaciones de I/O bloqueantes.

## 🏗️ Arquitectura y Diseño

### Estructura de Clases

```
CS-GradeCalculator/
│
├── src/
│   ├── Constants.java              # Constantes del sistema
│   ├── Evaluation.java             # Modelo: Evaluación individual
│   ├── GradeCalculationRequest.java # Modelo: Solicitud de cálculo
│   ├── GradeCalculationResult.java  # Modelo: Resultado del cálculo
│   ├── AttendancePolicy.java       # Política de asistencia (RF02)
│   ├── ExtraPointsPolicy.java      # Política de puntos extra (RF03)
│   ├── GradeCalculator.java        # Calculadora principal (RF01-RF05)
│   └── GradeCalculatorApp.java     # Interfaz de consola (CU001)
│
└── test/
    ├── EvaluationTest.java
    ├── AttendancePolicyTest.java
    ├── ExtraPointsPolicyTest.java
    └── GradeCalculatorTest.java
```

### Principios de Diseño Aplicados

1. **Separación de Responsabilidades**: Cada clase tiene una responsabilidad única y bien definida.
2. **Inyección de Dependencias**: `GradeCalculator` recibe políticas como parámetros.
3. **Inmutabilidad**: Objetos de modelo son inmutables con copias defensivas.
4. **Diseño Stateless**: `GradeCalculator` no mantiene estado entre llamadas (RNF02).
5. **Validación Exhaustiva**: Validaciones tempranas con mensajes claros.

## 🚀 Cómo Ejecutar

### Prerrequisitos
- Java Development Kit (JDK) 8 o superior
- JUnit 5 (para ejecutar tests)

### Compilar el Proyecto

```powershell
cd "c:\Users\User\VS-CODE\Ing soft\exaemn final"

# Compilar todas las clases
javac -d bin src/*.java
```

### Ejecutar la Aplicación

```powershell
# Desde la raíz del proyecto
java -cp bin GradeCalculatorApp
```

### Ejecutar Tests

```powershell
# Con JUnit 5
java -jar junit-platform-console-standalone.jar --class-path bin --scan-class-path
```

## 📝 Caso de Uso: CU001 - Calcular Nota Final

**Actor**: Docente UTEC

**Flujo Principal**:
1. El docente ingresa a la aplicación.
2. La aplicación solicita el código del estudiante.
3. El docente registra o revisa las evaluaciones con sus notas y pesos.
4. El docente indica si el estudiante alcanzó la asistencia mínima.
5. La aplicación consulta la política de puntos extra definida colectivamente.
6. La aplicación calcula la nota final del estudiante.
7. La aplicación muestra la nota final y el detalle del cálculo.

## 🧪 Cobertura de Pruebas

### Tests Implementados

**EvaluationTest** (5 tests)
- Creación válida de evaluaciones
- Validación de parámetros inválidos

**AttendancePolicyTest** (4 tests)
- Aplicación correcta de penalización
- Cálculo de monto de penalización

**ExtraPointsPolicyTest** (8 tests)
- Verificación de años habilitados
- Aplicación de puntos extra según criterios
- Límite máximo de nota

**GradeCalculatorTest** (17 tests)
- Cálculo normal de notas
- Casos con asistencia insuficiente
- Casos con/sin puntos extra
- Validaciones de límites (RNF01)
- Validaciones de rangos de notas
- Determinismo (RNF03)
- Casos combinados y borde

**Total: 34 tests unitarios**

### Casos de Prueba Clave

✅ Cálculo normal (asistencia OK, sin extra)  
✅ Penalización por inasistencia → nota final = 0  
✅ Aplicación de puntos extra (+1.0)  
✅ Sin puntos extra cuando año no habilitado  
✅ Sin puntos extra cuando estudiante no cumple criterios  
✅ 0 evaluaciones → excepción  
✅ Más de 10 evaluaciones → excepción (RNF01)  
✅ Suma de pesos ≠ 100% → excepción  
✅ Notas negativas → excepción  
✅ Notas > 20 → excepción  
✅ Determinismo: mismo input → mismo output (100 iteraciones)  
✅ No exceder nota máxima con bonificación  

## 📊 Constantes del Sistema

| Constante | Valor | Descripción |
|-----------|-------|-------------|
| `MAX_EVALUATIONS` | 10 | Máximo de evaluaciones permitidas (RNF01) |
| `MIN_EVALUATIONS` | 1 | Mínimo de evaluaciones requeridas |
| `MIN_GRADE` | 0.0 | Nota mínima válida |
| `MAX_GRADE` | 20.0 | Nota máxima válida |
| `EXTRA_POINTS_BONUS` | 1.0 | Puntos extra a otorgar |
| `ATTENDANCE_PENALTY_GRADE` | 0.0 | Nota resultante por inasistencia |
| `TOLERANCE_WEIGHT_SUM` | 0.01 | Tolerancia para suma de pesos (≈100%) |

## 🔍 Ejemplo de Ejecución

```
========================================
  CS-GradeCalculator
  Sistema de Cálculo de Nota Final
========================================

Ingrese el código del estudiante: U202012345

¿Cuántas evaluaciones desea registrar? (máximo 10): 3

--- Registro de Evaluaciones ---

Evaluación #1:
  Nombre (ej: PC1, Examen Final): PC1
  Nota obtenida (0-20): 15
  Peso en porcentaje (%): 30
  ✓ Evaluación registrada: PC1

Evaluación #2:
  Nombre (ej: PC1, Examen Final): PC2
  Nota obtenida (0-20): 16
  Peso en porcentaje (%): 30
  ✓ Evaluación registrada: PC2

Evaluación #3:
  Nombre (ej: PC1, Examen Final): Examen Final
  Nota obtenida (0-20): 14
  Peso en porcentaje (%): 40
  ✓ Evaluación registrada: Examen Final

¿El estudiante cumplió con la asistencia mínima? (S/N): S

Ingrese el año académico (ej: 2024): 2025

¿El estudiante cumple los criterios para puntos extra? (S/N): S

========================================
  RESULTADO DEL CÁLCULO
========================================
Estudiante: U202012345
Año académico: 2025

--- Evaluaciones Registradas ---
  PC1: 15.00 (peso: 30.0%)
  PC2: 16.00 (peso: 30.0%)
  Examen Final: 14.00 (peso: 40.0%)

--- Cálculo Detallado ---
Promedio Ponderado: 14.90
Asistencia: ✓ Cumplida (sin penalización)
Puntos Extra Aplicados: +1.00
  (Política activa para año 2025 y estudiante cumple criterios)

========================================
  NOTA FINAL: 15.90 / 20.0
========================================

Detalle completo:
Promedio ponderado: 14.90 | Asistencia: cumplida | Puntos extra: +1.00 | Nota final: 15.90
```

## 📐 Diagrama UML (Conceptual)

```
┌─────────────────┐
│   Constants     │
│  <<static>>     │
└─────────────────┘

┌─────────────────┐       ┌─────────────────────────┐
│   Evaluation    │       │ GradeCalculationRequest │
├─────────────────┤       ├─────────────────────────┤
│ - name: String  │◄──────│ - studentId: String     │
│ - score: double │       │ - evaluations: List     │
│ - weight: double│       │ - hasReachedMin: bool   │
└─────────────────┘       │ - academicYear: int     │
                          │ - meetsCriteria: bool   │
                          └─────────────────────────┘
                                     │
                                     │ uses
                                     ▼
                          ┌─────────────────────────┐
                          │   GradeCalculator       │
                          ├─────────────────────────┤
                          │ - attendancePolicy      │
                          │ - extraPointsPolicy     │
                          ├─────────────────────────┤
                          │ + validateRequest()     │
                          │ + computeWeighted()     │
                          │ + calculateFinalGrade() │
                          └─────────────────────────┘
                                  │     │
                         depends  │     │  depends
                                  ▼     ▼
                ┌──────────────────┐   ┌───────────────────┐
                │ AttendancePolicy │   │ ExtraPointsPolicy │
                ├──────────────────┤   ├───────────────────┤
                │ + applyPenalty() │   │ + applyExtraPoints│
                └──────────────────┘   └───────────────────┘

                          ┌─────────────────────────┐
                          │ GradeCalculationResult  │
                          ├─────────────────────────┤
                          │ - finalGrade: double    │
                          │ - weightedAvg: double   │
                          │ - penalty: double       │
                          │ - extraPoints: double   │
                          │ - detailText: String    │
                          └─────────────────────────┘
                                     ▲
                                     │ produces
                                     │
                          ┌─────────────────────────┐
                          │  GradeCalculatorApp     │
                          │      <<main>>           │
                          ├─────────────────────────┤
                          │ + main(args)            │
                          └─────────────────────────┘
```

## 🎨 Calidad del Código

### Buenas Prácticas Aplicadas

✅ **Nombres significativos**: No se usan nombres como `x1`, `dato`, `aux`  
✅ **Constantes configurables**: Sin valores mágicos en el código  
✅ **Validaciones con mensajes claros**: Excepciones descriptivas  
✅ **Comentarios relevantes**: Documentación Javadoc completa  
✅ **Formato consistente**: Indentación y estructura uniforme  
✅ **Sin lógica en la UI**: `GradeCalculatorApp` solo maneja interacción  
✅ **Comentarios no obvios**: Solo donde el código no es autoexplicativo  

### Análisis Estático con SonarQube

**Configuración del proyecto**:
- Project Key: `Backend-Student-08`
- Token: Configurado en `sonar-project.properties`

**Ejecución**:
```powershell
# Opción 1: Análisis completo (requiere SonarScanner instalado)
.\run-sonar.ps1

# Opción 2: Análisis local sin servidor
.\analyze-code.ps1

# Opción 3: Ver guía de configuración
.\run-sonar-manual.ps1
```

**Ver guía completa**: [SONARQUBE_SETUP.md](SONARQUBE_SETUP.md)

## 👥 Autor

Proyecto desarrollado para el examen final de Ingeniería de Software - UTEC

## 📅 Fecha

Noviembre 2025

---

**Nota**: Este sistema es un módulo de cálculo académico. En un entorno de producción debería integrarse con:
- Base de datos para persistencia
- Sistema de autenticación de docentes
- Interfaz gráfica (GUI)
- API REST para integración con otros sistemas
- Logging y auditoría de cálculos
