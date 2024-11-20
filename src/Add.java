import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Add extends JFrame {
    private JPanel panel;
    JButton close, accept;
    JTextField name;
    JTextField id;
    JTextField score;
    JTextField clss;
    private static final String URL = "jdbc:mysql://localhost:3306/class?useSSL=false&serverTimezone=GMT";
    private static final String USER = "root"; // 替换为你的数据库用户名
    private static final String PASSWORD = "123456"; // 替换为你的数据库密码
    private Connection connection;
    public Add() {
        panel = new JPanel(new GridLayout(5, 2)); // 5行2列的网格布局

        panel.add(new JLabel("id:"));
        id = new JTextField(20);
        panel.add(id);

        panel.add(new JLabel("class:"));
        clss = new JTextField(20);
        panel.add(clss);

        panel.add(new JLabel("name:"));
        name = new JTextField(20);
        panel.add(name);

        panel.add(new JLabel("score:"));
        score = new JTextField(20);
        panel.add(score);

        accept = new JButton("确定");
        panel.add(accept);

        close = new JButton("关闭");
        panel.add(close);
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立数据库连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // 创建Statement对象
            Statement statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        }
        accept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String insertQuery = "INSERT INTO score (id, class, name, score) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, id.getText());
                    preparedStatement.setString(2, clss.getText());
                    preparedStatement.setString(3, name.getText());
                    preparedStatement.setString(4, score.getText());
                    int rowsInserted = preparedStatement.executeUpdate();
                    System.out.println(rowsInserted + " row(s) inserted.");
                    JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
                    dialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取当前的 JDialog 实例并调用 dispose() 方法
                JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
                dialog.dispose();
            }
        });
    }
    public JPanel getPanel() {
        return panel;
    }
}