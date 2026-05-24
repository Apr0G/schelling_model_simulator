# Schelling Model Simulation

Agent-based simulation of Schelling's Model of Segregation, extended to support a third agent type with zero neighbor preference — used to study how indifferent agents affect overall system segregation.

Original code by Swapneel; extended by apr0g.

---

## Features

- 3 agent types: A (blue), B (yellow), C (green — zero preference)
- Configurable non-uniform population distributions
- Per-group homophily ratio stats across trials
- Text-based (SchellingSimulator) and graphical (SchellingVisualizer) modes
- Optional torus/wrap-around grid topology

---

## How it works

Each agent occupies a cell on a 2D grid. At each step, agents who don't have enough like-type neighbors move to a random empty cell. The simulation runs until all agents are satisfied.

Agent C is indifferent — it never moves, regardless of its neighbors. The experiment measures how the presence and proportion of these indifferent agents changes the segregation level of the system.

---

## Running

**Text mode**
```
javac src/SchellingSimulator.java
java -cp src SchellingSimulator
```

**Graphical mode**
```
javac src/SchellingSimulator.java src/SchellingVisualizer.java
java -cp src SchellingVisualizer
```

---

## Configuration

Edit the constructor in `SchellingVisualizer.java` (or `main` in `SchellingSimulator.java`):

| Parameter | Description |
|---|---|
| `seed` | Random seed for reproducibility |
| `numClasses` | Number of agent types (2 or 3) |
| `pop` | Total population |
| `popDistr` | Triple of population ratios (must sum to ≤ 1.0) |
| `numNeighbors` | Min like-neighbors to satisfy an agent |
| `width / height` | Grid dimensions |

**Example (3 agent types, 70×70 grid):**
```java
new SchellingSimulator(228, 3, (int)(70*70*0.75),
  new Triple(0.35, 0.35, 0.3), 3, 70, 70);
```

---

## Output

Each trial prints per-group homophily ratios and population share. After K trials, averages are reported for all groups.

```
Trial 0:
  Average homophily ratio for Group A: 0.82 / ratio of population: 0.35
  Average homophily ratio for Group B: 0.79 / ratio of population: 0.35
  Average homophily ratio for Group C: 0.00 / ratio of population: 0.30
Average homophily ratio for all groups across trials: 0.54
```

---

## Requirements

- Java 16+ (uses `record` syntax)
- No external dependencies
