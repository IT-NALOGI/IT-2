package avto.rent;

import avto.rent.model.Rental;
import avto.rent.repository.RentalRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

public class RentalQueryServiceImpl extends RentalQueryServiceGrpc.RentalQueryServiceImplBase {
    @Inject
    RentalRepository rentalRepository;
    @Override
    public void getRental(GetRentalRequest request, StreamObserver<RentalResponse> responseObserver) {
        try {
            ObjectId objectId = new ObjectId(request.getRentalId());
            Rental rental = rentalRepository.findById(objectId);
            if (rental != null) {
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
