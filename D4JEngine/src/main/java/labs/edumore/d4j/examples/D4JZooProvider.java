package labs.edumore.d4j.examples;

import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooProvider;


/**
 * An D4J model zoo provider implements the {@link ai.djl.repository.zoo.ZooProvider} interface.
 */
public class D4JZooProvider implements ZooProvider {
    
    
    /** {@inheritDoc} */
    @Override
    public String getName() {
        return D4JModelZoo.NAME;
    }

    /** {@inheritDoc} */
    @Override
    public ModelZoo getModelZoo() {
        return new D4JModelZoo();
    }

}
