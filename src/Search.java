import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Search extends JFrame {
    private JPanel panel;
    private JButton close, accept;
    private Connection connection;
    private JTextArea textArea;
    JTextField id;
    public Search() {
        panel = new JPanel(new BorderLayout()); // 使用BorderLayout布局
        JPanel inputPanel = new JPanel(new GridLayout(2, 2)); // 2行2列的网格布局
        inputPanel.add(new JLabel("id:"));
        id = new JTextField(20);
        inputPanel.add(id);
        panel.add(inputPanel, BorderLayout.NORTH); // 将输入面板添加到顶部
        // 创建一个JTextArea组件
        textArea = new JTextArea();
        // 设置文本区域的字体
        textArea.setFont(new Font("Serif", Font.PLAIN, 14));
        // 设置文本区域是否自动换行
        textArea.setLineWrap(true);
        // 设置文本区域是否在单词边界处换行
        textArea.setWrapStyleWord(true);
        // 直接将JTextArea添加到JPanel中，而不是JScrollPane
        panel.add(textArea, BorderLayout.CENTER); // 将文本区域添加到中间
        accept = new JButton("确定");
        inputPanel.add(accept);
        close = new JButton("关闭");
        inputPanel.add(close);
        // 添加按钮监听
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRecord();
                JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
            }
        });
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
                dialog.dispose();
            }
        });
    }
    private void searchRecord() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String URL = "jdbc:mysql://localhost:3306/class?useSSL=false&serverTimezone=GMT";
        final String USER = "root"; // 替换为你的数据库用户名
        final String PASSWORD = "123456"; // 替换为你的数据库密码
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立数据库连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // 创建PreparedStatement对象
            String query = "SELECT * FROM score WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.getText());
            // 执行查询操作
            resultSet = preparedStatement.executeQuery();

            // 处理查询结果
            StringBuilder result = new StringBuilder();
            if (resultSet.next()) {
                do {
                    result.append("class: ").append(resultSet.getString("class")).append("      ");
                    result.append("name: ").append(resultSet.getString("name")).append("      ");
                    result.append("score: ").append(resultSet.getString("score")).append("      ");
                } while (resultSet.next());
            } else {
                result.append("该学号学生不存在");
            }
            textArea.setText(result.toString());
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public JPanel getPanel() {
        return panel;
    }
}