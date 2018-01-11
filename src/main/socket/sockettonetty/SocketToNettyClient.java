package main.socket.sockettonetty;

import java.io.*;
import java.net.Socket;

/**
 * Created by lc on 2018/1/10.
 */
public class SocketToNettyClient {
    private void sendMsg(){
        //客户端
        try {
            Socket socket = new Socket("localhost", 10086);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while (true){
                pw.write("哈哈");
                pw.flush();
                System.out.println("我是客户端，服务器说：" + br.readLine());
                Thread.sleep(2000);
            }

            //5、关闭资源
            /*pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
            socket.close();
            serverSocket.close();*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketToNettyClient socketToNetty = new SocketToNettyClient();
        try {
            new Thread(socketToNetty::sendMsg).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
