package labs.edumore.d4j.engine;

import ai.djl.engine.Engine;
import ai.djl.engine.EngineProvider;

/** {@code D4JEngineProvider} is the Deep Learning 4 Java implementation of {@link EngineProvider}. */
public class D4JEngineProvider implements EngineProvider {

    private static final Engine ENGINE = new D4JEngine();

    /** {@inheritDoc} */
    @Override
    public Engine getEngine() {
        return ENGINE;
    }
    
}
