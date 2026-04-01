# Week 01 Progress Report

## Weekly Goal
- Build a basic Java Swing game window and interactive ball movement.

## Features Completed
- 800x600 black game window.
- Red ball follows mouse movement.
- Inertia-style smooth follow behavior.
- 50 white stars move downward for space-flight effect.
- Mouse click triggers boost: yellow + 2x size for 0.2 seconds.
- Console output on boost: `Engine Boost!`
- Java SE 7 style rewrite (no lambda syntax).

## Main Class / Module Updated
- `src/week1/Week1SwingStarter.java`

## Bugs or Difficulties
- Initial version used Java 8+ lambda syntax, which did not match course Java SE 7 teaching style.

## How AI Helped
- Generated and iteratively refined Swing rendering, mouse handling, and timer update loop.

## How I Validated AI Output
- Compiled with `javac`.
- Ran and manually tested each required behavior in the app.
- Verified Java SE 7 syntax style after rewrite.

## AI Collaboration Record
- Questions asked to AI:
  - How to build a single-file Swing game window.
  - How to add smooth follow with inertia and background stars.
  - How to convert to Java SE 7 syntax.
- Useful parts:
  - Event loop and rendering structure.
  - Timer-based animation updates.
- Incorrect or incomplete parts:
  - First drafts included lambda syntax, later replaced.
- Final learning:
  - Swing basics: EDT, event listeners, update-render loop.

## Prompt Samples
- Successful prompt:
  - "Use Java Swing to create a black 800x600 game window with a red ball that follows the mouse."
- Failed prompt:
  - "Make it cooler."
- Revised prompt:
  - "Add inertia follow, 50 moving stars, click boost effect, keep Java SE 7 style."

## Weekly Evidence
- Screenshot: `docs/weekly/assets/week01-run.png`
- Short video/GIF: `docs/weekly/assets/week01-demo.mp4` (or `.gif`)

## One-Command Evidence Workflow
```powershell
powershell -ExecutionPolicy Bypass -File scripts/evidence/run-week1-evidence.ps1 -ImportLatestVideo -CloseGameAtEnd
```

This helper will:
- compile and launch the game
- show recording countdown
- capture screenshot automatically
- import latest Game Bar `.mp4` into `docs/weekly/assets/week01-demo.mp4`

## Evidence Capture Steps
1. Run the app and keep the game window visible.
2. Capture screenshot:
   ```powershell
   powershell -ExecutionPolicy Bypass -File scripts/evidence/capture-screenshot.ps1 -OutputPath docs/weekly/assets/week01-run.png
   ```
3. Record 10-30 sec short video with Xbox Game Bar:
   - `Win + G` to open Game Bar
   - `Win + Alt + R` to start/stop recording
4. Move recorded `.mp4` into:
   - `docs/weekly/assets/week01-demo.mp4`
