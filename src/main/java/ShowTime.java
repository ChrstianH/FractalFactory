
import java.awt.TextField;

public class ShowTime extends Thread {

    long startTime, estTime, endTime, currTime;
    TextField tf;

    public ShowTime(FractalFactoryView ffv) {
        startTime = System.nanoTime();
        tf = ffv.getTimeField();
    }

    public void run() {
        while (!this.isInterrupted()) {
            currTime = System.nanoTime();
            estTime = currTime - this.startTime;
            tf.setText(buildString((int) (estTime / 1e9 + 0.5)));
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                interrupt();
                endTime = System.nanoTime();
                estTime = endTime - this.startTime;
                tf.setText(buildString(estTime / 1e9));
            }
        }
    }

    public String buildString(double time) {
        String strTime = "";
        if (time / 3600 < 10) {
            strTime += "0";
        }
        strTime += (int) (time / 3600) + ":";

        time = time % 3600;

        if (time / 60 < 10) {
            strTime += "0";
        }
        strTime += (int) (time / 60) + ":";

        time = time % 60;

        if (time < 10) {
            strTime += "0";
        }
        strTime += (int) time + ".";
        
        time = (int) (time * 1000) % 1000;
        if (time < 100) {
            strTime += "0";
        }
        if (time < 10) {
            strTime += "0";
        }
        strTime += (int) (time + 0.5);
        if (strTime.length() == 0) {
            strTime = "00:00:00.000";
        }
        return strTime;
    }
}
