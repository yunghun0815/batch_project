package com.kosa.batch.socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

@Component
public class ExecutorServiceEx {
	
	public static void putTask(Runnable task) {
		
		// 1000개의 R생성
		String[][] Rs = new String[100][2];
		for(int i=0; i<Rs.length; i++) {
			Rs[i][0] = i + " - IP주소"; 
			Rs[i][1] = "성공";

		}
		
		// 스레드풀 생성
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		
		
		// R을 보내는 작업생성과 처리요청
		for(int i=0; i<1000; i++) {
			final int idx = i;
			executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					LocalDate today = LocalDate.now();

					LocalTime present = LocalTime.now();
					//스레드가 처리할 작업 내용
					Thread thread = Thread.currentThread();
					String ip = Rs[idx][0];
					String content = Rs[idx][1];

					System.out.println("[" + thread.getName() + "]" + ip + " | " + content + " : " + today + " " + present);
				}
			});
		}
		
		// 스레드 종료
		executorService.shutdownNow();
		
	}
}