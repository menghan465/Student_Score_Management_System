import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Delete {
    private JPanel panel;
    private JButton close, accept;
    private JTextField idField;
    private Connection connection;
    JTextField id;
    public Delete() {
        // 初始化UI组件
        panel = new JPanel(new GridLayout(2, 2)); // 2行2列的网格布局
        panel.add(new JLabel("ID:"));
        id = new JTextField(20);
        panel.add(id);

        accept = new JButton("确定");
        panel.add(accept);

        close = new JButton("关闭");
        panel.add(close);
        // 数据库连接信息
        final String URL = "jdbc:mysql://localhost:3306/class?useSSL=false&serverTimezone=GMT";
        final String USER = "root"; // 替换为你的数据库用户名
        final String PASSWORD = "123456"; // 替换为你的数据库密码
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立数据库连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        }
        // 添加按钮监听
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
                JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
                dialog.dispose();
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
    private void deleteRecord() {
        try {
            String deleteQuery = "DELETE FROM score WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, id.getText());
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted + " row(s) deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public JPanel getPanel() {
        return panel;
    }
    public static void main(String[] args) {
        new Delete();
    }
}
