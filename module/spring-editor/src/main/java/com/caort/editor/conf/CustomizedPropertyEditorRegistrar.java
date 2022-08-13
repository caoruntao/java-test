package com.caort.editor.conf;

import com.caort.editor.convertor.StringToPropertiesPropertyEditor;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.util.Properties;

/**
 * @author Caort
 * @date 2021/1/7 20:26
 */
public class CustomizedPropertyEditorRegistrar implements PropertyEditorRegistrar {
    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        /*registry.registerCustomEditor(Properties.class, "context",new StringToPropertiesPropertyEditor());*/
        registry.registerCustomEditor(Properties.class, new StringToPropertiesPropertyEditor());
    }
}
