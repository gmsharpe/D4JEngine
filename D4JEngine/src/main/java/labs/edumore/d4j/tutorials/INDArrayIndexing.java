package labs.edumore.d4j.tutorials;

import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

public class INDArrayIndexing {
    // remember Nd4j.randn(rows,cols) gaussian 
    // and Nd4j.rand(rows, cols) uniform
    // Nd4j.rand(int[]) for n dimensions
    // Nd4j.getRandom().setSeed(long)
    
    
    /**
     * https://deeplearning4j.org/docs/latest/nd4j-overview
     * 
     * Appendix E:  Deep Learning - A Practitioner's Approach
     */
    
    @Test
    public void simpleIndex() {
        INDArray arr = Nd4j.randomBinomial(10, .6, new long[] {4,4});
        
        // Questions return indexOf values above/below a certain number
        
        // return values above/below threshold
        
        
    }
 
    
    /*
     * getter and setter with ndarrayindexing - https://deeplearning4j.org/docs/latest/nd4j-overview#getsetsub
     * 
     * Sub-Arrays: get(), put() and NDArrayIndex
        Get:
        
        A more powerful and general method is to use INDArray.get(NDArrayIndex...). This functionality allows you to get an arbitrary sub-arrays based on certain indexes. This is perhaps best explained by some examples:
        To get a single row (and all columns), you can use:
        myArray.get(NDArrayIndex.point(rowIdx), NDArrayIndex.all())
        
        To get a range of rows (row a (inclusive) to row b (exclusive)) and all columns, you can use:
        myArray.get(NDArrayIndex.interval(a,b), NDArrayIndex.all())
        
        To get all rows and every second column, you can use:
        myArray.get(NDArrayIndex.all(),NDArrayIndex.interval(0,2,nCols))
        
        Though the above examples are for 2d arrays only, the NDArrayIndex approach extends to 3 or more dimensions. For 3 dimension, you would provide 3 INDArrayIndex objects instead of just two, as above.
        Note that the NDArrayIndex.interval(...), .all() and .point(int) methods always return views of the underlying arrays. Thus, changes to the arrays returned by .get() will be reflected in the original array.
        
        Put:
        The same NDArrayIndex approach is also used to put elements to another array: in this case you use the INDArray.put(INDArrayIndex[], INDArray toPut) method. Clearly, the size of the NDArray toPut must match the size implied by the provided indexes.
        
        Also note that myArray.put(NDArrayIndex[],INDArray other) is functionally equivalent to doing myArray.get(INDArrayIndex...).assign(INDArray other). Again, this is because .get(INDArrayIndex...) returns a view of the underlying array, not a copy.
     * 
     */
    
    
    /*
     * https://deeplearning4j.org/docs/latest/nd4j-overview
     * 
     * Some additional functionality that might be useful in certain circumstances is the NDIndexIterator class. The NDIndexIterator allows you to get the indexes in a defined order (specifially, the C-order traversal order: [0,0,0], [0,0,1], [0,0,2], …, [0,1,0], … etc for a rank 3 array).

        To iterate over the values in a 2d array, you can use:
        
        NdIndexIterator iter = new NdIndexIterator(nRows, nCols);
        while (iter.hasNext()) {
            int[] nextIndex = iter.next();
            double nextVal = myArray.getDouble(nextIndex);
            //do something with the value
        }
     * 
     */
    
    
    
    
    /*
     * 
     *  Index Accumulation Ops - https://deeplearning4j.org/docs/latest/nd4j-overview#indexaccumops
     *  
        Index accumulation ops are very similar to accumulation ops. The difference is that they return an integer index, instead of a double values.
        
        Examples of index accumulation ops are IMax (argmax), IMin (argmin) and IAMax (argmax of absolute values).
        
        To get the index of the maximum value in the array:
        
        int idx = Nd4j.getExecutioner().execAndReturn(new IAMax(myArray)).getFinalResult();
        
        Index accumulation ops are often most useful when executed along a dimension. For example, to get the index of the maximum value in each column (in each column = along dimension 0), you can use:
        
        INDArray idxOfMaxInEachColumn = Nd4j.getExecutioner().exec(new IAMax(myArray),0);
        
        Suppose this was executed on a 3x3 input array. Visually, this argmax/IAMax operation along dimension 0 operation looks like:
        
        Argmax / IAMax
        
        As with the accumulation op described above, the output has shape [1,3]. Again, had we instead done the operation along dimension 1, we would get a column vector with shape [3,1], with values (1,0,2).
     * 
     * 
     */
    
    
    
    /*
     * 
     * Boolean Indexing
     * https://github.com/deeplearning4j/nd4j/blob/master/nd4j-backends/nd4j-tests/src/test/java/org/nd4j/linalg/indexing/BooleanIndexingTest.java
     */


    
    // https://scipy-cookbook.readthedocs.io/items/Indexing.html
    
    
    /*
     * >>> primes = np.array([2,3,5,7,11,13,17,19,23])
        >>> idx = [3,4,1,2,2]
        >>> primes[idx]
     */
    
    @Test
    public void index() {
        INDArray arr = Nd4j.create(new float[] {2,3,5,7,11,13,17,19,23});
        // int[] and long[] provide shape but not data!!
        INDArray idx = Nd4j.create(new int[] {3,4,1,2,2});
        //System.out.println(arr.get(idx));
        // that won't work
        
        INDArrayIndex iNDIndx = NDArrayIndex.interval(0, 4);
        System.out.println(arr.get(iNDIndx));
        
        //System.out.println(arr.get(NDArrayIndex.indices(1,3,5)));
        
    }
    
    
}
