package main.http.httpfile.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

/**
 * Created by lc on 2017/10/31.
 */
public class HttpFileServer {

    //指定ip地址和端口号
    static final String IP = "127.0.0.1";
    static final int PORT = 8080;

    public static void main(String[] args) {

        //创建线程组bossGroup(负责处理用户连接)和workerGroup（负责处理读写）
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务类
            ServerBootstrap b = new ServerBootstrap();
            //加入线程组
            b.group(bossGroup,workerGroup);
            //设置Socket工厂
            b.channel(NioServerSocketChannel.class);
            //设置管道工厂
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HttpRequestDecoder());
                    //解码器:将多个消息转换为单一的FullHttpRequest或FullHttpResponse （慎用）
                    ch.pipeline().addLast(new HttpObjectAggregator(65536));
                    //编码器：对响应的消息进行编码（序列化）
                    ch.pipeline().addLast(new HttpResponseEncoder());
                    //文件压缩
                    ch.pipeline().addLast(new HttpContentCompressor());
                    //支持异步发送大的码流，防止发生Java内存溢出
                    ch.pipeline().addLast(new ChunkedWriteHandler());
                    //业务处理类
                    ch.pipeline().addLast(new HttpFileServerHandler());
                }
            });

            //绑定端口号
            ChannelFuture future = b.bind(new InetSocketAddress(IP,PORT)).sync();

            System.out.println("打开浏览器  http://"+IP+":"+PORT);
            //关闭channelFuture
            future.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
