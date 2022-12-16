package com.kosa.batch.socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BatchClient {

	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	String clientIp;
//	Date date;
//	String message;

	
	// 서버연결 : (localhost, 50001)에 연결요청
	public void connect() throws IOException {
		socket = new Socket("localhost", 50001);
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		System.out.println("[클라이언트] 서버에 연결됨");
	}

	// 서버가 보낸 JSON 받기
	public void receive() {
		Thread thread = new Thread(() -> {
			try {
				while(true) {
					String json = dis.readUTF();
					JSONParser jsonParser = new JSONParser();
					JSONObject root = (JSONObject)jsonParser.parse(json);
					
					String clientIp = root.get("clientIp").toString();
					String message = root.get("message").toString();
					String date = root.get("date").toString();
					System.out.println("[" + clientIp + "]" + message + "|" + date);
				}
			}catch(Exception e1) {
				System.out.println("[클라이언트] 서버 끊김");
				System.exit(0);
			}
		});
		thread.start();

	}

	// 서버로 JSON 보내기
	public void send(String json) throws IOException{
		dos.writeUTF(json);
		dos.flush();
	}

	
	// 서버 연결 종료 : q입력 시 종료
	public void unconnect() throws IOException{
		socket.close();
	}

	
	// 메인
	public static void main(String[] args) {
		try {
			BatchClient batchClient = new BatchClient();
			batchClient.connect();

			Scanner scanner = new Scanner(System.in);
			System.out.println("접속 IP입력");
			batchClient.clientIp = scanner.nextLine();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", "start"); //command를 start로 보냄
			jsonObject.put("data", batchClient.clientIp);
			String json = jsonObject.toString();
			batchClient.send(json);

			batchClient.receive();
			
			System.out.println("--------------");
			System.out.println("종료시 q + enter");
			System.out.println("--------------");

			while(true) {
				String message = scanner.nextLine();
				if(message.toLowerCase().equals("q")) {
					break;
				}else {
					jsonObject = new JSONObject();
					jsonObject.put("command", "message");
					jsonObject.put("data", message);
					json = jsonObject.toString();
					batchClient.send(json);
				}
			}
			scanner.close();
			batchClient.unconnect();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("[클라이언트] 서버 연결 안됨");
		}
	}
}
