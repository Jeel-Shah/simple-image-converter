import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.*;

public class ImageConverter extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected JButton oneFile_Button = new JButton("Convert a single file");
	protected JButton mulFile_Button = new JButton("Convert multiple files");
	protected JButton convertButton = new JButton("Convert !");

	protected JTextField path_Field = new JTextField(20);

	private File inputFile;
	private File[] inputFiles;

	boolean oneFile = false;

	public ImageConverter() {
		super("Jeel\'s BMP to JPEG Converter");
		setSize(500,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());

		add(oneFile_Button);
		add(mulFile_Button);
		add(convertButton);

		add(path_Field);

		setVisible(true);

		oneFile_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);

				if(returnVal == JFileChooser.APPROVE_OPTION) {
					inputFile = chooser.getSelectedFile();
					path_Field.setText(inputFile.getAbsolutePath());
					oneFile = true;
				}

			}
		});

		mulFile_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);

				if(returnVal == JFileChooser.APPROVE_OPTION) {
					inputFiles = chooser.getSelectedFiles();
					oneFile = false;
					
				}
			}
		});
		
		convertButton.addActionListener(new convertListener());
		
	}

	class convertListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(oneFile) {
				try {
					Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
					
					ImageWriter writer = (ImageWriter)iter.next();
					ImageWriteParam iwp = writer.getDefaultWriteParam();
					
					iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
					iwp.setCompressionQuality(1);

					BufferedImage image = ImageIO.read(inputFile);

					File file = new File(inputFile.getAbsolutePath());
					FileImageOutputStream output = new FileImageOutputStream(file);
					writer.setOutput(output);

					IIOImage new_image = new IIOImage(image, null, null);

					writer.write(null, new_image, iwp);
					writer.dispose();
					
				}catch(Exception ex) {

				}
			}
		}
	}
}