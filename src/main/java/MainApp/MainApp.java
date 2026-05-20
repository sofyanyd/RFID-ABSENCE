package MainApp;

import com.rmakmurjayaabadi.rfid.absence.GUI.DashboardPage;
import com.formdev.flatlaf.FlatLightLaf;

public class MainApp {
    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            System.err.println("Gagal mengaktifkan tema FlatLaf.");
        }

        java.awt.EventQueue.invokeLater(() -> {
            new DashboardPage().setVisible(true);
        });
    }
}
