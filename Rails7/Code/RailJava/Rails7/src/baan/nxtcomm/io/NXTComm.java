package baan.nxtcomm.io;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class NXTComm implements NXTProtocol{
    private CommPort commPort;
    private InputStream in;
    private OutputStream out;
    private final int SERIAL_TIMEOUT = 2000;
    private final int BLUETOOTH_BAUD = 0x70800;
    
    private boolean verifyCommand = false;
    
    public NXTComm(String portname) throws IOException{
        CommPortIdentifier portIdentifier;
        try{
            portIdentifier = CommPortIdentifier.getPortIdentifier(portname);
        }catch(NoSuchPortException nspo){
            throw new IOException(portname + " is not a serial port");
        }
        
        if(portIdentifier.isCurrentlyOwned())
            throw new IOException("Port "+portname+" is currently in use");
        try{
            commPort = portIdentifier.open("Bluetooth " + portname, SERIAL_TIMEOUT);
        }catch(PortInUseException piuo){
            throw new IOException("Port "+portname+" is currently in use");
        }
        
        if(commPort instanceof SerialPort){
            SerialPort serialPort = (SerialPort)commPort;
            try{
                serialPort.setSerialPortParams(BLUETOOTH_BAUD, 8, 1, 0);
            }catch(UnsupportedCommOperationException ucoe){
                throw new IOException("Port "+portname+" could not be used");
            }
            
            serialPort.setRTS(true);
            serialPort.setDTR(true);
            in = serialPort.getInputStream();
            out = serialPort.getOutputStream();
        } else
            throw new IOException(portname + " is not a serial port");
    }
    
    
    public synchronized byte[] readData() throws IOException{
        int length = -1;
        
        while(length < 0)
            length = in.read();
        
        int MSB = in.read();
        length = 0xff & length | (0xff & MSB) << 8;
        
        byte[] message = new byte[length];
        in.read(message);
        //Log.logThis(this, "received: " + Log.printBytes(message));
        
        return message;
    }
    
    public synchronized void sendData(byte message[]) throws IOException {
        byte LSB = (byte)message.length;
        byte MSB = (byte)(message.length >>> 8);
        
        out.write(LSB);
        out.write(MSB);
        out.write(message);
        //Log.logThis(this, "sent: " + Log.printBytes(message));
    }
    
    
    public void setVerifyCommand(boolean b){
        verifyCommand = b;
    }
    
    public void setDirect(byte command, byte[] data) throws IOException{
        set(verifyCommand ? DIRECT_COMMAND_REPLY : DIRECT_COMMAND_NOREPLY, command, data);
    }
    public void setSystem(byte command, byte[] data) throws IOException{
        set(verifyCommand ? SYSTEM_COMMAND_REPLY : SYSTEM_COMMAND_NOREPLY, command, data);
    }
    private void set(byte type, byte command, byte[] data) throws IOException{
        byte[] request = new byte[data.length+2];
        request[0] = type;
        request[1] = command;
        System.arraycopy(data, 0, request, 2, data.length);
        
        sendData(request);
        
        if(verifyCommand){
            byte reply[] = readData();
            if(reply[2] != 0)
                throw new IOException(ErrorMessages.decodeError(reply[2]));
        }
    }
    public byte[] getDirect(byte command, byte[] data) throws IOException{
        return get(DIRECT_COMMAND_REPLY, command, data);
    }
    public byte[] getSystem(byte command, byte[] data) throws IOException{
        return get(SYSTEM_COMMAND_REPLY, command, data);
    }
    private byte[] get(byte type, byte command, byte[] data) throws IOException{
        byte[] request = new byte[data.length+2];
        request[0] = type;
        request[1] = command;
        
        System.arraycopy(data, 0, request, 2, data.length);
        
        sendData(request);
        
        byte[] reply = readData();
        if(reply.length < 3)
            throw new IOException("No response");
        if(reply[2] != 0)
            throw new IOException(ErrorMessages.decodeError(reply[2]));
        
        return reply;
    }
    
    public void close() throws IOException {
        in.close();
        out.close();
        commPort.close();
    }
}

