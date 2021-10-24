package web.services;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.*;
import jssc.SerialPortList;
import org.springframework.stereotype.Service;

@Service
public class ModbusServiceSensorImpl implements ModbusServiceSensor {
    int[] data;
    SerialParameters serialParameters = new SerialParameters();
    ModbusMaster modbusMaster;

    public ModbusServiceSensorImpl() throws SerialPortException {
        serialParameters.setDevice("/dev/ttyUSB0");
        serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_9600);
        serialParameters.setDataBits(8);
        serialParameters.setParity(SerialPort.Parity.NONE);
        serialParameters.setStopBits(1);
        SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        modbusMaster = ModbusMasterFactory.createModbusMasterRTU(serialParameters);
    }

    @Override
    public String[] getPorts() {
        return SerialPortList.getPortNames();
    }



    @Override
    public int[] getData() throws ModbusIOException, ModbusProtocolException, ModbusNumberException {
        try {
            modbusMaster.connect();
            data = modbusMaster.readInputRegisters(1, 1 , 2);
            modbusMaster.disconnect();
        } finally {
            modbusMaster.disconnect();
        }
        return data;
    }



}
