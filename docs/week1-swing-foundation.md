# Week 1 - Swing Foundation

## This Week's Goal
- Build and run your first Java Swing GUI app.
- Understand key GUI components used in game tools.
- Finish with a PR-based workflow update.

## Components You Will Learn
- `JFrame`: Main window
- `JPanel`: Layout container
- `JLabel`: Display text
- `JButton`: Trigger actions
- `JTextField`: One-line input
- `JTextArea`: Multi-line output/log

## Run Steps (PowerShell)
```powershell
mkdir out -ErrorAction SilentlyContinue
javac -d out src/week1/Week1SwingStarter.java
java -cp out week1.Week1SwingStarter
```

## Expected Result
- A window titled `Week 1 - Swing Starter` appears.
- Type a name and click `Start`.
- Event log grows and click count changes.
- `Clear Log` resets content and counter.

## Weekly Git Workflow
```powershell
git checkout -b week1/swing-foundation
git add .
git commit -m "feat: add week 1 swing starter app"
git push -u origin week1/swing-foundation
```

## Suggested PR Title
`Week 1: Swing foundation and first interactive GUI`
