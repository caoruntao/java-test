package com.caort.editor.convertor;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @author Caort
 * @date 2021/1/7 20:07
 */
public class StringToPropertiesPropertyEditor extends PropertyEditorSupport {
    @Override
    public String getAsText() {
        Properties properties = (Properties)getValue();
        return properties.toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Properties properties = new Properties();

        try (StringReader reader = new StringReader(text)){
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setValue(properties);
    }
}
