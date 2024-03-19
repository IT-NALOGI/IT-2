import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.acme.CreateRentalRequest;
import org.acme.RentalResponse;
import org.acme.model.Rental;
import org.acme.repository.RentalRepository;
import org.acme.service.RentalServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class RentalServiceImplTest {

    private RentalServiceImpl rentalService;
    private RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
        rentalRepository = mock(RentalRepository.class);
        rentalService = new RentalServiceImpl();
        rentalService.rentalRepository = rentalRepository;
    }

    @Test
    void testCreateRental() {
        // Mock data
        CreateRentalRequest request = CreateRentalRequest.newBuilder()
                .setUserId("user123")
                .setAvtoId("avto456")
                .setStartDate("2024-04-01")
                .setEndDate("2024-04-30")
                .build();


        // Mock repository behavior
        RentalRepository rentalRepository = mock(RentalRepository.class);
        doNothing().when(rentalRepository).persistOrUpdate(any(Rental.class));

        // Call the method under test
        StreamObserver<RentalResponse> responseObserver = mock(StreamObserver.class);
        rentalService.createRental(request, responseObserver);

        // Verify that the response contains the expected rental data
        verify(responseObserver, times(1)).onNext(any());
        verify(responseObserver, times(1)).onCompleted();
    }

    // Other test methods...
}
