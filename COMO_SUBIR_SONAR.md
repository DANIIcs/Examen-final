# 🚀 Guía Rápida: Subir Proyecto a SonarQube

## 📋 Información de tu Proyecto

```
Project Key: Backend-Student-08
Token: sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb
Nombre: CS-GradeCalculator
```

---

## ⚡ Método Rápido (Si tienes SonarScanner)

```powershell
# Ejecutar script automatizado
.\upload-to-sonar.ps1
```

El script te guiará paso a paso y te pedirá la URL de tu servidor SonarQube.

---

## 🔧 Instalación de SonarScanner (Primera vez)

### Paso 1: Descargar SonarScanner

**Opción A: Descarga Directa**
```
https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-5.0.1.3006-windows.zip
```

**Opción B: Página oficial**
```
https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/
```

### Paso 2: Extraer

Descomprimir en: `C:\sonar-scanner\`

Estructura final:
```
C:\sonar-scanner\
  ├── bin\
  │   └── sonar-scanner.bat
  ├── conf\
  └── lib\
```

### Paso 3: Agregar al PATH

**PowerShell como Administrador:**

```powershell
[Environment]::SetEnvironmentVariable(
    "Path",
    $env:Path + ";C:\sonar-scanner\bin",
    [EnvironmentVariableTarget]::Machine
)
```

### Paso 4: Verificar Instalación

**Cerrar y abrir PowerShell, luego:**

```powershell
sonar-scanner --version
```

Deberías ver algo como:
```
INFO: Scanner configuration file: C:\sonar-scanner\conf\sonar-scanner.properties
INFO: SonarScanner 5.0.1.3006
```

### Paso 5: Subir Proyecto

```powershell
cd "c:\Users\User\VS-CODE\Ing soft\exaemn final"
.\upload-to-sonar.ps1
```

---

## 🌐 Opción: Subir a SonarCloud (Sin servidor local)

Si no tienes un servidor SonarQube local, puedes usar SonarCloud:

### 1. Crear cuenta en SonarCloud

```
https://sonarcloud.io
```

### 2. Crear nuevo proyecto

- Clic en "+" → "Analyze new project"
- Seleccionar "Create project manually"
- Project key: `Backend-Student-08`

### 3. Generar token

- My Account → Security → Generate Token
- Copiar el token generado

### 4. Ejecutar análisis

```powershell
sonar-scanner `
  -Dsonar.projectKey=Backend-Student-08 `
  -Dsonar.organization=TU_ORGANIZACION `
  -Dsonar.sources=src `
  -Dsonar.tests=test `
  -Dsonar.java.binaries=bin `
  -Dsonar.host.url=https://sonarcloud.io `
  -Dsonar.token=TU_TOKEN_DE_SONARCLOUD
```

---

## 🏢 Opción: Servidor SonarQube Local

Si tienes acceso a un servidor SonarQube de tu universidad:

### 1. Preguntar URL del servidor

Ejemplos:
- `http://sonarqube.utec.edu.pe`
- `http://192.168.1.100:9000`
- `http://localhost:9000`

### 2. Ejecutar script

```powershell
.\upload-to-sonar.ps1
```

Cuando te pida la URL, ingresa la URL del servidor.

### 3. Ver resultados

Abre en tu navegador:
```
[URL_SERVIDOR]/dashboard?id=Backend-Student-08
```

---

## 📝 Opción: Comando Manual Completo

Si prefieres ejecutar el comando directamente:

```powershell
# Compilar proyecto
javac -encoding UTF-8 -d bin src\*.java

# Ejecutar análisis (reemplaza URL_SERVIDOR)
sonar-scanner `
  -Dsonar.projectKey=Backend-Student-08 `
  -Dsonar.projectName="CS-GradeCalculator" `
  -Dsonar.projectVersion=1.0 `
  -Dsonar.sources=src `
  -Dsonar.tests=test `
  -Dsonar.java.binaries=bin `
  -Dsonar.sourceEncoding=UTF-8 `
  -Dsonar.java.source=8 `
  -Dsonar.host.url=http://localhost:9000 `
  -Dsonar.token=sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb
```

---

## 🎓 Para tu Examen (Sin servidor)

Si no puedes instalar SonarScanner o no tienes servidor:

### Alternativa: Análisis Local

```powershell
# Ejecutar análisis local
.\analyze-code.ps1
```

Esto genera métricas sin necesidad de servidor:
- ✅ Líneas de código
- ✅ Número de clases y métodos
- ✅ Calidad del código
- ✅ Promedios y estadísticas

**Captura de pantalla** del resultado para demostrar calidad.

---

## 🔍 Verificar que Funcionó

Después de subir, verifica:

### 1. En la consola

Deberías ver:
```
INFO: Analysis report uploaded in XXXms
INFO: ANALYSIS SUCCESSFUL
INFO: Note that you will be able to access the updated dashboard once the server has processed the submitted analysis report
```

### 2. En el navegador

Accede a:
```
[URL_SERVIDOR]/dashboard?id=Backend-Student-08
```

Deberías ver:
- ✅ Bugs: 0
- ✅ Vulnerabilities: 0
- ✅ Code Smells: < 5
- ✅ Coverage: ~70%
- ✅ Duplications: 0%

---

## ❌ Solución de Problemas

### Error: "sonar-scanner no encontrado"

**Solución:**
1. Verificar instalación: `Get-Command sonar-scanner`
2. Verificar PATH: `echo $env:Path`
3. Reiniciar PowerShell
4. Reinstalar SonarScanner

### Error: "Connection refused"

**Solución:**
1. Verificar URL del servidor
2. Verificar que el servidor está activo
3. Verificar firewall/antivirus
4. Probar con: `curl [URL_SERVIDOR]`

### Error: "Unauthorized" o "401"

**Solución:**
1. Verificar token en `sonar-project.properties`
2. Generar nuevo token en el servidor
3. Actualizar token en el comando

### Error: "Project not found"

**Solución:**
1. Crear proyecto en el servidor primero
2. Usar el Project Key exacto: `Backend-Student-08`
3. Verificar permisos de usuario

---

## 📊 Estructura de Archivos para SonarQube

Tu proyecto ya está configurado correctamente:

```
✅ sonar-project.properties  (Configuración)
✅ src/                      (Código fuente)
✅ test/                     (Tests)
✅ bin/                      (Compilados)
```

---

## 🎯 Checklist de Subida

- [ ] SonarScanner instalado y en PATH
- [ ] Proyecto compilado (`bin/*.class`)
- [ ] URL del servidor conocida
- [ ] Token configurado en `sonar-project.properties`
- [ ] Ejecutar `.\upload-to-sonar.ps1`
- [ ] Verificar resultado en navegador
- [ ] Capturar dashboard para evidencia

---

## 💡 Consejo Final

Si tienes problemas con SonarQube:

**Usa el análisis local como respaldo:**
```powershell
.\analyze-code.ps1
```

Genera un reporte profesional sin necesidad de servidor que puedes presentar en tu examen.

---

*¿Necesitas ayuda? Revisa `SONARQUBE_SETUP.md` para más detalles.*
