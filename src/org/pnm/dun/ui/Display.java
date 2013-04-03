package org.pnm.dun.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.pnm.dun.Environment;
import org.pnm.dun.model.PassiveType;
import org.pnm.dun.model.Unit;
import org.pnm.dun.model.Unit.Side;
import org.pnm.dun.util.Location;

public class Display extends JPanel {

	Environment environment;
	Logger log = Logger.getLogger(Display.class);
	//private static final String IMAGE_DIR = "C:\\Users\\Phil\\Downloads\\WMH_Vassal421\\";
	private static final String IMAGE_DIR = "C:\\Users\\Phil\\Desktop\\temp\\sprites";
	Unit selected;
	int squareSize = 55;

	public Display(final Environment environment) {
		this.environment = environment;
		setPreferredSize(new Dimension(1000, 800));
		setBackground(Color.LIGHT_GRAY);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clicked(environment, e);
			}

			private void clicked(final Environment environment, MouseEvent e) {
				log.debug("Clicked: "+e);
				int x = e.getX()/squareSize;
				int y = e.getY()/squareSize;
				System.out.println("Clicked: "+new Location(x, y));
				Unit u = environment.getUnitAt(x, y);
				Unit previous = selected;
				selected = u;
				if (selected != null) {
					log.debug(u);
					System.out.println(selected);
					System.out.println(environment.getAdjacentEnemies(selected));
				}
				if(selected != previous){
					repaint();
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
		g.setColor(Color.GRAY);
		drawSelected(g);
		for (Entry<Unit, Location> entry : environment.getUnitEntries()) {
			drawUnit(g, entry);
		}
	}

	private void drawUnit(Graphics g, Entry<Unit, Location> entry) {
		int drawX = entry.getValue().x*squareSize + squareSize/2;
		int drawY = entry.getValue().y*squareSize + squareSize/2;
		Unit unit = entry.getKey();
		if(unit.base == 3){
			drawX += squareSize/2;
			drawY += squareSize/2;
		}
		Image sprite = getImage(unit.imagePath);
		int w = sprite.getWidth(null);
		//g.drawImage(sprite, drawX - w / 2, drawY - w / 2, null);
		
		double rotationRequired = Math.toRadians(environment.getOrientation(unit));
		double locationX = w / 2;
		double locationY = w / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		g.drawImage(op.filter((BufferedImage)sprite, null), drawX- w / 2, drawY - w / 2, null);
	}

	private void drawGrid(Graphics g) {
		g.setColor(Color.GRAY);
		for(int y = 0 ; y < getHeight(); y+=squareSize){
			g.drawLine(0, y, getWidth(), y);
		}
		for(int x = 0 ; x < getWidth(); x+=squareSize){
			g.drawLine(x, 0, x, getHeight());
		}
	}

	private void drawSelected(Graphics g) {
		if (selected != null) {
			Image aura;
			Location loc = environment.getLocation(selected);
			int drawX = loc.x * squareSize+ squareSize/2;
			int drawY = loc.y* squareSize+ squareSize/2;
			if(selected.base == 3){
				drawX += squareSize/2;
				drawY += squareSize/2;
			}
			if(selected.hasPassive(PassiveType.AURA)){
				aura = getImage("aura/120mm_Aura_Yellow");
			}
			else{
				String color = selected.side == Side.MONSTER ? "Red" : "Green";
				String auraPath = "aura/"+selected.base + "_Aura_"+color;
				aura = getImage(auraPath);
			}
			int w = aura.getWidth(null);
			g.drawImage(aura, drawX - w / 2, drawY - w / 2, null);
		}
	}

	private Image getImage(String name) {
		Image aura = null;
		try {
			File input = new File(IMAGE_DIR, name+".png");
			//System.out.println(input.getAbsolutePath());
			//System.out.println(input.exists());
			aura = ImageIO.read(input);
		} catch (IOException e) {
			System.out.println(name);
			e.printStackTrace();
		}
		return aura;
	}

}
