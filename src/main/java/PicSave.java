
import java.io.*;

public class PicSave {

    FileOutputStream fos;
    DataOutputStream dos;
    File f;
    int red, green, blue;

    public PicSave() {
        try {
            f = null;
            int counter = -1;
            String fosStr = "D:\\Temp\\FracPic";
            do {
                counter++;
                String number = "";

                if (counter < 1000) {
                    number = "0" + number;
                }
                if (counter < 100) {
                    number = "0" + number;
                }
                if (counter < 10) {
                    number = "0" + number;
                }
                number = number + String.valueOf(counter);
                f = new File(fosStr + number + ".bmp");
            } while (f.exists());

            fos = new FileOutputStream(f);
            dos = new DataOutputStream(fos);
        } catch (FileNotFoundException fnf) {
            System.err.println("Fehler beim Speichern: " + fnf);
        }
    }

    public void savePicture(int[][][] picture) {
        try {
            dos.writeBytes("BM");	//Kennung fuer bmp-Datei (2 Bytes)
            dos.writeByte(54);		//Dateigroesse (4 Bytes)
            dos.writeByte(236);
            dos.writeByte(94);
            dos.writeByte(0);
            dos.writeChar(0);		//reserviete Bytes
            dos.writeChar(0);		//muessen 0 sein
            dos.writeByte(54);		//Offset der Bilddaten (4 Bytes)
            dos.writeByte(0);
            dos.writeChar(0);
            dos.writeByte(40);		//Groesse der BITMAPINFOHEADER structure
            dos.writeByte(0);		//muss 40 sein (4 Bytes)
            dos.writeChar(0);
            dos.writeByte(128);		//Bildbreite in Pixels (4 Bytes)
            dos.writeByte(7);		//hier: 1920
            dos.writeChar(0);
            dos.writeByte(56);		//Bildhoehe in Pixels (4 Bytes)
            dos.writeByte(4);		//hier: 1080
            dos.writeChar(0);
            dos.writeByte(1);		//Anzahl Ebenen (2 Bytes)
            dos.writeByte(0);		//immer 1
            dos.writeByte(24);		//Anzahl Bits proPixel (2 Bytes)
            dos.writeByte(0);		//hier 24
            dos.writeChar(0);		//Komprimierungs-Modus (4 Bytes)
            dos.writeChar(0);		//keine Komprimierung
            dos.writeByte(0);		//Groesse Bilddaten (4 Bytes)
            dos.writeByte(236);
            dos.writeByte(94);
            dos.writeByte(0);
            dos.writeLong(0);
            dos.writeLong(0);
        } catch (IOException ioe1) {
            System.err.println("Fehler beim Speichern: " + ioe1);
        }
        for (int j = 1079; j >= 0; j--) {
            for (int i = 0; i < 1920; i++) {

                try {
                    dos.writeByte(picture[i][j][2]);
                    dos.writeByte(picture[i][j][1]);
                    dos.writeByte(picture[i][j][0]);
                } catch (IOException ioe2) {
                    System.err.println("Fehler beim Speichern: " + ioe2);
                }
            }
        }
        try {
            dos.close();
            fos.close();
        } catch (IOException ioe3) {
            System.err.println("Fehler beim Speichern: " + ioe3);
        }
    }
}
