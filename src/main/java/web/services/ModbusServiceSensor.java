package web.services;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;

public interface ModbusServiceSensor {
    String[] getPorts();
    int[] getData() throws ModbusIOException, ModbusProtocolException, ModbusNumberException;
}
