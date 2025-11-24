# Script para compilar y ejecutar tests
Write-Host "Compilando proyecto y tests..." -ForegroundColor Cyan

# Crear directorios
New-Item -ItemType Directory -Force -Path "bin" | Out-Null
New-Item -ItemType Directory -Force -Path "bin/test" | Out-Null

# Compilar clases principales
javac -encoding UTF-8 -d bin src/*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ Error compilando clases principales" -ForegroundColor Red
    exit 1
}

Write-Host "✓ Clases principales compiladas" -ForegroundColor Green

# Nota: Para ejecutar los tests se requiere JUnit 5
Write-Host "`nPara ejecutar los tests, necesitas:" -ForegroundColor Yellow
Write-Host "1. Descargar JUnit 5 Platform Console Standalone JAR" -ForegroundColor Yellow
Write-Host "   https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.1/junit-platform-console-standalone-1.10.1.jar" -ForegroundColor Yellow
Write-Host "`n2. Compilar tests con:" -ForegroundColor Yellow
Write-Host '   javac -cp "bin;junit-platform-console-standalone-1.10.1.jar" -d bin test/*.java' -ForegroundColor Cyan
Write-Host "`n3. Ejecutar tests con:" -ForegroundColor Yellow
Write-Host '   java -jar junit-platform-console-standalone-1.10.1.jar --class-path bin --scan-class-path' -ForegroundColor Cyan

Write-Host "`n✓ Proyecto listo para usar" -ForegroundColor Green
Write-Host "Ejecuta: .\run.ps1 para iniciar la aplicación" -ForegroundColor Green
