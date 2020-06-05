package labs.edumore.d4j.engine;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.Pointer;

import ai.djl.Device;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.index.NDIndex;
import ai.djl.ndarray.internal.NDArrayEx;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.ndarray.types.SparseFormat;

public class D4JNDArray implements NDArray, AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(D4JNDArray.class);

    private String name;
    private Device device;
    private DataType dataType;
    private Shape shape;
    private SparseFormat sparseFormat;
    private D4JNDManager manager;

    private Exception exception;

    private INDArray iNDArray;

    public INDArray iNDArray() {
        return iNDArray;
    }
    
    protected final AtomicReference<INDArray> handle;

    public D4JNDArray(D4JNDManager systemManager, INDArray iNDArray, Shape shape, Device device) {

        this.handle = new AtomicReference<INDArray>(iNDArray);
        this.iNDArray = iNDArray;
        this.manager = systemManager;
        this.device = device;
        this.shape = shape;
        if (logger.isTraceEnabled()) {
            exception = new Exception();
        }
    }

    public D4JNDArray(D4JNDManager systemManager, INDArray iNDArray, Device device) {

        this.handle = new AtomicReference<INDArray>(iNDArray);
        this.iNDArray = iNDArray;
        this.manager = systemManager;
        this.device = device;
        this.shape = extractShape(iNDArray);
        if (logger.isTraceEnabled()) {
            exception = new Exception();
        }
    }

    public static Shape extractShape(INDArray iNDArray) {

        return new Shape(iNDArray.shape());

    }

    @Override
    public NDManager getManager() {

        return manager;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String getUid() {

        return null;
    }

    @Override
    public DataType getDataType() {

        return dataType;
    }

    @Override
    public Device getDevice() {

        return device;
    }

    @Override
    public Shape getShape() {

        return shape;
    }

    @Override
    public SparseFormat getSparseFormat() {

        return null;
    }

    @Override
    public NDArray toDevice(Device device, boolean copy) {

        return null;
    }

    @Override
    public NDArray toType(DataType dataType, boolean copy) {

        return null;
    }

    @Override
    public void attachGradient() {

    }

    @Override
    public NDArray getGradient() {

        return null;
    }

    @Override
    public ByteBuffer toByteBuffer() {

        return null;
    }

    @Override
    public void set(Buffer data) {

        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void set(NDIndex index, NDArray value) {

        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void set(NDIndex index, Number value) {

        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setScalar(NDIndex index, Number value) {

        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public NDArray get(NDIndex index) {
        
        // TODO convert NDIndex to INDArrayIndex
        index.getIndices();
        INDArrayIndex idx;
      //  return  new D4JNDArray(manager,iNDArray.get(idx),device);
        return null;
    }

    @Override
    public void copyTo(NDArray array) {

    }

    @Override
    public NDArray booleanMask(NDArray index, int axis) {

        return null;
    }

    @Override
    public NDArray zerosLike() {

        return null;
    }

    @Override
    public NDArray onesLike() {

        return null;
    }

    @Override
    public boolean contentEquals(Number number) {

        return false;
    }

    @Override
    public boolean contentEquals(NDArray other) {

        return false;
    }

    @Override
    public NDArray eq(Number other) {

        return new D4JNDArray(manager, iNDArray.eq(other), device);
    }

    @Override
    public NDArray eq(NDArray other) {

        return new D4JNDArray(manager, iNDArray.eq(((D4JNDArray) other).iNDArray), device);
    }

    @Override
    public NDArray neq(Number other) {

        return new D4JNDArray(manager, iNDArray.neq(other), device);
    }

    @Override
    public NDArray neq(NDArray other) {

        return new D4JNDArray(manager, iNDArray.neq(((D4JNDArray) other).iNDArray), device);

    }

    @Override
    public NDArray gt(Number other) {

        return new D4JNDArray(manager, iNDArray.gt(other), device);

    }

    @Override
    public NDArray gt(NDArray other) {

        return new D4JNDArray(manager, iNDArray.gt(((D4JNDArray) other).iNDArray), device);

    }

    @Override
    public NDArray gte(Number other) {

        return new D4JNDArray(manager, iNDArray.gte(other), device);
    }

    @Override
    public NDArray gte(NDArray other) {

      return null;
    }

    @Override
    public NDArray lt(Number other) {

        return null;
    }

    @Override
    public NDArray lt(NDArray other) {

        return null;
    }

    @Override
    public NDArray lte(Number other) {

        return null;
    }

    @Override
    public NDArray lte(NDArray other) {

        return null;
    }

    @Override
    public NDArray add(Number n) {

        return null;
    }

    @Override
    public NDArray add(NDArray other) {

        return null;
    }

    @Override
    public NDArray sub(Number n) {

        return null;
    }

    @Override
    public NDArray sub(NDArray other) {

        return null;
    }

    @Override
    public NDArray mul(Number n) {

        return null;
    }

    @Override
    public NDArray mul(NDArray other) {

        return null;
    }

    @Override
    public NDArray div(Number n) {

        return null;
    }

    @Override
    public NDArray div(NDArray other) {

        return null;
    }

    @Override
    public NDArray mod(Number n) {

        return null;
    }

    @Override
    public NDArray mod(NDArray other) {

        return null;
    }

    @Override
    public NDArray pow(Number n) {

        return null;
    }

    @Override
    public NDArray pow(NDArray other) {

        return null;
    }

    @Override
    public NDArray addi(Number n) {

        return null;
    }

    @Override
    public NDArray addi(NDArray other) {

        return null;
    }

    @Override
    public NDArray subi(Number n) {

        return null;
    }

    @Override
    public NDArray subi(NDArray other) {

        return null;
    }

    @Override
    public NDArray muli(Number n) {

        return null;
    }

    @Override
    public NDArray muli(NDArray others) {

        return null;
    }

    @Override
    public NDArray divi(Number n) {

        return null;
    }

    @Override
    public NDArray divi(NDArray other) {

        return null;
    }

    @Override
    public NDArray modi(Number n) {

        return null;
    }

    @Override
    public NDArray modi(NDArray other) {

        return null;
    }

    @Override
    public NDArray powi(Number n) {

        return null;
    }

    @Override
    public NDArray powi(NDArray other) {

        return null;
    }

    @Override
    public NDArray maximum(Number n) {

        return null;
    }

    @Override
    public NDArray maximum(NDArray other) {

        return null;
    }

    @Override
    public NDArray minimum(Number n) {

        return null;
    }

    @Override
    public NDArray minimum(NDArray other) {

        return null;
    }

    @Override
    public NDArray neg() {

        return null;
    }

    @Override
    public NDArray negi() {

        return null;
    }

    @Override
    public NDArray abs() {

        return null;
    }

    @Override
    public NDArray square() {

        return null;
    }

    @Override
    public NDArray cbrt() {

        return null;
    }

    @Override
    public NDArray floor() {

        return null;
    }

    @Override
    public NDArray ceil() {

        return null;
    }

    @Override
    public NDArray round() {

        return null;
    }

    @Override
    public NDArray trunc() {

        return null;
    }

    @Override
    public NDArray exp() {

        return null;
    }

    @Override
    public NDArray log() {

        return null;
    }

    @Override
    public NDArray log10() {

        return null;
    }

    @Override
    public NDArray log2() {

        return null;
    }

    @Override
    public NDArray sin() {

        return null;
    }

    @Override
    public NDArray cos() {

        return null;
    }

    @Override
    public NDArray tan() {

        return null;
    }

    @Override
    public NDArray asin() {

        return null;
    }

    @Override
    public NDArray acos() {

        return null;
    }

    @Override
    public NDArray atan() {

        return null;
    }

    @Override
    public NDArray sinh() {

        return null;
    }

    @Override
    public NDArray cosh() {

        return null;
    }

    @Override
    public NDArray tanh() {

        return null;
    }

    @Override
    public NDArray asinh() {

        return null;
    }

    @Override
    public NDArray acosh() {

        return null;
    }

    @Override
    public NDArray atanh() {

        return null;
    }

    @Override
    public NDArray toDegrees() {

        return null;
    }

    @Override
    public NDArray toRadians() {

        return null;
    }

    @Override
    public NDArray max() {

        return null;
    }

    @Override
    public NDArray max(int[] axes, boolean keepDims) {

        return null;
    }

    @Override
    public NDArray min() {

        return null;
    }

    @Override
    public NDArray min(int[] axes, boolean keepDims) {

        return null;
    }

    @Override
    public NDArray sum() {

        return null;
    }

    @Override
    public NDArray sum(int[] axes, boolean keepDims) {

        return null;
    }

    @Override
    public NDArray prod() {

        return null;
    }

    @Override
    public NDArray prod(int[] axes, boolean keepDims) {

        return null;
    }

    @Override
    public NDArray mean() {

        return new D4JNDArray(manager, iNDArray.mean(0), device);
    }

    @Override
    public NDArray mean(int[] axes, boolean keepDims) {

        return null;
    }

    @Override
    public NDArray trace(int offset, int axis1, int axis2) {

        return null;
    }

    @Override
    public NDArray flatten() {

        return null;
    }

    @Override
    public NDArray reshape(Shape shape) {

        return null;
    }

    @Override
    public NDArray expandDims(int axis) {

        return null;
    }

    @Override
    public NDArray squeeze(int[] axes) {

        return null;
    }

    @Override
    public NDArray logicalAnd(NDArray other) {

        return null;
    }

    @Override
    public NDArray logicalOr(NDArray other) {

        return null;
    }

    @Override
    public NDArray logicalXor(NDArray other) {

        return null;
    }

    @Override
    public NDArray logicalNot() {

        return null;
    }

    @Override
    public NDArray argSort(int axis, boolean ascending) {

        return null;
    }

    @Override
    public NDArray sort() {

        return null;
    }

    @Override
    public NDArray sort(int axis) {

        return null;
    }



    @Override
    public NDArray cumSum() {

        return null;
    }

    @Override
    public NDArray cumSum(int axis) {

        return null;
    }

    @Override
    public NDArray isInfinite() {

        return null;
    }

    @Override
    public NDArray isNaN() {

        return null;
    }

    @Override
    public NDArray createMask(NDIndex index) {

        return null;
    }

    @Override
    public NDArray createMask(Predicate<Number> predicate) {

        return null;
    }

    @Override
    public NDArray tile(long repeats) {

        return null;
    }

    @Override
    public NDArray tile(int axis, long repeats) {

        return null;
    }

    @Override
    public NDArray tile(long[] repeats) {

        return null;
    }

    @Override
    public NDArray tile(Shape desiredShape) {

        return null;
    }

    @Override
    public NDArray repeat(long repeats) {

        return null;
    }

    @Override
    public NDArray repeat(int axis, long repeats) {

        return null;
    }

    @Override
    public NDArray repeat(long[] repeats) {

        return null;
    }

    @Override
    public NDArray repeat(Shape desiredShape) {

        return null;
    }

    @Override
    public NDArray dot(NDArray other) {

        return null;
    }

    @Override
    public NDArray clip(Number min, Number max) {

        return null;
    }

    @Override
    public NDArray transpose() {

        return null;
    }

    @Override
    public NDArray transpose(int... axes) {

        return null;
    }

    @Override
    public NDArray broadcast(Shape shape) {

        return null;
    }

    
    @Override
    public NDArray logSoftmax(int[] arg0, float arg1) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NDArray softmax(int[] arg0, float arg1) {

        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public NDArray argMax() {

        return null;
    }

    @Override
    public NDArray argMax(int axis) {

        return null;
    }

    @Override
    public NDArray argMin() {

        return null;
    }

    @Override
    public NDArray argMin(int axis) {

        return null;
    }

    @Override
    public NDArray percentile(Number percentile) {

        return null;
    }

    @Override
    public NDArray percentile(Number percentile, int[] axes) {

        return null;
    }

    @Override
    public NDArray median() {

        return null;
    }

    @Override
    public NDArray median(int[] axes) {

        return null;
    }

    @Override
    public NDArray toDense() {

        return null;
    }

    @Override
    public NDArray toSparse(SparseFormat fmt) {

        return null;
    }

    @Override
    public NDArray nonzero() {

        return null;
    }

    @Override
    public NDArrayEx getNDArrayInternal() {

        return null;
    }

    @Override
    public void close() {

        if (handle.get() != null) {
            handle.get().close();
            handle.getAndSet(null);
            manager.detach(getUid());
            manager = null;
        }
    }

    @Override
    public NDList split(long[] indices, int axis) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NDArray reshapeLike(NDArray array) {

        return null;
    }

    /*
     * 'AtomicReference', 'AutoCloseable' & below inspired by ai.djl.pytorch.jni.NativeResource
     */

    /** {@inheritDoc} */
    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {

        logger.warn("Resource ({}) was not closed explicitly: {}",
                    getUid(),
                    getClass().getSimpleName());

        close();
        super.finalize();
    }

    /**
     * Gets the boolean that indicates whether this resource has been released.
     *
     * @return whether this resource has been released
     */
    public boolean isReleased() {

        return handle.get() == null;
    }

    /**
     * Gets the {@link Pointer} to this resource.
     *
     * @return the {@link Pointer} to this resource
     */
    protected AtomicReference<INDArray> getHandle() {

        if (handle.get() == null) {
            throw new IllegalStateException("Native resource has been release already.");
        }
        return handle;
    }

    
    @Override
    public String toString() {
        return iNDArray.toString();
    }


    
}
