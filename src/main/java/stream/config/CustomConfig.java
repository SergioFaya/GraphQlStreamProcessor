package stream.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomConfig {

    @Value("${custom.config.responseLimit}")
    public Integer responseLimit;

    @Value("${cusotm.config.tableName}")
    public String tableName;

    @Value("#{'${custom.config.tags}'.split(',')}")
    public List<String> processorTags;

}