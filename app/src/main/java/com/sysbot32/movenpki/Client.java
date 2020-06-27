package com.sysbot32.movenpki;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class Client {
    public static final int PORT = 13681;

    private SocketChannel socketChannel;

    public Client() {
        try {
            socketChannel = SocketChannel.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean connect(String address) {
        if (Objects.isNull(socketChannel)) {
            return false;
        }

        boolean ret;
        try {
            ret = socketChannel.connect(new InetSocketAddress(address, PORT));
            if (ret) {
                System.out.println("Connected to " + socketChannel.getRemoteAddress() + ".");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

    public void send(ByteBuffer data) {
        if (!isConnected()) {
            return;
        }

        data.flip();
        try {
            socketChannel.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ByteBuffer receive() {
        if (!isConnected()) {
            return null;
        }

        ByteBuffer size = ByteBuffer.allocate(Integer.BYTES);
        ByteBuffer data;
        try {
            int ret = socketChannel.read(size);
            size.flip();
            if (ret == -1) {
                return null;
            }

            data = ByteBuffer.allocate(size.getInt());
            while (data.hasRemaining()) {
                socketChannel.read(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        data.flip();
        return data;
    }

    public boolean isConnected() {
        return Objects.nonNull(socketChannel) && socketChannel.isConnected();
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
