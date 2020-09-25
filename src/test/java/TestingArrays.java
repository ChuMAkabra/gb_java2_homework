import Assignment_06.ArraysToTest;

import org.junit.*;

public class TestingArrays {
        private ArraysToTest arraysToTest;

    @Before
    public void init() {
        arraysToTest = new ArraysToTest();
    }

    @Test
    public void afterLast4_1 () {
        Assert.assertArrayEquals(new int[] {5,6}, arraysToTest.numbersAfterLastFour(new int[]{1, 2, 3, 4, 5, 6}));
    }

    @Test (expected = RuntimeException.class)
    public void testAfterLast4_exception () {
        Assert.assertNull(arraysToTest.numbersAfterLastFour(new int[]{1, 2, 3, 7, 5, 6}));

// in jupiter:
//        Assert.assertThrows(RuntimeException.class, ()-> arraysToTest.numbersAfterLastFour(new int[]{1, 2, 3, 7, 5, 6}));
    }

    @Test
    @Ignore("Disabled")
    public void testAfterLast4_3() {

// in jupiter:
//        Assert.assertDoesNotThrow(() -> arraysToTest.numbersAfterLastFour(new int[]{1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void testAfterLast4_4() {
        Assert.assertArrayEquals(new int[] {3, 5, 6}, arraysToTest.numbersAfterLastFour(new int[]{1, 2, 4, 3, 5, 6}));
    }

    @Test
    public void testAfterLast4_5() {
        Assert.assertArrayEquals(new int[] {3, 5, 6}, arraysToTest.numbersAfterLastFour(new int[]{4, 1, 4, 3, 5, 6}));
    }

    @Test
    public void test1And4Only_1() {
        Assert.assertTrue(arraysToTest.hasOnesAndFours(new int[] {1,1,1,4}));
    }

    @Test
    public void test1And4Only_2() {
        Assert.assertFalse(arraysToTest.hasOnesAndFours(new int[] {1,1,1,1}));
    }

    @Test
    public void test1And4Only_3() {
        Assert.assertFalse(arraysToTest.hasOnesAndFours(new int[] {1,1,4,3}));
    }

    @Test
    public void test1And4Only_4() {
        Assert.assertFalse(arraysToTest.hasOnesAndFours(new int[] {4,4,4,4}));
    }

    @After
    public void finish() {
        arraysToTest = null;
    }
}
