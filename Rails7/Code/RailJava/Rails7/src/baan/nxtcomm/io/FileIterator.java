package baan.nxtcomm.io;

import java.io.IOException;
import java.util.Iterator;

/**
 * The FileItorator will ask the NXT to look for the first file when it is initiated.
 * When {@link FileIterator#next()} is called to obtain a FileInfo object the next is
 * immediately requested from the NXT. 
 * When {@link FileIterator#remove()} is called an attempt is made to delete the file.
 *
 * Note: errors from the NXT will be supressed.
 */

public class FileIterator implements Iterator<FileInfo>{
	private FileInfo current = null;
	private byte handle;
	private NXTComm parent;
	
	/**Creates a new FileIterator that iterates through a set of files on the NXT.
	 *  the files that are returned all match <code>wildCard</code>
	 *
	 * @param nxtcomm	the NXT 
	 * @param wildCard  is used for finding files. "*.*" for all files.
	 */
	public FileIterator(NXTComm nxtcomm, String wildCard){
		parent = nxtcomm;
		try{
			analyze(parent.getSystem(parent.FIND_FIRST, ByteOps.strToByte(wildCard)));
		}catch(IOException ioe){
		}
	}
	/** Can be used to tell if there are more files that can be looked up.
	 * @return <code>true</code> if {@link FileIterator#next()} will return a {@link FileInfo} object 
	 *   and <code>false</code> if it will return <code>null</code>.
	 */
	public boolean hasNext(){
		return (current != null);
	}
	
	/** For obtaining the next FileInfo. 
	 *
	 * @return FileInfo or <code>null</code>.
	 */
	public FileInfo next(){
		if(!hasNext())
			return null;
		
		FileInfo ret = current;
		try{
			byte[] request = { handle };
			analyze(parent.getSystem(parent.FIND_NEXT, request));
		}catch(IOException ioe){
			current = null;
		}
		return ret;
	}
	
	/** Deletes the next file.
	 */
	public void remove(){
		if(hasNext()){
			try{
				current.delete();
			}catch(IOException ioe){}
		}
	}
	
	private void analyze(byte[] data){
		handle = data[3];
			
		String fileName = new String(data, 4, 20);
		fileName = fileName.substring(0, fileName.indexOf(".") + 4);
		current = new FileInfo(parent, fileName);
		current.fileSize = ByteOps.byteToInt(data, 24);
	}
}
