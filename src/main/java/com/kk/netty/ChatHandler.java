package com.kk.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 *  TextWebSocketFrame: 在 Netty 中，用于为 WebSocket 专门处理文本的对象，frame 是消息的载体
 *
 *
 *
 * @author kaikanwu
 * @date 20/01/2019
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 创建一个全局变量：用于记录和管理所有客户端的 channel
     */
    private  static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        // 获取客户端传输过来的消息
        String content = textWebSocketFrame.text();
        System.out.println("接收到的数据: " + content);

        // 发送收到的消息
        for (Channel channel : clients) {
            channel.writeAndFlush(
                    new TextWebSocketFrame(
                            "【服务器在】：" + LocalDateTime.now()
                                    + "接收到消息，消息为：" + content));

        }
        // 这个方法和上面的 for 循环，可以实现同样的目的
//        clients.writeAndFlush(new TextWebSocketFrame(
//                "【服务器在】：" + LocalDateTime.now()
//                        + "接收到消息，消息为：" + content));

    }

    /**
     * 当客户端链接服务端后（打开链接）
     * 获取客户端的 channel， 并且放到 ChannelGroup（全局变量） 中，去进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发 handlerRemoved（） 方法， ChannelGroup 会自动移除对应客户端的 channel
        // 等同于： clients.remove(ctx.channel())
        System.out.println("客户端断开， Channel 对应的长 id 是：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开， Channel 对应的短 id 是：" +ctx.channel().id().asShortText());

    }
}
