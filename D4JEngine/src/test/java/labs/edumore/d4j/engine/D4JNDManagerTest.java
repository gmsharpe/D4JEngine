package labs.edumore.d4j.engine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.buffer.IntBuffer;
import org.nd4j.linalg.factory.Nd4jBackend.NoAvailableBackendException;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;

public class D4JNDManagerTest {

    D4JNDManager mngr;
    
    @Before
    public void before() {
        mngr = D4JNDManager.getSystemManager();
    }
    
    @Test
    public void getManager() throws NoAvailableBackendException {
     
        D4JNDManager mngr = D4JNDManager.getSystemManager();
        mngr.create(false);
    
    }
    
    

    @Test
    public void createTest() throws NoAvailableBackendException {
        //Nd4j.ones(4,1);
       

            //
            //nd4j.initContext();
        IntBuffer buff = new org.nd4j.linalg.api.buffer.IntBuffer(new int[] {1,2,3,4});
        buff.length();
        
        // https://books.google.com/books?id=iIK9DwAAQBAJ&pg=PA33&lpg=PA33&dq=%22Loader.load(%22+dl4j&source=bl&ots=jRyMvRxc5h&sig=ACfU3U03rDYdYRtnMa-XfbOjJ5vwsBqU2g&hl=en&sa=X&ved=2ahUKEwjx9uy-84boAhXRvp4KHXTsDigQ6AEwAHoECAoQAQ#v=onepage&q=%22Loader.load(%22%20dl4j&f=false
        // Loader.load(); 
         
         // need to load a backend.  run an example from nd4j and you'll see the desired output
         // Nd4jBackend
         // https://deeplearning4j.org/docs/latest/deeplearning4j-config-performance-debugging
        
        NDArray arr = mngr.create(new int[] {1,2,3,4});
        
        System.out.println(arr);
    
    }
    
    @Test
    public void createRandomNormal() {
        NDArray arr = mngr.randomNormal(new Shape(4,1),DataType.FLOAT64, mngr.getDevice());
        System.out.println(arr);
       
    }
    
    @After
    public void after() {
        mngr.close();
    }
    
    
}
