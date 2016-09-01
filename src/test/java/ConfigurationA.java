import com.github.developframework.scanner.annotation.ScanProperties;
import com.github.developframework.scanner.annotation.ScanProperty;
import lombok.Data;

@Data
@ScanProperties(location = "test", prefix = "configuration_a")
public class ConfigurationA {

    private String param1;

    @ScanProperty(alias = "param2", ifMissingOfValue = "999")
    private int param2;
}
