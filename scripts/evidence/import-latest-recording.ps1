param(
    [string]$DestinationPath = "docs/weekly/assets/week01-demo.mp4",
    [string]$CapturesDir = ""
)

if ([string]::IsNullOrWhiteSpace($CapturesDir)) {
    $CapturesDir = Join-Path ([Environment]::GetFolderPath("MyVideos")) "Captures"
}

if (!(Test-Path $CapturesDir)) {
    Write-Error ("Captures folder not found: " + $CapturesDir)
    exit 1
}

$latestVideo = Get-ChildItem -Path $CapturesDir -Filter *.mp4 -File |
    Sort-Object LastWriteTime -Descending |
    Select-Object -First 1

if ($null -eq $latestVideo) {
    Write-Error ("No .mp4 files found in: " + $CapturesDir)
    exit 1
}

$destinationDir = Split-Path -Path $DestinationPath -Parent
if (![string]::IsNullOrWhiteSpace($destinationDir) -and !(Test-Path $destinationDir)) {
    New-Item -ItemType Directory -Path $destinationDir -Force | Out-Null
}

Copy-Item -Path $latestVideo.FullName -Destination $DestinationPath -Force
Write-Output ("Imported video to: " + (Resolve-Path $DestinationPath))
Write-Output ("Source video: " + $latestVideo.FullName)
