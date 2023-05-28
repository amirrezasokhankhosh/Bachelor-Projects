import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Recevier implements Runnable {
    private int windowSize;
    private int[] data;
    private int dataLenght;
    private int recevied[];
    private Sender sender;
    private Buffer buffer;
    private int pointer;
    private int bufferPointer;

    public Recevier(int windowSize, Sender sender, int dataLenght, Buffer buffer) {
        this.windowSize = windowSize;
        this.sender = sender;
        this.dataLenght = dataLenght;
        this.buffer = buffer;
        data = new int[this.dataLenght];
        recevied = new int[this.dataLenght];
        pointer = 0;
        bufferPointer = 0;
    }

    @Override
    public void run() {
        while (pointer != dataLenght) {
            write("RECEVIER : Starting to read from buffer in index " + bufferPointer + "\n");

            Frame frame = buffer.read(bufferPointer);
            if (frame != null) {
                if (recevied[frame.index] != 1) {
                    int num = (int) (Math.random() * 100);
                    if (num < 50) {
                        write("RECEIVER : Got frame with index " + frame.index
                                + " from buffer , Sending NAK to sender.\n");
                        sender.NAK(frame.index);
                    } else {
                        data[frame.index] = frame.data;
                        recevied[frame.index] = 1;
                        write("RECEIVER : Got frame with index " + frame.index
                                + " from buffer , Sending ACK to sender.\n");
                        sender.ACK(frame.index);
                    }
                }

                bufferPointer = bufferPointer + 1;
            } else {
                write("RECEVIER : index " + bufferPointer + " is not added yet.\n");
            }
            boolean receviedAll = true;
            for (int i = pointer; i < pointer + windowSize; i++) {
                if (recevied[i] == 0) {
                    receviedAll = false;
                    break;
                }
            }
            if (receviedAll == true) {
                pointer = pointer + windowSize;
            }
        }

        System.out.print("Window Size : " + this.windowSize + " - Data Recevied.\n");

    }

    private void write(String message) {
        try {
            FileWriter fw = new FileWriter("./recevier.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(message);
            bw.newLine();
            bw.close();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

    }

}
