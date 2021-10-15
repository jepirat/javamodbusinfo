package web.controller;

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

    @GetMapping
    public ResponseEntity<String []> getData() {
        return new ResponseEntity<String []>(modbusService.testPort(), HttpStatus.OK);
    }
}
