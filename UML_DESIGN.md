## Diagrama UML del Sistema CS-GradeCalculator

### Diagrama de Clases Completo

```
┌─────────────────────────────────┐
│         <<utility>>             │
│         Constants               │
├─────────────────────────────────┤
│ + MAX_EVALUATIONS: int = 10     │
│ + MIN_EVALUATIONS: int = 1      │
│ + MIN_GRADE: double = 0.0       │
│ + MAX_GRADE: double = 20.0      │
│ + EXTRA_POINTS_BONUS: double    │
│ + ATTENDANCE_PENALTY_GRADE      │
│ + TOLERANCE_WEIGHT_SUM: double  │
│ + EXPECTED_WEIGHT_SUM: double   │
└─────────────────────────────────┘


┌─────────────────────────────────┐
│        Evaluation               │
├─────────────────────────────────┤
│ - name: String                  │
│ - score: double                 │
│ - weightPercentage: double      │
├─────────────────────────────────┤
│ + Evaluation(name, score, ...)  │
│ + getName(): String             │
│ + getScore(): double            │
│ + getWeightPercentage(): double │
│ + toString(): String            │
└─────────────────────────────────┘
              ▲
              │ 1..*
              │ contains
              │
┌─────────────────────────────────┐
│   GradeCalculationRequest       │
├─────────────────────────────────┤
│ - studentId: String             │
│ - evaluations: List<Evaluation> │
│ - hasReachedMinimumClasses: bool│
│ - academicYear: int             │
│ - studentMeetsExtraCriteria: bool│
├─────────────────────────────────┤
│ + GradeCalculationRequest(...)  │
│ + getStudentId(): String        │
│ + getEvaluations(): List        │
│ + hasReachedMinimumClasses()    │
│ + getAcademicYear(): int        │
│ + studentMeetsExtraCriteria()   │
└─────────────────────────────────┘
              │
              │ input to
              ▼
┌─────────────────────────────────────────────┐
│           GradeCalculator                   │
├─────────────────────────────────────────────┤
│ - attendancePolicy: AttendancePolicy        │
│ - extraPointsPolicy: ExtraPointsPolicy      │
├─────────────────────────────────────────────┤
│ + GradeCalculator(attendance, extraPoints)  │
│ + validateRequest(request): void            │
│ + computeWeightedAverage(evaluations): dbl  │
│ + calculateFinalGrade(request): Result      │
└─────────────────────────────────────────────┘
         │                        │
         │ uses                   │ uses
         │                        │
         ▼                        ▼
┌──────────────────────┐  ┌───────────────────────────┐
│  AttendancePolicy    │  │   ExtraPointsPolicy       │
├──────────────────────┤  ├───────────────────────────┤
│                      │  │ - allYearsTeachers: Map   │
├──────────────────────┤  ├───────────────────────────┤
│ + applyPenalty():dbl │  │ + ExtraPointsPolicy(map)  │
│ + getPenaltyAmount() │  │ + isExtraPointsEnabled()  │
└──────────────────────┘  │ + applyExtraPoints(): dbl │
                          │ + getExtraPointsAmount()  │
                          └───────────────────────────┘

              │
              │ produces
              ▼
┌─────────────────────────────────┐
│   GradeCalculationResult        │
├─────────────────────────────────┤
│ - finalGrade: double            │
│ - weightedAverage: double       │
│ - attendancePenalty: double     │
│ - extraPointsApplied: double    │
│ - detailText: String            │
├─────────────────────────────────┤
│ + GradeCalculationResult(...)   │
│ + getFinalGrade(): double       │
│ + getWeightedAverage(): double  │
│ + getAttendancePenalty(): double│
│ + getExtraPointsApplied(): dbl  │
│ + getDetailText(): String       │
│ + toString(): String            │
└─────────────────────────────────┘
              ▲
              │ displays
              │
┌─────────────────────────────────┐
│     GradeCalculatorApp          │
│        <<main>>                 │
├─────────────────────────────────┤
│ - scanner: Scanner {static}     │
├─────────────────────────────────┤
│ + main(args: String[]): void    │
└─────────────────────────────────┘
```

### Relaciones entre Clases

1. **Composición**: `GradeCalculationRequest` contiene 1..* `Evaluation`
2. **Dependencia**: `GradeCalculator` usa `AttendancePolicy` y `ExtraPointsPolicy`
3. **Producción**: `GradeCalculator.calculateFinalGrade()` produce `GradeCalculationResult`
4. **Uso**: `GradeCalculatorApp` crea y usa todas las demás clases

### Diagrama de Secuencia: CU001 - Calcular Nota Final

```
Usuario          App            Calculator      Attendance    ExtraPoints      Result
  │               │                  │               │              │             │
  │──input───────>│                  │               │              │             │
  │               │──new Request────>│               │              │             │
  │               │                  │               │              │             │
  │               │──calculate()────>│               │              │             │
  │               │                  │               │              │             │
  │               │                  │──validate()───┤              │             │
  │               │                  │               │              │             │
  │               │                  │──compute()────┤              │             │
  │               │                  │               │              │             │
  │               │                  │──applyPenalty()─>            │             │
  │               │                  │<─────grade────│              │             │
  │               │                  │               │              │             │
  │               │                  │──applyExtra()─────────────>  │             │
  │               │                  │<────finalGrade──────────────│             │
  │               │                  │                              │             │
  │               │                  │──new Result()───────────────────────────> │
  │               │<─────result──────│                              │             │
  │               │                                                                │
  │<──display─────│                                                                │
  │               │                                                                │
```

### Diagrama de Estados: Cálculo de Nota

```
                    ┌──────────────┐
                    │   Creado     │
                    └──────┬───────┘
                           │ validateRequest()
                           ▼
                    ┌──────────────┐
                    │  Validado    │
                    └──────┬───────┘
                           │ computeWeightedAverage()
                           ▼
                    ┌──────────────┐
                    │  Promediado  │
                    └──────┬───────┘
                           │ applyPenalty()
                           ▼
              ┌────────────┴────────────┐
              │                         │
        ┌─────▼──────┐          ┌──────▼─────┐
        │ Penalizado │          │ Sin Penalty│
        └─────┬──────┘          └──────┬─────┘
              │                         │
              └────────────┬────────────┘
                           │ applyExtraPoints()
                           ▼
              ┌────────────┴────────────┐
              │                         │
        ┌─────▼──────┐          ┌──────▼─────┐
        │ Con Bonus  │          │  Sin Bonus │
        └─────┬──────┘          └──────┬─────┘
              │                         │
              └────────────┬────────────┘
                           │
                           ▼
                    ┌──────────────┐
                    │  Finalizado  │
                    └──────────────┘
```

### Patrones de Diseño Utilizados

1. **Strategy Pattern**: `AttendancePolicy` y `ExtraPointsPolicy` son estrategias intercambiables
2. **Builder Pattern** (implícito): `GradeCalculationRequest` agrupa parámetros
3. **Value Object**: `Evaluation` es inmutable y representa un valor
4. **Data Transfer Object (DTO)**: `GradeCalculationResult` transfiere datos
5. **Dependency Injection**: `GradeCalculator` recibe dependencias en constructor

### Métricas de Diseño

- **Clases**: 8 (excl. tests)
- **Acoplamiento**: Bajo (máx. 2 dependencias por clase)
- **Cohesión**: Alta (una responsabilidad por clase)
- **Complejidad Ciclomática**: Baja (< 10 por método)
- **Líneas de código**: ~800 (src) + ~400 (tests)
