package properties;

import java.util.ResourceBundle;

public interface Properties {
    default String propReturn(String key){
        ResourceBundle bundle = ResourceBundle.getBundle("prop");
        return bundle.getString(key);
    }
}
