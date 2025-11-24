# Guia completa para subir el proyecto a SonarQube
# Proyecto: Backend-Student-08

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Subir Proyecto a SonarQube" -ForegroundColor Cyan
Write-Host "  Backend-Student-08" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Paso 1: Verificar compilacion
Write-Host "[Paso 1/4] Verificando compilacion..." -ForegroundColor Yellow
if (-not (Test-Path "bin\GradeCalculator.class")) {
    Write-Host "  Compilando proyecto..." -ForegroundColor Gray
    javac -encoding UTF-8 -d bin src\*.java
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  OK Compilacion exitosa" -ForegroundColor Green
    } else {
        Write-Host "  ERROR en compilacion" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "  OK Proyecto ya compilado" -ForegroundColor Green
}

Write-Host ""

# Paso 2: Verificar SonarScanner
Write-Host "[Paso 2/4] Verificando SonarScanner..." -ForegroundColor Yellow
$sonarCommand = Get-Command sonar-scanner -ErrorAction SilentlyContinue

if ($null -eq $sonarCommand) {
    Write-Host "  SonarScanner NO esta instalado" -ForegroundColor Red
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Yellow
    Write-Host "  OPCIONES PARA SUBIR A SONARQUBE" -ForegroundColor Yellow
    Write-Host "========================================" -ForegroundColor Yellow
    Write-Host ""
    
    Write-Host "OPCION 1: Instalar SonarScanner (Recomendado)" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "1. Descargar SonarScanner:" -ForegroundColor White
    Write-Host "   https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/" -ForegroundColor Gray
    Write-Host "   Archivo: sonar-scanner-cli-X.X.X-windows.zip" -ForegroundColor Gray
    Write-Host ""
    Write-Host "2. Extraer en: C:\sonar-scanner\" -ForegroundColor White
    Write-Host ""
    Write-Host "3. Agregar al PATH (PowerShell como Admin):" -ForegroundColor White
    Write-Host '   [Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\sonar-scanner\bin", [EnvironmentVariableTarget]::Machine)' -ForegroundColor Gray
    Write-Host ""
    Write-Host "4. Reiniciar PowerShell y ejecutar:" -ForegroundColor White
    Write-Host "   .\upload-to-sonar.ps1" -ForegroundColor Gray
    Write-Host ""
    
    Write-Host "OPCION 2: Usar Maven (si tienes Maven instalado)" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Ejecutar:" -ForegroundColor White
    Write-Host '   mvn clean verify sonar:sonar `' -ForegroundColor Gray
    Write-Host '     -Dsonar.projectKey=Backend-Student-08 `' -ForegroundColor Gray
    Write-Host '     -Dsonar.projectName="CS-GradeCalculator" `' -ForegroundColor Gray
    Write-Host '     -Dsonar.token=sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb' -ForegroundColor Gray
    Write-Host ""
    
    Write-Host "OPCION 3: Subir manualmente via Web UI" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "1. Acceder a tu servidor SonarQube" -ForegroundColor White
    Write-Host "2. Ir a Projects > Create Project" -ForegroundColor White
    Write-Host "3. Usar:" -ForegroundColor White
    Write-Host "   - Project Key: Backend-Student-08" -ForegroundColor Gray
    Write-Host "   - Token: sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb" -ForegroundColor Gray
    Write-Host "4. Seguir instrucciones en pantalla" -ForegroundColor White
    Write-Host ""
    
    exit 0
}

Write-Host "  OK SonarScanner instalado" -ForegroundColor Green
Write-Host ""

# Paso 3: Configurar URL del servidor
Write-Host "[Paso 3/4] Configurando servidor SonarQube..." -ForegroundColor Yellow
Write-Host ""
Write-Host "Ingresa la URL de tu servidor SonarQube" -ForegroundColor White
Write-Host "Ejemplos:" -ForegroundColor Gray
Write-Host "  - http://localhost:9000" -ForegroundColor Gray
Write-Host "  - http://sonarqube.tu-universidad.edu" -ForegroundColor Gray
Write-Host "  - https://sonarcloud.io" -ForegroundColor Gray
Write-Host ""
$sonarUrl = Read-Host "URL del servidor (Enter para localhost:9000)"

if ([string]::IsNullOrWhiteSpace($sonarUrl)) {
    $sonarUrl = "http://localhost:9000"
}

Write-Host "  Usando: $sonarUrl" -ForegroundColor Green
Write-Host ""

# Paso 4: Ejecutar analisis
Write-Host "[Paso 4/4] Ejecutando analisis y subiendo a SonarQube..." -ForegroundColor Yellow
Write-Host ""
Write-Host "Proyecto: Backend-Student-08" -ForegroundColor Gray
Write-Host "URL: $sonarUrl" -ForegroundColor Gray
Write-Host ""

# Ejecutar sonar-scanner
sonar-scanner `
    -Dsonar.projectKey=Backend-Student-08 `
    -Dsonar.projectName="CS-GradeCalculator" `
    -Dsonar.projectVersion=1.0 `
    -Dsonar.sources=src `
    -Dsonar.tests=test `
    -Dsonar.java.binaries=bin `
    -Dsonar.sourceEncoding=UTF-8 `
    -Dsonar.java.source=8 `
    -Dsonar.host.url=$sonarUrl `
    -Dsonar.token=sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "  SUBIDA EXITOSA!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Ver resultados en:" -ForegroundColor Cyan
    Write-Host "  $sonarUrl/dashboard?id=Backend-Student-08" -ForegroundColor White
    Write-Host ""
    Write-Host "Proyecto: Backend-Student-08" -ForegroundColor Gray
    Write-Host "Token usado: sqp_b98c48a0...f9bb" -ForegroundColor Gray
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "  ERROR AL SUBIR" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Posibles causas:" -ForegroundColor Yellow
    Write-Host "1. URL del servidor incorrecta" -ForegroundColor White
    Write-Host "2. Token invalido o expirado" -ForegroundColor White
    Write-Host "3. Servidor SonarQube no accesible" -ForegroundColor White
    Write-Host "4. Problemas de red/firewall" -ForegroundColor White
    Write-Host ""
    Write-Host "Verifica:" -ForegroundColor Yellow
    Write-Host "- Servidor: $sonarUrl" -ForegroundColor White
    Write-Host "- Token en: sonar-project.properties" -ForegroundColor White
    Write-Host ""
}
