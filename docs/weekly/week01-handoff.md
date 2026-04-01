# Week 01 Handoff Context (2026-04-01)

## Project
- Repository: `vibe-java-term-project`
- Branch in progress: `week1/swing-foundation`
- Language/style constraint: Java SE 7 style only (no lambda, no method reference)

## Current Implementation
- Main file: `src/week1/Week1SwingStarter.java`
- Implemented features:
  - 800x600 Swing window, black background
  - Red ball follows mouse with inertia-like smoothing
  - 50 white stars moving downward (space flight effect)
  - Mouse press boost:
    - Ball turns yellow
    - Ball size becomes 2x
    - Returns to normal after 0.2s
    - Prints `Engine Boost!` in console

## Evidence / Reporting Files
- Weekly report: `docs/weekly/week01.md`
- Screenshot script: `scripts/evidence/capture-screenshot.ps1`
- Video import script: `scripts/evidence/import-latest-recording.ps1`
- One-command helper: `scripts/evidence/run-week1-evidence.ps1`
- Expected evidence outputs:
  - `docs/weekly/assets/week01-run.png`
  - `docs/weekly/assets/week01-demo.mp4`

## Resume Prompt (for next session)
Use this at the start of next chat:

```text
請接續我昨天的 vibe-java-term-project，依 Java SE 7 風格繼續 Week 1。先讀 src/week1/Week1SwingStarter.java 與 docs/weekly/week01.md，再帶我做下一個 prompt。
```
