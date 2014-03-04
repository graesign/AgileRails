package baan.nxtcomm;

import java.io.IOException;
import baan.nxtcomm.io.NXTProtocol;
import baan.nxtcomm.io.ByteOps;

public class Output {
	public byte port = -1;
	private NXT parent = null;
	
	public byte powerSetpoint;
    public byte mode;
    public byte regulationMode;
    public byte turnRatio;
    public byte runState;
    public long tachoLimit;
    public int tachoCount;
    public int blockTachoCount;
    public int rotationCount;
        
	public Output(){}
	
	public void setPort(byte port) throws IOException{
		this.port = port;
		if(parent != null)
			getStatus();
	}
	public void setParent(NXT nxt) throws IOException{
		parent = nxt;
		if(port > -1)
			getStatus();
	}
		
	public void getStatus() throws IOException{
		if(port == -1 || parent == null)
			return;
		
		byte[] request = { port };
		byte[] data = parent.getComm().getDirect(NXTProtocol.GET_OUTPUT_STATE, request);
		
		powerSetpoint = data[4];
		mode = data[5];
		regulationMode = data[6];
		turnRatio = data[7];
		runState = data[8];
		tachoLimit = ByteOps.byteToInt(data, 9);
		tachoCount = ByteOps.byteToInt(data, 13);
		blockTachoCount = ByteOps.byteToInt(data, 17);
		rotationCount = ByteOps.byteToInt(data, 21);
	}
	
	public void setStatus() throws IOException{
		if(port == -1 || parent == null)
			return;
			
		byte[] request = {
		    port, 
			powerSetpoint, 
			mode, 
			regulationMode, 
			turnRatio, 
			runState, 
			0, 0, 0, 0
		};
		ByteOps.intToByte((int)tachoLimit, request, 6);
		parent.getComm().setDirect(NXTProtocol.SET_OUTPUT_STATE, request);
	}
	
	public void resetMotorPosition(boolean relative) throws IOException{
		if(port == -1 || parent == null)
			return;
			
		byte request[] = {
			port, (byte)(relative ? -1 : 0)
		};
		parent.getComm().setDirect(NXTProtocol.RESET_MOTOR_POSITION, request);
	}
	
}

