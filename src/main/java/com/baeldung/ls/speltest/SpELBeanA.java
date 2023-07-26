package com.baeldung.ls.speltest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpELBeanA {

    @Value("#{2+3}")
    private Integer add;
    @Value("#{'Learn ' + 'Spring'}")
    private String addString;
    @Value("#{2 == 2}")
    private boolean equal;
    @Value("#{3 > 2 ? 'a' : 'b'}")
    private String ternary;
    @Value("#{spELBeanB.prop1}")
    private Integer otherBeanProperty;

    public Integer getAdd() {
        return add;
    }

    public String getAddString() {
        return addString;
    }

    public boolean isEqual() {
        return equal;
    }

    public String getTernary() {
        return ternary;
    }

    public Integer getOtherBeanProperty() {
        return otherBeanProperty;
    }
}
