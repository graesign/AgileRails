package baan.nxtcomm;

import baan.nxtcomm.io.ByteOps;
import baan.nxtcomm.io.NXTComm;
import java.io.IOException;

public class Speaker {
	private NXTComm comm;
	
	public Speaker(NXTComm nxt){
		comm = nxt;
	}
	public void playSoundFile(String fileName) throws IOException{
		playSoundFile(fileName, false);
	}
	
	public void playSoundFile(String fileName, boolean repeat) throws IOException{
		byte request[] = new byte[fileName.length() + 2];
		request[0] = (byte)(repeat ? -1 : 0);
		System.arraycopy(ByteOps.strToByte(fileName), 0, request, 1, fileName.length()+1);
		
		comm.getDirect(comm.PLAY_SOUND_FILE, request);
	}
	public void playTone(short frequency, short duration) throws IOException{
		byte[] request = new byte[4];
		ByteOps.shortToByte(frequency, request, 0);
		ByteOps.shortToByte(duration, request, 2);
		comm.setDirect(comm.PLAY_TONE, request);
	}
	public void stopSoundPlayback() throws IOException{
		comm.setDirect(comm.STOP_SOUND_PLAYBACK, new byte[0]);
	}
}

