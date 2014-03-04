package USB4Nemo;

/**
 * Contains native methods to communicate with the USB4Nemo device through the c++ usb4nemo.dll.
 * Checks if values on the device have changed.
 *
 * This class is called when you make a new instance of the USB4NemoDevice class.
 * The pin-direction will by default be set on output.
 *
 * @author  USB4Nemo Team
 */
public class USBController extends Thread
{
	private USB4NemoDevice device;
	private ADConverter lastToPoll = null;
	private boolean running = true;
	
	public USBController(USB4NemoDevice device)
	{
		this.device = device;
	}
	
	/**
	 * Load the usb4nemo library to map native methods
	 */
	static {
        System.loadLibrary("usb4nemo");
     }
     
	/**
	 * Native method for opening stream to device
	 */
    public native boolean openStream();
    
    /**
	 * Native method for sending out data
	 */
    public native void out(byte protocol, byte data);
    
    /**
	 * Native method for requesting data
	 */
    public native byte[] in();
    
    /**
	 * Sends a packet to the controller
	 * @param pkt Protocolpacket with setup and data.
	 */
    public void transfer(USB4NemoProtocolPacket pkt)
    {
    	out(pkt.getFirstByte(), pkt.getSecondByte());
    }
    
    /**
	 *
	 * @param ad The ADConverter to poll.
	 * @param nr 
	 */
    protected void adPoll(ADConverter ad, int nr)
    {
    	lastToPoll = ad;
    	out((byte)(nr+3),(byte)0);
    }
    public void close()
    {
    	running = false;
    }
    /**
	 * When input is changed it updates the device.
	 */
    public void run()
    {
    	byte temp[]; boolean changed;
    	while((temp = in()) != null && running)// netter zo in while statement 
    	{
    		changed	= false;
    		//check ports for change.
    		if (device.getPort0().getPortPinValues() != temp[0] )
    		{
    			device.getPort0().setPortPinValuesFromDevice(temp[0]);
    			changed = true;
    		}
    		if (device.getPort1().getPortPinValues() != temp[1] )
    		{
    			device.getPort1().setPortPinValuesFromDevice(temp[1]);
    			changed = true;
    		}
    		if (device.getPort2().getPortPinValues() != temp[2] )
    		{
    			device.getPort2().setPortPinValuesFromDevice(temp[2]);
    			changed = true;
    		}
    		//check AD converter for change.
    		if (lastToPoll != null)
    		{
    			changed = true;
    			lastToPoll.setValue(temp[3]);
    			lastToPoll = null;
    			//System.out.println(device);
    		}
    		//call observers update if changed.
    		if (changed)
    		{
    			device.changed();
    			//try,catch to protect against errors in the usercode. (in their update methods)
    			try{ device.notifyObservers(); } catch(Exception e) { e.printStackTrace(); } 
    		}
    	}
    }
}