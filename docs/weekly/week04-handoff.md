# Week 04 Handoff Context (2026-04-14)

## Project
- Repository: `vibe-java-term-project`
- Branch in progress: `w04-keyboard-controls-pause-restart`
- Source convention: package-based files under `src/weekX`.

## Current Implementation
- Main file: `src/week4/Week4CircleFollowsMouse.java`
- Implemented features:
  - held-key movement (left/right/up/down)
  - movement stops when key released
  - space shooting with max 3 active bullets
  - pause toggle (`P`) and restart (`R`)
  - HUD status text for running/paused/game over
  - inherited meteor collision and score logic from previous week
  - recording toolbar with Start/Stop controls
  - Robot + JavaCV screen capture to `docs/weekly/assets/week04-demo.mp4`

## Evidence / Reporting Files
- Weekly report: `docs/weekly/week04.md`
- Weekly guide: `docs/week4-keyboard-controls.md`
- Prompt reference: `docs/weekly/week04-prompts.txt`
- Suggested evidence outputs:
  - `docs/weekly/assets/week04-run.png`
  - `docs/weekly/assets/week04-demo.mp4`

## Resume Prompt (for next session)
Use this at the start of next chat:

```text
請接續我的 vibe-java-term-project Week 4，先讀 src/week4/Week4CircleFollowsMouse.java 與 docs/weekly/week04.md，再幫我規劃 Week 5 功能與分支。
```
