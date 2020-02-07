// Copyright 2020 Gary Sharpe
package labs.edumore.d4j.engine;

import java.lang.ref.Reference;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.djl.Device;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.util.PairList;


public class D4JNDManager implements NDManager {

    private static final Logger logger = LoggerFactory.getLogger(D4JNDManager.class);
    
    /**
     * A global {@link NDManager} singleton instance.
     *
     * <p>
     * This NDManager is the root of all the other {@code NDManager}s. NDArrays created by this
     * manager are un-managed, so the user has to close them manually. Those NDArrays will be
     * released on GC, and might be run into an out of native memory issue.
     */
    private static final D4JNDManager SYSTEM_MANAGER = new SystemManager();

    private static final NDArray[] EMPTY = new NDArray[0];

    private NDManager parent;
    private String uid;
    private Device device;
    private Map<String, Reference<AutoCloseable>> resources;
    private AtomicBoolean closed = new AtomicBoolean(false);

    private D4JNDManager(NDManager parent, Device device) {

        this.parent = parent;
        this.device = Device.defaultIfNull(device);
        resources = new ConcurrentHashMap<>();
        uid = UUID.randomUUID().toString();
    }
    
    public static D4JNDManager getSystemManager() {
        return SYSTEM_MANAGER;
    }

    public ByteBuffer allocateDirect(int capacity) {

        return null;
    }

    public NDArray create(Shape shape, DataType dataType, Device device) {

        return null;
    }

    public NDArray
        createCSR(Buffer data, long[] indptr, long[] indices, Shape shape, Device device) {

        return null;
    }

    public NDArray
        createRowSparse(Buffer data, Shape dataShape, long[] indices, Shape shape, Device device) {

        return null;
    }

    public NDArray zeros(Shape shape, DataType dataType, Device device) {

        return null;
    }

    public NDArray ones(Shape shape, DataType dataType, Device device) {

        return null;
    }

    public NDArray
        arange(Number start, Number stop, Number step, DataType dataType, Device device) {

        return null;
    }

    public NDArray eye(int rows, int cols, int k, DataType dataType, Device device) {

        return null;
    }

    public NDArray linspace(Number start, Number stop, int num, boolean endpoint, Device device) {

        return null;
    }

    public NDArray
        randomUniform(Number low, Number high, Shape shape, DataType dataType, Device device) {

        return null;
    }

    public NDArray
        randomNormal(Number loc, Number scale, Shape shape, DataType dataType, Device device) {

        return null;
    }

    public NDArray randomMultinomial(int n, NDArray pValues) {

        return null;
    }

    public NDArray randomMultinomial(int n, NDArray pValues, Shape shape) {

        return null;
    }

    public NDManager getParentManager() {

        return null;
    }

    public D4JNDManager newSubManager() {

        return null;
    }

    public D4JNDManager newSubManager(Device device) {

        return null;
    }

    public Device getDevice() {

        return null;
    }

    public void attach(String resourceId, AutoCloseable resource) {

    }

    public void detach(String resourceId) {

    }

    public void
        invoke(String operation, NDArray[] src, NDArray[] dest, PairList<String, ?> params) {

    }

    public NDList invoke(String operation, NDList src, PairList<String, ?> params) {

        return null;
    }

    public void close() {
        if (!closed.getAndSet(true)) {
            for (Reference<AutoCloseable> resource : resources.values()) {
                AutoCloseable closeable = resource.get();
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (Exception e) {
                        logger.error("Resource close failed.", e);
                    }
                }
            }
            parent.detach(uid);
            resources.clear();
        }
    }

    /** The SystemManager is the root {@link MxNDManager} of which all others are children. */
    private static final class SystemManager extends D4JNDManager {

        SystemManager() {

            super(null, Device.defaultDevice());
        }

        /** {@inheritDoc} */
        @Override
        public void attach(String resourceId, AutoCloseable resource) {}

        /** {@inheritDoc} */
        @Override
        public void detach(String resourceId) {}

        /** {@inheritDoc} */
        @Override
        public void close() {}
    }



}
