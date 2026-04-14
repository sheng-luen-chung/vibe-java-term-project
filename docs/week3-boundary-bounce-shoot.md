# Week 3 - Boundary, Bounce, and Bullet Hit Score

## This Week's Goal
- Add left/right boundary for player movement.
- Remove bullets automatically when they leave the top edge.
- Add a meteor that bounces on walls.
- When a bullet hits a meteor, both are treated as removed, score +10, and print `hit`.

## Run Steps (PowerShell)
```powershell
mkdir out -ErrorAction SilentlyContinue
javac -d out src/week3/Week3CircleFollowsMouse.java
java -cp out week3.Week3CircleFollowsMouse
```

## Expected Result
- Player stays inside left/right boundaries.
- Clicking fires bullets upward.
- Bullets are removed when flying out of top boundary.
- Bouncing meteor reverses direction after hitting wall.
- Bullet hit on meteor increments score by 10 and prints `hit` in console.
- Frame and score continue updating on top-left.

## Weekly Git Workflow
```powershell
git checkout -b w03-boundary-bounce-shoot-score
git add .
git commit -m "feat: add week 3 boundary bounce bullet collision scoring"
git push -u origin w03-boundary-bounce-shoot-score
```

## Suggested PR Title
`Week 3: boundary checks, bouncing meteor, and bullet hit scoring`
