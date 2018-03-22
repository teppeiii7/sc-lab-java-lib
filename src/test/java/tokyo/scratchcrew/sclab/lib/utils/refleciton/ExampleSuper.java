package tokyo.scratchcrew.sclab.lib.utils.refleciton;

import tokyo.scratchcrew.sclab.lib.utils.ReflectionUtils;

public class ExampleSuper<T> {
    
    public Class<T> getType() {
        return ReflectionUtils.getGenericType(getClass());
    }
    
}
