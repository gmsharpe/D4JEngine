// Copyright 2020 Gary Sharpe
package labs.edumore.d4j.engine;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.djl .Device;
import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Block;
import ai.djl.training.Trainer;
import ai.djl.training.TrainingConfig;
import ai.djl.translate.Translator;
import ai.djl.util.Pair;
import ai.djl.util.PairList;
import ai.djl.util.Utils;

public class D4JModel implements Model {

    private static final Logger logger = LoggerFactory.getLogger(D4JModel.class);

    private static final int MODEL_VERSION = 1;

    private Path modelDir;
    private String modelName;
    private D4JNDManager manager;
    private Block block;
    private DataType dataType;
    private Map<String, String> properties;
    private PairList<String, Shape> inputData;
    private Map<String, Object> artifacts = new ConcurrentHashMap<>();
    // the variable is used to avoid ParameterStore copy for the first time
    private AtomicBoolean first;

    /**
     * Constructs a new Model on a given device.
     *
     * @param device the device the model should be located on
     */
    public D4JModel(Device device) {
        device = Device.defaultIfNull(device);
        dataType = DataType.FLOAT32;
        properties = new ConcurrentHashMap<>();
        manager = D4JNDManager.getSystemManager().newSubManager(device);
        first = new AtomicBoolean(true);
    }


    /**
     * Loads the D4J model from a specified location.
     *
     * <p>D4J engine looks for {MODEL_NAME}-symbol.json and {MODEL_NAME}-{EPOCH}.params files in
     * the specified directory. By default, D4J engine will pick up the latest epoch of the
     * parameter file. However, users can explicitly specify an epoch to be loaded:
     *
     * <pre>
     * Map&lt;String, String&gt; options = new HashMap&lt;&gt;()
     * <b>options.put("epoch", "3");</b>
     * model.load(modelPath, "squeezenet", options);
     * </pre>
     *
     * @param modelPath the directory of the model
     * @param modelName the name/prefix of the model
     * @param options load model options, see documentation for the specific engine
     * @throws IOException Exception for file loading
     */
    public void load(Path modelPath,
                     String modelName,
                     Map<String, String> options) throws IOException, MalformedModelException {
        modelDir = modelPath.toAbsolutePath();
        this.modelName = modelName;
        if (block == null) {
            // load MxSymbolBlock
            Path symbolFile = modelDir.resolve(modelName + "-symbol.json");
            if (Files.notExists(symbolFile)) {
                throw new FileNotFoundException(
                        "Symbol file not found in: " + modelPath + ", please set block manually.");
            }
        
            // TODO some of below not available in current stable release of api
            //    Symbol symbol = Symbol.load(manager, symbolFile.toAbsolutePath().toString());
            // TODO: change default name "data" to model-specific one
        //    block = new D4JSymbolBlock(manager, symbol);
        }
       // loadParameters(modelName, options);
        // TODO: Check if Symbol has all names that params file have
        
    }

    public void save(Path modelPath, String modelName) throws IOException {
        if (Files.notExists(modelPath)) {
            Files.createDirectories(modelPath);
        }

        if (block == null || !block.isInitialized()) {
            throw new IllegalStateException("Model has not be trained or loaded yet.");
        }

        String epochValue = getProperty("Epoch");
        int epoch =
                epochValue == null
                        ? Utils.getCurrentEpoch(modelPath, modelName) + 1
                        : Integer.parseInt(epochValue);

        Path paramFile = modelPath.resolve(String.format("%s-%04d.params", modelName, epoch));
        try (DataOutputStream dos = new DataOutputStream(Files.newOutputStream(paramFile))) {
            dos.writeBytes("DJL@");
            dos.writeInt(MODEL_VERSION);
            dos.writeUTF(modelName);
            dos.writeUTF(dataType.name());
            inputData = block.describeInput();
            dos.writeInt(inputData.size());
            for (Pair<String, Shape> desc : inputData) {
                String name = desc.getKey();
                if (name == null) {
                    dos.writeUTF("");
                } else {
                    dos.writeUTF(name);
                }
                dos.write(desc.getValue().getEncoded());
            }

            dos.writeInt(properties.size());
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                dos.writeUTF(entry.getKey());
                dos.writeUTF(entry.getValue());
            }

            block.saveParameters(dos);
        }
        this.modelName = modelName;
        modelDir = modelPath.toAbsolutePath();
        
    }

    public Block getBlock() {

        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public String getName() {

        return modelName;
    }

    public String getProperty(String key) {

        return null;
    }

    public void setProperty(String key, String value) {

    }

    public NDManager getNDManager() {

        return null;
    }

    public Trainer newTrainer(TrainingConfig trainingConfig) {

        return null;
    }

    public <I, O> Predictor<I, O> newPredictor(Translator<I, O> translator) {

        return null;
    }

    public PairList<String, Shape> describeInput() {
        if (inputData == null) {
            inputData = block.describeInput();
        }
        return inputData;
    }

    public PairList<String, Shape> describeOutput() {
        List<String> names = inputData.keys();
        Shape[] outputShapes =
                block.getOutputShapes(
                        manager, inputData.values().toArray(new Shape[inputData.size()]));
        return new PairList<>(names, Arrays.asList(outputShapes));
    }

    public String[] getArtifactNames() {
        try {
            List<Path> files =
                    Files.walk(modelDir).filter(Files::isRegularFile).collect(Collectors.toList());
            List<String> ret = new ArrayList<>(files.size());
            for (Path path : files) {
                String fileName = path.toFile().getName();
                if (fileName.endsWith(".params") || fileName.endsWith("-symbol.json")) {
                    // ignore symbol and param files.
                    continue;
                }
                Path relative = modelDir.relativize(path);
                ret.add(relative.toString());
            }
            return ret.toArray(new String[0]);
        } catch (IOException e) {
            throw new AssertionError("Failed list files", e);
        }
    }

    public <T> T getArtifact(String name, Function<InputStream, T> function) throws IOException {
        try {
            Object artifact =
                    artifacts.computeIfAbsent(
                            name,
                            v -> {
                                try (InputStream is = getArtifactAsStream(name)) {
                                    return function.apply(is);
                                } catch (IOException e) {
                                    throw new IllegalStateException(e);
                                }
                            });
            return (T) artifact;
        } catch (RuntimeException e) {
            Throwable t = e.getCause();
            if (t instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw e;
        }
    }

    public URL getArtifact(String artifactName) throws IOException {
        if (artifactName == null) {
            throw new IllegalArgumentException("artifactName cannot be null");
        }
        Path file = modelDir.resolve(artifactName);
        if (Files.exists(file) && Files.isReadable(file)) {
            return file.toUri().toURL();
        }
        throw new FileNotFoundException("File not found: " + file);
    }

    public InputStream getArtifactAsStream(String name) throws IOException {
        URL url = getArtifact(name);
        return url.openStream();
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DataType getDataType() {

        return dataType;
    }

    public void cast(DataType dataType) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void close() {
        manager.close();
    }

}
