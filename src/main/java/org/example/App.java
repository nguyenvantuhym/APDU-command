package org.example;



import javax.smartcardio.*;
import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            // Lấy danh sách các thiết bị thẻ thông minh có sẵn
            TerminalFactory terminalFactory = TerminalFactory.getDefault();
            CardTerminals cardTerminals = terminalFactory.terminals();

            // Chọn thiết bị thẻ thông minh đầu tiên trong danh sách
            if (cardTerminals.list().isEmpty()) return;
            CardTerminal cardTerminal = cardTerminals.list().get(0);

            // Kết nối tới thiết bị thẻ thông minh
            Card card = cardTerminal.connect("*");

            // Lấy đối tượng CardChannel để tương tác với thiết bị thẻ thông minh
            CardChannel cardChannel = card.getBasicChannel();

            // APDU command
            int[] command = {0xff, 0xca, 0x00, 0x00, 0x00};

            byte[] byteArray = new byte[5];
            for (int i = 0; i < command.length; i++) {
                byteArray[i] = (byte) (command[i]);
            }
            System.out.println((int) (byteArray[0] & 0xFF));


            // Gửi APDU command và nhận kết quả
            CommandAPDU com = new CommandAPDU(byteArray);
            ResponseAPDU response = cardChannel.transmit(com);

            // Xử lý kết quả
            byte[] responseData = response.getBytes();
            String responseHex = DatatypeConverter.printHexBinary(responseData);
            System.out.println("Response: " + responseHex);

            // Ngắt kết nối với thiết bị thẻ thông minh
            card.disconnect(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

