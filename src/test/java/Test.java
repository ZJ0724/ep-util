import pers.ZJ.UiAuto.exception.ExecuteException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Test {

    public static void main(String[] args) throws Exception{

        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        System.out.println(dateFormat.parse("2019-03-14T22:49:37"));

    }

}
