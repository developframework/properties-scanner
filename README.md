### Properties-scanner

可以方便的读取配置文件，把配置信息读取到实体类。

#### 注解解释
`@ScanProperties`	加在类上，用属性`location`标明读取的properties文件名，用属性`prefix`设定前缀。

`@ScanProperty`	可选注解，标注在字段上，可以用`alias`设定别名，`ifMissingOfValue`指定当配置不存在时的默认值。

范例：

test.properties

```properties
#configuration_a
configuration_a.param1=xxxxx
configuration_a.param2=

#configuration_b
configuration_b.param1=yyyyy
configuration_b.param2=200
```
ConfigurationA.java
```java
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
```
ConfigurationB.java
```java
import com.github.developframework.scanner.annotation.ScanProperties;
import lombok.Data;

@Data
@ScanProperties(location = "test", prefix = "configuration_b")
public class ConfigurationB {

    private String param1;

    private int param2;
}
```
Main.java
```java
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
```