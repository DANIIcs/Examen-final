# Compilar el proyecto
echo "Compilando clases..."
New-Item -ItemType Directory -Force -Path "bin" | Out-Null
javac -encoding UTF-8 -d bin src/*.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Compilación exitosa" -ForegroundColor Green
    
    # Ejecutar la aplicación
    Write-Host "`nEjecutando CS-GradeCalculator..." -ForegroundColor Cyan
    java -cp bin GradeCalculatorApp
} else {
    Write-Host "✗ Error en la compilación" -ForegroundColor Red
}
