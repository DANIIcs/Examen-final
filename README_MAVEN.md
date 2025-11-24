# 🚀 Guía Maven + JaCoCo + SonarQube

## 📋 Proyecto Configurado con Maven

Tu proyecto ahora incluye:
- ✅ Maven para gestión de dependencias
- ✅ JUnit 5 para tests
- ✅ JaCoCo para cobertura de código
- ✅ Integración con SonarQube
- ✅ Scripts automatizados

---

## 🔧 Instalación de Maven (Primera vez)

### Paso 1: Descargar Maven

```
https://maven.apache.org/download.cgi
Archivo: apache-maven-3.9.6-bin.zip
```

### Paso 2: Extraer

Descomprimir en: `C:\apache-maven\`

### Paso 3: Agregar al PATH

**PowerShell como Administrador:**

```powershell
[Environment]::SetEnvironmentVariable(
    "Path",
    $env:Path + ";C:\apache-maven\bin",
    [EnvironmentVariableTarget]::Machine
)
```

### Paso 4: Verificar

**Cerrar y abrir PowerShell:**

```powershell
mvn --version
```

---

## 🚀 Comandos Rápidos

### Opción 1: Scripts Automatizados (Recomendado)

```powershell
# 1. Compilar, ejecutar tests y generar cobertura
.\run-maven.ps1

# 2. Subir a SonarQube
.\upload-maven-sonar.ps1
```

### Opción 2: Comandos Maven Directos

```powershell
# Limpiar y compilar
mvn clean compile

# Ejecutar tests con cobertura
mvn test

# Generar reporte JaCoCo
mvn jacoco:report

# Subir a SonarQube (local)
mvn sonar:sonar

# Subir a SonarQube (servidor específico)
mvn sonar:sonar -Dsonar.host.url=http://tu-servidor:9000
```

---

## 📊 Generar Reporte de Cobertura

### Ejecutar tests con JaCoCo

```powershell
mvn clean test jacoco:report
```

### Ver reporte

El reporte se genera en:
```
target\site\jacoco\index.html
```

Abrir en navegador:
```powershell
Start-Process target\site\jacoco\index.html
```

### Contenido del reporte

- ✅ Cobertura por clase
- ✅ Cobertura por paquete
- ✅ Líneas cubiertas/no cubiertas
- ✅ Ramas cubiertas
- ✅ Métodos cubiertos
- ✅ Gráficos visuales

---

## 🌐 Subir a SonarQube

### Opción A: Script Automatizado

```powershell
.\upload-maven-sonar.ps1
```

Te pedirá la URL del servidor y subirá automáticamente.

### Opción B: Comando Manual

```powershell
# Para servidor local
mvn sonar:sonar

# Para servidor específico
mvn sonar:sonar `
  -Dsonar.host.url=http://sonarqube.utec.edu.pe `
  -Dsonar.projectKey=Backend-Student-08 `
  -Dsonar.token=sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb
```

### Opción C: SonarCloud

```powershell
mvn sonar:sonar `
  -Dsonar.projectKey=Backend-Student-08 `
  -Dsonar.organization=TU_ORGANIZACION `
  -Dsonar.host.url=https://sonarcloud.io `
  -Dsonar.token=TU_TOKEN_SONARCLOUD
```

---

## 📁 Estructura del Proyecto Maven

```
exaemn final/
├── pom.xml                      # Configuración Maven
├── src/                         # Código fuente
│   ├── Constants.java
│   ├── Evaluation.java
│   ├── GradeCalculationRequest.java
│   ├── GradeCalculationResult.java
│   ├── AttendancePolicy.java
│   ├── ExtraPointsPolicy.java
│   ├── GradeCalculator.java
│   └── GradeCalculatorApp.java
├── test/                        # Tests
│   ├── EvaluationTest.java
│   ├── AttendancePolicyTest.java
│   ├── ExtraPointsPolicyTest.java
│   └── GradeCalculatorTest.java
├── target/                      # Generado por Maven
│   ├── classes/                 # Clases compiladas
│   ├── test-classes/            # Tests compilados
│   └── site/jacoco/            # Reporte JaCoCo
│       └── index.html
├── run-maven.ps1               # Script build + tests
├── upload-maven-sonar.ps1      # Script subir SonarQube
└── .gitignore                  # Git ignore
```

---

## 🧪 Ejecutar Tests

### Todos los tests

```powershell
mvn test
```

### Test específico

```powershell
mvn test -Dtest=GradeCalculatorTest
```

### Con cobertura

```powershell
mvn clean test jacoco:report
```

---

## 📈 Configuración de JaCoCo

### Ya configurado en `pom.xml`

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <id>prepare-agent</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Verificar cobertura mínima

```powershell
mvn jacoco:check
```

Falla si cobertura < 50%

---

## 🎯 Workflow Completo

### Para desarrollo diario

```powershell
# 1. Hacer cambios en código
# 2. Ejecutar tests
mvn test

