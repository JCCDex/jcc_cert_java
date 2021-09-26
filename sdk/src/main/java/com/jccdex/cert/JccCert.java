package com.jccdex.cert;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jccdex.cert.config.Config;
import com.jccdex.cert.utils.HashUtils;
import com.jccdex.rpc.JccJingtum;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * 存证工具类
 * @author xdjiang
 * @version 1.0.0
 */
public class JccCert {
    private  String  secret = null;
    private  String receiver = null;
    private  String token ;
    private  String amount;
    private volatile static JccCert jccCert;
    private JccJingtum jccJingtum;

    private JccCert(Boolean guomi, ArrayList<String> rpcNodes)  throws Exception {
        try {
            this.token = Config.DEFAULT_TOKEN;
            this.amount = Config.DEFAULT_AMOUNT;
            this.initJccJingtum(guomi, rpcNodes);

        }catch (Exception e) {
            throw  e;
        }
    }

    private JccCert(String secret, String receiver, Boolean guomi, ArrayList<String> rpcNodes)  throws Exception {
        try {
            this.secret = secret;
            this.receiver = receiver;
            this.token = Config.DEFAULT_TOKEN;
            this.amount = Config.DEFAULT_AMOUNT;
            this.initJccJingtum(guomi, rpcNodes);

        }catch (Exception e) {
            throw e;
        }
    }

    private JccCert(String secret, String receiver, String alphabet, String baseToken, Boolean guomi, ArrayList<String> rpcNodes)  throws Exception {
        try {
            this.secret = secret;
            this.receiver = receiver;
            this.token = Config.DEFAULT_TOKEN;
            this.amount = Config.DEFAULT_AMOUNT;
            this.initJccJingtum(alphabet, Config.DEFAULT_FEE, baseToken, guomi, rpcNodes);
        }catch (Exception e) {
            throw e;
        }
    }


    private void initJccJingtum(Boolean guomi, ArrayList<String> rpcNodes)  throws Exception {
        try {
            jccJingtum = new JccJingtum(guomi,rpcNodes);
        }catch (Exception e) {
            throw e;
        }
    }

    private void initJccJingtum(String alphabet, Integer fee, String baseToken, Boolean guomi, ArrayList<String> rpcNodes)  throws Exception {
        try {
            jccJingtum = new JccJingtum(alphabet,fee, baseToken, guomi, rpcNodes);
        }catch (Exception e) {
            throw e;
        }
    }

