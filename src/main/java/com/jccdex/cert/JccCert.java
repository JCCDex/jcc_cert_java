package com.jccdex.cert;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jccdex.cert.config.Config;
import com.jccdex.cert.utils.HashUtils;
import com.jccdex.cert.utils.OkhttpUtil;
import com.jccdex.core.client.Wallet;
import com.jccdex.rpc.JccJingtum;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * 存证工具类
 * @author xdjiang
 * @version 1.0.0
 */
public class JccCert {
    private  Wallet sender;
    private  String receiver;
    private  String token ;
    private  String amount;
    private volatile static JccCert jccCert;
    private JccJingtum jccJingtum;

    private JccCert() {
        try {
            sender = Wallet.fromSecret(Config.DEFAULT_WALLET_1);
            receiver = Config.DEFAULT_WALLET_2;
            token = Config.DEFAULT_TOKEN;
            amount = Config.DEFAULT_AMOUNT;
            this.initJccJingtum();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JccCert(String secret1,String address) {
        try {
            sender = Wallet.fromSecret(secret1);
            receiver = address;
            token = Config.DEFAULT_TOKEN;
            amount = Config.DEFAULT_AMOUNT;
            this.initJccJingtum();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initJccJingtum() {
        try {
            String res = OkhttpUtil.get(Config.DEFAULT_SERVER_CONFIG);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(res);
            String list = obj.get("rpcpeers").toString();
            ArrayList<String> rpcNodes= mapper.readValue(list, new TypeReference<ArrayList<String>>() {
            });
            jccJingtum = new JccJingtum(rpcNodes);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JccCert初始化方法
     * @return JccCert对象
     * @throws Exception
     */
    public static JccCert init() throws Exception {
        try {
            if (jccCert == null) {
                synchronized (JccCert.class) {
                    if (jccCert == null) {
                        jccCert = new JccCert();
                    }
                }
            }
            return jccCert;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * JccCert初始化方法
     * @param secret1 上链的钱包密钥(发送者)
     * @param receiver 上链的钱包地址(接收者)
     * @return JccCert对象
     * @throws Exception
     */
    public static JccCert init(String secret1,String receiver) throws  Exception{
        try {
            if(!Wallet.isValidSecret(secret1)) {
                throw new Exception("钱包密钥不合法");
            }

            if(!Wallet.isValidAddress(receiver)) {
                throw new Exception("钱包地址不合法");
            }

            if (jccCert == null) {
                synchronized (JccCert.class) {
                    if (jccCert == null) {
                        jccCert = new JccCert(secret1,receiver);
                    }
                }
            }
            return jccCert;
        }  catch (Exception e) {
            throw e;
        }
    }

    /**
     * 文件上链存证
     * @param fis 文件流
     * @return 存证的上链hash和文件hash值:{"fid":"文件hash值","txHash":"上链的交易hash"}
     * @throws Exception
     */
    public  String saveCert(InputStream fis) throws  Exception {
        try {
            String cHash = HashUtils.HashCode(fis);
            return this.saveCert(cHash);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 字节流内容上链存证
     * @param input 字节流
     * @return 存证的上链hash和字节流hash值:{"fid":"字节流hash值","txHash":"上链的交易hash"}
     * @throws Exception
     */
    public  String saveCert(byte[] input) throws  Exception {
        try {
            String cHash = HashUtils.HashCode(input);
            return this.saveCert(cHash);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * hash上链存证
     * @param cHash 对象的hash值(调用者生成内容对象的hash值，然后调用该接口进行存证上链)
     * @return 存证的上链hash和字节流hash值:{"fid":"字节流hash值","txHash":"上链的交易hash"}
     * @throws Exception
     */
    public String saveCert(String cHash) throws  Exception {
        try {
            JSONObject memoObj = new JSONObject();
            memoObj.put("cid",cHash);
            String tx = jccJingtum.paymentWithCheck(sender.getSecret(),receiver, token, amount, memoObj.toJSONString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(tx);
            String txHash = obj.get("result").get("hash").asText();
            JSONObject retObj = new JSONObject();
            retObj.put("cid",cHash);
            retObj.put("txHash",txHash);
            return retObj.toJSONString();
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 校验存证hash的真伪
     * @param cid 文件hash值
     * @param txHash 区块链交易hash值
     * @return 校验存证是否有效
     */
    public Boolean checkCert(String cid, String txHash)  throws  Exception {
        try {
            String tx = jccJingtum.requestTx(txHash);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(tx);
            String account = obj.get("result").get("Account").asText();
            String dest = obj.get("result").get("Destination").asText();
            String memoHex = obj.get("result").get("Memos").get(0).get("Memo").get("MemoData").asText();
            String memo = jccJingtum.getMemoData(memoHex);
            JSONObject memoObj = JSONObject.parseObject(memo);
            if(!sender.getAddress().equals(account)) {
                System.out.println("1");
                return  false;
            }

            if(!receiver.equals(dest)) {
                System.out.println("2");
                return  false;
            }

            return cid.equals(memoObj.getString("cid"));
        } catch (Exception e) {
            return false;
        }
    }
}
