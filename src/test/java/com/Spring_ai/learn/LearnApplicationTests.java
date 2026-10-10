package com.Spring_ai.learn;

import com.Spring_ai.learn.service.JokeService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearnApplicationTests {

    @Autowired
	private  JokeService jokeService;



//	@Test
//	void getJoke() {
//		var response=jokeService.Joke("love","english");
//		System.out.println(response);
//	}
//	@Test
//	void injestpdfinvector(){
//		jokeService.injestPdfToVectorStore();
//	}

	@Test
	void buyshare (){
		var response=jokeService.buyshareIfitsInBudget("buy 10 shares of AAPL if its price is $150");
		System.out.println(response);
	}

}
