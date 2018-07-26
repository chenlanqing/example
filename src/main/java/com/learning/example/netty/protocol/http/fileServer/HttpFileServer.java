package com.learning.example.netty.protocol.http.fileServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bluefish 2018/6/10
 * @version 1.0.0
 */
@Slf4j
public class HttpFileServer {
    private static final String default_url = "/";

    public static void main(String[] args) throws Exception {
        String url = default_url;
        new HttpFileServer().run(8080, url);
    }

    public void run(final int port, final String url) throws Exception{
        EventLoopGroup boss =new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder())
                            .addLast("http-aggregator", new HttpObjectAggregator(65536))
                            .addLast("http-encoder", new HttpResponseEncoder())
                            .addLast("http-chunked",new ChunkedWriteHandler())
                            .addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture f = b.bind("192.168.31.196", port).sync();
            log.info("Http文件目录服务器启动，网址是：http://192.168.31.196:{}{}", port, url);
            f.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
