import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Sender implements Runnable {
    private Buffer buffer;
    private int windowSize;
    private int[] data;
    private int[] responds;
    private int[] sent;
    private int pointer;
    private boolean GBN;

    public Sender(Buffer buffer, int windowSize, int[] data, boolean GBN) {
        this.buffer = buffer;
        this.windowSize = windowSize;
        this.data = data;
        this.GBN = GBN;
        this.responds = new int[this.data.length];
        this.sent = new int[this.data.length];
    }

    @Override
    public void run() {
        while (pointer != data.length) {
            if (sent[pointer] == 0) {
                sendToBuffer();
            }
            boolean receviedAll = true;
            for (int i = pointer; i < pointer + windowSize; i++) {
                if (responds[i] != 1) {
                    receviedAll = false;
                    break;
                }
            }
            if (receviedAll == true) {
                pointer = pointer + windowSize;
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void sendToBuffer() {
        for (int i = pointer; i < pointer + windowSize; i++) {
            Frame frame = new Frame(i, data[i]);
            write("SENDER : Sending out frame to buffer with index : " 
                    + frame.index + " and data " + frame.data + "\n");
            buffer.push(frame);
            this.sent[i] = 1;
        }
    }

    public void ACK(int index) {
        this.responds[index] = 1;
        write("SENDER : just Got ACK from recevier for index " + index + "\n");
    }

    public void NAK(int index) {
        if (!GBN) {
            Frame frame = new Frame(index, data[index]);
            write("SENDER : Sending frame with index " + index 
                    + " again cause just got NAK from recevier.\n");
            buffer.push(frame);
        } else {
            for (int i = pointer; i < pointer + windowSize; i++) {
                Frame frame = new Frame(i, data[i]);
                write("SENDER : Sending out frame to buffer with index : " 
                        + frame.index + " and data " + frame.data
                        + " (GBN).\n");
                buffer.push(frame);
            }
        }

    }

    private void write(String message) {
        try {
            FileWriter fw = new FileWriter("./sender.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(message);
            bw.newLine();
            bw.close();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

    }
}
