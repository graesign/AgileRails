package main;

import connection.Config;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class RailsApp extends JFrame {
	/**
	 * De default klasse die alle controllers aanmaakt en de gui aanmaakt.
	 * Tevens zorgt deze klasse ervoor dat het scherm fullscreen gaat en dat de
	 * applicatie kan worden afgesloten met de ESC toets.
	 */
	private static final long serialVersionUID = 1L;
	private JPanel paneel;
	public Station station;
	private int time, timediv;
	public static int uur;
	public static int min;
	private RailScherm railScherm;
	private HoofdMenu hoofdMenu;
	private Station2 stationTwee;
	private GraphicsDevice device;
	private ReisBeheer reisBeheer;
	private TreinBeheer treinBeheer;
	private boolean isFullScreen = false;

	public RailsApp(GraphicsDevice device) {
		super(device.getDefaultConfiguration());
		reisBeheer = new ReisBeheer(treinBeheer);

		// Initialiseer tekenscherm (fullscreen)

		this.device = device;
		// Applicatie titel
		setTitle("RailCab Team 6 Project");
		// Controller objecten aanmaken

		// Hoofdpaneel instellen
		paneel = new JPanel();
		paneel.setLayout(null);

		// hoofdmenu instellen
		hoofdMenu = new HoofdMenu();
		hoofdMenu.setBounds(1130, 0, 150, 280);

		// onderscherm instellen
		railScherm = new RailScherm(treinBeheer, reisBeheer);
		railScherm.setBounds(0, 280, 1280, 520);
		treinBeheer = new TreinBeheer(reisBeheer, railScherm);

		// scherm Linksboven instellen
		station = new Station();
		station.setBounds(0, 0, 150, 280);

		// scherm boven instellen
		stationTwee = new Station2();
		stationTwee.setBounds(150, 0, 980, 280);

		// Atributen toevoegen
		paneel.add(railScherm);
		paneel.add(station);
		paneel.add(stationTwee);
		paneel.add(hoofdMenu);
		setContentPane(paneel);

		// Close on escape listener maken
		Action escape = new EscapeAction();

		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), escape);
		getRootPane().getActionMap().put(escape, escape);

		begin();
	}

	public void begin() {
		isFullScreen = device.isFullScreenSupported();
		setUndecorated(isFullScreen);
		setResizable(!isFullScreen);
		if (isFullScreen) {
			// Full-screen mode
			device.setFullScreenWindow(this);
			validate();
		} else {
			// Windowed mode
			pack();
			setVisible(true);
		}
		Statistiek.refresh();
		loop();
	}

	public void loop() {
		while (true) {
			for (Trein cab : treinBeheer.alleTreinen) {
				if (cab.running) {
					cab.run();
				}
			}
			timediv++;
			if (timediv >= 100) {
				time++;
				Statistiek.refresh();
				timediv = 0;
			}
			min = time % 60;
			uur = (time - min) / 60;
			if (uur > 23)uur = uur % 24;
			railScherm.repaint();
			try {
				Thread.sleep(10);
			} catch (Exception E1) {
				System.out.println("Kon niet slapen: " + E1);
			}
		}
		// loop();
	}

	public static void main(String Args[]) {
                Config.load();
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice myDevice = env.getDefaultScreenDevice();
		JFrame frame = new RailsApp(myDevice);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class EscapeAction extends AbstractAction {
		/**
		 * Deze klasse is een actionlistener voor de escape toets
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.exit(getDefaultCloseOperation());
		}
	}

}