package USB4Nemo;

/**
 * Represents a port (8pins) on the USB4Nemo device.<br>
 * Read and write values to the port and set the direction of a port.<br>
 * Set the direction of the port to input or output.<br>
 * <br>
 * 
 * 
 * @author USB4Nemo Team
 */
public class Port {
	public static final boolean INPUT = true;

	public static final boolean OUTPUT = false;

	private byte portPinValues = (byte) 0;

	private byte portPinDirections = (byte) 0;

	private USBController usbcontroller;

	private byte portFirstByteSetup;

	private byte portFirstByteData;

	/**
	 * * Example Code:<br>
	 * <br>
	 * public class Foo extends JPanel implements java.util.Observer{<br>
	 * USB4NemoDevice dev = new USB4NemoDevice();<br>
	 * dev.addObserver(this); <br>
	 * private Led led0 = new Led(); //custom class extends JLabel<br>
	 * led0.addMouseListener(new Listener())<br>
	 * <br>
	 * public void update(java.util.Observable o, Object arg)<br> {<br>
	 * if (dev.getPort0().getPinValue(0))<br> { led0.on(); }<br>
	 * else<br> { led0.off(); }<br> }<br>
	 * <br>
	 * public class Listener implements java.awt.event.MouseListener<br> { <br>
	 * public void mouseClicked(java.awt.event.MouseEvent e)<br>
	 * {//Invoked when the mouse button has been clicked (pressed and released)
	 * on a component.<br>
	 * if (e.getSource() == led0)<br> {<br>
	 * dev.getPort0().setPinValue(0,!dev.getPort0().getPinValue(0));<br>
	 * dev.changed(); dev.notifyObservers();<br> }<br> }<br> }<br>
	 * 
	 * @param portFirstByteData
	 * @param usbcontroller
	 */
	public Port(int portFirstByteData, USBController usbcontroller) {
		this.portFirstByteSetup = (byte) (portFirstByteData + 128);
		this.portFirstByteData = (byte) portFirstByteData;
		this.usbcontroller = usbcontroller;
	}

	/**
	 * Returns the value of the pins.
	 * 
	 * @return The current value of the pins.
	 */
	public byte getPortPinValues() {
		return portPinValues;
	}

	/**
	 * Set the values to the pins.
	 * 
	 * @param pinvalue
	 *            The value of the pins.
	 */
	public void setPortPinValues(byte pinvalue) {
		USB4NemoProtocolPacket pckt = new USB4NemoProtocolPacket(
				this.portFirstByteData, pinvalue);
		usbcontroller.transfer(pckt);
		portPinValues = pinvalue;
	}

	/**
	 * Set the values to the pins from the device.
	 * 
	 * @param pinvalue
	 *            The value of the pins in the device.
	 */
	protected void setPortPinValuesFromDevice(byte pinvalue) {
		portPinValues = pinvalue;
	}

	/**
	 * Returns the direction of the pins.
	 * 
	 * @return The current direction of the pins. (input or output)
	 */
	public byte getPortPinDirections() {
		return portPinDirections;
	}

	/**
	 * Set the direction to the pins.
	 * 
	 * @param direction
	 *            The direction of the pins. (input or output)
	 */
	public void setPortPinDirections(byte direction) {
		USB4NemoProtocolPacket pckt = new USB4NemoProtocolPacket(
				this.portFirstByteSetup, direction);
		usbcontroller.transfer(pckt);
		portPinDirections = direction;
	}

	/**
	 * Set the value of a specific pin.
	 * 
	 * @param pin
	 *            The position of the pin on the port.
	 * @param pinvalue
	 *            The value to set on the pin.
	 */
	public void setPinValue(int pin, boolean pinvalue) {

		if (pinvalue) {
			switch (pin) {
			case 0:
				portPinValues |= 1;
				break;
			case 1:
				portPinValues |= 2;
				break;
			case 2:
				portPinValues |= 4;
				break;
			case 3:
				portPinValues |= 8;
				break;
			case 4:
				portPinValues |= 16;
				break;
			case 5:
				portPinValues |= 32;
				break;
			case 6:
				portPinValues |= 64;
				break;
			case 7:
				portPinValues |= 128;
				break;
			}
		} else {
			switch (pin) {
			case 0:
				portPinValues &= 254;
				break;
			case 1:
				portPinValues &= 253;
				break;
			case 2:
				portPinValues &= 251;
				break;
			case 3:
				portPinValues &= 247;
				break;
			case 4:
				portPinValues &= 239;
				break;
			case 5:
				portPinValues &= 223;
				break;
			case 6:
				portPinValues &= 191;
				break;
			case 7:
				portPinValues &= 127;
				break;
			}
		}
		USB4NemoProtocolPacket pckt = new USB4NemoProtocolPacket(
				(byte) portFirstByteData, portPinValues);
		usbcontroller.transfer(pckt);

	}

	/**
	 * Set the direction of a specific pin.
	 * 
	 * @param pin
	 *            The position of the pin on the port.
	 * @param direction
	 *            The direction to set on the pin. (input or output)
	 */
	public void setPinDirection(int pin, boolean direction) {

		if (direction) {
			switch (pin) {
			case 0:
				portPinDirections |= 1;
				break;
			case 1:
				portPinDirections |= 2;
				break;
			case 2:
				portPinDirections |= 4;
				break;
			case 3:
				portPinDirections |= 8;
				break;
			case 4:
				portPinDirections |= 16;
				break;
			case 5:
				portPinDirections |= 32;
				break;
			case 6:
				portPinDirections |= 64;
				break;
			case 7:
				portPinDirections |= 128;
				break;
			}
		} else {
			switch (pin) {
			case 0:
				portPinDirections &= 254;
				break;
			case 1:
				portPinDirections &= 253;
				break;
			case 2:
				portPinDirections &= 251;
				break;
			case 3:
				portPinDirections &= 247;
				break;
			case 4:
				portPinDirections &= 239;
				break;
			case 5:
				portPinDirections &= 223;
				break;
			case 6:
				portPinDirections &= 191;
				break;
			case 7:
				portPinDirections &= 127;
				break;
			}
		}
		USB4NemoProtocolPacket pckt = new USB4NemoProtocolPacket(
				(byte) portFirstByteSetup, portPinDirections);
		usbcontroller.transfer(pckt);
	}

	/**
	 * Returns the value of a specific pin.
	 * 
	 * @param pin
	 *            The position of the pin on the port.
	 * @return The current value of the pin.
	 */
	public boolean getPinValue(int pin) {

		return (portPinValues & (1 << pin)) != 0;
	}

	/**
	 * Returns the direction of a specific pin.
	 * 
	 * @param pin
	 *            The position of the pin on the port.
	 * @return The current direction of the pins. (input or output)
	 */
	public boolean getPinDirection(int pin) {
		return (portPinDirections & (1 << pin)) != 0;
	}

	/**
	 * @return String representing the port.
	 */
	public String toString() {
		return "Port" + ((int) portFirstByteData) + "\t= "
				+ bits(portPinValues);
	}

	/**
	 * Convert the bits in the byte to a string.
	 * 
	 * @param in
	 *            Bytevalue.
	 * @return a string representing the bits in the byte (10101010).
	 */
	protected static String bits(byte in) {
		String out = "";
		for (int i = 0; i < 8; i++) {
			if ((in & 1) != 0)
				out = "1" + out;
			else
				out = "0" + out;
			in = (byte) (in >>> 1);
		}
		return out;
	}
}
