package bsu.rfect.java.lab3;

import javax.swing.table.AbstractTableModel;
import static java.lang.Math.*;

public class GornerTableModel extends AbstractTableModel{
    private Double[] coefficients;
    private Double from, to, step;
    private double result[] = new double[3];

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients){
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom(){return from;}
    public Double getTo() {return to;}
    public Double getStep(){return step;}

    public Class<?> getColumnClass(int columnIndex){return Double.class;}
    public int getColumnCount(){return 4;}

    public String getColumnName(int column){
        switch (column){
            case 0:
                return "Значение X";
            case 1:
                return "Значение полинома Горнера";
            case 2:
                return "Значение полинома, вычисленного с помощью Math.pow()";
            default:
                return "Разница между двумя значениями полинома";
        }
    }

    public int getRowCount(){
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int rowIndex, int columnIndex){
        double x = from + step * rowIndex;
        switch (columnIndex) {
            case 0:
                return x;
            case 1: {
                result[0] = 0.0;
                for (int i = 0; i < coefficients.length; i++) {
                    result[0] += Math.pow(x, coefficients.length - 1 - i) * coefficients[i];
                }
                return result[0];
            }
            case 2: {
                result[1] = 0.0;
                int p = coefficients.length - 1;
                for (int i = 0; i < coefficients.length; i++) {
                    result[1] += Math.pow(x, coefficients.length - 1 - i) * coefficients[p--];
                }
                return result[1];
            }
            default:
                return result[2] = result[1] - result[0];
        }
    }
}
