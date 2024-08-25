package ggyuel.ggyuup;

import ggyuel.ggyuup.DataCrawling.DataCrawlingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ggyuel.ggyuup.DataCrawling.DataCrawlingService;

import java.io.IOException;

@SpringBootTest
class GgyuUpApplicationTests {

	@Test
	void CrawlingTest() throws InterruptedException, IOException {
		DataCrawlingService.RefreshAllData();
	}

}
