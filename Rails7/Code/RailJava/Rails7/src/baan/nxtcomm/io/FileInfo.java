package baan.nxtcomm.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The FileInfo object represents a file on the NXT. The file can
 * be run, stopped, read, written to and deleted. 
 * Making a new file can be done by calling NXT.getFile(filename)
 * but the file won't be created until data is written to it.
 */

public class FileInfo{
	/** Contains the name of the file */
	public String fileName;
	/** Contains the filesize of the file */
	public int fileSize = 0;
	
	private NXTComm comm;
		
	/**
	* Create an existing or new file on the NXT.
	* No actual creating or opening is performed until any of the functions are called.
	*
	* @param  nxt      the NXT the file is on.
 	* @param  fileName the name of the file.
	* @see         NXTComm
	*/
	public FileInfo(NXTComm nxt, String fileName){
		this.comm = nxt;
		this.fileName = fileName;
	}
		
	/** Start the program file on the NXT.
	*
	* @throws IOException if the file does not exist or if the NXT returns an error. 
	*	Check {@link IOException#getMessage()} for details.
	*/
	public void start() throws IOException{
		comm.setDirect(NXTProtocol.START_PROGRAM, ByteOps.strToByte(fileName));
	}
	/** Stop the file if it is running.
	*
	* @throws IOException if the file does not exist or if the NXT returns an error.
	*	Check {@link IOException#getMessage()} for details.
	*/
	public void stop() throws IOException{
		comm.setDirect(NXTProtocol.STOP_PROGRAM, new byte[0]);
	}
		
	/** Open the file for reading data 
	*
	* @return      the input stream that can be used to read data from the file
	* @throws IOException if the file does not exist or if the NXT returns an error.
	*	Check {@link IOException#getMessage()} for details.
	* @see         InputStream
	*/
	public InputStream getInputStream() throws IOException{
		byte[] reply = comm.getDirect(NXTProtocol.OPEN_READ, ByteOps.strToByte(fileName));
		
		fileName = ByteOps.byteToStr(reply, 4, 20);
		fileName = fileName.substring(0, fileName.indexOf(".") + 4);
		fileSize = ByteOps.byteToInt(reply, 24);
		return new NXTFileInputStream(reply[3], fileSize);
	}
	/** Open the file for writing data
	*
	* @return      the output stream that can be used to write data to the file
	* @throws IOException if the NXT returns an error.
	*	Check {@link IOException#getMessage()} for details.
	* @see         OutputStream
	*/
	public OutputStream getOutputStream(int size) throws IOException{
		fileSize = size;
			
		byte data[] = new byte[20];
		for(int i=0; i < 16; i++)
			data[i] = 0;
		System.arraycopy(fileName.getBytes(), 0, data, 0, Math.min(fileName.length(), 15));
		ByteOps.intToByte(fileSize, data, 16);		
				
		byte[] reply = comm.getDirect(NXTProtocol.OPEN_WRITE, data);
		return new NXTFileOutputStream(reply[3], fileSize);
	}
	
	/** Delete the file on the NXT
	*
	* @throws IOException if the file does not exist or if the NXT returns an error.
	*	Check {@link IOException#getMessage()} for details.
	*/
	public void delete() throws IOException{
		comm.setSystem(NXTProtocol.DELETE, ByteOps.strToByte(fileName));
	}
	
	class NXTFileInputStream extends InputStream{
		private byte handle;
		private int size;
		
		public NXTFileInputStream(byte handle, int size){
			this.handle = handle;
			this.size = size;
		}
		public int available(){
			return size;
		}
		public void close() throws IOException{
			byte[] request = { handle };
			comm.setSystem(NXTProtocol.CLOSE, request);
		}
		public void mark(int readlimit){}
		public void reset(){}
		public boolean markSupported(){
			return false;
		}
		
		public int read() throws IOException{
			byte[] b = new byte[1];
			read(b);
			return b[0];
		}
		public int read(byte[] b) throws IOException{
			return read(b, 0, b.length);
		}
		public int read(byte[] b, int off, int len) throws IOException{
			if(b == null)
				throw new NullPointerException();
			if(off < 0 || len < 0 || off + len > b.length)
				throw new IndexOutOfBoundsException();
			if(len == 0)
				return 0;
			len = Math.min(len, size);
			
			byte request[] = {
				handle, (byte)len, (byte)(len >>> 8)
			};
			byte reply[] = comm.getSystem(NXTProtocol.READ, request);
			
			int data_len = reply.length - 6;
			System.arraycopy(reply, 6, b, off, data_len);
			size -= data_len;
			return data_len;
		}
		public long skip(long n) throws IOException{
			int len = Math.min((int)n, size);
			byte request[] = {
				handle, (byte)len, (byte)(len >>> 8)
			};
			byte reply[] = comm.getSystem(NXTProtocol.READ, request);
			int data_len = reply.length - 6;
			size -= data_len;
			return data_len;
		}
	}
	class NXTFileOutputStream extends OutputStream{
		private byte handle;
		private int size;
		
		public NXTFileOutputStream(byte handle, int size){
			this.handle = handle;
			this.size = size;
		}
		public void close() throws IOException{
			byte[] request = { handle };
			comm.setSystem(NXTProtocol.CLOSE, request);
		}
		public void flush() throws IOException{}
		public void write(byte[] b) throws IOException{
			write(b, 0 , b.length);
		}
		public void write(byte[] b, int off, int len) throws IOException{
			if(b == null)
				throw new NullPointerException();
			if(off < 0 || len < 0 || off + len > b.length)
				throw new IndexOutOfBoundsException();
			if(len == 0)
				return;
			len = Math.min(len, size);
			
			byte request[] = new byte[len + 1];
			request[0] = handle;
			System.arraycopy(b, off, request, 1, len);
		
			comm.setSystem(NXTProtocol.WRITE, request);
		}
		public void write(int b)  throws IOException{
			byte[] x = { (byte)b };
			write(b);
		}
	}
}

