package labs.edumore.d4j.engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.djl.Device;
import ai.djl.Model;
import ai.djl.engine.Engine;
import ai.djl.ndarray.NDManager;

/*
 * Copyright 2020 Gary Sharpe
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except
 * in compliance with the License. A copy of the License is located at in the "license" file 
 * accompanying this file. This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

/**
 * The {@code D4JEngine} is an implementation of the {@link Engine} based on the
 * <a href="https://deeplearning4j.org//">Deep Learning 4 Java Framework</a>.
 *
 * <p>
 * To get an instance of the {@code D4JEngine} when it is not the default Engine, call
 * {@link Engine#getEngine(String)} with the Engine name "D4J".
 */
public class D4JEngine extends Engine {

    private static final Logger logger = LoggerFactory.getLogger(D4JEngine.class);

    public static final String ENGINE_NAME = "D4J";

    /** Constructs a D4J Engine. */
    D4JEngine() {

    }

    /** {@inheritDoc} */
    @Override
    public String getEngineName() {

        return ENGINE_NAME;
    }

    /** {@inheritDoc} */
    @Override
    public String getVersion() {

        return "0.0.1";
    }

    /** {@inheritDoc} */
    @Override
    public Model newModel(Device device) {

        return new D4JModel(device);
    }

    /** {@inheritDoc} */
    @Override
    public NDManager newBaseManager() {

        return D4JNDManager.getSystemManager().newSubManager();
    }

    /** {@inheritDoc} */
    @Override
    public NDManager newBaseManager(Device device) {

        return D4JNDManager.getSystemManager().newSubManager(device);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(200);
        sb.append("Name: ")
          .append(getEngineName())
          .append(", version: ")
          .append(getVersion())
          .append(", capabilities: [\n");

        return sb.toString();
    }


    @Override
    public boolean hasCapability(String capability) {
        return false;
    }

    @Override
    public void setRandomSeed(int seed) {}

}
