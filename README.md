# jcc_cert_java(1.0.0)

## 引用依赖包
````java
<dependency>
    <groupId>io.github.jccdex</groupId>
    <artifactId>JccWallet</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>io.github.jccdex</groupId>
    <artifactId>JccRPC</artifactId>
    <version>3.1.0</version>
</dependency>
<dependency>
    <groupId>io.github.jccdex</groupId>
    <artifactId>JccCert</artifactId>
    <version>1.0.0</version>
</dependency>
````

## 接口描述

### 构造函数
````java
/**
* JccCert初始化方法
* @return JccCert对象
* @throws Exception
*/
public static JccCert init() throws Exception 

/**
* JccCert初始化方法
* @param secret1 上链的钱包密钥(发送者)
* @param receiver 上链的钱包地址(接收者)
* @return JccCert对象
* @throws Exception
*/
public static JccCert init(String secret1,String receiver) throws  Exception
````

### 文件存证上链
````java
/**
* 文件上链存证
* @param fis 文件流
* @return 存证的上链hash和文件hash值:{"cid":"文件hash值","txHash":"上链的交易hash"}
* @throws Exception
*/
public  String saveCert(InputStream fis) throws  Exception
````

### 字节流存证上链
````java
/**
* 字节流内容上链存证
* @param input 字节流
* @return 存证的上链hash和字节流hash值:{"cid":"字节流hash值","txHash":"上链的交易hash"}
* @throws Exception
*/
public  String saveCert(byte[] input) throws  Exception
````

### hash上链存证
````java
/**
* hash上链存证
* @param cHash 对象的hash值(调用者生成内容对象的hash值，然后调用该接口进行存证上链)
* @return 存证的上链hash和字节流hash值:{"cid":"字节流hash值","txHash":"上链的交易hash"}
* @throws Exception
*/
public String saveCert(String cHash) throws  Exception
````

### 校验存证
````java
/**
* 校验存证hash的真伪
* @param cid 文件hash值
* @param txHash 区块链交易hash值
* @return 校验存证是否有效
*/
public Boolean checkCert(String cid, String txHash)  throws  Exception
````

## 调用示例
````java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jccdex.cert.JccCert;
import java.io.FileInputStream;

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
````



