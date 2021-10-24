package web.settings;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
@Component
public class Settings {
    Map<String, String> properties;
    public void saveProperty(String key, String value) throws IOException {
        File file = new File("property");
        if(!file.exists()) {
            file.createNewFile();
        }

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            Map<String, String> properties = new HashMap();
            properties.clear();
            properties.put(key, value);
            oos.writeObject(properties);
        }
    }

    public void readProperties() throws IOException, ClassNotFoundException {
        File file = new File("property");
        if(!file.exists()) {
            file.createNewFile();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.properties = (HashMap) objectInputStream.readObject();
        } catch (EOFException e) {
            System.out.println(e.getStackTrace() + "файл прочитан");
        }

    }

    public String getProperty(String key) throws IOException, ClassNotFoundException {
        readProperties();
        String port = "Программа еще не настроена, выберите порт, затем сохраните и перезагрузите программу";
        try {
            port = properties.get(key);
        }

        catch(NullPointerException e) {
            System.out.println("Программа еще не настроена, выберите порт, затем сохраните и перезагрузите программу");
        }
        return port;
    }
}
