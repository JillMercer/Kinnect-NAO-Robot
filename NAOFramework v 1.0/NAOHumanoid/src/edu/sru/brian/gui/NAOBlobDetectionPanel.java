package edu.sru.brian.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import edu.sru.brian.nao.BlobProperties;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;

import java.awt.GridLayout;

import javax.swing.BoxLayout;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class NAOBlobDetectionPanel extends JPanel {
	
	// Java Swing GUI elements
	private JButton btnShowColorPicker;
	private JSpinner spinnerThreshold;
	
	// Other data
	// Blob properties
	private BlobProperties blobProps;
	private JLabel lblMinimiumSize;
	private JFormattedTextField fmtdMinSize;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLabel lblSpan;
	private JFormattedTextField fmtdSpan;
	private JPanel panel_5;
	private JLabel lblShape;
	private JComboBox cmboBxShape;
	private JSpinner spinner;
	private JPanel panel_6;

	
	private ActionListener btnColorListener;
	private ChangeListener spinThresholdListener;
	private PropertyChangeListener fmtdSpanListener;
	private PropertyChangeListener fmtdMinSizeListener;
	private ItemListener shapeListener;
	

	/**
	 * Create the panel.
	 */
	
	public NAOBlobDetectionPanel(BlobProperties lBlobProps) {
		
		if(lBlobProps == null) {
			lBlobProps = new BlobProperties();
		}
		
		
		setLayout(new GridLayout(6, 0, 0, 0));
		
		setMinimumSize(new Dimension(240,192));
		
		final SpinnerNumberModel spinModelCThreshold = new SpinnerNumberModel(lBlobProps.getColorThreshold(), 0, 255, 1);
		
		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel_1);
		//setLayout(new GridLayout(0, 0, 0, 0));
		
		JLabel lblBlobColor = new JLabel("Blob Color:");
		panel_1.add(lblBlobColor);
		
		btnShowColorPicker = new JButton("Show Color Picker");
		
		btnShowColorPicker.setBackground(lBlobProps.getColor());
		
		panel_1.add(btnShowColorPicker);
		
		panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		add(panel_2);
		
		JLabel lblColorThreshold = new JLabel("Color Threshold:");
		panel_2.add(lblColorThreshold);
		
		spinnerThreshold = new JSpinner(spinModelCThreshold);
		panel_2.add(spinnerThreshold);
		
		panel_3 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		add(panel_3);
		
		lblMinimiumSize = new JLabel("Minimium Size:");
		panel_3.add(lblMinimiumSize);
		
		fmtdMinSize = new JFormattedTextField();
		
		fmtdMinSize.setValue(""+lBlobProps.getMinSize());
		panel_3.add(fmtdMinSize);
		fmtdMinSize.setColumns(10);
		
		/*
		SpinnerNumberModel spinModel2 = new SpinnerNumberModel(Integer.MAX_VALUE-1, Integer.MIN_VALUE+1, Integer.MAX_VALUE-1, 1);
		spinner = new JSpinner(spinModel2);
		//spinner.setMinimumSize(new Dimension(80,30));
		spinner.setPreferredSize(new Dimension(102,20));
		panel_3.add(spinner);
		*/
		
		panel_4 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_4.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		add(panel_4);
		
		lblSpan = new JLabel("Span:");
		panel_4.add(lblSpan);
		
		fmtdSpan = new JFormattedTextField();
		fmtdSpan.setValue(""+lBlobProps.getSpan());
		fmtdSpan.setColumns(10);
		panel_4.add(fmtdSpan);
		
		panel_5 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_5.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		add(panel_5);
		
		lblShape = new JLabel("Shape:");
		panel_5.add(lblShape);
		
		cmboBxShape = new JComboBox();
		cmboBxShape.addItem("Circle");
		cmboBxShape.addItem("Unknown");
		cmboBxShape.setSelectedItem(lBlobProps.getShape());
		panel_5.add(cmboBxShape);
		
		//JColorChooser(Color.RED);
		
		this.blobProps = lBlobProps;
		
		btnColorListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onColorBtnClicked(e);
			}
		};
		
		btnShowColorPicker.addActionListener(btnColorListener);
		
		spinThresholdListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				blobProps.setColorThreshold(spinModelCThreshold.getNumber().intValue());
			}
		};
		spinnerThreshold.addChangeListener(spinThresholdListener);
		
		fmtdMinSizeListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {				
				int value;
				value  = Integer.parseInt(fmtdMinSize.getText());
				//System.out.println("MinSize changed, new Value: "+value);
				blobProps.setMinSize(value);
			}
		};
		fmtdMinSize.addPropertyChangeListener(fmtdMinSizeListener);
		
		fmtdSpanListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				float value;
				value  = Float.parseFloat(fmtdSpan.getText());
				//System.out.println("Span changed, new Value: "+value);
				blobProps.setSpan(value);
			}
		};
		fmtdSpan.addPropertyChangeListener(fmtdSpanListener);
		
		shapeListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				blobProps.setShape((String)cmboBxShape.getSelectedItem());
			}
		};
		
		cmboBxShape.addItemListener(shapeListener);

	}
	
	private void onColorBtnClicked(ActionEvent e) {
		Color color;
		color = JColorChooser.showDialog(this, "Blob Color", blobProps.getColor());
		if(color != null) {
			blobProps.setColor(color);
			btnShowColorPicker.setBackground(color);
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(showDialog());
	}
	
	public static BlobProperties showDialog() {
		BlobProperties blobProps;
		blobProps = new BlobProperties();
		return showDialog(null, blobProps);
	}
	
	public static BlobProperties showDialog(JFrame frame, BlobProperties blobProps) {
		final JDialog dialog = new JDialog(frame, "Blob Config", true);;
		
		final NAOBlobDetectionPanel panel = new NAOBlobDetectionPanel(blobProps);
		
		/*
		if(frame != null && frame.getTitle() != "")
		{
			dialog = new JDialog(frame, true);
		}
		else
		{
			dialog = new JDialog(frame, "Blob Config", true);
		}
		*/
		
		
		dialog.setBounds(100, 100, 450, 300);
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setMinimumSize(new Dimension(240,275));
		
		dialog.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//panel.setLayout(new FlowLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialog.getContentPane().add(panel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			dialog.getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dialog.setVisible(false);
						dialog.dispose();
					}
					
				});
				buttonPane.add(okButton);
				dialog.getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						panel.uninitialize();
						panel.blobProps = null;
						dialog.setVisible(false);
						dialog.dispose();
					}
					
				});
				buttonPane.add(cancelButton);
			}
		}
		dialog.getContentPane().add(panel);
		dialog.setVisible(true);
		return panel.getBlobProperties();
	}
	
	public BlobProperties getBlobProperties() {
		return blobProps;
	}
	
	public void uninitialize() {
		btnShowColorPicker.removeActionListener(btnColorListener);
		spinnerThreshold.removeChangeListener(spinThresholdListener);
		fmtdMinSize.removePropertyChangeListener(fmtdMinSizeListener);
		fmtdSpan.removePropertyChangeListener(fmtdSpanListener);
		cmboBxShape.removeItemListener(shapeListener);
	}

}
