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

    }
}
