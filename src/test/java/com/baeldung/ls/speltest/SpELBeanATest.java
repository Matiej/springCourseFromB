package com.baeldung.ls.speltest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SpELBeanATest {

    @Autowired
    private SpELBeanA spELBeanA;

    @Test
    public void whenSpELBeanA_thenAllResolvedCorrectly() {
        assertNotNull(spELBeanA);
        assertEquals(spELBeanA.getAdd(), 5);
        assertEquals(spELBeanA.getAddString(), "Learn Spring");
        assertTrue(spELBeanA.isEqual());
        assertEquals(spELBeanA.getTernary(), "a");
        assertEquals(spELBeanA.getOtherBeanProperty(), 111110);
    }

}