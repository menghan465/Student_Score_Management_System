import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
public class WindowTyped extends JFrame {
    int i=0;
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    public boolean check(String username, String password) {
        boolean isValid = false;
        String query = "SELECT * FROM admin WHERE user = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (storedPassword.equals(password)) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }
    private static final String URL = "jdbc:mysql://localhost:3306/class?useSSL=false&serverTimezone=GMT";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private TextField usernameField;
    private TextField passwordField;
    int sleepTime;
    JButton enter = new JButton("登录"),
            exit = new JButton("退出");
    JTextField admin_user = new JTextField(20); // 创建一个文本框，宽度为20个字符
    JTextField admin_password = new JTextField(20); // 创建一个文本框，宽度为20个字符
    public WindowTyped() {
        JLabel usernameLabel = new JLabel("用户名：");
        JLabel passwordLabel = new JLabel("密码：");
        usernameField = new TextField(20);
        passwordField = new TextField(20);
        passwordField.setEchoChar('*'); // 密码字段显示为星号
        // 创建面板并设置布局
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        // 添加组件到面板
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new Label()); // 占位符
        // 添加面板到窗口
        add(panel, BorderLayout.CENTER);
        // 添加管理员登录部分
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(2, 2));
        // 添加按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(enter);
        buttonPanel.add(exit);
        // 添加到窗口
        add(adminPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        // 设置窗口大小
        setSize(400, 150);
        // 设置窗口关闭行为
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加按钮点击事件监听器
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if (check(username, password)) {
                    System.out.println("登录成功");
                    Score scoreWindow = new Score();
                    scoreWindow.setTitle("学生成绩管理系统后台");
                    scoreWindow.setVisible(true);
                    dispose();
                } else {
                    System.out.println("用户名或密码错误");
                    if((i++)==0) {
                        JLabel wrongLabel = new JLabel("用户名或密码错误");
                        // 设置 wrongLabel 的位置
                        wrongLabel.setBounds(10, 10, 200, 30);
                        panel.add(wrongLabel);
                        panel.revalidate(); // 重新验证布局
                        panel.repaint(); // 重绘面板
                    }
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        // 设置窗口可见
        setVisible(true);
    }
    public void setSleepTime(int n) {
        sleepTime = n;
    }
}