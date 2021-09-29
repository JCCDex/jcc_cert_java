
import com.jccdex.cert.JccCert;
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
            String cid = "D913C0CF9AE5B110DE32101B180C562EE010C7DA328BDF472CF06470BBC4CB7D";
            ret = jccCert.saveCert(cid);
            System.out.println(ret);

            //文件验真
            System.out.println("文件验真------------------------------------------------------");
            fis = new FileInputStream("/Users/xdjiang/Desktop/WechatIMG212.jpeg");
            String txHash = "D913C0CF9AE5B110DE32101B180C562EE010C7DA328BDF472CF06470BBC4CB7D";
            Boolean checkRet = jccCert.checkCert(fis,txHash);
            System.out.println("文件验真结果:"+checkRet);
            fis.close();

            //文本内容验真ss
            System.out.println("文本验真------------------------------------------------------");
            input = "测试字节流的存证上链哈希值";
            txHash = "E28E7E67072657ED3679F705DE7C757F537387618C04DC9BC71E05854CE5FA42";
            checkRet = jccCert.checkCert(input.getBytes("UTF-8"),txHash);
            System.out.println("文本验真结果:"+checkRet);

            //hash验真
            System.out.println("哈希验真------------------------------------------------------");
            cid = "3b77f64eb774969269555e7154a40ee137f431fa28d24dedcd25a77f54fef6bf";
            txHash = "BD6E853E37139A462BC1383087E139739034AABD9FCC492553F971D3495462DA";
            checkRet = jccCert.checkCert(cid,txHash);
            System.out.println("哈希验真结果:"+checkRet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
