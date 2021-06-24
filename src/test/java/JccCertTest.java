import com.jccdex.cert.JccCert;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.FileInputStream;

public class JccCertTest extends TestCase {
    JccCert jccCert;
    public void setUp() throws Exception {
        try {
            jccCert = JccCert.init();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    @Test
    public void testSaveCertWithFile() {

        System.out.println("in testSaveCertWithFile");
        try {
            FileInputStream fis = new FileInputStream("/Users/xdjiang/Desktop/WechatIMG212.jpeg");
            String ret = jccCert.saveCert(fis);
            System.out.println(ret);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void testSaveCertWithByte() {

        System.out.println("in testSaveCertWithByte");
        try {
            String input = "测试字节流的存证上链哈希值";
            String ret = jccCert.saveCert(input.getBytes("UTF-8"));
            System.out.println(ret);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    @Test
    public void testCheckCert() {

        System.out.println("in testCheckCert");
        try {
            Boolean ret = jccCert.checkCert("f90b34201d6b2bc6810172c4b4c2b88af4169c46e4ab8220e3925f4732ad28c9","521CA9ACCBA91A61F4008DE1C3AE3640968AC2DA26F461DD2D3C1807FEE8C79F");
            System.out.println(ret);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
