import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField populationLabel;
	private JTextField textField_2;

	private ArrayList<Point> vertexList = new ArrayList<Point>();
	private int best = Integer.MAX_VALUE;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 547);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		final GraphPanel graphVisualization = new GraphPanel(vertexList);

		graphVisualization.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				vertexList.add(new Point(e.getX(), e.getY()));

				graphVisualization.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
		});

		FlowLayout flowLayout = (FlowLayout) graphVisualization.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(graphVisualization, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblGeneracja = new JLabel("Generacja:");
		lblGeneracja.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblGeneracja);

		final JLabel generationCounter = new JLabel("");
		panel.add(generationCounter);

		JLabel lblNajlepszyZnaleziony = new JLabel("Najlepszy znaleziony:");
		panel.add(lblNajlepszyZnaleziony);

		final JLabel bestSoFar = new JLabel("");
		panel.add(bestSoFar);

		JPanel controlPanel = new JPanel();
		contentPane.add(controlPanel, BorderLayout.SOUTH);

		JLabel lblPopulation = new JLabel("Population");

		populationLabel = new JTextField();
		populationLabel.setColumns(10);

		JLabel lblGeneration = new JLabel("Generation");
		FlowLayout fl_controlPanel = new FlowLayout(FlowLayout.RIGHT, 2, 6);
		controlPanel.setLayout(fl_controlPanel);
		controlPanel.add(lblPopulation);
		controlPanel.add(populationLabel);
		controlPanel.add(lblGeneration);

		textField_2 = new JTextField();
		controlPanel.add(textField_2);
		textField_2.setColumns(10);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				graphVisualization.setMinimumPath(null);
				graphVisualization.repaint();

				int[][] matrix = Graph.calculateMatrix(vertexList);
				int population = Integer.parseInt(populationLabel.getText());
				int generation = Integer.parseInt(textField_2.getText());

				final Genetic work = new Genetic(population, matrix, vertexList);

				work.generateRandomPopulation();

				for (int i = 0; i < generation; i++) {
					Phenotype p = work.getBest();
					final int ii = i;

					graphVisualization.setMinimumPath(p.getPath());
					bestSoFar.setText(p.toString());

					if (p.evaluate() < best) {
						best = p.evaluate();

						System.out.println("nowe minimum - " + p.toString());
						System.out.flush();
					}

					Runnable r = new Runnable() {

						@Override
						public void run() {
							generationCounter.setText("" + ii);
							graphVisualization.repaint();
						}
					};
					new Thread(r).start();

					ArrayList<Phenotype> toCross = work.linearRanking(5);
					ArrayList<Phenotype> crossed = work.cross(toCross);

					work.mutation(crossed, 0.01);
					work.toPopulation(crossed);
				}
			}
		});

		controlPanel.add(btnStart);
	}
}
