package cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ilichev.webprac.WebpracApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = WebpracApplication.class)
public class CucumberSpringConfiguration {
}
