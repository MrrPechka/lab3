package bsu.rfect.java.lab3;

import jdk.nashorn.internal.scripts.JO;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainFrame extends JFrame{

    private static final int WIDTH = 1000,
                             HEIGHT = 500;

    private Double[] coefficients;
    private JFileChooser fileChooser = null;

    private JMenuItem saveToTextMenuItem,
                      saveToGraphicsMenuItem,
                      commaSeparatedValues,
                      searchValueMenuItem,
                      searchRangeMenuAction;

    private JMenuItem informationItem;

    private JTextField textFieldFrom,
                       textFieldTo,
                       textFieldStep;

    private Box hBoxResult;
    private DecimalFormat formatter = new DecimalFormat("###.#####");
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    private GornerTableModel data;

    public MainFrame(Double[] coefficients){

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
