
import com.jccdex.cert.JccCert;
import com.jccdex.core.client.Wallet;
import com.jccdex.core.client.WalletSM;

import java.io.FileInputStream;
import java.util.ArrayList;

public class DemoSWTGuomi {
    public static void main(String[] args) {
        JccCert jccCert;
        WalletSM wallet1 = WalletSM.fromSecret("snHu1pSHRksTQQMJGa2gZH6gTSukK");//jpSojXsu7mfwStH7ig72yCg86ViKH5dHWN
        WalletSM wallet2 = WalletSM.fromSecret("snwsVxeKqgKqckhomX6xvguwj5Jci");//j35gQsGrcsTPSmmRmxgVRwtetkp6NGvN4Z

        ArrayList<String> rpcNodes = new ArrayList <String> ();
        rpcNodes.add("http://139.198.19.157:4950");
        try {
            //初始化
            jccCert = JccCert.init(wallet1.getSecret(), wallet2.getAddress(),true,rpcNodes);

            //文件存证
            System.out.println("文件存证上链------------------------------------------------------");
            FileInputStream fis = new FileInputStream("/Users/xdjiang/Desktop/WechatIMG212.jpeg");
            String ret = jccCert.saveCert(fis);
            System.out.println(ret);
            fis.close();

            //文本内容存证
            System.out.println("文本内容存证上链------------------------------------------------------");
            String input = "测试字节流的存证上链哈希值";
            ret = jccCert.saveCert(input.getBytes("UTF-8"));
            System.out.println(ret);

            //hash上链
            System.out.println("哈希存证上链------------------------------------------------------");
            String cHash = "3b77f64eb774969269555e7154a40ee137f431fa28d24dedcd25a77f54fef6bf";
            ret = jccCert.saveCert(cHash);
            System.out.println(ret);

            //文件验真
            System.out.println("文件验真------------------------------------------------------");
            fis = new FileInputStream("/Users/xdjiang/Desktop/WechatIMG212.jpeg");
            String txHash = "E5622C3F704C4D9F4312E650DA1FC95A980AF05280701DA311B736E943466CE9";
            Boolean checkRet = jccCert.checkCert(fis,txHash);
            System.out.println("文件验真结果:"+checkRet);
            fis.close();

            //文本内容验真ss
            System.out.println("文本验真------------------------------------------------------");
            input = "测试字节流的存证上链哈希值";
            txHash = "2F9A4F58A18E7D2F47ABD096776411E956E729E76EEEC7CAD6ECF7AB1DADB502";
            checkRet = jccCert.checkCert(input.getBytes("UTF-8"),txHash);
            System.out.println("文本验真结果:"+checkRet);

            //hash验真
            System.out.println("哈希验真------------------------------------------------------");
            cHash = "3b77f64eb774969269555e7154a40ee137f431fa28d24dedcd25a77f54fef6bf";
            txHash = "FE93BDED70DBF9A6CC336F3BAE1FB2EB97173583E59E5C07964B3E78BA6E94FC";
            checkRet = jccCert.checkCert(cHash,txHash);
            System.out.println("哈希验真结果:"+checkRet);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
