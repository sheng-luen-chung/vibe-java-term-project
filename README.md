# vibe-java-term-project

Java Swing game development journey (vibe coding style).

## Course Roadmap
- Week 0: Environment setup, GitHub repo init, first PR
- Week 1+: Swing fundamentals and GUI components
- Project A: Space Invaders
- Project B: Tetris

## Repository Workflow
- Use one Pull Request per week
- Keep commits small and focused
- Write what was completed and what remains in each PR description

## Week 1 Quick Start
```powershell
mkdir out -ErrorAction SilentlyContinue
javac -d out src/week1/Week1SwingStarter.java
java -cp out week1.Week1SwingStarter
```

## Week 2 Quick Start
```powershell
mkdir out -ErrorAction SilentlyContinue
javac -d out src/week2/Week2CircleFollowsMouse.java
java -cp out week2.Week2CircleFollowsMouse
```

## Week 3 Quick Start
```powershell
mkdir out -ErrorAction SilentlyContinue
javac -d out src/week3/Week3CircleFollowsMouse.java
java -cp out week3.Week3CircleFollowsMouse
```

## Week 4 Quick Start
```powershell
mvn -q exec:java -Dexec.mainClass=week4.Week4CircleFollowsMouse
```

## Week 4 Recording Setup
- Week 4 now uses JavaCV for on-demand recording.
- Start recording from the toolbar inside the app.
- The output file is saved to `docs/weekly/assets/week04-demo.mp4` when you stop recording or close the window.
