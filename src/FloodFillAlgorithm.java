import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class Position {

	int x, y;

	public Position(int x, int y) {

		this.x = x;
		this.y = y;
	}
}

class Main {

	private static final int[] row = { -1, -1, -1, 0, 0, 1, 1, 1 }; // Masīvi attēlo visus 8 iespējamos ceļus (uz augšu,
																	// uz leju, pa labi, pa kreisi un pa diagonālēm).
	private static final int[] column = { -1, 0, 1, -1, 1, -1, 0, 1 };

	private static int inputX = 0; // Tiek definēta X vērtība, lai turpmāk raksturotu pikseļa pozīciju.
	private static int inputY = 0; // Tiek definēta Y vērtība, lai turpmāk raksturotu pikseļa pozīciju.
	private static int gridSize = 10;

	private static Grid grid = new Grid(gridSize); // Tiek izveidots tukšs 10x10 režģis.

	public static boolean isValid(char[][] matrix, int x, int y, char targetColor) { // Pārbauda vai ir iespējams doties
																						// uz norādīto pikseli (x, y) no
																						// tagadējā pikseļa.
		return x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length && matrix[x][y] == targetColor; 
		// Funkcija atgriež false, ja pikselis ir citā krāsā vai ja tas nav valīds.
	}

	static class Grid implements ActionListener {
		private JFrame frame = null;

		public Grid(int length) {

			frame = new JFrame();

			JPanel panel = new JPanel(new GridLayout(length, length, 5, 5)) {

				private static final long serialVersionUID = 1L;

				@Override
				public Dimension getPreferredSize() {
					return new Dimension(500, 500);
				}
			};

			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					JPanel p2 = new JPanel();
					p2.setBackground(Color.gray);
					panel.add(p2);
				}
			}

			JMenuBar mb;
			JMenu file;
			JMenuItem run;
			JTextArea ta;
			run = new JMenuItem("Start");
			run.addActionListener(this);
			mb = new JMenuBar();
			file = new JMenu("Run");
			file.add(run);
			mb.add(file);
			ta = new JTextArea();
			ta.setBounds(5, 5, 360, 320);
			frame.add(mb);
			frame.add(ta);
			frame.setJMenuBar(mb);
			frame.setVisible(true);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Flood Fill algorithm");
			frame.setContentPane(panel);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			int numberX = Integer.parseInt(JOptionPane.showInputDialog("Type in X (1-" + gridSize + "): "));
			inputX = numberX; // No lietotāja ievades tiek iegūta X pozīcija sākuma vienībai.

			int numberY = Integer.parseInt(JOptionPane.showInputDialog("Type in Y (1-" + gridSize + "): "));
			inputY = numberY; // No lietotāja ievades tiek iegūta Y pozīcija sākuma vienībai.

			char[][] matrix = { // Tiek nodefinēta matrixrica, kas satur dažādas krāsas, kas saglabātas kā char
								// mainīgais divdimensiju masīvā.
					"RRRRGGGGGG".toCharArray(), "RBRRRRRGBB".toCharArray(), "WWWGGGGBBB".toCharArray(),
					"WWWWWGGGGB".toCharArray(), "WYYYYGGBBB".toCharArray(), "WWWYGGGBBB".toCharArray(),
					"WXWYGYGYYB".toCharArray(), "WWWXXXYBBB".toCharArray(), "WXXXWXXXBB".toCharArray(),
					"WXXBBBBBBB".toCharArray() };

			char replacement = 'C'; // krāsa, ar ko aizstāt.

			floodFill(matrix, inputX, inputY, replacement); // Tiek palaists Flood Fill algoritms, tan padodot
															// divdimensiju masīvu, (x, y) un krāsu, ar ko aizstāt
															// valīdos pikseļus.
															// Aizstāj mērķa krāsu ar krāsu, ar ko aizstāt.

