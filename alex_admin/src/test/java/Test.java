import org.jasypt.util.text.BasicTextEncryptor;

/**
 *@description:
 *@author:       alex
 *@createDate:   2022/7/28 19:58
 *@version:      1.0.0
 */
public class Test {

    public static void main(String[] args) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("02700083-9fd9-4b82-a4b4-9177e0560e92");
        String encrypt = encryptor.encrypt("root");
        System.out.println(encrypt);
        String decrypt = encryptor.decrypt(encrypt);
        System.out.println(decrypt);
        String encrypt1 = encryptor.encrypt("mysql");
        System.out.println(encrypt1);
        String decrypt1 = encryptor.decrypt(encrypt1);
        System.out.println(decrypt1);
    }
}