    /**
     * JccCert初始化方法
     * @param guomi 是否是国密版本
     * @param rpcNodes rpc节点服务器地址列表
     * @return JccCert对象
     * @throws Exception 抛出异常
     */
    public static JccCert init(Boolean guomi, ArrayList<String> rpcNodes) throws Exception {
        try {
            if (jccCert == null) {
                synchronized (JccCert.class) {
                    if (jccCert == null) {
                        jccCert = new JccCert(guomi, rpcNodes);
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
     * @param secret 交易发起方的钱包密钥
     * @param receiver 交易接收方的钱包地址
     * @param guomi 是否是国密版本
     * @param rpcNodes rpc节点服务器地址列表
     * @return JccCert对象
     * @throws Exception 抛出异常
     */
    public static JccCert init(String secret, String receiver, Boolean guomi, ArrayList<String> rpcNodes) throws Exception {
        try {
            if (jccCert == null) {
                synchronized (JccCert.class) {
                    if (jccCert == null) {
                        jccCert = new JccCert(secret, receiver, guomi, rpcNodes);
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
     * @param secret 交易发起方的钱包密钥
     * @param receiver 交易接收方的钱包地址
     * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
     * @param baseToken 交易燃料手续费通证,也是公链的本币
     * @param guomi 是否是国密版本
     * @param rpcNodes rpc节点服务器地址列表
     * @return JccCert对象
     * @throws Exception 抛出异常
     */
    public static JccCert init(String secret, String receiver, String alphabet, String baseToken,  Boolean guomi, ArrayList<String> rpcNodes) throws  Exception{
        try {
            if (jccCert == null) {
                synchronized (JccCert.class) {
                    if (jccCert == null) {
                        jccCert = new JccCert(secret, receiver, alphabet, baseToken, guomi, rpcNodes);
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
     * @return 存证的上链hash和文件hash值:{"cid":"文件hash值","txHash":"上链的交易hash"}
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
     * 文件上链存证
     * @param secret 上链的钱包密钥(发送者)
     * @param receiver 上链的钱包地址(接收者)
     * @param fis 文件流
     * @return 存证的上链hash和文件hash值:{"cid":"文件hash值","txHash":"上链的交易hash"}
     * @throws Exception
     */
    public  String saveCert(String secret,String receiver,InputStream fis) throws  Exception {

        try {
            String cHash = HashUtils.HashCode(fis);
            return this.saveCert(secret, receiver, cHash);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 字节流内容上链存证
     * @param input 字节流
     * @return 存证的上链hash和字节流hash值:{"cid":"字节流hash值","txHash":"上链的交易hash"}
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
     * 字节流内容上链存证
     * @param secret 上链的钱包密钥(发送者)
     * @param receiver 上链的钱包地址(接收者)
     * @param input 字节流
     * @return 存证的上链hash和字节流hash值:{"cid":"字节流hash值","txHash":"上链的交易hash"}
     * @throws Exception
     */
    public  String saveCert(String secret,String receiver,byte[] input) throws  Exception {

        try {
            String cHash = HashUtils.HashCode(input);
            return this.saveCert(secret, receiver, cHash);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * hash上链存证
     * @param cHash 对象的hash值(调用者生成内容对象的hash值，然后调用该接口进行存证上链)
     * @return 存证的上链hash和字节流hash值:{"cid":"字节流hash值","txHash":"上链的交易hash"}
     * @throws Exception
     */
    public String saveCert(String cHash) throws  Exception {
        try {
            if(this.secret == null) {
                throw new Exception("初始化未指定交易钱包");
            }
            if(this.receiver == null) {
                throw new Exception("初始化未为指定交易钱包");
            }

            JSONObject memoObj = new JSONObject();
            memoObj.put("cid",cHash);
            String tx = jccJingtum.paymentWithCheck(this.secret,this.receiver, this.token, this.amount, memoObj.toJSONString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(tx);
            String txHash = obj.get("result").get("tx_json").get("hash").asText();
            JSONObject retObj = new JSONObject();
            retObj.put("cid",cHash);
            retObj.put("txHash",txHash);
            return retObj.toJSONString();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * hash上链存证
     * @param secret 上链的钱包密钥(发送者)
     * @param receiver 上链的钱包地址(接收者)
     * @param cHash 对象的hash值(调用者生成内容对象的hash值，然后调用该接口进行存证上链)
     * @return 存证的上链hash和字节流hash值:{"cid":"字节流hash值","txHash":"上链的交易hash"}
     * @throws Exception
     */
    public String saveCert(String secret,String receiver,String cHash) throws  Exception {

        try {
            JSONObject memoObj = new JSONObject();
            memoObj.put("cid",cHash);
            String tx = jccJingtum.paymentWithCheck(secret,receiver, this.token, this.amount, memoObj.toJSONString());

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
     * @param fis 文件流
     * @param txHash 区块链交易hash值
     * @return 校验存证是否有效
     */
    public Boolean checkCert(InputStream fis, String txHash) {
        try {
            String cid = HashUtils.HashCode(fis);
            String tx = jccJingtum.requestTx(txHash);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(tx);
            String account = obj.get("result").get("Account").asText();
            String dest = obj.get("result").get("Destination").asText();
            String memoHex = obj.get("result").get("Memos").get(0).get("Memo").get("MemoData").asText();
            String memo = jccJingtum.getMemoData(memoHex);
            JSONObject memoObj = JSONObject.parseObject(memo);
            if(!jccJingtum.getAddress(secret).equals(account)) {
                return  false;
            }

            if(!receiver.equals(dest)) {
                return  false;
            }

            return cid.equals(memoObj.getString("cid"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验存证hash的真伪
     * @param sender 上链的钱包地址(发送者)
     * @param receiver 上链的钱包地址(接收者)
     * @param fis 文件流
     * @param txHash 区块链交易hash值
     * @return 校验存证是否有效
     */
    public Boolean checkCert(String sender,String receiver,InputStream fis, String txHash)  throws  Exception {
        try {
            String cid = HashUtils.HashCode(fis);
            String tx = jccJingtum.requestTx(txHash);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(tx);
            String account = obj.get("result").get("Account").asText();
            String dest = obj.get("result").get("Destination").asText();
            String memoHex = obj.get("result").get("Memos").get(0).get("Memo").get("MemoData").asText();
            String memo = jccJingtum.getMemoData(memoHex);
            JSONObject memoObj = JSONObject.parseObject(memo);
            if(!sender.equals(account)) {
                return  false;
            }

            if(!receiver.equals(dest)) {
                return  false;
            }

            return cid.equals(memoObj.getString("cid"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验存证hash的真伪
     * @param input 字节流
     * @param txHash 区块链交易hash值
     * @return 校验存证是否有效
     */
    public Boolean checkCert(byte[] input, String txHash) {
        try {
            String cid = HashUtils.HashCode(input);
            String tx = jccJingtum.requestTx(txHash);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(tx);
            String account = obj.get("result").get("Account").asText();
            String dest = obj.get("result").get("Destination").asText();
            String memoHex = obj.get("result").get("Memos").get(0).get("Memo").get("MemoData").asText();
            String memo = jccJingtum.getMemoData(memoHex);
            JSONObject memoObj = JSONObject.parseObject(memo);
            if(!jccJingtum.getAddress(secret).equals(account)) {
                return  false;
            }

            if(!receiver.equals(dest)) {
                return  false;
            }

            return cid.equals(memoObj.getString("cid"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验存证hash的真伪
     * @param sender 上链的钱包地址(发送者)
     * @param receiver 上链的钱包地址(接收者)
     * @param input 字节流
     * @param txHash 区块链交易hash值
     * @return 校验存证是否有效
     */
    public Boolean checkCert(String sender,String receiver,byte[] input, String txHash)  throws  Exception {
        try {
            String cid = HashUtils.HashCode(input);
            String tx = jccJingtum.requestTx(txHash);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(tx);
            String account = obj.get("result").get("Account").asText();
            String dest = obj.get("result").get("Destination").asText();
            String memoHex = obj.get("result").get("Memos").get(0).get("Memo").get("MemoData").asText();
            String memo = jccJingtum.getMemoData(memoHex);
            JSONObject memoObj = JSONObject.parseObject(memo);
            if(!sender.equals(account)) {
                return  false;
            }

            if(!receiver.equals(dest)) {
                return  false;
            }

            return cid.equals(memoObj.getString("cid"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验存证hash的真伪
     * @param cid 文件hash值
     * @param txHash 区块链交易hash值
     * @return 校验存证是否有效
     */
    public Boolean checkCert(String cid, String txHash) {
        try {
            String tx = jccJingtum.requestTx(txHash);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(tx);
            String account = obj.get("result").get("Account").asText();
            String dest = obj.get("result").get("Destination").asText();
            String memoHex = obj.get("result").get("Memos").get(0).get("Memo").get("MemoData").asText();
            String memo = jccJingtum.getMemoData(memoHex);
            JSONObject memoObj = JSONObject.parseObject(memo);
            if(!jccJingtum.getAddress(secret).equals(account)) {
                return  false;
            }

            if(!receiver.equals(dest)) {
                return  false;
            }

            return cid.equals(memoObj.getString("cid"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验存证hash的真伪
     * @param sender 上链的钱包地址(发送者)
     * @param receiver 上链的钱包地址(接收者)
     * @param cid 文件hash值
     * @param txHash 区块链交易hash值
     * @return 校验存证是否有效
     */
    public Boolean checkCert(String sender,String receiver,String cid, String txHash)  throws  Exception {
        try {
            String tx = jccJingtum.requestTx(txHash);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(tx);
            String account = obj.get("result").get("Account").asText();
            String dest = obj.get("result").get("Destination").asText();
            String memoHex = obj.get("result").get("Memos").get(0).get("Memo").get("MemoData").asText();
            String memo = jccJingtum.getMemoData(memoHex);
            JSONObject memoObj = JSONObject.parseObject(memo);
            if(!sender.equals(account)) {
                return  false;
            }

            if(!receiver.equals(dest)) {
                return  false;
            }

            return cid.equals(memoObj.getString("cid"));
        } catch (Exception e) {
            return false;
        }
    }
}
