package USB4Nemo;

/**
 * Exceptionhandler in case the device is not found.
 *
 * Will print an error message on screen when the device is not attached or removed while running. 
 * This class is called from USB4NemoDevice.java in the method openStream().
 *
 * @author  USB4Nemo Team
 */
class deviceNotFoundException extends RuntimeException
{
	private String message;
	private static final long serialVersionUID = 4324535L;
	
	/**
	 * Return a message when the device is not found
	 */
	public deviceNotFoundException()
	{
		message = "USB4NEMO Device not found, make sure the device is connected.";
		System.out.println(message);
		this.printStackTrace();
	}
}