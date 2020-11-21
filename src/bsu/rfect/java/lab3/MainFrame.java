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
