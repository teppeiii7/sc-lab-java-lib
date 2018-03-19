package tokyo.scratchcrew.sclab.utils.refleciton;

import tokyo.scratchcrew.sclab.utils.ReflectionUtils;

public class ExampleSuper<T> {
    
    public Class<T> getType() {
        return ReflectionUtils.getGenericType(getClass());
    }
    
}
