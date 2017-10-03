package ua.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ua.entity.Cases;
import ua.entity.CasesToFind;
import ua.entity.Courts;
import ua.entity.SelectedCases;
import ua.entity.SelectedCasesHistory;
import ua.entity.TemporaryCases;
import ua.graphics.CasesToFindFrame;
import ua.graphics.CourtsListFrame;
import ua.graphics.SelectedCasesListFrame;
import ua.mainLogic.Parsing;
import ua.mainLogic.ParsingTempCases;
import ua.mainLogic.VisualLogic;
import ua.repository.CaseRepository;
import ua.repository.CasesToFindRepository;
import ua.repository.SelectedCasesHistoryRepository;
import ua.repository.SelectedCasesRepository;
import ua.repository.TemporaryCasesRepository;
import ua.tableModel.TableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;
import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;

@Service
public class FirstPage extends JFrame {

	private static final long serialVersionUID = 8593672284935730017L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnNewButton = new JButton("Пошук");
	private JScrollPane scrollPane;
	private JTable table;
	public TableModel model;
	private JButton button;
	public JButton btnNewButton_1;
	private JButton button_3;
	private List<SelectedCases> listSelectedCases;
	private List<TemporaryCases> listTempCases;
	private List<Courts> courts;
	private List<CasesToFind> listCasesToFind;
	private TemporaryCasesRepository temporaryCasesRepository;

