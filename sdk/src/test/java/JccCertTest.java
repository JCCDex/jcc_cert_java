import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jccdex.cert.JccCert;
import com.jccdex.core.client.WalletSM;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.io.FileInputStream;
import java.util.ArrayList;

public class JccCertTest extends TestCase {
    JccCert jccCert;
    WalletSM wallet1 = WalletSM.fromSecret("snHu1pSHRksTQQMJGa2gZH6gTSukK");//jpSojXsu7mfwStH7ig72yCg86ViKH5dHWN
    WalletSM wallet2 = WalletSM.fromSecret("snwsVxeKqgKqckhomX6xvguwj5Jci");//j35gQsGrcsTPSmmRmxgVRwtetkp6NGvN4Z
    public void setUp() {
        try {
            ArrayList<String> rpcNodes = new ArrayList <String> ();
            rpcNodes.add("https://testskywelldrpc.ahggwl.com");
            jccCert = JccCert.init(wallet1.getSecret(), wallet2.getAddress(),true,rpcNodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveCertWithFile() {
        System.out.println("in testSaveCertWithFile");
        try {
            FileInputStream fis = new FileInputStream("/Users/xdjiang/Desktop/WechatIMG212.jpeg");
            String ret = jccCert.saveCert(fis);
            fis.close();
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveCertWithHash() {
        System.out.println("in testSaveCertWithByte");
        try {
            String cHash = "3b77f64eb774969269555e7154a40ee137f431fa28d24dedcd25a77f54fef6bf";
            String ret = jccCert.saveCert(cHash);
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckCertWithFile() {

        System.out.println("in testCheckCertWithFile");
        try {
            FileInputStream fis = new FileInputStream("/Users/xdjiang/Desktop/WechatIMG212.jpeg");
            String result = jccCert.saveCert(fis);
            fis.close();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(result);
            String txHash = obj.get("txHash").asText();
            fis = new FileInputStream("/Users/xdjiang/Desktop/WechatIMG212.jpeg");
            Boolean ret = jccCert.checkCert(fis,txHash);
            System.out.println(ret);
            Assert.assertTrue(ret);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckCertWithByte() {
        System.out.println("in testCheckCertWithByte");
        try {
            String input = "测试字节流的存证上链哈希值";
            String result = jccCert.saveCert(input.getBytes("UTF-8"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(result);
            String txHash = obj.get("txHash").asText();
            Boolean ret = jccCert.checkCert(input.getBytes("UTF-8"),txHash);
            System.out.println(ret);
            Assert.assertTrue(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckCertWithHash() {
        System.out.println("in testCheckCertWithHash");
        try {
            String cHash = "3b77f64eb774969269555e7154a40ee137f431fa28d24dedcd25a77f54fef6bf";
            String result = jccCert.saveCert(cHash);
//            System.out.println(result);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(result);
            String txHash = obj.get("txHash").asText();
            Boolean ret = jccCert.checkCert(cHash,txHash);
            System.out.println(ret);
            Assert.assertTrue(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
