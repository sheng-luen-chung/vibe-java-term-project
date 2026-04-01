param(
    [string]$SourceFile = "src/week1/Week1SwingStarter.java",
    [string]$MainClass = "week1.Week1SwingStarter",
    [string]$OutDir = "out",
    [string]$ScreenshotPath = "docs/weekly/assets/week01-run.png",
    [string]$VideoPath = "docs/weekly/assets/week01-demo.mp4",
    [int]$StartCountdownSec = 5,
    [int]$RecordSec = 15,
    [switch]$SkipScreenshot,
    [switch]$ImportLatestVideo,
    [switch]$CloseGameAtEnd
)

Write-Host "=== Week 1 Evidence Helper ==="
Write-Host "Step 1/4: Compile source..."

if (!(Test-Path $OutDir)) {
    New-Item -ItemType Directory -Path $OutDir -Force | Out-Null
}

javac -d $OutDir $SourceFile
if ($LASTEXITCODE -ne 0) {
    Write-Error "Compilation failed. Please fix compile errors first."
    exit 1
}

Write-Host "Compile OK."
Write-Host "Step 2/4: Launch app..."

$gameProcess = Start-Process -FilePath "java" -ArgumentList @("-cp", $OutDir, $MainClass) -PassThru
Start-Sleep -Seconds 2

Write-Host ""
Write-Host "Step 3/4: Prepare recording"
Write-Host "Bring the game window to front now."
Write-Host "Open Xbox Game Bar: Win + G"
Write-Host "Start recording: Win + Alt + R"
Write-Host ""

for ($i = $StartCountdownSec; $i -ge 1; $i--) {
    Write-Host ("Recording starts in " + $i + "...")
    Start-Sleep -Seconds 1
}

Write-Host "Now recording. Interact with the game."
for ($i = 1; $i -le $RecordSec; $i++) {
    Write-Host ("  elapsed: " + $i + " / " + $RecordSec + " sec")
    Start-Sleep -Seconds 1
}

Write-Host ""
Write-Host "Stop recording now: Win + Alt + R"
Write-Host "Step 4/4: Collect evidence files"

if (-not $SkipScreenshot) {
    $captureScript = Join-Path $PSScriptRoot "capture-screenshot.ps1"
    powershell -ExecutionPolicy Bypass -File $captureScript -OutputPath $ScreenshotPath | Out-Host
}
else {
    Write-Host "Skipped screenshot capture."
}

if ($ImportLatestVideo) {
    $importScript = Join-Path $PSScriptRoot "import-latest-recording.ps1"
    powershell -ExecutionPolicy Bypass -File $importScript -DestinationPath $VideoPath | Out-Host
}
else {
    Write-Host "To import latest video automatically, re-run with -ImportLatestVideo."
}

if ($CloseGameAtEnd -and $null -ne $gameProcess -and !$gameProcess.HasExited) {
    Stop-Process -Id $gameProcess.Id
    Write-Host "Game process closed."
}
else {
    Write-Host "Game is still running. Close it manually when done."
}

Write-Host "Evidence helper finished."
