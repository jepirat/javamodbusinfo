package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.services.ModbusServiceSensor;
import web.settings.Settings;

import java.io.IOException;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    ModbusServiceSensor modbusServiceSensor;
    Settings settings;

    public SettingsController(Settings settings, ModbusServiceSensor modbusServiceSensor) {
        this.modbusServiceSensor = modbusServiceSensor;
        this.settings = settings;
    }

    @GetMapping
    public String getSettings(Model model) throws IOException, ClassNotFoundException {
        model.addAttribute("savedPort", settings.getProperty("port"));
        model.addAttribute("systemSerialPorts", modbusServiceSensor.getPorts());
        return "settings";
    }


    @PostMapping
    public String saveSettings(@RequestParam(name = "port") String port) throws IOException, ClassNotFoundException {
        settings.saveProperty("port", port);
        settings.readProperties();
        return "redirect:/modbusinfo";
    }
}
