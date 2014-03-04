package baan.onderdelen;

import baan.nxtcomm.DeviceInfo;
import baan.nxtcomm.LightSensor;
import baan.nxtcomm.Motor;
import baan.nxtcomm.NXT;
import baan.nxtcomm.Output;
import baan.nxtcomm.io.FileInfo;
import baan.nxtcomm.*;
import baan.nxtcomm.io.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;
import baan.utilities.Log;

/* 1. deze klasse controleert of er een fysieke trein beschikbaar is en probeert verbinding te
 *    maken via de com-poort over bluetooth
 * 2. als er geen trein is wordt deze trein een neptrein en genereert alleen op aangeven van
 *    de bovenliggende laag interrupts
*/
public class TreinOrigineel extends Observable implements ChangeListener, Runnable {
    // TODO: settings to be put in a "settings" file in same directory
    private NXT nxt;
    private boolean driving;
    private final byte speed = (byte) 50; // for the settings file
    private int lightvalue;
    private Log log = new Log();
    private String compoort; // for the settings file
    private final int nr = 0;
    private ArrayList<Motor> motors; // for the settings file
    private ArrayList<LightSensor> lights; // for the settings file
    
    public TreinOrigineel(String compoort) {
        this.compoort = compoort;
    }
    
    public void run() {
        try{
            connect();
            //nxts[i].getSpeaker().playTone((short)1000, (short)1000);
            //probeer te zorgen dat de motor van de tweede trein aangestuurd wordt
            /* byte[] hoppedcommando = {NXTProtocol.DIRECT_COMMAND_NOREPLY,
                                     NXTProtocol.LS_WRITE,
                                     NXTProtocol.PORT_1,
                                     0x05,
                                     0x00,
                                     NXTProtocol.DIRECT_COMMAND_NOREPLY,
                                     NXTProtocol.SET_INPUT_MODE,
                                     NXTProtocol.SENSOR_PORT_1,
                                     NXTProtocol.LIGHT_ACTIVE,
                                     NXTProtocol.PCTFULLSCALEMODE};
            byte[] directcommando = {NXTProtocol.DIRECT_COMMAND_REPLY,
                                    NXTProtocol.SET_INPUT_MODE,
                                    NXTProtocol.SENSOR_PORT_1,
                                    NXTProtocol.LIGHT_INACTIVE,
                                    NXTProtocol.PCTFULLSCALEMODE,
                                    0x00};*/
            //nxt.getComm().sendData(hoppedcommando);
            //nxt.kill();
            
            LightSensor light = new LightSensor(100);
            nxt.setInput((byte)0, light);           //lightsensor wordt aangesloten op poort 0 (port 1)
            light.setSlope(1);                      //waarschuwd alleen als de waarde 12 of meer veranderd
            light.setLight(true);
            light.addChangeListener(this);          //stateChanged() wordt aangeroepen als de lichtwaarde veranderd
            lightvalue = light.scaledValue;
            log.logThis("lightvalue = " + Integer.toString(lightvalue));
            
            // wel de compoort weer afsluiten...
            Motor motor = new Motor();
            nxt.setOutput((byte)0, motor);         //motor wordt aangesloten op poort 0 (port A)
            //motor.forward(speed);                  //motor vooruit aan
            //motor.backward(speed);                 //motor achteruit aan
            driving = true;
            while (driving){}
            //motor.stop();
            //nxt.kill();
        } catch(IOException ioe){
            log.logThis("Error with NXT on "+ compoort);
            ioe.printStackTrace();
        }
    }
    
    private void connect() throws IOException {
        log.logThis("Zoek naar NXT op poort " + compoort);
        nxt = new NXT(compoort);
        DeviceInfo info = nxt.getDeviceInfo();
        log.logThis("Nxt op " + compoort + " gevonden");
        printStatus();
    }
    
    public void driveToNextLightSensorPoint() {
        // make the train drive up to the next point
        // measured by the lightsource
    }
    
    public void stateChanged(ChangeEvent ce){
        if(ce.getSource() instanceof LightSensor){
            lightChanged((LightSensor) ce.getSource());
        }
    }
    
    private void lightChanged(LightSensor light) {
        int currentlight = light.scaledValue;
            log.logThis(Integer.toString(currentlight - lightvalue));
            //log.logThis(currentlight);
            if (Math.abs(currentlight - lightvalue) > 10) {
                try{
                    Output o = light.getParent().getOutput(0);
                    if(driving){
                        if(o instanceof Motor)
                            log.logThis("Stop de motor!!!");
                        nxt.getSpeaker().playSoundFile("Woops.rso");
                        ((Motor)o).stop();
                        light.setLight(false);
                        nxt.kill();
                        driving = !driving;
                    }
                }catch(IOException ioe){}
            }
            lightvalue = currentlight;
    }
    
    private void printStatus() throws IOException{
        DeviceInfo info = nxt.getDeviceInfo();
        log.logThis("-------------------------");
        log.logThis("Name: "+ info.NXTname);
        log.logThis("Bluetooth adress: "+ info.bluetoothAddress);
        log.logThis("Signal strength: "+ info.signalStrength);
        log.logThis("Free memory: "+ info.freeFlash);
        log.logThis("Protocol version: "+ info.protocolversion);
        log.logThis("Firmware version: "+ info.firmwareversion);
        log.logThis("Battery level: "+ nxt.getBatteryLevel());
        
        log.logThis("Files: ");
        Iterator<FileInfo> iterator = nxt.findFiles("*.*");
        while(iterator.hasNext())
            log.logThis("    " + iterator.next().fileName);
        
    }
}