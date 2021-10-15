package web.services;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortFactoryJSSC;
import com.intelligt.modbus.jlibmodbus.serial.SerialUtils;
import jssc.SerialPortList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ModbusService {
    String[] dev;
    SerialParameters serialParameters = new SerialParameters();
    public String[] testPort() {
        try {
            // you can use just string to get connection with remote slave,
            // but you can also get a list of all serial ports available at your system.
            String[] dev_list = SerialPortList.getPortNames();
            dev = dev_list;
            // if there is at least one serial port at your system
            if (dev_list.length > 0) {
                // you can choose the one of those you need
                serialParameters.setDevice(dev_list[1]);
                // these parameters are set by default
                serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_9600);
                serialParameters.setDataBits(8);
                serialParameters.setParity(SerialPort.Parity.NONE);
                serialParameters.setStopBits(1);
                //you can choose the library to use.
                //the library uses jssc by default.
                //
                //first, you should set the factory that will be used by library to create an instance of SerialPort.
                //SerialUtils.setSerialPortFactory(new SerialPortFactoryRXTX());
                //  JSSC is Java Simple Serial Connector
                SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());

                //  PJC is PureJavaComm.
                //SerialUtils.setSerialPortFactory(new SerialPortFactoryPJC());
                //  JavaComm is the Java Communications API (also known as javax.comm)
                //SerialUtils.setSerialPortFactory(new SerialPortFactoryJavaComm());
                //in case of using serial-to-wifi adapter
                //String ip = "192.168.0.180";//for instance
                //int port  = 777;
                //SerialUtils.setSerialPortFactory(new SerialPortFactoryTcp(new TcpParameters(InetAddress.getByName(ip), port, true)));
                // you should use another method:
                //next you just create your master and use it.
                ModbusMaster modbusMaster = ModbusMasterFactory.createModbusMasterRTU(serialParameters);
                modbusMaster.connect();

                int slaveId = 2;
                int offset = 0;
                int quantity = 1;
                //you can invoke #connect method manually, otherwise it'll be invoked automatically
                try {
                  boolean[] coils = new boolean[]{true};
                    modbusMaster.writeSingleCoil(2, 0, true);
                    System.out.println(modbusMaster.readCoils(2, 0, 1));

                    // at next string we receive ten registers from a slave with id of 1 at offset of 0.
                   // int[] registerValues = modbusMaster.readHoldingRegisters(slaveId, offset, quantity);
                    // print values
                   // for (int value : registerValues) {
                    //    System.out.println("Address: " + offset++ + ", Value: " + value);
                   // }
                    modbusMaster.disconnect();
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        modbusMaster.disconnect();
                    } catch (ModbusIOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dev;
    }
}
