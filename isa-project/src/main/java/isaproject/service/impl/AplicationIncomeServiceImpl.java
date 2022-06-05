package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.IncomeDTO;
import isaproject.mapper.IncomeMapper;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingReservation;
import isaproject.model.Income;
import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.CottageReservation;
import isaproject.repository.FishingReservationRepository;
import isaproject.repository.boat.BoatReservationRepository;
import isaproject.repository.cottage.CottageReservationRepository;
import isaproject.service.AplicationIncomeService;
import isaproject.service.StatisticsService;

@Service
public class AplicationIncomeServiceImpl implements AplicationIncomeService {

	private StatisticsService statisticsService;
	private CottageReservationRepository cottageReservationRepository;
	private BoatReservationRepository boatReservationRepository;
	private FishingReservationRepository fishingReservationRepository;

	@Autowired
	public AplicationIncomeServiceImpl(StatisticsService statisticsService,
			CottageReservationRepository cottageReservationRepository,
			BoatReservationRepository boatReservationRepository,
			FishingReservationRepository fishingReservationRepository) {
		super();
		this.statisticsService = statisticsService;
		this.cottageReservationRepository = cottageReservationRepository;
		this.boatReservationRepository = boatReservationRepository;
		this.fishingReservationRepository = fishingReservationRepository;
	}

	@Override
	public IncomeDTO getAllIncomeYearly(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Income income = new Income();
		income.setYearlySum(new Double[(int) yearCount]);
		Income boatIncome = getAllBoatsIncomeYearly(duration);
		Income cottageIncome = getAllCottagesIncomeYearly(duration);
		Income fishingIncome = getAllFishingCoursesIncomeYearly(duration);
		for (int i = 0; i < yearCount; i++) {
			income.getYearlySum()[i] = boatIncome.getYearlySum()[i] + cottageIncome.getYearlySum()[i]
					+ fishingIncome.getYearlySum()[i];
		}
		return IncomeMapper.IncomeToIncomeDTO(income);
	}
	
	@Override
	public IncomeDTO getAllIncomeMonthly(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Income income = new Income();
		income.setMonthlySum(new Double[(int) yearCount][12]);
		Income boatIncome = getAllBoatsIncomeMonthly(duration);
		Income cottageIncome = getAllCottagesIncomeMonthly(duration);
		Income fishingIncome = getAllFishingCoursesIncomeMonthly(duration);
		for (int i = 0; i < yearCount; i++) {
			for (int j = 0; j < 12; j++) {
				income.getMonthlySum()[i][j] = boatIncome.getMonthlySum()[i][j] + cottageIncome.getMonthlySum()[i][j]
						+ fishingIncome.getMonthlySum()[i][j];
			}
		}
		return IncomeMapper.IncomeToIncomeDTO(income);
	}

	@Override
	public IncomeDTO getAllIncomeDaily(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Income income = new Income();
		income.setDailySum(new Double[(int) yearCount][12][31]);
		Income boatIncome = getAllBoatsIncomeDaily(duration);
		Income cottageIncome = getAllCottagesIncomeDaily(duration);
		Income fishingIncome = getAllFishingCoursesIncomeDaily(duration);
		for (int i = 0; i < yearCount; i++) {
			for (int j = 0; j < 12; j++) {
				for (int k = 0; k < 31; k++) {
					income.getDailySum()[i][j][k] = boatIncome.getDailySum()[i][j][k]
							+ cottageIncome.getDailySum()[i][j][k] + fishingIncome.getDailySum()[i][j][k];
				}
			}
		}
		return IncomeMapper.IncomeToIncomeDTO(income);
	}

	private Income getAllBoatsIncomeYearly(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Double[] incomeSum = new Double[(int) yearCount];
		Income income = new Income();
		for (BoatReservation reservation : boatReservationRepository.findByConfirmedIsTrue()) {
			for (int i = 0; i < yearCount; i++) {
				incomeSum = statisticsService.yearlyIncome(reservation.getDuration(), reservation.getSiteIncome(), i,
						incomeSum, duration.getStartDate().getYear());
			}
		}
		income.setYearlySum(incomeSum);
		return income;
	}

	private Income getAllBoatsIncomeMonthly(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Double[][] incomeSum = new Double[(int) yearCount][12];
		Income income = new Income();
		for (BoatReservation reservation : boatReservationRepository.findByConfirmedIsTrue()) {
			for (int i = 0; i < yearCount; i++) {
				for (int j = 0; j < 12; j++) {
					if (duration.isBetween(reservation.getDuration().getEndDate())) {
						incomeSum = statisticsService.monthlyIncome(reservation.getDuration(),
								reservation.getSiteIncome(), i, j, incomeSum, duration.getStartDate().getYear());
					}
				}
			}
		}
		income.setMonthlySum(incomeSum);
		return income;
	}

