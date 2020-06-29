package com.sysbot32.movenpki;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static final int PORT = 13681;

    private SocketChannel socketChannel;
    private ExecutorService executorService;
    private ByteBuffer receivedData = null;

    private static Client client;

    public Client() {
        try {
            socketChannel = SocketChannel.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService = Executors.newSingleThreadExecutor();

        client = this;
    }

    public void start() {
        executorService.submit(this::receiving);
    }

    public void stop() {
        executorService.shutdownNow();
    }

    public void connect(String address) {
        if (Objects.isNull(socketChannel)) {
            return;
        }

        if (socketChannel.isConnected()) {
            disconnect();
        }
        if (!socketChannel.isOpen()) {
            try {
                socketChannel = SocketChannel.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new Thread(() -> {
            try {
                boolean ret = socketChannel.connect(new InetSocketAddress(address, PORT));
                if (ret) {
                    System.out.println("Connected to " + socketChannel.getRemoteAddress() + ".");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void disconnect() {
        stop();
        try {
            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Disconnected.");
    }

    public void send(ByteBuffer data) {
        if (!isConnected()) {
            return;
        }

        new Thread(() -> {
            int size = data.capacity();
            ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES + size);
            byteBuffer.putInt(size);
            byteBuffer.put(data);
            byteBuffer.flip();
            try {
                socketChannel.write(byteBuffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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

    private void receiving() {
        while (isConnected()) {
            ByteBuffer data = receive();
            if (Objects.isNull(data)) {
                break;
            }
            receivedData = data;
        }
        disconnect();
    }

    public boolean isConnected() {
        return Objects.nonNull(socketChannel) && socketChannel.isConnected();
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public ByteBuffer getReceivedData() {
        return receivedData;
    }

    public static Client getClient() {
        return client;
    }
}
