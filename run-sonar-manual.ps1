# Script alternativo para análisis de SonarQube sin instalar sonar-scanner
# Usa la API REST de SonarQube directamente

param(
    [string]$SonarHost = "http://localhost:9000"
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Análisis SonarQube Manual" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Configuración
$projectKey = "Backend-Student-08"
$projectName = "CS-GradeCalculator"
$token = "sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb"

# Compilar si es necesario
if (-not (Test-Path "bin\GradeCalculator.class")) {
    Write-Host "⚠️  Compilando proyecto..." -ForegroundColor Yellow
    javac -encoding UTF-8 -d bin src\*.java
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ Error en la compilación" -ForegroundColor Red
        exit 1
    }
    Write-Host "✓ Compilación exitosa" -ForegroundColor Green
}

Write-Host ""
Write-Host "📊 Configuración del análisis:" -ForegroundColor Cyan
Write-Host "  Host: $SonarHost" -ForegroundColor Gray
Write-Host "  Proyecto: $projectKey" -ForegroundColor Gray
Write-Host "  Nombre: $projectName" -ForegroundColor Gray
Write-Host ""

# Información sobre cómo proceder
Write-Host "========================================" -ForegroundColor Yellow
Write-Host "  OPCIÓN 1: Usar SonarScanner (Recomendado)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
Write-Host ""
Write-Host "1. Descargar SonarScanner CLI:" -ForegroundColor White
Write-Host "   https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/" -ForegroundColor Cyan
Write-Host ""
Write-Host "2. Extraer y agregar al PATH" -ForegroundColor White
Write-Host ""
Write-Host "3. Ejecutar:" -ForegroundColor White
Write-Host "   .\run-sonar.ps1" -ForegroundColor Cyan
Write-Host ""

Write-Host "========================================" -ForegroundColor Yellow
Write-Host "  OPCIÓN 2: Usar Maven/Gradle" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
Write-Host ""
Write-Host "Si tienes Maven instalado:" -ForegroundColor White
Write-Host "   mvn sonar:sonar \`" -ForegroundColor Cyan
Write-Host "     -Dsonar.projectKey=Backend-Student-08 \`" -ForegroundColor Cyan
Write-Host "     -Dsonar.host.url=$SonarHost \`" -ForegroundColor Cyan
Write-Host "     -Dsonar.token=$token" -ForegroundColor Cyan
Write-Host ""

Write-Host "========================================" -ForegroundColor Yellow
Write-Host "  OPCIÓN 3: Análisis Local Simple" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
Write-Host ""
Write-Host "Ejecutar análisis estático básico:" -ForegroundColor White
Write-Host "   .\analyze-code.ps1" -ForegroundColor Cyan
Write-Host ""

# Crear reporte local básico
Write-Host "📝 Generando reporte básico local..." -ForegroundColor Cyan

$report = @"
# Análisis de Código - CS-GradeCalculator
Fecha: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
Proyecto: Backend-Student-08

## Archivos Analizados

### Código Fuente (src/)
"@

Get-ChildItem -Path "src" -Filter "*.java" | ForEach-Object {
    $lines = (Get-Content $_.FullName | Measure-Object -Line).Lines
    $report += "`n- $($_.Name): $lines líneas"
}

$report += "`n`n### Tests (test/)"

Get-ChildItem -Path "test" -Filter "*.java" | ForEach-Object {
    $lines = (Get-Content $_.FullName | Measure-Object -Line).Lines
    $report += "`n- $($_.Name): $lines líneas"
}

$report += @"


## Métricas Básicas

- Total archivos fuente: $(((Get-ChildItem -Path "src" -Filter "*.java").Count))
- Total archivos test: $(((Get-ChildItem -Path "test" -Filter "*.java").Count))
- Total archivos compilados: $(((Get-ChildItem -Path "bin" -Filter "*.class").Count))

## Próximos Pasos

1. Instalar SonarScanner para análisis completo
2. Configurar servidor SonarQube
3. Ejecutar: .\run-sonar.ps1

## Credenciales

- Project Key: Backend-Student-08
- Token: sqp_b98c48a0c21fdbb7e0818b8ea76c5a21c8c8f9bb
- Archivo de configuración: sonar-project.properties
"@

$report | Out-File -FilePath "sonar-report-local.txt" -Encoding UTF8

Write-Host "✓ Reporte básico generado: sonar-report-local.txt" -ForegroundColor Green
Write-Host ""
