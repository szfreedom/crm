import com.sz.crm.utils.DateTimeUtil;
import com.sz.crm.utils.MD5Util;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class Test01 {
    @Test
    public static void main(String[] args) {
        /*String time1="2021-1-1 19:27:50";
        String time2 = DateTimeUtil.getSysTime();
        int i = time1.compareTo(time2);
        System.out.println(time2);
        System.out.println(i);*/
        String s = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(s);

    }
}
