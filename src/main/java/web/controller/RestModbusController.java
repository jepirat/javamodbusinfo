package web.controller;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import web.services.ModbusServiceSensor;

@RestController
public class RestModbusController {
    ModbusServiceSensor modbusServiceSensor;

    public RestModbusController(ModbusServiceSensor modbusServiceSensor) {
        this.modbusServiceSensor = modbusServiceSensor;
    }

    @GetMapping("/ports")
    public ResponseEntity<String []> getData() {
        return new ResponseEntity<String []>(modbusServiceSensor.getPorts(), HttpStatus.OK);
    }

    @GetMapping("/data")
    public ResponseEntity<int[]> getTemperature() throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        return new ResponseEntity<int[]>(modbusServiceSensor.getData(), HttpStatus.OK);
    }
}