			int i = 0;
			for (char[] row : matrix) {
				System.out.println(Arrays.toString(row)); // Pēc krāsu aizstāšanas rindā, rinda tiek izprintēta.
				for (char c : row) {
					switch (c) {
					case 'Y':
						grid.frame.getContentPane().getComponents()[i].setBackground(Color.YELLOW);
						i++;
						break;
					case 'G':
						grid.frame.getContentPane().getComponents()[i].setBackground(Color.GREEN);
						i++;
						break;
					case 'X':
						grid.frame.getContentPane().getComponents()[i].setBackground(Color.GRAY);
						i++;
						break;
					case 'W':
						grid.frame.getContentPane().getComponents()[i].setBackground(Color.WHITE);
						i++;
						break;
					case 'R':
						grid.frame.getContentPane().getComponents()[i].setBackground(Color.RED);
						i++;
						break;
					case 'B':
						grid.frame.getContentPane().getComponents()[i].setBackground(Color.BLUE);
						i++;
						break;
					case 'C':
						grid.frame.getContentPane().getComponents()[i].setBackground(Color.PINK);
						i++;
						break;
					}
				}
			}
		}
	}

	public static void floodFill(char[][] matrix, int x, int y, char replacement) { // Flood Fill algoritms izmantojot
																					// BFS.

		Queue<Position> queue = new ArrayDeque<>(); // Izveido rindu un ierindo sākotnējo pikseli.
		queue.add(new Position(x, y));

		char targetColor = matrix[x][y]; // Gūst info par mērķa krāsu.

		if (targetColor == replacement) { // Pārbauda vai krāsa ir tāda pati kā krāsa, ar ko aizstāt.
			return;
		}

		while (!queue.isEmpty()) { // Pilda darbības tik ilgi, kamēr rinda nav tukša.
			Position node = queue.poll(); // Izņem no rindas attiecīgo vienību un apstrādā to.

			x = node.x; // (x, y) nosaka tagadējo pikseli.
			y = node.y;

			matrix[x][y] = replacement; // Aizstāj tagadējo pikseli ar krāsu, kas ir norādīta kā krāsa, ar ko aizstāt.

			for (int k = 0; k < row.length; k++) { // Apstrādā visus 8 tagadējā pikseļa kaimiņu pikseļus un ierindo tos,
													// ja tie ir valīdi.
				if (isValid(matrix, x + row[k], y + column[k], targetColor)) { // Pārbauda vai kaimiņu pikselis pozīcijā
																				// (x + row[k], y + column[k]) ir valīds
																				// un ir tādā pašā krāsā kā tagadējais
																				// pikselis.
					queue.add(new Position(x + row[k], y + column[k])); // Pievieno kaimiņa pikseli rindai.
				}
			}
		}
	}

	public static void main(String[] args) {

		char[][] matrix = { // Tiek nodefinēta matrixrica, kas satur dažādas krāsas, kas saglabātas kā char
							// mainīgais divdimensiju masīvā.
				"RRRRGGGGGG".toCharArray(), "RBRRRRRGBB".toCharArray(), "WWWGGGGBBB".toCharArray(),
				"WWWWWGGGGB".toCharArray(), "WYYYYGGBBB".toCharArray(), "WWWYGGGBBB".toCharArray(),
				"WXWYGYGYYB".toCharArray(), "WWWXXXYBBB".toCharArray(), "WXXXWXXXBB".toCharArray(),
				"WXXBBBBBBB".toCharArray() };

		int i = 0;
		for (char[] row : matrix) {
			System.out.println(Arrays.toString(row)); // Pēc krāsu aizstāšanas rindā, rinda tiek izprintēta.
			for (char c : row) {
				switch (c) {
				case 'Y':
					grid.frame.getContentPane().getComponents()[i].setBackground(Color.YELLOW);
					i++;
					break;
				case 'G':
					grid.frame.getContentPane().getComponents()[i].setBackground(Color.GREEN);
					i++;
					break;
				case 'X':
					grid.frame.getContentPane().getComponents()[i].setBackground(Color.GRAY);
					i++;
					break;
				case 'W':
					grid.frame.getContentPane().getComponents()[i].setBackground(Color.WHITE);
					i++;
					break;
				case 'R':
					grid.frame.getContentPane().getComponents()[i].setBackground(Color.RED);
					i++;
					break;
				case 'B':
					grid.frame.getContentPane().getComponents()[i].setBackground(Color.BLUE);
					i++;
					break;
				}
			}
		}
	}
}
