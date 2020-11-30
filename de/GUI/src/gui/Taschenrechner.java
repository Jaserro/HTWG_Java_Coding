package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Taschenrechner extends JFrame implements ActionListener, ItemListener, KeyListener {

    //div Panel
    private JPanel lEingabe;
    private JPanel lEinstellungen;
    private JPanel lOperationen;
    private JPanel lClear;

    //Eingabe
    private JLabel bOperand1;
    private JLabel bOperand2;
    private JLabel bAusgabe;
    private JTextField tOperand1;
    private JTextField tOperand2;
    private JTextField tAusgabe;

    //Einstellungen
    private JRadioButton rad;
    private JRadioButton deg;
    private JCheckBox mode;

    //Operationen
    private JButton sum;
    private JButton mul;
    private JButton dif;
    private JButton quo;
    private JButton sin;
    private JButton cos;
    private JButton pow;
    private JButton log;

    //Clear
    private JButton clear;

    private JPanel mainPanel;

    public Taschenrechner(){
        this.setTitle("Taschenrechner");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 300));

        //Eingabe
        lEingabe = new JPanel();
        lEingabe.setLayout(new GridLayout(3,2));
        lEingabe.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));

        bOperand1 = new JLabel("Operand x");
        tOperand1 = new JTextField(15);
        bOperand2 = new JLabel("Operand y");
        tOperand2 = new JTextField(15);
        bAusgabe = new JLabel("Resultat");
        tAusgabe = new JTextField(15);
        tAusgabe.setEditable(false);
        tAusgabe.setBackground(Color.WHITE);
        tOperand1.addKeyListener(this);
        tOperand2.addKeyListener(this);
        lEingabe.add(bOperand1);
        lEingabe.add(tOperand1);
        lEingabe.add(bOperand2);
        lEingabe.add(tOperand2);
        lEingabe.add(bAusgabe);
        lEingabe.add(tAusgabe);

        //Einstellungen
        lEinstellungen = new JPanel();
        lEinstellungen.setLayout(new FlowLayout(FlowLayout.CENTER));
        lEinstellungen.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));

        deg = new JRadioButton("Deg");
        deg.setSelected(true);
        rad = new JRadioButton("Rad");
        ButtonGroup mas = new ButtonGroup();
        mas.add(deg);
        mas.add(rad);
        mode = new JCheckBox("Helles Display");
        mode.setSelected(true);
        deg.addActionListener(this);
        rad.addActionListener(this);
        mode.addItemListener(this);
        lEinstellungen.add(deg);
        lEinstellungen.add(rad);
        lEinstellungen.add(mode);

        //Operationen
        lOperationen = new JPanel();
        GridLayout operationLayout = new GridLayout(2,4);
        operationLayout.setVgap(4);
        operationLayout.setHgap(8);
        lOperationen.setLayout(operationLayout);
        lOperationen.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));

        sum = new JButton("+");
        mul = new JButton("*");
        dif = new JButton("-");
        quo = new JButton("/");
        sin = new JButton("sin");
        cos = new JButton("cos");
        pow = new JButton("x^y");
        log = new JButton("log2");
        sum.addActionListener(this);
        mul.addActionListener(this);
        dif.addActionListener(this);
        quo.addActionListener(this);
        sin.addActionListener(this);
        cos.addActionListener(this);
        pow.addActionListener(this);
        log.addActionListener(this);
        lOperationen.add(sum);
        lOperationen.add(mul);
        lOperationen.add(dif);
        lOperationen.add(quo);
        lOperationen.add(sin);
        lOperationen.add(cos);
        lOperationen.add(pow);
        lOperationen.add(log);

        //Clear
        lClear = new JPanel();
        lClear.setLayout(new FlowLayout(FlowLayout.CENTER));
        lClear.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));

        clear = new JButton("Clear");
        clear.addActionListener(this);
        lClear.add(clear);

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        //Gruppen einfuegen
        mainPanel.add(lEingabe);
        mainPanel.add(lEinstellungen);
        mainPanel.add(lOperationen);
        mainPanel.add(lClear);

        setContentPane(mainPanel);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        boolean degmas = deg.isSelected();
        double o1;
        double o2;

        if (source == clear){
            clear();
        } else if (source == deg || source == rad){
            return;
        }
        else {
            double ausgabe = 0;
            try {
                 o1 = Double.parseDouble(tOperand1.getText());
            }
            catch (Exception f){
                tOperand1.setBackground(Color.RED);
                JOptionPane.showMessageDialog(null, "Falsche Eingabe in Operand x", "Das war so aber nicht geplant", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (source == sin){
                ausgabe = sin(o1, degmas);

            } else if (source == cos){
                ausgabe = cos(o1, degmas);
            } else if (source == log){
                ausgabe = log(o1);
            } else {
                try {
                    o2 = Double.parseDouble(tOperand2.getText());
                }
                catch (Exception f){
                    tOperand2.setBackground(Color.RED);
                    JOptionPane.showMessageDialog(null, "Falsche Eingabe in Operand y", "Das war so aber nicht geplant", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (source == sum){
                    ausgabe = sum(o1, o2);
                } else if (source == mul){
                    ausgabe = mul(o1, o2);
                } else if (source == dif){
                    ausgabe = dif(o1, o2);
                } else if (source == quo){
                    ausgabe = quo(o1, o2);
                } else if (source == pow){
                    ausgabe = pow(o1, o2);
                }
            }
           tAusgabe.setText(Double.toString(ausgabe));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED){
            darktheme();

        } else {
            lighttheme();
        }
    }

    public static void main(String[] args) {
        JFrame rechner = new Taschenrechner();
    }

    private double sum(double p1, double p2){
        return p1 + p2;
    }
    private double mul(double p1, double p2){
        return p1 * p2;
    }
    private double dif(double p1, double p2){
        return p1 - p2;
    }
    private double quo(double p1, double p2){
        if (p2 == 0){
            tOperand2.setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, "Durch 0 teilen ist auch keine Lösung", "Das war so aber nicht geplant", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }
        return p1 / p2;
    }
    private double sin(double p1, boolean degmas){
        tOperand2.setText("0");
        if (degmas){
            return Math.sin(Math.toRadians(p1));
        }
        return Math.sin(p1);
    }
    private double cos(double p1, boolean degmas){
        tOperand2.setText("0");
        if (degmas){
            return Math.cos(Math.toRadians(p1));
        }
        return Math.cos(p1);
    }
    private double pow(double p1, double p2){
        double temp = Math.pow(p1, p2);
        if (Double.isInfinite(temp)){
            JOptionPane.showMessageDialog(null, "It's over 9000", "Das war so aber nicht geplant", JOptionPane.INFORMATION_MESSAGE);
            return 9001;
        }
        return temp;
    }
    private double log(double p1){
        if (!(p1 > 0)){
            tOperand1.setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, "Mathe 1 nicht so deins? \n Operand muss größer 0 sein", "Das war so aber nicht geplant", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }
        tOperand2.setText("0");
        return Math.log(p1);
    }
    private void clear(){
        tOperand1.setText("0");
        tOperand2.setText("0");
        tAusgabe.setText("0");
        keyTyped(null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (tOperand1.getBackground() == Color.RED || tOperand2.getBackground() == Color.RED){
            if (mode.isSelected()){
                lighttheme();
            } else {
                darktheme();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    private void darktheme(){
        tOperand1.setForeground(Color.YELLOW);
        tOperand1.setBackground(Color.BLACK);
        tOperand2.setForeground(Color.YELLOW);
        tOperand2.setBackground(Color.BLACK);
        tAusgabe.setForeground(Color.YELLOW);
        tAusgabe.setBackground(Color.BLACK);
        mainPanel.setBackground(Color.DARK_GRAY);
        lEingabe.setBackground(Color.DARK_GRAY);
        lEinstellungen.setBackground(Color.DARK_GRAY);
        lOperationen.setBackground(Color.DARK_GRAY);
        lClear.setBackground(Color.DARK_GRAY);
        clear.setBackground(Color.GRAY);
        clear.setForeground(Color.WHITE);
        bOperand1.setForeground(Color.WHITE);
        bOperand2.setForeground(Color.WHITE);
        bAusgabe.setForeground(Color.WHITE);
        rad.setBackground(Color.GRAY);
        rad.setForeground(Color.WHITE);
        deg.setBackground(Color.GRAY);
        deg.setForeground(Color.WHITE);
        mode.setBackground(Color.GRAY);
        mode.setForeground(Color.WHITE);
        sum.setBackground(Color.GRAY);
        sum.setForeground(Color.WHITE);
        mul.setBackground(Color.GRAY);
        mul.setForeground(Color.WHITE);
        dif.setBackground(Color.GRAY);
        dif.setForeground(Color.WHITE);
        quo.setBackground(Color.GRAY);
        quo.setForeground(Color.WHITE);
        sin.setBackground(Color.GRAY);
        sin.setForeground(Color.WHITE);
        cos.setBackground(Color.GRAY);
        cos.setForeground(Color.WHITE);
        pow.setBackground(Color.GRAY);
        pow.setForeground(Color.WHITE);
        log.setBackground(Color.GRAY);
        log.setForeground(Color.WHITE);
    }
    private void lighttheme(){
        tOperand1.setForeground(Color.BLACK);
        tOperand1.setBackground(Color.WHITE);
        tOperand2.setForeground(Color.BLACK);
        tOperand2.setBackground(Color.WHITE);
        tAusgabe.setForeground(Color.BLACK);
        tAusgabe.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        lEingabe.setBackground(Color.WHITE);
        lEinstellungen.setBackground(Color.WHITE);
        lOperationen.setBackground(Color.WHITE);
        lClear.setBackground(Color.WHITE);
        Color defButton = new JButton().getBackground();
        clear.setBackground(defButton);
        clear.setForeground(Color.BLACK);
        bOperand1.setForeground(Color.BLACK);
        bOperand2.setForeground(Color.BLACK);
        bAusgabe.setForeground(Color.BLACK);
        rad.setBackground(Color.WHITE);
        rad.setForeground(Color.BLACK);
        deg.setBackground(Color.WHITE);
        deg.setForeground(Color.BLACK);
        mode.setBackground(Color.WHITE);
        mode.setForeground(Color.BLACK);
        sum.setBackground(defButton);
        sum.setForeground(Color.BLACK);
        mul.setBackground(defButton);
        mul.setForeground(Color.BLACK);
        dif.setBackground(defButton);
        dif.setForeground(Color.BLACK);
        quo.setBackground(defButton);
        quo.setForeground(Color.BLACK);
        sin.setBackground(defButton);
        sin.setForeground(Color.BLACK);
        cos.setBackground(defButton);
        cos.setForeground(Color.BLACK);
        pow.setBackground(defButton);
        pow.setForeground(Color.BLACK);
        log.setBackground(defButton);
        log.setForeground(Color.BLACK);
    }
}
