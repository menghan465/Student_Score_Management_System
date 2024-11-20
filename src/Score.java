import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
public class Score extends javax.swing.JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/class?useSSL=false&serverTimezone=GMT";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private Connection connection;
    private JTable table;
    private JScrollPane scrollPane;
    JPanel buttonPanel = new JPanel();
    JButton addButton = new JButton("插入"),
            deleteButton = new JButton("删除"),
            changeButton = new JButton("修改"),
            searchButton = new JButton("查找");
    public Score() {
        setLayout(new BorderLayout());
        setSize(1600, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加按钮到 buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(changeButton);
        buttonPanel.add(searchButton);
        // 将 buttonPanel 添加到窗口的 NORTH 位置
        add(buttonPanel, BorderLayout.NORTH);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 实例化 Add 对象
                Add add = new Add();
                // 创建一个 JDialog 并将 Add 对象添加到其中
                JDialog dialog = new JDialog();
                dialog.setTitle("插入");
                JPanel panel = add.getPanel();
                panel.setPreferredSize(new java.awt.Dimension(400, 150)); // 设置首选大小
                dialog.setContentPane(panel); // 使用 Add 类的内容面板
                dialog.pack();
                // 添加窗口监听器
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent we) {
                        // 在窗口关闭后调用目标函数
                        refreshScoreTable();
                    }
                });
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // 确保关闭后触发事件
                dialog.setVisible(true);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Delete delete = new Delete();
                // 创建一个 JDialog 并将 Add 对象添加到其中
                JDialog dialog = new JDialog();
                dialog.setTitle("删除");
                JPanel panel = delete.getPanel();
                panel.setPreferredSize(new java.awt.Dimension(400, 75)); // 设置首选大小
                dialog.setContentPane(panel); // 使用 Add 类的内容面板
                dialog.pack();
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent we) {
                        // 在窗口关闭后调用目标函数
                        refreshScoreTable();
                    }
                });
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // 确保关闭后触发事件
                dialog.setVisible(true);
            }
        });
        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Change change = new Change();
                // 创建一个 JDialog 并将 Add 对象添加到其中
                JDialog dialog = new JDialog();
                dialog.setTitle("修改");
                JPanel panel = change.getPanel();
                panel.setPreferredSize(new java.awt.Dimension(400, 150)); // 设置首选大小
                dialog.setContentPane(panel); // 使用 Add 类的内容面板
                dialog.pack();
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent we) {
                        // 在窗口关闭后调用目标函数
                        refreshScoreTable();
                    }
                });
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // 确保关闭后触发事件
                dialog.setVisible(true);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Search search = new Search();
                // 创建一个 JDialog 并将 Add 对象添加到其中
                JDialog dialog = new JDialog();
                dialog.setTitle("查找");
                JPanel panel = search.getPanel();
                panel.setPreferredSize(new java.awt.Dimension(400, 75)); // 设置首选大小
                dialog.setContentPane(panel); // 使用 Add 类的内容面板
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        // 连接数据库
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            displayScoreTable();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据库连接失败: " + e.getMessage());
        }
        setVisible(true);
    }
    public void displayScoreTable() {
        try {
            // 查询 score 表格
            String query = "SELECT * FROM score";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // 获取列名
            int columnCount = resultSet.getMetaData().getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = resultSet.getMetaData().getColumnName(i);
            }
            // 获取数据
            Object[][] data = new Object[100][columnCount]; // 假设最多100行数据
            int row = 0;
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    data[row][i - 1] = resultSet.getObject(i);
                }
                row++;
            }
            // 创建 JTable
            table = new JTable(data, columnNames);
            scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据查询失败: " + e.getMessage());
        }
    }
    public void refreshScoreTable() {
        // 移除旧的表格
        if (scrollPane != null) {
            remove(scrollPane);
        }
        // 重新显示表格
        displayScoreTable();
        revalidate();
        repaint();
    }
}