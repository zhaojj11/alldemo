package com.pyjava.demo.demochat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>描述: [功能描述] </p>
 *
 * @author zhaojj11
 * @since 1.0
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 9999;


    public GroupChatServer() {
        try {
            // 得到Selector
            selector = Selector.open();

            listenChannel = ServerSocketChannel.open();

            listenChannel.socket().bind(new InetSocketAddress(PORT));

            listenChannel.configureBlocking(false);

            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {

        try {
            while (true) {
                int select = selector.select(2000);
                if (select > 0) {
                    // 有事件需要处理

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        // 通道accept
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }

                        // 通道可读
                        if (key.isReadable()) {
                            // 读取数据
                            this.readData(key);
                        }

                        iterator.remove();
                    }
                } else {
                    // 等待
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

    private void readData(SelectionKey key) {
        // 定义一个socketchannel
        SocketChannel channel = null;

        try {
            // 取得关联的channel
            channel = (SocketChannel) key.channel();
            // 创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            int read = channel.read(byteBuffer);

            if (read > 0) {
                // 读取到数据了
                String s = new String(byteBuffer.array());
                System.out.println("from 客户端: " + s);
                // 向其他客户端推送
                this.sendMsgToOtherClients(s, channel);
            }
        } catch (Exception e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                // 取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    /**
     * 转发消息
     *
     * @param msg  消息
     * @param self 当前客户端
     */
    private void sendMsgToOtherClients(String msg, SocketChannel self) throws IOException {
        // 转发消息
        for (SelectionKey key : selector.keys()) {
            // 取出通道
            Channel target = key.channel();
            // 排除自己
            if (target instanceof SocketChannel && target != self) {
                SocketChannel dest = (SocketChannel) target;
                ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                dest.write(wrap);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
