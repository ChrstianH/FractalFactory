import java.awt.*;
import java.awt.event.*;

public class FractalFactoryView {

	FractalFactoryController ffc;
	Canvas canvas;
	int[][][] picture = new int[1920][1080][3];
	int[][] pictureRaw = new int[1920][1080];
	TextField t_zeit;
	int colorSchema = 1;
	Point point1, point2;
	Frame myFrame = new Frame();
	
	public FractalFactoryView() {
		this.ffc = new FractalFactoryController(this);
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		myFrame.setTitle("Fractal Factory HD");
		myFrame.setLocation(231,151);
		myFrame.setLayout(new FlowLayout());
		
		GridBagLayout gbl = new GridBagLayout();
		p2.setLayout(gbl);
		
		Label l_zeit = new Label("Zeit",Label.LEFT);
		this.t_zeit = new TextField("00:00:00.000",15);
		this.t_zeit.setFont(new Font("sans-serif",0,14));
		
		addComponent(p2, gbl, l_zeit, 0,0,1,1,0.5,0.5,
				GridBagConstraints.CENTER, new Insets(5,5,5,5),5,5);
		addComponent(p2, gbl, this.t_zeit, 1,0,1,1,0.5,0.5,
				GridBagConstraints.CENTER, new Insets(5,5,5,5),5,5);
		
		
		MenuBar myMenuBar = new MenuBar();
		myFrame.setMenuBar(myMenuBar);
		Menu fileMenu = new Menu("Datei");
		myMenuBar.add(fileMenu);
		MenuItem EndeItem = new MenuItem("Beenden");
		EndeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(EndeItem);
		Menu BildMenu = new Menu("Bild");
		myMenuBar.add(BildMenu);
		MenuItem NeuItem = new MenuItem("Neu");
		Menu FarbeItem = new Menu("Farbe");
		MenuItem AusschnittItem = new MenuItem("Ausschnitt");
		BildMenu.add(NeuItem);
		BildMenu.add(FarbeItem);
		BildMenu.add(AusschnittItem);
		myFrame.setVisible(true);
		
		MenuItem Farbe1Item = new MenuItem("750 Farben");
		MenuItem Farbe2Item = new MenuItem("1000 Farben Modus");
		MenuItem Farbe3Item = new MenuItem("2000 Farben Modus");
		
		FarbeItem.add(Farbe1Item);
		FarbeItem.add(Farbe2Item);
		FarbeItem.add(Farbe3Item);

		canvas = new Canvas();
		canvas.setLocation(0, 10);
		canvas.setSize(960, 540);
		canvas.setBackground(new Color(0,0,0));

		Farbe1Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorSchema = 1;
				paint(canvas.getGraphics());
			}
		});
		Farbe2Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorSchema = 2;
				paint(canvas.getGraphics());
			}
		});
		Farbe3Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorSchema = 3;
				paint(canvas.getGraphics());
			}
		});
		NeuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewDialog newDialog = new NewDialog(myFrame,ffc);
				newDialog.setVisible(true);
				pictureRaw = ffc.newPic(ffc.getxMin(), ffc.getxMax(), ffc.getyMin(), ffc.getyMax(), ffc.getMaxIter());
				myFrame.setTitle("Fractal Factory HD - Bild fertig");
				paint(canvas.getGraphics());
				PicSave ps = new PicSave();
				ps.savePicture(picture);
			}
		});
		
		canvas.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				point2 = e.getPoint();
				System.out.println(point2.x+","+point2.y);
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				point1 = e.getPoint();
				System.out.println(point1.x+","+point1.y);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				point2 = e.getPoint();
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		myFrame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		p1.setSize(960, 540);
		p2.setSize(200, 540);
		p1.add(canvas);
		myFrame.setResizable(false);
		myFrame.setBackground(new Color(192,192,192));
		p1.setBackground(new Color(192,192,192));
		p2.setBackground(new Color(192,192,192));
		myFrame.add(p1);
		myFrame.add(p2);

           	myFrame.setSize(1210, 620);
		myFrame.addWindowListener(new TestWindowListener());

	}
	
	public void paint(Graphics g){
		picture = ffc.createColorTable(pictureRaw);
		for(int i=0;i<1920;i+=2) {
			for(int j=0;j<1080;j+=2) {
				g.setColor(new Color(picture[i][j][0],picture[i][j][1],picture[i][j][2]));
				g.drawLine(i/2, j/2, i/2, j/2);
			}
		}
	}
	
	public TextField getTimeField(){
		return t_zeit;
	}
	public Frame getMyFrame(){
		return myFrame;
	}
	public void setColorSchema(int colorSchema){
		this.colorSchema = colorSchema;
	}
	public int getColorSchema(){
		return colorSchema;
	}
	
	public static void addComponent( Container cont,
            GridBagLayout gbl,
            Component c,
            int x, int y,
            int width, int height,
            double weightx, double weighty,
            int anchor, Insets insets,
            int ipadx, int ipady){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = width; gbc.gridheight = height;
		gbc.weightx = weightx; gbc.weighty = weighty;
		gbc.anchor = anchor;
		gbc.insets = insets; 
		gbc.ipadx = ipadx; gbc.ipady = ipady;
		gbl.setConstraints( c, gbc );
		cont.add( c );
	}
}

class TestWindowListener extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		e.getWindow().dispose(); // Fenster "killen"
		System.exit(0); // VM "killen"
	}
}
