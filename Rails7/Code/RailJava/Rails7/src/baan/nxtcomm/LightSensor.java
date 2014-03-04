package baan.nxtcomm;

import java.io.IOException;
import baan.nxtcomm.io.NXTProtocol;

public class LightSensor extends Input {
	int interval;
	public LightSensor(int interval){
		this.interval = interval;
	}
	
	protected void init() throws IOException{
		setMode(NXTProtocol.LIGHT_ACTIVE, NXTProtocol.PCTFULLSCALEMODE);
		schedule(interval);
		update();
	}
	
	public void setLight(boolean b) throws IOException{
		if(b)
			setMode(NXTProtocol.LIGHT_ACTIVE, NXTProtocol.PCTFULLSCALEMODE);
		else
			setMode(NXTProtocol.LIGHT_INACTIVE, NXTProtocol.PCTFULLSCALEMODE);
	}
	
	
}

