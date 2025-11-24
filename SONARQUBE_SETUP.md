# 🔍 Configuración de SonarQube - CS-GradeCalculator

## 📋 Credenciales del Proyecto

```
Componente: Backend
Project Key: Backend-Student-08
Token: sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb
```

---

## 🚀 Guía Rápida de Uso

### Opción 1: Análisis con SonarScanner (Recomendado)

**Prerrequisito**: Tener SonarScanner instalado

```powershell
# Ejecutar análisis completo
.\run-sonar.ps1
```

### Opción 2: Análisis Local Básico

**Sin instalar SonarScanner**

```powershell
# Análisis estático local
.\analyze-code.ps1
```

### Opción 3: Información Manual

```powershell
# Ver opciones de análisis
.\run-sonar-manual.ps1
```

---

## 📦 Instalación de SonarScanner

### Windows

**Paso 1: Descargar**
```
https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/
```

**Paso 2: Extraer**
Descomprimir en `C:\sonar-scanner\`

**Paso 3: Agregar al PATH**
```powershell
# Opción A: Temporal (solo sesión actual)
$env:Path += ";C:\sonar-scanner\bin"

# Opción B: Permanente (PowerShell como Admin)
[Environment]::SetEnvironmentVariable(
    "Path",
    $env:Path + ";C:\sonar-scanner\bin",
    [EnvironmentVariableTarget]::Machine
)
```

**Paso 4: Verificar**
```powershell
sonar-scanner --version
```

---

## ⚙️ Configuración del Proyecto

### Archivo: `sonar-project.properties`

Ya está configurado con:

```properties
sonar.projectKey=Backend-Student-08
sonar.projectName=CS-GradeCalculator
sonar.projectVersion=1.0
sonar.token=sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb
sonar.sources=src
sonar.tests=test
sonar.java.binaries=bin
sonar.sourceEncoding=UTF-8
sonar.java.source=8
```

### Personalizar (Opcional)

Si tu servidor SonarQube está en una URL específica:

```properties
# Agregar al final de sonar-project.properties
sonar.host.url=http://tu-servidor:9000
```

---

## 🔧 Scripts Disponibles

### 1. `run-sonar.ps1`
Ejecuta análisis completo con SonarScanner.

**Uso:**
```powershell
.\run-sonar.ps1
```

**Requisitos:**
- SonarScanner instalado
- Proyecto compilado (automático si falta)

### 2. `analyze-code.ps1`
Análisis estático local sin SonarQube.

**Uso:**
```powershell
.\analyze-code.ps1
```

**Genera:**
- Reporte en consola
- Métricas básicas: líneas, métodos, clases
- Detección de code smells básicos

### 3. `run-sonar-manual.ps1`
Muestra opciones de análisis manual.

**Uso:**
```powershell
.\run-sonar-manual.ps1
```

**Opciones:**
- Instrucciones para SonarScanner
- Comandos Maven/Gradle
- Genera reporte local básico

---

## 📊 Análisis con Servidor SonarQube

### Si tienes acceso a un servidor SonarQube

**Paso 1: Configurar URL**

Editar `sonar-project.properties`:
```properties
sonar.host.url=http://localhost:9000
# O la URL de tu servidor
```

**Paso 2: Ejecutar análisis**
```powershell
.\run-sonar.ps1
```

**Paso 3: Ver resultados**
Abrir navegador en:
```
http://localhost:9000/dashboard?id=Backend-Student-08
```

### Si NO tienes servidor (alternativa local)

**Ejecutar análisis local:**
```powershell
.\analyze-code.ps1
```

Esto generará un reporte básico sin necesidad de servidor.

---

## 📈 Métricas Analizadas

### Por SonarQube (con servidor)
- ✅ Bugs y vulnerabilidades
- ✅ Code smells
- ✅ Cobertura de tests
- ✅ Duplicación de código
- ✅ Complejidad ciclomática
- ✅ Deuda técnica
- ✅ Mantenibilidad
- ✅ Confiabilidad
- ✅ Seguridad

### Por Análisis Local (sin servidor)
- ✅ Líneas de código
- ✅ Número de clases y métodos
- ✅ Ratio de comentarios
- ✅ Métodos largos (estimado)
- ✅ Números mágicos (estimado)
- ✅ Promedio líneas por clase

---

## 🎯 Integración con el Proyecto

### Estructura de Archivos

```
exaemn final/
├── sonar-project.properties    # Configuración SonarQube
├── run-sonar.ps1              # Script análisis completo
├── analyze-code.ps1           # Script análisis local
├── run-sonar-manual.ps1       # Guía manual
├── SONARQUBE_SETUP.md         # Esta guía
├── src/                       # Código a analizar
├── test/                      # Tests a analizar
└── bin/                       # Binarios compilados
```

### Flujo de Trabajo

1. **Desarrollar código** en `src/`
2. **Compilar**: `.\run.ps1` o `javac ...`
3. **Analizar**: `.\run-sonar.ps1` o `.\analyze-code.ps1`
4. **Revisar** resultados
5. **Corregir** issues detectados
6. **Re-analizar**

---

## 🐛 Resolución de Problemas

### Error: "sonar-scanner no encontrado"

**Solución:**
```powershell
# Verificar instalación
Get-Command sonar-scanner

