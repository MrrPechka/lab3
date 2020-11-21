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
