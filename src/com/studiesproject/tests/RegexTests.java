import com.studiesproject.utils.CustomDateRegexFinder;
import com.studiesproject.utils.DateRegexFinder;
import com.studiesproject.utils.PhoneNumberRegexFinder;
import com.studiesproject.utils.URLRegexFinder;
import org.junit.*;

/**
 * Created by mrlukashem on 20.11.16.
 */
public class RegexTests {
    private DateRegexFinder mFinder = new DateRegexFinder();

    private PhoneNumberRegexFinder mPhoneFinder = new PhoneNumberRegexFinder();

    private URLRegexFinder mUrlFinder = new URLRegexFinder();

    private CustomDateRegexFinder mCDateFinder = new CustomDateRegexFinder();

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

    @Test
    public void testDate11() {
        Assert.assertTrue(mFinder.match("300.I.02"));
    }

    @Test
    public void testDate12() {
        Assert.assertTrue(mFinder.match("3000.XI.31"));
    }

    // Phone numbers regex tests
    @Test
    public void testPhoneNumber1() {
        Assert.assertTrue(mPhoneFinder.match("68 3276773"));
    }

    @Test
    public void testPhoneNumber2() {
        Assert.assertTrue(mPhoneFinder.match("22 9076773"));
    }

    @Test
    public void testPhoneNumber3() {
        Assert.assertTrue(mPhoneFinder.match("+48 68 3276773"));
    }

    @Test
    public void testPhoneNumber4() {
        Assert.assertTrue(mPhoneFinder.match("+48 722392303"));
    }

    @Test
    public void testPhoneNumber5() {
        Assert.assertTrue(mPhoneFinder.match("+48722392303"));
    }

    @Test
    public void testPhoneNumber6() {
        Assert.assertTrue(mPhoneFinder.match("+48683276773"));
    }

    @Test
    public void testPhoneNumber7() {
        Assert.assertTrue(mPhoneFinder.match("+48 68 3276773"));
    }

    @Test
    public void testPhoneNumber8() {
        Assert.assertTrue(mPhoneFinder.match("+48 68 327 67 73"));
    }

    @Test
    public void testPhoneNumber9() {
        Assert.assertTrue(mPhoneFinder.match("68 327 67 73"));
    }

    @Test
    public void testPhoneNumber10() {
        Assert.assertTrue(mPhoneFinder.match("72 239 23 03"));
    }

    @Test
    public void testPhoneNumber11() {
        Assert.assertTrue(mPhoneFinder.match("722392303"));
    }


    // URLRegexFinder tests
    @Test
    public void testUrl1() {
        Assert.assertTrue(mUrlFinder.match("www.google.com"));
    }

    @Test
    public void testUrl2() {
        Assert.assertTrue(mUrlFinder.match("www.google.pl"));
    }

    @Test
    public void testUrl3() {
        Assert.assertTrue(mUrlFinder.match("http://www.google.en"));
    }

    @Test
    public void testUrl4() {
        Assert.assertTrue(mUrlFinder.match("https://www.testdlugiegolinkaurl_test_test_tes.com"));
    }

    @Test
    public void testUrl5() {
        Assert.assertTrue(mUrlFinder.match("www.mykomputer.com"));
    }

    @Test
    public void testUrl6() {
        Assert.assertFalse(mUrlFinder.match("www.google"));
    }

    @Test
    public void testUrl7() {
        Assert.assertFalse(mUrlFinder.match("htttp://www.google.com"));
    }

    @Test
    public void testUrl8() {
        Assert.assertFalse(mUrlFinder.match("http://www.hello.p"));
    }


    // CustomDateRegexFinder tests.
    @Test
    public void testCDate1() {
        Assert.assertTrue(mCDateFinder.match("20 stycznia"));
    }

    @Test
    public void testCDate2() {
        Assert.assertTrue(mCDateFinder.match("31 styczeń"));
    }

    @Test
    public void testCDate3() {
        Assert.assertTrue(mCDateFinder.match("01 Stycznia"));
    }

    @Test
    public void testCDate4() {
        Assert.assertTrue(mCDateFinder.match("4 stycznia"));
    }

    @Test
    public void testCDate5() {
        Assert.assertTrue(mCDateFinder.match("stycznia"));
    }

    @Test
    public void testCDate6() {
        Assert.assertTrue(mCDateFinder.match("styczniu"));
    }

    @Test
    public void testCDate7() {
        Assert.assertTrue(mCDateFinder.match("10 stycznia"));
    }

    @Test
    public void testCDate8() {
        Assert.assertTrue(mCDateFinder.match("stycznia 1119"));
    }

    @Test
    public void testSentenceSplitterRegex() {
        Assert.assertTrue("Testowy String. Drugie zdanie".matches("((.*)\\.(.*))*"));
    }
}
