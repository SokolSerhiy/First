package ua.mainLogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableCellRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import ua.entity.Cases;
import ua.entity.CasesToFind;
import ua.entity.Courts;
import ua.entity.SelectedCases;
import ua.entity.TemporaryCases;
import ua.repository.CaseRepository;
import ua.repository.CasesToFindRepository;
import ua.repository.CourtsRepository;
import ua.repository.SelectedCasesRepository;
import ua.repository.TemporaryCasesRepository;

@Service
public class VisualLogic {
	@Autowired
	public CourtsRepository courtRepository;

	@Autowired
	public CaseRepository caseRepository;
	@Autowired
	public SelectedCasesRepository selCasRepos;

	@Autowired
	public TemporaryCasesRepository tempCaseRepository;

	public List<Cases> findAll() {

		try {
			List<Cases> listCases = caseRepository.findAll();
			for (Cases cases : listCases) {
				System.out.println(cases);
			}
			return listCases;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<>();

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public List<Cases> findElement(String text) {

		try {
			List<Cases> listCases = caseRepository.findAll();
			List<Cases> listCases2 = new ArrayList<Cases>();
			for (Cases cases : listCases) {
				if (cases.getSides().toString().toLowerCase().contains(text.toLowerCase())
						|| cases.getJudge().toString().toLowerCase().contains(text.toLowerCase())
						|| cases.getType().toString().toLowerCase().contains(text.toLowerCase())
						|| cases.getNumber().toString().toLowerCase().contains(text.toLowerCase())
						|| cases.getCourt().toString().toLowerCase().contains(text.toLowerCase())) {
					listCases2.add(cases);
					System.out.println(cases);
				}
			}

			return listCases2;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public List<Courts> findAllCourts(ConfigurableApplicationContext run) {

		try {
			CourtsRepository courtRepository = run.getBean(CourtsRepository.class);

			List<Courts> listCourts = courtRepository.findAll();

			return listCourts;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public List<SelectedCases> findAllSelectedCases(ConfigurableApplicationContext run) {

		try {
			SelectedCasesRepository selCasRepos = run.getBean(SelectedCasesRepository.class);
			List<SelectedCases> listSelecCas = selCasRepos.findAll();

			return listSelecCas;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public List<TemporaryCases> findAllTemporaryCases(ConfigurableApplicationContext run) {

		try {
			TemporaryCasesRepository tempCaseRepository = run.getBean(TemporaryCasesRepository.class);
			List<TemporaryCases> listCases = tempCaseRepository.findAll();

			return listCases;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public List<CasesToFind> findAllCasesToFind(ConfigurableApplicationContext run) {

		try {
			CasesToFindRepository casesToFindRepository = run.getBean(CasesToFindRepository.class);
			List<CasesToFind> listCasesToFind = casesToFindRepository.findAll();

			return listCasesToFind;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

	}

}
