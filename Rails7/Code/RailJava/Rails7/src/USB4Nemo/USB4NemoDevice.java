package USB4Nemo;


import java.io.*;

/**
 * Represents the USB4Nemo device.<br>
 * You can open a connection to the device and get the state of the different
 * objects on the device.<br>
 * <br>
 * <br>
 * When a new instance of the object is created it automaticly checks if the
 * usb4nemo.dll is on the system. If this is not the case, it will be copied
 * from the jar-file to windows\\system32\\usb4nemo.dll if this is where your
 * Windows is installed.
 * 
 * 
 * 
 * @author USB4Nemo Team
 */
public class USB4NemoDevice extends java.util.Observable {

	private USBController usbController;

	private Port port0;

	private Port port1;

	private Port port2;

	private ADConverter ad0, ad1, ad2, ad3, ad4;

	/**
	 * * Example code to open a connection to the device:<br>
	 * <br>
	 * public class Foo extends JPanel implements java.util.Observer{<br>
	 * <br>
	 * private USB4NemoDevice dev;<br>
	 * <br>
	 * public Foo()<br> {<br>
	 * JFrame frame = new JFrame();<br>
	 * <br>
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);<br>
	 * frame.add(this, BorderLayout.CENTER);<br>
	 * frame.setVisible(true); <br>
	 * <br>
	 * dev = new USB4NemoDevice();<br>
	 * dev.getPort0().setPortPinDirections((byte)0);<br>
	 * dev.getPort1().setPortPinDirections((byte)0);<br>
	 * dev.getPort2().setPortPinDirections((byte)0);<br>
	 * <br>
	 * dev.addObserver(this);<br> }<br> }<br>
	 * 
	 */
	public USB4NemoDevice() {
		placeLibrary();
		usbController = new USBController(this);
		// usbController.
		openStream();

		port0 = new Port(0, usbController);
		port1 = new Port(1, usbController);
		port2 = new Port(2, usbController);

		ad0 = new ADConverter(0, usbController);
		ad1 = new ADConverter(1, usbController);
		ad2 = new ADConverter(2, usbController);
		ad3 = new ADConverter(3, usbController);
		ad4 = new ADConverter(4, usbController);

		usbController.start();
	}

	private void placeLibrary() {
		File system = new File("C:\\windows\\system32\\usb4nemo.dll");
		if (!system.exists()) {
			try { // copies the dll from the jar file to
					// "C:\\windows\\system32\\usb4nemo.dll"
				File relative = new File(USB4NemoDevice.class.getResource(
						"usb4nemo.dll").toURI());
				InputStream in = new FileInputStream(relative);
				OutputStream out = new FileOutputStream(system);

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Tries to open a stream to the USB4Nemo device.
	 */
	void openStream() {
		if (usbController.openStream())
			;
		else
			throw new deviceNotFoundException();
	}

	/**
	 * Tries close the thread so comunication with the device can stop.
	 */
	public void close() {
		usbController.close();
	}

	/**
	 * @return Returns the port0.
	 */
	public Port getPort0() {
		return port0;
	}

	/**
	 * @return Returns the port1.
	 */
	public Port getPort1() {
		return port1;
	}

	/**
	 * @return Returns the port2.
	 */
	public Port getPort2() {
		return port2;
	}

	/**
	 * @return Returns ad0.
	 */
	public ADConverter getAD0() {
		return ad0;
	}

	/**
	 * @return Returns ad1.
	 */
	public ADConverter getAD1() {
		return ad1;
	}

	/**
	 * @return Returns ad2.
	 */
	public ADConverter getAD2() {
		return ad2;
	}

	/**
	 * @return Returns ad3.
	 */
	public ADConverter getAD3() {
		return ad3;
	}

	/**
	 * @return Returns ad4.
	 */
	public ADConverter getAD4() {
		return ad4;
	}

	/**
	 * @return String representing the ports on the device.
	 */
	public String toString() {
		return "\n" + port0 + "\n" + port1 + "\n" + port2 + "\n" + ad0 + "\n"
				+ ad1 + "\n" + ad2 + "\n" + ad3 + "\n" + ad4;
	}

	/**
	 * Checks it the device has changed.
	 */
	public void changed() {
		try {
			this.setChanged();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
