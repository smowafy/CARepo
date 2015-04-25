package Mips;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Components.BitsConverter;
import Memory.RegisterFile;
import Parser.Parser;

public class GUI extends JFrame implements ActionListener {
	JLabel zero, at, v0, v1, a0, a1, a2, a3, t0, t1, t2, t3, t4, t5, t6, t7,
			s0, s1, s2, s3, s4, s5, s6, s7, t8, t9, k0, k1, gp, fp, sp, ra,
			startAdr;
	JTextField tzero, tat, tv0, tv1, ta0, ta1, ta2, ta3, tt0, tt1, tt2, tt3,
			tt4, tt5, tt6, tt7, ts0, ts1, ts2, ts3, ts4, ts5, ts6, ts7, tt8,
			tt9, tk0, tk1, tgp, tfp, tsp, tra, tstartAdr;
	JTextArea program;
	JButton execute;
	RegisterFile regFile;
	BitsConverter converter;
	ArrayList<String> prg = new ArrayList<String>();
	int address;
	Parser parser;

	public GUI() {
		regFile = new RegisterFile();
		converter = new BitsConverter();
		parser = new Parser();
		setTitle("MIPS Simulator");
		Container content = getContentPane();
		setBounds(280, 50, 800, 650);
		setLayout(null);
		this.setBackground(Color.GRAY);
		zero = new JLabel("$zero");
		zero.setBounds(10, 10, 70, 20);
		tzero = new JTextField("0");
		tzero.enable(false);
		tzero.setBounds(52, 10, 30, 20);
		tzero.addActionListener(this);
		content.add(zero);
		content.add(tzero);

		at = new JLabel("$at");
		at.setBounds(10, 40, 70, 20);
		tat = new JTextField("0");
		tat.setBounds(52, 40, 30, 20);
		tat.addActionListener(this);
		content.add(at);
		content.add(tat);

		v0 = new JLabel("$v0");
		v0.setBounds(10, 70, 70, 20);
		tv0 = new JTextField("0");
		tv0.setBounds(52, 70, 30, 20);
		tv0.addActionListener(this);
		content.add(v0);
		content.add(tv0);

		v1 = new JLabel("$v1");
		v1.setBounds(10, 100, 70, 20);
		tv1 = new JTextField("0");
		tv1.setBounds(52, 100, 30, 20);
		tv1.addActionListener(this);
		content.add(v1);
		content.add(tv1);

		a0 = new JLabel("$a0");
		a0.setBounds(10, 130, 70, 20);
		ta0 = new JTextField("0");
		ta0.setBounds(52, 130, 30, 20);
		ta0.addActionListener(this);
		content.add(a0);
		content.add(ta0);

		a1 = new JLabel("$a1");
		a1.setBounds(10, 160, 70, 20);
		ta1 = new JTextField("0");
		ta1.setBounds(52, 160, 30, 20);
		ta1.addActionListener(this);
		content.add(a1);
		content.add(ta1);
		// setVisible(true);

		a2 = new JLabel("$a2");
		a2.setBounds(10, 190, 70, 20);
		ta2 = new JTextField("0");
		ta2.setBounds(52, 190, 30, 20);
		ta2.addActionListener(this);
		content.add(a2);
		content.add(ta2);

		a3 = new JLabel("$a3");
		a3.setBounds(10, 220, 70, 20);
		ta3 = new JTextField("0");
		ta3.setBounds(52, 220, 30, 20);
		ta3.addActionListener(this);
		content.add(a3);
		content.add(ta3);
		// setVisible(true);

		t0 = new JLabel("$t0");
		t0.setBounds(10, 250, 70, 20);
		tt0 = new JTextField("0");
		tt0.setBounds(52, 250, 30, 20);
		tt0.addActionListener(this);
		content.add(t0);
		content.add(tt0);

		t1 = new JLabel("$t1");
		t1.setBounds(10, 280, 70, 20);
		tt1 = new JTextField("0");
		tt1.setBounds(52, 280, 30, 20);
		tt1.addActionListener(this);
		content.add(t1);
		content.add(tt1);

		t2 = new JLabel("$t2");
		t2.setBounds(10, 310, 70, 20);
		tt2 = new JTextField("0");
		tt2.setBounds(52, 310, 30, 20);
		tt2.addActionListener(this);
		content.add(t2);
		content.add(tt2);

		t3 = new JLabel("$t3");
		t3.setBounds(10, 340, 70, 20);
		tt3 = new JTextField("0");
		tt3.setBounds(52, 340, 30, 20);
		tt3.addActionListener(this);
		content.add(t3);
		content.add(tt3);

		t4 = new JLabel("$t4");
		t4.setBounds(10, 370, 70, 20);
		tt4 = new JTextField("0");
		tt4.setBounds(52, 370, 30, 20);
		tt4.addActionListener(this);
		content.add(t4);
		content.add(tt4);

		t5 = new JLabel("$t5");
		t5.setBounds(10, 400, 70, 20);
		tt5 = new JTextField("0");
		tt5.setBounds(52, 400, 30, 20);
		tt5.addActionListener(this);
		content.add(t5);
		content.add(tt5);

		t6 = new JLabel("$t6");
		t6.setBounds(10, 430, 70, 20);
		tt6 = new JTextField("0");
		tt6.setBounds(52, 430, 30, 20);
		tt6.addActionListener(this);
		content.add(t6);
		content.add(tt6);

		t7 = new JLabel("$t7");
		t7.setBounds(10, 460, 70, 20);
		tt7 = new JTextField("0");
		tt7.setBounds(52, 460, 30, 20);
		tt7.addActionListener(this);
		content.add(t7);
		content.add(tt7);

		t8 = new JLabel("$t8");
		t8.setBounds(10, 490, 70, 20);
		tt8 = new JTextField("0");
		tt8.setBounds(52, 490, 30, 20);
		tt8.addActionListener(this);
		content.add(t8);
		content.add(tt8);

		t9 = new JLabel("$t9");
		t9.setBounds(10, 520, 70, 20);
		tt9 = new JTextField("0");
		tt9.setBounds(52, 520, 30, 20);
		tt9.addActionListener(this);
		content.add(t9);
		content.add(tt9);

		s0 = new JLabel("$s0");
		s0.setBounds(100, 10, 70, 20);
		ts0 = new JTextField("0");
		ts0.setBounds(140, 10, 30, 20);
		ts0.addActionListener(this);
		content.add(s0);
		content.add(ts0);

		s1 = new JLabel("$s1");
		s1.setBounds(100, 40, 70, 20);
		ts1 = new JTextField("0");
		ts1.setBounds(140, 40, 30, 20);
		ts1.addActionListener(this);
		content.add(s1);
		content.add(ts1);

		s2 = new JLabel("$s2");
		s2.setBounds(100, 70, 70, 20);
		ts2 = new JTextField("0");
		ts2.setBounds(140, 70, 30, 20);
		ts2.addActionListener(this);
		content.add(s2);
		content.add(ts2);

		s3 = new JLabel("$s3");
		s3.setBounds(100, 100, 70, 20);
		ts3 = new JTextField("0");
		ts3.setBounds(140, 100, 30, 20);
		ts3.addActionListener(this);
		content.add(s3);
		content.add(ts3);

		s4 = new JLabel("$s4");
		s4.setBounds(100, 130, 70, 20);
		ts4 = new JTextField("0");
		ts4.setBounds(140, 130, 30, 20);
		ts4.addActionListener(this);
		content.add(s4);
		content.add(ts4);

		s5 = new JLabel("$s5");
		s5.setBounds(100, 160, 70, 20);
		ts5 = new JTextField("0");
		ts5.setBounds(140, 160, 30, 20);
		ts5.addActionListener(this);
		content.add(s5);
		content.add(ts5);

		s6 = new JLabel("$s6");
		s6.setBounds(100, 190, 70, 20);
		ts6 = new JTextField("0");
		ts6.setBounds(140, 190, 30, 20);
		ts6.addActionListener(this);
		content.add(s6);
		content.add(ts6);

		s7 = new JLabel("$s7");
		s7.setBounds(100, 220, 70, 20);
		ts7 = new JTextField("0");
		ts7.setBounds(140, 220, 30, 20);
		ts7.addActionListener(this);
		content.add(s7);
		content.add(ts7);

		k0 = new JLabel("$k0");
		k0.setBounds(100, 250, 70, 20);
		tk0 = new JTextField("0");
		tk0.setBounds(140, 250, 30, 20);
		tk0.addActionListener(this);
		content.add(k0);
		content.add(tk0);

		k1 = new JLabel("$k1");
		k1.setBounds(100, 280, 70, 20);
		tk1 = new JTextField("0");
		tk1.setBounds(140, 280, 30, 20);
		tk1.addActionListener(this);
		content.add(k1);
		content.add(tk1);

		gp = new JLabel("$gp");
		gp.setBounds(100, 310, 70, 20);
		tgp = new JTextField("0");
		tgp.setBounds(140, 310, 30, 20);
		tgp.addActionListener(this);
		content.add(gp);
		content.add(tgp);

		fp = new JLabel("$fp");
		fp.setBounds(100, 340, 70, 20);
		tfp = new JTextField("0");
		tfp.setBounds(140, 340, 30, 20);
		tfp.addActionListener(this);
		content.add(fp);
		content.add(tfp);

		sp = new JLabel("$sp");
		sp.setBounds(100, 370, 70, 20);
		tsp = new JTextField("0");
		tsp.setBounds(140, 370, 30, 20);
		tsp.addActionListener(this);
		content.add(sp);
		content.add(tsp);

		ra = new JLabel("$ra");
		ra.setBounds(100, 400, 70, 20);
		tra = new JTextField("0");
		tra.setBounds(140, 400, 30, 20);
		tra.addActionListener(this);
		content.add(ra);
		content.add(tra);

		startAdr = new JLabel("Start address: ");
		startAdr.setBounds(100, 430, 100, 50);
		tstartAdr = new JTextField("0");
		tstartAdr.setBounds(120, 470, 50, 50);
		tstartAdr.addActionListener(this);
		content.add(startAdr);
		content.add(tstartAdr);

		program = new JTextArea();
		program.setBounds(200, 10, 500, 500);
		//program.addActionListener(this);
		content.add(program);

		execute = new JButton("Run");
		execute.setBounds(600, 520, 100, 70);
		execute.addActionListener(this);
		content.add(execute);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == execute) {
			regFile.insertIntoRegister(0, 0);
			if (!tat.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tat.getText()), 1);
			if (!tv0.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tv0.getText()), 2);
			if (!tv1.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tv1.getText()), 3);
			if (!ta0.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ta0.getText()), 4);
			if (!ta1.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ta1.getText()), 5);
			if (!ta2.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ta2.getText()), 6);
			if (!ta3.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ta3.getText()), 7);
			if (!tt0.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt0.getText()), 8);
			if (!tt1.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt1.getText()), 9);
			if (!tt2.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt2.getText()), 10);
			if (!tt3.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt3.getText()), 11);
			if (!tt4.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt4.getText()), 12);
			if (!tt5.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt5.getText()), 13);
			if (!tt6.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt6.getText()), 14);
			if (!tt7.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt7.getText()), 15);
			if (!ts0.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ts0.getText()), 16);
			if (!ts1.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ts1.getText()), 17);
			if (!ts2.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ts2.getText()), 18);
			if (!ts3.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ts3.getText()), 19);
			if (!ts4.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ts4.getText()), 20);
			if (!ts5.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ts5.getText()), 21);
			if (!ts6.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ts6.getText()), 22);
			if (!ts7.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(ts7.getText()), 23);
			if (!tt8.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt8.getText()), 24);
			if (!tt9.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tt9.getText()), 25);
			if (!tk0.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tk0.getText()), 26);
			if (!tk1.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tk1.getText()), 27);
			if (!tgp.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tgp.getText()), 28);

			if (!tfp.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tfp.getText()), 29);

			if (!tsp.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tsp.getText()), 30);

			if (!tra.getText().equals(""))
				regFile.insertIntoRegister(Integer.parseInt(tra.getText()), 31);
			
			address = 0;
			if(!tstartAdr.equals("")) address = Integer.parseInt(tstartAdr.getText());
			
			
			for (String line : program.getText().split("\\n")) prg.add(line);
			
			/*System.out.println(prg.toString());
			int [] file = regFile.getFile();
			for(int i = 0; i< 31; i++)
			{
				System.out.print(file[i] + ",");
			}
			System.out.println();
			System.out.println("adr: " + address);*/
			new Mips(prg, address, regFile);
		}

	}

	public static void main(String[] args) {
		new GUI();
	}

}
