package com.SE.ITHub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"spring.sql.init.mode=never",
		"flyway.enabled=false"
})
class ItHubForAClientApplicationTests {

	@Test
	void contextLoads() {
	}

}