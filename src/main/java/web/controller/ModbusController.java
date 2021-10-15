package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModbusController {
    @GetMapping("/modbusinfo")
    public String getInfo() {
        return "modbusinfo";
    }


}
