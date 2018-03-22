package tokyo.scratchcrew.sclab.lib.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tokyo.scratchcrew.sclab.lib.utils.refleciton.ExampleSub;
import tokyo.scratchcrew.sclab.lib.utils.refleciton.ExampleSuper;
import tokyo.scratchcrew.sclab.lib.utils.refleciton.ExampleType;

public class ReflectionUtilsTest {
    
    @Test
    public void test_getGenericType() {
        
        assertEquals(ExampleType.class, ReflectionUtils.getGenericType(ExampleSub.class));

        ExampleSub sub1 = new ExampleSub();
        ExampleSuper<?> super1 = new ExampleSub();
        
        assertEquals(ExampleType.class, sub1.getType());
        assertEquals(ExampleType.class, super1.getType());
    }

}
