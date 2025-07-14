import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OnlineExaminationSystem extends JFrame implements ActionListener {
    CardLayout cardLayout;
    JPanel cards;

    JTextField userField;
    JPasswordField passField;
    JLabel loginStatus;

    String[] questions = {
        "1. What is the capital of India?",
        "2. Which planet is known as the Red Planet?",
        "3. What is 5 + 3?"
    };
    String[][] options = {
        {"Mumbai", "Delhi", "Chennai", "Kolkata"},
        {"Venus", "Earth", "Mars", "Jupiter"},
        {"5", "7", "8", "9"}
    };
    int[] answers = {1, 2, 2};

    int currentQ = 0;
    int score = 0;
    ButtonGroup group;
    JRadioButton[] radios;
    JLabel questionLabel;

    public OnlineExaminationSystem() {
        setTitle("Online Examination");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        cards.add(loginPanel(), "Login");
        cards.add(examPanel(), "Exam");
        cards.add(resultPanel(), "Result");

        add(cards);
        cardLayout.show(cards, "Login");
        setVisible(true);
    }

    // ✅ Custom JPanel with background image
    class BackgroundPanel extends JPanel {
        Image bgImage;

        public BackgroundPanel(String path) {
            bgImage = new ImageIcon(path).getImage();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Login Panel with background
    JPanel loginPanel() {
        BackgroundPanel bg = new BackgroundPanel("exam_bg.jpg");
        bg.setLayout(new GridBagLayout());

        JPanel box = new JPanel(new GridLayout(7, 1, 10, 10));
        box.setBackground(new Color(255, 255, 255, 200)); // Slight transparency
        box.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Welcome to Online Exam", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        loginBtn.setPreferredSize(new Dimension(90, 25));
        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals("user") && pass.equals("pass")) {
                cardLayout.show(cards, "Exam");
            } else {
                loginStatus.setText("❌ Invalid credentials");
                loginStatus.setForeground(Color.RED);
            }
        });

        loginStatus = new JLabel("", SwingConstants.CENTER);
        loginStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        box.add(title);
        box.add(userLabel);
        box.add(userField);
        box.add(passLabel);
        box.add(passField);
        box.add(loginBtn);
        box.add(loginStatus);

        bg.add(box);
        return bg;
    }

    JPanel examPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        panel.setBackground(new Color(255, 255, 240));

        questionLabel = new JLabel("", SwingConstants.LEFT);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBackground(new Color(255, 255, 240));
        radios = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            radios[i] = new JRadioButton();
            radios[i].setFont(new Font("Segoe UI", Font.PLAIN, 15));
            radios[i].setBackground(new Color(255, 255, 240));
            group.add(radios[i]);
            optionsPanel.add(radios[i]);
        }

        panel.add(optionsPanel, BorderLayout.CENTER);

        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nextBtn.setPreferredSize(new Dimension(90, 25));
        nextBtn.addActionListener(this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 255, 240));
        bottomPanel.add(nextBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        loadQuestion();

        return panel;
    }

    JPanel resultPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(230, 255, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        JLabel result = new JLabel("🎉 Your Score: " + score + " / " + questions.length, SwingConstants.CENTER);
        result.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(result, BorderLayout.CENTER);

        JButton restartBtn = new JButton("Restart");
        restartBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        restartBtn.setPreferredSize(new Dimension(90, 25));
        restartBtn.addActionListener(e -> {
            currentQ = 0;
            score = 0;
            cardLayout.show(cards, "Login");
        });

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(230, 255, 240));
        btnPanel.add(restartBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    void loadQuestion() {
        group.clearSelection();
        questionLabel.setText(questions[currentQ]);
        for (int i = 0; i < 4; i++) {
            radios[i].setText(options[currentQ][i]);
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 4; i++) {
            if (radios[i].isSelected()) {
                if (i == answers[currentQ]) score++;
                break;
            }
        }
        currentQ++;
        if (currentQ < questions.length) {
            loadQuestion();
        } else {
            cards.add(resultPanel(), "Result");
            cardLayout.show(cards, "Result");
        }
    }

    public static void main(String[] args) {
        new OnlineExaminationSystem();
    }
}
