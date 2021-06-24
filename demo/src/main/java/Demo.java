
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jccdex.cert.JccCert;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        //挂单
        try {
            JccCert jccCert = JccCert.init();
            //存证上链--------------------------
            FileInputStream fis = new FileInputStream("/Users/xdjiang/Desktop/WechatIMG212.jpeg");
            String ret = jccCert.saveCert(fis);
            System.out.println("存证上链内容:"+ret);
            fis.close();

            //存证校验--------------------------
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(ret);
            String txHash = obj.get("txHash").asText();
            String cid = obj.get("cid").asText();
            System.out.println("存证校验结果:"+jccCert.checkCert(cid,txHash));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
