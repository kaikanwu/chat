package com.kk.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author kaikanwu
 * @date 20/01/2019
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // WebSocket 基于 http 协议，所以需要 http 编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对 写大数据流 的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对 HTTP Message 进行聚合，聚合为 FullHttpRequest 或者 FullHttpResponse
        // 几乎在 Netty 的编程中，都会使用到此 handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        //============= 以上handler 是为了支持 Http 协议==========================


        // WebSocket 服务器处理的协议，用于指定给客户端连接访问的路由：/ws
        // 本 handler 会帮你处理一些繁重的复杂的事
        // 会帮你处理握手动作： handshaking(close, ping, pong) ping + pong = 心跳
        // 对于 WebSocket 来说，都是以 frames 进行传输，不同的数据类型对应的 frames 也不同
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义的 handler
        pipeline.addLast(new ChatHandler());
    }
}