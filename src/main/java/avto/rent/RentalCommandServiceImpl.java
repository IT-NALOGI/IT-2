package avto.rent;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;
import avto.rent.model.Rental;
import avto.rent.*;
import avto.rent.repository.RentalRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@GrpcService
public class RentalCommandServiceImpl extends RentalCommandServiceGrpc.RentalCommandServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(RentalCommandServiceImpl.class);

    @Inject
    public RentalRepository rentalRepository;

    @Override
    public void createRental(CreateRentalRequest request, StreamObserver<RentalResponse> responseObserver) {
        try {
            Rental rental = new Rental(
                    request.getUserId(),
                    request.getAvtoId(),
                    LocalDate.parse(request.getStartDate()),
                    LocalDate.parse(request.getEndDate()),
                    "active" // Initial status
            );

            rentalRepository.persist(rental);
            RentalResponse response = buildRentalResponse(rental);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }


    @Override
    public void updateRental(UpdateRentalRequest request, StreamObserver<RentalResponse> responseObserver) {
        try {
            ObjectId objectId = new ObjectId(request.getRentalId());
            Rental rental = rentalRepository.findById(objectId);
            if (rental != null) {
                rental.setUserId(request.getUserId());
                rental.setAvtoId(request.getAvtoId());
                rental.setStartDate(LocalDate.parse(request.getStartDate()));
                rental.setEndDate(LocalDate.parse(request.getEndDate()));
                rental.setStatus(request.getStatus());

                rentalRepository.persistOrUpdate(rental);
                RentalResponse response = buildRentalResponse(rental);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
            }
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void deleteRental(DeleteRentalRequest request, StreamObserver<RentalResponse> responseObserver) {
        try {
            ObjectId objectId = new ObjectId(request.getRentalId());
            boolean result = rentalRepository.deleteById(objectId);
            RentalResponse response = RentalResponse.newBuilder()
                    .setStatus(result ? "deleted" : "not found")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    private RentalResponse buildRentalResponse(Rental rental) {
        return RentalResponse.newBuilder()
                .setRentalId(rental.getId().toString())
                .setUserId(rental.getUserId())
                .setAvtoId(rental.getAvtoId())
                .setStartDate(rental.getStartDate().toString())
                .setEndDate(rental.getEndDate().toString())
                .setStatus(rental.getStatus())
                .build();
    }
}

