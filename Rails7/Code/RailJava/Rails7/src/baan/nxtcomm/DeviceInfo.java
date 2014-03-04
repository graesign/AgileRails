package baan.nxtcomm;

import java.io.IOException;
import baan.nxtcomm.io.NXTProtocol;
import baan.nxtcomm.io.NXTComm;
import baan.nxtcomm.io.ByteOps;

public class DeviceInfo{
	public String NXTname;
    public String bluetoothAddress;
    public int signalStrength;
    public int freeFlash;
    public String protocolversion, firmwareversion;
    
    private NXTComm comm;

    public DeviceInfo(NXTComm nxt) throws IOException{
	    comm = nxt;
	    update();
    }
    
    public void update() throws IOException{
		byte[] data = comm.getSystem(NXTProtocol.GET_DEVICE_INFO, new byte[0]);
	    
		NXTname = ByteOps.byteToStr(data, 3, 15);
		bluetoothAddress = getBluetoothAdress(data, 18);
		signalStrength = ByteOps.byteToInt(data, 25);
		freeFlash = ByteOps.byteToInt(data, 29);
		
		data = comm.getSystem(NXTProtocol.GET_FIRMWARE_VERSION, new byte[0]);
		protocolversion = new String(data[4] + "." + data[3]);
		firmwareversion = new String(data[6] + "." + data[5]);
    }
         
    public void setName(String name) throws IOException{
	    NXTname = name;
		comm.setSystem(NXTProtocol.SET_BRICK_NAME, ByteOps.strToByte(name));
    }

    private String getBluetoothAdress(byte[] data, int offset){
	    String s = "";
	    for(int i=0; i < 7; i++)
	    	s += Integer.toHexString(data[offset + i]) + ":";
	    return  s;
    }
}

