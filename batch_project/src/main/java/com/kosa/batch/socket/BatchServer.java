package com.kosa.batch.socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.json.simple.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BatchServer {

	ServerSocket serverSocket;
	ExecutorService threadPool = Executors.newFixedThreadPool(10);


	// * 서버 시작
	public void start() throws IOException {
		Executors.newFixedThreadPool(10);
	}



	
	// =================================================================
	// 소켓 클라이언트에게 메시지를 보내는 것
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private LocalDateTime startDay; 
	
	// 전체 클라이언트에 전달할 data 
	public void send(String message) { 
		
	}
	// =================================================================

	// * 서버 종료
	public void stop() {
		try {
			
			threadPool.shutdownNow();
			batchRoom.values().stream().forEach(sc -> sc.close());
			System.out.println("[서버] 종료");
		} catch (IOException e1) {
		}
	}


	// 메인 메소드
	public static void main(String[] args) {
		try {
			BatchServer batchServer = new BatchServer();
			batchServer.start();
			
			System.out.println("---------------------");
			System.out.println("서버 종료하려면 q + enter");
			System.out.println("---------------------");
			
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String key = scanner.nextLine();
				if (key.equals("q")) break; // 수동으로 종료할 때
			}
			scanner.close();
			batchServer.stop();
		} catch (Exception e) {
			System.out.println("[서버] " + e.getMessage());
		}
	}

}
