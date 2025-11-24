# Casos de Prueba Manual - CS-GradeCalculator

## Instrucciones de Uso

Ejecute la aplicación con:
```powershell
.\run.ps1
```

O manualmente:
```powershell
javac -encoding UTF-8 -d bin src\*.java
java -cp bin GradeCalculatorApp
```

---

## 📋 Caso de Prueba 1: Cálculo Normal (Sin Penalizaciones)

### Entrada
```
Código del estudiante: U202012345
Cantidad de evaluaciones: 3

Evaluación #1:
  Nombre: PC1
  Nota: 15
  Peso: 30

Evaluación #2:
  Nombre: PC2
  Nota: 16
  Peso: 30

Evaluación #3:
  Nombre: Examen Final
  Nota: 14
  Peso: 40

¿Cumplió asistencia mínima? S
Año académico: 2024
¿Cumple criterios para puntos extra? N
```

### Resultado Esperado
```
Promedio Ponderado: 14.90
Asistencia: ✓ Cumplida (sin penalización)
Puntos Extra: No aplicados (política inactiva para año 2024)

NOTA FINAL: 14.90 / 20.0
```

### Validación
- ✅ Promedio: (15×30 + 16×30 + 14×40) / 100 = 14.90
- ✅ Sin penalización (asistencia OK)
- ✅ Sin puntos extra (año 2024 deshabilitado)

---

## 📋 Caso de Prueba 2: Penalización por Inasistencia

### Entrada
```
Código del estudiante: U202098765
Cantidad de evaluaciones: 2

Evaluación #1:
  Nombre: Parcial 1
  Nota: 18
  Peso: 50

Evaluación #2:
  Nombre: Parcial 2
  Nota: 17
  Peso: 50

¿Cumplió asistencia mínima? N  ← Importante
Año académico: 2024
¿Cumple criterios para puntos extra? S
```

### Resultado Esperado
```
Promedio Ponderado: 17.50
Penalización por Inasistencia: -17.50
  (El estudiante NO cumplió con la asistencia mínima)

NOTA FINAL: 0.00 / 20.0
```

### Validación
- ✅ Promedio: (18×50 + 17×50) / 100 = 17.50
- ✅ Penalización aplicada → nota final = 0 (RF02)
- ✅ No se aplican puntos extra (ya está en 0)

---

## 📋 Caso de Prueba 3: Con Puntos Extra

### Entrada
```
Código del estudiante: U202055555
Cantidad de evaluaciones: 4

Evaluación #1:
  Nombre: Lab1
  Nota: 14
  Peso: 20

Evaluación #2:
  Nombre: Lab2
  Nota: 15
  Peso: 20

Evaluación #3:
  Nombre: Lab3
  Nota: 16
  Peso: 20

Evaluación #4:
  Nombre: Examen
  Nota: 17
  Peso: 40

¿Cumplió asistencia mínima? S
Año académico: 2025  ← Política activa
¿Cumple criterios para puntos extra? S  ← Cumple
```

### Resultado Esperado
```
Promedio Ponderado: 15.80
Asistencia: ✓ Cumplida (sin penalización)
Puntos Extra Aplicados: +1.00
  (Política activa para año 2025 y estudiante cumple criterios)

NOTA FINAL: 16.80 / 20.0
```

### Validación
- ✅ Promedio: (14×20 + 15×20 + 16×20 + 17×40) / 100 = 15.80
- ✅ Puntos extra aplicados: 15.80 + 1.00 = 16.80 (RF03)
- ✅ Año 2025 tiene política activa
- ✅ Estudiante cumple criterios

---

## 📋 Caso de Prueba 4: Puntos Extra con Límite Máximo

### Entrada
```
Código del estudiante: U202099999
Cantidad de evaluaciones: 2

Evaluación #1:
  Nombre: Trabajo Final
  Nota: 19.5
  Peso: 50

Evaluación #2:
  Nombre: Presentación
  Nota: 19.5
  Peso: 50

¿Cumplió asistencia mínima? S
Año académico: 2023  ← Política activa
¿Cumple criterios para puntos extra? S
```

### Resultado Esperado
```
Promedio Ponderado: 19.50
Asistencia: ✓ Cumplida (sin penalización)
Puntos Extra Aplicados: +0.50
  (Limitado para no exceder nota máxima de 20.0)

NOTA FINAL: 20.00 / 20.0
```

### Validación
- ✅ Promedio: 19.50
- ✅ Puntos extra: 19.50 + 1.00 = 20.50 → limitado a 20.00
- ✅ No excede MAX_GRADE (RNF - determinismo)

---

## 📋 Caso de Prueba 5: Máximo de Evaluaciones (RNF01)

### Entrada
```
Código del estudiante: U202011111
Cantidad de evaluaciones: 10  ← Máximo permitido

Evaluación #1-#10:
  Nombre: Eval1, Eval2, ..., Eval10
  Nota: 15 (todas)
  Peso: 10 (cada una, suma = 100)

¿Cumplió asistencia mínima? S
Año académico: 2024
¿Cumple criterios para puntos extra? N
```

