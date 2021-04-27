package connection_classes;

import androidx.test.filters.SmallTest;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyClassTest extends TestCase {
    private MyClass myClass = null;

    @Before
    public void setUp() throws Exception{
        myClass =  MyClass.getInstance();
    }

    @After
    @Before
    public void tearDown() throws Exception{
        myClass =  MyClass.getInstance();
    }

    @SmallTest
    public void testGetSum() {
        Assert.assertEquals(myClass.getSum(1,1),2);
    }
}