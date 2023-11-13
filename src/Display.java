import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
    public Display() { this.setBackground(new Color(220, 220, 220)); }

    @Override
    public Dimension getPreferredSize() { return super.getPreferredSize(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // TODO
        for (Store store : Common.getSimulationStores()) {
            store.draw((Graphics2D) g);
        }
        for (Customer customer : Common.getSimulationCustomers()) {
            customer.draw((Graphics2D) g);
        }

    }
}
