package labs.edumore.d4j.engine;

import org.junit.Test;

public class D4JNDManagerTest {

    @Test
    public void getManager() {
        D4JNDManager mngr = D4JNDManager.getSystemManager();
        mngr.create(false);
    
    }
    
}
