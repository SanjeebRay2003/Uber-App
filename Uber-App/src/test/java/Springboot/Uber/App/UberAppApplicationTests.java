package Springboot.Uber.App;

import Springboot.Uber.App.Services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class UberAppApplicationTests {

	@Autowired
	private EmailService emailService;

//	@Test
//	void sendEmail() {
//		emailService.sendEmail(
//				"sroy30014@gmail.com",
//				"hello",
//				"This is Body"
//		);
//	}

	@Test
	void sendEmailToMultiple() {
				String email[] = {

//						"sroy30014@gmail.com",
						"gafepa1020@nutrv.com"

				};

		emailService.sendEmailToMultiple(
                email,
				"hello",
				"This is Body"
		);


	}

}
