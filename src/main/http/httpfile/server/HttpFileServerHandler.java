package main.http.httpfile.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by lc on 2017/10/31.
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if(!request.decoderResult().isSuccess()){
            return;
        }
        if(request.method() != HttpMethod.GET){
            return;
        }
        final String uri = request.uri();
        final String path = sanitizeUri(uri);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private String sanitizeUri(String uri){
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException e1) {
                throw new Error("URL解码错误！");
            }
        }
        uri = uri.replace('/', File.separatorChar);
        if(uri.contains(File.separator + '.') || uri.contains('.' + File.separator) ||uri.endsWith(".")){
            return null;
        }
        return  System.getProperty("user.dir") + File.separator + uri;
    }
}
