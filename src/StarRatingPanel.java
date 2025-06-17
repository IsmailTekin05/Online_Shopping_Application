import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StarRatingPanel extends JPanel {
    private final List<JToggleButton> stars = new ArrayList<>();
    private int selectedRating = 0;
    private final Color selectedColor = new Color(255, 193, 7); // altın sarısı
    private final Color deselectedColor = Color.LIGHT_GRAY;

    public StarRatingPanel(int maxStars) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        ActionListener listener = e -> {
            JToggleButton selectedButton = (JToggleButton) e.getSource();
            selectedRating = Integer.parseInt(selectedButton.getActionCommand());
            updateStars();
        };

        for (int i = 1; i <= maxStars; i++) {
            JToggleButton star = new JToggleButton("★");
            star.setActionCommand(String.valueOf(i));
            star.setFont(new Font("Arial", Font.BOLD, 24));
            star.setFocusPainted(false);
            star.setBorderPainted(false);
            star.setContentAreaFilled(false);
            star.addActionListener(listener);
            stars.add(star);
            add(star);
        }
        updateStars();
    }

    private void updateStars() {
        for (int i = 0; i < stars.size(); i++) {
            if (i < selectedRating) {
                stars.get(i).setForeground(selectedColor);
                stars.get(i).setSelected(true);
            } else {
                stars.get(i).setForeground(deselectedColor);
                stars.get(i).setSelected(false);
            }
        }
    }

    public int getSelectedRating() {
        return this.selectedRating;
    }
}