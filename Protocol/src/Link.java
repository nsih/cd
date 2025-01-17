import java.io.*;
import java.net.*;
import java.util.*;

public class Link implements Runnable {
	static Vector<Socket> clientList = new Vector<Socket>();
	static Vector<String> link=new Vector<String>();

	private Socket socket = null;

	private static Timer timer = null;
	int sender = -1;
	int receiver = -1;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	
	private static FileWriter fw = null;
	private static BufferedWriter bw= null;

//	static boolean receiverIsReady = false; // 呪重切亜 呪重 層搾研 刃戟梅澗走 蟹展鎧澗 雌殿

	synchronized public static int now() {
		return timer.now();
	}


	public static String fnow() {
		int time = now();
		int min = (int) (time / 60000);
		int sec = (int) (time / 1000);
		int msec = (int) (time % 1000);
		return String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%03d", msec);
	}

	public synchronized static void wFile(String str) {
		try {
			bw.write(str);
			bw.newLine();
			bw.flush(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	

	public Link(Socket socket, Timer timer) {
		try {

			this.socket = socket;
			Link.timer=timer;
			clientList.add(socket);
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			int socketNumber = clientList.indexOf(socket);
			out.write(socketNumber + "\n");
			out.flush();
			//wFile("けけ搭重馬壱 赤澗 社掴拭惟 革 腰硲 護 腰戚虞壱 硝形捜");
			//wFile("けけ薄仙 什傾球:" + Thread.currentThread().getId() + ", 薄仙 什傾球税 input:" + in);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// いい///

	}

	public void run() {
		try {
			// 4鯵 葛球亜 乞砧 尻衣吃 凶猿走 企奄
			while (clientList.size() != 4) {
				//wFile("けけi am thread:" + Thread.currentThread().getId());
			}

			timer.wake();
			Thread.sleep(10);
			
			//System.out.println("??");
			while (now()<60000) {
				// 適虞戚情闘亜 杭哀 左鎧掩 奄陥顕
				String inputMessage = in.readLine();
				
				//獣娃岨戚虞 身
				//獣娃 捜
				out.write(now()+"\n");
				out.flush();
				
				inputMessage = in.readLine();
				//雌殿澗? 戚虞壱 身
				if (inputMessage.contains("get status from Node:")) {
					int size=link.size();
					//搾醸生檎 搾醸陥 左蛙
					if(size==0) {
						out.write("idle\n");
						out.flush();
						//獣娃岨戚虞壱 身
						inputMessage=in.readLine();
						//獣娃 捜
						//wFile("けけ獣娃:"+fnow());
						out.write(now()+"\n");
						out.flush();
						//wFile("けけ獣娃早製");
						
						inputMessage=in.readLine();
						//穿勺馬切壱 身
						if (inputMessage.contains("data send request:")) {
							//元滴拭 汽戚斗 臣顕
							int sender = Character.getNumericValue(inputMessage.charAt(inputMessage.indexOf(":") + 1));
							int receiver = Character.getNumericValue(inputMessage.charAt(inputMessage.indexOf("-") + 1));
							link.add(sender+"-"+receiver);
							wFile(now() + " Node"+sender+" Data Send Request To Node"+receiver);
							//10 衆
							Thread.sleep(10);
							
							//jam戚劃? 虞壱 身
							inputMessage=in.readLine();
							//jam 伊紫
							//jam績
							if(link.size()>=2) {
								//jam 重硲 左蛙
								link.add("jam");
								wFile(now() + " Jam: Node"+sender+" Data Send Request To Node"+receiver);
								//適虞戚情闘拭惟 jam戚虞壱 左蛙
								out.write("jam:"+now()+"\n");
								out.flush();
								//50 衆
								Thread.sleep(50);
								link.clear();
								//+1けけけけけけけけけ
								//clear
								continue;
							//jam 焼還
							}else { 
								//適虞戚情闘拭惟 jam 焼艦悟 勺重 推短 呪喰廃陥壱 左蛙
								//"けけjam 焼艦陥");
								wFile(now() + " Accept: Node"+sender+" Data Send Request To Node"+receiver);
								out.write(now() +" Data Send Request Accept from Link\n");
								out.flush();
								// 呪重切税 ok 級嬢臣 凶猿走 域紗 奄陥顕
								while(true) {
									String temp="asdf";
									if(link.size()!=0) temp=link.get(0);
									if(temp.equals("ok!!")) {break;}
								}
								
								synchronized(this) {
								link.clear();

								link.add("start");
								}
								
								//獣拙戚虞 左鎧捜
								wFile(now()+" Node"+sender+" Data Send Start to Node"+receiver);
								out.write(now()+" Data Send Start to Node "+receiver+"\n");
								out.flush();
								//元滴拭亀 start 蓄亜

								//5段
								timer.inc();
								Thread.sleep(1);
								timer.inc();
								Thread.sleep(1);
								timer.inc();
								Thread.sleep(1);
								timer.inc();
								Thread.sleep(1);
								timer.inc();
								Thread.sleep(1);
								//魁左鎧捜
								synchronized(this) {
									link.clear();
									link.add("end");
								}
								
								wFile(now()+" Node"+sender+" Data Send Finished to Node"+receiver);
								out.write(now()+" Data Send Finished to Node "+receiver+"\n");
								out.flush();
								//元滴拭亀 魁 蓄亜

								continue;
							}
						 }
						 //魁鎧切壱 身
						 else {
							 continue;
						 }
					//照 搾醸生檎 照 搾醸陥 左蛙
					}else {
						out.write("not idle\n");
						out.flush();
						//獣娃岨戚虞壱 身
						inputMessage=in.readLine();
						//獣娃 捜
						out.write(now()+"\n");
						out.flush();
						
						inputMessage=in.readLine();
						
						//適虞戚情闘亜 左馨 獣娃戚 戚耕 走概陥檎, 走概延 廃汽 穿勺 亜管馬艦? 虞壱 身
						if(inputMessage.contains("data send request:")) {
							out.write("you cannot\n");
							out.flush();
							int sender = Character.getNumericValue(inputMessage.charAt(inputMessage.indexOf(":") + 1));
							int receiver = Character.getNumericValue(inputMessage.charAt(inputMessage.indexOf("-") + 1));
							wFile(now()+" Reject: Node"+sender+" Data Send Request To Node"+receiver);
						}
						//適虞戚情闘亜 左馨 獣娃戚 照 走概陥檎, 益撹 okay 馬壱 身
						else {
							
						}
						

						
						
						
						//jam戚劃?虞壱 身
						inputMessage=in.readLine();
						//jam 伊紫
						//jam戚蟹 更 陥献 適虞戚情闘亜 搭重掻績
						if(link.size()>=2) {
							//wFile("けけjam績");
							//適虞戚情闘拭惟 jam戚虞壱 左蛙
							out.write("jam\n");
							out.flush();
							continue;
						//jam 焼還
						}else {
							//陥獣 廃 腰 伊紫
							Thread.sleep(5);
						
							if(link.size()>=2) {
								out.write("jam\n");
								out.flush();
								continue;
							}
							
							//jam 焼艦虞壱 左蛙
							//wFile("けけ遭促 jam 焼還");
							out.write("no\n");
							out.flush();
							
							//蟹廃砺 左鎧劃壱 身
							inputMessage=in.readLine();
							//wFile("けけinputMessage:"+inputMessage+", 鎧 鳶填戚劃");
							//革 鳶填昔走 伊紫

							if(link.size()==0) {
								out.write("not yours\n");
								out.flush();
								continue;
							}
							
							
							String packet=link.get(0);
							//wFile("けけ鳶填:"+packet);
							int id = Character.getNumericValue(inputMessage.charAt(0));
							int sender = Character.getNumericValue(packet.charAt(0));
							int dest = Character.getNumericValue(packet.charAt(2));
							//格税 鳶填戚 o
							if(id==dest) {
								//wFile("けけ 鎧 鳶填戚 限生艦 閤切");
								out.write("yours\n");
								out.flush();
								//tell them accept虞壱 身
								inputMessage=in.readLine();
								//元滴拭 ok 臣軒壱 益隈陥壱 源敗
								synchronized(this) {
									link.clear(); //けけ
									link.add("ok!!");
								}
								out.write(now()+" Data Receive Start from Node "+sender+"\n"); 

								out.flush();
								//元滴拭 start 臣 凶猿走 企奄
								while(true) {
									String temp="asdf";
									try {
										if(link.size()!=0) temp=link.get(0);
									}catch(Exception e) {
										temp="asdf";
									}
									//wFile("けけ呪重切亜閤精依:"+temp);
									if(temp.equals("start")) {break;}
								}
								//wFile("けけ呪重切亜 start研 閤陥");
								//start梅陥壱 左鎧捜
								out.write("start\n");
								out.flush();
								//元滴拭 end 臣 凶猿走 企奄
								while(true) {
									String temp="asdf";
									try {
										if(link.size()!=0) temp=link.get(0);
									}catch(Exception e) {
										temp="asdf";
									}
									//wFile("けけ呪重切亜閤精依:"+temp);
									if(temp.equals("end")) {break;}
								}
								//wFile("けけ呪重切亜 end研 閤陥");
								//end梅陥壱 左鎧捜
								out.write(now()+" Data Receive Finished from Node"+sender+"\n");
								out.flush();
								// 元滴 短社
								link.clear();
								//獣娃岨戚虞壱 身
								inputMessage=in.readLine();
								//獣娃 捜
								out.write(now()+"\n");
								out.flush();
								continue;
							}
							//格税 鳶填戚 x
							else {
								//x虞壱 左鎧捜
								out.write("not yours\n");
								out.flush();
								continue;
							}
						}
						
					}
				}
				Thread.sleep(1);
			}
		

		} catch (Exception e) {
			
			//e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				//bw.close(); 
				//fw.close();
//				senderOut.close();
//				receiverOut.close();
			} catch (IOException e) {
				wFile("社掴聖 丸走 公敗");
			}
		}
	}

	public static void main(String[] args) {
		ServerSocket server = null;
		Socket client1 = null;
		Socket client2 = null;
		Socket client3 = null;
		Socket client4 = null;

		try {
			int socketPort = 3112;
			server = new ServerSocket(socketPort);

			
			Timer timer = new Timer();
			Thread t5 = new Thread(timer);
			t5.start();
			
			fw= new FileWriter("Link.txt"); 
			bw= new BufferedWriter(fw); 
			
			client1 = server.accept();
			Link link1 = new Link(client1, timer);
			Thread t1 = new Thread(link1);
			t1.start();

			client2 = server.accept();
			Link link2 = new Link(client2, timer);
			Thread t2 = new Thread(link2);
			t2.start();

			client3 = server.accept();
			Link link3 = new Link(client3, timer);
			Thread t3 = new Thread(link3);
			t3.start();

			client4 = server.accept();
			Link link4 = new Link(client4, timer);
			Thread t4 = new Thread(link4);
			t4.start();

			wFile(now() + " Link Start //00min 00sec 000msec");
			wFile(now() + " System Clock Start //00min 00sec 000msec");
	
			

			while (now()<60000) {
				;
			}
			t1.interrupt();
			t2.interrupt();
			t3.interrupt();
			t4.interrupt();
			
			
			
			wFile(now()+" Link Finished");
			bw.close();
			t5.interrupt();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
				client1.close();
				client2.close();
				client3.close();
				client4.close();
				
			} catch (IOException e) {
				wFile("社掴聖 丸走 公敗");
			}
		}
	}
}