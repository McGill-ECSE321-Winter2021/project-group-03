package ca.mcgill.ecse321.isotopecr;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@EntityScan("ca.mcgill.ecse321.model")
//@RunWith(SpringRunner.class)
@SpringBootTest
class IsotopeCRApplicationTests {

	@Test
	void contextLoads() {
	}

}
