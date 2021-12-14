package com.pyjava.demo.demochat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * <p>描述: [功能描述] </p>
 *
 * @author zhaojj11
 * @since 1.0
 */
public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 9999;

    private Selector selector;
    private SocketChannel socketChannel;
    private String username;


    public GroupChatClient() {
        try {
            // 得到Selector
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + "is ok ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        msg = username + ": " + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readMsg(){
        try {
            int select = selector.select();
            if(select > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if(key.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        String s = new String(buffer.array());
                        System.out.println(s.trim());
                    }
                    iterator.remove();
                }
            }else{
                //
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();

        new Thread(() -> {
            while (true){
                groupChatClient.readMsg();
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            groupChatClient.sendMsg(s);
        }
    }
}
