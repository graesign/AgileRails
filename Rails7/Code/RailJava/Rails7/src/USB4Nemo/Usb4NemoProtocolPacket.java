package USB4Nemo;

/**
 * Represents a USB4Nemo packet. This packets contains a setup and databyte to configure the device.
 *
 * The first byte is the setupbyte, it contains information on which Port or ADConverter to configure.
 * The second byte is the databyte, this byte contains information about the data on the Pin or ADConverter.
 *
 * @author  USB4Nemo Team
 */
class USB4NemoProtocolPacket
{
	
	private byte firstByte;
	private byte secondByte;
	
	public USB4NemoProtocolPacket(byte fb, byte sb)
	{
		firstByte = fb;
		secondByte = sb;
	}
	
	/**
	 * USB4Nemo Setupbyte
	 * @return Setupbyte
	 */
	public byte getFirstByte()
	{
		return firstByte;
	}
	
	/**
	 * USB4Nemo Databyte
	 * @return Databyte
	 */
	public byte getSecondByte()
	{
		return secondByte;
	}
}