### Resultado Esperado
```
Promedio Ponderado: 15.00
Asistencia: ✓ Cumplida (sin penalización)
Puntos Extra: No aplicados

NOTA FINAL: 15.00 / 20.0
```

### Validación
- ✅ Acepta exactamente 10 evaluaciones (RNF01)
- ✅ Suma de pesos = 100%
- ✅ Cálculo correcto

---

## 📋 Caso de Prueba 6: Error - Más de 10 Evaluaciones

### Entrada
```
Código del estudiante: U202022222
Cantidad de evaluaciones: 11  ← Excede límite
```

### Resultado Esperado
```
Error: No puede haber más de 10 evaluaciones (RNF01)
```

### Validación
- ✅ Rechaza 11 evaluaciones
- ✅ Mensaje de error claro
- ✅ Cumple RNF01

---

## 📋 Caso de Prueba 7: Error - Suma de Pesos Incorrecta

### Entrada
```
Código del estudiante: U202033333
Cantidad de evaluaciones: 3

Evaluación #1:
  Nombre: PC1
  Nota: 15
  Peso: 30

Evaluación #2:
  Nombre: PC2
  Nota: 16
  Peso: 30

Evaluación #3:
  Nombre: Examen
  Nota: 14
  Peso: 35  ← Suma = 95% (incorrecto)
```

### Resultado Esperado
```
❌ Error de validación: La suma de pesos debe ser 100.0% (actual: 95.00%)
```

### Validación
- ✅ Detecta suma incorrecta
- ✅ Muestra valor actual y esperado
- ✅ No permite continuar

---

## 📋 Caso de Prueba 8: Error - Nota Fuera de Rango

### Entrada
```
Código del estudiante: U202044444
Cantidad de evaluaciones: 2

Evaluación #1:
  Nombre: PC1
  Nota: 25  ← Mayor que 20
  Peso: 50
```

### Resultado Esperado
```
❌ Error de validación: La nota de 'PC1' no puede ser mayor a 20.0
```

### Validación
- ✅ Rechaza notas > MAX_GRADE
- ✅ Mensaje específico con nombre de evaluación
- ✅ Protege integridad de datos

---

## 📋 Caso de Prueba 9: Caso Mínimo (1 Evaluación)

### Entrada
```
Código del estudiante: U202000001
Cantidad de evaluaciones: 1

Evaluación #1:
  Nombre: Examen Único
  Nota: 18
  Peso: 100

¿Cumplió asistencia mínima? S
Año académico: 2025
¿Cumple criterios para puntos extra? S
```

### Resultado Esperado
```
Promedio Ponderado: 18.00
Asistencia: ✓ Cumplida (sin penalización)
Puntos Extra Aplicados: +1.00

NOTA FINAL: 19.00 / 20.0
```

### Validación
- ✅ Acepta 1 evaluación (MIN_EVALUATIONS)
- ✅ Peso = 100% válido
- ✅ Cálculo correcto

---

## 📋 Caso de Prueba 10: Determinismo (RNF03)

### Procedimiento
1. Ejecute el Caso de Prueba 1
2. Anote la nota final: **14.90**
3. Ejecute nuevamente con los mismos datos
4. Anote la nota final: **14.90**
5. Repita 5 veces más

### Resultado Esperado
```
Ejecución 1: 14.90
Ejecución 2: 14.90
Ejecución 3: 14.90
Ejecución 4: 14.90
Ejecución 5: 14.90
Ejecución 6: 14.90
Ejecución 7: 14.90
```

### Validación
- ✅ Mismo input → mismo output (RNF03)
- ✅ Sin variación aleatoria
- ✅ Cálculo determinista

---

## 📋 Resumen de Validación Manual

| # | Caso de Prueba | Objetivo | Estado |
|---|----------------|----------|--------|
| 1 | Cálculo Normal | Flujo estándar sin penalizaciones | ⬜ |
| 2 | Inasistencia | Validar RF02 (nota = 0) | ⬜ |
| 3 | Puntos Extra | Validar RF03 (bonus +1.0) | ⬜ |
| 4 | Límite Máximo | No exceder 20.0 con bonus | ⬜ |
| 5 | 10 Evaluaciones | Validar RNF01 (máximo) | ⬜ |
| 6 | Error >10 Eval | Rechazo correcto | ⬜ |
| 7 | Error Pesos | Suma ≠ 100% | ⬜ |
| 8 | Error Nota >20 | Rango inválido | ⬜ |
| 9 | 1 Evaluación | Caso mínimo válido | ⬜ |
| 10 | Determinismo | RNF03: repetibilidad | ⬜ |

**Instrucciones**: Marque con ✅ cada caso después de probarlo.

---

## 🎯 Checklist de Aceptación

Después de ejecutar todos los casos de prueba:

- [ ] ✅ Todos los casos válidos calculan correctamente
- [ ] ✅ Todos los casos de error rechazan correctamente
- [ ] ✅ Mensajes de error son claros y descriptivos
- [ ] ✅ La aplicación no falla con excepciones no controladas
- [ ] ✅ El formato de salida es legible y profesional
- [ ] ✅ Los cálculos son consistentes (determinismo)

**Si todos los checks están marcados, el sistema está listo para producción.**
