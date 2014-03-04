package baan.nxtcomm.io;

/**
 * Convenience class for storing or extracting Strings, ints and shorts in/from
 * byte arrays.
 */

public class ByteOps {
	/**  Creates a new byte array that and stores array1 and array2 in it.
	 * @return a byte array with length array1.length + array2.length that contains de data from array1(
	 *  starting at offset 0) and array2(starting at offset array1.length).
	 */
	public static byte[] appendBytes(byte array1[], byte array2[]){
		byte array[] = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, array, 0, array1.length);
		System.arraycopy(array2, 0, array, array1.length, array2.length);
		return array;
	}
	
	/** Reades 4 bytes from <code>data</code>, starting at <code>offset</code> and creates an int from them. 
	 *		The first byte contains the lowest 8 bits and the fourth the highest.
	 *
	 */
	public static int byteToInt(byte[] data, int offset){
	    int x = 0;
	    for(int i=0; i < 4; i++)
	    	x |= (0xff & data[offset+i]) << (8 * i);
	    return x;
    }
	public static void intToByte(int x, byte[] data, int offset){
	    for(int i=0; i < 4; i++)
	    	data[offset+i] = (byte)(x >>> (8*i));
    }
    public static short byteToShort(byte[] data, int offset){
	    short s = 0;
	    for(int i=0; i < 2; i++)
	    	s |= (0xff & data[offset+i]) << (8 * i);
	    return s;
    }
    public static void shortToByte(short x, byte[] data, int offset){
	    for(int i=0; i < 2; i++)
	    	data[offset+i] = (byte)(x >>> (8*i));
    }
    
	public static byte[] strToByte(String s){
	    byte[] b = new byte[s.length()+1];
	    System.arraycopy(s.getBytes(), 0, b, 0, s.length());
	    b[s.length()] = 0;
	    return b;
    }
    public static String byteToStr(byte[] b, int off, int len){
	    return new String(b, off, len).trim();
    }
}

