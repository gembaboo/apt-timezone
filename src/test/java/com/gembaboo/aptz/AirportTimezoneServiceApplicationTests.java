package com.gembaboo.aptz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AirportTimezoneServiceApplicationTests.class)
public class AirportTimezoneServiceApplicationTests {


	@Test
	public void applicationStarts(){
		AirportTimezoneServiceApplication.main(new String[0]);
	}

}
