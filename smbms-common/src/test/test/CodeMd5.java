package test;

import com.smbms.tools.MD5;
import org.apache.commons.lang.RandomStringUtils;

public class CodeMd5 {
    public static void main(String[] args) {
        String password = "6666666";
        String salt = RandomStringUtils.randomAlphabetic(20);
        String s = MD5.md5(password, salt);
        System.out.println(salt);
        System.out.println(s);
    }
}
