
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
            String txHash = "5AA504FCB2B751ABFC3F62B800EBCE14050DEE20F3E96EDFEAD1435B25B3FD5B";
            Boolean checkRet = jccCert.checkCert(fis,txHash);
            System.out.println("文件验真结果:"+checkRet);
            fis.close();

            //文本内容验真
            System.out.println("文本验真------------------------------------------------------");
            input = "测试字节流的存证上链哈希值";
            txHash = "528549CD686786526F3FD9DE43656C64C6D8A82ADBC09D2125639E55B46DECC9";
            checkRet = jccCert.checkCert(input.getBytes("UTF-8"),txHash);
            System.out.println("文本验真结果:"+checkRet);

            //hash验真
            System.out.println("哈希验真------------------------------------------------------");
            cid = "D913C0CF9AE5B110DE32101B180C562EE010C7DA328BDF472CF06470BBC4CB7D";
            txHash = "DDD2355FDB09EA42BB2EDF66272BAC4F7AC4BC9BFCAF1CE3E8D7D4C92CEB04C4";
            checkRet = jccCert.checkCert(cid,txHash);
            System.out.println("哈希验真结果:"+checkRet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
