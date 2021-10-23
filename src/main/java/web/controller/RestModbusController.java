package web.controller;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.services.ModbusService;

@RestController
public class RestModbusController {
    ModbusService modbusService;

    public RestModbusController(ModbusService modbusService) {
        this.modbusService = modbusService;
    }

    @GetMapping("/ports")
    public ResponseEntity<String []> getData() {
        return new ResponseEntity<String []>(modbusService.testPort(), HttpStatus.OK);
    }

    @GetMapping("/temperature")
    public ResponseEntity<String> getTemperature() throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        return new  ResponseEntity<String>(modbusService.getTemperature(), HttpStatus.OK);
    }

    @GetMapping("/humidity")
    public ResponseEntity<String> getHumidity() {
        return null;
    }
}