	private Income getAllBoatsIncomeDaily(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Double[][][] incomeSum = new Double[(int) yearCount][12][31];
		Income income = new Income();
		for (BoatReservation reservation : boatReservationRepository.findByConfirmedIsTrue()) {
			for (int i = 0; i < yearCount; i++) {
				for (int j = 0; j < 12; j++) {
					for (int k = 0; k < 31; k++) {
						if (duration.isBetween(reservation.getDuration().getEndDate())) {
							incomeSum = statisticsService.dailyIncome(reservation.getDuration(),
									reservation.getSiteIncome(), i, j, k, incomeSum, duration.getStartDate().getYear());
						}
					}
				}
			}
		}
		income.setDailySum(incomeSum);
		return income;
	}

	private Income getAllCottagesIncomeYearly(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Double[] incomeSum = new Double[(int) yearCount];
		Income income = new Income();
		for (CottageReservation reservation : cottageReservationRepository.findByConfirmedIsTrue()) {
			for (int i = 0; i < yearCount; i++) {
				incomeSum = statisticsService.yearlyIncome(reservation.getDuration(), reservation.getSiteIncome(), i,
						incomeSum, duration.getStartDate().getYear());
			}
		}
		income.setYearlySum(incomeSum);
		return income;
	}

	private Income getAllCottagesIncomeMonthly(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Double[][] incomeSum = new Double[(int) yearCount][12];
		Income income = new Income();
		for (CottageReservation reservation : cottageReservationRepository.findByConfirmedIsTrue()) {
			for (int i = 0; i < yearCount; i++) {
				for (int j = 0; j < 12; j++) {
					if (duration.isBetween(reservation.getDuration().getEndDate())) {
						incomeSum = statisticsService.monthlyIncome(reservation.getDuration(),
								reservation.getSiteIncome(), i, j, incomeSum, duration.getStartDate().getYear());
					}
				}
			}
		}
		income.setMonthlySum(incomeSum);
		return income;
	}

	private Income getAllCottagesIncomeDaily(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Double[][][] incomeSum = new Double[(int) yearCount][12][31];
		Income income = new Income();
		for (CottageReservation reservation : cottageReservationRepository.findByConfirmedIsTrue()) {
			for (int i = 0; i < yearCount; i++) {
				for (int j = 0; j < 12; j++) {
					for (int k = 0; k < 31; k++) {
						if (duration.isBetween(reservation.getDuration().getEndDate())) {
							incomeSum = statisticsService.dailyIncome(reservation.getDuration(),
									reservation.getSiteIncome(), i, j, k, incomeSum, duration.getStartDate().getYear());
						}
					}
				}
			}
		}
		income.setDailySum(incomeSum);
		return income;
	}

	private Income getAllFishingCoursesIncomeYearly(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Double[] incomeSum = new Double[(int) yearCount];
		Income income = new Income();
		for (FishingReservation reservation : fishingReservationRepository.findByConfirmedIsTrue()) {
			for (int i = 0; i < yearCount; i++) {
				incomeSum = statisticsService.yearlyIncome(reservation.getDuration(), reservation.getSiteIncome(), i,
						incomeSum, duration.getStartDate().getYear());
			}
		}
		income.setYearlySum(incomeSum);
		return income;
	}

	private Income getAllFishingCoursesIncomeMonthly(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Double[][] incomeSum = new Double[(int) yearCount][12];
		Income income = new Income();
		for (FishingReservation reservation : fishingReservationRepository.findByConfirmedIsTrue()) {
			for (int i = 0; i < yearCount; i++) {
				for (int j = 0; j < 12; j++) {
					if (duration.isBetween(reservation.getDuration().getEndDate())) {
						incomeSum = statisticsService.monthlyIncome(reservation.getDuration(),
								reservation.getSiteIncome(), i, j, incomeSum, duration.getStartDate().getYear());
					}
				}
			}
		}
		income.setMonthlySum(incomeSum);
		return income;
	}

	private Income getAllFishingCoursesIncomeDaily(DateTimeSpan duration) {
		long yearCount = duration.getYears() + 1;
		Double[][][] incomeSum = new Double[(int) yearCount][12][31];
		Income income = new Income();
		for (FishingReservation reservation : fishingReservationRepository.findByConfirmedIsTrue()) {
			for (int i = 0; i < yearCount; i++) {
				for (int j = 0; j < 12; j++) {
					for (int k = 0; k < 31; k++) {
						if (duration.isBetween(reservation.getDuration().getEndDate())) {
							incomeSum = statisticsService.dailyIncome(reservation.getDuration(),
									reservation.getSiteIncome(), i, j, k, incomeSum, duration.getStartDate().getYear());
						}
					}
				}
			}
		}
		income.setDailySum(incomeSum);
		return income;
	}

}
