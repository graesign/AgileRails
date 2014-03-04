package baan.nxtcomm;

import baan.nxtcomm.io.ByteOps;
import baan.nxtcomm.io.NXTProtocol;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * The Input object controls a sensor connected to one of the input ports on the NXT.
 * After the port and parent NXT object are set init() is called. The values will be
 * set right after update() is called or if the update is scheduled with
 * schedule(int interval).
 * It is preferred that you call {@link NXT#setInput(int, Input)} right after making a new
 * instance because the NXT will set the port, parent NXT and timer.
 */

public class Input {
    
    /** The port this object controls */
    public byte port = -1;
    private NXT parent = null;
    private Timer timer;
    private LinkedList<ChangeListener> listeners = new LinkedList<ChangeListener>();
    private int normal = -1;
    private int slope = 1;
    
    /** <code>true</code> if a sensor is connected to the port */
    public boolean valid;
    /** <code>true</code> if the sensor is calibrated */
    public boolean isCalibrated;
    /** Contains the type of the sensor */
    public byte sensorType;
    /** Contains the mode of the sensor */
    public byte sensorMode;
    /** Contains the raw value returned by the AD converted */
    public short rawADValue;
    /** Contains the normalized version of rawADValue */
    public short normalizedADValue;
    /** Contains the scaled version of rawADValue */
    public short scaledValue;
    /** Contains the calibrated version of rawADValue */
    public short calibratedValue;
    
    /** Create a new uninitialized Input instance */
    public Input(){
    }
    
    /** Set the port that this input controls
     * @param  port  the port this instance should control
     */
    public void setPort(byte port) throws IOException{
        this.port = port;
        if(parent != null && timer != null)
            init();
    }
    /** Set the parent NXT
     * @param  nxt  the NXT object that the sensor is connected to
     */
    public void setParent(NXT nxt) throws IOException{
        parent = nxt;
        if(port != -1 && timer != null)
            init();
    }
    /** Get the parent NXT
     * @return      The NXT object that this instance makes calls to
     */
    public NXT getParent(){
        return parent;
    }
    /** Set the {@link Timer} object for this input to use.
     *	The Timer is used for scheduling updates.
     * @param  t  the Timer object this Input should schedule on
     */
    public void setTimer(Timer t) throws IOException{
        timer = t;
        if(port != -1 && parent != null)
            init();
    }
    /** Initializes the sensor when parent NXT and the port are set. */
    protected void init() throws IOException{
    }
    
    /** Schedules a sensor check in the timer.
     * @param  interval  the update interval in milliseconds
     */
    public void schedule(int interval){
        timer.scheduleAtFixedRate(new Updater(), 0, interval);
    }
    
    /** Sets the type and mode for the sensor.
     *
     * @param  sensorType   the sensor type.
     *	  Possible types defined in {@link NXTProtocol}:
     *		<code>NO_SENSOR, SWITCH, TEMPERATURE, REFLECTION, ANGLE,
     *		LIGHT_ACTIVE, LIGHT_INACTIVE, SOUND_DB, SOUND_DBA,
     *		CUSTOM, LOWSPEED, LOWSPEED_9V, NO_OF_SENSOR_TYPES</code>
     * @param  sensorMode   the sensor mode.
     *	  Possible modes defined in {@link NXTProtocol}:
     *		<code>RAWMODE, BOOLEANMODE, TRANSITIONCNTMODE, PERIODCOUNTERMODE,
     *		PCTFULLSCALEMODE, CELSIUSMODE, FAHRENHEITMODE, ANGLESTEPSMODE,
     *		SLOPEMASK, MODEMASK</code>
     * @throws IOException if the NXT returns an error.
     *	Check {@link IOException#getMessage()} for details.
     */
    public void setMode(byte sensorType, byte sensorMode) throws IOException{
        if(port == -1 || parent == null)
            return;
        
        this.sensorType = sensorType;
        this.sensorMode = sensorMode;
        
        byte request[] = {
            port, sensorType, sensorMode
        };
        parent.getComm().setDirect(NXTProtocol.SET_INPUT_MODE, request);
    }
    
    public void setSlope(int slope){
        this.slope = slope;
    }
    
    /** Calls NXT and updates the field values */
    public void update() throws IOException{
        if(port == -1 || parent == null)
            return;
        
        byte[] request = { port };
        byte[] data = parent.getComm().getDirect(NXTProtocol.GET_INPUT_VALUES, request);
        
        valid = (data[4] != 0);
        isCalibrated = (data[5] == 0);
        sensorType = data[6];
        sensorMode = data[7];
        rawADValue = 		ByteOps.byteToShort(data, 8);
        normalizedADValue =     ByteOps.byteToShort(data, 10);
        scaledValue = 		ByteOps.byteToShort(data, 12);
        calibratedValue = 	ByteOps.byteToShort(data, 14);
        
        if(Math.abs(scaledValue - normal) >= slope){
            if(normal != -1)
                fireChangeEvent();
            normal = scaledValue;
        }
    }
    
    /** Resets the way scaledValue is calculated */
    public void resetScaledInputValue() throws IOException{
        if(port == -1 || parent == null)
            return;
        
        byte request[] = { port };
        parent.getComm().setDirect(NXTProtocol.RESET_SCALED_INPUT_VALUE, request);
    }
    
    public void addChangeListener(ChangeListener l){
        listeners.add(l);
    }
    
    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }
    
    
    private void fireChangeEvent(){
        if(listeners == null)
            return;
        
        ChangeEvent e = new ChangeEvent(this);
        for(int i=0; i < listeners.size(); i++)
            listeners.get(i).stateChanged(e);
    }
    
    class Updater extends TimerTask{
        public void run(){
            try{
                Thread.sleep(10);
                update();
            }catch(Exception e){}
        }
    }
}

