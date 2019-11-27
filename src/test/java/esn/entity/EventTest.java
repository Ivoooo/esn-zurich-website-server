package esn.entity;

import esn.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class EventTest {

    @Test
    public void createEvent(){
        //makes sense to test once proper DB is there.
    }
}