# Si no existe, instalar según sección "Instalación"
# O usar análisis local:
.\analyze-code.ps1
```

### Error: "Proyecto no compilado"

**Solución:**
```powershell
# Compilar manualmente
javac -encoding UTF-8 -d bin src\*.java

# O usar script que compila automáticamente
.\run-sonar.ps1
```

### Error: "Token inválido"

**Verificar** en `sonar-project.properties`:
```properties
sonar.token=sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb
```

Si el token cambió, actualizarlo en el archivo.

### Error: "No se puede conectar al servidor"

**Opciones:**

1. **Configurar URL** en `sonar-project.properties`
2. **Usar análisis local**: `.\analyze-code.ps1`
3. **Verificar firewall** y permisos de red

---

## 📚 Recursos Adicionales

### Documentación Oficial
- [SonarQube Docs](https://docs.sonarqube.org/)
- [SonarScanner CLI](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/)
- [Java Analysis](https://docs.sonarqube.org/latest/analysis/languages/java/)

### Reglas de Calidad Java
- [Java Code Quality Rules](https://rules.sonarsource.com/java/)

### Tutoriales
- [Getting Started with SonarQube](https://docs.sonarqube.org/latest/setup/get-started-2-minutes/)

---

## 🎓 Integración con el Proyecto Académico

### Para el Examen Final

Este proyecto ya incluye:
- ✅ Configuración completa de SonarQube
- ✅ Scripts de análisis automatizados
- ✅ Documentación de uso
- ✅ Análisis local alternativo

### Cómo Demostrar Calidad

**Opción 1: Con SonarQube Server**
1. Ejecutar `.\run-sonar.ps1`
2. Captura de pantalla del dashboard
3. Mostrar métricas: 0 bugs, 0 vulnerabilidades, calidad A

**Opción 2: Sin Server (local)**
1. Ejecutar `.\analyze-code.ps1`
2. Captura de resultados en consola
3. Mostrar métricas: líneas, métodos, ratio comentarios

---

## ✅ Checklist de Calidad

Antes de la entrega, verificar:

- [ ] Ejecutar análisis: `.\run-sonar.ps1` o `.\analyze-code.ps1`
- [ ] 0 bugs críticos
- [ ] 0 vulnerabilidades de seguridad
- [ ] Ratio de comentarios > 10%
- [ ] Sin duplicación de código
- [ ] Complejidad ciclomática < 10 por método
- [ ] Sin números mágicos
- [ ] Nombres de variables descriptivos

---

## 🎯 Resultados Esperados

### Métricas del Proyecto CS-GradeCalculator

```
Líneas de código: ~800
Clases: 8
Métodos: ~50
Tests: 34
Cobertura: ~70%
Ratio comentarios: ~15%
Bugs esperados: 0
Code smells: < 5 (minor)
Calidad esperada: A
```

---

## 🚀 Comandos Rápidos

```powershell
# Análisis completo (con SonarScanner)
.\run-sonar.ps1

# Análisis local (sin servidor)
.\analyze-code.ps1

# Compilar + ejecutar aplicación
.\run.ps1

# Compilar para análisis
javac -encoding UTF-8 -d bin src\*.java
```

---

## 📞 Soporte

Para problemas con SonarQube:
1. Revisar esta guía (`SONARQUBE_SETUP.md`)
2. Ejecutar análisis local como alternativa
3. Verificar logs en consola
4. Consultar documentación oficial

---

*Configuración lista para Backend-Student-08*  
*Proyecto: CS-GradeCalculator*  
*Fecha: Noviembre 2025*
