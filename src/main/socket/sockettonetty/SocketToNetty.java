package main.socket.sockettonetty;

import java.io.*;
import java.net.Socket;

/**
 * Created by lc on 2018/1/10.
 */
public class SocketToNetty {
    private void sendMsg() throws Exception{
        Socket socket;
        //客户端
        try {
            socket = new Socket("127.0.0.1", 10086);
            System.out.println("客户端开始连接");
            //一直读取控制台
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                //包体
                byte[] content = new byte[]{0x01, 0x02};
                BufferedOutputStream bis = new BufferedOutputStream(socket.getOutputStream());
                bis.write(content);
                bis.flush();
                Thread.sleep(2000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new SocketToNetty().sendMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
