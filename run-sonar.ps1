# Script para ejecutar análisis de SonarQube
# Proyecto: CS-GradeCalculator (Backend-Student-08)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Análisis SonarQube - CS-GradeCalculator" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Verificar que el proyecto esté compilado
if (-not (Test-Path "bin\GradeCalculator.class")) {
    Write-Host "⚠️  El proyecto no está compilado. Compilando..." -ForegroundColor Yellow
    javac -encoding UTF-8 -d bin src\*.java
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ Error en la compilación" -ForegroundColor Red
        exit 1
    }
    Write-Host "✓ Compilación exitosa" -ForegroundColor Green
}

# Verificar si sonar-scanner está instalado
$sonarCommand = Get-Command sonar-scanner -ErrorAction SilentlyContinue

if ($null -eq $sonarCommand) {
    Write-Host ""
    Write-Host "❌ SonarScanner no está instalado" -ForegroundColor Red
    Write-Host ""
    Write-Host "Para instalar SonarScanner:" -ForegroundColor Yellow
    Write-Host "1. Descargar desde: https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/" -ForegroundColor Yellow
    Write-Host "2. Extraer y agregar al PATH del sistema" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Alternativamente, usa el análisis manual:" -ForegroundColor Yellow
    Write-Host "  .\run-sonar-manual.ps1" -ForegroundColor Cyan
    Write-Host ""
    exit 1
}

# Ejecutar análisis con SonarScanner
Write-Host "🔍 Iniciando análisis de código con SonarQube..." -ForegroundColor Cyan
Write-Host "Proyecto: Backend-Student-08" -ForegroundColor Gray
Write-Host ""

sonar-scanner

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "  ✓ Análisis completado exitosamente" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Ver resultados en el dashboard de SonarQube" -ForegroundColor Cyan
    Write-Host "Proyecto: Backend-Student-08" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "❌ Error en el análisis" -ForegroundColor Red
    Write-Host "Verifica la configuración en sonar-project.properties" -ForegroundColor Yellow
    exit 1
}
