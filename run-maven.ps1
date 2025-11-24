# Script para compilar, ejecutar tests y generar cobertura con Maven
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Maven Build - CS-GradeCalculator" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Verificar si Maven está instalado
$mvnCommand = Get-Command mvn -ErrorAction SilentlyContinue

if ($null -eq $mvnCommand) {
    Write-Host "Maven NO esta instalado" -ForegroundColor Red
    Write-Host ""
    Write-Host "Instalar Maven:" -ForegroundColor Yellow
    Write-Host "1. Descargar desde: https://maven.apache.org/download.cgi" -ForegroundColor White
    Write-Host "2. Extraer en C:\apache-maven" -ForegroundColor White
    Write-Host "3. Agregar al PATH: C:\apache-maven\bin" -ForegroundColor White
    Write-Host "4. Reiniciar PowerShell" -ForegroundColor White
    Write-Host ""
    exit 1
}

Write-Host "Maven version:" -ForegroundColor Green
mvn --version
Write-Host ""

# Limpiar builds anteriores
Write-Host "[1/5] Limpiando proyecto..." -ForegroundColor Yellow
mvn clean
Write-Host ""

# Compilar
Write-Host "[2/5] Compilando codigo fuente..." -ForegroundColor Yellow
mvn compile
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR en compilacion" -ForegroundColor Red
    exit 1
}
Write-Host "OK Compilacion exitosa" -ForegroundColor Green
Write-Host ""

# Compilar tests
Write-Host "[3/5] Compilando tests..." -ForegroundColor Yellow
mvn test-compile
Write-Host ""

# Ejecutar tests con JaCoCo
Write-Host "[4/5] Ejecutando tests y generando cobertura..." -ForegroundColor Yellow
mvn test
if ($LASTEXITCODE -ne 0) {
    Write-Host "ADVERTENCIA: Algunos tests fallaron" -ForegroundColor Yellow
}
Write-Host ""

# Generar reportes
Write-Host "[5/5] Generando reportes..." -ForegroundColor Yellow
mvn jacoco:report
Write-Host ""

Write-Host "========================================" -ForegroundColor Green
Write-Host "  Build Completado" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Verificar reportes generados
if (Test-Path "target\site\jacoco\index.html") {
    Write-Host "Reporte JaCoCo generado:" -ForegroundColor Cyan
    Write-Host "  target\site\jacoco\index.html" -ForegroundColor White
    Write-Host ""
    Write-Host "Abrir reporte de cobertura? (S/N): " -ForegroundColor Yellow -NoNewline
    $response = Read-Host
    if ($response -eq "S" -or $response -eq "s") {
        Start-Process "target\site\jacoco\index.html"
    }
}

Write-Host ""
Write-Host "Proximo paso - Subir a SonarQube:" -ForegroundColor Cyan
Write-Host "  mvn sonar:sonar" -ForegroundColor White
Write-Host "  O ejecutar: .\upload-maven-sonar.ps1" -ForegroundColor White
Write-Host ""
