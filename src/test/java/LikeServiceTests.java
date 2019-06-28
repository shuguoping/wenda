import com.nowcoder.WendaApplication;
import com.nowcoder.service.LikeService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class LikeServiceTests {
    @Autowired
    LikeService likeService;

    /* 每次都初始化*/
    @Before
    public void setUp() {
        System.out.println("setUP");
    }

    @After
    public void setdown() {
        System.out.println("setdown");
    }

    @Test
    public void testlike() {
        System.out.println("testlike");
        likeService.like(1, 1, 1);
        //断言
        Assert.assertEquals(1, likeService.getLikeStatus(1, 1, 1));
        System.out.println();
        likeService.disLike(1, 1, 1);
        Assert.assertEquals(-1, likeService.getLikeStatus(1, 1, 1));
    }

    @Test
    public void testxx() {
        System.out.println(
                "testxxx"
        );
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void testexception() throws IllegalAccessException {
        System.out.println(
                "testexception"
        );
        throw new IllegalAccessException("异常");
    }*/

    /*仅仅初始化一次*/
    @BeforeClass
    public static void setUpclass() {
        System.out.println("setUP");
    }

    @AfterClass
    public static void setdownclass() {
        System.out.println("setdown");
    }
}
