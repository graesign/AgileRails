package baan.onderdelen;

import baan.nxtcomm.DeviceInfo;
import baan.nxtcomm.LightSensor;
import baan.nxtcomm.Motor;
import baan.nxtcomm.NXT;
import java.awt.event.ActionEvent;
import javax.swing.event.*;
import java.io.*;
import baan.nxtcomm.*;
import baan.nxtcomm.io.*;
import java.awt.event.ActionListener;

public class Trein extends TreinInterface implements ChangeListener, ActionListener {
    // de variabeleni die alleen initialiseren
    private String compoort = "";
    private NXT nxt;
    private Motor motor;
    private int motorPort = 0;
    private LightSensor light;
    private int lightPort = 0;
    private int currentLight;
    private int treinNummer;
    private byte snelheid = 40;
    private boolean lightOn = false;
    private boolean sensorOn = false;
    private boolean negeer = false;
    
    // de overige variabelen
    private boolean isRijdend = false;
    private boolean isVerbonden =  false;
    static private javax.swing.Timer delayTimer;
    
    public void setCompoort(String compoort) {
        this.compoort = compoort;
    }
    
    public String getCompoort() {
        return compoort;
    }
    
    public int getMotorPoort() {
        return motorPort;
    }
    
    public int getLightPort() {
        return lightPort;
    }
    
    /**
     * Deze methode retourneert het ID van deze trein
     * @return id De ID van de trein
     */
    public int getTreinNummer() {
        return treinNummer;
    }
    
  
    // constructor
    public Trein(int treinNummer) throws TreinException, IOException {
        // maak een trein aan,
        // check of er nog een poort beschikbaar is,
        // verbind hem met de compoort die opgegeven staat in compoorten
        this.treinNummer = treinNummer;
        
        delayTimer = new javax.swing.Timer(300, this);
        delayTimer.stop();
    }
    
    /**
     * Deze methode start de trein
     * @throws baan.onderdelen.TreinException
     * @throws java.io.IOException
     */
    public void start() throws TreinException, IOException {
        // het starten van de trein gebeurt hier.
        if (!this.isRijdend){
            motor.forward(snelheid);
            this.isRijdend = true;
        } else throw new TreinException("Trein is al aan het rijden, stop eerst voor je hem kan starten");
    }
    
    /**
     * Deze methode stopt de trein
     * @throws baan.onderdelen.TreinException
     * @throws java.io.IOException
     */
    public void stop() throws TreinException , IOException {
        // het stoppen van de trein gebeurt hier
        if (this.isRijdend){
            motor.stop();
            this.isRijdend = false;
        } else throw new TreinException("Trein staat op dit moment stil, start hem eerst voor je hem kan stoppen");
    }
    
    /**
     * Deze methode wijzigd de snelheid van de trein
     * @param speed de nieuwe snelheid voor deze trein
     * @throws java.io.IOException
     */
    public void setSnelheid(byte setSnelheid) throws IOException {
        this.snelheid = setSnelheid;
        motor.forward();
    }
    
    public void setLight(boolean lightOn) throws IOException {
        this.lightOn = lightOn;
        light.setLight(lightOn);
    }
    
    public void setSensor(boolean putSensorOn) throws IOException {
        if (putSensorOn) {
            sensorOn = true;
            light.addChangeListener(this);
        } else if (!putSensorOn) {
            sensorOn = false;
            light.removeChangeListener(this);
        }
    }
    
    public String getInfo() throws IOException {
        String infoString = "";
        DeviceInfo info = nxt.getDeviceInfo();
        infoString += "-------------------------\n";
        infoString += "Naam: "+ info.NXTname + "\n";
        infoString += "Bluetooth adres: "+ info.bluetoothAddress + "\n";
        infoString += "Signaal sterkte: "+ info.signalStrength + "\n";
        infoString += "Vrij geheugen: "+ info.freeFlash + "\n";
        infoString += "Protocol versie: "+ info.protocolversion + "\n";
        infoString += "Firmware versie: "+ info.firmwareversion + "\n";
        infoString += "Batterij niveau: "+ nxt.getBatteryLevel() + "\n";
        
        // infoString += "Bestanden: \n";
        // Iterator<FileInfo> iterator = nxt.findFiles("*.*");
        // while(iterator.hasNext()) {
        //     infoString += "    " + iterator.next().fileName + "\n";
        // }
        return infoString;
    }
    
    public void verbind() throws IOException {
        nxt = new NXT(compoort);
        isVerbonden = true;
        initMotor();
        initLight();
    }
    
    public void verbreek() throws IOException {
        light.setLight(false);
        if(isRijdend) {
            try {stop(); } catch(Exception e) { }
        }
        nxt.kill();
        isVerbonden = false;
    }
    
    //interne methodes
    private void initMotor() throws IOException {
        motor = new Motor();
        nxt.setOutput((byte)motorPort, motor);
        motor.stop();
    }
    
    private void initLight() throws IOException {
        light = new LightSensor(10);
        nxt.setInput((byte)lightPort, light);
        light.setLight(false);
        lightOn = false;
    }
    
    public boolean isConnected() {
        return isVerbonden;
    }
    
    public boolean isLightOn() {
        return lightOn;
    }
    
    public boolean isSensorOn() {
        return sensorOn;
    }
    
    public boolean isRijdend() {
        return isRijdend;
    }
    
    public int getCurrentLight() {
        return currentLight;
    }
    
    public void stateChanged(ChangeEvent ce) {
        // Afkomstig van lichtsensor?
        if(ce.getSource() instanceof LightSensor){
        currentLight = light.scaledValue;
            if( light.scaledValue - currentLight < 10)
                if(currentLight > 40 & !negeer) {
                    setChanged();
                    notifyObservers(currentLight);
                    delayTimer.start();
                    negeer = true;
                }
        }
    }

    public void actionPerformed(ActionEvent e) {
        negeer = false;
        delayTimer.stop();
    }
}
