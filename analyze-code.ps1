# Script de analisis estatico local (sin SonarQube)
# Genera un reporte de calidad del codigo

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Analisis Estatico de Codigo Local" -ForegroundColor Cyan
Write-Host "  CS-GradeCalculator" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Analizar todos los archivos Java
Write-Host "Analizando archivos..." -ForegroundColor Cyan
Write-Host ""

$srcFiles = Get-ChildItem -Path "src" -Filter "*.java"
$testFiles = Get-ChildItem -Path "test" -Filter "*.java"

$totalLines = 0
$totalMethods = 0
$totalClasses = 0

Write-Host "Codigo Fuente (src/):" -ForegroundColor Yellow
foreach ($file in $srcFiles) {
    $lines = (Get-Content $file.FullName | Measure-Object -Line).Lines
    $content = Get-Content $file.FullName -Raw
    $methods = ([regex]::Matches($content, 'public\s+\w+\s+\w+')).Count
    $classes = ([regex]::Matches($content, 'class\s+\w+')).Count
    
    $totalLines += $lines
    $totalMethods += $methods
    $totalClasses += $classes
    
    Write-Host "  $($file.Name)" -ForegroundColor Gray
    Write-Host "    Lineas: $lines | Metodos: $methods | Clases: $classes" -ForegroundColor DarkGray
}

Write-Host ""
Write-Host "Tests (test/):" -ForegroundColor Yellow
$testLines = 0
foreach ($file in $testFiles) {
    $lines = (Get-Content $file.FullName | Measure-Object -Line).Lines
    $testLines += $lines
    Write-Host "  $($file.Name)" -ForegroundColor Gray
    Write-Host "    Lineas: $lines" -ForegroundColor DarkGray
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  RESUMEN DEL ANALISIS" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

Write-Host "Metricas Generales:" -ForegroundColor Cyan
Write-Host "  Total lineas (src): $totalLines" -ForegroundColor White
Write-Host "  Total lineas (test): $testLines" -ForegroundColor White
Write-Host "  Total clases: $totalClasses" -ForegroundColor White
Write-Host "  Total metodos: $totalMethods" -ForegroundColor White
Write-Host ""

if ($totalClasses -gt 0) {
    $avgMethodsPerClass = [Math]::Round($totalMethods / $totalClasses, 1)
    $avgLinesPerClass = [Math]::Round($totalLines / $totalClasses, 0)
    
    Write-Host "Calidad del Codigo:" -ForegroundColor Cyan
    Write-Host "  Metodos por clase (promedio): $avgMethodsPerClass" -ForegroundColor White
    Write-Host "  Lineas por clase (promedio): $avgLinesPerClass" -ForegroundColor White
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  Analisis Completado" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

Write-Host "Recomendaciones:" -ForegroundColor Cyan
Write-Host "  1. Instalar SonarScanner para analisis completo" -ForegroundColor White
Write-Host "  2. Ejecutar: .\run-sonar.ps1" -ForegroundColor White
Write-Host "  3. Ver: SONARQUBE_SETUP.md" -ForegroundColor White
Write-Host ""
Write-Host "Credenciales configuradas en: sonar-project.properties" -ForegroundColor Gray
Write-Host "Project Key: Backend-Student-08" -ForegroundColor Gray
Write-Host ""
