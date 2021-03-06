// Copyright 2020 Gary Sharpe
package labs.edumore.d4j.engine;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import org.bytedeco.javacpp.Loader;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.djl.Device;
import ai.djl.engine.Engine;
import ai.djl.ndarray.BaseNDManager;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.util.PairList;

public class D4JNDManager extends BaseNDManager {

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

    private D4JNDManager(NDManager parent, Device device) {
        
        super(parent, device);
        
        // initializes underlying context.  Loads the backend (cpu/gpu)
        Nd4j nd4j = new Nd4j();
        
    }

    public static D4JNDManager getSystemManager() {

        return SYSTEM_MANAGER;
    }

    /** {@inheritDoc} */
    @Override
    public ByteBuffer allocateDirect(int capacity) {

        return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
    }

    /** {@inheritDoc} */
    @Override
    public NDArray create(Shape shape, DataType dataType, Device device) {

        // TODO shape?
        return new D4JNDArray(this,
                              Nd4j.empty(org.nd4j.linalg.api.buffer.DataType.fromNumpy(dataType.name())),
                              shape,
                              device);

    }

    /** {@inheritDoc} */
    @Override
    public D4JNDArray create(Buffer data, Shape shape, DataType dataType) {

        int size = data.remaining();
        // int8, uint8, boolean use ByteBuffer, so need to explicitly input DataType
        DataType inputType = DataType.fromBuffer(data);

        int numOfBytes = inputType.getNumOfBytes();
        ByteBuffer buf = allocateDirect(size * numOfBytes);

        org.nd4j.linalg.api.buffer.DataBuffer nd4jDataBuffer = null;

        switch (inputType) {
            case FLOAT32 :
                buf.asFloatBuffer().put((FloatBuffer) data);
                // nd4jDataBuffer =
                nd4jDataBuffer = new org.nd4j.linalg.api.buffer.FloatBuffer(buf, size * numOfBytes);
                break;
            case FLOAT64 :
                buf.asDoubleBuffer().put((DoubleBuffer) data);
                nd4jDataBuffer = new org.nd4j.linalg.api.buffer.DoubleBuffer(buf, size * numOfBytes);
                break;
            case UINT8 :
            case INT8 :
            case BOOLEAN :
                //buf.put((ByteBuffer) data);
                break;
            case INT32 :
                buf.asIntBuffer().put((IntBuffer) data);
                nd4jDataBuffer = new org.nd4j.linalg.api.buffer.IntBuffer(buf, size * numOfBytes);
                break;
            case INT64 :
                buf.asLongBuffer().put((LongBuffer) data);
                nd4jDataBuffer = new org.nd4j.linalg.api.buffer.LongBuffer(buf, size * numOfBytes);
                break;
            case FLOAT16 :
            default :
                throw new AssertionError("Show never happen");
        }

        // TODO throw exception
        return new D4JNDArray(this, Nd4j.create(nd4jDataBuffer), shape, device);
        // , shape, dataType, SparseFormat.DENSE, device);
    }

    /** {@inheritDoc} */
    @Override
    public NDArray
        createCSR(Buffer data, long[] indptr, long[] indices, Shape shape, Device device) {

        /*
         * https://deeplearning4j.org/api/latest/org/nd4j/linalg/api/ndarray/BaseSparseNDArrayCSR.
         * html#pointerB
         * 
         * Constructor Summary:
         * 
         * BaseSparseNDArrayCSR(DataBuffer data, int[] columnsPointers, int[] pointerB, int[]
         * pointerE, long[] shape)
         * 
         * BaseSparseNDArrayCSR(double[] data, int[] columnsPointers, int[] pointerB, int[]
         * pointerE, long[] shape)
         * 
         * The length of the values and columns arrays is equal to the number of non-zero elements
         * in A.
         * 
         * BaseSparseNDArrayCSR(float[] data, int[] columnsPointers, int[] pointerB, int[] pointerE,
         * long[] shape)
         * 
         */
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public NDArray
        createRowSparse(Buffer data, Shape dataShape, long[] indices, Shape shape, Device device) {

        return null;
    }

    /** {@inheritDoc} */
    @Override
    public NDArray zeros(Shape shape, DataType dataType, Device device) {

        return new D4JNDArray(this,
                              Nd4j.zeros(org.nd4j.linalg.api.buffer.DataType.fromNumpy(dataType.name()),
                                         shape.getShape()),
                              shape,
                              device);
    }

    /** {@inheritDoc} */
    @Override
    public NDArray ones(Shape shape, DataType dataType, Device device) {

        return new D4JNDArray(this,
                              Nd4j.ones(org.nd4j.linalg.api.buffer.DataType.fromNumpy(dataType.name()),
                                        shape.getShape()),
                              shape,
                              device);
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

        return new D4JNDArray(this,
                              Nd4j.random()
                                  .uniform(low.doubleValue(),
                                           high.doubleValue(),
                                           org.nd4j.linalg.api.buffer.DataType.fromNumpy(dataType.name()),
                                           shape.getShape()),
                              shape,
                              device);
    }

    public NDArray
        randomNormal(Number loc, Number scale, Shape shape, DataType dataType, Device device) {

        return new D4JNDArray(this,
                              Nd4j.random()
                                  .normal(loc.doubleValue(),
                                          scale.doubleValue(),
                                          org.nd4j.linalg.api.buffer.DataType.fromNumpy(dataType.name()),
                                          shape.getShape()),
                              shape,
                              device);
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

        return newSubManager(device);
    }

    public D4JNDManager newSubManager(Device device) {

        D4JNDManager manager = new D4JNDManager(this, device);
        attach(manager.uid, manager);
        return manager;
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

        throw new UnsupportedOperationException("Not implemented");
    }

    public NDList invoke(String operation, NDList src, PairList<String, ?> params) {

        throw new UnsupportedOperationException("Not implemented");
    }

    /** The SystemManager is the root {@link D4JNDManager} of which all others are children. */
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

    @Override
    public boolean isOpen() {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Engine getEngine() {

        return Engine.getEngine(D4JEngine.ENGINE_NAME);
    }

}
