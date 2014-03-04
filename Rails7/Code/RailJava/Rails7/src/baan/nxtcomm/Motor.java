package baan.nxtcomm;

import java.io.IOException;
import baan.nxtcomm.io.NXTProtocol;

public class Motor extends Output {
	public Motor(){}
	
	public void forward(byte speed) throws IOException{
		setSpeed(speed);
		forward();
	}
		
	public void forward() throws IOException{
		mode = NXTProtocol.MOTORON;
		regulationMode = NXTProtocol.REGULATION_MODE_IDLE;
		runState = NXTProtocol.MOTOR_RUN_STATE_RUNNING;
		setStatus();
	}
	public void backward(byte speed) throws IOException{
		setSpeed((byte)-speed);
		forward();
	}
	
	public void setSpeed(byte speed) throws IOException{
		powerSetpoint = speed;
	}
	
	public void stop() throws IOException{
		powerSetpoint = 0;
		mode = NXTProtocol.BRAKE + NXTProtocol.REGULATED;
		regulationMode = NXTProtocol.REGULATION_MODE_MOTOR_SPEED;
		runState = NXTProtocol.MOTOR_RUN_STATE_IDLE;
		setStatus();
	}
}

