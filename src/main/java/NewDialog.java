import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class NewDialog extends Dialog {
	
	public NewDialog(final Frame myFrame, final FractalFactoryController ffc){
		super(null,ModalityType.APPLICATION_MODAL);
		
		final GridLayout gl = new GridLayout(7,2,10,10);
		this.setSize(310, 230);
		this.setLocationRelativeTo(null);
		this.setLayout(gl);
		final TextField t_xMin = new TextField(String.valueOf(ffc.getxMin()));
		final TextField t_xMax = new TextField(String.valueOf(ffc.getxMax()));
		final TextField t_yMin = new TextField(String.valueOf(ffc.getyMin()));
		final TextField t_yMax = new TextField(String.valueOf(ffc.getyMax()));
		final TextField t_iter = new TextField(String.valueOf(ffc.getMaxIter()));
		final Label l_xMin = new Label("xMin");
		final Label l_xMax = new Label("xMax");
		final Label l_yMin = new Label("yMin");
		final Label l_yMax = new Label("yMax");
		final Label l_iter = new Label("Iterationen");
		final Button confirm = new Button("OK");

		final CheckboxGroup colors = new CheckboxGroup();
		Checkbox color1 = new Checkbox("750 Farben", colors, true);
		this.add(color1);
		Checkbox color2 = new Checkbox("1000 Farben", colors, false);
		this.add(color2);
		Checkbox color3 = new Checkbox("2000 Farben", colors, false);
		this.add(color3);
		
		l_xMin.setSize(100,25);
		l_xMax.setSize(100,25);
		l_yMin.setSize(100,25);
		l_yMax.setSize(100,25);
		l_iter.setSize(100,25);
		t_xMin.setSize(200,25);
		t_xMax.setSize(200,25);
		t_yMin.setSize(200,25);
		t_yMax.setSize(200,25);
		t_iter.setSize(200,25);
		confirm.setSize(100,25);

		l_xMin.setLocation(10,10);
		l_xMax.setLocation(10,30);
		l_yMin.setLocation(10,50);
		l_yMax.setLocation(10,70);
		l_iter.setLocation(10,90);
		t_xMin.setLocation(110,10);
		t_xMax.setLocation(110,30);
		t_yMin.setLocation(110,50);
		t_yMax.setLocation(110,70);
		t_iter.setLocation(110,90);
		color1.setLocation(70, 110);
		color2.setLocation(90, 110);
		color3.setLocation(90, 130);
		confirm.setLocation(70,150);

		l_xMin.setFont(new Font("SansSerif",Font.PLAIN,12));
		l_xMax.setFont(new Font("SansSerif",Font.PLAIN,12));
		l_yMin.setFont(new Font("SansSerif",Font.PLAIN,12));
		l_yMax.setFont(new Font("SansSerif",Font.PLAIN,12));
		l_iter.setFont(new Font("SansSerif",Font.PLAIN,12));
		t_xMin.setFont(new Font("SansSerif",Font.PLAIN,12));
		t_xMax.setFont(new Font("SansSerif",Font.PLAIN,12));
		t_yMin.setFont(new Font("SansSerif",Font.PLAIN,12));
		t_yMax.setFont(new Font("SansSerif",Font.PLAIN,12));
		t_iter.setFont(new Font("SansSerif",Font.PLAIN,12));
		confirm.setFont(new Font("SansSerif",Font.PLAIN,12));
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
            dispose();
            }
        });
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if(colors.getSelectedCheckbox().getLabel().equals("750 Farben")){
					ffc.getFfv().setColorSchema(1);
				}
				if(colors.getSelectedCheckbox().getLabel().equals("1000 Farben")){
					ffc.getFfv().setColorSchema(2);
				}
				if(colors.getSelectedCheckbox().getLabel().equals("2000 Farben")){
					ffc.getFfv().setColorSchema(3);
				}
				ffc.setxMin(Double.valueOf(t_xMin.getText().replace(",", ".")));
				ffc.setxMax(Double.valueOf(t_xMax.getText().replace(",", ".")));
				ffc.setyMin(Double.valueOf(t_yMin.getText().replace(",", ".")));
				ffc.setyMax(Double.valueOf(t_yMax.getText().replace(",", ".")));
				ffc.setMaxIter(Integer.valueOf(t_iter.getText()));
			}
		});
		this.add(l_xMin);
		this.add(t_xMin);
		this.add(l_xMax);
		this.add(t_xMax);
		this.add(l_yMin);
		this.add(t_yMin);
		this.add(l_yMax);
		this.add(t_yMax);
		this.add(l_iter);
		this.add(t_iter);
		this.add(color1);
		this.add(color2);
		this.add(color3);
		this.add(confirm);
	}
}
