package ua.controller;

import java.awt.Font;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.entity.Cases;
import ua.mainLogic.VisualLogic;
import ua.tableModel.TableModel;
import ua.view.DateCellRenderer;
import ua.view.FirstPage;

@Service
public class MainController {
	
	@Autowired
	private VisualLogic visualLogic;

	private FirstPage mainFrame;
	
	

	@PostConstruct
	void post() {
		mainFrame.setVisible(true);
		addEventListenerToNewButton();
		
	}
	
	void addEventListenerToNewButton() {
		mainFrame.getBtnNewButton().addActionListener((e) -> {
			try {
				String text = mainFrame.getTextField().getText();

				List<Cases> cases = null;

				if (text != null && text.trim().length() > 0) {

					cases = visualLogic.findElement(text);

				} else {
					cases = visualLogic.findAll();
				}
				TableModel model = new TableModel(cases);
				JTable table = mainFrame.getTable();
				mainFrame.setModel(model);
				table.setModel(model);
				table.setFont(new Font("Times new roman", Font.PLAIN, 14));
				table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
				TableColumnModel colModel = table.getColumnModel();
				colModel.getColumn(0).setPreferredWidth(100);
				colModel.getColumn(0).setCellRenderer(new DateCellRenderer());
				colModel.getColumn(1).setPreferredWidth(70);
				colModel.getColumn(2).setPreferredWidth(70);
				colModel.getColumn(3).setPreferredWidth(300);
				colModel.getColumn(4).setPreferredWidth(300);
				colModel.getColumn(5).setPreferredWidth(100);
				table.setAutoCreateRowSorter(true);

			} catch (Exception exc) {
				System.out.println("problem 1");
				exc.printStackTrace();

			}
		});
	}
}
