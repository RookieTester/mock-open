import com.server.example.util.comparator.Size;
import com.server.example.util.comparator.SizeComparator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/12/25.
 */
public class SizeComparatorTest {
    @Test
    public void test(){
        Size size_0=new Size(0);
        Size size_1=new Size(2);
        Size size_2=new Size(12);
        Size size_3=new Size(3);
        List<Size> sizes=new ArrayList<Size>();
        sizes.add(size_0);
        sizes.add(size_1);
        sizes.add(size_2);
        sizes.add(size_3);

        SizeComparator sizeComparator=new SizeComparator();
        Collections.sort(sizes,sizeComparator);
        for (Size size:sizes){
            System.out.println(size.getTime());
        }
    }
}
