import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass mainClassObj = new MainClass();

    @Test
    public void testGetLocalNumber() {
        int expected = 14;
        int actual = mainClassObj.getLocalNumber();
        Assert.assertTrue("Returned number is not 14", actual == expected);
    }

}
