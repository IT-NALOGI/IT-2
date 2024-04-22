package avto.rent.repository;
import avto.rent.model.Rental;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RentalRepository implements PanacheMongoRepository<Rental> {
}

