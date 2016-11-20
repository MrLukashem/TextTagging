import com.studiesproject.utils.DateRegexFinder;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by mrlukashem on 20.11.16.
 */
public class RegexTests {
    private DateRegexFinder mFinder = new DateRegexFinder();

    @Test
    public void testDate1() {
        Assert.assertTrue(mFinder.match("2012:12:30"));
    }

    @Test
    public void testDate2() {
        Assert.assertTrue(mFinder.match("2012:10:31"));
    }

    @Test
    public void testDate3() {
        Assert.assertTrue(mFinder.match("2012:09:10"));
    }

    @Test
    public void testDate4() {
        Assert.assertTrue(mFinder.match("2022:9:02"));
    }

    @Test
    public void testDate5() {
        Assert.assertTrue(mFinder.match("2022122:9:10"));
    }

    @Test
    public void testDate6() {
        Assert.assertTrue(mFinder.match("0:1:11"));
    }

    @Test
    public void testDate7() {
        Assert.assertTrue(mFinder.match("1:1:1"));
    }

    @Test
    public void testDate8() {
        Assert.assertFalse(mFinder.match("300:232:02"));
    }

    @Test
    public void testDate9() {
        Assert.assertTrue(mFinder.match("300.12.02"));
    }

    @Test
    public void testDate10() {
        Assert.assertTrue(mFinder.match("300/12/02"));
    }

}
