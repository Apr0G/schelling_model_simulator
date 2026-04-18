import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Graphical visualizer for Schelling simulator
 * 
 * @author swapneel, modified by apr0g
 * 
 */
public class SchellingVisualizer {

	// frame for drawing
	private JFrame frame;

	private SchellingSimulator simulator;

	/**
	 * Used to determine how large to make each cell on the grid
	 */
	public final int SCALE = 12;

	/**
	 * A component representing the grid
	 * 
	 * @author zives
	 */
	private class SchellingMap extends Component {

		private static final long serialVersionUID = 1L;

		public Dimension getPreferredSize() {
			return new Dimension((simulator.getWidth() + 1) * SCALE + 1,
					(simulator.getHeight() + 1) * SCALE + 1);
		}

		public Dimension getMinimumSize() {
			return getPreferredSize();
		}

		/**
		 * Draw the grid
		 */
		public void paint(Graphics g) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, (simulator.getWidth()) * SCALE - 1,
					(simulator.getHeight()) * SCALE - 1);

			g.setColor(Color.BLACK);
			g.drawRect(0, 0, (simulator.getWidth()) * SCALE - 1,
					(simulator.getHeight()) * SCALE - 1);

			for (int x = 0; x < simulator.getWidth(); x++) {
				for (int y = 0; y < simulator.getHeight(); y++) {
					int label = simulator.getGrid(x, y);

					if (label == 1) {
						g.setColor(Color.BLUE);
						g.fillRect(x * SCALE, y * SCALE, SCALE - 1, SCALE - 1);
						g.setColor(Color.LIGHT_GRAY);
						g.drawRect(x * SCALE, y * SCALE, SCALE - 1, SCALE - 1);
					} else if (label == 2) {
						g.setColor(Color.YELLOW);
						g.fillRect(x * SCALE, y * SCALE, SCALE - 1, SCALE - 1);
						g.setColor(Color.LIGHT_GRAY);
						g.drawRect(x * SCALE, y * SCALE, SCALE - 1, SCALE - 1);
					} else if (label == 3) {
						g.setColor(Color.GREEN);
						g.fillRect(x * SCALE, y * SCALE, SCALE - 1, SCALE - 1);
						g.setColor(Color.LIGHT_GRAY);
						g.drawRect(x * SCALE, y * SCALE, SCALE - 1, SCALE - 1);
					} else {
						g.setColor(Color.LIGHT_GRAY);
						g.fillRect(x * SCALE, y * SCALE, SCALE - 1, SCALE - 1);
						g.setColor(Color.DARK_GRAY);
						g.drawRect(x * SCALE, y * SCALE, SCALE - 1, SCALE - 1);
					}
				}
			}
		}
	}

	/**
	 * The constructor
	 * Will create an object of the simulator and create the graphics
	 */
	public SchellingVisualizer() {
		/*
		Create new simulator
		numClasses: number of agents (2 or 3)
		population: total population
		populationDistribution (Triple): each argument represents the population distribution of each agent respectively
		numNeighbors: number of neighbors of the same type needed to satisfy the agent (for agents 1, 2)
		width & height: sizes of the grid
		*/
		simulator = new SchellingSimulator(228, 3, (int) (70 * 70 * 0.75), new SchellingSimulator.Triple(0.35, 0.35, 0.3), 3, 70, 70);

		
		//do all the graphics
		frame = new JFrame("Schelling Visualizer");

		frame.setLayout(new BorderLayout());

		JPanel p = new JPanel();

		p.add(new SchellingMap());

		frame.add(BorderLayout.CENTER, p);
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Run the simulation
	 * 
	 * @param K the number of trials
	 */
	public void simulate(int K) {
		ArrayList<SchellingSimulator.Triple> homophilyTriple = simulator.simulate(K);

		double avgHomA = 0.0;
		double avgHomB = 0.0;
		double avgHomC = 0.0;


		// Compute mean
		double avgHom = 0;

		for (int i = 0; i < K; i++) {
			avgHomA += homophilyTriple.get(i).A();
			avgHomB += homophilyTriple.get(i).B();
			avgHomC += homophilyTriple.get(i).C();
		}

		avgHomA /= K;
		avgHomB /= K;
		avgHomC /= K;

		avgHom += (avgHomA + avgHomB + avgHomC) / simulator.classes;

		System.out.println("Average homophily ratio for Group A across trials: " + avgHomA);
		System.out.println("Average homophily ratio for Group B across trials: " + avgHomB);

		if (simulator.classes == 3) {
			System.out.println("Average homophily ratio for Group C across trials: " + avgHomC);
		}

		System.out.println("Average homophily ratio for all groups across trials: " + avgHom);

		updateMap();
	}

	/**
	 * This method will update the schelling map, based on the simulation just completed
	 */
	public void updateMap() {
		//redraw the frame
		frame.validate();
		frame.repaint();
		//make the frame visible now - since we are done with the simulation
		frame.setVisible(true);
	}

	/**
	 * Main program
	 */
	public static void main(String[] args) {

		SchellingVisualizer vis = new SchellingVisualizer();
		vis.simulate(300);
	}
}
