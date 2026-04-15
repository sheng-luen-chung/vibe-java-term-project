# Week 4 - Keyboard Control, Pause, and Restart

## This Week's Goal
- Replace mouse movement with keyboard movement.
- Support continuous movement while key is held and stop on release.
- Use space key to shoot with max 3 bullets on screen.
- Add `P` pause and `R` restart with on-screen status.

## Run Steps (PowerShell)
```powershell
mkdir out -ErrorAction SilentlyContinue
javac -d out src/week4/Week4CircleFollowsMouse.java
java -cp out week4.Week4CircleFollowsMouse
```

## Expected Result
- Arrow keys move player continuously while pressed.
- Releasing keys stops movement immediately.
- Space shoots upward bullets with at most 3 active bullets.
- `P` toggles pause/resume.
- `R` resets game state.
- HUD shows `RUNNING`, `PAUSED`, or `GAME OVER`.

## Weekly Git Workflow
```powershell
git checkout -b w04-keyboard-controls-pause-restart
git add .
git commit -m "feat: add week 4 keyboard controls pause restart"
git push -u origin w04-keyboard-controls-pause-restart
```

## Suggested PR Title
`Week 4: keyboard movement, capped shooting, pause/resume, and restart`
