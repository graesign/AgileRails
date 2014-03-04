package baan.nxtcomm;

import java.util.Timer;
import java.util.Vector;
import java.io.IOException;
import java.util.Iterator;
import baan.nxtcomm.io.NXTCommand;
import baan.nxtcomm.io.NXTComm;
import baan.nxtcomm.io.ByteOps;
import baan.nxtcomm.io.FileInfo;
import baan.nxtcomm.io.FileIterator;
import baan.nxtcomm.io.NXTProtocol;

/**
 *	The NXT class represents the actual NXT device.
 *	An instance can be made for every NXT device connected to the COMM ports.
 *	{@link NXT#setOutput(int, Output)} can be used for adding {@link Output} classes
 *	(like {@link Motor}) and {@link NXT#setInput(int, Input)} for {@link Input} classes
 *	(like {@link LightSensor} or {@link TouchSensor}).
 *<p>
 *	This class contains a {@link Timer} instance that will be passed to every {@link Input}
 *	instance added in order to keep the amounts of threads for update checks on those sensors
 *	to a minimum.
 *<p>
 *	Note: Keep in mind that connection problems can occur at any time, causing an {@link IOException}
 *	to be thrown by the next method that makes a call to the device.
 */

public class NXT {
    private NXTCommand nxtcommand;
    /** Speaker object to control the NXT internal speaker */
    public Speaker speaker;
    /** Contains information about the NXT */
    public DeviceInfo device;
    
    private Timer timer;
    private Vector<Output> out;
    private Vector<Input> in;
    
    /**
     * Upon calling the constructor a connection is created
     *	and information for {@link DeviceInfo} is downloaded.
     *
     * @param  commport      contains a valid COMM port name(example: "COM1")
     * @throws IOException	 if the connection with the COMM port could not be made, the NXT does
     *	not respond or an error occured while trying to connect.
     *	Check {@link IOException#getMessage()} for details.
     */
    public NXT(String commport) throws IOException{
        nxtcommand = new NXTCommand(commport);
        timer = new Timer();
        out = new Vector<Output>(4);
        in = new Vector<Input>(4);
        
        speaker = new Speaker(nxtcommand.nxtcomm);
        device = new DeviceInfo(nxtcommand.nxtcomm);
    }
    
    /**
     * Connect output to port on this NXT. After calling this method output can
     *	communicate with whatever is plugged into the real NXT device on this
     *	port.
     *
     * @param  port      the port to connect to
     * @param  output    the Ouput instance to control output port
     * @throws IOException	 if errors occur when initializing output.
     *	Check {@link IOException#getMessage()} for details.
     */
    public void setOutput(int port, Output output) throws IOException{
        output.setPort((byte)port);
        output.setParent(this);
        if(out.size() < port+ 1)
            out.add((byte)port, output);
        else
            out.set((byte)port, output);
    }
    
    /**	Returns the Output object that controls this port
     *
     * @param  port    a port on the NXT
     * @return		the Output object set to this port, or <code>null</code> if nothing is set to the port.
     */
    public Output getOutput(int port){
        return out.get((byte)port);
    }
    
    /**
     * Connect input to port on this NXT. After calling this method output can
     *	communicate with the sensor that is plugged into the real NXT device on this
     *	port.
     *
     * @param  port     the port to connect to
     * @param  input	the Input instance to control input port
     * @throws IOException	 if errors occur when initializing input.
     *	Check {@link IOException#getMessage()} for details.
     */
    public void setInput(int port, Input input) throws IOException{
        input.setPort((byte)port);
        input.setParent(this);
        input.setTimer(timer);
        if(in.size() < port+ 1)
            in.add((byte)port, input);
        else
            in.set((byte)port, input);
    }
    
    /**	Returns the Input object that controls this port
     *
     * @param  port    a port on the NXT
     * @return		the Input object set to this port, or <code>null</code> if nothing is set to the port.
     */
    public Input getInput(int port){
        return in.get((byte)port);
    }
    
    /**	Returns a DeviceInfo instance with information about the NXT device.
     *
     * @return		a DeviceInfo instance.
     */
    public DeviceInfo getDeviceInfo(){
        return device;
    }
    
    /**	Returns a Speaker instance that can play sounds on the NXT device.
     *
     * @return		a Speaker instance.
     */
    public Speaker getSpeaker(){
        return speaker;
    }
    
    /**	Returns the current battery level of the NXT device.
     *
     * @return		a short containing the battery level in millivolts.
     * @throws IOException	 if the NXT returns an error or incorrect data.
     *	Check {@link IOException#getMessage()} for details.
     */
    public short getBatteryLevel() throws IOException{
        byte reply[] = nxtcommand.nxtcomm.getDirect(NXTProtocol.GET_BATTERY_LEVEL, new byte[0]);
        if(reply[1] != 11)
            throw new IOException("Weird data reply received.");
        
        return ByteOps.byteToShort(reply, 3);
    }
    
    /**	Returns the program file that is currently running on the NXT.
     *
     * @return		a FileInfo containing the name of the file.
     * @throws IOException	 if the NXT returns an error.
     *	Check {@link IOException#getMessage()} for details.
     */
    public FileInfo getRunningFile() throws IOException{
        byte reply[] = nxtcommand.nxtcomm.getDirect(NXTProtocol.GET_CURRENT_PROGRAM_NAME, new byte[0]);
        String name = ByteOps.byteToStr(reply, 2, reply.length-2);
        
        return new FileInfo(nxtcommand.nxtcomm, name);
    }
    
    /**	Creates a FileInfo object with the specified filename.
     *
     * @param  filename    the name of a file
     * @return		a FileInfo object containing the name of the file.
     */
    public FileInfo getFile(String filename){
        return new FileInfo(nxtcommand.nxtcomm, filename);
    }
    
    /**	Searches the filesystem on the NXT for files that match searchstr. The iterator can be
     * used to cycle through the found files.
     *
     * @param  searchstr    the string to look for. "*" wildcards are allowed.
     * @return		An Iterator that iterates over FileInfo representations of the found files.
     * @throws IOException	 if the NXT returns an error.
     *	Check {@link IOException#getMessage()} for details.
     */
    public Iterator<FileInfo> findFiles(String searchstr) throws IOException{
        return new FileIterator(nxtcommand.nxtcomm, searchstr);
    }
    
    /** Returnes the communication class that is used to send byte codes to the NXT device.
     *
     * @return		A NXTComm object that has a connection to the NXT device.
     * @see baan.nxtcomm.io.NXTProtocol
     */
    public NXTComm getComm(){
        return nxtcommand.nxtcomm;
    }
    
    /** Kills the connection with the NXT device and stops the timer
     *
     * @see baan.nxtcomm.io.NXTComm for it's close() method
     */
    public void kill() throws IOException{
        timer.cancel();
        nxtcommand.close();
    }
}

