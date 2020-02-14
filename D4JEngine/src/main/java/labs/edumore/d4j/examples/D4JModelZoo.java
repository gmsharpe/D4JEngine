package labs.edumore.d4j.examples;

import java.util.List;

import ai.djl.repository.zoo.ModelLoader;
import ai.djl.repository.zoo.ModelNotFoundException;


public class D4JModelZoo implements ai.djl.repository.zoo.ModelZoo {
    
    public static final String NAME = "D4J";

    @Override
    public List<ModelLoader<?, ?>> getModelLoaders() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <I, O> ModelLoader<I, O> getModelLoader(String name) throws ModelNotFoundException {

        // TODO Auto-generated method stub
        return null;
    }

}
