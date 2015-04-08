package com.gembaboo.aptz.dto.test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Before;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.util.*;


public class DtoTest {

    // The package to test.
    public static final String POJO_PACKAGE = "com.gembaboo.aptz.dto";

    private PojoValidator pojoValidator;

    private List<PojoClass> pojoClasses;

    @Test
    public void testPackageClasses() {
        pojoClasses.forEach(pojoValidator::runValidation);
    }

    @Before
    public void setup() {
        setupPojoClasses();
        registerRandomGenerator(ZoneId.class, XMLGregorianCalendar.class);
        setupPojoValidator();
    }

    private void registerRandomGenerator(Class<?>... clazz) {
        RandomFactory.addRandomGenerator(new DtoClassesRandomGenerator(clazz));
    }

    private void setupPojoClasses() {
        FilterPackageInfo pojoClassFilter = new FilterPackageInfo() {
            @Override
            public boolean include(PojoClass pojoClass) {
                if (pojoClass.isEnum() || pojoClass.isAbstract()) {
                    return false;
                }
                return super.include(pojoClass);
            }
        };
        pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE, pojoClassFilter);
    }

    private void setupPojoValidator() {
        pojoValidator = new PojoValidator();

        // Create Rules to validate structure for POJO_PACKAGE
        //pojoValidator.addRule(new BusinessKeyMustExistRule());
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new NoFieldShadowingRule());
        pojoValidator.addRule(new NoNestedClassRule());
        pojoValidator.addRule(new NoPrimitivesRule());
        pojoValidator.addRule(new NoPublicFieldsExceptStaticFinalRule());
        pojoValidator.addRule(new NoStaticExceptFinalRule());
        pojoValidator.addRule(new SerializableMustHaveSerialVersionUIDRule());
        pojoValidator.addRule(new SetterMustExistRule());
        pojoValidator.addRule(new NoPublicFieldsRule());

        // Create Testers to validate behaviour for POJO_PACKAGE
        //pojoValidator.addTester(new BusinessIdentityTester());
        pojoValidator.addTester(new GetterTester());
        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new DefaultValuesNullTester());
    }

}
