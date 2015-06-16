package com.gembaboo.aptz.dto.test;

import com.gembaboo.aptz.domain.XY;
import org.junit.Test;

public class XYTest {


    @Test(expected = RuntimeException.class)
    public void Given_Constructor_With_Invalid_Uri_Parameter_Exception_Thrown(){
        new XY("SOMEWHATEVER");
    }

    @Test
    public void Given_Valid_Constructor_Object_Created(){
        new XY("geo:13782,12923");
    }

    @Test(expected = NumberFormatException.class)
    public void Given_Constructor_With_Invalid_Value_Number_Format_Exception_Thrown(){
        new XY("geo:233s,231");
    }
}