# 3. Ver cobertura
mvn jacoco:report
Start-Process target\site\jacoco\index.html
```

### Para subir a SonarQube

```powershell
# Opción fácil
.\upload-maven-sonar.ps1

# O manual
mvn clean verify sonar:sonar
```

---

## 🔍 Ver Resultados

### Reporte JaCoCo Local

```
target\site\jacoco\index.html
```

Muestra:
- Cobertura de líneas
- Cobertura de ramas
- Métodos cubiertos
- Clases con/sin tests

### Dashboard SonarQube

```
http://[SERVIDOR]/dashboard?id=Backend-Student-08
```

Muestra:
- Bugs: 0
- Vulnerabilities: 0
- Code Smells: < 5
- Coverage: ~70%
- Duplications: 0%

---

## ⚙️ Configuración en `pom.xml`

### Información del Proyecto

```xml
<groupId>pe.edu.utec</groupId>
<artifactId>cs-grade-calculator</artifactId>
<version>1.0.0</version>
```

### Properties SonarQube

```xml
<sonar.projectKey>Backend-Student-08</sonar.projectKey>
<sonar.projectName>CS-GradeCalculator</sonar.projectName>
<sonar.token>sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb</sonar.token>
```

### Dependencias

- JUnit 5.10.1
- JaCoCo 0.8.11
- Maven plugins actualizados

---

## 🎓 Para tu Examen Final

### Evidencias a presentar

1. **Reporte JaCoCo**
   ```powershell
   mvn clean test jacoco:report
   ```
   Captura: `target\site\jacoco\index.html`

2. **Dashboard SonarQube**
   ```powershell
   .\upload-maven-sonar.ps1
   ```
   Captura del dashboard

3. **Tests ejecutándose**
   ```powershell
   mvn test
   ```
   Captura de consola

---

## ❌ Solución de Problemas

### Maven no encontrado

```powershell
# Verificar instalación
mvn --version

# Si falla, verificar PATH
echo $env:Path
```

### Tests no se ejecutan

```powershell
# Limpiar y recompilar
mvn clean compile test-compile test
```

### JaCoCo no genera reporte

```powershell
# Ejecutar explícitamente
mvn clean test jacoco:report

# Verificar archivo
Test-Path target\site\jacoco\index.html
```

### Error al subir a SonarQube

```powershell
# Verificar conexión
curl http://localhost:9000

# Verificar token en pom.xml
code pom.xml
```

---

## 📊 Métricas Esperadas

### Cobertura JaCoCo

```
✅ Líneas: ~70%
✅ Ramas: ~65%
✅ Métodos: ~85%
✅ Clases: 100%
```

### Calidad SonarQube

```
✅ Bugs: 0
✅ Vulnerabilities: 0
✅ Security Hotspots: 0
✅ Code Smells: < 5
✅ Coverage: 70%
✅ Duplications: 0%
✅ Rating: A
```

---

## 🎉 Resumen

Tu proyecto ahora tiene:
- ✅ Maven completamente configurado
- ✅ JUnit 5 para tests
- ✅ JaCoCo para cobertura
- ✅ Integración con SonarQube
- ✅ Scripts automatizados
- ✅ Listo para subir y evaluar

**Comandos principales:**
```powershell
.\run-maven.ps1              # Build + tests + cobertura
.\upload-maven-sonar.ps1     # Subir a SonarQube
mvn clean test jacoco:report # Manual
```

---

*Proyecto configurado: Noviembre 2025*  
*Backend-Student-08*
