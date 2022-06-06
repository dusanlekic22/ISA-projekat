package isaproject.test.unit.cottage;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.repository.CustomerRepository;
import isaproject.repository.cottage.CottageRepository;
import isaproject.service.cottage.CottageReservationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PessimisticLockTest {

	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CottageReservationService cottageReservationService;
	

	@Test(expected = PessimisticLockingFailureException.class)
	public void testPessimisticLockingScenario() throws Throwable {

		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 1");
		        //cottageRepository.findById(1L).get(); // izvrsavanje transakcione metode traje oko 200 milisekundi
				CottageReservationDTO cottageReservationDTO = new CottageReservationDTO();
				cottageReservationDTO.setDuration(new DateTimeSpan(LocalDateTime.now(),LocalDateTime.now().plusDays(2)));
				cottageReservationDTO.setCottage(cottageRepository.findById(1L).get());
				Customer customer =new Customer();
				customer.setId(6L);
				cottageReservationDTO.setCustomer(customer);
				try {
					cottageReservationService.reserveCottageOwner(cottageReservationDTO,null);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		Future<?> future2 = executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");
		        try { Thread.sleep(150); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
		        /*
		         * Drugi thread pokusava da izvrsi transakcionu metodu findOneById dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
		         * Metoda je oznacena sa NO_WAIT, sto znaci da drugi thread nece cekati da prvi thread zavrsi sa izvrsavanjem metode vec ce odmah dobiti PessimisticLockingFailureException uz poruke u logu:
		         * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : SQL Error: 0, SQLState: 55P03
		         * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : ERROR: could not obtain lock on row in relation "product"
		         * Prema Postgres dokumentaciji https://www.postgresql.org/docs/9.3/errcodes-appendix.html, kod 55P03 oznacava lock_not_available
		         */
		        CottageReservationDTO cottageReservationDTO = new CottageReservationDTO();
				cottageReservationDTO.setDuration(new DateTimeSpan(LocalDateTime.now(),LocalDateTime.now().plusDays(2)));
				cottageReservationDTO.setCottage(cottageRepository.findById(1L).get());
				cottageReservationDTO.setCustomer(new Customer());
		        cottageReservationService.reserveCustomer(cottageReservationDTO);
			}
		});
		try {
		    future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
		} catch (ExecutionException e) {
		    System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
		    throw e.getCause();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}

}
