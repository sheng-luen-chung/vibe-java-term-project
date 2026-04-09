# Week 02 Progress Report

## Weekly Goal
- Convert loop to `javax.swing.Timer` with 16ms updates.
- Keep update and rendering logic separated.
- Add a falling meteor with top respawn behavior.
- Show frame count on screen as runtime evidence.

## Features Completed
- `updateGame()` + `paintComponent()` split structure.
- `javax.swing.Timer` based loop at 16ms interval.
- Smooth mouse-following circle movement with easing.
- 50 falling stars as background.
- 1 meteor falling from top and respawning at random x.
- Top-left frame counter display (`Frame: N`).
- Mouse click boost effect (yellow + larger circle for 0.2s).

## Main Class / Module Updated
- `src/week2/Week2CircleFollowsMouse.java`

## Bugs or Difficulties
- Initial week2 file was outside `src/weekX` structure.
- Updated to package-based structure for consistent compile/run flow.

## How AI Helped
- Reorganized week2 code into project conventions.
- Kept all requested features while standardizing class/package layout.
- Generated matching docs and weekly handoff record.

## How I Validated AI Output
- Compiled with:
  - `javac -d out src/week2/Week2CircleFollowsMouse.java`
- Manual runtime checks:
  - meteor respawn behavior
  - frame counter updates
  - boost timing and visual effect

## AI Collaboration Record
- Questions asked to AI:
  - How to structure week2 code in same format as week1.
  - How to keep Timer update loop and rendering separated.
- Useful parts:
  - package refactor and doc template alignment.
- Incorrect or incomplete parts:
  - none observed after compile check.
- Final learning:
  - Consistent project layout makes weekly PR workflow easier.

## Prompt Samples
- Successful prompt:
  - "Use javax.swing.Timer every 16ms, split updateGame and paintComponent, add meteor and frame count."
- Failed prompt:
  - "Add effects."
- Revised prompt:
  - "Add one meteor falling from top to bottom and respawn above screen at random position."

## Weekly Evidence
- Screenshot: `docs/weekly/assets/week02-run.png`
- Short video/GIF: `docs/weekly/assets/week02-demo.mp4` (or `.gif`)
