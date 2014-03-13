package main;

public class Sensor {
	// Posities
	private double[] posities = { 2.5, 6, 7.5, 11, 14.5, 18, 19.5, 23, 26.5,
			30, 31.5, 35, 39.5, 43, 44.5, 47.5, 52.5, 56, 57.5, 61, 64.5, 68,
			69.5, 73, 76.5, 80, 81.5, 85, 89.5, 93, 94.5, 97.5 };
	private int id;
	private CabAnimation cab;

	public Sensor(int id, CabAnimation cab) {
		this.id = id;
		this.cab = cab;
	}

	public int check() {
		int sensorid = 0;
		for (double sp : posities) {
			if (cab.positie >= sp - 0.15 && cab.positie <= sp + 0.15) {
				// Boolean controleert of dit een positie is die in een
				// wissel zit
				boolean iswissel = (sensorid - 1) % 4 == 0
						|| (sensorid - 2) % 4 == 0;
				if ((iswissel && cab.inwissel) || !iswissel) {
					return sensorid + 1;
				}
			}
			sensorid++;
		}
		return 0;
	}
}
