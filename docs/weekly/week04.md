# Week 04 Progress Report

## Weekly Goal
- Switch to keyboard-based movement.
- Add vertical movement and limited bullet count.
- Add pause/restart controls with visible status text.

## Features Completed
- Arrow keys control player movement (left/right/up/down).
- Hold key for continuous movement and stop on key release.
- Space key fires bullets upward.
- Active bullets on screen are limited to 3.
- Added `P` key pause toggle and `R` key restart.
- HUD now displays frame, score, and status (`RUNNING`/`PAUSED`/`GAME OVER`).
- Existing meteor and bouncing meteor collision mechanics retained.

## Main Class / Module Updated
- `src/week4/Week4CircleFollowsMouse.java`

## Bugs or Difficulties
- Keyboard focus can be lost after clicking outside panel.
- Mitigated by requesting focus again when panel is clicked.

## How AI Helped
- Refactored Week4 draft into project package structure.
- Implemented held-key movement flags and bullet cap logic.
- Completed weekly documentation and prompt archive in existing format.

## How I Validated AI Output
- Compiled with:
  - `javac -d out src/week4/Week4CircleFollowsMouse.java`
- Manual checks:
  - continuous movement while holding keys
  - movement stops on key release
  - bullet cap at 3
  - `P` pause and `R` restart behavior
  - status text updates correctly

## AI Collaboration Record
- Questions asked to AI:
  - How to convert to keyboard-hold movement pattern.
  - How to avoid space key auto-repeat creating unlimited bullets.
- Useful parts:
  - key state tracking and game state handling.
- Incorrect or incomplete parts:
  - none observed after compile and manual verification.
- Final learning:
  - input-state flags are cleaner than one-shot key events for game movement.

## Prompt Samples
- Successful prompt:
  - "Use arrow keys for movement, space to shoot max 3 bullets, add pause/restart status."
- Failed prompt:
  - "Make keyboard support better."
- Revised prompt:
  - "Hold arrow keys to move continuously and stop on release; P pause, R restart."

## Weekly Evidence
- Screenshot: `docs/weekly/assets/week04-run.png`
- Short video/GIF: `docs/weekly/assets/week04-demo.mp4` (or `.gif`)
