package com.kosa.batch.socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

import javax.sound.midi.Receiver;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//  클라이언트와 일대일 통신하는 역할

public class SocketClient {

	BatchServer batchServer; // server()의 메소드 호출위해 필요
	Socket socket;			// 연결을 끊을 때 필요
	DataInputStream dis;	
	DataOutputStream dos;
	String clientIp;
	Date date;
	String message;


	// 생성자
	// 서버와 소켓을 필드에 저장하고 문자열 입출력위해 보조스트림을 생성해 필드에 저장
	public SocketClient(BatchServer batchServer, Socket socket) {
		try {
			this.batchServer = batchServer;
			this.socket = socket;
			this.dis = new DataInputStream(socket.getInputStream());
			this.dos = new DataOutputStream(socket.getOutputStream());
			InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress(); // 클라이언트 정보를 얻고자 
			 // getHostName 사용하려고 강제 타입 변환 실행
			this.clientIp = isa.getHostName(); // 클라이언트 주소를 필드에 저장
			receive();
		}catch (IOException e) {
		}
	}


	// 클라이언트가 보낸 메시지를 receive메소드로 JSON 받고 읽기
	private void receive() {
		batchServer.threadPool.execute(() -> {
			try {
				while(true) {
					String receiveJson = dis.readUTF(); // 제이슨 읽기
					JSONParser jsonParser = new JSONParser();
					JSONObject jsonObject = (JSONObject) jsonParser.parse(receiveJson);
					 // jsonObject로 파싱해 command값 얻기
					String command = jsonObject.get("command").toString();

					switch (command) {
					case "start":
						this.clientIp = jsonObject.get("data").toString();
						batchServer.sendToSocketClient(this, "연결 성공");
						batchServer.addSocketClient(this);
						break;

					case "message":
						String message = jsonObject.get("data").toString();
						batchServer.sendToSocketClient(this, message);
						break;
					}
				}
			}catch(IOException | ParseException e) {
				batchServer.sendToSocketClient(this, "연결 해제");
				batchServer.removeSocketClient(this);
			}
		});

	}


	// JSON 보내기
	public void send(String json) {
		try {
			dos.writeUTF(json);
			dos.flush();
		}catch(IOException e) {
		}
	}


	// 연결종료
	public void close() {
		try {
			socket.close();
		}catch(Exception e) {}

	}

}
