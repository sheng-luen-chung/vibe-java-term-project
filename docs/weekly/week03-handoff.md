# Week 03 Handoff Context (2026-04-14)

## Project
- Repository: `vibe-java-term-project`
- Branch in progress: `w03-boundary-bounce-shoot-score`
- Source convention: package-based files under `src/weekX`.

## Current Implementation
- Main file: `src/week3/Week3CircleFollowsMouse.java`
- Implemented features:
  - player left/right boundary clamp
  - bullet firing by mouse press
  - bullet auto-remove when leaving top edge
  - vertical meteor + bouncing meteor
  - bullet collision scoring (+10) and console `hit`
  - HUD: frame counter and score
  - game over overlay when player collides with hazards

## Evidence / Reporting Files
- Weekly report: `docs/weekly/week03.md`
- Weekly guide: `docs/week3-boundary-bounce-shoot.md`
- Prompt reference: `docs/weekly/week03-prompts.txt`
- Suggested evidence outputs:
  - `docs/weekly/assets/week03-run.png`
  - `docs/weekly/assets/week03-demo.mp4`

## Resume Prompt (for next session)
Use this at the start of next chat:

```text
請接續我的 vibe-java-term-project Week 3，先讀 src/week3/Week3CircleFollowsMouse.java 與 docs/weekly/week03.md，再幫我規劃 Week 4 功能與分支。
```
