
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jccdex.cert.JccCert;
import com.jccdex.core.client.Wallet;

import java.io.FileInputStream;
import java.util.ArrayList;

public class DemoSWT {
    public static void main(String[] args) {
        JccCert jccCert;
        Wallet wallet1 = Wallet.fromSecret("snJzg27EHUxJj6fiHU1t7nJea5Y3Q");//jsXw3znEHPYDB78w1f21T6Ya3hUVVUwT6H
        Wallet wallet2 = Wallet.fromSecret("snLpjnCcbG3QEEiGdvZAg6yBvMKHp");//jsys77BYKczqePgUA2S5oinctLtpd48qVP

        ArrayList<String> rpcNodes = new ArrayList <String> ();
        rpcNodes.add("https://stestswtcrpc.jccdex.cn");
        try {
            //初始化
            jccCert = JccCert.init(wallet1.getSecret(), wallet2.getAddress(),false,rpcNodes);

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
            String txHash = "FF23599CC99D7A98BB2C7127A0994EA29CC2DB331DF3DEB381A7F78AA1A609A7";
            Boolean checkRet = jccCert.checkCert(fis,txHash);
            System.out.println("文件验真结果:"+checkRet);
            fis.close();

            //文本内容验真ss
            System.out.println("文本验真------------------------------------------------------");
            input = "测试字节流的存证上链哈希值";
            txHash = "C4047C2A1A5AAE7C705956F1FB2B4FF084AF0534B2097BD97AEDFC8E079BACBD";
            checkRet = jccCert.checkCert(input.getBytes("UTF-8"),txHash);
            System.out.println("文本验真结果:"+checkRet);

            //hash验真
            System.out.println("哈希验真------------------------------------------------------");
            cHash = "3b77f64eb774969269555e7154a40ee137f431fa28d24dedcd25a77f54fef6bf";
            txHash = "DF6938695AD9461F3CCB09D0DE18E45EA4591BA55AAF1A0E4776462E3A318EFA";
            checkRet = jccCert.checkCert(cHash,txHash);
            System.out.println("哈希验真结果:"+checkRet);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
