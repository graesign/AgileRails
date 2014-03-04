package USB4Nemo;

/**
 * Represents the ADconverter on the USB4Nemo device.<br>
 * Gets and sets values of an ADconverter and allows you to poll for new values.<br>
 * 
 * @author USB4Nemo Team
 */
public class ADConverter {

	private double value;

	private USBController usbController;

	private int nr;

	/**
	 * Example Code:<br>
	 * <br>
	 * public class Foo extends JPanel implements java.util.Observer{<br>
	 * USB4NemoDevice dev = new USB4NemoDevice();<br>
	 * dev.addObserver(this);<br>
	 * private JButton poll0 = new JButton("poll AD0");
	 * poll0.addMouseListener(new Listener()); <br>
	 * private JLabel ad0 = new JLabel();<br>
	 * <br>
	 * public void update(java.util.Observable o, Object arg)<br> {<br>
	 * try{ad0.setText((""+dev.getAD0().getValue()).substring(0,7)+"V");}
	 * catch(Exception e){}<br> }<br>
	 * <br>
	 * public class Listener implements java.awt.event.MouseListener<br> { <br>
	 * public void mouseClicked(java.awt.event.MouseEvent e)<br>
	 * {//Invoked when the mouse button has been clicked (pressed and released)
	 * on a component.<br>
	 * if(e.getSource() == poll0) dev.getAD0().poll();<br> }<br> }<br> }<br>
	 * 
	 * @param nr Number of the ADConverter
	 * @param usbController
	 */
	public ADConverter(int nr, USBController usbController) {
		this.nr = nr;
		this.usbController = usbController;
	}

	/**
	 * Sends packet to device to read ADConverter
	 */
	public void poll() {
		usbController.adPoll(this, nr);
	}

	/**
	 * Returns the value of an ADconverter
	 * 
	 * @return The value of the ADConverter.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the values from the ADConverter (device -> pc)
	 * 
	 * @param data
	 * 
	 */
	protected void setValue(byte data) {
		value = (data & 255) / 52f;
	}

	/**
	 * @return String representing the ADConverter.
	 */
	public String toString() {
		return "AD" + nr + "\t= " + value;
	}
}
