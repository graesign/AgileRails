package baan.nxtcomm.io;

import java.io.PrintStream;
import java.io.IOException;

public class NXTCommand implements NXTProtocol{
	public NXTComm nxtcomm;
				
	public NXTCommand(String NXTCOMM1) throws IOException{
		nxtcomm = new NXTComm(NXTCOMM1);
	}
			
	public void deleteUserFlash() throws IOException{
		nxtcomm.setSystem(DELETE_USER_FLASH, new byte[0]);
	}
		
	public int keepAlive() throws IOException{
		byte reply[] = nxtcomm.getDirect(KEEP_ALIVE, new byte[0]);
		return ByteOps.byteToInt(reply, 3);
	}
	
	public byte pollLength(byte bufferNumber) throws IOException{
		byte request[] = {
			bufferNumber
		};
		byte reply[] = nxtcomm.getSystem(POLL_LENGTH, request);
		return reply[4];
	}

	public byte[] poll(byte bufferNumber, byte commandLength) throws IOException{
		byte request[] = {
			bufferNumber, commandLength
		};
		
		byte reply[] = nxtcomm.getSystem(POLL, request);
		if(reply[3] != 0){
			byte replyCommandLength = reply[4];
			byte replyPollCommand[] = new byte[replyCommandLength];
			System.arraycopy(reply, 5, replyPollCommand, 0, replyCommandLength);
			return replyPollCommand;
		}else
			return null;
	}
			
	public void messageWrite(String message, byte inbox) throws IOException{
		byte request[] = new byte[message.length() + 3];
		request[0] = inbox;
		request[1] = (byte)(message.length() + 1);
		System.arraycopy(ByteOps.strToByte(message), 0, request, 2, message.length() + 1);
		nxtcomm.setDirect(MESSAGE_WRITE, request);
	}

	public String messageRead(byte remoteInbox, byte localInbox, boolean remove) throws IOException{
		byte[] reply = nxtcomm.getDirect(MESSAGE_READ, new byte[0]);
		return ByteOps.byteToStr(reply, 4, reply.length-4);
	}

	
	public byte LSGetStatus(byte port) throws IOException{
		byte request[] = { port };
		try{
			byte reply[] = nxtcomm.getDirect(LS_GET_STATUS, request);
			return reply[3];
		}catch(IOException ioe){
			//throw new IOException("LSGetStatus() error: Communications Bus Error");
			return 0;
		}
	}

	public void LSWrite(byte port, byte txData[], byte rxDataLength) throws IOException{
		byte request[] = {
			port, (byte)txData.length, rxDataLength
		};
		nxtcomm.setDirect(LS_WRITE, ByteOps.appendBytes(request, txData));
	}

	public byte[] LSRead(byte port) throws IOException{
		byte request[] = {
			port
		};
		byte reply[] = nxtcomm.getDirect(LS_READ, request);
					
		byte rxLength = reply[3];
		byte rxData[] = new byte[rxLength];
		System.arraycopy(reply, 4, rxData, 0, rxLength);
		return rxData;
	}
	
	public void close() throws IOException{
		nxtcomm.close();
	}
}

