import com.github.developframework.scanner.PropertiesBox;
import com.github.developframework.scanner.PropertiesScanner;

public class Main {

    public static void main(String[] args) {
        PropertiesScanner propertiesScanner = new PropertiesScanner();
        PropertiesBox box = propertiesScanner.scan(ConfigurationA.class, ConfigurationB.class);

        ConfigurationA configuration_a = box.get(ConfigurationA.class);
        ConfigurationB configuration_b = box.get(ConfigurationB.class);

        System.out.println(configuration_a.toString());
        System.out.println(configuration_b.toString());
    }
}
