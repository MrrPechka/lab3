package bsu.rfect.java.lab3;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GornerTableCellRenderer implements TableCellRenderer{
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private String needle = null;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();
    private boolean whichSearch = false;

    public GornerTableCellRenderer() {
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
    }

    public void setNeedle(String needle){ this.whichSearch = whichSearch; }
    public void setWhichSearch(boolean whichSearch) {this.whichSearch = whichSearch; }

    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column){
        String formattedDouble = formatter.format(value);

        if(Double.parseDouble(formattedDouble) < 0.){
            label.setText(formattedDouble);
            panel.add(label);
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        }

        if(Double.parseDouble(formattedDouble) == 0.){
            label.setText(formattedDouble);
            panel.add(label);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        }

        if(Double.parseDouble(formattedDouble) > 0.){
            label.setText(formattedDouble);
            panel.add(label);
            panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        }

        if(!this.whichSearch){
            if(needle != null && needle.equals(formattedDouble))
                panel.setBackground(Color.RED);
            else
                panel.setBackground(Color.WHITE);
        }

        if(this.whichSearch){
            if(needle != null){
                Double dPlus = Double.parseDouble(needle) + 0.1;
                Double dMinus = Double.parseDouble(needle) - 0.1;
                String plus = formatter.format(dPlus);
                String minus = formatter.format(dMinus);
                if(plus.equals(formattedDouble) || minus.equals(formattedDouble))
                    panel.setBackground(Color.RED);
                else
                    panel.setBackground(Color.WHITE);
            }
        }
        return panel;
    }
}
