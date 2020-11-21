package bsu.rfect.java.lab3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;

public class MainFrame extends JFrame{

    private static final int WIDTH = 1000,
                             HEIGHT = 500;

    private Double[] coefficients;
    private JFileChooser fileChooser = null;

    private JMenuItem saveToTextMenuItem,
                      saveToGraphicsMenuItem,
                      saveToCSVMenuItem,
                      searchValueMenuItem,
                      searchCloseValueMenuItem,
                      aboutTheProgramMenuItem;

    private JTextField textFieldFrom,
                       textFieldTo,
                       textFieldStep;

    private Box hBoxResult;
    private DecimalFormat formatter = new DecimalFormat("###.#####");
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    private GornerTableModel data;
    private ImageIO icon = null;

    public MainFrame(Double[] coefficients){
    super("Табулирование многочлена на отрезке двумя способами");

    this.coefficients = coefficients;
    setSize(WIDTH, HEIGHT);
    Toolkit kit = Toolkit.getDefaultToolkit();
    setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);

    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);
    JMenu tableMenu = new JMenu("Table");
    menuBar.add(tableMenu);
    JMenu referenceMenu = new JMenu("Reference");
    menuBar.add(referenceMenu);

        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser == null){
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("F"));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION){
                    saveToTextFile(fileChooser.getSelectedFile());
                }
            }
        };
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(false);

        Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser == null){
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("F"));
                }
                if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    saveToGraphicsFile(fileChooser.getSelectedFile());
                }
            }
        };
        saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        saveToGraphicsMenuItem.setEnabled(false);

        Action saveToCSVAction = new AbstractAction("Сохранение в CSV файл") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser == null){
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("F"));
                }
                if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    saveToCSVFile(fileChooser.getSelectedFile());
                }
            }
        };
        saveToCSVMenuItem =fileMenu.add(saveToCSVAction);
        saveToCSVMenuItem.setEnabled(false);

        Action searchValueAction = new AbstractAction("Поиск значения") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска", "Поиск значения", JOptionPane.QUESTION_MESSAGE);
                try{
                    renderer.setWhichSearch(false);
                    Double num = Double.parseDouble(value);
                    renderer.setNeedle(value);
                    getContentPane().repaint();
                } catch (NumberFormatException | NullPointerException ex){
                    JOptionPane.showMessageDialog(MainFrame.this,"Неправильный формат вещественного числа", "Неправильный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        searchCloseValueMenuItem = tableMenu.add(searchValueAction);
        searchCloseValueMenuItem.setEnabled(false);

        Action searchCloseValueAction = new AbstractAction("Найти близкое к простому") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите простое значение для поиска", "Искать близкое значение", JOptionPane.QUESTION_MESSAGE);
                try{
                    renderer.setWhichSearch(true);
                    Double num = Double.parseDouble(value);
                    renderer.setNeedle(value);
                    getContentPane().repaint();
                }catch(NumberFormatException | NullPointerException ex){
                    JOptionPane.showMessageDialog(MainFrame.this, "Неправильный формат вещественного числа", "Неправильный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        searchCloseValueMenuItem = tableMenu.add(searchCloseValueAction);
        searchCloseValueMenuItem.setEnabled(false);

        Action aboutTheProgramAction = new AbstractAction("О программе") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Image image = ImageIO.read(new File("bsu.rfect.java.lab3/photo.jpg"));
                    image = image.getScaledInstance(250,250, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);
                    JOptionPane.showMessageDialog(MainFrame.this, "Гук Анна\n8 группа", "О студенте", JOptionPane.INFORMATION_MESSAGE, icon);
                } catch(IOException ioException){
                    ioException.printStackTrace();
                }
            }
        };
        aboutTheProgramMenuItem = referenceMenu.add(aboutTheProgramAction);
        aboutTheProgramMenuItem.setEnabled(true);

        JLabel labelForFrom = new JLabel("X варьируется от:");
        textFieldFrom = new JTextField("0.0", 10);
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());
        JLabel labelForTo = new JLabel("to:");
        textFieldTo = new JTextField("4.0", 10);
        textFieldTo.setMaximumSize(textFieldFrom.getPreferredSize());
        JLabel labelForStep = new JLabel("с шагом:");
        textFieldStep = new JTextField("0.1", 10);
        textFieldStep.setMaximumSize(textFieldFrom.getPreferredSize());

        Box hBoxRange = Box.createHorizontalBox();
        hBoxRange.setBorder(BorderFactory.createBevelBorder(1));
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(labelForFrom);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(textFieldFrom);
        hBoxRange.add(Box.createVerticalGlue());
        hBoxRange.add(labelForTo);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(textFieldTo);
        hBoxRange.add(Box.createVerticalGlue());
        hBoxRange.add(labelForStep);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(textFieldStep);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.setPreferredSize(new Dimension(new Double(hBoxRange.getMaximumSize().getWidth()).intValue(), new Double(hBoxRange.getMinimumSize().getHeight()).intValue() * 2));
        hBoxRange.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        getContentPane().add(hBoxRange, BorderLayout.NORTH);

        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Double from = Double.parseDouble(textFieldFrom.getText()),
                           to = Double.parseDouble(textFieldTo.getText()),
                           step = Double.parseDouble(textFieldStep.getText());
                    data = new GornerTableModel(from, to, step, MainFrame.this.coefficients);
                    JTable table = new JTable(data);
                    table.setDefaultRenderer(Double.class,renderer);
                    table.setRowHeight(30);
                    hBoxResult.removeAll();
                    renderer.setNeedle(null);
                    hBoxResult.add(new JScrollPane(table));
                    getContentPane().validate();
                    saveToTextMenuItem.setEnabled(true);
                    saveToGraphicsMenuItem.setEnabled(true);
                    saveToCSVMenuItem.setEnabled(true);
                    searchValueMenuItem.setEnabled(true);
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(MainFrame.this, "Неправильный формат вещественного числа", "Неправильный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton buttonReset = new JButton("Очистить");
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldFrom.setText("0.0");
                textFieldTo.setText("4.0");
                textFieldStep.setText("0.1");
                hBoxResult.removeAll();
                hBoxResult.add(new JPanel());
                saveToTextMenuItem.setEnabled(false);
                saveToGraphicsMenuItem.setEnabled(false);
                saveToCSVMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
                searchCloseValueMenuItem.setEnabled(false);
                renderer.setNeedle(null);
                getContentPane().validate();
            }
        });

        Box hBoxButtons = Box.createHorizontalBox();
        hBoxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hBoxButtons.add(Box.createVerticalGlue());
        hBoxButtons.add(buttonCalc);
        hBoxButtons.add(Box.createHorizontalStrut(50));
        hBoxButtons.add(buttonReset);
        hBoxButtons.add(Box.createHorizontalGlue());
        hBoxButtons.setPreferredSize(new Dimension((new Double(hBoxButtons.getMaximumSize().getWidth()).intValue(), new Double(hBoxButtons.getMinimumSize().getHeight()).intValue() * 2));
        hBoxButtons.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        getContentPane().add(hBoxButtons,BorderLayout.SOUTH);
        hBoxResult = Box.createHorizontalBox();
        hBoxResult.add(new JPanel());
        hBoxResult.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        getContentPane().add(hBoxResult, BorderLayout.CENTER);
    }

    private  void saveToGraphicsFile(File selectedFile){
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
            for(int i = 0; i < data.getRowCount(); i++){
                for(int j = 0; j < data.getColumnCount(); j++)
                    out.writeDouble((Double)data.getValueAt(i, j));
            }
            out.close();
        } catch(IOException e){
            System.out.println("Файл не создан");
        }
    }

    private void saveToTextFile(File selectedFile){
        try{
            FileWriter writer = new FileWriter(selectedFile);
            for(int i = 0; i < data.getColumnCount(); i++){
                writer.write(data.getColumnName(i));
                writer.write("\t|\t");
            }
            writer.write("\n___________________________________________________________" +
                             "______________________________________________________________\n");
            for(int i = 0; i < data.getRowCount(); i++){
                for(int j = 0; j < data.getColumnCount(); j++){
                    writer.write(String.valueOf(formatter.format(data.getValueAt(i, j))));
                    int a = formatter.format(data.getValueAt(i, j)).toString().length();
                    for(int l = 1; l < (30 - a); l++)
                        writer.write(" ");
                }
                writer.write("\n");
            }
            writer.flush();
        } catch(IOException e){
            System.out.println("Файл не создан");
        }
    }



    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Невозможно табулировать полином, для которого не указан коэффициент!");
            System.exit(-1);
        }

        Double[] coefficients = new Double[args.length];
        int i = 0;
        try{
            for(String arg : args)
                coefficients[i++] = Double.parseDouble(arg);
        } catch (NumberFormatException e){
            System.out.println("Ошибка конвертирования строки '" + args[i] + "' в Double");
            System.exit(-2);
        }
        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
