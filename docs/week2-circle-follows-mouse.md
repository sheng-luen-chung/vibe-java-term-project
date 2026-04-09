# Week 2 - Timer Loop, Meteor, and Frame Counter

## This Week's Goal
- Refactor game loop to use `javax.swing.Timer` at ~60 FPS (`16ms`).
- Separate update logic (`updateGame`) from rendering (`paintComponent`).
- Add one meteor that falls from top to bottom and respawns above screen.
- Show frame count on top-left to verify continuous updates.

## Run Steps (PowerShell)
```powershell
mkdir out -ErrorAction SilentlyContinue
javac -d out src/week2/Week2CircleFollowsMouse.java
java -cp out week2.Week2CircleFollowsMouse
```

## Expected Result
- Black 800x600 game window appears.
- Red ball follows mouse smoothly.
- White stars move downward continuously.
- One meteor falls downward and respawns above once out of screen.
- Top-left frame count keeps increasing.
- Mouse click triggers boost effect and prints `Engine Boost!`.

## Weekly Git Workflow
```powershell
git checkout -b w02-circle-follows-mouse-meteor
git add .
git commit -m "feat: add week 2 timer loop, meteor, and frame counter"
git push -u origin w02-circle-follows-mouse-meteor
```

## Suggested PR Title
`Week 2: timer update loop, meteor falling effect, and frame counter`
