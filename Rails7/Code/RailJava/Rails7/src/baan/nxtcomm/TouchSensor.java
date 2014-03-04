package baan.nxtcomm;

import java.io.IOException;
import baan.nxtcomm.io.NXTProtocol;

public class TouchSensor extends Input {
	private int interval;
	public TouchSensor(int interval){
		this.interval = interval;
	}
	
	protected void init() throws IOException{
		setMode(NXTProtocol.SWITCH, NXTProtocol.BOOLEANMODE);
		schedule(interval);
	}
}

