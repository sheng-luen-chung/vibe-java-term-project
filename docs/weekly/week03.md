# Week 03 Progress Report

## Weekly Goal
- Add player boundary checks.
- Add bouncing meteor movement.
- Add bullets and collision-based score update.

## Features Completed
- Player x-position is clamped within left/right boundary.
- Bullets are spawned on mouse press and move upward.
- Bullets are removed after leaving top screen.
- Added one bouncing meteor that reverses on wall collision.
- Bullet collision with meteor or bouncing meteor:
  - bullet removed
  - target respawned immediately (treated as removed from scene)
  - score +10
  - console prints `hit`
- Frame and score are rendered in top-left HUD.
- Player collision with meteor triggers game over overlay.

## Main Class / Module Updated
- `src/week3/Week3CircleFollowsMouse.java`

## Bugs or Difficulties
- Original Week3 draft was in repository root and not package-based.
- Refactored to `src/week3` package structure for consistent build flow.

## How AI Helped
- Converted draft into project-standard source layout.
- Added missing `hit` console output for collision prompt requirement.
- Completed matching weekly docs and prompt archive files.

## How I Validated AI Output
- Compiled with:
  - `javac -d out src/week3/Week3CircleFollowsMouse.java`
- Manual checks:
  - boundary clamp behavior
  - bullet cleanup at top edge
  - bounce behavior on wall hit
  - score increase and `hit` output on collisions

## AI Collaboration Record
- Questions asked to AI:
  - How to preserve week2 style while adding shooting/collision.
  - How to keep PR changes clean in weekly workflow.
- Useful parts:
  - collision and state update structure.
- Incorrect or incomplete parts:
  - none observed after compile and manual checks.
- Final learning:
  - feature growth is easier when keeping fixed module structure each week.

## Prompt Samples
- Successful prompt:
  - "Add boundary checks, bouncing meteor, bullet cleanup, and +10 score on hit with console output."
- Failed prompt:
  - "Improve game."
- Revised prompt:
  - "When bullet hits meteor, remove both, add 10 score, print hit."

## Weekly Evidence
- Screenshot: `docs/weekly/assets/week03-run.png`
- Short video/GIF: `docs/weekly/assets/week03-demo.mp4` (or `.gif`)