	public FirstPage() {
		setFont(new Font("Times New Roman", Font.PLAIN, 14));
		setTitle("Cases List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("Введіть дані сторони справи");

		textField = new JTextField();
		textField.setColumns(18);

		/////////////////////////////////////////////////////////////////////
		btnNewButton = new JButton("Пошук");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				

			}
		});

		///////////////////////////////////////////////////////////////
		button = new JButton("Обрати суд");

		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					CourtsListFrame lof = new CourtsListFrame(run);
					lof.setVisible(true);
				} catch (Exception exc) {
					exc.printStackTrace();
				}

			}

		});

		////////////////////////////////////////////////////////////////////
		btnNewButton_1 = new JButton("Додати в обране\r\n");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<Cases> listtt = new VisualLogic().findAll(run);
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(null, "Потрібно обрати справу!");
					return;
				}
				String cas = (String) table.getValueAt(row, 2);
				System.out.println(cas);
				Cases casesTemp = null;
				for (Cases i : listtt) {
					if (i.getNumber().toString().equals(cas)) {
						casesTemp = i;
					}
				}

				SelectedCasesRepository selCas = run.getBean(SelectedCasesRepository.class);
				List<SelectedCases> listSelecCas = new VisualLogic().findAllSelectedCases(run);
				for (SelectedCases selectedCases : listSelecCas) {
					if (selectedCases.getNumber().equals(casesTemp.getNumber())) {
						JOptionPane.showMessageDialog(null, "Дана справа вже є в переліку обраних");
						return;
					}
				}
				SelectedCases sc = new SelectedCases();
				sc.setDate(casesTemp.getDate());
				sc.setJudge(casesTemp.getJudge());
				sc.setNumber(casesTemp.getNumber());
				sc.setSides(casesTemp.getSides());
				sc.setType(casesTemp.getType());
				sc.setCourt(casesTemp.getCourt());
				sc.setHistory(casesTemp.getDate().toString());
				selCas.save(sc);

				try {
					SelectedCasesListFrame sclf = new SelectedCasesListFrame(run);
					sclf.setVisible(true);

				} catch (Exception exc) {
					System.out.println("problem 2");
					exc.printStackTrace();
				}
			}

		});

		/////////////////////////////////////////////////////////////////////////
		JButton button_1 = new JButton("Обране");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					SelectedCasesListFrame sclf = new SelectedCasesListFrame(run);
					sclf.setVisible(true);

				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		/////////////////////////////////////////////////////////////////////////
		JButton button_2 = new JButton("Очистити ");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CaseRepository caseRep = run.getBean(CaseRepository.class);
				List<Cases> listCases = new VisualLogic().findAll(run);
				caseRep.delete(listCases);
				refresh();
			}
		});
		/////////////////////////////////////////////////////////////////////////
		button_3 = new JButton("Відслідковувати");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CasesToFindFrame ctf = new CasesToFindFrame(run);
				ctf.setVisible(true);
			}
		});

		/////////////////////////////////////////////////////////////////////////
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(5).addComponent(label).addGap(5)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(button_2)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(button_3)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(button)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton_1)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(button_1)
						.addContainerGap(112, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(9).addComponent(label))
						.addGroup(gl_panel.createSequentialGroup().addGap(5)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(btnNewButton)
										.addComponent(button_2).addComponent(button_3).addComponent(button)
										.addComponent(btnNewButton_1).addComponent(button_1)))
						.addGroup(gl_panel.createSequentialGroup().addGap(6).addComponent(textField,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		//////////////////////////////////////////////////////////////////////////////

		scrollPane = new JScrollPane();
		scrollPane.setFont(scrollPane.getFont().deriveFont(scrollPane.getFont().getSize() + 5f));
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
		//////////////////////////////////////////////////////////////////////////////

//		new Refresh().refreshHourly();
		//////////////////////////////////////////////////////////////////////////////
	}
	

		public void refresh() {
			try {

				List<Cases> cases = new VisualLogic().findAll(run);

				model = new TableModel(cases);
				table.setModel(model);
				table.setFont(new Font("Times new roman", Font.PLAIN, 14));
				table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
				table.setVisible(true);

				TableColumnModel colModel = table.getColumnModel();
				colModel.getColumn(0).setCellRenderer(new DateCellRenderer());
				colModel.getColumn(1).setPreferredWidth(70);
				colModel.getColumn(2).setPreferredWidth(50);
				colModel.getColumn(3).setPreferredWidth(200);
				colModel.getColumn(4).setPreferredWidth(400);
				colModel.getColumn(5).setPreferredWidth(100);

				table.setAutoCreateRowSorter(true);

			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		/////////////////////////////////////////////////////////////////////////////
		@Scheduled(fixedDelay=5000)
		public void refreshHourly() {
//			new Thread(new Runnable() {
//				public void run() {
//					while (true) {
//
//						try {
//							Thread.sleep(5000);
							// ______________________________________________перевірка на перепризначення справ______________________________________________________________
							System.out.println("відбувається перевірка...");
						
							listSelectedCases = new VisualLogic().findAllSelectedCases(run);

							listTempCases = new VisualLogic().findAllTemporaryCases(run);

							courts = new VisualLogic().findAllCourts(run);

							SelectedCasesRepository selCasRepository = run.getBean(SelectedCasesRepository.class);

							ArrayList<Courts> arList = new ArrayList<Courts>();
							Courts tempCourt = null;

							for (Courts court : courts) {
								for (SelectedCases selCases : listSelectedCases) {
									if (selCases != null) {
										if (selCases.getCourt().equals(court.getName())) {

											if (court.equals(tempCourt)) {

											} else {

												arList.add(court);
											}
											tempCourt = court;
										}
									}
								}
							}
							
							int i = 1;
							for (Courts courts2 : arList) {
								new ParsingTempCases().parse(run, courts2);
								// Thread.sleep(5000);
								System.out.println("Парсінг для перевірки перепризначення справ -1" + i);
								i++;
							}
							SelectedCasesHistoryRepository selCasHistRepository = run
									.getBean(SelectedCasesHistoryRepository.class);
							SelectedCasesHistory sch = new SelectedCasesHistory();
							temporaryCasesRepository = run.getBean(TemporaryCasesRepository.class);

							for (TemporaryCases tempCases : listTempCases) {
								for (SelectedCases selCases : listSelectedCases) {
									if (tempCases.getNumber().equals(selCases.getNumber())
											&& !tempCases.getDate().equals(selCases.getDate())) {
										System.out.println("знайдено розбіжності в датах справи :" + selCases.getNumber());
										selCases.setDate(tempCases.getDate());
										selCasRepository.save(selCases);
										sch.setSelectedCases(selCases);
										sch.setHistory(selCases.getDate().toString());
										selCasHistRepository.save(sch);
									}
								}
							}
							// temporaryCasesRepository.delete(listTempCases); ?????
							// _______________________________________перевірка для списку відстежуваних справ_________________________________________________________________________
							Courts tempCourt1 = null;
							temporaryCasesRepository = run.getBean(TemporaryCasesRepository.class);
//							listTempCases = new VisualLogic().findAllTemporaryCases(run);
							String selectedCase = null;
							SelectedCasesRepository selCas = run.getBean(SelectedCasesRepository.class);
							CasesToFindRepository ctfr = run.getBean(CasesToFindRepository.class);
							listCasesToFind = new VisualLogic().findAllCasesToFind(run);

							ArrayList<Courts> arList1 = new ArrayList<Courts>();

							for (CasesToFind casToFind : listCasesToFind) {
								for (Courts court : courts) {
									if (casToFind != null) {
										if (casToFind.getCourt().equals(court.getName())) {

											if (court.equals(tempCourt1)) {

											} else {

												arList1.add(court);
												System.out.println(court);
											}
											tempCourt1 = court;
										}
									}
								}
							}
							for (Courts arL : arList1) {
								new ParsingTempCases().parse(run, arL);
								// Thread.sleep(5000);
								System.out.println("Парсінг для перевірки призначення справ із списку відстежуваних -2");
							}
						
							for (TemporaryCases tempCases : listTempCases) {
								for (CasesToFind listctf : listCasesToFind) {
									if (tempCases.getSides().toLowerCase()
											.contains(listctf.getCasesToFind().toLowerCase())) {
										SelectedCases sc = new SelectedCases();
										sc.setDate(tempCases.getDate());
										sc.setJudge(tempCases.getJudge());
										sc.setNumber(tempCases.getNumber());
										sc.setSides(tempCases.getSides());
										sc.setType(tempCases.getType());
										sc.setCourt(tempCases.getCourt());
										sc.setHistory(tempCases.getDate().toString());
										selCas.save(sc);
										selectedCase = listctf.getCasesToFind();
										JOptionPane.showMessageDialog(null, selectedCase + " Додана в обрані.");
										ctfr.delete(listctf);
									}
								}
							}

							temporaryCasesRepository.delete(listTempCases);

//						} catch (Exception e) {
//							System.out.println("problem 3");
//							e.printStackTrace();
//						}
//					}
//				}
//			}).start();
		}
		
		public JButton getBtnNewButton() {
			return btnNewButton;
		}

		public JButton getButton() {
			return button;
		}

		public JButton getBtnNewButton_1() {
			return btnNewButton_1;
		}

		public JButton getButton_3() {
			return button_3;
		}


		public static long getSerialversionuid() {
			return serialVersionUID;
		}


		public JPanel getContentPane() {
			return contentPane;
		}


		public JTextField getTextField() {
			return textField;
		}


		public JScrollPane getScrollPane() {
			return scrollPane;
		}


		public JTable getTable() {
			return table;
		}


		public TableModel getModel() {
			return model;
		}


		public List<SelectedCases> getListSelectedCases() {
			return listSelectedCases;
		}


		public List<TemporaryCases> getListTempCases() {
			return listTempCases;
		}


		public List<Courts> getCourts() {
			return courts;
		}


		public List<CasesToFind> getListCasesToFind() {
			return listCasesToFind;
		}


		public TemporaryCasesRepository getTemporaryCasesRepository() {
			return temporaryCasesRepository;
		}


		public void setModel(TableModel tableModel) {
			this.model = tableModel;
		}
		
	/////////////////////////////////////////////////////////////////////////////

}
