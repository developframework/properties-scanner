import com.github.developframework.scanner.annotation.ScanProperties;
import lombok.Data;

/**
 * Created by Administrator on 2016/9/1.
 */
@Data
@ScanProperties(location = "test", prefix = "configuration_b")
public class ConfigurationB {

    private String param1;

    private int param2;
}
