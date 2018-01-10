package main.tcp.sockettonetty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 成都科技大，与网关对接
 * Created by lc on 2017/3/23.
 */
public class SocketToNettyServerHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(SocketToNettyServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("SimpleServerHandler.channelRead");

        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];

        result.readBytes(result1);
        System.out.println(Arrays.toString(result1));
        result.release();

        // 向客户端发送消息
        String response = "success" + System.getProperty("line.separator");
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
