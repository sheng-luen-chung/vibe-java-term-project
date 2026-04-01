param(
    [string]$OutputPath = "docs/weekly/assets/week01-run.png"
)

Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

$directory = Split-Path -Path $OutputPath -Parent
if (![string]::IsNullOrWhiteSpace($directory) -and !(Test-Path $directory)) {
    New-Item -ItemType Directory -Path $directory -Force | Out-Null
}

$bounds = [System.Windows.Forms.Screen]::PrimaryScreen.Bounds
$bitmap = New-Object System.Drawing.Bitmap $bounds.Width, $bounds.Height
$graphics = [System.Drawing.Graphics]::FromImage($bitmap)

try {
    $graphics.CopyFromScreen($bounds.Location, [System.Drawing.Point]::Empty, $bounds.Size)
    $bitmap.Save($OutputPath, [System.Drawing.Imaging.ImageFormat]::Png)
    Write-Output ("Saved screenshot to: " + (Resolve-Path $OutputPath))
}
finally {
    $graphics.Dispose()
    $bitmap.Dispose()
}
