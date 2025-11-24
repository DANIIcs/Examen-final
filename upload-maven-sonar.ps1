# Script para subir proyecto a SonarQube usando Maven
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Subir a SonarQube con Maven" -ForegroundColor Cyan
Write-Host "  Backend-Student-08" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Verificar Maven
$mvnCommand = Get-Command mvn -ErrorAction SilentlyContinue
if ($null -eq $mvnCommand) {
    Write-Host "ERROR: Maven no esta instalado" -ForegroundColor Red
    Write-Host "Ejecuta primero: .\run-maven.ps1" -ForegroundColor Yellow
    exit 1
}

# Preguntar URL del servidor
Write-Host "Ingresa la URL del servidor SonarQube" -ForegroundColor Yellow
Write-Host "Ejemplos:" -ForegroundColor Gray
Write-Host "  - http://localhost:9000" -ForegroundColor Gray
Write-Host "  - https://sonarcloud.io" -ForegroundColor Gray
Write-Host "  - http://sonarqube.utec.edu.pe" -ForegroundColor Gray
Write-Host ""
$sonarUrl = Read-Host "URL (Enter para localhost:9000)"

if ([string]::IsNullOrWhiteSpace($sonarUrl)) {
    $sonarUrl = "http://localhost:9000"
}

Write-Host ""
Write-Host "Usando servidor: $sonarUrl" -ForegroundColor Green
Write-Host ""

# Compilar y ejecutar tests con cobertura
Write-Host "[1/2] Compilando y ejecutando tests con cobertura..." -ForegroundColor Yellow
mvn clean verify

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "ADVERTENCIA: Build con errores" -ForegroundColor Yellow
    Write-Host "Continuando con analisis de SonarQube..." -ForegroundColor Yellow
    Write-Host ""
}

# Subir a SonarQube
Write-Host "[2/2] Subiendo a SonarQube..." -ForegroundColor Yellow
Write-Host ""
Write-Host "Proyecto: Backend-Student-08" -ForegroundColor Gray
Write-Host "URL: $sonarUrl" -ForegroundColor Gray
Write-Host ""

mvn sonar:sonar `
    -Dsonar.host.url=$sonarUrl `
    -Dsonar.projectKey=Backend-Student-08 `
    -Dsonar.projectName="CS-GradeCalculator" `
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
    Write-Host "Reporte de cobertura local:" -ForegroundColor Cyan
    Write-Host "  target\site\jacoco\index.html" -ForegroundColor White
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "  ERROR AL SUBIR" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Verifica:" -ForegroundColor Yellow
    Write-Host "- URL del servidor: $sonarUrl" -ForegroundColor White
    Write-Host "- Token en pom.xml" -ForegroundColor White
    Write-Host "- Conexion a internet" -ForegroundColor White
    Write-Host ""
}
