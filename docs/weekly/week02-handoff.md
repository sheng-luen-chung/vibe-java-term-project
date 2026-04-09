# Week 02 Handoff Context (2026-04-09)

## Project
- Repository: `vibe-java-term-project`
- Branch in progress: `w02-circle-follows-mouse-meteor`
- Language/style: Java Swing with package-based source layout (`src/weekX`).

## Current Implementation
- Main file: `src/week2/Week2CircleFollowsMouse.java`
- Implemented features:
  - 800x600 Swing window and black background
  - `javax.swing.Timer` game loop at 16ms
  - clear separation of `updateGame()` and `paintComponent()`
  - smooth circle follow to mouse target
  - falling star background
  - one meteor with respawn above screen
  - frame counter rendered in top-left
  - click boost effect with console output `Engine Boost!`

## Evidence / Reporting Files
- Weekly report: `docs/weekly/week02.md`
- Weekly guide: `docs/week2-circle-follows-mouse.md`
- Prompt reference: `docs/weekly/week02-prompts.txt`
- Suggested evidence outputs:
  - `docs/weekly/assets/week02-run.png`
  - `docs/weekly/assets/week02-demo.mp4`

## Resume Prompt (for next session)
Use this at the start of next chat:

```text
請接續我的 vibe-java-term-project Week 2，先讀 src/week2/Week2CircleFollowsMouse.java 與 docs/weekly/week02.md，再幫我規劃 Week 3 的目標與分支。
```
