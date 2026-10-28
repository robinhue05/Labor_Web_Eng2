package de.dhbw.skywatch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Smoke test: fails if the Spring context can't start (e.g. broken config)
@SpringBootTest
class SkywatchApplicationTests {

	@Test
	void contextLoads() {
		// nothing to do here, starting the context is the test
	}

}
